package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.workbook.WorkbookUserResGroup;

/**
 *
 * 习题册和用户资源分组关联
 *
 * @author raolei
 */
public interface IWorkbookUserResGroupDao {

	/**
	 * 批量添加习题册和用户资源分组关联
	 * 
	 * @param userResGroupId
	 * @param workbookIds
	 * @param modifiedBy
	 * @return
	 */
	int addBatchWorkbookUserResGroup(@Param("userResGroupId") Long userResGroupId,
			@Param("workbookIds") Set<Long> workbookIds, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 批量移动
	 * 
	 * @param oldUserResGroupId
	 * @param newUserResGroupId
	 * @param workbookIds
	 * @param modifiedBy
	 * @return
	 */
	int moveBatchWorkbookUserResGroup(@Param("oldUserResGroupId") Long oldUserResGroupId,
			@Param("newUserResGroupId") Long newUserResGroupId,
			@Param("workbookIds") Set<Long> workbookIds, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 批量删除
	 * 
	 * @param userResGroupId
	 * @param workbookIds
	 * @param modifiedBy
	 * @return
	 */
	int deleteBatchWorkbookUserResGroup(@Param("userResGroupId") Long userResGroupId,
			@Param("workbookIds") Set<Long> workbookIds, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 根据用户资源分组删除关联关系
	 * 
	 * @param userResGroupId
	 * @param modifiedBy
	 * @return
	 */
	int deleteWURGroupByUserResGroupId(@Param("userResGroupId") Long userResGroupId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 查询指定资源分组下习题册个数，用于排重
	 * 
	 * @param userResGroupId
	 * @param workbookId
	 * @return
	 */
	int queryCount(@Param("userResGroupId") Long userResGroupId,
			@Param("workbookId") Long workbookId);

	/**
	 * 根据分组查询资源
	 * 
	 * @param userResGroupId
	 * @param page
	 * @return
	 */
	List<WorkbookUserResGroup> queryWURGroupByUserResGroupId(
			@Param("userResGroupId") Long userResGroupId, Page page);

	/**
	 * 根据习题册查询用户资源分组
	 * 
	 * @param workbookId
	 * @param userId
	 * @return
	 */
	List<WorkbookUserResGroup> findByWorkbookAndUser(@Param("workbookId") Long workbookId,
			@Param("userId") Long userId);
}
