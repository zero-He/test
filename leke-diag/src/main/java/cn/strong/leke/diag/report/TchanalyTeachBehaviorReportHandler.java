package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.dao.homework.model.HomeworkCst;
import cn.strong.leke.diag.dao.homework.model.ResTypeStat;
import cn.strong.leke.diag.dao.homework.model.WorkStatInfo;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDao;
import cn.strong.leke.diag.dao.homework.mybatis.ReviewWorkStatDao;
import cn.strong.leke.diag.dao.lesson.model.AttendStatInfo2;
import cn.strong.leke.diag.dao.lesson.model.EvaluateInfo;
import cn.strong.leke.diag.dao.lesson.mongo.LessonBehaviorDao;
import cn.strong.leke.diag.dao.lesson.mybatis.AttendanceDao;
import cn.strong.leke.diag.dao.lesson.mybatis.EvaluateDao;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.model.NameValue;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.tchanaly.TchanalyRptQuery;
import cn.strong.leke.diag.model.tchanaly.TchanalyTeachBehaviorRptView;
import cn.strong.leke.diag.model.tchanaly.TchanalyTeachBehaviorRptView.BeikeStatInfo;
import cn.strong.leke.diag.model.tchanaly.TchanalyTeachBehaviorRptView.InteractStatInfo;
import cn.strong.leke.diag.util.ScoreUtils;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.lesson.model.query.ScheduleQuery;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Component
public class TchanalyTeachBehaviorReportHandler
		implements ReportHandler<TchanalyRptQuery, TchanalyTeachBehaviorRptView> {

	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private EvaluateDao evaluateDao;
	@Resource
	private AttendanceDao attendanceDao;
	@Resource
	private ReviewWorkStatDao reviewWorkStatDao;
	@Resource
	private LessonBehaviorDao lessonBehaviorDao;
	@Resource
	private ReportCycleService reportCycleService;
	@Resource
	private ILessonRemoteService lessonRemoteService;

	@Override
	public TchanalyTeachBehaviorRptView execute(TchanalyRptQuery query) {
		Long teacherId = query.getTeacherId();
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(query.getCycleId());
		List<ReportCycle> nearCycles = this.reportCycleService.findNear6ReportCycle(reportCycle);
		List<Long> lessonIds = this.findReportCycleLessonIds(query, reportCycle);
		Integer totalNum = lessonIds.size();
		if (totalNum > 0) {
			// 过滤未上课课堂ID
			lessonIds = this.attendanceDao.filterNotAttendCourseSingleIds(lessonIds);
		}

		// 备课
		BeikeStatInfo beikeInfo = this.getBeikeInfoByCourseSingleIds(lessonIds);
		// 评价
		EvaluateInfo evalInfo = this.getEvaluateInfo(query, nearCycles);
		// 互动
		InteractStatInfo interactInfo = this.getTeachInteractStatInfoByReportCycle(teacherId, nearCycles);
		// 批改
		WorkStatInfo workInfo = this.homeworkDao.getTeachWorkStatInfoByTeacherIdAndTime(teacherId,
				reportCycle.getStart(), reportCycle.getEnd());
		// 考勤
		AttendStatInfo2 attendInfo = this.getTeachAttendStatInfoByCourseSingleIds(lessonIds, totalNum);

		TchanalyTeachBehaviorRptView view = new TchanalyTeachBehaviorRptView();
		view.setBeikeInfo(beikeInfo);
		view.setEvalInfo(evalInfo);
		view.setInteractInfo(interactInfo);
		view.setWorkInfo(workInfo);
		view.setAttendInfo(attendInfo);
		return view;
	}

	private EvaluateInfo getEvaluateInfo(TchanalyRptQuery query, List<ReportCycle> reportCycles) {
		reportCycles = Lists.newArrayList(reportCycles);
		Collections.reverse(reportCycles);
		EvaluateInfo evaluateInfo = null;
		List<NameValue> trends = new ArrayList<>();
		for (ReportCycle reportCycle : reportCycles) {
			List<Long> lessonIds = this.findReportCycleLessonIds(query, reportCycle);
			evaluateInfo = this.getEvaluateInfoByCourseSingleIds(lessonIds);
			trends.add(this.calcEvaluateAvgScore(reportCycle, evaluateInfo));
		}
		evaluateInfo.setTrends(trends);
		return evaluateInfo;
	}

	private NameValue calcEvaluateAvgScore(ReportCycle reportCycle, EvaluateInfo evaluateInfo) {
		Double total = evaluateInfo.getInteractionScore() + evaluateInfo.getProfessionalScore()
				+ evaluateInfo.getRhythmScore();
		Double score = ScoreUtils.toFixed(total / 3, 1);
		return new NameValue(reportCycle.getLabel(), score);
	}

	private EvaluateInfo getEvaluateInfoByCourseSingleIds(List<Long> lessonIds) {
		if (lessonIds.isEmpty()) {
			return new EvaluateInfo();
		}
		EvaluateInfo evaluateInfo = this.evaluateDao.getEvaluateInfoByCourseSingleIds(lessonIds);
		if (evaluateInfo == null) {
			evaluateInfo = new EvaluateInfo();
		}
		return evaluateInfo;
	}

	private InteractStatInfo getTeachInteractStatInfoByReportCycle(Long teacherId, List<ReportCycle> nearCycles) {
		List<LessonBehavior> behaviors = this.lessonBehaviorDao.findTeacherBehaviorByTeacherIdAndReportCycle(teacherId,
				nearCycles.get(0));
		InteractStatInfo interactInfo = new InteractStatInfo();
		interactInfo.setAuthedNum(sum(behaviors, LessonBehavior::getAuthedNum));
		interactInfo.setCalledNum(sum(behaviors, LessonBehavior::getCallNum));
		interactInfo.setQuickNum(sum(behaviors, LessonBehavior::getQuickNum));
		interactInfo.setDiscuNum(sum(behaviors, LessonBehavior::getDiscuNum));
		interactInfo.setExamNum(sum(behaviors, LessonBehavior::getExamNum));
		interactInfo.setDelayNum(count(behaviors, LessonBehavior::getIsOvertime));
		interactInfo.setFlowerNum(sum(behaviors, LessonBehavior::getFlower));
		interactInfo.setTotalNum(interactInfo.getAuthedNum() + interactInfo.getCalledNum() + interactInfo.getQuickNum()
				+ interactInfo.getDiscuNum() + interactInfo.getExamNum() + interactInfo.getDelayNum());
		List<LessonBehavior> behaviors2 = this.lessonBehaviorDao.findTeacherBehaviorByTeacherIdAndReportCycle(teacherId,
				nearCycles.get(1));
		interactInfo.setPrevFlowerNum(sum(behaviors2, LessonBehavior::getFlower));
		interactInfo.setPrevTotalNum(sum(behaviors2, (be) -> {
			int result = 0;
			result += be.getAuthedNum() != null ? be.getAuthedNum() : 0;
			result += be.getCallNum() != null ? be.getCallNum() : 0;
			result += be.getQuickNum() != null ? be.getQuickNum() : 0;
			result += be.getDiscuNum() != null ? be.getDiscuNum() : 0;
			result += be.getExamNum() != null ? be.getExamNum() : 0;
			result += Boolean.TRUE.equals(be.getIsOvertime()) ? 1 : 0; // 拖堂不算互动
			return result;
		}));
		return interactInfo;
	}

	static <T> int sum(Collection<T> coll, Function<T, Integer> mapper) {
		return coll.stream().map(mapper).filter(v -> v != null).mapToInt(v -> v).sum();
	}

	static <T> int count(Collection<T> coll, Function<T, Boolean> mapper) {
		return (int) coll.stream().map(mapper).filter(v -> Boolean.TRUE.equals(v)).count();
	}

	private BeikeStatInfo getBeikeInfoByCourseSingleIds(List<Long> lessonIds) {
		BeikeStatInfo beikeInfo = new BeikeStatInfo();
		beikeInfo.setTotalNum(lessonIds.size());
		if (beikeInfo.getTotalNum() == 0) {
			return beikeInfo;
		}
		int beikeNum = this.reviewWorkStatDao.getBeikeLessonNumByLessonIds(lessonIds);
		if (beikeNum == 0) {
			return beikeInfo;
		}
		beikeInfo.setBeikeNum(beikeNum);
		List<ResTypeStat> resTypeStats = this.reviewWorkStatDao.findResTypeStatByLessonIds(lessonIds);

		resTypeStats.stream().filter(v -> v.getResType() == HomeworkCst.RES_TYPE_CW).findFirst()
				.ifPresent(v -> beikeInfo.setResType1(v.getLessonNum()));
		resTypeStats.stream().filter(v -> v.getResType() == HomeworkCst.RES_TYPE_MC).findFirst()
				.ifPresent(v -> beikeInfo.setResType2(v.getLessonNum()));
		resTypeStats.stream().filter(v -> v.getResType() == HomeworkCst.RES_TYPE_PAP).findFirst()
				.ifPresent(v -> beikeInfo.setResType3(v.getLessonNum()));
		return beikeInfo;
	}

	private AttendStatInfo2 getTeachAttendStatInfoByCourseSingleIds(List<Long> lessonIds, Integer totalNum) {
		AttendStatInfo2 attendStatInfo;
		if (lessonIds.size() > 0) {
			attendStatInfo = this.attendanceDao.getTeachAttendStatInfoByCourseSingleIds(lessonIds);
		} else {
			attendStatInfo = new AttendStatInfo2();
		}
		attendStatInfo.setLessonNum(totalNum);
		return attendStatInfo;
	}

	private List<Long> findReportCycleLessonIds(TchanalyRptQuery query, ReportCycle reportCycle) {
		ScheduleQuery scheduleQuery = new ScheduleQuery();
		scheduleQuery.setRoleId(RoleCst.TEACHER);
		scheduleQuery.setTeacherId(query.getTeacherId());
		scheduleQuery.setStartTime(reportCycle.getStart());
		scheduleQuery.setEndTime(reportCycle.getEnd());
		List<LessonVM> lessonList = this.lessonRemoteService.findScheduleByQuery(scheduleQuery);
		return lessonList.stream().map(LessonVM::getCourseSingleId).collect(toList());
	}
}
