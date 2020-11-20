package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService{
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return cartMapper.selectList(queryWrapper);
    }

    /**
     * Sql:update tb_cart set num=#{num},updated=#{updated}
     *     where user_id=#{userId} and item_id=#{itemId}
     * @param cart
     */
    @Override
    public void updateCartNum(Cart cart) {
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum());
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId())
                    .eq("item_id",cart.getItemId());
        cartMapper.update(cartTemp,queryWrapper);
        //参数1：封装set条件  参数2:封装where条件
    }

    @Override
    public void deleteCart(Cart cart) { //userId,itemId
        cartMapper.delete(new QueryWrapper<>(cart));
        //根据对象中不为null的属性当做where条件
    }

    /**
     * 如果购物车已存在,则更新数量,否则新增.
     * 如何判断购物车数据是否存在   userId itemId
     * @param cart
     */
    @Override
    public void addCart(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId())
                    .eq("item_id",cart.getItemId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if(cartDB == null){
            cartMapper.insert(cart);
        }else{
            int num =cartDB.getNum() + cart.getNum();
            Cart cartTemp = new Cart();
            cartTemp.setNum(num).setId(cartDB.getId());
            cartMapper.updateById(cartTemp);
        }
    }
}
