package com.htkfood.entity.vo;
/**
 * 查询条件
 * @author Administrator
 *
 */
public class SearchVo {
	
	private Long currentPage;
	private Long pageSize;
	private String logisticsNo;
	private String expressCompany;
	private String deliveryOrder;
	private Integer logisticsStatus;
	private String orgNo;
	private String keyword;
	private String organization;
	private String client;
	private String address;
	private Integer timeType;
	private Long beginTime;
	private Long endTime;
	private Integer companyType;
	private String name;
	private String plate;
	private String phone;
	private String index;
	
	
	

	
	
	
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "SearchVo [currentPage=" + currentPage + ", pageSize=" + pageSize + ", logisticsNo=" + logisticsNo
				+ ", expressCompany=" + expressCompany + ", deliveryOrder=" + deliveryOrder + ", logisticsStatus="
				+ logisticsStatus + ", orgNo=" + orgNo + ", keyword=" + keyword + ", organization=" + organization
				+ ", client=" + client + ", address=" + address + ", timeType=" + timeType + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", companyType=" + companyType + ", getCompanyType()=" + getCompanyType()
				+ ", getTimeType()=" + getTimeType() + ", getBeginTime()=" + getBeginTime() + ", getEndTime()="
				+ getEndTime() + ", getClient()=" + getClient() + ", getAddress()=" + getAddress()
				+ ", getOrganization()=" + getOrganization() + ", getOrgNo()=" + getOrgNo() + ", getKeyword()="
				+ getKeyword() + ", getCurrentPage()=" + getCurrentPage() + ", getPageSize()=" + getPageSize()
				+ ", getLogisticsNo()=" + getLogisticsNo() + ", getExpressCompany()=" + getExpressCompany()
				+ ", getDeliveryOrder()=" + getDeliveryOrder() + ", getLogisticsStatus()=" + getLogisticsStatus()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public Integer getCompanyType() {
		return companyType;
	}
	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	public Integer getTimeType() {
		return timeType;
	}
	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
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
	public String getDeliveryOrder() {
		return deliveryOrder;
	}
	public void setDeliveryOrder(String deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}
	public Integer getLogisticsStatus() {
		return logisticsStatus;
	}
	public void setLogisticsStatus(Integer logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}
	
	
	
	

}
