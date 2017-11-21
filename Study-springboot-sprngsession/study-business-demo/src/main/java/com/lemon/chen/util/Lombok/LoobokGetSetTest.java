package com.lemon.chen.util.Lombok;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 一个是定义访问级别， 一个是定义生成哪些方法
 */
public class LoobokGetSetTest {


    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PROTECTED)
    private String id ;

    @Setter
    @Getter
    private int age ;


}
