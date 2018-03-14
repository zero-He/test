package cn.strong.leke.scs.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.strong.leke.model.BaseModel;

/**
 * 佣金记录表
 * @author  andy
 * @created 2015年6月15日 下午2:23:54
 * @since   v1.0.0
 */
public class Commission extends BaseModel {

	private static final long serialVersionUID = 4952532917628730969L;
	// 记录ID
	private Long record;
	// 客户ID
	private Long customerId;
	// 佣金类型
	private Integer commType;
	// 来源标识
	private Long sourceId;
	// 佣金比例
	private BigDecimal commRate;
	// 佣金金额
	private BigDecimal commAmount;
	// 交易金额
	private BigDecimal tradeAmount;
	// 交易时间
	private Date tradeTime;

	public Long getRecord() {
		return record;
	}

	public void setRecord(Long record) {
		this.record = record;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getCommType() {
		return commType;
	}

	public void setCommType(Integer commType) {
		this.commType = commType;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public BigDecimal getCommRate() {
		return commRate;
	}

	public void setCommRate(BigDecimal commRate) {
		this.commRate = commRate;
	}

	public BigDecimal getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(BigDecimal commAmount) {
		this.commAmount = commAmount;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
}
