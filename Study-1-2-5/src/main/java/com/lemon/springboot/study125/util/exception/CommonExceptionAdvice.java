package com.lemon.springboot.study125.util.exception;



import com.lemon.springboot.study125.util.ResultVO;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author chenhualong
 * 全局异常处理中心，所以异常均放在此处进行整理发送
 */

@ControllerAdvice
public class CommonExceptionAdvice {


    private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);

        return new ResultVO(ErrorContent.MISS_FILED_CODE, ErrorContent.MISS_FILED_MESSAGE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultVO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return new ResultVO(ErrorContent.PARAMETER_RESOLUTION_FAILURE_CDOE,ErrorContent.PARAMETER_RESOLUTION_FAILURE_MESSAGE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
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
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultVO handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String code = error.getDefaultMessage();
        return new ResultVO(ErrorContent.PARAMETER_BINDING_FAILURE_CDOE,code);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
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
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResultVO handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return new ResultVO(ErrorContent.PARAMETER_VALIDATION_FAILURE_CDOE,ErrorContent.PARAMETER_VALIDATION_FAILURE_MESSAGE);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return new ResultVO(ErrorContent.METHOD_NOT_SUPPORTED_CDOE,ErrorContent.METHOD_NOT_SUPPORTED_MESSAGE);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResultVO handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return new ResultVO(ErrorContent.MEDIA_NOT_SUPPORTED_CDOE,ErrorContent.MEDIA_NOT_SUPPORTED_MESSAGE);
    }


    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO handleException(Exception e) {
        logger.error("通用异常", e);
        return new ResultVO(ErrorContent.GENEREL_ERROR_CDOE,e.getMessage());
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResultVO handleException(DataIntegrityViolationException e) {
        logger.error("操作数据库出现异常:", e);
        return new ResultVO(ErrorContent.DATABASE_ERROR_CDOE,e.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultVO handleServiceException(BusinessException e) {
        logger.error("业务逻辑异常", e);
        return new ResultVO(e.getCode(), e.getMessage());
    }


    /**
     * 跳转错误页面
     */
    @ExceptionHandler(value = PageException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");

        return mav;
    }


}
