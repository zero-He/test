package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.manage.FileDescService;
import cn.strong.leke.homework.manage.HomeworkProgressService;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.AnswerInfo;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.WorkRequest;
import cn.strong.leke.homework.model.mobile.FileDesc;
import cn.strong.leke.homework.model.mobile.WorkModel;
import cn.strong.leke.homework.model.mobile.WorkModel.WorkInfo;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.homework.util.JsonPaper;
import cn.strong.leke.homework.util.JsonPaper.ScoredQuestion;
import cn.strong.leke.homework.util.JsonQuestion;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.question.AnswerResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.question.QuestionTemplates;
import cn.strong.leke.model.user.User;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/auth/m/*")
public class MWorkPersonController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private HomeworkMainService homeworkMainService;
	@Resource
	private HomeworkProgressService homeworkProgressService;
	@Resource
	private FileDescService fileDescService;

	private static final Logger logger = LoggerFactory.getLogger(MWorkPersonController.class);
	
	@RequestMapping("person/homework/viewwork")
	public String viewwork(Long homeworkDtlId, Model model) {
		// 作业信息
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlById(homeworkDtlId);
		Validation.isPageNotFound(homeworkDtl == null);
		Homework homework = this.homeworkService.getHomeworkById(homeworkDtl.getHomeworkId());
		User user = UserContext.user.get();
		Validation.isPageNotFound(RoleCst.STUDENT.equals(user.getCurrentRole().getId())
				&& !user.getId().equals(homeworkDtl.getStudentId()));

		model.addAttribute("homeworkName", homework.getHomeworkName());
		if (HomeworkCst.HOMEWORK_RES_PAPER == homework.getResType()) {
			WorkModel viewWorkModel = this.buildWorkModel(homework, homeworkDtl);
			model.addAttribute("_ts", System.currentTimeMillis());
			model.addAttribute("viewWorkModel", JsonUtils.toJSON(viewWorkModel));
			return "/auth/m/homework/person/person-viewwork";
		}

		Integer position = this.workDetailService.getPosition(homeworkDtlId);
		Map<String, Object> workModel = Maps.newHashMap();
		workModel.put("homeworkId", homework.getHomeworkId());
		workModel.put("homeworkDtlId", homeworkDtlId);
		workModel.put("position", position);
		if (HomeworkCst.HOMEWORK_RES_COURSEWARE == homework.getResType()) {
			CoursewareDTO courseware = CoursewareContext.getCourseware(homework.getPaperId());
			FileDesc fileDesc = this.fileDescService.convertToFileDesc(courseware);
			workModel.put("file", fileDesc);
		} else {
			MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(homework.getPaperId());
			if (microcourse.getMicrocourseType() == 1 ) {
				throw new ValidateException("录制微课暂不支持播放");
			}
			workModel.put("isNewVod", microcourse.getMicrocourseType() == 3);
			workModel.put("micId", microcourse.getMicrocourseId());
			workModel.put("micName", microcourse.getMicrocourseName());
			FileDesc fileDesc = this.fileDescService.convertToFileDesc(microcourse);
			workModel.put("file", fileDesc);
		}
		if (RoleCst.STUDENT.equals(user.getCurrentRole().getId())) {
			this.homeworkMainService.saveStartWork(homeworkDtlId, HomeworkCst.HOMEWORK_DATA_SOURCE_MOBILE);
		}
		model.addAttribute("workModel", JsonUtils.toJSON(workModel));
		return "/auth/m/homework/person/student-viewwork1";
	}

	private WorkModel buildWorkModel(Homework homework, HomeworkDtl homeworkDtl) {
		WorkModel viewWorkModel = new WorkModel();
		// 试卷
		JsonPaper paper = this.parsePaperDTO(homework, homeworkDtl);
		viewWorkModel.setPaper(paper);
		// 试题
		viewWorkModel.setQues(this.parseQuestionMap(viewWorkModel.getPaper()));
		// 答题
		WorkDetail workDetail = this.workDetailService.getWorkDetailByHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
		if (workDetail != null) {
			Map<Long, QuestionResult> results = workDetail.getQuestions().stream()
					.collect(toMap(v -> v.getQuestionId(), v -> v));
			viewWorkModel.setResults(results);
			this.rectifyQuestionResults(viewWorkModel);
		}
		// 第一次做题时，更新开始时间
		if (homeworkDtl.getStartTime() == null) {
			homeworkDtl.setStartTime(new Date());
			this.homeworkDtlService.updateHomeworkDtl(homeworkDtl);
			this.sendDoWorkMessage(homeworkDtl.getHomeworkDtlId(), homework.getHomeworkName());
		}
		WorkInfo workInfo = new WorkInfo();
		workInfo.setHomeworkId(homeworkDtl.getHomeworkId());
		workInfo.setHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
		workInfo.setStudentId(homeworkDtl.getStudentId());
		workInfo.setStudentName(homeworkDtl.getStudentName());
		workInfo.setUsedTime(homeworkDtl.getUsedTime());
		workInfo.setSubmitTime(homeworkDtl.getSubmitTime());
		workInfo.setCorrectTime(homeworkDtl.getCorrectTime());
		workInfo.setScore(homeworkDtl.getScore());
		workInfo.setScoreRate(homeworkDtl.getScoreRate());
		workInfo.setAvgScore(homework.getAvgScore());
		workInfo.setComments(homeworkDtl.getSoundFile());
		workInfo.setSelfCheck(homework.getIsSelfCheck());
		workInfo.setOpenAnswer(homework.getIsOpenAnswer());
		viewWorkModel.setWorkInfo(workInfo);
		return viewWorkModel;
	}

	// 纠正用户数据结构
	private void rectifyQuestionResults(WorkModel viewWorkModel) {
		JsonPaper paper = viewWorkModel.getPaper();
		Map<Long, JsonQuestion> queMap = viewWorkModel.getQues();
		Map<Long, QuestionResult> resultMap = viewWorkModel.getResults();
		paper.getDetail().getGroups().forEach(group -> {
			group.getQuestions().forEach(scored -> {
				JsonQuestion que = queMap.get(scored.getQuestionId());
				QuestionResult result = resultMap.get(scored.getQuestionId());
				this.rectifyQuestionResult(que, result);
			});
		});
	}
	
	private void rectifyQuestionResult(JsonQuestion que, QuestionResult result) {
		if (!que.getHasSub()) { // 没子题
			this.rectifyQuestionResult2(que, result);
			return;
		}
		Map<Long, QuestionResult> subresultMap = result.getSubs().stream()
				.collect(toMap(QuestionResult::getQuestionId, v -> v));
		que.getSubs().forEach(subque -> {
			QuestionResult subresult = subresultMap.get(subque.getQuestionId());
			this.rectifyQuestionResult(subque, subresult);
		});
	}

	private void rectifyQuestionResult2(JsonQuestion que, QuestionResult result) {
		if (result == null) {
			return;
		}
		if (QuestionTemplates.SINGLE_CHOICE.equals(que.getTemplate())
				|| QuestionTemplates.MULTI_CHOICE.equals(que.getTemplate())
				|| QuestionTemplates.JUDGEMENT.equals(que.getTemplate())) {
			Set<String> keys = que.getAnswers().stream().map(v -> String.valueOf(v.getAnswerId())).collect(toSet());
			result.getAnswerResults().removeIf(v -> !keys.contains(v.getMyAnswer()));
			que.setIsModified(true);
		} else if (QuestionTemplates.FILL_BLANK.equals(que.getTemplate())
				|| QuestionTemplates.PUNCTUATE.equals(que.getTemplate())) {
			int fillSize = que.getAnswers().size();
			int userSize = result.getAnswerResults().size();
			if (fillSize != userSize) {
				List<AnswerResult> answerResults = IntStream.range(0, fillSize).mapToObj(i -> {
					AnswerResult answerResult = new AnswerResult();
					answerResult.setMyAnswer("");
					return answerResult;
				}).collect(toList());
				if (result.getTotalIsRight() != null) {
					answerResults.forEach(answerResult -> {
						answerResult.setIsRight(result.getTotalIsRight());
						answerResult.setResultScore(new BigDecimal(0));
					});
				}
				result.setAnswerResults(answerResults);
				que.setIsModified(true);
			}
		}
	}

	// 用户动态消息发送
	private void sendDoWorkMessage(Long homeworkDtlId, String title) {
		User user = UserContext.user.get();
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(user.getId());
		dynamicInfo.setUserName(user.getUserName());
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(user.getCurrentSchool().getId());
		// 动态
		dynamicInfo.setDynamicType(DynamicTypes.HW_HOMEWORK_START);
		dynamicInfo.setTitle(title);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("homeworkDtlId", homeworkDtlId);
		dynamicInfo.setParams(params);
		DynamicHelper.publish(dynamicInfo);
	}

	private JsonPaper parsePaperDTO(Homework homework, HomeworkDtl homeworkDtl) {
		PaperDTO paper;
		if (homework.getPaperId() != null) {
			paper = PaperContext.getPaperDTO(homework.getPaperId());
		} else {
			paper = HomeworkUtils.buildPaperDTO(homeworkDtl.getPaperId(), homework.getHomeworkName(),
					homework.getSubjectName(), homework.getSubjectId());
		}
		return JsonPaper.mapper(paper);
	}

	private Map<Long, JsonQuestion> parseQuestionMap(JsonPaper paper) {
		List<Long> qids = paper.getDetail().getGroups().stream().flatMap(group -> {
			return group.getQuestions().stream().map(ScoredQuestion::getQuestionId);
		}).collect(toList());
		List<QuestionDTO> questionList = QuestionContext.findQuestions(qids);
		return questionList.stream().collect(toMap(QuestionDTO::getQuestionId, JsonQuestion::mapper));
	}
	

	@RequestMapping("student/homework/redirectDoWork")
	public String redirectDoWork(Long homeworkId, Model model) {
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlByHomeworkIdAndStudentId(homeworkId,
				UserContext.user.getUserId());
		return "redirect:/auth/m/student/homework/dowork.htm?homeworkDtlId=" + homeworkDtl.getHomeworkDtlId();
	}

	/**
	 * 做作业页面
	 * @param homeworkDtlId
	 * @param model
	 * @return
	 */
	@RequestMapping("student/homework/dowork")
	public String dowork(Long homeworkDtlId, Model model) {
		// 作业信息
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlById(homeworkDtlId);
		if (homeworkDtl.getSubmitTime() != null) {
			return this.viewwork(homeworkDtlId, model);
		}
		Validation.isPageNotFound(homeworkDtl == null);
		Homework homework = this.homeworkService.getHomeworkById(homeworkDtl.getHomeworkId());
		User user = UserContext.user.get();
		Validation.isPageNotFound(RoleCst.STUDENT.equals(user.getCurrentRole().getId())
				&& !user.getId().equals(homeworkDtl.getStudentId()));

		model.addAttribute("homeworkName", homework.getHomeworkName());
		if (HomeworkCst.HOMEWORK_RES_PAPER == homework.getResType()) {
			WorkModel viewWorkModel = this.buildWorkModel(homework, homeworkDtl);
			model.addAttribute("_ts", System.currentTimeMillis());
			model.addAttribute("viewWorkModel", JsonUtils.toJSON(viewWorkModel));
			return "/auth/m/homework/person/student-dowork";
		}

		Integer position = this.workDetailService.getPosition(homeworkDtlId);
		Map<String, Object> workModel = Maps.newHashMap();
		workModel.put("homeworkId", homework.getHomeworkId());
		workModel.put("homeworkDtlId", homeworkDtlId);
		workModel.put("position", position);
		if (HomeworkCst.HOMEWORK_RES_COURSEWARE == homework.getResType()) {
			CoursewareDTO courseware = CoursewareContext.getCourseware(homework.getPaperId());
			workModel.put("fileId", courseware.getFileId());
		} else {
			MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(homework.getPaperId());
			Validation.isFalse(microcourse.getMicrocourseType() == 1 , "录制微课暂不支持播放");
			workModel.put("isNewVod", microcourse.getMicrocourseType() == 3);
			workModel.put("micId", microcourse.getMicrocourseId());
			workModel.put("micName", microcourse.getMicrocourseName());
			if(microcourse.getMicrocourseType() != 1 && microcourse.getMicrocourseType() != 3){
				workModel.put("fileId", microcourse.getMicrocourseFile().getFileId());
			}
		}
		model.addAttribute("workModel", JsonUtils.toJSON(workModel));
		if (RoleCst.STUDENT.equals(user.getCurrentRole().getId())) {
			this.homeworkMainService.saveStartWork(homeworkDtlId, HomeworkCst.HOMEWORK_DATA_SOURCE_MOBILE);
		}
		return "/auth/m/homework/person/student-dowork1";
	}

	/**
	 * 保存学生答题信息。
	 * @param homeworkDtlId 学生作业标识
	 * @param answerJson    学生答题信息JSON
	 * @return
	 */
	@ResponseBody
	@RequestMapping("student/homework/saveWork2")
	public Object saveWork2(Long homeworkDtlId, Integer position, Integer duration) {
		this.workDetailService.updatePosition(homeworkDtlId, position, duration, UserContext.user.getUserId());
		return new JsonResult();
	}

	/**
	 * 学生完成课件或者微课的学习
	 * @param homeworkId
	 * @param homeworkDtlId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/student/homework/submitWork2")
	public JsonResult submitWork2(WorkRequest request) {
		request.setUserId(UserContext.user.getUserId());
		request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);
		this.homeworkMainService.savePreviewSubmit(request);
		return new JsonResult();
	}

	/**
	 * 作业心跳
	 * @param homeworkId
	 * @param answerJson
	 * @return
	 */
	@ResponseBody
	@RequestMapping("student/homework/heartbeat")
	public JsonResult heartbeat(Long homeworkId, Long homeworkDtlId, String answerJson) {
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(homeworkDtlId);
		request.setAnswerInfoList(JsonUtils.readList(answerJson, AnswerInfo.class));
		request.setIsAutoSave(true);
		this.homeworkMainService.saveAnswerSnapshot(request);
		return new JsonResult();
	}

	/**
	 * 保存学生答题信息。
	 * @param homeworkDtlId 学生作业标识
	 * @param answerJson 学生答题信息JSON
	 * @return
	 */
	@ResponseBody
	@RequestMapping("student/homework/save")
	public Object saveWork(Long homeworkDtlId, String answerJson) {
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(homeworkDtlId);
		request.setAnswerInfoList(JsonUtils.readList(answerJson, AnswerInfo.class));
		request.setIsAutoSave(false);
		this.homeworkMainService.saveAnswerSnapshot(request);
		return new JsonResult();
	}

	/**
	 * 保存并提交学生答题信息。
	 * @param homeworkDtlId 学生作业标识
	 * @param answerJson 学生答题信息JSON
	 * @return
	 */
	@ResponseBody
	@RequestMapping("student/homework/submit")
	public Object submitWork(Long homeworkDtlId, String answerJson) {
		logger.info("submitHomework from mobile: homeworkDtlId = " + homeworkDtlId + ",answerJson=" + answerJson);
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(homeworkDtlId);
		request.setAnswerInfoList(JsonUtils.readList(answerJson, AnswerInfo.class));
		this.homeworkMainService.saveAnswerSubmit(request);
		// 响应信息获取
		JsonResult json = new JsonResult();
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlService.getHomeworkDtlInfoById(homeworkDtlId);
		Homework homework = homeworkService.getHomeworkById(homeworkDtlInfo.getHomeworkId());
		json.addDatas("finishNum", homework.getFinishNum() == 0 ? 1 : homework.getFinishNum());
		json.addDatas("homeworkType", HomeworkType.valueOf(homework.getHomeworkType()).name);
		fillUsedTime(homeworkDtlInfo);
		json.addDatas("isCorrect", homeworkDtlInfo.getCorrectTime() != null);
		json.addDatas("score", homeworkDtlInfo.getScore());
		json.addDatas("usedTime", homeworkDtlInfo.getUsedTime());
		if (homeworkDtlInfo.getCorrectTime() != null) {
			Award award = this.homeworkMainService.sendIncTypeForSubmitHomework(homeworkDtlInfo);
			if (award.getSucc() && award.getHave()) {
				json.addDatas("expVal", award.getExpVal());
				json.addDatas("lekeVal", award.getLekeVal());
			}
		}
		return json;
	}

	private void fillUsedTime(HomeworkDtlInfo homeworkDtlInfo) {
		Integer usedTime = homeworkDtlInfo.getUsedTime();
		if (usedTime == null || usedTime == 0) {
			usedTime = homeworkProgressService.getStudentUsedTime(homeworkDtlInfo.getHomeworkId(),
					homeworkDtlInfo.getStudentId());
		}
		homeworkDtlInfo.setUsedTime(HomeworkUtils.convertUsedTime(usedTime));
	}
}
