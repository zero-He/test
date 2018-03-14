package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.dao.homework.model.HomeworkCst;
import cn.strong.leke.diag.dao.homework.model.WorkFinishAnaly;
import cn.strong.leke.diag.dao.homework.mongo.ExerciseDao;
import cn.strong.leke.diag.dao.homework.mybatis.DoubtExplainDao;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDtlDao;
import cn.strong.leke.diag.dao.homework.mybatis.ReviewWorkStatDao;
import cn.strong.leke.diag.dao.incentive.mongo.UserIncentiveDao;
import cn.strong.leke.diag.dao.lesson.model.AttendStatInfo2;
import cn.strong.leke.diag.dao.lesson.mongo.LessonBehaviorDao;
import cn.strong.leke.diag.dao.lesson.mybatis.AttendanceDao;
import cn.strong.leke.diag.dao.lesson.mybatis.EvaluateDao;
import cn.strong.leke.diag.dao.note.mybatis.NoteBookDao;
import cn.strong.leke.diag.dao.wrongtopic.mybatis.WrongBookDao;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.model.ReviewCount;
import cn.strong.leke.diag.model.UserRate;
import cn.strong.leke.diag.model.report.BehaviorRptQuery;
import cn.strong.leke.diag.model.report.BehaviorRptView;
import cn.strong.leke.diag.model.report.BehaviorRptView.BehaviorInfo;
import cn.strong.leke.diag.model.report.BehaviorRptView.OtherWiseInfo;
import cn.strong.leke.diag.model.report.BehaviorRptView.SummaryInfo;
import cn.strong.leke.diag.model.report.BehaviorRptView.ViewFinishAnaly;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.ResReviewState;
import cn.strong.leke.diag.model.stuanaly.UserAchievement;
import cn.strong.leke.diag.model.stuanaly.UserAchievement.TrendItem;
import cn.strong.leke.diag.util.ScoreUtils;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.lesson.model.query.ScheduleQuery;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.lessonlog.model.StudentBehavior;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Component
public class StuanalyPersonBehaviorReportHandler implements ReportHandler<BehaviorRptQuery, BehaviorRptView> {

	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private ExerciseDao exerciseDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private DoubtExplainDao doubtExplainDao;
	@Resource
	private WrongBookDao wrongBookDao;
	@Resource
	private ReviewWorkStatDao reviewWorkStatDao;
	@Resource
	private EvaluateDao evaluateDao;
	@Resource
	private AttendanceDao attendanceDao;
	@Resource
	private LessonBehaviorDao lessonBehaviorDao;
	@Resource
	private UserIncentiveDao userIncentiveDao;
	@Resource
	private ReportCycleService reportCycleService;
	@Resource
	private ILessonRemoteService lessonRemoteService;

