package com.htkfood.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.htkfood.entity.UserEntity;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.UserService;
import com.htkfood.util.ResultHelper;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService UserService;
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(value ="/login", method = RequestMethod.POST)
    public Map<String,Object> login(@RequestParam String userName, @RequestParam String password, HttpServletRequest request) throws CommonException  {
		UserEntity userEntity=UserService.login(userName, password);
		request.getSession().setAttribute("user", userEntity);
        return ResultHelper.success();
    }
	
	@Privileges(PrivilegeConstant.LOGIN_PAGE)
	@RequestMapping(value ="/getUserInfo", method = RequestMethod.POST)
    public Map<String,Object> getUserInfo( HttpServletRequest request) throws CommonException  {
		Map<String, Object>result=ResultHelper.success();
		UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
		result.put("user", userEntity);
        return result;
    }
}
