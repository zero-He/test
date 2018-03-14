/**
 * 
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.QuestionWorkbookNode;
import cn.strong.leke.question.dao.mybatis.QuestionWorkbookNodeDao;
import cn.strong.leke.question.service.IQuestionWbNodeService;

/**
 * @author liulongbiao
 *
 */
@Service
public class QuestionWbNodeService implements IQuestionWbNodeService {

	@Autowired
	private QuestionWorkbookNodeDao questionWorkbookNodeDao;

	@Override
	public void addQuestionWbNode(QuestionWorkbookNode assoc) {
		questionWorkbookNodeDao.insertQuestionWorkbookNode(assoc);
	}

	@Override
	public void deleteQuestionWbNodes(Long questionId) {
		questionWorkbookNodeDao.deleteQuestionWorkbookNodeByQuestionId(questionId);
	}

	@Override
	public List<QuestionWorkbookNode> findQuestionWbNodes(Long questionId) {
		return questionWorkbookNodeDao.findQuestionWorkbookNodesByQuestionId(questionId);
	}

	@Override
	public void insertQuestionWorkbookNodes(List<QuestionWorkbookNode> questionWbNodes) {
		if (CollectionUtils.isEmpty(questionWbNodes)) {
			throw new ValidateException("questionWbNodes should not be null.");
		}
		questionWorkbookNodeDao.insertQuestionWorkbookNodes(questionWbNodes);
	}

	@Override
	public void deleteBatchQuestionWbNode(List<Long> questionIds, Long workbookNodeId) {
		if (CollectionUtils.isEmpty(questionIds)) {
			throw new ValidateException("questionIds should not be null.");
		}
		if (workbookNodeId == null) {
			throw new ValidateException("workbookNodeId should not be null.");
		}
		questionWorkbookNodeDao.deleteBatchQuestionWbNode(questionIds, workbookNodeId);
	}
}
