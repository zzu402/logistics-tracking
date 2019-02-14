package com.htkfood.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.htkfood.entity.DeliveryOrderEntity;
import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.entity.EmployeeEntity;
import com.htkfood.entity.ExpressCompanyEntity;
import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.LogisticsEntity;
import com.htkfood.entity.NotificationEntity;
import com.htkfood.entity.OrderReportEntity;
import com.htkfood.entity.ReportDetailEntity;
import com.htkfood.entity.ReportEntity;
import com.htkfood.entity.UserEntity;
import com.htkfood.entity.Vehicle;
import com.htkfood.entity.vo.LogisticsVo;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.mapper.Criteria;
import com.htkfood.mapper.slave.DriverMapper;
import com.htkfood.mapper.slave.ExpressMapper;
import com.htkfood.mapper.slave.LogisticsMapper;
import com.htkfood.mapper.slave.NotificationMapper;
import com.htkfood.mapper.slave.ReportMapper;
import com.htkfood.mapper.slave.UserMapper;
import com.htkfood.mapper.slave.VehicleMapper;
import com.htkfood.service.CommonService;
import com.htkfood.util.StringUtil;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LogisticsMapper LogisticsMapper;
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private ExpressMapper expressMapper;
	@Autowired
	private NotificationMapper notificationMapper;
	@Autowired
	private DriverMapper driverMapper;
	@Autowired
	private VehicleMapper vehicleMapper;

	@Override
	public List<ExpressCompanyEntity> getCompanyByUser(UserEntity user, SearchVo model, Map<String, Object> result)
			throws CommonException {
		@SuppressWarnings({ "rawtypes" })
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		String keyword = model.getKeyword();
		if (!StringUtil.isBlank(keyword))
			keyword = "%" + keyword + "%";
		else
			keyword = null;
		List<ExpressCompanyEntity> list = userMapper.getExpressCompany(model.getOrgNo(), keyword);
		result.put("companies", list);
		result.put("totalNum", page.getTotal());
		return list;
	}

	@Override
	public void getDeliveryOrder(UserEntity user, SearchVo model, Map<String, Object> result) throws CommonException {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -1);
		Long date = calendar.getTime().getTime() / 1000;
		Criteria criteria = new Criteria();
		criteria.put("time", date);
		List<ExpressEntity> expressEntities = expressMapper.getExpress(criteria);
		@SuppressWarnings("rawtypes")
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		String keyword = model.getKeyword();
		if (!StringUtil.isBlank(keyword))
			keyword = "%" + keyword + "%";
		else
			keyword = null;
		List<DeliveryOrderEntity> list = userMapper.getDeliveryOrder(keyword, model.getOrgNo(), expressEntities);
		result.put("orders", list);
		result.put("totalNum", page.getTotal());
	}

	@Override
	public HSSFWorkbook exportReport(UserEntity user, SearchVo model) throws CommonException {

		String[] excelHeader = { "所属组织", "物流单号", "承运单位", "物流联系人", "联系人电话", "派车时间", "进厂时间", "出厂时间", "到达时间", "在厂时间",
				"运输时间", "省份", "城市", "客户", "收货人", "收货人电话", "评价语", "物流分数", "包装分数", "服务分数" };
		Criteria criteria = new Criteria();
		if (StringUtil.isBlank(model.getOrganization())) {
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		criteria.put("organization", model.getOrganization());
		if (!StringUtil.isBlank(model.getExpressCompany())) {
			criteria.put("expressCompany", "%" + model.getExpressCompany() + "%");
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
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("物流信息");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			sheet.setAutobreaks(true);
			sheet.setColumnWidth(i, 16 * 256);
		}
		List<ReportEntity> reportList = reportMapper.getReport(criteria);
		List<ReportDetailEntity> allList = new ArrayList<>();
		for (int i = 0; i < reportList.size(); i++) {
			criteria.put("organization", model.getOrganization());
			criteria.put("expressCompany", reportList.get(i).getExpressCompany());
			List<ReportDetailEntity> list = reportMapper.getReportDetail(criteria);
			allList.addAll(list);
		}
		for (int i = 0; i < allList.size(); i++) {
			row = sheet.createRow(i + 1);
			ReportDetailEntity vo = allList.get(i);
			row.createCell(0).setCellValue(model.getOrganization());
			row.createCell(1).setCellValue(vo.getLogisticsNo());
			row.createCell(2).setCellValue(vo.getExpressCompany());
			row.createCell(3).setCellValue(vo.getDriverName());
			row.createCell(4).setCellValue(vo.getDriverPhone());
			row.createCell(5).setCellValue(fomatTime(vo.getDispatchTime()));
			row.createCell(6).setCellValue(fomatTime(vo.getArriveTime()));
			row.createCell(7).setCellValue(fomatTime(vo.getLeaveTime()));
			row.createCell(8).setCellValue(fomatTime(vo.getDeliveryTime()));
			row.createCell(9).setCellValue(getTime(vo.getInFactoryTime()));
			row.createCell(10).setCellValue(getTime(vo.getTransportTime()));
			row.createCell(11).setCellValue(vo.getProvince());
			row.createCell(12).setCellValue(vo.getCity());
			row.createCell(13).setCellValue(vo.getClient());
			row.createCell(14).setCellValue(vo.getReceiver());
			row.createCell(15).setCellValue(vo.getReceiverPhone());
			row.createCell(16).setCellValue(vo.getComment());
			row.createCell(17).setCellValue(vo.getTimelinessScore());
			row.createCell(18).setCellValue(vo.getWholenessScore());
			row.createCell(19).setCellValue(vo.getServeScore());
		}

		return wb;
	}

	private String fomatTime(Long value) {
		if (value == null || value == 0)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(value * 1000);
		return sdf.format(date);
	}

	private String getTime(Long value) {
		if (value == null)
			return "0";
		int s = value.intValue();
		int m = 0;
		int h = 0;
		if (s > 60) {
			m = s / 60;
			s = s % 60;
		}
		if (m > 60) {
			h = m / 60;
			m = h % 60;
		}
		String result = s + "秒";
		if (m > 0)
			result = m + "分" + result;
		if (h > 0)
			result = h + "小时" + result;
		return result;
	}

	@Override
	public HSSFWorkbook exportLogistics(UserEntity user, SearchVo model) throws CommonException {
		Criteria criteria = new Criteria();
		if (!StringUtil.isBlank(model.getLogisticsNo())) {
			criteria.put("logisticsNo", "%" + model.getLogisticsNo() + "%");
		}
		if (!StringUtil.isBlank(model.getDeliveryOrder())) {
			criteria.put("deliveryOrder", "%" + model.getDeliveryOrder() + "%");
		}
		if (!StringUtil.isBlank(model.getExpressCompany())) {
			criteria.put("expressCompany", "%" + model.getExpressCompany() + "%");
		}
		if (!StringUtil.isBlank(model.getClient())) {
			criteria.put("client", "%" + model.getClient() + "%");
		}
		if (!StringUtil.isBlank(model.getAddress())) {
			criteria.put("address", "%" + model.getAddress() + "%");
		}
		if (StringUtil.isBlank(model.getOrganization())) {
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		criteria.put("organization", model.getOrganization());
		if (model.getLogisticsStatus() != null && model.getLogisticsStatus() >= 0) {
			criteria.put("logisticsStatus", model.getLogisticsStatus());
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
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		List<LogisticsVo> logisticsVos = new ArrayList<LogisticsVo>();
		List<ExpressEntity> expressEntities;
		LogisticsVo logisticsVo = null;
		LogisticsEntity entity = null;
		for (int i = 0; i < list.size(); i++) {
			entity = list.get(i);
			expressEntities = entity.getExpressEntity();
			if (expressEntities != null && expressEntities.size() > 0) {
				for (int j = 0; j < expressEntities.size(); j++) {
					logisticsVo = new LogisticsVo();
					logisticsVo.setLogisticsEntity(entity);
					logisticsVo.setExpressEntity(expressEntities.get(j));
					logisticsVos.add(logisticsVo);
				}
			} else {
				logisticsVo = new LogisticsVo();
				logisticsVo.setLogisticsEntity(entity);
				logisticsVos.add(logisticsVo);
			}
		}
		String[] excelHeader = { "所属公司", "物流单号", "承运单位", "物流联系人", "联系人电话", "出库单号", "客户", "收货人", "收货人电话", "收货地址", "业务员",
				"业务员电话", "运输状态", "调度员", "创建时间", "派车时间", "到厂时间", "装载时间", "出厂时间", "到达时间" };
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("物流信息");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		style.setAlignment(HorizontalAlignment.CENTER);

		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			sheet.setAutobreaks(true);
			sheet.setColumnWidth(i, 16 * 256);
		}
		for (int i = 0; i < logisticsVos.size(); i++) {
			row = sheet.createRow(i + 1);
			LogisticsVo vo = logisticsVos.get(i);
			row.createCell(0).setCellValue(vo.getLogisticsEntity().getOrganization());
			row.createCell(1).setCellValue(vo.getLogisticsEntity().getLogisticsNo());
			row.createCell(2).setCellValue(vo.getLogisticsEntity().getExpressCompany());
			row.createCell(3).setCellValue(vo.getLogisticsEntity().getDriverName());
			row.createCell(4).setCellValue(vo.getLogisticsEntity().getDriverPhone());
			row.createCell(5).setCellValue(vo.getExpressEntity().getDeliveryOrder());
			row.createCell(6).setCellValue(vo.getExpressEntity().getClient());
			row.createCell(7).setCellValue(vo.getExpressEntity().getReceiver());
			row.createCell(8).setCellValue(vo.getExpressEntity().getReceiverPhone());
			row.createCell(9).setCellValue(vo.getExpressEntity().getReceiveAddress());
			row.createCell(10).setCellValue(vo.getExpressEntity().getEmployeeName());
			row.createCell(11).setCellValue(vo.getExpressEntity().getEmployeePhone());
			row.createCell(12).setCellValue(getStatus(vo.getLogisticsEntity().getLogisticsStatus()));
			row.createCell(13).setCellValue(vo.getLogisticsEntity().getUserName());
			row.createCell(14).setCellValue(fomatTime(vo.getLogisticsEntity().getCreateTime()));
			row.createCell(15).setCellValue(fomatTime(vo.getLogisticsEntity().getDispatchTime()));
			row.createCell(16).setCellValue(fomatTime(vo.getLogisticsEntity().getArriveTime()));
			row.createCell(17).setCellValue(fomatTime(vo.getLogisticsEntity().getShipmentTime()));
			row.createCell(18).setCellValue(fomatTime(vo.getLogisticsEntity().getLeaveTime()));
			row.createCell(19)
					.setCellValue(vo.getExpressEntity().getStatus() != null && vo.getExpressEntity().getStatus() > 0
							? fomatTime(vo.getExpressEntity().getUpdateTime())
							: "");
		}
		return wb;
	}

	private String getStatus(int statusCode) {
		if (statusCode == 0) {
			return "新建";
		}
		if (statusCode == 1) {
			return "已派车";
		}
		if (statusCode == 2) {
			return "已进厂";
		}
		if (statusCode == 3) {
			return "已装载";
		}
		if (statusCode == 4) {
			return "已出厂";
		}
		if (statusCode == 5) {
			return "已送达";
		}
		if (statusCode == 6) {
			return "已评价";
		}
		return "未知";
	}

	@Override
	@Transactional
	public void getNotificationByUser(UserEntity user, Map<String, Object> result) throws CommonException {
		Criteria criteria = new Criteria();
		List<NotificationEntity> list = new ArrayList<>();
		if (user == null) {
			throw CommonExceptionHelper.commonException("请先登录", null);
		}
		criteria.put("status", 0);
		criteria.put("userId", user.getUserId());
		list.addAll(notificationMapper.getNotification(criteria));
		result.put("notifications", list);
		if (list != null) {
			NotificationEntity update = new NotificationEntity();
			update.setStatus(1);
			update.setUpdateTime(System.currentTimeMillis() / 1000);
			for (int i = 0; i < list.size(); i++) {
				update.setId(list.get(i).getId());
				notificationMapper.updateByEntity(update);
			}
		}

	}

	@Override
	public void getExpressDetail4App(String deliveryOrder, Map<String, Object> result, HttpServletRequest request)
			throws CommonException {
		Criteria criteria = new Criteria();
		criteria.put("deliveryOrder", deliveryOrder);
		List<ExpressEntity> list = expressMapper.getExpress(criteria);
		if (list == null || list.isEmpty())
			throw CommonExceptionHelper.commonException("出库信息不存在", null);
		ExpressEntity expressEntity = list.get(0);
		criteria = new Criteria();
		criteria.put("id", expressEntity.getLogisticsId());
		List<LogisticsEntity> list2 = LogisticsMapper.getLogistics(criteria);
		if (list2 == null || list2.isEmpty()) {
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ "/app4expressDetaill?expressId=" + expressEntity.getId() + "&t=" + expressEntity.getToken();
		result.put("url", url);
	}

	@Override
	public void getEmployee(SearchVo model, Map<String, Object> result) throws CommonException {
		@SuppressWarnings("rawtypes")
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		String keyword = model.getKeyword();
		if (!StringUtil.isBlank(keyword))
			keyword = "%" + keyword + "%";
		else
			keyword = null;
		List<EmployeeEntity> list = userMapper.getEmployee(keyword, model.getOrgNo());
		result.put("employees", list);
		result.put("totalNum", page.getTotal());

	}

	@Override
	public List<DriverAndVehicle> getDriverByUser(UserEntity user, SearchVo model, Map<String, Object> result)
			throws CommonException {
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		String keyword = model.getKeyword();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(model.getOrganization())) {
			criteria.put("organization", model.getOrganization());
		}
		if (!StringUtil.isBlank(keyword)) {
			keyword = "%" + keyword + "%";
			criteria.put("name", keyword);
		} else
			keyword = null;

		List<DriverAndVehicle> list = driverMapper.getDriverAndVehicleList(criteria);
		result.put("driverList", list);
		result.put("totalNum", page.getTotal());
		return list;

	}

	@Override
	public List<DriverAndVehicle> getVehicleByUser(UserEntity user, SearchVo model, Map<String, Object> result)
			throws CommonException {
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		String keyword = model.getKeyword();
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(model.getOrganization())) {
			criteria.put("organization", model.getOrganization());
		}
		if (!StringUtil.isBlank(keyword)) {
			keyword = "%" + keyword + "%";
			criteria.put("plate", keyword);
		} else
			keyword = null;

		List<Vehicle> list = vehicleMapper.getVehicle(criteria);
		result.put("vehicleList", list);
		result.put("totalNum", page.getTotal());// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HSSFWorkbook exportOrderReport(UserEntity user, SearchVo model) throws CommonException {
		String[] excelHeader = { "统计开始时间", "统计结束时间", "所属组织", "发货单数量", "物流单数量", "匹配数量", "有发货单无物流单数量", "有物流单无发货单数量",
				"月完成率", "月完成正确率" };
		Criteria criteria = new Criteria();
//		if (StringUtil.isBlank(model.getOrganization())) {
//			throw CommonExceptionHelper.commonException("请选择所属公司", null);
//		}
//		criteria.put("organization", model.getOrganization());
		if (model.getBeginTime() != null && model.getBeginTime() > 0) {
			criteria.put("beginTime", model.getBeginTime());
		}
		if (model.getEndTime() != null && model.getEndTime() > 0) {
			criteria.put("endTime", model.getEndTime());
		}

		String[] orgs = { "福建回头客食品有限公司", "山东回头客食品有限公司", "四川回头客食品有限公司", "湖北回头客食品有限公司", "吉林回头客食品有限公司", "福建回头客电子商务有限公司" };

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("物流单据报告");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			sheet.setAutobreaks(true);
			sheet.setColumnWidth(i, 20 * 256);
		}
		int  num=0;
		for (String org : orgs) {
			criteria.put("organization", org);
			List<OrderReportEntity> reportList = reportMapper.getOrderReport(criteria);
			for (int i = 0; i < reportList.size(); i++) {
				row = sheet.createRow(num + 1);
				OrderReportEntity vo = reportList.get(i);
				row.createCell(0).setCellValue(fomatTime(model.getBeginTime()));
				row.createCell(1).setCellValue(fomatTime(model.getEndTime()));
				row.createCell(2).setCellValue(org);
				row.createCell(3).setCellValue(vo.getErpQty());
				row.createCell(4).setCellValue(vo.getLogisticsQty());
				row.createCell(5).setCellValue(vo.getMatchQty());
				row.createCell(6).setCellValue(vo.getErpNoMatchLogisticsQty());
				row.createCell(7).setCellValue(vo.getLogisticsNoMatchErpQty());
				row.createCell(8).setCellValue(vo.getFinishRate());
				row.createCell(9).setCellValue(vo.getFinishMatchRate());
			}
			num++;
		}

		return wb;
	}

}
