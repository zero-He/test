package cn.strong.leke.homework.service.impl;

import static cn.strong.leke.homework.util.AnswerUtils.decideIsPassedFix;
import static cn.strong.leke.homework.util.AnswerUtils.decideIsRight;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.core.mq.rabbit.MessageRequiredRepeatRunException;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.homework.dao.mongo.WatchHistoryDao;
import cn.strong.leke.homework.dao.mongo.WorkDetailDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDataDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.manage.HolidayHwMicroService;
import cn.strong.leke.homework.manage.HomeworkProgressService;
import cn.strong.leke.homework.manage.HwQueCommentaryService;
import cn.strong.leke.homework.manage.SheetQueryService;
import cn.strong.leke.homework.manage.SheetTaskService;
import cn.strong.leke.homework.model.AnswerInfo;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkCorrectMQ;
import cn.strong.leke.homework.model.HomeworkData;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkProgressMQ;
import cn.strong.leke.homework.model.HomeworkSubmitMQ;
import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.OnlineCorrectHomework;
import cn.strong.leke.homework.model.PaperVisitor;
import cn.strong.leke.homework.model.ResRawType;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.SheetErr;
import cn.strong.leke.homework.model.SheetResult;
import cn.strong.leke.homework.model.StudentAnswer;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.model.WorkDetail.GroupModel;
import cn.strong.leke.homework.model.WorkDetail.GroupScore;
import cn.strong.leke.homework.model.WorkRequest;
import cn.strong.leke.homework.service.HomeworkMainService;
import cn.strong.leke.homework.service.HomeworkSnapshotService;
import cn.strong.leke.homework.util.AnswerUtils;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.homework.util.HomeworkUtils;
import cn.strong.leke.homework.util.PhaseUtils;
import cn.strong.leke.homework.util.ScoreUtils;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.model.BaseModelHelper;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.AnswerResult;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.model.wrong.WrongMQ;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.notice.model.MessageBusinessTypes;
import cn.strong.leke.notice.model.todo.HwCorrectEvent;
import cn.strong.leke.notice.model.todo.HwDtlInfo;
import cn.strong.leke.notice.model.todo.HwSubmitEvent;
import cn.strong.leke.remote.service.beike.IBeikeRemoteService;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;
import cn.strong.leke.tags.question.check.QuestionCheckAdapter;

@Service("homeworkMainService")
public class HomeworkMainServiceImpl implements HomeworkMainService {

	private static final Logger logger = LoggerFactory.getLogger(HomeworkMainServiceImpl.class);

	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private HomeworkDataDao homeworkDataDao;
	@Resource
	private WorkDetailDao workDetailDao;
	@Resource
	private WatchHistoryDao watchHistoryDao;
	@Resource
	private SheetTaskService sheetTaskService;
	@Resource
	private SheetQueryService sheetQueryService;
	@Resource
	private MessageService messageService;
	@Resource
	private HomeworkSnapshotService homeworkSnapshotService;
	@Resource
	private ILessonRemoteService lessonRemoteService;
	@Resource
	private IBeikeRemoteService beikeRemoteService;
	@Resource
	private HomeworkProgressService homeworkProgressService;
	@Resource
	private HolidayHwMicroService holidayHwMicroService;
	@Resource
	private MessageSender wrongtopicSender;

	/**
	 * 尝试持有学生作业表的乐观锁。
	 * @param homeworkDtlId
	 * @param version
	 */
	private void tryHoldOptimisticLocking(Long homeworkDtlId, Integer version) {
		int touchRows = this.homeworkDtlDao.updateHomeworkDtlVersion(homeworkDtlId, version);
		if (touchRows == 0) {
			logger.info("repart submit, {}.", homeworkDtlId);
			throw new ValidateException("homework.homework.repartsubmit");
		}
	}

	/**
	 * 将原来的一个更新拆分为一个查询和一个更新，预防死锁问题出现。
	 * @param homeworkId
	 */
	public void updateHomeworkStat(Long homeworkId) {
		Map<String, Object> map = this.homeworkDao.getUpdateHomeworkStatMap(homeworkId);
		if (map != null) {
			map.put("homeworkId", homeworkId);
			this.homeworkDao.updateHomeworkStat(map);
		}
	}

	/**
	 * 计算大题得分情况
	 * @param questions
	 * @param paperDetail
	 * @return
	 */
	private List<GroupScore> buildQueGroupScores(List<QuestionResult> questions, PaperVisitor paper) {
		Map<Long, QuestionResult> questionResultMap = questions.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
		List<GroupScore> groupScores = new ArrayList<>();
		for (int i = 0; i < paper.getPaperDetail().getGroups().size(); i++) {
			QuestionGroup questionGroup = paper.getPaperDetail().getGroups().get(i);
			BigDecimal rightNum = new BigDecimal(0);
			BigDecimal selfScore = new BigDecimal(0);
			BigDecimal totalScore = new BigDecimal(0);
			for (ScoredQuestion scoredQuestion : questionGroup.getQuestions()) {
				QuestionResult questionResult = questionResultMap.get(scoredQuestion.getQuestionId());
				if (questionResult != null && questionResult.getTotalResultScore() != null) {
					rightNum = rightNum.add(questionResult.getTotalScoreRate());
					selfScore = selfScore.add(questionResult.getTotalResultScore());
					totalScore = totalScore.add(questionResult.getTotalResultScore());
				}
			}

			GroupScore groupScore = new GroupScore();
			groupScore.setId(i + 1L);
			groupScore.setName(questionGroup.getGroupTitle());
			groupScore.setTotalNum(questionGroup.getQuestions().size());
			groupScore.setRightNum(rightNum);
			groupScore.setSelfScore(selfScore);
			groupScore.setTotalScore(totalScore);
			groupScores.add(groupScore);
		}
		return groupScores;
	}

	/**
	 * 计算知识点得分情况
	 * @param questions
	 * @param paper
	 * @return
	 */
	private List<GroupScore> buildKnoGroupScores(List<QuestionResult> questions, PaperVisitor paper) {
		List<GroupModel> knoGroupModelList = this.parseKnoGroupModel(paper);
		Map<Long, QuestionResult> questionResultMap = questions.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
		return knoGroupModelList.stream().map(groupModel -> {
			BigDecimal rightNum = new BigDecimal(0), selfScore = new BigDecimal(0);
			for (Long qid : groupModel.getQids()) {
				QuestionResult questionResult = questionResultMap.get(qid);
				if (questionResult != null && questionResult.getTotalIsRight() != null) {
					rightNum = rightNum.add(questionResult.getTotalScoreRate());
					selfScore = selfScore.add(questionResult.getTotalResultScore());
				}
			}
			GroupScore groupScore = new GroupScore();
			groupScore.setId(groupModel.getId());
			groupScore.setName(groupModel.getName());
			groupScore.setTotalNum(groupModel.getQids().size());
			groupScore.setRightNum(rightNum);
			groupScore.setSelfScore(selfScore);
			groupScore.setTotalScore(groupModel.getScore());
			return groupScore;
		}).collect(toList());
	}

	private List<GroupModel> parseKnoGroupModel(PaperVisitor paper) {
		Map<Long, GroupModel> groupModelMap = new HashMap<>();
		for (QuestionGroup group : paper.getPaperDetail().getGroups()) {
			for (ScoredQuestion sq : group.getQuestions()) {
				QuestionDTO questionDTO = paper.getQuestion(sq.getQuestionId());
				if (CollectionUtils.isNotEmpty(questionDTO.getKnowledges())) {
					for (QuestionKnowledge knowledge : questionDTO.getKnowledges()) {
						GroupModel groupModel = groupModelMap.get(knowledge.getKnowledgeId());
						if (groupModel == null) {
							groupModel = new GroupModel();
							groupModel.setId(knowledge.getKnowledgeId());
							groupModel.setName(knowledge.getPath());
							groupModel.setQids(new ArrayList<>());
							groupModel.setScore(new BigDecimal(0));
							groupModelMap.put(knowledge.getKnowledgeId(), groupModel);
						}
						groupModel.getQids().add(sq.getQuestionId());
						groupModel.setScore(groupModel.getScore().add(sq.getScore()));
					}
				}
			}
		}
		return groupModelMap.values().stream().sorted((a, b) -> Long.compare(a.getId(), b.getId())).collect(toList());
	}

	/**
	 * 更新作业数据，如果不存在就插入。<br>
	 * 该方法用户学生暂存和提交作业时调用。
	 * @param homeworkDtlInfo
	 * @param questions
	 */
	private void updateQuestionsByStudentSubmit(HomeworkDtlInfo homeworkDtlInfo, List<QuestionResult> questions,
			List<GroupScore> queScores, List<GroupScore> knoScores, PaperVisitor paper, String bookId) {
		WorkDetail workDetail = new WorkDetail();
		workDetail.setScore(homeworkDtlInfo.getScore());
		workDetail.setScoreRate(homeworkDtlInfo.getScoreRate());
		workDetail.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		workDetail.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		workDetail.setStudentId(homeworkDtlInfo.getStudentId());
		workDetail.setStudentName(homeworkDtlInfo.getStudentName());
		workDetail.setCorrectCount(homeworkDtlInfo.getCorrectCount());
		workDetail.setQuestionNum(paper.getQuestionNum());
		workDetail.setQuestions(questions);
		workDetail.setQueScores(queScores);
		workDetail.setKnoScores(knoScores);
		workDetail.setSheetBookId(bookId);
		this.workDetailDao.updateQuestionsByStudentSubmit(workDetail);
	}

