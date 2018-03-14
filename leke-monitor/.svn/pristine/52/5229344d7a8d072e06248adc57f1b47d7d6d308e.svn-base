/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.monitor.mongo.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.model.AccessUrl;
import cn.strong.leke.monitor.mongo.service.IAccessUrlService;

/**
 *
 *
 * @author  liulongbiao
 */
@Service
public class AccessUrlService implements IAccessUrlService {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<AccessUrl> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("access.url", AccessUrl.class);
	}

	@Override
	public void save(AccessUrl url) {
		if (url == null || StringUtils.isEmpty(url.getServerName())
				|| StringUtils.isEmpty(url.getServletPath())) {
			throw new IllegalArgumentException("URL信息不完整");
		}
		int type = AccessUrl.TYPE_NORMAL;
		String servletPath = url.getServletPath();
		if (servletPath.startsWith("/api/n")) {
			type = AccessUrl.TYPE_API_N;
		}
		if (servletPath.startsWith("/api/w")) {
			type = AccessUrl.TYPE_API_W;
		}
		url.setType(type);
		
		Bson filter = and(eq("serverName", url.getServerName()),
				eq("servletPath", url.getServletPath()));
		Document update = new Document("$set", new Document("name",
				url.getName()).append("type", url.getType()));
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public List<AccessUrl> findAll() {
		return coll.find().into(new ArrayList<>());
	}
}
