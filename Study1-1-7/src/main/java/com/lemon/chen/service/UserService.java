package com.lemon.chen.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chenhualong on 2017/7/11.
 */

@Component
public class UserService {


    @Autowired
    private AmqpTemplate rabbitTemplate ;


    public void sendMessage(){

        String msg =" 我只是一条过路的信息----lemon";

        rabbitTemplate.convertAndSend("我是队列名",msg);
    }
}
