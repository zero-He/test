/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.workbook.FamousSchoolWorkbook;
import cn.strong.leke.question.model.workbook.query.AgencyFmsSchWorkbookQuery;

/**
 * 名校习题册DAO
 * 
 * @author liulongbiao
 *
 */
public interface IFamousSchoolWorkbookDao {
	/**
	 * 添加名校习题册
	 * 
	 * @param assoc
	 * @return
	 */
	int add(FamousSchoolWorkbook assoc);

	/**
	 * 移除名校习题册
	 * 
	 * @param assoc
	 * @return
	 */
	int remove(FamousSchoolWorkbook assoc);

	/**
	 * 根据习题册和学校获取记录
	 * 
	 * @param workbookId
	 * @param schoolId
	 * @return
	 */
	FamousSchoolWorkbook getByWorkbookAndSchool(@Param("workbookId") Long workbookId,
			@Param("schoolId") Long schoolId);

	/**
	 * 更新状态
	 * 
	 * @param assoc
	 * @return
	 */
	int updateStatus(FamousSchoolWorkbook assoc);

	/**
	 * 根据习题册ID查找关联关系
	 * 
	 * @param workbookIds
	 * @param schoolId
	 * @return
	 */
	List<FamousSchoolWorkbook> findByWorkbookIds(@Param("workbookIds") List<Long> workbookIds,
			@Param("schoolId") Long schoolId);

	/**
	 * 查询我的代录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryMyAgencyFmsSchWorkbooks(AgencyFmsSchWorkbookQuery query, Page page);
}
