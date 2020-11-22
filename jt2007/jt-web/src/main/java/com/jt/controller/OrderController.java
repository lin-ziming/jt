package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Reference
    private DubboCartService cartService;
    @Reference
    private DubboOrderService orderService;

    /**
     * 跳转订单确认页面
     *  url:http://www.jt.com/order/create.html
     *  参数: 暂时没有
     *  返回值:  order-cart.jsp
     *  页面取值:  ${carts}  获取购物车对象
     */
    @RequestMapping("/create")
    public String create(Model model){
        Long userId = UserThreadLocal.get().getId();
        List<Cart> carts = cartService.findCartListByUserId(userId);
        model.addAttribute("carts",carts);
        return "order-cart";
    }

    /**
     * 完成订单入库操作
     * url地址:   http://www.jt.com/order/submit
     * 参数:      整个表单对象  用order对象接收
     * 返回值:    SysResult对象(orderId)
     */
    @PostMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order){
        Long userId = UserThreadLocal.get().getId();
        order.setUserId(userId);
        String orderId = orderService.saveOrder(order);
        if(StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }else {
            return SysResult.success(orderId);
        }
    }

    /**
     * 完成订单查询
     * url地址: http://www.jt.com/order/success.html?id=71605862296712
     * 参数:    orderId
     * 返回值:  订单成功页面 success.jsp
     * 页面取值: ${order.orderId}
     */
    @RequestMapping("/success")
    public String success(String id, Model model){
        Order order = orderService.findOrderId(id);
        model.addAttribute("order",order);
        return "success";
    }

}
