package cn.strong.leke.monitor.mongo.online.service.impl;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cn.strong.leke.monitor.mongo.course.model.query.PlatformActiveStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;
import cn.strong.leke.monitor.mongo.online.service.IPlatformActiveUserDao;
@Repository
public class PlatformActiveDao implements IPlatformActiveUserDao{
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<PlatformActiveStat> coll;  

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("dayactive.user", PlatformActiveStat.class);
	}


	@Override
	public List<PlatformActiveStat> getDateActiveUser(ActiveUserQuery query,int endDate, int fromDate) {
		Bson filter = and(gte("ts", fromDate), lte("ts", endDate),exists("allValidUser",true));
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				match(query.toBSON()),
				group("$ts", addToSet("activeUsers", "$userId"),max("ts", "$ts"),
						max("allValidUser", "$allValidUser")),
				sort(ascending("ts")),
				project(fields(excludeId(),include("activeUsers",  "allValidUser","ts"))));
		return coll.aggregate(pipeline, PlatformActiveStat.class).into(new ArrayList<>());
	}

	@Override
	public List<PlatformActiveStat> getWeekActiveUser(ActiveUserQuery query, int startWeek, int endWeek) {
		Bson filter = and(gte("week", startWeek), lte("week", endWeek),exists("allValidUser",true));
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				match(query.toBSON()),
				group("$week", addToSet("activeUsers", "$userId"),max("ts", "$week"),
						max("allValidUser", "$allValidUser")),
				sort(ascending("ts")),
				project(fields(excludeId(),include("activeUsers",  "allValidUser","ts"))));
		return coll.aggregate(pipeline, PlatformActiveStat.class).into(new ArrayList<>());
	}

	@Override
	public List<PlatformActiveStat> getMonthActiveUser(ActiveUserQuery query, int startMonth, int endMonth) {
		Bson filter = and(gte("month", startMonth), lte("month", endMonth),exists("allValidUser",true));
		List<Bson> pipeline = Arrays.asList(
				match(filter),
				match(query.toBSON()),
				group("$month", addToSet("activeUsers", "$userId"),max("ts", "$month"),
						max("allValidUser", "$allValidUser")),
				sort(ascending("ts")),
				project(fields(excludeId(),include("activeUsers",  "allValidUser","ts"))));
		return coll.aggregate(pipeline, PlatformActiveStat.class).into(new ArrayList<>());
	}

}
