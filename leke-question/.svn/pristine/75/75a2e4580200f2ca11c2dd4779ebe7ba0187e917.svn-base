/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.duplication.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.batch.BatchAction;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.core.business.util.Pages;
import cn.strong.leke.framework.exceptions.LekeRuntimeException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.duplication.QueDupCst;
import cn.strong.leke.question.duplication.QueSimDoc;
import cn.strong.leke.question.duplication.QuestionContentExtracter;
import cn.strong.leke.question.duplication.model.DupJob;
import cn.strong.leke.question.duplication.model.DupQuestionQuery;
import cn.strong.leke.question.duplication.model.SimQue;
import cn.strong.leke.question.duplication.model.SimQueDTO;
import cn.strong.leke.question.duplication.model.UpdatedQuestionQuery;
import cn.strong.leke.question.duplication.service.DupJobService;
import cn.strong.leke.question.duplication.service.DuplicationQuestionManager;
import cn.strong.leke.question.duplication.service.DuplicationQuestionService;
import cn.strong.leke.question.duplication.service.QueSimDocService;
import cn.strong.leke.question.duplication.service.SimQueService;
import cn.strong.leke.question.service.QuestionManager;

/**
 * 
 * 
 * @author liulongbiao
 */
@Service
public class DuplicationQuestionManagerImpl implements
		DuplicationQuestionManager {

	private static Logger logger = LoggerFactory
			.getLogger(DuplicationQuestionManagerImpl.class);

	@Autowired
	private DupJobService dupJobService;
	@Autowired
	private QueSimDocService queSimDocService;
	@Autowired
	private DuplicationQuestionService questionService;
	@Autowired
	private SimQueService simQueService;
	@Autowired
	private QuestionManager questionManager;

	@Override
	public void runDupJob() {
		logger.info("【标记重复任务】开始 ：{} ", DateUtils.formatTime(new Date()));
		long ms1 = System.currentTimeMillis();
		try {
			doDupJob();
		} catch (RuntimeException e) {
			logger.info("【标记重复任务】失败: ", e);
			throw e;
		} finally {
			long ms2 = System.currentTimeMillis();
			logger.info("【标记重复任务】结束 ：{} ", DateUtils.formatTime(new Date()));
			logger.info("【标记重复任务】总共耗时： {} ms", (ms2 - ms1));
		}
	}

	private void doDupJob() {
		ObjectId jobId = new ObjectId();
		dupJobService.save(jobId, DupJob.STATUS_INIT);
		try {
			DupJob lastJob = dupJobService.getLastSuccess();
			Date now = jobId.getDate();
			Date last = lastJob == null ? null : lastJob.get_id().getDate();
			updateRemovedPlatformQuestions(now, last);
			dupJobService.save(jobId, DupJob.STATUS_REMOVED_UPDATED);
			updateUpdatedPlatformQuestions(now, last);
			dupJobService.save(jobId, DupJob.STATUS_SUCCESS);
		} catch (Exception e) {
			dupJobService.save(jobId, DupJob.STATUS_FAILED);
			throw new RuntimeException("dup job error.", e);
		}
	}

	@Override
	public void updateRemovedPlatformQuestions(Date now, Date last) {
		UpdatedQuestionQuery query = new UpdatedQuestionQuery();
		query.setNow(now);
		query.setLast(last);

		logger.info("start update removed plarform question.");

		batchExecute(query, new Function<UpdatedQuestionQuery, List<Long>>() {

			@Override
			public List<Long> apply(UpdatedQuestionQuery query) {
				return questionService.queryRemovedPlatformQuestionIds(query);
			}

		}, new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				removeDuplication(questionId);
			}

		});
	}

	private void removeDuplication(Long questionId) {
		if (questionId == null) {
			return;
		}
		queSimDocService.deleteQueSimDoc(questionId);
		removeSimQue(questionId);
		if (logger.isTraceEnabled()) {
			logger.trace("removeDuplication {} ", questionId);
		}
	}

	private void batchExecute(UpdatedQuestionQuery query,
			Function<UpdatedQuestionQuery, List<Long>> queryFn,
			BatchAction<Long> action) {
		int batchSize = query.getBatchSize();
		List<Long> questionIds = queryFn.apply(query);
		while (CollectionUtils.isNotEmpty(questionIds)) {
			for (Long qid : questionIds) {
				action.execute(qid);
			}

			if (questionIds.size() == batchSize) {
				Long lastId = questionIds.get(batchSize - 1);
				query.setLastQuestionId(lastId);
				questionIds = queryFn.apply(query);
			} else {
				questionIds = null;
			}
		}
	}

	@Override
	public void updateUpdatedPlatformQuestions(Date now, Date last) {
		UpdatedQuestionQuery query = new UpdatedQuestionQuery();
		query.setNow(now);
		query.setLast(last);

		logger.info("start update updated plarform question.");

		batchExecute(query, new Function<UpdatedQuestionQuery, List<Long>>() {

			@Override
			public List<Long> apply(UpdatedQuestionQuery query) {
				return questionService.queryUpdatedPlatformQuestionIds(query);
			}

		}, new BatchAction<Long>() {

			@Override
			public void execute(Long questionId) {
				updateDuplication(questionId);
			}

		});
	}

	private void updateDuplication(Long questionId) {
		QuestionDTO dto = QuestionContext.getQuestionDTO(questionId);
		QueSimDoc doc = QuestionContentExtracter.getQueSimDoc(dto);
		if (doc == null || doc.getTermCount() < QueDupCst.MIN_DUP_TERM_COUNT) {
			// 小于最小分词数的文档不处理
			return;
		}

		List<Long> simIds = queSimDocService.findSimilarQuestionIds(doc);
		if (needRemoveOldSimQue(questionId, simIds)) {
			removeSimQue(questionId);
		}

		boolean mayDup = CollectionUtils.isNotEmpty(simIds);
		questionService.updateMayDup(questionId, mayDup);
		if (mayDup) {
			for (Long simId : simIds) {
				simQueService.addRef(questionId, simId);
				simQueService.addRef(simId, questionId);
			}
		}

		queSimDocService.saveQueSimDoc(doc);
		if (logger.isTraceEnabled()) {
			logger.trace("updateDuplication {} ", questionId);
		}
	}

	private boolean needRemoveOldSimQue(Long questionId, List<Long> simIds) {
		boolean result = false;
		SimQue old = simQueService.getById(questionId);
		if (old != null) {
			List<Long> oldSimIds = old.getSimIds();
			result = simIds.size() != oldSimIds.size();
			if (!result) {
				List<Long> subtraction = CollectionUtils.subtract(oldSimIds,
						simIds);
				result = !subtraction.isEmpty();
			}
		}
		return result;
	}

	@Override
	public void removeSimQue(Long questionId) {
		if (questionId == null) {
			return;
		}
		simQueService.removeById(questionId);
		simQueService.findContains(questionId).forEach((Consumer<SimQue>) ref -> {
			removeRefSimQue(questionId, ref);
		});
	}

	private void removeRefSimQue(Long questionId, SimQue ref) {
		if (ref == null) {
			return;
		}
		Long refId = ref.getQid();
		List<Long> simIds = ref.getSimIds();
		simIds.remove(questionId);
		if (CollectionUtils.isEmpty(simIds)) {
			questionService.updateMayDup(refId, false);
			removeDuplication(refId);
		} else {
			simQueService.removeRef(refId, questionId);
		}
	}

	@Override
	public List<SimQueDTO> queryDupQuestions(DupQuestionQuery query, Page page) {
		List<SimQueDTO> result = new ArrayList<SimQueDTO>();
		List<Long> qids = questionService.queryDupQuestionIds(query, page);
		if (CollectionUtils.isNotEmpty(qids)) {
			for (Long qid : qids) {
				SimQue sq = simQueService.getById(qid);
				if (sq != null && CollectionUtils.isNotEmpty(sq.getSimIds())) {
					result.add(toSimQueDTO(sq));
				} else {
					// 重新设置可能遗漏更新的标识
					questionService.updateMayDup(qid, false);
				}
			}
		}
		return result;
	}

	private SimQueDTO toSimQueDTO(SimQue sq) {
		SimQueDTO result = new SimQueDTO();
		result.setRecord(QuestionContext.getQuestionDTO(sq.getQid()));
		Page similarities = toSimilarities(sq, null);
		result.setSimilarities(similarities);
		return result;
	}

	private Page toSimilarities(SimQue sq, Page page) {
		if (page == null) {
			page = new Page();
		}
		if (sq == null) {
			page.setTotalSize(0);
			page.setDataList(Collections.emptyList());
		} else {
			List<Long> ids = Pages.ofPage(sq.getSimIds(), page);
			List<QuestionDTO> dataList = ListUtils.map(ids,
					QuestionDTOFn.INSTANCE);
			page.setDataList(dataList);
		}
		return page;
	}

	private static enum QuestionDTOFn implements Function<Long, QuestionDTO> {
		INSTANCE;

		@Override
		public QuestionDTO apply(Long questionId) {
			return questionId == null ? null : QuestionContext
					.getQuestionDTO(questionId);
		}

	}

	@Override
	public Page findSimilarities(Long questionId, Page page) {
		SimQue sq = simQueService.getById(questionId);
		return toSimilarities(sq, page);
	}

	@Override
	public void deleteQuestion(Long questionId, User user) {
		QuestionDTO dto = new QuestionDTO();
		dto.setQuestionId(questionId);
		dto.setModifiedBy(user.getModifiedBy());
		dto.setOperatorName(user.getUserName());
		questionManager.deleteQuestionDTO(dto);

		removeDuplication(questionId);
	}

	@Override
	public void disableQuestionWithTx(Long questionId, User user) {
		QuestionDTO dto = new QuestionDTO();
		dto.setQuestionId(questionId);
		dto.setModifiedBy(user.getModifiedBy());
		dto.setOperatorName(user.getUserName());
		questionManager.disableQuestion(dto, user);

		removeDuplication(questionId);
	}

	@Override
	public void updateNotDup(Long questionId, User user) {
		questionService.updateMayDup(questionId, false);
		removeDuplication(questionId);
	}

	@Override
	public void disableOtherQuestionsWithTx(Long dupGroupId, Long questionId,
			User user) {
		if (dupGroupId == null || questionId == null) {
			throw new LekeRuntimeException("信息不完整");
		}

		// 获取相似分组数据应该在更新非重复标识前，否则相似分组信息可能就被移除了
		SimQue group = simQueService.getById(dupGroupId);

		updateNotDup(questionId, user);

		if (group == null) {
			return;
		}
		disableQuestionWhenNotEq(group.getQid(), questionId, user);
		List<Long> simIds = group.getSimIds();
		if (CollectionUtils.isNotEmpty(simIds)) {
			for (Long simId : simIds) {
				disableQuestionWhenNotEq(simId, questionId, user);
			}
		}
	}

	private void disableQuestionWhenNotEq(Long simId, Long questionId, User user) {
		if (simId == null || simId.equals(questionId)) {
			return;
		}
		disableQuestionWithTx(simId, user);
	}
}
