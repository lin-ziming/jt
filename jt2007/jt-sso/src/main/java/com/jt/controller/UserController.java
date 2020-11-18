package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 业务需求: 查询所有用户信息
     * url:  sso.jt.com   localhost:8093   /findAll
     * 返回值: List<User>
     */
    @GetMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }
    /**
     * 需求:实现用户信息校验
     * 校验步骤:  需要接收用户的请求,之后利用RestFul获取数据,
     *            实现数据库校验,按照JSONP的方式返回数据.
     * url地址:   http://sso.jt.com/user/check/admin123/1?r=0.8&callback=jsonp16
     * 参数:      restFul方式获取
     * 返回值:    JSONPObject
     */
    @GetMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param,
                                 @PathVariable Integer type,
                                 String callback){
        //只需要校验数据库中是否有结果
        boolean flag = userService.checkUser(param,type);
        SysResult sysResult = SysResult.success(flag);
//        int a = 1/0;
        return new JSONPObject(callback, sysResult);
    }

    /**
     * http://sso.jt.com/user/testHttpClient
     * 返回List<User>
     */
    @RequestMapping("/testHttpClient")
    public List<User> testHttpClient(){
        return userService.findAll();
    }

}
