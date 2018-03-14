/**
 * 
 */
package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.workbook.FamousSchoolWorkbookCheckLog;

/**
 * @author liulongbiao
 *
 */
public interface IFamousSchoolWorkbookCheckLogDao {

	/**
	 * 添加审核日志
	 * 
	 * @param log
	 * @return
	 */
	int add(FamousSchoolWorkbookCheckLog log);

	/**
	 * 根据习题册和学校查询审核日志，并按时间倒序排列
	 * 
	 * @param workbookId
	 * @param schoolId
	 * @return
	 */
	List<FamousSchoolWorkbookCheckLog> findByWorkbookAndSchool(
			@Param("workbookId") Long workbookId, @Param("schoolId") Long schoolId);
}
