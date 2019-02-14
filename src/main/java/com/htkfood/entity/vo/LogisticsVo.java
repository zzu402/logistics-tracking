package com.htkfood.entity.vo;

import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.LogisticsEntity;

public class LogisticsVo {
	private LogisticsEntity logisticsEntity;
	private ExpressEntity expressEntity;
	public LogisticsEntity getLogisticsEntity() {
		return logisticsEntity;
	}
	public void setLogisticsEntity(LogisticsEntity logisticsEntity) {
		this.logisticsEntity = logisticsEntity;
	}
	public ExpressEntity getExpressEntity() {
		return expressEntity;
	}
	public void setExpressEntity(ExpressEntity expressEntity) {
		this.expressEntity = expressEntity;
	}
}
