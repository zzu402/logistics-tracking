package com.htkfood.entity;

/**
 * 用于获取ERP出库信息实体
 * @author Administrator
 *
 */
public class DeliveryOrderEntity {
	
	private String deliveryOrder;//出库单号
	private String client;//客户
	private String clientId;//客户Id
	private String receiver;//收货人
	private String receiverPhone;//收货人联系电话
	private String receiveAddress;//收货地址
	
	private String fnumber;//业务员编号
	private String fname;//业务员名称
	private String fphone;//业务员手机号码
	
	private String transportNo;
	
	private Integer status;//0-代表没发货，1-代表已发货
	
	
	
	
	
	public String getTransportNo() {
		return transportNo;
	}
	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFphone() {
		return fphone;
	}
	public void setFphone(String fphone) {
		this.fphone = fphone;
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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getFnumber() {
		return fnumber;
	}
	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	@Override
	public String toString() {
		return "DeliveryOrderEntity [deliveryOrder=" + deliveryOrder + ", client=" + client + ", clientId=" + clientId
				+ ", receiver=" + receiver + ", receiverPhone=" + receiverPhone + ", receiveAddress=" + receiveAddress
				+ ", fnumber=" + fnumber + ", fname=" + fname + "]";
	}
	
	

}
