package com.lemon.chen.util.Lombok;


import com.lemon.chen.controller.user.form.LoginForm;
import lombok.Data;

/**
 * Data 类注解，相当于同时应用了
 * @Getter、
 * @Setter、
 * @ToString、
 * @EqualsAndHashCode、
 * @RequiredArgsConstructor。
 * 如果已经定义了一个构造方法，就不会再自动生成构造方法了
 *
 *
 * 平时直接使用这个注解就万事大吉了
 */
@Data
public class LombokDataTest {

    private LoginForm loginForm;

    private String username;

    private int age =3;


    public static void main(String[] args) {

        System.out.println(new LombokDataTest());
    }
}
