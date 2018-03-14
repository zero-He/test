/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model;

import java.util.List;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-6-10 下午9:06:40
 * @since   v1.0.0
 */
public class ExerciseSettingDTO extends ExerciseSetting {

	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ExerciseSettingDtl> exerciseSettingDtlList;

	public List<ExerciseSettingDtl> getExerciseSettingDtlList() {
		return exerciseSettingDtlList;
	}

	public void setExerciseSettingDtlList(List<ExerciseSettingDtl> exerciseSettingDtlList) {
		this.exerciseSettingDtlList = exerciseSettingDtlList;
	}

}
