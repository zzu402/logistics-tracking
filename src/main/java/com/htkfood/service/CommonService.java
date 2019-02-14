package com.htkfood.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.entity.ExpressCompanyEntity;
import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;

public interface CommonService {
	
	void getNotificationByUser(UserEntity user,Map<String, Object>result) throws CommonException;
	
	/**
	 * 获取物流公司
	 * 因为不同分公司能够获取的数据是不同的，这里还需要传入一个
	 * User来选择它所拥有查询的数据权限
	 * @param user
	 * @return
	 * @throws CommonException
	 */
	List<ExpressCompanyEntity> getCompanyByUser(UserEntity user,SearchVo model,Map<String, Object>result) throws CommonException;
	
	List<DriverAndVehicle> getDriverByUser(UserEntity user,SearchVo model,Map<String, Object>result) throws CommonException;
	
	List<DriverAndVehicle> getVehicleByUser(UserEntity user,SearchVo model,Map<String, Object>result) throws CommonException;
	
	void getExpressDetail4App(String expressId,Map<String, Object>result,HttpServletRequest request)throws CommonException;
	
	void getDeliveryOrder(UserEntity user,SearchVo model,Map<String, Object>result) throws CommonException;
	
	void getEmployee(SearchVo model,Map<String, Object>result) throws CommonException;
	
	HSSFWorkbook exportReport(UserEntity user,SearchVo model) throws CommonException;
	
	HSSFWorkbook exportOrderReport(UserEntity user,SearchVo model) throws CommonException;
	
	HSSFWorkbook exportLogistics(UserEntity user,SearchVo model) throws CommonException;
}