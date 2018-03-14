/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.model;

import java.util.Date;

/**
 *
 *
 * @author  liulongbiao
 * @created 2015年1月18日 下午2:02:09
 * @since   v3.2.2
 */
public class UpdatedQuestionQuery {

	private static final int DEFAULT_BATCH_SIZE = 200;

	private Date now; // 当前时间
	private Date last; // 上次成功时间
	private Long lastQuestionId; // 上次查询的最后一条记录的习题ID
	private int batchSize = DEFAULT_BATCH_SIZE; // 批次大小

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public Date getLast() {
		return last;
	}

	public void setLast(Date last) {
		this.last = last;
	}

	public Long getLastQuestionId() {
		return lastQuestionId;
	}

	public void setLastQuestionId(Long lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
