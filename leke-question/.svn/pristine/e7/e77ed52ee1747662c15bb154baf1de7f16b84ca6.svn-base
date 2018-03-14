/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.workbook.FamousTeacherWorkbook;
import cn.strong.leke.question.model.workbook.query.AgencyFmsTchWorkbookQuery;

/**
 * 名师习题册DAO
 * 
 * @author liulongbiao
 *
 */
public interface IFamousTeacherWorkbookDao {
	/**
	 * 添加名师习题册
	 * 
	 * @param assoc
	 * @return
	 */
	int add(FamousTeacherWorkbook assoc);

	/**
	 * 移除名师习题册
	 * 
	 * @param assoc
	 * @return
	 */
	int remove(FamousTeacherWorkbook assoc);

	/**
	 * 根据习题册和老师获取记录
	 * 
	 * @param workbookId
	 * @param teacherId
	 * @return
	 */
	FamousTeacherWorkbook getByWorkbookAndTeacher(@Param("workbookId") Long workbookId,
			@Param("teacherId") Long teacherId);

	/**
	 * 更新状态
	 * 
	 * @param assoc
	 * @return
	 */
	int updateStatus(FamousTeacherWorkbook assoc);

	/**
	 * 根据习题册ID查找关联关系
	 * 
	 * @param workbookIds
	 * @param teacherId
	 * @return
	 */
	List<FamousTeacherWorkbook> findByWorkbookIds(@Param("workbookIds") List<Long> workbookIds,
			@Param("teacherId") Long teacherId);

	/**
	 * 查询我的代录
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryMyAgencyFmsTchWorkbooks(AgencyFmsTchWorkbookQuery query, Page page);
}
