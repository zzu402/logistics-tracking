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
@RequestMapping(value = "/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getReportList", method = RequestMethod.POST)
    public Map<String,Object> getReportList(@RequestBody SearchVo model,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
		request.getSession().setAttribute("queryOrg",model.getOrganization());
		reportService.getReport(userEntity,model,result);
        return result;
    }
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getReportDetailList", method = RequestMethod.POST)
    public Map<String,Object> getReportDetailList(@RequestBody SearchVo model,HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		String org=(String) request.getSession().getAttribute("queryOrg");
		UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
		model.setOrganization(org);
		reportService.getReportDetail(userEntity,model,result);
        return result;
    }

}
