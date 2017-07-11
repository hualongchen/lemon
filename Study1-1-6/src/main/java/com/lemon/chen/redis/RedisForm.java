package com.lemon.chen.redis;

import java.io.Serializable;

/**
 * Created by chenhualong on 2016/12/3.
 */
public class RedisForm implements Serializable{


    private Object  key ;

    private Object  value ;


    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "RedisForm{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
