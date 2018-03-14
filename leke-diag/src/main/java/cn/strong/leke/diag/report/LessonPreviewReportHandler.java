package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.homework.model.HomeworkCst;
import cn.strong.leke.diag.dao.homework.model.LessonExamAnaly;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDao;
import cn.strong.leke.diag.dao.homework.mybatis.ReviewWorkStatDao;
import cn.strong.leke.diag.model.report.ReviewInfo;
import cn.strong.leke.diag.model.report.ReviewQuery;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.beike.IBeikeRemoteService;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Component
public class LessonPreviewReportHandler implements ReportHandler<ReviewQuery, ReviewInfo> {

	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private ReviewWorkStatDao reviewWorkStatDao;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	@Resource
	private IBeikeRemoteService beikeRemoteService;

	@Override
	public ReviewInfo execute(ReviewQuery query) {
		List<LessonExamAnaly> exams = this.homeworkDao.findExamScoreByLessonIdAndUsePhase(query.getLessonId(), 1);
		List<ReviewInfo.StudentInfo> details = this.parseReviewDetails(query);
		ReviewInfo.Overall overall = new ReviewInfo.Overall();
		overall.setTotal(details.size());
		overall.setFinish((int) details.stream().filter(v -> v.getState() == 2).count());
		overall.setPortion((int) details.stream().filter(v -> v.getState() == 1).count());
		overall.setFinish1((int) details.stream().filter(v -> v.getState1() == 1).count());
		overall.setFinish2((int) details.stream().filter(v -> v.getState2() == 1).count());
		overall.setFinish3((int) details.stream().filter(v -> v.getState3() == 1).count());

		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setOverall(overall);
		reviewInfo.setExams(exams);
		reviewInfo.setDetails(details);
		return reviewInfo;
	}

	private List<ReviewInfo.StudentInfo> parseReviewDetails(ReviewQuery query) {
		Long courseSingleId = query.getLessonId();
		List<Long> userIds = this.lessonRemoteService.findStudentIdsByLessonId(query.getLessonId());

		List<UserBase> users = UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0]));
		List<ReviewInfo.StudentInfo> details = users.stream().map(user -> {
			ReviewInfo.StudentInfo info = new ReviewInfo.StudentInfo();
			info.setUserId(user.getUserId());
			info.setUserName(user.getUserName());
			return info;
		}).collect(toList());

		this.fillCoursewareFinishState(details, courseSingleId);
		this.fillMicrocourseFinishState(details, courseSingleId);
		this.fillHomeworkFinishState(details, courseSingleId);
		
		Set<Long> hasIssueUserIds = this.beikeRemoteService.findStudentIdsHasConfuse(courseSingleId);

		details.forEach(info -> {
			if (info.getState1() != 0 && info.getState2() != 0 && info.getState3() != 0) {
				// 已完成：状态都不是0的，标记为已经完成
				info.setState(2);
			} else if (info.getState1() > 0 || info.getState2() > 0 || info.getState3() > 0) {
				// 如果不是已经完成，并且有状态为完成的，标记为部分完成
				info.setState(1);
			} else {
				// 其它的都标记为未完成
				info.setState(0);
			}
			info.setHasIssue(hasIssueUserIds.contains(info.getUserId()));
		});
		return details;
	}

	private void fillCoursewareFinishState(List<ReviewInfo.StudentInfo> details, Long lessonId) {
		Integer phase = HomeworkCst.PHASE_PREVIEW, resType = HomeworkCst.RES_TYPE_CW;
		int resNum = this.reviewWorkStatDao.findReviewResNumByLessonId(lessonId, resType, phase);
		if (resNum > 0) {
			Set<Long> userIds = this.reviewWorkStatDao.findReviewUserIdsByLessonId(lessonId, resType, phase);
			details.forEach(info -> info.setState1(userIds.contains(info.getUserId()) ? 1 : 0));
		}
	}

	private void fillMicrocourseFinishState(List<ReviewInfo.StudentInfo> details, Long lessonId) {
		Integer phase = HomeworkCst.PHASE_PREVIEW, resType = HomeworkCst.RES_TYPE_MC;
		int resNum = this.reviewWorkStatDao.findReviewResNumByLessonId(lessonId, resType, phase);
		if (resNum > 0) {
			Set<Long> userIds = this.reviewWorkStatDao.findReviewUserIdsByLessonId(lessonId, resType, phase);
			details.forEach(info -> info.setState2(userIds.contains(info.getUserId()) ? 1 : 0));
		}
	}

	private void fillHomeworkFinishState(List<ReviewInfo.StudentInfo> details, Long lessonId) {
		Integer phase = HomeworkCst.PHASE_PREVIEW, resType = HomeworkCst.RES_TYPE_PAP;
		int resNum = this.reviewWorkStatDao.findReviewResNumByLessonId(lessonId, resType, phase);
		if (resNum > 0) {
			Set<Long> userIds = this.reviewWorkStatDao.findReviewUserIdsByLessonId(lessonId, resType, phase);
			details.forEach(info -> info.setState3(userIds.contains(info.getUserId()) ? 1 : 0));
		}
	}
}
