/**
 * 
 */
package cn.strong.leke.monitor.listener;

import org.springframework.beans.factory.annotation.Autowired;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.monitor.msg.CourseAttendMsg;
import cn.strong.leke.monitor.core.service.course.ICourseService;

/**
 * 课堂结束考勤消息监听
 * 
 * @author liulongbiao
 *
 */
public class CourseAttendListener extends AbstractRabbitMQListener {
	@Autowired
	private ICourseService courseService;

	@Override
	public void handleMessage(Object obj) throws Exception {
		Validation.isInstanceOf(CourseAttendMsg.class, obj, "传入的消息不是 CourseAttendMsg 类型的");
		CourseAttendMsg msg = (CourseAttendMsg) obj;
		courseService.handle(msg);
	}
}
