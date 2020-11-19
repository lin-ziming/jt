package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer{

	/**
	 * 开启匹配后缀型配置
	 * 说明: 由于京东商城的商品展现时通过
	 * url:https://item.jd.com/10021377498920.html,京东的访问是根据.html进行拦截,
	 * 之后通过restFul结构动态获取商品的ID号,之后查询数据库进行的回显.
	 * 所以需要对后缀进行拦截.有了如下的配置.
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}

}
