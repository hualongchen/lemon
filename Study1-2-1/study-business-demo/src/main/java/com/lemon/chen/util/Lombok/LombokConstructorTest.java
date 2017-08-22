package com.lemon.chen.util.Lombok;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @NoArgsConstructor 类注解，自动生成一个无参构造函数。
 * @AllArgsConstructor 类注解，生成一个初始化所有字段的构造函数。
 * @RequiredArgsConstructor 类注解，为final字段和标记了@NotNull的字段生成构造函数。
 */
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
public class LombokConstructorTest {


    @NonNull
    private int id;
    @NonNull
    private String shap;

    private int age;


    public static void main(String[] args) {
        new LombokConstructorTest(1, "circle");
        //使用静态工厂方法
        LombokConstructorTest.of(2, "circle");
        //无参构造
        new LombokConstructorTest();
        //包含所有参数
        new LombokConstructorTest(1, "circle", 2);
    }
}
