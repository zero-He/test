package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.querys.BaseWorkbookQuery;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.User;

/**
 *
 * 描述:习题册服务接口
 *
 * @author raolei
 * @created 2015年11月19日 下午4:32:16
 * @since v1.0.0
 */
public interface IWorkbookService {
	/**
	 * 添加习题册
	 * 
	 * @author liulongbiao
	 * @param wb
	 * @param copySections
	 * @param user
	 * @deprecated
	 * @see cn.strong.leke.question.core.workbook.cmd.WorkbookSaveHandler#save(cn.strong.leke.repository.common.model.RepoSaveContext)
	 */
	@Deprecated
	void addWorkbook(Workbook wb, boolean copySections, User user);

	/**
	 * 删除习题册
	 * 
	 * @author liulongbiao
	 * @param workbookId
	 * @param user
	 */
	void deleteWorkbook(Long workbookId, User user);
	
	/**
	 * 禁用习题册
	 * 
	 * @param workbookId
	 * @param user
	 */
	void updateDisableWorkbook(Long workbookId, User user);

	/**
	 *
	 * 描述:上架
	 *
	 * @author raolei
	 * @created 2015年11月25日 上午10:55:28
	 * @since v1.0.0
	 * @param wb
	 * @return void
	 */
	void saveWorkbookUp(Long workbookId, User user);

	/**
	 *
	 * 描述:下架
	 *
	 * @author raolei
	 * @created 2015年11月25日 上午10:55:31
	 * @since v1.0.0
	 * @param wb
	 * @return void
	 */
	void workbookDown(Long workbookId, User user);

	/**
	 *
	 * 描述:修改单条记录
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午3:46:07
	 * @param wb
	 * @return void
	 */
	void updateWorkbook(Workbook wb, User user);

	/**
	 *
	 * 描述:查询单条记录
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午3:48:03
	 * @param workbookId
	 * @return Workbook
	 */
	Workbook getWorkbook(Long workbookId);

	/**
	 *
	 * 描述: 增加收藏次数
	 *
	 * @author raolei
	 * @created 2016年6月13日 下午6:01:29
	 * @since v1.0.0
	 * @param workbookId
	 * @return void
	 */
	void updateIncFavCount(Long workbookId);

	/**
	 *
	 * 描述: 批量增加收藏次数
	 *
	 * @author raolei
	 * @created 2016年6月13日 下午6:06:05
	 * @since v1.0.0
	 * @param workbookIds
	 * @return void
	 */
	void updateBatchIncFavCount(List<Long> workbookIds);

	/**
	 *
	 * 描述:分页查询
	 *
	 * @author raolei
	 * @created 2015年11月19日 下午7:51:42
	 * @param query
	 * @param page
	 * @return List<Workbook>
	 */
	List<Workbook> queryWorkbooks(BaseWorkbookQuery query, Page page);

	/**
	 * 根据ID数组获取习题册信息
	 * 
	 * @param workbookIds
	 * @return
	 */
	List<Workbook> findWorkbookByIds(Long[] workbookIds);

	/**
	 * 查询个人习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryPersonalWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询个人收藏习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryFavoriteWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询学校习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> querySchoolWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查询联盟习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryLeagueWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查找乐课分享习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryLekeShareWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 查找乐课精品习题册
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryLekeBoutiqueWorkbooks(RepositoryWorkbookQuery query, Page page);

	/**
	 * 乐课精品习题册封面上传
	 * 
	 * @param workbookId
	 * @param user
	 * @param photoUrl
	 * @return
	 */
	void workbookPhotoUpload(Long workbookId, User user, String photoUrl);

}
