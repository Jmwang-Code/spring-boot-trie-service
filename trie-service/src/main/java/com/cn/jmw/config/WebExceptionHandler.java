package com.cn.jmw.config;

import com.cn.jmw.dto.ResponseData;
import com.cn.jmw.exceptions.TrieException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @Date 2023/4/20 14:31
 * @Author jmw
 * @Description 全局异常处理
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 全局异常处理方法
     * @param e 异常对象
     * @return 响应数据
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseData<Object> exceptionHandler(Exception e) {
        Object data = null;
        String msg = null;
        msg = e.getMessage();
        if (msg == null) {
            Throwable cause = e.getCause();
            if (cause != null) {
                msg = cause.getMessage();
            }
        }
        log.error(msg, e);
        ResponseData.ResponseDataBuilder<Object> builder = ResponseData.builder();
        return builder.success(false)
                .message(msg)
                .data(data)
                .build();
    }

    /**
     * 自定义异常处理方法
     * @param e 自定义异常对象
     * @return 响应数据
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TrieException.class)
    public ResponseData<Object> myExceptionHandler(TrieException e) {
        Object data = null;
        String msg = e.getMessage();
        log.error(msg, e);
        ResponseData.ResponseDataBuilder<Object> builder = ResponseData.builder();
        return builder.success(false)
                .message(msg)
                .data(data)
                .build();
    }

}
