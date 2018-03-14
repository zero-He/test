package cn.strong.leke.homework.model;

import java.io.Serializable;

public class ExportStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1480986680339717321L;
	
	private Long homeworkId;
	private Long studentId;
	private String studentName;
	private Integer submitStatus;
	public Long getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
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
	public Integer getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}
	
}
