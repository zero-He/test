package cn.strong.leke.diag.service.teachingMonitor;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.diag.dao.teachingMonitor.AttendanceXDao;
import cn.strong.leke.diag.model.teachingMonitor.ClassBean;
import cn.strong.leke.diag.model.teachingMonitor.RankResultBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.attendance.*;
import cn.strong.leke.diag.util.SetRankUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-17 14:34:48
 */
@Service
public class AttendanceXService {

	@Resource
	private AttendanceXDao attendanceXDao;
	@Resource
	private CommService commService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	protected static final Logger logger = LoggerFactory.getLogger(AttendanceXService.class);

	private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * 根据筛选条件查询考勤的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryAttendancePageData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		ReturnResultBean resultBean = new ReturnResultBean();

		//年级学科班级对比
		ReturnResultBean compareResultBean = queryAttendanceCompareData(vo, true);
		List<AttendanceCompareBean> compareBeanList = (List<AttendanceCompareBean>) compareResultBean.getCompareBeanList();
		resultBean.setCompareBeanList(compareBeanList);

		final CountDownLatch countDownLatch = new CountDownLatch(3);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				logger.info("start calc attendance count data……");
				//统计
				AttendanceCountBean countBean = attendanceXDao.selectAttendanceCountPartData(vo);
				resultBean.setCountBean(countBean);
				logger.info("end calc attendance count data……");
				countDownLatch.countDown();
			}
		});

		executor.execute(new Runnable() {
			@Override
			public void run() {
				logger.info("start calc attendance trend data……");
				//日周月走势
				ReturnResultBean trendResultBean = queryAttendanceTrendData(vo, true);
				resultBean.setTrendList((List<AttendanceBean>) trendResultBean.getTrendList());
				logger.info("end calc attendance trend data……");
				countDownLatch.countDown();
			}
		});

		executor.execute(new Runnable() {
			@Override
			public void run() {
				logger.info("start calc attendance rank data……");
				//排行
				List<AttendanceRankBean> rankBeanList = attendanceXDao.selectAttendanceRankFrontPartData(vo);
				RankResultBean rankData = (new SetRankUtils<AttendanceRankBean>()).getRankData(rankBeanList, "int");
				resultBean.setRankFrontBeanList(rankData.getRankFrontBeanList());
				resultBean.setRankBackBeanList(rankData.getRankBackBeanList());
				logger.info("end calc attendance rank data……");
				countDownLatch.countDown();
			}
		});

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error("teaching attendance collect error #################");
		}

		return resultBean;
	}

	/**
	 * 根据条件筛选考勤走势数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryAttendanceTrendData(RequestVo vo, boolean isExecutor) {
		if (!isExecutor) {
			//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
			commService.setCommPropToRequestVo(vo);
		}
		ReturnResultBean resultBean = new ReturnResultBean();
		//日周月走势
		List<AttendanceBean> trendList = null;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			trendList = attendanceXDao.selectAttendanceDayTrendPartData(vo);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			trendList = attendanceXDao.selectAttendanceWeekTrendPartData(vo);
		} else {
			trendList = attendanceXDao.selectAttendanceMonthTrendPartData(vo);
		}
		resultBean.setTrendList(trendList);
		return resultBean;
	}

	/**
	 * 根据条件筛选考勤对比数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryAttendanceCompareData(RequestVo vo, boolean isExecutor) {
		if (!isExecutor) {
			//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
			commService.setCommPropToRequestVo(vo);
		}
		ReturnResultBean resultBean = new ReturnResultBean();
		//年级学科班级对比
		List<AttendanceCompareBean> compareBeanList = new ArrayList<>();
		if (RequestVo.GRADE.equalsIgnoreCase(vo.getCompType())) {
			List<AttendanceCompareBean> beanList = new ArrayList<>();
			List<GradeRemote> gradeOfSchool = commService.findGradeOfSchool();
			gradeOfSchool.forEach(g -> {
				List<AttendanceCompareBean> collect = attendanceXDao.selectAttendanceCompareGradePartData(vo).stream()
						.filter(c -> Objects.equals(c.getGradeId(), g.getGradeId())).collect(Collectors.toList());
				if (collect.size() <= 0) {
					AttendanceCompareBean bean = new AttendanceCompareBean();
					bean.setGradeId(g.getGradeId());
					bean.setGradeName(g.getGradeName());
					beanList.add(bean);
				}
				beanList.addAll(collect);
			});
			compareBeanList = beanList;
		} else if (RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType()) || RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())) {
			Long subjectId = vo.getSubjectId();
			if (subjectId != null) {//有学科
				compareBeanList = attendanceXDao.selectAttendanceCompareSubjectPartData(vo).stream().filter(c -> Objects.equals(c.getSubjectId(), subjectId)).collect(Collectors.toList());
			} else {//无学科
				List<SubjectRemote> subjectOfSchool = commService.findSubjectOfSchool();
				List<AttendanceCompareBean> beanList = new ArrayList<>();
				subjectOfSchool.forEach(s -> {
					List<AttendanceCompareBean> collect = attendanceXDao.selectAttendanceCompareSubjectPartData(vo).stream()
							.filter(c -> Objects.equals(c.getSubjectId(), s.getSubjectId())).collect(Collectors.toList());
					if (collect.size() <= 0) {
						AttendanceCompareBean bean = new AttendanceCompareBean();
						bean.setSubjectId(s.getSubjectId());
						bean.setSubjectName(s.getSubjectName());
						beanList.add(bean);
					}
					beanList.addAll(collect);
				});
				compareBeanList = beanList;
			}

		} else if (RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())) {
			//查询上过课的classId（根据schoolId、gradeId、subjectId、startDate、endDate）
			List<ClassBean> classList = commService.queryIsLessonClazz(vo);

			/*//根据schoolId、gradeId查出所有的classIds
			ClazzQuery clazzQuery = new ClazzQuery();
			clazzQuery.setSchoolId(vo.getSchoolId());
			clazzQuery.setGradeId(vo.getGradeId());
			*//*clazzQuery.setType(1);*//*
			List<Clazz> clazzByQuery = klassRemoteService.findClazzByQuery(clazzQuery);*/
			List<AttendanceCompareBean> beanList = new ArrayList<>();
			classList.forEach(c -> {
				List<AttendanceCompareBean> collect = attendanceXDao.selectAttendanceCompareClassPartData(vo).stream()
						.filter(z -> Objects.equals(z.getClassId(), c.getClassId())).collect(Collectors.toList());
				if (collect.size() <= 0) {
					AttendanceCompareBean bean = new AttendanceCompareBean();
					bean.setClassId(c.getClassId());
					bean.setClassName(c.getClassName());
					beanList.add(bean);
				}
				beanList.addAll(collect);
			});
			compareBeanList = beanList.stream().sorted(new CommService.CnComparator<AttendanceCompareBean>(true, true, "className", AttendanceCompareBean.class)).collect(Collectors.toList());
		}
		resultBean.setCompareBeanList(compareBeanList);
		return resultBean;
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<AttendanceDetailBean> queryAttendanceDetailDataPage(RequestVo vo, Page page) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("allOn");
			vo.setOrderType("desc");
		}
		List<AttendanceDetailBean> attendanceDetailBeanList = attendanceXDao.selectAttendanceDetailPartData(vo);

		page.setTotalSize(attendanceDetailBeanList.size());
		attendanceDetailBeanList = commService.handleOrderData(vo, attendanceDetailBeanList, AttendanceDetailBean.class).stream()
				.skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());

		return attendanceDetailBeanList;
	}

	/**
	 * 根据筛选条件查询明细数据
	 * @param vo
	 * @return
	 */
	public List<AttendanceDetailBean> queryAttendanceDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("allOn");
			vo.setOrderType("desc");
		}
		List<AttendanceDetailBean> attendanceDetailBeanList = attendanceXDao.selectAttendanceDetailPartData(vo);
		return commService.handleOrderData(vo, attendanceDetailBeanList, AttendanceDetailBean.class);
	}

	/**
	 * 根据筛选条件查全勤率及详细数据
	 * @param vo
	 * schoolId:必须
	 * gradeId:可选
	 * subjectId:可选
	 * teacherName:可选（模糊匹配）
	 * startDate:必须
	 * endDate:必须
	 * @return
	 */
	public List<AttendanceProBean> queryAttendanceProAndDetail(RequestVo vo) {
		return attendanceXDao.selectAttendanceProAndDetail(vo);
	}

}
