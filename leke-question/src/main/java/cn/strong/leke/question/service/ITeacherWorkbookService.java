package cn.strong.leke.question.service;

import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.workbook.TeacherWorkbook;

/**
 *
 * 描述:老师收藏的习题册
 *
 * @author raolei
 * @created 2015年11月28日 下午4:52:32
 * @since v1.0.0
 */
public interface ITeacherWorkbookService {

	/**
	 *
	 * 描述:收藏
	 *
	 * @author raolei
	 * @created 2015年12月1日 下午1:46:40
	 * @since v1.0.0
	 * @param teacherWorkbook
	 * @return void
	 */
	Award addTeacherWorkbook(TeacherWorkbook fav, User user);
	
	Award addBatchTeacherWorkbook(Long[] workbookIds, User user);

	/**
	 *
	 * 描述:取消收藏
	 *
	 * @author raolei
	 * @created 2015年11月30日 下午3:25:38
	 * @since v1.0.0
	 * @param teacherWorkbook
	 * @return
	 * @return void
	 */
	void deleteTeacherWorkbook(TeacherWorkbook teacherWorkbook);

	void deleteBatchTeacherWorkbook(Long[] workbookIds, Long teacherId);
}
