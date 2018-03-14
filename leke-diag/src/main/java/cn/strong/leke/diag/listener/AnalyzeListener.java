package cn.strong.leke.diag.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.diag.service.AnalyzeService;

/**
 * 作业分析消息处理器。
 * @author  andy
 * @created 2014年8月7日 下午5:53:38
 * @since   v1.0.0
 */
public class AnalyzeListener extends AbstractRabbitMQListener {

	@Resource
	private AnalyzeService analyzeService;

	@Override
	public void handleMessage(Object object) throws Exception {
		this.analyzeService.generateHomeworkAnalyzeWithTx((Long) object);
	}
}
