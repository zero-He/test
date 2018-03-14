/**
 * 
 */
package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.context.question.MaterialTreeContext;
import cn.strong.leke.core.business.tree.ReIndexVisitor;
import cn.strong.leke.core.business.tree.TraverseVisitor;
import cn.strong.leke.core.business.tree.TreeBuilder;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.question.core.material.query.IMaterialFileQueryService;
import cn.strong.leke.question.dao.mybatis.MaterialDao;
import cn.strong.leke.question.dao.mybatis.MaterialNodeDao;
import cn.strong.leke.question.dao.mybatis.base.IStageSubjectPressDao;
import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.SectionKnowledgeAddEvent;
import cn.strong.leke.question.model.StatisMaterialNode;
import cn.strong.leke.question.model.base.MaterialDTO;
import cn.strong.leke.question.model.base.StageSubjectPress;
import cn.strong.leke.question.model.material.MaterialFile;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.question.service.MaterialService;
import cn.strong.leke.remote.model.question.MatPath;

/**
 * 教材服务实现
 * 
 * @author liulb
 * 
 */
@Service
public class MaterialServiceImpl implements MaterialService {
	private static final Logger LOG = LoggerFactory.getLogger(MaterialService.class);

	@Autowired
	private MaterialDao materialDao;
	@Autowired
	private MaterialNodeDao materialNodeDao;
	@Autowired
	private KnowledgeService knowledgeService;
	@Autowired
	private IStageSubjectPressDao stageSubjectPressDao;
	@Autowired
	private IMaterialFileQueryService materialFileQueryService;

	@Override
	public void addMaterial(Material material) {
		if (material == null || !StringUtils.hasText(material.getMaterialName())) {
			throw new ValidateException("que.material.info.incomplete");
		}
		material.setMaterialName(StringUtils.trim(material.getMaterialName()));
		if (materialDao.isExist(material) > 0) {
			throw new ValidateException("相同的教材已存在！");
		}
		materialDao.insertMaterial(material);
		MaterialNode materialNode = buildRootMaterialNode(material);
		materialNodeDao.insertMaterialNode(materialNode);

		// 清理缓存
		MaterialContext.deleteCache();
	}

	/*
	 * 从教材构建教材目录根节点
	 */
	private MaterialNode buildRootMaterialNode(Material material) {
		MaterialNode node = new MaterialNode();
		node.setMaterialNodeName(material.getMaterialName());
		node.setMaterialId(material.getMaterialId());
		node.setNodeType(MaterialNode.NODE_TYPE_MATERIAL);
		node.setSchoolStageId(material.getSchoolStageId());
		node.setGradeId(material.getGradeId());
		node.setSubjectId(material.getSubjectId());
		node.setParentId(TreeCst.TREE_ROOT_ID);
		node.setOrd(0);
		int lft = TreeCst.TREE_FIRST_LFT;
		node.setLft(lft);
		node.setRgt(lft + 1);
		return node;
	}

	@Override
	public void updateMaterialName(Material material) {
		if (material == null || material.getMaterialId() == null
				|| !StringUtils.hasText(material.getMaterialName())) {
			throw new ValidateException("que.material.info.incomplete");
		}
		Long materialId = material.getMaterialId();
		String materialName = StringUtils.trim(material.getMaterialName());
		Material old = materialDao.getMaterialById(materialId);
		if (old == null) {
			throw new ValidateException("que.material.info.incomplete");
		}
		String oldMaterialName = StringUtils.trim(old.getMaterialName());
		if (!oldMaterialName.equals(materialName)) {
			if (materialDao.isExist(material) > 0) {
				throw new ValidateException("相同的学段科目出版社的教材名称已存在！");
			}
		}
		Material updates = new Material();
		updates.setMaterialId(material.getMaterialId());
		updates.setMaterialName(materialName);
		updates.setModifiedBy(material.getModifiedBy());
		updates.setOrd(material.getOrd());
		materialDao.updateMaterialName(updates);
		materialNodeDao.updateMaterialName(updates);

		// 清理缓存
		MaterialContext.deleteCache();
		MaterialTreeContext.deleteCache(material.getMaterialId());
	}

