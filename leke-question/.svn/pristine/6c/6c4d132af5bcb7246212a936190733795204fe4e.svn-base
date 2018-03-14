/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.QuestionSection;

/**
 * 习题章节关联DAO
 * 
 * @author liulongbiao
 *
 */
public interface IQuestionSectionDao {

	/**
	 * 添加关联
	 * 
	 * @param assoc
	 * @return
	 */
	int insertQuestionSection(QuestionSection assoc);

	/**
	 * 根据习题ID删除关联关系
	 * 
	 * @param questionId
	 * @param modifiedBy
	 * @return
	 */
	int deleteQuestionSections(@Param("questionId") Long questionId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 *
	 * 描述: 根据主键删除
	 *
	 * @author raolei
	 * @created 2016年8月3日 下午4:16:11
	 * @since v1.0.0
	 * @param quesSectionId
	 * @param modifiedBy
	 * @return
	 * @return int
	 */
	int deleteQuestionSection(@Param("quesSectionId") Long quesSectionId, @Param("modifiedBy") Long modifiedBy);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2016年8月3日 下午4:16:19
	 * @since v1.0.0
	 * @param quesSectionId
	 * @return
	 * @return QuestionSection
	 */
	QuestionSection getQuestionSection(@Param("quesSectionId") Long quesSectionId);

	/**
	 * 根据习题ID查询关联关系
	 * 
	 * @param questionId
	 * @return
	 */
	List<QuestionSection> findByQuestionId(Long questionId);

	/**
	 * 根据习题ID查询关联关系
	 * 
	 * @param questionIds
	 * @return
	 */
	List<QuestionSection> findByQuestionIds(@Param("questionIds") List<Long> questionIds);
}
