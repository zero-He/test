/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.dao.impl;

import static com.mongodb.client.model.Filters.eq;

import javax.annotation.PostConstruct;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.question.analysis.mongo.dao.IMatNodeKlgsDao;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgs;

/**
 * @author liulongbiao
 *
 */
@Repository
public class MatNodeKlgsDao implements IMatNodeKlgsDao {
	@Autowired
	private MongoDatabase db;
	private MongoCollection<MatNodeKlgs> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection("analysis.matnodeklgs", MatNodeKlgs.class);
	}

	@Override
	public void save(MatNodeKlgs data) {
		Bson update = BsonUtils.toUpdateSetDoc(data);
		coll.updateOne(eq("_id", data.getMaterialNodeId()), update,
				new UpdateOptions().upsert(true));
	}

	@Override
	public MatNodeKlgs getById(Long materialNodeId) {
		return coll.find(eq("_id", materialNodeId)).first();
	}
}
