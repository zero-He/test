/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Accumulators.min;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

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

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseSingleQuery;
import cn.strong.leke.monitor.mongo.course.model.query.PeriodCourseStat;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleDao;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseSingleDao implements ICourseSingleDao {

	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseSingle> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.single", CourseSingle.class);
	}

	@Override
	public void save(CourseSingle cs) {
		cs.setModifiedOn(new Date());
		coll.updateOne(eq("csId", cs.getCsId()), BsonUtils.toUpdateSetDoc(cs),
				new UpdateOptions().upsert(true));
	}

	@Override
	public CourseSingle getById(Long csId) {
		return coll.find(eq("csId", csId)).first();
	}

	@Override
	public void removeById(Long csId) {
		Bson update = combine(set("isDeleted", true), set("modifiedOn", new Date()));
		coll.updateOne(eq("csId", csId), update);
	}

	@Override
	public long getExpectStuCount(Long schoolId, Date ts) {
		List<Bson> pipeline = Arrays.asList(
				match(and(eq("schoolId", schoolId), lte("startTime", ts), gte("endTime", ts),
						eq("isDeleted", false))),
				group(null, sum("expectStuCount", "$expectStuCount")),
				project(fields(excludeId(), include("expectStuCount"))));
		Document stat = coll.aggregate(pipeline, Document.class).first();
		return stat == null ? 0L : stat.getLong("expectStuCount");
	}

	@Override
	public void updateActualStuCount(Long csId, long actualStuCount) {
		Bson update = combine(set("actualStuCount", actualStuCount), set("modifiedOn", new Date()));
		coll.updateOne(eq("csId", csId), update);
	}

	@Override
	public void updateIsEnded(Long csId, boolean isEnded) {
		Bson update = combine(set("isEnded", isEnded), set("modifiedOn", new Date()));
		coll.updateOne(eq("csId", csId), update);
	}
	
	@Override
	public void updateIsOnline(Long csId, boolean isOnline) {
		Bson update = combine(set("isOnline", isOnline), set("modifiedOn", new Date()));
		coll.updateOne(eq("csId", csId), update);
	}

	@Override
	public List<CourseSingle> findUnstartedByClazzId(Long clazzId) {
		// 查找指定套课ID下，时间大于今天且未结束的未删除的单课
		Bson filter = and(eq("clazzId", clazzId), gte("startTime", LocalDate.now().toDate()),
				eq("isEnded", false), eq("isDeleted", false));
		return coll.find(filter).into(new ArrayList<>());
	}

	@Override
	public void updateExpectStuCountForUnstarted(Long clazzId, long expectStuCount) {
		// 查找指定套课ID下，时间大于今天且未结束的未删除的单课
		Bson filter = and(eq("clazzId", clazzId), gte("startTime", LocalDate.now().toDate()),
				eq("isEnded", false), eq("isDeleted", false));
		Bson update = combine(set("expectStuCount", expectStuCount), set("modifiedOn", new Date()));
		coll.updateMany(filter, update);
	}

	@Override
	public List<PeriodCourseStat> findPeriodCourseStat(Long schoolId, int day) {
		LocalDate ld = StatUtils.dayToLocalDate(day);
		List<Bson> pipeline = Arrays.asList(
				match(and(eq("schoolId", schoolId), gte("startTime", ld.toDate()),
						lt("startTime", ld.plusDays(1).toDate()), eq("isDeleted", false))),
				group("$period", sum("lessonCount", 1), sum("hours", "$hours"),
						sum("expectStuTimes", "$expectStuCount"),
						sum("actualStuTimes", "$actualStuCount"), addToSet("csIds", "$csId"),
						addToSet("clazzIds", "$clazzId"), min("minStartTime", "$startTime"),
						max("maxEndTime", "$endTime")),
				project(fields(
						excludeId(),
						computed("period", "$_id"),
						include("lessonCount", "hours", "expectStuTimes", "actualStuTimes",
								"csIds", "clazzIds", "minStartTime", "maxEndTime"))));
		return coll.aggregate(pipeline, PeriodCourseStat.class).into(new ArrayList<>());
	}

	@Override
	public List<CourseSingle> findCourseSingles(DayRangeCourseSingleQuery query) {
		return coll.find(query.toBSON()).into(new ArrayList<>());
	}
}
