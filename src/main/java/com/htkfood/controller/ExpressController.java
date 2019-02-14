package com.htkfood.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.htkfood.entity.vo.ExpressVo;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.ExpressService;
import com.htkfood.util.ResultHelper;
import com.htkfood.util.StringUtil;

import java.util.Map;
@RestController
@RequestMapping(value = "/express")
public class ExpressController {
	@Autowired
	private ExpressService expressService;
	@Privileges(PrivilegeConstant.DISPATCH_PAGE)
	@RequestMapping(value ="/postExpress", method = RequestMethod.POST)
    public Map<String,Object> postExpress(@RequestBody ExpressVo expressList) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		String msg=expressService.addExpress(null, expressList);
		if(!StringUtil.isBlank(msg)) {
			result.put("msg", msg);
		}
        return result;
    }
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value ="/queryLogistics", method = RequestMethod.POST)
    public Map<String,Object> queryLogistics(@RequestParam Long expressId,@RequestParam String t) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		expressService.queryLogistics(null, expressId, t, result);
        return result;
    }
	
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value ="/queryLogistics4App", method = RequestMethod.POST)
    public Map<String,Object> queryLogistics4App(@RequestParam Long expressId) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		expressService.queryLogistics4App(expressId,  result);
        return result;
    }
	
	
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value ="/queryStatus", method = RequestMethod.POST)
    public Map<String,Object> queryStatus(@RequestParam Long expressId,@RequestParam String t) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		expressService.queryStatus(null, expressId, t, result);
        return result;
    }
}
