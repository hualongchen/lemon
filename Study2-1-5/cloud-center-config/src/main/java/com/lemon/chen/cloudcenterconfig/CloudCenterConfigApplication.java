package com.lemon.chen.cloudcenterconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer //开启Spring Cloud Config的服务端功能
public class CloudCenterConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudCenterConfigApplication.class, args);
	}
}
