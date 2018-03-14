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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.monitor.mongo.model.LekeDomain;
import cn.strong.leke.monitor.mongo.service.ILekeDomainService;

/**
 *
 *
 * @author  liulongbiao
 */
@Repository
public class LekeDomainService implements ILekeDomainService {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<LekeDomain> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("leke.domain", LekeDomain.class);
	}

	@Override
	public List<LekeDomain> findAllDomains() {
		return coll.find().into(new ArrayList<LekeDomain>());
	}

	@Override
	public void save(LekeDomain domain) {
		if (domain == null || StringUtils.isEmpty(domain.getSubdomain())) {
			throw new ValidateException("mon.ex.domain.invalid");
		}
		Bson update = new Document("$set", new Document("name",
				domain.getName()));
		coll.updateOne(eq("_id", domain.getSubdomain()), update,
				new UpdateOptions().upsert(true));
	}

	@Override
	public void remove(String subdomain) {
		coll.deleteOne(eq("_id", subdomain));
	}
}
