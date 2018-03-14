package cn.strong.leke.homework.dao.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.homework.model.mongo.ExerciseRank;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

@Repository
public class ExerciseRankDao implements InitializingBean {

	private static final String COLL_NAME = "exercise.rank";

	@Autowired
	private MongoDatabase db;
	
	private MongoCollection<ExerciseRank> coll;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, ExerciseRank.class);
	}
	
	public ExerciseRank findWeekRank(Integer year,Integer week) {
		return this.coll.find(and(eq("year", year),eq("week", week),eq("isDeleted", false))).first();
	}
	
	public void saveWeekRank(ExerciseRank exerciseRank){
		Bson filter = and(eq("isDeleted", false),eq("year", exerciseRank.getYear()),eq("week", exerciseRank.getWeek()));
		Document set = BsonUtils.toBSON(exerciseRank);
		set.remove("_id");
		Document update = new Document().append("$set", set);
		this.coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

}
