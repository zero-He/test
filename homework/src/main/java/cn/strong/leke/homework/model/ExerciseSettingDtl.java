package cn.strong.leke.homework.model;

import cn.strong.leke.model.BaseModel;

/**
 * 实体对象：练习设置明细表
 */
public class ExerciseSettingDtl extends BaseModel {

	private static final long serialVersionUID = 3321256676843954899L;

	// 练习题型ID
	private Long settingDtlId;
	// 练习设置标识
	private Long settingId;
	// 学科标识
	private Long subjectId;
	// 学科名字
	private String subjectName;
	// 题目数量
	private Integer questionNum;
	
	
	/**
	 * 练习题型ID
	 */
	public Long getSettingDtlId() {
		return this.settingDtlId;
	}

	/**
	 * 练习题型ID
	 */
	public void setSettingDtlId(Long settingDtlId) {
		this.settingDtlId = settingDtlId;
	}
	
	/**
	 * 练习设置标识
	 */
	public Long getSettingId() {
		return this.settingId;
	}

	/**
	 * 练习设置标识
	 */
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}
	
	/**
	 * 学科标识
	 */
	public Long getSubjectId() {
		return this.subjectId;
	}

	/**
	 * 学科标识
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	/**
	 * 题目数量
	 */
	public Integer getQuestionNum() {
		return this.questionNum;
	}

	/**
	 * 题目数量
	 */
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}