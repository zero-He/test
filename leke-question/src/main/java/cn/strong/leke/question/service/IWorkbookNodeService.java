package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.model.question.querys.WorkbookTreeQuery;
import cn.strong.leke.model.user.User;

/**
 *
 * 描述:习题册章节
 *
 * @author raolei
 * @created 2015年11月21日 下午2:46:58
 * @since v1.0.0
 */
public interface IWorkbookNodeService {
	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:33
	 * @since v1.0.0
	 * @param wn
	 * @return
	 * @return void
	 */
	void addWorkbookNode(WorkbookNode wn, User user);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:36
	 * @since v1.0.0
	 * @param wn
	 * @return
	 * @return void
	 */
	void delWorkbookNode(Long workbookNodeId, User user);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:39
	 * @since v1.0.0
	 * @param wn
	 * @return
	 * @return void
	 */
	void updateWorkbookNode(WorkbookNode wn, User user);
	
	/**
	 *
	 * 描述:修改第一章节名称
	 *
	 * @author raolei
	 * @created 2015年11月25日 下午1:41:43
	 * @since v1.0.0
	 * @param wn
	 * @return
	 * @return void
	 */
	void updateWorkbookNodeName(WorkbookNode wn);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月21日 下午2:45:41
	 * @since v1.0.0
	 * @param workbookNodeId
	 * @return
	 * @return WorkbookNode
	 */
	WorkbookNode getWorkbookNode(Long workbookNodeId);

	/**
	 * 获取根节点
	 * 
	 * @author liulongbiao
	 * @param workbookId
	 * @return
	 */
	WorkbookNode getRootNode(Long workbookId);

	/**
	 * 
	 * 描述:查询所有子节点
	 * 
	 * @author raolei
	 * @created 2015年11月23日 上午10:34:14
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<WorkbookNode>
	 */
	List<WorkbookNode> findChildren(Long parentId);

	/**
	 * 查找树节点
	 * 
	 * @author liulongbiao
	 * @param query
	 * @return
	 */
	List<WorkbookNode> findWorkbookTreeNodes(WorkbookTreeQuery query);

	/**
	 * 
	 * 描述:重建索引
	 * 
	 * @author raolei
	 * @created 2015年11月25日 下午4:05:56
	 * @since v1.0.0
	 * @param workbookNodeId
	 * @return void
	 */
	void rebuildTreeIndexWithTx(Long workbookId);

	/**
	 * 根据习题册ID获取所有节点
	 * 
	 * @param workbookId
	 * @return
	 */
	List<WorkbookNode> findByWorkbookId(Long workbookId);

	/**
	 * 获取到某个节点的所有节点(路径)
	 * 
	 * @param node
	 * @return
	 */
	List<WorkbookNode> findPath(WorkbookNode node);
	

	/**
	 * 描述: 上移教材节点
	 * 
	 * @author qw
	 * @param workbookNode
	 */
	void moveUpNode(Long workbookNodeId, User user);

	/**
	 * 描述: 下移教材节点
	 * 
	 * @author qw
	 * @param workbookNode
	 */
	void moveDownNode(Long workbookNodeId, User user);
}
