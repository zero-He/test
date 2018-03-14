/**
 * 
 */
package cn.strong.leke.monitor.mongo.appcenter.dao.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cn.strong.leke.monitor.mongo.appcenter.dao.IUserImeiDao;
import cn.strong.leke.monitor.mongo.appcenter.model.UserImei;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;

/**
 * @author liulongbiao
 *
 */
@Repository
public class UserImeiDao implements IUserImeiDao {

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<UserImei> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("appcenter.userimei", UserImei.class);

		coll.createIndex(Indexes.ascending("userId", "imei"));
	}

	@Override
	public void save(String userId, String imei) {
		coll.updateOne(and(eq("userId", userId), eq("imei", imei)), set("ts", new Date()),
				new UpdateOptions().upsert(true));
	}

	@Override
	public List<UserImei> findByUser(String userId) {
		return coll.find(eq("userId", userId)).sort(descending("ts")).into(new ArrayList<>());
	}
	@Override
	public List<UserImei> findByUser(List<String> userIds) {
		return coll.find(in("userId", userIds)).sort(descending("ts")).into(new ArrayList<>());
	}

	@Override
	public UserImei getByImei(String imei){
		return coll.find(eq("imei", imei)).first();
	}
	
	@Override
	public List<UserImei> findByImeis(String imei){
		return coll.find(eq("imei", imei)).into(new ArrayList<>());
	}
}