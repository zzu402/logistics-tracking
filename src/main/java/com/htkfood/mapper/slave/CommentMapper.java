package com.htkfood.mapper.slave;

import java.util.List;

import com.htkfood.entity.CommentEntity;
import com.htkfood.mapper.Criteria;

/**
 * 关于Comment表的操作，包括增删改查
 * @author Administrator
 */
public interface CommentMapper {
	/**
	 * 获取Logistics
	 * @return
	 */
	List<CommentEntity>  getComment(Criteria criteria);
	/*
	 * 添加
	 */
	int insertByEntity(CommentEntity commentEntity);
	/*
	 * 更新
	 */
	int updateByEntity(CommentEntity commentEntity);
	/**
	 * 刪除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	


}
