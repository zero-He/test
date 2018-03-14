/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.service.impl;

import static com.mongodb.client.model.Filters.eq;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.online.model.OnlineDaily;
import cn.strong.leke.monitor.mongo.online.service.IOnlineDailyDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class OnlineDailyDao implements IOnlineDailyDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<OnlineDaily> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("online.daily", OnlineDaily.class);
	}

	@Override
	public void save(OnlineDaily daily) {
		Document update = new Document("$set", 
				new Document("total", daily.getTotal())
					.append("max", daily.getMax()));
		coll.updateOne(eq("day", daily.getDay()), update, new UpdateOptions().upsert(true));
	}
}
