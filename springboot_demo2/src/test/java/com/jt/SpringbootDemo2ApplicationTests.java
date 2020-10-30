package com.jt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootDemo2ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据主键查询
     */
    @Test
    public void select01(){
        User user = userMapper.selectById(21);
        System.out.println(user);
        int count = userMapper.selectCount(null);
        System.out.println("总记录数："+count);
    }

    /**
     * 需求：查询年龄=18用户，同时要求性别为女
     * 条件构造器的作用：用来拼接where条件
     * sql： xxx  where age=18 and sex="女"
     * 逻辑运算符：=eq,  >gt,  <lt,  >=ge,  <=le
     */
    @Test
    public void select02(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("age", 18)
                    .eq("sex", "女");
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     * 查询ID=1, 3, 5, 6 的数据
     * sql:select * from user where id in (1,3,5,6);
     */
    @Test
    public void select03(){
        Integer[] ids = {1,3,5,7}; //模拟用户参数
        List<Integer> idList = Arrays.asList(ids);
        List<User> userList = userMapper.selectBatchIds(idList);
        System.out.println(userList);

        //如果需要获取表中的第一列主键信息


    }

    /**
     * 完成用户数据入库
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("名媛").setAge(80).setSex("女");
        userMapper.insert(user);
    }
    /**
     * 更新操作
     * 老师建议：但凡写更新操作时，最好自己手写
     * 需求： 需要将name="名媛" 改为 "北京大爷"
     */
    @Test
    public void testUpdate(){
        //根据对象中不为null的属性，当做set条件
//        User user = new User();
//        user.setId(65).setName("北京大爷");
//        userMapper.updateById(user);

        //1.参数1: 需要修改的数据    参数2:修改的where条件
        User user = new User();
        user.setName("北京大爷");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "名媛");
        userMapper.update(user,updateWrapper);

    }

    /**
     * 删除操作：
     * 1.根据ID删除用户信息 65
     * 2.删除name="名媛"的数据
     */
    @Test
    public void testDelete(){
        userMapper.deleteById(65);//根据主键删除
//        userMapper.deleteBatchIds("id集合信息");
        //根据除主键之外的数据删除信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", "名媛");
        userMapper.delete(queryWrapper);
    }

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(100).setName("ssss");
    }

}
