package cn.strong.leke.homework.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkCorrectMQ;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkProgressMQ;
import cn.strong.leke.homework.model.HomeworkSubmitMQ;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.ScoreUtils;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.wrong.WrongMQ;
import cn.strong.leke.model.wrong.WrongMQ.WrongQuestion;
import cn.strong.leke.model.wrong.WrongSource;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.notice.model.MessageBusinessTypes;
import cn.strong.leke.notice.model.todo.HwBugFixEvent;
import cn.strong.leke.notice.model.todo.HwCorrectEvent;
import cn.strong.leke.notice.model.todo.HwDtlInfo;
import cn.strong.leke.notice.model.todo.HwReviewEvent;
import cn.strong.leke.notice.model.todo.HwSelfCheckEvent;
import cn.strong.leke.notice.model.todo.HwSubmitEvent;
import cn.strong.leke.remote.model.beike.HomeworkDtlNoticeRemote;
import cn.strong.leke.tags.question.render.QuestionResultRender;

@Component
public class MessageService {

	@Resource
	private MessageSender wrongtopicSender;
	@Resource
	private MessageSender homeworkSubmitSender;
	@Resource
	private MessageSender homeworkCorrectSender;
	@Resource
	private MessageSender homeworkProgressSender;
	@Resource
	private MessageSender resourceHomeworkDtlSender;

	public void sendHomeworkSubmitMessage(HomeworkSubmitMQ homeworkSubmitMQ) {
		this.homeworkSubmitSender.send(homeworkSubmitMQ);
	}

	public void sendHomeworkCorrectMessage(Long homeworkDtlId,boolean isAgain,List<Long> newWrongQuestions) {
		HomeworkCorrectMQ homeworkCorrectMQ = new HomeworkCorrectMQ();
		homeworkCorrectMQ.setHomeworkDtlId(homeworkDtlId);
		homeworkCorrectMQ.setIsAgain(isAgain);
		homeworkCorrectMQ.setNewWrongQuestions(newWrongQuestions);
		this.homeworkCorrectSender.send(homeworkCorrectMQ);
	}

	/**
	 * @param homeworkDtlId
	 * @param correctNum
	 */
	public void sendHomeworkProgressMessage(Long homeworkDtlId, Integer correctCount) {
		HomeworkProgressMQ homeworkProgressMQ = new HomeworkProgressMQ();
		homeworkProgressMQ.setHomeworkDtlId(homeworkDtlId);
		homeworkProgressMQ.setCorrectCount(correctCount);
		this.homeworkProgressSender.send(homeworkProgressMQ);
	}

	/**
	 * 作业满分
	 */
	public void sendFullScoreLetterMessage(HomeworkDtlInfo homeworkDtlInfo) {
		LetterMessage message = createFullScoreMessage(homeworkDtlInfo);
		NoticeHelper.send(message);
	}

