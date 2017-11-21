package com.lemon.maven.module;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :chenhualong
 * @Date : created in 14:00 2017/11/10
 * @Description :
 */

@Data
public class UserForm implements Serializable {


    private String userName ;

    private String userAge ;

    private String passWord ;


}
