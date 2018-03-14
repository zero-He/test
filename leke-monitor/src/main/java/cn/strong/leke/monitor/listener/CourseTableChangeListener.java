/**
 * 
 */
package cn.strong.leke.monitor.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.monitor.msg.CourseTableChangeMsg;
import cn.strong.leke.monitor.core.service.course.ICourseService;

/**
 * 课表变更消息监听
 * 
 * @author liulongbiao
 *
 */
public class CourseTableChangeListener extends AbstractRabbitMQListener {
	@Autowired
	private ICourseService courseService;

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Object obj) throws Exception {
		Validation.isInstanceOf(List.class, obj, "传入的消息需为 List 类型");
		List<CourseTableChangeMsg> msgs = (List<CourseTableChangeMsg>) obj;
		courseService.handleCourseTableChangeMsgs(msgs);
	}
}
