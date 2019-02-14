package com.htkfood.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.htkfood.entity.Driver;
import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.entity.Vehicle;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.mapper.Criteria;
import com.htkfood.mapper.slave.DriverMapper;
import com.htkfood.service.DriverService;
import com.htkfood.service.VehicleService;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverMapper driverMapper;
	@Autowired
	private VehicleService vehicleService;

	@Override
	public void insertDriver(Driver driver) throws CommonException {
		driverMapper.insertByEntity(driver);

	}

	@Override
	public void updateDriver(Driver updateDriver) throws CommonException {
		driverMapper.updateByEntity(updateDriver);

	}

	@Override
	public void delete(Long id) throws CommonException {
		driverMapper.deleteById(id);

	}

	@Override
	public List<Driver> getDriver(Criteria criteria) throws CommonException {
		return driverMapper.getDriver(criteria);
	}

	@Override
	public void getDriverAndVehicleList(SearchVo model, Map<String, Object> result) throws CommonException {
		Criteria criteria = new Criteria();
		if (!StringUtil.isEmpty(model.getName())) {
			criteria.put("name", "%" + model.getName() + "%");
		}
		if (!StringUtil.isEmpty(model.getPlate())) {
			criteria.put("plate", "%" + model.getPlate() + "%");
		}
		if (!StringUtil.isEmpty(model.getPhone())) {
			criteria.put("phone", "%" + model.getPhone() + "%");
		}
		if (StringUtil.isEmpty(model.getOrganization())) {
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		criteria.put("organization", model.getOrganization());
		Page<?> page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		List<DriverAndVehicle> list = driverMapper.getDriverAndVehicleList(criteria);
		result.put("DriverAndVehicleList", list);
		result.put("totalNum", page.getTotal());

	}

	@Transactional
	@Override
	public void insertOrUpdateDriverAndVehicle(DriverAndVehicle driverAndVehicle) throws CommonException {
		Long now = System.currentTimeMillis() / 1000;
		Vehicle vehicle = insertOrUpdateVehicle(driverAndVehicle);
		if (driverAndVehicle.getId() == null || driverAndVehicle.getId() <= 0) {
			// 新增
			Driver insertDriver = new Driver();
			insertDriver.setCreateTime(now);
			insertDriver.setUpdateTime(now);
			insertDriver.setIdCard(driverAndVehicle.getIdCard());
			insertDriver.setName(driverAndVehicle.getName());
			insertDriver.setIdCardPhoto(driverAndVehicle.getIdCardPhoto());
			insertDriver.setDriverPhoto(driverAndVehicle.getDriverPhoto());
			insertDriver.setOrganization(driverAndVehicle.getOrganization());
			insertDriver.setPhone(driverAndVehicle.getPhone());
			insertDriver.setStatus(1);
			insertDriver.setVehicleId(vehicle.getId());
			
			Criteria criteria=new Criteria();
			criteria.put("organization", insertDriver.getOrganization());
			criteria.put("name", insertDriver.getName());
			criteria.put("phone", insertDriver.getPhone());
			List<Driver> list=getDriver(criteria);
			if(list!=null&&!list.isEmpty()) {
				throw CommonExceptionHelper.cacheException("该组织下已经存在同名同号码的司机，请勿重复提交", null);
			}
			
			insertDriver(insertDriver);
		} else {
			// 修改
			Criteria criteria = new Criteria();
			criteria.put("id", driverAndVehicle.getId());
			Driver update = getDriver(criteria).get(0);
			update.setUpdateTime(now);
			update.setIdCard(driverAndVehicle.getIdCard());
			update.setName(driverAndVehicle.getName());
			if (StringUtils.isNotBlank(driverAndVehicle.getIdCardPhoto()))
				update.setIdCardPhoto(driverAndVehicle.getIdCardPhoto());
			if (StringUtils.isNotBlank(driverAndVehicle.getDriverPhoto()))
				update.setDriverPhoto(driverAndVehicle.getDriverPhoto());
			update.setOrganization(driverAndVehicle.getOrganization());
			update.setPhone(driverAndVehicle.getPhone());
			update.setStatus(1);
			update.setVehicleId(vehicle.getId());
			criteria.clear();
			criteria.put("organization", update.getOrganization());
			criteria.put("name", update.getName());
			criteria.put("phone", update.getPhone());
			List<Driver> list=getDriver(criteria);
			if(list!=null&&!list.isEmpty()) {
				for(Driver driver:list) {
					if(!driver.getId().equals(driverAndVehicle.getId())) {
						throw CommonExceptionHelper.cacheException("该组织下已经存在同名同号码的司机，请勿重复提交", null);
					}
				}
			}
			updateDriver(update);
		}

	}

	private Vehicle insertOrUpdateVehicle(DriverAndVehicle driverAndVehicle) throws CommonException {
		Long now = System.currentTimeMillis() / 1000;
		Vehicle vehicle = null;
		Criteria criteria2 =new Criteria();
		criteria2.put("plate", driverAndVehicle.getPlate());
		//criteria2.put("engineNo",driverAndVehicle.getEngineNo() );
		List<Vehicle> list=vehicleService.getVehicle(criteria2 );
		if(list!=null&&!list.isEmpty()) {
			driverAndVehicle.setVehicleId(list.get(0).getId());
		}else {
			driverAndVehicle.setVehicleId(null);
		}
		if (driverAndVehicle.getVehicleId() == null || driverAndVehicle.getVehicleId() < 0) {
			vehicle = new Vehicle();
			vehicle.setPlate(driverAndVehicle.getPlate());
			vehicle.setCreateTime(now);
			vehicle.setOwner(driverAndVehicle.getOwner());
			vehicle.setStatus(1);
			vehicle.setUpdateTime(now);
			vehicle.setEngineNo(driverAndVehicle.getEngineNo());
			vehicle.setVehicleCardPhoto(driverAndVehicle.getVehicleCardPhoto());
			vehicle.setVin(driverAndVehicle.getVin());
			vehicle.setVehicleType(driverAndVehicle.getVehicleType());
			vehicle.setOrganization(driverAndVehicle.getOrganization());
			Long id = vehicleService.insertAndReturnId(vehicle);
			vehicle.setId(id);
		} else {
			Criteria criteria = new Criteria();
			criteria.put("id", driverAndVehicle.getVehicleId());
			vehicle = vehicleService.getVehicle(criteria).get(0);
			vehicle.setPlate(driverAndVehicle.getPlate());
			vehicle.setOwner(driverAndVehicle.getOwner());
			vehicle.setStatus(1);
			vehicle.setUpdateTime(now);
			vehicle.setEngineNo(driverAndVehicle.getEngineNo());
			if (StringUtils.isNotBlank(driverAndVehicle.getVehicleCardPhoto()))
				vehicle.setVehicleCardPhoto(driverAndVehicle.getVehicleCardPhoto());
			vehicle.setVin(driverAndVehicle.getVin());
			vehicle.setVehicleType(driverAndVehicle.getVehicleType());
			vehicleService.updateVehicle(vehicle);

		}
		return vehicle;

	}

	@Override
	public List<DriverAndVehicle> getDriverAndVehicleByBillNo(Criteria criteria) throws CommonException {
		return driverMapper.getDriverAndVehicleByBillNo(criteria);
	}

}
