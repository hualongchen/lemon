## (Spring cloud 工具集四）服务容错保护 Hystrix
###  1.0 简介
在Spring Cloud Hystrix中实现了线程隔离、断路器等一系列的服务保护功能。它也是基于Netflix的开源框架 Hystrix实现的，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备了服务降级、服务熔断、线程隔离、请求缓存、请求合并以及服务监控等强大功能。

###  1.1重要的概念

1. 断路器的三个重要参数：快照时间窗、请求总数下限、错误百分比下限。这个参数的作用分别是：

2.  **快照时间窗**：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒。
3.  **请求总数下限**：在快照时间窗内，必须满足请求总数下限才有资格根据熔断。默认为20，意味着在10秒内，如果该hystrix命令的调用此时不足20次，即时所有的请求都超时或其他原因失败，断路器都不会打开。

4.  **错误百分比下限**：当请求总数在快照时间窗内超过了下限，比如发生了30次调用，如果在这30次调用中，有16次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%下限情况下，这时候就会将断路器打开。
5.  **熔断器打开**：如果设置为5秒，那么每个请求就都要延迟5秒才会返回。当熔断器在10秒内发现请求总数超过20，
并且错误百分比超过50%，这个时候熔断器打开。打开之后，再有请求调用的时候，将不会调用主逻辑，
而是直接调用降级逻辑，这个时候就不会等待5秒之后才返回fallback。通过断路器，
实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果
6.  **恢复逻辑**：
hystrix也为我们实现了自动恢复功能。当断路器打开，对主逻辑进行熔断之后，
hystrix会启动一个休眠时间窗，在这个时间窗内，降级逻辑是临时的成为主逻辑，
当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，
如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复，如果这次请求依然有问题，
断路器继续进入打开状态，休眠时间窗重新计时。

###  2.0 Hystrix入门使用
#### 首先在服务消费端增加以下依赖
####  2.1 增加maven的依赖

```
<!--需要具体指明version版本的放在下面-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

```

####  2.2 增加Hystrix的maven依赖
```
<!--增加断路器-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
```

####  2.3 启动项进行配置

```
@SpringBootApplication
@EnableCircuitBreaker //开启hystrix
@EnableDiscoveryClient
public class CloudServiceConsumerApplication {


	/**
	 * 初始化客户端调用template
	 * @retur
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(CloudServiceConsumerApplication.class, args);
	}
}
```

####  2.4  Service层完成服务降级和调用
```
/**
 * Created by chenhualong on 2017/7/25.
 */

@Service
public class UserService {


    @Autowired
    private RestTemplate  template ;


    /**
     * 业务调用方法
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public String  consumer(){

      return   template.getForObject("http://user-service-provider/hello", String.class);
    }


    /**
     *hystrix服务降级错误，返回back调用方法
     * 降级错误回调方法
     * @return
     */
    public String fallback(){

        return "ERROR";
    }
}
```

####  2.5 Controller层完成路由
```
/**
 * Created by chenhualong on 2017/7/25.
 */

@RestController
public class HystrixUserController {


    @Autowired
    private UserService  userService ;



    @GetMapping("/hystrix")
    public String  getUser(){

        return userService.consumer();
    }
}
```

####  2.6 增加Hystrix的面板监控(新起一个项目)
#####  2.6.1 增加面板监控的Maven依赖
```
        <!--Hystrix监控面板-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
		</dependency>

```
#####  2.6.2增加启动项注解

```
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class CloudHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudHystrixDashboardApplication.class, args);
	}
}
```

#####  2.6.3 增加微服务的监控注解
```

@SpringBootApplication
//该注解能激活Eureka中的DiscoveryClient实现，这样才能实现Controller中对服务信息的输出
@EnableDiscoveryClient
//
@EnableCircuitBreaker
public class CloudUserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudUserServerApplication.class, args);
	}
}
```
#####  2.6.4 监控相关讲解
```
1. 监控的三种模式
默认的集群监控：通过URLhttp://turbine-hostname:port/turbine.stream开启，实现对默认集群的监控。
指定的集群监控：通过URLhttp://turbine-hostname:port/turbine.stream?cluster=[clusterName]开启，实现对clusterName集群的监控。
单体应用的监控：通过URLhttp://hystrix-app:port/hystrix.stream开启，实现对具体某个服务实例的监控

注意： 前两者都对集群的监控，需要整合Turbine才能实现。


2. 访问地址
http://localhost:9004/hystrix

3. 具体的监控页面
http://localhost:9004/hystrix.stream
```

### 2.6.5 增加Turbine集群监控
新建立项目，然后增加maven依赖

```
<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-turbine</artifactId>
	</dependency>
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
```
启动项，开启功能检测

```
@Configuration
@EnableAutoConfiguration
@EnableTurbine
@EnableDiscoveryClient
public class TurbineApplication {
	public static void main(String[] args) {
		SpringApplication.run(TurbineApplication.class, args);
	}
}
```
增加application.xml的配置

```
spring.application.name=turbine
server.port=8989
management.port=8990
eureka.client.serviceUrl.defaultZone=http://localhost:1001/eureka/
turbine.app-config=eureka-consumer-ribbon-hystrix
turbine.cluster-name-expression="default"
turbine.combine-host-port=true
turbine.app-config参数指定了需要收集监控信息的服务名；
turbine.cluster-name-expression 参数指定了集群名称为default，当我们服务数量非常多的时候，可以启动多个Turbine服务来构建不同的聚合集群，而该参数可以用来区分这些不同的聚合集群，同时该参数值可以在Hystrix仪表盘中用来定位不同的聚合集群，只需要在Hystrix Stream的URL中通过cluster参数来指定；
turbine.combine-host-port参数设置为true，可以让同一主机上的服务通过主机名与端口号的组合来进行区分，默认情况下会以host来区分不同的服务，这会使得在本地调试的时候，本机上的不同服务聚合成一个服务来统计
```
访问地址：
http://localhost:8989/turbine.stream


###  3.0 实战案例与Demo

https://github.com/hualongchen/lemon

####**有疑问和兴趣，请添加个人公众号**

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/qrcode_for_gh_363af0fc9423_430.jpg "在这里输入图片标题")