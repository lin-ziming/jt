package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {

    @Value("${server.port}")
    private String port;
    /**
     * 获取端口号信息
     */
    @RequestMapping("/getPort")
    public String getPort(){
        return "当前访问的服务器的端口号为："+port;
    }

}
