package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;
    /**
     * 参数介绍：
     * @param1 request 用户的请求对象
     * @param2 response 服务器响应对象
     * @param3 handler  当前处理器本身
     * @return boolean true 请求放行  false 请求拦截 一般配合重定向使用
     * @throws Exception
     * 如果用户不登录则重定向到登录页面
     *
     * 需求:   如何判断用户是否登录?
     * 依据:   1.根据cookie    2.判断redis
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        //1.判断cookie中是否有记录
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0){
            for (Cookie cookie : cookies) {
                if( "JT_TICKET".equals(cookie.getName()) ){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        //2.判断cookie数据是否有效
        if(!StringUtils.isEmpty(ticket)){
            if(jedisCluster.exists(ticket)){
                String userJSON = jedisCluster.get(ticket);
                User user = ObjectMapperUtil.toObject(userJSON,User.class);
                //3.利用request对象进行数据的传递 request对象是最为常用的传递参数的媒介
                request.setAttribute("JT_USER",user);
                UserThreadLocal.set(user);
                return true;  //表示用户已经登录
            }
        }
        //重定向到用户登录页面
        response.sendRedirect("/user/login.html");
        return false;
    }

    /**
     * 为了满足业务需要将数据删除
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        request.removeAttribute("JT_USER");
        UserThreadLocal.remove();
    }
}
