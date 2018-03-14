package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.manage.SheetTaskService;

public class HomeworkSheetParseListener extends AbstractRabbitMQListener {

	@Resource
	private SheetTaskService sheetTaskService;

	@Override
	public void handleMessage(Object object) throws Exception {
		this.sheetTaskService.executeSheetTask((String) object, false);
	}
}
