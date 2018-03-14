/**
 * 
 */
package cn.strong.leke.monitor.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.monitor.core.service.IJobExService;
import cn.strong.leke.monitor.listener.model.JobExMsg;
import cn.strong.leke.monitor.mongo.model.JobExRecord;
import cn.strong.leke.monitor.mongo.service.IJobExRecordService;

/**
 * @author liulongbiao
 *
 */
@Service
public class JobExService implements IJobExService {

	@Autowired
	private IJobExRecordService recordService;

	@Override
	public void add(JobExMsg msg) {
		JobExRecord record = new JobExRecord();
		record.setType(msg.getType());
		record.setTs(new Date(msg.getTime()));
		record.setMessage(msg.getMessage());
		record.setStack(msg.getStack());
		record.setIp(msg.getIp());
		recordService.add(record);
	}

}
