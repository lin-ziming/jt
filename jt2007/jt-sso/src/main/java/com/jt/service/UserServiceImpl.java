package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    private static Map<Integer,String> paramMap = new HashMap<>();
    static {
        paramMap.put(1,"username");
        paramMap.put(2,"phone");
        paramMap.put(3,"email");
    }
    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }
    /**
     *  校验数据库中是否有数据....
     *  Sql: select count(*) from tb_user where username="admin123";
     *  要求:返回数据true用户已存在，false用户不存在
     */
    @Override
    public boolean checkUser(String param, Integer type) {
        String column = paramMap.get(type);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, param);
        int count = userMapper.selectCount(queryWrapper);
//        return count>0 ? true : false;
        return count>0;
    }
}
