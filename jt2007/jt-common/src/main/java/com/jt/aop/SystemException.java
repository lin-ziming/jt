package com.jt.aop;

import com.jt.vo.SysResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //定义全局异常处理
public class SystemException {

    @ExceptionHandler(RuntimeException.class)
    public Object fail(Exception e){
        e.printStackTrace();
        return SysResult.fail();
    }
}
