package com.lemon.chen.web;

import com.lemon.chen.service.ApplicationContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by chenhualong on 2017/7/5.
 */

@RestController
public class HelloController {


    @Autowired
    private ApplicationContents applicationContents;


    @RequestMapping(value = "/hello", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object helloWorld() {

        return "hello world";
    }


    /**
     * 测试读取配置文件信息
     *
     * @return
     */
    @RequestMapping(value = "/app",produces = { "application/json;charset=UTF-8" })
    public Object getApplicatoinContents() {

        System.out.println(applicationContents.getBigNumber());
        System.out.println(applicationContents.getBlogNumber());
        System.out.println(applicationContents.getBlogValue());
        System.out.println(applicationContents.getTest1());
        System.out.println(applicationContents.getTest2());
        System.out.println(applicationContents.getDesc());
        System.out.println(applicationContents.getLemonName());
        System.out.println(applicationContents.getTitle());
        System.out.println(applicationContents.getDesc2());

        return applicationContents.getTitle();

    }

    @GetMapping("/test")
    public void test(){

        System.out.println("1234789");
    }

}
