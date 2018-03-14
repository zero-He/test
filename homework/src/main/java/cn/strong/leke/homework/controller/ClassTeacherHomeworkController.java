package cn.strong.leke.homework.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.QueryClazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

/**
 * 描述:班主任班级作业
 * @author  DuanYanming
 * @created 2014年10月17日 下午7:56:41
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/classTeacher/*")
public class ClassTeacherHomeworkController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	/**
	 * 班主任管辖班级作业列表
	 * @param queryClazz
	 * @param model
	 */
	@RequestMapping("/homework/homeworkList")
	public void homeworkList(QueryClazz queryClazz, Model model) {
		List<Clazz> clazzList = getClazz();
		model.addAttribute("clazzs", clazzList);
	}

	/**
	 * 根据条件查询班主任管辖班级
	 * @param queryClazz
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/homework/getClasses")
	public JsonResult getClasses(QueryClazz queryClazz) {
		List<Clazz> clazzList = getClazz();
		JsonResult json = new JsonResult();
		json.addDatas("classes", clazzList);
		return json;
	}
	
	/**
	 * 获取当前用户的关联班级
	 * @return
	 */
	private List<Clazz> getClazz(){
		User user = UserContext.user.get();
		ClazzQuery query = new ClazzQuery();
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		return clazzList;
	}
}
