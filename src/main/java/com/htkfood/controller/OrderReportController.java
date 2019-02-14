package com.htkfood.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.ReportService;
import com.htkfood.util.ResultHelper;

@RestController
@RequestMapping(value = "/orderReport")
public class OrderReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getOrderReportList", method = RequestMethod.POST)
    public Map<String,Object> getReportList(@RequestBody SearchVo model,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
//		request.getSession().setAttribute("queryOrg",model.getOrganization());
		reportService.getOrderReport(userEntity,model,result);
        return result;
    }
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getOrderReportDetailList", method = RequestMethod.POST)
    public Map<String,Object> getOrderReportDetailList(@RequestBody SearchVo model,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		String[] orgs = { "福建回头客食品有限公司", "山东回头客食品有限公司", "四川回头客食品有限公司", "湖北回头客食品有限公司", "吉林回头客食品有限公司", "福建回头客电子商务有限公司" };
		String org=orgs[Integer.valueOf(model.getIndex())];
    	UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
    	model.setOrganization(org);
    	reportService.getOrderReportDetail(userEntity,model,result);
        return result;
    }

}
