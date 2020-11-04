package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{
	/**
	 * 注意事项：以后写sql语句时，字段名称、表名 小写
	 * 先排序再分页
	 * @param startIndex
	 * @param rows
	 * @return
	 */
	@Select("select * from tb_item order by updated desc limit #{startIndex},#{rows}")
	List<Item> findItemByPage(Integer startIndex, Integer rows);

	void deleteItems(Long[] ids);

	void updateStatus(Integer status, @Param("ids") Long[] ids);
}
