package cn.strong.leke.homework.controller;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.ExerciseService;
import cn.strong.leke.homework.manage.HolidayHwMicroService;
import cn.strong.leke.homework.model.AnswerInfo;
import cn.strong.leke.homework.model.ExerciseType;
import cn.strong.leke.homework.model.HolidayMatNode;
import cn.strong.leke.homework.model.MyHomeworkDTO;
import cn.strong.leke.homework.model.mongo.*;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.util.AnswerUtils;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.PhotoUtils;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.querys.QuestionSelectQuery;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.notice.model.MessageBusinessTypes;
import cn.strong.leke.remote.service.question.IKnowledgeRemoteService;
import cn.strong.leke.remote.service.question.IQuestionSelectRemoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 寒暑假假期作业 ajax data
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
@RestController
@RequestMapping(value = { "/auth/common/holiday/*", "/auth/hd/common/holiday/*" })
public class HolidayHwDataController {

	@Resource
	private HolidayHwMicroService holidayHwService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private ExerciseService exerciseService;
	@Resource
	private IQuestionSelectRemoteService questionSelectRemoteService;
	@Resource
	private IKnowledgeRemoteService knowledgeRemoteService;

	/**
	 * 获取学生的作业或微课列表
	 * @param type 1：作业，2：微课
	 * @return
	 */
	@RequestMapping("ajax/loadListByStudent")
	public JsonResult loadListByStudent(Long studentId, Integer type, Page page) {
		if(RoleCst.STUDENT.equals(UserContext.user.getCurrentRoleId())){
			studentId = UserContext.user.getUserId();
		}
		JsonResult jsonResult = new JsonResult();
		List<HolidayHwMicro> list = this.holidayHwService.findStuHolidayHomeworks(studentId, type, page);
		page.setDataList(list);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}

	/**
	 * 获取学生的作业或微课列表
	 * type 1：作业，2：微课
	 * @param subjectId
	 * @param classId
	 * @param year
	 * @param holiday
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("ajax/loadListByClass")
	public JsonResult loadListByClass(Long subjectId, Long classId, Long year, Integer holiday, Page page, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		List<HolidayHwMicro> list = this.holidayHwService.findClassHolidayHomeworks(subjectId, classId, year, holiday, page);
		for(HolidayHwMicro hhm : list){
			hhm.setUserName(UserBaseContext.getUserBaseByUserId(hhm.getUserId()).getUserName());
		}
		page.setDataList(list);
		jsonResult.addDatas("page", page);
		HashMap<Long,String> photoURL = new HashMap<Long, String>();
		list.stream().forEach(v->{
			UserBase ubase = UserBaseContext.getUserBaseByUserId(v.getUserId());
			String photoSrc= PhotoUtils.getAbsoluteResizePhoto(ubase.getPhoto(), ubase.getSex(), request);
			photoURL.put(v.getUserId(), photoSrc);
			
		});
		jsonResult.addDatas("userPhoto", photoURL);
		return jsonResult;
	}

	/**
	 * 返回学生寒假某个学科的作业或微课概要信息
	 * @param id
	 * @return
	 */
	@RequestMapping("ajax/getHolidayHwMicroSummary")
	public JsonResult getHolidayHwMicroSummary(String id) {
		JsonResult jsonResult = new JsonResult();
		HolidayHwMicro holidayHomework = this.holidayHwService.getSingleHolidayHomework(id);
		/*Long userId = UserContext.user.getUserId();
		Validation.isPageNotFound(userId != holidayHomework.getUserId());*/
		jsonResult.addDatas("summary", holidayHomework);
		return jsonResult;
	}
	

