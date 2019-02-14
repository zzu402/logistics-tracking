package com.htkfood.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.mapper.Criteria;
import com.htkfood.service.DriverService;
import com.htkfood.service.VehicleService;
import com.htkfood.util.ResultHelper;

@RestController
@RequestMapping(value = "/driverAndVehicle")
public class DriverAndVehicleController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleService vehicleService;
	
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getDriverAndVehicleListList", method = RequestMethod.POST)
    public Map<String,Object> getDriverAndVehicleListList(@RequestBody SearchVo model,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		request.getSession().setAttribute("queryOrg",model.getOrganization());
	    //System.out.println(model.getOrganization());
	    driverService.getDriverAndVehicleList(model, result);
        return result;
    }
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/insertOrUpdateDriverAndVehicle", method = RequestMethod.POST)
    public Map<String,Object> insertOrUpdateDriverAndVehicle(@RequestBody DriverAndVehicle driverAndVehicle,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		driverAndVehicle.setOrganization((String) request.getSession().getAttribute("queryOrg"));
		driverService.insertOrUpdateDriverAndVehicle(driverAndVehicle);
        return result;
    }
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getVehicleList", method = RequestMethod.POST)
    public Map<String,Object> getVehicleList(HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		Criteria criteria=new Criteria();
		criteria.put("organization", (String) request.getSession().getAttribute("queryOrg"));
		result.put("vehicleInfo",vehicleService.getVehicle(criteria));
        return result;
    }
	
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/deleteDriverAndVehicle", method = RequestMethod.POST)
    public Map<String,Object> deleteDriverAndVehicle(@RequestParam Long id,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		driverService.delete(id);
        return result;
    }
	
	

}
