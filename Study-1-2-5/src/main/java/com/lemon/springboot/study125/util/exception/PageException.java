package com.lemon.springboot.study125.util.exception;


import lombok.Data;

/**
 * @author chenhualong
 * 处理页面逻辑错误异常
 */

@Data
public class PageException extends RuntimeException {


    private static final long serialVersionUID = -6277262771057257568L;

    private String code;


    public PageException(String code, String message) {

        super(message);
        this.code = code;

    }




}
