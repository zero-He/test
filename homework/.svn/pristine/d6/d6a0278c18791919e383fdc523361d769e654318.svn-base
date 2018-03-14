/*
 * Copyright (c) 2016 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.pushEach;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.homework.model.mongo.HomeworkProgress;
import cn.strong.leke.homework.model.mongo.HomeworkProgress.StudentTs;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

/**
 *
 * 描述:
 *
 * @author  raolei
 * @created 2016年3月7日 下午2:14:49
 * @since   v1.0.0
 */
@Repository
public class HomeworkProgressDao implements InitializingBean {

	@Autowired
	private MongoDatabase db;
	private MongoCollection<HomeworkProgress> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection("howework.progress", HomeworkProgress.class);
	}

	/**
	 * 保存动态信息
	 * @param progress
	 */
	public void saveHomeworkProgress(HomeworkProgress progress) {
		this.coll.insertOne(progress);
	}

	/**
	 * 保存心跳，返回更新是否成功。
	 * @param homeworkId
	 * @param studentId
	 * @return
	 */
	public boolean saveHeartbeat(Long homeworkId, Long studentId) {
		long time = System.currentTimeMillis() - 29 * 1000;
		Bson filter = and(eq("_id", homeworkId),
				elemMatch("unfinished", and(eq("studentId", studentId), lte("ts", new Date(time)))));
		Bson update = new Document("$set", new Document("unfinished.$.ts", new Date())).append("$inc",
				new Document("unfinished.$.used", 30));
		UpdateResult result = coll.updateOne(filter, update);
		return result.getModifiedCount() > 0;
	}

	/**
	 * 更新学生动态到已完成。
	 * @param homeworkId
	 * @param studentId
	 * @param used
	 */
	public void updateToFinished(Long homeworkId, Long studentId, Integer used) {
		Bson filter = and(eq("_id", homeworkId), eq("unfinished.studentId", studentId));
		Bson update = new Document("$pull", new Document("unfinished", new Document("studentId", studentId))).append(
				"$push", new Document("finished", new Document("studentId", studentId).append("used", used).append("ts", new Date())));
		coll.updateOne(filter, update);
	}

	/**
	 * 追加学生到未完成
	 * @param homeworkId
	 * @param ts
	 */
	public void appendUnFinished(Long homeworkId, StudentTs ts) {
		Bson filter = eq("_id", homeworkId);
		Bson update = and(pushEach("unfinished", Arrays.asList(BsonUtils.toBsonValue(ts))), inc("totalNum", 1));
		this.coll.updateOne(filter, update);
	}

	/**
	 * 查询一个学生的动态信息。
	 * @param homeworkId
	 * @param studentId
	 * @return
	 */
	public HomeworkProgress getStudentTs(Long homeworkId, Long studentId) {
		Bson filter = eq("_id", homeworkId);
		Bson projection = and(elemMatch("unfinished", eq("studentId", studentId)),
				elemMatch("finished", eq("studentId", studentId)));
		return coll.find(filter).projection(projection).first();
	}

	/**
	 * 根据作业ID获取作业动态信息
	 * @param homeworkId
	 * @return
	 */
	public HomeworkProgress getProgressById(Long homeworkId) {
		return coll.find(eq("_id", homeworkId)).first();
	}

}
