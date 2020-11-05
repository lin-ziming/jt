package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 描述商品的详情信息
 */
@TableName("tb_item_desc")
@Data
@Accessors(chain = true)
public class ItemDesc extends BasePojo {
    @TableId
    private Long itemId;  //商品Id号，不需要主键自增，要求与商品表数据一致
    private String itemDesc;  //商品详情信息
}
