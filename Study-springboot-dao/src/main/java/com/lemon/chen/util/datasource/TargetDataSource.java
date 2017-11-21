package com.lemon.chen.util.datasource;

import java.lang.annotation.*;

/**
 * Created by chenhualong on 2016/11/24.
 * 在方法是使用，标示哪个是数据源，当然也可以在类上面使用
 */
@Target({ElementType.METHOD,ElementType.TYPE})//接口，类，美剧，方法注解
@Retention(RetentionPolicy.RUNTIME) //注解会在class字节码文件中存在，运行时可通过反射获取。
@Documented  //包含在java doc中
public @interface TargetDataSource {

    String name();
}
