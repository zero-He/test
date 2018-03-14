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
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.course.model.CourseSchoolDaily;
import cn.strong.leke.monitor.mongo.course.model.query.DailyCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolDailyDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseSchoolDailyDao implements ICourseSchoolDailyDao {
	private static Bson AGG_GROUP = group("$day", sum("schoolCount", 1),
			sum("lessonCount", "$lessonCount"), sum("hours", "$hours"),
			sum("expectStuCount", "$expectStuCount"), sum("actualStuCount", "$actualStuCount"),
			sum("expectStuTimes", "$expectStuTimes"), sum("actualStuTimes", "$actualStuTimes"));
	private static Bson AGG_PROJECT = project(fields(excludeId(), computed("day", "$_id"), include("schoolCount","lessonCount",
			"hours", "expectStuCount", "actualStuCount", "expectStuTimes", "actualStuTimes")));
	
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseSchoolDaily> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.school.daily", CourseSchoolDaily.class);
	}

	@Override
	public void save(CourseSchoolDaily cs) {
		cs.setModifiedOn(new Date());
		coll.updateOne(and(eq("day", cs.getDay()), eq("schoolId", cs.getSchoolId())),
				BsonUtils.toUpdateSetDoc(cs), new UpdateOptions().upsert(true));
	}

	@Override
	public DailyCourseStat getDailyCourseStat(int day) {
		List<Bson> pipeline = Arrays.asList(match(eq("day", day)), AGG_GROUP, AGG_PROJECT);
		DailyCourseStat daily = coll.aggregate(pipeline, DailyCourseStat.class).first();
		return daily != null ? daily : new DailyCourseStat(day);
	}

	@Override
	public List<DailyCourseStat> findDailyCourseStats(DayRangeCourseQuery query) {
		Document filter = query.toBSON();
		filter.append("lessonCount", new Document("$gt", 0L)); // 仅统计课堂数量大于0 的记录
		List<Bson> pipeline = Arrays.asList(match(filter), AGG_GROUP, AGG_PROJECT);
		return coll.aggregate(pipeline, DailyCourseStat.class).into(new ArrayList<>());
	}
}
