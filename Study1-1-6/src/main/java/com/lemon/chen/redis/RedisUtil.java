package com.lemon.chen.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by chenhualong on 2016/12/3.
 */

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate  redisTemplate ;


    /**
     * 塞值
     * @param form
     */
    public void setValue(RedisForm  form){

        redisTemplate.opsForValue().set(form.getKey().toString(),form.getValue().toString());
    }


    /**
     * 获取对应的缓存值
     * @param form
     * @return
     */
    public  RedisForm  getValue(RedisForm form){

        RedisForm  returnForm  = new RedisForm();

        returnForm.setKey(form.getKey());
        returnForm.setValue(redisTemplate.opsForValue().get(form.getKey().toString()));

        return  returnForm ;
    }
}
