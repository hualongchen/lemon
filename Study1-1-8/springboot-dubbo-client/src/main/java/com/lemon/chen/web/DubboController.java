package com.lemon.chen.web;

import com.lemon.chen.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhualong on 2017/7/17.
 */

@RestController
public class DubboController {


    @Autowired
    private UserService  userService ;


    /**
     * 测试dubbo的联通性
     * @return
     */
    @RequestMapping("/dubbo")
    public String  countUser(){

        System.out.println("RPC DUBBO THE RESULT IS:" +userService.countUser());

        return  "SUCCESS" ;
    }



}
