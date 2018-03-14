/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.course.model.CourseSchoolSnapshot;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotQuery;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotStat;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolSnapshotDao;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.model.user.SchoolAreaRemote;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseSchoolSnapshotDao implements ICourseSchoolSnapshotDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseSchoolSnapshot> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.school.snapshot", CourseSchoolSnapshot.class);
	}

	@Override
	public void saveActualStuCount(SchoolAreaRemote sa, Date ts, long actualStuCount) {
		Bson filter = and(eq("ts", ts), eq("schoolId", sa.getSchoolId()));
		Document update = 
			new Document("$set",
				new Document("actualStuCount", actualStuCount)
					.append("regionIds", sa.getRegionIds())
					.append("marketIds", sa.getMarketIds())
					.append("modifiedOn", new Date()))
			.append("$setOnInsert", new Document("expectStuCount", 0L));
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public void saveExpectStuCount(SchoolAreaRemote sa, Date ts, long expectStuCount) {
		Bson filter = and(eq("ts", ts), eq("schoolId", sa.getSchoolId()));
		Document update = 
				new Document("$set", 
					new Document("expectStuCount", expectStuCount)
						.append("regionIds", sa.getRegionIds())
						.append("marketIds", sa.getMarketIds())
							.append("modifiedOn", new Date()))
				.append("$setOnInsert", new Document("actualStuCount", 0L));
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public long getMaxActualStuCount(int day) {
		LocalDate ld = StatUtils.dayToLocalDate(day);
		Bson filter = and(gte("ts", ld.toDate()), lt("ts", ld.plusDays(1).toDate()));
		List<Bson> pipeline = Arrays.asList(match(filter),
				group("$ts", sum("actualStuCount", "$actualStuCount")),
				group(null, max("actualStuCount", "$actualStuCount")));
		Document stat = coll.aggregate(pipeline, Document.class).first();
		return stat == null ? 0L : stat.getLong("actualStuCount");
	}

	@Override
	public List<CourseSnapshotStat> findCourseSnapshotStats(CourseSnapshotQuery query) {
		Document filter = query.toBSON();
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				group("$ts", sum("expectStuCount", "$expectStuCount"),
						sum("actualStuCount", "$actualStuCount")),
				project(fields(excludeId(), computed("ts", "$_id"),
						include("expectStuCount", "actualStuCount"))));
		return coll.aggregate(pipeline, CourseSnapshotStat.class).into(new ArrayList<>());
	}

}
