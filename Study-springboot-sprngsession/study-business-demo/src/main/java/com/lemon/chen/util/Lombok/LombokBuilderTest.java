package com.lemon.chen.util.Lombok;


import com.lemon.chen.controller.user.form.LoginForm;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Singular;

import java.util.Set;

/**
 * @Builder：用在类、构造器、方法上，为你提供复杂的builder APIs，
 * 让你可以像如下方式一样调用Person.builder().name("Adam Savage").city("San Francisco").job("Mythbusters").job("Unchained Reaction").build();
还可以使用@Builder.Default,自动填充自带数据

@Singular
这个注解把每个不同类型的成员变量都设置成默认值
 */

@Builder
@Data
public class LombokBuilderTest {

    private LoginForm loginForm;

    @Builder.Default
    private String username="testooooo";


    @Builder.Default
    private int age =3;

    @Singular
    private Set<String> occupations;


    public static void main(String[] args) {

        LombokBuilderTest test=   LombokBuilderTest.builder().occupation("test2").age(3).build();

        System.out.println(test);
    }

}
