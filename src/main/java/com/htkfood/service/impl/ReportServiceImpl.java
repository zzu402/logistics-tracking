package com.htkfood.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.htkfood.entity.LogisticsEntity;
import com.htkfood.entity.OrderDetailReportEntity;
import com.htkfood.entity.OrderReportEntity;
import com.htkfood.entity.ReportDetailEntity;
import com.htkfood.entity.ReportEntity;
import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.mapper.Criteria;
import com.htkfood.mapper.slave.ReportMapper;
import com.htkfood.service.LogisticsService;
import com.htkfood.service.ReportService;
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private LogisticsService logisticsService;
	@Override
	public void getReport(UserEntity user,SearchVo model, Map<String, Object> result) throws CommonException {
		Criteria criteria=new Criteria();
		if (!StringUtil.isEmpty(model.getExpressCompany())) {
			criteria.put("expressCompany", "%"+model.getExpressCompany()+"%");
		}
		if(StringUtil.isEmpty(model.getOrganization())) {
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		if (model.getTimeType() != null && model.getTimeType() > -1) {
			String beginType = null;
			String endType = null;
			if (model.getTimeType() == 0) {
				beginType = "beginCreateTime";
				endType = "endCreateTime";
			} else if (model.getTimeType() == 1) {
				beginType = "beginDispatchTime";
				endType = "endDispatchTime";
			} else if (model.getTimeType() == 2) {
				beginType = "beginArriveTime";
				endType = "endArriveTime";
			} else if (model.getTimeType() == 3) {
				beginType = "beginShipmentTime";
				endType = "endShipmentTime";
			} else if (model.getTimeType() == 4) {
				beginType = "beginLeaveTime";
				endType = "endLeaveTime";
			} else if (model.getTimeType() == 5) {
				beginType = "beginDeliveryTime";
				endType = "endDeliveryTime";
			}
			if (model.getBeginTime() != null && model.getBeginTime() > 0) {
				criteria.put(beginType, model.getBeginTime());
			}
			if (model.getEndTime() != null && model.getEndTime() > 0) {
				criteria.put(endType, model.getEndTime() + 24 * 60 * 60);
			}
		}
		
		
		criteria.put("organization", model.getOrganization());
		@SuppressWarnings("rawtypes")
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		List<ReportEntity> list=reportMapper.getReport(criteria);
		result.put("totalNum", page.getTotal());
		result.put("reportList", list);
	}
	@Override
	public void getReportDetail(UserEntity user, SearchVo model, Map<String, Object> result)throws CommonException{
		Criteria criteria=new Criteria();
		if(StringUtil.isEmpty(model.getOrganization())) {
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		criteria.put("organization", model.getOrganization());
		if(StringUtil.isEmpty(model.getLogisticsNo())) {
			throw CommonExceptionHelper.commonException("找不到信息，参数出错！", null);
		}
		LogisticsEntity logisticsEntity=logisticsService.getLogisticsByNo(user, model.getLogisticsNo());
		criteria.put("expressCompany", logisticsEntity.getExpressCompany());
		if (model.getTimeType() != null && model.getTimeType() > -1) {
			String beginType = null;
			String endType = null;
			if (model.getTimeType() == 0) {
				beginType = "beginCreateTime";
				endType = "endCreateTime";
			} else if (model.getTimeType() == 1) {
				beginType = "beginDispatchTime";
				endType = "endDispatchTime";
			} else if (model.getTimeType() == 2) {
				beginType = "beginArriveTime";
				endType = "endArriveTime";
			} else if (model.getTimeType() == 3) {
				beginType = "beginShipmentTime";
				endType = "endShipmentTime";
			} else if (model.getTimeType() == 4) {
				beginType = "beginLeaveTime";
				endType = "endLeaveTime";
			} else if (model.getTimeType() == 5) {
				beginType = "beginDeliveryTime";
				endType = "endDeliveryTime";
			}
			if (model.getBeginTime() != null && model.getBeginTime() > 0) {
				criteria.put(beginType, model.getBeginTime());
			}
			if (model.getEndTime() != null && model.getEndTime() > 0) {
				criteria.put(endType, model.getEndTime() + 24 * 60 * 60);
			}
		}
		@SuppressWarnings("rawtypes")
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		List<ReportDetailEntity> list=reportMapper.getReportDetail(criteria);
		result.put("totalNum", page.getTotal());
		result.put("reportList", list);
	}
	@Override
	public void getOrderReport(UserEntity user, SearchVo model, Map<String, Object> result) throws CommonException {
		Criteria criteria=new Criteria();
//		if(StringUtil.isEmpty(model.getOrganization())) {
//			throw CommonExceptionHelper.commonException("请选择所属公司", null);
//		}
//		criteria.put("organization", model.getOrganization());
		if(model.getBeginTime()!=null&&model.getBeginTime()!=0) {
			criteria.put("beginTime", model.getBeginTime());
		}
		if(model.getEndTime()!=null&&model.getEndTime()!=0) {
			criteria.put("endTime", model.getEndTime());	
		}
		List<OrderReportEntity> list=new ArrayList<OrderReportEntity>();
		List<OrderReportEntity> subList;
		OrderReportEntity vo;
		String[] orgs = { "福建回头客食品有限公司", "山东回头客食品有限公司", "四川回头客食品有限公司", "湖北回头客食品有限公司", "吉林回头客食品有限公司", "福建回头客电子商务有限公司" };
		for (String org : orgs) {
			criteria.put("organization", org);
			subList=reportMapper.getOrderReport(criteria);
			if(subList!=null&&subList.size()>0) {
				vo=subList.get(0);
				vo.setOrg(org);
				list.add(vo);
			}
		}

		//Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		
		result.put("totalNum", list.size());
		result.put("reportList", list);
	}
	@Override
	public void getOrderReportDetail(UserEntity user, SearchVo model, Map<String, Object> result)
			throws CommonException {
		Criteria criteria=new Criteria();
		if(StringUtil.isEmpty(model.getOrganization())) {
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		criteria.put("organization", model.getOrganization());
		if(model.getBeginTime()!=null&&model.getBeginTime()!=0) {
			criteria.put("beginTime", model.getBeginTime());
		}
		if(model.getEndTime()!=null&&model.getEndTime()!=0) {
			criteria.put("endTime", model.getEndTime());	
		}
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		List<OrderDetailReportEntity> list=reportMapper.getOrderReportDetailList(criteria);
		result.put("totalNum", page.getTotal());
		result.put("reportList", list);
		
	}

}
