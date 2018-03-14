package cn.strong.leke.question.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.QuestionWorkbookNode;
import cn.strong.leke.question.service.IQuestionWbNodeService;
import cn.strong.leke.remote.service.question.IQuestionWbNodeRemoteService;

@Service
public class QuestionWbNodeRemoteService implements
		IQuestionWbNodeRemoteService {

	@Autowired
	private IQuestionWbNodeService questionWbNodeService;

	@Override
	public void insertQuestionWorkbookNodes(
			List<QuestionWorkbookNode> questionWbNodes) {
		questionWbNodeService.insertQuestionWorkbookNodes(questionWbNodes);
	}

	@Override
	public void deleteBatchQuestionWbNode(List<Long> questionIds,
			Long workbookNodeId) {
		questionWbNodeService.deleteBatchQuestionWbNode(questionIds,
				workbookNodeId);
	}

}
