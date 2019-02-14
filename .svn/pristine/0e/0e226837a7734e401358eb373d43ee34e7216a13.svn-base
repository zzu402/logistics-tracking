package com.htkfood.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.QrScanService;
import com.htkfood.util.ResultHelper;

@RestController
@RequestMapping(value = "/qrScan")
public class QrScanController {
	@Autowired
	private QrScanService qrScanService;
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value = "/driverQrScan", method = RequestMethod.POST)
	public Map<String, Object> driverQrScan(@RequestParam String logisticsNo,@RequestParam String t) throws CommonException {
		Map<String,Object>result=ResultHelper.success();
		qrScanService.driverQrScan(logisticsNo, t,result);
		return result;
	}
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value = "/clientQrScan", method = RequestMethod.POST)
	public Map<String, Object> driverQrScan(@RequestParam Long expressId,@RequestParam String t) throws CommonException {
		Map<String,Object>result=ResultHelper.success();
		qrScanService.clientQrScan(expressId, t, result);
		return result;
	}

}
