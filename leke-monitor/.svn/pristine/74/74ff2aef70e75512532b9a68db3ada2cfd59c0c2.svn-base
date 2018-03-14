/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.descending;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.online.model.OnlineSnapshot;
import cn.strong.leke.monitor.mongo.online.service.IOnlineSnapshotDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class OnlineSnapshotDao implements IOnlineSnapshotDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<OnlineSnapshot> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("online.snapshot", OnlineSnapshot.class);
	}

	@Override
	public void save(OnlineSnapshot snapshot) {
		Document update = new Document("$set", new Document("count", snapshot.getCount()));
		coll.updateOne(eq("ts", snapshot.getTs()), update, new UpdateOptions().upsert(true));
	}

	@Override
	public long getMaxCountOfDay(Date day) {
		LocalDate ld = new LocalDate(day);
		OnlineSnapshot max = coll
				.find(and(gte("ts", ld.toDate()), lt("ts", ld.plusDays(1).toDate())))
				.sort(descending("count")).limit(1).first();
		return max == null ? 0 : max.getCount();
	}
}
