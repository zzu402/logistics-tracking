package com.htkfood.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.htkfood.entity.ProductLinePhotoVo;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;
import com.htkfood.mapper.Criteria;
import com.htkfood.mapper.slave1.ProductPhotoMapper;
import com.htkfood.service.ProductLinePhotoService;

@Service
public class ProductLinePhotoServiceImpl implements ProductLinePhotoService {

	@Autowired
	private ProductPhotoMapper productLinePhotoMapper;

	private static final String[] company = new String[] { "福建回头客食品有限公司", "湖北回头客食品有限公司", "吉林回头客食品有限公司", "山东回头客食品有限公司",
			"四川回头客食品有限公司" };

	@Override
	public List<ProductLinePhotoVo> getProductLinePhotoVos(SearchVo model,Map<String, Object>result) throws CommonException {
		Criteria criteria = new Criteria();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 这个是你要转成后的时间的格式
		if (model.getBeginTime() != null && model.getBeginTime() > 0) {
			criteria.put("beginDate", sdf.format(new Date(model.getBeginTime() * 1000)));
		}
		if (model.getEndTime() != null && model.getEndTime() > 0) {
			if (model.getBeginTime().longValue() != model.getEndTime().longValue())
				criteria.put("endDate", sdf.format(new Date(model.getEndTime() * 1000)));
		}
		if (StringUtils.isNotBlank(model.getKeyword())) {
			criteria.put("keyword", "%" + model.getKeyword() + "%");
		}
		if (model.getCompanyType() != null && model.getCompanyType() > -1) {
			criteria.put("org", company[model.getCompanyType()]);
		}
		@SuppressWarnings("rawtypes")
		Page page=PageHelper.startPage(model.getCurrentPage().intValue(), model.getPageSize().intValue(), true);
		List<ProductLinePhotoVo> list=productLinePhotoMapper.select(criteria);
		result.put("total", page.getTotal());
		
		return list;
	}

}
