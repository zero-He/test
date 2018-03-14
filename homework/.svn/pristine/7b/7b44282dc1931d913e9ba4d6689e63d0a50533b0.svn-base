package cn.strong.leke.homework.manage;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.homework.dao.mongo.RankListDao;
import cn.strong.leke.homework.dao.mongo.UserRankDao;
import cn.strong.leke.homework.dao.mongo.UserStatsDao;
import cn.strong.leke.homework.model.RankList;
import cn.strong.leke.homework.model.UserRank;
import cn.strong.leke.homework.model.UserStats;
import cn.strong.leke.model.user.UserBase;

@Component
public class UserStatsService {

	@Resource
	private UserRankDao userRankDao;
	@Resource
	private RankListDao rankListDao;
	@Resource
	private UserStatsDao userStatsDao;

	/**
	 * 修改用户统计信息（练习统计）
	 * @param userId
	 * @param totalNum
	 * @param rightNum
	 */
	public void handleUserStatsByExercise(Long userId, Integer totalNum, Integer rightNum) {
		UserStats userStats = this.findUserStatsOrCreated(userId);
		Integer day = (int) (System.currentTimeMillis() / 1000 / 3600 / 24);
		if (userStats.getDetails().containsKey(day)) {
			UserStats.DayDtl detail = userStats.getDetails().get(day);
			detail.setTotalNum(detail.getTotalNum() + totalNum);
			detail.setRightNum(detail.getRightNum() + rightNum);
			userStats.getDetails().put(day, detail);
		} else {
			UserStats.DayDtl detail = new UserStats.DayDtl();
			detail.setTotalNum(totalNum);
			detail.setRightNum(rightNum);
			userStats.getDetails().put(day, detail);
		}
		this.userStatsDao.saveOrUpdateUserStats(userStats);
	}

	private UserStats findUserStatsOrCreated(Long userId) {
		UserStats userStats = this.userStatsDao.getUserStatsByUserId(userId);
		if (userStats == null) {
			UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
			userStats = new UserStats();
			userStats.setUserId(userId);
			userStats.setUserName(userBase.getUserName());
			userStats.setTotalNum(0);
			userStats.setNormalNum(0);
			userStats.setDelayNum(0);
			userStats.setWorks(new ArrayList<UserStats.SubjWork>());
			userStats.setScores(new ArrayList<UserStats.SubjScore>());
			userStats.setDetails(new HashMap<Integer, UserStats.DayDtl>());
		}
		return userStats;
	}

	public RankList queryRankList(Long dataId, Integer type) {
		RankList rank = rankListDao.getRankList(dataId, type);
		return rank==null?new RankList():rank;
	}

	public UserRank getUserRankByUserId(Long userId) {
		UserRank rank = userRankDao.getUserRankByUserId(userId);
		return rank==null?new UserRank():rank;
	}
}
