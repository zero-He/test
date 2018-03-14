/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.workbook.FamousTeacherWorkbookCheckLog;

/**
 * 名师习题册审核日志DAO
 * 
 * @author liulongbiao
 *
 */
public interface IFamousTeacherWorkbookCheckLogDao {

	/**
	 * 添加审核日志
	 * 
	 * @param log
	 * @return
	 */
	int add(FamousTeacherWorkbookCheckLog log);

	/**
	 * 根据习题册和学校查询审核日志，并按时间倒序排列
	 * 
	 * @param workbookId
	 * @param teacherId
	 * @return
	 */
	List<FamousTeacherWorkbookCheckLog> findByWorkbookAndTeacher(
			@Param("workbookId") Long workbookId, @Param("teacherId") Long teacherId);
}
