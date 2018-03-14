package cn.strong.leke.scs.model.view;

import java.math.BigDecimal;
import java.util.Date;

import cn.strong.leke.scs.util.CommRuleUtils;

public class MonthCommDtl implements java.io.Serializable {

	private static final long serialVersionUID = 6803604961038366865L;
	// 客户ID
	private Long customerId;
	// 客户名称
	private String customerName;
	// 首次交易时间
	private Date consumeTime;
	// 交易金额
	private BigDecimal tradeAmount;
	// 佣金金额
	private BigDecimal commAmount;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(BigDecimal commAmount) {
		this.commAmount = commAmount;
	}

	public int getBindYear() {
		return CommRuleUtils.getSeveralYear(new Date(), consumeTime);
	}
}
