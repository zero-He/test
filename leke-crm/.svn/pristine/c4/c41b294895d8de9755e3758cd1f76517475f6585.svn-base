package cn.strong.leke.scs.dao.slave;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.Slave;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.CustomerDTO;
import cn.strong.leke.scs.model.CustomerVO;
import cn.strong.leke.scs.model.query.CustomerQuery;

@Slave
public interface CustomerDao {

	/**
	 * 根据客户ID，获取客户信息
	 * @param customerId 客户ID
	 * @return
	 */
	public Customer getCustomerByCustomerId(Long customerId);

	/**
	 * 根据乐课ID和类型获取有效客户。
	 * 不返回状态解除关系的客户。
	 * @param lekeId 乐课ID
	 * @param customerType 客户类型
	 * @return
	 */
	public Customer getCustomerByLekeIdAndCustomerType(@Param("lekeId") Long lekeId,
			@Param("customerType") Integer customerType);
	
	/**
	 * 根据乐课ID和类型获取有效客户。
	 * 返回所有状态。
	 * @param lekeId 乐课ID
	 * @param customerType 客户类型
	 * @return
	 */
	public Customer getCustomerByLekeIdAndCustomerTypeAll(@Param("lekeId") Long lekeId,
			@Param("customerType") Integer customerType);
	/**
	 * 根据条件查询客户列表。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<CustomerVO> findCustomerByQuery(CustomerQuery query, Page page);
	
	/**
	 * 根据条件查询客户列表,只查本表，未跟其他表有关联关系
	 * 不返回状态解除关系的客户。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<CustomerVO> findCustomerListByQuery(CustomerQuery query, Page page);
	
	/**
	 * 查找 销售id
	 * @param lekeId
	 * @param customerType
	 * @return
	 */
	public Long getSellerByLekeIdCustomerType(@Param("lekeId") Long lekeId,
			@Param("customerType") Integer customerType);
	
	
	public List<CustomerDTO> selectApproveList(@Param("lekeId") Long lekeId,
			@Param("customerType") Integer customerType,@Param("status") Integer status, Page page);
	

}
