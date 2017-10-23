package com.lemon.springboot.study125.util.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenhualong
 */
@Data
@Component
public class ApplicationConfig {

    @Value("${com.lemon.blog.value}")
    private String blogValue ;

    @Value("${com.lemon.blog.number}")
    private Integer blogNumber;

    @Value("${com.lemon.blog.bignumber}")
    private Long bigNumber ;

    @Value("${com.lemon.blog.test1}")
    private Integer test1;

    @Value("${com.lemon.blog.test2}")
    private Integer test2;

    @Value("${com.lemon.name}")
    private String  lemonName ;

    @Value("${com.lemon.title}")
    private String  title ;

    @Value("${com.lemon.company.desc}")
    private String desc ;

    @Value("${com.lemon.company.desc2}")
    private String desc2 ;
}
