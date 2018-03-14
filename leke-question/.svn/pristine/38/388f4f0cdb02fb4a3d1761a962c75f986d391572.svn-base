package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.question.QuestionUserResGroup;

/**
 *
 * 习题和用户资源分组关联
 *
 * @author raolei
 */
public interface IQuestionUserResGroupDao {

	/**
	 * 批量添加习题和用户资源分组关联
	 * 
	 * @param userResGroupId
	 * @param questionIds
	 * @param modifiedBy
	 * @return
	 */
	int addBatchQuestionUserResGroup(@Param("userResGroupId") Long userResGroupId,
			@Param("questionIds") Set<Long> questionIds, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 批量移动
	 * 
	 * @param oldUserResGroupId
	 * @param newUserResGroupId
	 * @param questionIds
	 * @param modifiedBy
	 * @return
	 */
	int moveBatchQuestionUserResGroup(@Param("oldUserResGroupId") Long oldUserResGroupId,
			@Param("newUserResGroupId") Long newUserResGroupId,
			@Param("questionIds") Set<Long> questionIds, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 批量删除
	 * 
	 * @param userResGroupId
	 * @param questionIds
	 * @param modifiedBy
	 * @return
	 */
	int deleteBatchQuestionUserResGroup(@Param("userResGroupId") Long userResGroupId,
			@Param("questionIds") Set<Long> questionIds, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 根据用户资源分组删除关联关系
	 * 
	 * @param userResGroupId
	 * @param modifiedBy
	 * @return
	 */
	int deleteQURGroupByUserResGroupId(@Param("userResGroupId") Long userResGroupId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 查询指定资源分组下习题个数，用于排重
	 * 
	 * @param userResGroupId
	 * @param questionId
	 * @return
	 */
	int queryCount(@Param("userResGroupId") Long userResGroupId,
			@Param("questionId") Long questionId);

	/**
	 * 根据分组查询资源
	 * 
	 * @param userResGroupId
	 * @param page
	 * @return
	 */
	List<QuestionUserResGroup> queryQURGroupByUserResGroupId(
			@Param("userResGroupId") Long userResGroupId, Page page);

	/**
	 * 根据习题查询用户资源分组
	 * 
	 * @param questionId
	 * @param userId
	 * @return
	 */
	List<QuestionUserResGroup> findByQuestionAndUser(@Param("questionId") Long questionId,
			@Param("userId") Long userId);
}