	/**
	 * 返回学生寒假某个学科的作业列表
	 * @param id
	 * @return
	 */
	@RequestMapping("ajax/getStuSubjectHomeworkInfo")
	public JsonResult getStuSubjectHomeworkInfo(String id, Page page) {
		JsonResult jsonResult = new JsonResult();
		HolidayHwMicro holidayHomework = this.holidayHwService.getSingleHolidayHomework(id);
		/*Long userId = UserContext.user.getUserId();
		Validation.isPageNotFound(userId != holidayHomework.getUserId());*/
		List<Long> homeworkDtlIds = holidayHomework.getHomeworkDtlIds();
		if(homeworkDtlIds==null){
			page.setTotalSize(0);
			page.setDataList(new ArrayList<MyHomeworkDTO>());
			jsonResult.addDatas("page", page);
			return jsonResult;
		}else{
			page.setTotalSize(homeworkDtlIds.size());
			homeworkDtlIds = homeworkDtlIds.stream().skip(page.getOffset()).limit(page.getLimit()).collect(Collectors.toList());
			if(homeworkDtlIds.size() > 0){
				List<MyHomeworkDTO> homeworkList = homeworkDtlService.findHomeworkDtlByIds(homeworkDtlIds);
				page.setDataList(homeworkList);
			}
			jsonResult.addDatas("page", page);
			return jsonResult;
		}
		
	}
	
