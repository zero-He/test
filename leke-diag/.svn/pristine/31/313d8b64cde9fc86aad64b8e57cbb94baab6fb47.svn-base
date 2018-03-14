package cn.strong.leke.diag.dao.incentive.mongo;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.include;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class UserIncentiveDao implements InitializingBean {

	private static final String COLL_NAME = "user.incentive";

	@Resource(name = "incentiveDb")
	private MongoDatabase db;
	private MongoCollection<Document> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, Document.class);
	}

	/**
	 * 获取用户各个激励类型的乐币获取情况
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<Long, Integer> findTypeLekeValByUserId(Long userId, Date start, Date end) {
		Bson $match = match(and(eq("userId", userId), ne("lekeChangeVal", 0), gt("createdOn", start), lt("createdOn", end)));
		Bson $project = project(and(include("typeId", "lekeChangeVal"), exclude("_id")));
		Bson $group = group("$typeId", sum("lekeVal", "$lekeChangeVal"));
		List<Bson> pipeline = Arrays.asList($match, $project, $group);
		List<Document> documents = this.coll.aggregate(pipeline).into(new ArrayList<>());
		return documents.stream().collect(toMap(doc -> doc.getLong("_id"), doc -> doc.getInteger("lekeVal")));
	}
}
