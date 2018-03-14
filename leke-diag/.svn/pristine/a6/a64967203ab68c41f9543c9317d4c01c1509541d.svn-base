package cn.strong.leke.diag.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.TeacherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.pinyin.Pinyin;
import cn.strong.leke.diag.model.CourseSubjectHomeworkDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsQueryDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsDto;
import cn.strong.leke.diag.model.CourseSubjectHomeworkStatisticsStudentsQueryDto;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.diag.util.DiagHelp;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.Subject;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Controller
@RequestMapping("/auth/teacher")
public class TeacherStatController {

	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private HomeworkService homeworkService;
	
	/*----------------------------------学生个人成长曲线----------------------------------*/
	@RequestMapping("/achievement/studentList")
	public void studentList(Model model) {
		// 获取老师的行政班级
		User user = UserContext.user.get();
		ClazzQuery query = new ClazzQuery();
		query.setRoleId(RoleCst.TEACHER);
		query.setUserId(user.getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		query.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> classes = klassRemoteService.findClazzByQuery(query);
		model.addAttribute("classes1", classes);
		// 获取老师的所有补课班级
		query.setType(Clazz.CLAZZ_VIRTUAL);
		classes = klassRemoteService.findClazzByQuery(query);
		model.addAttribute("classes2", classes);
	}
	
	@RequestMapping("/achievement/findStudentList")
	@ResponseBody
	public JsonResult findStudentList(Integer type,Long classId,String userName){
		JsonResult jsonResult = new JsonResult();
		List<Long> userIds = klassRemoteService.findStudentIdsByClassId(classId);
		if(userIds != null && userIds.size() > 0){
			List<UserBase> users = UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0]));
			if(users != null && users.size() > 0){
				if(!StringUtils.isEmpty(userName)){
					users = users.stream().filter(u->u.getUserName().contains(userName)).collect(Collectors.toList());
				}
				//排序操作
				users.sort((a,b)->Pinyin.toPinyinAbbr(a.getUserName()).compareTo(Pinyin.toPinyinAbbr(b.getUserName())));
				jsonResult.addDatas("dataList", users);
			}
		}
		return jsonResult;
	}
	
	/*----------------------------------学生成绩统计----------------------------------*/
	/**
	 * 学生成绩统计
	 * @param model
	 */
	@RequestMapping("/achievement/achievement")
	public void achievement(Model model) {
		this.addSubjectClassToRequest(model);
		addStartFinishDateToModel(model);
	}
	
	/**
	 * 学生成绩统计 data
	 * @param subjectId
	 * @param teacherId
	 * @param start
	 * @param finish
	 * @param page
	 * @return
	 */
	@RequestMapping("achievement/findTeacherAchievement")
	@ResponseBody
	public JsonResult findTeacherAchievement(CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto,Page page){
		JsonResult jsonResult = new JsonResult();
		queryDto.setTeacherId(TeacherContext.teacher.getUserId());
		queryDto.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<CourseSubjectHomeworkDto> dataList = homeworkService.findCourseSubject(queryDto, page);
		page.setDataList(dataList);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	/**
	 * 学生成绩统计 图形分析
	 * @param subjectId
	 * @param teacherId
	 * @param courseId
	 * @param model
	 */
	@RequestMapping("achievement/achievementDetails")
	public void achievementDetails(Long subjectId, Long courseId, String courseName, Model model) {
		model.addAttribute("subjectId", subjectId);
		SubjectRemote subjectRemote = SubjectContext.getSubject(subjectId);
		if (subjectRemote != null) {
			model.addAttribute("subjectName", subjectRemote.getSubjectName());
		}
		//teacherId 当前用户id
		model.addAttribute("teacherId", UserContext.user.getUserId());
		model.addAttribute("courseId", courseId);
		model.addAttribute("courseName", courseName);
		addStartFinishDateToModel(model);
	}

	/*----------------------------------学生优劣势学科分析----------------------------------*/
	/**
	 * 学生优劣势学科分析
	 * @param model
	 */
	@RequestMapping("/swot/subAnalysis")
	public void subAnalysis(Model model) {
		this.addSubjectClassToRequest(model);
	}

	/**
	 * 作业勤奋报告
	 * @param model
	 */
	@RequestMapping("/diligent/teaDiligent")
	public void teaDiligent(Model model) {
		List<Subject> subjectList = TeacherContext.teacher.getSubjects();
		model.addAttribute("subjectList", subjectList);
		addStartFinishDateToModel(model);
	}

	/**
	 * 学生详细作业提交数据。
	 * @param stuDiligentDtlForm 表单数据
	 * @param model
	 */
	@RequestMapping("/diligent/stuDiligentDtl")
	public void stuDiligentDtl(Long classId,Long subjectId,Model model){
		model.addAttribute("classId", classId);
		model.addAttribute("subjectId", subjectId);
		addStartFinishDateToModel(model);
	}

	private void addSubjectClassToRequest(Model model) {
		User user = UserContext.user.get();
		ClazzQuery query = new ClazzQuery();
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		
		query.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		query.setType(Clazz.CLAZZ_VIRTUAL);
		List<Clazz> virtualClazzList = this.klassRemoteService.findClazzByQuery(query);
		
		List<Subject> subjectList = TeacherContext.teacher.getSubjects();
		model.addAttribute("clazzList", clazzList);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("virtualClazzList", virtualClazzList);
	}
	


	@ResponseBody
	@RequestMapping("/diligent/findCourseSubjectInfo")
	public JsonResult findCourseSubjectInfo(CourseSubjectHomeworkStatisticsQueryDto queryDto, Page page) {
		JsonResult jsonResult = new JsonResult();
		UserContext user = UserContext.user;
		queryDto.setTeacherId(user.getUserId());
		queryDto.setSchoolId(user.getCurrentSchoolId());
		List<CourseSubjectHomeworkStatisticsDto> dataList = homeworkService.findCourseSubjectInfo(queryDto, page);
		page.setDataList(dataList);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping("diligent/findCourseSubjectStudents")
	public JsonResult findCourseSubjectStudents(CourseSubjectHomeworkStatisticsStudentsQueryDto queryDto, Page page) {
		JsonResult jsonResult = new JsonResult();
 		List<CourseSubjectHomeworkStatisticsStudentsDto> dataList =homeworkService.findCourseSubjectStudents(queryDto, page);
 		page.setDataList(dataList);
 		jsonResult.addDatas("page", page);
		return jsonResult;
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
