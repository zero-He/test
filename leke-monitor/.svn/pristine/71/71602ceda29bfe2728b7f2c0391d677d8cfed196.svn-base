package cn.strong.leke.monitor.core.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.AttendanceQuery;
import cn.strong.leke.monitor.core.model.AttendanceStudentDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDtlDTO;
/**
 * 考勤统计接口
 * @Description 
 * @author Deo
 * @createdate 2017年3月23日 下午1:53:45
 */
public interface IAttendanceService
{

	/**
	 * 加载统计列表
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceStudentDTO> getList(AttendanceQuery query, Page page);
	
	/**
	 * 加载班级考勤统计列表(学生)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceStudentDTO> getStudentAttendanceClassList(AttendanceQuery query, Page page);
	
	
	/**
	 * 加载班级考勤统计列表(班主任)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceTeacherDTO> getTeacherAttendanceClassList(AttendanceQuery query, Page page);
	
	
	/**
	 * 课堂考勤(学生)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceStudentDTO> getStudentAttendanceLessonList(AttendanceQuery query, Page page);
	
	/**
	 * 课堂考勤(班主任)
	 * @param query
	 * @param page
	 * @return
	 */
	List<AttendanceTeacherDtlDTO> getTeacherAttendanceLessonList(AttendanceQuery query, Page page);
	
	
}
