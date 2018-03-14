package cn.strong.leke.homework.model;

import java.math.BigDecimal;

import cn.strong.leke.model.BaseModel;

/**
 * 实体对象：练习表
 */
public class ExerciseOld extends BaseModel {

	private static final long serialVersionUID = 6658102817152464660L;

	/** 同步练习 */
	public static final long EXERCISE_TYPE_SYNCHRONIZE = 1;
	/** 知识点检测 */
	public static final long EXERCISE_TYPE_KNOWLEDGE = 2;

	// 练习标识
	private Long exerciseId;
	// 练习类型
	private Long exerciseType;
	// 练习名称
	private String exerciseName;
	// 关联标识。知识点标识或者章节标识
	private Long relId;
	// 学生标识
	private Long studentId;
	// 学段ID
	private Long schoolStageId;
	// 学科标识
	private Long subjectId;
	// 学科名称
	private String subjectName;
	// 正确题数
	private Integer rightNum;
	// 练习题数
	private Integer totalNum;
	// 平均正确率。平均正确率=每道题的正确率/总题数
	private BigDecimal accuracy;
	// 开始时间
	private java.util.Date startTime;
	// 结束时间
	private java.util.Date submitTime;
	//学校ID 
	private Long schoolId;

	/**
	 * 练习标识
	 */
	public Long getExerciseId() {
		return this.exerciseId;
	}

	/**
	 * 练习标识
	 */
	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * 练习类型
	 */
	public Long getExerciseType() {
		return this.exerciseType;
	}

	/**
	 * 练习类型名称
	 */
	public String getExerciseTypeString() {
		if (this.exerciseType == EXERCISE_TYPE_SYNCHRONIZE) {
			return "同步练习";
		} else if (this.exerciseType == EXERCISE_TYPE_KNOWLEDGE) {
			return "知识点检测";
		}
		return "";
	}

	/**
	 * 练习类型
	 */
	public void setExerciseType(Long exerciseType) {
		this.exerciseType = exerciseType;
	}

	/**
	 * 练习名称
	 */
	public String getExerciseName() {
		return this.exerciseName;
	}

	/**
	 * 练习名称
	 */
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	/**
	 * 关联标识。知识点标识或者章节标识
	 */
	public Long getRelId() {
		return this.relId;
	}

	/**
	 * 关联标识。知识点标识或者章节标识
	 */
	public void setRelId(Long relId) {
		this.relId = relId;
	}

	/**
	 * 学生标识
	 */
	public Long getStudentId() {
		return this.studentId;
	}

	/**
	 * 学生标识
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	/**
	 * 学科标识
	 */
	public Long getSubjectId() {
		return this.subjectId;
	}

	/**
	 * 学科标识
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * 学科名称
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * 学科名称
	 */
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

	/**
	 * 平均正确率。平均正确率=每道题的正确率/总题数
	 */
	public BigDecimal getAccuracy() {
		return this.accuracy;
	}

	/**
	 * 平均正确率。平均正确率=每道题的正确率/总题数
	 */
	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * 开始时间
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	public String getStartTimeString() {
		return cn.strong.leke.common.utils.DateUtils.formatTime(startTime);
	}

	/**
	 * 开始时间
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间
	 */
	public java.util.Date getSubmitTime() {
		return this.submitTime;
	}

	/**
	 * 结束时间
	 */
	public void setSubmitTime(java.util.Date submitTime) {
		this.submitTime = submitTime;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
