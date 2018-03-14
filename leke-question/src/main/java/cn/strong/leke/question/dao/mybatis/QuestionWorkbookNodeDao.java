/**
 * 
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.QuestionWorkbookNode;

/**
 * 习题习题册节点关联 DAO
 * 
 * @author liulongbiao
 *
 */
public interface QuestionWorkbookNodeDao {
	/**
	 * 插入习题习题册节点关联
	 * 
	 * @param assoc
	 * @return
	 */
	int insertQuestionWorkbookNode(QuestionWorkbookNode assoc);

	/**
	 * 批量插入习题习题册节点关联
	 * 
	 * @param questionWbNodes
	 * @return
	 */
	int insertQuestionWorkbookNodes(
			@Param("questionWbNodes") List<QuestionWorkbookNode> questionWbNodes);

	/**
	 * 根据习题ID删除习题册节点关联
	 * 
	 * @param questionId
	 * @return
	 */
	int deleteQuestionWorkbookNodeByQuestionId(Long questionId);

	/**
	 * 批量根据习题ID和习题册ID删除习题册节点关联
	 * 
	 * @param questionIds
	 * @param workbookNodeId
	 * @return
	 */
	int deleteBatchQuestionWbNode(@Param("questionIds") List<Long> questionIds,
			@Param("workbookNodeId") Long workbookNodeId);

	/**
	 * 根据习题ID获取习题册节点关联
	 * 
	 * @param questionId
	 * @return
	 */
	List<QuestionWorkbookNode> findQuestionWorkbookNodesByQuestionId(Long questionId);

	/**
	 * 删除关联关系
	 * 
	 * @param questionId
	 * @param workbookNodeId
	 * @param modifiedBy
	 * @return
	 */
	int deleteQuestionWorkbookNode(@Param("questionId") Long questionId,
			@Param("workbookNodeId") Long workbookNodeId, @Param("modifiedBy") Long modifiedBy);
}
