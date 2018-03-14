/**
 * 
 */
package cn.strong.leke.monitor.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.monitor.core.service.ISqlService;
import cn.strong.leke.monitor.listener.model.SqlMsg;
import cn.strong.leke.monitor.mongo.model.SqlHash;
import cn.strong.leke.monitor.mongo.model.SqlRecord;
import cn.strong.leke.monitor.mongo.model.updates.SqlDailyDelta;
import cn.strong.leke.monitor.mongo.service.ISqlDailyService;
import cn.strong.leke.monitor.mongo.service.ISqlHashService;
import cn.strong.leke.monitor.mongo.service.ISqlRecordService;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * @author liulongbiao
 *
 */
@Service
public class SqlService implements ISqlService {
	@Autowired
	private ISqlHashService hashService;
	@Autowired
	private ISqlRecordService recordService;
	@Autowired
	private ISqlDailyService dailyService;

	@Override
	public void add(SqlMsg msg) {
		SqlHash sqlHash = SqlHash.of(msg.getSqlId(), msg.getClassName(), msg.getSql());
		hashService.save(sqlHash);

		SqlRecord record = new SqlRecord();
		record.setHash(sqlHash.getHash());
		record.setTs(new Date(msg.getCreatedTime()));
		record.setCostTime(msg.getCostTime());
		record.setParams(msg.getParams());
		record.setIp(msg.getIp());
		recordService.add(record);

		Date date = new Date(msg.getCreatedTime());
		int day = StatUtils.ofDay(date);

		SqlDailyDelta delta = new SqlDailyDelta();
		delta.setDay(day);
		delta.setHash(sqlHash.getHash());
		try {
			String serverName = getServerName(msg.getClassName());
			delta.setServerName(serverName);
		} catch (Exception e) {
			// ignore serverName
		}

		delta.setSqlId(msg.getSqlId());
		delta.setClassName(msg.getClassName());
		delta.setCostTime(msg.getCostTime());
		dailyService.update(delta);
	}

	private static final String PRE_LEKE = "cn.strong.leke.";
	private static final int PRE_LEKE_IDX = PRE_LEKE.length();
	private static final String POST_LEKE = ".leke.cn";
	private static final String PRE_LIUXUE = "com.liuxue.";
	private static final String DM_LIUXUE = "liuxue.cn";

	private static String getServerName(String className) {
		if (className.startsWith(PRE_LEKE)) {
			String subdomain = className.substring(PRE_LEKE_IDX,
					className.indexOf('.', PRE_LEKE_IDX));
			return subdomain + POST_LEKE;
		} else if (className.startsWith(PRE_LIUXUE)) {
			return DM_LIUXUE;
		} else {
			throw new IllegalArgumentException("unknown serverName for class " + className);
		}
	}
}
