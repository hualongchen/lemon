package com.lemon.bean;

import java.io.Serializable;

/**
 * @author :chenhualong
 * @Date : created in 15:16 2017/11/13
 * @Description :
 */
public class UserForm implements Serializable {

    private String userName ;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
