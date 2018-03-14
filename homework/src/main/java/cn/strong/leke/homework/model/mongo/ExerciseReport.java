package cn.strong.leke.homework.model.mongo;

import java.math.BigDecimal;
import java.util.List;

public class ExerciseReport {

	/**
	 * 关联知识点
	 */
	public static Integer RELATION_KNOWLEDGE = 1;
	/**
	 * 直接选择的知识点
	 */
	public static Integer KNOWLEDGE = 2;
	/**
	 * 报告类型1：直接选择的知识点，2：关联知识点
	 */
	private Integer type;
	private Long knowledgeId;
	private String knowledgeName;
	private BigDecimal accuracy;
	private List<Long> questions;

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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

	/**
	 * @return the knowledgeName
	 */
	public String getKnowledgeName() {
		return knowledgeName;
	}

	/**
	 * @param knowledgeName the knowledgeName to set
	 */
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	/**
	 * @return the questions
	 */
	public List<Long> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Long> questions) {
		this.questions = questions;
	}

	/**
	 * @return the accuracy
	 */
	public BigDecimal getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

}
