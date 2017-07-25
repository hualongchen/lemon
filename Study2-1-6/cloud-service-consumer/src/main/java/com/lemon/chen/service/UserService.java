package com.lemon.chen.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by chenhualong on 2017/7/25.
 */

@Service
public class UserService {


    @Autowired
    private RestTemplate  template ;


    /**
     * 业务调用方法
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public String  consumer(){

      return   template.getForObject("http://user-service-provider/hello", String.class);
    }


    /**
     *hystrix服务降级错误，返回back调用方法
     * 降级错误回调方法
     * @return
     */
    public String fallback(){

        return "ERROR";
    }
}
