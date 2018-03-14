package cn.strong.leke.question.core.question.query;

import java.util.List;

import cn.strong.leke.model.user.User;

/**
 *
 * 描述: 收藏的习题
 *
 * @author raolei
 * @created 2016年8月11日 下午2:50:57
 * @since v1.0.0
 */
public interface IFavoriteQuestionQueryService {

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2017年6月29日 下午2:34:46
	 */
	boolean countFavorite(Long questionId, Long userId);

	/**
	 *
	 * 描述: 查询习题是否收藏过
	 *
	 * @author raolei
	 * @created 2016年8月11日 下午2:50:03
	 * @since v1.0.0
	 * @param questionIds
	 * @param userId
	 * @return
	 * @return List<Long>
	 */
	List<Long> filterFavoriteQuestions(List<Long> questionIds, User user);
}
