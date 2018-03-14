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
import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 * 
 * 描述: 审核员题目查询
 * 
 * @author liulb
 * @created 2014年5月16日 下午2:47:27
 * @since v1.0.0
 */
public class CheckerQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 3727425989626867830L;
	public static final int TYPE_UNCHECKED = 96;
	public static final int TYPE_CHECKED = 97;

	private int statusType = TYPE_UNCHECKED;
	private Long checkerId; // 录入人员ID
	private Date checkDate; // 审核日期
	private Date minCheckDate; // 最小审核日期
	private Date maxCheckDate; // 最大审核日期

	public int getStatusType() {
		return statusType;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

	public Long getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(Long checkerId) {
		this.checkerId = checkerId;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
		if (checkDate != null) {
			this.minCheckDate = checkDate;
			this.maxCheckDate = DateUtils.addDays(checkDate, 1);
		}
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

}
