package cn.strong.leke.question.core.question.cmd.store.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionMessage;
import cn.strong.leke.question.core.question.cmd.store.IQuestionMessageHandler;
import cn.strong.leke.question.dao.mybatis.QuestionDao;

@Service
public class QuestionMessageHandler implements IQuestionMessageHandler {

	@Resource
	private QuestionDao questionDao;

	@Override
	public void updateQuestionMessages(QuestionMessage questionMessage) {
		Validation.isTrue(questionMessage != null && questionMessage.getPaperId() != null, "que.question.info.incomplete");
		if (CollectionUtils.isNotEmpty(questionMessage.getNewQuestionIds())) {
			Long paperId = questionMessage.getPaperId();
			List<Long> questionIds = questionMessage.getNewQuestionIds();
			questionDao.updateQuestionsPaperId(paperId, questionIds);
		}
		List<Long> qids = questionMessage.getQuestionIds();
		if (CollectionUtils.isNotEmpty(qids)) {
			qids.forEach(qid -> {
				questionDao.incUsedCount(qid);
				incCountSetPaperIdInCache(qid, questionMessage.getPaperId(), "usedCount");
			});
		}

	}

	private void incCountSetPaperIdInCache(Long questionId, Long paperId, String field) {
		QuestionDTO cache = QuestionContext.getQuestionDTO(questionId);
		if (cache == null) {
			throw new ValidateException("que.question.not.exist");
		}
		cache.setPaperId(paperId);
		Integer old = (Integer) BeanUtils.getProperty(cache, field);
		BeanUtils.setProperty(cache, field, old == null ? 1 : old + 1);
		QuestionContext.updateQuestionToCache(cache);
	}

}
