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
import static com.mongodb.client.model.Updates.addToSet;
import static com.mongodb.client.model.Updates.pull;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.question.duplication.QueDupCst;
import cn.strong.leke.question.duplication.model.SimQue;
import cn.strong.leke.question.duplication.service.SimQueService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class SimQueServiceImpl implements SimQueService {

	@Autowired
	private MongoDatabase db;
	private MongoCollection<SimQue> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection(QueDupCst.COLL_SIM_QUES, SimQue.class);
	}

	@Override
	public SimQue getById(Long questionId) {
		return coll.find(eq("_id", questionId)).first();
	}

	@Override
	public void removeById(Long questionId) {
		coll.deleteOne(eq("_id", questionId));
	}

	@Override
	public FindIterable<SimQue> findContains(Long questionId) {
		return coll.find(eq("simIds", questionId));
	}

	@Override
	public void addRef(Long questionId, Long simId) {
		coll.updateOne(eq("_id", questionId), addToSet("simIds", simId),
				new UpdateOptions().upsert(true));
	}

	@Override
	public void removeRef(Long questionId, Long simId) {
		coll.updateOne(eq("_id", questionId), pull("simIds", simId));
	}
}
