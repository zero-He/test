package cn.strong.leke.question.mq.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.model.wrong.HomeworkForWrongMQ;
import cn.strong.leke.question.service.IWrongQuestionService;


/**
 * 老师错题题目监听
 * @author Zhang Fujun
 * @date 2017年1月11日
 */
public class WrongQuestionListener extends AbstractRabbitMQListener {

	@Resource
	private IWrongQuestionService iwrongQuestionService;
	
	@Override
	public void handleMessage(Object object) throws Exception {
		HomeworkForWrongMQ mq = (HomeworkForWrongMQ) object;
		iwrongQuestionService.saveWrongQuestion(mq);
		
	}

}
