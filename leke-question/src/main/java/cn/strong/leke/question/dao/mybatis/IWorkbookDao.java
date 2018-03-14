package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.querys.BaseWorkbookQuery;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.question.model.InputStatisDTO;
/**
 *
 * 描述:习题册
 *
 * @author raolei
 * @created 2015年11月19日 下午3:37:57
 * @since v1.0.0
 */
public interface IWorkbookDao {
	/**
	 *
	 * 描述: 添加
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午3:39:10
	 * @since v1.0.0
	 * @param wb
	 * @return
	 * @return int
	 */
	int addWorkbook(Workbook wb);
	
	/**
	 *
	 * 描述:删除单条记录
	 * 
	 * @param workbookId
	 * @param userId
	 *
	 */
	int deleteWorkbook(@Param("workbookId") Long workbookId, @Param("userId") Long userId);

	/**
	 *
	 * 描述:批量删除记录
	 * 
	 * @param workbookIds
	 * @param userId
	 *
	 */
	int deleteBatchWorkbook(@Param("workbookIds") List<Long> workbookIds,
			@Param("userId") Long userId);

	/**
	 * 禁用习题册
	 * 
	 * @param workbookId
	 * @param userId
	 * @return
	 */
	int disableWorkbook(@Param("workbookId") Long workbookId, @Param("userId") Long userId);

	/**
	 *
	 * 描述:
	 *
	 * @author raolei
	 * @created 2015年11月25日 上午10:54:05
	 * @since v1.0.0
	 * @param wb
	 * @return
	 * @return int
	 */
	int workbookUp(Workbook wb);

	/**
	 *
	 * 描述:下架
	 *
	 * @author raolei
	 * @created 2015年11月25日 上午10:54:23
	 * @since v1.0.0
	 * @param wb
	 * @return
	 * @return int
	 */
	int workbookDown(Workbook wb);

	/**
	 *
	 * 描述:修改单条记录
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午3:46:07
	 * @since v1.0.0
	 * @param wb
	 * @return
	 * @return int
	 */
	int updateWorkbook(Workbook wb);

	/**
	 *
	 * 描述: 修改习题册名称
	 *
	 * @author raolei
	 * @created 2016年10月26日 下午3:35:00
	 * @since v1.0.0
	 * @param workbookName
	 * @param userId
	 * @return
	 * @return int
	 */
	int updateWorkbookName(Workbook wb);


	/**
	 *
	 * 描述:查询单条记录
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午3:48:03
	 * @since v1.0.0
	 * @param workbookId
	 * @return
	 * @return Workbook
	 */
	public Workbook getWorkbook(Long workbookId);

	/**
	 *
	 * 描述:分页查询
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午7:51:42
	 * @since v1.0.0
	 * @param query
	 * @param page
	 * @return
	 * @return List<Workbook>
	 */
	List<Workbook> queryWorkbooks(BaseWorkbookQuery query, Page page);


	/**
	 * 根据ID列表获取习题册信息
	 * 
	 * @param workbookIds
	 * @return
	 */
	List<Workbook> findWorkbookByIds(@Param("workbookIds") Long[] workbookIds);

	/**
	 * 增加使用次数
	 * 
	 * @author liulongbiao
	 * @param workbookId
	 * @return
	 */
	int incUsedCount(Long workbookId);

	/**
	 *
	 * 描述: 增加收藏次数
	 *
	 * @author raolei
	 * @created 2016年6月13日 下午6:00:05
	 * @since v1.0.0
	 * @param workbookId
	 * @return
	 * @return int
	 */
	int incFavCount(Long workbookId);


	/**
	 *
	 * 描述: 批量增加收藏次数
	 *
	 * @author raolei
	 * @created 2016年6月13日 下午6:04:08
	 * @since v1.0.0
	 * @param workbookIds
	 * @return
	 * @return int
	 */
	int incBatchFavCount(@Param("workbookIds") List<Long> workbookIds);

	/**
	 * 增加点赞次数
	 * 
	 * @author liulongbiao
	 * @param workbookId
	 * @return
	 */
	int incPraiseCount(Long workbookId);

	/**
	 *
	 * 描述:乐课资源库查询
	 *
	 * @author raolei
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLekeWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询个人习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryPersonalWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询收藏的习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryFavoriteWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询学校习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> querySchoolWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询联盟习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLeagueWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查找乐课分享习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLekeShareWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查找乐课精品习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLekeBoutiqueWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查找名师习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryFamousTeacherWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查找名校习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryFamousSchoolWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 *
	 * 描述: 查询全库
	 *
	 * @author raolei
	 * @created 2017年8月23日 上午11:45:55
	 */
	List<Long> queryPersonalAll(RepositoryWorkbookQuery query, Page page);

	/**
	 * 添加习题册到个人库
	 * 
	 * @param workbookId
	 * @param userId
	 * @param modifiedBy
	 * @return
	 */
	int addToPersonal(@Param("workbookId") Long workbookId, @Param("userId") Long userId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题册从个人库中移除
	 * 
	 * @param workbookId
	 * @param userId
	 * @param modifiedBy
	 * @return
	 */
	int removeFromPersonal(@Param("workbookId") Long workbookId, @Param("userId") Long userId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 添加习题册到乐课精品库
	 * 
	 * @param workbookId
	 * @param modifiedBy
	 */
	int addToLekeBoutique(@Param("workbookId") Long workbookId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题册从乐课精品库中移除
	 * 
	 * @param workbookId
	 * @param modifiedBy
	 */
	int removeFromLekeBoutique(@Param("workbookId") Long workbookId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 添加习题册到乐课分享库
	 * 
	 * @param workbookId
	 * @param modifiedBy
	 */
	int addToLekeShare(@Param("workbookId") Long workbookId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题册从乐课分享库中移除
	 * 
	 * @param workbookId
	 * @param modifiedBy
	 */
	int removeFromLekeShare(@Param("workbookId") Long workbookId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 *
	 * 描述:
	 *
	 * @param wb
	 * @return
	 * @return int
	 */
	void workbookPhotoUpload(Workbook wb);

	/**
	 *
	 * 描述: 查询乐课教研员录入量统计
	 *
	 */
	List<InputStatisDTO> queryWorkbookAmount(InputStatisDTO query);
	
	/**
	 *
	 * 描述: 查询整个乐课录入量统计
	 *
	 */
	Long queryWorkbookTotalAmount();

	int setWorkbooLeve(@Param("workbookId") Long workbookId, @Param("level") Integer level, @Param("userId") Long userId);

	int countLekeWorkbook(RepositoryWorkbookQuery query);

	List<RepoDayGroup> groupCreatedOnLekeWorkbook(RepositoryWorkbookQuery query);
}
