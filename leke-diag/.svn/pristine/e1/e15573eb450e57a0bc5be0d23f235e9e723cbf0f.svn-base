package cn.strong.leke.diag.dao.teachingMonitor;

import java.util.List;
import java.util.Map;

import cn.strong.leke.diag.model.teachingMonitor.BeikeRate;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikeInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.Clazz;

public interface LessonBeikeInfoDao {
	
	/**
	 * 根据学校、年级、学科查询一段时间内的课堂备课信息
	 * @param param
	 * @return
	 */
	public List<LessonBeikeInfo> findLessonBeikeInfoByGradeSubject(RequestVo vo);
	
	/**
	 * 查询老师备课率排行
	 * @param vo
	 * @return
	 */
	public List<BeikeRate> findTeacherBeikeRateRank(RequestVo vo);
	
	/**
	 * 根据年级查学段
	 * @param vo
	 * @return
	 */
	public Long findStageIdByGrade(RequestVo vo);
	
	/**
	 * 按日查询趋势数据
	 * @param vo 请求参数
	 * @return
	 */
	public List<CommProp> findTrendDateByDay(RequestVo vo);
	
	/**
	 * 按周查询趋势数据
	 * @param vo 请求参数
	 * @return
	 */
	public List<CommProp> findTrendDateByWeek(RequestVo vo);
	
	/**
	 * 按月查询趋势数据
	 * @param vo 请求参数
	 * @return
	 */
	public List<CommProp> findTrendDateByMonth(RequestVo vo);
	
	/**
	 * 查询老师上课明细数据
	 * @param vo
	 * @return
	 */
	public List<LessonBeikeInfo> findLessonDtlOfTeacher(RequestVo vo, Page page);
	
	public List<LessonBeikeInfo> findLessonDtlOfTeacher(RequestVo vo);
	
	/**
	 * 按老师统计备课率
	 * @param vo
	 * @return
	 */
	public List<CommProp> findTeacherSubject(RequestVo vo);
}
