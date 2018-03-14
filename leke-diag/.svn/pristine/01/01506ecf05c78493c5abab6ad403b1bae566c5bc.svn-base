package cn.strong.leke.diag.dao.teachingMonitor;

import java.util.List;

import cn.strong.leke.diag.model.teachingMonitor.HomePageDynamic;
import cn.strong.leke.diag.model.teachingMonitor.HomePageLessonSchedule;
import cn.strong.leke.diag.model.teachingMonitor.HomePageSchedule;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;

public interface HomePageDao {
	
	/**
	 * 查询老师动态-上课、备课
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherLessonAndBeike(RequestVo vo);
	
	/**
	 * 查询老师动态-布置作业
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherAssignHW(RequestVo vo);
	
	/**
	 * 查询老师动态-已批改作业
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherCorrectHW(RequestVo vo);
	
	/**
	 * 查询老师动态-待批改作业
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherAwaitingCorrectHW(RequestVo vo);
	
	/**
	 * 查询老师动态-答疑
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherDoubt(RequestVo vo);
	
	/**
	 * 查询学生动态-考勤
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findStudentAttendent(RequestVo vo);
	
	/**
	 * 查询学生动态-提问
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findStudentDoubt(RequestVo vo);
	
	/**
	 * 查询日课表
	 * @param vo
	 * @return
	 */
	public List<HomePageLessonSchedule> findHomePageSchedule(RequestVo vo);
	
}
