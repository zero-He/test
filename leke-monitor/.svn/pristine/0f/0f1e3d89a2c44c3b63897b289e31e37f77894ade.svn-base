/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.course.model.CoursePlatformDaily;
import cn.strong.leke.monitor.mongo.course.service.ICoursePlatformDailyDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CoursePlatformDailyDao implements ICoursePlatformDailyDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CoursePlatformDaily> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.platform.daily", CoursePlatformDaily.class);
	}

	@Override
	public void save(CoursePlatformDaily cs) {
		cs.setModifiedOn(new Date());
		coll.updateOne(eq("day", cs.getDay()), BsonUtils.toUpdateSetDoc(cs),
				new UpdateOptions().upsert(true));
	}

	@Override
	public List<CoursePlatformDaily> findCoursePlatformDaily(int startDay, int endDay) {
		return coll.find(and(gte("day", startDay), lte("day", endDay))).sort(descending("day"))
				.into(new ArrayList<>());
	}
}
