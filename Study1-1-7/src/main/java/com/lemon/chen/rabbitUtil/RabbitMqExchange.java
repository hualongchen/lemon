package com.lemon.chen.rabbitUtil;

/**
 * Created by chenhualong on 2017/8/7.
 */
public enum RabbitMqExchange {

    /**
     * 目前HEADERS已经淘汰，基本不适用哈
     */
        DEFAULT, DIRECT, TOPIC, HEADERS, FANOUT
}
