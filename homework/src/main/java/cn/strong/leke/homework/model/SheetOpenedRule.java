package cn.strong.leke.homework.model;

import java.math.BigDecimal;

public class SheetOpenedRule extends SheetRangeRule {

	private BigDecimal maxScore;

	public BigDecimal getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}

	@Override
	public String getRangeType() {
		return "11";
	}
}
