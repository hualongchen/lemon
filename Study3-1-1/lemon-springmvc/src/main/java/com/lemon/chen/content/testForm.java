package com.lemon.chen.content;

import java.io.Serializable;

/**
 * Created by chenhualong on 16/6/21.
 */
public class testForm implements Serializable {

    private Integer userid;

    private String username;

    private Integer age;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
