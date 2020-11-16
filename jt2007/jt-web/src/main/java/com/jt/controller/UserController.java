package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 实现用户的登录、注册页面的跳转
     */
    @GetMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName){
        return moduleName;
    }
}
