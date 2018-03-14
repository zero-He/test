package cn.strong.leke.dwh.dao.mongo;

import java.io.Serializable;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.data.mongo.BsonUtils;

@Repository
public class SparkQueryDao implements InitializingBean {

	private static final String COLL_NAME = "spark.query";

	@Resource
	private MongoDatabase db;
	private MongoCollection<Document> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, Document.class);
	}

	/**
	 * 查询条件入库
	 * @param query
	 * @return
	 */
	public String insertQuery(Serializable query) {
		String id = new ObjectId().toHexString();
		Document document = BsonUtils.toBSON(query);
		document.append("_id", id);
		this.coll.insertOne(document);
		return id;
	}
}
