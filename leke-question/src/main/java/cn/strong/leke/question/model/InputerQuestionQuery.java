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

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 
 * 描述: 录入人员题目查询
 * 
 * @author liulb
 * @created 2014年5月13日 下午3:13:50
 * @since v1.0.0
 */
public class InputerQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 1996076796300614395L;
	private Long inputerId; // 录入人员ID
	private Date inputDate; // 录入日期
	private Integer statusType = Question.QUE_STATUS_INPUT; // 查询类型
	private Date minInputDate; // 最小录入日期
	private Date maxInputDate; // 最大录入日期
	private Long materialNodeId; // 章节ID

	public Long getInputerId() {
		return inputerId;
	}

	public void setInputerId(Long inputerId) {
		this.inputerId = inputerId;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
		if (inputDate != null) {
			this.minInputDate = inputDate;
			this.maxInputDate = DateUtils.addDays(inputDate, 1);
		}
	}

	public Integer getStatusType() {
		return statusType;
	}

	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
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

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

}
