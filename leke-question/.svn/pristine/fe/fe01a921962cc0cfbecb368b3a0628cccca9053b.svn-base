/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.model;

import java.util.Date;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.model.question.querys.BaseQuestionQuery;

/**
 *
 * 描述:
 *
 * @author  liulb
 * @created 2014年5月20日 下午4:20:39
 * @since   v1.0.0
 */
public class StatisQuestionQuery extends BaseQuestionQuery {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -1150343493287916310L;
	private Long operatorId; // 操作人员ID
	private Integer operateType; // 操作类型：录入、审核、校对
	private Date operateDate; // 操作日期
	private Date minOperateDate; // 最小操作日期
	private Date maxOperateDate; // 最大操作日期
	private Integer statusType; // 查询状态类型
	private Long materialNodeId; // 教材节点ID

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
		if (operateDate != null) {
			this.minOperateDate = operateDate;
			this.maxOperateDate = DateUtils.addDays(operateDate, 1);
		}
	}

	public Date getMinOperateDate() {
		return minOperateDate;
	}

	public void setMinOperateDate(Date minOperateDate) {
		this.minOperateDate = minOperateDate;
	}

	public Date getMaxOperateDate() {
		return maxOperateDate;
	}

	public void setMaxOperateDate(Date maxOperateDate) {
		this.maxOperateDate = maxOperateDate;
	}

	public Integer getStatusType() {
		return statusType;
	}

	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}

	public Long getMaterialNodeId() {
		return materialNodeId;
	}

	public void setMaterialNodeId(Long materialNodeId) {
		this.materialNodeId = materialNodeId;
	}

}
