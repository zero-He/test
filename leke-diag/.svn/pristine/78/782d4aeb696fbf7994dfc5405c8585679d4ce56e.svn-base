package cn.strong.leke.diag.dao.teachingMonitor;

import java.util.List;

import cn.strong.leke.diag.model.teachingMonitor.LessonAttendInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;

public interface LessonAttendInfoDao {
	
	/**
	 * 查询上课率统计信息
	 * @param vo
	 * @return
	 */
	public LessonAttendInfo findAttendLessonStat(RequestVo vo);
	
	/**
	 * 按日查询上课率走势
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByDay(RequestVo vo);
	
	/**
	 * 按周查询上课率走势
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByWeek(RequestVo vo);
	
	/**
	 * 按月查询上课率走势
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByMonth(RequestVo vo);
	
	/**
	 * 上课率对比(全校按年级)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByGrade(RequestVo vo);
	
	/**
	 * 上课率对比(全校按学科)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByAllSubject(RequestVo vo);
	
	/**
	 * 上课率对比(年级按班级)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByClazz(RequestVo vo);
	
	/**
	 * 上课率对比(年级按学科)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByGradeSubject(RequestVo vo);
	
	/**
	 * 查询上课率排名
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateRank(RequestVo vo);
	
	/**
	 * 查询老师上课明细数据
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateDtl(RequestVo vo);
	
	public List<LessonAttendInfo> findAttendLessonRateDtl(RequestVo vo, Page page);
}
