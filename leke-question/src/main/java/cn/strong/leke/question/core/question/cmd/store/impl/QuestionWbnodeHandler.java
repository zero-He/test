/**
 * 
 */
package cn.strong.leke.question.core.question.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.question.QuestionWorkbookNode;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.question.cmd.store.IQuestionWbnodeHandler;
import cn.strong.leke.question.dao.mybatis.QuestionWorkbookNodeDao;
import cn.strong.leke.remote.model.question.WorkbookNodeRemote;
import cn.strong.leke.remote.service.question.IWorkbookNodeRemoteService;

/**
 * @author liulongbiao
 *
 */
@Service
public class QuestionWbnodeHandler implements IQuestionWbnodeHandler {
	@Resource
	private QuestionWorkbookNodeDao questionWorkbookNodeDao;
	@Resource
	private IWorkbookNodeRemoteService workbookNodeRemoteService;

	@Override
	public void add(Long questionId, Long workbookNodeId, User user) {
		Validation.isTrue(questionId != null && workbookNodeId != null,
				"que.workbookNode.info.incomplete");
		WorkbookNodeRemote node = workbookNodeRemoteService.getWorkbookNodeById(workbookNodeId);
		Validation.notNull(node, "指定习题册章节不存在");

		QuestionWorkbookNode assoc = new QuestionWorkbookNode();
		assoc.setQuestionId(questionId);
		assoc.setWorkbookNodeId(workbookNodeId);
		assoc.setWorkbookId(node.getWorkbookId());
		assoc.setPath(node.getPath());
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);
		questionWorkbookNodeDao.insertQuestionWorkbookNode(assoc);
	}

	@Override
	public void remove(Long questionId, Long workbookNodeId, User user) {
		questionWorkbookNodeDao
				.deleteQuestionWorkbookNode(questionId, workbookNodeId, user.getId());
	}


}
