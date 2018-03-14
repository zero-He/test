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
import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 
 * 描述: 教研员审核习题查询
 * 
 * @author liulb
 * @created 2014年9月24日 下午2:47:00
 * @since v1.1.0
 */
public class CheckQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -8429737962155520902L;
	public static final int TYPE_UNCHECKED = 96; // 未审核
	public static final int TYPE_CHECKED = 97; // 已审核

	private int statusType = TYPE_UNCHECKED; // 状态
	private Long materialNodeId; // 教材节点ID
	private String creatorName; // 创建者姓名
	private Long userId;//用户id
	private Date minInputDate; // 最小录入日期
	private Date maxInputDate; // 最大录入日期
	private List<SchoolStageSubject> schoolStageSubjects;//学段学科

	public int getStatusType() {
		return statusType;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

	public String getCreatorName() {
		return creatorName;
	}

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

	public Date getMinInputDate() {
		return minInputDate;
	}

	public void setMinInputDate(Date minInputDate) {
		this.minInputDate = minInputDate;
	}

	public Date getMaxInputDate() {
		return maxInputDate;
	}

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
