package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.question.WorkbookTreeContext;
import cn.strong.leke.core.business.tree.ReIndexVisitor;
import cn.strong.leke.core.business.tree.TraverseVisitor;
import cn.strong.leke.core.business.tree.TreeBuilder;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.model.question.querys.WorkbookTreeQuery;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.IWorkbookNodeDao;
import cn.strong.leke.question.service.IWorkbookNodeService;

/**
 *
 * 描述:习题册章节
 *
 * @author raolei
 * @created 2015年11月21日 下午2:46:52
 * @since v1.0.0
 */
@Service
public class WorkbookNodeService implements IWorkbookNodeService {

	@Resource
	private IWorkbookNodeDao workbookNodeDao;

	@Override
	public void addWorkbookNode(WorkbookNode wn, User user) {
		if (wn == null || wn.getParentId() == null
				|| wn.getWorkbookId() == null) {
			throw new ValidateException("que.workbookNode.info.incomplete");
		}
		Long userId = user.getId();
		wn.setCreatedBy(userId);
		wn.setModifiedBy(userId);
		Long parentId = wn.getParentId();
		WorkbookNode parent = workbookNodeDao.getWorkbookNode(parentId);
		Validation.notNull(parent, "que.workbookNode.parent.not.exist");
		if (wn.getOrd() == null) {
			int count = workbookNodeDao.findChildren(parentId).size();
			wn.setOrd(count + 1);
		}

		int parentRgt = parent.getRgt();
		wn.setLft(parentRgt);
		wn.setRgt(parentRgt + 1);
		workbookNodeDao.updateIncLftIndexGteLftVal(wn);
		workbookNodeDao.updateIncRgtIndexGteLftVal(wn);
		workbookNodeDao.addWorkbookNode(wn);

		WorkbookTreeContext.deleteCache(wn.getWorkbookId());
	}

	@Override
	public void delWorkbookNode(Long workbookNodeId, User user) {
		Validation.notNull(workbookNodeId, "que.workbookNode.info.incomplete");
		WorkbookNode wn = new WorkbookNode();
		wn.setWorkbookNodeId(workbookNodeId);
		wn.setModifiedBy(user.getId());
		WorkbookNode backend = workbookNodeDao.getWorkbookNode(workbookNodeId);
		Validation.notNull(backend, "que.workbookNode.not.exist");
		Validation.isTrue(backend.isLeaf(), "que.workbookNode.has.children");
		Validation.isFalse(backend.isLevelOne(), "que.workbookNode.undeletable");

		workbookNodeDao.updateDecRgtIndexGteLftVal(backend);
		workbookNodeDao.updateDecLftIndexGteLftVal(backend);
		workbookNodeDao.delWorkbookNode(wn);

		WorkbookTreeContext.deleteCache(backend.getWorkbookId());
	}

	@Override
	public void updateWorkbookNode(WorkbookNode wn, User user) {
		if (wn == null || wn.getWorkbookNodeId() == null
				|| StringUtils.isEmpty(wn.getWorkbookNodeName())) {
			throw new ValidateException("que.workbookNode.info.incomplete");
		}
		WorkbookNode backend = workbookNodeDao.getWorkbookNode(wn.getWorkbookNodeId());
		Validation.notNull(backend, "que.workbookNode.not.exist");
		wn.setModifiedBy(user.getId());
		workbookNodeDao.updateWorkbookNode(wn);

		WorkbookTreeContext.deleteCache(backend.getWorkbookId());
	}

	@Override
	public void updateWorkbookNodeName(WorkbookNode wn) {
		if (wn == null || wn.getWorkbookNodeId() == null
				|| wn.getWorkbookNodeName() == null) {
			throw new ValidateException("que.workbookNode.info.incomplete");
		}
		WorkbookNode backend = workbookNodeDao.getWorkbookNode(wn.getWorkbookNodeId());
		Validation.notNull(backend, "que.workbookNode.not.exist");
		workbookNodeDao.updateWorkbookNodeName(wn);

		WorkbookTreeContext.deleteCache(backend.getWorkbookId());
	}

	@Override
	public WorkbookNode getWorkbookNode(Long workbookNodeId) {
		return workbookNodeDao.getWorkbookNode(workbookNodeId);
	}

	@Override
	public WorkbookNode getRootNode(Long workbookId) {
		List<WorkbookNode> roots = workbookNodeDao.findRootNodeByWorkbookId(workbookId);
		return CollectionUtils.getFirst(roots);
	}

	@Override
	public List<WorkbookNode> findChildren(Long parentId) {
		return workbookNodeDao.findChildren(parentId);
	}

	@Override
	public List<WorkbookNode> findWorkbookTreeNodes(WorkbookTreeQuery query) {
		if (query == null || query.getWorkbookId() == null) {
			return Collections.emptyList();
		}
		Long parentId = query.getParentId();
		if (parentId == null || parentId.equals(TreeCst.TREE_ROOT_ID)) {
			return workbookNodeDao.findRootNodeByWorkbookId(query.getWorkbookId());
		} else {
			return workbookNodeDao.findChildren(parentId);
		}
	}

