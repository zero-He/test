package cn.strong.leke.diag.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.pinyin.Pinyin;
import cn.strong.leke.diag.dao.homework.model.UserWorkSubmitRate;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDtlDao;
import cn.strong.leke.diag.dao.lesson.model.UserAttendInfo;
import cn.strong.leke.diag.dao.lesson.mybatis.AttendanceDao;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.ScoreRptQuery;
import cn.strong.leke.diag.model.report.StuScoreRptView;
import cn.strong.leke.diag.model.studentMonitor.HomeworkDetailBean;
import cn.strong.leke.diag.model.tchanaly.TchanalyKlassBehaviorRptView;
import cn.strong.leke.diag.model.tchanaly.TchanalyKlassScoreRptView;
import cn.strong.leke.diag.model.tchanaly.TchanalyRptQuery;
import cn.strong.leke.diag.model.tchanaly.TchanalyTeachBehaviorRptView;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.report.StuSubjScoreReportHandler;
import cn.strong.leke.diag.report.TchanalyKlassBehaviorReportHandler;
import cn.strong.leke.diag.report.TchanalyKlassScoreReportHandler;
import cn.strong.leke.diag.report.TchanalyTeachBehaviorReportHandler;
import cn.strong.leke.diag.service.studentMonitor.HomeworkAnalyseService;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.lesson.model.query.ScheduleQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Controller
@RequestMapping({ "/auth/common/report/tchanaly/", "/auth/hd/report/tchanaly/" })
public class ReportTchanalyController extends ReportBaseController {

	@Resource
	private AttendanceDao attendanceDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	@Resource
	private StuSubjScoreReportHandler stuSubjScoreReportHandler;
	@Resource
	private TchanalyKlassScoreReportHandler tchanalyKlassScoreReportHandler;
	@Resource
	private TchanalyTeachBehaviorReportHandler tchanalyTeachBehaviorReportHandler;
	@Resource
	private TchanalyKlassBehaviorReportHandler tchanalyKlassBehaviorReportHandler;
	@Resource
	private HomeworkAnalyseService homeworkAnalyseService;
	
	private List<Long> findReportCycleLessonIds(Long teacherId, Long subjectId, Long classId, ReportCycle reportCycle) {
		ScheduleQuery scheduleQuery = new ScheduleQuery();
		scheduleQuery.setClassId(classId);
		scheduleQuery.setRoleId(RoleCst.TEACHER);
		scheduleQuery.setTeacherId(teacherId);
		scheduleQuery.setSubjectId(subjectId);
		scheduleQuery.setStartTime(reportCycle.getStart());
		scheduleQuery.setEndTime(reportCycle.getEnd());
		List<LessonVM> lessonList = this.lessonRemoteService.findScheduleByQuery(scheduleQuery);
		return lessonList.stream().map(LessonVM::getCourseSingleId).collect(toList());
	}

	/**
	 * 学科老师：教学行为分析
	 * @param model
	 * @return
	 */
	@RequestMapping("/klass-score")
	public String klassScore(Model model) {
		Map<String, Object> ReportCst = new HashMap<>();
		this.setReportCycleModel(ReportCst);
		this.setKlassModel(ReportCst);
		ReportCst.put("userName", UserContext.user.getUserName());
		model.addAttribute("Csts", JsonUtils.toJSON(ReportCst));
		return "/auth/report/tchanaly/klass-score";
	}

	/**
	 * 班级成绩报告数据查询。
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/klass-score-data")
	public JsonResult score2(TchanalyRptQuery query) {
		query.setTeacherId(UserContext.user.getUserId());
		TchanalyKlassScoreRptView view = this.tchanalyKlassScoreReportHandler.execute(query);
		view.setUserName(UserContext.user.getUserName());
		return new JsonResult().addDatas("view", view);
	}

	/**
	 * 班级下学生成绩报告
	 * @param classId
	 * @param subjectId
	 * @param hwType
	 * @param cycleId
	 * @param studentId
	 * @param model
	 * @return
	 */
	@RequestMapping("/klass-score-detail/{classId:\\d+}-{subjectId:\\d+}-{cycleId:\\d+}-{studentId:\\d+}")
	public String detail(@PathVariable("classId") Long classId, @PathVariable("subjectId") Long subjectId,
			@PathVariable("cycleId") Integer cycleId, @PathVariable("studentId") Long studentId, Model model) {
		ScoreRptQuery query = new ScoreRptQuery();
		query.setClassId(classId);
		query.setSubjectId(subjectId);
		query.setCycleId(cycleId);
		query.setStudentId(studentId);
		StuScoreRptView view = this.stuSubjScoreReportHandler.execute(query);
		String userName = UserBaseContext.getUserBaseByUserId(studentId).getUserName();

		Map<String, Object> ReportCst = new HashMap<>();
		ReportCst.put("view", view);
		ReportCst.put("subjId", subjectId);
		ReportCst.put("userId", query.getStudentId());
		ReportCst.put("userName", userName);
		model.addAttribute("userName", userName);
		model.addAttribute("Csts", JsonUtils.toJSON(ReportCst));
		return "/auth/report/tchanaly/klass-score-detail";
	}

	/**
	 * 学科老师：教学行为分析
	 * @param model
	 * @return
	 */
	@RequestMapping("/klass-behavior")
	public String klassBehavior(Model model) {
		Map<String, Object> ReportCst = new HashMap<>();
		this.setReportCycleModel(ReportCst);
		this.setKlassModel(ReportCst);
		ReportCst.put("userName", UserContext.user.getUserName());
		model.addAttribute("Csts", JsonUtils.toJSON(ReportCst));
		return "/auth/report/tchanaly/klass-behavior";
	}

