package com.lemon.chen.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

/**
 * Created by chenhualong on 2017/7/11.
 */

@Configuration
public class RabbitConfig {


    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }


}
