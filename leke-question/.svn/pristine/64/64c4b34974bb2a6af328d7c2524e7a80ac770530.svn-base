/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import java.util.Date;
import java.util.List;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 
 * 描述: 教研员题目查询
 * 
 * @author liulb
 * @created 2014年5月19日 上午10:55:59
 * @since v1.0.0
 */
public class ResearcherQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 4655001246643262569L;
	private Integer statusType = Question.QUE_STATUS_CHECKED; // 查询类型
	private Long researcherId; // 教研员ID
	private Long materialNodeId; // 教材节点ID
	private Date minCheckDate; // 最小审核日期
	private Date maxCheckDate; // 最大审核日期
	private String creatorName; // 创建者姓名
	private Long userId;//用户id
	private Date minInputDate; // 最小录入日期
	private Date maxInputDate; // 最大录入日期
	private List<SchoolStageSubject> schoolStageSubjects;//学段学科

	public Integer getStatusType() {
		return statusType;
	}

	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}

	public Long getResearcherId() {
		return researcherId;
	}

	public void setResearcherId(Long researcherId) {
		this.researcherId = researcherId;
	}

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

	public Date getMinCheckDate() {
		return minCheckDate;
	}

	public void setMinCheckDate(Date minCheckDate) {
		this.minCheckDate = minCheckDate;
	}

	public Date getMaxCheckDate() {
		return maxCheckDate;
	}

	public void setMaxCheckDate(Date maxCheckDate) {
		this.maxCheckDate = maxCheckDate;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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
	 * @return the minInputDate
	 */
	public Date getMinInputDate() {
		return minInputDate;
	}

	/**
	 * @param minInputDate the minInputDate to set
	 */
	public void setMinInputDate(Date minInputDate) {
		this.minInputDate = minInputDate;
	}

	/**
	 * @return the maxInputDate
	 */
	public Date getMaxInputDate() {
		return maxInputDate;
	}

	/**
	 * @param maxInputDate the maxInputDate to set
	 */
	public void setMaxInputDate(Date maxInputDate) {
		this.maxInputDate = maxInputDate;
	}

	/**
	 * 
	 * 描述: 最小录入时间
	 * 
	 * @author liulb
	 * @created 2014年9月24日 下午2:53:58
	 * @since v1.1.0
	 * @return
	 */
	public Date getMinInputDateTime() {
		return minInputDate;
	}

	/**
	 * 
	 * 描述: 最大录入时间
	 * 
	 * @author liulb
	 * @created 2014年9月24日 下午2:55:12
	 * @since v1.1.0
	 * @return
	 */
	public Date getMaxInputDateTime() {
		return maxInputDate == null ? null : DateUtils.addDays(maxInputDate, 1);
	}

	/**
	 * @return the schoolStageSubjects
	 */
	public List<SchoolStageSubject> getSchoolStageSubjects() {
		return schoolStageSubjects;
	}

	/**
	 * @param schoolStageSubjects the schoolStageSubjects to set
	 */
	public void setSchoolStageSubjects(List<SchoolStageSubject> schoolStageSubjects) {
		this.schoolStageSubjects = schoolStageSubjects;
	}

}
