package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转页面: userList.jsp
     * url:localhost:8090/findAll
     * 步骤:
     *      1.pojo  2.mapper 3.service  4.com.jt.controller
     *      5.YML配置前后缀
     *      6.jar包问题  3个包
     *      7.导入页面 userList.jsp
     *      */
    @RequestMapping("/findAll")
    public String findAll(Model model){
        //1.查询用户列表信息
        List<User> userList = userService.findAll();
        //2.将数据保存到request域中 之后返回给用户 视图渲染过程
        model.addAttribute("userList",userList);
        return "userList";
    }
    /**
     * 实现页面的跳转
     * url地址:  localhost:8090/ajax
     * 跳转的页面:  ajax.jsp
     */
    @RequestMapping("/ajax")
    public String ajax(){
        return "ajax";
    }
    /**
     * 实现ajax业务调用
     * url: http://localhost:8090/findAjax?id=40
     * 参数: id
     * 返回值:List<User>
     */
    @RequestMapping("/findAjax")
    @ResponseBody    //ajax结束标识
    public List<User> findAjax(){
        return userService.findAll();
    }

}
