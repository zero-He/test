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

import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 
 * 描述: 草稿题目查询
 * 
 * @author liulb
 * @created 2014年5月22日 下午2:42:14
 * @since v1.0.0
 */
public class DraftQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 5113619137041900984L;
	private Long inputerId; // 录入人员ID
	private String inputerName; // 录入人员姓名
	private Date minInputDate; // 最小录入日期
	private Date maxInputDate; // 最大录入日期
	private Long materialNodeId; // 教材节点ID

	public Long getInputerId() {
		return inputerId;
	}

	public void setInputerId(Long inputerId) {
		this.inputerId = inputerId;
	}

	public String getInputerName() {
		return inputerName;
	}

	public void setInputerName(String inputerName) {
		this.inputerName = inputerName;
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
