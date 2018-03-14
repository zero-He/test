/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.model;

import java.io.Serializable;

/**
 * 重复习题查询条件
 * 
 * @author liulongbiao
 * @created 2015年1月19日 下午3:26:29
 * @since v3.2.2
 */
public class DupQuestionQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3195420019118755653L;
	private Long schoolStageId; // 学段ID
	private Long subjectId; // 学科ID

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
