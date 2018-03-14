/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.set;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.question.duplication.QueDupCst;
import cn.strong.leke.question.duplication.model.DupJob;
import cn.strong.leke.question.duplication.service.DupJobService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class DupJobServiceImpl implements DupJobService {

	@Autowired
	private MongoDatabase db;
	private MongoCollection<DupJob> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection(QueDupCst.COLL_DUP_JOBS, DupJob.class);
	}

	@Override
	public void save(ObjectId id, int status) {
		coll.updateOne(eq("_id", id), set("status", status),
				new UpdateOptions().upsert(true));
	}

	@Override
	public DupJob getLastSuccess() {
		return coll.find(eq("status", DupJob.STATUS_SUCCESS)).sort(descending("_id")).first();
	}

}
