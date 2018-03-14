package cn.strong.leke.diag.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 *
 *
 * @author  DuanYanming
 * @created 2014年8月4日 上午8:45:27
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/parent/data")
public class ParentsDataController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	/**
	 *	
	 * 描述:家长查看学生个人成长曲线
	 *
	 * @author  DuanYanming
	 * @param model
	 * @return
	 * @return  String
	 */
	@RequestMapping("/line/statistical")
	public String returnStudentJsp(Model model) {
		this.addParamToModel(model);
		return "auth/parent/selfStatistical";
	}

	/**
	 *	
	 * 描述:家长查看学生作业提交状态
	 *
	 * @author  DuanYanming
	 * @param model
	 * @return
	 * @return  String
	 */
	@RequestMapping("/pie/submitState")
	public String returnStuSubmitState(Model model) {
		this.addParamToModel(model);
		return "auth/parent/parSubmitState";
	}

	/**
	 * 描述:家长查看学生学科优劣势分析
	 * @author  DuanYanming
	 * @created 2014年7月31日 上午9:54:32
	 * @since   v1.0.0 
	 * @param model
	 * @return
	 * @return  String
	 */
/*	@RequestMapping("/bar/subjectScore")
	public String returnStuSubjectScore(Model model) {
		
		List<Student> studentList = ParentContext.parent.findChildren();
		if (studentList.isEmpty()) {
			throw new ValidateException("您已经向子女发出绑定申请，待子女确认。");
		}
		Long studentId = studentList.get(0).getId();
		
		ClazzQuery query = new ClazzQuery();
		query.setUserId(studentId);
		query.setType(Clazz.CLAZZ_VIRTUAL);
		query.setRoleId(RoleCst.STUDENT);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		
		model.addAttribute("clazzList", clazzList);
		model.addAttribute("studentList", studentList);
		model.addAttribute("studentId", studentId);
		return "auth/parent/parSubjectScore";
	}*/
	
	private void addParamToModel(Model model) {
		Long userId = UserContext.user.getUserId();
		List<UserRemote> studentList = userRemoteService.findNowChildList(userId);
		if (studentList.isEmpty()) {
			throw new ValidateException("您已经向子女发出绑定申请，待子女确认。");
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("studentId", studentList.get(0).getId());
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
	public JsonResult getClasses(ClazzQuery query) {
		JsonResult json = new JsonResult();
		query.setRoleId(RoleCst.STUDENT);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		json.addDatas("classes", clazzList);
		return json;
	}
}
