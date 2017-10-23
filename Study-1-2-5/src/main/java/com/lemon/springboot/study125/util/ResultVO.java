package com.lemon.springboot.study125.util;

import lombok.Data;

import java.io.Serializable;


/**
 * @author chenhualong
 * @param <T>
 */

@Data
public class ResultVO<T> implements Serializable {


    /**
     * 业务对错编码
     */
    private String code;

    /**
     * 业务描述
     */
    private String message;


    /**
     * 接口交互数据，请求错误情况下， 数据为空
     */
    private T data;


    public ResultVO() {

        this.message = "请求成功";
        this.code = "200";
    }

    public ResultVO(String code, String message) {

        this.message = message;
        this.code = code;
    }


    @Override
    public String toString() {
        return "ResultVO{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
