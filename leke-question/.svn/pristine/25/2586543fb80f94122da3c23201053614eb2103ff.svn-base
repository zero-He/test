/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.model;

import org.bson.types.ObjectId;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 排重调度记录
 * 
 * @author liulongbiao
 * @created 2015年1月18日 上午10:15:56
 * @since v3.2.2
 */
public class DupJob {

	public static final int STATUS_INIT = 0; // 任务初始化
	public static final int STATUS_REMOVED_UPDATED = 1; // 比对文档源已更新
	public static final int STATUS_SUCCESS = 2; // 任务完成
	public static final int STATUS_FAILED = 3; // 任务失败

	public static DupJob now() {
		return new DupJob(new ObjectId(), STATUS_INIT);
	}

	@_id
	private ObjectId _id;
	private Integer status;

	public DupJob() {
		super();
	}

	public DupJob(ObjectId _id, Integer status) {
		this._id = _id;
		this.status = status;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
