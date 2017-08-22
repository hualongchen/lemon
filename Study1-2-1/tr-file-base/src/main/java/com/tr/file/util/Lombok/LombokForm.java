package com.tr.file.util.Lombok;


import com.lemon.chen.controller.user.form.LoginForm;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Data
public class LombokForm {

    /**
     * 防止空指针
     */
    @NonNull @Setter @Getter private LoginForm   loginForm ;

    @Setter @Getter private String username ;

    private int age ;

}
