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
 * 描述: 初审习题查询
 * 
 * @author liulb
 * @created 2014年7月29日 下午2:49:41
 * @since v1.1.0
 */
public class ReviewQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -4709658037834770321L;
	private Boolean reviewed = false; // 是否已初审
	private Long materialNodeId; // 教材节点ID

	private Date minInputDate; // 最小录入日期
	private Date maxInputDate; // 最大录入日期
	private String creatorName; // 创建者姓名

	public Boolean getReviewed() {
		return reviewed;
	}

	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
