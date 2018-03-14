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
import cn.strong.leke.monitor.mongo.model.SqlRecord;
import cn.strong.leke.monitor.mongo.model.query.SqlRecordQuery;
import cn.strong.leke.monitor.mongo.model.query.SqlStat;
import cn.strong.leke.monitor.mongo.service.ISqlRecordService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class SqlRecordService implements ISqlRecordService {
	public static final Document STAT_GROUP;
	public static final Document STAT_PROJECT;

	static {
		Document grp = new Document("_id", "$hash").append("count", new Document("$sum", 1))
				.append("maxCostTime", new Document("$max", "$costTime"))
				.append("totalCostTime", new Document("$sum", "$costTime"));
		STAT_GROUP = new Document("$group", grp);
		Document prj = new Document("_id", 0).append("hash", "$_id").append("count", 1)
				.append("maxCostTime", 1).append("totalCostTime", 1);
		STAT_PROJECT = new Document("$project", prj);
	}

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<SqlRecord> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("sql.record", SqlRecord.class);
	}

	@Override
	public void add(SqlRecord record) {
		record.setId(new ObjectId().toString());
		coll.insertOne(record);
	}

	@Override
	public List<SqlRecord> findSqlRecords(SqlRecordQuery query, Page page) {
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
	public SqlStat getSqlStat(SqlRecordQuery query) {
		if (query == null) {
			throw new IllegalArgumentException("query should not be null.");
		}
		Document filter = query.toBSON();
		Bson match = new Document("$match", filter);
		return coll.aggregate(Arrays.asList(match, STAT_GROUP, STAT_PROJECT), SqlStat.class)
				.first();
	}
}
