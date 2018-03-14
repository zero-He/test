/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.model;

import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 相似习题关联
 * 
 * @author liulongbiao
 * @created 2015年1月19日 上午9:52:56
 * @since v3.2.2
 */
public class SimQue {
	@_id
	private Long qid;
	private List<Long> simIds;

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public List<Long> getSimIds() {
		return simIds;
	}

	public void setSimIds(List<Long> simIds) {
		this.simIds = simIds;
	}

}