	/**
	 * 学科老师：教学行为分析
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/klass-behavior-data")
	public Object klassBehaviorData(TchanalyRptQuery query) {
		query.setTeacherId(UserContext.user.getUserId());
		TchanalyKlassBehaviorRptView view = this.tchanalyKlassBehaviorReportHandler.execute(query);
		view.setUserName(UserContext.user.getUserName());
		return new JsonResult().addDatas("view", view);
	}

	/**
	 * 学科老师：教学行为分析
	 * @param model
	 * @return
	 */
	@RequestMapping("/teach-behavior")
	public String teachBehavior(Model model) {
		Map<String, Object> ReportCst = new HashMap<>();
		this.setReportCycleModel(ReportCst);
		this.setKlassModel(ReportCst);
		ReportCst.put("userName", UserContext.user.getUserName());
		model.addAttribute("Csts", JsonUtils.toJSON(ReportCst));
		return "/auth/report/tchanaly/teach-behavior";
	}

	/**
	 * 学科老师：教学行为分析
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/teach-behavior-data")
	public Object teachBehaviorData(TchanalyRptQuery query) {
		query.setTeacherId(UserContext.user.getUserId());
		TchanalyTeachBehaviorRptView view = this.tchanalyTeachBehaviorReportHandler.execute(query);
		view.setUserName(UserContext.user.getUserName());
		return new JsonResult().addDatas("view", view);
	}

	/**
	 * 学科老师：教学行为分析
	 * @param query
	 * @return
	 */
	@RequestMapping("/klass-behavior-attend/{classId:\\d+}-{subjectId:\\d+}-{cycleId:\\d+}")
	public Object klassBehaviorAttend(@PathVariable("classId") Long classId, @PathVariable("subjectId") Long subjectId,
			@PathVariable("cycleId") Integer cycleId, Model model) {
		Long teacherId = UserContext.user.getUserId();
		Clazz clazz = this.klassRemoteService.getClazzByClassId(classId);
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(cycleId);
		List<Long> courseSingleIds = this.findReportCycleLessonIds(teacherId, subjectId, classId, reportCycle);
		List<UserAttendInfo> userAttendInfos;
		if (CollectionUtils.isNotEmpty(courseSingleIds)) {
			userAttendInfos = this.attendanceDao.findUserAttendInfoByCourseSingleIds(courseSingleIds);
			userAttendInfos.forEach(v -> v.setUserNamePy(Pinyin.toPinyinAbbr(v.getUserName())));
		} else {
			userAttendInfos = new ArrayList<>();
		}
		model.addAttribute("clazz", clazz);
		model.addAttribute("reportCycle", reportCycle);
		model.addAttribute("datas", JsonUtils.toJSON(userAttendInfos));
		return "/auth/report/tchanaly/klass-behavior-attend";
	}

	/**
	 * 学科老师：教学行为分析
	 * @param query
	 * @return
	 */
	@RequestMapping("/klass-behavior-work/{classId:\\d+}-{subjectId:\\d+}-{cycleId:\\d+}")
	public Object klassBehaviorWork(@PathVariable("classId") Long classId, @PathVariable("subjectId") Long subjectId,
			@PathVariable("cycleId") Integer cycleId, Model model) {
		Long teacherId = UserContext.user.getUserId();
		Clazz clazz = this.klassRemoteService.getClazzByClassId(classId);
		List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(classId);
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(cycleId);
		List<UserWorkSubmitRate> userWorkSubmitRates = Collections.emptyList();
		RequestVo vo = new RequestVo();
		vo.setSchoolId(UserContext.user.getCurrentSchoolId());
		vo.setClassId(classId);
		vo.setSubjectId(subjectId);
		vo.setStartDate(DateUtils.formatDate(reportCycle.getStart()));
		vo.setEndDate(DateUtils.formatDate(reportCycle.getEnd()));
		if (!userIds.isEmpty()) {
			userWorkSubmitRates = this.homeworkDtlDao.findUserWorkSubmitRatesByUserIds(teacherId, subjectId, 
					reportCycle.getStart(), reportCycle.getEnd(), classId);
		}
		model.addAttribute("clazz", clazz);
		model.addAttribute("reportCycle", reportCycle);
		List<Map<String, String>> hmList = new ArrayList<>();
		userWorkSubmitRates.forEach(h->{
			Map<String, String> m = new HashMap<>();
			m.put("studentName", String.valueOf(h.getUserName()));
			m.put("stuHomeworkNum", String.valueOf(h.getShouldSubmitCount()));
			m.put("submitNum", String.valueOf(h.getNormalSubmitCount()));
			m.put("lateSubmitNum", String.valueOf(h.getLateSubmitCount()));
			m.put("noSubmitNum", String.valueOf(h.getNoSubmitCount()));
			m.put("submitNumPro", h.getNormalSubmitRate() + "%(" + h.getNormalSubmitCount() +  ")");
			m.put("lateSubmitNumPro", h.getLateSubmitRate() + "%(" + h.getLateSubmitCount() +  ")");
			m.put("noSubmitNumPro", h.getNoSubmitRate() + "%(" + h.getNoSubmitCount() +  ")");
			hmList.add(m);
		});
		model.addAttribute("datas", JsonUtils.toJSON(hmList));
		return "/auth/report/tchanaly/klass-behavior-work";
	}
}
