/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 教师收藏习题
 * 
 * @author liulongbiao
 *
 */
public interface ITeacherQuestionDao {

	/**
	 * 添加我的收藏
	 * 
	 * @param questionId
	 * @param userId
	 * @return
	 */
	int addFavorite(@Param("questionId") Long questionId, @Param("userId") Long userId);

	/**
	 * 删除我的收藏
	 * 
	 * @param questionIds
	 * @param userId
	 * @return
	 */
	int deleteFavorites(@Param("questionIds") Long[] questionIds, @Param("userId") Long userId);

	/**
	 * 查看数据库中某习题的收藏数量(用于排重)
	 * 
	 * @param questionId
	 * @param userId
	 * @return
	 */
	int countFavorite(@Param("questionId") Long questionId, @Param("userId") Long userId);

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
	List<Long> filterFavoriteQuestions(@Param("questionIds") List<Long> questionIds, @Param("userId") Long userId);
}
