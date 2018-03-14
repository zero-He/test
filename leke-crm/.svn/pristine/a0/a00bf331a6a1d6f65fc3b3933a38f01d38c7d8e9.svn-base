package cn.strong.leke.scs.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.crm.PointRechangeMQ;
import cn.strong.leke.scs.dao.master.SellerMasterDao;
import cn.strong.leke.scs.dao.master.CommRuleMasterDao;
import cn.strong.leke.scs.dao.master.CommissionMasterDao;
import cn.strong.leke.scs.dao.master.CustomerMasterDao;
import cn.strong.leke.scs.dao.slave.CommissionDao;
import cn.strong.leke.scs.model.CommRule;
import cn.strong.leke.scs.model.Commission;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.query.MonthCommDtlQuery;
import cn.strong.leke.scs.model.query.MonthCommQuery;
import cn.strong.leke.scs.model.rule.PointCommRule;
import cn.strong.leke.scs.model.view.MonthComm;
import cn.strong.leke.scs.model.view.MonthCommDtl;
import cn.strong.leke.scs.service.CommissionService;
import cn.strong.leke.scs.util.CommRuleUtils;
import cn.strong.leke.scs.util.CrmCsts;

@Service
public class CommissionServiceImpl implements CommissionService {

	private static final Logger logger = LoggerFactory.getLogger(CommissionService.class);

	@Resource
	private CommissionDao commissionDao;
	@Resource
	private CustomerMasterDao customerMasterDao;
	@Resource
	private CommRuleMasterDao commRuleMasterDao;
	@Resource
	private SellerMasterDao accumCommMasterDao;
	@Resource
	private CommissionMasterDao commissionMasterDao;

	@Override
	public void executePointRechangeWithTx(PointRechangeMQ rechange) {
		// 验证销售关系
		Customer customer = this.customerMasterDao.getCustomerByLekeIdAndCustomerType(rechange.getLekeId(),
				rechange.getCustomerType());
		if (customer == null) {
			logger.info("客户无销售关系，不生成佣金信息。");
			return;
		}
		// 验证点充值佣金规则
		CommRule commRule = this.commRuleMasterDao.getValidCommRuleByCommType(CrmCsts.COMM_TYPE_POINT);
		if (commRule == null || StringUtils.isEmpty(commRule.getDetail())) {
			throw new LekeRuntimeException("客户{}({})点充值佣金规则设置不正确", customer.getCustomerName(), customer.getCustomerId());
		}
		// 获取当时充值时间相对于首次充值的年份
		int year = CommRuleUtils.getSeveralYear(rechange.getTradeTime(), customer.getConsumeTime());
		// 解析点充值佣金规则
		PointCommRule ruleDetail = CommRuleUtils.parsePointCommRuleDetail(commRule.getDetail());
		// 计算佣金比例
		BigDecimal commRate = ruleDetail.rate(year - 1);
		if (commRate == null) {
			logger.info("客户{}({})销售佣金已过期，不生成佣金信息。", customer.getCustomerName(), customer.getCustomerId());
			return;
		}
		// 计算佣金金额
		BigDecimal commAmount = this.computeCommAmount(commRate, rechange.getTradeAmount());
		// 保存佣金信息
		Commission commission = new Commission();
		commission.setCustomerId(customer.getCustomerId());
		commission.setCommType(CrmCsts.COMM_TYPE_POINT);
		commission.setSourceId(rechange.getSourceId());
		commission.setCommRate(commRate);
		commission.setCommAmount(commAmount);
		commission.setTradeAmount(rechange.getTradeAmount());
		commission.setTradeTime(rechange.getTradeTime());
		commission.setCreatedBy(rechange.getOperatorId());
		this.commissionMasterDao.insertCommission(commission);
		// 更新销售佣金累计
		this.accumCommMasterDao.updateSellerCommAmount(customer.getSellerId(), commAmount);
		// 更新客户消费时间(非空忽略)
		if (customer.getConsumeTime() == null) {
			this.customerMasterDao.updateConsumeTimeByCustomerId(customer.getCustomerId(), rechange.getTradeTime(),
					rechange.getOperatorId());
		}
	}

	/**
	 * 根据佣金比例，计算交易的佣金金额
	 * @param commRate 佣金比例
	 * @param tradeAmount 交易金额
	 * @return
	 */
	private BigDecimal computeCommAmount(BigDecimal commRate, BigDecimal tradeAmount) {
		return tradeAmount.multiply(commRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
	}

	@Override
	public List<Commission> findCommissionByCondition(Long customerId, Date month) {
		return this.commissionDao.findCommissionByCondition(customerId, month);
	}

	@Override
	public List<MonthComm> findMonthCommByQuery(MonthCommQuery query, Page page) {
		return this.commissionDao.findMonthCommByQuery(query, page);
	}

	@Override
	public BigDecimal getTotalMonthCommByQuery(MonthCommQuery query) {
		return this.commissionDao.getTotalMonthCommByQuery(query);
	}

	@Override
	public List<MonthCommDtl> findMonthCommDtlByQuery(MonthCommDtlQuery query, Page page) {
		return this.commissionDao.findMonthCommDtlByQuery(query, page);
	}

	@Override
	public BigDecimal getTotalMonthCommDtlByQuery(MonthCommDtlQuery query) {
		return this.commissionDao.getTotalMonthCommDtlByQuery(query);
	}
}
