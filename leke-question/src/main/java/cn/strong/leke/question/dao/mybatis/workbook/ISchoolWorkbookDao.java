package cn.strong.leke.question.dao.mybatis.workbook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.workbook.SchoolWorkbook;

/**
 * 学校习题册DAO
 * 
 * @author liulongbiao
 *
 */
public interface ISchoolWorkbookDao {

	/**
	 * 添加学校习题册
	 * 
	 * @param assoc
	 * @return
	 */
	int add(SchoolWorkbook assoc);

	/**
	 *
	 * 描述: 批量添加分享到学校习题册记录
	 *
	 * @author raolei
	 */
	int insertBatchSchoolWorkbook(@Param("assocs") List<SchoolWorkbook> schoolWorkbooks);

	/**
	 * 移除学校习题册
	 * 
	 * @param assoc
	 * @return
	 */
	int remove(SchoolWorkbook assoc);

	/**
	 * 根据习题册和学校获取记录
	 * 
	 * @param workbookId
	 * @param schoolId
	 * @return
	 */
	SchoolWorkbook getByWorkbookAndSchool(@Param("workbookId") Long workbookId,
			@Param("schoolId") Long schoolId);

	/**
	 * 更新状态
	 * 
	 * @param assoc
	 * @return
	 */
	int updateStatus(SchoolWorkbook assoc);

	/**
	 *
	 * 描述: 根据习题册ID和学校Id获取学校备课分享的记录
	 *
	 * @author raolei
	 * @param workbookId
	 * @param schoolId
	 * @return
	 */
	int countSchoolWorkbook(@Param("workbookId") Long workbookId, @Param("schoolId") Long schoolId);

	/**
	 * 根据习题册ID列表查找关联关系
	 * 
	 * @param workbookIds
	 * @param schoolId
	 * @return
	 */
	List<SchoolWorkbook> findByWorkbookIds(@Param("workbookIds") List<Long> workbookIds,
			@Param("schoolId") Long schoolId);

}
