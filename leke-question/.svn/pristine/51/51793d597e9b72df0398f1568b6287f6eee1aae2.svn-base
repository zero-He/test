/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import java.util.List;

import cn.strong.leke.common.utils.batch.BatchEvent;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月18日 下午6:17:23
 * @since   v1.0.0
 */
public class BatchQuestionIdEvent extends BatchEvent<Long> {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = 137370170127390385L;
	private List<Long> questionIds;
	private String operatorName;

	public List<Long> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(List<Long> questionIds) {
		this.questionIds = questionIds;
	}

	@Override
	public List<Long> getItems() {
		return questionIds;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
