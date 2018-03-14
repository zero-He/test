/**
 * 
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.SectionKnowledgeAddEvent;
import cn.strong.leke.question.model.StatisMaterialNode;
import cn.strong.leke.question.model.base.MaterialDTO;
import cn.strong.leke.remote.model.question.MatPath;

/**
 * 教材服务接口
 * 
 * @author liulb
 * 
 */
public interface MaterialService {

	/**
	 * 添加教材
	 * 
	 * @param material
	 */
	void addMaterial(Material material);

	/**
	 * 更新教材名称
	 * 
	 * @param material
	 */
	void updateMaterialName(Material material);

	/**
	 * 删除教材
	 * 
	 * @param material
	 */
	void deleteMaterial(Material material);

	/**
	 * 查询教材
	 * 
	 * @param material
	 * @return
	 */
	List<Material> queryMaterials(Material material, Page page);

	List<MaterialDTO> queryMaterialDTOs(Material material, Page page);

	/**
	 * 添加教材目录节点
	 * 
	 * @param materialNode
	 */
	void addMaterialNode(MaterialNode materialNode);

	/**
	 * 修改教材目录节点信息(不包含父节点ID、左右索引和删除标识)
	 * 
	 * @param materialNode
	 */
	void updateMaterialNode(MaterialNode materialNode);

	/**
	 * 删除教材目录节点
	 * 
	 * @param materialNode
	 */
	void deleteMaterialNode(MaterialNode materialNode);

	/**
	 * 查询教材目录节点
	 * 
	 * @param materialNode
	 * @return
	 */
	List<MaterialNode> queryMaterialNodes(MaterialNode materialNode);

	/**
	 * 
	 * 描述: 根据教材ID获取整棵子树
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午9:27:43
	 * @since v1.0.0
	 * @param materialId
	 * @return
	 * @return List<MaterialNode>
	 */
	List<MaterialNode> queryMaterialNodesByMaterialId(Long materialId);

	/**
	 * 根据教材章节ID获取该节点信息
	 * 
	 * @param materialNodeId
	 * @return
	 */
	MaterialNode getMaterialNodeById(Long materialNodeId);

	/**
	 * 
	 * 描述: 查询指定节点下的知识点节点
	 * 
	 * @author liulb
	 * @created 2014年5月15日 上午9:44:14
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<MaterialNode>
	 */
	List<MaterialNode> queryKnowledgeNodes(MaterialNode query);

	/**
	 * 
	 * 描述: 新增知识点节点
	 * 
	 * @author liulb
	 * @created 2014年5月15日 下午3:23:28
	 * @since v1.0.0
	 * @param event
	 * @return void
	 */
	void addSectionKnowledges(SectionKnowledgeAddEvent event);

	/**
	 * 
	 * 描述: 查询题目知识点树节点
	 * 
	 * @author liulb
	 * @created 2014年5月20日 上午11:00:42
	 * @since v1.0.0
	 * @param query
	 * @return List<StatisMaterialNode>
	 */
	List<StatisMaterialNode> queryQueKnowledgeTreeNodes(
			QueKnowledgeTreeQuery query);

	/**
	 * 
	 * 描述: 重建教材目录树左右索引
	 * 
	 * @author liulb
	 * @created 2014年8月28日 上午8:55:29
	 * @since v1.0.0
	 * @param materialId
	 * @return void
	 */
	void rebuildTreeIndexWithTx(Long materialId);

	/**
	 * 描述: 上移教材节点
	 * 
	 * @author qw
	 * @param materialNode
	 */
	void moveUpNode(MaterialNode materialNode);

	/**
	 * 描述: 下移教材节点
	 * 
	 * @author qw
	 * @param materialNode
	 */
	void moveDownNode(MaterialNode materialNode);

	/**
	 * 获取教材节点完整路径信息
	 * 
	 * @param materialNodeId
	 * @return
	 */
	MatPath getMatPath(Long materialNodeId);
}
