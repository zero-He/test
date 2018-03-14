package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.homework.model.HomeworkCst;
import cn.strong.leke.diag.dao.homework.model.LessonExamAnaly;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDao;
import cn.strong.leke.diag.dao.homework.mybatis.ReviewWorkStatDao;
import cn.strong.leke.diag.dao.note.mybatis.NoteBookDao;
import cn.strong.leke.diag.model.report.ResReviewState;
import cn.strong.leke.diag.model.report.ReviewInfo;
import cn.strong.leke.diag.model.report.ReviewQuery;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Component
public class LessonReviewReportHandler implements ReportHandler<ReviewQuery, ReviewInfo> {

	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private ReviewWorkStatDao reviewWorkStatDao;
	@Resource
	private ILessonRemoteService lessonRemoteService;

	@Override
	public ReviewInfo execute(ReviewQuery query) {
		List<LessonExamAnaly> exams = this.homeworkDao.findExamScoreByLessonIdAndUsePhase(query.getLessonId(),
				HomeworkCst.RES_TYPE_PAP);
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
		Long lessonId = query.getLessonId();
		List<Long> userIds = this.lessonRemoteService.findStudentIdsByLessonId(query.getLessonId());

		List<UserBase> users = UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0]));
		List<ReviewInfo.StudentInfo> details = users.stream().map(user -> {
			ReviewInfo.StudentInfo info = new ReviewInfo.StudentInfo();
			info.setUserId(user.getUserId());
			info.setUserName(user.getUserName());
			return info;
		}).collect(toList());
		this.fillHomeworkFinishState(details, lessonId);
		this.fillCoursewareFinishState(details, lessonId);
		this.fillNoteIsReadState(details, lessonId);
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
		});
		return details;
	}

	private void fillCoursewareFinishState(List<ReviewInfo.StudentInfo> details, Long lessonId) {
		Integer phase = HomeworkCst.PHASE_REVIEW, resType = HomeworkCst.RES_TYPE_CW;
		int resNum = this.reviewWorkStatDao.findReviewResNumByLessonId(lessonId, resType, phase);
		if (resNum > 0) {
			Set<Long> userIds = this.reviewWorkStatDao.findReviewUserIdsByLessonId(lessonId, resType, phase);
			details.forEach(info -> info.setState1(userIds.contains(info.getUserId()) ? 1 : 0));
		}
	}

	private void fillHomeworkFinishState(List<ReviewInfo.StudentInfo> details, Long lessonId) {
		Integer phase = HomeworkCst.PHASE_REVIEW, resType = HomeworkCst.RES_TYPE_PAP;
		int resNum = this.reviewWorkStatDao.findReviewResNumByLessonId(lessonId, resType, phase);
		if (resNum > 0) {
			Set<Long> userIds = this.reviewWorkStatDao.findReviewUserIdsByLessonId(lessonId, resType, phase);
			details.forEach(info -> info.setState2(userIds.contains(info.getUserId()) ? 1 : 0));
		}
	}

	private void fillNoteIsReadState(List<ReviewInfo.StudentInfo> details, Long lessonId) {
		List<ResReviewState> reviewStates = this.noteBookDao.findResReviewStateByCourseSingleId(lessonId);
		Set<Long> doneIds = reviewStates.stream().filter(v -> v.getState()).map(ResReviewState::getStudentId)
				.collect(toSet());
		Set<Long> undoIds = reviewStates.stream().filter(v -> !v.getState()).map(ResReviewState::getStudentId)
				.collect(toSet());
		details.forEach(info -> {
			if (doneIds.contains(info.getUserId())) {
				info.setState3(1);
			} else if (undoIds.contains(info.getUserId())) {
				info.setState3(0);
			}
		});
	}
}
