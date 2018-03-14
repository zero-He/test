package cn.strong.leke.monitor.mongo.online.service;

import java.util.List;
import cn.strong.leke.monitor.mongo.course.model.query.PlatformActiveStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;

public interface IPlatformActiveUserDao {

	
	List<PlatformActiveStat> getDateActiveUser(ActiveUserQuery query,int fromDate, int endDate);
	
	List<PlatformActiveStat> getWeekActiveUser(ActiveUserQuery query,int startWeek, int endWeek);
	
	List<PlatformActiveStat> getMonthActiveUser(ActiveUserQuery query,int startMonth, int endMonth);
	
}
