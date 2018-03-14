/**
 * 
 */
package cn.strong.leke.monitor.listener;

import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.monitor.msg.CourseTeacherStatusMsg;
import cn.strong.leke.monitor.core.service.course.ICourseService;

/**
 * @author liulongbiao
 *
 */
public class CourseTeacherStatusListener extends AbstractRabbitMQListener {
	@Autowired
	private ICourseService courseService;

	@Override
	public void handleMessage(Object obj) throws Exception {
		Validation.isInstanceOf(CourseTeacherStatusMsg.class, obj,
				"传入的消息不是 CourseTeacherStatusMsg 类型的");
		CourseTeacherStatusMsg msg = (CourseTeacherStatusMsg) obj;
		courseService.handle(msg);
	}
}
