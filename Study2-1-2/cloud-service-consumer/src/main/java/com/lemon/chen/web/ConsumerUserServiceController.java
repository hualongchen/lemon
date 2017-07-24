package com.lemon.chen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by chenhualong on 2017/7/24.
 */

@RestController
public class ConsumerUserServiceController {


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


}
