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
import cn.strong.leke.monitor.mongo.model.AccessRecord;
import cn.strong.leke.monitor.mongo.model.query.AccessRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.AccessStat;
import cn.strong.leke.monitor.mongo.service.IAccessRecordService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class AccessRecordService implements IAccessRecordService {

	public static final Document STAT_GROUP;
	public static final Document STAT_PROJECT;
	
	static {
		Document grp = new Document("_id",
				new Document("serverName", "$serverName").append("servletPath", "$servletPath"))
				.append("count", new Document("$sum", 1))
				.append("maxExecuteTime", new Document("$max", "$executeTime"))
				.append("totalExecuteTime", new Document("$sum", "$executeTime"));
		STAT_GROUP = new Document("$group", grp);
		Document prj = new Document("_id", 0)
				.append("serverName", "$_id.serverName")
				.append("servletPath", "$_id.servletPath").append("count", 1)
				.append("maxExecuteTime", 1).append("totalExecuteTime", 1);
		STAT_PROJECT = new Document("$project", prj);
	}
	
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<AccessRecord> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("access.record", AccessRecord.class);
	}

	@Override
	public void add(AccessRecord record) {
		record.setId(new ObjectId().toString());
		coll.insertOne(record);
	}

	@Override
	public List<AccessRecord> findAccessRecords(AccessRecordQuery query, Page page) {
		if (query == null || page == null) {
			throw new IllegalArgumentException("query or page should not be null.");
		}

		Document filter = query.toBSON();
		int total = (int) coll.count(filter);
		page.setTotalSize(total);
		
		if(total <= 0) {
			return Collections.emptyList();
		}
		return coll.find(filter).sort(descending("ts")).skip(page.getOffset())
				.limit(page.getLimit()).into(new ArrayList<>());
	}

	@Override
	public AccessStat getAccessRecordStat(AccessRecordQuery query) {
		if (query == null) {
			throw new IllegalArgumentException("query should not be null.");
		}
		Document filter = query.toBSON();
		Bson match = new Document("$match", filter);
		return coll.aggregate(Arrays.asList(match, STAT_GROUP, STAT_PROJECT),
				AccessStat.class).first();
	}
}
