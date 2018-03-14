package cn.strong.leke.homework.listener;

import javax.annotation.Resource;

import cn.strong.leke.core.mq.rabbit.AbstractRabbitMQListener;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.SheetWriteDto;
import cn.strong.leke.homework.service.HomeworkMainService;

public class HomeworkSheetWriteListener extends AbstractRabbitMQListener {

	@Resource
	private HomeworkMainService homeworkMainService;

	@Override
	public void handleMessage(Object object) throws Exception {
		SheetWriteDto sheetWriteDto = (SheetWriteDto) object;
		HomeworkDtlInfo homeworkDtlInfo;
		if (sheetWriteDto.getIsRepeat()) {
			homeworkDtlInfo = this.homeworkMainService.handleSheetOverrideMessageWithTx(sheetWriteDto.getBookId(),
					sheetWriteDto.getResults());
		} else {
			homeworkDtlInfo = this.homeworkMainService.handleSheetSubmitMessageWithTx(sheetWriteDto.getBookId());
		}
		if (homeworkDtlInfo != null) {
			this.homeworkMainService.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());
		}
	}
}
