package com.htkfood.service;
import java.util.List;

import com.htkfood.entity.Vehicle;
import com.htkfood.exception.CommonException;
import com.htkfood.mapper.Criteria;

public interface VehicleService {
	
	void insertVehicle(Vehicle vehicle) throws CommonException;
	
	Long insertAndReturnId(Vehicle vehicle) throws CommonException;
	
	void updateVehicle(Vehicle updateVehicle) throws CommonException;
	
	void delete(Long id)throws CommonException;;
	
	List<Vehicle> getVehicle(Criteria criteria) throws CommonException;
}
