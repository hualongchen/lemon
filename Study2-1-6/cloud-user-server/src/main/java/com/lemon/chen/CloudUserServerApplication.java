package com.lemon.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//该注解能激活Eureka中的DiscoveryClient实现，这样才能实现Controller中对服务信息的输出
@EnableDiscoveryClient
@EnableCircuitBreaker
public class CloudUserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudUserServerApplication.class, args);
	}
}
