package com.htkfood.mapper.slave;

import java.util.List;

import com.htkfood.entity.Vehicle;
import com.htkfood.mapper.Criteria;

/**
 * 关于Vehicle表的操作，包括增删改查
 * @author Administrator
 */
public interface VehicleMapper {
	/**
	 * 获取Logistics
	 * @return
	 */
	List<Vehicle>  getVehicle(Criteria criteria);
	/*
	 * 添加
	 */
	Long insertByEntity(Vehicle Vehicle);
	
	Long insertAndReturnVehicle(Vehicle Vehicle);
	/*
	 * 更新
	 */
	int updateByEntity(Vehicle Vehicle);
	/**
	 * 刪除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	


}
