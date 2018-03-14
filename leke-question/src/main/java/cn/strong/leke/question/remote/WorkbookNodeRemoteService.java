/**
 * 
 */
package cn.strong.leke.question.remote;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.model.question.querys.WorkbookTreeQuery;
import cn.strong.leke.question.service.IWorkbookNodeService;
import cn.strong.leke.remote.model.question.WorkbookNodeRemote;
import cn.strong.leke.remote.service.question.IWorkbookNodeRemoteService;

/**
 * @author liulongbiao
 *
 */
@Service
public class WorkbookNodeRemoteService implements IWorkbookNodeRemoteService {

	@Autowired
	private IWorkbookNodeService workbookNodeService;

	@Override
	public List<WorkbookNode> findWorkbookTreeNodes(WorkbookTreeQuery query) {
		return workbookNodeService.findWorkbookTreeNodes(query);
	}

	@Override
	public List<WorkbookNode> findByWorkbookId(Long workbookId) {
		return workbookNodeService.findByWorkbookId(workbookId);
	}

	@Override
	public WorkbookNodeRemote getWorkbookNodeById(Long workbookNodeId) {
		WorkbookNode node = workbookNodeService.getWorkbookNode(workbookNodeId);
		if (node == null) {
			return null;
		}
		WorkbookNodeRemote result = new WorkbookNodeRemote();
		result.setWorkbookId(node.getWorkbookId());
		result.setWorkbookNodeId(node.getWorkbookNodeId());
		result.setWorkbookNodeName(node.getWorkbookNodeName());
		result.setLft(node.getLft());
		result.setParentId(node.getParentId());
		List<WorkbookNode> paths = workbookNodeService.findPath(node);
		String path = paths.stream().map(WorkbookNode::getWorkbookNodeName)
				.collect(Collectors.joining("-"));
		result.setPath(path);
		return result;
	}

	@Override
	public WorkbookNode getRootNode(Long workbookId) {
		// TODO Auto-generated method stub
		return workbookNodeService.getRootNode(workbookId);
	}

}
