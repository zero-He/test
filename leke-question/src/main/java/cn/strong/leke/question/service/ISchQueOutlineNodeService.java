package cn.strong.leke.question.service;

import cn.strong.leke.model.question.SchoolQuestionOutlineNode;

/**
 *
 * 描述: 学校习题和大纲章节关联表
 *
 * @author raolei
 * @created 2016年6月16日 下午6:14:45
 * @since v1.0.0
 */
public interface ISchQueOutlineNodeService {

	/**
	 *
	 * 描述: 添加习题关联大纲
	 *
	 * @author raolei
	 * @created 2016年6月16日 下午6:15:14
	 * @since v1.0.0
	 * @param assoc
	 * @return void
	 */
	void add(SchoolQuestionOutlineNode assoc);

	/**
	 *
	 * 描述: 删除习题关联的大纲
	 *
	 * @author raolei
	 * @created 2016年6月16日 下午6:15:56
	 * @since v1.0.0
	 * @param questionId
	 * @param userId
	 * @return void
	 */
	void delete(Long questionId, Long userId);

	SchoolQuestionOutlineNode findOutlineNodeByQuestionId(Long questionId);

}
