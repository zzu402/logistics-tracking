package com.htkfood.entity;

/**
 * 出库单信息实体
 * @author Administrator
 *
 */
public class ExpressEntity {
	private Long id;//pk
	private Long logisticsId;//物流信息id
	private String deliveryOrder;//出库单号
	private String client;//客户
	private String receiver;//收货人
	private String receiverPhone;//收货人联系电话
	private String receiveAddress;//收货地址
	private Integer status;//0-运送中 1-运送完成
	private Long createTime;//创建时间戳
	private Long updateTime;//修改时间戳
	private String token;//访问令牌
	private String employeeName;//业务员名称
	private String employeePhone;//业务员电话
	private String  transportNo;//运输单号
	
	
	
	
	public String getTransportNo() {
		return transportNo;
	}
	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeePhone() {
		return employeePhone;
	}
	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(Long logisticsId) {
		this.logisticsId = logisticsId;
	}
	public String getDeliveryOrder() {
		return deliveryOrder;
	}
	public void setDeliveryOrder(String deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	@Override
	public String toString() {
		return "ExpressEntity [id=" + id + ", logisticsId=" + logisticsId + ", deliveryOrder=" + deliveryOrder
				+ ", client=" + client + ", receiver=" + receiver + ", receiverPhone=" + receiverPhone
				+ ", receiveAddress=" + receiveAddress + ", status=" + status + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
