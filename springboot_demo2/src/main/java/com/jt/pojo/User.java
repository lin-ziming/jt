package com.jt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data //动态生成get/set/toString/equals/无参构造方法
@Accessors(chain = true) //开启链式加载结构
@NoArgsConstructor   //无参构造 必须添加
@AllArgsConstructor  //全参构造
public class User {
    //实体对象的属性类型应该都是包装类型  Integer默认值为null, int默认值为0
    private Integer id;
    private String name;
    private Integer age;
    private String sex;

    /**
     * 通过重写set方法实现链式加载
     * @param id
     * @return
     */
    public User setId(Integer id){
        this.id = id;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}
