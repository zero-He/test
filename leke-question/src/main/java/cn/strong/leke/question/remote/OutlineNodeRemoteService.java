package cn.strong.leke.question.remote;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.question.service.IOutlineNodeService;
import cn.strong.leke.question.service.OutlineService;
import cn.strong.leke.remote.service.question.IOutlineNodeRemoteService;

/**
 *
 * 描述: 大纲
 *
 * @author raolei
 * @created 2016年6月15日 下午2:13:58
 * @since v1.0.0
 */
@Service
public class OutlineNodeRemoteService implements IOutlineNodeRemoteService {

	@Resource
	private OutlineService outlineService;
	@Resource
	private IOutlineNodeService outlineNodeService;

	@Override
	public List<OutlineNode> queryOutlineNodes(Long outlineId, Long parentId) {
		if (outlineId == null || parentId == null) {
			return Collections.emptyList();
		}
		OutlineNode query = new OutlineNode();
		query.setOutlineId(outlineId);
		query.setParentId(parentId);
		return outlineService.queryOutlineNodes(query);
	}

	@Override
	public List<OutlineNode> findOutlineTreeNodes(OutlineNode outlineNode) {
		if (outlineNode == null || outlineNode.getOutlineId() == null) {
			return Collections.emptyList();
		}
		Long outlineId = outlineNode.getOutlineId();
		return outlineService.queryOutlineNodesByOutlineId(outlineId);
	}

	@Override
	public OutlineNode getOutlineNodeById(Long outlineNodeId) {
		return outlineNodeService.getOutlineNodeById(outlineNodeId);
	}

}
