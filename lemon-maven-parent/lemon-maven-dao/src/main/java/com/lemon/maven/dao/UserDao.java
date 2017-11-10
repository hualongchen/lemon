package com.lemon.maven.dao;

import com.lemon.maven.module.UserForm;
import org.springframework.stereotype.Service;

/**
 * @author :chenhualong
 * @Date : created in 11:58 2017/11/10
 * @Description :
 */

@Service
public class UserDao {


    public String   testUserDao(){


        UserForm  userForm = new UserForm();

        userForm.setPassWord("test");

        System.out.println("this is userDao," +userForm.toString() );

        return  userForm.getPassWord();
    }
}
