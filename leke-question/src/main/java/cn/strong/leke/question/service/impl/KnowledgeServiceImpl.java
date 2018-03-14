/**
 * 
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.question.KnowledgeTreeContext;
import cn.strong.leke.core.business.tree.ReIndexVisitor;
import cn.strong.leke.core.business.tree.TraverseVisitor;
import cn.strong.leke.core.business.tree.TreeBuilder;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.question.dao.mybatis.KnowledgeDao;
import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.StatisKnowledge;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.remote.model.question.KlgPath;

/**
 * 
 * 描述: 知识点维护服务实现
 * 
 * @author liulb
 * @created 2014年4月25日 上午9:49:47
 * @since v1.0.0
 */
@Service
public class KnowledgeServiceImpl implements KnowledgeService {
	private static final Logger LOG = LoggerFactory.getLogger(KnowledgeService.class);
	@Autowired
	private KnowledgeDao knowledgeDao;

	@Override
	public void addKnowledgeRoot(Knowledge knowledge) {
		if (knowledge == null
				|| !StringUtils.hasText(knowledge.getKnowledgeName())
				|| knowledge.getSchoolStageId() == null
				|| knowledge.getSubjectId() == null) {
			throw new ValidateException("que.knowledge.info.incomplete");
		}
		int count = knowledgeDao.countKnowledgeRoot(knowledge);
		if (count > 0) {
			throw new ValidateException("que.knowledge.root.already.exist");
		}
		knowledge.setParentId(TreeCst.TREE_ROOT_ID);
		knowledge.setOrd(0);
		int lft = TreeCst.TREE_FIRST_LFT;
		knowledge.setLft(lft);
		knowledge.setRgt(lft + 1);
		knowledgeDao.insertKnowledge(knowledge);

		delCache(knowledge);
	}

	/*
	 * 清理缓存
	 */
	private void delCache(Knowledge klg) {
		KnowledgeTreeContext.deleteCache(klg.getSchoolStageId(), klg.getSubjectId());
	}

	@Override
	public void addKnowledge(Knowledge knowledge) {
		if (knowledge == null
				|| !StringUtils.hasText(knowledge.getKnowledgeName())
				|| knowledge.getParentId() == null) {
			throw new ValidateException("que.knowledge.info.incomplete");
		}
		Long parentId = knowledge.getParentId();
		Knowledge parent = knowledgeDao.getKnowledgeById(parentId);
		if (parent == null) {
			throw new ValidateException("que.knowledge.parent.not.exist");
		}
		knowledge.setSchoolStageId(parent.getSchoolStageId());
		knowledge.setSubjectId(parent.getSubjectId());
		int parentRgt = parent.getRgt();
		knowledge.setLft(parentRgt);
		knowledge.setRgt(parentRgt + 1);
		knowledgeDao.updateIncLftIndexGteLftVal(knowledge);
		knowledgeDao.updateIncRgtIndexGteLftVal(knowledge);
		knowledgeDao.insertKnowledge(knowledge);

		delCache(knowledge);
	}

	@Override
	public void updateKnowledge(Knowledge knowledge) {
		if (knowledge == null
				|| !StringUtils.hasText(knowledge.getKnowledgeName())
				|| knowledge.getKnowledgeId() == null) {
			throw new ValidateException("que.knowledge.info.incomplete");
		}
		Long nodeId = knowledge.getKnowledgeId();
		Knowledge backend = knowledgeDao.getKnowledgeById(nodeId);
		if (backend == null) {
			throw new ValidateException("que.knowledge.not.exist");
		}
		knowledgeDao.updateKnowledge(knowledge);

		delCache(backend);
	}

	@Override
	public void deleteKnowledge(Knowledge knowledge) {
		if (knowledge == null || knowledge.getKnowledgeId() == null) {
			throw new ValidateException("que.knowledge.info.incomplete");
		}
		Long nodeId = knowledge.getKnowledgeId();
		Knowledge backend = knowledgeDao.getKnowledgeById(nodeId);
		if (backend == null) {
			throw new ValidateException("que.knowledge.not.exist");
		}
		if (!backend.isLeaf()) {
			throw new ValidateException("que.knowledge.has.children");
		}
		knowledgeDao.updateDecRgtIndexGteLftVal(backend);
		knowledgeDao.updateDecLftIndexGteLftVal(backend);
		knowledgeDao.deleteKnowledge(knowledge);

		delCache(backend);
	}

	@Override
	public List<Knowledge> queryKnowledges(Knowledge knowledge) {
		return knowledgeDao.queryKnowledges(knowledge);
	}

	@Override
	public Knowledge getKnowledgeById(Long knowledgeId) {
		return knowledgeDao.getKnowledgeById(knowledgeId);
	}

	@Override
	public Knowledge getKnowledge(Long knowledgeId) {
		return knowledgeDao.getKnowledge(knowledgeId);
	}

	@Override
	public List<StatisKnowledge> queryQueKnowledgeTreeNodes(
			QueKnowledgeTreeQuery query) {
		return knowledgeDao.queryQueKnowledgeTreeNodes(query);
	}

