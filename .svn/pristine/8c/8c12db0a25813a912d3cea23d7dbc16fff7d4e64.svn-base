package com.htkfood.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.htkfood.entity.ProductLinePhotoVo;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.service.ProductLinePhotoService;
import com.htkfood.util.ResultHelper;

@RestController
@RequestMapping(value = "/productLinePhoto")
public class ProductLinePhotoController {
	
	@Autowired
	private ProductLinePhotoService productLinePhotoService;
	
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping("/getPhotos")
	private Map<String,Object> getPhotos(@RequestBody SearchVo model)throws CommonException{
		Map<String, Object>result=ResultHelper.success();
		List<ProductLinePhotoVo> list=productLinePhotoService.getProductLinePhotoVos(model,result);
		result.put("photos", list);
		return result;
	}
	
	
	
}
