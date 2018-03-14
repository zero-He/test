/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.mq.listener;

import java.util.List;

import javax.annotation.Resource;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.model.question.QuestionMessage;
import cn.strong.leke.question.core.question.cmd.store.IQuestionMessageHandler;
import cn.strong.leke.question.service.QuestionService;


/**
 *
 * 试题组卷次数监听器
 *
 * @author liulongbiao
 */
public class QuestionPaperCountListener extends AbstractRabbitMQListener {

	@Resource
	private QuestionService questionService;
	@Resource
	private IQuestionMessageHandler questionMessageHandler;

	@Override
	@SuppressWarnings("unchecked")
	public void handleMessage(Object obj) {
		if (obj == null) {
			return;
		}
		if (obj instanceof QuestionMessage) {
			QuestionMessage questionMessage=(QuestionMessage)obj;
			questionMessageHandler.updateQuestionMessages(questionMessage);
		} else if(obj instanceof List) {
			List<Long> questionIds = (List<Long>) obj;
			if (CollectionUtils.isEmpty(questionIds)) {
				return;
			}
			for (Long qid : questionIds) {
				questionService.updateIncUsedCount(qid);
			}
		}

	}

}
