package cn.strong.leke.diag.service.studentMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.diag.dao.studentMonitor.HomeworkAnalyseDao;
import cn.strong.leke.diag.model.studentMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.*;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.util.SetRankUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-15 23:43:05
 */
@Service
public class HomeworkAnalyseService {

	@Resource
	private HomeworkAnalyseDao homeworkAnalyseDao;
	@Resource
	private CommService commService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	protected static final Logger logger = LoggerFactory.getLogger(HomeworkAnalyseService.class);

	/*private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());*/

	/**
	 * 查询作业提交统计、走势、对比、排行等数据
	 * @param vo
	 */
	public ReturnResultBean queryHomeworkAnalysePageData(RequestVo vo) {
		/*CountDownLatch countDownLatch = new CountDownLatch(4);*/

		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);

		//查每个学生的作业提交情况
		List<StudentHomeworkSubmitBean> studentHomeworkList = homeworkAnalyseDao.selectStudentHomeworkSubmitStatus(vo);

		ReturnResultBean resultBean = new ReturnResultBean();

		/*executor.execute(new Runnable() {
			@Override
			public void run() {*/
				long startTime1 = System.currentTimeMillis();
				//作业提交统计
				int submitNum = studentHomeworkList.stream().mapToInt(StudentHomeworkSubmitBean::getSubmitNum).sum();           //正常提交
				int lateSubmitNum = studentHomeworkList.stream().mapToInt(StudentHomeworkSubmitBean::getLateSubmitNum).sum();   //延迟提交
				int noSubmitNum = studentHomeworkList.stream().mapToInt(StudentHomeworkSubmitBean::getNoSubmitNum).sum();       //未提交
				int totalNum = submitNum + lateSubmitNum + noSubmitNum;     //共作业份数

				double submitPro = totalNum > 0 ? (double) submitNum / totalNum : 0;           //正常提交占比
				double lateSubmitPro = totalNum > 0 ? (double) lateSubmitNum / totalNum : 0;   //延迟提交占比
				double noSubmitPro = totalNum > 0 ? (double) noSubmitNum / totalNum : 0;       //未提交占比

