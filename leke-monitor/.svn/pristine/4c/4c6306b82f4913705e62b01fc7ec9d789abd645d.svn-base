package cn.strong.leke.monitor.mq.listener;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.rabbit.core.MQListenerTemplate;
import cn.strong.leke.model.monitor.ResourceUsedDetail;
import cn.strong.leke.monitor.mongo.resource.IResourceUsedDao;

/**
 *
 * 描述: 资源使用消息
 *
 * @author raolei
 * @created 2017年11月7日 下午8:04:53
 * @since v1.0.0
 */
@Component
public class ResourceUsedListener {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceUsedListener.class);

	@Value("${mq.q.monitor.resource.used}")
	private String queue;
	@Resource
	private MQListenerTemplate template;
	@Resource
	private IResourceUsedDao resourceUsedDao;


	@RabbitListener(queues = { "${mq.q.monitor.resource.used}" })
	public void handle(List<ResourceUsedDetail> msg) {
		LOG.info(StringUtils.join(msg, ","));
		template.execute(queue, msg, () -> {
			resourceUsedDao.save(msg);
		});
	}
}
