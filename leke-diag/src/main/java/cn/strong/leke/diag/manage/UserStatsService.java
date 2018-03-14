package cn.strong.leke.diag.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.datetime.SchoolYear;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.dao.homework.mongo.UserStatsDao;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDtlDao;
import cn.strong.leke.diag.model.UserStats;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;

@Component
public class UserStatsService {

	private static final Logger logger = LoggerFactory.getLogger(UserStatsService.class);

	@Resource
	private UserStatsDao userStatsDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private ISchoolRemoteService schoolRemoteService;

	public void executeUserStats() {
		logger.info("学校统计任务处理开始。");
		StopWatch watch = new StopWatch("作业统计");

		Date startTime = new SchoolYear().getStartDate();
		Date endTime = DateUtils.truncateDate(new Date());
		List<Long> schoolIds = this.schoolRemoteService.findSchoolIdBySchoolNature(SchoolCst.NATURE_BASIC);

		watch.start("K12学校");
		for (int i = 0; i < schoolIds.size(); i++) {
			Long schoolId = schoolIds.get(i);
			if (SchoolCst.LEKE == schoolId) {
				continue;
			}
			logger.info("process schoolId({}), {} / {}.", schoolId, i, schoolIds.size());
			List<Clazz> clazzList = this.klassRemoteService.findDeptClazzBySchoolId(schoolId);
			for (Clazz clazz : clazzList) {
				List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(clazz.getClassId());
				for (Long userId : userIds) {
					this.executeUserStatsAtom(userId, clazz, startTime, endTime);
				}
			}
		}
		watch.stop();
		watch.start("乐课网");
		List<Long> userIds = this.userRemoteService.findUserIdsBySchoolIdAndRoleId(SchoolCst.LEKE, RoleCst.STUDENT);
		for (Long userId : userIds) {
			this.executeUserStatsAtom(userId, null, startTime, endTime);
		}
		watch.stop();
		logger.info(watch.prettyPrint());
	}

	private void executeUserStatsAtom(Long userId, Clazz clazz, Date startTime, Date endTime) {
		UserStats userStats = this.userStatsDao.getUserStatsByUserId(userId);
		if (userStats == null) {
			UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
			if (userBase == null) {
				return;
			}
			userStats = new UserStats();
			userStats.setUserId(userId);
			userStats.setUserName(userBase.getUserName());
			userStats.setDetails(new HashMap<Integer, UserStats.DayDtl>());
		}
		if (clazz != null) {
			userStats.setClassId(clazz.getClassId());
			userStats.setClassName(clazz.getClassName());
			userStats.setGradeId(clazz.getGradeId());
			userStats.setSchoolId(clazz.getSchoolId());
		} else {
			userStats.setSchoolId(SchoolCst.LEKE);
		}

		List<UserStats.SubjWork> works = homeworkDtlDao.findSubjWorkByUserId(userId, startTime, endTime);
		List<UserStats.SubjScore> scores = homeworkDtlDao.findSubjScoreByUserId(userId, startTime, endTime);
		userStats.setWorks(works);
		userStats.setScores(scores);
		int totalNum = 0, normalNum = 0, delayNum = 0;
		for (UserStats.SubjWork work : works) {
			totalNum += work.getTotalNum();
			normalNum += work.getNormalNum();
			delayNum += work.getDelayNum();
		}
		userStats.setTotalNum(totalNum);
		userStats.setNormalNum(normalNum);
		userStats.setDelayNum(delayNum);

		this.userStatsDao.saveOrUpdateUserStats(userStats);
	}
}
