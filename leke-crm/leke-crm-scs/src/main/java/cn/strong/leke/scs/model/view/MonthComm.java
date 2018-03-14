package cn.strong.leke.scs.model.view;

import java.math.BigDecimal;
import java.util.Date;

public class MonthComm implements java.io.Serializable {

	private static final long serialVersionUID = 5579265515571562096L;
	// 销售ID
	private Long sellerId;
	// 销售姓名
	private String sellerName;
	// 月份
	private Date month;
	// 交易金额
	private BigDecimal tradeAmount;
	// 佣金金额
	private BigDecimal commAmount;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
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
}
