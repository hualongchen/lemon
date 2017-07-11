package com.lemon.chen.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by chenhualong on 2017/7/11.
 *
 * 消费指定队列的信息
 */

@Component
@RabbitListener(queues = "hello")
public class RabbitConsumer {


    @RabbitHandler
    public void process(String hello) {

        System.out.println("RabbitConsumer accept the msg  is : " + hello);
    }
}
