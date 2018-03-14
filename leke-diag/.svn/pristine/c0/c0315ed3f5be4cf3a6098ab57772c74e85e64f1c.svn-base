package cn.strong.leke.diag.controller;

import static cn.strong.leke.lessonlog.model.TeacherActions.CALLED_AUTO;
import static cn.strong.leke.lessonlog.model.TeacherActions.CALLED_HAND;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.lesson.mybatis.AttendanceDao;
import cn.strong.leke.diag.dao.lesson.mybatis.EvaluateDao;
import cn.strong.leke.diag.manage.LessonBehaviorService;
import cn.strong.leke.diag.model.report.MiddleInfo;
import cn.strong.leke.diag.model.report.MiddleQuery;
import cn.strong.leke.diag.model.report.ReviewInfo;
import cn.strong.leke.diag.model.report.ReviewQuery;
import cn.strong.leke.diag.report.LessonMiddleReportHandler;
import cn.strong.leke.diag.report.LessonPreviewReportHandler;
import cn.strong.leke.diag.report.LessonReviewReportHandler;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.lessonlog.model.InteractInfo;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.lessonlog.model.StudentBehavior;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

/**
 * 学生行为分析报告
 * @author  andy
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/m/report/lesson/")
public class MobileReportLessonController {

	@Resource
	private EvaluateDao evaluateDao;
	@Resource
	private AttendanceDao attendanceDao;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	@Resource
	private LessonBehaviorService lessonBehaviorService;

	@Resource
	private LessonPreviewReportHandler lessonPreviewReportHandler;
	@Resource
	private LessonMiddleReportHandler lessonMiddleReportHandler;
	@Resource
	private LessonReviewReportHandler lessonReviewReportHandler;

	/**
	 * 预习检查
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/preview/{lessonId}")
	public String preview(@PathVariable("lessonId") Long lessonId, Model model) {
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(lessonId);
		ReviewQuery query = new ReviewQuery();
		query.setLessonId(lessonId);
		query.setCloseTime(lesson.getStartTime());
		ReviewInfo reviewInfo = this.lessonPreviewReportHandler.execute(query);
		Map<String, Object> ReportCst = new HashMap<>();
		ReportCst.put("lesson", lesson);
		ReportCst.put("lessonId", lessonId);
		ReportCst.put("reviewInfo", reviewInfo);
		model.addAttribute("ReportCst", JsonUtils.toJSON(ReportCst));
		return "/auth/m/report/lesson-preview";
	}

	/**
	 * 课堂报告
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/middle/{lessonId}")
	public String middle(@PathVariable("lessonId") Long lessonId, Model model) {
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(lessonId);
		MiddleQuery query = new MiddleQuery();
		query.setLessonId(lessonId);
		query.setTeacherId(lesson.getTeacherId());
		query.setStartTime(lesson.getStartTime());
		query.setCloseTime(lesson.getEndTime());
		MiddleInfo middleInfo = this.lessonMiddleReportHandler.execute(query);
		Map<String, Object> ReportCst = new HashMap<>();
		ReportCst.put("lesson", lesson);
		ReportCst.put("lessonId", lessonId);
		ReportCst.put("middleInfo", middleInfo);
		model.addAttribute("ReportCst", JsonUtils.toJSON(ReportCst));
		return "/auth/m/report/lesson-middle";
	}

	/**
	 * 复习检查
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/review/{lessonId}")
	public String review(@PathVariable("lessonId") Long lessonId, Model model) {
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(lessonId);
		ReviewQuery query = new ReviewQuery();
		query.setLessonId(lessonId);
		ReviewInfo reviewInfo = this.lessonReviewReportHandler.execute(query);
		Map<String, Object> ReportCst = new HashMap<>();
		ReportCst.put("lesson", lesson);
		ReportCst.put("lessonId", lessonId);
		ReportCst.put("reviewInfo", reviewInfo);
		model.addAttribute("ReportCst", JsonUtils.toJSON(ReportCst));
		return "/auth/m/report/lesson-review";
	}

	/**
	 * 课堂中学生行为统计
	 * @param lessonId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/behavior/{lessonId}")
	public String behavior(@PathVariable("lessonId") Long lessonId, Model model) {
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(lessonId);
		List<StudentBehavior> behaviors = this.lessonBehaviorService.findStudentBehaviorByLessonId(lessonId);
		model.addAttribute("lesson", lesson);
		model.addAttribute("behaviors", behaviors);
		return "/auth/m/report/lesson-behavior";
	}

	/**
	 * 考勤名单列表
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping("/attendnames/{lessonId}-{status}")
	public String attendnames(@PathVariable("lessonId") Long lessonId, @PathVariable("status") Integer status,
			Model model) {
		List<String> userNames = this.attendanceDao.findNamesByCourseSingleIdAndStatus(lessonId, status);
		model.addAttribute("status", status);
		model.addAttribute("userNames", userNames);
		return "/auth/m/report/lesson-attendnames";
	}

	/**
	 * 考勤名单列表
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping("/callnames/{lessonId}-{index}")
	public String callnames(@PathVariable("lessonId") Long lessonId, @PathVariable("index") Integer index,
			Model model) {
		LessonBehavior lessonBehavior = this.lessonBehaviorService.getLessonBehaviorByLessonId(lessonId);
		List<InteractInfo> calleds = lessonBehavior.getInteracts().stream()
				.filter(v -> CALLED_AUTO.type == v.getType() || CALLED_HAND.type == v.getType()).collect(toList());
		List<String> userNames = Lists.newArrayList();
		String title = "未点到名单";
		if (calleds.size() > index) {
			List<Long> unids = calleds.get(index).getUnids();
			List<UserBase> users = UserBaseContext.findUserBaseByUserId(unids.toArray(new Long[0]));
			userNames = users.stream().map(UserBase::getUserName).collect(toList());
			title = calleds.get(index).getTopic() + title;
		}
		model.addAttribute("title", title);
		model.addAttribute("userNames", userNames);
		return "/auth/m/report/lesson-callnames";
	}
}
