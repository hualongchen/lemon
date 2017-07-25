package com.example.cloudapigetway;

import com.example.cloudapigetway.filter.MyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy   //API网关
public class CloudApiGetwayApplication {


    @Bean
    public MyFilter myFilter() {

        return new MyFilter();
    }


    public static void main(String[] args) {
        SpringApplication.run(CloudApiGetwayApplication.class, args);
    }
}
