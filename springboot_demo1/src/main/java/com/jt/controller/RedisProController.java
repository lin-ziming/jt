package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//需要通过spring容器加载配置文件
@PropertySource(value = "classpath:/properties/redis.properties",encoding = "UTF-8")
public class RedisProController {
    @Value("${redis.pro.host}")
    private String proHost;
    @Value("${redis.pro.port}")
    private Integer proPort;

    @RequestMapping("getMsgPro")
    public String getMsg2(){
        return proHost + ":" + proPort;
    }
}
