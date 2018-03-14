/**
 * 
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.model.question.OutlineNode;

/**
 * 教材目录节点DAO
 * 
 * @author liulb
 * 
 */
public interface OutlineNodeDao {
	
	/**
	 * 查询提纲节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	List<OutlineNode> queryOutlineNodes(OutlineNode outlineNode);

	/**
	 * 根据ID获取提纲节点
	 * 
	 * @param outlineId
	 * @return
	 */
	List<OutlineNode> queryOutlineNodesByOutlineId(Long outlineId);

	/**
	 * 添加提纲节点
	 * 
	 * @param root
	 * @return
	 */
	void addOutlineNode(OutlineNode root);

	/**
	 * 通过Id查询提纲节点
	 * 
	 * @param nodeId
	 * @return
	 */
	OutlineNode getOutlineNodeById(Long nodeId);

	/**
	 * 给大于等于指定左值的所有左索引加2
	 * 
	 * @param outlineNode
	 * @return
	 */
	void updateIncLftIndexGteLftVal(OutlineNode outlineNode);

	/**
	 * 给大于等于指定右值的所有左索引加2
	 * 
	 * @param outlineNode
	 * @return
	 */
	void updateIncRgtIndexGteLftVal(OutlineNode outlineNode);

	/**
	 * 更新大纲节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	void updateOutlineNode(OutlineNode outlineNode);

	/**
	 * 更新大纲节点树信息
	 * 
	 * @param outlineNode
	 * @return
	 */
	void updateOutlineNodeTreeInfo(OutlineNode outlineNode);
	
	/**
	 * 删除大纲节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	void deleteOutlineNode(OutlineNode outlineNode);

	/**
	 * 给大于等于指定左值的所有左索引减2
	 * 
	 * @param backend
	 * @return
	 */
	void updateDecRgtIndexGteLftVal(OutlineNode backend);

	/**
	 * 给大于等于指定左值的所有右索引减2
	 * 
	 * @param backend
	 * @return
	 */
	void updateDecLftIndexGteLftVal(OutlineNode backend);

	/**
	 * 交换提纲两节点的order 左值 右值
	 * 
	 * @param outlineNode
	 * @param orderFrontNode
	 * @return
	 */
	void switchOutlineNode(OutlineNode outlineNode, OutlineNode orderFrontNode);

	/**
	 * 获取同个parentId下相同order的Id在前节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	List<OutlineNode> getSameOrderFrontNode(OutlineNode outlineNode);

	/**
	 * 获取同个parentId下前一个order的节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	List<OutlineNode> getOrderFrontNode(OutlineNode outlineNode);

	/**
	 * 获取同个parentId下后一个order的节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	List<OutlineNode> getOrderAfterNode(OutlineNode toSwitchNode);

	/**
	 * 获取同个parentId下相同order的Id在后节点
	 * 
	 * @param outlineNode
	 * @return
	 */
	List<OutlineNode> getSameOrderAfterNode(OutlineNode toSwitchNode);

	/**
	 * 更新左右索引值
	 * 
	 * @param outlineNode
	 * @return
	 */
	void updateLftRgtIndex(OutlineNode node);
	
	/**
	 * 根据提纲ID删除节点
	 * 
	 * @author qw
	 * @param outlineId
	 */
	int deleteOutlineNodeByOutlineId(Long outlineId);

	List<OutlineNode> findRootNodeByOutlineId(Long outlineId);

	void updateOutlineNodeName(OutlineNode root);
}
