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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.model.JobExRecord;
import cn.strong.leke.monitor.mongo.model.query.JobExRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.JobExStat;
import cn.strong.leke.monitor.mongo.service.IJobExRecordService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class JobExRecordService implements IJobExRecordService {
	public static final Document STAT_GROUP;
	public static final Document STAT_PROJECT;

	static {
		Document grp = new Document("_id", "$type").append("count", new Document("$sum", 1));
		STAT_GROUP = new Document("$group", grp);
		Document prj = new Document("_id", 0).append("type", "$_id").append("count", 1);
		STAT_PROJECT = new Document("$project", prj);
	}

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<JobExRecord> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("jobex.record", JobExRecord.class);
	}

	@Override
	public void add(JobExRecord record) {
		record.setId(new ObjectId().toString());
		coll.insertOne(record);
	}

	@Override
	public List<JobExRecord> findJobExRecords(JobExRecordQuery query, Page page) {
		if (query == null || page == null) {
			throw new IllegalArgumentException("query or page should not be null.");
		}

		Document filter = query.toBSON();
		int total = (int) coll.count(filter);
		page.setTotalSize(total);

		if (total <= 0) {
			return Collections.emptyList();
		}
		return coll.find(filter).sort(descending("ts")).skip(page.getOffset())
				.limit(page.getLimit()).into(new ArrayList<>());
	}

	@Override
	public List<JobExStat> findJobExStats(JobExRecordQuery query) {
		if (query == null) {
			throw new IllegalArgumentException("query should not be null.");
		}
		Document filter = query.toBSON();
		Bson match = new Document("$match", filter);
		return coll.aggregate(Arrays.asList(match, STAT_GROUP, STAT_PROJECT), JobExStat.class)
				.into(new ArrayList<>());
	}

}
