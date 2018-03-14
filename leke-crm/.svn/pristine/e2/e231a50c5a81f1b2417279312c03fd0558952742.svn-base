package cn.strong.leke.scs.dao.slave;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.Tutor;
import cn.strong.leke.scs.model.query.StuAttendQuery;
import cn.strong.leke.scs.model.query.TeachAttendQuery;
import cn.strong.leke.scs.model.view.StudentAttend;
import cn.strong.leke.scs.model.view.TeachAttend;

@Tutor
public interface AttendanceDao {

	/**
	 * 根据单课考勤ID获取考勤信息。
	 * @param csAttendId 单课考勤ID
	 * @return
	 */
	public TeachAttend getTeachAttendByAttendId(Long csAttendId);

	/**
	 * 根据条件查询老师单课考勤列表。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<TeachAttend> findTeachAttendListByQuery(TeachAttendQuery query, Page page);

	/**
	 * 根据条件查询学生单课考勤列表。
	 * @param query 条件
	 * @param page 分页
	 * @return
	 */
	public List<StudentAttend> findStuAttendListByQuery(StuAttendQuery query, Page page);
}
