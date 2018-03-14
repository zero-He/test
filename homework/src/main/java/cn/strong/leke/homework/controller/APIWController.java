package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseCourseware;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.beike.model.MicrocourseFile;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.datetime.Week;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.cas.utils.TicketUtils;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.ExerciseService;
import cn.strong.leke.homework.manage.HomeworkProgressService;
import cn.strong.leke.homework.manage.HwQueCommentaryService;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.ApiHwCount;
import cn.strong.leke.homework.model.ApiHwQueCommentary;
import cn.strong.leke.homework.model.ApiMutualiCorrect;
import cn.strong.leke.homework.model.ApiParamDTO;
import cn.strong.leke.homework.model.ApiStudentHomeworkListDTO;
import cn.strong.leke.homework.model.CorrectHomeworkDtlInfo;
import cn.strong.leke.homework.model.HanWang;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlDTO;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkDtlQuery;
import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.QuestionProgress;
import cn.strong.leke.homework.model.ResRawType;
import cn.strong.leke.homework.model.ResType;
import cn.strong.leke.homework.model.StuHwByDayDTO;
import cn.strong.leke.homework.model.StuSubjRes;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.WorkRequest;
import cn.strong.leke.homework.model.mongo.Exercise;
import cn.strong.leke.homework.model.mongo.ExerciseQuestionResult;
import cn.strong.leke.homework.model.mongo.ExerciseRank;
import cn.strong.leke.homework.model.mongo.ExerciseRankUser;
import cn.strong.leke.homework.model.mongo.ExerciseReport;
import cn.strong.leke.homework.model.mongo.HwQueCommentary;
import cn.strong.leke.homework.model.query.ApiHomeworkListQuery;
import cn.strong.leke.homework.model.query.ApiKey;
import cn.strong.leke.homework.model.query.ApiParamExercise;
import cn.strong.leke.homework.model.query.ApiParamForBatchCorrect;
import cn.strong.leke.homework.model.query.ApiParamForBatchQuestionSave;
import cn.strong.leke.homework.model.query.ApiParamForHomeworkStuInfo;
import cn.strong.leke.homework.model.query.ApiParamForReviewHwStuInfo;
import cn.strong.leke.homework.model.query.ApiParamForReviewWorkSave;
import cn.strong.leke.homework.model.query.ApiParamForStuHomework;
import cn.strong.leke.homework.model.query.ApiParamForSubmitBugFix;
import cn.strong.leke.homework.service.DoubtService;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.service.MutualCorrectionService;
import cn.strong.leke.homework.util.ExerciseUtils;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.homework.util.JsonPaper;
import cn.strong.leke.homework.util.JsonQuestion;
import cn.strong.leke.homework.util.ScoreUtils;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.IDiscussionGroupRemoteService;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;
import cn.strong.leke.remote.service.microcourse.IMicrocourseRemoteService;
import cn.strong.leke.tutor.model.course.ScheduleQuery;
import cn.strong.leke.tutor.model.course.ScheduleStat;

import com.google.common.collect.Maps;

