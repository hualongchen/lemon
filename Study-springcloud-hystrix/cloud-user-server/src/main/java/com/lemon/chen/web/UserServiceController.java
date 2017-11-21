package com.lemon.chen.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhualong on 2017/7/24.
 */

@RestController
public class UserServiceController {

    public Logger logger = LoggerFactory.getLogger(UserServiceController.class);


    @Autowired
    private DiscoveryClient client;

    /**
     * 进行调试
     *
     * @return
     */
    @RequestMapping("/hello")
    public Object helloWord() {

        logger.info("hello !! this is  helloController   method is helloWord");

        System.out.println(client.getServices().toString());

        for (String service : client.getServices()) {

            System.out.println(service.toString());
        }

        return "helloWord";
    }
}
