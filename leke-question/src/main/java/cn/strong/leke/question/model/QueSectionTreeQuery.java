/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

/**
 * 
 * 描述: 题目章节树查询
 * 
 * @author liulb
 * @created 2014年5月20日 上午10:51:48
 * @since v1.0.0
 */
public class QueSectionTreeQuery {

	public static final int TYPE_DRAFT = 98; // 草稿, 包含录入和退回录入

	private Long materialId; // 教材ID
	private Integer statusType = TYPE_DRAFT; // 状态类型
	private Long parentId; // 父节点ID

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Integer getStatusType() {
		return statusType;
	}

	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
