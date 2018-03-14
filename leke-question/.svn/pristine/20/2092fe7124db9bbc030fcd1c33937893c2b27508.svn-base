/**
 * 
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.StatisKnowledge;
import cn.strong.leke.remote.model.question.KlgPath;

/**
 * 知识点维护服务接口
 * 
 * @author liulb
 * 
 */
public interface KnowledgeService {

	/**
	 * 添加知识点根节点
	 * 
	 * @param knowledge
	 */
	void addKnowledgeRoot(Knowledge knowledge);

	/**
	 * 添加知识点
	 * 
	 * @param knowledge
	 */
	void addKnowledge(Knowledge knowledge);

	/**
	 * 修改知识点(不包含父节点ID、左右索引和删除标识)
	 * 
	 * @param knowledge
	 */
	void updateKnowledge(Knowledge knowledge);

	/**
	 * 删除知识点
	 * 
	 * @param knowledge
	 */
	void deleteKnowledge(Knowledge knowledge);

	/**
	 * 
	 * 描述: 根据条件查询知识点
	 * 
	 * @author liulb
	 * @created 2014年4月25日 上午11:12:04
	 * @since v1.0.0
	 * @param knowledge
	 * @return
	 * @return List<Knowledge>
	 */
	List<Knowledge> queryKnowledges(Knowledge knowledge);

	/**
	 * 
	 * 描述: 根据ID获取知识点
	 * 
	 * @author liulb
	 * @created 2014年5月15日 下午3:36:11
	 * @since v1.0.0
	 * @param knowledgeId
	 * @return Knowledge
	 */
	Knowledge getKnowledgeById(Long knowledgeId);

	Knowledge getKnowledge(Long knowledgeId);

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
	 * 描述: 上移教材节点
	 * 
	 * @author qw
	 * @param Knowledge
	 */
	void moveUpNode(Knowledge knowledgeNode);

	/**
	 * 描述: 下移教材节点
	 * 
	 * @author qw
	 * @param Knowledge
	 */
	void moveDownNode(Knowledge knowledgeNode);

	/**
	 * 重新构建树索引
	 * 
	 * @param schoolStageId
	 * @param subjectId
	 */
	void rebuildTreeIndexWithTx(Long schoolStageId, Long subjectId);

	/**
	 * 获取知识点路径
	 * 
	 * @param knowledgeId
	 * @return
	 */
	List<Knowledge> getPath(Long knowledgeId);

	/**
	 * 获取知识点路径
	 * 
	 * @param knowledgeId
	 * @return
	 */
	KlgPath getKlgPath(Long knowledgeId);
}
