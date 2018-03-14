package cn.strong.leke.homework.dao.mongo;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.data.mongo.MongoPageUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.mongo.HolidayHwMicro;
import cn.strong.leke.homework.model.query.HolidayHwQuery;
import cn.strong.leke.homework.util.HomeworkCst;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

@Repository
public class HolidayHwDao implements InitializingBean {

	private static final String COLL_NAME = "holiday.homework";

	@Autowired
	private MongoDatabase db;

	private MongoCollection<HolidayHwMicro> coll;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.coll = db.getCollection(COLL_NAME, HolidayHwMicro.class);
	}

	/**
	 * 获取微课或作业列表
	 * @param query
	 * @return
	 */
	public List<HolidayHwMicro> findHolidayHomeworks(HolidayHwQuery query, Page page) {
		Bson filter = eq("isDeleted", false);
		Bson sort = and(Sorts.descending("createdOn"),Sorts.ascending("userId","type"));
		if (query.getClassId() != null) {
			filter = and(filter, eq("classId", query.getClassId()));
		}
		if (query.getSubjectId() != null) {
			filter = and(filter, eq("subjectId", query.getSubjectId()));
		}
		if (query.getType() != null) {
			filter = and(filter, eq("type", query.getType()));
		}
		if (query.getYear() != null) {
			filter = and(filter, eq("year", query.getYear()));
		}
		if (query.getHoliday() != null) {
			filter = and(filter, eq("holiday", query.getHoliday()));
		}
		if (query.getStudentId() != null) {
			filter = and(filter, eq("userId", query.getStudentId()));
			sort= and(Sorts.descending("createdOn"),Sorts.ascending("subjectId", "matVersion", "bookId"));
		}
		Bson projection = and(eq("microcourses", 0), eq("matNodes", 0));
		return MongoPageUtils.find(coll, filter, projection, sort, page);
		
	}

	/**
	 * 查未完成寒暑假作业的学生信息
	 * @return java.util.List<cn.strong.leke.homework.model.mongo.HolidayHwMicro>
	 * @Author LIU.SHITING
	 * @Version 2.6
	 * @Date 2017/5/12 10:37
	 * @Param [query]
	 */
	public List<HolidayHwMicro> queryVacationHomeworks(HolidayHwQuery query) {
		Bson filter = eq("isDeleted", false);
		if (query.getClassId() != null) {
			filter = and(filter, eq("classId", query.getClassId()));
		}
		if (query.getSubjectId() != null) {
			filter = and(filter, eq("subjectId", query.getSubjectId()));
		}
		if (query.getYear() != null) {
			filter = and(filter, eq("year", query.getYear()));
		}
		if (query.getHoliday() != null) {
			filter = and(filter, eq("holiday", query.getHoliday()));
		}
		filter = and(filter, ne("finish", "total"));
		return this.coll.find(filter).into(new ArrayList<HolidayHwMicro>());
	}

	/**
	 * 定时催交寒暑假作业
	 * @return
	 */
	public Set<Long> queryVacationHomeworkByAuto(Date date, Integer holiday) {
		Bson filter = eq("isDeleted", false);
		if (holiday == HomeworkCst.SUMMER_VACATION_HOMEWORK) {
			filter = and(filter,
				ne("finish", "total"), eq("year", DateUtils.getYear(date)), eq("holiday", holiday), gte("createdOn", DateUtils.addDays(date, -31)), lte("createdOn", DateUtils.addDays(date, -30))
			);
		} else {
			filter = and(filter,
				ne("finish", "total"), eq("year", DateUtils.getYear(date)), eq("holiday", holiday), gte("createdOn", DateUtils.addDays(date, -16)), lte("createdOn", DateUtils.addDays(date, -15))
			);
		}
		List<HolidayHwMicro> list = new ArrayList<>();
		this.coll.find(filter).projection(Projections.include("userId")).into(list);
		return list.stream().map(HolidayHwMicro::getUserId).collect(Collectors.toSet());
	}

	/**
	 * 获取微课和学科作业
	 * @param id
	 * @return
	 */
	public HolidayHwMicro getById(String id) {
		Bson filter = eq("_id", new ObjectId(id));
		return this.coll.find(filter).first();
	}

	/**
	 * 更新作业的状态信息
	 * @param homeworkDtlId
	 */
	public void updateHwState(Long homeworkDtlId) {
		Bson filter = eq("homeworkDtlIds", homeworkDtlId);
		HolidayHwMicro holidayHomework = this.coll.find(filter).first();
		Date curDate = new Date();
		Document set = new Document().append("lastTime", curDate).append("finish", holidayHomework.getFinish() + 1);
		if (holidayHomework.getFirstTime() == null) {
			set.append("firstTime", curDate);
		}
		Document update = new Document().append("$set", set);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 更新微课的状态信息
	 * @param id
	 * @param microId
	 * @param isFirstFinish
	 */
	public void updateMicroState(String id, Long microId,Boolean isFirstFinish,Integer position) {
		Bson filter = and(eq("_id", new ObjectId(id)), eq("microcourses.microId", microId));
		HolidayHwMicro holidayHomework = this.coll.find(filter).first();
	
		Date curDate = new Date();

		Document set = new Document().append("lastTime", curDate);
		set.append("microcourses.$.position",position);
		if(isFirstFinish){
			set.append("finish", holidayHomework.getFinish() + 1)
				.append("microcourses.$.microState", 1);
		}
		if (holidayHomework.getFirstTime() == null) {
			set.append("firstTime", curDate);
		}
		Document update = new Document().append("$set", set);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 更新微课练习作业的状态信息
	 * @param userId
	 * @param exerciseId
	 * @param accuracy
	 */
	public void updateMicroHwRate(Long userId, String exerciseId, BigDecimal accuracy) {
		Bson filter = Filters.and(eq("userId", userId) ,eq("microcourses.exerciseId", exerciseId));
		HolidayHwMicro holidayHomework = this.coll.find(filter).first();
		Date curDate = new Date();
		
		Document set = new Document().append("lastTime", curDate);
		if (holidayHomework.getFirstTime() == null) {
			set.append("firstTime", curDate);
		}
		if(accuracy != null){
			set.append("microcourses.$.accuracy", accuracy.doubleValue());
		}
		Document update = new Document().append("$set", set);
		this.coll.updateOne(filter, update);
	}

	/**
	 * 新增一条记录
	 * @param holidayHomework
	 */
	public void insertOne(HolidayHwMicro holidayHomework) {
		this.coll.insertOne(holidayHomework);
	}

}
