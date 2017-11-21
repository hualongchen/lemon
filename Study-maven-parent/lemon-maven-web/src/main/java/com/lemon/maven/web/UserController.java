package com.lemon.maven.web;

import com.lemon.maven.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :chenhualong
 * @Date : created in 14:54 2017/11/10
 * @Description :
 */

@RestController
public class UserController {


    @Resource
    private UserService  userService;



    @GetMapping("/test")
    public String test(){

        System.out.println("this is test controller");

        return userService.test();
    }

}