	/**
	 * @param homeworkDtlInfo
	 * @return
	 */
	public LetterMessage createFullScoreMessage(HomeworkDtlInfo homeworkDtlInfo) {
		String to = String.valueOf(homeworkDtlInfo.getStudentId());
		String subject = "作业满分";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_FULLSCORE_TEMPLATE);
		LetterMessage message = new LetterMessage(to, subject, content);
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addVariable("homeworkName", homeworkDtlInfo.getHomeworkName());
		return message;
	}

	/**
	 * 批改已批改
	 */
	public void sendCorrectLetterMessage(Long userId, String homeworkName) {
		LetterMessage message = createCorrectMessage(userId, homeworkName);
		NoticeHelper.send(message);
	}

	/**
	 * @param homeworkDtlInfo
	 * @return
	 */
	public LetterMessage createCorrectMessage(Long userId, String homeworkName) {
		String to = String.valueOf(userId);
		String subject = "作业已批改";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_CORRECTING_TEMPLATE);
		LetterMessage message = new LetterMessage(to, subject, content);
		message.addVariable("1", homeworkName);
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		return message;
	}

	/**
	 * 作业自行校对提醒消息。
	 */
	public void sendSelfCheckLetterMessage(List<Long> userIds, String homeworkName) {
		List<String> to = userIds.stream().map(String::valueOf).collect(Collectors.toList());
		String subject = "作业自行校对";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_SELF_CHECK_TEMPLATE);
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.addVariable("homeworkName", homeworkName);
		NoticeHelper.send(message);
	}
	/**
	 * 公布答案提醒消息。
	 */
	public void sendOpenAnswerLetterMessage(List<Long> userIds, String homeworkName) {
		List<String> to = userIds.stream().map(String::valueOf).collect(Collectors.toList());
		String subject = "作业已公布答案";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_OPEN_ANSWER_TEMPLATE);
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.addVariable("homeworkName", homeworkName);
		NoticeHelper.send(message);
	}
	
	/**
	 * 作业作废提醒消息。
	 */
	public void sendInvalidLetterMessage(List<Long> userIds, Homework homework) {
		List<String> to = userIds.stream().map(String::valueOf).collect(Collectors.toList());
		String subject = homework.getExam() ? "考试已作废" : "作业已作废";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_INVALID_TEMPLATE);
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.addVariable("homeworkName", homework.getHomeworkName());
		NoticeHelper.send(message);
	}
	/**
	 * 作业截止日期提醒消息。
	 */
	public void sendModifyHwCloseTimeLetterMessage(List<Long> userIds, String homeworkName,Date colseTime) {
		List<String> to = userIds.stream().map(String::valueOf).collect(Collectors.toList());
		String subject = "作业截止时间更改";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_MODIFY_CLOSE_TIME_TEMPLATE);
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.setEndTime(colseTime.getTime());
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.setEndTime(colseTime.getTime());
		message.addVariable("homeworkName", homeworkName);
		message.addVariable("closeTime", DateUtils.format(colseTime,DateUtils.LONG_DATE_PATTERN));
		NoticeHelper.send(message);
	}
	
	/**
	 * 作业自行校对待办消息。
	 */
	public void sendSelfCheckTodoMessage(List<HwDtlInfo> hwDtlInfos,Long homeworkId,Long teacherId, String homeworkName) {
		HwSelfCheckEvent event = new HwSelfCheckEvent();
		event.setHomeworkId(homeworkId);
		event.setTeacherId(teacherId);
		event.setHomeworkName(homeworkName);
		event.setHwDtlInfos(hwDtlInfos);
		NoticeHelper.todo(event);
	}

	/**
	 * 作业通过订正（单人）。
	 */
	public void sendReviewPassedLetterMessage(HomeworkDtlInfo homeworkDtlInfo) {
		String to = String.valueOf(homeworkDtlInfo.getStudentId());
		String subject = "作业通过订正";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_REVIEW_PASSED_TEMPLATE);
		LetterMessage message = new LetterMessage(to, subject, content);
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addVariable("homeworkName", homeworkDtlInfo.getHomeworkName());
		NoticeHelper.send(message);
	}

	/**
	 * 作业通过订正（多人）。
	 */
	public void sendReviewPassedLetterMessage(List<Long> userIds, String homeworkName) {
		if (CollectionUtils.isEmpty(userIds)) {
			return;
		}
		List<String> to = userIds.stream().map(String::valueOf).collect(Collectors.toList());
		String subject = "作业通过订正";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_REVIEW_PASSED_TEMPLATE);
		LetterMessage message = new LetterMessage();
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.addVariable("homeworkName", homeworkName);
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		NoticeHelper.send(message);
	}

	/**
	 * 发送作业提交待办消息。
	 */
	public void sendSubmitTodoMessage(HomeworkDtlInfo homeworkDtlInfo, Boolean subjective, Boolean isFullScore) {
		HwSubmitEvent event = new HwSubmitEvent();
		event.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		event.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		event.setHomeworkName(homeworkDtlInfo.getHomeworkName());
		event.setStudentId(homeworkDtlInfo.getStudentId());
		event.setSubjective(homeworkDtlInfo.getCorrectTime() == null);
		event.setTeacherId(homeworkDtlInfo.getTeacherId());
		event.setIsSelfCheck(homeworkDtlInfo.getIsSelfCheck());
		event.setCloseTime(homeworkDtlInfo.getCloseTime());
		event.setIsFullScore(isFullScore);
		event.setIsAllowBugFix(homeworkDtlInfo.getUsePhase() == null || homeworkDtlInfo.getUsePhase().equals(HomeworkCst.HOMEWORK_USE_PHASE_AFTER));
		NoticeHelper.todo(event);
	}

	/**
	 * 发送作业批改(重批)结束消息
	 */
	public void sendFinishCorrectMessage(HomeworkDtlInfo homeworkDtlInfo, Boolean isFullScore,Boolean isBugFix) {
		HwCorrectEvent event = createHwCorrectEvent(homeworkDtlInfo, isFullScore);
		event.setIsAllowBugFix(isBugFix);
		NoticeHelper.todo(event);
	}

	/**
	 * @param homeworkDtlInfo
	 * @param isFullScore
	 * @return
	 */
	public HwCorrectEvent createHwCorrectEvent(HomeworkDtlInfo homeworkDtlInfo, Boolean isFullScore) {
		HwCorrectEvent event = new HwCorrectEvent();
		event.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		event.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		event.setHomeworkName(homeworkDtlInfo.getHomeworkName());
		event.setStudentId(homeworkDtlInfo.getStudentId());
		event.setTeacherId(homeworkDtlInfo.getTeacherId());
		event.setIsFullScore(isFullScore);
		event.setIsAllowBugFix(homeworkDtlInfo.getUsePhase() == null || homeworkDtlInfo.getUsePhase().equals(HomeworkCst.HOMEWORK_USE_PHASE_AFTER));
		return event;
	}

	public void sendBugFixTodoMessage(HomeworkDtlInfo homeworkDtlInfo, Boolean isPassedFix, Boolean hasSubjective) {
		HwBugFixEvent event = new HwBugFixEvent();
		event.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		event.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		event.setStudentId(homeworkDtlInfo.getStudentId());
		event.setTeacherId(homeworkDtlInfo.getTeacherId());
		event.setIsPassedFix(isPassedFix);
		event.setSubjective(hasSubjective);
		NoticeHelper.todo(event);
	}

	public void sendReviewTodoMessage(HomeworkDtlInfo homeworkDtlInfo) {
		HwReviewEvent event = new HwReviewEvent();
		event.setTeacherId(homeworkDtlInfo.getTeacherId());
		event.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		event.setHomeworkName(homeworkDtlInfo.getHomeworkName());
		HwDtlInfo hwDtlInfo = new HwDtlInfo();
		hwDtlInfo.setStudentId(homeworkDtlInfo.getStudentId());
		hwDtlInfo.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		event.setHwDtlInfos(Arrays.asList(hwDtlInfo));
		NoticeHelper.todo(event);
	}

	public void sendReviewTodoMessage(Homework homework, List<HwDtlInfo> hwDtlInfos) {
		HwReviewEvent event = new HwReviewEvent();
		event.setTeacherId(homework.getTeacherId());
		event.setHomeworkId(homework.getHomeworkId());
		event.setHomeworkName(homework.getHomeworkName());
		event.setHwDtlInfos(hwDtlInfos);
		NoticeHelper.todo(event);
	}
	
	// 用户动态消息发送
	public void sendDoWorkMessage(HomeworkDtlInfo homeworkDtlInfo) {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(homeworkDtlInfo.getStudentId());
		dynamicInfo.setUserName(homeworkDtlInfo.getStudentName());
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(homeworkDtlInfo.getSchoolId());
		// 动态
		dynamicInfo.setDynamicType(DynamicTypes.HW_HOMEWORK_START);
		dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("homeworkDtlId", homeworkDtlInfo.getHomeworkDtlId());
		dynamicInfo.setParams(params);
		DynamicHelper.publish(dynamicInfo);
	}

	/**
	 * 发送用户动态（激励）消息。
	 */
	public void sendDynamicInfoMessage(HomeworkDtlInfo homeworkDtlInfo, boolean dynamic, boolean incentive) {
		if(homeworkDtlInfo.getCorrectUserId() == null){
			return ;//非老师批改，动态不发送
		}
		DynamicInfo dynamicInfo = createDynamicInfo(homeworkDtlInfo, dynamic, incentive);
		DynamicHelper.publish(dynamicInfo);
	}

	/**
	 * @param homeworkDtlInfo
	 * @param dynamic
	 * @param incentive
	 * @return
	 */
	public DynamicInfo createDynamicInfo(HomeworkDtlInfo homeworkDtlInfo, boolean dynamic, boolean incentive) {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(homeworkDtlInfo.getStudentId());
		dynamicInfo.setUserName(homeworkDtlInfo.getStudentName());
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(homeworkDtlInfo.getSchoolId());
		dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
		// 动态
		if (dynamic) {
			dynamicInfo.setDynamicType(DynamicTypes.HW_HOMEWORK_FINISH);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("homeworkDtlId", homeworkDtlInfo.getHomeworkDtlId());
			dynamicInfo.setParams(params);
		}
		// 激励
		if (incentive) {
			dynamicInfo.setTypeId(IncentiveTypes.STUDENT.HW_HOMEWORK_FINISH);
			int state = ScoreUtils.toState(homeworkDtlInfo.getScoreRate());
			dynamicInfo.setState(state);
		}
		return dynamicInfo;
	}


	/**
	 * 发送错题本消息。
	 */
	public void sendWrongTopicMessage(HomeworkDtlInfo homeworkDtlInfo, List<QuestionResult> questionResults) {
		WrongMQ wrongMQ = createWrongMQ(homeworkDtlInfo, questionResults);
		if (wrongMQ != null) {
			this.wrongtopicSender.send(wrongMQ);
		}
	}
	
	public WrongMQ createWrongMQ(HomeworkDtlInfo homeworkDtlInfo, List<QuestionResult> questionResults) {
		Long paperId = homeworkDtlInfo.getPaperId();
		PaperDTO paperDTO = PaperContext.getPaperDTO(paperId);
		if (paperId == null
				|| (PaperDTO.PAPER_TYPE_ANSWER_SHEET == paperDTO.getPaperType() && paperDTO.getPaperAttachmentId() == null)) {
			//答题卡(无附件的)错题不进入错题本
			return null;
		}

		Predicate<QuestionResult> filter = questionResult -> Boolean.FALSE.equals(questionResult.getTotalIsRight());
		List<WrongQuestion> questions = questionResults.stream().filter(filter).map(questionResult -> {
			WrongQuestion question = new WrongQuestion();
			question.setQuestionId(questionResult.getQuestionId());
			question.setAnswerContent(QuestionResultRender.doRender(questionResult));
			return question;
		}).collect(Collectors.toList());
		if (questions.isEmpty()) {
			return null;
		}
		WrongMQ wrongMQ = new WrongMQ();
		wrongMQ.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		wrongMQ.setHomeworkName(homeworkDtlInfo.getHomeworkName());
		wrongMQ.setPaperId(homeworkDtlInfo.getPaperId());
		wrongMQ.setStudentId(homeworkDtlInfo.getStudentId());
		wrongMQ.setSubjectId(homeworkDtlInfo.getSubjectId());
		wrongMQ.setSubjectName(homeworkDtlInfo.getSubjectName());
		wrongMQ.setSourceType(WrongSource.HOMEWORK.value);
		wrongMQ.setSourceName(homeworkDtlInfo.getHomeworkName());
		wrongMQ.setAnswerTime(homeworkDtlInfo.getSubmitTime());
		wrongMQ.setQuestions(questions);
		return wrongMQ;
	}

	/**
	 * 发送作业批改状态到点播课系统(点播作业)
	 * @param homeworkDtlInfo
	 */
	public void sendHomeworkVodMessage(HomeworkDtlInfo homeworkDtlInfo) {
		Long homeworkId = homeworkDtlInfo.getHomeworkId();
		if (homeworkDtlInfo.getParentId() != null) {
			homeworkId = homeworkDtlInfo.getParentId();
		}
		HomeworkDtlNoticeRemote homeworkDtlNotice = createHomeworkDtlNoticeRemote(homeworkId,
				homeworkDtlInfo.getHomeworkDtlId(), homeworkDtlInfo.getStudentId(), homeworkDtlInfo.getCorrectTime());
		this.resourceHomeworkDtlSender.send(homeworkDtlNotice);
	}

	/**
	 * @param homeworkDtlInfo
	 * @return
	 */
	public HomeworkDtlNoticeRemote createHomeworkDtlNoticeRemote(Long homeworkId, Long homeworkDtlId, Long studentId,
			Date correctTime) {
		HomeworkDtlNoticeRemote homeworkDtlNotice = new HomeworkDtlNoticeRemote();
		homeworkDtlNotice.setHomeworkId(homeworkId);
		homeworkDtlNotice.setHomeworkDtlId(homeworkDtlId);
		homeworkDtlNotice.setStudentId(studentId);
		homeworkDtlNotice.setSubmitStatus(1L);// 已提交
		if (correctTime != null) {
			homeworkDtlNotice.setSubmitStatus(2L); // 已批改
		}
		return homeworkDtlNotice;
	}
}
