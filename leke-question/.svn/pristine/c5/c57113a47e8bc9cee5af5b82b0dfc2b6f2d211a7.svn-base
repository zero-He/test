/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import static cn.strong.leke.model.question.Question.QUE_STATUS_CHECKED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_CORRECTION;
import static cn.strong.leke.model.question.Question.QUE_STATUS_VERIFIED;

/**
 * 
 * 描述: 题目知识点树查询
 * 
 * @author liulb
 * @created 2014年5月20日 上午10:25:22
 * @since v1.0.0
 */
public class QueKnowledgeTreeQuery {

	public static final int TYPE_CHECKED = QUE_STATUS_CHECKED;
	public static final int TYPE_CORRECTION = QUE_STATUS_CORRECTION;
	public static final int TYPE_VERIFIED = QUE_STATUS_VERIFIED;
	public static final int TYPE_PUBLISHED = 99; // 已发布, 包含已审核和已校对

	private Long materialId; // 教材ID
	private Integer statusType = TYPE_PUBLISHED; // 状态类型
	private Long parentId; // 父节点ID
	private Long schoolStageId; // 学段ID
	private Long subjectId; // 学科ID

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

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

}
