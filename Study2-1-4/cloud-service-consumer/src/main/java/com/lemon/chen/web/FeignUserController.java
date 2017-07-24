package com.lemon.chen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
