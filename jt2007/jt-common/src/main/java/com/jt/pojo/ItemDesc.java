package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 描述商品的详情信息
 */
@TableName("tb_item_desc")
@Data   //重写的toString方法一般只会添加自己的属性信息,父级的属性不会添加
@Accessors(chain = true)
public class ItemDesc extends BasePojo {
    @TableId
    private Long itemId;  //商品Id号，不需要主键自增，要求与商品表数据一致
    private String itemDesc;  //商品详情信息

    //Data注解动态生成get/set方法
//    public String getLYJ(){
//        return "刘昱江";
//    }
//    public void setLYJ(String xxx){
//        System.out.println("我的set方法被成功调用!!!!");
//    }

}
