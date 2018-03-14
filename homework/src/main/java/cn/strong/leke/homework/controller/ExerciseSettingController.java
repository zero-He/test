package cn.strong.leke.homework.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.ExerciseSettingDTO;
import cn.strong.leke.homework.service.ExerciseSettingService;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.question.IQuestionTypeRemoteService;

@Controller
@RequestMapping("/auth/*")
public class ExerciseSettingController {

	protected static final Logger logger = LoggerFactory.getLogger(ExerciseSettingController.class);

	@Resource
	private ExerciseSettingService exerciseSettingService;
	@Resource
	private IQuestionTypeRemoteService iQuestionTypeRemoteService;


	@RequestMapping("provost/exerciseSetting/preSetExerciseSetting")
	public void preSetExerciseSetting(ModelMap modelMap, Long homeworkId) {
		List<GradeRemote> gradeList = UserContext.user.findGradesOfCurrentSchool();
		List<SubjectRemote> subjectList = UserContext.user.findSubjectsOfCurrentSchool();
		List<QuestionType> questionTypeList = iQuestionTypeRemoteService.findAllQuestionTypes();
		
		List<QuestionType> kgqtList = new ArrayList<QuestionType>();
		//筛选出客观题
		for (QuestionType questionType : questionTypeList) {
			if (questionType.getSubjective().equals(false)) {
				kgqtList.add(questionType);
			}
		}
		
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("gradeListSize", gradeList.size());
		modelMap.addAttribute("kgqtList", kgqtList);
	}
	
	@RequestMapping("provost/exerciseSetting/loadExerciseSettingList")
	@ResponseBody
	public JsonResult loadExerciseSettingList() {
		JsonResult json = new JsonResult();
		Map<Long, List<ExerciseSettingDTO>> soESMap = exerciseSettingService.findExerciseSettingList(UserContext.user.getCurrentSchoolId(), 1);
		Map<Long, List<ExerciseSettingDTO>> kdESMap = exerciseSettingService.findExerciseSettingList(UserContext.user.getCurrentSchoolId(), 2);
		Map<String, List<QuestionType>> map = iQuestionTypeRemoteService.findAllSubjectQuestionTypeMap();
		
		Map<String, String> qtsMap = new HashMap<String, String>();
		//组装客观题和题型对应关系，以questionTypeId-subjectId作key
		for (String key : map.keySet()) {
			List<QuestionType> qtList = map.get(key);
			for (QuestionType questionType : qtList) {
				if (questionType.getSubjective().equals(false)) {
					qtsMap.put(questionType.getQuestionTypeId() + "-" + key, "true");
				}
			}
		}
		
		json.addDatas("soESMap", CollectionUtils.isEmpty(soESMap) ? "" : soESMap);
		json.addDatas("kdESMap", CollectionUtils.isEmpty(kdESMap) ? "" : kdESMap);
		json.addDatas("qtsMap", qtsMap);
		return json;
	}

	
	@RequestMapping("provost/exerciseSetting/setExerciseSetting")
	@ResponseBody
	public JsonResult setExerciseSetting(String soStr, String kdStr) {
		JsonResult json = new JsonResult();
		Boolean isSuccessed = exerciseSettingService.setExerciseSettingWithTx(soStr, kdStr);
		if (isSuccessed) {
			json.setMessage("设置成功");
		} else {
			json.setErr("设置失败");
		}
		return json;
	}
	
//	@RequestMapping("testcc")
//	@ResponseBody
//	public JsonResult testcc() {
//		JsonResult jsonResult = new JsonResult();
//		exerciseSettingService.test();
//		return jsonResult;
//	}
}
