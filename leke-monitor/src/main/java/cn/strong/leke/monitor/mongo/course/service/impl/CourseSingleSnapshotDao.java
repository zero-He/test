/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.course.model.CourseSingleSnapshot;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleSnapshotDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseSingleSnapshotDao implements ICourseSingleSnapshotDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseSingleSnapshot> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.single.snapshot", CourseSingleSnapshot.class);
	}

	@Override
	public void save(CourseSingleSnapshot record) {
		Bson filter = and(eq("ts", record.getTs()), eq("csId", record.getCsId()),
				eq("schoolId", record.getSchoolId()));
		Bson update = set("actualStuCount", record.getActualStuCount());
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public long getActualStuCount(Long schoolId, Date ts) {
		List<Bson> pipeline = Arrays.asList(
				match(and(eq("ts", ts), eq("schoolId", schoolId))),
				group(new Document("ts", "$ts").append("schoolId", "$schoolId"),
						sum("actualStuCount", "$actualStuCount")),
				project(fields(excludeId(), include("actualStuCount"))));
		Document stat = coll.aggregate(pipeline, Document.class).first();
		return stat == null ? 0L : stat.getLong("actualStuCount");
	}
}
