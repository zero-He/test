package cn.strong.leke.diag.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.pinyin.Pinyin;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.StuDiligentDtlForm;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.model.UserStatsDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.util.DiagHelp;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Controller
@RequestMapping("/auth/classTeacher")
public class ClassTeacherStatController {

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private SchoolStatsService schoolStatsService;
	
//------------------------------------------学生成绩统计-------------------------
	/**
	 * 学生成绩统计
	 * @param model
	 */
	@RequestMapping("/achievement/achievement")
	public void achievement(Model model) {
		model.addAttribute("clazzList", getClazz());
		addStartFinishDateToModel(model);
	}
	
	/** 学生成绩统计 （学科视角）- data
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/achievement/findSubjectAchievement")
	public JsonResult findSubjectAchievement(Long gradeId,Long classId) {
		JsonResult jsonResult = new JsonResult();
		//从mongodb中获取数据 
		List<SubjStatsDto> dataList = schoolStatsService.findClassSubjScore(gradeId,classId);
		jsonResult.addDatas("dataList", dataList);
		jsonResult.addDatas("classId", classId);
		return jsonResult;
	}
	
	/** 学生成绩统计(学生视角) - 按照班级和学科查询学生
	 * @param subjectId
	 * @param classId
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/achievement/achievementDetails")
	public void achievementDetail(Long subjectId, Long classId, String subjectName, String className, Model model)
			throws UnsupportedEncodingException {
		model.addAttribute("subjectId", subjectId);
		model.addAttribute("classId", classId);
		model.addAttribute("subjectName", URLDecoder.decode(subjectName, "UTF-8"));
		model.addAttribute("className", URLDecoder.decode(className, "UTF-8"));
		addStartFinishDateToModel(model);
	}
	
	/**学生成绩统计(学生视角) - 按照班级和学科查询学生 data
	 * @param classId
	 * @param subjectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/achievement/findStudentAchievement")
	public JsonResult findStudentAchievement(Long classId,Long subjectId) {
		JsonResult jsonResult = new JsonResult();
		//从mongodb中获取数据 
		List<UserStatsDto>  dataList = schoolStatsService.findClassUserScore(classId, subjectId);
		if (dataList != null && dataList.size() > 0) {
			dataList.sort((a, b) -> Pinyin.toPinyinAbbr(a.getUserName()).compareTo(Pinyin.toPinyinAbbr(b.getUserName())));
		}
		jsonResult.addDatas("dataList", dataList);
		return jsonResult;
	}
	
	/**学生成绩统计 学生课程成绩图形分析
	 * @param subjectId
	 * @param studentId
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/achievement/achievementStuSubjectDetails")
	public void achievementStuSubjectDetail(Long classId, Long subjectId, Long studentId, String className, Model model)
			throws UnsupportedEncodingException {
		model.addAttribute("classId", classId);
		model.addAttribute("className", URLDecoder.decode(className,"UTF-8"));
		model.addAttribute("subjectId", subjectId);
		SubjectRemote subjectRemote = SubjectContext.getSubject(subjectId);
		if (subjectRemote != null) {
			model.addAttribute("subjectName", subjectRemote.getSubjectName());
		}
		model.addAttribute("studentId", studentId);
		UserBase userBase = UserBaseContext.getUserBaseByUserId(studentId);
		if(userBase != null){
			model.addAttribute("studentName", userBase.getUserName());
		}
		addStartFinishDateToModel(model);
	}
	//------------------------------------------学科优劣分析-------------------------
	/**
	 * 学科优劣分析（班级）
	 * @param model
	 */
	@RequestMapping("/swot/clsAnalysis")
	public void clsAnalysis(Model model) {
		model.addAttribute("clazzList", getClazz());
	}
	
	/** 学科优劣分析 page 查看某一个学生的各学科的优劣分析
	 * @param stuId
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/swot/stuSubjectAnalysis")
	public void stuAnalysisBar(Long stuId, String className,Model model) throws UnsupportedEncodingException{
		UserBase userBase = UserBaseContext.getUserBaseByUserId(stuId);
		if (userBase != null) {
			model.addAttribute("studentId", userBase.getUserId());
			model.addAttribute("studentName", userBase.getUserName());
		}
		model.addAttribute("className", URLDecoder.decode(className, "UTF-8"));
		addStartFinishDateToModel(model);
	}

	/**
	 * 学科优劣分析（学生）
	 * @param model
	 */
	@RequestMapping("/swot/stuAnalysis")
	public void stuAnalysis(Model model) {
		User user = UserContext.user.get();
		ClazzQuery query = new ClazzQuery();
		query.setType(Clazz.CLAZZ_ORGAN);
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		model.addAttribute("clazzList", clazzList);
		addStartFinishDateToModel(model);
		
	}

	/**
	 * 学科优劣分析（学生）数据
	 * @param classType 班级类型
	 * @param classId 班级ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/swot/stuAnalysisData")
	public JsonResult stuAnalysisData(Long classId) {
		JsonResult result = new JsonResult();
		 List<Long> studentIdList = klassRemoteService.findStudentIdsByClassId(classId);
		List<UserBase>  userBaseList = UserBaseContext.findUserBaseByUserId(studentIdList.toArray(new Long[0]));
		result.addDatas("dataList", userBaseList);
		return result;
	}

	//------------------------------------------作业勤奋报告-------------------------
	/**
	 * 作业勤奋报告
	 * @param model
	 */
	@RequestMapping("/diligent/teaDiligent")
	public void teaDiligent(Model model) {
		model.addAttribute("clazzList", getClazz());
		Date startDate = DiagHelp.getSemesterStarDate();
		model.addAttribute("startDate", DateUtils.formatDate(startDate));
		model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
	}

	/**  获取班级下 所有学生作业统计信息
	 * @param classId
	 * @param studentName
	 * @param start
	 * @param finishDate
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/diligent/classStuWorkDiligent")
	public JsonResult findClassStuHomeworks(Long classId) {
		JsonResult jsonResult = new JsonResult();
		List<UserStatsDto> dataList = schoolStatsService.findClassDillgent(classId);
		jsonResult.addDatas("dataList", dataList);
		return jsonResult;
	}

	/**
	 * 学生详细作业提交数据。
	 * @param stuDiligentDtlForm 表单数据
	 * @param model
	 */
	@RequestMapping("/diligent/stuDiligentDtl")
	public void stuDiligentDtl(StuDiligentDtlForm stuDiligentDtlForm, Model model) throws Exception {
		if (StringUtils.isNotBlank(stuDiligentDtlForm.getStudentName())) {
			stuDiligentDtlForm.setStudentName(URLDecoder.decode(stuDiligentDtlForm.getStudentName(), "UTF-8"));
		}
		if (StringUtils.isNotBlank(stuDiligentDtlForm.getSubjectName())) {
			stuDiligentDtlForm.setSubjectName(URLDecoder.decode(stuDiligentDtlForm.getSubjectName(), "UTF-8"));
		}
		model.addAttribute("stuDiligentDtlForm", stuDiligentDtlForm);
	}

	/**
	 * 获取班主任班级
	 * @return
	 */
	private List<Clazz> getClazz() {
		User user = UserContext.user.get();
		ClazzQuery query = new ClazzQuery();
		query.setType(Clazz.CLAZZ_ORGAN);
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		return this.klassRemoteService.findClazzByQuery(query);
	}

	/**
	 * 描述: 班主任返回选修班级和行政班级
	 * @author  DuanYanming
	 * @since   v1.0.0 
	 * @param queryClazz
	 * @return  JsonResult
	 */
	@RequestMapping("/achievement/getClasses")
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