	@Override
	public void deleteMaterial(Material material) {
		if (material == null || material.getMaterialId() == null) {
			throw new ValidateException("que.material.info.incomplete");
		}
		Long materialId = material.getMaterialId();
		Material backend = materialDao.getMaterialById(materialId);
		if (backend == null) {
			throw new ValidateException("que.material.not.exist");
		}
		int count = materialNodeDao.countByMaterialId(materialId);
		if (count > 1) {
			throw new ValidateException("que.material.has.children");
		}
		materialDao.deleteMaterial(material);
		materialNodeDao.deleteMaterialNodeByMaterialId(materialId);

		// 清理缓存
		MaterialContext.deleteCache();
		MaterialTreeContext.deleteCache(material.getMaterialId());
	}

	@Override
	public List<Material> queryMaterials(Material material, Page page) {
		return materialDao.queryMaterials(material, page);
	}

	@Override
	public List<MaterialDTO> queryMaterialDTOs(Material material, Page page) {
		List<Material> materials = materialDao.queryMaterials(material, page);
		if (CollectionUtils.isEmpty(materials)) {
			return Collections.emptyList();
		}
		List<MaterialDTO> dtos = ListUtils.newArrayList();
		materials.forEach(m -> {
			MaterialDTO dto = new MaterialDTO();
			BeanUtils.copyProperties(dto, m);
			Long schoolStageId = dto.getSchoolStageId();
			Long subjectId = dto.getSubjectId();
			Long pressId = dto.getPressId();
			StageSubjectPress ssp = stageSubjectPressDao.getStageSubjectPress(schoolStageId, subjectId, pressId);
			if (ssp != null) {
				dto.setIsMain(ssp.getIsMain());
			} else {
				dto.setIsMain(Boolean.FALSE);
			}
			MaterialFile materialFile = materialFileQueryService.getByMaterialId(m.getMaterialId());
			if (materialFile != null) {
				dto.setType(materialFile.getType());
				dto.setFileId(materialFile.getFileId());
			}
			dtos.add(dto);
		});
		return dtos;
	}

	@Override
	public void addMaterialNode(MaterialNode materialNode) {
		if (materialNode == null || materialNode.getParentId() == null
				|| !StringUtils.hasText(materialNode.getMaterialNodeName())) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long parentId = materialNode.getParentId();
		MaterialNode parent = materialNodeDao.getMaterialNodeById(parentId);
		if (parent == null) {
			throw new ValidateException("que.materialNode.parent.not.exist");
		}
		materialNode.setSchoolStageId(parent.getSchoolStageId());
		materialNode.setGradeId(parent.getGradeId());
		materialNode.setSubjectId(parent.getSubjectId());
		materialNode.setMaterialId(parent.getMaterialId());
		int parentRgt = parent.getRgt();
		materialNode.setLft(parentRgt);
		materialNode.setRgt(parentRgt + 1);
		materialNodeDao.updateIncLftIndexGteLftVal(materialNode);
		materialNodeDao.updateIncRgtIndexGteLftVal(materialNode);
		materialNodeDao.insertMaterialNode(materialNode);

		// 清理缓存
		MaterialTreeContext.deleteCache(parent.getMaterialId());
	}

