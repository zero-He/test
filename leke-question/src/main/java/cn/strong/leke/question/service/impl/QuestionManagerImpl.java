/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import static cn.strong.leke.common.utils.CollectionUtils.isEmpty;
import static cn.strong.leke.common.utils.CollectionUtils.isNotEmpty;
import static cn.strong.leke.model.question.Question.QUE_STATUS_CHECKED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_CHECKED_EDITED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_CORRECTION;
import static cn.strong.leke.model.question.Question.QUE_STATUS_DELETED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_DISABLED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORTED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORT_FIXED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_IMPORT_REJECTED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_INPUT;
import static cn.strong.leke.model.question.Question.QUE_STATUS_REACHER_FIXED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_REJECT_FIXED;
import static cn.strong.leke.model.question.Question.QUE_STATUS_REJECT_INPUT;
import static cn.strong.leke.model.question.Question.QUE_STATUS_TEACHER_INPUT;
import static cn.strong.leke.model.question.Question.QUE_STATUS_VERIFIED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.spring.MessageSource;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.DynamicTypes;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.paper.PaperDetail;
import cn.strong.leke.model.paper.QuestionGroup;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.model.question.Analysis;
import cn.strong.leke.model.question.Answer;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.model.question.QuestionSelectRule;
import cn.strong.leke.model.question.QuestionStem;
import cn.strong.leke.model.question.QuestionTypeRule;
import cn.strong.leke.model.question.QuestionWorkbookNode;
import cn.strong.leke.model.question.SchoolQuestionOutlineNode;
import cn.strong.leke.model.question.StrengThenSelectRule;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.repository.QuestionRepositoryRecord;
import cn.strong.leke.model.repository.RepoCsts.AddType;
import cn.strong.leke.model.repository.RepoCsts.CheckStatus;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.model.user.Role;
import cn.strong.leke.model.user.User;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.IQuestionFeedbackDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionCheckPendingDao;
import cn.strong.leke.question.model.QuestionShareEvent;
import cn.strong.leke.question.model.QuestionTaskItem;
import cn.strong.leke.question.model.RandSelectQuestionQuery;
import cn.strong.leke.question.model.question.SchoolQuestion;
import cn.strong.leke.question.service.AnalysisService;
import cn.strong.leke.question.service.AnswerService;
import cn.strong.leke.question.service.IQueImageProcessor;
import cn.strong.leke.question.service.IQuestionLogService;
import cn.strong.leke.question.service.IQuestionShareLogService;
import cn.strong.leke.question.service.IQuestionTaskService;
import cn.strong.leke.question.service.IQuestionWbNodeService;
import cn.strong.leke.question.service.ISchQueOutlineNodeService;
import cn.strong.leke.question.service.QuestionKnowledgeService;
import cn.strong.leke.question.service.QuestionManager;
import cn.strong.leke.question.service.QuestionOfficialTagService;
import cn.strong.leke.question.service.QuestionSectionService;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.question.service.QuestionStemService;
import cn.strong.leke.question.service.SchoolQuestionService;
import cn.strong.leke.question.util.QueContentDiff;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.remote.model.question.SchoolQuestionContribution;
import cn.strong.leke.remote.model.question.SchoolQuestionContributionQuery;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.repository.common.cmd.store.ISchoolRepoHandler;

/**
 * 
 * 描述: 题目管理服务实现
 * 
 * @author liulb
 * @created 2014年5月4日 上午10:30:46
 * @since v1.0.0
 */
@Service
public class QuestionManagerImpl implements QuestionManager {

	private static Logger logger = LoggerFactory
			.getLogger(QuestionManagerImpl.class);

	private static final int BATCH_CACHE_SIZE = 100;
	private static final int POOL_SIZE = 10;

	@Autowired
	private QuestionService questionService;
	@Resource
	private IQuestionQueryService questionQueryService;
	@Autowired
	private QuestionStemService questionStemService;
	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private IQuestionLogService questionLogService;
	@Autowired
	private QuestionSectionService questionSectionService;
	@Autowired
	private QuestionKnowledgeService questionKnowledgeService;
	@Autowired
	private IQuestionWbNodeService questionWbNodeService;
	@Autowired
	private QuestionOfficialTagService questionOfficialTagService;
	@Autowired
	private IQuestionTaskService questionTaskService;
	@Autowired
	private SchoolQuestionService schoolQuestionService;
	@Autowired
	private IQueImageProcessor queImageProcessor;
	@Resource
	@Qualifier("userDynamicSender")
	private MessageSender userDynamicSender;
	@Resource
	private IQuestionShareLogService questionShareLogService;
	@Resource
	private ISchQueOutlineNodeService schQueOutlineNodeService;
	@Autowired
	@Qualifier("schoolQuestionHandler")
	private ISchoolRepoHandler schoolRepoHandler;
	@Resource
	private IQuestionCheckPendingDao questionCheckPendingDao;
	@Resource
	private IQuestionFeedbackDao questionFeedbackDao;
	@Resource
	private IUserRemoteService userRemoteService;

	
	@Override
	public QuestionDTO getQuestionDTO(Long questionId) {
		QuestionDTO result = null;
		QuestionDTO que = questionService.getQuestion(questionId);
		if (que != null) {
			result = (QuestionDTO) BeanUtils.cloneBean(que);
			fillContents(result);
			fillRelations(result);
		}
		return result;
	}

	private void fillContents(QuestionDTO result) {
		Long questionId = result.getQuestionId();
		QuestionStem stem = questionStemService
				.getQuestionStemByQuestionId(questionId);
		result.setStem(stem);
		Analysis analysis = analysisService.getAnalysisByQuestionId(questionId);
		result.setAnalysis(analysis);

		Boolean hasSub = result.getHasSub();
		if (hasSub != null && hasSub.equals(true)) {
			List<QuestionDTO> subs = questionService
					.findSubQuestions(questionId);
			if (!CollectionUtils.isEmpty(subs)) {
				for (QuestionDTO sub : subs) {
					fillContents(sub);
				}
			}
			result.setSubs(subs);
		} else {
			List<Answer> answers = answerService
					.findAnswersByQuestionId(questionId);
			result.setAnswers(answers);
		}
	}

