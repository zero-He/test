package cn.strong.leke.homework.model;

import java.util.Date;

import cn.strong.leke.model.BaseModel;

/**
 * 批量布置作业DO对象
 * @author Zhang Fujun
 * @date 2016年5月19日
 */
public class AssignLogDO extends BaseModel {
	private static final long serialVersionUID = -6830007817939676595L;
	private Long assignId;
	/**
	 * 作业名称
	 */
	private String homeworkName;
	/**
	 * 关联的hw_homework作业ID 数组 [1,2]
	 */
	private String homeworkIdJson;
	/**
	 * 班级信息
	 */
	private String classInfo;
	/**
	 * 试卷ID
	 */
	private Long paperId;
	/**
	 * 作业的开始时间
	 */
	private Date startTime;
	/**
	 * 作业截止时间
	 */
	private Date closeTime;
	/**
	 * 公开答案时间
	 */
	private Date openAnswerTime;
	/**
	 * 作业状态，1：正常，2：作废
	 */
	private Integer status;

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
	 * @return the homeworkIdJson
	 */
	public String getHomeworkIdJson() {
		return homeworkIdJson;
	}

	/**
	 * @param homeworkIdJson the homeworkIdJson to set
	 */
	public void setHomeworkIdJson(String homeworkIdJson) {
		this.homeworkIdJson = homeworkIdJson;
	}

	/**
	 * @return the classInfo
	 */
	public String getClassInfo() {
		return classInfo;
	}

	/**
	 * @param classInfo the classInfo to set
	 */
	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
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
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	/**
	 * @return the assignId
	 */
	public Long getAssignId() {
		return assignId;
	}

	/**
	 * @param assignId the assignId to set
	 */
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	/**
	 * @return the openAnswerTime
	 */
	public Date getOpenAnswerTime() {
		return openAnswerTime;
	}

	/**
	 * @param openAnswerTime the openAnswerTime to set
	 */
	public void setOpenAnswerTime(Date openAnswerTime) {
		this.openAnswerTime = openAnswerTime;
	}

}