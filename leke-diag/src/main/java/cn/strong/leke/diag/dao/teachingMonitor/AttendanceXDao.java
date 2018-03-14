package cn.strong.leke.diag.dao.teachingMonitor;

import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.attendance.*;
import cn.strong.leke.framework.page.jdbc.Page;

import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-17 14:35:25
 */
public interface AttendanceXDao {

	/**
	 * 根据筛选条件查询统计相关的数据
	 * @param vo
	 * @return
	 */
	AttendanceCountBean selectAttendanceCountPartData(RequestVo vo);

	/**
	 * 根据筛选条件查询日走势数据
	 * @param vo
	 * @return
	 */
	List<AttendanceBean> selectAttendanceDayTrendPartData(RequestVo vo);

	/**
	 * 根据筛选条件查询周走势数据
	 * @param vo
	 * @return
	 */
	List<AttendanceBean> selectAttendanceWeekTrendPartData(RequestVo vo);

	/**
	 * 根据筛选条件查询月走势数据
	 * @param vo
	 * @return
	 */
	List<AttendanceBean> selectAttendanceMonthTrendPartData(RequestVo vo);

	/**
	 * 根据筛选条件查询年级横向对比数据
	 * @param vo
	 * @return
	 */
	List<AttendanceCompareBean> selectAttendanceCompareGradePartData(RequestVo vo);

	/**
	 * 根据筛选条件查询学科横向对比数据
	 * @param vo
	 * @return
	 */
	List<AttendanceCompareBean> selectAttendanceCompareSubjectPartData(RequestVo vo);

	/**
	 * 根据筛选条件查询班级横向对比数据
	 * @param vo
	 * @return
	 */
	List<AttendanceCompareBean> selectAttendanceCompareClassPartData(RequestVo vo);

	/**
	 * 根据筛选条件查询全勤前5名
	 * @param vo
	 * @return
	 */
	List<AttendanceRankBean> selectAttendanceRankFrontPartData(RequestVo vo);

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	List<AttendanceDetailBean> selectAttendanceDetailDataPage(RequestVo vo, Page page);

	/**
	 * 根据筛选条件查询明细数据
	 * @param vo
	 * @return
	 */
	List<AttendanceDetailBean> selectAttendanceDetailPartData(RequestVo vo);

	/**
	 * 根据筛选条件查全勤率
	 * @param vo
	 * @return
	 */
	List<AttendanceProBean> selectAttendanceProAndDetail(RequestVo vo);
}
