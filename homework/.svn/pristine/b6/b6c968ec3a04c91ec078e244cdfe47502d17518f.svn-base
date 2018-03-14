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

import cn.strong.leke.common.utils.DateUtils;

/**
 *
 * 描述:用于api中返回统计作业量
 *
 * @author  basil
 * @created 2014-6-13 下午2:58:52
 * @since   v1.0.0
 */
public class ApiHwCount implements Serializable{
	/**
	 * Description: 
	 */
	private static final long serialVersionUID = 1L;

	private Date date;
	
	private Integer count;

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDate() {
		return DateUtils.format(this.date, DateUtils.SHORT_DATE_PATTERN);
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
