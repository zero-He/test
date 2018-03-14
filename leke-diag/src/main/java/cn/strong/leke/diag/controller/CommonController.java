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
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.base.UserBaseHelper;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.diag.util.DiagHelp;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 图表数据获取。
 * @author  andy
 * @created 2014年7月22日 上午10:17:11
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/common")
public class CommonController {

	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private IUserRemoteService userRemoteService;

	/**
	 * 根据年级标识，返回班级列表数据
	 * @param gradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/classList")
	public JsonResult classList(Long gradeId) {
		User user = UserContext.user.get();
		ClazzQuery query = new ClazzQuery();
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		query.setGradeId(gradeId);
		List<Clazz> classList = this.klassRemoteService.findClazzByQuery(query);
		JsonResult result = new JsonResult();
		result.addDatas("classList", classList);
		return result;
	}

	/**
	 * 根据学笠标识，返回老师列表数据
	 * @param subjectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/teacherList")
	public JsonResult teacherList(Long subjectId) {
		JsonResult result = new JsonResult();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		List<UserRemote> teacherList = this.userRemoteService.findTeacherListBySchoolIdAndSubjectId(schoolId, subjectId);
		result.addDatas("teacherList", teacherList);
		return result;
	}
	
	@RequestMapping("/test")
	public String goTest(){
		
		return "test";
	}
	
	/**
	 * 描述：查询单个学生，某一课程某一学科的作业提交状态
	 * @param subjectId
	 * @param classId
	 * @param className
	 * @param subjectName
	 * @param stuId
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/stuHomeworkInfo")
	public void stuHomeworkInfo(Long subjectId, Long classId, String className, String subjectName,Long stuId, Model model) throws UnsupportedEncodingException {
		
		User user = UserContext.user.get();
		if (stuId != null) {
			user = UserBaseHelper.toUser(UserBaseContext.getUserBaseByUserId(stuId));
		}
		model.addAttribute("student", user);
		model.addAttribute("subjectId", subjectId);
		if (StringUtils.isNotEmpty(className)){
			model.addAttribute("className", URLDecoder.decode(className,"UTF-8"));
		}
		if (StringUtils.isNotEmpty(subjectName)){
			model.addAttribute("subjectName", URLDecoder.decode(subjectName,"UTF-8"));
		}
		model.addAttribute("classId", classId);
		Date nowDate = new Date();
		Date startDate = DiagHelp.getSemesterStarDate();
		model.addAttribute("startDate", DateUtils.formatDate(startDate));
		model.addAttribute("finishDate", DateUtils.formatDate(nowDate));
	}
	
}
