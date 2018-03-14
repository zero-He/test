package cn.strong.leke.homework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.AssignLogDO;
import cn.strong.leke.homework.model.AssignLogDTO;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.query.AssignLogQueryDTO;
import cn.strong.leke.homework.model.query.AssignMatchingStuClass;
import cn.strong.leke.homework.model.query.AssignMatchingTeacher;
import cn.strong.leke.homework.model.query.AssignMatchingTeacherQuery;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.service.IAssignLogService;
import cn.strong.leke.homework.service.impl.MessageService;
import cn.strong.leke.lesson.model.KlassTeacher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 批量布置作业
 * @author Zhang Fujun
 * @date 2016年5月19日
 */
@Controller
@RequestMapping("/auth/*")
public class AssignLogController {

	@Resource
	private IAssignLogService assignLogServiceImpl;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkService homeworkService;
	@Resource
	private MessageService messageService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	/**
	 * page
	 * 布置作业页面
	 * @return
	 */
	@RequestMapping(value = "teacher/assignLog", method = RequestMethod.GET)
	public String batcheHomework() {
		return "auth/teacher/batch/assignLog";
	}

	/**
	 * page
	 * 批量布置作业列表页面
	 * @return
	 */
	@RequestMapping(value = "teacher/assignLogList", method = RequestMethod.GET)
	public String batchList() {
		return "auth/teacher/batch/assignLogList";
	}

	/**
	 * data 
	 * 异步加载 作业列表
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "teacher/loadAssignLogList")
	@ResponseBody
	public JsonResult loadAssignLogList(AssignLogQueryDTO query, Page page) {
		JsonResult json = new JsonResult();
		query.setUserId(UserContext.user.getUserId());
		page.setDataList(assignLogServiceImpl.findAssignLogList(query, page));
		json.addDatas("page", page);
		return json;
	}

	/**
	 * ajax
	 * 批量布置保存作业（代人布置）
	 * @param assignLogDTO
	 * @return
	 */
	@RequestMapping(value = "teacher/assignLog/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult bathSave(String dataJson) {
		AssignLogDTO assignLogDTO = JsonUtils.fromJSON(dataJson, AssignLogDTO.class);
		User user = UserContext.user.get();
		assignLogDTO.setTeacherId(user.getId());
		assignLogDTO.setTeacherName(user.getUserName());
		assignLogDTO.setSchoolId(user.getCurrentSchool().getId());
		assignLogServiceImpl.addAssignLog(assignLogDTO);
		return new JsonResult();
	}

	/**
	 * 按照试卷学科和班级Id对应关系，查询唯一老师
	 * eg：subjectId:[1,2,3],classId:[401,365] ,老师三个学科和两个班级都授课，该老师被查询到
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "teacher/assignLog/findAssignTeacher", method = RequestMethod.POST)
	@ResponseBody
	private JsonResult findAssignTeacher(String json) {
		AssignMatchingTeacherQuery query = JsonUtils.fromJSON(json, AssignMatchingTeacherQuery.class);
		List<KlassTeacher> datas = klassRemoteService.findKlassTeachersBySubjectIds(query.getSubjectIds(),
				UserContext.user.getCurrentSchoolId());
		List<AssignMatchingTeacher> teacherList = new ArrayList<>();
		for (AssignMatchingStuClass item : query.getStuClasses()) {
			KlassTeacher teacher = filterTeacher(query.getSubjectIds(), item, datas);
			if (teacher != null) {
				AssignMatchingTeacher assignTeacher = new AssignMatchingTeacher(item.getIndex(), teacher.getUserId(),
						teacher.getUserName());
				teacherList.add(assignTeacher);
			}
		}
		return new JsonResult().addDatas("list", teacherList);

	}

	/**
	 * 过滤 班级和学科都符合的老师，
	 * @param subjectId
	 * @param stuClass
	 * @param datas
	 * @return
	 */
	private KlassTeacher filterTeacher(List<Long> subjectId, AssignMatchingStuClass stuClass, List<KlassTeacher> datas) {
		Set<Long> teacherIdSet = null;
		for (Long subjId : subjectId) {
			for (Long classId : stuClass.getClassIds()) {
				Set<Long> classIdTeacher = new HashSet<>();
				datas.forEach(t->{
				 if(t.getSubjectId().equals(subjId) && t.getClassId().equals(classId)){
					 classIdTeacher.add(t.getUserId());
				 }});
				if(teacherIdSet == null){
					teacherIdSet = new HashSet<Long>();
					teacherIdSet.addAll(classIdTeacher);
				}else{
					teacherIdSet.retainAll(classIdTeacher);
				}
			}
		}
		if(teacherIdSet.size() != 1){
			return null;
		}else{
			Long teacherId = teacherIdSet.stream().findFirst().get();
			return datas.stream().filter(t -> {
				return t.getSubjectId().equals(subjectId.get(0)) && t.getClassId().equals(stuClass.getClassIds().get(0))
						&& teacherId.equals(t.getUserId());
			}).findFirst().get();
		}
	}

