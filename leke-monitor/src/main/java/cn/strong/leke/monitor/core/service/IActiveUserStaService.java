package cn.strong.leke.monitor.core.service;

import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.query.SchoolActiveUserStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;


public interface IActiveUserStaService {
	
	List<SchoolActiveUserStat> getClassActiveUserInfo(ActiveUserQuery query);
	
	List<ActiveUserQuery> getClassBySchool(ActiveUserQuery query);
	
	List<SchoolActiveUserStat> getDistrictSchoolActiveUser(ActiveUserQuery query);
}
