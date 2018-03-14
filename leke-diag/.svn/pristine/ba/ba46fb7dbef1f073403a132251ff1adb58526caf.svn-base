package cn.strong.leke.diag.dao.lesson.mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.elemMatch;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.lessonlog.model.StudentBehavior;

@Repository
public class LessonBehaviorDao implements InitializingBean {

	private static final String COLL_NAME = "LessonBehavior";

	@Resource(name = "lessonDb")
	private MongoDatabase db;
	private MongoCollection<LessonBehavior> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, LessonBehavior.class);
	}

	/**
	 * 获取课堂行为信息
	 * @param lessonId
	 * @return
	 */
	public LessonBehavior getLessonBehaviorByLessonId(Long courseSingleId) {
		Bson filter = eq("_id", courseSingleId);
		Bson project = exclude("students");
		return this.coll.find(filter).projection(project).first();
	}

	/**
	 * 获取一个课堂中学生行为列表
	 * @param courseSingleId
	 * @return
	 */
	public List<StudentBehavior> findStudentBehaviorByLessonId(Long courseSingleId) {
		Bson filter = eq("_id", courseSingleId);
		LessonBehavior behavior = this.coll.find(filter).first();
		if (behavior != null) {
			return behavior.getStudents();
		}
		return Collections.emptyList();
	}

	/**
	 * 获取学生一段时间内的行为列表。
	 * @param studentId
	 * @param start
	 * @param end
	 * @param page
	 * @return
	 */
	public List<LessonBehavior> findStudentBehaviorByStudentId(Long studentId, Date start, Date end, Page page) {
		Bson filter = and(eq("students.studentId", studentId), gt("start", start), lt("start", end));
		Bson project = and(elemMatch("students", eq("studentId", studentId)), include("courseSingleName", "subjectName",
				"start", "end", "teacherName", "callNum", "examNum", "discuNum", "quickNum"));
		Bson sort = ascending("start");
		return MongoPageUtils.find(coll, filter, project, sort, page);
	}

	/**
	 * 获取学生一段时间内的行为列表。
	 * @param studentId
	 * @param courseSingleIds
	 * @return
	 */
	public List<LessonBehavior> findStudentBehaviorByStudentId(Long studentId, List<Long> courseSingleIds) {
		Bson filter = and(in("_id", courseSingleIds), eq("students.studentId", studentId));
		Bson project = and(include("callNum", "examNum", "discuNum", "quickNum", "flower"),
				elemMatch("students", eq("studentId", studentId)));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

	/**
	 * 根据老师及时间周期查询课堂互动信息
	 * @param teacherId
	 * @param reportCycle
	 * @return
	 */
	public List<LessonBehavior> findTeacherBehaviorByTeacherIdAndReportCycle(Long teacherId, ReportCycle reportCycle) {
		Bson filter = and(eq("teacherId", teacherId), gte("start", reportCycle.getStart()),
				lt("start", reportCycle.getEnd()));
		Bson project = include("callNum", "examNum", "discuNum", "authedNum", "quickNum", "isOvertime", "flower");
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}
	
	public List<LessonBehavior> getStudentBehaviorByCourseSingleIds(Long userId, List<Long> courseSingleIds) {
		Bson filter = and(in("_id", courseSingleIds), eq("students.studentId", userId));
		Bson project = and(include("callNum"), elemMatch("students", eq("studentId", userId)));
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}

	/**
	 * 根据老师及时间周期查询课堂互动信息
	 * @param teacherId
	 * @return
	 */
	public List<LessonBehavior> findStudentBehaviorByCourseSingleIds(List<Long> courseSingleIds) {
		Bson filter = in("_id", courseSingleIds);
		Bson project = include("callNum", "students");
		return this.coll.find(filter).projection(project).into(new ArrayList<>());
	}
}