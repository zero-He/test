/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.strong.leke.common.utils.Booleans;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.coll.JsArray;
import cn.strong.leke.common.utils.coll.Stack;
import cn.strong.leke.core.business.tree.ReIndexVisitor;
import cn.strong.leke.core.business.tree.TraverseVisitor;
import cn.strong.leke.core.business.tree.TreeBuilder;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookAddHandler;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.IWorkbookNodeDao;
import cn.strong.leke.question.dao.mybatis.MaterialNodeDao;
import cn.strong.leke.repository.common.model.RepoSaveContext;

/**
 * @author liulongbiao
 *
 */
@Service
public class WorkbookAddHandler implements IWorkbookAddHandler {

	@Resource
	private IWorkbookDao workbookDao;
	@Resource
	private IWorkbookNodeDao workbookNodeDao;
	@Resource
	private MaterialNodeDao materialNodeDao;

	@Override
	@Transactional
	public Long add(Workbook data, User user, RepoSaveContext<Workbook> ctx) {
		boolean copySections = Booleans.isTrue(ctx.attr("copySections"));
		if (copySections && data.getMaterialId() == null) {
			throw new ValidateException("que.material.not.exist");
		}

		data.setWorkbookId(null);

		Long userId = user.getId();
		data.setModifiedBy(userId);

		insertWorkbook(data);

		if (copySections) {
			copyMaterailNodesToWorkbookNodes(data, user);
		} else {
			WorkbookNode root = buildRootWorkbookNode(data, user);
			workbookNodeDao.addWorkbookNode(root);
		}

		return data.getWorkbookId();
	}

	private void insertWorkbook(Workbook data) {
		if (data.getStatus() == null) {
			data.setStatus(Workbook.STATUS_SHELF_OFF);
		}
		data.setSharePersonal(false);
		data.setShareSchool(false);
		data.setSharePlatform(false);
		workbookDao.addWorkbook(data);
	}

	/**
	 * 拷贝
	 * 
	 * @author liulongbiao
	 * @param wb
	 * @param user
	 */
	private void copyMaterailNodesToWorkbookNodes(Workbook wb, User user) {
		List<MaterialNode> matNodes = materialNodeDao.queryMaterialNodesByMaterialId(wb
				.getMaterialId());
		List<MaterialNode> nodes = ListUtils.filter(matNodes, node -> {
			Integer type = node.getNodeType();
			return type != null && !type.equals(MaterialNode.NODE_TYPE_KNOWLEDGE);
		});
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}

		MaterialNode tree = TreeBuilder.build(nodes, MaterialNode::getMaterialNodeId);
		tree.accept(new ReIndexVisitor<MaterialNode>());
		tree.setMaterialNodeName(wb.getWorkbookName()); // 设置根节点名称
		tree.accept(new WorkbookNodeCopyVisitor(workbookNodeDao, wb.getWorkbookId(), user.getId()));
	}

	private static class WorkbookNodeCopyVisitor extends TraverseVisitor<MaterialNode> {
		final IWorkbookNodeDao workbookNodeDao;
		final Long workbookId;
		final Long userId;
		final Stack<Long> stack;

		public WorkbookNodeCopyVisitor(IWorkbookNodeDao workbookNodeDao, Long workbookId,
				Long userId) {
			super();
			this.workbookNodeDao = workbookNodeDao;
			this.workbookId = workbookId;
			this.userId = userId;
			stack = new JsArray<Long>();
			stack.push(TreeCst.TREE_ROOT_ID);
		}

		@Override
		protected void preVisit(MaterialNode node) {
			WorkbookNode current = new WorkbookNode();
			current.setWorkbookNodeName(node.getMaterialNodeName());
			current.setWorkbookId(workbookId);
			current.setMaterialNodeId(node.getMaterialNodeId());
			current.setOrd(node.getOrd());
			current.setLft(node.getLft());
			current.setRgt(node.getRgt());
			current.setParentId(stack.peek());
			current.setCreatedBy(userId);
			current.setModifiedBy(userId);
			workbookNodeDao.addWorkbookNode(current);

			stack.push(current.getWorkbookNodeId());
		}

		@Override
		protected void postVisit(MaterialNode node) {
			stack.pop();
		}

	}

	/**
	 * 构建根节点
	 */
	private WorkbookNode buildRootWorkbookNode(Workbook wb, User user) {
		WorkbookNode node = new WorkbookNode();
		node.setWorkbookNodeName(wb.getWorkbookName());
		node.setWorkbookId(wb.getWorkbookId());
		node.setParentId(TreeCst.TREE_ROOT_ID);
		node.setOrd(0);
		int lft = TreeCst.TREE_FIRST_LFT;
		node.setLft(lft);
		node.setRgt(lft + 1);
		Long userId = user.getId();
		node.setCreatedBy(userId);
		node.setModifiedBy(userId);
		return node;
	}
}
