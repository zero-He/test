package cn.strong.leke.scs.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.crm.SaleRelationMQ;
import cn.strong.leke.model.user.mq.UserMQ;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.CustomerDTO;
import cn.strong.leke.scs.model.CustomerVO;
import cn.strong.leke.scs.model.query.CustomerQuery;

/**
 * 销售关系服务。
 * @author  andy
 * @created 2015年6月16日 上午9:15:59
 * @since   v1.0.0
 */
public interface CustomerService {

	/**
	 * 销售关系绑定消息处理。
	 * @param saleRelMessage
	 */
	public void bindSaleRelationWithTx(SaleRelationMQ saleRelMessage);
	
	/**	
	 * 描述:删除学校客户 关联
	 * @author  zhengyiyin
	 * @created 2015年11月30日 下午7:18:36
	 */
	public boolean deleteSchoolCustomer(Long customerId,Long modifiedBy);

	/**
	 * 处理用户信息变更消息
	 * @param user 用户信息
	 */
	public void executeUserUpdatedWithTx(UserMQ user);

	/**
	 * 更新客户备注信息
	 * @param customerId 客户ID
	 * @param remark 备注信息
	 * @param modifiedBy 更新人
	 */
	public void updateRemarkByCustomerId(Long customerId, String remark, Long modifiedBy);

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
	public Customer getCustomerByLekeIdAndCustomerType(Long lekeId, Integer customerType);
	
	/**
	 * 根据乐课ID和类型获取有效客户。
	 * 返回状态是0待审核，1已绑定，2拒绝绑定的
	 * @param lekeId 乐课ID
	 * @param customerType 客户类型
	 * @return
	 */
	public Customer getCustomerByLekeIdAndCustomerTypeAll(Long lekeId, Integer customerType);

	/**
	 * 根据条件查询客户列表。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<CustomerVO> findCustomerByQuery(CustomerQuery query, Page page);
	
	/**
	 * 查找 销售id
	 * @param lekeId
	 * @param customerType
	 * @return
	 */
	public Long getSellerByLekeIdCustomerType(Long lekeId,
			Integer customerType);

	
	/**
	 * 获取待审核列表
	 * 
	 * @param lekeId
	 * @param cursomerType
	 * @return
	 */
	public List<CustomerDTO> getApproveList(Long lekeId, Integer cursomerType, Integer status, Page page);
	
	/**
	 * 保存绑定状态
	 * 
	 * @param customerId
	 * @param status
	 */
	public void saveBind(Long customerId, Integer status, Integer relationSale);
	
	/**
	 * 获取生效且解绑的记录
	 * @param lekeId
	 * @return
	 */
	List<Customer> getNearlyEffectCustomer(Long lekeId);

}
