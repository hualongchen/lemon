package com.lemon.springboot.study125.web;


import com.lemon.springboot.study125.util.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author chenhualong
 */
@RestController
public class HelloController {


    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 第一个接口
     *
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Object helloWorld() {

        return "hello world";
    }


    /**
     * 测试读取配置文件信息
     *
     * @return
     */
    @RequestMapping(value = "/app", produces = {"application/json;charset=UTF-8"})
    public Object getApplicatoinContents() {
        System.out.println(applicationConfig.getBigNumber());
        System.out.println(applicationConfig.getBlogNumber());
        System.out.println(applicationConfig.getBlogValue());
        System.out.println(applicationConfig.getTest1());
        System.out.println(applicationConfig.getTest2());
        System.out.println(applicationConfig.getDesc());
        System.out.println(applicationConfig.getLemonName());
        System.out.println(applicationConfig.getTitle());
        System.out.println(applicationConfig.getDesc2());

        return applicationConfig.getTitle();

    }



}