	@Override
	public BehaviorRptView execute(BehaviorRptQuery query) {
		Long userId = query.getStudentId();
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(query.getCycleId());
		Date start = reportCycle.getStart(), end = reportCycle.getEnd();
		List<Long> courseSingleIds = this.findReportCycleLessonIds(query, reportCycle);
		// 作业统计
		WorkFinishAnaly workFinishAnaly = this.homeworkDtlDao.getStudentFinishAnalyByTime(userId, start, end);
		// 预复习统计
		ViewFinishAnaly viewFinishAnaly = this.calcViewFinishAnaly(userId, courseSingleIds);
		// 考勤信息
		AttendStatInfo2 attendStatInfo = this.getAttendStatInfoByStudentId(userId, courseSingleIds);
		// 乐豆
		Map<Long, Integer> lekeVal = this.userIncentiveDao.findTypeLekeValByUserId(userId, start, end);
		// 行为统计
		BehaviorInfo behaviorInfo = this.calcUserBehaviorInfo(userId, courseSingleIds);
		// 其它行为
		OtherWiseInfo otherWiseInfo = this.calcUserOtherWiseInfo(userId, start, end);

		UserAchievement achievement = this.calcUserAchievementResult(userId, query, reportCycle);

		SummaryInfo summaryInfo = new SummaryInfo();
		summaryInfo.setTotalNum(courseSingleIds.size());
		summaryInfo.setAttendRate(
				ScoreUtils.toRate(attendStatInfo.getRealNum(), attendStatInfo.getLessonNum(), 0).intValue());
		summaryInfo.setPreviewRate(viewFinishAnaly.getPreviewRate());
		summaryInfo.setReviewRate(viewFinishAnaly.getReviewRate());
		summaryInfo.setSubmitRate(ScoreUtils.toRate(workFinishAnaly.getNormalNum() + workFinishAnaly.getDelayNum(),
				workFinishAnaly.getTotalNum(), 0).intValue());
		summaryInfo.setBugFixRate(ScoreUtils.toRate(workFinishAnaly.getDoneBugFixNum(),
				workFinishAnaly.getUndoBugFixNum() + workFinishAnaly.getDoneBugFixNum(), 0).intValue());
		summaryInfo.setAchieveNum(achievement.getNum());
		summaryInfo.setLekeNum(lekeVal.values().stream().mapToInt(v -> v).sum());

		BehaviorRptView view = new BehaviorRptView();
		view.setSummaryInfo(summaryInfo);
		view.setAchievement(achievement);
		view.setBehaviorInfo(behaviorInfo);
		view.setAttendStatInfo(attendStatInfo);
		view.setViewFinishAnaly(viewFinishAnaly);
		view.setWorkFinishAnaly(workFinishAnaly);
		view.setOtherWiseInfo(otherWiseInfo);
		view.setLekeVal(lekeVal);
		return view;
	}

	private AttendStatInfo2 getAttendStatInfoByStudentId(Long userId, List<Long> lessonIds) {
		if (lessonIds.isEmpty()) {
			return new AttendStatInfo2();
		}
		AttendStatInfo2 attendStatInfo = this.attendanceDao.getAttendStatInfoByStudentId(userId, lessonIds);
		//	attendStatInfo.setLessonNum(lessonIds.size());
		return attendStatInfo;
	}

	private OtherWiseInfo calcUserOtherWiseInfo(Long userId, Date start, Date end) {
		OtherWiseInfo otherWiseInfo = new OtherWiseInfo();
		// 提问数量
		otherWiseInfo.setDoubtNum(this.doubtExplainDao.getStudentDoubtNumByTime(userId, start, end));
		// 练习题数
		otherWiseInfo.setPracticeNum(this.exerciseDao.getStudentPracticeNumByTime(userId, start, end));
		// 记笔记数量
		otherWiseInfo.setWriteNoteNum(this.noteBookDao.getStudentWriteNoteNumByTime(userId, start, end));
		// 消灭错题量
		otherWiseInfo.setKillWrongNum(this.wrongBookDao.getStudentKillWrongNumByTime(userId, start, end));
		return otherWiseInfo;
	}

