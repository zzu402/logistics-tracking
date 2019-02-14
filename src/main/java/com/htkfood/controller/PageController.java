package com.htkfood.controller;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.google.zxing.WriterException;
import com.htkfood.entity.UserEntity;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.util.QrCodeUtil;
@Controller
public class PageController {
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request){
        return "project/login";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request){
		UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
		if(userEntity!=null) {
			request.getSession().removeAttribute("user");
		}
        return "project/login";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/main", method = RequestMethod.GET)
    public String mainPage(){
        return "project/main";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/report", method = RequestMethod.GET)
    public String reportPage(){
        return "project/report";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/orderReport", method = RequestMethod.GET)
    public String orderReportPage(){
        return "project/orderReport";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/reportDetail", method = RequestMethod.GET)
    public String reportDetailPage(){
        return "project/reportDetail";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/orderReportDetail", method = RequestMethod.GET)
    public String orderReportDetailPage(){
        return "project/orderReportDetail";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/comment", method = RequestMethod.GET)
    public String commentPage(){
        return "project/commentPage";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/baseManager", method = RequestMethod.GET)
    public String baseManagerPage(){
        return "project/baseManager";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/logisticsManager", method = RequestMethod.GET)
    public String expressManagerPage(){
        return "project/logisticsManager";
    }
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
    @RequestMapping(value ="/welcome", method = RequestMethod.GET)
    public String welcomePage(){
        return "project/welcome";
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
    @RequestMapping(value ="/shipment", method = RequestMethod.GET)
    public ModelAndView shipmentPage(@RequestParam String logisticsNo){
    	Map<String, String> map=new LinkedHashMap<>();
    	map.put("logisticsNo", logisticsNo);
    	return new ModelAndView("project/shipment",map);
    }
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
    @RequestMapping(value ="/logisticsEdit", method = RequestMethod.GET)
    public String logisticsEdit(){
    	return "project/logisticsEdit";
    }
	
	
	
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/clipboardPage", method = RequestMethod.GET)
    public ModelAndView clipboardPage(@RequestParam String logisticsNo){
    	Map<String, String> map=new LinkedHashMap<>();
    	map.put("logisticsNo", logisticsNo);
    	return new ModelAndView("project/clipboardPage",map);
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/d", method = RequestMethod.GET)
    public String driverQrUrlPage(){
    	return "project/driverQrUrl";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/c", method = RequestMethod.GET)
    public String clientQrUrlPage(){
    	return "project/clientQrUrl";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/c2", method = RequestMethod.GET)
    public String clientQrUrlPage3(){
    	return "project/clientQrUrl-2";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/app4expressDetaill", method = RequestMethod.GET)
    public String app4expressDetaillPage(){
    	return "project/app4expressDetaill";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/e", method = RequestMethod.GET)
    public String employeeUrlPage(){
    	return "project/employeeUrl";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/driverQrScanResult", method = RequestMethod.GET)
    public String driverQrScanResultPage(){
        return "project/driverQrScanResult";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/clientQrScanResult", method = RequestMethod.GET)
    public String clientQrScanResultPage(){
        return "project/clientQrScanResult";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/getQrCode", method = RequestMethod.GET)
    public void qrCode(@RequestParam String info,HttpServletRequest request,HttpServletResponse response) throws IOException, WriterException{
    	String t=request.getParameter("t");
    	if(!StringUtil.isEmpty(t)) {
    		info+="&t="+t;
    	}
    	QrCodeUtil.getQrCode(info, response, 300, 300);
    }
	
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/clientQrUrl", method = RequestMethod.GET)
    public String clientQrUrlPage2(){
    	return "project/clientQrUrl2";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/employeeUrl", method = RequestMethod.GET)
    public String employeeUrlPage2(){
    	return "project/employeeUrl2";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/driverQrUrl", method = RequestMethod.GET)
    public String driverQrUrlPage2(){
    	return "project/driverQrUrl2";
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
    @RequestMapping(value ="/productLinePhoto", method = RequestMethod.GET)
    public String productLinePhoto(){
    	return "project/productLinePhoto";
    }
	
}
