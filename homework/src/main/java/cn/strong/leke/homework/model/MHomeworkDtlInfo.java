package cn.strong.leke.homework.model;

import java.math.BigDecimal;
import java.util.Date;

public class MHomeworkDtlInfo {

	private Long homeworkId;
	private String homeworkName;
	private Long teacherId;
	private String teacherName;
	private Boolean isSelfCheck;
	private Boolean isOpenAnswer;
	private Date closeTime;
	private Date correctTime;
	
	private Long homeworkDtlId;
	private Long studentId;
	private String studentName;
	private Date submitTime;
	private Integer usedTime;
	private Integer submitStatus;
	private BigDecimal score;
	private BigDecimal scoreRate;
	private Integer bugFixStage;
	/**
	 * @return the homeworkId
	 */
	public Long getHomeworkId() {
		return homeworkId;
	}
	/**
	 * @param homeworkId the homeworkId to set
	 */
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}
	/**
	 * @return the homeworkName
	 */
	public String getHomeworkName() {
		return homeworkName;
	}
	/**
	 * @param homeworkName the homeworkName to set
	 */
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	/**
	 * @return the teacherId
	 */
	public Long getTeacherId() {
		return teacherId;
	}
	/**
	 * @param teacherId the teacherId to set
	 */
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}
	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	/**
	 * @return the isSelfCheck
	 */
	public Boolean getIsSelfCheck() {
		return isSelfCheck;
	}
	/**
	 * @param isSelfCheck the isSelfCheck to set
	 */
	public void setIsSelfCheck(Boolean isSelfCheck) {
		this.isSelfCheck = isSelfCheck;
	}
	/**
	 * @return the isOpenAnswer
	 */
	public Boolean getIsOpenAnswer() {
		return isOpenAnswer;
	}
	/**
	 * @param isOpenAnswer the isOpenAnswer to set
	 */
	public void setIsOpenAnswer(Boolean isOpenAnswer) {
		this.isOpenAnswer = isOpenAnswer;
	}
	/**
	 * @return the closeTime
	 */
	public Date getCloseTime() {
		return closeTime;
	}
	/**
	 * @param closeTime the closeTime to set
	 */
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	/**
	 * @return the homeworkDtlId
	 */
	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}
	/**
	 * @param homeworkDtlId the homeworkDtlId to set
	 */
	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
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
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the submitTime
	 */
	public Date getSubmitTime() {
		return submitTime;
	}
	/**
	 * @param submitTime the submitTime to set
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	/**
	 * @return the usedTime
	 */
	public Integer getUsedTime() {
		return usedTime;
	}
	/**
	 * @param usedTime the usedTime to set
	 */
	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}
	/**
	 * @return the submitStatus
	 */
	public Integer getSubmitStatus() {
		return submitStatus;
	}
	/**
	 * @param submitStatus the submitStatus to set
	 */
	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}
	/**
	 * @return the score
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * @return the scoreRate
	 */
	public BigDecimal getScoreRate() {
		return scoreRate;
	}
	/**
	 * @param scoreRate the scoreRate to set
	 */
	public void setScoreRate(BigDecimal scoreRate) {
		this.scoreRate = scoreRate;
	}
	/**
	 * @return the bugFixStage
	 */
	public Integer getBugFixStage() {
		return bugFixStage;
	}
	/**
	 * @param bugFixStage the bugFixStage to set
	 */
	public void setBugFixStage(Integer bugFixStage) {
		this.bugFixStage = bugFixStage;
	}
	/**
	 * @return the correctTime
	 */
	public Date getCorrectTime() {
		return correctTime;
	}
	/**
	 * @param correctTime the correctTime to set
	 */
	public void setCorrectTime(Date correctTime) {
		this.correctTime = correctTime;
	}
	
	
	
}
