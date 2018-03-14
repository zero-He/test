/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.dao.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.inc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.question.analysis.mongo.dao.IMatNodeKlgCounterDao;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgCounter;

/**
 * @author liulongbiao
 *
 */
@Repository
public class MatNodeKlgCounterDao implements IMatNodeKlgCounterDao {
	private static final int MAX_KIDS = 10;
	@Autowired
	private MongoDatabase db;
	private MongoCollection<MatNodeKlgCounter> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection("analysis.matnodeklg.counter", MatNodeKlgCounter.class);
	}

	@Override
	public List<MatNodeKlgCounter> findAssocKlgIds(Long materialNodeId) {
		return coll.find(eq("mnid", materialNodeId)).sort(descending("count")).limit(MAX_KIDS)
				.into(new ArrayList<>());
	}

	@Override
	public void clear() {
		coll.drop();
		coll.createIndex(Indexes.ascending("mnid"));
	}

	@Override
	public void add(Long materialNodeId, Long knowledgeId, int count) {
		Bson filter = and(eq("mnid", materialNodeId), eq("kid", knowledgeId));
		Bson update = inc("count", count);
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}
}
