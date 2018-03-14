/**
 * 
 */
package cn.strong.leke.monitor.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.monitor.core.service.IMleService;
import cn.strong.leke.monitor.listener.model.MleMsg;
import cn.strong.leke.monitor.mongo.model.MleRecord;
import cn.strong.leke.monitor.mongo.service.IMleRecordService;

/**
 * @author liulongbiao
 *
 */
@Service
public class MleService implements IMleService {

	@Autowired
	private IMleRecordService recordService;

	@Override
	public void add(MleMsg msg) {
		MleRecord record = new MleRecord();
		record.setQueueName(msg.getQueueName());
		record.setMessageBody(msg.getMessageBody());
		record.setMessageClass(msg.getMessageClass());
		record.setExceptionStack(msg.getExceptionStack());
		record.setIpaddress(msg.getIpaddress());
		record.setTs(new Date(msg.getCreatedOn()));
		recordService.add(record);
	}

}
