package com.lemon.maven.service;

import com.lemon.maven.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :chenhualong
 * @Date : created in 14:43 2017/11/10
 * @Description :
 */

@Service
public class UserService {



    @Autowired
    private UserDao  userDao ;



    public String test(){

        System.out.println("this is  UserSerice");

        System.out.println(userDao.testUserDao());

        return  userDao.testUserDao();
    }

}
