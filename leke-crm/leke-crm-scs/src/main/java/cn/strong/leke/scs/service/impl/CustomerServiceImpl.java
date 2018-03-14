package cn.strong.leke.scs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.crm.SaleRelationMQ;
import cn.strong.leke.model.pay.BestowalMQ;
import cn.strong.leke.model.user.mq.UserMQ;
import cn.strong.leke.scs.constant.CustomerCst;
import cn.strong.leke.scs.dao.master.CustomerMasterDao;
import cn.strong.leke.scs.dao.master.SellerMasterDao;
import cn.strong.leke.scs.dao.slave.CustomerDao;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.CustomerDTO;
import cn.strong.leke.scs.model.CustomerVO;
import cn.strong.leke.scs.model.query.CustomerQuery;
import cn.strong.leke.scs.service.CustomerService;
import cn.strong.leke.scs.util.CrmCsts;
import cn.strong.leke.user.model.parameters.BestowalSetting;
import cn.strong.leke.user.model.parameters.ParameterCsts;

@Service
public class CustomerServiceImpl implements CustomerService
{

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerMasterDao customerMasterDao;
	@Resource
	private SellerMasterDao sellerMasterDao;
	@Resource
	private MessageSender bestowalSender;

	@Override
	public void bindSaleRelationWithTx(SaleRelationMQ saleRelation)
	{
		Customer customer = this.customerDao.getCustomerByLekeIdAndCustomerTypeAll(saleRelation.getLekeId(), saleRelation.getCustomerType());
		if (customer != null)
		{
			if (customer.getStatus().equals(2))
			{
				// 如果状态是拒绝绑定，再次绑定需要删除此条数据，配合之前的解绑业务逻辑
				customerMasterDao.deleteCustomer(customer.getCustomerId(), saleRelation.getLekeId());
			} else
			{
				logger.error("该客户({},{})已经建立销售关系，不可以重复建立。", customer.getLekeId(), customer.getCustomerType());
				throw new LekeRuntimeException("该客户已经建立销售关系，不可以重复建立。");
			}
		}
		Customer nearlyDelete = this.customerMasterDao.getNearlyDeleteCustomer(saleRelation.getLekeId());
		customer = new Customer();
		customer.setCustomerName(saleRelation.getCustomerName());
		customer.setCustomerType(saleRelation.getCustomerType());
		customer.setLekeId(saleRelation.getLekeId());
		customer.setLekeLoginName(saleRelation.getLekeLoginName());
		customer.setPhone(saleRelation.getPhone());
		customer.setSellerId(saleRelation.getSellerId());
		customer.setSellerName(saleRelation.getSellerName());
		customer.setStatus(CrmCsts.CUSTOMER_STATUS_CHECK);
		if (saleRelation.getStatus() != null)
		{
			customer.setStatus(saleRelation.getStatus());
		}
		customer.setBindTime(nearlyDelete == null ? saleRelation.getBindTime() : nearlyDelete.getBindTime());
		customer.setConsumeTime(nearlyDelete == null ? null : nearlyDelete.getConsumeTime());
		this.customerMasterDao.insertCustomer(customer);

		// 待审核的放到确认绑定逻辑里面170320
		if (saleRelation.getStatus() != null && saleRelation.getStatus().equals(1))
		{
			this.sellerMasterDao.updateIncrementCustomerNum(saleRelation.getSellerId(), true);

			// 信息完善赠送点
			BestowalSetting bestowalSetting = ParametersContext.getObject(ParameterCsts.ACCOUNT_POINT_BESTOWAL, BestowalSetting.class);
			if (bestowalSetting.getRelationSale() > 0)
			{
				BestowalMQ bestowalMQ = new BestowalMQ();
				bestowalMQ.setOwnerId(customer.getLekeId());
				bestowalMQ.setOwnerType(saleRelation.getCustomerType());
				bestowalMQ.setOperatorId(customer.getLekeId());
				bestowalMQ.setBestowal(bestowalSetting.getRelationSale());
				bestowalMQ.setRemark("绑定客户经理");
				this.bestowalSender.send(bestowalMQ);
			}
		}
	}

