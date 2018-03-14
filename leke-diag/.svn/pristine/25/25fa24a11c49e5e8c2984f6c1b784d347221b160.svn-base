package cn.strong.leke.diag.dao.teachingMonitor;

import java.util.List;

import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ResolveDoubt;
import cn.strong.leke.framework.page.jdbc.Page;

public interface ResolveDoubtDao {
	/**
	 * 查询解答统计信息
	 * @param vo
	 * @return
	 */
	public ResolveDoubt findResolveDoubtStat(RequestVo vo);
	
	/**
	 * 按日查询解答数走势
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByDay(RequestVo vo);
	
	/**
	 * 按周查询解答数走势
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByWeek(RequestVo vo);
	
	/**
	 * 按月查询解答数走势
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByMonth(RequestVo vo);
	
	/**
	 * 解答数对比(全校按年级)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByGrade(RequestVo vo);
	
	/**
	 * 解答数对比(全校按学科)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByAllSubject(RequestVo vo);
	
	/**
	 * 解答数对比(年级按班级)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByClazz(RequestVo vo);
	
	/**
	 * 解答数对比(年级按学科)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByGradeSubject(RequestVo vo);
	
	/**
	 * 查询解答数排名
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByRank(RequestVo vo);
	
	/**
	 * 查询解答数明细
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtDtl(RequestVo vo, Page page);
	
	public List<ResolveDoubt> findResolveDoubtDtl(RequestVo vo);
}
