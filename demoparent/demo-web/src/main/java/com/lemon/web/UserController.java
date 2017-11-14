package com.lemon.web;

import com.lemon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :chenhualong
 * @Date : created in 16:00 2017/11/13
 * @Description :
 */

@RestController
@Slf4j
public class UserController {


    @Autowired
    private UserService  userService ;


    /**
     * 测试模块链路是否通畅
     * @return
     */
    @RequestMapping("/test")
    public String testUserController(){

        System.out.println("demo-web-UserControoler");

        log.info("demo-web-UserControoler-log");

        return  userService.testUserSerivice();
    }


    /**
     * 测试mybatis分模块成功
     * @return
     */
    @RequestMapping("/test2")
    public Object  testDevUserPo(){

        log.info("demo-web-userController-devUserPO-log");

        return  userService.findUser();
    }
}
