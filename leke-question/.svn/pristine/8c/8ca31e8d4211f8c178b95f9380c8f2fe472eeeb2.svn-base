/**
 * 
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.StatisMaterialNode;

/**
 * 教材目录节点DAO
 * 
 * @author liulb
 * 
 */
public interface MaterialNodeDao {
	/**
	 * 新增教材节点
	 * 
	 * @param materialNode
	 * @return
	 */
	int insertMaterialNode(MaterialNode materialNode);

	/**
	 * 修改教材节点
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateMaterialNode(MaterialNode materialNode);

	/**
	 * 删除教材节点
	 * 
	 * @param materialNode
	 * @return
	 */
	int deleteMaterialNode(MaterialNode materialNode);

	/**
	 * 查询教材节点
	 * 
	 * @param materialNode
	 * @return
	 */
	List<MaterialNode> queryMaterialNodes(MaterialNode materialNode);

	/**
	 * 根据ID获取教材节点
	 * 
	 * @param materialNodeId
	 * @return
	 */
	MaterialNode getMaterialNodeById(Long materialNodeId);

	/**
	 * 给大于等于指定左值的所有左索引加2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateIncLftIndexGteLftVal(MaterialNode materialNode);

	/**
	 * 给大于等于指定左值的所有右索引加2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateIncRgtIndexGteLftVal(MaterialNode materialNode);

	/**
	 * 给大于等于指定左值的所有左索引减2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateDecLftIndexGteLftVal(MaterialNode materialNode);

	/**
	 * 给大于等于指定左值的所有右索引减2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateDecRgtIndexGteLftVal(MaterialNode materialNode);

	/**
	 * 更新教材节点的节点名称
	 * 
	 * @param material
	 * @return
	 */
	int updateMaterialName(Material material);

	/**
	 * 根据教材ID获取节点数
	 * 
	 * @param materialId
	 * @return
	 */
	int countByMaterialId(Long materialId);

	/**
	 * 描述: 根据教材ID删除整棵子树,用于删除教材时
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午8:57:48
	 * @since v1.0.0
	 * @param materialId
	 * @return int
	 */
	int deleteMaterialNodeByMaterialId(Long materialId);

	/**
	 * 
	 * 描述: 根据教材ID获取整棵子树
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午9:28:59
	 * @since v1.0.0
	 * @param materialId
	 * @return
	 * @return List<MaterialNode>
	 */
	List<MaterialNode> queryMaterialNodesByMaterialId(Long materialId);

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
	 * 更新左右索引值
	 * 
	 * @author liulb
	 * @created 2014年8月28日 下午4:04:32
	 * @since v1.1.0
	 * @param materialNode
	 */
	void updateLftRgtIndex(MaterialNode materialNode);

	/**
	 * 获取同parent下order在前的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<MaterialNode> getOrderFrontNode(MaterialNode toSwitchNode);

	/**
	 * 获取同parent下order相同id在前的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<MaterialNode> getSameOrderFrontNode(MaterialNode toSwitchNode);

	/**
	 * 获取同parent下order在后的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<MaterialNode> getOrderAfterNode(MaterialNode toSwitchNode);

	/**
	 * 获取同parent下order相同id在后的节点
	 * 
	 * @author qw
	 * @param toSwitchNode
	 */
	List<MaterialNode> getSameOrderAfterNode(MaterialNode toSwitchNode);

	/**
	 * 查找教材章节路径
	 * 
	 * @param materialNodeId
	 * @return
	 */
	List<MaterialNode> getPath(Long materialNodeId);
}
