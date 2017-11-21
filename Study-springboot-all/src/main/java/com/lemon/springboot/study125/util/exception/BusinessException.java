package com.lemon.springboot.study125.util.exception;

import lombok.Data;

/**
 * @author chenhualong
 * 业务的异常处理,用户处理数据错误
 */
@Data
public class BusinessException extends RuntimeException {


    private static final long serialVersionUID = 5646283057216412238L;

    private String code;


    public BusinessException(String code, String message) {

        super(message);
        this.code = code;

    }
}


