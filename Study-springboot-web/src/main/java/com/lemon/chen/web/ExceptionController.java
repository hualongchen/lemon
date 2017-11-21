package com.lemon.chen.web;

import com.lemon.chen.util.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenhualong on 2017/7/6.
 */

@Controller
public class ExceptionController {


    @RequestMapping("/exception1")
    public String hello() throws Exception{

        throw new Exception("好像出错了呀");
    }


    @RequestMapping("/exception2")
    public Object hello2() throws BusinessException {

        throw new BusinessException("我就任性的抛一个");
    }
}
