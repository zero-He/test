package cn.strong.leke.monitor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.common.utils.ObjectMapperUtils;
import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.monitor.core.service.IMleService;
import cn.strong.leke.monitor.listener.model.MleMsg;

/**
 * 消息监听异常队列监听器
 * 
 * @author liulongbiao
 */
public class CommonMleListener extends AbstractRabbitMQListener {
	private static Logger LOG = LoggerFactory
			.getLogger(CommonMleListener.class);
	@Autowired
	private IMleService mleService;

	@Override
	public void handleMessage(Object obj) {
		try {
			MleMsg msg = ObjectMapperUtils.convertValue(obj, MleMsg.class);
			mleService.add(msg);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
