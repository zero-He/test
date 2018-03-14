package cn.strong.leke.dwh.remote;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.dwh.model.diag.AchievementQuery;
import cn.strong.leke.dwh.model.diag.AchievementResult;
import cn.strong.leke.dwh.service.AchievementService;
import cn.strong.leke.remote.service.dwh.IAchievementRemoteService;

@Component
public class AchievementRemoteService implements IAchievementRemoteService {

	@Resource
	private AchievementService achievementService;

	@Override
	public AchievementResult getKlassAchievementForTeacher(AchievementQuery query) {
		return this.achievementService.getKlassAchievementForTeacher(query);
	}

}
