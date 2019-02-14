package com.htkfood.service;

import java.util.Map;

import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.ExpressVo;
import com.htkfood.exception.CommonException;

public interface ExpressService {
	
	String addExpress(UserEntity user,ExpressVo expressVo)throws CommonException;
	
	void queryLogistics(UserEntity user,Long expressId,String  t,Map<String, Object>result)throws CommonException;
	void queryLogistics4App(Long expressId,Map<String, Object>result)throws CommonException;
	
	void queryStatus(UserEntity user,Long expressId,String  t,Map<String, Object>result)throws CommonException;
	
	ExpressEntity  queryExpressById(Long expressId) throws CommonException;
	
	void  updateByEntity(ExpressEntity entity) throws CommonException;

}
