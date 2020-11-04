package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.pojo.Item;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;

	/**
	 * 1.后端查询数据库记录
	 * 2.将后端数据通过业务调用转化为VO对象
	 * 3.前端通过VO对象的JSON进行数据的解析
	 * 手写分页
	 * Sql分页查询：select * from tb_item limit 0,20;
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		int startIndex = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemByPage(startIndex, rows);
		// total  获取数据库总记录数
		Long total = Long.valueOf(itemMapper.selectCount(null));
		return new EasyUITable(total,itemList);
	}
	/**
	 * 方法2：利用MP方式实现分页
	 */
	@Override
	public EasyUITable findItemByPage2(Integer page, Integer rows){
		//1.需要使用MP的方式进行分页
		IPage<Item> iPage = new Page<>(page,rows);
		QueryWrapper<Item> queryWrapper = new QueryWrapper();
		queryWrapper.orderByDesc("updated");
		//MP通过分页操作将分页的相关数据统一封装到IPage对象中
		iPage = itemMapper.selectPage(iPage, queryWrapper);
		return new EasyUITable(iPage.getTotal(), iPage.getRecords());
	}

	@Override
	public void saveItem(Item item) {
//		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		item.setStatus(1);
		itemMapper.insert(item);
	}

	/**
	 * 一般更新操作都是根据主键更新
	 * Sql: update tb_item set titel=#{xxx},x,x where id=#{xxx}
	 * @param item
	 */
	@Override
	public void updateItem(Item item) {
		//根据对象中不为null的元素充当set条件
		itemMapper.updateById(item);
	}

	@Override
	public void deleteItems(Long[] ids) {
//		List<Long> idsList = Arrays.asList(ids);
//		itemMapper.deleteBatchIds(idsList);

		//手动删除数据
		itemMapper.deleteItems(ids);
	}
	/**
	 * 参数说明:  entity:修改数据的值   updateWrapper
	 */
	@Override
	public void updateStatus(Long[] ids, Integer status) {
		Item item = new Item();
		item.setStatus(status);
		//where id in (1,2,3,4)
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", Arrays.asList(ids));
		itemMapper.update(item,updateWrapper);
	}
	/**
	 * //作业:sql手动完成
	 */
	@Override
	public void updateStatus2(Long[] ids, Integer status) {
		itemMapper.updateStatus(status, ids);
	}
}
