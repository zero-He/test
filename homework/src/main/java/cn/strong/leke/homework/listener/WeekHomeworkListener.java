package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.model.HomeworkPaperMQ;
import cn.strong.leke.homework.service.IWeekMonthHomeworkService;

/**
 * 周错题作业生成监听
 * @author Zhang Fujun
 * @date 2016年11月14日
 */
public class WeekHomeworkListener extends AbstractRabbitMQListener {

	@Resource
	private IWeekMonthHomeworkService weekMonthHomeworkService;

	@Override
	public void handleMessage(Object object) throws Exception {
		HomeworkPaperMQ weekHomeworkMQ = (HomeworkPaperMQ) object;
		this.weekMonthHomeworkService.saveClassStuWrongHomework(weekHomeworkMQ.getClassId(),
				weekHomeworkMQ.getSchoolId(), weekHomeworkMQ.getType());
	}
}
