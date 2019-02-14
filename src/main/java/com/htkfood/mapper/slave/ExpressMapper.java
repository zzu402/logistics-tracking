package com.htkfood.mapper.slave;

import java.util.List;

import com.htkfood.entity.ExpressEntity;
import com.htkfood.mapper.Criteria;

/**
 * 关于Express表的操作，包括增删改查
 * @author Administrator
 */
public interface ExpressMapper {
	/**
	 * 获取express
	 * @return
	 */
	List<ExpressEntity>  getExpress(Criteria criteria);
	/*
	 * 添加
	 */
	int insertByEntity(ExpressEntity expressEntity);
	/*
	 * 更新
	 */
	int updateByEntity(ExpressEntity expressEntity);
	/**
	 * 刪除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	
	int  updateDriver4deliveryNotice(Criteria criteria);
	
	int  updateDriver4outStock(Criteria criteria);
	


}
