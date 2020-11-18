package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice  //定义全局异常处理
public class SystemException {

    //JSONP报错 返回值 callback(JSON) 如果请求参数中包含callback参数,则标识为跨域请求
    @ExceptionHandler(RuntimeException.class)
    public Object fail(Exception e, HttpServletRequest request){
        e.printStackTrace();
        String callback = request.getParameter("callback");
        if(StringUtils.isEmpty(callback)){
            //如果参数为空表示 不是跨域请求.
            return SysResult.fail();
        }else{
            //有callback参数,表示是跨域请求.
            SysResult sysResult = SysResult.fail();
            return new JSONPObject(callback, sysResult);
        }

    }
}
