package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.manage.HolidayHwMicroService;
import cn.strong.leke.homework.model.HolidayHwMQ;

/**
 * 生成寒暑假作业监听
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
public class HolidayHwListener extends AbstractRabbitMQListener {

	@Resource
	private HolidayHwMicroService holidayHwMicroService;

	@Override
	public void handleMessage(Object object) throws Exception {
		HolidayHwMQ mq = (HolidayHwMQ) object;
		this.holidayHwMicroService.executeHolidayHw(mq);
	}
}
