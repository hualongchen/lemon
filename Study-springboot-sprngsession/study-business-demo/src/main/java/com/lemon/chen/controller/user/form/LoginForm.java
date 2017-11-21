package com.lemon.chen.controller.user.form;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 登陆类
 */

@Data
public class LoginForm implements Serializable{


    @Getter@Setter private String nickName ;


    @Getter@Setter private String passWord ;




}
