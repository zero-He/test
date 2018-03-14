package cn.strong.leke.dwh.dao.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class SparkResultDao implements InitializingBean {

	private static final String COLL_NAME = "spark.result";

	@Resource
	private MongoDatabase db;
	private MongoCollection<Document> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, Document.class);
	}

	/**
	 * 查询Spark结果对象
	 * @param id
	 * @param resultClass
	 * @return
	 */
	public  <T extends Serializable> T getResult(String id, Class<T> resultClass) {
		Bson filter = eq("_id", id);
		return this.coll.find(filter, resultClass).first();
	}
}