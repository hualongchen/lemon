package com.lemon.service;

import com.lemon.dao.UserDao;
import com.lemon.dao.user.bean.DevUserPO;
import com.lemon.dao.user.mapper.DevUserPOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :chenhualong
 * @Date : created in 15:58 2017/11/13
 * @Description :
 */

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserDao  userDao ;

    @Autowired
    private DevUserPOMapper   devUserPOMapper ;


    /**
     * 测试基本模块的调用
     * @return
     */
    public String testUserSerivice(){

        System.out.println("demo-service-userService");

        log.info("demo-service-userService-log");

        return userDao.testUserDao();
    }


    /**
     * 测试mybatis加载是否正常
     * @return
     */
    public DevUserPO  findUser(){


        DevUserPO  devUserPO = new DevUserPO();

        devUserPO.setUsername("test Hello Word");

        devUserPOMapper.insertSelective(devUserPO);

        return  devUserPO;

    }


}
