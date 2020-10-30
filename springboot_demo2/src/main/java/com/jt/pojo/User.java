package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data //动态生成get/set/toString/equals/无参构造方法
@Accessors(chain = true) //开启链式加载结构
@NoArgsConstructor   //无参构造 必须添加
@AllArgsConstructor  //全参构造

@TableName("user") //标识对象与表的映射关系，如果表名与对象名称一致则可以省略不写
public class User {
    //实体对象的属性类型应该都是包装类型  Integer默认值为null, int默认值为0

    @TableId(type = IdType.AUTO) //主键自增
    private Integer id;
    //@TableField(value = "name") //标识属性和字段
    private String name;
    private Integer age;
    private String sex;

//    /**
//     * 通过重写set方法实现链式加载
//     * @param id
//     * @return
//     */
//    public User setId(Integer id){
//        this.id = id;
//        return this;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }

}
