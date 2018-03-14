/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model.question;

import cn.strong.leke.model.BaseModel;

/**
 * 
 * 描述: 教师题目收藏
 * 
 * @author liulb
 * @created 2014年6月23日 下午1:42:06
 * @since v1.0.0
 */
public class TeacherQuestion extends BaseModel {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -3933275220337343106L;
	private Long teacherQuestionId; // 主键
	private Long teacherId; // 教师ID
	private Long questionId; // 题目ID

	public Long getTeacherQuestionId() {
		return teacherQuestionId;
	}

	public void setTeacherQuestionId(Long teacherQuestionId) {
		this.teacherQuestionId = teacherQuestionId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
