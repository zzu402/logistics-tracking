package com.htkfood.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.htkfood.entity.Driver;
import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.entity.ErpStockInfo;
import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.LogisticsEntity;
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
import com.htkfood.mapper.slave.UserMapper;
import com.htkfood.service.DriverService;
import com.htkfood.service.ExpressService;
import com.htkfood.service.LogisticsService;
import com.htkfood.service.SmsService;
import com.htkfood.service.VehicleService;

@Service
public class LogisticsServiceImpl implements LogisticsService {
	private Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	@Autowired
	private LogisticsMapper LogisticsMapper;
	@Autowired
	private ExpressService expressService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private ExpressMapper expressMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleService vehicleService;

	@SuppressWarnings("rawtypes")
	@Override
	public void getLogisticsList(UserEntity user, SearchVo model, Map<String, Object> result) throws CommonException {
		Criteria criteria = new Criteria();
		if (!StringUtil.isEmpty(model.getLogisticsNo())) {
			criteria.put("logisticsNo", "%" + model.getLogisticsNo() + "%");
		}
		if (!StringUtil.isEmpty(model.getDeliveryOrder())) {
			criteria.put("deliveryOrder", "%" + model.getDeliveryOrder() + "%");
		}
		if (!StringUtil.isEmpty(model.getExpressCompany())) {
			criteria.put("expressCompany", "%" + model.getExpressCompany() + "%");
		}
		if (!StringUtil.isEmpty(model.getClient())) {
			criteria.put("client", "%" + model.getClient() + "%");
		}
		if (!StringUtil.isEmpty(model.getAddress())) {
			criteria.put("address", "%" + model.getAddress() + "%");
		}
		if (model.getLogisticsStatus() != null && model.getLogisticsStatus() > -1) {
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
		if (StringUtil.isEmpty(model.getOrganization())) {
			logger.error(String.format("用户[%s] 未选择所属公司，无法查询物流信息", user == null ? "" : user.getUserId()));
			throw CommonExceptionHelper.commonException("请选择所属公司", null);
		}
		criteria.put("organization", model.getOrganization());
		Page page = PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
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
		result.put("logisticsList", logisticsVos);
		result.put("totalNum", page.getTotal());
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public void addLogistics(UserEntity user, String logisticsNo, String organization, String expressCompany,
			String driverName, String driverPhone, String destination) throws CommonException {
		if (StringUtil.isEmpty(organization)) {
			throw CommonExceptionHelper.commonException("所属组织不能为空", null);
		}
		if (StringUtil.isEmpty(expressCompany)) {
			throw CommonExceptionHelper.commonException("物流公司不能为空", null);
		}
		if (StringUtil.isEmpty(driverName)) {
			throw CommonExceptionHelper.commonException("司机姓名不能为空", null);
		}
		if (StringUtil.isEmpty(driverPhone)) {
			throw CommonExceptionHelper.commonException("司机电话不能为空", null);
		}
		if (StringUtil.isEmpty(destination)) {
			throw CommonExceptionHelper.commonException("送货地点不能为空", null);
		}
		LogisticsEntity logisticsEntity = new LogisticsEntity();
		if (StringUtil.isEmpty(logisticsNo)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String date = df.format(new Date());
			logisticsNo = date;
		}
		logisticsEntity.setUserId(user.getUserId());
		logisticsEntity.setUserName(user.getName());
		logisticsEntity.setDestination(destination);
		logisticsEntity.setLogisticsNo(logisticsNo);
		logisticsEntity.setOrganization(organization);
		logisticsEntity.setExpressCompany(expressCompany);
		logisticsEntity.setDriverName(driverName);
		logisticsEntity.setDriverPhone(driverPhone);
		logisticsEntity.setLogisticsStatus(0);// 0-新建
		logisticsEntity.setCreateTime(System.currentTimeMillis() / 1000);
		logisticsEntity.setUpdateTime(System.currentTimeMillis() / 1000);
		logisticsEntity.setAccessToken(com.htkfood.util.StringUtil.simpleUUID().substring(0, 6));
		logisticsEntity.setIsDelete(0);
		logisticsEntity.setIsUpdate(0);
		Criteria criteria = new Criteria();
		criteria.put("logisticsNo", logisticsNo);
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		if (list != null && !list.isEmpty()) {
			logger.error(String.format("物流单号[%s] 重复，无法添加物流信息", logisticsNo));
			throw CommonExceptionHelper.commonException("物流单号不能重复", null);
		}
		
		/*criteria.clear();
		criteria.put("organization", logisticsEntity.getOrganization());
		criteria.put("name", logisticsEntity.getDriverName());
		criteria.put("phone", logisticsEntity.getDriverPhone());
		List<Driver> list2=driverService.getDriver(criteria);
		if(list2==null||list2.isEmpty()) {
			throw CommonExceptionHelper.cacheException("请先在基础管理添加司机信息，然后再选择该司机", null);
		}*/
		LogisticsMapper.insertByEntity(logisticsEntity);
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public void updateLogistics(Long logisticsId, String logisticsNo, String organization, String expressCompany,
			String driverName, String driverPhone, String destination) throws CommonException {
		if (StringUtil.isEmpty(organization)) {
			throw CommonExceptionHelper.commonException("所属组织不能为空", null);
		}
		if (StringUtil.isEmpty(expressCompany)) {
			throw CommonExceptionHelper.commonException("物流公司不能为空", null);
		}
		if (StringUtil.isEmpty(driverName)) {
			throw CommonExceptionHelper.commonException("司机姓名不能为空", null);
		}
		if (StringUtil.isEmpty(driverPhone)) {
			throw CommonExceptionHelper.commonException("司机电话不能为空", null);
		}
		if (StringUtil.isEmpty(destination)) {
			throw CommonExceptionHelper.commonException("送货地点不能为空", null);
		}

		LogisticsEntity oldLogistics = getLogisticsById(logisticsId);
		if (oldLogistics == null) {
			logger.error(String.format("物流Id[%s] 对应物流信息不存在，无法修改物流信息", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不存在，无法修改", null);
		}
		if (oldLogistics.getLogisticsStatus() != 0) {
			logger.error(String.format("物流Id[%s] 不是新建状态，无法修改物流信息", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不是新建状态，无法修改", null);
		}

		LogisticsEntity logisticsEntity = new LogisticsEntity();
		if (!StringUtil.isEmpty(logisticsNo)) {
			logisticsEntity.setLogisticsNo(logisticsNo);
			Criteria criteria = new Criteria();
			criteria.put("logisticsNo", logisticsNo);
			List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
			LogisticsEntity oldEntity = null;
			if (list != null && !list.isEmpty())
				oldEntity = list.get(0);
			if (oldEntity != null && oldEntity.getId().longValue() != logisticsId.longValue()) {
				logger.error(String.format("物流单号[%s] 重复，无法修改物流信息", logisticsNo));
				throw CommonExceptionHelper.commonException("物流单号不能重复", null);
			}
		}
		logisticsEntity.setOrganization(organization);
		logisticsEntity.setExpressCompany(expressCompany);
		logisticsEntity.setDriverName(driverName);
		logisticsEntity.setDriverPhone(driverPhone);
		logisticsEntity.setId(logisticsId);
		logisticsEntity.setDestination(destination);
		logisticsEntity.setUpdateTime(System.currentTimeMillis() / 1000);
		LogisticsMapper.updateByEntity(logisticsEntity);
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public void deleteLogistics(Long logisticsId) throws CommonException {
		if (logisticsId == null) {
			logger.error(String.format("物流ID[%s] 为空,无法删除", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息出错", null);
		}
		LogisticsEntity logisticsEntity = getLogisticsById(logisticsId);
		if (logisticsEntity == null) {
			throw CommonExceptionHelper.commonException("物流信息不存在，无法删除", null);
		}
		if (logisticsEntity.getLogisticsStatus() != 0) {
			throw CommonExceptionHelper.commonException("已经派车的物流无法删除", null);
		}
		LogisticsMapper.deleteById(logisticsId);
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public String dispatchCar(Long logisticsId, HttpServletRequest request) throws CommonException {
		if (logisticsId == null) {
			logger.error(String.format("物流ID[%s] 为空,无法派车", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息出错", null);
		}
		LogisticsEntity logisticsEntity = getLogisticsById(logisticsId);
		if (logisticsEntity == null) {
			logger.error(String.format("物流ID[%s] 对应物流信息不存在,无法派车", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不存在，无法派车", null);
		}
		if (logisticsEntity.getLogisticsStatus() != 0) {
			logger.error(String.format("物流ID[%s]对应状态不是新建状态,无法派车", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不是新建状态，无法派车", null);
		}
		LogisticsEntity updateLogistics = new LogisticsEntity();
		updateLogistics.setId(logisticsId);
		updateLogistics.setLogisticsStatus(1);
		updateLogistics.setDispatchTime(System.currentTimeMillis() / 1000);
		updateLogistics.setUpdateTime(System.currentTimeMillis() / 1000);
		LogisticsMapper.updateByEntity(updateLogistics);
		String smsResult = smsService.sendSms2Driver(logisticsEntity);
		String info = String.format("物流单号:%s 派车，短信发送结果：%s", logisticsEntity.getLogisticsNo(), smsResult);
		logger.info(info);
		return smsResult;
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public void updateStatus(Long logisticsId, Integer status, Long expressId) throws CommonException {
		if (logisticsId == null) {
			logger.error(String.format("物流ID[%s] 为空,无法更新状态", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息出错", null);
		}
		LogisticsEntity logisticsEntity = getLogisticsById(logisticsId);
		if (logisticsEntity == null) {
			logger.error(String.format("物流ID[%s]对应物流不存在，无法更新状态", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}
		LogisticsEntity updateLogistics = new LogisticsEntity();
		updateLogistics.setId(logisticsId);
		if (status != 5 || expressId == null)
			// 扫码时候expressId会为空，这时候调用这里方法表示全部送达
			updateLogistics.setLogisticsStatus(status);
		if (status == 2) {
			updateLogistics.setArriveTime(System.currentTimeMillis() / 1000);
		}
		if (status == 4) {
			updateLogistics.setLeaveTime(System.currentTimeMillis() / 1000);
		}
		if (status == 5 && expressId == null) {
			updateLogistics.setLogisticsStatus(status);
			updateLogistics.setDeliveryTime(System.currentTimeMillis() / 1000);
		} else if (status == 5 && expressId != null) {
			ExpressEntity expressEntity = expressService.queryExpressById(expressId);
			expressEntity.setStatus(1);
			expressEntity.setUpdateTime(System.currentTimeMillis() / 1000);
			expressService.updateByEntity(expressEntity);
			LogisticsEntity logistics = getLogisticsById(logisticsId);
			boolean flag = true;
			List<ExpressEntity> entities = logistics.getExpressEntity();
			int size = entities.size();
			for (int i = 0; i < size; i++) {
				expressEntity = entities.get(i);
				if (expressEntity.getStatus() == 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				updateLogistics.setDeliveryTime(System.currentTimeMillis() / 1000);
				updateLogistics.setLogisticsStatus(status);
			}
		}
		if (status == 3) {
			updateLogistics.setShipmentTime(System.currentTimeMillis() / 1000);
		}
		updateLogistics.setUpdateTime(System.currentTimeMillis() / 1000);
		LogisticsMapper.updateByEntity(updateLogistics);

	}

	private LogisticsEntity getLogisticsById(Long id) {
		Criteria criteria = new Criteria();
		criteria.put("id", id);
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		if (list == null || list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	public void getLogisticsByNo(UserEntity user, String logisticsNo, Map<String, Object> result)
			throws CommonException {
		Criteria criteria = new Criteria();
		criteria.put("logisticsNo", logisticsNo);
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		if (list == null || list.isEmpty()) {
			logger.error(String.format("物流单号[%s] 对应信息为空,无法获取信息", logisticsNo));
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}
		result.put("logistics", list.get(0));
	}

	@Override
	public LogisticsEntity getLogisticsByNo(UserEntity user, String logisticsNo) throws CommonException {
		Criteria criteria = new Criteria();
		criteria.put("logisticsNo", logisticsNo);
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		if (list == null || list.isEmpty()) {
			logger.error(String.format("物流单号[%s] 对应信息为空,无法获取信息", logisticsNo));
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}
		return list.get(0);
	}

	@Override
	public void queryLogistics(UserEntity user, String logisticsNo, String t, Map<String, Object> result)
			throws CommonException {
		Criteria criteria = new Criteria();
		criteria.put("logisticsNo", logisticsNo);
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		if (list == null || list.isEmpty()) {
			logger.error(String.format("物流单号[%s] 对应信息为空,无法获取信息", logisticsNo));
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}
		LogisticsEntity logisticsEntity = list.get(0);
		if (t != null && logisticsEntity.getAccessToken().equals(t))
			result.put("logistics", logisticsEntity);
		else {
			logger.error(String.format("物流单号[%s] 和token[%s] 不匹配", logisticsNo, t));
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}

	}

	@Override
	public LogisticsEntity getLogisticsById(UserEntity user, Long logisticsId) throws CommonException {
		Criteria criteria = new Criteria();
		criteria.put("id", logisticsId);
		List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
		if (list == null || list.isEmpty()) {
			logger.error(String.format("物流Id[%s] 对应信息为空,无法获取信息", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不存在", null);
		}
		return list.get(0);
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public void deleteLogistics2(Long logisticsId, Long expressId) throws CommonException {
		if (logisticsId == null) {
			logger.error(String.format("物流ID[%s] 为空,无法删除", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息出错", null);
		}
		LogisticsEntity logisticsEntity = getLogisticsById(logisticsId);
		if (logisticsEntity == null) {
			throw CommonExceptionHelper.commonException("物流信息不存在，无法删除", null);
		}
		LogisticsEntity updateLogistics = new LogisticsEntity();
		updateLogistics.setId(logisticsId);
		updateLogistics.setUpdateTime(System.currentTimeMillis() / 1000);
		Criteria criteria = new Criteria();
		criteria.put("logisticsId", logisticsId);
		List<ExpressEntity> list = expressMapper.getExpress(criteria);
		if (list != null && list.size() == 1) {
			updateLogistics.setIsDelete(1);
		} else if (list == null || list.isEmpty()) {
			updateLogistics.setIsDelete(1);
		}
		// 直接删除运单信息
		if (expressId != null && expressId.longValue() > 0) {
			expressMapper.deleteById(expressId);
		}
		LogisticsMapper.updateByEntity(updateLogistics);
	}

	@Override
	@Transactional(rollbackFor = { CommonException.class, RuntimeException.class, Error.class })
	public void updateLogistics2(Long logisticsId, String logisticsNo, String organization, String expressCompany,
			String driverName, String driverPhone, String destination, String expressId, String deliveryOrder,
			String client, String receiver, String receiverPhone, String receiveAddress, String employeeName,
			String employeePhone) throws CommonException {
		if (StringUtil.isEmpty(organization)) {
			throw CommonExceptionHelper.commonException("所属组织不能为空", null);
		}
		if (StringUtil.isEmpty(expressCompany)) {
			throw CommonExceptionHelper.commonException("物流公司不能为空", null);
		}
		if (StringUtil.isEmpty(driverName)) {
			throw CommonExceptionHelper.commonException("司机姓名不能为空", null);
		}
		if (StringUtil.isEmpty(driverPhone)) {
			throw CommonExceptionHelper.commonException("司机电话不能为空", null);
		}
		if (StringUtil.isEmpty(destination)) {
			throw CommonExceptionHelper.commonException("送货地点不能为空", null);
		}

		LogisticsEntity oldLogistics = getLogisticsById(logisticsId);
		if (oldLogistics == null) {
			logger.error(String.format("物流Id[%s] 对应物流信息不存在，无法修改物流信息", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不存在，无法修改", null);
		}
		LogisticsEntity logisticsEntity = new LogisticsEntity();
		if (!StringUtil.isEmpty(logisticsNo)) {
			logisticsEntity.setLogisticsNo(logisticsNo);
			Criteria criteria = new Criteria();
			criteria.put("logisticsNo", logisticsNo);
			List<LogisticsEntity> list = LogisticsMapper.getLogistics(criteria);
			LogisticsEntity oldEntity = null;
			if (list != null && !list.isEmpty())
				oldEntity = list.get(0);
			if (oldEntity != null && oldEntity.getId().longValue() != logisticsId.longValue()) {
				logger.error(String.format("物流单号[%s] 重复，无法修改物流信息", logisticsNo));
				throw CommonExceptionHelper.commonException("物流单号不能重复", null);
			}
		}
		logisticsEntity.setOrganization(organization);
		logisticsEntity.setExpressCompany(expressCompany);
		logisticsEntity.setDriverName(driverName);
		logisticsEntity.setDriverPhone(driverPhone);
		logisticsEntity.setId(logisticsId);
		logisticsEntity.setDestination(destination);
		logisticsEntity.setUpdateTime(System.currentTimeMillis() / 1000);
		logisticsEntity.setIsUpdate(1);
		if (StringUtil.isNotEmpty(expressId)) {
			//
			if (StringUtil.isEmpty(deliveryOrder)) {
				throw CommonExceptionHelper.commonException("出库单号不能为空", null);
			}
			if (StringUtil.isEmpty(receiver)) {
				throw CommonExceptionHelper.commonException("收货人不能为空", null);
			}
			if (StringUtil.isEmpty(receiveAddress)) {
				throw CommonExceptionHelper.commonException("收货地址不能为空", null);
			}
			if (StringUtil.isEmpty(receiverPhone)) {
				throw CommonExceptionHelper.commonException("收货电话不能为空", null);
			}
			if (StringUtil.isEmpty(client)) {
				throw CommonExceptionHelper.commonException("客户不能为空", null);
			}
			if (StringUtil.isEmpty(employeeName)) {
				throw CommonExceptionHelper.commonException("业务员不能为空", null);
			}
			if (StringUtil.isEmpty(employeePhone)) {
				throw CommonExceptionHelper.commonException("业务员电话不能为空", null);
			}
			Criteria criteria = new Criteria();
			criteria.put("deliveryOrder", deliveryOrder);
			List<ExpressEntity> old = expressMapper.getExpress(criteria);
			if (old != null && !old.isEmpty()) {
				ExpressEntity oldEntity = old.get(0);
				if (!String.valueOf(oldEntity.getId()).equals(expressId))
					throw CommonExceptionHelper.commonException("出库单号不能重复", null);
			}
			// 更新
			ExpressEntity expressEntity = new ExpressEntity();
			expressEntity.setId(Long.parseLong(expressId));
			expressEntity.setDeliveryOrder(deliveryOrder);
			expressEntity.setClient(client);
			expressEntity.setReceiver(receiver);
			expressEntity.setReceiverPhone(receiverPhone);
			expressEntity.setReceiveAddress(receiveAddress);
			expressEntity.setEmployeeName(employeeName);
			expressEntity.setEmployeePhone(employeePhone);
			expressMapper.updateByEntity(expressEntity);
		}
		LogisticsMapper.updateByEntity(logisticsEntity);

	}

	@Override
	public void getErpStockInfo(String billNo, Map<String, Object> result) throws CommonException {
		if (StringUtils.isBlank(billNo)) {
			throw CommonExceptionHelper.commonException("订单编号不存在", null);
		}
		ErpStockInfo stockInfo = userMapper.getStockInfo(billNo);
		if (stockInfo == null)
			throw CommonExceptionHelper.commonException("ERP不存在该销售出库单", null);
		Criteria criteria=new Criteria();
		criteria.put("billNo", billNo);
		List<DriverAndVehicle> list=driverService.getDriverAndVehicleByBillNo(criteria);
		if(list!=null&&!list.isEmpty()) {
			DriverAndVehicle d=list.get(0);
			stockInfo.setIdCard(d.getIdCard());
			stockInfo.setPlate(d.getPlate());
			stockInfo.setEngineNo(d.getEngineNo());
			stockInfo.setVehicleType(d.getVehicleType());
			stockInfo.setDriver(d.getName());
		}
		
		result.put("stockInfo", stockInfo);
	}

	@Override
	public void editDriver(Long logisticsId, Long driverId,String plate,String engineNo,String vehicleType,HttpServletRequest request) throws CommonException {
		if(logisticsId==null||logisticsId<1||driverId==null||driverId<1) {
			throw CommonExceptionHelper.commonException("参数异常", null);
		}
		LogisticsEntity oldLogistics = getLogisticsById(logisticsId);
		if (oldLogistics == null) {
			logger.error(String.format("物流Id[%s] 对应物流信息不存在，无法修改物流信息", logisticsId));
			throw CommonExceptionHelper.commonException("物流信息不存在，无法修改", null);
		}
		Criteria criteria =new Criteria();
		criteria.put("plate", plate);
		List<Vehicle> list=vehicleService.getVehicle(criteria );
		if(list==null||list.isEmpty()) {
			String org=(String) request.getSession().getAttribute("queryOrg");
			Vehicle vehicle = new Vehicle();
			vehicle.setCreateTime(System.currentTimeMillis()/1000L);
			vehicle.setEngineNo(engineNo);
			vehicle.setPlate(plate);
			vehicle.setVehicleType(vehicleType);
			vehicle.setStatus(1);
			vehicle.setOrganization(org);
			vehicleService.insertAndReturnId(vehicle);
		}
		LogisticsEntity logisticsEntity = new LogisticsEntity();
		logisticsEntity.setId(logisticsId);
		logisticsEntity.setDriverId(driverId);
		logisticsEntity.setPlate(plate);
		logisticsEntity.setEngineNo(engineNo);
		logisticsEntity.setVehicleType(vehicleType);
		//logisticsEntity.setOwner(vehicle.getOwner());
		LogisticsMapper.updateByEntity(logisticsEntity);
	}
}
