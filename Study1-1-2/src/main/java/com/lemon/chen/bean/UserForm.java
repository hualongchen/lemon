package com.lemon.chen.bean;

import java.io.Serializable;

/**
 * Created by chenhualong on 2017/7/6.
 */
public class UserForm  implements Serializable{

    private String userName ;

    private Integer userAge ;

    private Integer userId ;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }


    @Override
    public String toString() {
        return "UserForm{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userId=" + userId +
                '}';
    }
}