	@Override
	public void moveUpNode(Knowledge knowledgeNode) {
		if (knowledgeNode == null || knowledgeNode.getKnowledgeId() == null) {
			throw new ValidateException("que.knowledgeNode.info.incomplete");
		}
		Long nodeId = knowledgeNode.getKnowledgeId();
		Knowledge toSwitchNode = knowledgeDao.getKnowledgeById(nodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.knowledgeNode.not.exist");
		}
		
		//都要是同个parent下
		List<Knowledge> orderFrontNodeList = knowledgeDao.getOrderFrontNode(toSwitchNode);
		Knowledge orderFrontMaxNode = getMaxOrderFrontNode(orderFrontNodeList);
		if (orderFrontMaxNode == null || orderFrontMaxNode.getKnowledgeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成-1
			List<Knowledge> sameOrderFrontNodeList = knowledgeDao.getSameOrderFrontNode(toSwitchNode);
			if (sameOrderFrontNodeList.size() == 0){
				throw new ValidateException("que.knowledgeNode.not.existFront");
			}
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord - 1);
			knowledgeDao.updateKnowledge(toSwitchNode);
		} else{
			switchKnowledgeNode(toSwitchNode, orderFrontMaxNode);
		}
		delCache(toSwitchNode);
	}
	

	@Override
	public void moveDownNode(Knowledge knowledgeNode) {
		if (knowledgeNode == null || knowledgeNode.getKnowledgeId() == null) {
			throw new ValidateException("que.knowledgeNode.info.incomplete");
		}
		Long nodeId = knowledgeNode.getKnowledgeId();
		Knowledge toSwitchNode = knowledgeDao.getKnowledgeById(nodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.knowledgeNode.not.existFront");
		}
		
		//都要是同个parent下
		List<Knowledge> orderAfterNodeList = knowledgeDao.getOrderAfterNode(toSwitchNode);
		Knowledge orderAfterMinNode = getMinOrderAfterNode(orderAfterNodeList);
		if (orderAfterMinNode == null || orderAfterMinNode.getKnowledgeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成+1
			
			List<Knowledge> sameOrderAfterNodeList = knowledgeDao.getSameOrderAfterNode(toSwitchNode);
			if (sameOrderAfterNodeList.size() == 0){
				throw new ValidateException("que.outlineNode.not.existAfter");
			}
			
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord + 1);
			knowledgeDao.updateKnowledge(toSwitchNode);
		} else{
			switchKnowledgeNode(toSwitchNode, orderAfterMinNode);
		}
		delCache(toSwitchNode);
	}
	
	private void switchKnowledgeNode(Knowledge toSwitchNode, Knowledge orderFrontNode) {
		Integer ord = toSwitchNode.getOrd(); // 序号

		toSwitchNode.setOrd(orderFrontNode.getOrd());
		orderFrontNode.setOrd(ord);
	
		knowledgeDao.updateKnowledge(toSwitchNode);
		knowledgeDao.updateKnowledge(orderFrontNode);
	}
	
	private Knowledge getMaxOrderFrontNode(List<Knowledge> orderFrontNodeList) {
		if (orderFrontNodeList.size() != 0){
			Knowledge outlineNodeReturn = orderFrontNodeList.get(0);
			for (Knowledge outlineNode : orderFrontNodeList){
				if (outlineNode.getOrd() > outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}
	
	
	private Knowledge getMinOrderAfterNode(List<Knowledge> orderAfterNodeList) {
		if (orderAfterNodeList.size() != 0){
			Knowledge outlineNodeReturn = orderAfterNodeList.get(0);
			for (Knowledge outlineNode : orderAfterNodeList){
				if (outlineNode.getOrd() < outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}

	@Override
	public void rebuildTreeIndexWithTx(Long schoolStageId, Long subjectId) {
		if (schoolStageId == null || subjectId == null) {
			throw new ValidateException("请指定学段学科");
		}
		Knowledge query = new Knowledge();
		query.setSchoolStageId(schoolStageId);
		query.setSubjectId(subjectId);
		List<Knowledge> nodes = queryKnowledges(query);
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}
		LOG.info("rebuild klg tree index for {}/{}", schoolStageId, subjectId);
		Knowledge tree = TreeBuilder.build(nodes, Knowledge::getKnowledgeId);
		tree.accept(new ReIndexVisitor<Knowledge>()); // 重建左右索引
		tree.accept(new TraverseVisitor<Knowledge>() {

			@Override
			public void preVisit(Knowledge node) {
				knowledgeDao.updateLftRgtIndex(node);
			}

		});

		// 清理缓存
		KnowledgeTreeContext.deleteCache(schoolStageId, subjectId);
	}

	@Override
	public List<Knowledge> getPath(Long knowledgeId) {
		return knowledgeDao.getPath(knowledgeId);
	}

	@Override
	public KlgPath getKlgPath(Long knowledgeId) {
		Knowledge node = knowledgeDao.getKnowledge(knowledgeId);
		if (node == null) {
			return null;
		}
		List<Knowledge> nodePath = knowledgeDao.getPath(knowledgeId);
		KlgPath result = new KlgPath();
		result.setSchoolStageId(node.getSchoolStageId());
		result.setSubjectId(node.getSubjectId());
		result.setKnowledgeId(node.getKnowledgeId());
		result.setKnowledgeName(node.getKnowledgeName());
		result.setPath(ListUtils.map(nodePath, KlgPath::toKlgNode));
		return result;
	}
	
}