	private BehaviorInfo calcUserBehaviorInfo(Long userId, List<Long> lessonIds) {
		BehaviorInfo behaviorInfo = new BehaviorInfo();
		if (lessonIds.isEmpty()) {
			// 没有课堂时，不处理，但要设置默认数据
			return behaviorInfo;
		}
		// 课中行为
		List<LessonBehavior> behaviors = lessonBehaviorDao.findStudentBehaviorByStudentId(userId, lessonIds);
		if (behaviors.isEmpty()) {
			// 没有课堂行为数据时，不处理
			return behaviorInfo;
		}

		// 课中整体行为
		behaviorInfo.setCallNum2(behaviors.stream().mapToInt(LessonBehavior::getCallNum).sum());
		behaviorInfo.setExamNum2(behaviors.stream().mapToInt(LessonBehavior::getExamNum).sum());
		behaviorInfo.setQuickNum2(behaviors.stream().mapToInt(LessonBehavior::getQuickNum).sum());
		behaviorInfo.setDiscuNum2(behaviors.stream().mapToInt(LessonBehavior::getDiscuNum).sum());
		// 学生课中行为
		List<StudentBehavior> students = behaviors.stream().map(v -> v.getStudents().get(0)).collect(toList());
		behaviorInfo.setCallNum1(students.stream().mapToInt(StudentBehavior::getCalled).sum());
		behaviorInfo.setExamNum1(students.stream().mapToInt(StudentBehavior::getExamNum).sum());
		behaviorInfo.setQuickNum1(students.stream().mapToInt(StudentBehavior::getQuickNum).sum());
		behaviorInfo.setDiscuNum1(students.stream().mapToInt(StudentBehavior::getDiscuNum).sum());
		behaviorInfo.setRaised(students.stream().mapToInt(StudentBehavior::getRaised).sum());
		behaviorInfo.setAuthed(students.stream().mapToInt(StudentBehavior::getAuthed).sum());
		behaviorInfo.setNoteNum(students.stream().mapToInt(StudentBehavior::getNoteNum).sum());
		behaviorInfo.setFlowerNum(students.stream().mapToInt(StudentBehavior::getFlower).sum());
		behaviorInfo.setEvalNum((int) students.stream().filter(StudentBehavior::getIsEval).count());
		behaviorInfo.setRank1((int) students.stream().filter(v -> v.getRank() != null && v.getRank() == 1).count());
		behaviorInfo.setRank2((int) students.stream().filter(v -> v.getRank() != null && v.getRank() == 2).count());
		behaviorInfo.setRank3((int) students.stream().filter(v -> v.getRank() != null && v.getRank() == 3).count());
		return behaviorInfo;
	}

