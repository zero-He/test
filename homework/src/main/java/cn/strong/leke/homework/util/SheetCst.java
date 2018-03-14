package cn.strong.leke.homework.util;

import java.util.Comparator;

import cn.strong.leke.homework.model.SheetPage;

public interface SheetCst {

	// 数据检查
	int STEP_CHECK = 3;

	// 数据合并
	int STEP_MERGE = STEP_CHECK + 1;

	// 数据匹配
	int STEP_MATCH = STEP_MERGE + 1;

	// 数据解析
	int STEP_PARSE = STEP_MATCH + 1;

	// 数据写入
	int STEP_WRITE = STEP_PARSE + 1;

	// 完成任务
	int STEP_FINISH = STEP_WRITE + 1;

	// 数据上传中
	int STATUS_UPLOADING = 0;

	// 数据处理中
	int STATUS_PROCESSING = 1;

	// 上传结束无异常
	int STATUS_FINISH_NOERROR = 2;

	// 上传结束有异常
	int STATUS_FINISH_HASERROR = 3;

	// 上传结束无数据
	int STATUS_FINISH_NODATA = 4;

	Comparator<SheetPage.Block> COMP_BLOCK = (a, b) -> Integer.compare(Integer.parseInt(a.getPageId()),
			Integer.parseInt(b.getPageId()));
}
