/**
 * 
 */
package cn.strong.leke.question.analysis.mongo.dao;

import java.util.List;

import cn.strong.leke.question.analysis.mongo.model.SmartPaperQueryCache;

/**
 * @author liulongbiao
 *
 */
public interface ISmartPaperQueryCacheDao {
	/**
	 * 添加记录
	 * 
	 * @param items
	 */
	void addAll(List<SmartPaperQueryCache> items);

	/**
	 * 根据类型获取缓存记录
	 * 
	 * @param queryId
	 * @param typeId
	 * @return
	 */
	SmartPaperQueryCache getByType(String queryId, Long typeId);
}
