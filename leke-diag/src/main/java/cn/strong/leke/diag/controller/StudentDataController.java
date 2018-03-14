package cn.strong.leke.diag.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.base.UserBaseHelper;
import cn.strong.leke.context.user.StudentContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.diag.util.DiagHelp;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.RoleSchool;
import cn.strong.leke.model.user.Student;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SubjectGradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.base.IRoleSchoolRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 图表数据获取。
 * @author  andy
 * @created 2014年7月22日 上午10:17:11
 * @since   v1.0.0
 */
@Controller
@RequestMapping({"/auth/student/data","/auth/parent/data","/auth/teacher/data"})
public class StudentDataController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private SchoolStatsService schoolStatsService;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IRoleSchoolRemoteService roleSchoolRemoteService;
	

	
	/**
	 * 个人成长曲线列表
	 * @param model
	 */
	@RequestMapping("/selfStatisticalList")
	public String stuStatisticalList(Model model){
		UserContext user =  UserContext.user;
		Long roleId = user.getCurrentRoleId();
		if(roleId.equals(RoleCst.STUDENT)){
			model.addAttribute("studentName", UserContext.user.getUserName());
		}
		else if (roleId.equals(RoleCst.PARENT)){
			List<UserRemote> studentList = userRemoteService.findNowChildList(user.getUserId());
			model.addAttribute("studentList", studentList);
		}
		addStartFinishDateToModel(model);
		return "/auth/student/selfStatisticalList";
	}
	
	/** 个人成长曲线列表 list 数据
	 * @param startDate
	 * @param finishDate
	 * @param page
	 * @return
	 */
	@RequestMapping("/loadStuStatisticalData")
	@ResponseBody
	public JsonResult loadStuStatisticalData(Long studentId, Date startDate,Date finishDate)
	{
		JsonResult jsonResult = new JsonResult();
		//从mongodb 中获取数据 
		List<SubjStatsDto> dataList = schoolStatsService.findStudentSubjScore2(studentId);
		jsonResult.addDatas("dataList", dataList);
		return jsonResult;
	}
	
	/**
	 * 描述:学生个人成长曲线
	 * @author  DuanYanming
	 * @param model
	 * @return  String
	 */
	@RequestMapping("/line/stuStatistical")
	public String returnStudentJsp(Long studentId, Long subjectId,Model model) {
		model.addAttribute("studentId", studentId);
		UserBase userBase = UserBaseContext.getUserBaseByUserId(studentId);
		if(userBase != null){
			model.addAttribute("studentName", userBase.getUserName());
		}
		model.addAttribute("subjectId", subjectId);
		if(subjectId != null){
			SubjectRemote subjectRemote = SubjectContext.getSubject(subjectId);
			if(subjectRemote!=null){
				model.addAttribute("subjectName", subjectRemote.getSubjectName());
			}
		}
		
		addStartFinishDateToModel(model);
		//获取当前老师的所有学科
		UserContext user = UserContext.user;
		if(user.getCurrentRoleId().equals(RoleCst.TEACHER)){
			List<SubjectGradeRemote> teacherSubjects = userRemoteService.findSubjectGradeByTeacherId(user.getUserId(),user.getCurrentSchoolId());
			model.addAttribute("subjects", teacherSubjects);
		}
		return "auth/student/selfStatistical";
	}

	/**
	 * 描述:学科优劣势分析
	 * @author  DuanYanming
	 * @created 2014年7月31日 上午9:54:32
	 * @since   v1.0.0 
	 * @param model
	 * @return  String
	 * @throws ParseException 
	 */
	@RequestMapping("/bar/stuSubjectScore")
	public String returnStuSubjectScore(Model model) throws ParseException {
		UserContext user = UserContext.user;
		if (user.getCurrentRoleId().equals(RoleCst.STUDENT)) {
			addParamToModel(model);
		} else if (user.getCurrentRoleId().equals(RoleCst.PARENT)) {
			List<UserRemote> studentList = userRemoteService.findNowChildList(user.getUserId());
			model.addAttribute("studentList", studentList);
		}
		addStartFinishDateToModel(model);
		return "auth/student/stuSubjectScore";
	}

	/**
	 * 描述:学生考勤
	 * @author  DuanYanming
	 * @created 2014年10月30日 下午8:51:00
	 * @since   v1.0.0 
	 * @return  String
	 */
	@RequestMapping("/stuPie/attendance")
	public String attendance(Model model) {
		UserContext user = UserContext.user;
		if (user.getCurrentRoleId().equals(RoleCst.PARENT)) {
			List<UserRemote> studentList = userRemoteService.findNowChildList(user.getUserId());
			model.addAttribute("studentList", studentList);
		}
		model.addAttribute("studentId", user.getUserId());
		return "auth/student/attendance/stuAttendance";
	}

	private void addParamToModel(Model model) {
		if (UserContext.user.getCurrentRoleId().equals(RoleCst.STUDENT)) {
			Student student = StudentContext.student.get();
			model.addAttribute("student", student);
		}
	}
	
	/** 添加 开学期间 start finish 时间
	 * @param model
	 */
	private void addStartFinishDateToModel(Model model) {
		Date startDate = DiagHelp.getSemesterStarDate();
		model.addAttribute("startDate", DateUtils.formatDate(startDate));
		model.addAttribute("finishDate", DateUtils.formatDate(new Date()));
	}

	
	/**
	 * 描述: 学生返回选修班级和行政班级
	 * @author  DuanYanming
	 * @since   v1.0.0 
	 * @param queryClazz
	 * @return  JsonResult
	 */
	@RequestMapping("/clazz/getClasses")
	@ResponseBody
	public JsonResult getClasses(Integer type,Long studentId) {
		ClazzQuery query = new ClazzQuery();
		query.setType(type);
		User user = UserBaseHelper.toUser(UserBaseContext.getUserBaseByUserId(studentId));// UserBaseHelper.toUser(UserBaseContext.getUserBaseByUserId(studentId));
		query.setUserId(user.getId());
		query.setRoleId(RoleCst.STUDENT);
		//根据userId 查询 学校Id 
		List<RoleSchool> roleSchools = roleSchoolRemoteService.findRoleSchoolList(studentId, RoleCst.STUDENT);
		Long schoolId = roleSchools.get(0).getSchoolId();
		query.setSchoolId(schoolId);
		
		List<Clazz> classList = this.klassRemoteService.findClazzByQuery(query);
		JsonResult json = new JsonResult();
		json.addDatas("classes", classList);
		
		return json;
	}
	
	
	/**描述：作业勤奋报告-列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/myHomeworkList")
	public String myHomeworkList(Model model){
		UserContext user = UserContext.user;
		if (user.getCurrentRoleId().equals(RoleCst.STUDENT)) {
			model.addAttribute("studentId", user.getUserId());
		} else if (user.getCurrentRoleId().equals(RoleCst.PARENT)) {
			List<UserRemote> studentList = userRemoteService.findNowChildList(user.getUserId());
			model.addAttribute("studentList", studentList);
		}
		addStartFinishDateToModel(model);
		return "/auth/student/myHomeworkList";
	}
		
	/** 学生：作业勤奋报告 获取数据 异步数据
	 * @param queryDto
	 * @param page
	 * @return
	 */
	@RequestMapping("/findMyHomeworkList")
	@ResponseBody
	public JsonResult findMyHomeworkList(Long studentId){
		JsonResult jsonResult = new JsonResult();
		List<SubjStatsDto>  dataList = schoolStatsService.findStudentSubjDillgent(studentId);
		jsonResult.addDatas("dataList", dataList);
		return jsonResult;
	}

}
