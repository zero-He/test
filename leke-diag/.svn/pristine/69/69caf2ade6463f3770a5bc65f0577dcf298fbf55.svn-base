package cn.strong.leke.diag.model.studentMonitor;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;
import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ActiveLearningBean {

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

	// 结束时间
	private Long submitTime;
	private Long createdOn;
	private Long createdBy;
	private Boolean isDeleted;
	private Long modifiedOn;
	private Long modifiedBy;
	/**
	 * 难易程度
	 * 容易：1
	 * 较易：2
	 * 一般：3
	 * 较难：4
	 * 困难：5
	 */
	private Integer difficultyLevel;

	public String getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getMysqlKey() {
		return mysqlKey;
	}

	public void setMysqlKey(Long mysqlKey) {
		this.mysqlKey = mysqlKey;
	}

	public Long getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(Long exerciseType) {
		this.exerciseType = exerciseType;
	}

	public Integer getSubmitState() {
		return submitState;
	}

	public void setSubmitState(Integer submitState) {
		this.submitState = submitState;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public List<Long> getRelIds() {
		return relIds;
	}

	public void setRelIds(List<Long> relIds) {
		this.relIds = relIds;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getRightNum() {
		return rightNum;
	}

	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	public Integer getGrowth() {
		return growth;
	}

	public void setGrowth(Integer growth) {
		this.growth = growth;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Long submitTime) {
		this.submitTime = submitTime;
	}

	public Long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public Long getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Long modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(Integer difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
}