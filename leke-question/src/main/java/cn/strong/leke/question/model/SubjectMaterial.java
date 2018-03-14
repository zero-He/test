/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import java.util.List;

/**
 *
 * 描述:
 *
 * @author  lavender
 * @created 2015年1月16日 下午4:13:47
 * @since   v1.0.0
 */
public class SubjectMaterial {

	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;

	private Long subjectId;
	private String subjectName;
	private List<MaterialAmount> materialQuestionAmounts;
	
	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}
	
	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	/**
	 * @return the materialQuestionAmounts
	 */
	public List<MaterialAmount> getMaterialQuestionAmounts() {
		return materialQuestionAmounts;
	}
	
	/**
	 * @param materialQuestionAmounts the materialQuestionAmounts to set
	 */
	public void setMaterialQuestionAmounts(List<MaterialAmount> materialQuestionAmounts) {
		this.materialQuestionAmounts = materialQuestionAmounts;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
