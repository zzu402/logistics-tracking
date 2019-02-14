package com.htkfood.mapper.slave;

import java.util.List;

import com.htkfood.entity.Driver;
import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.mapper.Criteria;

/**
 * 关于Driver表的操作，包括增删改查
 * @author Administrator
 */
public interface DriverMapper {
	/**
	 * 获取Logistics
	 * @return
	 */
	List<Driver>  getDriver(Criteria criteria);
	/*
	 * 添加
	 */
	int insertByEntity(Driver Driver);
	/*
	 * 更新
	 */
	int updateByEntity(Driver Driver);
	/**
	 * 刪除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	
	List<DriverAndVehicle> getDriverAndVehicleList(Criteria criteria);
	
	List<DriverAndVehicle> getDriverAndVehicleByBillNo(Criteria criteria);
	


}
