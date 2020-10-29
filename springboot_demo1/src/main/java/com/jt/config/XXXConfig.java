package com.jt.config;

import com.jt.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * //标识我是一个配置类
 */
@Configuration
public class XXXConfig {

    /**
     * 编辑某些需要实例化的内容，交给spring容器管理  一般与@Bean联用
     */
    @Bean
    public TestService testService(){
        return new TestService();
    }

}