	private ViewFinishAnaly calcViewFinishAnaly(Long userId, List<Long> lessonIds) {
		ViewFinishAnaly viewFinishAnaly = new ViewFinishAnaly();
		if (lessonIds.isEmpty()) {
			return viewFinishAnaly;
		}
		// 查询完成情况
		List<ResReviewState> preview1 = this.reviewWorkStatDao.findResReviewStateByStudentId(userId, lessonIds,
				HomeworkCst.RES_TYPE_CW, HomeworkCst.PHASE_PREVIEW);
		List<ResReviewState> preview2 = this.reviewWorkStatDao.findResReviewStateByStudentId(userId, lessonIds,
				HomeworkCst.RES_TYPE_MC, HomeworkCst.PHASE_PREVIEW);
		List<ResReviewState> preview3 = this.reviewWorkStatDao.findResReviewStateByStudentId(userId, lessonIds,
				HomeworkCst.RES_TYPE_PAP, HomeworkCst.PHASE_PREVIEW);
		List<ResReviewState> review1 = this.reviewWorkStatDao.findResReviewStateByStudentId(userId, lessonIds,
				HomeworkCst.RES_TYPE_PAP, HomeworkCst.PHASE_REVIEW);
		List<ResReviewState> review2 = this.reviewWorkStatDao.findResReviewStateByStudentId(userId, lessonIds,
				HomeworkCst.RES_TYPE_CW, HomeworkCst.PHASE_REVIEW);
		List<ResReviewState> review3 = noteBookDao.findResReviewStateByStudentId(userId, lessonIds);
		// 设置单个类型完成数量
		viewFinishAnaly.setTotalNum(lessonIds.size());
		// 预习完成情况
		viewFinishAnaly.setPreviews1(Arrays.asList(preview1.size(), preview2.size(), preview3.size()));
		viewFinishAnaly.setPreviews2(Arrays.asList((int) preview1.stream().filter(ResReviewState::getState).count(),
				(int) preview2.stream().filter(ResReviewState::getState).count(),
				(int) preview3.stream().filter(ResReviewState::getState).count()));
		Set<Long> previewSet = new HashSet<>();
		preview1.forEach(v -> previewSet.add(v.getCourseSingleId()));
		preview2.forEach(v -> previewSet.add(v.getCourseSingleId()));
		preview3.forEach(v -> previewSet.add(v.getCourseSingleId()));
		viewFinishAnaly.setPreview1(previewSet.size());
		preview1.stream().filter(v -> !v.getState()).forEach(v -> previewSet.remove(v.getCourseSingleId()));
		preview2.stream().filter(v -> !v.getState()).forEach(v -> previewSet.remove(v.getCourseSingleId()));
		preview3.stream().filter(v -> !v.getState()).forEach(v -> previewSet.remove(v.getCourseSingleId()));
		viewFinishAnaly.setPreview2(previewSet.size());
		// 复习完成情况
		viewFinishAnaly.setReviews1(Arrays.asList(review1.size(), review2.size(), review3.size()));
		viewFinishAnaly.setReviews2(Arrays.asList((int) review1.stream().filter(ResReviewState::getState).count(),
				(int) review2.stream().filter(ResReviewState::getState).count(),
				(int) review3.stream().filter(ResReviewState::getState).count()));
		Set<Long> reviewSet = new HashSet<>();
		review1.forEach(v -> reviewSet.add(v.getCourseSingleId()));
		review2.forEach(v -> reviewSet.add(v.getCourseSingleId()));
		review3.forEach(v -> reviewSet.add(v.getCourseSingleId()));
		viewFinishAnaly.setReview1(reviewSet.size());
		review1.stream().filter(v -> !v.getState()).forEach(v -> reviewSet.remove(v.getCourseSingleId()));
		review2.stream().filter(v -> !v.getState()).forEach(v -> reviewSet.remove(v.getCourseSingleId()));
		review3.stream().filter(v -> !v.getState()).forEach(v -> reviewSet.remove(v.getCourseSingleId()));
		viewFinishAnaly.setReview2(reviewSet.size());
		// 预复习完成比率
		if (viewFinishAnaly.getPreview1() > 0) {
			viewFinishAnaly.setPreviewRate(
					ScoreUtils.toRate(viewFinishAnaly.getPreview2(), viewFinishAnaly.getPreview1(), 0).intValue());
		}
		if (viewFinishAnaly.getReview1() > 0) {
			viewFinishAnaly.setReviewRate(
					ScoreUtils.toRate(viewFinishAnaly.getReview2(), viewFinishAnaly.getReview1(), 0).intValue());
		}
		return viewFinishAnaly;
	}

	private List<Long> findReportCycleLessonIds(BehaviorRptQuery query, ReportCycle reportCycle) {
		ScheduleQuery scheduleQuery = new ScheduleQuery();
		scheduleQuery.setRoleId(RoleCst.STUDENT);
		scheduleQuery.setStudentId(query.getStudentId());
		scheduleQuery.setStartTime(reportCycle.getStart());
		scheduleQuery.setEndTime(reportCycle.getEnd());
		List<LessonVM> lessonList = this.lessonRemoteService.findScheduleByQuery(scheduleQuery);
		List<Long> lessonIds = lessonList.stream().map(LessonVM::getCourseSingleId).collect(toList());
		if (!lessonIds.isEmpty()) {
			// 过滤未上课课堂ID
			lessonIds = this.attendanceDao.filterNotAttendCourseSingleIds(lessonIds);
		}
		return lessonIds;
	}

	private UserAchievement calcUserAchievementResult(Long userId, BehaviorRptQuery query, ReportCycle reportCycle) {
		List<ReportCycle> cycles = this.reportCycleService.findNear6ReportCycle(reportCycle);
		List<UserAchievement> achieves = cycles.stream().map(cycle -> {
			List<Long> lessonIds = this.findReportCycleLessonIds(query, cycle);
			return this.calcUserAchievement(userId, lessonIds, cycle);
		}).collect(toList());
		UserAchievement achievement = achieves.get(0);
		Collections.reverse(achieves);
		achievement.setTrends(achieves.stream().map(v -> new TrendItem(v.getLabel(), v.getScore())).collect(toList()));
		return achievement;
	}

