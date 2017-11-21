package com.lemon.springboot.study125.web;

import com.lemon.springboot.study125.dao.bean.user.SpringBootUserPO;
import com.lemon.springboot.study125.dao.mapper.user.SpringBootUserPOMapper;
import com.lemon.springboot.study125.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhualong
 */

@RestController
public class MybatisController {


    @Autowired
    private SpringBootUserPOMapper  springBootUserPOMapper;

    @Autowired
    private UserService  userService ;

    /**
     * 测试一  是否打通数据库
     * @return
     */
    @GetMapping("/mybatis")
    public Object test(){


        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融");

        springBootUserPOMapper.insert(springBootUserPO);


        return springBootUserPOMapper.selectAll().get(0);

    }

    /**
     * 测试二，联通mybatis表加事务
     * @return
     */
    @GetMapping("/mybatis2")
    public Object test2(){

        SpringBootUserPO  springBootUserPO = new SpringBootUserPO();

        springBootUserPO.setUsername("通融2");

        return userService.addUser(springBootUserPO);
    }
}
