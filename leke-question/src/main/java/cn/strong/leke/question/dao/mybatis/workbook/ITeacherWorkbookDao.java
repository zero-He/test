/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 教师收藏习题册
 * 
 * @author liulongbiao
 *
 */
public interface ITeacherWorkbookDao {

	/**
	 * 添加我的收藏
	 * 
	 * @param workbookId
	 * @param userId
	 * @return
	 */
	int addFavorite(@Param("workbookId") Long workbookId, @Param("userId") Long userId);

	/**
	 * 删除我的收藏
	 * 
	 * @param workbookIds
	 * @param userId
	 * @return
	 */
	int deleteFavorites(@Param("workbookIds") Long[] workbookIds, @Param("userId") Long userId);

	/**
	 * 查看数据库中某习题册的收藏数量(用于排重)
	 * 
	 * @param workbookId
	 * @param userId
	 * @return
	 */
	int countFavorite(@Param("workbookId") Long workbookId, @Param("userId") Long userId);

	/**
	 *
	 * 描述: 查询习题册是否收藏过
	 *
	 * @author raolei
	 * @created 2016年8月11日 下午2:55:59
	 * @since v1.0.0
	 * @param workbookIds
	 * @param userId
	 * @return
	 * @return List<Long>
	 */
	List<Long> filterFavoriteWorkbooks(@Param("workbookIds") List<Long> workbookIds, @Param("userId") Long userId);
}
