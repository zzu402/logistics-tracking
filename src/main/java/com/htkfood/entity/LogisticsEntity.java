package com.htkfood.entity;

import java.util.List;

/**
 *  物流信息实体
 * @author Administrator
 *
 */
public class LogisticsEntity {
	private Long id;//pk,物流id
	private String userId;//用户id，该物流信息表创建者具有查看权限
	private String userName;//用户名，调度员
	private String organization;//所属组织，因为一个调度员可能所属很多公司，这里要确定这个物流属于哪个组织
	private String logisticsNo;//物流单号
	private String expressCompany;//承运单位
	private String driverName;
	private String driverPhone;
	private Long createTime;//创建时间戳
	private Long updateTime;//修改时间戳
	private Integer logisticsStatus;//物流状态0-新建 1-调度派车 2-到厂 3-装载货物 4-出厂 5-送达 6-客户评价
	private Long dispatchTime;//派车时间
	private Long arriveTime;//到厂时间戳
	private Long shipmentTime;//装载时间
	private Long leaveTime;//出厂时间戳
	private Long  deliveryTime;//送达时间戳
	private String accessToken;//访问令牌
	private String destination;
	private Integer isDelete;//是否删除
	private Integer isUpdate;//是否更新
	private Long driverId;//司机id
	
	private String name;//司机姓名
	private String phone;//司机电话
	
	private String plate;
	private String vehicleType;
	private String owner;
	private String engineNo;
	
	
	
	
	
	
	
	
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	private List<ExpressEntity> expressEntity;

	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public Integer getLogisticsStatus() {
		return logisticsStatus;
	}
	public void setLogisticsStatus(Integer logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}
	public Long getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(Long dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public Long getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Long arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Long getShipmentTime() {
		return shipmentTime;
	}
	public void setShipmentTime(Long shipmentTime) {
		this.shipmentTime = shipmentTime;
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Long getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogisticsNo() {
		return logisticsNo;
	}
	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public List<ExpressEntity> getExpressEntity() {
		return expressEntity;
	}
	public void setExpressEntity(List<ExpressEntity> expressEntity) {
		this.expressEntity = expressEntity;
	}
	@Override
	public String toString() {
		return "LogisticsEntity [id=" + id + ", userId=" + userId + ", userName=" + userName + ", organization="
				+ organization + ", logisticsNo=" + logisticsNo + ", expressCompany=" + expressCompany + ", driverName="
				+ driverName + ", driverPhone=" + driverPhone + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", logisticsStatus=" + logisticsStatus + ", dispatchTime=" + dispatchTime
				+ ", arriveTime=" + arriveTime + ", shipmentTime=" + shipmentTime + ", leaveTime=" + leaveTime
				+ ", deliveryTime=" + deliveryTime + ", accessToken=" + accessToken + ", destination=" + destination
				+ ", expressEntity=" + expressEntity + ", getDestination()=" + getDestination() + ", getUserName()="
				+ getUserName() + ", getAccessToken()=" + getAccessToken() + ", getOrganization()=" + getOrganization()
				+ ", getLogisticsStatus()=" + getLogisticsStatus() + ", getDispatchTime()=" + getDispatchTime()
				+ ", getArriveTime()=" + getArriveTime() + ", getShipmentTime()=" + getShipmentTime()
				+ ", getLeaveTime()=" + getLeaveTime() + ", getDeliveryTime()=" + getDeliveryTime() + ", getUserId()="
				+ getUserId() + ", getId()=" + getId() + ", getLogisticsNo()=" + getLogisticsNo()
				+ ", getExpressCompany()=" + getExpressCompany() + ", getDriverName()=" + getDriverName()
				+ ", getDriverPhone()=" + getDriverPhone() + ", getCreateTime()=" + getCreateTime()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getExpressEntity()=" + getExpressEntity()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
