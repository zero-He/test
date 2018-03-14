/**
 * 
 */
package cn.strong.leke.monitor.listener;

import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.monitor.msg.CsetStusChangeMsg;
import cn.strong.leke.monitor.core.service.course.ICourseService;

/**
 * 套课学生名单变更监听器
 * 
 * @author liulongbiao
 *
 */
public class CsetStusChangeListener extends AbstractRabbitMQListener {

	@Autowired
	private ICourseService courseService;

	@Override
	public void handleMessage(Object obj) throws Exception {
		Validation.isInstanceOf(CsetStusChangeMsg.class, obj, "传入的消息不是 CsetStusChangeMsg 类型的");
		CsetStusChangeMsg msg = (CsetStusChangeMsg) obj;
		courseService.handle(msg);
	}

}
