/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 描述:用于筛选单课实体
 *
 * @author  basil
 * @created 2014-6-21 下午2:53:57
 * @since   v1.0.0
 */
public class CourseSingleFilterDTO implements Serializable{
	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 1. 预习
	 * 2. 随堂
	 */
	private Integer type;
	
	//开始时间范围
	private Date startTime_head;
	private Date startTime_tail;
	
	//结束时间范围
	private Date endTime_head;
	private Date endTime_tail;
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the startTime_head
	 */
	public Date getStartTime_head() {
		return startTime_head;
	}
	/**
	 * @param startTime_head the startTime_head to set
	 */
	public void setStartTime_head(Date startTime_head) {
		this.startTime_head = startTime_head;
	}
	/**
	 * @return the startTime_tail
	 */
	public Date getStartTime_tail() {
		return startTime_tail;
	}
	/**
	 * @param startTime_tail the startTime_tail to set
	 */
	public void setStartTime_tail(Date startTime_tail) {
		this.startTime_tail = startTime_tail;
	}
	/**
	 * @return the endTime_head
	 */
	public Date getEndTime_head() {
		return endTime_head;
	}
	/**
	 * @param endTime_head the endTime_head to set
	 */
	public void setEndTime_head(Date endTime_head) {
		this.endTime_head = endTime_head;
	}
	/**
	 * @return the endTime_tail
	 */
	public Date getEndTime_tail() {
		return endTime_tail;
	}
	/**
	 * @param endTime_tail the endTime_tail to set
	 */
	public void setEndTime_tail(Date endTime_tail) {
		this.endTime_tail = endTime_tail;
	}
}
