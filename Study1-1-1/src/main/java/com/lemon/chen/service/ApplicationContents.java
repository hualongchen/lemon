package com.lemon.chen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by chenhualong on 2017/7/6.
 */
@Component
public class ApplicationContents {



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

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getLemonName() {
        return lemonName;
    }

    public void setLemonName(String lemonName) {
        this.lemonName = lemonName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBlogValue() {
        return blogValue;
    }

    public void setBlogValue(String blogValue) {
        this.blogValue = blogValue;
    }

    public Integer getBlogNumber() {
        return blogNumber;
    }

    public void setBlogNumber(Integer blogNumber) {
        this.blogNumber = blogNumber;
    }

    public Long getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(Long bigNumber) {
        this.bigNumber = bigNumber;
    }

    public Integer getTest1() {
        return test1;
    }

    public void setTest1(Integer test1) {
        this.test1 = test1;
    }

    public Integer getTest2() {
        return test2;
    }

    public void setTest2(Integer test2) {
        this.test2 = test2;
    }
}
