package com.tr.file.util.Lombok;


import lombok.ToString;

@ToString(exclude = "id", callSuper = true, includeFieldNames = true)
public class LombokToStringTest {

    private String id ;

    private int age ;

    private String username ;


    public static void main(String[] args) {

        /**
         * 输入结果为：LombokToStringTest(super=com.lemon.chen.util.Lombok.LombokToStringTest@5f184fc6, age=0, username=null)
         *
         * 没有去除id情况如下：LombokToStringTest(super=com.lemon.chen.util.Lombok.LombokToStringTest@5f184fc6, id=null, age=0, username=null)
         */
        System.out.println(new LombokToStringTest());
    }

}