	/**
	 * 返回学生寒假某个学科的微课列表
	 * @param id
	 * @return
	 */
	@RequestMapping("ajax/getStuSubjectMicroInfos")
	public JsonResult getStuSubjectMicroInfos(String id, Page page) {
		JsonResult jsonResult = new JsonResult();
		HolidayHwMicro holidayHomework = this.holidayHwService.getSingleHolidayHomework(id);
		List<HolidayMatNode> dataList = convertToMatNodes(holidayHomework, page);
		page.setDataList(dataList);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	/**
	 * 已经学习的微课，更新状态
	 * @param id
	 * @return
	 */
	@RequestMapping("ajax/updateMicroState")
	public JsonResult updateMicroState(String id, Long microId, Integer position, Boolean isPlayEnd) {
		if (RoleCst.STUDENT.equals(UserContext.user.getCurrentRoleId())) {
			HolidayHwMicro holidayHwMicro = this.holidayHwService.getSingleHolidayHomework(id);
			Optional<HolidayMicrocourse> holidayMicro = holidayHwMicro.getMicrocourses().stream()
					.filter(v -> v.getMicroId().equals(microId)).findFirst();
			if (holidayMicro.isPresent() && holidayHwMicro.getUserId().equals(UserContext.user.getUserId())) {
				Boolean isFirstFinish = holidayMicro.get().getMicroState() != 1 && isPlayEnd;
				this.holidayHwService.updateMicroState(id, microId, isFirstFinish, position);
			}
		}
		return new JsonResult();
	}
	
	private List<HolidayMatNode> convertToMatNodes(HolidayHwMicro holidayHomework, Page page) {
		List<HolidayMatNode> list = new ArrayList<HolidayMatNode>();
		List<HolidayMicrocourse> microcourses = holidayHomework.getMicrocourses();
		page.setTotalSize(microcourses.size());
		microcourses = microcourses.stream().skip(page.getOffset()).limit(page.getLimit()).collect(Collectors.toList());
		for (HolidayMicrocourse mic : microcourses) {
			HolidayMatNode node = new HolidayMatNode();
			MatNode matNode = holidayHomework.getMatNodes().stream().filter(v->v.getNodeId().equals(mic.getMatNodeId())).findFirst().get();
			node.setMatNodeId(matNode.getNodeId());
			node.setMatNodeName(matNode.getNodeName());
			KnoledgeNode knoledgeNode = matNode.getKnoledageNodes().stream().filter(v->v.getkId().equals(mic.getKnowledgId())).findFirst().get();
			node.setkId(knoledgeNode.getkId());
			node.setkName(knoledgeNode.getkName());
			HolidayMicrocourse microcourse = microcourses
					.stream()
					.filter(v -> v.getKnowledgId().equals(knoledgeNode.getkId())
							&& v.getMatNodeId().equals(matNode.getNodeId())).findFirst().get();
			node.setMicroHwState(microcourse.getAccuracy() != null ? 1 : 0);
			node.setMicroId(microcourse.getMicroId());
			node.setMicroName(microcourse.getMicroName());
			node.setMicroState(microcourse.getMicroState());
			node.setTime(microcourse.getTime());
			node.setTimeStr(microcourse.getTimeStr());
			node.setAccuracy(microcourse.getAccuracy());
			node.setExerciseId(microcourse.getExerciseId());
			list.add(node);
		}
		return list;
	}
	
	/**
	 * 生成微课作业，生成成功返回其id
	 * @param exerciseId
	 * @param knowledgeId
	 * @param knowledgeName
	 * @param subjectId
	 * @param subjectName
	 * @return
	 */
	@RequestMapping("generateExerciseForMicro")
	@ResponseBody
	public JsonResult generateExerciseForMicro(String exerciseId, Long knowledgeId,String knowledgeName,Long subjectId,String subjectName) {
		JsonResult jsonResult = new JsonResult();
		Exercise dbExercise = this.exerciseService.getExerciseById(exerciseId);
		if(dbExercise != null){
			jsonResult.addDatas("exerciseId", dbExercise.getExerciseId());
			return jsonResult;
		}
		
		//生成练习
		Exercise exercise = new Exercise();
		exercise.setSubjectId(subjectId);
		exercise.setSubjectName(subjectName);
		exercise.setExerciseId(exerciseId);
		exercise.setRelIds(Arrays.asList(knowledgeId));
		exercise.setExerciseName(knowledgeName);
		exercise.setExerciseType((long) ExerciseType.KNOWLEDGE.value);
		exercise.setStatus(2);//非自主练习
		QuestionSelectQuery query = new QuestionSelectQuery();
		query.setKnowledgeIds(exercise.getRelIds());
		//TODO zfj 易中难 122找题目暂时忽略
		//query.setDiffLevel(exercise.getDifficultyLevel()); //取消难度系数，后期再恢复
		query.setQuestionNum(5);
		query.setSubjectId(exercise.getSubjectId());
		query.setIncludeSubjective(false);
		List<Long> questionIds = questionSelectRemoteService.queryRandomQuestionIds(query);
		if (CollectionUtils.isEmpty(questionIds)) {
			jsonResult.addDatas("exerciseId", null);
			return jsonResult;
		}

		User user = UserContext.user.get();
		exercise.setStudentId(user.getId()); 
		exercise.setSchoolId(user.getCurrentSchool().getId());
		exercise.setIsDeleted(false);
		List<QuestionDTO> questionDTOs = QuestionContext.findQuestions(questionIds);
		List<AnswerInfo> answerInfoList = AnswerUtils.virtualAnswerInfos(questionDTOs);
		this.exerciseService.saveExercise(exercise, answerInfoList);
		jsonResult.addDatas("exerciseId", exercise.getExerciseId());
		return jsonResult;
	}

	/**
	 * 老师手动催交寒暑假作业
	 * @Author LIU.SHITING
	 * @Version 2.6
	 * @Date 2017/5/11 20:19
	 * @Param [classId, year, holiday]
	 * @return cn.strong.leke.framework.web.JsonResult
	 */
	@RequestMapping(value = "callVacationHomework", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult callVacationHomework(Long subjectId, Long classId, Long year, Integer holiday){
		JsonResult result = new JsonResult();
		//查未完成寒暑假作业的学生信息
		List<HolidayHwMicro> list = this.holidayHwService.queryVacationHomeworks(subjectId, classId, year, holiday);
		if (list.size() > 0) {
			List<Long> userIdList = new ArrayList<Long>();
			for (HolidayHwMicro holidayHwMicro : list) {
				Long userId = holidayHwMicro.getUserId();
				if (!userIdList.contains(userId)) {
					userIdList.add(userId);
				}
			}
			List<String> to = userIdList.stream().map(String::valueOf).collect(Collectors.toList());
			String subject = "催交寒暑假作业";
			String content = ParametersContext.getString(HomeworkCst.HOMWORK_CALL_VACATION_HOMEWORKS);
			LetterMessage message = new LetterMessage();
			message.setBusinessType(MessageBusinessTypes.HOMEWORK);
			message.addTo(to);
			message.setSubject(subject);
			message.setContent(content);
			message.addVariable("vacation", holiday == 1 ? "寒假" : "暑假");
			NoticeHelper.send(message);
		} else {
			throw new ValidateException("没有可催交的作业！");
		}

		return result;
	}

}
