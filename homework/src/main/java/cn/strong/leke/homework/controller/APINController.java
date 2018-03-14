package cn.strong.leke.homework.controller;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.strong.leke.beike.model.Courseware;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.beike.model.Types;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.ApiParamDTO;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkData;
import cn.strong.leke.homework.model.StudentReviewInfo;
import cn.strong.leke.homework.model.struct.SimplePaper;
import cn.strong.leke.homework.model.struct.SimplePaperMapper;
import cn.strong.leke.homework.model.struct.SimplePaperQueScored;
import cn.strong.leke.homework.model.struct.SimpleQuestionMapper;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.PhaseUtils;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/n/*")
public class APINController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkMainService homeworkMainService;
	@Resource
	private MessageSender onlinetestSender;
	@Resource
	private MessageSender homeworkMutualCorrectSender;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	
	/**
	 * 根据试卷ID列表获取试卷及题目信息列表。
	 * 注意：该数据结果为简化结构，在APIW中还有一个同名接口，它是给Flash课堂调用的
	 * 调用者：离线化课堂代理，临时接口
	 */
	@RequestMapping("getSimplePaperByHomeworkId")
	public JsonResult getSimplePaper(String data) {
		APILogger.invoking("getSimplePaperByHomeworkId", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Validation.notNull(param.getHomeworkId(), "请求参数不能为空");
		Homework homework = this.homeworkService.getHomeworkById(param.getHomeworkId());
		Validation.notNull(homework, "作业信息不存在");
		PaperDTO paperDTO = PaperContext.getPaperDTO(homework.getPaperId());
		SimplePaper paper = SimplePaperMapper.mapper(paperDTO);
		List<Long> questionIds = paper.getDetail().getGroups().stream()
				.flatMap(v -> v.getQuestions().stream().map(SimplePaperQueScored::getQuestionId)).collect(toList());
		paper.setQuestions(SimpleQuestionMapper.mapper(QuestionContext.findQuestions(questionIds)));
		return new JsonResult().addDatas("paper", paper);
	}

	/**
	 * 上传随堂作业数据
	 * 调用者：课堂服务端
	 */
	@RequestMapping("upHwInfo")
	public JsonResult upHwInfo(String data) {
		APILogger.invoking("upHwInfo", data);
		HomeworkData homeworkData = JsonUtils.fromJSON(data, HomeworkData.class);
		homeworkData.setIsDeleted(false);
		homeworkData.setAnswerData(StringUtils.replaceUtf8mb4(homeworkData.getAnswerData()));
		this.homeworkMainService.saveOnlineAnswerTempData(homeworkData);
		this.onlinetestSender.send(homeworkData.getDataId());
		return new JsonResult();
	}

	/**
	 * 上传课堂互批数据
	 * 调用者：课堂服务端
	 * @param data
	 * @return
	 */
	@RequestMapping("upMutualCorrectInfo")
	public JsonResult upMutualCorrectInfo(String data) {
		APILogger.invoking("upMutualCorrectInfo", data);
		HomeworkData homeworkData = JsonUtils.fromJSON(data, HomeworkData.class);
		homeworkData.setIsDeleted(false);
		homeworkData.setAnswerData(StringUtils.replaceUtf8mb4(homeworkData.getAnswerData()));
		this.homeworkMainService.saveOnlineAnswerTempData(homeworkData);
		this.homeworkMutualCorrectSender.send(homeworkData.getDataId());
		return new JsonResult();
	}

	/**
	 * 根据单课标识和老师标识，获取指定课上一节课的作业列表。
	 * 调用者：课堂服务端
	 */
	@RequestMapping("getPrevHwByLessonId")
	@JsonView(Homework.MobileView.class)
	public JsonResult getPrevHwByLessonId(String data) {
		APILogger.invoking("getPrevHwByLessonId", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		Long prevLessonId = lessonRemoteService.getPreLessonId(param.getLessonId());
		List<Homework> list = homeworkService.findAfterHwByLessonId(param.getTeacherId(), prevLessonId);
		return new JsonResult().addDatas("list", list);
	}
	
	/**
	 * 根据单课标识和老师标识，获取作业列表。
	 * 调用者：课堂服务端
	 */
	@RequestMapping("getHwByLessonId")
	@JsonView(Homework.MobileView.class)
	public JsonResult getHwByLessonId(String data) {
		APILogger.invoking("getHwByLessonId", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<Homework> list = new ArrayList<>();
		if (!param.getIsSpecial()) {
			//获取的是讲解作业（课堂id已经是上一节课的）
			list = homeworkService.findAfterHwByLessonId(param.getTeacherId(), param.getLessonId());
		} else {
			list = homeworkService.findLessonWorkByLessonId(param.getTeacherId(), param.getLessonId(),
					HomeworkCst.HOMEWORK_RES_PAPER);

			// 只保存原始作业
			list = list.stream().collect(groupingBy(Homework::getBeikeGuid)).values().stream().map(v -> {
				v.sort(Comparator.comparingLong(Homework::getHomeworkId));
				return v.get(0);
			}).collect(toList());
			
			//移除非课中资源
			list.removeIf(v -> !PhaseUtils.validatePhase(v.getComboPhase(), PhaseUtils.parsePhase(PhaseUtils.PHASE_2)));
		}
		return new JsonResult().addDatas("list", list);
	}

	/**
	 * 根据单课标识和老师标识，获取课件列表。
	 * 调用者：课堂服务端
	 */
	@RequestMapping("getCwByLessonId")
	public JsonResult getCwByLessonId(String data) {
		APILogger.invoking("getCwByLessonId", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<Homework> homeworks = homeworkService.findLessonWorkByLessonId(param.getTeacherId(), param.getLessonId(),
				HomeworkCst.HOMEWORK_RES_COURSEWARE);
		//移除非课中资源
		homeworks.removeIf(v -> !PhaseUtils.validatePhase(v.getComboPhase(), PhaseUtils.parsePhase(PhaseUtils.PHASE_2)));
		List<Map<String, Object>> list = homeworks.stream().map(Homework::getPaperId)
				.map(CoursewareContext::getCourseware).map(this::convertCourseware).collect(toList());
		return new JsonResult().addDatas("list", list);
	}
	
	private Map<String, Object> convertCourseware(Courseware courseware) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coursewareVersion_id", courseware.getCoursewareId());
		map.put("fileId", courseware.getFileId());
		map.put("name", courseware.getName());
		if (Types.isMedia(courseware.getType())) {
			map.put("multimedia", 1);
		} else {
			map.put("multimedia", 0);
		}
		return map;
	}

	/**
	 * 根据单课标识和老师标识，获取微课列表。
	 * 调用者：课堂服务端
	 */
	@RequestMapping("getMcByLessonId")
	public JsonResult getMcByLessonId(String data) {
		APILogger.invoking("getMcByLessonId", data);
		ApiParamDTO param = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<Homework> homeworks = homeworkService.findLessonWorkByLessonId(param.getTeacherId(), param.getLessonId(),
				HomeworkCst.HOMEWORK_RES_MICROCOURSE);
		//移除非课中资源
		homeworks.removeIf(v->!PhaseUtils.validatePhase( v.getComboPhase(), PhaseUtils.parsePhase(PhaseUtils.PHASE_2)));
		List<Map<String, Object>> list = homeworks.stream().map(Homework::getPaperId)
				.map(MicrocourseContext::getMicrocourse).map(this::convertMicrocourseFile).filter(v -> v != null)
				.collect(toList());
		return new JsonResult().addDatas("list", list);
	}
	
	private Map<String, Object> convertMicrocourseFile(MicrocourseDTO microcourseDTO) {
		if (microcourseDTO.getMicrocourseFile() == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coursewareVersion_id", microcourseDTO.getMicrocourseId());
		map.put("fileId", microcourseDTO.getMicrocourseFile().getFileId());
		map.put("name", microcourseDTO.getMicrocourseName());
		map.put("changeType", microcourseDTO.getMicrocourseFile().getType());
//		map.put("totalPage", microcourseDTO.getMicrocourseFile().getPageCount());
		if (Types.isMedia(microcourseDTO.getMicrocourseFile().getType())) {
			map.put("multimedia", 1);
		} else {
			map.put("multimedia", 0);
		}
		return map;
	}

	/**
	 * 获取课堂金榜的预习情况（完成、得分）
	 * @param data
	 * @return
	 */
	@RequestMapping("findReviewInfos")
	public JsonResult findReviewInfos(String data) {
		APILogger.invoking("findReviewInfos", data);
		ApiParamDTO apiParamDTO = JsonUtils.fromJSON(data, ApiParamDTO.class);
		List<StudentReviewInfo> reviewInfos = this.homeworkDtlService
				.findStudentReviewInfosByLessonId(apiParamDTO.getCourseSingleId());
		return new JsonResult().addDatas("reviewInfos", reviewInfos);
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
			item.put("attachment", paperDTO.getAttachment());

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

}
