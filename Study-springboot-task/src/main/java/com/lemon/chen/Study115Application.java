package com.lemon.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableCaching //开启缓存功能
@EnableAsync  //开启异步调用
public class Study115Application {

	public static void main(String[] args) {
		SpringApplication.run(Study115Application.class, args);
	}
}
