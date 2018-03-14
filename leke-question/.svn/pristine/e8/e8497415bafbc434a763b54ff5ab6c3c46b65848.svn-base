package cn.strong.leke.question.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.question.dao.mybatis.OutlineNodeDao;
import cn.strong.leke.question.service.IOutlineNodeService;

/**
 *
 * 描述:
 *
 * @author raolei
 * @created 2016年6月15日 下午2:20:04
 * @since v1.0.0
 */
@Service
public class OutlineNodeService implements IOutlineNodeService {

	@Resource
	private OutlineNodeDao outlineNodeDao;

	@Override
	public OutlineNode getOutlineNodeById(Long outlineNodeId) {
		return outlineNodeDao.getOutlineNodeById(outlineNodeId);
	}

	@Override
	public List<OutlineNode> queryOutlineNodes(Long outlineId, Long parentId) {
		OutlineNode outlineNode = new OutlineNode();
		outlineNode.setOutlineId(outlineId);
		outlineNode.setParentId(parentId);
		return outlineNodeDao.queryOutlineNodes(outlineNode);
	}

}
