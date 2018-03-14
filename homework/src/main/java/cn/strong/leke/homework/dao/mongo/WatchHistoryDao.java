/*
 * Copyright (c) 2016 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;

import java.util.Date;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.model.mongo.WatchHistory;

@Repository
public class WatchHistoryDao implements InitializingBean {

	@Autowired
	private MongoDatabase db;
	private MongoCollection<WatchHistory> coll;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection("WatchHistory", WatchHistory.class);
	}

	/**
	 * 更新观看位置
	 * @param homeworkDtlId
	 * @param position
	 * @param userId
	 */
	public void updatePosition(Long homeworkDtlId, Integer position, Integer duration, Long userId) {
		Bson filter = eq("_id", homeworkDtlId);
		Document set = new Document().append("position", position).append("duration", duration)
				.append("modifiedBy", userId).append("modifiedOn", new Date());
		Document setOnInsert = new Document().append("createdBy", userId).append("createdOn", new Date());
		Document update = new Document().append("$set", set).append("$setOnInsert", setOnInsert);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	/**
	 * 获取观看位置
	 * @param homeworkDtlId
	 * @return
	 */
	public Integer getPosition(Long homeworkDtlId) {
		Bson filter = eq("_id", homeworkDtlId);
		Bson projection = include("position");
		WatchHistory watchHistory = this.coll.find(filter).projection(projection).first();
		if (watchHistory != null) {
			return watchHistory.getPosition();
		}
		return 0;
	}
}
