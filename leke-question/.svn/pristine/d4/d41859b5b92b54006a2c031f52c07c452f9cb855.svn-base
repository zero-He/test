package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.workbook.SchoolWorkbookCheckLog;


/**
 * 学校习题册审核日志DAO
 * 
 * @author liulongbiao
 *
 */
public interface ISchoolWorkbookCheckLogDao {

	/**
	 * 添加审核日志
	 * 
	 * @param log
	 * @return
	 */
	int add(SchoolWorkbookCheckLog log);

	/**
	 *
	 * 描述: 查找一条审核记录
	 *
	 * @author raolei
	 */
	SchoolWorkbookCheckLog getCheckLog(Long checkLogId);

	/**
	 * 根据习题册和学校查询审核日志，并按时间倒序排列
	 * 
	 * @param workbookId
	 * @param schoolId
	 * @return
	 */
	List<SchoolWorkbookCheckLog> findByWorkbookAndSchool(@Param("workbookId") Long workbookId,
			@Param("schoolId") Long schoolId);
}
