package com.htkfood.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htkfood.entity.Vehicle;
import com.htkfood.exception.CommonException;
import com.htkfood.mapper.Criteria;
import com.htkfood.mapper.slave.VehicleMapper;
import com.htkfood.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleMapper VehicleMapper;

	@Override
	public void insertVehicle(Vehicle Vehicle) throws CommonException {
		VehicleMapper.insertByEntity(Vehicle);

	}

	@Override
	public void updateVehicle(Vehicle updateVehicle) throws CommonException {
	   VehicleMapper.updateByEntity(updateVehicle);

	}

	@Override
	public void delete(Long id) throws CommonException {
		VehicleMapper.deleteById(id);

	}

	@Override
	public List<Vehicle> getVehicle(Criteria criteria) throws CommonException {
		return VehicleMapper.getVehicle(criteria);
	}

	@Override
	public Long insertAndReturnId(Vehicle vehicle) throws CommonException {
		Long row=VehicleMapper.insertAndReturnVehicle(vehicle);
		return vehicle.getId();
	}

}
