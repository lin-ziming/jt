package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired(required = false) //程序启动时，如果没有该对象，暂时不加载
    private Jedis jedis;

    @Override
    public String findItemCatById(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId).getName();
    }

    @CacheFind(preKey="ITEMCAT_PARENTID")
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

    /**  ITEMCAT::0
     * Redis: 2大要素：  key: 业务标识+::+变化参数
     *                  value: String  数据的JSON串
     * 实现步骤：
     * 1.应该查询Redis缓存
     *  有：获取缓存数据之后转化为对象返回
     *  没有：应该查询数据库，并且将查询的结果转化为JSON之后保存到redis，方便下次使用
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITree> findItemCatCache(Long parentId) {
        Long startTime = System.currentTimeMillis();
        List<EasyUITree> treeList = new ArrayList<>();
        String key = "ITEMCAT::"+parentId;
        if (jedis.exists(key)){
            //redis中有数据
            String json = jedis.get(key);
            treeList = ObjectMapperUtil.toObject(json,treeList.getClass());
            Long endTime = System.currentTimeMillis();
            System.out.println("查询redis缓存的时间："+(endTime-startTime)+"毫秒");
        }else{
            //redis中没有数据，应该查询数据库
            treeList = findItemCatList(parentId);
            //需要把数据转成JSON
            String json = ObjectMapperUtil.toJSON(treeList);
            jedis.set(key,json);
            Long endTime = System.currentTimeMillis();
            System.out.println("查询数据库的时间："+(endTime-startTime)+"毫秒");
        }
        return treeList;
    }
}
