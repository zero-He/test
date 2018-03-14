package cn.strong.leke.scs.dao.master;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.scs.model.Customer;

/**
 * 客户访问类。
 * @author  andy
 * @created 2015年6月16日 上午9:41:23
 * @since   v1.0.0
 */
public interface CustomerMasterDao {

	/**
	 * 保存客户。
	 * @param customer 客户信息
	 * @return
	 */
	public int insertCustomer(Customer customer);

	/**	
	 * 描述:删除客户关联
	 * @author  zhengyiyin
	 * @created 2015年11月30日 下午7:23:45
	 * @since   v1.0.0 
	 */
	public int deleteCustomer(@Param("customerId") Long customerId, @Param("modifiedBy") Long modifiedBy );
	
	/**
	 * 更新客户信息。
	 * @param customer 客户信息
	 * @return
	 */
	public int updateCustomer(Customer customer);

	/**
	 * 更新客户首次消费时间
	 * @param customerId 客户ID
	 * @param consumeTime 首次消费时间
	 * @param modifiedBy 更新人
	 * @return
	 */
	public int updateConsumeTimeByCustomerId(@Param("customerId") Long customerId,
			@Param("consumeTime") Date consumeTime, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 更新客户备注信息
	 * @param customerId 客户ID
	 * @param remark 备注信息
	 * @param modifiedBy 更新人
	 */
	public void updateRemarkByCustomerId(@Param("customerId") Long customerId, @Param("remark") String remark,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 根据乐课ID和类型获取有效客户。
	 * 不返回状态解除关系的客户。
	 * @param lekeId 乐课ID
	 * @param customerType 客户类型
	 * @return
	 */
	public Customer getCustomerByLekeIdAndCustomerType(@Param("lekeId") Long lekeId,
			@Param("customerType") Integer customerType);
	
	public Customer getNearlyDeleteCustomer(@Param("lekeId") Long lekeId);

	/**
	 * 更新绑定状态
	 * @param customerId
	 * @param status
	 * @return
	 */
	public Integer updateCustomerByCustomerID(@Param("customerId") Long customerId, @Param("status") Integer status);
	
	/**
	 * 生效且解绑的记录
	 * @param lekeId
	 * @return
	 */
	public List<Customer> selectNearlyEffectCustomer(@Param("lekeId") Long lekeId);
	
}