	private UserAchievement calcUserAchievement(Long userId, List<Long> lessonIds, ReportCycle reportCycle) {
		Date start = reportCycle.getStart(), end = reportCycle.getEnd();

		int threshold_attend = reportCycle.getType() == 1 ? 100 : 98;
		int threshold_exercise = reportCycle.getType() == 1 ? 7 : 30;
		int threshold_doubt = reportCycle.getType() == 1 ? 3 : 12;
		int threshold_dowork = reportCycle.getType() == 1 ? 100 : 95;
		int threshold_bugfix = 80;
		int threshold_called = 90;
		int threshold_raised = 70;
		int threshold_evaluate = 90;
		int threshold_flower = reportCycle.getType() == 1 ? 1 : 4;
		int threshold_preview = 90;
		int threshold_review = 90;
		int threshold_top3 = reportCycle.getType() == 1 ? 1 : 3;

		List<Integer> types = Lists.newArrayList();

		// 练习达人
		this.decideAndPush(types, 9, () -> {
			Integer value = this.exerciseDao.getStudentPracticeNumByTime(userId, start, end);
			return value >= threshold_exercise * 4;
		});

		// 提问
		this.decideAndPush(types, 4, () -> {
			Integer value = this.doubtExplainDao.getStudentDoubtNumByTime(userId, start, end);
			return value >= threshold_doubt;
		});

		// 不磨蹭 & 及时改错
		UserRate homeworkRate = this.homeworkDtlDao.getUserSubmitBugFixRatesByUserIds(userId, start, end);
		if (homeworkRate != null) {
			// 不磨蹭
			this.decideAndPush(types, 8, () -> {
				return homeworkRate.getRate() != null && homeworkRate.getRate() >= threshold_dowork;
			});

			// 及时改错
			this.decideAndPush(types, 7, () -> {
				return homeworkRate.getRate2() != null && homeworkRate.getRate2() >= threshold_bugfix;
			});
		}

		if (lessonIds.size() > 0) {
			// 全勤
			this.decideAndPush(types, 1, () -> {
				Integer value = this.attendanceDao.getUserRateByCourseSingleIds(userId, lessonIds);
				return value >= threshold_attend;
			});

			List<LessonBehavior> behaviors = lessonBehaviorDao.getStudentBehaviorByCourseSingleIds(userId, lessonIds);
			// 专心听讲
			this.decideAndPush(types, 3, () -> {
				int callNum = behaviors.stream().filter(v -> v.getCallNum() != null)
						.mapToInt(LessonBehavior::getCallNum).sum();
				if (callNum == 0) {
					return false;
				}
				int called = behaviors.stream().flatMap(v -> v.getStudents().stream())
						.filter(v -> v.getCalled() != null).mapToInt(StudentBehavior::getCalled).sum();
				return ScoreUtils.toRate(called, callNum, 0) >= threshold_called;
			});

			// 活跃分子
			this.decideAndPush(types, 5, () -> {
				int total = behaviors.size();
				int raised = (int) behaviors.stream().flatMap(v -> v.getStudents().stream())
						.filter(v -> v.getRaised() > 0).count();
				return ScoreUtils.toRate(raised, total, 0) >= threshold_raised;
			});

			// 手有余香(送花)
			this.decideAndPush(types, 6, () -> {
				int total = behaviors.size();
				int raised = (int) behaviors.stream().flatMap(v -> v.getStudents().stream())
						.filter(v -> v.getFlower() > 0).count();
				if (ScoreUtils.toRate(raised, total, 0) < threshold_flower) {
					return false;
				}
				int evalRate = this.evaluateDao.getEvalRateByCourseSingleIds(userId, lessonIds);
				return evalRate >= threshold_evaluate;
			});

			// 金榜前三计算
			this.decideAndPush(types, 11, () -> {
				long top3Num = behaviors.stream().filter(v -> {
					Integer rank = v.getStudents().get(0).getRank();
					return rank != null && rank <= 3;
				}).count();
				return top3Num >= threshold_top3;
			});

			// 提前预习
			this.decideAndPush(types, 2, () -> {
				List<ReviewCount> tchCounts = this.reviewWorkStatDao.findTeacherReviewCountByLessonIds(lessonIds,
						HomeworkCst.PHASE_PREVIEW);
				if (tchCounts.size() == 0) {
					return false;
				}
				List<ReviewCount> stuCounts = this.reviewWorkStatDao.getStudentReviewCountByLessonIds(userId, lessonIds,
						HomeworkCst.PHASE_PREVIEW, null);
				int total = tchCounts.size();

				Map<Long, Integer> lessonMap = stuCounts.stream()
						.collect(groupingBy(ReviewCount::getLessonId, summingInt(ReviewCount::getNum)));
				int value = (int) tchCounts.stream().filter(v -> {
					Integer num = lessonMap.get(v.getLessonId());
					return num != null && num == v.getNum();
				}).count();
				return ScoreUtils.toRate(value, total, 0) >= threshold_preview;
			});

			// 温故知新
			this.decideAndPush(types, 10, () -> {
				List<ReviewCount> tchCounts = this.reviewWorkStatDao.findTeacherReviewCountByLessonIds(lessonIds,
						HomeworkCst.PHASE_REVIEW);
				if (tchCounts.size() == 0) {
					return false;
				}
				List<ReviewCount> count12 = this.reviewWorkStatDao.getStudentReviewCountByLessonIds(userId, lessonIds,
						HomeworkCst.PHASE_REVIEW, null);
				List<ReviewCount> count3 = this.noteBookDao.getStudentReviewCountByCourseSingleId(userId, lessonIds);

				Map<Long, Boolean> stateMap12 = Maps.newHashMap();
				Map<Long, Boolean> stateMap3 = Maps.newHashMap();

				if (!count12.isEmpty()) {
					Map<Long, Integer> lessonMap = count12.stream()
							.collect(toMap(ReviewCount::getLessonId, ReviewCount::getNum));
					// 收集复习作业完成量与布置量相同的数据
					tchCounts.forEach(v -> {
						Integer num = lessonMap.get(v.getLessonId());
						stateMap12.put(v.getLessonId(), num != null && num == v.getNum());
					});
				}
				if (count3 != null) {
					// 收集查看笔记次数大于0的数据
					count3.forEach(v -> stateMap3.put(v.getLessonId(), v.getNum() > 0));
				}

				int total = (int) lessonIds.stream().filter(lessonId -> {
					return stateMap12.containsKey(lessonId) || stateMap3.containsKey(lessonId);
				}).count();
				int value = (int) lessonIds.stream().filter(lessonId -> {
					Boolean state12 = stateMap12.get(lessonId);
					Boolean state3 = stateMap3.get(lessonId);
					return Boolean.TRUE.equals(state12) && Boolean.TRUE.equals(state3);
				}).count();
				return ScoreUtils.toRate(value, total, 0) >= threshold_review;
			});
		}

		UserAchievement achievement = new UserAchievement();
		achievement.setTypes(types);
		achievement.setLabel(reportCycle.getLabel());
		achievement.setNum(types.size());
		achievement.setScore(buildAchieveScore(types));
		return achievement;
	}

	private void decideAndPush(List<Integer> types, int type, Supplier<Boolean> supplier) {
		if (supplier.get()) {
			types.add(type);
		}
	}

	private int buildAchieveScore(List<Integer> types) {
		return types.stream().filter(v -> v <= 10).mapToInt(v -> rates[v - 1]).sum();
	}

	private static final int[] rates = { 10, 15, 10, 12, 5, 5, 8, 15, 8, 12 };
}
