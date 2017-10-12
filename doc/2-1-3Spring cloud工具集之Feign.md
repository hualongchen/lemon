## (Spring cloud 工具集三）声明式Http客户端Feign

### 1.0 简介
Feign是一个声明式的Web服务客户端。这使得Web服务客户端的写入更加方便 要使用Feign创建一个界面并对其进行注释。它具有可插入注释支持，包括Feign注释和JAX-RS注释。Feign还支持可插拔编码器和解码器。Spring Cloud增加了对Spring MVC注释的支持，并使用Spring Web中默认使用的HttpMessageConverters。Spring Cloud集成Ribbon和Eureka以在使用Feign时提供负载均衡的http客户端。

### 2.0 Feign的入门使用

#### 2.1 增加maven的依赖

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

#### 2.2 增加feign的maven依赖

```

<!--消费端封装http->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
        </dependency>
```

####  2.3 启动项开启

```
@SpringBootApplication
@EnableFeignClients    //开启feign
@EnableDiscoveryClient
public class CloudServiceConsumerApplication {


	/**
	 * 初始化客户端调用template
	 * @return
	 */
	@Bean

	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(CloudServiceConsumerApplication.class, args);
	}
}

```

####  2.4 增加匹配服务的接口

```
/**
 * Created by chenhualong on 2017/7/24.
 */

/**
 * 这里匹配对应的服务的应用名
 */
@FeignClient("user-service-provider")
public interface UserClient {

    /**
     * 这里和微服务的请求进行匹配,，和微服务一致
     * @return
     */
    @GetMapping("/hello")
    String consumer();

}

```

####  2.5业务的controller层实现调用

```
/**
 * Created by chenhualong on 2017/7/24.
 */

@RestController
public class FeignUserController {


    @Autowired
    UserClient   userClient ;

    @GetMapping("/consumer")
    public String dc() {

        return userClient.consumer();
    }
}
```

###  3.0 实战案例与Demo

https://github.com/hualongchen/lemon

####**有疑问和兴趣，请添加个人公众号**

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/qrcode_for_gh_363af0fc9423_430.jpg "在这里输入图片标题")