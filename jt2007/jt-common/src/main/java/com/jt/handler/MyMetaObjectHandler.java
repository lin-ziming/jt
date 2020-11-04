package com.jt.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component  //将对象交给spring容器管理
public class MyMetaObjectHandler implements MetaObjectHandler {
    //完成入库操作自动赋值
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.setFieldValByName("created", date, metaObject);
        this.setFieldValByName("updated", date, metaObject);
    }
    //完成更新操作自动赋值
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updated",new Date(),metaObject);
    }
}
