package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 实现用户的登录、注册页面的跳转
     */
    @GetMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName){

        return moduleName;
    }

    @RequestMapping("testHttpClient")
    @ResponseBody
    public List<User> testHttpClient(){
        return userService.testHttpClient();
    }

}
