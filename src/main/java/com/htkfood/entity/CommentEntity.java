package com.htkfood.entity;
/**
 * 评论实体
 * @author Administrator
 *
 */
public class CommentEntity {
	
	private Long id;//pk
	private Long expressId;//出库单id
	private String  comment;//评价
	private Integer timelinessScore;//时效性评分
	private Integer wholenessScore;//完整性评分
	private Integer serveScore;//服务态度评分
	private Integer status;//0-正常 1-表示异常
	private Long createTime;//创建时间，精确到秒
	private String imgs;//图片路径，用,分割
	
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getExpressId() {
		return expressId;
	}
	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getTimelinessScore() {
		return timelinessScore;
	}
	public void setTimelinessScore(Integer timelinessScore) {
		this.timelinessScore = timelinessScore;
	}
	public Integer getWholenessScore() {
		return wholenessScore;
	}
	public void setWholenessScore(Integer wholenessScore) {
		this.wholenessScore = wholenessScore;
	}
	public Integer getServeScore() {
		return serveScore;
	}
	public void setServeScore(Integer serveScore) {
		this.serveScore = serveScore;
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
	
}
