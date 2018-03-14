/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Accumulators.addToSet;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Updates.addEachToSet;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.pullAll;
import static com.mongodb.client.model.Updates.set;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import cn.strong.leke.monitor.mongo.course.model.ClazzStus;
import cn.strong.leke.monitor.mongo.course.service.IClazzStusDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class ClazzStusDao implements IClazzStusDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<ClazzStus> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.clazz.stus", ClazzStus.class);
	}

	@Override
	public void addClazzStus(Long clazzId, List<Long> stuIds) {
		Bson update = combine(addEachToSet("stuIds", stuIds), set("modifiedOn", new Date()));
		coll.updateOne(eq("_id", clazzId), update, new UpdateOptions().upsert(true));
	}

	@Override
	public UpdateResult removeClazzStus(Long clazzId, List<Long> stuIds) {
		Bson update = combine(pullAll("stuIds", stuIds), set("modifiedOn", new Date()));
		return coll.updateOne(eq("_id", clazzId), update);
	}

	@Override
	public void setClazzStus(Long clazzId, List<Long> stuIds) {
		Document update = new Document("$set", new Document("stuIds", stuIds).append("modifiedOn",
				new Date()));
		coll.updateOne(eq("_id", clazzId), update, new UpdateOptions().upsert(true));
	}

	@Override
	public ClazzStus getClazzStus(Long clazzId) {
		return coll.find(eq("_id", clazzId)).first();
	}

	@Override
	public long getExpectStuCount(Long clazzId) {
		ClazzStus cs = getClazzStus(clazzId);
		return cs == null ? 0 : cs.getStuIds().size();
	}

	/*
db.course.clazz.stus.aggregate([{
  $match: {
    _id: {$in: [1,2]}
  }
}, { 
  $unwind : "$stuIds" 
}, {
  $group: {
    _id: null,
    stuIds: {
      $addToSet: '$stuIds'
    }
  }
}, {
  $project: {
    expectStuCount: {
      $size: '$stuIds'
    }
  }
}]);
	 */
	@Override
	public long getExpectStuCount(Set<Long> clazzIds) {
		List<Bson> pipeline = Arrays.asList(
				match(in("_id", clazzIds)),
				unwind("$stuIds"),
				group(null, addToSet("stuIds", "$stuIds")),
				project(fields(excludeId(),
						computed("expectStuCount", new Document("$size", "$stuIds")))));
		Document stat = coll.aggregate(pipeline, Document.class).first();
		return stat == null ? 0L : stat.getInteger("expectStuCount");
	}
}
