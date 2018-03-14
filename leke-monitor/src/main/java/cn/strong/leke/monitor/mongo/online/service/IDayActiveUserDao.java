package cn.strong.leke.monitor.mongo.online.service;

import java.util.List;
import java.util.Set;

import cn.strong.leke.monitor.mongo.course.model.query.SchoolActiveUserStat;
import cn.strong.leke.monitor.mongo.online.model.DayActiveUser;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;

public interface IDayActiveUserDao {
	
	/**
	 * 采集每日的活跃用户
	 * @param deviceOnlineUserSta
	 */
	void saveDayActiveUser(DayActiveUser deviceOnlineUserSta);
	
	/**
	 * 客户经理查询学校活跃用户
	 * @param query
	 * @return
	 */
	List<SchoolActiveUserStat> getActiveUser(ActiveUserQuery query,List<Long> schoolIds);
	
	/**
	 * 客户经理查询学校活跃用户
	 * @param query
	 * @return
	 */
	List<SchoolActiveUserStat> getClassActiveUser(ActiveUserQuery query);
	
	Set<Integer> getStaDay(int fromDate,int endDate);
	
	Set<Integer> getStaWeek(int startWeek, int endWeek);
	
	Set<Integer> getStaMonth(int startMonth, int endMonth);

	
}
