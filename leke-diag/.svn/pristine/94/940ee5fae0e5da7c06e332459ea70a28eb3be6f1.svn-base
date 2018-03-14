package cn.strong.leke.diag.service.teachingMonitor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.diag.dao.teachingMonitor.LessonAttendInfoDao;
import cn.strong.leke.diag.model.teachingMonitor.LessonAttendInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;

@Service
public class LessonAttendInfoService {
	
	@Resource
	private LessonAttendInfoDao lessonAttendInfoDao;
	
	/**
	 * 查询上课率统计信息
	 * @param vo
	 * @return
	 */
	public LessonAttendInfo findAttendLessonStat(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonStat(vo);
	}
	
	/**
	 * 按日查询上课率走势
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByDay(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByDay(vo);
	}
	
	/**
	 * 按周查询上课率走势
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByWeek(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByWeek(vo);
	}
	
	/**
	 * 按月查询上课率走势
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByMonth(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByMonth(vo);
	}
	
	/**
	 * 上课率对比(全校按年级)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByGrade(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByGrade(vo);
	}
	
	/**
	 * 上课率对比(全校按学科)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByAllSubject(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByAllSubject(vo);
	}
	
	/**
	 * 上课率对比(年级按班级)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByClazz(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByClazz(vo);
	}
	
	/**
	 * 上课率对比(年级按学科)
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateByGradeSubject(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateByGradeSubject(vo);
	}
	
	/**
	 * 查询上课率排名
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateRank(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateRank(vo);
	}
	
	/**
	 * 查询老师上课明细数据
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> findAttendLessonRateDtl(RequestVo vo, Page page){
		return lessonAttendInfoDao.findAttendLessonRateDtl(vo, page);
	}
	
	public List<LessonAttendInfo> findAttendLessonRateDtl(RequestVo vo){
		return lessonAttendInfoDao.findAttendLessonRateDtl(vo);
	}
	
}
