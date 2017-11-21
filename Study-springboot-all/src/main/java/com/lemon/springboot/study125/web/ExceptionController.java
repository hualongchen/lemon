package com.lemon.springboot.study125.web;


import com.lemon.springboot.study125.util.exception.BusinessException;
import com.lemon.springboot.study125.util.exception.PageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenhualong
 * 异常阐释
 */

@Controller
public class ExceptionController {


    /**
     * 模拟页面跳转错误
     */
    @RequestMapping("/exception1")
    public String hello() throws PageException{

        throw new PageException("500","我的程序跑到国外去了");
    }

    /**
     * 模拟数据传输错误
     * @return
     * @throws BusinessException
     */
    @RequestMapping("/exception2")
    public Object hello2() throws BusinessException {

        throw new BusinessException("500","我的程序拉肚子了！");
    }



}
