package com.htkfood.service;
import com.htkfood.entity.CommentEntity;
import com.htkfood.exception.CommonException;

public interface CommentService {
	/**
	 * 添加评价
	 * @param entity
	 */
	void insertComment(Long expressId,String t,String comment,Integer score1,Integer score2,Integer score3,String imgs) throws CommonException;
	
	CommentEntity getComment(Long expressId,String t) throws CommonException;
}