	@Override
	public void rebuildTreeIndexWithTx(Long workbookId) {
		if (workbookId == null) {
			throw new ValidateException("que.workbookNode.info.incomplete");
		}
		List<WorkbookNode> nodes = workbookNodeDao.findByWorkbookId(workbookId);
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}
		WorkbookNode tree = TreeBuilder.build(nodes, WorkbookNode::getWorkbookNodeId);
		tree.accept(new ReIndexVisitor<WorkbookNode>()); // 重建左右索引
		tree.accept(new TraverseVisitor<WorkbookNode>() {

			@Override
			public void preVisit(WorkbookNode node) {
				workbookNodeDao.updateLftRgtIndex(node);
			}

		});
		WorkbookTreeContext.deleteCache(workbookId);
	}

	@Override
	public List<WorkbookNode> findByWorkbookId(Long workbookId) {
		return workbookNodeDao.findByWorkbookId(workbookId);
	}

	@Override
	public List<WorkbookNode> findPath(WorkbookNode node) {
		if (node == null) {
			return Collections.emptyList();
		}
		return workbookNodeDao.findPath(node);
	}

	@Override
	public void moveUpNode(Long workbookNodeId, User user) {
		if (workbookNodeId == null) {
			throw new ValidateException("que.workbookNode.info.incomplete");
		}
		WorkbookNode toSwitchNode = workbookNodeDao.getWorkbookNode(workbookNodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.workbookNode.not.exist");
		}
		
		//都要是同个parent下
		List<WorkbookNode> orderFrontNodeList = workbookNodeDao.getOrderFrontNode(toSwitchNode);
		WorkbookNode orderFrontMaxNode = getMaxOrderFrontNode(orderFrontNodeList);
		if (orderFrontMaxNode == null || orderFrontMaxNode.getWorkbookNodeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成-1
			List<WorkbookNode> sameOrderFrontNodeList = workbookNodeDao.getSameOrderFrontNode(toSwitchNode);
			if (sameOrderFrontNodeList.size() == 0){
				throw new ValidateException("que.workbookNode.not.existFront");
			}
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord - 1);
			toSwitchNode.setModifiedBy(user.getId());
			workbookNodeDao.updateWorkbookNode(toSwitchNode);
			WorkbookTreeContext.deleteCache(toSwitchNode.getWorkbookId());
		} else{
			switchWorkbookNode(toSwitchNode, orderFrontMaxNode);
			WorkbookTreeContext.deleteCache(toSwitchNode.getWorkbookId());
			WorkbookTreeContext.deleteCache(orderFrontMaxNode.getWorkbookId());
		}
	}
	

	@Override
	public void moveDownNode(Long workbookNodeId, User user) {
		if (workbookNodeId == null) {
			throw new ValidateException("que.workbookNode.info.incomplete");
		}
		WorkbookNode toSwitchNode = workbookNodeDao.getWorkbookNode(workbookNodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.workbookNode.not.existFront");
		}
		
		//都要是同个parent下
		List<WorkbookNode> orderAfterNodeList = workbookNodeDao.getOrderAfterNode(toSwitchNode);
		WorkbookNode orderAfterMinNode = getMinOrderAfterNode(orderAfterNodeList);
		if (orderAfterMinNode == null || orderAfterMinNode.getWorkbookNodeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成+1
			
			List<WorkbookNode> sameOrderAfterNodeList = workbookNodeDao.getSameOrderAfterNode(toSwitchNode);
			if (sameOrderAfterNodeList.size() == 0){
				throw new ValidateException("que.workbookNode.not.existAfter");
			}
			
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord + 1);
			toSwitchNode.setModifiedBy(user.getId());
			workbookNodeDao.updateWorkbookNode(toSwitchNode);
			WorkbookTreeContext.deleteCache(toSwitchNode.getWorkbookId());
		} else{
			switchWorkbookNode(toSwitchNode, orderAfterMinNode);
			// 清理缓存
			WorkbookTreeContext.deleteCache(toSwitchNode.getWorkbookId());
			WorkbookTreeContext.deleteCache(orderAfterMinNode.getWorkbookId());
		}
	}
	
	private void switchWorkbookNode(WorkbookNode toSwitchNode, WorkbookNode orderFrontNode) {
		Integer ord = toSwitchNode.getOrd(); // 序号

		toSwitchNode.setOrd(orderFrontNode.getOrd());
		orderFrontNode.setOrd(ord);
	
		workbookNodeDao.updateWorkbookNode(toSwitchNode);
		workbookNodeDao.updateWorkbookNode(orderFrontNode);
	}
	
	private WorkbookNode getMaxOrderFrontNode(List<WorkbookNode> orderFrontNodeList) {
		if (orderFrontNodeList.size() != 0){
			WorkbookNode outlineNodeReturn = orderFrontNodeList.get(0);
			for (WorkbookNode outlineNode : orderFrontNodeList){
				if (outlineNode.getOrd() > outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}
	
	
	private WorkbookNode getMinOrderAfterNode(List<WorkbookNode> orderAfterNodeList) {
		if (orderAfterNodeList.size() != 0){
			WorkbookNode outlineNodeReturn = orderAfterNodeList.get(0);
			for (WorkbookNode outlineNode : orderAfterNodeList){
				if (outlineNode.getOrd() < outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}
}
