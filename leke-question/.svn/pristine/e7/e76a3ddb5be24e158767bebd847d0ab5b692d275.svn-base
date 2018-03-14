/**
 * 
 */
package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.coll.JsArray;
import cn.strong.leke.common.utils.coll.Stack;
import cn.strong.leke.common.utils.function.Predicate;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.question.OutlineContext;
import cn.strong.leke.context.question.OutlineTreeContext;
import cn.strong.leke.core.business.tree.ReIndexVisitor;
import cn.strong.leke.core.business.tree.TraverseVisitor;
import cn.strong.leke.core.business.tree.TreeBuilder;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.model.question.Outline;
import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.MaterialNodeDao;
import cn.strong.leke.question.dao.mybatis.OutlineDao;
import cn.strong.leke.question.dao.mybatis.OutlineNodeDao;
import cn.strong.leke.question.model.outline.BaseOutlineQuery;
import cn.strong.leke.question.service.OutlineService;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;

/**
 * 
 * 描述: 提纲维护服务实现
 * 
 * @author qw
 * @created 2014年4月25日 上午9:49:47
 * @since v1.0.0
 */
@Service
public class OutlineServiceImpl implements OutlineService {

	@Autowired
	private OutlineDao outlineDao;
	@Autowired
	private OutlineNodeDao outlineNodeDao;
	@Resource
	private MaterialNodeDao materialNodeDao;
	
	@Override
	public List<Outline> queryOutline(Outline outline, Page page) {
		return outlineDao.queryOutline(outline, page);
	}
	
	@Override
	public List<Outline> queryOutlines(BaseOutlineQuery outline) {
		return outlineDao.queryOutlines(outline);
	}

	@Override
	public void updateOutlineName(Outline outline) {
		if (outline == null || outline.getOutlineId() == null
				|| !StringUtils.hasText(outline.getOutlineName())) {
			throw new ValidateException("que.material.info.incomplete");
		}
		String outlineName = outline.getOutlineName();
		Outline updates = new Outline();
		updates.setOutlineId(outline.getOutlineId());
		updates.setOutlineName(outlineName);
		updates.setModifiedBy(outline.getModifiedBy());
		outlineDao.updateOutlineName(updates);
		//outlineNodeDao.updateOutlineName(updates);

		List<OutlineNode> roots = outlineNodeDao.findRootNodeByOutlineId(outline.getOutlineId());
		for (OutlineNode root : roots) {
			root.setOutlineNodeName(outlineName);
			root.setModifiedBy(outline.getModifiedBy());
			outlineNodeDao.updateOutlineNodeName(root);
		}
		
		// 清理缓存
		OutlineContext.deleteCache();
		OutlineTreeContext.deleteCache(outline.getOutlineId());
	}

	@Override
	public void deleteOutline(Outline outline) {
		if (outline == null || outline.getOutlineId() == null) {
			throw new ValidateException("que.material.info.incomplete");
		}
		Long outlineId = outline.getOutlineId();
		Outline backend = outlineDao.getOutlineById(outlineId);
		if (backend == null) {
			throw new ValidateException("que.material.not.exist");
		}
		/*int count = outlineNodeDao.countByOutlineId(outlineId);
		if (count > 1) {
			throw new ValidateException("que.material.has.children");
		}*/
		outlineDao.deleteOutline(outline);
		outlineNodeDao.deleteOutlineNodeByOutlineId(outlineId);

		// 清理缓存
		OutlineContext.deleteCache();
		OutlineTreeContext.deleteCache(outline.getOutlineId());
	}
	
	@Override
	public List<OutlineNode> queryOutlineNodesByOutlineId(Long outlineId) {
		return outlineNodeDao.queryOutlineNodesByOutlineId(outlineId);
	}
	
	@Override
	public List<OutlineNode> queryOutlineNodes(OutlineNode query) {
		if (query == null || query.getParentId() == null) {
			return Collections.emptyList();
		}
		//query.setNodeType(MaterialNode.NODE_TYPE_KNOWLEDGE);
		return outlineNodeDao.queryOutlineNodes(query);
	}

