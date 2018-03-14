package cn.strong.leke.diag.dao.studentMonitor;

import java.util.List;

import cn.strong.leke.diag.model.studentMonitor.StudentAttend;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;

public interface StudentAttendAnalyseDao {
	
	/**
	 * 按课堂统计学生考勤
	 * @param vo
	 * @return
	 */
	List<StudentAttend> findStudentAttendByLesson(RequestVo vo, Page page);
	
	/**
	 * 按课堂统计学生考勤-合计
	 * @param vo
	 * @param page
	 * @return
	 */
	StudentAttend findStudentAttendAggregateByLesson(RequestVo vo);
	
	/**
	 * 按课堂查询学生考勤明细
	 * @param vo
	 * @return
	 */
	List<StudentAttend> findStudentAttendDtlByLesson(RequestVo vo, Page page);
	
	/**
	 * 按学生统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	List<StudentAttend> findStudentAttendByStudent(RequestVo vo, Page page);
	
	StudentAttend findStudentAttendAggregateByStudent(RequestVo vo);
	
	/**
	 * 按老师统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	List<StudentAttend> findStudentAttendByTeacher(RequestVo vo, Page page);
	
	StudentAttend findStudentAttendAggregateByTeacher(RequestVo vo);
	
	/**
	 * 按班级统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	List<StudentAttend> findStudentAttendByClass(RequestVo vo, Page page);
	
	StudentAttend findStudentAttendAggregateByClass(RequestVo vo);
	
	/**
	 * 按班级查询学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	List<StudentAttend> findStudentAttendDtlByClass(RequestVo vo, Page page);
	
	/**
	 * 按学科统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	List<StudentAttend> findStudentAttendBySubject(RequestVo vo, Page page);
	
	StudentAttend findStudentAttendAggregateBySubject(RequestVo vo);
	
	/**
	 * 按日统计学生考勤
	 * @param vo
	 * @param page
	 * @return
	 */
	List<StudentAttend> findStudentAttendByDay(RequestVo vo, Page page);
	
}
