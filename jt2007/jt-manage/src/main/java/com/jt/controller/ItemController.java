package com.jt.controller;

import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	/**
	 * 业务需求：以分页的形式查询商品列表信息
	 * url: http://localhost:8091/item/query?page=1&rows=20
	 * 请求参数:  page=1&rows=20  page当前页数，rows每页展现的行数
	 * 返回值结果: EasyUITable
	 * 方法1：手写sql方式实现分页
	 * 方法2：利用MP方式实现分页
	 */
	@GetMapping("/item/query")
	public EasyUITable findItemByPage(Integer page,Integer rows){
//		return itemService.findItemByPage(page,rows);
		return itemService.findItemByPage2(page,rows);
	}
	
}
