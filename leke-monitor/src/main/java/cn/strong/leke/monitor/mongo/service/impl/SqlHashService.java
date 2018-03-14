/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.service.impl;

import static com.mongodb.client.model.Filters.eq;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.model.SqlHash;
import cn.strong.leke.monitor.mongo.service.ISqlHashService;

/**
 * 
 * 
 * @author liulongbiao
 */
@Service
public class SqlHashService implements ISqlHashService {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<SqlHash> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("sql.hash", SqlHash.class);
	}

	@Override
	public void save(SqlHash sqlHash) {
		if (sqlHash == null || StringUtils.isEmpty(sqlHash.getHash())) {
			throw new IllegalArgumentException("sqlHash to save is not valid.");
		}
		Document doc = BsonUtils.toBSON(sqlHash);
		doc.remove("_id");
		coll.updateOne(eq("_id", sqlHash.getHash()), new Document("$set", doc),
				new UpdateOptions().upsert(true));
	}

	@Override
	public SqlHash getSqlHash(String hash) {
		return coll.find(eq("_id", hash)).first();
	}
}
