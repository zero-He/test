/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.monitor.mongo.course.model.CourseSingleOnline;
import cn.strong.leke.monitor.mongo.course.model.query.OnlineCourseStat;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleOnlineDao;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseSingleOnlineDao implements ICourseSingleOnlineDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseSingleOnline> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.single.online", CourseSingleOnline.class);
	}

	@Override
	public void save(CourseSingleOnline record) {
		Bson filter = and(eq("csId", record.getCsId()), eq("schoolId", record.getSchoolId()));
		Bson update = combine(set("ts", record.getTs()),
				set("actualStuCount", record.getActualStuCount()));
		coll.updateOne(filter, update, new UpdateOptions().upsert(true));
	}

	@Override
	public OnlineCourseStat getOnlineCourseStat() {
		List<Bson> pipeline = Arrays
				.asList(match(gte("ts", StatUtils.getCurrentOnlineTs())),
						group("$schoolId", sum("lessonCount", 1),
								sum("actualStuCount", "$actualStuCount")),
						group(null, sum("schoolCount", 1), sum("lessonCount", "$lessonCount"),
								sum("actualStuCount", "$actualStuCount")));
		OnlineCourseStat stat = coll.aggregate(pipeline, OnlineCourseStat.class).first();
		return stat != null ? stat : new OnlineCourseStat();
	}
}
