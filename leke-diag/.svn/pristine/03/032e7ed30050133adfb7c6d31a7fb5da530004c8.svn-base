package cn.strong.leke.diag.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.GradeContext;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.GradeStatsDto;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.util.DiagCst;
import cn.strong.leke.diag.util.DiagHelp;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Controller
@RequestMapping("/auth/provost")
public class ProvostStatController {

	@Resource
	private SchoolStatsService schoolStatsService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	/**
	 * 班级成绩统计
	 * @param model
	 */
	@RequestMapping("/achievement/achievement")
	public void achievement(ClazzQuery query, Model model) {
		User user = UserContext.user.get();
		List<GradeRemote> gradeList = UserContext.user.findGradesOfCurrentSchool();
		query.setType(DiagCst.CLAZZ_VIRTUAL);
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		query.setGradeId(gradeList.get(0).getGradeId());
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		model.addAttribute("classes", clazzList);
	}

	/**
	 * 班级成绩统计
	 * @param model
	 */
	@RequestMapping("/achievement/classStatistical")
	public void classStatistical() {
		
	}
	/*---------------------------班级优劣学科分析----------------------*/
	/**
	 * 班级优劣学科分析
	 * @param model
	 */
	@RequestMapping("/swot/clsAnalysis")
	public String clsAnalysis(Model model) {
		addStartFinishDateToModel(model);
		return "/auth/provost/swot/stuAnalysis";
	}
	
	/**
	 * 班级优劣学科分析 data
	 * @param model
	 */
	@ResponseBody
	@RequestMapping("/swot/findStuAnalysis")
	public JsonResult findStuAnalysis() {
		JsonResult jsonResult = new JsonResult();
		long schoolId = UserContext.user.getCurrentSchoolId();
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		List<GradeRemote> gradeList = new ArrayList<GradeRemote>();
		for (Long schoolStageId : school.getSchoolStageIds()) {
			gradeList.addAll(SchoolStageContext.getSchoolStage(schoolStageId).getGrades());
		}
		jsonResult.addDatas("dataList", gradeList);
		return jsonResult;
	}
	
	/**
	 * 班级优劣学科分析 -详情页
	 * @param model
	 */
	@RequestMapping("/swot/stuAnalysisDetails")
	public void stuAnalysisDetails(Long gradeId, Model model) {
		model.addAttribute("gradeId", gradeId);
		GradeRemote gradeRemote = GradeContext.getGrade(gradeId);
		if (gradeRemote != null) {
			model.addAttribute("gradeName", gradeRemote.getGradeName());
		}
		addStartFinishDateToModel(model);
	}
	/**
	 * 班级优劣学科分析 -详情页 data
	 * @param model
	 */
	@ResponseBody
	@RequestMapping("/swot/findStuAnalysisDetails")
	public JsonResult findStuAnalysisDetails(Long gradeId, Page page) {
		JsonResult jsonResult = new JsonResult();
		//从mongodb 中读取数据 
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<SubjStatsDto> dataList = schoolStatsService.findGradeSubjScore(schoolId, gradeId);
		jsonResult.addDatas("dataList", dataList);
		return jsonResult;
	}
	
	/**
	 * 班级优劣学科分析 -详情页 -图标分析
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/swot/stuAnalysisDetailsBar")
	public void stuAnalysisDetails(Long gradeId, Long subjectId, String gradeName, String subjectName, Model model)
			throws UnsupportedEncodingException {
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		model.addAttribute("subjectId", subjectId);
		model.addAttribute("gradeName", URLDecoder.decode(gradeName, "UTF-8"));
		model.addAttribute("subjectName", URLDecoder.decode(subjectName, "UTF-8"));
		addStartFinishDateToModel(model);
	}
	
	/*---------------------------班级作业勤奋报告----------------------*/
	/**
	 * 班级作业勤奋报告
	 * @param model
	 */
	@RequestMapping("/diligent/teaDiligent")
	public void teaDiligent(Model model) {
		Date startDate = DiagHelp.getSemesterStarDate();
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		model.addAttribute("startDate", DateUtils.formatDate(startDate));
		model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
	}
	
	/** 班级作业勤奋报告 data
	 * @param schoolStageId
	 * @param page
	 * @return
	 */
	@RequestMapping("/diligent/gradeStuWorkDiligent")
	@ResponseBody
	public JsonResult findGradeStuWorkDiligent(Long schoolStageId, Long schoolId, Page page) {
		List<GradeStatsDto> list = this.schoolStatsService.findSchoolDiligent(UserContext.user.getCurrentSchoolId());
		JsonResult json = new JsonResult();
		json.addDatas("list", list);
		return json;
	}
	
	/** 作业考勤报告 -详情页  年级下所有班级作业情况
	 * @param gradeId
	 * @param gradeName
	 * @param model
	 */
	@RequestMapping("/diligent/teaDiligentDetails")
	public void teaDiligentDetails(Long gradeId, Model model) {
		model.addAttribute("gradeId", gradeId);
		GradeRemote gradeRemote = GradeContext.getGrade(gradeId);
		if (gradeRemote != null) {
			model.addAttribute("gradeName", gradeRemote.getGradeName());
		}
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		addStartFinishDateToModel(model);
	}
	
	/**
	 * 教师作业批改统计
	 * @param model
	 */
	@RequestMapping("/correct/correctStat")
	public void correctStat(Model model) {
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
	}

	/**
	 * 教师题库贡献
	 * @param model
	 */
	@RequestMapping("/questat/contribute")
	public void contribute(Model model) {

	}
	
	/**
	 * 描述: 教务返回选修班级和行政班级
	 * @author  DuanYanming
	 * @since   v1.0.0 
	 * @param queryClazz
	 * @return  JsonResult
	 */
	@RequestMapping("/data/getClasses")
	@ResponseBody
	public JsonResult getClasses(ClazzQuery query) {
		User user = UserContext.user.get();
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		JsonResult json = new JsonResult();
		json.addDatas("classes", clazzList);
		return json;
	}
	
	
	/** 添加 开学期间 start finish 时间
	 * @param model
	 */
	private void addStartFinishDateToModel(Model model) {
		Date startDate = DiagHelp.getSemesterStarDate();
		model.addAttribute("startDate", DateUtils.formatDate(startDate));
		model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
	}

}
