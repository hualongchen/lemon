package com.lemon.dao;

import com.lemon.bean.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author :chenhualong
 * @Date : created in 15:19 2017/11/13
 * @Description :
 */

@Service
@Slf4j
public class UserDao {



    public String  testUserDao(){

        UserForm  userForm = new UserForm();

        userForm.setUserName("hello world");

        System.out.println("demo-dao- test function");

        log.info("demo-dao- test function-log");

        return  userForm.getUserName();
    }

}
