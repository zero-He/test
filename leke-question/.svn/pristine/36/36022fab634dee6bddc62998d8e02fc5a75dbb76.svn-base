package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.model.question.WorkbookNode;

/**
 *
 * 描述:习题册章节
 *
 * @author raolei
 * @created 2015年11月21日 下午2:33:43
 * @since v1.0.0
 */
public interface IWorkbookNodeDao {

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:33
	 * @param wn
	 * @return int
	 */
	int addWorkbookNode(WorkbookNode wn);
	
	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:36
	 * @param wn
	 * @return int
	 */
	int delWorkbookNode(WorkbookNode wn);

	/**
	 * 根据习题册ID删除节点
	 * 
	 * @author liulongbiao
	 * @param wn
	 */
	int deleteByWorkbookId(WorkbookNode wn);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:39
	 * @param wn
	 * @return int
	 */
	int updateWorkbookNode(WorkbookNode wn);

	/**
	 * 
	 * 描述:修改节点名称
	 * 
	 * @author raolei
	 * @created 2015年11月25日 下午1:41:43
	 * @param wn
	 * @return int
	 */
	int updateWorkbookNodeName(WorkbookNode wn);

	/**
	 * 给大于等于指定左值的所有左索引加2
	 * 
	 * @param workbookNode
	 * @return
	 */
	int updateIncLftIndexGteLftVal(WorkbookNode workbookNode);

	/**
	 * 给大于等于指定左值的所有右索引加2
	 * 
	 * @param workbookNode
	 * @return
	 */
	int updateIncRgtIndexGteLftVal(WorkbookNode workbookNode);

	/**
	 * 给大于等于指定左值的所有左索引减2
	 * 
	 * @param workbookNode
	 * @return
	 */
	int updateDecLftIndexGteLftVal(WorkbookNode workbookNode);

	/**
	 * 给大于等于指定左值的所有右索引减2
	 * 
	 * @param workbookNode
	 * @return
	 */
	int updateDecRgtIndexGteLftVal(WorkbookNode workbookNode);

	/**
	 * 更新左右索引值
	 * 
	 * @author liulb
	 * @created 2014年8月28日 下午4:04:32
	 * @param materialNode
	 */
	void updateLftRgtIndex(WorkbookNode materialNode);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:41
	 * @param workbookNodeId
	 * @return WorkbookNode
	 */
	WorkbookNode getWorkbookNode(Long workbookNodeId);

	/**
	 * 
	 * 根据习题册ID获取所有节点
	 * 
	 * @author raolei
	 * @created 2015年11月25日 下午4:11:29
	 * @param workbookId
	 * @return List<WorkbookNode>
	 */
	List<WorkbookNode> findByWorkbookId(Long workbookId);

	/**
	 * 根据习题册ID获取节点数量
	 * 
	 * @author liulongbiao
	 * @param workbookId
	 * @return
	 */
	int countByWorkbookId(Long workbookId);

	/**
	 * 获取根节点
	 * 
	 * @author liulongbiao
	 * @param workbookId
	 * @return
	 */
	List<WorkbookNode> findRootNodeByWorkbookId(Long workbookId);

	/**
	 * 
	 * 查询所有子节点
	 * 
	 * @author raolei
	 * @created 2015年11月23日 上午10:32:37
	 * @param parentId
	 * @return List<WorkbookNode>
	 */
	List<WorkbookNode> findChildren(Long parentId);

	/**
	 * 获取到某个节点的所有节点(路径)
	 * 
	 * @param node
	 * @return
	 */
	List<WorkbookNode> findPath(WorkbookNode node);

	/**
	 * 获取同parent下order在前的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<WorkbookNode> getOrderFrontNode(WorkbookNode toSwitchNode);

	/**
	 * 获取同parent下order相同id在后的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<WorkbookNode> getSameOrderAfterNode(WorkbookNode toSwitchNode);

	/**
	 * 获取同parent下order在后的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<WorkbookNode> getOrderAfterNode(WorkbookNode toSwitchNode);

	/**
	 * 获取同parent下order相同id在前的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<WorkbookNode> getSameOrderFrontNode(WorkbookNode toSwitchNode);

}
