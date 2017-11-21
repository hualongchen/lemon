package com.tr.file.modul.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
public class LoginForm implements Serializable {


    @NotEmpty
    private  String userName ;


    @NotEmpty
    private String password ;


}
