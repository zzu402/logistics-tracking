package com.htkfood.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htkfood.entity.CommentEntity;
import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.LogisticsEntity;
import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.mapper.Criteria;
import com.htkfood.mapper.slave.CommentMapper;
import com.htkfood.service.CommentService;
import com.htkfood.service.ExpressService;
import com.htkfood.service.LogisticsService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private ExpressService expressService;

	@Override
	@Transactional
	public void insertComment(Long expressId, String t, String comment, Integer score1, Integer score2, Integer score3,
			String imgs) throws CommonException {
		ExpressEntity expressEntity = expressService.queryExpressById(expressId);
		LogisticsEntity logisticsEntity=null;
		// LogisticsEntity logisticsEntity = logisticsService.getLogisticsById(null,
		// expressEntity.getLogisticsId());
		if (t != null && expressEntity.getToken().equals(t)) {
			if (queryCommentByExpressId(expressId) == null) {
				CommentEntity entity = new CommentEntity();
				entity.setExpressId(expressId);
				entity.setComment(comment);
				entity.setTimelinessScore(score1);
				entity.setWholenessScore(score2);
				entity.setServeScore(score3);
				entity.setStatus(0);
				entity.setImgs(imgs);
				entity.setCreateTime(System.currentTimeMillis() / 1000);
				commentMapper.insertByEntity(entity);
				//
				if (expressEntity.getStatus() == 1) {
					ExpressEntity update = new ExpressEntity();
					update.setId(expressId);
					update.setStatus(2);
					update.setUpdateTime(System.currentTimeMillis() / 1000);
					expressService.updateByEntity(update);
				}
				logisticsEntity = logisticsService.getLogisticsById(null, expressEntity.getLogisticsId());
				List<ExpressEntity> entities = logisticsEntity.getExpressEntity();
				int size = entities.size();
				boolean flag = true;// 全部评价标志
				ExpressEntity oldEntity = null;
				for (int i = 0; i < size; i++) {
					oldEntity = entities.get(i);
					if (oldEntity.getStatus() != 2) {
						flag = false;
						break;
					}
				}
				if (flag) {// 全部送达更改物流标志
					logisticsService.updateStatus(logisticsEntity.getId(), 6, null);
				}
			} else {
				throw CommonExceptionHelper.commonException("您已评价，请勿重复评价！", null);
			}
		} else {
			throw CommonExceptionHelper.commonException("物流信息不存在,无法评价", null);
		}
	}

	private CommentEntity queryCommentByExpressId(Long expressId) {
		Criteria criteria = new Criteria();
		criteria.put("expressId", expressId);
		List<CommentEntity> list = commentMapper.getComment(criteria);
		if (list == null || list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	public CommentEntity getComment(Long expressId, String t) throws CommonException {
		ExpressEntity expressEntity = expressService.queryExpressById(expressId);
		// LogisticsEntity logisticsEntity = logisticsService.getLogisticsById(null,
		// expressEntity.getLogisticsId());
		if (t != null && expressEntity.getToken().equals(t)) {
			return queryCommentByExpressId(expressId);
		} else {
			throw CommonExceptionHelper.commonException("物流信息不存在,无法评价", null);
		}
	}

}