				HomeworkCountBean homeworkCountBean = new HomeworkCountBean();
				homeworkCountBean.setStudentTotalNum(homeworkAnalyseDao.selectStudentIdByParam(vo).size());
				homeworkCountBean.setClassHomeworkNum(homeworkAnalyseDao.selectTotalOverClassHomeworkNum(vo));   //查共需完成的班级作业份数
				homeworkCountBean.setSubmitNum(submitNum);
				homeworkCountBean.setLateSubmitNum(lateSubmitNum);
				homeworkCountBean.setNoSubmitNum(noSubmitNum);
				homeworkCountBean.setSubmitPro(commService.handlerPoint(submitPro * 100));
				homeworkCountBean.setLateSubmitPro(commService.handlerPoint(lateSubmitPro * 100));
				homeworkCountBean.setNoSubmitPro(commService.handlerPoint(noSubmitPro * 100));
				resultBean.setCountBean(homeworkCountBean);
				long endTime1 = System.currentTimeMillis();
				logger.info("end calc homework count >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + (endTime1 - startTime1) + "ms" + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				/*countDownLatch.countDown();
			}
		});*/


		/*executor.execute(new Runnable() {
			@Override
			public void run() {*/
				long startTime2 = System.currentTimeMillis();
				//作业提交率走势
				resultBean.setTrendList((List<HomeworkTrendBean>) queryHomeworkTrendData(vo, true).getTrendList());
				long endTime2 = System.currentTimeMillis();
				logger.info("end calc homework trendList data >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + (endTime2 - startTime2) + "ms" + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				/*countDownLatch.countDown();
			}
		});*/


		/*executor.execute(new Runnable() {
			@Override
			public void run() {*/
				long startTime3 = System.currentTimeMillis();
				//作业提交率对比
				resultBean.setCompareBeanList(getCompareData(vo));
				long endTime3 = System.currentTimeMillis();
				logger.info("end calc homework compare data >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + (endTime3 - startTime3) + "ms" + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				/*countDownLatch.countDown();
			}
		});*/


		/*executor.execute(new Runnable() {
			@Override
			public void run() {*/
				long startTime4 = System.currentTimeMillis();
				//作业正常提交率排行
				List<RankBean> rankBeans = new ArrayList<>();
				studentHomeworkList.forEach(s -> {
					Long studentId = s.getStudentId();
					String studentName = s.getStudentName();
					double avgStuSubmitPro = s.getStuHomeworkNum() > 0 ? (double) s.getSubmitNum() / s.getStuHomeworkNum() : 0;
					RankBean rankBean = new RankBean();
					rankBean.setStudentId(studentId);
					rankBean.setStudentName(studentName);
					rankBean.setTotalLevel(BigDecimal.valueOf(commService.handlerPoint(avgStuSubmitPro * 100)));
					rankBeans.add(rankBean);
				});

				List<RankBean> rankBeanList = rankBeans.stream().sorted(Comparator.comparing(RankBean::getTotalLevel).reversed()).collect(Collectors.toList());

				RankResultBean anInt = (new SetRankUtils<RankBean>()).getRankData(rankBeanList, "BigDecimal", false);
				resultBean.setRankFrontBeanList(anInt.getRankFrontBeanList());
				resultBean.setRankBackBeanList(anInt.getRankBackBeanList());
				long endTime4 = System.currentTimeMillis();
				logger.info("end calc homework rank data >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + (endTime4 - startTime4) + "ms" + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				/*countDownLatch.countDown();
			}
		});*/

		/*try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error("homework analyse collect error #################");
		}*/
		return resultBean;
	}

	/**
	 * 作业走势
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryHomeworkTrendData(RequestVo vo, boolean isExecutor) {
		if (!isExecutor) {
			//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
			commService.setCommPropToRequestVo(vo);
		}
		ReturnResultBean resultBean = new ReturnResultBean();
		//作业提交率走势
		List<HomeworkTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的日
			trendList = getTrendData(commService.findBeikeRateByDay(vo), vo);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//根据周查出对应的走势数据
			trendList = getTrendData(commService.findBeikeRateByWeek(vo), vo);
		} else {
			//根据月查出对应的走势数据
			trendList = getTrendData(commService.findBeikeRateByMonth(vo), vo);
		}
		resultBean.setTrendList(trendList);
		return resultBean;
	}

	/**
	 * 根据日周月List查出对应的走势数据
	 * @param commList
	 * @param vo
	 * @return
	 */
	private List<HomeworkTrendBean> getTrendData(List<CommProp> commList, RequestVo vo) {
		List<HomeworkTrendBean> trendList = new ArrayList<>();
		List<StudentHomeworkSubmitBean> studentAllHomeworkList = homeworkAnalyseDao.selectStudentHomework(vo);
		commList.forEach(comm -> {
			HomeworkTrendBean homeworkTrendBean = new HomeworkTrendBean();
			Date startDate = DateUtils.parseDate(comm.getStartDate());
			Date endDate = DateUtils.addDays(DateUtils.parseDate(comm.getEndDate()), 1);
			homeworkTrendBean.setStartDate(comm.getStartDate());
			homeworkTrendBean.setEndDate(DateUtils.formatDate(endDate));
			homeworkTrendBean.setDateStr(comm.getDateStr());

			List<StudentHomeworkSubmitBean> collectList = studentAllHomeworkList.stream()
					.filter(s -> s.getCloseTime().getTime() >= startDate.getTime() && s.getCloseTime().getTime() < endDate.getTime())
					.collect(Collectors.toList());

			long submitNum = collectList.stream().filter(s -> s.getSubmitStatus() == 1).count();    //正常提交
			long notSubmitNum = collectList.stream().filter(s -> s.getSubmitStatus() != 1).count(); //非正常提交
			long totalNum = submitNum + notSubmitNum;     //共作业份数
			double submitPro = totalNum > 0 ? (double) submitNum / totalNum : 0;           //正常提交占比

			homeworkTrendBean.setSubmitPro(commService.handlerPoint(submitPro * 100));
			trendList.add(homeworkTrendBean);
		});
		return trendList;
	}


	/**
	 * 获取对比数据
	 * @param vo
	 * @return
	 */
	private List<HomeworkCompareBean> getCompareData(RequestVo vo) {
		List<HomeworkCompareBean> compareBeanList = new ArrayList<>();
		//根据筛选条件查行政班级列表
		List<ClazzBean> clazzes = commService.queryClazzByParam(vo);
		clazzes.forEach(c -> {
			vo.setClassId(c.getClassId());
			//再根据studentIds查作业
			List<StudentHomeworkSubmitBean> studentHomeworkList = homeworkAnalyseDao.selectStudentHomeworkSubmitStatus(vo);

			int submitNum = studentHomeworkList.stream().mapToInt(StudentHomeworkSubmitBean::getSubmitNum).sum();           //正常提交
			int lateSubmitNum = studentHomeworkList.stream().mapToInt(StudentHomeworkSubmitBean::getLateSubmitNum).sum();   //延迟提交
			int noSubmitNum = studentHomeworkList.stream().mapToInt(StudentHomeworkSubmitBean::getNoSubmitNum).sum();       //未提交

			int totalNum = submitNum + lateSubmitNum + noSubmitNum;                        //共作业份数
			double submitPro = totalNum > 0 ? (double) submitNum / totalNum : 0;           //正常提交占比

			HomeworkCompareBean compareBean = new HomeworkCompareBean();
			compareBean.setClassId(c.getClassId());
			compareBean.setClassName(c.getClassName());
			compareBean.setSubmitPro(commService.handlerPoint(submitPro * 100));
			compareBeanList.add(compareBean);

		});
		return compareBeanList;
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<HomeworkDetailBean> queryHomeworkDetailDataPage(RequestVo vo, Page page) {
		List<HomeworkDetailBean> list = handlerHomeworkDetailData(vo);
		page.setTotalSize(list.size());
		return commService.handleOrderData(vo, list, HomeworkDetailBean.class).stream().skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());
	}

	/**
	 * 查询要导出的数据
	 * @param vo
	 * @return
	 */
	public List<HomeworkDetailBean> queryHomeworkDetailData(RequestVo vo) {
		List<HomeworkDetailBean> list = handlerHomeworkDetailData(vo);
		return commService.handleOrderData(vo, list, HomeworkDetailBean.class);
	}

	/**
	 * 处理明细和导出数据
	 * @param vo
	 * @return
	 */
	public List<HomeworkDetailBean> handlerHomeworkDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("submitNum");
			vo.setOrderType("desc");
		}

		return homeworkAnalyseDao.selectStudentIdByParam(vo).size() > 0 ? homeworkAnalyseDao.selectHomeworkDetailDataPage(vo) : new ArrayList<>();
	}

	/**
	 * 查看作业详情数据
	 * @param vo
	 * @param page
	 * @return
	 * 批改状态：1.未批改 2.部分批改 3.已批改
	 */
	public List<StuHomeworkDetailBean> queryStuHomeworkDetailData(RequestVo vo, Page page) {
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("closeTime");
			vo.setOrderType("desc");
		}
		List<StuHomeworkDetailBean> list = homeworkAnalyseDao.selectStuHomeworkDetailData(vo);
		list.forEach(l -> {
			if (l.getCorrectTime() == null && l.getScore() == null) {
				l.setCorrectStatus(1);
			} else if (l.getSubmitStatus() != 0 && l.getCorrectTime() == null && l.getScore() >= 0) {
				l.setCorrectStatus(2);
			} else if (l.getCorrectTime() != null) {
				l.setCorrectStatus(3);
			}
		});
		page.setTotalSize(list.size());
		return commService.handleOrderData(vo, list, StuHomeworkDetailBean.class).stream().skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());
	}
}
