package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    private String host = "127.0.0.1";
    private Integer port = 6379;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/getMsg")
    public String getMsg(){
        return host + ":" + port;
    }

}
