package com.lemon.chen.content;

import java.io.Serializable;

/**
 * Created by chenhualong on 16/6/21.
 */
public class ResultVO<T> implements Serializable {


    private  T   data ;

    private String  errorMsg ;

    private  int  errorCode ;

    public ResultVO( String errorMsg, int errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }


    public ResultVO() {
        this.errorMsg = "成功";
        this.errorCode = 200;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
