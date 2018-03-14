package cn.strong.leke.homework.model;

import java.util.Date;

/**
 * 批改作业信息
 * @author Zhang Fujun
 * @date 2017年5月27日
 */
public class CorrectHomeworkDtlInfo  {


	private Long homeworkId;
	private Long homeworkDtlId;
	private Long teacherId;
	private String teacherName;
	private Long paperId;
	private Long studentId;
	private String studentName;
	private String homeworkName;
	private Integer homeworkType;
	private Long subjectId;
	private String subjectName;
	private Integer resType;
	private Date submitTime;
	private Boolean isFinishedSelf;
	
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
	 * @return the homeworkType
	 */
	public Integer getHomeworkType() {
		return homeworkType;
	}
	/**
	 * @param homeworkType the homeworkType to set
	 */
	public void setHomeworkType(Integer homeworkType) {
		this.homeworkType = homeworkType;
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
	 * @return the resType
	 */
	public Integer getResType() {
		return resType;
	}
	/**
	 * @param resType the resType to set
	 */
	public void setResType(Integer resType) {
		this.resType = resType;
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
	 * @return the paperId
	 */
	public Long getPaperId() {
		return paperId;
	}
	/**
	 * @param paperId the paperId to set
	 */
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	/**
	 * @return the isFinishedSelf
	 */
	public Boolean getIsFinishedSelf() {
		return isFinishedSelf;
	}
	/**
	 * @param isFinishedSelf the isFinishedSelf to set
	 */
	public void setIsFinishedSelf(Boolean isFinishedSelf) {
		this.isFinishedSelf = isFinishedSelf;
	}

}