	@Override
	public void executeUserUpdatedWithTx(UserMQ user)
	{
		Customer customer = this.customerMasterDao.getCustomerByLekeIdAndCustomerType(user.getUserId(), CrmCsts.CUSTOMER_TYPE_PERSON);
		if (customer == null)
		{
			return;
		}
		customer.setCustomerName(user.getUserName());
		customer.setLekeLoginName(user.getLoginName());
		customer.setPhone(user.getPhone());
		customer.setModifiedBy(user.getUserId());
		this.customerMasterDao.updateCustomer(customer);
	}

	@Override
	public void updateRemarkByCustomerId(Long customerId, String remark, Long modifiedBy)
	{
		this.customerMasterDao.updateRemarkByCustomerId(customerId, remark, modifiedBy);
	}

	@Override
	public Customer getCustomerByCustomerId(Long customerId)
	{
		return this.customerDao.getCustomerByCustomerId(customerId);
	}

	@Override
	public Customer getCustomerByLekeIdAndCustomerType(Long lekeId, Integer customerType)
	{
		return customerDao.getCustomerByLekeIdAndCustomerType(lekeId, customerType);
	}
	 

	@Override
	public Customer getCustomerByLekeIdAndCustomerTypeAll(Long lekeId, Integer customerType)
	{
		return customerDao.getCustomerByLekeIdAndCustomerTypeAll(lekeId, customerType);
	}

	@Override
	public List<CustomerVO> findCustomerByQuery(CustomerQuery query, Page page)
	{
		if (query != null && query.getCustomerType().equals(CustomerCst.CUSTOMERTYPE_SCHOOL))
		{
			// 返回学校客户 列表
			return customerDao.findCustomerListByQuery(query, page);
		}
		return customerDao.findCustomerByQuery(query, page);
	}

	@Override
	public boolean deleteSchoolCustomer(Long customerId, Long modifiedBy)
	{
		// 通过客户查找 现客户经理id
		Customer customer = customerDao.getCustomerByCustomerId(customerId);
		Long sellerId = modifiedBy;
		if (customer != null)
		{
			sellerId = customer.getSellerId();
		}
		int count = customerMasterDao.deleteCustomer(customerId, modifiedBy);
		if (count > 0)
		{
			// 移除客户后，修改当前客户经理的 客户总数 ,待审核状态不能移除
			this.sellerMasterDao.updateIncrementCustomerNum(sellerId, false);
		}
		return count > 0;
	}

	@Override
	public Long getSellerByLekeIdCustomerType(Long lekeId, Integer customerType)
	{
		return customerDao.getSellerByLekeIdCustomerType(lekeId, customerType);
	}


	@Override
	public List<CustomerDTO> getApproveList(Long lekeId, Integer cursomerType, Integer status, Page page)
	{
		return customerDao.selectApproveList(lekeId, cursomerType, status, page);
	}

	@Override
	public void saveBind(Long customerId, Integer status, Integer relationSale)
	{
		Customer customer = this.customerDao.getCustomerByCustomerId(customerId);
		logger.info("customerId：" + customer.getCustomerId());
		if (status.equals(1))
		{
			this.sellerMasterDao.updateIncrementCustomerNum(customer.getSellerId(), true);
			// 信息完善赠送点
			BestowalSetting bestowalSetting = ParametersContext.getObject(ParameterCsts.ACCOUNT_POINT_BESTOWAL, BestowalSetting.class);
			if (bestowalSetting.getRelationSale() > 0)
			{
				logger.info("赠送点数：" + relationSale);
				BestowalMQ bestowalMQ = new BestowalMQ();
				bestowalMQ.setOwnerId(customer.getLekeId());
				bestowalMQ.setOwnerType(customer.getCustomerType());
				bestowalMQ.setOperatorId(customer.getLekeId());
				bestowalMQ.setBestowal(relationSale != null ? relationSale : bestowalSetting.getRelationSale());
				bestowalMQ.setRemark("绑定客户经理");
				this.bestowalSender.send(bestowalMQ);
			}
		}
		logger.info("customerId：" + customerId + ",status:" + status);
		customerMasterDao.updateCustomerByCustomerID(customerId, status);
	}

	@Override
	public List<Customer> getNearlyEffectCustomer(Long lekeId)
	{
		return customerMasterDao.selectNearlyEffectCustomer(lekeId);
	}
}
