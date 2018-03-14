package cn.strong.leke.diag.service.studentMonitor;

import java.util.List;

import javax.annotation.Resource;

import cn.strong.leke.diag.dao.studentMonitor.StudentAttendAnalyseDao;
import cn.strong.leke.diag.model.studentMonitor.StudentAttend;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;
import org.springframework.stereotype.Service;

@Service
public class StudentAttendAnalyseService {
	
	@Resource
	private StudentAttendAnalyseDao studentAttendAnalyseDao;
	
	/**
	 * 按课堂统计学生考勤
	 * @param vo
	 * @return
	 */
	public List<StudentAttend> findStudentAttendByLesson(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendByLesson(vo, page);
	}
	
	public StudentAttend findStudentAttendAggregateByLesson(RequestVo vo){
		return studentAttendAnalyseDao.findStudentAttendAggregateByLesson(vo);
	}
	
	/**
	 * 按课堂查询学生考勤明细
	 * @param vo
	 * @return
	 */
	public List<StudentAttend> findStudentAttendDtlByLesson(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendDtlByLesson(vo, page);
	}
	
	/**
	 * 按学生统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StudentAttend> findStudentAttendByStudent(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendByStudent(vo, page);
	}
	
	public StudentAttend findStudentAttendAggregateByStudent(RequestVo vo){
		return studentAttendAnalyseDao.findStudentAttendAggregateByStudent(vo);
	}
	
	/**
	 * 按老师统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StudentAttend> findStudentAttendByTeacher(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendByTeacher(vo, page);
	}
	
	public StudentAttend findStudentAttendAggregateByTeacher(RequestVo vo){
		return studentAttendAnalyseDao.findStudentAttendAggregateByTeacher(vo);
	}
	
	/**
	 * 按班级统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StudentAttend> findStudentAttendByClass(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendByClass(vo, page);
	}
	
	public StudentAttend findStudentAttendAggregateByClass(RequestVo vo){
		return studentAttendAnalyseDao.findStudentAttendAggregateByClass(vo);
	}
	
	/**
	 * 按班级查询学生考勤明细
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StudentAttend> findStudentAttendDtlByClass(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendDtlByClass(vo, page);
	}
	
	/**
	 * 按学科统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StudentAttend> findStudentAttendBySubject(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendBySubject(vo, page);
	}
	
	public StudentAttend findStudentAttendAggregateBySubject(RequestVo vo){
		return studentAttendAnalyseDao.findStudentAttendAggregateBySubject(vo);
	}
	
	/**
	 * 按日统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StudentAttend> findStudentAttendByDay(RequestVo vo, Page page){
		return studentAttendAnalyseDao.findStudentAttendByDay(vo, page);
	}

}
