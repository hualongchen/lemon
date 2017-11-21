package com.tr.file.util.exception;

/**
 * 业务的异常处理
 */
public class BusinessException extends RuntimeException {


    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessException(String code, String message) {


        super(message);
        this.code = code;

    }
}


