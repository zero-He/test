package cn.strong.leke.homework.manage;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.homework.dao.mongo.HomeworkProgressDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.mongo.HomeworkProgress;
import cn.strong.leke.homework.model.mongo.HomeworkProgress.StudentTs;
import cn.strong.leke.homework.model.mongo.ProgressInfo;
import cn.strong.leke.model.user.SimpleUser;
import cn.strong.leke.model.user.UserBase;

@Service
public class HomeworkProgressService {

	@Resource
	private HomeworkProgressDao homeworkProgressDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;

	// 根据作业ID生成动态初始化数据结构
	private HomeworkProgress buildHomeworkProgress(Long homeworkId) {
		Homework homework = this.homeworkDao.getHomeworkById(homeworkId);
		List<Long> finishedIds = this.homeworkDtlDao.findFinishedStudentIdsByHomeworkId(homeworkId);
		List<Long> unfinishIds = this.homeworkDtlDao.findUnfinishStudentIdsByHomeworkId(homeworkId);
		List<StudentTs> finished = finishedIds.stream().map(userId -> {
			StudentTs studentTs = new StudentTs();
			studentTs.setStudentId(userId);
			studentTs.setTs(new Date(0));
			studentTs.setUsed(0);
			return studentTs;
		}).collect(toList());
		List<StudentTs> unfinished = unfinishIds.stream().map(userId -> {
			StudentTs studentTs = new StudentTs();
			studentTs.setStudentId(userId);
			studentTs.setTs(new Date(0));
			studentTs.setUsed(0);
			return studentTs;
		}).collect(toList());
		HomeworkProgress progress = new HomeworkProgress();
		progress.setHomeworkId(homework.getHomeworkId());
		progress.setStartTime(homework.getStartTime());
		progress.setCloseTime(homework.getCloseTime());
		progress.setTotalNum(finishedIds.size() + unfinished.size());
		progress.setFinished(finished);
		progress.setUnfinished(unfinished);
		return progress;
	}

	/**
	 * 获取作业动态信息，如果Mongo中没有动态信息，根据作业ID生成。
	 * @param homeworkId
	 * @return
	 */
	public ProgressInfo getProgressInfoByHomeworkId(Long homeworkId) {
		HomeworkProgress progress = homeworkProgressDao.getProgressById(homeworkId);
		if (progress == null) {
			progress = this.buildHomeworkProgress(homeworkId);
			this.homeworkProgressDao.saveHomeworkProgress(progress);
		}
		List<StudentTs> finished = progress.getFinished();
		List<StudentTs> doing = progress.getUnfinished();
		ProgressInfo progressInfo = new ProgressInfo();
		if (CollectionUtils.isNotEmpty(finished)) {
			Collections.sort(finished, Comparator.nullsLast(Comparator.comparing(StudentTs::getTs)));
			progressInfo.setFinished(toSimpleUsers(finished));
		} else {
			progressInfo.setFinished(Collections.emptyList());
		}
		if (CollectionUtils.isNotEmpty(doing)) {
			long last = System.currentTimeMillis() - 120000;
			// 最近两分钟内有活动的为正在做作业的学生，按学生ID排序
			doing = doing.stream().filter(t -> t.getTs() != null && t.getTs().getTime() > last)
					.sorted(Comparator.comparingLong(StudentTs::getStudentId)).collect(toList());
			progressInfo.setUnfinished(toSimpleUsers(doing));
		} else {
			progressInfo.setUnfinished(Collections.emptyList());
		}
		return progressInfo;
	}

	/**
	 * 30秒一次的 心跳
	 * @param homeworkId
	 * @param studentId
	 */
	public void saveHeartbeat(Long homeworkId, Long studentId) {
		boolean success = homeworkProgressDao.saveHeartbeat(homeworkId, studentId);
		if (!success) {
			HomeworkProgress progress = this.homeworkProgressDao.getStudentTs(homeworkId, studentId);
			if (progress == null) {
				// 如果动态没有找到，那么根据作业生成动态
				progress = this.buildHomeworkProgress(homeworkId);
				progress.getUnfinished().stream().filter(v -> v.getStudentId().equals(studentId)).findFirst()
						.ifPresent(v -> v.setUsed(30));
				this.homeworkProgressDao.saveHomeworkProgress(progress);
				return;
			}
			if (CollectionUtils.isEmpty(progress.getFinished()) && CollectionUtils.isEmpty(progress.getUnfinished())) {
				StudentTs ts = new StudentTs();
				ts.setStudentId(studentId);
				ts.setTs(new Date());
				ts.setUsed(30);
				this.homeworkProgressDao.appendUnFinished(homeworkId, ts);
			}
		}
	}

	/**
	 * 学生作业提交时，提交动态
	 * @param homeworkId
	 * @param studentId
	 * @return
	 */
	public Integer submit(Long homeworkId, Long studentId) {
		HomeworkProgress progress = this.homeworkProgressDao.getStudentTs(homeworkId, studentId);
		Integer used = 30;
		if (progress != null && CollectionUtils.isNotEmpty(progress.getUnfinished())) {
			StudentTs ts = progress.getUnfinished().get(0);
			used = ts.getUsed() != null ? ts.getUsed() : 30;
		}
		homeworkProgressDao.updateToFinished(homeworkId, studentId, used);
		return used;
	}

	/**
	 * 线下答题卡学生作业提交时，提交动态
	 * @param homeworkId
	 * @param studentId
	 * @return
	 */
	public void submitForSheet(Long homeworkId, Long studentId) {
		homeworkProgressDao.updateToFinished(homeworkId, studentId, null);
	}

	// 转换用户信息
	private List<SimpleUser> toSimpleUsers(List<StudentTs> users) {
		if (CollectionUtils.isEmpty(users)) {
			return Collections.emptyList();
		}
		List<Long> userIds = users.stream().map(StudentTs::getStudentId).collect(toList());
		List<UserBase> userBases = UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0]));
		return userBases.stream().map(userBase -> {
			SimpleUser user = new SimpleUser();
			user.setUserId(userBase.getUserId());
			user.setUserName(userBase.getUserName());
			user.setLoginName(userBase.getLoginName());
			user.setNick(userBase.getNick());
			user.setPhoto(userBase.getPhoto());
			return user;
		}).collect(toList());
	}

	/**
	 * 获取学生作业用户（近似）
	 * @param homeworkId
	 * @param studentId
	 * @return
	 */
	public int getStudentUsedTime(Long homeworkId, Long studentId) {
		HomeworkProgress progress = this.homeworkProgressDao.getStudentTs(homeworkId, studentId);
		if (progress != null && CollectionUtils.isNotEmpty(progress.getUnfinished())) {
			return progress.getUnfinished().get(0).getUsed();
		} else if (progress != null && CollectionUtils.isNotEmpty(progress.getFinished())) {
			return progress.getFinished().get(0).getUsed();
		} else {
			return 60;
		}
	}
}
