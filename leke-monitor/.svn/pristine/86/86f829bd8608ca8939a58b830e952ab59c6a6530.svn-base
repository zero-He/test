/**
 * 
 */
package cn.strong.leke.monitor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.common.utils.ObjectMapperUtils;
import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.monitor.core.service.IJobExService;
import cn.strong.leke.monitor.listener.model.JobExMsg;

/**
 * @author liulongbiao
 *
 */
public class JobExListener extends AbstractRabbitMQListener {
	private static Logger LOG = LoggerFactory.getLogger(ExceptionListener.class);
	@Autowired
	private IJobExService jobExService;

	@Override
	public void handleMessage(Object obj) {
		try {
			JobExMsg msg = ObjectMapperUtils.convertValue(obj, JobExMsg.class);
			jobExService.add(msg);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
