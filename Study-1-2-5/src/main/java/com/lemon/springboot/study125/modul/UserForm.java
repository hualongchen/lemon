package com.lemon.springboot.study125.modul;

import lombok.Data;

import java.io.Serializable;


/**
 * @author chenhualong
 */

@Data
public class UserForm implements Serializable {

    private static final long serialVersionUID = 2693876187145480413L;

    private String userName ;

    private Integer userAge ;

    private Integer userId ;


}
