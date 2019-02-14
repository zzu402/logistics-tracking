package com.htkfood.service;
import java.util.List;
import java.util.Map;

import com.htkfood.entity.Driver;
import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.mapper.Criteria;

public interface DriverService {
	
	void insertDriver(Driver driver) throws CommonException;
	
	void updateDriver(Driver updateDriver) throws CommonException;
	
	void delete(Long id)throws CommonException;;
	
	List<Driver> getDriver(Criteria criteria) throws CommonException;
	
	void getDriverAndVehicleList(SearchVo model,Map<String, Object>result)throws CommonException;
	
	
	void insertOrUpdateDriverAndVehicle(DriverAndVehicle driverAndVehicle)throws CommonException;
	
	List<DriverAndVehicle> getDriverAndVehicleByBillNo(Criteria criteria)throws CommonException;
	
}
