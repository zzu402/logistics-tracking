/**
 * 
 */
package com.htkfood.mapper.slave;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.htkfood.entity.DeliveryOrderEntity;
import com.htkfood.entity.EmployeeEntity;
import com.htkfood.entity.ErpStockInfo;
import com.htkfood.entity.ExpressCompanyEntity;
import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.UserEntity;

/**
 * 
 * 项目名称：logistics-tracking 类名称：UserMapper 类描述： TODO 创建人：Administrator
 * 创建时间：2018年8月8日 下午1:57:27 修改人：Administrator 修改时间：2018年8月8日 下午1:57:27 修改备注：
 * 
 * @version
 *
 */
public interface UserMapper {

	/**
	 * 方法描述：获取erp用户信息 创建人：Administrator 参数：@param userId 参数：@return 返回：UserEntity
	 */
	UserEntity getUserInfoById(String userId);

	List<ExpressCompanyEntity> getExpressCompany(@Param("orgId") String orgId, @Param("keyword") String keyword);

	List<DeliveryOrderEntity> getDeliveryOrder(@Param("keyword") String keyword, @Param("orgId") String orgId,
			@Param("deliveryOrders") List<ExpressEntity> list);
	
	List<EmployeeEntity> getEmployee(@Param("keyword") String keyword, @Param("orgId") String orgId);
	
	ErpStockInfo getStockInfo(@Param("billNo") String billNo );

}
