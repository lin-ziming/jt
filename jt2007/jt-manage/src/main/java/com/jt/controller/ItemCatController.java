package com.jt.controller;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  //返回JSON数据
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    /**
     * 业务：根据商品分类的ID，查询商品分类的名称
     * url: /item/cat/queryItemName
     * 参数：itemCatId 商品分类id
     * 返回值：商品分类名称
     */
    @RequestMapping("/queryItemName")
    public String findItemCatName(Long itemCatId){

        return itemCatService.findItemCatById(itemCatId);
    }
    /**
     * 业务: 实现商品分类的查询
     * url地址: /item/cat/list
     * 参数:   id: 默认应该0 否则就是用户的ID
     * 返回值结果: List<EasyUITree>
     */
    @RequestMapping("/list")
    public List<EasyUITree> findItemCatList(Long id){
        Long parentId = (id==null) ? 0 : id;
        return itemCatService.findItemCatList(parentId);
    }

}
