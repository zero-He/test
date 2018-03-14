package cn.strong.leke.diag.service.teachingMonitor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.diag.dao.teachingMonitor.ResolveDoubtDao;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ResolveDoubt;
import cn.strong.leke.framework.page.jdbc.Page;

@Service
public class ResolveDoubtService {
	
	@Resource
	private ResolveDoubtDao resolveDoubtDao;
	
	/**
	 * 查询解答统计信息
	 * @param vo
	 * @return
	 */
	public ResolveDoubt findResolveDoubtStat(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtStat(vo);
	}
	
	/**
	 * 按日查询解答数走势
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByDay(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByDay(vo);
	}
	
	/**
	 * 按周查询解答数走势
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByWeek(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByWeek(vo);
	}
	
	/**
	 * 按月查询解答数走势
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByMonth(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByMonth(vo);
	}
	
	/**
	 * 解答数对比(全校按年级)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByGrade(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByGrade(vo);
	}
	
	/**
	 * 解答数对比(全校按学科)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByAllSubject(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByAllSubject(vo);
	}
	
	/**
	 * 解答数对比(年级按班级)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByClazz(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByClazz(vo);
	}
	
	/**
	 * 解答数对比(年级按学科)
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByGradeSubject(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByGradeSubject(vo);
	}
	
	/**
	 * 查询解答数排名
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtByRank(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtByRank(vo);
	}
	
	/**
	 * 查询解答数明细
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> findResolveDoubtDtl(RequestVo vo, Page page){
		return resolveDoubtDao.findResolveDoubtDtl(vo, page);
	}
	
	public List<ResolveDoubt> findResolveDoubtDtl(RequestVo vo){
		return resolveDoubtDao.findResolveDoubtDtl(vo);
	}
}
