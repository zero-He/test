package cn.strong.leke.homework.model.mongo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;
import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;
import cn.strong.leke.homework.util.ExerciseCommon;

public class Exercise {

	// 练习标识
	@_id
	@ObjectId
	private String exerciseId;
	/**
	 * 数据来源 ，1：平板端
	 */
	private Integer sourceType;
	/**
	 * 1: 代表自主练习
	 * 2： 代表微课练习，自主练习列表不显示该数据
	 */
	private Integer status;
	/**
	 * @return the sourceType
	 */
	private Long mysqlKey;
	// 练习类型 1:知识点，2：章节
	private Long exerciseType;
	//提交状态 1：已提交 0：未提交
	private Integer submitState;
	// 练习名称
	private String exerciseName;
	// 关联标识。知识点标识或者章节标识
	private List<Long> relIds;
	// 学生标识
	private Long studentId;
	// 学科标识
	private Long subjectId;
	// 学科名称
	private String subjectName;
	// 正确题数
	private Integer rightNum;
	// 练习题数
	private Integer totalNum;
	// 平均正确率。平均正确率=每道题的正确率/总题数
	@BsonDecimal(scale = 4, round = RoundingMode.HALF_UP)
	private BigDecimal accuracy;
	/**
	 * 较上次练习，争取率提升程度 -1：下降 0：持平，1：提高
	 */
	private Integer growth;
	private Long schoolId;
	/**
	 * 包含的题目信息
	 */
	private List<ExerciseQuestionResult> questions;
	
	/**
	 * 按照知识点生成的报告
	 */
	private List<ExerciseReport> report;
	// 结束时间
	private Long  submitTime;
	private Long createdOn;
	private Long createdBy;
	private Boolean isDeleted;
	private Long modifiedOn;
	private Long modifiedBy;
	/**
	 * 难易程度
	 * 容易：1
		较易：2
		一般：3
		较难：4
		困难：5
	 */
	private Integer difficultyLevel;
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
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
	 * @return the rightNum
	 */
	public Integer getRightNum() {
		return rightNum;
	}
	/**
	 * @param rightNum the rightNum to set
	 */
	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}
	/**
	 * @return the totalNum
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
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
	/**
	 * @return the growth
	 */
	public Integer getGrowth() {
		return growth;
	}
	/**
	 * @param growth the growth to set
	 */
	public void setGrowth(Integer growth) {
		this.growth = growth;
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
	 * @return the questions
	 */
	public List<ExerciseQuestionResult> getQuestions() {
		return questions;
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<ExerciseQuestionResult> questions) {
		this.questions = questions;
	}
	/**
	 * @return the report
	 */
	public List<ExerciseReport> getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(List<ExerciseReport> report) {
		this.report = report;
	}
	/**
	 * @return the submitTime
	 */
	public Long getSubmitTime() {
		return submitTime;
	}
	/**
	 * @param submitTime the submitTime to set
	 */
	public void setSubmitTime(Long submitTime) {
		this.submitTime = submitTime;
	}
	/**
	 * @return the createdOn
	 */
	public Long getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the modifiedOn
	 */
	public Long getModifiedOn() {
		return modifiedOn;
	}
	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Long modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	/**
	 * @return the modifiedBy
	 */
	public Long getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
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
	 * @return the mysqlKey
	 */
	public Long getMysqlKey() {
		return mysqlKey;
	}
	/**
	 * @param mysqlKey the mysqlKey to set
	 */
	public void setMysqlKey(Long mysqlKey) {
		this.mysqlKey = mysqlKey;
	}
	/**
	 * @return the submiteState
	 */
	public Integer getSubmitState() {
		if(submitState == null){
			return ExerciseCommon.EXERCISE_NO_SUBMIT;
		}
		else{
			return submitState;
		}
	}
	/**
	 * @param submiteState the submiteState to set
	 */
	public void setSubmitState(Integer submitState) {
		this.submitState = submitState;
	}
	/**
	 * @return the sourceType
	 */
	public Integer getSourceType() {
		return sourceType;
	}
	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
