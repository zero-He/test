package cn.strong.leke.diag.controller;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.diag.dao.homework.model.AnalysePhase;
import cn.strong.leke.diag.model.Homework;
import cn.strong.leke.diag.model.HomeworkDtl;
import cn.strong.leke.diag.model.report.HwRptQuery;
import cn.strong.leke.diag.model.report.HwRptView;
import cn.strong.leke.diag.report.HomeworkReportHandler;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.diag.util.ExcelWriter;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.user.User;

@Controller
@RequestMapping({ "/auth/common/report/homework", "/auth/hd/report/homework" })
public class ReportHomeworkController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkReportHandler homeworkReportHandler;

	/**
	 * 老师作业报告
	 * @param homeworkId
	 * @param model
	 * @return
	 */
	@RequestMapping("/{homeworkId}")
	public String overall(@PathVariable("homeworkId") Long homeworkId, Model model, HttpServletRequest request) {
		// 验证
		User user = UserContext.user.get();
		Homework homework = this.homeworkService.getHomeworkByHomeworkId(homeworkId);
		Long roleId = user.getCurrentRole().getId();
		if (RoleCst.TEACHER.equals(roleId)) {
			Validation.isPageNotFound(!homework.getTeacherId().equals(user.getId()));
		} else {
			Validation.isPageNotFound(!homework.getSchoolId().equals(user.getCurrentSchool().getId()));
		}
		// 多阶段切换
		if (homework.getBeikeGuid() != null) {
			List<AnalysePhase> phases = this.homeworkService.findAnalysePhasesByBeikeGuid(homework.getBeikeGuid());
			if (phases.size() > 1) {
				phases.forEach(v -> v.setSelected(v.getHomeworkId().equals(homeworkId)));
				model.addAttribute("phases", phases);
			}
		}
		// 查询
		HwRptQuery query = new HwRptQuery();
		query.setHomeworkId(homeworkId);
		HwRptView hwRptView = this.homeworkReportHandler.execute(query);
		// 返回
		Map<String, Object> ReportCst = Maps.newHashMap();
		ReportCst.put("view", hwRptView);
		ReportCst.put("userName", user.getUserName());
		ReportCst.put("homeworkId", homeworkId);
		ReportCst.put("homeworkName", homework.getHomeworkName());
		ReportCst.put("classId", homework.getClassId());
		ReportCst.put("subjectId", homework.getSubjectId());
		ReportCst.put("device", request.getAttribute("device"));
		model.addAttribute("ReportCst", JsonUtils.toJSON(ReportCst));
		return "/auth/report/homework/overall";
	}

	/**
	 * 老师查看学生作业报告
	 * @param homeworkId
	 * @param studentId
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{homeworkId:\\d+}-{studentId:\\d+}")
	public String detail(@PathVariable("homeworkId") Long homeworkId, @PathVariable("studentId") Long studentId,
			Model model, HttpServletRequest request) {
		// 验证
		User user = UserContext.user.get();
		Homework homework = this.homeworkService.getHomeworkByHomeworkId(homeworkId);
		Long roleId = user.getCurrentRole().getId();
		if (RoleCst.TEACHER.equals(roleId)) {
			Validation.isPageNotFound(!homework.getTeacherId().equals(user.getId()));
		} else {
			Validation.isPageNotFound(!homework.getSchoolId().equals(user.getCurrentSchool().getId()));
		}
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlByHomeworkIdAndStudentId(homeworkId, studentId);
		Validation.isPageNotFound(homeworkDtl.getCorrectTime() == null);
		// 查询
		HwRptQuery query = new HwRptQuery();
		query.setHomeworkId(homeworkId);
		query.setHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
		HwRptView hwRptView = this.homeworkReportHandler.execute(query);
		// 返回
		Map<String, Object> ReportCst = Maps.newHashMap();
		ReportCst.put("myself", false);
		ReportCst.put("view", hwRptView);
		ReportCst.put("userId", homeworkDtl.getStudentId());
		ReportCst.put("userName", homeworkDtl.getStudentName());
		ReportCst.put("device", request.getAttribute("device"));
		ReportCst.put("homeworkId", homeworkId);
		model.addAttribute("ReportCst", JsonUtils.toJSON(ReportCst));
		model.addAttribute("userNamePrefix", homeworkDtl.getStudentName() + "的");
		return "/auth/report/homework/person";
	}

	/**
	 * 学生查看自已作业报告
	 * @param homeworkId
	 * @param homeworkDtlId
	 * @param model
	 * @return
	 */
	@RequestMapping("/person/{homeworkId:\\d+}-{homeworkDtlId:\\d+}")
	public String person(@PathVariable("homeworkId") Long homeworkId, @PathVariable("homeworkDtlId") Long homeworkDtlId,
			Model model, HttpServletRequest request) {
		// 验证
		User user = UserContext.user.get();
		Validation.isForbidden(!RoleCst.STUDENT.equals(user.getCurrentRole().getId()));
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlByHomeworkDtlId(homeworkDtlId);
		Validation.isPageNotFound(homeworkDtl.getCorrectTime() == null);
		Validation.isPageNotFound(!homeworkDtl.getStudentId().equals(user.getId()));
		// 多阶段切换
		Homework homework = this.homeworkService.getHomeworkByHomeworkId(homeworkId);
		if (homework.getBeikeGuid() != null) {
			List<AnalysePhase> phases = this.homeworkDtlService
					.findStudentAnalysePhasesByBeikeGuid(homework.getBeikeGuid(), homeworkDtl.getStudentId());
			if (phases.size() > 1) {
				phases.forEach(v -> v.setSelected(v.getHomeworkId().equals(homeworkId)));
				model.addAttribute("phases", phases);
				model.addAttribute("studentId", homeworkDtl.getStudentId());
			}
		}
		// 查询
		HwRptQuery query = new HwRptQuery();
		query.setHomeworkId(homeworkId);
		query.setHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
		HwRptView hwRptView = this.homeworkReportHandler.execute(query);
		// 返回
		Map<String, Object> ReportCst = Maps.newHashMap();
		ReportCst.put("myself", true);
		ReportCst.put("view", hwRptView);
		ReportCst.put("userId", user.getId());
		ReportCst.put("userName", user.getUserName());
		ReportCst.put("device", request.getAttribute("device"));
		ReportCst.put("homeworkId", homeworkId);
		model.addAttribute("ReportCst", JsonUtils.toJSON(ReportCst));
		return "/auth/report/homework/person";
	}

	/**
	 * 导出成绩列表。
	 */
	@RequestMapping("/export")
	public void export(Long homeworkId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<HomeworkDtl> list = this.homeworkDtlService.findHomeworkDtlByHomeworkId(homeworkId);
		List<Map<String, Object>> datas = list.stream().map(homeworkDtl -> {
			Map<String, Object> item = new HashMap<>();
			item.put("name", homeworkDtl.getStudentName());
			if (homeworkDtl.getCorrectTime() != null) {
				item.put("level", 1);
				item.put("score", homeworkDtl.getScore());
			} else if (homeworkDtl.getSubmitTime() != null) {
				item.put("level", 2);
				item.put("score", "+" + homeworkDtl.getScore());
			} else {
				item.put("level", 3);
				item.put("score", "--");
			}
			return item;
		}).collect(toList());
		list = null;
		datas.sort((a, b) -> Integer.compare((Integer) a.get("level"), (Integer) b.get("level")));
		for (int i = 0; i < datas.size(); i++) {
			datas.get(i).put("index", i + 1);
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String title = "成绩单-" + DateUtils.formatDate(new Date());
		String fileName = FileUtils.getEncodingFileName(title + ".xls", request.getHeader("user-agent"));
		response.setHeader("Content-disposition", "attachment;" + fileName);

		ExcelWriter writer = new ExcelWriter();
		writer.createSheet("成绩单");
		writer.appendHead(Arrays.asList("排名", "姓名", "得分"));
		writer.appendData(datas, Arrays.asList("index", "name", "score"));
		writer.writeEnd(response.getOutputStream());
	}
}
