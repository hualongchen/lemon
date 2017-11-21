package com.lemon.chen.bean;

import java.io.Serializable;

/**
 * Created by chenhualong on 2017/7/10.
 */
public class User implements Serializable {


    private Long userId ;


    private String userName ;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
