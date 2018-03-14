package cn.strong.leke.monitor.core.service.activeuser;

import java.util.List;

import cn.strong.leke.monitor.mongo.course.model.query.PlatformActiveStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;

public interface IPlatformActiveUserService {
	
	List<PlatformActiveStat> getPlatformActiveUserInfo(ActiveUserQuery query);
}
