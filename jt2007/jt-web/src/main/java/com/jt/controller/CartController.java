package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    /**
     *  业务描述: 展现购物车列表页面,同时查询购物车数据
     *  url: http://www.jt.com/cart/show.html
     *  参数: userId=7L
     *  返回值:  页面逻辑名称  cart.jsp
     *  页面取值:  ${cartList}
     */
    @RequestMapping("/show")
    public String show(){
        Long userId = 7L;
        return "cart";
    }

}
