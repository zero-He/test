package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.repository.IconUtils;
import cn.strong.leke.context.user.TeacherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.AssignResourceViewModel;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDTO;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkQuery;
import cn.strong.leke.homework.model.LayerClazz;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.user.Teacher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.user.ClazzGroupRemote;
import cn.strong.leke.remote.service.lesson.IStudentGroupRemoteService;


@Controller
@RequestMapping("/auth/hd/**")
public class HDHomeworkController {
	
	@Resource
	private HomeworkService homeworkService;
	@Resource
	private IStudentGroupRemoteService studentGroupRemoteService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkMainService homeworkMainService;
	
	/**
	 * @param model
	 * @param resIds
	 * @param resType 3:试卷  1：课件  2：微课
	 * @return
	 */
	@RequestMapping("teacher/assignHomework")
	public String assignHomework(Model model,Long[] resIds,Integer resType) {
		User user = UserContext.user.get();
		List<ClazzGroupRemote> clazzList = studentGroupRemoteService.findClazzGroupByTeacherId(user.getId());
		List<LayerClazz> classes = clazzList.stream().map(v -> {
			LayerClazz clazz = new LayerClazz();
			clazz.setClassId(v.getClassId());
			clazz.setClassName(v.getClassName());
			clazz.setClassType(v.getType());
			if (CollectionUtils.isNotEmpty(v.getStudentGroupList())) {
				clazz.setGroups(v.getStudentGroupList().stream().map(g -> {
					LayerClazz.Group group = new LayerClazz.Group();
					group.setGroupId(g.getGroupId());
					group.setGroupName(g.getGroupName());
					return group;
				}).collect(toList()));
			}
			return clazz;
		}).sorted((a, b) -> {
			int result = Integer.compare(a.getClassType(), b.getClassType());
			if (result == 0) {
				result = Long.compare(b.getClassId(), a.getClassId());
			}
			return result;
		}).collect(toList());
		model.addAttribute("classes", classes);
		model.addAttribute("resourceList", findResourcesJson(resIds, resType));
		return "auth/hd/homework/assignHomework";
	}
	
	private String findResourcesJson(Long[] resIds,Integer resType){
		List<AssignResourceViewModel> resources = new ArrayList<AssignResourceViewModel>();
		if(resIds == null && resType == null) {
			return null;
		}
		List<Long> resourceIds = Arrays.asList(resIds);
		if(HomeworkCst.HOMEWORK_RES_PAPER == resType){
			resourceIds.forEach(resId->{
				PaperDTO paper = PaperContext.getPaperDTO(resId);
				resources.add(builidResource(resId,paper.getTitle(),resType, IconUtils.getIcon((long)resType, resId)));
			});
		}else if(HomeworkCst.HOMEWORK_RES_COURSEWARE == resType){
			resourceIds.forEach(resId->{
				CoursewareDTO courseware  = CoursewareContext.getCourseware(resId);
				resources.add(builidResource(resId,courseware.getName(),resType, IconUtils.getIcon((long)resType, resId)));
			});
		}else if(HomeworkCst.HOMEWORK_RES_MICROCOURSE == resType){
			resourceIds.forEach(resId->{
				MicrocourseDTO micro  = MicrocourseContext.getMicrocourse(resId);
				resources.add(builidResource(resId,micro.getMicrocourseName(),resType, IconUtils.getIcon((long)resType, resId)));
			});
		}
		String jsonStr = JsonUtils.toJSON(resources);
		return jsonStr;
	}
	
	private AssignResourceViewModel builidResource(Long id,String name,Integer type,String suffix) {
		AssignResourceViewModel viewModel = new AssignResourceViewModel ();
		viewModel.setResId(id);
		viewModel.setResName(name);
		viewModel.setResType(type);
		viewModel.setSuffix(suffix);
		return viewModel;
	}
	
	@RequestMapping("teacher/homework/homeworkList")
	public String homeworkList(Integer ord,HomeworkQuery query,Model model) throws UnsupportedEncodingException {
		model.addAttribute("ord", ord);
		model.addAttribute("classId", query.getClassId());
		if (StringUtils.hasText(query.getClassName())) {
			model.addAttribute("className", URLDecoder.decode(query.getClassName(), "UTF-8"));
		}
		boolean isOfflineSheet = false;
		Long schoolId = UserContext.user.getCurrentSchoolId();
		if (schoolId != null) {
			School school = SchoolContext.getSchoolBySchoolId(schoolId);
			if (school != null && school.getIsOfflineSheet() != null) {
				isOfflineSheet = school.getIsOfflineSheet();
			}
		}
		model.addAttribute("isOfflineSheet", isOfflineSheet);
		//查找复批作业数
		query.setTeacherId(UserContext.user.getUserId());
		Integer reCorrectNum = homeworkService.getReCorrectNum(query);
		if (reCorrectNum == null) {
			model.addAttribute("reCorrectNum", 0);
		} else if (reCorrectNum > 99) {
			model.addAttribute("reCorrectNum", "99+");
		} else {
			model.addAttribute("reCorrectNum", reCorrectNum);
		}
		Integer unfinishedNum = homeworkService.getUnfinishedNum(UserContext.user.getUserId());
		if (unfinishedNum == null) {
			model.addAttribute("unfinishedNum", 0);
		} else if (unfinishedNum > 99) {
			model.addAttribute("unfinishedNum", "99+");
		} else {
			model.addAttribute("unfinishedNum", unfinishedNum);
		}
		return "/auth/hd/homework/teacher/homeworkList";
	}

