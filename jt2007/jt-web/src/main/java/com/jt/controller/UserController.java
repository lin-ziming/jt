package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Reference(check = false)  //启动消费者时，不去校验是否有生产者
    private DubboUserService dubboUserService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 实现用户的登录、注册页面的跳转
     */
    @GetMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName){

        return moduleName;
    }

    @RequestMapping("testHttpClient")
    @ResponseBody
    public List<User> testHttpClient(){
        return userService.testHttpClient();
    }

    /**
     * 完成用户的注册操作
     * url地址: http://www.jt.com/user/doRegister
     *          Request Method: POST
     * 请求参数:
     *          password: admin123
     *          username: admin123123123
     *          phone: 13111112225
     * 返回值类型:
     *          SysResult对象
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user){
        //利用dubbo进行RPC调用
        dubboUserService.saveUser(user);
        return SysResult.success();
    }

    /**
     * 业务:完成用户登录操作
     * url地址: http://www.jt.com/user/doLogin?r=0.35842191622936337
     * 参数:
     *          username: admin123
     *          password: admin123456
     * 返回值:   SysResult对象
     *
     * 业务具体实现:
     *      1.校验用户名和密码是否正确
     *      2.判断返回值结果是否为null 用户名和密码有误 返回201状态码
     *      3.如果返回值结果不为null  uuid保存到cookie中即可.
     *
     *  Cookie知识介绍:
     *      1.cookie.setPath("/")  根目录有效
     *      url1:  www.jt.com/addUser
     *      url2:  www.jt.com/user/addUser
     *
     *      2. cookie.setDomain("域名地址");  cookie在哪个域名中共享
     *      例子1:   cookie.setDomain("www.jt.com");
     *               只有在www.jt.com的域名中有效
     *
     *               cookie.setDomain("jt.com");
     *               只有在jt.com结尾的域名中有效
     *
     */
    @PostMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response){
        String uuid = dubboUserService.doLogin(user);
        if(StringUtils.isEmpty(uuid)){
            return SysResult.fail();
        }
        //将uuid保存到Cookie中
        Cookie cookie = new Cookie("JT_TICKET",uuid);
        cookie.setMaxAge(30*24*60*60); //让cookie 30有效
        cookie.setPath("/"); //cookie在哪个url路径生效，“/"代表根目录
        cookie.setDomain("jt.com");  //设定cookie共享
        response.addCookie(cookie);

        return SysResult.success();
    }

    /**
     * 完成用户退出操作
     * url地址:http://www.jt.com/user/logout.html
     * 参数:   没有参数
     * 返回值:  String 重定向到系统首页
     * 业务:
     *      1.删除redis   K-V   获取ticket信息
     *      2.删除cookie
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //1.获取Cookie中的JT_TICKET中的值
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("JT_TICKET")){
                    String ticket = cookie.getValue();
                    //redis删除ticket信息
                    jedisCluster.del(ticket);
                    //
                    cookie.setMaxAge(0); //0表示立即删除
                    //规则：cookie如果需要操作，必须严格定义
                    cookie.setPath("/");
                    cookie.setDomain("jt.com");
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/";
    }

}