	@Override
	public void updateMaterialNode(MaterialNode materialNode) {
		if (materialNode == null || materialNode.getMaterialNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = materialNode.getMaterialNodeId();
		MaterialNode backend = materialNodeDao.getMaterialNodeById(nodeId);
		if (backend == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		materialNodeDao.updateMaterialNode(materialNode);

		// 清理缓存
		MaterialTreeContext.deleteCache(backend.getMaterialId());
	}

	@Override
	public void deleteMaterialNode(MaterialNode materialNode) {
		if (materialNode == null || materialNode.getMaterialNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = materialNode.getMaterialNodeId();
		MaterialNode backend = materialNodeDao.getMaterialNodeById(nodeId);
		if (backend == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		if (!backend.isLeaf()) {
			throw new ValidateException("que.materialNode.has.children");
		}
		if (backend.isLevelOne()) {
			throw new ValidateException("que.materialNode.undeletable");
		}
		materialNodeDao.updateDecRgtIndexGteLftVal(backend);
		materialNodeDao.updateDecLftIndexGteLftVal(backend);
		materialNodeDao.deleteMaterialNode(materialNode);

		// 清理缓存
		MaterialTreeContext.deleteCache(backend.getMaterialId());
	}

	@Override
	public List<MaterialNode> queryMaterialNodes(MaterialNode materialNode) {
		return materialNodeDao.queryMaterialNodes(materialNode);
	}

	@Override
	public List<MaterialNode> queryMaterialNodesByMaterialId(Long materialId) {
		return materialNodeDao.queryMaterialNodesByMaterialId(materialId);
	}

	@Override
	public MaterialNode getMaterialNodeById(Long materialNodeId) {
		return materialNodeDao.getMaterialNodeById(materialNodeId);
	}

	@Override
	public List<MaterialNode> queryKnowledgeNodes(MaterialNode query) {
		if (query == null || query.getParentId() == null) {
			return Collections.emptyList();
		}
		query.setNodeType(MaterialNode.NODE_TYPE_KNOWLEDGE);
		return materialNodeDao.queryMaterialNodes(query);
	}

	@Override
	public void addSectionKnowledges(SectionKnowledgeAddEvent event) {
		if (event == null || event.getMaterialNodeId() == null
				|| CollectionUtils.isEmpty(event.getKnowledgeIds())) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long parentId = event.getMaterialNodeId();
		MaterialNode backend = materialNodeDao.getMaterialNodeById(parentId);
		if (backend == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		List<Long> knowledgeIds = event.getKnowledgeIds();
		for (Long knowledgeId : knowledgeIds) {
			Knowledge knowledge = knowledgeService
					.getKnowledgeById(knowledgeId);
			if(knowledge == null) {
				throw new ValidateException("que.knowledge.not.exist");
			}

			MaterialNode node = new MaterialNode();
			node.setNodeType(MaterialNode.NODE_TYPE_KNOWLEDGE);
			node.setParentId(parentId);
			node.setKnowledgeId(knowledgeId);
			node.setMaterialNodeName(knowledge.getKnowledgeName());
			node.setCreatedBy(event.getCreatedBy());
			node.setModifiedBy(event.getModifiedBy());

			addMaterialNode(node);
		}

		// 清理缓存
		MaterialTreeContext.deleteCache(backend.getMaterialId());
	}

	@Override
	public List<StatisMaterialNode> queryQueKnowledgeTreeNodes(
			QueKnowledgeTreeQuery query) {
		return materialNodeDao.queryQueKnowledgeTreeNodes(query);
	}

	@Override
	public void rebuildTreeIndexWithTx(Long materialId) {
		if (materialId == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		List<MaterialNode> nodes = queryMaterialNodesByMaterialId(materialId);
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}
		LOG.info("rebuild mat tree index for {}", materialId);
		MaterialNode tree = TreeBuilder.build(nodes, MaterialNode::getMaterialNodeId);
		tree.accept(new ReIndexVisitor<MaterialNode>()); // 重建左右索引
		tree.accept(new TraverseVisitor<MaterialNode>() {

			@Override
			public void preVisit(MaterialNode node) {
				materialNodeDao.updateLftRgtIndex(node);
			}

		});

		// 清理缓存
		MaterialTreeContext.deleteCache(materialId);
	}
	
	@Override
	public void moveUpNode(MaterialNode materialNode) {
		if (materialNode == null || materialNode.getMaterialNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = materialNode.getMaterialNodeId();
		MaterialNode toSwitchNode = materialNodeDao.getMaterialNodeById(nodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.materialNode.not.exist");
		}
		
		//都要是同个parent下
		List<MaterialNode> orderFrontNodeList = materialNodeDao.getOrderFrontNode(toSwitchNode);
		MaterialNode orderFrontMaxNode = getMaxOrderFrontNode(orderFrontNodeList);
		if (orderFrontMaxNode == null || orderFrontMaxNode.getMaterialNodeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成-1
			List<MaterialNode> sameOrderFrontNodeList = materialNodeDao.getSameOrderFrontNode(toSwitchNode);
			if (sameOrderFrontNodeList.size() == 0){
				throw new ValidateException("que.materialNode.not.existFront");
			}
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord - 1);
			materialNodeDao.updateMaterialNode(toSwitchNode);
		} else{
			switchMaterialNode(toSwitchNode, orderFrontMaxNode);
		}

		MaterialTreeContext.deleteCache(toSwitchNode.getMaterialId());
	}
	

	@Override
	public void moveDownNode(MaterialNode materialNode) {
		if (materialNode == null || materialNode.getMaterialNodeId() == null) {
			throw new ValidateException("que.materialNode.info.incomplete");
		}
		Long nodeId = materialNode.getMaterialNodeId();
		MaterialNode toSwitchNode = materialNodeDao.getMaterialNodeById(nodeId);
		if (toSwitchNode == null) {
			throw new ValidateException("que.materialNode.not.existFront");
		}
		
		//都要是同个parent下
		List<MaterialNode> orderAfterNodeList = materialNodeDao.getOrderAfterNode(toSwitchNode);
		MaterialNode orderAfterMinNode = getMinOrderAfterNode(orderAfterNodeList);
		if (orderAfterMinNode == null || orderAfterMinNode.getMaterialNodeId() == null){
			//如果没有同一个父节点下的 前面的树节点 就把ord 设成+1
			
			List<MaterialNode> sameOrderAfterNodeList = materialNodeDao.getSameOrderAfterNode(toSwitchNode);
			if (sameOrderAfterNodeList.size() == 0){
				throw new ValidateException("que.outlineNode.not.existAfter");
			}
			
			Integer ord = toSwitchNode.getOrd(); // 序号
			toSwitchNode.setOrd(ord + 1);
			materialNodeDao.updateMaterialNode(toSwitchNode);
		} else{
			switchMaterialNode(toSwitchNode, orderAfterMinNode);
		}

		MaterialTreeContext.deleteCache(toSwitchNode.getMaterialId());
	}
	
	private void switchMaterialNode(MaterialNode toSwitchNode, MaterialNode orderFrontNode) {
		Integer ord = toSwitchNode.getOrd(); // 序号

		toSwitchNode.setOrd(orderFrontNode.getOrd());
		orderFrontNode.setOrd(ord);
	
		materialNodeDao.updateMaterialNode(toSwitchNode);
		materialNodeDao.updateMaterialNode(orderFrontNode);
	}
	
	private MaterialNode getMaxOrderFrontNode(List<MaterialNode> orderFrontNodeList) {
		if (orderFrontNodeList.size() != 0){
			MaterialNode outlineNodeReturn = orderFrontNodeList.get(0);
			for (MaterialNode outlineNode : orderFrontNodeList){
				if (outlineNode.getOrd() > outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}
	
	
	private MaterialNode getMinOrderAfterNode(List<MaterialNode> orderAfterNodeList) {
		if (orderAfterNodeList.size() != 0){
			MaterialNode outlineNodeReturn = orderAfterNodeList.get(0);
			for (MaterialNode outlineNode : orderAfterNodeList){
				if (outlineNode.getOrd() < outlineNodeReturn.getOrd()){
					outlineNodeReturn = outlineNode;
				}
			}
			return outlineNodeReturn;
		}
		
		return null;
	}

	@Override
	public MatPath getMatPath(Long materialNodeId) {
		MaterialNode node = materialNodeDao.getMaterialNodeById(materialNodeId);
		if (node == null) {
			return null;
		}
		List<MaterialNode> nodePath = materialNodeDao.getPath(materialNodeId);
		Long materialId = node.getMaterialId();
		Material mat = MaterialContext.getMaterial(materialId);
		MatPath result = new MatPath();
		result.setSchoolStageId(mat.getSchoolStageId());
		result.setSubjectId(mat.getSubjectId());
		result.setPressId(mat.getPressId());
		result.setMaterialId(mat.getMaterialId());
		result.setMaterialNodeId(node.getMaterialNodeId());
		result.setMaterialNodeName(node.getMaterialNodeName());
		result.setPath(ListUtils.map(nodePath, MatPath::toMatNode));
		return result;
	}
}
