package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
//	@RequestMapping("/page/{moduleName}")
//	public String module1(@PathVariable String moduleName) {
//
//		return moduleName;
//	}
	/**
	 * 需求:实现通用页面跳转
	 *  url: /page/item-add    页面:item-add.jsp
	 *  url: /page/item-list   页面:item-list.jsp
	 * 结论: url中的地址就是跳转的页面信息.
	 *
	 * restFul风格实现1:
	 *     作用: 可以动态的接收url中的参数
	 *     语法:
	 *          1.url中的地址如果是参数,则需要使用/分割
	 *          2.controller方法接收参数时,需要使用{}号方式获取
	 *          3.如果需要获取参数信息,则使用特定的注解标识
	 *
	 * restFul风格实现2: 需要指定访问的请求类型,并且根据特定的类型执行业务
	 *      请求类型:
	 *          1.get    执行查询操作
	 *          2.post   执行入库操作
	 *          3.put    执行更新操作
	 *          4.delete 执行删除操作
	 */
//	@RequestMapping(value = "/page/{moduleName}",method = RequestMethod.GET)
	@GetMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}

}
