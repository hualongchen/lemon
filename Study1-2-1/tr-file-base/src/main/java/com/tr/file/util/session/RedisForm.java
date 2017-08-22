package com.tr.file.util.session;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Data;


/**
 * 使用 lombok框架，可以省去写 set  get 方法
 */
@Configuration
@Data
public class RedisForm {

    @Value("${spring.redis.database}")
    private String database;
    @Value("${spring.redis.host}")
    private String host;
    //@Value("${spring.redis.}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-idle}")
    private String maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private String minIdle;
    @Value("${spring.redis.pool.max-active}")
    private String maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private String maxWait;

}
