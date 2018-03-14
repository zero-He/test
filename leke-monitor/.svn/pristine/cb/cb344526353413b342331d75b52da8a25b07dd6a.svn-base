/**
 * 
 */
package cn.strong.leke.monitor.mongo.course.model.query;

import org.bson.Document;

import cn.strong.leke.monitor.util.BsonCriteria;

/**
 * 日期范围课堂查询条件
 * 
 * @author liulongbiao
 *
 */
public class DayRangeCourseQuery extends BaseAreaSchoolQuery {

	private Integer startDay; // 开始日期
	private Integer endDay; // 结束日期

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

	public Document toBSON() {
		if (startDay == null || endDay == null) {
			throw new IllegalStateException("startDay or endDay should not be null");
		}
		BsonCriteria filter = new BsonCriteria().gte("day", startDay).lte("day", endDay);
		appendAreaSchool(filter);
		return filter;
	}

}
