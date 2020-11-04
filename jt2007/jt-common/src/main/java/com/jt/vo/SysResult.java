package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 作用：指定系统返回值vo对象，与前端进行交互
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable {
    private static final long serialVersionUID = -8647903942026804569L;
    private Integer status; //200成功  201失败
    private String msg;     //服务器返回提示信息
    private Object data;    //服务器数据

    /**
     * 1.编辑失败方法
     */
    public static SysResult fail(){
        return new SysResult(201,"服务器调用失败",null);
    }
    /**
     * 2.重载成功的方法
     * 重载时参数不要包含
     */
    public static SysResult success(){
        return new SysResult(200,"服务器执行成功",null);
    }
    public static SysResult success(Object data){
        return new SysResult(200,"服务器执行成功",data);
    }
    public static SysResult success(String msg,Object data){
        return new SysResult(200,msg,data);
    }
}
