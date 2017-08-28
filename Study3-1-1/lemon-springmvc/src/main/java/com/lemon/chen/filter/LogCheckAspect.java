package com.lemon.chen.filter;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;

/**
 * Created by chenhualong on 16/6/22.
 */

@Controller
@Aspect
public class LogCheckAspect {




    @Pointcut("execution(* com.lemon.chen.web.FirstWebController.*(..))")
    public void checkLogin(){

        System.out.println("----------->this is checkLogin pointcut");
    }



    @Before("checkLogin()")
    public void beforecheck(){

        System.out.println("------->开始前切割------");
    }


    @After("checkLogin()")
    public void afterCheck(){

        System.out.println("--------->切割完毕");
    }


}
