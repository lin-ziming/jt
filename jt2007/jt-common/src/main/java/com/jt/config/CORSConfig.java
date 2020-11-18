package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 完成CORS跨域配置：实现思路是在请求的响应头中添加访问信息
 */
@Configuration
public class CORSConfig implements WebMvcConfigurer{//web项目的全局配置接口

    /**
     * 参数介绍:
     *      1.addMapping()  哪些请求可以进行跨域操作
     *        addMapping("/**")   表示所有访问后端的服务器的请求都允许跨域
     *        addMapping("/addUser/**")  表示部分请求可以跨域
     *        /*       只能拦截一级目录
     *        /**      可以拦截多级目录
     *
     *      2.allowedOrigins("*")  允许哪些网站跨域
     *      3.allowCredentials(true)  请求跨域时是否允许携带Cookie/Session相关
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true);
//                .maxAge(); //默认30分钟 是否允许跨域请求 30分钟之内不会再次验证
//                .allowedMethods(); //允许请求类型
    }
}
