/**
 * 
 */
package cn.strong.leke.question.analysis.db.dao.mybatis;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.analysis.db.model.SmartPaperDbQuery;
import cn.strong.leke.question.analysis.db.model.SmartPaperQue;

/**
 * 智能组卷习题DAO
 * 
 * @author liulongbiao
 *
 */
public interface ISmartPaperQuestionDao {

	/**
	 * 查询智能组卷习题
	 * 
	 * @param query
	 * @return
	 */
	Set<Long> findSmartPaperQids(SmartPaperDbQuery query);

	/**
	 * 查找习题信息
	 * 
	 * @param questionIds
	 * @return
	 */
	List<SmartPaperQue> findSmartPaperQues(@Param("questionIds") Collection<Long> questionIds);
}
