package cn.strong.leke.question.dao.mybatis.question;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.model.question.SchoolQuestionOutlineNode;

/**
 *
 * 描述: 学校习题和大纲章节关联表
 *
 * @author raolei
 * @created 2016年6月16日 下午6:08:25
 * @since v1.0.0
 */
public interface ISchoolQuestionOutlineNodeDao {

	int add(SchoolQuestionOutlineNode assoc);

	int delete(@Param("questionId") Long questionId, @Param("userId") Long userId);

	SchoolQuestionOutlineNode findOutlineNodeByQuestionId(Long questionId);

}
