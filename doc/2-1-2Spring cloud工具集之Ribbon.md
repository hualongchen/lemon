## (Spring cloud 工具集二）客户端负责均衡Ribbon

###1.0 简介
Ribbon是一个客户端负载均衡器，它可以很好地控制HTTP和TCP客户端的行为。Feign已经使用Ribbon。

在使用Spring Cloud Ribbon的时候，不论是与Eureka还是Consul结合，都会在引入Spring Cloud Eureka或Spring Cloud Consul依赖的时候通过自动化配置来加载上述所说的配置内容，所以我们可以快速在Spring Cloud中实现服务间调用的负载均衡。

###2.0 Ribbon实践

这些个模块都是增加到服务消费端上，切莫加错了！

####2.1 增加spring cloud的依赖

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
#### 2.2增加Spring Ribbon的依赖

```
<!--消费端负载均衡-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-ribbon</artifactId>
		</dependency>
``` 

####2.3启动项增加注解项

```
@SpringBootApplication
@EnableDiscoveryClient
public class CloudServiceConsumerApplication {


	/**
	 * 初始化客户端调用template
	 * @return
	 */
	@Bean
	@LoadBalanced //增加ribbon的负载均衡
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(CloudServiceConsumerApplication.class, args);
	}
}

```

####2.4利用RestTemplate进行微服务调用（erueka上查找服务）

```

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/consumer")
    public String dc() {

        /**
         * 通过服务的选择，找出自己需要的服务，然后进行调用
         */
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-service-provider");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/hello";
        System.out.println(url);
        return restTemplate.getForObject(url, String.class);
    }
    
    /**
     * 直接使用ribbion负载后的template进行调用微服务
     * @return
     */
    @GetMapping("/ribbon")
    public String  ribbon(){

        System.out.println("use the ribbon 进行负责");
        return restTemplate.getForObject("http://user-service-provider/hello", String.class);
    }

```

####2.5 对template同样可以进行一些属性的编辑

```
@Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return template;
    }
```

####2.6 落实到配置文件
```
开启重试机制
spring.cloud.loadbalancer.retry.enabled=true
断路器时间必须要大于Ribbon时间，不然不会重试
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=10000
连接超时时间
hello-service.ribbon.ConnectTimeout=250
请求处理超时时间
hello-service.ribbon.ReadTimeout=1000
对所有的操作都进行重试机制
hello-service.ribbon.OkToRetryOnAllOperations=true
切换实例后的重试次数
hello-service.ribbon.MaxAutoRetriesNextServer=2
当前实例的重试次数
hello-service.ribbon.MaxAutoRetries=1
```

###3.0 实战案例与Demo

https://github.com/hualongchen/lemon

####**有疑问和兴趣，请添加个人公众号**

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/qrcode_for_gh_363af0fc9423_430.jpg "在这里输入图片标题")