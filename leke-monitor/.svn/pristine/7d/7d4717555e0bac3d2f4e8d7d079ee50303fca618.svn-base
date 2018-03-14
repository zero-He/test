package cn.strong.leke.monitor.mongo.course.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lte;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.monitor.core.model.lesson.LessonBehavior;
import cn.strong.leke.monitor.core.model.lesson.SchoolLessonDto;
import cn.strong.leke.monitor.mongo.course.service.ILessonDao;

@Repository
public class LessonDao implements ILessonDao{

	@Resource(name="lessonDb")
	private MongoDatabase db;
	private MongoCollection<LessonBehavior> coll;
	
	@PostConstruct
	public void init() {
		Assert.notNull(db, "DB should not be null");
		coll = db.getCollection("LessonBehavior", LessonBehavior.class);
	}
	
	@Override
	public List<LessonBehavior> getLessonBehavior(SchoolLessonDto query) {
		Bson filter = and(gte("start", query.getFromDate()),lte("start", DateUtils.addDays(query.getEndDate(), 1)));
		return MongoPageUtils.find(this.coll, filter, null);
	}

	@Override
	public List<LessonBehavior> getLessonBehaviorByCourseSingleId(List<Long> courseSingleIds) {
		Bson filter = and(in("_id", courseSingleIds));
		return MongoPageUtils.find(this.coll, filter, null);
	}

}
