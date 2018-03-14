package cn.strong.leke.diag.service.teachingMonitor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.diag.dao.teachingMonitor.CorrectHWDao;
import cn.strong.leke.diag.model.teachingMonitor.CorrectHW;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;

@Service
public class CorrectHWService {
	
	@Resource
	private CorrectHWDao correctHWDao;
	
	/**
	 * 查询作业批改统计课堂、老师数
	 * @param vo
	 * @return
	 */
	public CorrectHW findCorrectHWLessonTeacherNum(RequestVo vo){
		return correctHWDao.findCorrectHWLessonTeacherNum(vo);
	}
	
	/**
	 * 查询布置作业数，及主客观数
	 * @param vo
	 * @return
	 */
	public CorrectHW findCorrectHWAssignNum(RequestVo vo){
		return correctHWDao.findCorrectHWAssignNum(vo);
	}
	
	/**
	 * 通用查询作业批改情况，包括应批改作业份数、自动批改、全部批改、部分批改、未批改作业份数及占比
	 * @param vo
	 * @return
	 */
	public CorrectHW findCorrectHWComm(RequestVo vo){
		return correctHWDao.findCorrectHWComm(vo);
	}
	
	/**
	 * 按日查询批改率走势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByDay(RequestVo vo){
		return correctHWDao.findCorrectHWByDay(vo);
	}
	
	/**
	 * 按周查询批改率走势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByWeek(RequestVo vo){
		return correctHWDao.findCorrectHWByWeek(vo);
	}
	
	/**
	 * 按月查询批改率走势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByMonth(RequestVo vo){
		return correctHWDao.findCorrectHWByMonth(vo);
	}
	
	
	/**
	 * 批改率对比(全校按年级)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByGrade(RequestVo vo){
		return correctHWDao.findCorrectHWByGrade(vo);
	}
	
	/**
	 * 批改率对比(全校按学科)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByAllSubject(RequestVo vo){
		return correctHWDao.findCorrectHWByAllSubject(vo);
	}
	
	/**
	 * 批改率对比(年级按班级)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByClazz(RequestVo vo){
		return correctHWDao.findCorrectHWByClazz(vo);
	}
	
	/**
	 * 批改率对比(年级按学科)
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByGradeSubject(RequestVo vo){
		return correctHWDao.findCorrectHWByGradeSubject(vo);
	}
	
	/**
	 * 查询批改率排名
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWByRank(RequestVo vo){
		return correctHWDao.findCorrectHWByRank(vo);
	}
	
	/**
	 * 查询批改率明细
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> findCorrectHWDtl(RequestVo vo, Page page){
		return correctHWDao.findCorrectHWDtl(vo, page);
	}
	
	public List<CorrectHW> findCorrectHWDtl(RequestVo vo){
		return correctHWDao.findCorrectHWDtl(vo);
	}
}
