package com.uqierp.util;

import com.uqierp.result.Result;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        System.out.println("=====================全局异常信息捕获=======================");
	    System.out.println("出现异常："+ExceptionUtils.getStackTrace(ex));
	    return Result.error("请求失败");
    }
}