	@Override
	public List<Outline> findOutlinesBySchIdGraIdSubId(Long schoolId, Long gradeId, Long subjectId) {
		if (schoolId == null || gradeId == null || subjectId == null) {
			return Collections.emptyList();
		}
		return outlineDao.findOutlinesBySchIdGraIdSubId(schoolId, gradeId, subjectId);
	}

	@Override
	public List<Outline> findOutlinesBySchIdStaIdSubId(Long schoolId, Long schoolStageId,
			Long subjectId) {
		if (schoolId == null || schoolStageId == null || subjectId == null) {
			return Collections.emptyList();
		}
		return outlineDao.findOutlinesBySchIdStaIdSubId(schoolId, schoolStageId, subjectId);
	}

	/**
	 * 拷贝
	 * 
	 * @author QW
	 * @param outline
	 * @param user
	 */
	private void copyMaterailNodesToWorkbookNodes(Outline outline, User user) {
		List<MaterialNode> matNodes = materialNodeDao.queryMaterialNodesByMaterialId(outline.getMaterialId());
		List<MaterialNode> nodes = ListUtils.filter(matNodes, MatSectionFilter.INSTANCE);
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}

		MaterialNode tree = TreeBuilder.build(nodes, MaterialNode::getMaterialNodeId);
		tree.accept(new ReIndexVisitor<MaterialNode>());
		tree.setMaterialNodeName(outline.getOutlineName()); // 设置根节点名称
		tree.accept(new WorkbookNodeCopyVisitor(outlineNodeDao, outline.getOutlineId(), user.getId()));
	}
	
	private static class WorkbookNodeCopyVisitor extends TraverseVisitor<MaterialNode> {
		final OutlineNodeDao outlineNodeDao;
		final Long outlineId;
		final Long userId;
		final Stack<Long> stack;

		public WorkbookNodeCopyVisitor(OutlineNodeDao outlineNodeDao, Long outlineId, Long userId) {
			super();
			this.outlineNodeDao = outlineNodeDao;
			this.outlineId = outlineId;
			this.userId = userId;
			stack = new JsArray<Long>();
			stack.push(TreeCst.TREE_ROOT_ID);
		}

		@Override
		protected void preVisit(MaterialNode node) {
			OutlineNode current = new OutlineNode();
			current.setOutlineNodeName(node.getMaterialNodeName());
			current.setOutlineId(outlineId);
			current.setMaterialNodeId(node.getMaterialNodeId());
			current.setOrd(node.getOrd());
			current.setLft(node.getLft());
			current.setRgt(node.getRgt());
			current.setParentId(stack.peek());
			current.setCreatedBy(userId);
			current.setModifiedBy(userId);
			outlineNodeDao.addOutlineNode(current);

			stack.push(current.getOutlineNodeId());
		}

		@Override
		protected void postVisit(MaterialNode node) {
			stack.pop();
		}

	}
	
	public static enum MatSectionFilter implements Predicate<MaterialNode> {
		INSTANCE;

		@Override
		public boolean test(MaterialNode node) {
			Integer type = node.getNodeType();
			return type != null && !type.equals(MaterialNode.NODE_TYPE_KNOWLEDGE);
		}

	}
	
	/**
	 * 构建根节点
	 */
	private OutlineNode buildRootOutlineNode(Outline outline, User user) {
		OutlineNode node = new OutlineNode();
		node.setOutlineNodeName(outline.getOutlineName());
		node.setOutlineId(outline.getOutlineId());
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
	
	@Override
	public void addOutline(Outline outline, boolean copySections, User user) {
		if (outline == null || StringUtils.isEmpty(outline.getOutlineName()) || outline.getSubjectId() == null
				|| user == null) {
			throw new ValidateException("que.workbook.info.incomplete");
		}
		if (copySections && outline.getMaterialId() == null) {
			throw new ValidateException("que.material.not.exist");
		}
		populateWorkbook(outline, user);
		//outlineDao.addOutline(outline);
		outlineDao.insertOutline(outline);
		if (copySections) {
			copyMaterailNodesToWorkbookNodes(outline, user);
		} else {
			OutlineNode root = buildRootOutlineNode(outline, user);
			outlineNodeDao.addOutlineNode(root);
		}
		// 清理缓存
		OutlineContext.deleteCache();
		//sendRecordAddEvent(outline);
	}
	
	/**
	 * 填充习题册
	 */
	private void populateWorkbook(Outline outline, User user) {
		if (outline.getStatus() == null) {
			outline.setStatus(Outline.STATUS_OFF);
		}
		/*	if (outline.getShareSchool() == null) {
			outline.setShareSchool(false);
		}
		if (outline.getSharePlatform() == null) {
			outline.setSharePlatform(false);
		}*/

		Long userId = user.getId();
		outline.setCreatedBy(userId);
		outline.setModifiedBy(userId);
		//outline.setCreatorUser(userName);
		School school = user.getCurrentSchool();
		if (school != null) {
			outline.setSchoolId(school.getId());
			outline.setSchoolName(school.getName());
		}
		
		Long gradeId = outline.getGradeId();
		SchoolStageRemote schoolStageRemote = SchoolStageContext.getSchoolStageByGradeId(gradeId);
		Long schoolStageId = schoolStageRemote.getSchoolStageId();
		String schoolStageName = schoolStageRemote.getSchoolStageName();
		outline.setSchoolStageId(schoolStageId);
		outline.setSchoolStageName(schoolStageName);
	}
	
	@Override
	public void addOutlineNode(OutlineNode outlineNode) {
		if (outlineNode == null || outlineNode.getParentId() == null
				|| !StringUtils.hasText(outlineNode.getOutlineNodeName())) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long parentId = outlineNode.getParentId();
		OutlineNode parent = outlineNodeDao.getOutlineNodeById(parentId);
		if (parent == null) {
			throw new ValidateException("que.materialNode.parent.not.exist");
		}
		outlineNode.setOutlineId(parent.getOutlineId());
		int parentRgt = parent.getRgt();
		outlineNode.setLft(parentRgt);
		outlineNode.setRgt(parentRgt + 1);
		outlineNodeDao.updateIncLftIndexGteLftVal(outlineNode);
		outlineNodeDao.updateIncRgtIndexGteLftVal(outlineNode);
		//outlineNodeDao.insertOutlineNode(outlineNode);
		outlineNodeDao.addOutlineNode(outlineNode);

		// 清理缓存
		OutlineTreeContext.deleteCache(parent.getOutlineId());
	}

	@Override
	public void updateOutlineNode(OutlineNode outlineNode) {
		if (outlineNode == null || outlineNode.getOutlineNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = outlineNode.getOutlineNodeId();
		OutlineNode backend = outlineNodeDao.getOutlineNodeById(nodeId);
		if (backend == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		outlineNodeDao.updateOutlineNode(outlineNode);

		// 清理缓存
		OutlineTreeContext.deleteCache(backend.getOutlineId());
	}
	
	@Override
	public void deleteOutlineNode(OutlineNode outlineNode) {
		if (outlineNode == null || outlineNode.getOutlineNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = outlineNode.getOutlineNodeId();
		OutlineNode backend = outlineNodeDao.getOutlineNodeById(nodeId);
		if (backend == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		if (!backend.isLeaf()) {
			throw new ValidateException("que.materialNode.has.children");
		}
		if (backend.isLevelOne()) {
			throw new ValidateException("que.materialNode.undeletable");
		}
		outlineNodeDao.updateDecRgtIndexGteLftVal(backend);
		outlineNodeDao.updateDecLftIndexGteLftVal(backend);
		outlineNodeDao.deleteOutlineNode(outlineNode);

		// 清理缓存
		OutlineTreeContext.deleteCache(backend.getOutlineId());
	}
	
	@Override
	public void moveUpNode(OutlineNode outlineNode) {
		if (outlineNode == null || outlineNode.getOutlineNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = outlineNode.getOutlineNodeId();
		OutlineNode toSwitchNode = outlineNodeDao.getOutlineNodeById(nodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		
		//都要是同个parent下
		List<OutlineNode> orderFrontNodeList = outlineNodeDao.getOrderFrontNode(toSwitchNode);
		OutlineNode orderFrontMaxNode = getMaxOrderFrontNode(orderFrontNodeList);
		if (orderFrontMaxNode == null || orderFrontMaxNode.getOutlineNodeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成-1
			List<OutlineNode> sameOrderFrontNodeList = outlineNodeDao.getSameOrderFrontNode(toSwitchNode);
			if (sameOrderFrontNodeList.size() == 0){
				throw new ValidateException("que.outlineNode.not.existFront");
			}
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord - 1);
			outlineNodeDao.updateOutlineNode(toSwitchNode);
			OutlineTreeContext.deleteCache(toSwitchNode.getOutlineId());
		} else{
			switchOutlineNode(toSwitchNode, orderFrontMaxNode);
			OutlineTreeContext.deleteCache(toSwitchNode.getOutlineId());
			OutlineTreeContext.deleteCache(orderFrontMaxNode.getOutlineId());
		}
		
	}
	
	private void switchOutlineNode(OutlineNode toSwitchNode, OutlineNode orderFrontNode) {
		Integer ord = toSwitchNode.getOrd(); // 序号

		toSwitchNode.setOrd(orderFrontNode.getOrd());
		orderFrontNode.setOrd(ord);
	
		outlineNodeDao.updateOutlineNodeTreeInfo(toSwitchNode);
		outlineNodeDao.updateOutlineNodeTreeInfo(orderFrontNode);
	}
	
	private OutlineNode getMaxOrderFrontNode(List<OutlineNode> orderFrontNodeList) {
		if (orderFrontNodeList.size() != 0){
			OutlineNode outlineNodeReturn = orderFrontNodeList.get(0);
			for (OutlineNode outlineNode : orderFrontNodeList){
				if (outlineNode.getOrd() > outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}
	
	@Override
	public void moveDownNode(OutlineNode outlineNode) {
		if (outlineNode == null || outlineNode.getOutlineNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = outlineNode.getOutlineNodeId();
		OutlineNode toSwitchNode = outlineNodeDao.getOutlineNodeById(nodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		
		//都要是同个parent下
		List<OutlineNode> orderAfterNodeList = outlineNodeDao.getOrderAfterNode(toSwitchNode);
		OutlineNode orderAfterMinNode = getMinOrderAfterNode(orderAfterNodeList);
		if (orderAfterMinNode == null || orderAfterMinNode.getOutlineNodeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成+1
			
			List<OutlineNode> sameOrderAfterNodeList = outlineNodeDao.getSameOrderAfterNode(toSwitchNode);
			if (sameOrderAfterNodeList.size() == 0){
				throw new ValidateException("que.outlineNode.not.existAfter");
			}
			
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord + 1);
			outlineNodeDao.updateOutlineNode(toSwitchNode);
			OutlineTreeContext.deleteCache(toSwitchNode.getOutlineId());
		} else{
			switchOutlineNode(toSwitchNode, orderAfterMinNode);
			OutlineTreeContext.deleteCache(toSwitchNode.getOutlineId());
			OutlineTreeContext.deleteCache(orderAfterMinNode.getOutlineId());
		}
		
	}
	
	private OutlineNode getMinOrderAfterNode(List<OutlineNode> orderAfterNodeList) {
		if (orderAfterNodeList.size() != 0){
			OutlineNode outlineNodeReturn = orderAfterNodeList.get(0);
			for (OutlineNode outlineNode : orderAfterNodeList){
				if (outlineNode.getOrd() < outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}
	
	@Override
	public void rebuildTreeIndexWithTx(Long outlineId) {
		if (outlineId == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		List<OutlineNode> nodes = queryOutlineNodesByOutlineId(outlineId);
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}
		OutlineNode tree = TreeBuilder.build(nodes, OutlineNode::getOutlineNodeId);
		tree.accept(new ReIndexVisitor<OutlineNode>()); // 重建左右索引
		tree.accept(new TraverseVisitor<OutlineNode>() {

			@Override
			public void preVisit(OutlineNode node) {
				outlineNodeDao.updateLftRgtIndex(node);
			}

		});
		OutlineTreeContext.deleteCache(outlineId);
		
	}
	
	@Override
	public void outlineUp(Long outlineId, User user) {
		Outline ol = new Outline();
		ol.setOutlineId(outlineId);
		ol.setModifiedBy(user.getId());
		outlineDao.outlineUp(ol);
	}

	@Override
	public void outlineDown(Long outlineId, User user) {
		Outline ol = new Outline();
		ol.setOutlineId(outlineId);
		ol.setModifiedBy(user.getId());
		outlineDao.outlineDown(ol);
	}
	
}
