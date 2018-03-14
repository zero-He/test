/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.monitor.mongo.model.LoginRecord;
import cn.strong.leke.monitor.mongo.service.ILoginRecordService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class LoginRecordService implements ILoginRecordService {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<LoginRecord> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("login.record", LoginRecord.class);
	}

	@Override
	public void add(LoginRecord record) {
		record.setId(new ObjectId().toString());
		coll.insertOne(record);
	}
}