	/**
	 * ajax
	 * 修改作业的截止日期
	 * @param assignId
	 * @param closeTime
	 * @return
	 */
	@RequestMapping(value = "teacher/assignLog/modify")
	@ResponseBody
	public JsonResult modifyCloseTime(Long assignId, Date closeTime) {
		AssignLogDO oldAssignLog = assignLogServiceImpl.getAssignLog(assignId);
		String homeworkIds = oldAssignLog.getHomeworkIdJson();
		Date oldStartTime = oldAssignLog.getStartTime();
		Date oldCloseTime = oldAssignLog.getCloseTime();
		AssignLogDO assignLog = new AssignLogDO();
		assignLog.setAssignId(assignId);
		assignLog.setCloseTime(closeTime);
		assignLog.setModifiedBy(UserContext.user.getUserId());
		assignLogServiceImpl.modifyCloseTime(assignLog);

		//截止时间修改发送消息提醒
		if (closeTime != null && new Date().after(oldStartTime) && oldCloseTime.compareTo(closeTime) != 0) {
			if (StringUtils.isNotEmpty(homeworkIds)) {
				String[] hwIds = homeworkIds.split(",");
				for (String id : hwIds) {
					Long homeworkId = new Long(id);
					Homework hw = homeworkService.getHomeworkById(homeworkId);
					if (!hw.getStatus().equals(2)) {
						List<Long> userIds = homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId).stream()
								.map(s -> s.getStudentId()).collect(Collectors.toList());
						messageService.sendModifyHwCloseTimeLetterMessage(userIds, hw.getHomeworkName(), closeTime);
					}
				}
			}
		}
		return new JsonResult();
	}

	/**
	 * ajax
	 * 作废作业
	 * @param assignId
	 * @return
	 */
	@RequestMapping(value = "teacher/assignLog/invalid")
	@ResponseBody
	public JsonResult invalid(Long assignId) {
		AssignLogDO assignLog = new AssignLogDO();
		assignLog.setAssignId(assignId);
		assignLog.setModifiedBy(UserContext.user.getUserId());
		assignLogServiceImpl.invalid(assignLog);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("teacher/auto/findTeacher")
	public Object findTeacher(String teacherName, Page page) {
		if (StringUtils.isEmpty(teacherName)) {
			throw new ValidateException("请输入用户名");
		}
		JsonResult jsonResult = new JsonResult();
		List<UserRemote> teacherList = userRemoteService.queryUserByRoleIdSchoolId(RoleCst.TEACHER, teacherName,
				UserContext.user.getCurrentSchoolId());
		Function<UserRemote, Map<String, Object>> userMaps = (user) -> {
			Map<String, Object> item = new HashMap<>();
			item.put("userName", user.getUserName());
			item.put("id", user.getId());
			return item;
		};
		List<Map<String, Object>> list = teacherList.stream().map(userMaps).collect(Collectors.toList());
		jsonResult.addDatas("items", list);
		return jsonResult;
	}
}