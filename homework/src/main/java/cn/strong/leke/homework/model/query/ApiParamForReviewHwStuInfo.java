/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model.query;

/**
 * api
 * params:复批作业学生列表  的参数
 * @author Zhang Fujun
 * @date 2016年4月18日
 */
public class ApiParamForReviewHwStuInfo {

	// 老师作业ID
	private Long homeworkId;

	/**
	 * 排序规则
	 * 0 | null  :默认排序: 订正时间 升序
	 * 1：成绩按倒序排
	 */
	private Integer sort;

	/**
	 * @return the homeworkId
	 */
	public Long getHomeworkId() {
		return homeworkId;
	}

	/**
	 * @param homeworkId the homeworkId to set
	 */
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
