/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import cn.strong.leke.model.question.Knowledge;

/**
 * 
 * 描述: 带题量统计的知识点
 * 
 * @author liulb
 * @created 2014年5月23日 下午4:51:49
 * @since v1.0.0
 */
public class StatisKnowledge extends Knowledge {
	/**
	 * Description:
	 */
	private static final long serialVersionUID = -4793803175587347595L;
	private Long queCount; // 题目数

	public Long getQueCount() {
		return queCount;
	}

	public void setQueCount(Long queCount) {
		this.queCount = queCount;
	}
}
