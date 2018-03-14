package cn.strong.leke.monitor.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.dao.mybatis.IAttendanceDao;
import cn.strong.leke.monitor.core.model.AttendanceQuery;
import cn.strong.leke.monitor.core.model.AttendanceStudentDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDTO;
import cn.strong.leke.monitor.core.model.AttendanceTeacherDtlDTO;
import cn.strong.leke.monitor.core.service.IAttendanceService;

/**
 * 考勤统计业务实现
 * 
 * @Description
 * @author Deo
 * @createdate 2017年3月23日 下午1:54:48
 */
@Service
public class AttendanceServiceImpl implements IAttendanceService
{
	@Resource
	private IAttendanceDao attendanceDao;

	@Override
	public List<AttendanceStudentDTO> getList(AttendanceQuery query, Page page)
	{
		return attendanceDao.selectAttendanceList(query, page);
	}

	@Override
	public List<AttendanceStudentDTO> getStudentAttendanceClassList(AttendanceQuery query, Page page)
	{
		return attendanceDao.selectStudentAttendanceList(query, page);
	}

	@Override
	public List<AttendanceTeacherDTO> getTeacherAttendanceClassList(AttendanceQuery query, Page page)
	{
		return attendanceDao.selectTeacherAttendanceList(query, page);
	}

	@Override
	public List<AttendanceStudentDTO> getStudentAttendanceLessonList(AttendanceQuery query, Page page)
	{
		return attendanceDao.selectStudentAttendanceLessonList(query, page);
	}

	@Override
	public List<AttendanceTeacherDtlDTO> getTeacherAttendanceLessonList(AttendanceQuery query, Page page)
	{
		return attendanceDao.selectTeacherAttendanceLessonList(query, page);
	}

}
