package com.lemon.chen.domain;

import java.io.Serializable;

/**
 * Created by chenhualong on 2017/7/10.
 */
public class User implements Serializable {

    private double userId ;


    private String userName ;


    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
