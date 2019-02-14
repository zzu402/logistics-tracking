package com.htkfood.mapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.htkfood.util.TypeCaseHelper;


public class Criteria {
	private String notNull;
	private String isNull;
	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	private Map<String, Object> condition;
	protected boolean distinct;
	protected String orderByClause;
	private Integer MySqlStart;

	private Integer MySqlEnd;

	protected Criteria(Criteria example) {
		this.orderByClause = example.orderByClause;
		this.condition = example.condition;
		this.distinct = example.distinct;
		this.MySqlStart = example.MySqlStart;
		this.MySqlEnd = example.MySqlEnd;
		this.isNull = example.isNull;
		this.notNull = example.notNull;
	}

	public Criteria() {
		condition = new HashMap<String, Object>();
	}
	public void clear() {
		this.condition.clear();
		this.orderByClause = null;
		this.distinct = false;
		this.MySqlStart = null;
		this.MySqlEnd = null;
		this.isNull = null;
		this.notNull = null;
	}
	public Criteria put(String condition, Object value) {
		this.condition.put(condition, value);
		return (Criteria) this;
	}
	public Object get(String key) {
		return this.condition.get(key);
	}


	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}
	public Map<String, Object> getCondition() {
		return condition;
	}
	public void setMySqlStart(Integer MySqlStart) {
		this.MySqlStart = MySqlStart;
	}

	public void setMySqlEnd(Integer MySqlEnd) {
		this.MySqlEnd = MySqlEnd;
	}
	public BigDecimal getAsBigDecimal(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
		if (obj != null)
			return (BigDecimal) obj;
		else
			return null;
	}
	public Date getAsDate(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
		if (obj != null)
			return (Date) obj;
		else
			return null;
	}
	public Integer getAsInteger(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
		if (obj != null)
			return (Integer) obj;
		else
			return null;
	}
	public Long getAsLong(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Long", null);
		if (obj != null)
			return (Long) obj;
		else
			return null;
	}


	public String getAsString(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null)
			return (String) obj;
		else
			return "";
	}
	public Timestamp getAsTimestamp(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null)
			return (Timestamp) obj;
		else
			return null;
	}
	public Boolean getAsBoolean(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Boolean", null);
		if (obj != null)
			return (Boolean) obj;
		else
			return null;
	}
}