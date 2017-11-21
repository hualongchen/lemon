package com.lemon.chen.modul;

import java.io.Serializable;

/**
 * Created by chenhualong on 2017/7/10.
 */
public class User implements Serializable{

    private String userName ;

    private int age ;

    private int userId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", userId=" + userId +
                '}';
    }
}
