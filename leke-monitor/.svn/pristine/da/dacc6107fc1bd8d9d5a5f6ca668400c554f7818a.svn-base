/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.service.impl;

import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.model.MleRecord;
import cn.strong.leke.monitor.mongo.model.query.MleRecordQuery;
import cn.strong.leke.monitor.mongo.service.IMleRecordService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class MleRecordService implements IMleRecordService {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<MleRecord> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("mle.record", MleRecord.class);
	}

	@Override
	public void add(MleRecord record) {
		record.setId(new ObjectId().toString());
		coll.insertOne(record);
	}

	@Override
	public List<MleRecord> findMleRecords(MleRecordQuery query, Page page) {
		if (query == null || page == null) {
			throw new IllegalArgumentException("query or page should not be null.");
		}

		Document filter = query.toBSON();
		int total = (int) coll.count(filter);
		page.setTotalSize(total);

		if (total <= 0) {
			return Collections.emptyList();
		}
		return coll.find(filter).sort(descending("ts")).skip(page.getOffset()).limit(page.getLimit())
				.into(new ArrayList<>());
	}
}
