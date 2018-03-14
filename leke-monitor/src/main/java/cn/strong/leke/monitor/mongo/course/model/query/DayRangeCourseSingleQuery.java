/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model.query;

import java.util.Date;

import org.bson.Document;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.monitor.util.BsonCriteria;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * 单课查询条件
 * 
 * @author liulongbiao
 *
 */
public class DayRangeCourseSingleQuery extends BaseAreaSchoolQuery {
	private Integer startDay; // 开始日期
	private Integer endDay; // 结束日期
	private String subjectName;
	private String teacherName;


	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void appendSubjectName(BsonCriteria filter) {
		if (StringUtils.isNotEmpty(subjectName)) {
			filter.eq("subjectName", subjectName);
		}
	}

	public void appendTeacherName(BsonCriteria filter) {
		if (StringUtils.isNotEmpty(teacherName)) {
			filter.eq("teacherName", teacherName);
		}
	}

	public Document toBSON() {
		if (startDay == null || endDay == null) {
			throw new IllegalStateException("startDay or endDay should not be null");
		}
		Date start = StatUtils.dayToLocalDate(startDay).toDate();
		Date end = StatUtils.dayToLocalDate(endDay).plusDays(1).toDate();
		BsonCriteria filter = new BsonCriteria().gte("startTime", start).lt("startTime", end);
		appendSubjectName(filter);
		appendTeacherName(filter);
		appendAreaSchool(filter);
		return filter;
	}
}
