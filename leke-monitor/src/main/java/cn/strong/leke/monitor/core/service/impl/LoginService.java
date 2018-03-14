/**
 * 
 */
package cn.strong.leke.monitor.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.monitor.core.service.ILoginService;
import cn.strong.leke.monitor.listener.model.LoginMsg;
import cn.strong.leke.monitor.mongo.model.LoginRecord;
import cn.strong.leke.monitor.mongo.service.ILoginRecordService;

/**
 * @author liulongbiao
 *
 */
@Service
public class LoginService implements ILoginService {

	@Autowired
	private ILoginRecordService recordService;

	@Override
	public void add(LoginMsg msg) {
		LoginRecord record = new LoginRecord();
		record.setUserId(msg.getUserId());
		record.setSchoolId(msg.getSchoolId());
		record.setTs(new Date(msg.getTime()));
		record.setNavigate(msg.getNavigate());
		record.setVersion(msg.getVersion());
		record.setOs(msg.getOs());
		record.setUserAgent(msg.getUserAgent());
		record.setId(msg.getIp());
		recordService.add(record);
	}

}
