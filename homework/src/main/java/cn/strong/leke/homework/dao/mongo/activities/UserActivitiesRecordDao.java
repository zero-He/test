package cn.strong.leke.homework.dao.mongo.activities;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.strong.leke.homework.model.activities.UserActivitiesRecord;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

/**
 * @author Administrator
 *
 */
@Repository
public class UserActivitiesRecordDao {
	@Resource
	private MongoDatabase db;
	private MongoCollection<UserActivitiesRecord> coll;

	@PostConstruct
	public void afterPropertiesSet() {
		Assert.notNull(db);
		coll = db.getCollection("howework.activities.UserActivitiesRecord", UserActivitiesRecord.class);
	}
	
	public void updateUserActivitiesRecord(UserActivitiesRecord userActivitiesRecord) {
		Bson filter = eq("userId", userActivitiesRecord.getUserId());
		Bson update = combine(
				set("userName", userActivitiesRecord.getUserName()),
				set("missionChain", userActivitiesRecord.getMissionChain()));
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	public UserActivitiesRecord getUserActivitiesRecord(Long userId) {
		Bson filter = eq("userId", userId);
		//coll.deleteOne(filter);
		return coll.find(filter).first();
	}
	
	public List<UserActivitiesRecord> findAll(){
		return coll.find().into(new ArrayList<>());
	}
}
