# 3-2-2 Spring boot 全局异常处理

### 1.0 定义最后的输出结果项

```
package com.lemon.chen.util;

import java.io.Serializable;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

```


### 2.0  定义错误的枚举项

```
package com.lemon.chen.util;

public class ErrorContent {

    public static String MISS_FILED_CODE="1001";

    public static String MISS_FILED_MESSAGE="缺少请求参数";

    public static String PARAMETER_RESOLUTION_FAILURE_CDOE="1002";

    public static String PARAMETER_RESOLUTION_FAILURE_MESSAGE="参数解析失败";

    public static String PARAMETER_VALIDATION_FAILURE_CDOE="1003";

    public static String PARAMETER_VALIDATION_FAILURE_MESSAGE="参数验证失败";

    public static String PARAMETER_BINDING_FAILURE_CDOE="1004";

    public static String METHOD_NOT_SUPPORTED_CDOE="1005";

    public static String METHOD_NOT_SUPPORTED_MESSAGE="不支持当前方法";

    public static String MEDIA_NOT_SUPPORTED_CDOE="1006";

    public static String MEDIA_NOT_SUPPORTED_MESSAGE="不支持当前媒体类型";

    public static String GENEREL_ERROR_CDOE="1007";

    public static String GENEREL_ERROR_MESSAGE="系统错误";

    public static String DATABASE_ERROR_CDOE="1008";

    public static String DATABASE_ERROR_MESSAGE="数据库外键重复";

}

```


### 3.0 定义错误的拦截器

```
package com.lemon.chen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 全局异常处理中心
 */

@ControllerAdvice
@ResponseBody
public class CommonExceptionAdvice {


    private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);

        return new ResultVO(ErrorContent.MISS_FILED_CODE, ErrorContent.MISS_FILED_MESSAGE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return new ResultVO(ErrorContent.PARAMETER_RESOLUTION_FAILURE_CDOE,ErrorContent.PARAMETER_RESOLUTION_FAILURE_MESSAGE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new ResultVO(ErrorContent.PARAMETER_VALIDATION_FAILURE_CDOE,message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultVO handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new ResultVO(ErrorContent.PARAMETER_BINDING_FAILURE_CDOE,message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVO handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new ResultVO(ErrorContent.PARAMETER_VALIDATION_FAILURE_CDOE,message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResultVO handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return new ResultVO(ErrorContent.PARAMETER_VALIDATION_FAILURE_CDOE,ErrorContent.PARAMETER_VALIDATION_FAILURE_MESSAGE);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return new ResultVO(ErrorContent.METHOD_NOT_SUPPORTED_CDOE,ErrorContent.METHOD_NOT_SUPPORTED_MESSAGE);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultVO handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return new ResultVO(ErrorContent.MEDIA_NOT_SUPPORTED_CDOE,ErrorContent.MEDIA_NOT_SUPPORTED_MESSAGE);
    }


    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        logger.error("通用异常", e);
        return new ResultVO(ErrorContent.GENEREL_ERROR_CDOE,e.getMessage());
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultVO handleException(DataIntegrityViolationException e) {
        logger.error("操作数据库出现异常:", e);
        return new ResultVO(ErrorContent.DATABASE_ERROR_CDOE,e.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public ResultVO handleServiceException(BusinessException e) {
        logger.error("业务逻辑异常", e);
        return new ResultVO(e.getCode(), e.getMessage());
    }


}


```


### 4.0 自定义异常项

```
package com.lemon.chen.util;

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


```

### 5.0 进行测试

```
package com.lemon.chen.controller.user;


import com.lemon.chen.controller.user.form.LoginForm;
import com.lemon.chen.util.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {




    @GetMapping("test")
    public  Object  login(LoginForm form){

        throw new BusinessException("1001","test");
    }


}


```