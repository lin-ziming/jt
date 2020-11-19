package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JedisCluster jedisCluster;
    /**
     * 业务需求: 查询所有用户信息
     * url:  sso.jt.com   localhost:8093   /findAll
     * 返回值: List<User>
     */
    @GetMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }
    /**
     * 需求:实现用户信息校验
     * 校验步骤:  需要接收用户的请求,之后利用RestFul获取数据,
     *            实现数据库校验,按照JSONP的方式返回数据.
     * url地址:   http://sso.jt.com/user/check/admin123/1?r=0.8&callback=jsonp16
     * 参数:      restFul方式获取
     * 返回值:    JSONPObject
     */
    @GetMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param,
                                 @PathVariable Integer type,
                                 String callback){
        //只需要校验数据库中是否有结果
        boolean flag = userService.checkUser(param,type);
        SysResult sysResult = SysResult.success(flag);
//        int a = 1/0;
        return new JSONPObject(callback, sysResult);
    }

    /**
     * http://sso.jt.com/user/testHttpClient
     * 返回List<User>
     */
    @RequestMapping("/testHttpClient")
    public List<User> testHttpClient(){
        return userService.findAll();
    }

    /**
     * 业务说明:
     *   通过跨域请求方式,获取用户的JSON数据.
     *   1.url地址:  http://sso.jt.com/user/query/efd321aec0ca4cd6a319b49bd0bed2db?callback=jsonp1605775149414&_=1605775149460
     *   2.请求参数:  ticket信息
     *   3.返回值:   SysResult对象 (userJSON)
     *   需求: 通过ticket信息获取user JSON串
     */
    @GetMapping("/query/{ticket}")
    public JSONPObject findUserByTicket(@PathVariable String ticket,String callback){
        String userJSON = jedisCluster.get(ticket);
        if(StringUtils.isEmpty(userJSON)){
            return new JSONPObject(callback,SysResult.fail());
        }else {
            return new JSONPObject(callback,SysResult.success(userJSON));
        }

    }

}
