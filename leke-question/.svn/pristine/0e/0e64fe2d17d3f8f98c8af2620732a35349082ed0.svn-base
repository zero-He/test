/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.LeagueContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.dao.mybatis.IQuestionLogDao;
import cn.strong.leke.question.dao.mybatis.QuestionDao;
import cn.strong.leke.question.dao.mybatis.question.IQuestionCheckPendingDao;
import cn.strong.leke.question.model.CheckQuestionQuery;
import cn.strong.leke.question.model.CheckerQuestionQuery;
import cn.strong.leke.question.model.DraftQuestionQuery;
import cn.strong.leke.question.model.InputerQuestionQuery;
import cn.strong.leke.question.model.PublishedQuestionQuery;
import cn.strong.leke.question.model.ResearcherQuestionQuery;
import cn.strong.leke.question.model.ReviewQuestionQuery;
import cn.strong.leke.question.model.StatisQuestionQuery;
import cn.strong.leke.question.model.TeacherShareQuestionQuery;
import cn.strong.leke.question.service.GetQuestionFn;
import cn.strong.leke.question.service.GetUserQuestionDTOFn;
import cn.strong.leke.question.service.GetUserQuestionFeedbackFn;
import cn.strong.leke.question.service.IQuestionRejectionService;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.remote.model.question.SchoolQuestionContribution;
import cn.strong.leke.remote.model.question.SchoolQuestionContributionQuery;

