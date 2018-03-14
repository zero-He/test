package cn.strong.leke.diag.model.teachingMonitor.interact;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

import java.math.BigDecimal;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 19:34:18
 */
public class InteractTrendBean extends CommProp {

	private BigDecimal totalNum;
	private BigDecimal callNum;
	private BigDecimal quickNum;
	private BigDecimal examNum;
	private BigDecimal discuNum;
	private BigDecimal authedNum;

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getCallNum() {
		return callNum;
	}

	public void setCallNum(BigDecimal callNum) {
		this.callNum = callNum;
	}

	public BigDecimal getQuickNum() {
		return quickNum;
	}

	public void setQuickNum(BigDecimal quickNum) {
		this.quickNum = quickNum;
	}

	public BigDecimal getExamNum() {
		return examNum;
	}

	public void setExamNum(BigDecimal examNum) {
		this.examNum = examNum;
	}

	public BigDecimal getDiscuNum() {
		return discuNum;
	}

	public void setDiscuNum(BigDecimal discuNum) {
		this.discuNum = discuNum;
	}

	public BigDecimal getAuthedNum() {
		return authedNum;
	}

	public void setAuthedNum(BigDecimal authedNum) {
		this.authedNum = authedNum;
	}
}
