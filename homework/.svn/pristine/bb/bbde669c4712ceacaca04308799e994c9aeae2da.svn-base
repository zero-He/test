/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model;

import java.util.Date;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.homework.util.HomeworkUtils;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-6-9 下午3:00:56
 * @since   v1.0.0
 */
public class HomeworkDTO extends Homework{

	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userJson;
	
	private String paperName;
	
	private Long paperId;
	
	private Boolean isBind;
	
	private Long endTimeTmp;
	
	private Long startTimeTmp;
	
	private Integer submitStatus;
	
	// 分组信息
	private String studentGroupsJson;

	private String flag;

	private Integer corrects;

	public String getHomeworkTypeStr() {
		return HomeworkUtils.fmtHomeworkTypeStr(this.getHomeworkType());
	}

	public String getResTypeStr(){
		return HomeworkUtils.fmtResTypeStr(this.getResType());
	}
	
	public String getPaperTypeStr(){
		return HomeworkUtils.fmtPaperTypeStr(this.getPaperType());
	}
	
	public String getFinishNumStr() {
		return super.getFinishNum() == null ? "0" : super.getFinishNum().toString();
	}
	
	public String getCorrectNumStr() {
		return super.getCorrectNum() == null ? "0" : super.getCorrectNum().toString();
	}
	
	public String getCreatedOnStr() {
		return DateUtils.formatTime(super.getCreatedOn());
	}
	
	public String getStartTimeStr() {
		return DateUtils.formatTime(super.getStartTime());
	}
	
	public String getCloseTimeStr() {
		return DateUtils.format(super.getCloseTime(), DateUtils.MINITE_DATE_PATTERN);
	}

	/**
	 * @return the userJson
	 */
	public String getUserJson() {
		return userJson;
	}

	/**
	 * @param userJson the userJson to set
	 */
	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}

	/**
	 * @return the paperName
	 */
	public String getPaperName() {
		return paperName;
	}

	/**
	 * @param paperName the paperName to set
	 */
	public void setPaperName(String paperName) {
		this.paperName = paperName;
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
	 * @return the isBind
	 */
	public Boolean getIsBind() {
		return isBind;
	}

	/**
	 * @param isBind the isBind to set
	 */
	public void setIsBind(Boolean isBind) {
		this.isBind = isBind;
	}

	/**
	 * @return the endTimeTmp
	 */
	public Long getEndTimeTmp() {
		return endTimeTmp;
	}

	/**
	 * @param endTimeTmp the endTimeTmp to set
	 */
	public void setEndTimeTmp(Long endTimeTmp) {
		this.endTimeTmp = endTimeTmp;
		super.setStartTime(new Date(endTimeTmp)) ;
	}

	/**
	 * @return the startTimeTmp
	 */
	public Long getStartTimeTmp() {
		return startTimeTmp;
	}

	/**
	 * @param startTimeTmp the startTimeTmp to set
	 */
	public void setStartTimeTmp(Long startTimeTmp) {
		this.startTimeTmp = startTimeTmp;
		
		super.setStartTime(new Date(startTimeTmp)) ;
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

	public String getStudentGroupsJson() {
		return studentGroupsJson;
	}

	public void setStudentGroupsJson(String studentGroupsJson) {
		this.studentGroupsJson = studentGroupsJson;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getCorrects() {
		return corrects;
	}

	public void setCorrects(Integer corrects) {
		this.corrects = corrects;
	}
}
