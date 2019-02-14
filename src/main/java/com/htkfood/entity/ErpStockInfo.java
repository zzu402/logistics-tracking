package com.htkfood.entity;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;


public class ErpStockInfo {
	
	private String saler;
	private String carrier;
	private String billNo;
	private String stocker;
	private String startPoint;
	private String endPoint;
	private Double transportationCost;
	private String receiver;
	private String receivePhone;
	private String receiveAddress;
	private String driver;
	private String driverPhone;
	private String payMethod;
	private String wan;
	private String qian;
	private String bai;
	private String shi;
	private String ge;
	private String jiao;
	private String fen;
	private Double cub;
	private Double saleQty;
	private Double otherQty;
	private Double unitPrice;
	private String idCard;
	private String vehicleType;
	private String plate;
	private String engineNo;
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public Double getCub() {
		return cub;
	}
	public void setCub(Double cub) {
		this.cub = cub;
	}
	public Double getSaleQty() {
		return saleQty;
	}
	public void setSaleQty(Double saleQty) {
		this.saleQty = saleQty;
	}
	public Double getOtherQty() {
		return otherQty;
	}
	public void setOtherQty(Double otherQty) {
		this.otherQty = otherQty;
	}
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getStocker() {
		return stocker;
	}
	public void setStocker(String stocker) {
		this.stocker = stocker;
	}
	public String getStartPoint() {
		if(StringUtils.isNotBlank(stocker)) {
			if(stocker.contains("福建"))
				startPoint="惠安";
			else if(stocker.contains("山东"))
				startPoint="临沂";
			else if(stocker.contains("四川"))
				startPoint="成都/新繁";
			else if(stocker.contains("湖北"))
				startPoint="汉川";
			else if(stocker.contains("吉林"))
				startPoint="长春/德惠";
		}
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public Double getTransportationCost() {
		BigDecimal numberOfMoney=new BigDecimal(transportationCost);
		numberOfMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		return numberOfMoney.doubleValue();
	}
	public void setTransportationCost(Double transportationCost) {
		this.transportationCost = transportationCost;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getWan() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money/10000)];
		return wan;
	}
	public void setWan(String wan) {
		this.wan = wan;
	}
	public String getQian() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money/1000%10)];
		return qian;
	}
	public void setQian(String qian) {
		this.qian = qian;
	}
	public String getBai() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money/100%10)];
		return bai;
	}
	public void setBai(String bai) {
		this.bai = bai;
	}
	public String getShi() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money/10%10)];
		return shi;
	}
	public void setShi(String shi) {
		this.shi = shi;
	}
	public String getGe() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money%10)];
		return ge;
	}
	public void setGe(String ge) {
		this.ge = ge;
	}
	public String getJiao() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money/0.1%10)];
		return jiao;
	}
	public void setJiao(String jiao) {
		this.jiao = jiao;
	}
	public String getFen() {
		Double money=this.getTransportationCost();
		if(money!=null&&money<100000)
			return CN_UPPER_NUMBER[(int) (money/0.01%10)];
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}

    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
}
