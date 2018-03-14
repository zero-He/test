package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.datetime.Week;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.ExerciseService;
import cn.strong.leke.homework.model.AnswerInfo;
import cn.strong.leke.homework.model.ExerciseType;
import cn.strong.leke.homework.model.mongo.Exercise;
import cn.strong.leke.homework.model.mongo.ExerciseQuestionResult;
import cn.strong.leke.homework.model.mongo.ExerciseRank;
import cn.strong.leke.homework.model.mongo.ExerciseRankUser;
import cn.strong.leke.homework.model.mongo.ExerciseReport;
import cn.strong.leke.homework.model.query.ExerciseListQuery;
import cn.strong.leke.homework.util.ExerciseCommon;
import cn.strong.leke.homework.util.ExerciseUtils;
import cn.strong.leke.homework.util.PhotoUtils;
import cn.strong.leke.homework.util.ScoreUtils;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.user.RoleSchool;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.learn.ILearnRemoteService;
import cn.strong.leke.remote.service.microcourse.IMicrocourseRemoteService;
import cn.strong.leke.remote.service.question.IKnowledgeRemoteService;
import cn.strong.leke.tags.answer.ExerciseViewModel;
import cn.strong.leke.tags.answer.ResDataModel;
import cn.strong.leke.tags.question.prepare.PrepareHandler;
import cn.strong.leke.tags.question.prepare.PrepareHandlerAdapter;

@Controller
@RequestMapping("/auth/*")
public class ExerciseController {

	@Resource
	private ExerciseService exerciseService;
	@Resource
	private IKnowledgeRemoteService knowledgeRemoteService;
	@Resource
	private IMicrocourseRemoteService microcourseRemoteService;
	@Resource
	private ILearnRemoteService learnRemoteService;
	
	@RequestMapping("student/exercise/syn/exerciseList")
	public String ExerciseList() {
		return "redirect:/auth/student/exercise/list.htm";
	}

