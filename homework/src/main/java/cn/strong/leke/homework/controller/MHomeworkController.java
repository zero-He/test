package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.TeacherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.HomeworkDTO;
import cn.strong.leke.homework.model.mobile.MPersonWorkCount;
import cn.strong.leke.homework.model.mobile.MPersonWorkDTO;
import cn.strong.leke.homework.model.mobile.MPersonWorkQuery;
import cn.strong.leke.homework.model.query.ClassHomeworkQuery;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Controller
@RequestMapping("/auth/m/*")
public class MHomeworkController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	/**
	 * 老师：作业管家
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "teacher/homework/manager", method = RequestMethod.GET)
	public String teacherManager(Model model) {
		model.addAttribute("_ts", System.currentTimeMillis());
		return "/auth/m/homework/school/teacher-worklist";
	}

	/**
	 * 老师：作业管家数据查询
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "teacher/homework/worklist", method = RequestMethod.POST)
	public Object teacherWorklist(MPersonWorkQuery query) {
		query.setTeacherId(TeacherContext.teacher.get().getId());
		JsonResult json = new JsonResult();
		if (query.getCounter()) {
			MPersonWorkCount count = this.homeworkService.queryMobileTeacherWorkCount(query);
			json.addDatas("count", count);
		}
		List<MPersonWorkDTO> list = this.homeworkService.queryMobileTeacherWorkList(query);
		json.addDatas("list", list);
		return json;
	}

	// --------------------------------------------------------------------------------------

	/**
	 * 班主任：作业列表页（jquery）
	 * @return
	 */
	@RequestMapping(value = "classTeacher/homework/manager", method = RequestMethod.GET)
	public String classTeacherManager() {
		return "/auth/m/homework/school/classTeacher-worklist";
	}

	/**
	 * 班主任：作业列表页数据（jquery）
	 * @param query
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("classTeacher/homework/worklist")
	public JsonResult classTeacherWorklist(ClassHomeworkQuery query, Page page) {
		User user = UserContext.user.get();
		JsonResult result = new JsonResult();
		query.setClassType(Clazz.CLAZZ_ORGAN);
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		if (this.fillClassId(query, user)) {
			return new JsonResult().addDatas("page", page);
		}
		List<HomeworkDTO> list = homeworkService.findProvostHomeworkList(query, page);
		page.setDataList(list);
		result.addDatas("page", page);
		return result;
	}

	/**
	 * 过滤条件列表。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "classTeacher/homework/filters", method = RequestMethod.POST)
	public JsonResult classTeacherFilters() {
		JsonResult result = new JsonResult();
		//获取班级
		User user = UserContext.user.get();
		ClazzQuery clazzQuery = this.buildClassQuery(user);
		clazzQuery.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> classList = this.klassRemoteService.findClazzByQuery(clazzQuery);
		//获取学科
		List<SubjectRemote> subjectList = UserContext.user.findSubjectsOfCurrentSchool();
		result.addDatas("classList", classList);
		result.addDatas("subjectList", subjectList);
		return result;
	}

	// ---------------------------------------------------------------------------------------

	/**
	 * 教务：作业列表页（jquery）
	 * @return
	 */
	@RequestMapping(value = "provost/homework/manager", method = RequestMethod.GET)
	public String provostManager() {
		return "/auth/m/homework/school/provost-worklist";
	}

	/**
	 * 教务：作业列表页数据（jquery）
	 * @param query
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "provost/homework/worklist", method = RequestMethod.POST)
	public JsonResult provostWorkList(ClassHomeworkQuery query, Page page) {
		User user = UserContext.user.get();
		query.setSchoolId(UserContext.user.getCurrentSchoolId());
		if (this.fillClassId(query, user)) {
			return new JsonResult().addDatas("page", page);
		}
		List<HomeworkDTO> list = homeworkService.findProvostHomeworkList(query, page);
		page.setDataList(list);
		return new JsonResult().addDatas("page", page);
	}

	/**
	 * 过滤条件列表。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "provost/homework/filters", method = RequestMethod.POST)
	public JsonResult provostFilters() {
		JsonResult result = new JsonResult();
		//获取年级
		List<GradeRemote> gradeList = UserContext.user.findGradesOfCurrentSchool();
		//获取学科
		List<SubjectRemote> subjectList = UserContext.user.findSubjectsOfCurrentSchool();
		result.addDatas("gradeList", gradeList);
		result.addDatas("subjectList", subjectList);
		return result;
	}

	// ---------------------------------------------------------------------------------------

	/**
	 * 根据年级设置班级Id集合，如果班级ID没有找到，返回true，不用执行后面的查询
	 * @param query
	 * @return
	 */
	private boolean fillClassId(ClassHomeworkQuery query, User user) {
		if (CollectionUtils.isNotEmpty(query.getClassId())) {
			// 班级ID不为空时，直接直接查询
			return false;
		}
		Long roleId = user.getCurrentRole().getId();
		if (RoleCst.PROVOST.equals(roleId) && query.getGradeId() == null) {
			// 教务没有年级时，不需要填充班级ID，直接使用学校ID
			return false;
		}
		ClazzQuery clazzQuery = this.buildClassQuery(user);
		clazzQuery.setGradeId(query.getGradeId());
		clazzQuery.setType(query.getClassType());
		List<Clazz> classes = this.klassRemoteService.findClazzByQuery(clazzQuery);
		query.setClassId(classes.stream().map(Clazz::getClassId).collect(toList()));
		return classes.isEmpty();
	}

	private ClazzQuery buildClassQuery(User user) {
		ClazzQuery query = new ClazzQuery();
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		return query;
	}
}
