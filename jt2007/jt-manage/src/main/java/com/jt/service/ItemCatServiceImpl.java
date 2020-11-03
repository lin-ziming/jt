package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;


    @Override
    public String findItemCatById(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId).getName();
    }

    @Override
    public List<EasyUITree> findItemCatList(Long parentId) {
        //1.准备返回值的数据
        List<EasyUITree> treeList = new ArrayList<>();
        //2.实现数据库的查询
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", parentId);
        List<ItemCat> catList = itemCatMapper.selectList(queryWrapper);

        //思路：返回值的数据从哪来？ VO 转化 POJO数据
        //3.实现数据的转化  catList转化为treeList
        for (ItemCat itemCat : catList) {
            long id = itemCat.getId();  //获取ID值
            String text = itemCat.getName();    //获取商品分类名称
            //判断如果是父级应该closed，如果不是父级则open
            String state = itemCat.getIsParent() ? "closed" : "open";
            EasyUITree easyUITree = new EasyUITree(id,text,state);
            treeList.add(easyUITree);
        }
        return treeList;
    }
}
