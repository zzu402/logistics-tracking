package com.htkfood.entity;
/**
 * 统计参数放在这里
 * @author Administrator
 *
 */
public class OrderReportEntity {
	
	private String org;
	private String  erpQty;
	private String logisticsQty;
	private String matchQty;
	private String erpNoMatchLogisticsQty;
	private String logisticsNoMatchErpQty;
	private String finishRate;
	private String finishMatchRate;
	
	
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getErpQty() {
		return erpQty;
	}
	public void setErpQty(String erpQty) {
		this.erpQty = erpQty;
	}
	public String getLogisticsQty() {
		return logisticsQty;
	}
	public void setLogisticsQty(String logisticsQty) {
		this.logisticsQty = logisticsQty;
	}
	public String getMatchQty() {
		return matchQty;
	}
	public void setMatchQty(String matchQty) {
		this.matchQty = matchQty;
	}
	public String getErpNoMatchLogisticsQty() {
		return erpNoMatchLogisticsQty;
	}
	public void setErpNoMatchLogisticsQty(String erpNoMatchLogisticsQty) {
		this.erpNoMatchLogisticsQty = erpNoMatchLogisticsQty;
	}
	public String getLogisticsNoMatchErpQty() {
		return logisticsNoMatchErpQty;
	}
	public void setLogisticsNoMatchErpQty(String logisticsNoMatchErpQty) {
		this.logisticsNoMatchErpQty = logisticsNoMatchErpQty;
	}
	public String getFinishRate() {
		return finishRate;
	}
	public void setFinishRate(String finishRate) {
		this.finishRate = finishRate;
	}
	public String getFinishMatchRate() {
		return finishMatchRate;
	}
	public void setFinishMatchRate(String finishMatchRate) {
		this.finishMatchRate = finishMatchRate;
	}
	
	

}
