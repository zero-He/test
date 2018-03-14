/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.dao.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.question.analysis.mongo.dao.ISmartPaperQueryCacheDao;
import cn.strong.leke.question.analysis.mongo.model.SmartPaperQueryCache;

/**
 * @author liulongbiao
 *
 */
@Repository
public class SmartPaperQueryCacheDao implements ISmartPaperQueryCacheDao {
	@Resource
	private MongoDatabase db;
	private MongoCollection<SmartPaperQueryCache> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection("smartpaper.querycache", SmartPaperQueryCache.class);
	}

	@Override
	public void addAll(List<SmartPaperQueryCache> items) {
		if (items == null || items.isEmpty()) {
			return;
		}
		coll.insertMany(items);
	}

	@Override
	public SmartPaperQueryCache getByType(String queryId, Long typeId) {
		Bson filter = and(eq("queryId", new ObjectId(queryId)), eq("typeId",typeId));
		return coll.find(filter).first();
	}
}
