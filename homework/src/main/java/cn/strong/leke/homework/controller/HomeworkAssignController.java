package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.pinyin.Pinyin;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.LayerAssign;
import cn.strong.leke.homework.model.LayerClazz;
import cn.strong.leke.homework.model.LayerAssign.AssignResource;
import cn.strong.leke.homework.service.HomeworkAssignService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.lesson.model.GroupStu;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.user.ClazzGroupRemote;
import cn.strong.leke.remote.service.lesson.IStudentGroupRemoteService;

@Controller
@RequestMapping("/auth/teacher/assign/*")
public class HomeworkAssignController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkAssignService homeworkAssignService;
	@Resource
	private IStudentGroupRemoteService studentGroupRemoteService;

	/**
	 * @param
	 * @param model
	 */
	@RequestMapping("index")
	public void index(@RequestParam(value = "resIds", required = false) List<Long> resIds,
			@RequestParam(value = "resType", required = false) Integer resType, Model model) {
		List<AssignResource> resources = Collections.emptyList();
		if (CollectionUtils.isNotEmpty(resIds)) {
			if (HomeworkCst.HOMEWORK_RES_COURSEWARE == resType) {
				resources = resIds.stream().map(CoursewareContext::getCourseware).map(courseware -> {
					AssignResource resource = new AssignResource();
					resource.setResId(courseware.getCoursewareId());
					resource.setResName(courseware.getName());
					resource.setResType(HomeworkCst.HOMEWORK_RES_COURSEWARE);
					return resource;
				}).collect(toList());
			} else if (HomeworkCst.HOMEWORK_RES_MICROCOURSE == resType) {
				resources = resIds.stream().map(MicrocourseContext::getMicrocourse).map(courseware -> {
					AssignResource resource = new AssignResource();
					resource.setResId(courseware.getMicrocourseId());
					resource.setResName(courseware.getMicrocourseName());
					resource.setResType(HomeworkCst.HOMEWORK_RES_MICROCOURSE);
					return resource;
				}).collect(toList());
			} else if (HomeworkCst.HOMEWORK_RES_PAPER == resType) {
				resources = resIds.stream().map(PaperContext::getPaperDTO).map(paper -> {
					AssignResource resource = new AssignResource();
					resource.setResId(paper.getPaperId());
					resource.setResName(paper.getTitle());
					resource.setResType(HomeworkCst.HOMEWORK_RES_PAPER);
					return resource;
				}).collect(toList());
			}
		}
		model.addAttribute("resJson", JsonUtils.toJSON(resources));

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
		model.addAttribute("classes", JsonUtils.toJSON(classes));
	}

	/**
	 * 布置作业提交
	 * @param dataJson
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveAssign")
	public JsonResult saveAssign(String dataJson) {
		User user = UserContext.user.get();
		LayerAssign assign = JsonUtils.fromJSON(dataJson, LayerAssign.class);
		assign.setTeacherId(user.getId());
		assign.setTeacherName(user.getUserName());
		assign.setSchoolId(user.getCurrentSchool().getId());
		assign.setIsExam(false);
		List<Long> homeworkIds = this.homeworkAssignService.saveAssign(assign);
		Long homeworkId = homeworkIds.size() == 1 ? homeworkIds.get(0) : null;
		return new JsonResult().addDatas("homeworkId", homeworkId);
	}

	/**
	 * 布置作业成功跳转页
	 * @param homeworkId
	 * @param model
	 */
	@RequestMapping("success")
	public void success(Long homeworkId, Model model) {
		if (homeworkId != null) {
			Homework homework = this.homeworkService.getHomeworkById(homeworkId);
			model.addAttribute("homeworkId", homeworkId);
			model.addAttribute("paperId", homework.getPaperId());
			model.addAttribute("resType", homework.getResType());
		}
	}

	@ResponseBody
	@RequestMapping("classes")
	public JsonResult classes() {
		User user = UserContext.user.get();
		List<ClazzGroupRemote> clazzList = studentGroupRemoteService.findClazzGroupByTeacherId(user.getId());
		List<LayerClazz> list = clazzList.stream().map(v -> {
			LayerClazz clazz = new LayerClazz();
			clazz.setClassId(v.getClassId());
			clazz.setClassName(v.getClassName());
			clazz.setClassType(v.getType());
			return clazz;
		}).sorted((a, b) -> Integer.compare(a.getClassType(), a.getClassType())).collect(toList());
		return new JsonResult().addDatas("list", list);
	}

	@ResponseBody
	@RequestMapping("users")
	public JsonResult users(Long classId) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		List<Long> userIds = studentGroupRemoteService.findStudentByClassId(classId);
		List<GroupStu> groups = studentGroupRemoteService.findGroupStuByClassId(classId, userId);
		json.addDatas("users", this.transformToUserInfo(userIds));
		json.addDatas("groups", groups);
		return json;
	}

	@ResponseBody
	@RequestMapping("classUsers")
	public JsonResult classUsers(Long classId) {
		List<Long> userIds = studentGroupRemoteService.findStudentByClassId(classId);
		List<Map<String, Object>> list = this.transformToUserInfo(userIds);
		return new JsonResult().addDatas("list", list);
	}

	@ResponseBody
	@RequestMapping("groupUsers")
	public JsonResult groupUsers(Long groupId) {
		List<Long> userIds = studentGroupRemoteService.findStudentByGroupList(new Long[] { groupId });
		List<Map<String, Object>> list = this.transformToUserInfo(userIds);
		return new JsonResult().addDatas("list", list);
	}

	private List<Map<String, Object>> transformToUserInfo(List<Long> userIds) {
		return UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0])).stream().map(user -> {
			Map<String, Object> item = new HashMap<>();
			item.put("userId", user.getUserId());
			item.put("userName", user.getUserName());
			item.put("pinyin", Pinyin.toPinyinAbbr(user.getUserName()));
			return item;
		}).collect(toList());
	}
}
