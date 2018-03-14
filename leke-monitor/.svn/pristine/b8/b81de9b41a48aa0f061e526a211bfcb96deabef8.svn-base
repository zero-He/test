/**
 * 
 */
package cn.strong.leke.monitor.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.monitor.core.service.IExceptionService;
import cn.strong.leke.monitor.listener.model.ExceptionMsg;
import cn.strong.leke.monitor.mongo.model.ExceptionRecord;
import cn.strong.leke.monitor.mongo.service.IExceptionRecordService;

/**
 * @author liulongbiao
 *
 */
@Service
public class ExceptionService implements IExceptionService {

	@Autowired
	private IExceptionRecordService recordService;

	@Override
	public void add(ExceptionMsg msg) {
		ExceptionRecord record = new ExceptionRecord();
		record.setType(msg.getType());
		record.setTs(new Date(msg.getCreatedTime()));
		record.setMessage(msg.getMessage());
		record.setStack(msg.getStack());
		record.setIp(msg.getIp());
		recordService.add(record);
	}

}
