##  (Spring cloud 工具集一）服务注册中心

### 1.0  简介

  Spring Cloud Eureka是Spring Cloud Netflix项目下的服务治理模块。而Spring Cloud Netflix项目是Spring Cloud的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，它为Spring Boot应用提供了自配置的Netflix OSS整合。通过一些简单的注解，开发者就可以快速的在应用中配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（Eureka），断路器（Hystrix），智能路由（Zuul），客户端负载均衡（Ribbon）等。

### 2.0 主要用途

主要用于服务注册和发现， 并在此集群或则平台上进行服务的管理和维护。可以将服务器配置和部署为高可用性，每个服务器将注册服务的状态复制到其他服务器。

### 3.0 依赖maven
1.依赖spring  cloud 的版本

```
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
 
2.依赖eureka的maven

```
        <!--web 核心包-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--监控spring-boot-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!--服务发现注册-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>
```
### 4.0 服务注册搭建

#### 1.0.启动类增加注解

@EnableEurekaServer相当于开启此功能 

```
@SpringBootApplication
@EnableEurekaServer  //开启服务注册中心，供他们使用
public class CloudEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEurekaServerApplication.class, args);
	}
}
```

#### 2.0. application.properties 的配置

```
指定默认的日志格式为logback
logging.config=classpath:logback.xml
默认的eureka是 8761
server.port=8761
本应用的名称，如果是集群，请保持一致
spring.application.name=cloud-eureka-server
关闭监控点，否则无法访问info等重要信息
endpoints.configprops.enabled=false
本应用的hosts,这个需要在/etc/hosts配置
eureka.instance.hostname=localhost
代表自己是注册中心，禁止向自己注册，如果是集群，请删除此配置
eureka.client.register-with-eureka=false
是否从eureka获取注册信息，本身是注册中心，所有不用检索服务
eureka.client.fetch-registry=false
eureka client间隔多久去拉取服务注册信息，如果要迅速获取服务注册状态，可以缩小该值，比如5秒
eureka.client.registry-fetch-interval-seconds=30
心跳间隔时间
eureka.instance.lease-renewal-interval-in-seconds=30
服务失效时间
eureka.instance.lease-expiration-duration-in-seconds=60
关闭保护机制，如果失效了，还能在页面调用和查看，所以关闭
eureka.server.enable-self-preservation=true
定时清理已经关停的节点
eureka.server.eviction-interval-timer-in-ms=30000
eureka服务器的地址
eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
```

### 5.0. 建造一个微服务提供者

#### 5.1 加入上述maven的配置
#### 5.2 管理器上增加对应的注解

@EnableDiscoveryClient 将该应用服务注册到eureka上

```
@SpringBootApplication
//该注解能激活Eureka中的DiscoveryClient实现，这样才能实现Controller中对服务信息的输出
@EnableDiscoveryClient
public class CloudUserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudUserServerApplication.class, args);
	}
}
```
#### 5.3 application.xml的配置
```
spring.application.name=user-service-provider
server.port=9000
服务注册中心 cloud-eureka-server
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
```

### 6.0 访问

- 启动访问eureka : http://localhost:8761
- 启动微服务：http://localhost:9000
- 观察8761端口界面对应的服务注册信息

### 7.0 eureka集群配置

#### 7.1 节点A配置文件
```
这个地方是要配置hosts，配置地址是/etc/hosts/
eureka.instance.hostname=lemon1
eureka服务器的地址
eureka.client.serviceUrl.defaultZone=http://lemon2:8762/eureka/
去掉配置：eureka.client.register-with-eureka=false

```
#### 7.2节点B配置文件
```
eureka.instance.hostname=lemon2
eureka服务器的地址
eureka.client.serviceUrl.defaultZone=http://lemon1:8761/eureka/
```
这样做可以做到A节点和B节点进行相互注册，进来的服务也同时进行注册到两个节点上面，起到了平衡负载的作用


### 8.0 附录

#### 1.0  完整的eureka配置
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/1.jpg "在这里输入图片标题")
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/2.jpg "在这里输入图片标题")
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/3.jpg "在这里输入图片标题")
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/4.jpg "在这里输入图片标题")
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/5.jpg "在这里输入图片标题")
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/6.jpg "在这里输入图片标题")