	private void fillRelations(QuestionDTO result) {
		Long questionId = result.getQuestionId();
		List<QuestionSection> sections = questionSectionService
				.findQuestionSectionsByQuestionId(questionId);
		result.setSections(sections);
		List<QuestionKnowledge> knowledges = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(questionId);
		result.setKnowledges(knowledges);
		List<QuestionWorkbookNode> wbnodes = questionWbNodeService.findQuestionWbNodes(questionId);
		result.setWbnodes(wbnodes);
		List<QuestionOfficialTag> tags = questionOfficialTagService
				.findQuestionOfficialTagByQuestionId(questionId);
		result.setTags(tags);
	}

	@Override
	public Long addQuestionDTO(QuestionDTO dto) {
		questionService.addQuestion(dto);
		saveQuestionContents(dto);
		saveQuestionRelations(dto);
		addLog(dto, QUE_STATUS_INPUT);
		Long questionId = dto.getQuestionId();
		return questionId;
	}

	/*
	 * 添加操作日志
	 */
	public void addLog(QuestionDTO dto, int type) {
		addLog(dto, type, null);
	}

	private void addLog(QuestionDTO dto, int type, String msg) {
		QuestionLog log = new QuestionLog();
		log.setQuestionId(dto.getQuestionId());
		log.setCreatedBy(dto.getModifiedBy());
		log.setUserName(dto.getOperatorName());
		log.setType(type);
		log.setReason(msg);
		questionLogService.addQuestionLog(log);
	}

	@Override
	public void addLog(Long questionId, int type, User user) {
		addLog(questionId, type, user, null);
	}

	private void addLog(Long questionId, int type, User user, String msg) {
		QuestionLog log = new QuestionLog();
		log.setQuestionId(questionId);
		if (user != null) {
			log.setCreatedBy(user.getId());
			log.setUserName(user.getUserName());
		}
		log.setType(type);
		log.setReason(msg);
		questionLogService.addQuestionLog(log);
	}

	/**
	 * 
	 * 描述: 保存题目内容
	 * 
	 * @author liulb
	 * @created 2014年5月7日 下午4:58:06
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	private void saveQuestionContents(QuestionDTO dto) {
		queImageProcessor.processBase64Images(dto);

		Long questionId = dto.getQuestionId();
		QuestionStem stem = dto.getStem();
		if (stem != null) {
			stem.setQuestionId(questionId);
			questionStemService.addQuestionStem(stem);
		}
		List<Answer> answers = dto.getAnswers();
		if (!CollectionUtils.isEmpty(answers)) {
			for (int i = 0; i < answers.size(); i++) {
				Answer answer = answers.get(i);
				answer.setOrd(i);
				answer.setQuestionId(questionId);
				answerService.addAnswer(answer);
			}
		}
		Analysis analysis = dto.getAnalysis();
		if (analysis != null) {
			analysis.setQuestionId(questionId);
			analysisService.addAnalysis(analysis);
		}

		List<QuestionDTO> subs = dto.getSubs();
		if (!CollectionUtils.isEmpty(subs)) {
			for (int j = 0; j < subs.size(); j++) {
				QuestionDTO sub = subs.get(j);
				sub.setOrd(j);
				sub.setSchoolStageId(dto.getSchoolStageId());
				sub.setSubjectId(dto.getSubjectId());
				sub.setParentId(questionId);
				questionService.addQuestion(sub);
				saveQuestionContents(sub);
			}
		}
	}

	/*
	 * 
	 * @update 2016-01-19 本地更新
	 */
	@Override
	public void updateQuestionDTO(QuestionDTO dto, User user) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		updateQuestionInline(dto);

		int oldStatus = backend.getQuestionStatus();
		Integer newStatus = null;
		Integer logType = null;
		switch (oldStatus) {
		case QUE_STATUS_REJECT_INPUT:
			newStatus = QUE_STATUS_INPUT;
			logType = QUE_STATUS_REJECT_FIXED;
			break;
		case QUE_STATUS_IMPORT_REJECTED:
			newStatus = QUE_STATUS_IMPORTED;
			logType = QUE_STATUS_IMPORT_FIXED;
			break;
		default:
			break;
		}
		if (newStatus != null) {
			updateStatus(questionId, newStatus);
		}
		if (logType != null) {
			addLog(dto, logType);
		}
		//教研员编辑加日志
		if (user.getCurrentRole().getId() == RoleCst.RESEARCHER) {
			addLog(dto, QUE_STATUS_REACHER_FIXED);
		}

