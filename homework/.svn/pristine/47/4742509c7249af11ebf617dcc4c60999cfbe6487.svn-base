/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import cn.strong.leke.homework.util.HomeworkCst;

/**
 *
 * 描述:
 *
 * @author  DuanYanming
 * @created 2014年6月11日 下午5:23:19
 * @since   v1.0.0
 */
public class MyHomeworkDTO extends HomeworkDTO {

	private static final long serialVersionUID = 8112731525137035426L;

	private Long homeworkDtlId; // 学生作业标识
	private Long studentId; // 学生ID
	private Date submitTime; // 提交时间
	private Integer submitStatus; // 提交状态
	private Date correctTime; // 批改时间
	private BigDecimal score; // 作业总分
	private BigDecimal scoreRate;
	private Integer bugFixStage;//订正状态

	// 截止时间
	private Date closeEndTime;
	//截至提交时间
	private Date submitEndTime;
	// 批改情况
	private String correctString;

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitTimeString() {
		if (this.submitTime == null) {
			return "暂无";
		}
		return DateDistance.getDistanceTimes(this.submitTime, new Date());
	}

	public Integer getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}

	public Date getCorrectTime() {
		return correctTime;
	}

	public void setCorrectTime(Date correctTime) {
		this.correctTime = correctTime;
	}

	public String getHomeworkStatus() {
		if(this.getResType() == null) {
			return "";
		}
		if (submitStatus == null || submitStatus == 0) {
			if (this.getResType() == 3) {
				return "未提交";
			} else {
				return "未学习";
			}
		} else {
			if (this.getResType() == 3) {
				if (correctTime == null) {
					return "待批改";
				} else {
					if (this.getBugFixStage().equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX)) {
						return "待订正";
					} else if (this.getBugFixStage().equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_FINISH)) {
						return "订正通过";
					} else if (this.getBugFixStage().equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW)) {
						return "已订正";
					} else {
						//已批改
						return score.setScale(1, RoundingMode.HALF_UP).toString();
					}
				}
			} else {
				return "已学习";
			}
		}
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Date getCloseEndTime() {
		return closeEndTime;
	}

	public void setCloseEndTime(Date closeEndTime) {
		this.closeEndTime = closeEndTime;
	}

	public Date getSubmitEndTime() {
		return submitEndTime;
	}

	public void setSubmitEndTime(Date submitEndTime) {
		this.submitEndTime = submitEndTime;
	}

	public String getCorrectString() {
		return correctString;
	}

	public void setCorrectString(String correctString) {
		this.correctString = correctString;
	}

	public boolean isCanDoWork() {
		if (this.getCloseTime() == null) {
			return true;
		}
		return new Date().before(this.getCloseTime());
	}

	/**
	 * @return the scoreRate
	 */
	public BigDecimal getScoreRate() {
		return scoreRate;
	}

	/**
	 * @param scoreRate the scoreRate to set
	 */
	public void setScoreRate(BigDecimal scoreRate) {
		this.scoreRate = scoreRate;
	}

	/**
	 * @return the bugFixStage
	 */
	public Integer getBugFixStage() {
		return bugFixStage;
	}

	/**
	 * @param bugFixStage the bugFixStage to set
	 */
	public void setBugFixStage(Integer bugFixStage) {
		this.bugFixStage = bugFixStage;
	}
	
	public String getCorrectStatusStr() {
		// 未提交不显示+
		if (this.getSubmitStatus() == null || this.getSubmitStatus() == 0) {
			return "";
		}
		// 已经提交，但未批改的作业显示+
		if (null == this.getCorrectTime()) {
			return "+";
		}
		return "";
	}
}
