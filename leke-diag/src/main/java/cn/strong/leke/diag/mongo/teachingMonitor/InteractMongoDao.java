package cn.strong.leke.diag.mongo.teachingMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.include;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 13:46:02
 */
@Repository
public class InteractMongoDao implements InitializingBean {

	private static final String COLL_NAME = "LessonBehavior";

	@Resource(name = "lessonDb")
	private MongoDatabase db;
	private MongoCollection<LessonBehavior> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, LessonBehavior.class);
	}

	/**
	 * 根据teacherId、subjectId、start、end查鲜花数
	 * @param teacherId
	 * @param subjectId
	 * @param start
	 * @param end
	 * @return
	 */
	public int queryEvaBeanByTeacherIds(Long teacherId, Long subjectId, Date start, Date end) {
		Bson match = match(and(eq("teacherId", teacherId), eq("subjectId", subjectId), gte("start", start), lte("end", end)));
		Bson project = project(and(include("flower"), exclude("_id")));
		Bson group = group(null, sum("flower", "$flower"));

		List<Bson> pipeline = Arrays.asList(match, project, group);
		AggregateIterable<LessonBehavior> aggregate = this.coll.aggregate(pipeline);
		LessonBehavior lessonBehavior = aggregate.first();
		if (lessonBehavior != null) {
			return lessonBehavior.getFlower();
		}
		return 0;
	}

	/**
	 * 根据courseSingleIds、subjectId(有无不定)、start、end查鲜花数
	 * @param vo
	 * @param courseSingleIds
	 * @return
	 */
	public int queryFlowerNumBySingleIds(RequestVo vo, List<Long> courseSingleIds) {
		Bson match = match(and(in("_id", courseSingleIds), gte("start", DateUtils.parseDate(vo.getStartDate())), lte("end", DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1))));
		if (vo.getSubjectId() != null) {
			match = match(and(in("_id", courseSingleIds), eq("subjectId", vo.getSubjectId()), gte("start", DateUtils.parseDate(vo.getStartDate())), lte("end", DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1))));
		}
		Bson project = project(and(include("flower"), exclude("_id")));
		Bson group = group(null, sum("flower", "$flower"));

		List<Bson> pipeline = Arrays.asList(match, project, group);
		AggregateIterable<LessonBehavior> aggregate = this.coll.aggregate(pipeline);
		LessonBehavior lessonBehavior = aggregate.first();
		if (lessonBehavior != null) {
			return lessonBehavior.getFlower();
		}
		return 0;
	}

	/**
	 * 根据courseSingleIds、subjectId(有无不定)、start、end查behaviorList
	 * @param vo
	 * @param courseSingleIds
	 * @return
	 */
	public List<LessonBehavior> queryLessonBehaviorBySingleIds(RequestVo vo, List<Long> courseSingleIds) {
		Bson filter = and(in("_id", courseSingleIds), gte("start", DateUtils.parseDate(vo.getStartDate())), lte("end", DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1)));
		if (vo.getSubjectId() != null) {
			filter = and(filter, eq("subjectId", vo.getSubjectId()));
		}
		Bson project = and(include("subjectId", "subjectName", "start", "end", "teacherId", "teacherName", "callNum", "quickNum", "examNum", "discuNum", "authedNum"));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}
}
