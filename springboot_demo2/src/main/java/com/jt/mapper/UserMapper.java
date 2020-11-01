package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("select id,name,age,sex from user")
    List<User> findAll();
}