	@RequestMapping("student/exercise/list")
	public void list(Model model) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		model.addAttribute("weekOfYear", weekOfYear);
	}
	
	@RequestMapping("student/exercise/index")
	public void index(Model model) {
		/*Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		model.addAttribute("weekOfYear", weekOfYear);*/
	}
	
	@RequestMapping(value = {"student/exercise/ajax/loadList"})
	@ResponseBody
	public JsonResult loadExerciseList(ExerciseListQuery query, Integer timeRangeDays, Page page) {
		if(timeRangeDays != null){
			query.setEndTime(new Date());
			query.setStartTime(DateUtils.addDays(new Date(), -timeRangeDays));
		}
		if (query.getEndTime() != null) {
			// 结束日期后移一天
			query.setEndTime(DateUtils.addDays(query.getEndTime(), 1));
		}
		query.setStudentId(UserContext.user.getUserId());
		List<Exercise> datas = exerciseService.findStuExerciseList(query, page);
		datas.forEach(v->{
			if(v.getAccuracy() != null){
				v.setAccuracy(new BigDecimal(ScoreUtils.toPercent(v.getAccuracy())));
			}
		});
		page.setDataList(datas);
		return new JsonResult().addDatas("page", page);
	}
	
	@RequestMapping(value = {"student/exercise/ajax/loadWeekRank"})
	@ResponseBody
	public JsonResult loadWeekRank(Integer week, HttpServletRequest request) {
		JsonResult json = new JsonResult();
		Integer year = DateUtils.getCurrentYear();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		Week wk = new Week(calendar.getTime());
		calendar.setTime(DateUtils.addDays(wk.getStartDate(), -1));
		ExerciseRank rank = new ExerciseRank();
		if(week == 0){
			week = calendar.get(Calendar.WEEK_OF_YEAR);
			year = calendar.get(Calendar.YEAR);
			rank = exerciseService.findWeekRank(year, week);
		}else{
			rank = exerciseService.findWeekRank(year, week);
		}
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		ArrayList<Long> userIds = new ArrayList<>();
		userIds.add(UserContext.user.getUserId());
		if (rank != null && rank.getList().size() > 0) {
			rank.getList().forEach(v -> {
				userIds.add(v.getId());	
			});
		}
		
		if (rank != null && rank.getList().size() > 0) {
			rank.getList().forEach(
					v -> {
						Map<String, Object> itemMap = new HashMap<>();
						UserBase userBase = UserBaseContext.getUserBaseByUserId(v.getId());
						itemMap.put("userId", v.getId());
						itemMap.put("userName", userBase.getUserName());
						String photo = PhotoUtils.getAbsoluteResizePhoto(userBase.getPhoto(), userBase.getSex(), request);
						itemMap.put("photo", photo);
						itemMap.put("total", v.getTotal());
						itemMap.put("rank", v.getRank());
						Optional<RoleSchool> firstOptional = userBase.getRoleSchoolList().stream()
								.filter(s -> s.getRoleId().equals(RoleCst.STUDENT)).findFirst();
						if (firstOptional.isPresent()) {
							String schoolName = firstOptional.get().getSchoolName();
							itemMap.put("schoolName", schoolName);
						}
						dataList.add(itemMap);
						userIds.add(v.getId());
					});
			//查询当前用户是否在该排名中
			Long userId = UserContext.user.getUserId();
			Optional<ExerciseRankUser> userRank = rank.getList().stream().filter(v -> v.getId().equals(userId))
					.findFirst();
			if (userRank.isPresent()) {
				json.addDatas("myRank", userRank.get());
			} else {
				Map<String, Object> myNoRank = new HashMap<String, Object>();
				Long total = exerciseService.getRightQCountByWeek(userId, year, week,rank.getCreatedOn());
				myNoRank.put("total", total);
				myNoRank.put("rank", null);
				if(total != null){
					myNoRank.put("difference", rank.getList().get(rank.getList().size() - 1).getTotal() - total);
				}
				json.addDatas("myRank", myNoRank);
			}
		}
		return json.addDatas("dataList", dataList);
	}

	@RequestMapping(value = {"student/exercise/ajax/loadTodayRank"})
	@ResponseBody
	public JsonResult loadTodayRank(HttpServletRequest request) {
		List<Exercise> list = exerciseService.findTodayRank();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		list.stream().forEach(v -> {
			Map<String, Object> itemMap = new HashMap<>();
			UserBase userBase = UserBaseContext.getUserBaseByUserId(v.getStudentId());
			itemMap.put("userId", v.getStudentId());
			itemMap.put("userName", userBase.getUserName());
			String photo = PhotoUtils.getAbsoluteResizePhoto(userBase.getPhoto(), userBase.getSex(), request);
			itemMap.put("photo", photo);
			itemMap.put("subjectId", v.getSubjectId());
			itemMap.put("subjectName", v.getSubjectName());
			itemMap.put("rightNum", v.getRightNum());
			itemMap.put("submitTime", DateUtils.format(new Date(v.getSubmitTime()), "HH:mm"));
			itemMap.put("schoolName", SchoolContext.getSchoolBySchoolId(v.getSchoolId()).getName());
			dataList.add(itemMap);
		});
		return new JsonResult().addDatas("dataList", dataList);
	}

	//查看页面
	@RequestMapping(value = { "student/exercise/viewWork", "common/exercise/viewWork" })
	public String viewWork(@RequestParam("id") String exerciseId, Model model) {
		Exercise exercise = this.exerciseService.getExerciseById(exerciseId);
		List<QuestionResult> questionResults = exerciseService.paresToQuestionResult(exercise.getQuestions());
		model.addAttribute("exercise", exercise);
		ExerciseViewModel exerciseViewModel = convertToExerciseViewModel(exercise, questionResults);
		exerciseViewModel.setScore(BigDecimal.valueOf(exercise.getAccuracy().doubleValue()*100));
		exerciseViewModel.setWorkMode(ResDataModel.DO_VIEWER);
		model.addAttribute("workModel", this.buildWorkModelJson(ResDataModel.DO_VIEWER, ResDataModel.DO_FINISH, exercise));
		model.addAttribute("exerciseData", exerciseViewModel);
		return "auth/student/exercise/viewWork";
	}
	
	//知识点详情查看页面
	@RequestMapping("student/exercise/viewWorkForKnowledge")
	public String viewWorkForKnowledge(@RequestParam("id") String exerciseId, Long knowledgeId, Model model) {
		Exercise exercise = this.exerciseService.getExerciseById(exerciseId);
		long userId = UserContext.user.getUserId();
		if (!exercise.getStudentId().equals(userId)) {
			throw new ValidateException("homework.common.pagenotfound");
		}
		Optional<ExerciseReport> first = exercise.getReport().stream()
				.filter(v -> v.getKnowledgeId().equals(knowledgeId)).findFirst();
		if (first.isPresent()) {
			List<Long> knowledgeQuestionIds = first.get().getQuestions();
			List<ExerciseQuestionResult> knowledgeQuestions = exercise.getQuestions().stream()
					.filter(v -> knowledgeQuestionIds.contains(v.getQuestionId())).collect(Collectors.toList());
			List<QuestionResult> questionResults = exerciseService.paresToQuestionResult(knowledgeQuestions);

			List<Map<String, Object>> questionList = new ArrayList<Map<String, Object>>();
			for (QuestionResult questionResult : questionResults) {
				PrepareHandlerAdapter.doPrepare(PrepareHandler.Mode.View, questionResult);
				Map<String, Object> question = new HashMap<String, Object>();
				question.put("questionId", questionResult.getQuestionId());
				question.put("questionResult", questionResult);
				questionList.add(question);
			}
			long rightNum = knowledgeQuestions.stream().filter(v -> Boolean.TRUE.equals(v.getIsCorrect())).count();

			ExerciseViewModel exerciseViewModel = convertToExerciseViewModel(exercise, questionResults);
			exerciseViewModel.setTitle("【知识点】"+first.get().getKnowledgeName());
			exerciseViewModel.setScore(BigDecimal.valueOf(rightNum*100).divide(
					BigDecimal.valueOf(knowledgeQuestionIds.size()),2, RoundingMode.HALF_UP));
			exerciseViewModel.setWorkMode(ResDataModel.DO_VIEWER);
			model.addAttribute("workModel", this.buildWorkModelJson(ResDataModel.DO_VIEWER, ResDataModel.DO_FINISH, exercise));
			model.addAttribute("exerciseData", exerciseViewModel);
		} else {
			throw new ValidateException("homework.common.pagenotfound");
		}
		return "auth/student/exercise/viewWork";

	}

	//查看页面
	@RequestMapping(value={"student/exercise/doExerciseWork","parent/exercise/doExerciseWork","teacher/exercise/doExerciseWork","classTeacher/exercise/doExerciseWork"})
	public String doExerciseWork(@RequestParam("id") String exerciseId, Model model) {
		Exercise exercise = this.exerciseService.getExerciseById(exerciseId);
		User user = UserContext.user.get();
		Long  curRoleId = UserContext.user.getCurrentRoleId();
		if(exercise.getSubmitTime() != null || RoleCst.TEACHER.equals(curRoleId) || RoleCst.PARENT.equals(curRoleId)){
			return "redirect:/auth/common/exercise/viewWork.htm?id="+exerciseId;
		}
		if (!exercise.getStudentId().equals(user.getId())) {
			throw new ValidateException("homework.common.pagenotfound");
		}
		List<QuestionResult> questionResults = exerciseService.paresToQuestionResult(exercise.getQuestions());

		model.addAttribute("exercise", exercise);
		ExerciseViewModel exerciseViewModel = convertToExerciseViewModel(exercise, questionResults);
		exerciseViewModel.setWorkMode(ResDataModel.DO_ANSWER);
		model.addAttribute("workModel", this.buildWorkModelJson(ResDataModel.DO_ANSWER, ResDataModel.DO_ANSWER, exercise));
		model.addAttribute("exerciseData", exerciseViewModel);
		return "/auth/student/exercise/doWork";
	}
	
	private String buildWorkModelJson(String workMode, String workStage, Exercise exercise) {
		Map<String, Object> model = new HashMap<>();
		model.put("workMode", workMode);
		model.put("workStage", workStage);
		model.put("exerciseId", exercise.getExerciseId());
		model.put("subjectId", exercise.getSubjectId());
		model.put("homeworkName", exercise.getExerciseName());
		model.put("exerciseId", exercise.getExerciseId());
		return JsonUtils.toJSON(model);
	}

	/**
	 * @param exercise
	 * @param questionResults
	 * @return
	 */
	private ExerciseViewModel convertToExerciseViewModel(Exercise exercise, List<QuestionResult> questionResults) {
		if (questionResults != null) {
			PrepareHandlerAdapter.doPrepare(PrepareHandler.Mode.Work, questionResults);
			questionResults.forEach(v -> v.setTotalResultScore(null));
			questionResults.stream().filter(v -> CollectionUtils.isNotEmpty(v.getSubs()))
					.flatMap(v -> v.getSubs().stream()).forEach(v -> v.setTotalResultScore(null));
		}
		ExerciseViewModel exerciseViewModel = new ExerciseViewModel();
		exerciseViewModel.setQuestionIds(questionResults.stream().map(v->v.getQuestionId()).collect(toList()));
		exerciseViewModel.setQuestionNum(questionResults.size());
		exerciseViewModel.setQuestionResults(questionResults);
		exerciseViewModel.setSubjectId(exercise.getSubjectId());
		exerciseViewModel.setSubjectName(exercise.getSubjectName());
		exerciseViewModel.setTitle(ExerciseUtils.getExerciseTitle(exercise.getExerciseName(), exercise.getExerciseType()));
		exerciseViewModel.setId(exercise.getExerciseId());
		if (exercise.getAccuracy() != null) {
			exerciseViewModel.setScore(exercise.getAccuracy().multiply(new BigDecimal(100)));
		}
		return exerciseViewModel;
	}

	@RequestMapping("student/exercise/generate")
	@ResponseBody
	public JsonResult generate(Exercise exercise, @RequestParam("relId[]") List<Long> relIds, Model model) {
		exercise.setRelIds(relIds);
		this.fillData(exercise);
		List<Long> questionIds = findExerciseQuestion(exercise);
		return  new JsonResult().addDatas("exerciseId", addNewExercise(exercise, questionIds));
	}

	/**
	 * @param exercise
	 * @param questionIds
	 */
	private String addNewExercise(Exercise exercise, List<Long> questionIds) {
		List<QuestionDTO> questonDtos = QuestionContext.findQuestions(questionIds);
		List<AnswerInfo> answerInfoList = convertToAnserInfo(questonDtos);
		this.exerciseService.saveExercise(exercise, answerInfoList);
		return exercise.getExerciseId();
	}

	/**
	 * @param exercise
	 * @return
	 */
	private List<Long> findExerciseQuestion(Exercise exercise) {
		List<Long> questionIds = this.exerciseService.findQuestionsFromResources(exercise);
		if (CollectionUtils.isEmpty(questionIds)) {
			throw new ValidateException("homework.exercise.nosuitable");
		}
		return questionIds;
	}

	/**
	 * @param questionIds
	 * @return
	 */
	private List<AnswerInfo> convertToAnserInfo(List<QuestionDTO> questonDtos) {
		 List<AnswerInfo> answerInfoList = questonDtos.stream().map(qto->{
			 AnswerInfo item = new AnswerInfo();
			 item.setQuestionId(qto.getQuestionId());
			 item.setAnswerContent(null);
			 if(CollectionUtils.isNotEmpty(qto.getSubs())){
				 item.setSubs(convertToAnserInfo(qto.getSubs()));
			 }
			 return item;
		 }).collect(toList());
		return answerInfoList;
	}


	@RequestMapping("student/exercise/report")
	public void report(@RequestParam("id") String exerciseId, Model model) {
		Exercise exercise = exerciseService.getExerciseById(exerciseId);
		exercise.setAccuracy(new BigDecimal(ScoreUtils.toPercent(exercise.getAccuracy())));
		List<ExerciseReport> directReport = null;
		if (exercise.getExerciseType() == ExerciseType.KNOWLEDGE.value) {
			directReport = exercise.getReport().stream().filter(v -> v.getType().equals(ExerciseReport.KNOWLEDGE))
					.collect(Collectors.toList());
		}
		if(CollectionUtils.isNotEmpty(directReport)){
			directReport.forEach(v->v.setAccuracy(new BigDecimal(ScoreUtils.toPercent(v.getAccuracy()))));
		}
		model.addAttribute("directReport", directReport);
		List<ExerciseReport> relationReport = exercise.getReport().stream()
				.filter(v -> v.getType().equals(ExerciseReport.RELATION_KNOWLEDGE)).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(relationReport)){
			relationReport.forEach(v->v.setAccuracy(new BigDecimal(ScoreUtils.toPercent(v.getAccuracy()))));
		}
		model.addAttribute("relationReport", relationReport);
		model.addAttribute("exerciseTitle", ExerciseUtils.getExerciseTitle(exercise.getExerciseName(), exercise.getExerciseType()));
		model.addAttribute("exercise", exercise);
		Exercise firstExercise = exerciseService.getFirstStuExercise(UserContext.user.getUserId());
		if(firstExercise != null && firstExercise.getExerciseId().equals(exerciseId)){
			model.addAttribute("firstExercise", true);
		}else{
			model.addAttribute("firstExercise", false);
		}
	}

	/**
	 * 根据练习类型和关联标识，生成练习试卷。
	 * @param exercise 练习信息
	 * @param
	 */
	@RequestMapping("student/exercise/doWork2")
	public String doWork2(Long knoId, Model model) {
		Knowledge knowledge = this.knowledgeRemoteService.getKnowledgeById(knoId);
		Validation.notNull(knowledge, "该知识不存在或者已被删除，试试其它知识点吧！");
		SubjectRemote subjectRemote = SubjectContext.getSubject(knowledge.getSubjectId());
		Exercise exercise = new Exercise();
		exercise.setExerciseName(knowledge.getKnowledgeName());
		exercise.setExerciseType((long) ExerciseType.KNOWLEDGE.value);
		exercise.setRelIds(Arrays.asList(knowledge.getKnowledgeId()));
		//exercise.setDifficultyLevel(3); 取消难度系数
		//exercise.setSchoolStageId(knowledge.getSchoolStageId());
		exercise.setSubjectId(knowledge.getSubjectId());
		exercise.setSubjectName(subjectRemote.getSubjectName());
		this.fillData(exercise);
		List<Long> questionIds = this.findExerciseQuestion(exercise);
		String id = this.addNewExercise(exercise, questionIds);
		return "redirect:/auth/student/exercise/doExerciseWork.htm?id=" + id;
	}

	/**
	 * 返回同步练习列表
	 * @param model
	 * @return
	 */
	@RequestMapping("student/exercise/page/chapterTree")
	public String exerciseList(Model model) {
		model.addAttribute("flag", ExerciseType.CHAPTER.value);
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		return "/auth/student/exercise/chapterTree";
	}

	/**
	 * 知识点检测列表
	 * @param model
	 * @return
	 */
	@RequestMapping("student/exercise/page/knowledgeTree")
	public String knowledgeList(Model model) {
		model.addAttribute("flag", ExerciseType.KNOWLEDGE.value);
		model.addAttribute("schoolId", UserContext.user.getCurrentSchoolId());
		return "/auth/student/exercise/knowledgeTree";
	}

	@RequestMapping("student/exercise/saveSnapshot")
	@ResponseBody
	public JsonResult saveSnapshot(Exercise exercise,String answerJson)
			throws Exception {
		List<AnswerInfo> answerInfoList = JsonUtils.readList(answerJson, AnswerInfo.class);
		this.exerciseService.saveExercise(exercise, answerInfoList);
		return new JsonResult();
	}

	private void fillData(Exercise exercise) {
		if(StringUtils.isEmpty(exercise.getExerciseId())){
			exercise.setExerciseId(new ObjectId().toString());
		}
		User user = UserContext.user.get();
		exercise.setSourceType(0);
		exercise.setStudentId(user.getId());
		exercise.setStatus(1);
		exercise.setSchoolId(user.getCurrentSchool().getId());
		exercise.setStudentId(user.getId());
		exercise.setIsDeleted(false);
		exercise.setCreatedBy(user.getId());
		Date nowDate = new Date();
		exercise.setCreatedOn(nowDate.getTime());
		exercise.setModifiedBy(user.getId());
		exercise.setModifiedOn(nowDate.getTime());
		exercise.setSchoolId(user.getCurrentSchool().getId());
	}

	/**

	 * 描述: 提交练习信息
	 * @param exercise 练习信息
	 * @param answerJson 练习答题信息
	 * @return
	 */
	@RequestMapping("student/exercise/submitWork")
	@ResponseBody
	public JsonResult submitWork(String exerciseId, String answerJson) throws Exception {
		Exercise exercise = this.exerciseService.getExerciseById(exerciseId);
		exercise.setSubmitTime(new Date().getTime());
		exercise.setSubmitState(ExerciseCommon.EXERCISE_SUBMIT);
		List<AnswerInfo> answerInfoList = JsonUtils.readList(answerJson, AnswerInfo.class);
		
		exercise = this.exerciseService.saveExercise(exercise, answerInfoList);
		String urlParams = "source=doWork&id=" + exercise.getExerciseId();
		Long todayNum = exerciseService.getTodaySubmitCount();
		urlParams += "&todayNum=" + (todayNum + 1);
		urlParams += "&accuracy=" + ScoreUtils.toPercent(exercise.getAccuracy());

		Award award = this.exerciseService.sendDynamic(exercise);
		if (award.getSucc() && award.getHave()) {
			urlParams += "&leke=" + award.getLekeVal() + "&exp=" + award.getExpVal();
		}
		return new JsonResult().addDatas("urlParams", urlParams);
	}

	@RequestMapping("student/exercise/doWorkSuccess")
	public void doWorkSuccess(Long leke, Long exp, String source, String id, BigDecimal accuracy, Long todayNum,
			Model model) {
		model.addAttribute("lebi", leke);
		model.addAttribute("exp", exp);
		model.addAttribute("source", source);
		model.addAttribute("exerciseId", id);
		model.addAttribute("accuracy", accuracy);
		model.addAttribute("todayNum", todayNum);
		model.addAttribute("isExercise", this.exerciseService.getExerciseById(id).getStatus() == 1);
	}

}
