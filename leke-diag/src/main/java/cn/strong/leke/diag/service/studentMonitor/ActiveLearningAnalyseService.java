package cn.strong.leke.diag.service.studentMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.model.studentMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.*;
import cn.strong.leke.diag.mongo.studentMonitor.ActiveMongoDao;
import cn.strong.leke.diag.mongo.studentMonitor.LearningMongoDao;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.util.SetRankUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-25 11:45:31
 */
@Service
public class ActiveLearningAnalyseService {

	@Resource
	private CommService commService;
	@Resource
	private ActiveMongoDao activeMongoDao;
	@Resource
	private LearningMongoDao learningMongoDao;
	@Resource
	private IKlassRemoteService klassRemoteService;

	/**
	 * 查询自主学习统计、走势、对比、排行等数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryActiveLearningAnalysePageData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		//根据schoolId、gradeId或者classId查所有学生
		List<Long> studentIdList = commService.queryStudentIdByParam(vo);

		int studentTotalNum = studentIdList.size(); //总人数
		vo.setStudentIds(studentIdList);
		List<LearningBean> learningBeanList = learningMongoDao.queryLearningData(vo);
		List<ActiveLearningBean> activeLearningBeanList = activeMongoDao.queryActiveLearningData(vo);

		//统计部分
		/*Map<Long, List<ActiveLearningBean>> learningListByStudentId = activeLearningBeanList.stream().collect(Collectors.groupingBy(ActiveLearningBean::getStudentId));*/

		int learningNum = learningBeanList.stream().collect(Collectors.groupingBy(LearningBean::getUserId)).size();             //微课通学习人数
		int totalLearnKlgNum = learningBeanList.stream().collect(Collectors.groupingBy(LearningBean::getLearnKlgId)).size();    //所有学生学习的知识点总数

		int totalNum = activeLearningBeanList.stream().mapToInt(ActiveLearningBean::getTotalNum).sum();
		int rightNum = activeLearningBeanList.stream().mapToInt(ActiveLearningBean::getRightNum).sum();

		double avgLearningNum = studentTotalNum > 0 ? (double) totalLearnKlgNum / studentTotalNum : 0;
		double avgQuestionNum = studentTotalNum > 0 ? (double) totalNum / studentTotalNum : 0;
		double rightPro = totalNum == 0 ? 0 : (double) rightNum / totalNum;

		ActiveLearningCountBean countBean = new ActiveLearningCountBean();
		countBean.setStudentTotalNum(studentTotalNum);                              //学生总人数
		countBean.setLearningNum(learningNum);                                      //学习人数
		countBean.setAvgLearningNum(commService.handlerPoint(avgLearningNum));      //人均学习知识点数
		countBean.setAvgQuestionNum(commService.handlerPoint(avgQuestionNum));      //人均刷题数
		countBean.setRightPro(commService.handlerPoint(rightPro * 100));            //平均正确率

		ReturnResultBean resultBean = new ReturnResultBean();
		resultBean.setCountBean(countBean);


