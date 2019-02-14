package com.htkfood.service;
import java.util.List;
import java.util.Map;

import com.htkfood.entity.ProductLinePhotoVo;
import com.htkfood.entity.vo.SearchVo;
import com.htkfood.exception.CommonException;

public interface ProductLinePhotoService {
	
	List<ProductLinePhotoVo> getProductLinePhotoVos(SearchVo model,Map<String, Object>result) throws CommonException;
}
