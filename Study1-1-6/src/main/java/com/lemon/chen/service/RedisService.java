package com.lemon.chen.service;

import com.lemon.chen.redis.RedisForm;
import com.lemon.chen.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenhualong on 2017/7/10.
 */

@Service
public class RedisService {


    @Autowired
    private RedisUtil redisUtil;


    /**
     * 存入redis
     */
    public void addUser() {

        RedisForm form = new RedisForm();

        form.setKey("lemon");
        form.setValue("chenhualong");
        redisUtil.setValue(form);
    }


    /**
     * 从redis读取
     * @return
     */
    public RedisForm  getUserByName(){

        RedisForm form = new RedisForm();

        form.setKey("lemon");

        return redisUtil.getValue(form);
    }


}
