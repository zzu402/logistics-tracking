package com.htkfood.mapper.slave;

import java.util.List;

import com.htkfood.entity.OrderDetailReportEntity;
import com.htkfood.entity.OrderReportEntity;
import com.htkfood.entity.ReportDetailEntity;
import com.htkfood.entity.ReportEntity;
import com.htkfood.mapper.Criteria;

/**
 * 获取报表
 * @author Administrator
 */
public interface ReportMapper {
	
	List<ReportEntity>  getReport(Criteria criteria);
	
	List<ReportDetailEntity>  getReportDetail(Criteria criteria);
	
	List<OrderReportEntity>  getOrderReport(Criteria criteria);
	
	List<OrderDetailReportEntity>  getOrderReportDetailList(Criteria criteria);

	


}