		QuestionContext.deleteCache(dto.getQuestionId());
	}

	private QuestionDTO updateQuestionInline(QuestionDTO dto) {
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = QuestionContext.getQuestionDTO(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		QueContentDiff diff = QueContentDiff.diff(backend, dto);
		questionService.updateQuestion(dto);
		updateQueContentDiff(diff);
		saveQuestionRelations(dto);
		return backend;
	}

	private void updateQueContentDiff(QueContentDiff diff) {
		for(QuestionStem stem: diff.stems) {
			questionStemService.updateQuestionStem(stem);
		}
		for (Answer answer : diff.answers) {
			answerService.updateAnswer(answer);
		}
		for (Analysis analysis : diff.analysises) {
			analysisService.updateAnalysis(analysis);
		}
	}

	@Override
	public void updateQuestionShare(QuestionDTO dto) {
		questionService.updateQuestionShare(dto);
		QuestionContext.deleteCache(dto.getQuestionId());
	}

	private void saveQuestionRelations(QuestionDTO dto) {
		if(dto == null || dto.getQuestionId() == null) {
			return;
		}
		Long questionId = dto.getQuestionId();
		Long userId = dto.getModifiedBy();
		List<QuestionSection> sections = dto.getSections();
		questionSectionService.deleteQuestionSectionByQuestionId(questionId, userId);
		if (isNotEmpty(sections)) {
			for (QuestionSection section : sections) {
				section.setQuestionId(questionId);
				section.setCreatedBy(userId);
				section.setModifiedBy(userId);
				questionSectionService.addQuestionSection(section);
			}
		}
		List<QuestionKnowledge> knowledges = dto.getKnowledges();
		questionKnowledgeService.deleteQuestionKnowledgeByQuestionId(questionId);
		if (isNotEmpty(knowledges)) {
			for (QuestionKnowledge knowledge : knowledges) {
				knowledge.setQuestionId(questionId);
				knowledge.setCreatedBy(userId);
				knowledge.setModifiedBy(userId);
				questionKnowledgeService.addQuestionKnowledge(knowledge);
			}
		}
		List<QuestionWorkbookNode> wbnodes = dto.getWbnodes();
		questionWbNodeService.deleteQuestionWbNodes(questionId);
		if (isNotEmpty(wbnodes)) {
			for (QuestionWorkbookNode wbnode : wbnodes) {
				wbnode.setQuestionId(questionId);
				wbnode.setCreatedBy(userId);
				wbnode.setModifiedBy(userId);
				questionWbNodeService.addQuestionWbNode(wbnode);
			}
		}
		List<QuestionOfficialTag> tags = dto.getTags();
		questionOfficialTagService.deleteQuestionOfficialTagByQuestionId(questionId);
		if (isNotEmpty(tags)) {
			for (QuestionOfficialTag tag : tags) {
				tag.setQuestionId(questionId);
				tag.setCreatedBy(userId);
				tag.setModifiedBy(userId);
				questionOfficialTagService.addQuestionOfficialTag(tag);
			}
		}
	}

	private void deleteQuestionContents(QuestionDTO dto) {
		Long questionId = dto.getQuestionId();
		questionStemService.deleteQuestionStemByQuestionId(questionId);
		answerService.deleteAnswerByQuestionId(questionId);
		analysisService.deleteAnalysisByQuestionId(questionId);
		Boolean hasSub = dto.getHasSub();
		if (hasSub != null && hasSub == true) {
			List<QuestionDTO> subs = questionService
					.findSubQuestions(questionId);
			if (!CollectionUtils.isEmpty(subs)) {
				for (QuestionDTO sub : subs) {
					questionService.deleteQuestion(sub);
					deleteQuestionContents(sub);
				}
			}
		}
	}

	@Override
	public void deleteQuestionDTO(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (backend.getUsedCount() > 0) {
			throw new ValidateException(
					"que.question.undeletable.has.paper.count");
		}
		questionService.deleteQuestion(dto);
		deleteQuestionContents(backend);
		deleteQuestionRelations(questionId, dto.getModifiedBy());
		addLog(dto, QUE_STATUS_DELETED);

		QuestionContext.deleteCache(questionId);
	}

	/*
	 * 描述: 删除和教材节点的关联关系
	 */
	private void deleteQuestionRelations(Long questionId, Long modifiedBy) {
		questionSectionService.deleteQuestionSectionByQuestionId(questionId, modifiedBy);
		questionKnowledgeService
				.deleteQuestionKnowledgeByQuestionId(questionId);
		questionWbNodeService.deleteQuestionWbNodes(questionId);
		questionOfficialTagService
				.deleteQuestionOfficialTagByQuestionId(questionId);
	}

	@Override
	public void updateDifficulty(Question question) {
		if (question == null || question.getQuestionId() == null
				|| question.getDifficulty() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		questionService.updateDifficulty(question);
		QuestionContext.deleteCache(question.getQuestionId());
	}

	@Override
	public void addQuestionKnowledges(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null
				|| CollectionUtils.isEmpty(dto.getKnowledges())) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		List<QuestionKnowledge> olds = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(questionId);
		List<QuestionKnowledge> addons = ListUtils.diff(dto.getKnowledges(),
				olds, new Function<QuestionKnowledge, Long>() {

					@Override
					public Long apply(QuestionKnowledge t) {
						return t.getKnowledgeId();
					}

				});
		if (CollectionUtils.isNotEmpty(addons)) {
			for (QuestionKnowledge item : addons) {
				item.setQuestionId(questionId);
				item.setCreatedBy(dto.getCreatedBy());
				item.setModifiedBy(dto.getModifiedBy());
				questionKnowledgeService.addQuestionKnowledge(item);
			}
		}

		QuestionContext.deleteCache(dto.getQuestionId());
	}

	@Override
	public void deleteQuestionKnowledge(QuestionKnowledge assoc) {
		QuestionKnowledge old = questionKnowledgeService
				.getQuestionKnowledge(assoc.getQuesKnowledgeId());
		if (old == null) {
			throw new ValidateException("que.question.knowledge.not.exist");
		}
		questionKnowledgeService.deleteQuestionKnowledge(assoc);
		QuestionContext.deleteCache(old.getQuestionId());
	}

	@Override
	public void addQuestionOfficialTags(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null
				|| CollectionUtils.isEmpty(dto.getTags())) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		List<QuestionOfficialTag> olds = questionOfficialTagService
				.findQuestionOfficialTagByQuestionId(questionId);
		List<QuestionOfficialTag> addons = ListUtils.diff(dto.getTags(), olds,
				new Function<QuestionOfficialTag, Long>() {

					@Override
					public Long apply(QuestionOfficialTag tag) {
						return tag.getOfficialTagId();
					}

				});
		if (CollectionUtils.isNotEmpty(addons)) {
			for (QuestionOfficialTag item : addons) {
				item.setQuestionId(questionId);
				item.setCreatedBy(dto.getCreatedBy());
				item.setModifiedBy(dto.getModifiedBy());
				questionOfficialTagService.addQuestionOfficialTag(item);
			}
		}

		QuestionContext.deleteCache(dto.getQuestionId());
	}

	@Override
	public void deleteQuestionOfficialTag(QuestionOfficialTag assoc) {
		QuestionOfficialTag old = questionOfficialTagService
				.getQuestionOfficialTag(assoc.getQuesOfficialTagId());
		if (old == null) {
			throw new ValidateException("que.question.tag.not.exist");
		}
		questionOfficialTagService.deleteQuestionOfficialTag(assoc);
		QuestionContext.deleteCache(old.getQuestionId());
	}

	@Override
	public void updateCheck(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		List<QuestionKnowledge> knowledges = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(questionId);
		if (CollectionUtils.isEmpty(knowledges)) {
			throw new ValidateException("que.question.no.knowledges");
		}
		List<QuestionOfficialTag> tags = questionOfficialTagService
				.findQuestionOfficialTagByQuestionId(questionId);
		if (CollectionUtils.isEmpty(tags)) {
			throw new ValidateException("que.question.no.tags");
		}
		
		updateStatus(questionId, QUE_STATUS_CHECKED);

		QuestionTaskItem taskItem = new QuestionTaskItem();
		taskItem.setQuestionId(questionId);
		taskItem.setModifiedBy(dto.getModifiedBy());
		questionTaskService.deleteQuestionTaskItemByQuestionId(taskItem);

		addLog(dto, QUE_STATUS_CHECKED);

		QuestionContext.deleteCache(dto.getQuestionId());
	}

	@Override
	public void updateReject(QuestionDTO dto, String rejectReason) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		updateStatus(questionId,  QUE_STATUS_REJECT_INPUT);
		
		QuestionLog log = new QuestionLog();
		log.setQuestionId(dto.getQuestionId());
		log.setCreatedBy(dto.getModifiedBy());
		log.setUserName(dto.getOperatorName());
		log.setReason(rejectReason);
		log.setType(QUE_STATUS_REJECT_INPUT);
		questionLogService.addQuestionLog(log);
		
		QuestionContext.deleteCache(dto.getQuestionId());
	}

	private void updateStatusWithLog(QuestionDTO dto, int status) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		updateStatus(questionId, status);
		addLog(dto, status);
	}

	public void updateStatus(Long questionId, int status) {
		Question que = new Question();
		que.setQuestionId(questionId);
		que.setQuestionStatus(status);
		questionService.updateQuestionStatus(que);
	}

	@Override
	public void updateCorrect(QuestionDTO dto) {
		updateStatusWithLog(dto, QUE_STATUS_CORRECTION);
		QuestionContext.deleteCache(dto.getQuestionId());
	}

	@Override
	public void updateVerify(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		List<QuestionKnowledge> knowledges = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(questionId);
		if (CollectionUtils.isEmpty(knowledges)) {
			throw new ValidateException("que.question.no.knowledges");
		}
		List<QuestionOfficialTag> tags = questionOfficialTagService
				.findQuestionOfficialTagByQuestionId(questionId);
		if (CollectionUtils.isEmpty(tags)) {
			throw new ValidateException("que.question.no.tags");
		}

		updateStatus(questionId, QUE_STATUS_VERIFIED);
		addLog(dto, QUE_STATUS_VERIFIED);
		QuestionContext.deleteCache(dto.getQuestionId());
	}

	@Override
	public List<UserQuestionDTO> queryFeedbackQuestions(QuestionFeedbackDTO questionFeedbackDTO, Researcher researcher,
			Page page) {
		List<UserQuestionDTO> qList = questionService.queryFeedbackQuestions(questionFeedbackDTO, researcher, page);
		return qList;
	}
	
	@Override
	public List<QuestionRepositoryRecord> queryAllFeedbackQuestionRecord(QuestionFeedbackDTO questionFeedbackDTO, Researcher researcher,
			Page page) {
		List<UserQuestionDTO> qList = questionService.queryAllFeedbackQuestions(questionFeedbackDTO, researcher, page);
		return toRepositoryRecord(qList);
	}
	
	@Override
	public List<QuestionRepositoryRecord> queryFeedbackQuestionRecord(QuestionFeedbackDTO questionFeedbackDTO, Researcher researcher,
			Page page) {
		List<UserQuestionDTO> qList = questionService.queryFeedbackQuestions(questionFeedbackDTO, researcher, page);
		return toRepositoryRecord(qList);
	}
	
	private List<QuestionRepositoryRecord> toRepositoryRecord(List<UserQuestionDTO> shareLogs) {
		return ListUtils.map(shareLogs, shareLog -> {
			Long questionId = shareLog.getQuestionId();
			List<RepositoryRecord> records = RepositoryContext.ofQuestions(Arrays.asList(questionId));
			QuestionRepositoryRecord record = (QuestionRepositoryRecord) records.get(0);
			record.attr("check", shareLog);

			record.setSource("已处理");
			for (QuestionFeedbackDTO feedbackDTO : shareLog.getFeedback()){
				if (feedbackDTO.getIsProcessed().equals(false)){
					record.setSource("未处理");
					record.setCreatedOn(feedbackDTO.getModifiedOn());
					break;
				}/*else if(feedbackDTO.getIsProcessed().equals(true)){
					record.setSource("已处理");
					record.setCreatedOn(feedbackDTO.getModifiedOn());
					break;
				}else{
					record.setSource("待纠错");
					record.setCreatedOn(feedbackDTO.getModifiedOn());
					break;
				}*/
			}
		
			return record;
		});
	}
	
	@Override
	public List<UserQuestionDTO> queryFeedbackQuestionsByUser(Long userId,
			Page page) {
		List<UserQuestionDTO> qList = questionService.queryFeedbackQuestionsByUser(userId, page);
		return qList;
	}

	@Override
	public List<UserQuestionDTO> queryRejectionQuestionsByUser(Long userId,
			Page page) {
		List<UserQuestionDTO> qList = questionService.queryRejectionQuestionsByUser(userId, page);
		return qList;
	}
	
	@Override
	public void addUserQuestionDTO(UserQuestionDTO dto) {
		validateUserQue(dto);
		questionService.addQuestion(dto);
		saveQuestionContents(dto);
		saveQuestionRelations(dto);
		updateStatus(dto.getQuestionId(), QUE_STATUS_TEACHER_INPUT);
		addLog(dto, QUE_STATUS_TEACHER_INPUT);

		sendQuestionAddedUserDynamic(dto);
	}

	/**
	 * 发送教师习题添加用户动态
	 */
	private void sendQuestionAddedUserDynamic(UserQuestionDTO dto) {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(dto.getCreatedBy());
		dynamicInfo.setUserName(dto.getOperatorName());
		dynamicInfo.setRoleId(RoleCst.TEACHER);
		dynamicInfo.setSchoolId(dto.getSchoolId());
		// 动态信息
		dynamicInfo.setDynamicType(DynamicTypes.QUE_ENTRY_QUESTION);
		dynamicInfo.setParams(Collections.<String, Object> emptyMap());

		userDynamicSender.send(dynamicInfo);
	}

	private void validateUserQue(UserQuestionDTO dto) {
		if (dto == null || dto.getQuestionTypeId() == null
				|| dto.getSchoolStageId() == null || dto.getSubjectId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		if (dto.getDifficulty() == null) {
			dto.setDifficulty(0.5);
		}
	}

	/*
	 * 
	 * @update 2016-01-19 本地更新
	 */
	@Override
	public void updateUserQuestionDTO(UserQuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		validateUserQue(dto);
		updateQuestionInline(dto);
		updateStatus(dto.getQuestionId(), QUE_STATUS_TEACHER_INPUT);

		QuestionContext.deleteCache(dto.getQuestionId());
	}

	@Override
	public void deleteUserQuestionDTO(UserQuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (backend.getUsedCount() > 0) {
			throw new ValidateException(
					"que.question.undeletable.has.paper.count");
		}
		questionService.deleteQuestion(dto);
		deleteQuestionContents(backend);
		deleteQuestionRelations(questionId, dto.getModifiedBy());
		deleteSchoolShare(dto);

		QuestionContext.deleteCache(questionId);
	}

	private void deleteSchoolShare(UserQuestionDTO dto) {
		Long userId = dto.getModifiedBy();
		SchoolQuestion sq = new SchoolQuestion();
		sq.setQuestionId(dto.getQuestionId());
		sq.setSchoolId(dto.getSchoolId());
		sq.setModifiedBy(userId);
		schoolQuestionService.deleteSchoolQuestion(sq);
	}

	@Override
	public QuestionSelectRule randExerciseByRule(QuestionSelectRule questionSelectRule) {
		for (QuestionTypeRule questionTypeRule : questionSelectRule.getQuestionTypeRuleList()) {
			RandSelectQuestionQuery query = new RandSelectQuestionQuery();
			query.setSubjectId(questionSelectRule.getSubjectId());
			query.setKnowledgeId(questionSelectRule.getKnowledgeId());
			query.setMaterialNodeId(questionSelectRule.getMaterialNodeId());
			query.setQuestionTypeId(questionTypeRule.getQuestionTypeId().longValue());
			query.setSubjective(questionSelectRule.isIncludeSubjective());
			query.setShareScope(questionSelectRule.getShareScope());
			query.setQuestionNum(questionTypeRule.getQuestionNum());
			List<Long> questionIds = questionQueryService.queryRandSelectQuestions(query);
			questionTypeRule.setQuestionIds(questionIds);
		}
		return questionSelectRule;
	}

	@Override
	public StrengThenSelectRule randStrengThenByQuestionId(
			StrengThenSelectRule rule) {
		QuestionDTO source = getQuestionDTO(rule.getQuestionId());
		if (source == null || isEmpty(source.getKnowledges())
				|| isEmpty(source.getSections())) {
			// 如果题目没有关联知识点或章节，不返回题目
			rule.setQuestionIds(Collections.<Long> emptyList());
			return rule;
		}

		// 构建选题条件
		RandSelectQuestionQuery query = new RandSelectQuestionQuery();

		List<Long> knowledgeIds = ListUtils.map(source.getKnowledges(),
				GetKnowledgeIdFn.INSTANCE);
		if (knowledgeIds.size() == 1) {
			query.setKnowledgeId(knowledgeIds.get(0));
		} else {
			query.setKnowledgeIds(knowledgeIds);
		}

		List<Long> materialNodeIds = ListUtils.map(source.getSections(),
				GetMaterialNodeIdFn.INSTANCE);
		if (materialNodeIds.size() == 1) {
			query.setMaterialNodeId(materialNodeIds.get(0));
		} else {
			query.setMaterialNodeIds(materialNodeIds);
		}


		query.setSubjectId(source.getSubjectId());
		query.setSchoolStageId(source.getSchoolStageId());
		query.setSubjective(false); // 不包含主观题
		// query.setDifficulty(source.getDifficulty()); // 暂时取题目难度系数
		Integer num = rule.getQuestionNum();
		query.setQuestionNum(num == null ? 10 : num);
		// 第一次选题，根据原题型选题
		List<Long> questionIds = questionQueryService.queryRandSelectQuestions(query);
		questionIds.remove(rule.getQuestionId());
		if (isEmpty(questionIds)) {
			// 第二次选题，不限定题型（只有第一次选题没有数据才执行第二次选题）
			query.setQuestionTypeId(null);
			questionIds = questionQueryService.queryRandSelectQuestions(query);
			questionIds.remove(rule.getQuestionId());
		}
		rule.setQuestionIds(questionIds);
		return rule;
	}

	private static enum GetKnowledgeIdFn implements
			Function<QuestionKnowledge, Long> {
		INSTANCE;

		@Override
		public Long apply(QuestionKnowledge t) {
			return t.getKnowledgeId();
		}

	}

	private static enum GetMaterialNodeIdFn implements
			Function<QuestionSection, Long> {
		INSTANCE;

		@Override
		public Long apply(QuestionSection t) {
			return t.getMaterialNodeId();
		}

	}

	@Override
	public void batchCacheQuestions() {
		int total = questionService.countStandaloneQuestions();
		if (total <= 0) {
			return;
		}
		List<Page> pages = pagination(total, BATCH_CACHE_SIZE);
		List<FutureTask<Integer>> tasks = new ArrayList<FutureTask<Integer>>();
		ExecutorService es = Executors.newFixedThreadPool(POOL_SIZE);
		for (final Page page : pages) {
			FutureTask<Integer> task = new FutureTask<Integer>(
					new Callable<Integer>() {

				@Override
				public Integer call() {
					logger.info("load question to cache with page "
									+ page.getCurPage());
					List<QuestionDTO> qs = questionService
							.queryStandaloneQuestions(page);
					for (int i = 0, len = qs.size(); i < len; i++) {
						QuestionDTO dto = qs.get(i);
						fillContents(dto);
						fillRelations(dto);
						QuestionContext.updateQuestionToCache(dto);
						dto = null;
					}
					return 1;
				}
			});
			tasks.add(task);
			es.submit(task);
		}
		for (FutureTask<Integer> task : tasks) {
			try {
				task.get();
			} catch (InterruptedException e) {
				logger.warn(e.getMessage(), e);
			} catch (ExecutionException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		es.shutdown();
	}

	private static List<Page> pagination(int total, int batch) {
		List<Page> result = new ArrayList<Page>();
		int totalPage = total % batch == 0 ? total / batch : total / batch + 1;
		for (int i = 1; i <= totalPage; i++) {
			result.add(new Page(i, batch));
		}
		return result;
	}

	@Override
	public void disableQuestion(QuestionDTO dto, User user) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		questionService.disableQuestion(dto);
		addLog(dto, QUE_STATUS_DISABLED);
		questionCheckPendingDao.del(questionId);
		if (RoleCst.RESEARCHER.equals(user.getCurrentRole().getId())) {
			sendAward(questionId, user.getId());
		}
		QuestionContext.deleteCache(questionId);
	}

	private void sendAward(Long questionId, Long userId) {
		List<Long> userIds = questionFeedbackDao.findCorrectUserIdsFeedback(questionId);
		if (CollectionUtils.isNotEmpty(userIds)) {
			List<UserRemote> userRemotes = userRemoteService.findUserListByUserIds(userIds);
			List<DynamicInfo> dynamicInfos = ListUtils.newArrayList();
			userRemotes.forEach(n -> {
				DynamicInfo dynamicInfo = new DynamicInfo();
				dynamicInfo.setTypeId(IncentiveTypes.TEACHER.REP_LOOKERR);
				dynamicInfo.setUserId(n.getId());
				dynamicInfo.setUserName(n.getUserName());
				dynamicInfo.setRoleId(RoleCst.TEACHER);
				dynamicInfos.add(dynamicInfo);
				
				LetterMessage message = new LetterMessage(String.valueOf(n.getId()), "纠错处理提醒");
				message.setContent(ParametersContext.getString("FEEDBACK_TEMPLATE"));
				message.addVariable("name", "习题");
				NoticeHelper.send(message);
			});
			
			questionFeedbackDao.doCorrect(questionId, userId);
			DynamicHelper.publish(dynamicInfos);
		}
	}

	@Override
	public Long updateQuestionOnVerify(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		List<QuestionKnowledge> knowledges = dto.getKnowledges();
		if (CollectionUtils.isEmpty(knowledges)) {
			throw new ValidateException("que.question.no.knowledges");
		}
		List<QuestionOfficialTag> tags = dto.getTags();
		if (CollectionUtils.isEmpty(tags)) {
			throw new ValidateException("que.question.no.tags");
		}

		Long questionId = dto.getQuestionId();
		updateQuestionInline(dto);

		updateStatus(questionId, QUE_STATUS_VERIFIED);
		addLog(dto, QUE_STATUS_VERIFIED);

		// 更新缓存
		QuestionContext.deleteCache(questionId);
		return questionId;
	}

	/*
	 * 新增题目，禁用旧题，返回新题ID
	 */
	private Long replaceQuestion(QuestionDTO dto, User user) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long oldId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(oldId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		// 新增习题
		dto.setQuestionId(null);
		dto.setCreatedBy(backend.getCreatedBy());
		dto.setCreatorName(backend.getCreatorName());
		dto.setSchoolId(backend.getSchoolId());
		dto.setSchoolName(backend.getSchoolName());
		dto.setShareSchool(backend.getShareSchool());
		dto.setSharePlatform(backend.getSharePlatform());

		questionService.addQuestion(dto);
		saveQuestionContents(dto);
		saveQuestionRelations(dto);
		String iptLog = MessageSource.getMessage("que.question.edit.from",
				oldId);
		addLog(dto, QUE_STATUS_INPUT, iptLog);
		Long newId = dto.getQuestionId();

		// 禁用旧题
		QuestionDTO old = new QuestionDTO();
		old.setQuestionId(oldId);
		old.setModifiedBy(dto.getModifiedBy());
		disableQuestion(old, user);

		return newId;
	}

	@Override
	public Long updateReplaceQuestion(QuestionDTO dto, User user) {
		// 替换题目
		Long newId = replaceQuestion(dto, user);
		// 更新缓存
		QuestionContext.deleteCache(newId);
		return newId;
	}

	@Override
	public List<SchoolQuestionContribution> findSchoolQuestionContributions(
			SchoolQuestionContributionQuery query) {
		return questionService.findSchoolQuestionContributions(query);
	}

	@Override
	public Long updateQuestionOnEditChecked(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		List<QuestionKnowledge> knowledges = dto.getKnowledges();
		if (CollectionUtils.isEmpty(knowledges)) {
			throw new ValidateException("que.question.no.knowledges");
		}
		List<QuestionOfficialTag> tags = dto.getTags();
		if (CollectionUtils.isEmpty(tags)) {
			throw new ValidateException("que.question.no.tags");
		}

		Long questionId = dto.getQuestionId();
		updateQuestionInline(dto);
		// 修改状态为已校对
		updateStatus(questionId, QUE_STATUS_CHECKED);
		addLog(dto, QUE_STATUS_CHECKED);
		//教研员审核编辑加日志
		addLog(dto, QUE_STATUS_CHECKED_EDITED);

		// 更新缓存
		QuestionContext.deleteCache(questionId);
		return questionId;
	}

	@Override
	public Long addCheckedQuestionDTO(QuestionDTO dto) {
		if (dto == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		questionService.addQuestion(dto);
		saveQuestionContents(dto);
		saveQuestionRelations(dto);

		Long questionId = dto.getQuestionId();
		addLog(dto, QUE_STATUS_INPUT);
		updateStatus(questionId, QUE_STATUS_CHECKED);
		addLog(dto, QUE_STATUS_CHECKED);

		QuestionContext.deleteCache(questionId);
		return questionId;
	}

	@Override
	public PaperDTO preprocessAnswerSheetQuestions(PaperDTO paper) {
		if (paper == null) {
			return null;
		}
		PaperDetail detail = paper.getDetail();
		List<QuestionGroup> groups = detail.getGroups();
		if (CollectionUtils.isNotEmpty(groups)) {
			for (QuestionGroup group : groups) {
				preprocessAnswerSheetQuestions(group, paper);
			}
		}
		return paper;
	}

	private void preprocessAnswerSheetQuestions(QuestionGroup group,
			PaperDTO paper) {
		List<ScoredQuestion> questions = group.getQuestions();
		if (CollectionUtils.isNotEmpty(questions)) {
			for (ScoredQuestion question : questions) {
				preprocessAnswerSheetQuestion(question, paper);
			}
		}
	}

	private void preprocessAnswerSheetQuestion(ScoredQuestion sq,
			PaperDTO paper) {
		queImageProcessor.processBase64Images(sq);

		Question question = toQuestion(sq, paper);
		questionService.addQuestion(question);
		questionService.disableQuestion(question); // 禁用答题卡习题，不可查询
		Long questionId = question.getQuestionId();
		sq.setQuestionId(questionId);
		preprocessAnswerSheetQuestionContents(sq);

		List<ScoredQuestion> subs = sq.getSubs();
		if (!CollectionUtils.isEmpty(subs)) {
			for (int j = 0; j < subs.size(); j++) {
				ScoredQuestion subsq = subs.get(j);
				Question sub = toQuestion(subsq, paper);
				sub.setOrd(j);
				sub.setParentId(sq.getQuestionId());
				questionService.addQuestion(sub);
				questionService.disableQuestion(sub); // 禁用答题卡习题，不可查询
				subsq.setQuestionId(sub.getQuestionId());

				preprocessAnswerSheetQuestionContents(subsq);
			}
		}
	}
	
	private void preprocessAnswerSheetQuestionContents(ScoredQuestion sq) {
		Long questionId = sq.getQuestionId();
		
		QuestionStem stem = sq.getStem();
		if (stem != null) {
			stem.setQuestionId(questionId);
			questionStemService.addQuestionStem(stem);
		}
		List<Answer> answers = sq.getAnswers();
		if (!CollectionUtils.isEmpty(answers)) {
			for (int i = 0; i < answers.size(); i++) {
				Answer answer = answers.get(i);
				answer.setOrd(i);
				answer.setQuestionId(questionId);
				answerService.addAnswer(answer);
			}
		}
		Analysis analysis = sq.getAnalysis();
		if (analysis != null) {
			analysis.setQuestionId(questionId);
			analysisService.addAnalysis(analysis);
		}
		
		sq.setStem(null);
		sq.setAnswers(null);
		sq.setAnalysis(null);
	}

	private Question toQuestion(ScoredQuestion sq, PaperDTO paper) {
		Question result = new Question();
		result.setQuestionTypeId(sq.getQuestionTypeId());
		result.setDifficulty(0.5);
		result.setHasSub(sq.getHasSub());
		result.setOrd(sq.getOrd());
		result.setSubjective(sq.getSubjective());
		result.setSharePersonal(false);
		result.setSharePlatform(false);
		result.setShareSchool(false);
		result.setSchoolId(paper.getSchoolId());
		result.setSchoolName(paper.getSchoolName());
		result.setCreatorName(paper.getCreatorName());
		result.setCreatedBy(paper.getCreatedBy());
		result.setModifiedBy(paper.getModifiedBy());
		return result;
	}

	@Override
	public Long addImportedQuestion(QuestionDTO dto, WordQuestionInfo info) {
		questionService.addQuestion(dto);
		saveQuestionContents(dto);
		saveQuestionRelations(dto);
		Long questionId = dto.getQuestionId();
		updateStatus(questionId, QUE_STATUS_IMPORTED);
		addLog(dto, QUE_STATUS_IMPORTED);
		questionService.updateNote(questionId, info.getNote());
		QuestionContext.deleteCache(questionId);
		return questionId;
	}

	/*
	 * 
	 * @update 2016-01-19 本地更新
	 */
	@Override
	public void updateImportedQuestion(QuestionDTO dto, User user) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = dto.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		int oldStatus = backend.getQuestionStatus();
		if (oldStatus != QUE_STATUS_IMPORTED && oldStatus != QUE_STATUS_IMPORT_REJECTED) {
			throw new ValidateException("que.question.status.uneditable");
		}

		updateQuestionInline(dto);
		updateStatus(questionId, QUE_STATUS_IMPORTED);
		if (oldStatus == QUE_STATUS_IMPORT_REJECTED) {
			addLog(dto, QUE_STATUS_IMPORT_FIXED);
		}

		QuestionContext.deleteCache(questionId);
	}

	@Override
	public void validateQueIntegrity(QuestionDTO dto) {
		if (dto == null || dto.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		validateQueIntegrity(dto.getQuestionId());
	}

	@Override
	public void validateQueIntegrity(Long questionId) {
		if (questionId == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		if (backend.getDifficulty() == null) {
			throw new ValidateException("que.question.no.difficulty");
		}
		List<QuestionKnowledge> knowledges = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(questionId);
		if (CollectionUtils.isEmpty(knowledges)) {
			throw new ValidateException("que.question.no.knowledges");
		}
		// List<QuestionOfficialTag> tags = questionOfficialTagService
		// .findQuestionOfficialTagByQuestionId(questionId);
		// if (CollectionUtils.isEmpty(tags)) {
		// throw new ValidateException("que.question.no.tags");
		// }
	}

	@Override
	public QuestionShareEvent getQuestionShareInfo(Long questionId, User user) {
		if (questionId == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		QuestionShareEvent result = new QuestionShareEvent();
		result.setQuestionId(backend.getQuestionId());
		result.setSchoolStageId(backend.getSchoolStageId());
		result.setSubjectId(backend.getSubjectId());
		result.setShareSchool(backend.getShareSchool());
		if (!result.getShareSchool()) {
			Long schoolId = user.getCurrentSchool().getId();
			if (schoolRepoHandler.contains(questionId, schoolId)) {
				result.setShareSchool(true);
			}
		}
		result.setSharePlatform(backend.getSharePlatform());
		List<QuestionSection> sections = questionSectionService
				.findQuestionSectionsByQuestionId(questionId);
		result.setSections(sections);
		List<QuestionKnowledge> knowledges = questionKnowledgeService
				.findQuestionKnowledgeByQuestionId(questionId);
		result.setKnowledge(CollectionUtils.getFirst(knowledges));
		SchoolQuestionOutlineNode outlineNode = schQueOutlineNodeService
				.findOutlineNodeByQuestionId(questionId);
		result.setOutlineNode(outlineNode);
		return result;
	}

	@Override
	public Award updateQuestionShare(QuestionShareEvent event, User user) {
		if (event == null || event.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Long questionId = event.getQuestionId();
		QuestionDTO backend = questionService.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}

		Long userId = user.getId();
		Long schoolId = user.getCurrentSchool().getId();
		backend.setModifiedBy(userId);

		// 处理章节知识点
		if (CollectionUtils.isNotEmpty(event.getSections())) {
			List<QuestionSection> sections = event.getSections();
			questionSectionService.deleteQuestionSectionByQuestionId(questionId, userId);
			if (isNotEmpty(sections)) {
				for (QuestionSection section : sections) {
					section.setQuestionId(questionId);
					section.setCreatedBy(userId);
					section.setModifiedBy(userId);
					questionSectionService.addQuestionSection(section);
				}
			}
		}

		// 处理共享范围
		int schoolDiff = 0;
		Boolean shareSchool = event.getShareSchool();
		if (shareSchool != null) {
			Boolean oldShareSchool = schoolRepoHandler.contains(questionId, schoolId);
			if (!shareSchool.equals(oldShareSchool)) {
				schoolDiff = shareSchool ? 1 : -1;
			}
		}

		int lekeDiff = 0;
		Boolean sharePlatform = event.getSharePlatform();
		if (sharePlatform != null) {
			Boolean oldSharePlatform = backend.getSharePlatform();
			if (!sharePlatform.equals(oldSharePlatform)) {
				lekeDiff = sharePlatform ? 1 : -1;
			}
		}

		if (schoolDiff != 0 || lekeDiff != 0) {
			if (shareSchool) {
				if (!schoolId.equals(backend.getSchoolId())) {
					backend.setSchoolId(schoolId);
				}
			}
			backend.setShareSchool(shareSchool);
			backend.setSharePlatform(sharePlatform);
			questionService.updateQuestionShare(backend);

			if (schoolDiff > 0) {
				SchoolQuestion schoolQuestion = defaultSchoolQuestion(questionId, user);
				schoolQuestionService.addSchoolQuestion(schoolQuestion);
				questionShareLogService.shareQuestionToSchool(questionId, user);
			}
			if (lekeDiff > 0) {
				questionShareLogService.shareQuestionToLeke(questionId, user);
			}
			if (schoolDiff > 0 || lekeDiff > 0) {
				Role role = user.getCurrentRole();
				if (role != null && RoleCst.TEACHER.equals(role.getId())) {
					DynamicInfo dynamicInfo = new DynamicInfo();
					dynamicInfo.setTypeId(IncentiveTypes.TEACHER.BK_SHARE_QUE);
					dynamicInfo.setDynamicType(DynamicTypes.BK_SHARE_QUE);
					dynamicInfo.setTitle("题号: " + backend.getQuestionId());
					Map<String, Object> params = new HashMap<>();
					params.put("questionId", backend.getQuestionId());
					dynamicInfo.setParams(params);
					dynamicInfo.setUserId(userId);
					dynamicInfo.setUserName(user.getUserName());
					dynamicInfo.setRoleId(RoleCst.TEACHER);
					dynamicInfo.setSchoolId(user.getCurrentSchool().getId());
					QuestionContext.deleteCache(questionId);
					return DynamicHelper.publish(dynamicInfo);
					// ignore returned award now
				}
			}
		}

		QuestionContext.deleteCache(questionId);
		return Award.ignore();
	}

	private SchoolQuestion defaultSchoolQuestion(Long questionId, User user) {
		SchoolQuestion schoolQuestion = new SchoolQuestion();
		Long userId = user.getId();
		Long schoolId = user.getCurrentSchool().getId();
		schoolQuestion.setSchoolId(schoolId);
		schoolQuestion.setAddType(AddType.SHARE);
		schoolQuestion.setQuestionId(questionId);
		schoolQuestion.setCreatedBy(userId);
		schoolQuestion.setModifiedBy(userId);
		schoolQuestion.setStatus(CheckStatus.INIT);
		return schoolQuestion;
	}

	@Override
	public void addQuestionSections(QuestionDTO dto, User user) {
		if (dto == null || dto.getQuestionId() == null || CollectionUtils.isEmpty(dto.getSections())) {
			throw new ValidateException("que.material.info.incomplete");
		}
		List<QuestionSection> sections = dto.getSections();
		Long questionId = dto.getQuestionId();
		Long userId = user.getId();
		if (isNotEmpty(sections)) {
			List<QuestionSection> backend = questionSectionService.findQuestionSectionsByQuestionId(questionId);
			Map<Long, QuestionSection> map = ListUtils.toMap(backend, n -> {
				return n.getMaterialNodeId();
			});
			for (QuestionSection section : sections) {
				if (!map.containsKey(section.getMaterialNodeId())) {
					section.setQuestionId(questionId);
					section.setCreatedBy(userId);
					section.setModifiedBy(userId);
					questionSectionService.addQuestionSection(section);
				}

			}
		}
		QuestionContext.deleteCache(questionId);
	}

	@Override
	public void deleteQuestionSection(Long quesSectionId,User user) {
		if (quesSectionId == null) {
			throw new ValidateException("que.material.info.incomplete");
		}
		QuestionSection old = questionSectionService.getQuestionSection(quesSectionId);
		 if (old == null) {
			throw new ValidateException("que.materialNode.not.exist");
		 }
		Long userId = user.getId();
		questionSectionService.deleteQuestionSectionByQuesSectionId(quesSectionId, userId);
		 QuestionContext.deleteCache(old.getQuestionId());
	}
}
