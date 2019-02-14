package com.htkfood.service;

import java.util.Map;

import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;

public interface ReportService {
		void getReport(UserEntity user,SearchVo model,Map<String, Object>result)throws CommonException;
		void getReportDetail(UserEntity user,SearchVo model,Map<String, Object>result)throws CommonException;
		
		void getOrderReport(UserEntity user,SearchVo model,Map<String, Object>result)throws CommonException;
		
		void getOrderReportDetail(UserEntity user,SearchVo model,Map<String, Object>result)throws CommonException;
}
