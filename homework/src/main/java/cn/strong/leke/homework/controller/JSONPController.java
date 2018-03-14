package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.HwWrongStudentService;
import cn.strong.leke.homework.model.MHomeworkDtlInfo;
import cn.strong.leke.homework.model.SubjectHwStatistics;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

@Controller
@RequestMapping
public class JSONPController {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HwWrongStudentService hwWrongStudentService;

	@ResponseBody
	@RequestMapping("/auth/student/homework/subjects")
	public Object subjects(String jsonpcallback) {
		List<SubjectRemote> subjectList = UserContext.user.findSubjectsOfCurrentSchool();

		User user = UserContext.user.get();
		long studentId = user.getId();
		List<SubjectHwStatistics> subjectHomeworks = homeworkDtlService.getSubjectHomeworkInfo(studentId);

		Map<Long, SubjectRemote> subjMap = subjectList.stream().collect(toMap(SubjectRemote::getSubjectId, v -> v));
		Map<Long, List<SubjectHwStatistics>> map = subjectHomeworks.stream()
				.collect(groupingBy(SubjectHwStatistics::getSubjectId));

		List<Map<String, Object>> subjs = map.values().stream().map(v -> {
			Map<String, Object> item = new HashMap<>();
			item.put("subjectId", v.get(0).getSubjectId());
			item.put("subjectName", v.get(0).getSubjectName());
			item.put("unfinishNum", v.stream().mapToLong(SubjectHwStatistics::getUnfinishNum).sum());
			subjMap.remove(v.get(0).getSubjectId());
			return item;
		}).collect(toList());
		subjMap.values().forEach(v -> {
			Map<String, Object> item = new HashMap<>();
			item.put("subjectId", v.getSubjectId());
			item.put("subjectName", v.getSubjectName());
			item.put("unfinishNum", 0);
			subjs.add(item);
		});

		JsonResult json = new JsonResult();
		json.addDatas("subjs", subjs);
		if (StringUtils.isNotEmpty(jsonpcallback)) {
			return new JSONPObject(jsonpcallback, json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/auth/teacher/homework/wrongUsers")
	public JSONPObject findWrongQuesStudent(Long homeworkId, Long questionId, String jsonpcallback) {
		List<Long> wrongQuesStudents = hwWrongStudentService.findWrongStudent(homeworkId, questionId);
		List<UserBase> userBaseList = UserBaseContext.findUserBaseByUserId(wrongQuesStudents.toArray(new Long[0]));
		return new JSONPObject(jsonpcallback, new JsonResult().addDatas("studentList", userBaseList));
	}
	
	/**
	 * @param homeworkIds
	 * @param jsonpcallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping("auth/student/findVodHwInfo")
	public JSONPObject findVodHwInfo(@RequestParam(name = "homeworkIds", required = false) Long[] homeworkIds,
			String jsonpcallback) {
		JsonResult jsonResult = new JsonResult();
		Long userId = UserContext.user.getUserId();
		List<MHomeworkDtlInfo> list = this.homeworkDtlService.findMHwInfo(Arrays.asList(homeworkIds), userId);
		jsonResult.addDatas("list", list);
//		return jsonResult;
		return new JSONPObject(jsonpcallback, jsonResult);
	}
}
