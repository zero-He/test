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

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.course.model.CourseSingleStudentsAttend;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleStudentsAttendDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseSingleStudentsAttendDao implements ICourseSingleStudentsAttendDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseSingleStudentsAttend> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.single.students.attend", CourseSingleStudentsAttend.class);
	}

	@Override
	public void save(CourseSingleStudentsAttend record) {
		record.setModifiedOn(new Date());
		coll.updateOne(eq("_id", record.getCsId()), BsonUtils.toUpdateSetDoc(record),
				new UpdateOptions().upsert(true));
	}

	@Override
	public CourseSingleStudentsAttend getById(Long csId) {
		return coll.find(eq("_id", csId)).first();
	}

	@Override
	public long getActualStuCount(Long csId) {
		CourseSingleStudentsAttend att = getById(csId);
		return att == null ? 0 : att.getStuIds().size();
	}

	@Override
	public long getActualStuCount(Set<Long> csIds) {
		List<Bson> pipeline = Arrays.asList(match(in("_id", csIds)), unwind("$stuIds"),
				group(null, addToSet("stuIds", "$stuIds")),
				project(fields(excludeId(),
						computed("actualStuCount", new Document("$size", "$stuIds")))));
		Document stat = coll.aggregate(pipeline, Document.class).first();
		return stat == null ? 0L : stat.getInteger("actualStuCount");
	}
}
