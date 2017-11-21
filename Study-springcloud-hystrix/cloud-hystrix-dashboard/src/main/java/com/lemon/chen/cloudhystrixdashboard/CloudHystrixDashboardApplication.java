package com.lemon.chen.cloudhystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class CloudHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudHystrixDashboardApplication.class, args);
	}
}
