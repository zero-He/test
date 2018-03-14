package cn.strong.leke.scs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.scs.dao.slave.AttendanceDao;
import cn.strong.leke.scs.model.query.StuAttendQuery;
import cn.strong.leke.scs.model.query.TeachAttendQuery;
import cn.strong.leke.scs.model.view.StudentAttend;
import cn.strong.leke.scs.model.view.TeachAttend;
import cn.strong.leke.scs.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Resource
	private AttendanceDao attendanceDao;

	@Override
	public TeachAttend getTeachAttendByAttendId(Long csAttendId) {
		return attendanceDao.getTeachAttendByAttendId(csAttendId);
	}

	@Override
	public List<TeachAttend> findTeachAttendListByQuery(TeachAttendQuery query, Page page) {
		return this.attendanceDao.findTeachAttendListByQuery(query, page);
	}

	@Override
	public List<StudentAttend> findStuAttendListByQuery(StuAttendQuery query, Page page) {
		return this.attendanceDao.findStuAttendListByQuery(query, page);
	}
}
