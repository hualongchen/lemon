package com.lemon.chen.controller.user.form;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 注册类
 */
public class RegistForm implements Serializable{


    /**
     * 用户Id
     */
    private String userid;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空哟")
    @Length(min = 4,max = 15,message = "用户昵称为4到15位哟")
    private String nickname;

    /**
     * 用户姓名
     */
    @NotBlank(message = "用户姓名不能为空哟")
    @Length(min = 4,max = 20,message = "用户姓名为4到15位哟")
    private String username;

    /**
     * 用户手机号
     */
    @NotBlank(message = "用户手机不能为空哟")
    @Length(min = 11,max = 11,message = "手机号为11位号码哟")
    private String mobilephone;

    /**
     * 用户的加密后的密码
     */
    @NotBlank(message = "用户注册密码不能为空哟")
    @Length(min = 6,max = 20,message = "密码为6--20位哟")
    private String password;

    /**
     * 用户手机唯一标示
     */
    private String imel;



    @Override
    public String toString() {
        return "RegistForm{" +
                "userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", mobilephone='" + mobilephone + '\'' +
                ", password='" + password + '\'' +
                ", imel='" + imel + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImel() {
        return imel;
    }

    public void setImel(String imel) {
        this.imel = imel;
    }


}