	/**
	 * 更新学生作业信息
	 * @param homeworkDtlInfo
	 * @param paper
	 * @param totalScore
	 * @param submitSource
	 */
	private void updateHomeworkDtlInfoBySubmit(HomeworkDtlInfo homeworkDtlInfo, PaperVisitor paper,
			BigDecimal totalScore, Integer submitSource, Boolean fullScore, Boolean fullCorrect, Date currentDate) {
		if (totalScore.compareTo(paper.getTotalScore()) > 0) {
			// 控制学生得分不超过试卷总分
			totalScore = paper.getTotalScore();
		}
		BigDecimal scoreRate = totalScore.divide(paper.getTotalScore(), 5, RoundingMode.HALF_UP);
		homeworkDtlInfo.setScore(fullScore ? paper.getTotalScore() : totalScore);
		homeworkDtlInfo.setScoreRate(fullScore ? new BigDecimal(1) : ScoreUtils.roundScoreRate(scoreRate));
		homeworkDtlInfo.setSubmitSource(submitSource);
		homeworkDtlInfo.setCorrectSource(HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE);

		if (homeworkDtlInfo.getCloseTime().after(currentDate)) {
			homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_NORMAL);
		} else {
			homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_DELAY);
		}
		homeworkDtlInfo.setSubmitTime(currentDate);
		if (fullCorrect) {
			homeworkDtlInfo.setCorrectTime(currentDate);
		}
		if (isAllowBugfix(homeworkDtlInfo)) {
			if ((fullCorrect && !fullScore) || homeworkDtlInfo.getIsSelfCheck()) {
				homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX);
			}
		}
		homeworkDtlInfo.setModifiedBy(homeworkDtlInfo.getStudentId());
		homeworkDtlInfo.setModifiedOn(currentDate);
		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
	}

	/**
	 * 是否允许订正
	 * @param homeworkId
	 * @return
	 */
	private boolean isAllowBugfix(HomeworkDtlInfo homeworkDtlInfo) {
		boolean isFlowHw = homeworkDtlInfo.getUsePhase() != null;
		if(isFlowHw) {
			boolean isLastPhase = PhaseUtils.isLastPhase(homeworkDtlInfo.getUsePhase(), homeworkDtlInfo.getComboPhase());
			//布置的作业和流转作业的最后一份允许订正	,课中作业只在课堂结束，最后一次发起的作业允许订正
			if (isLastPhase && HomeworkCst.HOMEWORK_USE_PHASE_LESSON == homeworkDtlInfo.getUsePhase()) {
				Long lessonId = homeworkDao.getHomeworkById(homeworkDtlInfo.getHomeworkId()).getCourseSingleId();
				return lessonRemoteService.getLessonVMByLessonId(lessonId).getIsEnd();
			}
			return isLastPhase;
		} 
		return true;
	}

	/**
	 * 更新学生作业信息
	 * @param homeworkDtlInfo
	 * @param paper
	 * @param totalScore
	 * @param fullScore
	 */
	private void updateHomeworkDtlInfoBySheetSubmit(HomeworkDtlInfo homeworkDtlInfo, PaperVisitor paper,
			BigDecimal totalScore, Long errorTotal) {
		boolean fullScore = errorTotal == 0;
		if (totalScore.compareTo(paper.getTotalScore()) > 0) {
			// 控制学生得分不超过试卷总分
			totalScore = paper.getTotalScore();
		}
		BigDecimal scoreRate = totalScore.divide(paper.getTotalScore(), 5, RoundingMode.HALF_UP);
		homeworkDtlInfo.setScore(fullScore ? paper.getTotalScore() : totalScore);
		homeworkDtlInfo.setScoreRate(fullScore ? new BigDecimal(1) : ScoreUtils.roundScoreRate(scoreRate));
		homeworkDtlInfo.setSubmitSource(HomeworkCst.HOMEWORK_DATA_SOURCE_SHEET);
		homeworkDtlInfo.setCorrectSource(HomeworkCst.HOMEWORK_DATA_SOURCE_SHEET);
		homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_NORMAL);
		homeworkDtlInfo.setSubmitTime(new Date());
		homeworkDtlInfo.setCorrectTime(new Date());
		if (isAllowBugfix(homeworkDtlInfo)) {
			if (!fullScore) {
				homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX);
			}
		}
		homeworkDtlInfo.setModifiedBy(homeworkDtlInfo.getStudentId());
		homeworkDtlInfo.setModifiedOn(new Date());
		homeworkDtlInfo.setErrorTotal(errorTotal);
		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
	}

	@Override
	public void saveStartWork(Long homeworkDtlId, Integer dataSource) {
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);
		boolean isFirstWork = homeworkDtlInfo.getStartTime() == null;
		if (homeworkDtlInfo.getResType() == HomeworkCst.HOMEWORK_RES_PAPER) {
			// 试卷作业，如果开始时间为空时，记录时间并发送动态
			if (homeworkDtlInfo.getStartTime() == null) {
				homeworkDtlInfo.setStartTime(new Date());
				this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
			}
		} else {
			if (homeworkDtlInfo.getStartTime() == null) {
				homeworkDtlInfo.setStartTime(new Date());
				homeworkDtlInfo.setBugFixCount(1);
			} else {
				homeworkDtlInfo.setBugFixCount(homeworkDtlInfo.getBugFixCount() + 1);
			}
			homeworkDtlInfo.setBugFixTime(new Date());
			homeworkDtlInfo.setBugFixSource(dataSource);
			this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
		}

		if (isFirstWork) {
			this.messageService.sendDoWorkMessage(homeworkDtlInfo);
		}
	}

	/*
	 * 暂存作业
	 */
	@Override
	public void saveAnswerSnapshot(WorkRequest request) {
		// 1、业务验证
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(request.getHomeworkDtlId());
		if(request.getIsAutoSave()){
			//发送心跳
			homeworkProgressService.saveHeartbeat(homeworkDtlInfo.getHomeworkId(), homeworkDtlInfo.getStudentId());
		}
		Validation.isFalse(homeworkDtlInfo.getSubmitStatus() > HomeworkCst.HOMEWORK_SUBMIT_STATUS_NOT,
				"homework.homework.repartsubmit");

		// 2、数据结构转换
		List<QuestionResult> questions = request.getAnswerInfoList().stream()
				.map(answerInfo -> AnswerUtils.transAnswerInfoToQuestionResult(answerInfo)).collect(toList());

		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		if (workDetail != null && workDetail.getQuestions() != null) {
			// 合并正确的题目
			Map<Long, QuestionResult> questionResultMap = questions.stream()
					.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
			AnswerUtils.mergeOldIsRight(questionResultMap, workDetail.getQuestions());
			questions = new ArrayList<>(questionResultMap.values());
		}

		// 3、更新学生作业答题信息
		this.workDetailDao.updateQuestionsByStudentSnapshot(homeworkDtlInfo, questions);
	}

	/*
	 * 提交作业
	 */
	@Override
	public void saveAnswerSubmit(WorkRequest request) {
		// 1、业务验证
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(request.getHomeworkDtlId());
		Validation.isFalse(homeworkDtlInfo.getSubmitStatus() > HomeworkCst.HOMEWORK_SUBMIT_STATUS_NOT,
				"homework.homework.repartsubmit");

		// 2、并发控制(乐观锁)
		this.tryHoldOptimisticLocking(request.getHomeworkDtlId(), homeworkDtlInfo.getVersion());

		// 3、数据结构转换
		List<QuestionResult> questions = request.getAnswerInfoList().stream()
				.map(answerInfo -> AnswerUtils.transAnswerInfoToQuestionResult(answerInfo)).collect(toList());
		Map<Long, QuestionResult> questionResultMap = questions.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(request.getHomeworkDtlId());
		if (workDetail != null && workDetail.getQuestions() != null) {
			AnswerUtils.mergeOldIsRight(questionResultMap, workDetail.getQuestions());
			questions = new ArrayList<>(questionResultMap.values());
		}
		PaperVisitor paper = getPaperVisitor(homeworkDtlInfo.getPaperId(), homeworkDtlInfo.getHwPaperId());
		// 4、批改客观题
		AnswerUtils.fillNullQuestionResult(questionResultMap, paper);
		BigDecimal totalScore = AnswerUtils.correctObjectiveAnswer(questionResultMap, paper);
		List<GroupScore> queScores = this.buildQueGroupScores(questions, paper);
		List<GroupScore> knoScores = this.buildKnoGroupScores(questions, paper);

		// 5、更新学生作业信息
		int correctCount = (int) questions.stream().filter(v -> v.getTotalIsRight() != null).count();
		boolean fullScore = questions.stream().allMatch(decideIsRight);
		boolean fullCorrect = questions.stream().allMatch(v -> v.getTotalIsRight() != null);
		homeworkDtlInfo.setCorrectCount(correctCount);
		if(homeworkDtlInfo.getIsExam() != null && homeworkDtlInfo.getIsExam()){
			homeworkDtlInfo.setUsedTime(request.getUsedTime());
		}
		this.updateHomeworkDtlInfoBySubmit(homeworkDtlInfo, paper, totalScore, request.getDataSource(), fullScore,
				fullCorrect, new Date());

		// 6、作业提交后续处理消息发送
		HomeworkSubmitMQ homeworkSubmitMQ = new HomeworkSubmitMQ();
		homeworkSubmitMQ.setHomeworkDtlId(request.getHomeworkDtlId());
		homeworkSubmitMQ.setFullScore(fullScore);
		homeworkSubmitMQ.setQuestions(questions);
		homeworkSubmitMQ.setQueScores(queScores);
		homeworkSubmitMQ.setKnoScores(knoScores);
		this.messageService.sendHomeworkSubmitMessage(homeworkSubmitMQ);
	}

	@Override
	public void savePreviewSubmit(WorkRequest request) {
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(request.getHomeworkDtlId());
		if (request.getPosition() >= request.getDuration() && ResRawType.isMedia(homeworkDtlInfo.getRawType())) {
			request.setPosition(0);
		}
		if (!Boolean.TRUE.equals(request.getIsPlayEnd())) {
			// 如果未结束，直接更新并返回
			this.watchHistoryDao.updatePosition(request.getHomeworkDtlId(), request.getPosition(),
					request.getDuration(), request.getUserId());
			return;
		}
		if (homeworkDtlInfo.getSubmitTime() != null) {
			// 如果已经提交，直接更新并返回
			this.watchHistoryDao.updatePosition(request.getHomeworkDtlId(), request.getPosition(),
					request.getDuration(), request.getUserId());
			return;
		}

		Date currentDate = new Date();
		homeworkDtlInfo.setSubmitSource(request.getDataSource());
		if (homeworkDtlInfo.getCloseTime().after(currentDate)) {
			homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_NORMAL);
		} else {
			homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_DELAY);
		}
		homeworkDtlInfo.setSubmitTime(currentDate);
		homeworkDtlInfo.setModifiedBy(request.getUserId());
		homeworkDtlInfo.setModifiedOn(currentDate);
		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());

		this.watchHistoryDao.updatePosition(request.getHomeworkDtlId(), request.getPosition(), request.getDuration(),
				request.getUserId());

		//关闭待办
		HwSubmitEvent event = new HwSubmitEvent();
		event.setStudentId(homeworkDtlInfo.getStudentId());
		event.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		event.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		event.setIsFullScore(true);
		event.setSubjective(false);
		event.setIsAllowBugFix(false);
		NoticeHelper.todo(event);
	}

	@Override
	public void savePreviewSubmitForRecordMicroCourse(WorkRequest request) {
		// PAD提交且当前位置与总长度差值小于 3.
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(request.getHomeworkDtlId());
		if (homeworkDtlInfo.getSubmitTime() != null) {
			// 如果已经提交，直接返回
			return;
		}

		Date currentDate = new Date();
		homeworkDtlInfo.setSubmitSource(request.getDataSource());
		if (homeworkDtlInfo.getCloseTime().after(currentDate)) {
			homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_NORMAL);
		} else {
			homeworkDtlInfo.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_DELAY);
		}
		homeworkDtlInfo.setSubmitTime(currentDate);
		homeworkDtlInfo.setModifiedBy(request.getUserId());
		homeworkDtlInfo.setModifiedOn(currentDate);
		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());

		//关闭待办
		HwSubmitEvent event = new HwSubmitEvent();
		event.setStudentId(homeworkDtlInfo.getStudentId());
		event.setHomeworkId(homeworkDtlInfo.getHomeworkId());
		event.setHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
		event.setIsFullScore(true);
		event.setSubjective(false);
		NoticeHelper.todo(event);
	}

	@Override
	public void saveOnlineAnswerTempData(HomeworkData homeworkData) {
		this.homeworkDataDao.insertHomeworkData(homeworkData);
	}

	@Override
	public HomeworkDtlInfo handleSheetSubmitMessageWithTx(String bookId) {
		SheetBook book = this.sheetQueryService.getSheetBookById(bookId);
		// 1、业务验证
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(book.getHomeworkDtlId());
		if (homeworkDtlInfo.getSubmitStatus() > HomeworkCst.HOMEWORK_SUBMIT_STATUS_NOT) {
			book.setErrorNo(SheetErr.NOMATCH_HOMEWORK.code);
			this.sheetQueryService.updateSheetBook(book);
			return null;
		}

		// 2、并发控制(乐观锁)
		this.tryHoldOptimisticLocking(book.getHomeworkDtlId(), homeworkDtlInfo.getVersion());

		// 3、数据结构转换
		PaperVisitor paper = PaperVisitor.build(homeworkDtlInfo.getPaperId());
		List<QuestionResult> questions = this.sheetTaskService.parseQuestionResults(paper, book.getResults());
		Map<Long, QuestionResult> questionResultMap = questions.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));

		// 4、批改题目
		BigDecimal totalScore = AnswerUtils.correctSheetAnswer(questionResultMap, paper);
		List<GroupScore> queScores = this.buildQueGroupScores(questions, paper);
		List<GroupScore> knoScores = this.buildKnoGroupScores(questions, paper);

		// 5、更新学生作业信息
		homeworkDtlInfo.setCorrectCount(questions.size());
		this.homeworkProgressService.submitForSheet(homeworkDtlInfo.getHomeworkId(), homeworkDtlInfo.getStudentId());
		homeworkDtlInfo.setUsedTime(0);
		Long errorTotal = questions.stream().filter(v -> Boolean.FALSE.equals(v.getTotalIsRight())).count();
		boolean fullScore = questions.stream().allMatch(decideIsRight);
		this.updateHomeworkDtlInfoBySheetSubmit(homeworkDtlInfo, paper, totalScore, errorTotal);

		// 6、更新学生答案
		this.updateQuestionsByStudentSubmit(homeworkDtlInfo, questions, queScores, knoScores, paper, bookId);

		// 7、更新老师作业统计
		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());

		// 8、发消息
		this.messageService.sendSubmitTodoMessage(homeworkDtlInfo, false, fullScore); // 答案卡待办消息时，试卷当作客观
		// 提交时只有客观题作业才发送消息
		Boolean subjective = homeworkDtlInfo.getSubjective();
		this.messageService.sendDynamicInfoMessage(homeworkDtlInfo, true, !subjective);

		if (HomeworkType.VOD.value == homeworkDtlInfo.getHomeworkType()) {
			this.messageService.sendHomeworkVodMessage(homeworkDtlInfo);
		}
		this.messageService.sendWrongTopicMessage(homeworkDtlInfo, questions);
		if (fullScore) {
			// 满分作业发送消息
			this.messageService.sendFullScoreLetterMessage(homeworkDtlInfo);
		}
		return homeworkDtlInfo;
	}

	@Override
	public HomeworkDtlInfo handleSheetOverrideMessageWithTx(String bookId, List<SheetResult> results) {
		SheetBook book = this.sheetQueryService.getSheetBookById(bookId);
		if (results == null) {
			results = book.getResults();
		}
		// 1、业务验证
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(book.getHomeworkDtlId());
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());

		// 2、并发控制(乐观锁)
		this.tryHoldOptimisticLocking(book.getHomeworkDtlId(), homeworkDtlInfo.getVersion());

		// 3、数据结构转换
		PaperVisitor paper = PaperVisitor.build(homeworkDtlInfo.getPaperId());
		Map<Long, QuestionResult> questionResultMap = workDetail.getQuestions().stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));

		// 数据合并
		List<QuestionResult> newQuestions = this.sheetTaskService.parseQuestionResults(paper, results);
		for (QuestionResult newQs : newQuestions) {
			QuestionResult oldQs = questionResultMap.get(newQs.getQuestionId());
			if (CollectionUtils.isEmpty(newQs.getSubs())) {
				oldQs.setAnswerResults(newQs.getAnswerResults());
				continue;
			}
			for (QuestionResult newSubQs : newQs.getSubs()) {
				QuestionResult oldSubQs = oldQs.getSubs().stream()
						.filter(v -> newSubQs.getQuestionId().equals(v.getQuestionId())).findFirst().get();
				oldSubQs.setAnswerResults(newSubQs.getAnswerResults());
			}
		}
		List<QuestionResult> questions = new ArrayList<>(questionResultMap.values());

		// 4、批改题目
		BigDecimal totalScore = AnswerUtils.correctSheetAnswer(questionResultMap, paper);
		List<GroupScore> queScores = this.buildQueGroupScores(questions, paper);
		List<GroupScore> knoScores = this.buildKnoGroupScores(questions, paper);

		// 5、更新学生作业信息
		homeworkDtlInfo.setCorrectCount(questions.size());
		Long errorTotal = questions.stream().filter(v -> Boolean.FALSE.equals(v.getTotalIsRight())).count();
		this.updateHomeworkDtlInfoBySheetSubmit(homeworkDtlInfo, paper, totalScore, errorTotal);

		// 6、更新学生答案
		this.updateQuestionsByStudentSubmit(homeworkDtlInfo, questions, queScores, knoScores, paper, bookId);

		// 7、更新老师作业统计
		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());
		return homeworkDtlInfo;
	}

	// 学生作业提交后续处理。
	@Override
	public void handleHomeworkSubmitMessageWithTx(HomeworkSubmitMQ homeworkSubmitMQ) {
		Long homeworkDtlId = homeworkSubmitMQ.getHomeworkDtlId();
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);
		if (homeworkDtlInfo.getSubmitTime() == null) {
			throw new MessageRequiredRepeatRunException("Homework submit message, id: " + homeworkDtlId);
		}
		PaperVisitor paper = getPaperVisitor(homeworkDtlInfo.getPaperId(), homeworkDtlInfo.getHwPaperId());

		// 1、更新学生用时
		if(homeworkDtlInfo.getUsedTime() == null || homeworkDtlInfo.getUsedTime() == 0) {
			Integer usedTime = homeworkProgressService.submit(homeworkDtlInfo.getHomeworkId(),
					homeworkDtlInfo.getStudentId());
			homeworkDtlInfo.setUsedTime(usedTime > 60 ? usedTime : 60);
		}
		if (homeworkDtlInfo.getCorrectTime() != null) {
			Long errorTotal = homeworkSubmitMQ.getQuestions().stream()
					.filter(v -> Boolean.FALSE.equals(v.getTotalIsRight())).count();
			homeworkDtlInfo.setErrorTotal(errorTotal);
		}
		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);

		// 2、更新老师作业统计
		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());

		// 3、更新学生答案
		this.updateQuestionsByStudentSubmit(homeworkDtlInfo, homeworkSubmitMQ.getQuestions(),
				homeworkSubmitMQ.getQueScores(), homeworkSubmitMQ.getKnoScores(), paper, null);

		// 4、发消息
		this.messageService.sendSubmitTodoMessage(homeworkDtlInfo, homeworkDtlInfo.getSubjective(),
				homeworkSubmitMQ.getFullScore());

		// 只有已经全部批改作业才发送消息
		this.messageService.sendDynamicInfoMessage(homeworkDtlInfo, true, false);

		if (HomeworkType.VOD.value == homeworkDtlInfo.getHomeworkType()) {
			this.messageService.sendHomeworkVodMessage(homeworkDtlInfo);
		}
		if (homeworkDtlInfo.getCorrectTime() != null) {
			// 没有主观题，且为普通试题
			this.messageService.sendWrongTopicMessage(homeworkDtlInfo, homeworkSubmitMQ.getQuestions());
		}
		if (homeworkSubmitMQ.getFullScore()) {
			// 满分作业发送消息
			this.messageService.sendFullScoreLetterMessage(homeworkDtlInfo);
		}
		//如果是寒暑假作业，更新完成量
		if (homeworkDtlInfo.getHomeworkType().equals(HomeworkType.HOLIDAY.value)) {
			this.holidayHwMicroService.updateHwState(homeworkDtlInfo.getHomeworkDtlId());
		}
	}

	@Override
	public void resolveOnlineSubmitHomeworkWithTx(Long dataId) {
		logger.info("Online Test Resolve, dataId=" + dataId);
		// 1、获取作业数据信息
		HomeworkData homeworkData = this.homeworkDataDao.getHomeworkDataByDataId(dataId);
		if (homeworkData == null) {
			String message = "Online Test(" + dataId + ") data is not found.";
			logger.error(message);
			throw new MessageRequiredRepeatRunException(message);
		}
		if (homeworkData.getTargetHomeworkId() != null) {
			// 如果已经有目标作业ID，忽略该方法的执行
			return;
		}
		// 2、测试作业格式是否正确
		try {
			JsonUtils.readList(homeworkData.getAnswerData(), StudentAnswer.class);
		} catch (Exception e) {
			String message = "Online Test(" + dataId + ") data parse error.";
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}
		// 3、根据作业ID，找到并生成新作业
		Homework homework = this.homeworkDao.getHomeworkById(homeworkData.getHomeworkId());
		homework = this.homeworkDao.getHomeworkByBeikeGuid(homework.getBeikeGuid(), homework.getCourseSingleId()); // 找到最新的作业
		Boolean isRedo = homeworkData.getIsRedo();
		Boolean isPreview = homework.getUsePhase() == HomeworkCst.HOMEWORK_USE_PHASE_BEFORE;
		boolean retainAnswer = !Boolean.TRUE.equals(isRedo);
		if (isPreview) {
				// 如果最新的作业是预习作业，那么生成随堂作业
				LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(homework.getCourseSingleId());
				homework = this.homeworkSnapshotService.saveSnapshotAndNextPhase(homework.getHomeworkId(), HomeworkCst.HOMEWORK_USE_PHASE_LESSON,
								lesson.getStartTime(), lesson.getEndTime(), retainAnswer);
		} else {
			// 如果最新的作业是随堂作业，判断是否需要生成新作业
			if (homework.getFinishNum() > 0 && isRedo) {
				// 生成新随堂作业
				Long oldHomeworkId = homework.getHomeworkId();
				LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(homework.getCourseSingleId());
				homework = this.homeworkSnapshotService.saveSnapshotAndNextPhase(oldHomeworkId,
						HomeworkCst.HOMEWORK_USE_PHASE_LESSON, lesson.getStartTime(), lesson.getEndTime(),
						retainAnswer);
				/* 旧作业改名，暂时取消
				if (homeworkData.getVersion() != null && homeworkData.getVersion() > 1) {
					int version = homeworkData.getVersion().intValue() - 1;
					this.homeworkDao.updateOnlineTestIsHistory(oldHomeworkId, version);
				} **/
			}
		}
		if (homework != null) {
			this.homeworkDataDao.updateTargetHomeworkId(dataId, homework.getHomeworkId());
		}
	}

	@Override
	public void handleOnlineSubmitMessageWithTx(Long dataId) {
		logger.info("Online Test Submit, dataId=" + dataId);
		// 1、获取作业数据信息
		HomeworkData homeworkData = this.homeworkDataDao.getHomeworkDataByDataId(dataId);
		// 2、测试作业格式是否正确
		List<StudentAnswer> studentAnswerList;
		try {
			studentAnswerList = JsonUtils.readList(homeworkData.getAnswerData(), StudentAnswer.class);
		} catch (Exception e) {
			String message = "Online Test(" + dataId + ") data parse error.";
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}

		// 3、根据作业ID找到作业
		Homework homework = this.homeworkDao.getHomeworkById(homeworkData.getTargetHomeworkId());

		// 4、根据试卷ID获取试卷信息及结构
		PaperVisitor paper = PaperVisitor.build(homework.getPaperId());

		// 5、处理学生作业信息
		List<HomeworkDtlInfo> homeworkDtlInfoList = new ArrayList<HomeworkDtlInfo>();
		long currentTime = System.currentTimeMillis();
		for (StudentAnswer studentAnswer : studentAnswerList) {
			// a1、获取学生数据
			HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao
					.getHomeworkDtlInfoByHomeworkIdAndStudentId(homework.getHomeworkId(), studentAnswer.getStudentId());
			if (homeworkDtlInfo == null) {
				// 如果学生不存在，那么补上这个学生
				HomeworkDtl homeworkDtl = new HomeworkDtl();
				homeworkDtl.setHomeworkId(homework.getHomeworkId());
				homeworkDtl.setStudentId(studentAnswer.getStudentId());
				homeworkDtl.setStudentName(studentAnswer.getStudentName());
				homeworkDtl.setSubmitStatus(HomeworkCst.HOMEWORK_SUBMIT_STATUS_NOT);
				homeworkDtl.setSchoolId(homework.getSchoolId());
				homeworkDtl.setOrderTime(homework.getCloseTime());
				BaseModelHelper.fillInsertProps(homeworkDtl, studentAnswer.getStudentId());
				this.homeworkDtlDao.saveHomeworkDtl(homeworkDtl);
				this.homeworkDao.updateHomeworkTotalNum(homework.getHomeworkId());
				homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoByHomeworkIdAndStudentId(
						homework.getHomeworkId(), studentAnswer.getStudentId());
			}
			homeworkDtlInfoList.add(homeworkDtlInfo);
			// a2、数据结构转换
			Map<Long, QuestionResult> questionResultMap = AnswerUtils
					.transAnswerInfoToQuestionResultMap(studentAnswer.getAnswerInfos());
			// a3、合并旧的数据
			WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
			if (workDetail != null && workDetail.getQuestions() != null) {
				AnswerUtils.mergeOldIsRight(questionResultMap, workDetail.getQuestions());
			}
			// a4、填充没有提交的题目答题结构
			AnswerUtils.fillNullQuestionResult(questionResultMap, paper);
			// a5、批改学生答案中的客观题，获取客观题得分总和
			BigDecimal totalScore = AnswerUtils.correctObjectiveAnswer(questionResultMap, paper);
			// a6、保存学生作业答题信息
			List<QuestionResult> questions = new ArrayList<QuestionResult>(questionResultMap.values());
			List<GroupScore> queScores = this.buildQueGroupScores(questions, paper);
			List<GroupScore> knoScores = this.buildKnoGroupScores(questions, paper);
			int correctCount = (int) questions.stream().filter(v -> v.getTotalIsRight() != null).count();
			homeworkDtlInfo.setCorrectCount(correctCount);
			this.updateQuestionsByStudentSubmit(homeworkDtlInfo, questions, queScores, knoScores, paper, null);

			// a7、更新学生作业信息
			boolean fullScore = questions.stream().allMatch(decideIsRight);
			boolean fullCorrect = questions.stream().allMatch(v -> v.getTotalIsRight() != null);
			this.updateHomeworkDtlInfoBySubmit(homeworkDtlInfo, paper, totalScore,
					HomeworkCst.HOMEWORK_DATA_SOURCE_CLASS, fullScore, fullCorrect, new Date(currentTime));
			currentTime++;
		}
		// 6、更新老师作业统计
		this.updateHomeworkStat(homework.getHomeworkId());
		// 7、删除作业原始数据
		this.homeworkDataDao.deleteHomeworkData(dataId);

		// 8、重新获取数据库中最新的记录信息
		List<HomeworkDtl> homeworkDtlList = this.homeworkDtlDao
				.findHomeworkDtlListByHomeworkId(homework.getHomeworkId());
		homeworkDtlList = homeworkDtlList.stream().filter(v -> v.getSubmitTime() != null).collect(toList());

		// 10、发送错题到错题本
		for (HomeworkDtlInfo homeworkDtlInfo : homeworkDtlInfoList) {
			WorkDetail workDetail = workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlInfo.getHomeworkDtlId());
			this.messageService.sendWrongTopicMessage(homeworkDtlInfo, workDetail.getQuestions());
			this.messageService.sendDynamicInfoMessage(homeworkDtlInfo, false, !homeworkDtlInfo.getSubjective());
		}
	}

	// -------------------------------------------------------------------------------------------------------------

	@Override
	public void saveCorrectSnapshotWithBatch(Long homeworkDtlId, QuestionResult questionResultNew) {
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByBatchCorrect(homeworkDtlId,
				questionResultNew.getQuestionId());
		QuestionResult questionResult = workDetail.getQuestions().get(0);
		List<QuestionResult> newQuestionResults = new ArrayList<QuestionResult>();
		newQuestionResults.add(questionResultNew);
		List<Long> newWrongQuestions = findNewAddWrongQuestions(workDetail.getQuestions(), newQuestionResults);
		//题目是否未批改过  ->  未批改过 = !重批  批改过 = 重批
		boolean isIncrProgress = questionResult.getTotalIsRight() == null;
		AnswerUtils.mergeQuestionResult(questionResult, questionResultNew);
		if (questionResult.getSubs() != null) {
			questionResult.setTotalIsRight(questionResult.getSubs().stream().allMatch(decideIsRight));
		}

		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);
		PaperVisitor paper = getPaperVisitor(homeworkDtlInfo.getPaperId(), homeworkDtlInfo.getHwPaperId());
		AnswerUtils.fillQuestionResultNullable(questionResult, paper);

		this.workDetailDao.updateQuestionWithBatchCorrect(workDetail, isIncrProgress);
		boolean isAllCorrect = false;
		if (isIncrProgress) {
			this.messageService.sendHomeworkProgressMessage(homeworkDtlId, workDetail.getCorrectCount() + 1);
			isAllCorrect = workDetail.getCorrectCount() + 1 >= paper.getQuestionNum();
		} else {
			isAllCorrect = workDetail.getCorrectCount() >= paper.getQuestionNum();
		}
		//所有题目批改完成时
		if (isAllCorrect) {
			this.messageService.sendHomeworkCorrectMessage(homeworkDtlId, !isIncrProgress, newWrongQuestions);
		}
	}

	// 更新学生作业的批改信息
	@Override
	public HomeworkDtlInfo saveCorrectSubmit(WorkRequest request) {

		// 1、查询作业信息和试卷信息
		Long homeworkDtlId = request.getHomeworkDtlId();
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);

		HomeworkUtils.validateCorrectAccess(request.getUserId(), homeworkDtlInfo);
		if (homeworkDtlInfo.getCorrectUserId() != null && homeworkDtlInfo.getTeacherId() == request.getUserId()
				&& homeworkDtlInfo.getCorrectTime() == null) {
			homeworkDtlInfo.setCorrectUserId(null);
		}
		homeworkDtlInfo.setCorrectUserId(request.getUserId());
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlId);
		List<Long> newWrongQuestions = this.findNewAddWrongQuestions(workDetail.getQuestions(),
				request.getQuestionResultList());
		List<QuestionResult> questions = workDetail.getQuestions();
		Map<Long, QuestionResult> questionResultMap = questions.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
		// 2、处理JSON格式数据合并，更新详细信息
		for (QuestionResult questionResultNew : request.getQuestionResultList()) {
			QuestionResult questionResultOld = questionResultMap.get(questionResultNew.getQuestionId());
			AnswerUtils.mergeQuestionResult(questionResultOld, questionResultNew);
		}
		PaperVisitor paper = getPaperVisitor(homeworkDtlInfo.getPaperId(), homeworkDtlInfo.getHwPaperId());
		AnswerUtils.fillQuestionResultNullable(questionResultMap, paper);

		BigDecimal score = ScoreUtils.roundScore(questions.stream().map(QuestionResult::getTotalResultScore)
				.reduce(new BigDecimal(0), (a, b) -> a.add(b)));
		score = score.compareTo(paper.getTotalScore()) > 0 ? paper.getTotalScore() : score;
		BigDecimal scoreRate = ScoreUtils.roundScoreRate(score.divide(paper.getTotalScore(), 5, RoundingMode.HALF_UP));
		workDetail.setCorrectCount(paper.getQuestionNum());
		workDetail.setQuestionNum(paper.getQuestionNum());
		workDetail.setScore(score);
		workDetail.setScoreRate(scoreRate);
		workDetail.setCommentary(request.getCommentary());
		workDetail.setModifiedBy(homeworkDtlInfo.getModifiedBy());
		workDetail.setModifiedOn(new Date());
		workDetail.setQuestions(new ArrayList<QuestionResult>(questionResultMap.values()));
		workDetail.setQueScores(this.buildQueGroupScores(questions, paper));
		workDetail.setKnoScores(this.buildKnoGroupScores(questions, paper));

		this.handleHomeworkCorrect(homeworkDtlInfo, workDetail, paper, request.getDataSource(), newWrongQuestions);

		return homeworkDtlInfo;
	}

	/**
	 * @param homeworkDtlInfo
	 * @return
	 */
	private PaperVisitor getPaperVisitor(Long paperId, String hwPaperId) {
		PaperVisitor paper = PaperVisitor.build(paperId);
		if (paper == null) {
			paper = PaperVisitor.build(HomeworkUtils.buildPaperDTO(hwPaperId));
		}
		return paper;
	}

	@Override
	public void handleHomeworkCorrectMessageWithTx(HomeworkCorrectMQ homeworkCorrectMQ) {
		Long homeworkDtlId = homeworkCorrectMQ.getHomeworkDtlId();
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlId);

		PaperVisitor paper = getPaperVisitor(homeworkDtlInfo.getPaperId(), homeworkDtlInfo.getHwPaperId());
		BigDecimal score = ScoreUtils.roundScore(workDetail.getQuestions().stream()
				.map(QuestionResult::getTotalResultScore).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
		score = score.compareTo(paper.getTotalScore()) > 0 ? paper.getTotalScore() : score;
		BigDecimal scoreRate = ScoreUtils.roundScoreRate(score.divide(paper.getTotalScore(), 5, RoundingMode.HALF_UP));

		workDetail.setCorrectCount(paper.getQuestionNum());
		workDetail.setQuestionNum(paper.getQuestionNum());
		workDetail.setScore(score);
		workDetail.setScoreRate(scoreRate);
		workDetail.setCommentary(null);
		workDetail.setModifiedBy(homeworkDtlInfo.getModifiedBy());
		workDetail.setModifiedOn(new Date());
		workDetail.setQueScores(this.buildQueGroupScores(workDetail.getQuestions(), paper));
		workDetail.setKnoScores(this.buildKnoGroupScores(workDetail.getQuestions(), paper));

		this.handleHomeworkCorrect(homeworkDtlInfo, workDetail, paper, HomeworkCst.HOMEWORK_DATA_SOURCE_WEBSITE,
				homeworkCorrectMQ.getNewWrongQuestions());
		if (!homeworkCorrectMQ.getIsAgain()) {
			UserBase userBase = UserBaseContext.getUserBaseByUserId(homeworkDtlInfo.getTeacherId());
			DynamicInfo dynamicInfo = new DynamicInfo();
			dynamicInfo.setDynamicType(DynamicTypes.HW_CORRECT_WORK);
			dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
			dynamicInfo.setUserId(userBase.getId());
			dynamicInfo.setUserName(userBase.getUserName());
			dynamicInfo.setRoleId(RoleCst.TEACHER);
			dynamicInfo.setSchoolId(homeworkDtlInfo.getSchoolId());
			DynamicHelper.publish(dynamicInfo);
		}
	}

	private HomeworkDtlInfo handleHomeworkCorrect(HomeworkDtlInfo homeworkDtlInfo, WorkDetail workDetail,
			PaperVisitor paper, Integer correctSource, List<Long> newWrongQuestions) {
		Boolean isAgainCorrect = homeworkDtlInfo.getScore() != null && homeworkDtlInfo.getCorrectTime() != null;
		Boolean isFullScore = workDetail.getQuestions().stream().allMatch(decideIsRight);
		Integer oldBugfixStage = homeworkDtlInfo.getBugFixStage();

		setHomeworkCorrectData(homeworkDtlInfo, workDetail, paper, correctSource, isAgainCorrect, isFullScore);
		Boolean isBugFix = isAllowBugfix(homeworkDtlInfo);
		if (isBugFix) {
			doBugfixHomeworkData(homeworkDtlInfo, workDetail, newWrongQuestions, isFullScore, isAgainCorrect);
		}

		boolean isNoBugFix = isFullScore;
		if (!isFullScore && homeworkDtlInfo.getBugFixStage().equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_FINISH)) {
			isNoBugFix = true;
		}
		if (!oldBugfixStage.equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW)
				|| CollectionUtils.isNotEmpty(newWrongQuestions)) {
			this.messageService.sendFinishCorrectMessage(homeworkDtlInfo, isNoBugFix, isBugFix);
		}

		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);

		// 5、更新老师作业情况
		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());
        //处理微课批注信息
		HwQueCommentaryService.removeMicroComments(workDetail.getQuestions());
		this.workDetailDao.updateQuestionsAsCorrect(workDetail);

		// 6、发消息，重批改不发送任何消息
		if (!isAgainCorrect) {
			sendFirstCorrectMessage(homeworkDtlInfo, workDetail, isFullScore);
		}

		return null;
	}

	/**
	 * @param homeworkDtlInfo
	 * @param workDetail
	 * @param paper
	 * @param correctSource
	 * @param isAgainCorrect
	 * @return
	 */
	private void setHomeworkCorrectData(HomeworkDtlInfo homeworkDtlInfo, WorkDetail workDetail, PaperVisitor paper,
			Integer correctSource, Boolean isAgainCorrect, Boolean isFullScore) {
		if (isFullScore) {
			workDetail.setScore(paper.getTotalScore());
			workDetail.setScoreRate(new BigDecimal(1));
		}
		// 4、更新学生作业情况
		homeworkDtlInfo.setCorrectCount(paper.getQuestionNum());
		homeworkDtlInfo.setSoundFile(workDetail.getCommentary());
		if (!isAgainCorrect) {
			homeworkDtlInfo.setCorrectTime(new Date());
		}
		homeworkDtlInfo.setCorrectSource(correctSource);
		homeworkDtlInfo.setScore(workDetail.getScore());
		homeworkDtlInfo.setScoreRate(workDetail.getScoreRate());
		Long errorTotal = workDetail.getQuestions().stream().filter(v -> Boolean.FALSE.equals(v.getTotalIsRight()))
				.count();
		homeworkDtlInfo.setErrorTotal(errorTotal);
	}

	/**
	 * @param homeworkDtlInfo
	 * @param workDetail
	 * @param newWrongQuestions
	 * @param isFullScore
	 * @param isAgainCorrect
	 * @return
	 */
	private void doBugfixHomeworkData(HomeworkDtlInfo homeworkDtlInfo, WorkDetail workDetail,
			List<Long> newWrongQuestions, Boolean isFullScore, Boolean isAgainCorrect) {
		Integer oldBugfixStage = homeworkDtlInfo.getBugFixStage();
		homeworkDtlInfo.setBugFixStage(
				isFullScore ? HomeworkCst.HOMEWORK_BUGFIX_STAGE_NORMAL : HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX);
		if (homeworkDtlInfo.getBugFixCount() != 0 && isAgainCorrect && !isFullScore) {
			//顺序不能改变 mergeBugFixDataToAnswerInfos 之前
			mergeBugFixForNewWrongQuestions(workDetail, newWrongQuestions);
			if (CollectionUtils.isNotEmpty(newWrongQuestions)
					&& oldBugfixStage.equals(HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW)) {
				mergeBugFixDataToAnswerInfos(workDetail);
			}

			List<QuestionResult> questionResults = workDetail.getQuestions().stream().flatMap(v -> {
				if (CollectionUtils.isNotEmpty(v.getSubs())) {
					return v.getSubs().stream();
				} else {
					return Stream.of(v);
				}
			}).filter(v -> Boolean.FALSE.equals(v.getTotalIsRight())).collect(Collectors.toList());
			if (questionResults.stream().allMatch(v -> Boolean.TRUE.equals(v.getIsPassedFix()))) {
				homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_FINISH);
			} else if (CollectionUtils.isEmpty(newWrongQuestions)
					&& questionResults.stream().anyMatch(v -> v.getIsPassedFix() == null)) {
				homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW);
			}
		}

	}

	/**
	 * @param homeworkDtlInfo
	 * @param workDetail
	 * @param isFullScore
	 * @param isAgainCorrect
	 */
	private void sendFirstCorrectMessage(HomeworkDtlInfo homeworkDtlInfo, WorkDetail workDetail, Boolean isFullScore) {
		// 重新批改和答题卡不发送消息
		this.messageService.sendWrongTopicMessage(homeworkDtlInfo, workDetail.getQuestions());
		// 重新批改不发送消息
		this.messageService.sendDynamicInfoMessage(homeworkDtlInfo, false, true);

		if (HomeworkType.VOD.value == homeworkDtlInfo.getHomeworkType()) {
			this.messageService.sendHomeworkVodMessage(homeworkDtlInfo);
		}
		this.messageService.sendCorrectLetterMessage(homeworkDtlInfo.getStudentId(), homeworkDtlInfo.getHomeworkName());

		if (isFullScore) {
			// 满分作业发送消息
			this.messageService.sendFullScoreLetterMessage(homeworkDtlInfo);
		}
	}

	/**
	 * 对比重批前后，新产生的错题
	 * @return
	 */
	private List<Long> findNewAddWrongQuestions(List<QuestionResult> oldQuestionResult,
			List<QuestionResult> newQuestionResult) {
		if (oldQuestionResult != null && newQuestionResult != null) {
			Predicate<QuestionResult> filter = v -> Boolean.FALSE.equals(v.getTotalIsRight());
			List<Long> newQuestionList = newQuestionResult.stream().flatMap(v -> {
				if (CollectionUtils.isEmpty(v.getSubs())) {
					return Stream.of(v);
				} else {
					return Stream.concat(Stream.of(v), v.getSubs().stream());
				}
			}).filter(filter).map(v -> v.getQuestionId()).collect(Collectors.toList());
			List<Long> oldQuestionList = oldQuestionResult.stream().flatMap(v -> {
				if (CollectionUtils.isEmpty(v.getSubs())) {
					return Stream.of(v);
				} else {
					return Stream.concat(Stream.of(v), v.getSubs().stream());
				}
			}).filter(filter).map(v -> v.getQuestionId()).collect(Collectors.toList());
			if (newQuestionList.size() == 0) {
				return null;
			} else {
				return newQuestionList.stream().filter(v -> !oldQuestionList.contains(v)).collect(Collectors.toList());
			}
		}
		return null;
	}

	/**
	 * 将订正记录中最后一条移到到暂存答案中
	 * @param workDetail
	 */
	private void mergeBugFixDataToAnswerInfos(WorkDetail workDetail) {
		if (workDetail != null && CollectionUtils.isEmpty(workDetail.getAnswerInfos())) {
			List<AnswerInfo> answerInfos = new ArrayList<AnswerInfo>();
			workDetail.getQuestions().stream().forEach(item -> {
				AnswerInfo answerInfo = new AnswerInfo();
				answerInfo.setQuestionId(item.getQuestionId());
				if (item.getSubs() != null && item.getSubs().size() > 0) {
					answerInfo.setSubs(item.getSubs().stream()
							.filter(s -> s.getIsPassedFix() == null && s.getBugfixs().size() > 0).map(s -> {
								AnswerInfo sub = new AnswerInfo();
								sub.setQuestionId(s.getQuestionId());
								sub.setAnswerContent(getBugFixLast(s.getBugfixs()));
								return sub;
							}).collect(Collectors.toList()));
				} else if (item.getIsPassedFix() == null) {
					answerInfo.setAnswerContent(getBugFixLast(item.getBugfixs()));
				}
				if (answerInfo.getAnswerContent() != null || CollectionUtils.isNotEmpty(answerInfo.getSubs())) {
					answerInfos.add(answerInfo);
				}
			});
			workDetail.setAnswerInfos(answerInfos);
		}
	}

	/**
	 * 对于新增的错题进行处理订正记录
	 * 有订正记录的，对其进行订正是否通过 标记为 false
	 * @param workDetail
	 */
	private void mergeBugFixForNewWrongQuestions(WorkDetail workDetail, List<Long> newWrongQuestions) {
		if (CollectionUtils.isNotEmpty(newWrongQuestions)) {
			//重批有新的错题产生
			workDetail.getQuestions().forEach(item -> {
				if (newWrongQuestions.contains(item.getQuestionId())) {
					item.setIsPassedFix(false);
				}
				if (CollectionUtils.isNotEmpty(item.getSubs())) {
					item.getSubs().stream().filter(sub -> newWrongQuestions.contains(sub.getQuestionId()))
							.forEach(sub -> {
								sub.setIsPassedFix(false);
							});
					if (item.getSubs().stream().anyMatch(sub -> newWrongQuestions.contains(sub.getQuestionId()))) {
						item.setIsPassedFix(false);
					}
				}
			});
		}
	}

	/**
	 * 查找订正记录最后一条
	 * @param bugfixs
	 * @return
	 */
	private String getBugFixLast(List<List<AnswerResult>> bugfixs) {
		if (CollectionUtils.isEmpty(bugfixs)) {
			return null;
		}
		List<AnswerResult> lastAnswerResults = bugfixs.get(bugfixs.size() - 1);
		String content = JsonUtils
				.toJSON(lastAnswerResults.stream().map(v -> v.getMyAnswer()).collect(Collectors.toList()));
		bugfixs.remove(lastAnswerResults);
		return content;
	}

	// 更新批改进度
	@Override
	public void handleCorrectProgressMessageWithTx(HomeworkProgressMQ homeworkProgressMQ) {
		this.homeworkDtlDao.updateHomeworkDtlProgress(homeworkProgressMQ);
	}

	@Override
	public void handleMutualCorrectSubmitMessageWithTx(Long dataId) {
		logger.info("Online Mutual Correct Submit, dataId=" + dataId);
		// 1、获取作业数据信息
		HomeworkData homeworkData = this.homeworkDataDao.getHomeworkDataByDataId(dataId);
		List<OnlineCorrectHomework> onlineCorrectHomeworks = JsonUtils.readList(homeworkData.getAnswerData(),
				OnlineCorrectHomework.class);
		Homework homework = this.homeworkDao.getHomeworkById(homeworkData.getHomeworkId());
		List<HomeworkDtl> homeworkDtls = this.homeworkDtlDao
				.findHomeworkDtlListByHomeworkId(homeworkData.getHomeworkId());
		PaperVisitor paper = PaperVisitor.build(homework.getPaperId());

		List<HwCorrectEvent> hwCorrectEvents = new ArrayList<HwCorrectEvent>();
		List<DynamicInfo> dynamicInfos = new ArrayList<DynamicInfo>();
		List<LetterMessage> fullScoreLetterMessages = new ArrayList<LetterMessage>();
		List<WrongMQ> wrongMQs = new ArrayList<WrongMQ>();
		for (HomeworkDtl homeworkDtl : homeworkDtls) {
			HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao
					.getHomeworkDtlInfoById(homeworkDtl.getHomeworkDtlId());
			if (homeworkDtlInfo.getSubmitTime() == null) {
				continue;
			}
			WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtl.getHomeworkDtlId());
			Optional<List<QuestionResult>> optionalResults = onlineCorrectHomeworks.stream()
					.filter(v -> v.getStudentId().equals(homeworkDtl.getStudentId())).map(v -> v.getQuestionResults())
					.findFirst();
			if (!optionalResults.isPresent() || CollectionUtils.isEmpty(optionalResults.get())) {
				continue;
			}
			if (homeworkDtlInfo.getHwPaperId() != null) {
				paper = PaperVisitor.build(HomeworkUtils.buildPaperDTO(homeworkDtlInfo.getHwPaperId()));
			}

			List<QuestionResult> questions = workDetail.getQuestions();
			Map<Long, QuestionResult> questionResultMap = questions.stream()
					.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
			// 2、处理JSON格式数据合并，更新详细信息
			for (QuestionResult questionResultNew : optionalResults.get()) {
				QuestionResult questionResultOld = questionResultMap.get(questionResultNew.getQuestionId());
				AnswerUtils.mergeQuestionResult(questionResultOld, questionResultNew);
			}
			AnswerUtils.fillQuestionResultNoCorrect(questionResultMap);
			AnswerUtils.fillQuestionResultNullable(questionResultMap, paper);

			Boolean isFullScore = workDetail.getQuestions().stream().allMatch(decideIsRight);
			if (isFullScore) {
				workDetail.setScore(paper.getTotalScore());
				workDetail.setScoreRate(new BigDecimal(1));
			}
			// 4、更新学生作业情况
			homeworkDtlInfo.setCorrectCount(paper.getQuestionNum());
			homeworkDtlInfo.setSoundFile(workDetail.getCommentary());
			homeworkDtlInfo.setCorrectTime(new Date());
			homeworkDtlInfo.setCorrectSource(HomeworkCst.HOMEWORK_DATA_SOURCE_CLASS);
			homeworkDtlInfo.setScore(workDetail.getScore());
			homeworkDtlInfo.setScoreRate(workDetail.getScoreRate());
			Long errorTotal = workDetail.getQuestions().stream().filter(v -> Boolean.FALSE.equals(v.getTotalIsRight()))
					.count();
			homeworkDtlInfo.setErrorTotal(errorTotal);
			if (homework.getUsePhase() != 1 && homework.getUsePhase() != 2) {
				doBugfixHomeworkData(homeworkDtlInfo, workDetail, null, isFullScore, false);
			}
			this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);
			this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());
			this.workDetailDao.updateQuestionsAsCorrect(workDetail);

			WrongMQ wrongMQ = messageService.createWrongMQ(homeworkDtlInfo, optionalResults.get());
			if (wrongMQ != null) {
				wrongMQs.add(wrongMQ);
			}
			hwCorrectEvents.add(this.messageService.createHwCorrectEvent(homeworkDtlInfo, isFullScore));
			dynamicInfos.add(messageService.createDynamicInfo(homeworkDtlInfo, false, true));
			if (isFullScore) {
				// 满分作业发送消息
				fullScoreLetterMessages.add(this.messageService.createFullScoreMessage(homeworkDtlInfo));
			}
		}

		homeworkDtls
				.forEach(v -> messageService.sendCorrectLetterMessage(v.getStudentId(), homework.getHomeworkName()));
		hwCorrectEvents.forEach(NoticeHelper::todo);
		dynamicInfos.forEach(DynamicHelper::publish);
		wrongMQs.forEach(this.wrongtopicSender::send);
	}

	@Override
	public void saveBugFixSnapshot(WorkRequest request) {
		this.workDetailDao.updateAnswerInfos(request.getHomeworkDtlId(), request.getAnswerInfoList());
	}

	@Override
	public HomeworkDtlInfo saveBugFixSubmit(WorkRequest request) {
		// 1、业务验证
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(request.getHomeworkDtlId());
		Validation.isFalse(homeworkDtlInfo.getBugFixStage() != HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX, "作业已订正");

		// 2、并发控制(乐观锁)
		this.tryHoldOptimisticLocking(request.getHomeworkDtlId(), homeworkDtlInfo.getVersion());

		// 3、获取已有数据
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(request.getHomeworkDtlId());

		//  未订正的题目信息，如果没有传过来，自动补全，默认是未答题
		this.fillNoBugFixs(workDetail.getQuestions(), request);
		// 4、合并学生答题数据并更新它
		this.mergeBugfixs(workDetail.getQuestions(), request.getAnswerInfoList(), null);

		// 5、修改学生作业信息
		homeworkDtlInfo.setBugFixCount(homeworkDtlInfo.getBugFixCount() + 1);
		homeworkDtlInfo.setBugFixSource(request.getDataSource());
		homeworkDtlInfo.setBugFixTime(new Date());
		homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW);
		homeworkDtlInfo.setModifiedBy(request.getUserId());
		homeworkDtlInfo.setModifiedOn(new Date());

		boolean isTotalReview = workDetail.getQuestions().stream()
				.flatMap(v -> v.getSubs() != null ? v.getSubs().stream() : Stream.of(v))
				.filter(v -> !Boolean.TRUE.equals(v.getTotalIsRight())).allMatch(v -> v.getIsPassedFix() != null);
		if (isTotalReview) {
			// 已全部复批，生成复批信息
			homeworkDtlInfo.setReviewSource(request.getDataSource());
			homeworkDtlInfo.setReviewTime(new Date());
		}

		boolean isTotalPassedFix = workDetail.getQuestions().stream().allMatch(decideIsPassedFix);
		if (isTotalPassedFix) {
			homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_FINISH); // 全部通过
		} else {
			if (isTotalReview) { // 全部批改
				homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX); // 待订正
			} else {
				homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW); // 待复批
			}
		}

		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);

		this.updateHomeworkStat(homeworkDtlInfo.getHomeworkId());

		this.workDetailDao.updateQuestionsAsBugFix(request.getHomeworkDtlId(), workDetail.getQuestions(),
				request.getUserId());

		if (isTotalPassedFix) {
			this.messageService.sendReviewPassedLetterMessage(homeworkDtlInfo);
		}

		this.messageService.sendBugFixTodoMessage(homeworkDtlInfo, isTotalPassedFix, !isTotalReview);

		return homeworkDtlInfo;
	}

	@Override
	public HomeworkDtlInfo saveReviewSubmit(WorkRequest request) {
		Long homeworkDtlId = request.getHomeworkDtlId();
		// 1、业务验证
		HomeworkDtlInfo homeworkDtlInfo = this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);
		Validation.isFalse(homeworkDtlInfo.getBugFixStage() != HomeworkCst.HOMEWORK_BUGFIX_STAGE_REVIEW, "作业已复批");

		// 2、并发控制(乐观锁)
		this.tryHoldOptimisticLocking(homeworkDtlId, homeworkDtlInfo.getVersion());

		// 3、获取作业数据
		WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlId);

		// 4、合并复批数据
		boolean notMergeComments = request.getDataSource() != null
				&& request.getDataSource() == HomeworkCst.HOMEWORK_DATA_SOURCE_PAD;
		this.mergeReviews(workDetail.getQuestions(), request.getQuestionResultList(), notMergeComments);
		boolean isTotalPassedFix = workDetail.getQuestions().stream().allMatch(decideIsPassedFix);

		// 5、修改学生作业：复批时间、复批来源、订正阶段、订正次数
		homeworkDtlInfo.setReviewTime(new Date());
		homeworkDtlInfo.setReviewSource(request.getDataSource());
		if (isTotalPassedFix) {
			homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_FINISH);
		} else {
			homeworkDtlInfo.setBugFixStage(HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX);
		}
		homeworkDtlInfo.setModifiedOn(new Date());
		this.homeworkDtlDao.updateHomeworkDtlInfo(homeworkDtlInfo);

		// 6、修改老师作业：订正提交人数、订正通过人数
		this.homeworkDao.updateHomeworkStatWithBugFix(homeworkDtlInfo.getHomeworkId());

		// 7、更新作业数据
		this.workDetailDao.updateQuestions(homeworkDtlId, workDetail.getQuestions(), request.getUserId());

		// 8、消息发送
		if (isTotalPassedFix) {
			this.messageService.sendReviewPassedLetterMessage(homeworkDtlInfo);
		} else {
			// 复批不通过的发送待办
			this.messageService.sendReviewTodoMessage(homeworkDtlInfo);
		}

		return homeworkDtlInfo;
	}

	private void mergeBugfixs(List<QuestionResult> questionResults, List<AnswerInfo> answerInfos,
			List<QuestionDTO> questions) {
		if (questions == null) {
			List<Long> questionIds = questionResults.stream().map(QuestionResult::getQuestionId).collect(toList());
			questions = QuestionContext.findQuestions(questionIds);
		}
		Map<Long, QuestionDTO> questionMap = questions.stream().collect(toMap(QuestionDTO::getQuestionId, v -> v));
		Map<Long, AnswerInfo> answerInfoMap = answerInfos.stream().collect(toMap(AnswerInfo::getQuestionId, v -> v));
		questionResults.stream().filter(v -> answerInfoMap.containsKey(v.getQuestionId())).forEach(questionResult -> {
			QuestionDTO question = questionMap.get(questionResult.getQuestionId());
			AnswerInfo answerInfo = answerInfoMap.get(questionResult.getQuestionId());
			if (questionResult.getSubs() != null) {
				this.mergeBugfixs(questionResult.getSubs(), answerInfo.getSubs(), question.getSubs());
				Predicate<QuestionResult> filter = v -> !Boolean.TRUE.equals(v.getTotalIsRight());
				Predicate<QuestionResult> matcher = v -> Boolean.TRUE.equals(v.getIsPassedFix());
				boolean isTotalPassedFix = questionResult.getSubs().stream().filter(filter).allMatch(matcher);
				questionResult.setIsPassedFix(isTotalPassedFix);
			} else if (answerInfo != null) {
				List<String> textList = AnswerUtils.parseAnswerContent(answerInfo.getAnswerContent());
				List<AnswerResult> answerResults = textList.stream().map(myAnswer -> {
					AnswerResult answerResult = new AnswerResult();
					answerResult.setMyAnswer(myAnswer);
					return answerResult;
				}).collect(toList());
				questionResult.getBugfixs().add(answerResults);
				QuestionCheckAdapter.review(question, questionResult);
			}
		});
	}

	/**
	 * 补全错题中的订正的信息(客户端订正题目提交数据补全)
	 * @param questions 完整的题目信息
	 * @param answerInfos  提交过来的订正的题目信息
	 */
	private void fillNoBugFixs(List<QuestionResult> questions, WorkRequest request) {
		List<AnswerInfo> answerInfos = request.getAnswerInfoList();
		List<AnswerInfo> list = answerInfos.stream().flatMap(
						v -> CollectionUtils.isNotEmpty(v.getSubs()) ? Stream.concat(Stream.of(v), v.getSubs().stream())
								: Stream.of(v)).collect(toList());

		answerInfos = questions.stream().filter(v->!Boolean.TRUE.equals(v.getTotalIsRight()) && !Boolean.TRUE.equals(v.getIsPassedFix())).map(v->{
			AnswerInfo item = new AnswerInfo();
			item.setQuestionId(v.getQuestionId());
			if(CollectionUtils.isNotEmpty(v.getSubs())) {
				List<AnswerInfo> subs = v.getSubs().stream().filter(w->!Boolean.TRUE.equals(w.getTotalIsRight()) && !Boolean.TRUE.equals(w.getIsPassedFix()) ).map(w->{
					AnswerInfo subItem = new AnswerInfo();
					subItem.setQuestionId(w.getQuestionId());
					Optional<AnswerInfo> first = list.stream().filter(l->l.getQuestionId().equals(w.getQuestionId())).findFirst();
					subItem.setAnswerContent(!first.isPresent() ? null : first.get().getAnswerContent());
					return subItem;
				}).collect(toList());
				item.setSubs(subs);
			}else {
				Optional<AnswerInfo> first = list.stream().filter(l->l.getQuestionId().equals(v.getQuestionId())).findFirst();
				item.setAnswerContent(!first.isPresent() ? null : first.get().getAnswerContent());
			}
			return item;
		}).collect(toList());
		request.setAnswerInfoList(answerInfos);
	}

	private void mergeReviews(List<QuestionResult> questions, List<QuestionResult> reviewInfos,
			boolean notMergeComments) {
		Map<Long, QuestionResult> reviewInfoMap = reviewInfos.stream()
				.collect(toMap(QuestionResult::getQuestionId, Function.identity()));
		questions.stream().filter(v -> reviewInfoMap.containsKey(v.getQuestionId())).forEach(questionResult -> {
			QuestionResult reviewInfo = reviewInfoMap.get(questionResult.getQuestionId());
			if (questionResult.getSubs() != null) {
				this.mergeReviews(questionResult.getSubs(), reviewInfo.getSubs(), notMergeComments);
				Predicate<QuestionResult> filter = v -> !Boolean.TRUE.equals(v.getTotalIsRight());
				Predicate<QuestionResult> matcher = v -> Boolean.TRUE.equals(v.getIsPassedFix());
				boolean isTotalPassedFix = questionResult.getSubs().stream().filter(filter).allMatch(matcher);
				questionResult.setIsPassedFix(isTotalPassedFix);
			} else if (reviewInfo != null) {
				questionResult.setIsPassedFix(reviewInfo.getIsPassedFix());
				if (!notMergeComments) {
					questionResult.setCorrectComments(reviewInfo.getCorrectComments());
				}
			}
		});
	}

	public void saveReviewSubmitWithBatch(WorkRequest request) {
		// 1、获取需要复批的作业ID
		List<Long> homeworkDtlIds = this.homeworkDtlDao.getPreReviewIdByHomeworkId(request.getHomeworkId());
		if (homeworkDtlIds.isEmpty()) {
			return;
		}
		Homework homework = this.homeworkDao.getHomeworkById(request.getHomeworkId());

		// 2、循环处理作业明细通过
		List<Long> userIds = new ArrayList<Long>();
		List<WorkDetail> details = new ArrayList<>();
		List<HwDtlInfo> hwDtlInfos = new ArrayList<>();
		for (Long homeworkDtlId : homeworkDtlIds) {
			WorkDetail workDetail = this.workDetailDao.getWorkDetailByHomeworkDtlId(homeworkDtlId);
			// 处理有子题的题目
			workDetail.getQuestions().stream().filter(v -> CollectionUtils.isNotEmpty(v.getSubs())).forEach(v -> {
				// 找到子题中不正确且订正状态未知的题目，直接设置为通过
				v.getSubs().stream().filter(q -> !Boolean.TRUE.equals(q.getTotalIsRight()))
						.filter(q -> q.getIsPassedFix() == null).forEach(p -> p.setIsPassedFix(true));
				// 统计子题是否全部正确或订正通过，并设置到父题中
				boolean isPassedFix = v.getSubs().stream().filter(q -> !Boolean.TRUE.equals(q.getTotalIsRight()))
						.allMatch(q -> Boolean.TRUE.equals(q.getIsPassedFix()));
				v.setIsPassedFix(isPassedFix);
			});
			// 处理无子题的题目，找到不正确且订正状态未知的题目，直接设置为通过
			workDetail.getQuestions().stream().filter(v -> !CollectionUtils.isNotEmpty(v.getSubs()))
					.filter(q -> !Boolean.TRUE.equals(q.getTotalIsRight())).filter(q -> q.getIsPassedFix() == null)
					.forEach(p -> p.setIsPassedFix(true));
			// 统计是否所有题目都正确或订正通过
			boolean isPassedFix = workDetail.getQuestions().stream()
					.allMatch(v -> Boolean.TRUE.equals(v.getTotalIsRight()) || Boolean.TRUE.equals(v.getIsPassedFix()));

			// 更新学生作业复批状态
			Integer bugFixStatge = HomeworkCst.HOMEWORK_BUGFIX_STAGE_BUGFIX;
			if (isPassedFix) {
				userIds.add(workDetail.getStudentId());
				bugFixStatge = HomeworkCst.HOMEWORK_BUGFIX_STAGE_FINISH;
			} else {
				HwDtlInfo hwDtlInfo = new HwDtlInfo();
				hwDtlInfo.setStudentId(workDetail.getStudentId());
				hwDtlInfo.setHomeworkDtlId(homeworkDtlId);
				hwDtlInfos.add(hwDtlInfo);
			}
			this.homeworkDtlDao.updateBugFixStageWithReview(homeworkDtlId, bugFixStatge, request.getDataSource(),
					request.getUserId());
			details.add(workDetail);
		}
		// 3、修改老师作业复批数量
		this.homeworkDao.updateHomeworkStatWithBugFix(request.getHomeworkId());
		// 4、更新MongoDB中的作业信息
		details.forEach(workDetail -> {
			workDetailDao.updateQuestions(workDetail.getHomeworkDtlId(), workDetail.getQuestions(),
					request.getUserId());
		});
		// 5、发送消息
		this.messageService.sendReviewPassedLetterMessage(userIds, homework.getHomeworkName());

		this.messageService.sendReviewTodoMessage(homework, hwDtlInfos);
	}

	@Override
	public Award sendIncTypeForSubmitHomework(HomeworkDtlInfo homeworkDtlInfo) {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(homeworkDtlInfo.getStudentId());
		dynamicInfo.setUserName(homeworkDtlInfo.getStudentName());
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(homeworkDtlInfo.getSchoolId());
		dynamicInfo.setTitle(homeworkDtlInfo.getHomeworkName());
		// 激励
		dynamicInfo.setTypeId(IncentiveTypes.STUDENT.HW_HOMEWORK_FINISH);
		int state = ScoreUtils.toState(homeworkDtlInfo.getScoreRate());
		dynamicInfo.setState(state);
		return DynamicHelper.publish(dynamicInfo);
	}

	@Override
	public void cuijiaoHomework(Long homeworkId) {
		Homework homework = homeworkDao.getHomeworkById(homeworkId);
		List<HomeworkDtl> homeworkDtls = homeworkDtlDao.findHomeworkDtlListByHomeworkId(homeworkId);
		List<String> toStudents = homeworkDtls.stream().filter(v -> v.getSubmitTime() == null)
				.map(v -> v.getStudentId().toString()).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(homeworkDtls)) {
			LetterMessage message = new LetterMessage();
			message.setBusinessType(MessageBusinessTypes.HOMEWORK);
			message.setSubject("作业通知");
			message.setContent(ParametersContext.getString(HomeworkCst.HOMEWORK__CUIJIAO_TEMPLETE));
			message.addVariable("1", homework.getHomeworkName());
			message.addTo(toStudents);
			NoticeHelper.send(message);
		}
	}
	
	@Override
	public Long getHomeworkDataTotal(Long homeworkId, Long version){
		return this.homeworkDataDao.getHomeworkDataTotal(homeworkId, version);
	}
}
