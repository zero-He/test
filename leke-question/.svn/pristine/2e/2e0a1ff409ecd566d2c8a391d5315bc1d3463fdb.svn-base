package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.OutlineNode;

/**
 *
 * 描述: 大纲章节
 *
 * @author raolei
 * @created 2016年6月15日 下午2:18:44
 * @since v1.0.0
 */
public interface IOutlineNodeService {

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2016年6月22日 下午2:27:31
	 * @since v1.0.0
	 * @param outlineNodeId
	 * @return
	 * @return OutlineNode
	 */
	OutlineNode getOutlineNodeById(Long outlineNodeId);

	/**
	 *
	 * 描述: 根据大纲Id和父Id查询章节
	 *
	 * @author raolei
	 * @created 2016年6月15日 下午2:19:17
	 * @since v1.0.0
	 * @param outlineId
	 * @param parentId
	 * @return
	 * @return List<OutlineNode>
	 */
	List<OutlineNode> queryOutlineNodes(Long outlineId, Long parentId);

}
