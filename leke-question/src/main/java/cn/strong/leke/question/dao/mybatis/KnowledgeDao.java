/**
 * 
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.StatisKnowledge;

/**
 * 知识点DAO
 * 
 * @author liulb
 * 
 */
public interface KnowledgeDao {
	/**
	 * 新增知识点
	 * 
	 * @param knowledge
	 * @return
	 */
	int insertKnowledge(Knowledge knowledge);

	/**
	 * 修改知识点
	 * 
	 * @param knowledge
	 * @return
	 */
	int updateKnowledge(Knowledge knowledge);

	/**
	 * 删除知识点
	 * 
	 * @param knowledge
	 * @return
	 */
	int deleteKnowledge(Knowledge knowledge);

	/**
	 * 查询知识点
	 * 
	 * @param knowledge
	 * @return
	 */
	List<Knowledge> queryKnowledges(Knowledge knowledge);

	/**
	 * 根据ID获取知识点节点
	 * 
	 * @param knowledgeId
	 * @return
	 */
	Knowledge getKnowledgeById(Long knowledgeId);

	Knowledge getKnowledge(Long knowledgeId);

	/**
	 * 给大于等于指定左值的所有左索引加2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateIncLftIndexGteLftVal(Knowledge knowledge);

	/**
	 * 给大于等于指定左值的所有右索引加2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateIncRgtIndexGteLftVal(Knowledge knowledge);

	/**
	 * 给大于等于指定左值的所有左索引减2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateDecLftIndexGteLftVal(Knowledge knowledge);

	/**
	 * 给大于等于指定左值的所有右索引减2
	 * 
	 * @param materialNode
	 * @return
	 */
	int updateDecRgtIndexGteLftVal(Knowledge knowledge);

	/**
	 * 更新左右索引值
	 * 
	 * @param knowledge
	 * @return
	 */
	int updateLftRgtIndex(Knowledge knowledge);

	/**
	 * 
	 * 描述: 根据学段ID和学科ID获取根节点数
	 * 
	 * @author liulb
	 * @created 2014年4月25日 上午9:45:26
	 * @since v1.0.0
	 * @param knowledge
	 * @return
	 * @return int
	 */
	int countKnowledgeRoot(Knowledge knowledge);

	/**
	 * 
	 * 描述: 查询知识点题量统计
	 * 
	 * @author liulb
	 * @created 2014年5月23日 下午4:55:42
	 * @since v1.0.0
	 * @param query
	 * @return List<StatisKnowledge>
	 */
	List<StatisKnowledge> queryQueKnowledgeTreeNodes(QueKnowledgeTreeQuery query);

	/**
	 * 获取同parent下order在前的节点
	 * 
	 * @author qw
	 * @param materialNode
	 */
	List<Knowledge> getOrderFrontNode(Knowledge toSwitchNode);

	/**
	 * 获取同parent下order相同id在后的节点
	 * 
	 * @author qw
	 * @param materialNode
	 */
	List<Knowledge> getSameOrderAfterNode(Knowledge toSwitchNode);

	/**
	 * 获取同parent下order相同id在前的节点
	 * 
	 * @author qw
	 * @param materialNode
	 */
	List<Knowledge> getSameOrderFrontNode(Knowledge toSwitchNode);

	/**
	 * 获取同parent下order在后的节点
	 * 
	 * @author qw
	 * @param materialNode
	 */
	List<Knowledge> getOrderAfterNode(Knowledge toSwitchNode);

	/**
	 * 根据多个ID获取知识点
	 * 
	 * @param knowledgeIds
	 * @return
	 */
	List<Knowledge> findByIds(@Param("knowledgeIds") List<Long> knowledgeIds);

	/**
	 * 获取知识点路径
	 * 
	 * @param knowledgeId
	 * @return
	 */
	List<Knowledge> getPath(Long knowledgeId);

}
