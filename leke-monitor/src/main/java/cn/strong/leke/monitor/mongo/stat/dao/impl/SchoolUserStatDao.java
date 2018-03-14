/**
 * 
 */
package cn.strong.leke.monitor.mongo.stat.dao.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.stat.dao.ISchoolUserStatDao;
import cn.strong.leke.monitor.mongo.stat.model.SchoolUserStat;

/**
 * @author liulongbiao
 *
 */
@Repository
public class SchoolUserStatDao implements ISchoolUserStatDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<SchoolUserStat> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("stat.user.school", SchoolUserStat.class);
	}

	@Override
	public void save(SchoolUserStat stat) {
		Bson filter = eq("_id", stat.getSchoolId());
		Bson update = BsonUtils.toUpdateSetDoc(stat);
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public SchoolUserStat getById(long schoolId) {
		return coll.find(eq("_id", schoolId)).first();
	}

	@Override
	public Set<Long> getActiveSchoolIdsSince(Date ts) {
		return coll.distinct("_id", gte("modifiedOn", ts), Long.class).into(new HashSet<>());
	}
}
