package com.lemon.chen.web;

import com.lemon.chen.lemon.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenhualong on 16/6/27.
 */

//@Controller
public class RedisController {




    @Autowired
    private RedisUtil  redisUtil ;


    /**
     * 测试redis的增删改查
     * @param request
     */
    @RequestMapping(value = "redis",method = RequestMethod.POST,produces = { "text/html;charset=UTF-8" })
    public void  testRedis(HttpServletRequest request){


         //塞进去
        redisUtil.set("hello", "world");

        //取出来
        System.out.println(redisUtil.get("hello"));


        //删除
        redisUtil.remove("hello");

        //判断是否有

        System.out.println(redisUtil.exists("hello"));

    }


}
