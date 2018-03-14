package cn.strong.leke.homework.controller.work;

import static java.util.stream.Collectors.toMap;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.beike.model.Types;
import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.beike.CoursewareContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.user.UserContext;
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
import cn.strong.leke.homework.model.WebWorkModel;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.WorkRequest;
import cn.strong.leke.homework.model.mobile.FileDesc;
import cn.strong.leke.homework.model.mongo.ProgressInfo;
import cn.strong.leke.homework.service.HomeworkAssignService;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkService;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.tags.answer.ResDataModel;
import cn.strong.leke.tags.question.prepare.PrepareHandler;
import cn.strong.leke.tags.question.prepare.PrepareHandlerAdapter;

/**
 * 学生做作业。
 * @author  andy
 */
@Controller
@RequestMapping("/auth/student/homework/*")
public class AnswerController {

	protected static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private HomeworkMainService homeworkMainService;
	@Resource
	private HomeworkAssignService homeworkAssignService;
	@Resource
	private HomeworkProgressService homeworkProgressService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private FileDescService fileDescService;

	/**
	 * 做题页面。<br>
	 * 如果学生是第一次做题，更新学生作业的开始时间；<br>
	 * 如果学生不是第一次做题，显示之前保存的答题信息；<br>
	 * 提交后的作业不可以再做题。
	 * @param homeworkDtlId 学生作业标识
	 * @param model
	 */
	@RequestMapping("doWork")
	public String doWork(Long homeworkDtlId, Model model, HttpServletRequest httpServletRequest) {
		User user = UserContext.user.get();
		model.addAttribute("userId", user.getId());
		HomeworkDtl homeworkDtl = this.homeworkDtlService.getHomeworkDtlById(homeworkDtlId);
		Validation.isPageNotFound(homeworkDtl == null || !user.getId().equals(homeworkDtl.getStudentId()));
		if (homeworkDtl.getSubmitTime() != null) {
			// 如果作业已经提交，重定向到作业查看页面。
			return "redirect:/auth/student/homework/viewWork.htm?homeworkDtlId=" + homeworkDtlId;
		}
		// 作业信息
		Homework homework = this.homeworkService.getHomeworkById(homeworkDtl.getHomeworkId());
		if (homework.getExam()) {
			throw new ValidateException("请移步至平板端进行考试！");
		}
		model.addAttribute("globalNoteParams", WorkSupport.buildGlobalNoteParams(homework, homeworkDtl));

		Integer resType = homework.getResType();
		if (resType == HomeworkCst.HOMEWORK_RES_PAPER) {//试卷
			// 答题信息
			ResDataModel respond = WorkSupport.buildResDataModel(user, ResDataModel.DO_ANSWER, homework, homeworkDtl);
			WorkDetail workDetail = this.workDetailService.getWorkDetailByHomeworkDtlId(homeworkDtlId);
			if (workDetail != null) {
				// 已保存的答案获取
				PrepareHandlerAdapter.doPrepare(PrepareHandler.Mode.Work, workDetail.getQuestions());
				Map<Long, QuestionResult> questionResultMap = workDetail.getQuestions().stream()
						.collect(toMap(QuestionResult::getQuestionId, v -> v));
				respond.setQuestionResultMap(questionResultMap);
			} else {
				// 试卷作业：第一次做题时，更新开始时间
				this.homeworkMainService.saveStartWork(homeworkDtlId, HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);
			}
			PaperDTO paper = WorkSupport.getOrBuildPaperDTO(homework, homeworkDtl);
			WebWorkModel webWorkModel = WorkSupport.buildWebWorkModel(respond, homework, homeworkDtl);
			model.addAttribute("paper", paper);
			model.addAttribute("respond", respond);
			model.addAttribute("workModel", JsonUtils.toJSON(webWorkModel));
			return "/auth/work/doAnswerWork";
		}

		model.addAttribute("homework", homework);
		model.addAttribute("homeworkDtl", homeworkDtl);
		// 非试卷作业：每次做题时，更新查看时间
		this.homeworkMainService.saveStartWork(homeworkDtlId, HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);
		Integer position = this.workDetailService.getPosition(homeworkDtlId);
		if (resType == HomeworkCst.HOMEWORK_RES_MICROCOURSE) {//微课
			MicrocourseDTO microcourse = MicrocourseContext.getMicrocourse(homework.getPaperId());
			if (microcourse.getMicrocourseType() == 1) {
				WorkRequest request = new WorkRequest();
				request.setHomeworkDtlId(homeworkDtlId);
				request.setUserId(UserContext.user.getUserId());
				request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);
				this.homeworkMainService.savePreviewSubmitForRecordMicroCourse(request);
				StringBuilder builder = new StringBuilder();
				builder.append("redirect:");
				builder.append(httpServletRequest.getServletContext().getInitParameter("onlineServerName"));
				builder.append("/view/service.php?action=recordStart&mode=2");
				builder.append("&coursewareId=").append(microcourse.getCrcwId());
				builder.append("&recordId=").append(microcourse.getRecordId());
				return builder.toString();
			} else if (microcourse.getMicrocourseType() == 3) {
				Map<String, Object> Csts = Maps.newHashMap();
				Csts.put("position", position);
				Csts.put("homeworkId", homeworkDtl.getHomeworkId());
				Csts.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
				Csts.put("status", homeworkDtl.getSubmitStatus());
				model.addAttribute("microcourse", microcourse);
				model.addAttribute("Csts", JsonUtils.toJSON(Csts));
				return "/auth/student/homework/doMicroWorkForNewVod";
			} else {
				FileDesc fileDesc = this.fileDescService.convertToFileDesc(microcourse);
				Map<String, Object> Csts = Maps.newHashMap();
				Csts.put("file", fileDesc);
				Csts.put("position", position);
				Csts.put("homeworkId", homeworkDtl.getHomeworkId());
				Csts.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
				model.addAttribute("microcourse", microcourse);
				model.addAttribute("type", Types.valueOf(microcourse.getMicrocourseFile().getType()));
				model.addAttribute("Csts", JsonUtils.toJSON(Csts));
				return "/auth/student/homework/doMicroWork";
			}
		} else {//课件
			CoursewareDTO courseware = CoursewareContext.getCourseware(homework.getPaperId());
			FileDesc fileDesc = this.fileDescService.convertToFileDesc(courseware);
			Map<String, Object> Csts = Maps.newHashMap();
			Csts.put("file", fileDesc);
			Csts.put("position", position);
			Csts.put("homeworkId", homeworkDtl.getHomeworkId());
			Csts.put("homeworkDtlId", homeworkDtl.getHomeworkDtlId());
			model.addAttribute("courseware", courseware);
			model.addAttribute("type", Types.valueOf(courseware.getType()));
			model.addAttribute("Csts", JsonUtils.toJSON(Csts));
			return "/auth/student/homework/doCourseWork";
		}
	}

	/**
	 * 场景化作业。
	 * @param homeworkId 作业ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("homeworkProgress")
	public JsonResult homeworkProgress(Long homeworkId) {
		ProgressInfo progress = this.homeworkProgressService.getProgressInfoByHomeworkId(homeworkId);
		return new JsonResult().addDatas("progress", progress);
	}

	/**
	 * 作业心跳/保存答题信息。
	 * @param homeworkDtlId 学生作业标识
	 * @param answerJson    学生答题信息JSON
	 * @return
	 */
	@ResponseBody
	@RequestMapping("heartbeat")
	public Object heartbeat(Long homeworkDtlId, String answerJson) {
		WorkRequest request = new WorkRequest();
		request.setIsAutoSave(true);
		request.setHomeworkDtlId(homeworkDtlId);
		request.setAnswerInfoList(JsonUtils.readList(answerJson, AnswerInfo.class));
		this.homeworkMainService.saveAnswerSnapshot(request);
		return new JsonResult();
	}

	/**
	 * 保存答题信息。
	 * @param homeworkDtlId 学生作业标识
	 * @param answerJson    学生答题信息JSON
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveWork")
	public Object saveWork(Long homeworkDtlId, String answerJson) {
		WorkRequest request = new WorkRequest();
		request.setIsAutoSave(false);
		request.setHomeworkDtlId(homeworkDtlId);
		request.setAnswerInfoList(JsonUtils.readList(answerJson, AnswerInfo.class));
		this.homeworkMainService.saveAnswerSnapshot(request);
		return new JsonResult();
	}

	/**
	 * 提交答题信息。
	 * @param homeworkDtlId 学生作业标识
	 * @param answerJson    学生答题信息JSON
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submitWork")
	public Object submitWork(Long homeworkDtlId, String answerJson) {
		logger.info("submitHomework by web: homeworkDtlId = " + homeworkDtlId + ",answerJson=" + answerJson);
		WorkRequest request = new WorkRequest();
		request.setHomeworkDtlId(homeworkDtlId);
		request.setAnswerInfoList(JsonUtils.readList(answerJson, AnswerInfo.class));
		this.homeworkMainService.saveAnswerSubmit(request);

		JsonResult json = new JsonResult();
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlService
				.getHomeworkDtlInfoById(request.getHomeworkDtlId());
		if (homeworkDtlInfo.getCorrectTime() != null) {
			// 已经批改的直接发送激励
			Award award = this.homeworkMainService.sendIncTypeForSubmitHomework(homeworkDtlInfo);
			json.addDatas("award", award);
		}
		return json;
	}

	/**
	 * 保存课件或者微课的学习进度。
	 * @param homeworkDtlId 学生作业标识
	 * @param position 进度位置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveWork2")
	public Object saveWork2(Long homeworkDtlId, Integer position, Integer duration) {
		this.workDetailService.updatePosition(homeworkDtlId, position, duration, UserContext.user.getUserId());
		return new JsonResult();
	}

	/**
	 * 完成课件或者微课的学习
	 * @param homeworkId
	 * @param homeworkDtlId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitWork2")
	public JsonResult submitWork2(WorkRequest request) {
		request.setUserId(UserContext.user.getUserId());
		request.setDataSource(HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);
		this.homeworkMainService.savePreviewSubmit(request);
		return new JsonResult();
	}

	/**
	 * 根据学生作业ID，显示作业提交成功信息
	 * @param homeworkDtlId
	 * @param model
	 */
	@RequestMapping("doWorkSuccess")
	public void doWorkSuccess(Long homeworkDtlId, Model model) {
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlService.getHomeworkDtlInfoById(homeworkDtlId);
		Validation.isPageNotFound(homeworkDtlInfo == null);
		Homework homework = homeworkService.getHomeworkById(homeworkDtlInfo.getHomeworkId());
		model.addAttribute("count", homework.getFinishNum() == 0 ? 1 : homework.getFinishNum());
		model.addAttribute("type", HomeworkType.valueOf(homework.getHomeworkType()).name);
		fillUsedTime(homeworkDtlInfo);
		model.addAttribute("homeworkDtl", homeworkDtlInfo);
		model.addAttribute("isShow", HomeworkType.HOLIDAY.value != homework.getHomeworkType());
		if (homeworkDtlInfo.getCorrectTime() != null) {
			//作业是否含有主观题
			if (homework.getSubjective()) {
				Award award = this.homeworkMainService.sendIncTypeForSubmitHomework(homeworkDtlInfo);
				if (award.getSucc() && award.getHave()) {
					model.addAttribute("exp", award.getExpVal());
					model.addAttribute("leke", award.getLekeVal());
				}
			}
		}
	}

	private void fillUsedTime(HomeworkDtlInfo homeworkDtlInfo) {
		Integer usedTime = homeworkDtlInfo.getUsedTime();
		if (usedTime == null || usedTime == 0) {
			usedTime = this.homeworkProgressService.getStudentUsedTime(homeworkDtlInfo.getHomeworkId(),
					homeworkDtlInfo.getStudentId());
		}
		homeworkDtlInfo.setUsedTime(HomeworkUtils.convertUsedTime(usedTime));
	}


	/**
	 * 学生作业跳转
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	@RequestMapping("redirectDoWork")
	public String redirectDoWork(@RequestParam("homeworkId") Long homeworkId) {
		User user = UserContext.user.get();
		HomeworkDtl homeworkDtl = this.homeworkService.createStuBeikeHwWithTx(homeworkId, user);
		Validation.isPageNotFound(homeworkDtl == null);
		return "redirect:/auth/student/homework/doWork.htm?homeworkDtlId=" + homeworkDtl.getHomeworkDtlId();
	}

	/**
	 * 学生作业跳转(点播作业)
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	@RequestMapping("vodDoWork")
	public String vodDoWork(@RequestParam("homeworkId") Long homeworkId) {
		User user = UserContext.user.get();
		HomeworkDtl homeworkDtl = this.homeworkAssignService.resolveStuVodHwWithTx(homeworkId, user);
		if (homeworkDtl.getSubmitTime() != null) {
			return "redirect:/auth/student/homework/viewWork.htm?homeworkDtlId=" + homeworkDtl.getHomeworkDtlId();
		}
		return "redirect:/auth/student/homework/doWork.htm?homeworkDtlId=" + homeworkDtl.getHomeworkDtlId();
	}
}
