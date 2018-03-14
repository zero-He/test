package cn.strong.leke.diag.model;

import java.math.BigDecimal;

public class ClassSubjectScoreDto {

	private Long subjectId;
	private BigDecimal maxScore;
	private BigDecimal avgScore;
	private BigDecimal minScore;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public BigDecimal getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}

	public BigDecimal getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(BigDecimal avgScore) {
		this.avgScore = avgScore;
	}

	public BigDecimal getMinScore() {
		return minScore;
	}

	public void setMinScore(BigDecimal minScore) {
		this.minScore = minScore;
	}
}