	@RequestMapping("teacher/homework/loadHomeworkList")
	@ResponseBody
	public JsonResult loadHomeworkList(HomeworkQuery homework, Page page) {
		if (homework != null && homework.getHomeworkName() != null) {
			homework.setHomeworkName(StringUtils.trimWhitespace(homework.getHomeworkName()));
		}
		Teacher teacher = TeacherContext.teacher.get();
		homework.setTeacherId(teacher.getId());
		homework.setSchoolId(teacher.getCurrentSchool().getId());
		List<HomeworkDTO> homeworkList = null;
		if(homework.getHomeworkFinishFlag()!=null&&homework.getHomeworkFinishFlag().equals("reCorrect")){
			homeworkList = homeworkService.queryReCorrectHomeworkDTOList(homework, page);
		}else{
			homeworkList = homeworkService.findHomeworkList(homework, page);
		}
		page.setDataList(homeworkList);

		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 描述:作业作废
	 * @author  DuanYanming
	 * @created 2015年1月14日 上午8:57:39
	 * @since   v1.0.0 
	 * @param homeworkId
	 * @return  void
	 */
	@RequestMapping("teacher/homework/homeworkInvalid")
	@ResponseBody
	public JsonResult homeworkInvalid(Long homeworkId) {
		Long teacherId = UserContext.user.getUserId();
		homeworkService.updateHomeworkInvalid(homeworkId, teacherId);
		return new JsonResult();
	}
	
	/**
	 * 操作自行校对
	 * */
	@RequestMapping("teacher/homework/selfCheck")
	@ResponseBody
	public JsonResult selfCheck(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		Long teacherId = UserContext.user.getUserId();
		Integer result = homeworkService.saveSelfCheck(homeworkId, teacherId);
		if (result < 0) {
			jsonResult.setErr("操作失败");
		}
		return jsonResult;
	}
	
	/**
	 * 操作公布答案
	 * */
	@RequestMapping("teacher/homework/openAnswer")
	@ResponseBody
	public JsonResult openAnswer(Long homeworkId) {
		JsonResult jsonResult = new JsonResult();
		Integer result = homeworkService.saveOpenAnswer(homeworkId, UserContext.user.getUserId());
		if (result < 0) {
			jsonResult.setErr("操作失败");
		}
		return jsonResult;
	}
	
	@RequestMapping("teacher/homework/cuijiao")
	@ResponseBody
	public JsonResult cuijiao(Long homeworkId) {
		JsonResult json = new JsonResult();
		Homework homework = this.homeworkService.getHomeworkById(homeworkId);
		List<HomeworkDtl> homeworkDtls = this.homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId);
		json.addDatas("past", System.currentTimeMillis() - homework.getCloseTime().getTime() > 0);
		if(CollectionUtils.isEmpty(homeworkDtls)){
			json.setErr("该作业没有学生~");
		}else{
			homeworkDtls = homeworkDtls.stream().filter(v->v.getSubmitTime() == null).collect(Collectors.toList());
			if(CollectionUtils.isEmpty(homeworkDtls)){
				json.setErr("所有学生已提交~");
			}else{
				json.addDatas("stuCount", homeworkDtls.size());
				json.addDatas("time", HomeworkUtils.fmtDiffTime(homework.getCloseTime().getTime()));
			}
		}
		return json;
	}
	
	@RequestMapping("teacher/homework/doCuijiao")
	@ResponseBody
	public JsonResult doCuijiao(Long homeworkId) {
		this.homeworkMainService.cuijiaoHomework(homeworkId);
		return new JsonResult();
	}
	
	/*老师：已作废作业列表*/
	@RequestMapping(value = { "teacher/homework/dumpedHomeworkList" })
	public String dumpedHomeworkList() {
		return "/auth/hd/homework/teacher/dumpedHomeworkList";
	}
	
	@RequestMapping("teacher/homework/loadDumpedHomeworkList")
	@ResponseBody
	public JsonResult loadDumpedHomeworkList(HomeworkQuery query, Page page) {
		Teacher teacher = TeacherContext.teacher.get();
		query.setTeacherId(teacher.getId());
		query.setSchoolId(teacher.getCurrentSchool().getId());
		List<HomeworkDTO> homeworkList = homeworkService.findDumpedHomeworkList(query, page);
		page.setDataList(homeworkList);
		
		JsonResult json = new JsonResult();
		json.addDatas("page", page);
		return json;
	}
	/**
	 * 恢复作废作业
	 * @param id
	 * @return
	 */
	@RequestMapping("teacher/homework/recover")
	@ResponseBody
	public JsonResult recover(Long id) {
		JsonResult result = new JsonResult();
		Boolean b = this.homeworkService.recoverHomework(Arrays.asList(id), UserContext.user.getUserId());
		if (b) {
			result.setMessage("该试卷不能恢复！！");
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	/**
	 * 永久删除作业
	 * @param id
	 * @return
	 */
	@RequestMapping("teacher/homework/del")
	@ResponseBody
	public JsonResult del(Long id) {
		this.homeworkService.delHomework(Arrays.asList(id), UserContext.user.getUserId());
		return new JsonResult();
	}
}
