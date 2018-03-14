package cn.strong.leke.question.model.wrongquestion;

import java.math.BigDecimal;

public class WrongQuestionQuery {

	private Long userId;
	private Long subjectId;
	private Long classId;
	private Long questionTypeId;
	private Integer diffLevel; // 难度层级: 1 - 易， 2 - 较易， 3 - 一般， 4 - 难， 5 - 较难
	private BigDecimal minRate;
	private BigDecimal maxRate;
	private Double minDifficulty; // 最小难度值
	private Double maxDifficulty; // 最大难度值
	private Long schoolId;
	private Long knowledgeId;//知识点id
	/**
	 * 1: modifiedOn
	   2: rate
	   3: errorTotal
	 */
	private Integer sort;
	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @return the questionTypeId
	 */
	public Long getQuestionTypeId() {
		return questionTypeId;
	}
	/**
	 * @param questionTypeId the questionTypeId to set
	 */
	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	/**
	 * @return the sortColumn
	 */
	public String getOrderColumn() {
		if (this.sort != null) {
			if (this.sort == 1) {
				return "modifiedOn";
			} else if (sort == 2) {
				return "rate";
			} else if (sort == 3) {
				return "errorTotal";
			}
		}
		return null;
	}

	public Integer getDiffLevel() {
		return diffLevel;
	}

	public void setDiffLevel(Integer diffLevel) {
		this.diffLevel = diffLevel;
		if (diffLevel != null && diffLevel != 0) {
			double min = (diffLevel - 1) * 0.2;
			double max = diffLevel * 0.2;
			setMinDifficulty(min);
			setMaxDifficulty(max);
		}
	}

	public Double getMinDifficulty() {
		return minDifficulty;
	}

	public void setMinDifficulty(Double minDifficulty) {
		this.minDifficulty = minDifficulty;
	}

	public Double getMaxDifficulty() {
		return maxDifficulty;
	}

	public void setMaxDifficulty(Double maxDifficulty) {
		this.maxDifficulty = maxDifficulty;
	}
	/**
	 * @return the minRate
	 */
	public BigDecimal getMinRate() {
		return minRate;
	}
	/**
	 * @param minRate the minRate to set
	 */
	public void setMinRate(BigDecimal minRate) {
		this.minRate = minRate;
	}
	/**
	 * @return the maxRate
	 */
	public BigDecimal getMaxRate() {
		return maxRate;
	}
	/**
	 * @param maxRate the maxRate to set
	 */
	public void setMaxRate(BigDecimal maxRate) {
		this.maxRate = maxRate;
	}
	/**
	 * @return the classId
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * @return the knowledgeId
	 */
	public Long getKnowledgeId() {
		return knowledgeId;
	}
	/**
	 * @param knowledgeId the knowledgeId to set
	 */
	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
}
