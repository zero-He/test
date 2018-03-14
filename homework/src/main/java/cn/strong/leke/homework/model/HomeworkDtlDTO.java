/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.homework.util.HomeworkCst;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-6-10 上午9:17:53
 * @since   v1.0.0
 */

public class HomeworkDtlDTO extends HomeworkDtl {
	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;

	private String homeworkName;

	private Integer paperQuestionNum;


	public String getSubmitTimeStr() {
		if (null == super.getSubmitTime()) {
			return "未提交";
		}
		return DateUtils.formatTime(super.getSubmitTime());
	}

	public String getCorrectStatusStr() {
		// 未提交不显示+
		if (this.getSubmitStatus() == null || this.getSubmitStatus() == 0) {
			return "";
		}
		// 已经提交，但未批改的作业显示+
		if (null == super.getCorrectTime()) {
			return "+";
		}
		return "";
	}

	public String getSubmitStatusStr() {
		if (this.getSubmitStatus() != null && this.getSubmitStatus() == HomeworkCst.HOMEWORK_SUBMIT_STATUS_DELAY) {
			return "（迟交）";
		}
		return "";
	}

	/**
	 * @return the homeworkName
	 */
	public String getHomeworkName() {
		return homeworkName;
	}

	/**
	 * @param homeworkName the homeworkName to set
	 */
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}

	/**
	 * @return the paperQuestionNum
	 */
	public Integer getPaperQuestionNum() {
		return paperQuestionNum;
	}

	/**
	 * @param paperQuestionNum the paperQuestionNum to set
	 */
	public void setPaperQuestionNum(Integer paperQuestionNum) {
		this.paperQuestionNum = paperQuestionNum;
	}

	public String getCorrectUserName() {
		if (this.getCorrectUserId() != null) {
			return UserBaseContext.getUserBaseByUserId(this.getCorrectUserId()).getUserName();
		}
		return null;
	}
}
