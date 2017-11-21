package com.lemon.chen.util.Lombok;

import lombok.Synchronized;

/**
 * 使用@Synchronized的例子
 *
 * 用在方法上，将方法声明为同步的，并自动加锁，而锁对象是一个私有的属性$lock或$LOCK，
 * 而java中的synchronized关键字锁对象是this，
 * 锁在this或者自己的类对象上存在副作用，就是你不能阻止非受控代码去锁this或者类对象，
 * 这可能会导致竞争条件或者其它线程错误
 */
public class LombokSynchronizedTest {


    @Synchronized
    public static void hello() {
        System.out.println("world");
    }


    /**
     * 没有使用注解的情况如下：
     *
     */

    private static final Object $LOCK = new Object[0];
    public static void hello2() {
        synchronized ($LOCK) {
            System.out.println("world");
        }
    }
}
