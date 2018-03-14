/**
 * 
 */
package cn.strong.leke.monitor.mongo.stat.dao.impl;

import static com.mongodb.client.model.Filters.eq;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.stat.dao.ISchoolStatDailyDao;
import cn.strong.leke.monitor.mongo.stat.model.SchoolStatDaily;

/**
 * @author liulongbiao
 *
 */
@Repository
public class SchoolStatDailyDao implements ISchoolStatDailyDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<SchoolStatDaily> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("stat.school.daily", SchoolStatDaily.class);
	}

	@Override
	public void save(SchoolStatDaily stat) {
		Bson update = BsonUtils.toUpdateSetDoc(stat);
		coll.updateOne(eq("_id", stat.getDay()), update, new UpdateOptions().upsert(true));
	}
}
