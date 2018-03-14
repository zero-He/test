package cn.strong.leke.diag.model;

import java.math.BigDecimal;

public class StatisticalDtl {

	private Long homeworkDtlId;
	private String homeworkName;
	private Long studentId;
	private String studentName;
	private BigDecimal score;
	private BigDecimal scoreRate;
	private BigDecimal maxScore;
	private BigDecimal minScore;
	private BigDecimal avgScore;

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public String getHomeworkName() {
		return homeworkName;
	}

	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getScoreRate() {
		return scoreRate;
	}

	public void setScoreRate(BigDecimal scoreRate) {
		this.scoreRate = scoreRate;
	}

	public BigDecimal getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}

	public BigDecimal getMinScore() {
		return minScore;
	}

	public void setMinScore(BigDecimal minScore) {
		this.minScore = minScore;
	}

	public BigDecimal getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(BigDecimal avgScore) {
		this.avgScore = avgScore;
	}

}
