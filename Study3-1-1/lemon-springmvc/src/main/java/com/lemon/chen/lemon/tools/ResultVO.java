package com.lemon.chen.lemon.tools;

import java.io.Serializable;

/**
 * Created by chenhualong on 16/7/28.
 */
public class ResultVO<T> implements Serializable {



    private  T  data ;


    private boolean  isSuccess ;


    private String  errorCode ;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
