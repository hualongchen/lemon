package com.lemon.chen.web;

import com.lemon.chen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhualong on 2017/7/25.
 */

@RestController
public class HystrixUserController {


    @Autowired
    private UserService  userService ;



    @GetMapping("/hystrix")
    public String  getUser(){

        return userService.consumer();
    }
}
