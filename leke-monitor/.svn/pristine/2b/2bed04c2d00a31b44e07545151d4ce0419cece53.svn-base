package cn.strong.leke.monitor.core.dao.mybatis;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.AttendanceQuery;
import cn.strong.leke.monitor.core.model.AttendanceStudentDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDtlDTO;

/**
 * 考勤统计数据
 * 
 * @Description
 * @author Deo
 * @createdate 2017年3月23日 下午1:56:05
 */
public interface IAttendanceDao
{
	/**
	 * 加载考勤数据
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceStudentDTO> selectAttendanceList(AttendanceQuery query, Page page);
	
	/**
	 *  加载班级考勤数据(学生)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceStudentDTO> selectStudentAttendanceList(AttendanceQuery query, Page page);
	
	/**
	 * 加载班级考勤数据(班主任)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceTeacherDTO> selectTeacherAttendanceList(AttendanceQuery query, Page page);
	
	/**
	 * 课堂考勤(学生)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceStudentDTO> selectStudentAttendanceLessonList(AttendanceQuery query, Page page);
	
	/**
	 * 课堂考勤(班主任)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceTeacherDtlDTO> selectTeacherAttendanceLessonList(AttendanceQuery query, Page page);
	
}
