package com.htkfood.mapper.slave;

import java.util.List;
import com.htkfood.entity.LogisticsEntity;
import com.htkfood.mapper.Criteria;

/**
 * 关于Logistics表的操作，包括增删改查
 * @author Administrator
 */
public interface LogisticsMapper {
	/**
	 * 获取Logistics
	 * @return
	 */
	List<LogisticsEntity>  getLogistics(Criteria criteria);
	/*
	 * 添加
	 */
	int insertByEntity(LogisticsEntity logisticsEntity);
	/*
	 * 更新
	 */
	int updateByEntity(LogisticsEntity logisticsEntity);
	/**
	 * 刪除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	


}
