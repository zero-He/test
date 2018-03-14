/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.dao;

import java.util.List;

import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgCounter;

/**
 * 教材章节与知识点关联数据源
 * 
 * @author liulongbiao
 *
 */
public interface IMatNodeKlgCounterDao {
	/**
	 * 获取教材章节关联知识点ID(最多10条)
	 * 
	 * @param materialNodeId
	 * @return
	 */
	List<MatNodeKlgCounter> findAssocKlgIds(Long materialNodeId);

	/**
	 * 清空数据
	 */
	void clear();

	/**
	 * 增加关联次数
	 * 
	 * @param materialNodeId
	 * @param knowledgeId
	 * @param count
	 */
	void add(Long materialNodeId, Long knowledgeId, int count);

	/**
	 * 增加1次关联次数
	 * 
	 * @param materialNodeId
	 * @param knowledgeId
	 */
	default void add(Long materialNodeId, Long knowledgeId) {
		add(materialNodeId, knowledgeId, 1);
	}

}
