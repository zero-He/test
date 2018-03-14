package cn.strong.leke.homework.model.query;

import java.util.List;

import cn.strong.leke.homework.model.AnswerInfo;

public class ApiParamExercise {

	private String exerciseId;
	private String  exerciseName;
	private Integer difficultyLevel;// 难度系数 ，可为null
	private Long exerciseType;// 1：章节，2：知识点 ，not null 
	private List<Long> relIds;// 选择的章节id或知识点id not null
	private Long subjectId;//学科id not null 
	private String subjectName;
	private List<AnswerInfo> answerJson;
	private Long userId;
	private Long knowledgeId;

	/**
	 * @return the difficultyLevel
	 */
	public Integer getDifficultyLevel() {
		return difficultyLevel;
	}

	/**
	 * @param difficultyLevel the difficultyLevel to set
	 */
	public void setDifficultyLevel(Integer difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	/**
	 * @return the exerciseType
	 */
	public Long getExerciseType() {
		return exerciseType;
	}

	/**
	 * @param exerciseType the exerciseType to set
	 */
	public void setExerciseType(Long exerciseType) {
		this.exerciseType = exerciseType;
	}

	/**
	 * @return the relIds
	 */
	public List<Long> getRelIds() {
		return relIds;
	}

	/**
	 * @param relIds the relIds to set
	 */
	public void setRelIds(List<Long> relIds) {
		this.relIds = relIds;
	}

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
	 * @return the exerciseId
	 */
	public String getExerciseId() {
		return exerciseId;
	}

	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * @return the answerJson
	 */
	public List<AnswerInfo> getAnswerJson() {
		return answerJson;
	}

	/**
	 * @param answerJson the answerJson to set
	 */
	public void setAnswerJson(List<AnswerInfo> answerJson) {
		this.answerJson = answerJson;
	}

	/**
	 * @return the exerciseName
	 */
	public String getExerciseName() {
		return exerciseName;
	}

	/**
	 * @param exerciseName the exerciseName to set
	 */
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
