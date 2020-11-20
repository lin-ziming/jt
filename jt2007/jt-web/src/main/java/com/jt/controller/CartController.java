package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Item;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(check = false)
    private DubboCartService dubboCartService;
    /**
     *  业务描述: 展现购物车列表页面,同时查询购物车数据
     *  url: http://www.jt.com/cart/show.html
     *  参数: userId=7L
     *  返回值:  页面逻辑名称  cart.jsp
     *  页面取值:  ${cartList}
     */
    @RequestMapping("/show")
    public String show(Model model, HttpServletRequest request){
        User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId(); //暂时写死
        List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);
        return "cart";
    }
    /**
     * 业务描述:
     *  完成购物车数量的修改操作
     *  url地址:  http://www.jt.com/cart/update/num/1474392004/4
     *  参数:     restFul风格
     *  返回值:   void
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody //让ajax的程序结束
    public void updateNum(Cart cart){//springmvc 针对restFul提供的功能，名称和属性名一致就行
        Long userId = 7L;
        cart.setUserId(userId);
        dubboCartService.updateCartNum(cart);
    }
    /**
     * 实现购物车删除操作
     * 1.url地址: http://www.jt.com/cart/delete/1474392004.html
     * 2.参数:    1474392004 itemId
     * 3.返回值:  String   重定向到列表页面
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){
        Long userId = 7L;
        cart.setUserId(userId);
        dubboCartService.deleteCart(cart);
        return "redirect:/cart/show.html";
    }
    /**
     * 购物车新增操作
     * url地址:http://www.jt.com/cart/add/1474391990.html
     * url参数: 购物车属性数据
     * 返回值:  重定向到购物车列表页面
     */
    @PostMapping("/add/{itemId}")
    public String addCart(Cart cart){
        Long userId = 7L;
        cart.setUserId(userId);
        dubboCartService.addCart(cart);
        return "redirect:/cart/show.html";
    }



}