		//走势部分
		List<ActiveLearningTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//根据日查出对应的走势数据
			trendList = getTrendData(activeLearningBeanList, learningBeanList, commService.findBeikeRateByDay(vo), studentTotalNum);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//根据周查出对应的走势数据
			trendList = getTrendData(activeLearningBeanList, learningBeanList, commService.findBeikeRateByWeek(vo), studentTotalNum);
		} else {
			//根据月查出对应的走势数据
			trendList = getTrendData(activeLearningBeanList, learningBeanList, commService.findBeikeRateByMonth(vo), studentTotalNum);
		}
		resultBean.setTrendList(trendList);


		//对比
		resultBean.setCompareBeanList(getCompareData(vo));


		//排行
		List<ActiveLearningRankBean> rankBeanList = new ArrayList<>();
		studentIdList.forEach(studentId -> {
			vo.setStudentId(studentId);
			List<LearningBean> learningBeanLists = learningMongoDao.queryLearningDataByStudentId(vo);
			List<ActiveLearningBean> activeLearningBeans = activeMongoDao.queryActiveLearningDataByStudentId(vo);

			long klgNum = learningBeanLists.stream().collect(Collectors.groupingBy(LearningBean::getLearnKlgId)).size();
			int totalNum1 = activeLearningBeans.stream().mapToInt(ActiveLearningBean::getTotalNum).sum();

			ActiveLearningRankBean learningRankBean = new ActiveLearningRankBean();
			learningRankBean.setStudentId(studentId);
			learningRankBean.setStudentName(UserBaseContext.getUserBaseByUserId(studentId).getUserName());
			learningRankBean.setLearningNum((int) klgNum);
			rankBeanList.add(learningRankBean);
		});

		List<ActiveLearningRankBean> rankBeanList1 = new ArrayList<>();
		studentIdList.forEach(studentId -> {
			vo.setStudentId(studentId);
			List<LearningBean> learningBeanLists = learningMongoDao.queryLearningDataByStudentId(vo);
			List<ActiveLearningBean> activeLearningBeans = activeMongoDao.queryActiveLearningDataByStudentId(vo);

			long klgNum = learningBeanLists.stream().collect(Collectors.groupingBy(LearningBean::getLearnKlgId)).size();
			int totalNum1 = activeLearningBeans.stream().mapToInt(ActiveLearningBean::getTotalNum).sum();

			ActiveLearningRankBean learningRankBean = new ActiveLearningRankBean();
			learningRankBean.setStudentId(studentId);
			learningRankBean.setStudentName(UserBaseContext.getUserBaseByUserId(studentId).getUserName());
			learningRankBean.setQuestionNum(totalNum1);
			rankBeanList1.add(learningRankBean);
		});

		List<ActiveLearningRankBean> collect = rankBeanList.stream().sorted(Comparator.comparing(ActiveLearningRankBean::getLearningNum).reversed()).collect(Collectors.toList());
		collect.forEach(c -> {
			c.setTotalCount(c.getLearningNum());
		});
		List<ActiveLearningRankBean> collect1 = rankBeanList1.stream().sorted(Comparator.comparing(ActiveLearningRankBean::getQuestionNum).reversed()).collect(Collectors.toList());
		collect1.forEach(c1 -> {
			c1.setTotalCount(c1.getQuestionNum());
		});

		RankResultBean anInt = (new SetRankUtils<ActiveLearningRankBean>()).getRankData(collect, "int", false);
		RankResultBean anInt1 = (new SetRankUtils<ActiveLearningRankBean>()).getRankData(collect1, "int", false);

		resultBean.setRankFrontBeanList(anInt.getRankFrontBeanList());
		resultBean.setRankBackBeanList(anInt.getRankBackBeanList());
		resultBean.setShareRankFrontBeanList(anInt1.getRankFrontBeanList());
		resultBean.setShareRankBackBeanList(anInt1.getRankBackBeanList());

		return resultBean;
	}

	/**
	 * 走势
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryActiveLearningTrendData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		//根据schoolId、gradeId或者classId查所有学生
		List<Long> studentIdList = commService.queryStudentIdByParam(vo);

		int studentTotalNum = studentIdList.size(); //总人数
		vo.setStudentIds(studentIdList);
		List<LearningBean> learningBeanList = learningMongoDao.queryLearningData(vo);
		List<ActiveLearningBean> activeLearningBeanList = activeMongoDao.queryActiveLearningData(vo);

		ReturnResultBean resultBean = new ReturnResultBean();
		//走势部分
		List<ActiveLearningTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//根据日查出对应的走势数据
			trendList = getTrendData(activeLearningBeanList, learningBeanList, commService.findBeikeRateByDay(vo), studentTotalNum);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//根据周查出对应的走势数据
			trendList = getTrendData(activeLearningBeanList, learningBeanList, commService.findBeikeRateByWeek(vo), studentTotalNum);
		} else {
			//根据月查出对应的走势数据
			trendList = getTrendData(activeLearningBeanList, learningBeanList, commService.findBeikeRateByMonth(vo), studentTotalNum);
		}
		resultBean.setTrendList(trendList);
		return resultBean;
	}

	/**
	 * 对比
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryActiveLearningCompareData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		ReturnResultBean resultBean = new ReturnResultBean();
		resultBean.setCompareBeanList(getCompareData(vo));
		return resultBean;
	}

	/**
	 * 根据日周月List查出对应的走势数据
	 * @param activeLearningBeanList
	 * @param learningBeanList
	 * @param commList
	 * @param studentTotalNum
	 * @return
	 */
	private List<ActiveLearningTrendBean> getTrendData(List<ActiveLearningBean> activeLearningBeanList, List<LearningBean> learningBeanList, List<CommProp> commList, int studentTotalNum) {
		List<ActiveLearningTrendBean> trendList = new ArrayList<>();
		for (CommProp comm : commList) {
			Date startDate = DateUtils.parseDate(comm.getStartDate());
			Date endDate = DateUtils.addDays(DateUtils.parseDate(comm.getEndDate()), 1);
			ActiveLearningTrendBean activeLearningTrendBean = new ActiveLearningTrendBean();
			activeLearningTrendBean.setStartDate(comm.getStartDate());
			activeLearningTrendBean.setEndDate(DateUtils.formatDate(endDate));
			activeLearningTrendBean.setDateStr(comm.getDateStr());

			long klgNum = learningBeanList.stream().filter(l -> l.getStart().getTime() >= startDate.getTime() && l.getEnd().getTime() < endDate.getTime())
					.collect(Collectors.groupingBy(LearningBean::getLearnKlgId)).size();
			int totalNum = activeLearningBeanList.stream().filter(a -> a.getSubmitTime() >= startDate.getTime() && a.getSubmitTime() < endDate.getTime()).mapToInt(ActiveLearningBean::getTotalNum).sum();

			double avgLearningNum = studentTotalNum > 0 ? (double) klgNum / studentTotalNum : 0;
			double avgQuestionNum = studentTotalNum > 0 ? (double) totalNum / studentTotalNum : 0;

			activeLearningTrendBean.setAvgLearningNum(commService.handlerPoint(avgLearningNum));
			activeLearningTrendBean.setAvgQuestionNum(commService.handlerPoint(avgQuestionNum));

			trendList.add(activeLearningTrendBean);
		}
		return trendList;
	}

	/**
	 * 获取对比数据
	 * @return
	 */
	private List<ActiveLearningCompareBean> getCompareData(RequestVo vo) {
		List<ActiveLearningCompareBean> compareBeanList = new ArrayList<>();
		//根据studentIds查行政班级列表
		List<ClazzBean> clazzes = commService.queryClazzByParam(vo);
		clazzes.forEach(c -> {
			//根据classId查这个班级的学生
			List<Long> studentIdsByClassId = klassRemoteService.findStudentIdsByClassId(c.getClassId());
			int studentTotalNum = studentIdsByClassId.size();   //该班级的学生总人数
			vo.setStudentIds(studentIdsByClassId);
			List<LearningBean> learningBeanList = learningMongoDao.queryLearningData(vo);
			List<ActiveLearningBean> activeLearningBeanList = activeMongoDao.queryActiveLearningData(vo);

			long klgNum = learningBeanList.stream().collect(Collectors.groupingBy(LearningBean::getLearnKlgId)).size();
			int totalNum = activeLearningBeanList.stream().mapToInt(ActiveLearningBean::getTotalNum).sum();

			double avgLearningNum = studentTotalNum > 0 ? (double) klgNum / studentTotalNum : 0;
			double avgQuestionNum = studentTotalNum > 0 ? (double) totalNum / studentTotalNum : 0;

			ActiveLearningCompareBean compareBean = new ActiveLearningCompareBean();
			compareBean.setClassId(c.getClassId());
			compareBean.setClassName(c.getClassName());
			compareBean.setAvgLearningNum(commService.handlerPoint(avgLearningNum));
			compareBean.setAvgQuestionNum(commService.handlerPoint(avgQuestionNum));
			compareBeanList.add(compareBean);

		});

		return compareBeanList;
	}

	/**
	 * 查询分页明细
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<ActiveLearningDetailBean> queryActiveLearningDetailDataPage(RequestVo vo, Page page) {
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("learningNum");
			vo.setOrderType("desc");
		}
		List<ActiveLearningDetailBean> learningDetailBeanList = handlerDetailData(vo);

		page.setTotalSize(learningDetailBeanList.size());
		return commService.handleOrderData(vo, learningDetailBeanList, ActiveLearningDetailBean.class).stream().skip((page.getCurPage() - 1) * page.getPageSize())
				.limit(page.getPageSize()).collect(Collectors.toList());
	}

	/**
	 * 自主学习导出
	 * @param vo
	 * @return
	 */
	public List<ActiveLearningDetailBean> exportActiveLearningDtlData(RequestVo vo) {
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("learningNum");
			vo.setOrderType("desc");
		}
		List<ActiveLearningDetailBean> learningDetailBeanList = handlerDetailData(vo);
		return commService.handleOrderData(vo, learningDetailBeanList, ActiveLearningDetailBean.class);
	}

	/**
	 * 统一处理明细数据
	 * @param vo
	 * @return
	 */
	private List<ActiveLearningDetailBean> handlerDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);

		List<ActiveLearningDetailBean> learningDetailBeanList = new ArrayList<>();

		List<Clazz> clazzList = new ArrayList<>();
		if (vo.getClassId() != null) {
			Clazz clazz = new Clazz();
			clazz.setClassId(vo.getClassId());
			clazz.setClassName(klassRemoteService.findClazzByClassIds(Arrays.asList(vo.getClassId())).get(0).getClassName());
			clazzList.add(clazz);
		} else {
			clazzList = commService.queryClassByGradeId(vo.getGradeId());
		}

		clazzList.forEach(c -> {
			Long classId = c.getClassId();
			String className = c.getClassName();

			//根据schoolId、gradeId或者classId查所有学生
			vo.setClassId(classId);
			List<Long> studentIdList = commService.queryStudentIdByParam(vo);

			studentIdList.forEach(studentId -> {
				vo.setStudentId(studentId);
				List<LearningBean> learningBeanList = learningMongoDao.queryLearningDataByStudentId(vo);
				List<ActiveLearningBean> activeLearningBeanList = activeMongoDao.queryActiveLearningDataByStudentId(vo);

				long klgNum = learningBeanList.stream().collect(Collectors.groupingBy(LearningBean::getLearnKlgId)).size();
				int totalNum = activeLearningBeanList.stream().mapToInt(ActiveLearningBean::getTotalNum).sum();
				int rightNum = activeLearningBeanList.stream().mapToInt(ActiveLearningBean::getRightNum).sum();

				BigDecimal rightPro = totalNum == 0 ? BigDecimal.ZERO : BigDecimal.valueOf((double) rightNum / totalNum);

				ActiveLearningDetailBean learningDetailBean = new ActiveLearningDetailBean();
				learningDetailBean.setClassId(classId);
				learningDetailBean.setClassName(className);
				learningDetailBean.setStudentId(studentId);
				learningDetailBean.setStudentName(UserBaseContext.getUserBaseByUserId(studentId).getUserName());
				learningDetailBean.setLearningNum((int) klgNum);
				learningDetailBean.setQuestionNum(totalNum);
				learningDetailBean.setRightPro(commService.handlerPoint(rightPro.multiply(new BigDecimal(100))));
				learningDetailBeanList.add(learningDetailBean);
			});

		});
		return learningDetailBeanList;
	}

}
