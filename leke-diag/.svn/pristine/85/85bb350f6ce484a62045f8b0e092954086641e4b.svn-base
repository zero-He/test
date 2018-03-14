package cn.strong.leke.diag.dao.teachingMonitor;

import java.util.List;

import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.diag.model.teachingMonitor.CorrectHW;

public interface CorrectHWDao {
	/**
	 * 查询作业批改统计课堂、老师数
	 * @param vo
	 * @return
	 */
	public CorrectHW findCorrectHWLessonTeacherNum(RequestVo vo);
	
	/**
	 * 查询布置作业数，及主客观数
	 * @param vo
	 * @return
	 */
	public CorrectHW findCorrectHWAssignNum(RequestVo vo);
	
	/**
	 * 通用查询作业批改情况，包括应批改作业份数、自动批改、全部批改、部分批改、未批改作业份数及占比
	 * @param vo
	 * @return
	 */
	public CorrectHW findCorrectHWComm(RequestVo vo);
	
	/**
	 * 按日查询批改率走势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByDay(RequestVo vo);
	
	/**
	 * 按周查询批改率走势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByWeek(RequestVo vo);
	
	/**
	 * 按月查询批改率走势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByMonth(RequestVo vo);
	
	/**
	 * 批改率对比(全校按年级)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByGrade(RequestVo vo);
	
	/**
	 * 批改率对比(全校按学科)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByAllSubject(RequestVo vo);
	
	/**
	 * 批改率对比(年级按班级)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByClazz(RequestVo vo);
	
	/**
	 * 批改率对比(年级按学科)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByGradeSubject(RequestVo vo);
	
	/**
	 * 查询批改率排名
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByRank(RequestVo vo);
	
	/**
	 * 查询批改率明细
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWDtl(RequestVo vo, Page page);
	
	public List<CorrectHW> findCorrectHWDtl(RequestVo vo);
}
