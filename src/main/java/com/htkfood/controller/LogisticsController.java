package com.htkfood.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.LogisticsService;
import com.htkfood.util.ResultHelper;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping(value = "/logistics")
public class LogisticsController {
	@Autowired
	private LogisticsService logisticsService;
	/**
	 * 获取物流信息列表
	 * @param model 查询条件
	 * @return
	 * @throws CommonException
	 */
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getLogisticsList", method = RequestMethod.POST)
    public Map<String,Object> getLogisticsList(@RequestBody SearchVo model,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
		request.getSession().setAttribute("queryOrg",model.getOrganization());
		logisticsService.getLogisticsList(userEntity,model,result);
        return result;
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/addLogistics", method = RequestMethod.POST)
    public Map<String,Object> addLogistics(@RequestParam(required=false) String logisticsNo,@RequestParam String organization,@RequestParam String expressCompany,@RequestParam String driverName,@RequestParam String driverPhone,@RequestParam String destination,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		UserEntity userEntity =(UserEntity) request.getSession().getAttribute("user");
		logisticsService.addLogistics(userEntity,logisticsNo, organization, expressCompany, driverName, driverPhone,destination);
        return result;
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/updateLogistics", method = RequestMethod.POST)
    public Map<String,Object> updateLogistics(@RequestParam Long logisticsId,@RequestParam String logisticsNo,@RequestParam String organization,@RequestParam String expressCompany,@RequestParam String driverName,@RequestParam String driverPhone,@RequestParam String destination) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.updateLogistics(logisticsId, logisticsNo, organization, expressCompany, driverName, driverPhone,destination);
        return result;
    }
	
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/updateLogistics2", method = RequestMethod.POST)
    public Map<String,Object> updateLogistics2(@RequestParam Long logisticsId,@RequestParam String logisticsNo,@RequestParam String organization,@RequestParam String expressCompany,@RequestParam String driverName,@RequestParam String driverPhone,@RequestParam String destination,
    		@RequestParam String expressId,@RequestParam String deliveryOrder,@RequestParam String client,@RequestParam String receiver,@RequestParam String receiverPhone,@RequestParam String receiveAddress,@RequestParam String employeeName,@RequestParam String employeePhone) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.updateLogistics2(logisticsId, logisticsNo, organization, expressCompany, driverName, driverPhone,destination,expressId,deliveryOrder,client,receiver,receiverPhone,receiveAddress,employeeName,employeePhone);
        return result;
    }
	
	
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/deleteLogistics", method = RequestMethod.POST)
    public Map<String,Object> deleteLogistics(@RequestParam Long logisticsId) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.deleteLogistics(logisticsId);
        return result;
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/deleteLogistics2", method = RequestMethod.POST)
    public Map<String,Object> deleteLogistics2(@RequestParam Long logisticsId,@RequestParam(required=false) Long expressId) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.deleteLogistics2(logisticsId,expressId);
        return result;
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/dispatchCar", method = RequestMethod.POST)
    public Map<String,Object> dispatchCar(@RequestParam Long logisticsId,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		String smsResult=logisticsService.dispatchCar(logisticsId,request);
		String msg=null;
		if(smsResult.equals("-10006")) {
			msg="派车成功,请复制链接给司机！";
		}else if(smsResult.equals("OK")) {
			msg="派车成功,短信发送成功！";
		}else {
			msg="派车成功,短信发送错误:"+smsResult+",请复制链接给司机！";
		}
		result.put("msg",msg );
        return result;
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/updateStatus", method = RequestMethod.POST)
    public Map<String,Object> updateStatus(@RequestParam Long logisticsId,@RequestParam Integer status,@RequestParam Long expressId) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.updateStatus(logisticsId, status,expressId);
        return result;
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value ="/queryLogistics", method = RequestMethod.POST)
    public Map<String,Object> queryLogistics(@RequestParam String logisticsNo,@RequestParam String t) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.queryLogistics(null, logisticsNo, t, result);
        return result;
    }
	
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/getErpStockInfo", method = RequestMethod.POST)
    public Map<String,Object> getErpStockInfo(@RequestParam String billNo) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.getErpStockInfo(billNo, result);
        return result;
    }
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/editDriver", method = RequestMethod.POST)
    public Map<String,Object> editDriver(@RequestParam Long logisticsId,@RequestParam Long driverId,@RequestParam String plate,@RequestParam String engineNo
    		,@RequestParam String vehicleType,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		logisticsService.editDriver(logisticsId, driverId,plate,engineNo,vehicleType,request);
        return result;
    }
}