package cn.strong.leke.diag.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.dao.homework.model.WorkDtlQuery;
import cn.strong.leke.diag.dao.homework.mybatis.HomeworkDtlDao;
import cn.strong.leke.diag.manage.LessonBehaviorService;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.model.report.BehaviorRptQuery;
import cn.strong.leke.diag.model.report.BehaviorRptView;
import cn.strong.leke.diag.model.report.DataZone;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.ScoreRptQuery;
import cn.strong.leke.diag.model.report.StuScoreRptView;
import cn.strong.leke.diag.report.StuComboScoreReportHandler;
import cn.strong.leke.diag.report.StuSubjScoreReportHandler;
import cn.strong.leke.diag.report.StuanalyPersonBehaviorReportHandler;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Controller
@RequestMapping({ "/auth/common/report/stuanaly/", "/auth/hd/report/stuanaly/" })
public class ReportStuanalyController {

	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private ReportCycleService reportCycleService;
	@Resource
	private LessonBehaviorService lessonBehaviorService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private StuComboScoreReportHandler stuComboScoreReportHandler;
	@Resource
	private StuSubjScoreReportHandler stuSubjScoreReportHandler;
	@Resource
	private StuanalyPersonBehaviorReportHandler stuanalyPersonBehaviorReportHandler;

	@RequestMapping("/index")
	public String index() {
		return "redirect:person-combined.htm";
	}

	/**
	 * 获取学生行政班信息
	 * @param userId
	 * @return
	 */
	private Clazz getOrganClazzByUserId(Long userId) {
		ClazzQuery query = new ClazzQuery();
		query.setUserId(userId);
		query.setRoleId(RoleCst.STUDENT);
		query.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		return CollectionUtils.getFirst(clazzList);
	}

	/**
	 * 学生成绩报告页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/person-score", method = RequestMethod.GET)
	public String personScore(@RequestParam(defaultValue = "0") int mode, Model model, HttpServletRequest request) {
		User user = UserContext.user.get();
		Clazz clazz = this.getOrganClazzByUserId(user.getId());
		Long classId = clazz != null ? clazz.getClassId() : null;
		List<DataZone> zones = Lists.newArrayList();
		if (mode == 0 || mode == 1) {
			zones.add(new DataZone("综合", classId, 0L));
		}
		if (mode == 0 || mode == 2) {
			List<SubjectRemote> subjectRemotes = UserContext.user.findSubjectsOfCurrentSchool();
			subjectRemotes.forEach(v -> zones.add(new DataZone(v.getSubjectName(), classId, v.getSubjectId())));
		}
		List<Map<String, Object>> types = this.reportCycleService.buildReportTypes();
		Map<String, Object> ReportCst = new HashMap<>();
		ReportCst.put("userName", user.getUserName());
		ReportCst.put("types", types);
		ReportCst.put("zones", zones);
		ReportCst.put("mode", mode);
		ReportCst.put("device", request.getAttribute("device"));
		model.addAttribute("Csts", JsonUtils.toJSON(ReportCst));
		return "/auth/report/stuanaly/person-score";
	}

	/**
	 * 学生成绩报告查询
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/person-score-data", method = RequestMethod.POST)
	public JsonResult personScoreData(ScoreRptQuery query) {
		query.setStudentId(UserContext.user.getUserId());
		StuScoreRptView view;
		if (query.getSubjectId() > 0) {
			view = this.stuSubjScoreReportHandler.execute(query);
		} else {
			view = this.stuComboScoreReportHandler.execute(query);
		}
		return new JsonResult().addDatas("view", view);
	}

	/**
	 * 综合成绩报告
	 * @param model
	 * @return
	 */
	@RequestMapping("/person-combined")
	public String personCombined(Model model, HttpServletRequest request) {
		model.addAttribute("subnav", "person-combined");
		model.addAttribute("title", "综合分析报告");
		return this.personScore(1, model, request);
	}

	/**
	 * 学科成绩报告
	 * @param model
	 * @return
	 */
	@RequestMapping("/person-subject")
	public String personSubject(Model model, HttpServletRequest request) {
		model.addAttribute("subnav", "person-subject");
		model.addAttribute("title", "学科分析报告");
		return this.personScore(2, model, request);
	}

	/**
	 * 行为分析报告页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/person-behavior")
	public String behavior(Model model, HttpServletRequest request) {
		User user = UserContext.user.get();
		List<DataZone> zones = Lists.newArrayList(new DataZone("全部", 0L, 0L));
		List<Map<String, Object>> types = this.reportCycleService.buildReportTypes();
		Map<String, Object> ReportCst = new HashMap<>();
		ReportCst.put("userName", user.getUserName());
		ReportCst.put("types", types);
		ReportCst.put("zones", zones);
		ReportCst.put("device", request.getAttribute("device"));
		model.addAttribute("Csts", JsonUtils.toJSON(ReportCst));
		return "/auth/report/stuanaly/person-behavior";
	}

	/**
	 * 行为分析报告数据
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/person-behavior-data")
	public JsonResult behavior(BehaviorRptQuery query) {
		query.setStudentId(UserContext.user.getUserId());
		BehaviorRptView view = this.stuanalyPersonBehaviorReportHandler.execute(query);
		view.setUserName(UserContext.user.getUserName());
		return new JsonResult().addDatas("view", view);
	}

	/**
	 * 行为分析报告作业完成详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/person-behavior-homework")
	public String personBehaviorHomework(Integer cycleId, Model model) {
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(cycleId);
		model.addAttribute("reportCycle", reportCycle);
		return "/auth/report/stuanaly/person-behavior-homework";
	}

	/**
	 * 行为分析报告作业完成详情数据
	 * @param cycleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/person-behavior-homework-data")
	public JsonResult personBehaviorHomeworkData(Integer cycleId, Integer submitStatus, Page page) {
		Long studentId = UserContext.user.getUserId();
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(cycleId);
		WorkDtlQuery query = new WorkDtlQuery();
		query.setStudentId(studentId);
		query.setStartTime(reportCycle.getStart());
		query.setEndTime(reportCycle.getEnd());
		query.setSubmitStatus(submitStatus);
		List<Map<String, Object>> list = this.homeworkDtlDao.findWorkDtlsByQuery(query, page);
		page.setDataList(list);
		return new JsonResult().addDatas("page", page);
	}

	/**
	 * 行为分析报告作业课堂行为列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/person-behavior-interact")
	public String personBehaviorInteract(Integer cycleId, Model model) {
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(cycleId);
		model.addAttribute("reportCycle", reportCycle);
		return "/auth/report/stuanaly/person-behavior-interact";
	}

	/**
	 * 行为分析报告作业课堂行为列表数据
	 * @param cycleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/person-behavior-interact-data")
	public JsonResult personBehaviorInteractData(Integer cycleId, Page page) {
		Long studentId = UserContext.user.getUserId();
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(cycleId);
		List<LessonBehavior> list = this.lessonBehaviorService.findStudentBehaviorByStudentId(studentId,
				reportCycle.getStart(), reportCycle.getEnd(), page);
		page.setDataList(list);
		return new JsonResult().addDatas("page", page);
	}
}