@RestController
@RequestMapping("/api/w/*")
public class APIWController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkMainService homeworkMainService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private ExerciseService exerciseService;
	@Resource
	private DoubtService doubtService;

	@Resource
	private HomeworkProgressService homeworkProgressService;
	@Resource
	private MutualCorrectionService mutualCorrectionService;
	@Resource
	private IDiscussionGroupRemoteService discussionGroupRemoteService;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	@Resource
	private IMicrocourseRemoteService microcourseRemoteService;
	@Resource
	private HwQueCommentaryService hwQueCommentaryService;

	/**
	 * 老师操作非自己布置作业的提示语
	 */
	private static String WHEN_UNOWNER_HOMEWORK_ERROR_MSG = "该作业不是老师布置的，无法完成操作";

	/**
	 * 试题信息获取接口。
	 * 调用者：APP学生端、APP老师端
	 */
	@RequestMapping("getQuestions")
	public JsonResult getQuestion(String data) {
		APILogger.invoking("getQuestions", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<QuestionDTO> questions = QuestionContext.findQuestions(param.getQuestionIds());
		return new JsonResult().addDatas("questions", JsonQuestion.mapper(questions));
	}

	/**
	 * 自主练习
	 * 开始练习时，查找相关章节或知识点题目
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("findExerciseQuestion")
	public JsonResult findExerciseQuestion(String data, String ticket) {
		APILogger.invoking("findExerciseQuestion", data);
		Long studentId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiParamExercise paramDTO = JsonUtils.fromJSON(data, ApiParamExercise.class);
		paramDTO.setUserId(studentId);
		List<Long> questionIds = this.exerciseService.findQuestionsFromResources(convertToExercise(paramDTO));
		return new JsonResult().addDatas("list", questionIds);
	}

	/**
	 * 根据试卷ID列表，获取试卷及题目信息。
	 * 调用者：课堂服务端
	 */
	@RequestMapping("getPaperInfo")
	public JsonResult getPaperInfo(String data) {
		APILogger.invoking("getPaperInfo", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);

		List<Map<String, Object>> list = param.getPaperIds().stream().map((Long paperId) -> {
			PaperDTO paperDTO = PaperContext.getPaperDTO(paperId);

			Map<String, Object> item = new HashMap<String, Object>();
			item.put("paperId", paperId);
			item.put("paperType", paperDTO.getPaperType());
			item.put("paperDetail", paperDTO.getDetail());
			if (paperDTO.getAttachment() != null) {
				item.put("fileId", paperDTO.getAttachment().getFileId());
			}

			List<Long> questionIds = new ArrayList<Long>();
			for (QuestionGroup questionGroup : paperDTO.getDetail().getGroups()) {
				for (ScoredQuestion scoredQuestion : questionGroup.getQuestions()) {
					questionIds.add(scoredQuestion.getQuestionId());
				}
			}
			List<QuestionDTO> questionList = QuestionContext.findQuestions(questionIds.toArray(new Long[0]));
			item.put("questionList", questionList);
			return item;
		}).collect(toList());
		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 自主练习
	 * 保存(暂存)练习
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveSnapshotExercise")
	public JsonResult saveSnapshotExercise(String data, String ticket) {
		APILogger.invoking("saveSnapshotExercise", data);
		Long studentId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiParamExercise paramDTO = JsonUtils.fromJSON(data, ApiParamExercise.class);
		paramDTO.setUserId(studentId);
		Exercise exercise = convertToExercise(paramDTO);
		if (StringUtils.isEmpty(exercise.getExerciseId())) {
			exercise.setExerciseId(new ObjectId().toString());
		}
		this.exerciseService.saveExercise(exercise, paramDTO.getAnswerJson());
		return new JsonResult().addDatas("exerciseId", exercise.getExerciseId());
	}

	/**
	 * 自主练习
	 * 提交练习
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("submitExercise")
	public JsonResult submitExercise(String data, String ticket) {
		JsonResult jsonResult = new JsonResult();
		APILogger.invoking("submitExercise", data);
		Long studentId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiParamExercise paramDTO = JsonUtils.fromJSON(data, ApiParamExercise.class);
		paramDTO.setUserId(studentId);
		Exercise exercise = convertToExercise(paramDTO);
		exercise.setSubmitTime(new Date().getTime());
		if (StringUtils.isEmpty(exercise.getExerciseId())) {
			exercise.setExerciseId(new ObjectId().toString());
		}
		this.exerciseService.saveExercise(exercise, paramDTO.getAnswerJson());
		//激励
		Award award = this.exerciseService.sendDynamic(exercise);
		jsonResult.addDatas("award", award);
		return jsonResult.addDatas("exerciseId", exercise.getExerciseId());
	}

	/**
	 * 自主练习
	 * 获取练习已保存的数据
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("getExerciseQuestionResult")
	public JsonResult getExerciseQuestionResult(String data, String ticket) {
		JsonResult jsonResult = new JsonResult();
		APILogger.invoking("getExerciseQuestionResult", data);
		ApiParamExercise paramDTO = JsonUtils.fromJSON(data, ApiParamExercise.class);
		Exercise exercise = exerciseService.getExerciseById(paramDTO.getExerciseId());
		List<QuestionResult> questionResults = exerciseService.paresToQuestionResult(exercise.getQuestions());
		jsonResult
				.addDatas("list", questionResults)
				.addDatas("exerciseType", exercise.getExerciseType())
				.addDatas("relIds", exercise.getRelIds())
				.addDatas("exerciseId", exercise.getExerciseId())
				.addDatas("exerciseName",
						ExerciseUtils.getExerciseTitle(exercise.getExerciseName(), exercise.getExerciseType()))
				.addDatas("subjectId", exercise.getSubjectId()).addDatas("subjectName", exercise.getSubjectName())
				.addDatas("accuracy", ScoreUtils.toPercent(exercise.getAccuracy()))
				.addDatas("submitTime", exercise.getSubmitTime()).addDatas("totalNum", exercise.getTotalNum())
				.addDatas("rightNum", exercise.getRightNum());
		return jsonResult;
	}

	@RequestMapping("getExerciseQuestionResultByKid")
	public JsonResult getExerciseQuestionResultByKid(String data, String ticket) {
		JsonResult jsonResult = new JsonResult();
		APILogger.invoking("getExerciseQuestionResultByKid", data);
		ApiParamExercise paramDTO = JsonUtils.fromJSON(data, ApiParamExercise.class);
		Exercise exercise = exerciseService.getExerciseById(paramDTO.getExerciseId());
		if (paramDTO.getKnowledgeId() != null) {
			Optional<ExerciseReport> first = exercise.getReport().stream()
					.filter(v -> v.getKnowledgeId().equals(paramDTO.getKnowledgeId())).findFirst();
			if (first.isPresent()) {
				List<Long> knowledgeQuestionIds = first.get().getQuestions();
				List<ExerciseQuestionResult> knowledgeQuestions = exercise.getQuestions().stream()
						.filter(v -> knowledgeQuestionIds.contains(v.getQuestionId())).collect(Collectors.toList());
				List<QuestionResult> questionResults = exerciseService.paresToQuestionResult(knowledgeQuestions);
				jsonResult.addDatas("totalNum", knowledgeQuestionIds.size());
				long rightNum = knowledgeQuestions.stream().filter(v -> Boolean.TRUE.equals(v.getIsCorrect())).count();
				jsonResult.addDatas("rightNum", rightNum);
				jsonResult.addDatas("list", questionResults);
				jsonResult.addDatas("knowledgeName", first.get().getKnowledgeName());
			}
		}
		return jsonResult;
	}

	//查看页面
	@RequestMapping("viewWorkExercise")
	public JsonResult viewWorkExercise(String data, String ticket) {
		JsonResult jsonResult = new JsonResult();
		APILogger.invoking("viewWorkExercise", data);
		ApiParamExercise paramDTO = JsonUtils.fromJSON(data, ApiParamExercise.class);
		Exercise exercise = this.exerciseService.getExerciseById(paramDTO.getExerciseId());
		List<QuestionResult> questionResults = exerciseService.paresToQuestionResult(exercise.getQuestions());
		return jsonResult
				.addDatas("list", questionResults)
				.addDatas("exerciseId", exercise.getExerciseId())
				.addDatas("rightNum", exercise.getRightNum())
				.addDatas("totalNum", exercise.getTotalNum())
				.addDatas("accuracy", new BigDecimal(ScoreUtils.toPercent(exercise.getAccuracy())))
				.addDatas("submitTime", exercise.getSubmitTime())
				.addDatas("exerciseName",
						ExerciseUtils.getExerciseTitle(exercise.getExerciseName(), exercise.getExerciseType()))
				.addDatas("subjectId", exercise.getSubjectId()).addDatas("subjectName", exercise.getSubjectName());
	}

	private Exercise convertToExercise(ApiParamExercise paramDTO) {
		Exercise exercise = new Exercise();
		exercise.setIsDeleted(false);
		exercise.setCreatedBy(paramDTO.getUserId());
		Long curTime = new Date().getTime();
		exercise.setCreatedOn(curTime);
		exercise.setModifiedBy(paramDTO.getUserId());
		exercise.setModifiedOn(curTime);
		exercise.setSubjectName(paramDTO.getSubjectName());
		exercise.setExerciseName(paramDTO.getExerciseName());
		exercise.setStudentId(paramDTO.getUserId());
		exercise.setStatus(1);
		exercise.setDifficultyLevel(paramDTO.getDifficultyLevel());
		exercise.setRelIds(paramDTO.getRelIds());
		exercise.setSubjectId(paramDTO.getSubjectId());
		exercise.setExerciseType(paramDTO.getExerciseType());
		UserBase userbase = UserBaseContext.getUserBaseByUserId(paramDTO.getUserId());
		Long schoolId = userbase.getRoleSchoolList().stream().filter(v -> v.getRoleId().equals(RoleCst.STUDENT))
				.findFirst().get().getSchoolId();
		exercise.setSchoolId(schoolId);
		if (StringUtils.isNotEmpty(paramDTO.getExerciseId())) {
			exercise.setExerciseId(paramDTO.getExerciseId());
		}
		//标记平板端
		exercise.setSourceType(1);
		return exercise;
	}

	/**
	 * 试卷信息获取接口。
	 * 调用者：APP学生端、APP老师端
	 */
	@RequestMapping("getPapers")
	public JsonResult getPapers(String data) {
		APILogger.invoking("getPapers", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		if (CollectionUtils.isNotEmpty(param.getPaperIds())) {
			List<PaperDTO> papers = param.getPaperIds().stream().map((Long paperId) -> {
				return PaperContext.getPaperDTO(paperId);
			}).collect(Collectors.toList());
			return new JsonResult().addDatas("papers", JsonPaper.mapper(papers));
		} else {
			HomeworkDtlInfo dtl = homeworkDtlService.getHomeworkDtlInfoById(param.getHomeworkDtlId());
			List<PaperDTO> paps = new ArrayList<>(1);
			if (dtl != null) {
				PaperDTO paper = HomeworkUtils.buildPaperDTO(dtl.getHwPaperId(), dtl.getHomeworkName(),
						dtl.getSubjectName(), dtl.getSubjectId());
				if (paper != null) {
					paps.add(paper);
				}
			}
			return new JsonResult().addDatas("papers", JsonPaper.mapper(paps));
		}
	}

	/**
	 * 作业题目及答案
	 * 场景：老师批改某个学生答题后的试卷，渲染时调用
	 * 调用者：APP学生端、APP老师端
	 */
	@RequestMapping("getHwDtlInfo")
	public JsonResult getHwDtlInfo(String data) {
		APILogger.invoking("getHwDtlInfo", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlById(param.getHomeworkDtlId());
		JsonResult json = new JsonResult();
		if (homeworkDtl != null) {
			WorkDetail workDetail = this.workDetailService.getWorkDetailByHomeworkDtlId(param.getHomeworkDtlId());
			if (workDetail != null) {
				json.addDatas("questionResultList", workDetail.getQuestions());
				json.addDatas("answerInfoList", workDetail.getAnswerInfos());
			} else {
				json.addDatas("questionResultList", Collections.EMPTY_LIST);
			}
			json.addDatas("modifiedOn", homeworkDtl.getModifiedOn());
		}

		return json;
	}

	/**
	 * 获取学生学科作业统计信息。
	 * 调用者：APP学生端
	 */
	@RequestMapping("getHwSubjectList")
	public JsonResult getHwSubjectList(String data, String ticket) {
		APILogger.invoking("getHwSubjectList", data);
		Long studentId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<StuSubjRes> list = this.homeworkDtlService
				.findStuSubjResByStudentId(studentId, param.getSubjectId(), null);
		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 学生作业列表查询。
	 * 调用者：APP学生端
	 */
	@RequestMapping("findStuHomeworks")
	public JsonResult findStuHomeworks(String data) {
		APILogger.invoking("findStuHomeworks", data);
		HomeworkDtlQuery query = JsonUtils.fromJSON(data, HomeworkDtlQuery.class);
		List<StuHwByDayDTO> list = homeworkDtlService.findStuHomeworkList(query);
		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 学生作业列表查询。
	 * 调用者：APP学生端
	 */
	@RequestMapping("findStuHomeworks_v1")
	public JsonResult findStuHomeworks_v1(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("findStuHomeworks_v1", data);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiParamForStuHomework paramDTO = JsonUtils.fromJSON(data, ApiParamForStuHomework.class);
		paramDTO.setStudentId(userId);
		//检查参数
		if (paramDTO.getSubjectId() == null) {
			json.setErr("参数：subjectId 值不能为空");
		}
		if (paramDTO.getLimit() == null) {
			paramDTO.setLimit(10);
		}
		if (paramDTO.getStart() == null) {
			paramDTO.setStart(0);
		}

		if (json.isSuccess()) {
			List<ApiStudentHomeworkListDTO> list = homeworkDtlService.findStuHomeworkList_v1(paramDTO);
			Map<String, Integer> totalMap = homeworkService.findStuHomeworkInfoTotal(userId, paramDTO.getSubjectId());
			json.addDatas("total", totalMap.get("total"));
			json.addDatas("doingTotal", totalMap.get("doingTotal"));
			json.addDatas("bugfixTotal", totalMap.get("bugfixTotal"));
			json.addDatas("list", list);
		}
		return json;
	}

	/**
	 * 查询作业信息。
	 * 调用者：APP学生端
	 */
	@RequestMapping("getStuHomework")
	public JsonResult getStuHomework(String data) {
		APILogger.invoking("getStuHomework", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		JsonResult json = new JsonResult();
		StuHwByDayDTO stuHwByDayDTO;
		if (param.getHomeworkDtlId() != null) {
			stuHwByDayDTO = homeworkDtlService.getStuHomeworkByHomeworkDtlId(param.getHomeworkDtlId());
		} else {
			stuHwByDayDTO = homeworkDtlService.getStuHomeworkByHomeworkIdAndStudentId(param.getHomeworkId(),
					param.getStudentId());
		}
		if (stuHwByDayDTO == null) {
			return new JsonResult(false, "参数错误，数据不存在");
		}
		if (stuHwByDayDTO.getExam()) {
			if ((new Date()).before(stuHwByDayDTO.getStartTime())) {
				return new JsonResult(false, "考试时间还未开始");
			}
		}
		json.addDatas("usedTime", HomeworkUtils.convertUsedTime(stuHwByDayDTO.getUsedTime()));
		json.addDatas("studentId", stuHwByDayDTO.getStudentId());
		json.addDatas("studentName", stuHwByDayDTO.getStudentName());
		json.addDatas("homeworkId", stuHwByDayDTO.getHomeworkId());
		json.addDatas("teacherName", stuHwByDayDTO.getTeacherName());
		json.addDatas("resType", stuHwByDayDTO.getResType());
		json.addDatas("paperId", stuHwByDayDTO.getPaperId());
		json.addDatas("status", stuHwByDayDTO.getStatus());
		json.addDatas("subjectId", stuHwByDayDTO.getSubjectId());
		json.addDatas("subjectName", stuHwByDayDTO.getSubjectName());
		json.addDatas("className", stuHwByDayDTO.getClassName());
		json.addDatas("courseName", stuHwByDayDTO.getCourseName());
		json.addDatas("homeworkName", stuHwByDayDTO.getHomeworkName());
		json.addDatas("homeworkType", stuHwByDayDTO.getHomeworkType());
		json.addDatas("startTime", DateUtils.formatTime(stuHwByDayDTO.getStartTime()));
		json.addDatas("closeTime", DateUtils.formatTime(stuHwByDayDTO.getCloseTime()));
		json.addDatas("subjective", stuHwByDayDTO.getSubjective());
		json.addDatas("bugFixStage", stuHwByDayDTO.getBugFixStage());
		json.addDatas("isSelfCheck", stuHwByDayDTO.getIsSelfCheck());
		json.addDatas("isOpenAnswer", stuHwByDayDTO.getIsOpenAnswer());
		json.addDatas("avgScore", stuHwByDayDTO.getAvgScore());
		if (stuHwByDayDTO.getCorrectTime() != null && stuHwByDayDTO.getStatsStatus() != null
				&& stuHwByDayDTO.getStatsStatus() == 2) {
			// 只有已批改的且老师已经分析的作业才返回
			Integer rank = this.homeworkDtlService.getRankByHomeworkIdAndScore(stuHwByDayDTO.getHomeworkId(),
					stuHwByDayDTO.getScore());
			json.addDatas("rank", rank);
		} else {
			json.addDatas("rank", null);
		}
		json.addDatas("homeworkDtlId", stuHwByDayDTO.getHomeworkDtlId());
		json.addDatas("submitStatus", stuHwByDayDTO.getSubmitStatus());
		json.addDatas("correctTime", DateUtils.formatTime(stuHwByDayDTO.getCorrectTime()));
		json.addDatas("soundFile", stuHwByDayDTO.getSoundFile());
		json.addDatas("score", stuHwByDayDTO.getScore());
		json.addDatas("isExam", stuHwByDayDTO.getExam());
		return json;
	}

	/**
	 * 保存/提交学生作业，返回批改结果。
	 * 调用者：APP学生端
	 */
	@RequestMapping("upStuHwInfo")
	public JsonResult upStuHwInfo(String data) {
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		JsonResult json = new JsonResult();

		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(param.getHomeworkDtlId());
		request.setAnswerInfoList(param.getAnswerList());
		request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_PAD);
		request.setUsedTime(param.getUsedTime());

		if (HomeworkCst.API_MOBILE_SUBMIT_TYPE_SAVE == param.getSubmitType()) {
			APILogger.invoking("upStuHwInfo", "snapshotData: homeworkDtlId =" + param.getHomeworkDtlId());
			// 保存作业
			request.setIsAutoSave(true);
			this.homeworkMainService.saveAnswerSnapshot(request);
		} else {
			//只有提交时打印日志
			APILogger.invoking("upStuHwInfo", data);
			// 提交作业
			this.homeworkMainService.saveAnswerSubmit(request);
			HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlById(param.getHomeworkDtlId());
			json.addDatas("homeworkDtlId", param.getHomeworkDtlId());
			json.addDatas("submitStatus", homeworkDtl.getSubmitStatus());
			json.addDatas("correctTime", DateUtils.format(homeworkDtl.getCorrectTime(), DateUtils.LONG_DATE_PATTERN));
			json.addDatas("soundFile", homeworkDtl.getSoundFile());
			json.addDatas("score", homeworkDtl.getScore());

			if (homeworkDtl.getCorrectTime() != null) {
				// 已经批改的直接发送激励
				HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlService.getHomeworkDtlInfoById(param
						.getHomeworkDtlId());
				Award award = this.homeworkMainService.sendIncTypeForSubmitHomework(homeworkDtlInfo);
				json.addDatas("award", award);
			}
		}
		return json;
	}

	/**
	 * 根据月份和老师标识，查询教师每日作业数量
	 * 调用者：APP老师端
	 */
	@Deprecated
	@RequestMapping("getHwByMonth")
	public JsonResult getHwByMonth(String data) {
		APILogger.departed("getHwByMonth", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<ApiHwCount> hwCountList = homeworkService.getHwByMonth(param.getTeacherId(), param.getMonth());
		return new JsonResult().addDatas("list", hwCountList);
	}

	/**
	 * 根据日期和老师标识，查询教师当日作业情况
	 * 调用者：APP老师端
	 */
	@Deprecated
	@RequestMapping("getHwByDay")
	public JsonResult getHwByDay(String data) {
		APILogger.departed("getHwByDay", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<Homework> hwList = homeworkService.getHwByDay(param.getTeacherId(), param.getDay());

		List<HashMap<String, Object>> list = hwList.stream().map((Homework hw) -> {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("homeworkId", hw.getHomeworkId());
			map.put("homeworkType", HomeworkType.valueOf(hw.getHomeworkType()).name);
			map.put("homeworkName", hw.getHomeworkName());
			map.put("status", hw.getStatus());
			map.put("paperId", hw.getPaperId());
			map.put("subjective", hw.getSubjective());
			map.put("className", hw.getClassName());
			map.put("closeTime", DateUtils.format(hw.getCloseTime(), DateUtils.LONG_DATE_PATTERN));
			map.put("totalNum", hw.getTotalNum());
			map.put("submitNum", hw.getFinishNum());
			map.put("correctNum", hw.getCorrectNum());
			return map;
		}).collect(Collectors.toList());

		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 老师作业统计接口
	 * 调用者：APP老师端
	 */
	@RequestMapping("findHomeworkCountForTeacher")
	public JsonResult findHomeworkCountForTeacher(String data, String ticket) {
		APILogger.invoking("findHomeworkCountForTeacher", data);
		ScheduleQuery query = JsonUtils.fromJSON(data, ScheduleQuery.class);
		query.setUserId(Long.parseLong(TicketUtils.getUserId(ticket)));
		query.setRoleId(RoleCst.TEACHER);
		List<ScheduleStat> list = this.homeworkService.findHomeworkScheduleByQuery(query);
		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 老师日作业列表接口
	 * 调用者：APP老师端
	 */
	@Deprecated
	@RequestMapping("findHomeworkListForTeacher")
	public JsonResult findHomeworkListForTeacher(String data, String ticket) {
		APILogger.invoking("findHomeworkListForTeacher", data);
		ScheduleQuery query = JsonUtils.fromJSON(data, ScheduleQuery.class);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		String date = DateUtils.formatDate(query.getCurrTime());
		List<Homework> hwList = homeworkService.getHwByDay(userId, date);

		List<Map<String, Object>> list = hwList.stream().map((Homework hw) -> {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("homeworkId", hw.getHomeworkId());
			map.put("homeworkType", HomeworkType.valueOf(hw.getHomeworkType()).name);
			map.put("homeworkName", hw.getHomeworkName());
			map.put("status", hw.getStatus());
			map.put("paperId", hw.getPaperId());
			map.put("subjective", hw.getSubjective());
			map.put("className", hw.getClassName());
			map.put("closeTime", hw.getCloseTime());
			map.put("totalNum", hw.getTotalNum());
			map.put("submitNum", hw.getFinishNum());
			map.put("correctNum", hw.getCorrectNum());
			//新增字段
				map.put("bugFixNum", hw.getBugFixNum());
				map.put("totalFixNum", hw.getTotalFixNum());
				map.put("reviewNum", hw.getReviewNum());
				map.put("isOpenAnswer", hw.getIsOpenAnswer());
				map.put("isSelfCheck", hw.getIsSelfCheck());
				return map;
			}).collect(Collectors.toList());

		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 老师作业列表
	 * 支持分页查询
	 * 调用者：APP老师端
	 */
	@RequestMapping("findHomeworkListForTeacher_v1")
	public JsonResult findHomeworkListForTeacher_v1(String data, String ticket) {
		APILogger.invoking("findHomeworkListForTeacher_v1", data);
		ApiHomeworkListQuery query = JsonUtils.fromJSON(data, ApiHomeworkListQuery.class);
		if (query.getLimit() == null) {
			query.setLimit(10);
		}
		query.setUserId(Long.parseLong(TicketUtils.getUserId(ticket)));
		List<Homework> hwList = homeworkService.findTeacherHomework(query);
		List<Map<String, Object>> list = hwList.stream().map((Homework hw) -> {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("homeworkId", hw.getHomeworkId());
			map.put("homeworkTypeId", hw.getHomeworkType());
			map.put("homeworkType", HomeworkType.getHomeworkTypeName(hw.getHomeworkType()));
			map.put("homeworkName", hw.getHomeworkName());
			map.put("resType", hw.getResType());
			map.put("resTypeName", ResType.getResTypeName(hw.getResType()));
			map.put("rawType", hw.getRawType());
			map.put("rawTypeName", ResRawType.resolve(hw.getRawType()).name);
			map.put("usePhase", hw.getUsePhase());
			map.put("status", hw.getStatus());
			map.put("paperId", hw.getPaperId());
			map.put("paperType", hw.getPaperType());
			map.put("subjective", hw.getSubjective());
			map.put("classId", hw.getClassId());
			map.put("className", hw.getClassName());
			map.put("closeTime", hw.getCloseTime());
			map.put("totalNum", hw.getTotalNum());
			map.put("submitNum", hw.getFinishNum());
			map.put("correctNum", hw.getCorrectNum());
			map.put("bugFixNum", hw.getBugFixNum());
			map.put("totalFixNum", hw.getTotalFixNum());
			map.put("reviewNum", hw.getReviewNum());
			if (!hw.getIsOpenAnswer() && hw.getOpenAnswerTime() != null) {
				map.put("isOpenAnswer", hw.getOpenAnswerTime().getTime() <= new Date().getTime());
			} else {
				map.put("isOpenAnswer", hw.getIsOpenAnswer());
			}
			map.put("openAnswerTime", hw.getOpenAnswerTime());
			map.put("isSelfCheck", hw.getIsSelfCheck());
			//新增字段
				map.put("avgScore", hw.getAvgScore());
				map.put("avgScoreRate", hw.getAvgScoreRate());
				return map;
			}).collect(Collectors.toList());
		Map<String, Long> totalsMap = this.homeworkService.findTeacherHomeworkInfoTotal(query.getUserId());

		return new JsonResult().addDatas("total", totalsMap.get("total"))
				.addDatas("correctTotal", totalsMap.get("correctTotal"))
				.addDatas("reviewTotal", totalsMap.get("reviewTotal")).addDatas("list", list);
	}

	/**
	 * 根据作业标识，查询每个学生的作业上交情况
	 * 调用者：APP老师端
	 */
	@RequestMapping("getHwDtl")
	public JsonResult getHwDtl(String data) {
		APILogger.invoking("getHwDtl", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<HomeworkDtlDTO> hwDtlList = homeworkDtlService.getHwDtl(param.getHomeworkId());

		List<HashMap<String, Object>> list = hwDtlList.stream().map((HomeworkDtlDTO dtl) -> {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("homeworkDtlId", dtl.getHomeworkDtlId());
			map.put("studentId", dtl.getStudentId());
			map.put("studentName", dtl.getStudentName());
			map.put("homeworkName", dtl.getHomeworkName());
			map.put("score", dtl.getScore());
			map.put("isSubmit", dtl.getSubmitStatus());
			map.put("isCorrect", dtl.getCorrectTime() == null ? 0 : 1);
			map.put("submitSource", dtl.getSubmitSource());
			map.put("correctSource", dtl.getCorrectSource());
			map.put("soundFile", dtl.getSoundFile());
			return map;
		}).collect(Collectors.toList());

		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 批改结果回传
	 * 场景：老师批改某个学生整份试卷后，提交调用
	 * 调用者：APP老师端
	 */
	@RequestMapping("upCorrectResult")
	public JsonResult upCorrectResult(String data, String ticket) {
		APILogger.invoking("upCorrectResult", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(param.getHomeworkDtlId());
		request.setUserId(userId);
		request.setCommentary(param.getSoundFile());
		request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_PAD);
		request.setQuestionResultList(param.getQuestionResultList());
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkMainService.saveCorrectSubmit(request);
		Homework homework = homeworkService.getHomeworkById(homeworkDtlInfo.getHomeworkId());

		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setDynamicType(DynamicTypes.HW_CORRECT_WORK);
		dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
		dynamicInfo.setUserId(homework.getTeacherId());
		dynamicInfo.setUserName(homework.getTeacherName());
		dynamicInfo.setRoleId(RoleCst.TEACHER);
		dynamicInfo.setSchoolId(homework.getSchoolId());
		Award award = DynamicHelper.publish(dynamicInfo);
		JsonResult json = new JsonResult();
		json.addDatas("award", award);
		return json;
	}

	/**
	 * 作业统计信息查询。
	 * @param data 
	 * @return
	 */
	@RequestMapping("getStatsByHomeworkId")
	public JsonResult getStatsByHomeworkId(String data) {
		APILogger.invoking("getStatsByHomeworkId", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);

		JsonResult json = new JsonResult();
		Homework homework = this.homeworkService.getHomeworkById(param.getHomeworkId());
		json.addDatas("homeworkId", homework.getHomeworkId());
		json.addDatas("paperId", homework.getPaperId());
		json.addDatas("finishNum", homework.getFinishNum());
		json.addDatas("delayNum", homework.getDelayNum());
		json.addDatas("correctNum", homework.getCorrectNum());
		json.addDatas("totalNum", homework.getTotalNum());
		json.addDatas("maxScore", homework.getMaxScore());
		json.addDatas("avgScore", homework.getAvgScore());
		json.addDatas("minScore", homework.getMinScore());
		json.addDatas("startTime", homework.getStartTime());
		json.addDatas("colseTime", homework.getCloseTime());

		List<HomeworkDtlDTO> homeworkDtlDTOList = homeworkDtlService.findHomeworkDtlList(param.getHomeworkId(), null);
		List<Map<String, Object>> details = homeworkDtlDTOList.stream().map(homeworkDtl -> {
			Map<String, Object> dtl = new HashMap<>();
			dtl.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
			dtl.put("studentId", homeworkDtl.getStudentId());
			dtl.put("studentName", homeworkDtl.getStudentName());
			dtl.put("score", homeworkDtl.getScore());
			dtl.put("submitStatus", homeworkDtl.getSubmitStatus());
			dtl.put("submitTime", homeworkDtl.getSubmitTime());
			dtl.put("correctTime", homeworkDtl.getCorrectTime());
			dtl.put("topicList", Collections.EMPTY_LIST);
			return dtl;
		}).collect(Collectors.toList());
		json.addDatas("details", details);

		return json;
	}

	/**
	 * 保存汉王脚本。
	 * @param data 
	 * @return
	 */
	@RequestMapping("saveHanWang")
	public JsonResult saveHanWang(String data) {
		APILogger.invoking("saveHanWang", data);
		HanWang hanWang = JsonUtils.fromJSON(data, HanWang.class);
		this.homeworkDtlService.saveHanWang(hanWang);
		return new JsonResult().addDatas("id", hanWang.getId());
	}

	/**
	 * 获取汉王脚本。
	 * @param data
	 * @return
	 */
	@RequestMapping("getHanWang")
	public JsonResult getHanWang(String data) {
		APILogger.invoking("getHanWang", data);
		HanWang hanWang = JsonUtils.fromJSON(data, HanWang.class);
		hanWang = this.homeworkDtlService.getHanWangById(hanWang.getId());
		return new JsonResult().addDatas("data", hanWang.getData());
	}

	/** 将作业公布答案
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveOpenAnwser")
	public JsonResult saveOpenAnwser(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("saveOpenAnwser", data);

		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long teacherId = Long.parseLong(TicketUtils.getUserId(ticket));
		//验证参数
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId不能为空");
		}
		if (json.isSuccess()) {
			Long homeworkId = paramDTO.getHomeworkId();
			if (isOwerHomework(homeworkId, teacherId)) {
				homeworkService.saveOpenAnswer(paramDTO.getHomeworkId(), teacherId);
			}
		}
		return json;
	}

	/**将作业设置为自行校对
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveSelfCheck")
	public JsonResult saveSelfCheck(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("saveSelfCheck", data);

		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long teacherId = Long.parseLong(TicketUtils.getUserId(ticket));
		//验证参数
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId不能为空");
		}
		if (json.isSuccess()) {

			if (isOwerHomework(paramDTO.getHomeworkId(), teacherId)) {
				homeworkService.saveSelfCheck(paramDTO.getHomeworkId(), teacherId);
			} else {
				json.setErr(WHEN_UNOWNER_HOMEWORK_ERROR_MSG);
			}
		}
		return json;
	}

	/**
	 * 将作业作废
	 * @param data
	 * @return
	 */
	@RequestMapping("saveHomeworkInvalid")
	public JsonResult saveHomeworkInvalid(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("saveHomeworkInvalid", data);

		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long teacherId = Long.parseLong(TicketUtils.getUserId(ticket));
		//验证参数
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId不能为空");
		}
		if (json.isSuccess()) {
			if (isOwerHomework(paramDTO.getHomeworkId(), teacherId)) {
				homeworkService.updateHomeworkInvalid(paramDTO.getHomeworkId(), teacherId);
			} else {
				json.setErr(WHEN_UNOWNER_HOMEWORK_ERROR_MSG);
			}
		}
		return json;
	}

	/**
	 * 老师：全部通过待复批的作业
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveReviewSubmitWithBatch")
	public JsonResult saveReviewSubmitWithBatch(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("saveReviewSubmitWithBatch", data);

		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId 不能为空");
		}
		Long teacherId = Long.parseLong(TicketUtils.getUserId(ticket));
		if (json.isSuccess()) {
			if (isOwerHomework(paramDTO.getHomeworkId(), teacherId)) {
				WorkRequest workRequest = new WorkRequest();
				workRequest.setHomeworkId(paramDTO.getHomeworkId());
				workRequest.setUserId(teacherId);
				homeworkMainService.saveReviewSubmitWithBatch(workRequest);
			} else {
				json.setErr(WHEN_UNOWNER_HOMEWORK_ERROR_MSG);
			}
		}

		return json;
	}

	/**
	 * api ，批改作业列表按人（查找学生作业列表）
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("getHomeworkStuInfo")
	private JsonResult getHomeworkStuInfo(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("getHomeworkStuInfo", data);

		ApiParamForHomeworkStuInfo paramDTO = JsonUtils.fromJSON(data, ApiParamForHomeworkStuInfo.class);
		Long homeworkId = paramDTO.getHomeworkId();
		if (homeworkId == null) {
			json.setErr("参数 homeworkId 不能为空");
		}
		if (json.isSuccess()) {
			List<HomeworkDtl> homeworkDtls = homeworkDtlService.findHomeworkDtlListByHomeworkId(homeworkId);
			//排序
			/* 0 | null. 默认排序: 待批改, 已批改, 未提交
			 * 1. 按成绩排序降序
			 * 2. 按提交时间升序 
			 * */
			Collections.sort(homeworkDtls, new Comparator<HomeworkDtl>() {
				@Override
				public int compare(HomeworkDtl o1, HomeworkDtl o2) {
					Integer sort = paramDTO.getSort() == null ? 0 : paramDTO.getSort();
					if (sort.equals(2)) {
						//按提交时间升序 
						Long max = new Date().getTime();
						Long o1_v = o1.getSubmitTime() != null ? o1.getSubmitTime().getTime() : max;
						Long o2_v = o2.getSubmitTime() != null ? o2.getSubmitTime().getTime() : max;
						return o1_v.compareTo(o2_v);
					} else if (sort.equals(1)) {
						//按成绩排序降序
						BigDecimal o2_v = o2.getScore() != null ? o2.getScore() : new BigDecimal("-1");
						BigDecimal o1_v = o1.getScore() != null ? o1.getScore() : new BigDecimal("-1");
						return o2_v.compareTo(o1_v);
					} else {
						//默认排序: 待批改升序, 已批改降序, 未提交
						Integer o1Group = getGroup(o1);
						Integer o2Group = getGroup(o2);
						if (o1Group != o2Group) {
							return o1Group.compareTo(o2Group);
						} else {
							Long max = new Date().getTime();
							if (o1Group.equals(1)) {
								Long o1_submit = o1.getSubmitTime() != null ? o1.getSubmitTime().getTime() : max;
								Long o2_submit = o2.getSubmitTime() != null ? o2.getSubmitTime().getTime() : max;
								return o1_submit.compareTo(o2_submit);
							} else if (o1Group.equals(2)) {
								Long o1_correct = o1.getCorrectTime() != null ? o1.getCorrectTime().getTime() : max;
								Long o2_correct = o2.getCorrectTime() != null ? o2.getCorrectTime().getTime() : max;
								return o2_correct.compareTo(o1_correct);
							} else {
								return 0;
							}
						}
					}
				}

				private Integer getGroup(HomeworkDtl dtl) {
					if (dtl.getSubmitTime() == null && dtl.getCorrectTime() == null) {
						return 3;//未提交
					} else if (dtl.getSubmitTime() != null && dtl.getCorrectTime() == null) {
						return 1;//未批改
					} else {
						return 2;//已批改
					}
				}
			});

			Homework hw = homeworkService.getHomeworkById(homeworkId);
			Integer questionNum = 0;
			if (hw.getPaperId() != null && hw.getResType() == HomeworkCst.HOMEWORK_RES_PAPER) {
				questionNum = PaperContext.getPaperDTO(hw.getPaperId()).getDetail().getQuestionNum();
			}
			json.addDatas("questionNum", questionNum);
			json.addDatas("closeTime", hw.getCloseTime().getTime());
			json.addDatas("isExam", hw.getExam());
			if (hw.getExam()) {
				/*1.未开始、2.已结束、3.正在考试*/
				String examStatus = "1";
				if (new Date().getTime() < hw.getStartTime().getTime()) {
					examStatus = "1";
				} else if (hw.getCloseTime().getTime() < new Date().getTime()) {
					examStatus = "2";
				} else {
					examStatus = "3";
				}
				json.addDatas("examStatus", examStatus);
			}

			List<Map<String, Object>> list = homeworkDtls
					.stream()
					.map(s -> {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("homeworkDtlId", s.getHomeworkDtlId());
						map.put("homeworkId", s.getHomeworkId());
						map.put("studentId", s.getStudentId());
						map.put("studentName", s.getStudentName());
						map.put("score", s.getScore());
						map.put("scoreRate", s.getScoreRate());
						map.put("submitTime", s.getSubmitTime());
						map.put("correctCount", s.getCorrectCount());
						map.put("usedTime", HomeworkUtils.convertUsedTime(s.getUsedTime()));
						map.put("learnTimes", s.getBugFixCount());
						map.put("firstLearnTime", s.getStartTime() == null ? 0 : s.getStartTime().getTime());
						map.put("recentLearnTime", s.getBugFixTime() == null ? 0 : s.getBugFixTime().getTime());
						map.put("correctUserId", s.getCorrectUserId());
						map.put("correctUserName", s.getCorrectUserId() == null ? null : UserBaseContext
								.getUserBaseByUserId(s.getCorrectUserId()).getUserName());
						return map;

					}).collect(Collectors.toList());
			json.addDatas("list", list);
		}
		return json;
	}

	/**
	 *  api 作业批改情况（按人）
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("getBatchQuestions")
	public JsonResult getBatchQuestions(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("getBatchQuestions", data);
		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数： homeworkId 不能为空");
		}
		if (json.isSuccess()) {
			Homework hw = homeworkService.getHomeworkById(paramDTO.getHomeworkId());
			PaperDTO paperDto = PaperContext.getPaperDTO(hw.getPaperId());
			//			Integer paperType = paperDto.getPaperType();
			json.addDatas("totalNum", hw.getFinishNum());//总数
			List<QuestionGroup> questionGroups = paperDto.getDetail().getGroups();
			List<QuestionProgress> questionProgresses = workDetailService.findBatchProgressByHomeworkId(paramDTO
					.getHomeworkId());
			List<Map<String, Object>> list = questionGroups.stream().map(groupt -> {
				Map<String, Object> groupMap = new HashMap<String, Object>();
				groupMap.put("ord", groupt.getOrd());
				groupMap.put("grouptitle", groupt.getGroupTitle());

				groupMap.put("questions", groupt.getQuestions().stream().map(q -> {
					Map<String, Object> questionMap = new HashMap<String, Object>();
					questionMap.put("questionId", q.getQuestionId());
					questionMap.put("ord", q.getOrd());
					questionMap.put("subjective", q.getSubjective());
					QuestionProgress item = questionProgresses.stream().filter(fq -> {
						return fq.getQuestionId().equals(q.getQuestionId());
					}).findFirst().get();
					Integer currNum = 0;
					if (item != null) {
						currNum = item.getCorrectNum();
					}
					questionMap.put("corrNum", currNum);
					return questionMap;
				}).collect(Collectors.toList()));
				return groupMap;
			}).collect(Collectors.toList());
			json.addDatas("list", list);
		}

		return json;
	}

	/**
	 *	api	 作业复批学生列表情况
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("getReviewHwStuInfo")
	public JsonResult getReviewHwStuInfo(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("getReviewHwStuInfo", data);
		ApiParamForReviewHwStuInfo paramDTO = JsonUtils.fromJSON(data, ApiParamForReviewHwStuInfo.class);
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数: homeworkId 不能为空");
		}

		if (json.isSuccess()) {
			List<HomeworkDtl> stuHomeworks = homeworkDtlService.findHomeworkDtlListByHomeworkId(paramDTO
					.getHomeworkId());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (stuHomeworks != null && stuHomeworks.size() > 0) {
				//排序
				Collections.sort(stuHomeworks, new Comparator<HomeworkDtl>() {
					@Override
					public int compare(HomeworkDtl o1, HomeworkDtl o2) {
						Integer sort = paramDTO.getSort() == null ? 0 : paramDTO.getSort();
						if (sort == 1) {
							// 成绩按倒序排
							BigDecimal o2_v = o2.getScore() != null ? o2.getScore() : new BigDecimal("-1");
							BigDecimal o1_v = o1.getScore() != null ? o1.getScore() : new BigDecimal("-1");
							return o2_v.compareTo(o1_v);
						} else {
							//按订正时间 升序
							Long max = new Date().getTime();
							Long o1_v = o1.getBugFixTime() != null ? o1.getBugFixTime().getTime() : max;
							Long o2_v = o2.getBugFixTime() != null ? o2.getBugFixTime().getTime() : max;
							return o1_v.compareTo(o2_v);
						}
					}

				});
				//组装返回参数
				list = stuHomeworks.stream().map(hwdtl -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("studentId", hwdtl.getStudentId());
					map.put("studentName", hwdtl.getStudentName());
					map.put("bugFixCount", hwdtl.getBugFixCount());
					map.put("bugFixTime", hwdtl.getBugFixTime() != null ? hwdtl.getBugFixTime().getTime() : null);
					map.put("bugFixStage", hwdtl.getBugFixStage());
					map.put("homeworkDtlId", hwdtl.getHomeworkDtlId());
					map.put("score", hwdtl.getScore());

					return map;

				}).collect(Collectors.toList());
			}
			json.addDatas("list", list);
		}
		return json;
	}

	/**
	 * api	批量批改，获取当前题目的下一个学生提交的作业
	 * @param data
	 * @param ticket
	 * @return
	 */
	@Deprecated
	@RequestMapping("getNextStuCorrectQuestion")
	public JsonResult getNextStuCorrectQuestion(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("getNextStuCorrectQuestion", data);
		ApiParamForBatchCorrect paramDTO = JsonUtils.fromJSON(data, ApiParamForBatchCorrect.class);
		//检查参数
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId 不能为空");
		} else if (paramDTO.getQuestionId() == null) {
			json.setErr("参数：questionId 不能为空");
		} else if (paramDTO.getCurDate() == null) {
			paramDTO.setCurDate(new Date().getTime());
		}

		if (json.isSuccess()) {
			Integer doneNum = workDetailService.getBatchCorrectCount(paramDTO.getHomeworkId(),
					paramDTO.getQuestionId(), new Date(paramDTO.getCurDate()));
			json.addDatas("doneNum", doneNum);
			WorkDetail workDetail = workDetailService.getBatchCorrectNext(paramDTO.getHomeworkId(),
					paramDTO.getQuestionId(), new Date(paramDTO.getCurDate()));
			if (workDetail != null) {
				json.addDatas("studentId", workDetail.getStudentId());
				json.addDatas("homeworkDtlId", workDetail.getHomeworkDtlId());
				json.addDatas("studentName", workDetail.getStudentName());
				json.addDatas("questionResult", workDetail.getQuestions().get(0));
			}
		}
		return json;

	}

	/**
	 * api 查找某个作业中已提交的学生作业Id
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("getBatchSubmitHwDtls")
	public JsonResult getBatchSubmitHwDtls(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("getBatchSubmitHwDtls", data);
		ApiParamForBatchCorrect paramDTO = JsonUtils.fromJSON(data, ApiParamForBatchCorrect.class);
		//检查参数
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId 不能为空");
		} else if (paramDTO.getCurDate() == null) {
			json.setErr("参数：curDate 不能为空");
		}
		List<Long> homeworkDtlIds = workDetailService.getBatchSubmitHwDtls(paramDTO.getHomeworkId(),
				new Date(paramDTO.getCurDate()));
		return new JsonResult().addDatas("homeworkDtlIds", homeworkDtlIds);
	}

	/**
	 * api	批量批改，获取学生某道题目答题信息
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("getBatchSingleQuestion")
	public JsonResult getBatchSingleQuestion(String data, String ticket) {
		JsonResult json = new JsonResult();
		APILogger.invoking("getBatchSingleQuestion", data);
		ApiParamForBatchCorrect paramDTO = JsonUtils.fromJSON(data, ApiParamForBatchCorrect.class);
		//检查参数
		if (paramDTO.getHomeworkId() == null) {
			json.setErr("参数：homeworkId 不能为空");
		} else if (paramDTO.getQuestionId() == null) {
			json.setErr("参数：questionId 不能为空");
		} else if (paramDTO.getCurDate() == null) {
			paramDTO.setCurDate(new Date().getTime());
		}

		if (json.isSuccess()) {
			Integer doneNum = workDetailService.getBatchCorrectCount(paramDTO.getHomeworkId(),
					paramDTO.getQuestionId(), new Date(paramDTO.getCurDate()));
			json.addDatas("doneNum", doneNum);
			WorkDetail workDetail = getStuWorkDetail(paramDTO.getHomeworkId(), paramDTO.getHomeworkDtlId(),
					paramDTO.getQuestionId(), paramDTO.getCurDate());
			if (workDetail != null) {
				json.addDatas("studentId", workDetail.getStudentId());
				json.addDatas("homeworkDtlId", workDetail.getHomeworkDtlId());
				json.addDatas("studentName", workDetail.getStudentName());
				json.addDatas("questionResult", workDetail.getQuestions().get(0));
			}
		}
		return json;

	}

	private WorkDetail getStuWorkDetail(Long homeworkId, Long homeworkDtlId, Long questionId, Long date) {
		Date cutDate = new Date(date);
		if (homeworkDtlId == null) {
			return workDetailService.getBatchCorrectNext(homeworkId, questionId, cutDate);
		} else {
			return workDetailService.getWorkDetailByBatchCorrect(homeworkDtlId, questionId);
		}
	}

	/**
	 * api	批量批改，提交某个学生的某试题的批改信息
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveBatchQuestion")
	public JsonResult saveBatchQuestion(String data, String ticket) {
		APILogger.invoking("saveBatchQuestion", data);
		JsonResult json = new JsonResult();
		ApiParamForBatchQuestionSave paramDTO = JsonUtils.fromJSON(data, ApiParamForBatchQuestionSave.class);
		if (paramDTO.getHomeworkDtlId() == null) {
			json.setErr("参数 homeworkDtlId 不能为空");
		}

		if (json.isSuccess()) {
			this.homeworkMainService.saveCorrectSnapshotWithBatch(paramDTO.getHomeworkDtlId(),
					paramDTO.getQuestionResult());
		}
		return json;
	}

	/**
	 * api	复批作业提交
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveReviewWork")
	public JsonResult saveReviewWork(String data, String ticket) {
		APILogger.invoking("saveReviewWork", data);
		JsonResult json = new JsonResult();
		ApiParamForReviewWorkSave paramDTO = JsonUtils.fromJSON(data, ApiParamForReviewWorkSave.class);
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(paramDTO.getHomeworkDtlId());
		request.setCommentary(paramDTO.getCommentary());
		request.setQuestionResultList(paramDTO.getCorrectJson());
		request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_PAD);
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkMainService.saveReviewSubmit(request);
		// 激励发送
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setDynamicType(DynamicTypes.HW_REVIEW_WORK);
		dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
		dynamicInfo.setUserId(userBase.getUserId());
		dynamicInfo.setUserName(userBase.getUserName());
		dynamicInfo.setRoleId(RoleCst.TEACHER);
		dynamicInfo.setSchoolId(homeworkDtlInfo.getSchoolId());
		Award award = DynamicHelper.publish(dynamicInfo);
		if (award.getSucc() && award.getHave()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("leke", award.getLekeVal());
			map.put("exp", award.getExpVal());
			json.addDatas("tips", map);
		}
		return json;
	}

	/**
	 * api	学生订正作业提交
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("submitBugFix")
	public JsonResult submitBugFix(String data, String ticket) {
		APILogger.invoking("submitBugFix", data);
		JsonResult json = new JsonResult();
		ApiParamForSubmitBugFix paramDTO = JsonUtils.fromJSON(data, ApiParamForSubmitBugFix.class);
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(paramDTO.getHomeworkDtlId());
		request.setAnswerInfoList(paramDTO.getAnswerJson());
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkMainService.saveBugFixSubmit(request);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
		DynamicInfo dynamicInfo = new DynamicInfo();
		//dynamicInfo.setTypeId(IncentiveTypes.STUDENT.HW_HOMEWORK_BUGFIX);
		dynamicInfo.setDynamicType(DynamicTypes.HW_HOMEWORK_BUGFIX);
		dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
		dynamicInfo.setUserId(userBase.getUserId());
		dynamicInfo.setUserName(userBase.getUserName());
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(homeworkDtlInfo.getSchoolId());
		Map<String, Object> params = new HashMap<>();
		params.put("homeworkDtlId", paramDTO.getHomeworkDtlId());
		dynamicInfo.setParams(params);
		json.addDatas("bugFixStage", homeworkDtlInfo.getBugFixStage());
		Award award = DynamicHelper.publish(dynamicInfo);
		if (award.getSucc() && award.getHave()) {
			Map<String, Object> map = new HashMap<String, Object>();
			//			map.put("type", dynamicInfo.getTypeId());
			map.put("leke", award.getLekeVal());
			map.put("exp", award.getExpVal());
			json.addDatas("tips", map);
		}

		return json;
	}

	/**
	 * api	[场景化作业]获取正在做某作业的学生信息
	 * @param data
	 * @return
	 */
	@RequestMapping("getHomeworkProgress")
	public JsonResult getHomeworkProgress(String data) {
		APILogger.invoking("getHomeworkProgress", data);
		JsonResult json = new JsonResult();
		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		json.addDatas("progress", homeworkProgressService.getProgressInfoByHomeworkId(paramDTO.getHomeworkId()));
		return json;
	}

	/**
	 * api	[场景化作业] 正在做作业的学生进行记录
	 * 该接口取消
	 * @param data
	 * @param ticket
	 * @return
	 */
	@Deprecated
	@RequestMapping("saveHeartbeat")
	public JsonResult saveHeartbeat(String data, String ticket) {
		APILogger.invoking("saveHeartbeat", data);
		/*Long studentId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiParamDTO paramDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		homeworkProgressService.saveHeartbeat(paramDTO.getHomeworkId(), studentId);*/
		return new JsonResult();
	}

	/*-------------------------------private method-------------------------*/

	/**
	 * api	校验作业是否是该老师的 
	 * @param homeworkId
	 * @param teacherId
	 * @return
	 */
	private Boolean isOwerHomework(Long homeworkId, Long teacherId) {
		Homework hw = homeworkService.getHomeworkById(homeworkId);
		return hw != null && hw.getTeacherId().equals(teacherId);
	}

	/**
	 * 根据单课标识和老师标识，获取作业列表。
	 * 调用者：课堂服务端
	 */
	@RequestMapping("findPaperWorkByName")
	public JsonResult findPaperWorkByName(String data) {
		APILogger.invoking("findPaperWorkByName", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Validation.notNull(param.getCourseSingleId(), "课堂ID不能为空");
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(param.getCourseSingleId());
		Validation.notNull(lesson, "课堂ID无效");
		Page page = new Page(param.getCurPage(), param.getPageSize());
		List<Homework> list = this.homeworkService.findPaperWorkByName(lesson.getClassId(), param.getTeacherId(),
				param.getHomeworkName(), page);
		List<Map<String, Object>> maps = list.stream().map(v -> {
			Map<String, Object> item = new HashMap<>();
			item.put("homeworkId", v.getHomeworkId());
			item.put("homeworkName", v.getHomeworkName());
			item.put("homeworkType", v.getHomeworkType());
			item.put("closeTime", v.getCloseTime());
			item.put("className", v.getClassName());
			item.put("resType", v.getResType());
			item.put("beikeGuid", v.getBeikeGuid());
			item.put("paperId", v.getPaperId());
			item.put("usePhase", v.getUsePhase());
			item.put("finishNum", v.getFinishNum());
			item.put("correctNum", v.getCorrectNum());
			item.put("totalNum", v.getTotalNum());
			return item;
		}).collect(toList());
		return new JsonResult().addDatas("page", page).addDatas("list", maps);
	}

	/**
	 * 恢复作废作业
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/recover")
	public JsonResult recover(String data, String ticket) {
		JsonResult result = new JsonResult();
		long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiKey paramDTO = JsonUtils.fromJSON(data, ApiKey.class);
		Boolean b = this.homeworkService.recoverHomework(Arrays.asList(paramDTO.getId()), userId);
		if (b) {
			result.setMessage("该试卷不能恢复！！");
			result.setSuccess(false);
			return result;
		}
		return result;
	}

	/**
	 * 永久删除作业
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/delete")
	public JsonResult del(String data, String ticket) {
		long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiKey paramDTO = JsonUtils.fromJSON(data, ApiKey.class);
		this.homeworkService.delHomework(Arrays.asList(paramDTO.getId()), userId);
		return new JsonResult();
	}

	/**
	 * 获取老师今日需要解答的乐答总数
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/getResolveDoubtTotal")
	public JsonResult getResolveDoubtTotal(String ticket) {
		APILogger.invoking("getResolveDoubtTotal", ticket);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		Integer total = doubtService.getResolveDoubtTotal(userId);
		return new JsonResult().addDatas("total", total);
	}

	/**
	 * 获取学生待完成的作业数(不含寒暑假作业)
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/getToFinishHomeworkTotal")
	public JsonResult getToFinishHomeworkTotal(String ticket) {
		APILogger.invoking("getToFinishHomeworkTotal", ticket);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		Integer total = homeworkDtlService.getStuToFinishHwTotal(userId);
		return new JsonResult().addDatas("total", total);
	}

	/**
	 * 获取学生自主练习 上周排名
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/getExerciseRank")
	public JsonResult getExerciseRank(String ticket) {
		APILogger.invoking("getExerciseRank", ticket);
		JsonResult json = new JsonResult();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		Week wk = new Week(calendar.getTime());
		calendar.setTime(DateUtils.addDays(wk.getStartDate(), -1));
		Integer week = calendar.get(Calendar.WEEK_OF_YEAR);
		Integer year = calendar.get(Calendar.YEAR);
		ExerciseRank rank = exerciseService.findWeekRank(year, week);
		//查询当前用户是否在该排名中
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		Optional<ExerciseRankUser> userRank = rank.getList().stream().filter(v -> v.getId().equals(userId)).findFirst();
		if (userRank.isPresent()) {
			json.addDatas("rank", userRank.get().getRank());
		} else {
			json.addDatas("rank", null);
		}
		return json;
	}

	/**
	 * 学生作业的答案及批改信息
	 * 场景：学生课前作业在课堂中只做错题，获取预习作业的答案。
	 * 调用者：课堂随堂作业
	 */
	@RequestMapping("getHwDtlInfoForClass")
	public JsonResult getHwDtlInfoForClass(String data) {
		APILogger.invoking("getHwDtlInfoForClass", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long studentId = param.getStudentId();
		Long homeworkId = param.getHomeworkId();
		Homework homework = this.homeworkService.getHomeworkById(homeworkId);
		homeworkId = this.homeworkService.getHomeworkIdByBeikeGuid(homework.getBeikeGuid()); // 定位最新作业ID
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlByHomeworkIdAndStudentId(homeworkId, studentId);
		List<QuestionResult> questionResultList = Collections.emptyList();
		if (homeworkDtl != null) {
			WorkDetail workDetail = this.workDetailService.getWorkDetailByHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
			if (workDetail != null) {
				questionResultList = workDetail.getQuestions();
			}
		}
		JsonResult json = new JsonResult();
		json.addDatas("questionResultList", questionResultList);
		return json;
	}

	/**
	 * 根据点击的某一份作业获取课件作业相应数据。
	 */
	@RequestMapping("getHwFileInfo")
	public JsonResult getHwFileInfo(String data) {
		APILogger.invoking("getHwFileInfo", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		HomeworkDtl homeworkDtl = homeworkDtlService.getHomeworkDtlById(param.getHomeworkDtlId());
		Validation.isFalse(homeworkDtl == null, "数据异常");
		Homework homework = homeworkService.getHomeworkById(homeworkDtl.getHomeworkId());
		Integer position = this.workDetailService.getPosition(homeworkDtl.getHomeworkDtlId());
		String fileId = null;
		Map<String, Object> file = Maps.newHashMap();
		if (homework.getResType() == ResType.COURSEWARE.value) {
			CoursewareDTO courseware = CoursewareContext.getCourseware(homework.getPaperId());
			fileId = courseware.getFileId();
			file.put("coursewareId", courseware.getCoursewareId());
			file.put("name", courseware.getName());
			file.put("type", courseware.getType());
			file.put("schoolStageName", courseware.getSchoolStageName());
			file.put("subjectName", courseware.getSubjectName());

		} else {
			MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(homework.getPaperId());
			file.put("microcourseId", microcourse.getMicrocourseId());
			file.put("microcourseType", microcourse.getMicrocourseType());
			file.put("coursewareId", microcourse.getCoursewareId());
			file.put("recordId", microcourse.getRecordId());
			file.put("name", microcourse.getMicrocourseName());
			file.put("schoolStageName", microcourse.getSchoolStageName());
			file.put("subjectName", microcourse.getSubjectName());
			if (microcourse.getMicrocourseType() == 2) {
				MicrocourseFile mf = microcourse.getMicrocourseFile();
				fileId = mf.getFileId();
				file.put("type", mf.getType());
			} else if (microcourse.getMicrocourseType() == 3) {
				MicrocourseCourseware microCourseware = microcourseRemoteService.getMicrocourseCourseware(microcourse
						.getMicrocourseId());
				if (microCourseware != null) {
					fileId = microCourseware.getFileId();
				}
			}
		}
		this.homeworkMainService.saveStartWork(param.getHomeworkDtlId(), HomeworkCst.HOMEWORK_DATA_SOURCE_PAD);
		JsonResult json = new JsonResult();
		json.addDatas("fileId", fileId);
		json.addDatas("position", position);
		json.addDatas("subjectId", homework.getSubjectId());
		json.addDatas("subjectName", homework.getSubjectName());
		json.addDatas("file", file);
		return json;
	}

	/**
	 * 当学生退出课件和微课时，通知服务器退出时作业的数据。
	 */
	@RequestMapping("upHwFileInfo")
	public JsonResult upHwFileInfo(String data) {
		APILogger.invoking("upHwFileInfo", data);
		WorkRequest request = JsonUtils.fromJSON(data, WorkRequest.class);
		request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_PAD);
		this.homeworkMainService.savePreviewSubmit(request);
		return new JsonResult();
	}

	/**
	 * 在每份作业的操作区“更多”按钮点击后新增催交作业功能。老师点击催交作业功能后，未提交该份作业的学生会收到相应消息提醒
	 */
	@RequestMapping("urgeHomework")
	public JsonResult urgeHomework(String data) {
		APILogger.invoking("getHwDtlInfoForClass", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long homeworkId = param.getHomeworkId();
		this.homeworkMainService.cuijiaoHomework(homeworkId);
		return new JsonResult();
	}

	@RequestMapping("validateCuijiao")
	public JsonResult validateCuijiao(String data) {
		APILogger.invoking("validateCuijiao", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		JsonResult json = new JsonResult();
		Homework homework = this.homeworkService.getHomeworkById(param.getHomeworkId());
		List<HomeworkDtl> homeworkDtls = this.homeworkDtlService.findHomeworkDtlListByHomeworkId(param.getHomeworkId());
		Boolean past = System.currentTimeMillis() - homework.getCloseTime().getTime() > 0;
		if (past) {
			json.addDatas("isAllow", false);
			json.addDatas("tips", "作业已经超过截止时间~");
		} else if (CollectionUtils.isEmpty(homeworkDtls)) {
			json.addDatas("isAllow", false);
			json.addDatas("tips", "该作业没有学生~");
		} else {
			homeworkDtls = homeworkDtls.stream().filter(v -> v.getSubmitTime() == null).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(homeworkDtls)) {
				json.addDatas("isAllow", false);
				json.addDatas("tips", "所有学生已提交~");
			} else {
				json.addDatas("isAllow", true);
				String time = HomeworkUtils.fmtDiffTime(homework.getCloseTime().getTime());
				json.addDatas("tips", "作业距离截止时间还有" + time + "，还有" + homeworkDtls.size() + "学生未提交作业，确认催交作业吗？");
			}
		}
		return json;
	}

	/**
	 * 查找指定互批的学生群组
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("findClassGroups")
	public JsonResult findStuGroup(String data, String ticket) {
		APILogger.invoking("findClassGroups", data);
		JsonResult jsonResult = new JsonResult();
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiMutualiCorrect param = JsonUtils.fromJSON(data, ApiMutualiCorrect.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<HomeworkDtl> homeworkDtls = homeworkDtlService.findHomeworkDtlListByHomeworkId(param.getHomeworkId());
		homeworkDtls.removeIf(v -> v.getStudentId().equals(param.getExcludeUserId()));
		List<Long> groupLeaders = discussionGroupRemoteService.findGroupLeaer(param.getClassId(), userId);
		homeworkDtls.forEach(v -> {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("userId", v.getStudentId());
			item.put("userName", v.getStudentName());
			item.put("isLeader", groupLeaders.contains(v.getStudentId()));
			list.add(item);
		});
		return jsonResult.addDatas("list", list);
	}

	/**
	 * 作业互批
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("saveMututalCorrect")
	public JsonResult saveMututalCorrect(String data, String ticket) {
		APILogger.invoking("saveMututalCorrect", data);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiMutualiCorrect param = JsonUtils.fromJSON(data, ApiMutualiCorrect.class);

		JsonResult jsonResult = new JsonResult();
		try {
			if (CollectionUtils.isNotEmpty(param.getUserIds())) {
				mutualCorrectionService.saveCorrectUsers(param.getHomeworkId(), userId, param.getUserIds());
			} else {
				mutualCorrectionService.saveCorrectEachOther(param.getHomeworkId(), userId);
			}
		} catch (Exception ex) {
			jsonResult.setErr(ex.getMessage());
		}
		return jsonResult;
	}

	@RequestMapping("changeCorrectUser")
	public JsonResult changeCorrectUser(String data, String ticket) {
		APILogger.invoking("changeCorrectUser", data);
		JsonResult jsonResult = new JsonResult();
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiMutualiCorrect param = JsonUtils.fromJSON(data, ApiMutualiCorrect.class);
		try {
			mutualCorrectionService.changeCorrectUserWithTx(param.getHomeworkDtlId(), userId, param.getCorrectUserId());
		} catch (Exception e) {
			jsonResult.setErr(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 作业互批操作前验证
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("validateMutualCorrect")
	public JsonResult validateCorrect(String data, String ticket) {
		APILogger.invoking("validateMutualCorrect", data);
		JsonResult jsonResult = new JsonResult();
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		ApiMutualiCorrect param = JsonUtils.fromJSON(data, ApiMutualiCorrect.class);
		try {
			mutualCorrectionService.validateMutualCorrection(param.getHomeworkId(), userId);
		} catch (Exception e) {
			jsonResult.setErr(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 获取批改人待批改的作业信息
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("findCorrectHomeworkList")
	public JsonResult findCorrectHomeworkList(String data, String ticket) {
		APILogger.invoking("findCorrectHomeworkList", data);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		List<CorrectHomeworkDtlInfo> datas = mutualCorrectionService.findHomeworkDtlByCorrectUser(userId);
		if (datas.size() > 0) {
			List<Long> homeworkIds = datas.stream().map(CorrectHomeworkDtlInfo::getHomeworkId).collect(toList());
			List<CorrectHomeworkDtlInfo> submits = mutualCorrectionService.findSubmitByHwIds(userId, homeworkIds);
			Set<Long> submitHwIds = submits.stream().map(CorrectHomeworkDtlInfo::getHomeworkId).collect(toSet());
			for (int i = 0; i < datas.size(); i++) {
				CorrectHomeworkDtlInfo v = datas.get(i);
				v.setStudentName("学生" + (i + 1));
				v.setIsFinishedSelf(submitHwIds.contains(v.getHomeworkId()));
			}
		}
		return new JsonResult().addDatas("list", datas);
	}

	@RequestMapping("getHomework")
	public JsonResult getHomework(String data, String ticket) {
		APILogger.invoking("getHomework", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Homework hw = this.homeworkService.getHomeworkById(param.getHomeworkId());
		Map<String, Object> map = new HashMap<>();
		map.put("homeworkId", param.getHomeworkId());
		map.put("resType", hw.getResType());
		return new JsonResult().addDatas("homework", map);
	}

	/*
	 * 课堂作业是否处理完成
	 * */
	@RequestMapping("isFinishHomeworkData")
	public JsonResult isFinishHomeworkData(String data) {
		APILogger.invoking("isFinishHomeworkData", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		return new JsonResult().addDatas("isFinish",
				homeworkMainService.getHomeworkDataTotal(param.getHomeworkId(), param.getVersion()) == 0);
	}
	
	@RequestMapping("isExistQuestion")
	public JsonResult isExistQuestion(String data, String ticket){
		APILogger.invoking("isExistQuestion", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		Boolean isExist = workDetailService.isExistQuestion(param.getQuestionId(), userId);
		return new JsonResult().addDatas("isExist", isExist);
	}

	/**
	 * api : 微课批注推送
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("shareCommentary")
	public JsonResult shareCommentary(String data, String ticket) {
		APILogger.invoking("shareCommentary", data);
		ApiHwQueCommentary param = JsonUtils.fromJSON(data, ApiHwQueCommentary.class);
		HwQueCommentary commentary = new HwQueCommentary();
		long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		commentary.setHomeworkId(param.getHomeworkId());
		commentary.setQuestionId(param.getQuestionId());
		commentary.setResId(param.getResId());
		commentary.setResName(param.getResName());
		commentary.setType(param.getType());
		commentary.setIncludeUserIds(param.getIncludeUserIds());
		commentary.setCreatedBy(userId);
		commentary.setCreatedOn(new Date());
		hwQueCommentaryService.saveCommentary(commentary);
		return new JsonResult();
	}

	/**
	 * api : 撤销 微课批注推送
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("cancelShareCommentary")
	public JsonResult cancelShareCommentary(String data, String ticket) {
		APILogger.invoking("cancelShareCommentary", data);
		ApiHwQueCommentary param = JsonUtils.fromJSON(data, ApiHwQueCommentary.class);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		hwQueCommentaryService.removeCommentary(param.getHomeworkId(), param.getQuestionId(), param.getResId(), userId,
				param.getExcludeUserId());
		return new JsonResult();
	}

	/**
	 * api : 移除 单个学生微课批注推送
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("removeSingleCommentary")
	public JsonResult removeSingleCommentary(String data, String ticket) {
		APILogger.invoking("removeSingleCommentary", data);
		ApiHwQueCommentary param = JsonUtils.fromJSON(data, ApiHwQueCommentary.class);
		Long userId = Long.parseLong(TicketUtils.getUserId(ticket));
		hwQueCommentaryService.removeCommentaryForStu(param.getHomeworkId(), param.getQuestionId(), param.getResId(),
				param.getExcludeUserId(), userId);
		return new JsonResult();
	}
	
	/**api : 获取作业某个题目的批注信息
	 * @param data
	 * @param ticket
	 * @return
	 */
	@RequestMapping("findSignleQueComment")
	public JsonResult findSignleQueComment(String data, String ticket) {
		APILogger.invoking("findSignleQueComment", data);
		ApiHwQueCommentary param = JsonUtils.fromJSON(data, ApiHwQueCommentary.class);
		List<HwQueCommentary> commentaries = hwQueCommentaryService.findCommentaries(param.getHomeworkId(),
				param.getQuestionId(), param.getStudentId());
		List<Map<String, Object>> list = commentaries
				.stream()
				.map(v -> {
					Map<String, Object> item = new HashMap<>();
					item.put("type", v.getType());
					item.put("resId", v.getResId());
					item.put("resName", v.getResName());
					return item;
				}).collect(toList());
		return new JsonResult().addDatas("list", list);
	}
}
