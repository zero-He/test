/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.model;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

/**
 * 访问URL信息
 * 
 * @author liulongbiao
 */
public class AccessUrl extends AccessBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 287974701160170256L;
	public static final int TYPE_NORMAL = 0; // 普通
	public static final int TYPE_API_N = 1; // 内部接口
	public static final int TYPE_API_W = 2; // 外部接口

	@_id
	@ObjectId
	private String id;
	private String name; // 功能名称
	private Integer type; // URL类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
