/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.service.HomeworkMainService;

public class OnlinetestListener extends AbstractRabbitMQListener {

	@Resource
	private HomeworkMainService homeworkMainService;

	@Override
	public void handleMessage(Object object) throws Exception {
		Long dataId = (Long) object;
		
		// 1. 确定随堂作业ID，将确定的ID写入targetHomeworkId，如果该字段已经写入则忽略（幂等）
		this.homeworkMainService.resolveOnlineSubmitHomeworkWithTx(dataId);
		// 2. 随堂作业写入
		this.homeworkMainService.handleOnlineSubmitMessageWithTx(dataId);
	}

}
