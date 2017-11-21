package com.lemon.chen.web;

import com.lemon.chen.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhualong on 2017/7/12.
 */

@RestController
public class TestUserController {

    @Autowired
    private ServiceImpl service ;


    @RequestMapping("/test")
    public String test(){

        service.findUser();

        return "success" ;
    }
}
