package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 业务说明：查询所有的user信息
     * url路径：http://localhost:8090/findAll
     * 参数：  暂时没有
     * 返回值： userJSON信息
     */
    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }
}
