package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName
@Data
@Accessors(chain = true)
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;//主键自增
    private String name;
    private Integer age;
    private String sex;
}