/**
 * 
 * 描述: 题目服务实现
 * 
 * @author liulb
 * @created 2014年5月4日 上午10:30:18
 * @since v1.0.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private IQuestionLogDao questionLogDao;
	@Autowired
	private IQuestionCheckPendingDao questionCheckPendingDao;
	@Autowired
	private IQuestionRejectionService questionRejectionService;

	@Override
	public void addQuestion(Question question) {
		if (question == null || question.getQuestionTypeId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		question.setQuestionStatus(Question.QUE_STATUS_INPUT);
		if (question.getHasSub() == null) {
			question.setHasSub(false);
		}
		if (question.getOrd() == null) {
			question.setOrd(0);
		}
		if (question.getDifficulty() == null) {
			question.setDifficulty(0.5);
		}
		questionDao.insertQuestion(question);
	}

	@Override
	public void updateQuestion(Question question) {
		questionDao.updateQuestion(question);
	}

	@Override
	public void updateQuestionShare(Question question) {
		questionDao.updateQuestionShare(question);
	}

	@Override
	public void deleteQuestion(Question question) {
		questionDao.deleteQuestion(question);
	}

	@Override
	public QuestionDTO getQuestion(Long questionId) {
		return questionDao.getQuestion(questionId);
	}

	@Override
	public List<QuestionDTO> findSubQuestions(Long questionId) {
		return questionDao.findSubQuestions(questionId);
	}

	@Override
	public List<QuestionDTO> queryInputerQuestions(InputerQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao.queryInputerQuestionIds(query,
				page);
		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		retrieveLogs(result);
		return result;
	}

	@Override
	public List<QuestionDTO> queryCheckerQuestions(CheckerQuestionQuery query,
			Page page) {
		if (query == null || query.getCheckerId() == null) {
			return Collections.emptyList();
		}
		int type = query.getStatusType();
		List<Long> questionIds = null;
		if (type == CheckerQuestionQuery.TYPE_CHECKED) {
			questionIds = questionDao.queryCheckerCheckedQuestionIds(query,
					page);
		} else {
			questionIds = questionDao.queryCheckerUncheckedQuestionIds(query,
					page);
		}
		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		retrieveLogs(result);
		return result;
	}

	@Override
	public void updateDifficulty(Question question) {
		if (question == null || question.getQuestionId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Double difficulty = question.getDifficulty();
		if (difficulty == null) {
			difficulty = 0.5;
		}
		Question params = new Question();
		params.setQuestionId(question.getQuestionId());
		params.setDifficulty(difficulty);
		params.setModifiedBy(question.getModifiedBy());
		questionDao.updateDifficulty(question);
	}

	@Override
	public List<QuestionDTO> queryResearcherQuestions(
			ResearcherQuestionQuery query, Page page) {
		if (query == null || query.getResearcherId() == null) {
			return Collections.emptyList();
		}
		List<Long> questionIds = questionDao.queryResearcherQuestionIds(query,
				page);

		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		retrieveLogs(result);
		return result;
	}

	@Override
	public List<QuestionDTO> queryStatisQuestions(StatisQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao
				.queryStatisQuestionIds(query, page);

		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		retrieveLogs(result);
		return result;
	}

	private void retrieveLogs(List<? extends QuestionDTO> questions) {
		if (CollectionUtils.isNotEmpty(questions)) {
			for (QuestionDTO question : questions) {
				List<QuestionLog> logs = questionLogDao
						.findQuestionLogByQuestionId(question.getQuestionId());
				question.setLogs(logs);
			}
		}
	}

	@Override
	public List<QuestionDTO> queryDraftQuestions(DraftQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao.queryDraftQuestionIds(query, page);

		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		retrieveLogs(result);
		return result;
	}

	@Override
	public List<QuestionDTO> queryPublishedQuestions(
			PublishedQuestionQuery query, Page page) {
		List<Long> questionIds = questionDao.queryPublishedQuestionIds(query,
				page);

		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		return result;
	}

	@Override
	public List<UserQuestionDTO> queryAllFeedbackQuestions(QuestionFeedbackDTO questionFeedbackDto, Researcher researcher,
			Page page) {
		List<Long> questionIds = questionDao.queryAllFeedbackQuestionIds(
				researcher, page);
		return ListUtils.map(
				questionIds,
				questionId -> {
					UserQuestionDTO result = new UserQuestionDTO();
					QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
					BeanUtils.copyProperties(result, question);

					return result;
				});
	}
	
	@Override
	public List<UserQuestionDTO> queryFeedbackQuestions(QuestionFeedbackDTO questionFeedbackDto, Researcher researcher,
			Page page) {
		List<Long> questionIds = questionDao.queryFeedbackQuestionIds(
				researcher, page);
		return ListUtils.map(questionIds, GetUserQuestionFeedbackFn.INSTANCE);
	}

	@Override
	public List<UserQuestionDTO> queryFeedbackQuestionsByUser(Long userId,
			Page page) {
		List<Long> questionIds = questionDao.queryFeedbackQuestionIdsByUser(
				userId, page);
		return ListUtils.map(questionIds, GetUserQuestionFeedbackFn.INSTANCE);
	}

	@Override
	public List<UserQuestionDTO> queryRejectionQuestionsByUser(Long userId,
			Page page) {
		List<Long> questionIds = questionDao.queryRejectionQuestionIdsByUser(
				userId, page);
		return ListUtils.map(questionIds, GetUserQuestionDTOFn.INSTANCE);
	}

	@Override
	public int countStandaloneQuestions() {
		return questionDao.countStandaloneQuestions();
	}

	@Override
	public List<QuestionDTO> queryStandaloneQuestions(Page page) {
		return questionDao.queryStandaloneQuestions(page);
	}

	@Override
	public void updateQuestionType(Question question) {
		if (question == null || question.getQuestionTypeId() == null) {
			throw new ValidateException("que.question.info.incomplete");
		}
		Question params = new Question();
		params.setQuestionId(question.getQuestionId());
		params.setQuestionTypeId(question.getQuestionTypeId());
		params.setModifiedBy(question.getModifiedBy());
		questionDao.updateQuestionType(question);
	}

	@Override
	public List<QuestionDTO> queryTypeMismatchInputerQuestions(
			InputerQuestionQuery query, Page page) {
		List<Long> questionIds = questionDao
				.queryTypeMismatchInputerQuestionIds(query, page);

		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		return result;
	}

	@Override
	public QuestionDTO getQuestionWithLog(Long questionId) {
		QuestionDTO result = questionDao.getQuestion(questionId);
		if (result != null) {
			List<QuestionLog> logs = questionLogDao
					.findQuestionLogByQuestionId(questionId);
			result.setLogs(logs);
		}
		return result;
	}

	@Override
	public void disableQuestion(Question question) {
		questionDao.disableQuestion(question);
	}

	@Override
	public List<QuestionDTO> queryReviewQuestions(ReviewQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao
				.queryReviewQuestionIds(query, page);

		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		retrieveLogs(result);
		return result;
	}

	@Override
	public List<UserQuestionDTO> queryTeacherShareQuestions(
			TeacherShareQuestionQuery query, Page page) {
		List<Long> questionIds = questionDao.queryTeacherShareQuestionIds(
				query, page);
		List<UserQuestionDTO> result = ListUtils.map(questionIds,
				GetUserQuestionDTOFn.INSTANCE);

		retrieveLogs(result);
		return result;
	}

	@Override
	public List<SchoolQuestionContribution> findSchoolQuestionContributions(
			SchoolQuestionContributionQuery query) {
		return questionDao.findSchoolQuestionContributions(query);
	}

	@Override
	public Long findQuestionCount() {
		return questionDao.queryQuestionCount();
	}

	@Override
	public List<QuestionDTO> queryQuestionsForCheck(CheckQuestionQuery query,
			Page page) {
		List<Long> questionIds = questionDao.queryQuestionIdsForCheck(query,
				page);
		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		return result;
	}

	@Override
	public void updateQuestionStatus(Question que) {
		questionDao.updateQuestionStatus(que);
	}

	@Override
	public void updateNote(Long questionId, String note) {
		if (questionId == null || StringUtils.isEmpty(note)) {
			return;
		}
		Question question = new Question();
		question.setQuestionId(questionId);
		question.setNote(note);
		questionDao.updateNote(question);
	}

	@Override
	public List<QuestionDTO> queryResearcherQuestionList(
			ResearcherQuestionQuery query, Page page) {
		if (query == null || query.getStatusType() == 0) {
			return Collections.emptyList();
		}
		List<Long> questionIds = switchQuery(query, page);
		List<QuestionDTO> result = ListUtils.map(questionIds,
				GetQuestionFn.INSTANCE);
		return result;
	}

	private List<Long> switchQuery(ResearcherQuestionQuery query, Page page) {
		if (query.getStatusType() == 1 && query.getMaterialNodeId() == null && query.getMinInputDate() == null
				&& query.getMaxInputDate() == null && query.getCreatorName() == null && query.getQuestionTypeId() == null
				&& query.getQuestionId() == null && query.getContent() == null) {
			// 查询条件只有学段学科
			return questionCheckPendingDao.queryResearcherQuestionIdList(query, page);
		} else {
			return questionDao.queryResearcherQuestionIdList(query, page);
		}
	}

	@Override
	public void updateIncUsedCount(Long questionId) {
		QuestionDTO backend = questionDao.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		questionDao.incUsedCount(questionId);

		incCountInCache(questionId, "usedCount");
	}

	private void incCountInCache(Long questionId, String field) {
		QuestionDTO cache = QuestionContext.getQuestionDTO(questionId);
		if (cache == null) {
			throw new ValidateException("que.question.not.exist");
		}
		Integer old = (Integer) BeanUtils.getProperty(cache, field);
		BeanUtils.setProperty(cache, field, old == null ? 1 : old + 1);
		QuestionContext.updateQuestionToCache(cache);
	}

	@Override
	public void updateIncFavCount(Long questionId) {
		questionDao.incFavCount(questionId);
		incCountInCache(questionId, "favCount");
	}

	@Override
	public void updateBatchIncFavCount(List<Long> questionIds) {
		questionDao.incBatchFavCount(questionIds);
		questionIds.forEach((questionId) -> {
			incCountInCache(questionId, "favCount");
		});
	}

	@Override
	public void updateIncPraiseCount(Long questionId) {
		QuestionDTO backend = questionDao.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		questionDao.incPraiseCount(questionId);

		incCountInCache(questionId, "praiseCount");
	}

	@Override
	public void updateIncCommentCount(Long questionId) {
		QuestionDTO backend = questionDao.getQuestion(questionId);
		if (backend == null) {
			throw new ValidateException("que.question.not.exist");
		}
		questionDao.incCommentCount(questionId);

		incCountInCache(questionId, "commentCount");
	}

	@Override
	public List<RepositoryRecord> queryPersonalQuestions(
			RepositoryQuestionQuery query, Page page) {
		List<Long> ids = questionDao.queryPersonalQuestions(query, page);
		return RepositoryContext.ofQuestions(ids);
	}

	@Override
	public List<RepositoryRecord> queryFavoriteQuestions(
			RepositoryQuestionQuery query, Page page) {
		List<Long> ids = questionDao.queryFavoriteQuestions(query, page);
		return RepositoryContext.ofQuestions(ids);
	}

	@Override
	public List<RepositoryRecord> querySchoolQuestions(
			RepositoryQuestionQuery query, Page page) {
		List<Long> ids = questionDao.querySchoolQuestions(query, page);
		return RepositoryContext.ofQuestions(ids);
	}

	@Override
	public List<RepositoryRecord> queryLeagueQuestions(RepositoryQuestionQuery query, Page page) {
		List<Long> ids = queryLeagueQuestionIds(query, page);
		return RepositoryContext.ofQuestions(ids);
	}

	private List<Long> queryLeagueQuestionIds(RepositoryQuestionQuery query, Page page) {
		if (query == null || query.getLeagueId() == null) {
			return Collections.emptyList();
		}
		Set<Long> ids = LeagueContext.toLeagueMemberIds(query.getLeagueId(),
				query.getLeagueMemberId(), query.getSchoolId());
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		query.setLeagueMemberIds(ids);
		return questionDao.queryLeagueQuestions(query, page);
	}
}
