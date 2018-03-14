/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.type;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.online.model.DeviceOnlineUser;
import cn.strong.leke.monitor.mongo.online.service.IDeviceOnlineUserDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class DeviceOnlineUserDao implements IDeviceOnlineUserDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<DeviceOnlineUser> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("onlineuser.device", DeviceOnlineUser.class);
	}

	@Override
	public void save(DeviceOnlineUser user) {
		if (user == null || user.getUserId() == null || user.getD() == null) {
			return;
		}
		if (user.getTs() == null) {
			user.setTs(new Date());
		}
		Bson filter = and(eq("userId", user.getUserId()), eq("d", user.getD()));
		Bson update = BsonUtils.toUpdateSetDoc(user);
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public Set<Long> getActiveSchoolIdsSince(Date ts) {
		Bson filter = and(gte("ts", ts), type("schoolId", BsonType.INT64));
		return coll.distinct("schoolId", filter, Long.class).into(new HashSet<>());
	}

	@Override
	public Set<Long> findActiveUserIdsSince(Date ts, long schoolId) {
		Bson filter = and(gte("ts", ts), eq("schoolId", schoolId));
		return coll.find(filter, Document.class).projection(include("userId")).map(doc -> {
			return doc.getLong("userId");
		}).into(new HashSet<>());
	}

	@Override
	public long countActiveUsersSince(Date ts, long schoolId, int d) {
		Bson filter = and(gte("ts", ts), eq("schoolId", schoolId), eq("d", d));
		return coll.count(filter);
	}
	
	@Override
	public List<DeviceOnlineUser> getActiveUser() {
		Bson filter = and(gte("ts", LocalDate.now().minusDays(1).toDate()),
				lte("ts", new LocalDate(new Date()).toDate()));
		return coll.find(filter).into(new ArrayList<>());
	}

}
