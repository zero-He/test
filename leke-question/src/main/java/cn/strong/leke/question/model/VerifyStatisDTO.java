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
 * 描述: 教研员题目校对
 * 
 * @author liulb
 * @created 2014年5月30日 下午1:29:46
 * @since v1.0.0
 */
public class VerifyStatisDTO {

	private Long schoolStageId; // 学段ID
	private Long gradeId; // 年级ID
	private String gradeName; // 年级名称
	private Long subjectId; // 学科ID
	private String subjectName; // 学科名称
	private Long total; // 总题量(存在已审核日志的题量)
	private Long unverified; // 未校对量(当前状态为已审核的题量)
	private Long uncorrected; // 未修正量(当前状态为纠错的题量)

	public Long getSchoolStageId() {
		return schoolStageId;
	}

	public void setSchoolStageId(Long schoolStageId) {
		this.schoolStageId = schoolStageId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getUnverified() {
		return unverified;
	}

	public void setUnverified(Long unverified) {
		this.unverified = unverified;
	}

	public Long getUncorrected() {
		return uncorrected;
	}

	public void setUncorrected(Long uncorrected) {
		this.uncorrected = uncorrected;
	}

}
