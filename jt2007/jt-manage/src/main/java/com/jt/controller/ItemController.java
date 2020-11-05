package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	/**
	 * 业务需求：以分页的形式查询商品列表信息
	 * url: http://localhost:8091/item/query?page=1&rows=20
	 * 请求参数:  page=1&rows=20  page当前页数，rows每页展现的行数
	 * 返回值结果: EasyUITable
	 * 方法1：手写sql方式实现分页
	 * 方法2：利用MP方式实现分页
	 */
	@GetMapping("/item/query")
	public EasyUITable findItemByPage(Integer page,Integer rows){
//		return itemService.findItemByPage(page,rows);
		return itemService.findItemByPage2(page,rows);
	}
	/**
	 * 业务需求：完成商品的入库操作
	 * 注意事项：1.防止出错，添加try-catch
	 * 		   2.新增商品的状态信息，为1
	 * 		   3.入库操作时，完成时间的记录
	 * 请求参数：
	 * 		1.url地址：/item/save
	 * 		2.请求参数：form表单  对象接收
	 * 		3.返回值： 系统级别的VO对象
	 */
	@RequestMapping("/item/save")
	@Transactional  //控制事务
	public SysResult saveItem(Item item, ItemDesc itemDesc){
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
	}

	/**
	 * 实现商品修改操作
	 * 1.url地址：/item/update
	 * 2.请求参数：form表单提交
	 * 3.返回值：SysResult对象
	 * @param item
	 * @return
	 */
	@RequestMapping("/item/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc){
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}

	/**
	 * 业务需求：删除商品信息
	 * 1.url地址：/item/delete
	 * 2.参数： ids: 100,101,102  servlet(request)  同名提交问题
	 * 3.返回值：SysResult
	 * @param ids
	 * @return
	 */
	@RequestMapping("/item/delete")
	public SysResult deleteItems(Long[] ids){
		itemService.deleteItems(ids);
		return SysResult.success();
	}
	/**
	 * 业务: 实现商品的上架/下架
	 * url地址: /item/updateStatus/2
	 * 参数:  状态码信息/ids
	 * 返回值: SysResult对象
	 * */
	@RequestMapping("/item/updateStatus/{status}")
	public SysResult updateStatus(@PathVariable Integer status,Long... ids){
//		itemService.updateStatus(ids,status);
		itemService.updateStatus2(ids,status);
		return SysResult.success();
	}
	/**
	 * 需求: 根据商品Id,查询商品的详情信息.
	 * url地址: http://localhost:8091/item/query/item/desc/1474392019
	 * 参数:    商品Id号
	 * 返回值:  SysResult对象
	 */
	@RequestMapping("/item/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId){
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		return SysResult.success(itemDesc);
	}
}
