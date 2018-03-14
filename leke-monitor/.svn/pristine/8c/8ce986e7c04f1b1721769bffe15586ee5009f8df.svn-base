/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import cn.strong.leke.data.mongo.BsonUtils;
import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;
import cn.strong.leke.monitor.mongo.course.service.ICourseTableDao;

/**
 * @author liulongbiao
 *
 */
@Repository
public class CourseTableDao implements ICourseTableDao {
	@Resource(name = "monitorDb")
	private MongoDatabase db;
	private MongoCollection<CourseTable> coll;

	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("course.table", CourseTable.class);
	}

	@Override
	public void save(CourseTable cs) {
		cs.setModifiedOn(new Date());
		Bson filter = and(eq("day", cs.getDay()), eq("schoolId", cs.getSchoolId()),
				eq("period", cs.getPeriod()));
		coll.updateOne(filter, BsonUtils.toUpdateSetDoc(cs), new UpdateOptions().upsert(true));
	}

	@Override
	public void deleteBySchoolAndDay(Long schoolId, int day) {
		coll.deleteMany(and(eq("day", day), eq("schoolId", schoolId)));
	}

	@Override
	public List<CourseTable> findCourseTables(DayRangeCourseQuery query) {
		return coll.find(query.toBSON()).into(new ArrayList<>());
	}

}
