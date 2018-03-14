/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.type;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.online.model.OnlineUser;
import cn.strong.leke.monitor.mongo.online.model.query.OnlineUserQuery;
import cn.strong.leke.monitor.mongo.online.service.IOnlineUserDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class OnlineUserDao implements IOnlineUserDao {

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<OnlineUser> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("online.user", OnlineUser.class);
	}

	@Override
	public void save(OnlineUser hb) {
		Bson query = eq("userId", hb.getUserId());
		Document update = new Document();
		update.append("$set",
				new Document("ts", hb.getTs())
					.append("loginName", hb.getLoginName())
					.append("oldLoginName", hb.getOldLoginName())
					.append("userName", hb.getUserName())
					.append("schoolId", hb.getSchoolId())
					.append("schoolName", hb.getSchoolName())
					.append("ip", hb.getIp())
					.append("networkOperator", hb.getNetworkOperator())
					.append("navigate", hb.getNavigate())
					.append("version", hb.getVersion())
					.append("os", hb.getOs())
					.append("userAgent", hb.getUserAgent()));
		coll.updateOne(query, update, new UpdateOptions().upsert(true));
	}

	@Override
	public long getActiveUserCountSince(Date ts) {
		return coll.count(gte("ts", ts));
	}

	@Override
	public List<OnlineUser> queryOnlineUsers(OnlineUserQuery query, Page page) {
		Bson filter = query.toBSON();
		long total = coll.count(filter);
		page.setTotalSize((int) total);
		if (total <= 0) {
			return Collections.emptyList();
		}
		return coll.find(filter).skip(page.getOffset()).limit(page.getLimit())
				.into(new ArrayList<>());
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

}
