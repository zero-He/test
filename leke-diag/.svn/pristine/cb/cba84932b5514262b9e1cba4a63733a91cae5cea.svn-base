package cn.strong.leke.diag.service.studentMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.studentMonitor.ReadyAnalyseDao;
import cn.strong.leke.diag.model.studentMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.*;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.util.SetRankUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author  LIU.SHITING
 * @version  1.5
 * @date  2017-11-21 10:38:21
 */
@Service
public class ReadyAnalyseService {

	@Resource
	private ReadyAnalyseDao readyAnalyseDao;
	@Resource
	private CommService commService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private ILessonRemoteService lessonRemoteService;

	protected static final Logger logger = LoggerFactory.getLogger(ReadyAnalyseService.class);

	/**
	 * 查询预习统计、走势、对比、排行等数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryReadyAnalysePageData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);

		//根据schoolId、gradeId、classId、subjectId等查所有预习作业
		List<ReadyHomeworkBean> readyHomeworks = readyAnalyseDao.queryAllReadyHomework(vo);
		//所有学生所有课堂的所有预习作业
		Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList = readyHomeworks.stream()
				.collect(Collectors.groupingBy(ReadyHomeworkBean::getStudentId, Collectors.groupingBy(ReadyHomeworkBean::getCourseSingleId)));

		ReturnResultBean resultBean = new ReturnResultBean();
		//预习统计部分
		ReadyBean readyBean = handlerReadyPro(collectList);
		ReadyCountBean countBean = new ReadyCountBean();
		countBean.setStudentTotalNum(commService.queryStudentIdByParam(vo).size()); //总共学生数
		countBean.setLessonTotalNum(readyAnalyseDao.querySingleIds(vo).size());//总共课堂数
		countBean.setAllReady(readyBean.getAllReady());
		countBean.setNoReady(readyBean.getNoReady());
		countBean.setPartReady(readyBean.getPartReady());
		countBean.setAllReadyPro(readyBean.getAllReadyPro());
		countBean.setNoReadyPro(readyBean.getNoReadyPro());
		countBean.setPartReadyPro(readyBean.getPartReadyPro());
		resultBean.setCountBean(countBean);

		//预习率走势部分
		List<ReadyTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//根据日查出对应的走势数据
			trendList = getTrendData(readyHomeworks, commService.findBeikeRateByDay(vo));
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//根据周查出对应的走势数据
			trendList = getTrendData(readyHomeworks, commService.findBeikeRateByWeek(vo));
		} else {
			//根据月查出对应的走势数据
			trendList = getTrendData(readyHomeworks, commService.findBeikeRateByMonth(vo));
		}
		resultBean.setTrendList(trendList);

		//预习率对比部分
		resultBean.setCompareBeanList(getCompareData(vo, readyHomeworks));

		//排行
		List<ReadyStatusBean> readyStatusBeanList = handlerAllStudentSingleHomework(collectList);
		readyStatusBeanList.forEach(r -> {
			int totalReadyNum = r.getAllReady() + r.getPartReady() + r.getNoReady();
			double allReadyPro = totalReadyNum == 0 ? 0 : (double) r.getAllReady() / totalReadyNum;
			r.setTotalLevel(BigDecimal.valueOf(commService.handlerPoint(allReadyPro * 100)));
			r.setStudentName(UserBaseContext.getUserBaseByUserId(r.getStudentId()).getUserName());
		});
		List<ReadyStatusBean> rankBeanList = readyStatusBeanList.stream().sorted(Comparator.comparing(ReadyStatusBean::getTotalLevel).reversed()).collect(Collectors.toList());
		RankResultBean anInt = (new SetRankUtils<ReadyStatusBean>()).getRankData(rankBeanList, "BigDecimal", false);
		resultBean.setRankFrontBeanList(anInt.getRankFrontBeanList());
		resultBean.setRankBackBeanList(anInt.getRankBackBeanList());

		return resultBean;
	}

	/**
	 * 根据日周月List查出对应的走势数据
	 * @param readyHomeworks
	 * @param commList
	 * @return
	 */
	private List<ReadyTrendBean> getTrendData(List<ReadyHomeworkBean> readyHomeworks, List<CommProp> commList) {

		List<ReadyTrendBean> trendList = new ArrayList<>();
		commList.forEach(comm -> {
			Date startDate = DateUtils.parseDate(comm.getStartDate());
			Date endDate = DateUtils.addDays(DateUtils.parseDate(comm.getEndDate()), 1);
			ReadyTrendBean trendBean = new ReadyTrendBean();
			trendBean.setStartDate(comm.getStartDate());
			trendBean.setEndDate(DateUtils.formatDate(endDate));
			trendBean.setDateStr(comm.getDateStr());
			Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList = readyHomeworks.stream()
					.filter(s -> s.getCloseTime().getTime() >= startDate.getTime() && s.getCloseTime().getTime() < endDate.getTime())
					.collect(Collectors.groupingBy(ReadyHomeworkBean::getStudentId, Collectors.groupingBy(ReadyHomeworkBean::getCourseSingleId)));

			//所有学生全面预习、未预习、部分预习的课堂数
			ReadyBean readyBean = handlerReadyPro(collectList);

			trendBean.setAllReadyPro(readyBean.getAllReadyPro());
			trendBean.setPartReadyPro(readyBean.getPartReadyPro());
			trendBean.setNoReadyPro(readyBean.getNoReadyPro());
			trendList.add(trendBean);
		});

		return trendList;
	}

	/**
	 * 获取对比数据
	 * @param vo
	 * @return
	 */
	private List<ReadyCompareBean> getCompareData(RequestVo vo, List<ReadyHomeworkBean> readyHomeworks) {

		List<ReadyCompareBean> compareBeanList = new ArrayList<>();
		//根据studentIds查行政班级列表
		List<ClazzBean> clazzes = commService.queryClazzByParam(vo);
		clazzes.forEach(c -> {
			//根据classId查这个班级的学生
			List<Long> studentIdsByClassId = klassRemoteService.findStudentIdsByClassId(c.getClassId());

			List<ReadyHomeworkBean> classStuHomeworks = new ArrayList<>();
			studentIdsByClassId.forEach(studentId -> {
				List<ReadyHomeworkBean> stuHomework = readyHomeworks.stream().filter(r -> r.getStudentId().equals(studentId)).collect(Collectors.toList());
				classStuHomeworks.addAll(stuHomework);
			});

			Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList = classStuHomeworks.stream()
					.collect(Collectors.groupingBy(ReadyHomeworkBean::getStudentId, Collectors.groupingBy(ReadyHomeworkBean::getCourseSingleId)));

			//该班所有学生全面预习、未预习、部分预习的课堂数
			ReadyBean readyBean = handlerReadyPro(collectList);
			ReadyCompareBean compareBean = new ReadyCompareBean();
			compareBean.setClassId(c.getClassId());
			compareBean.setClassName(c.getClassName());
			compareBean.setAllReadyPro(readyBean.getAllReadyPro());
			compareBean.setNoReadyPro(readyBean.getNoReadyPro());
			compareBean.setPartReadyPro(readyBean.getPartReadyPro());
			compareBeanList.add(compareBean);
		});

		return compareBeanList;
	}

	/**
	 * 处理预习率数据
	 * @param collectList
	 * @return
	 */
	private ReadyBean handlerReadyPro(Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList) {
		//所有学生全面预习、未预习、部分预习的课堂数
		List<ReadyStatusBean> readyStatusBeanList = handlerAllStudentSingleHomework(collectList);

		//预习统计部分
		//所有学生的全面预习、未预习、部分预习的课堂数
		final int[] allReady = {0}, noReady = {0}, partReady = {0};
		readyStatusBeanList.forEach(r -> {
			allReady[0] = allReady[0] + r.getAllReady();
			noReady[0] = noReady[0] + r.getNoReady();
			partReady[0] = partReady[0] + r.getPartReady();
		});

		int proLessonNum = allReady[0] + noReady[0] + partReady[0];

		//所有学生的全面预习、未预习、部分预习的占比
		double allReadyPro = proLessonNum > 0 ? (double) allReady[0] / proLessonNum : 0;
		double noReadyPro = proLessonNum > 0 ? (double) noReady[0] / proLessonNum : 0;
		double partReadyPro = proLessonNum > 0 ? (double) partReady[0] / proLessonNum : 0;

		ReadyBean readyBean = new ReadyBean();
		readyBean.setAllReady(allReady[0]);
		readyBean.setNoReady(noReady[0]);
		readyBean.setPartReady(partReady[0]);
		readyBean.setAllReadyPro(commService.handlerPoint(allReadyPro * 100));
		readyBean.setNoReadyPro(commService.handlerPoint(noReadyPro * 100));
		readyBean.setPartReadyPro(commService.handlerPoint(partReadyPro * 100));
		return readyBean;
	}


	/**
	 * 所有学生全面预习、未预习、部分预习的课堂数
	 * @param collectList
	 * @return
	 */
	private List<ReadyStatusBean> handlerAllStudentSingleHomework(Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList) {
		List<ReadyStatusBean> readyStatusBeanList = new ArrayList<>();
		collectList.forEach((studentId, readyHomeworkBeanList) -> {
			final int[] allReady = {0}, noReady = {0}, partReady = {0};
			readyHomeworkBeanList.forEach((singleId, singleBeanList) -> {
				int readyHomeworkNum = singleBeanList.size();    //该学生该堂课应该要预习的作业数
				//提交状态 0：未提交，1：正常提交，2：延迟提交
				int submitNum = (int) singleBeanList.stream().filter(r -> r.getSubmitStatus() != 0).mapToLong(ReadyHomeworkBean::getHomeworkDtlId).count();
				int noSubmitNum = (int) singleBeanList.stream().filter(r -> r.getSubmitStatus() == 0).mapToLong(ReadyHomeworkBean::getHomeworkDtlId).count();
				if (readyHomeworkNum == submitNum) {
					allReady[0]++;
				} else if (readyHomeworkNum == noSubmitNum) {
					noReady[0]++;
				} else {
					partReady[0]++;
				}

			});
			ReadyStatusBean readyStatusBean = new ReadyStatusBean();
			readyStatusBean.setStudentId(studentId);
			readyStatusBean.setAllReady(allReady[0]);
			readyStatusBean.setNoReady(noReady[0]);
			readyStatusBean.setPartReady(partReady[0]);
			readyStatusBeanList.add(readyStatusBean);
		});
		return readyStatusBeanList;
	}

	/**
	 * 预习走势
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryReadyTrendData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);

		//根据schoolId、gradeId、classId、subjectId等查所有预习作业
		List<ReadyHomeworkBean> readyHomeworks = readyAnalyseDao.queryAllReadyHomework(vo);
		//所有学生所有课堂的所有预习作业
		Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList = readyHomeworks.stream()
				.collect(Collectors.groupingBy(ReadyHomeworkBean::getStudentId, Collectors.groupingBy(ReadyHomeworkBean::getCourseSingleId)));

		ReturnResultBean resultBean = new ReturnResultBean();
		//预习率走势部分
		List<ReadyTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//根据日查出对应的走势数据
			trendList = getTrendData(readyHomeworks, commService.findBeikeRateByDay(vo));
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//根据周查出对应的走势数据
			trendList = getTrendData(readyHomeworks, commService.findBeikeRateByWeek(vo));
		} else {
			//根据月查出对应的走势数据
			trendList = getTrendData(readyHomeworks, commService.findBeikeRateByMonth(vo));
		}
		resultBean.setTrendList(trendList);
		return resultBean;
	}

	/**
	 * 明细数据查询
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<ReadyStatusBean> queryReadyDetailDataPage(RequestVo vo, Page page) {
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("allReady");
			vo.setOrderType("desc");
		}
		List<ReadyStatusBean> readyStatusBeanList = handlerReadyDetailData(vo);
		page.setTotalSize(readyStatusBeanList.size());
		return commService.handleOrderData(vo, readyStatusBeanList, ReadyStatusBean.class).stream().skip((page.getCurPage() - 1) * page.getPageSize())
				.limit(page.getPageSize()).collect(Collectors.toList());

	}

	/**
	 * 预习明细导出
	 * @param vo
	 * @return
	 */
	public List<ReadyStatusBean> queryReadyDetailData(RequestVo vo) {
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("allReady");
			vo.setOrderType("desc");
		}
		List<ReadyStatusBean> readyStatusBeanList = handlerReadyDetailData(vo);
		return commService.handleOrderData(vo, readyStatusBeanList, ReadyStatusBean.class);
	}

	/**
	 * 处理预习明细数据
	 * @param vo
	 * @return
	 */
	private List<ReadyStatusBean> handlerReadyDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);

		//所有学生全面预习、未预习、部分预习的课堂数
		List<ReadyStatusBean> readyStatusBeanList = new ArrayList<>();

		//根据schoolId、gradeId、classId、subjectId等查所有预习作业
		List<ReadyHomeworkBean> readyHomeworks = readyAnalyseDao.queryAllReadyHomework(vo);
		//所有学生所有课堂的所有预习作业
		Map<Long, Map<Long, List<ReadyHomeworkBean>>> collectList = readyHomeworks.stream()
				.collect(Collectors.groupingBy(ReadyHomeworkBean::getStudentId, Collectors.groupingBy(ReadyHomeworkBean::getCourseSingleId)));
		collectList.forEach((studentId, stuList) -> {

			int readyLessonNum = stuList.size();     //该学生总共应该预习的课堂数
			final int[] allReady = {0}, noReady = {0}, partReady = {0};
			stuList.forEach((singleId, readyHomeBean) -> {

				int readyHomeworkNum = readyHomeBean.size();    //该学生该堂课应该要预习的作业数
				//提交状态 0：未提交，1：正常提交，2：延迟提交
				int submitNum = (int) readyHomeBean.stream().filter(r -> r.getSubmitStatus() != 0).mapToLong(ReadyHomeworkBean::getHomeworkDtlId).count();
				int noSubmitNum = (int) readyHomeBean.stream().filter(r -> r.getSubmitStatus() == 0).mapToLong(ReadyHomeworkBean::getHomeworkDtlId).count();
				if (readyHomeworkNum == submitNum) {
					allReady[0]++;
				} else if (readyHomeworkNum == noSubmitNum) {
					noReady[0]++;
				} else {
					partReady[0]++;
				}

			});

			ReadyStatusBean readyStatusBean = new ReadyStatusBean();
			readyStatusBean.setStudentId(studentId);
			readyStatusBean.setStudentName(UserBaseContext.getUserBaseByUserId(studentId).getUserName());
			Clazz clazz = klassRemoteService.getDeptClazzByStudentId(studentId);
			if (clazz != null) {
				readyStatusBean.setClassId(clazz.getClassId());
				readyStatusBean.setClassName(clazz.getClassName());
			}
			readyStatusBean.setReadyLessonNum(readyLessonNum);
			readyStatusBean.setAllReady(allReady[0]);
			readyStatusBean.setNoReady(noReady[0]);
			readyStatusBean.setPartReady(partReady[0]);
			readyStatusBean.setAllReadyPro(readyLessonNum > 0 ? commService.handlerPoint(((double) allReady[0] / readyLessonNum) * 100) : 0);
			readyStatusBean.setNoReadyPro(readyLessonNum > 0 ? commService.handlerPoint(((double) noReady[0] / readyLessonNum) * 100) : 0);
			readyStatusBean.setPartReadyPro(readyLessonNum > 0 ? commService.handlerPoint(((double) partReady[0] / readyLessonNum) * 100) : 0);
			readyStatusBean.setStartDate(vo.getStartDate());
			readyStatusBean.setEndDate(vo.getEndDate());
			readyStatusBeanList.add(readyStatusBean);

		});

		return readyStatusBeanList;
	}

	/**
	 * 查看预习详情数据
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<StuReadyDetailBean> queryStuReadyDetailData(RequestVo vo, Page page) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);

		//根据studentId查该生有预习的课堂
		List<Long> singleIdList = readyAnalyseDao.querySingleIdByStudentId(vo.getSchoolId(), vo.getStudentId(), vo.getStartDate(), vo.getEndDate(), vo.getSubjectId());
		int readyLessonNum = singleIdList.size();     //该学生总共应该预习的课堂数

		List<StuReadyDetailBean> resultList = new ArrayList<>();
		final int[] allReady = {0}, noReady = {0}, partReady = {0};
		singleIdList.forEach(singleId -> {
			//查该生该课堂有哪些预习作业
			List<ReadyHomeworkBean> readyHomeworkBeanList = readyAnalyseDao.queryReadyHomeworkBySingleId(vo.getSchoolId(), singleId, vo.getStudentId(), vo.getStartDate(), vo.getEndDate(), vo.getSubjectId());

			int readyHomeworkNum = readyHomeworkBeanList.size();    //该学生该堂课应该要预习的作业数

			//该生该课堂提交的作业和作业类型
			Map<Long, Integer> submitMap = new HashMap<>();
			//该生该课堂未提交的作业和作业类型
			Map<Long, Integer> noSubmitMap = new HashMap<>();
			for (ReadyHomeworkBean readyHomeworkBean : readyHomeworkBeanList) {
				Integer resType = readyHomeworkBean.getResType();
				Integer submitStatus = readyHomeworkBean.getSubmitStatus();
				if (submitStatus != 0) {
					submitMap.put(readyHomeworkBean.getHomeworkDtlId(), resType);
				} else {
					noSubmitMap.put(readyHomeworkBean.getHomeworkDtlId(), resType);
				}
			}

			//提交状态 0：未提交，1：正常提交，2：延迟提交
			int submitNum = submitMap.size();
			int noSubmitNum = noSubmitMap.size();
			int courseNum1 = 0, microNum1 = 0, homeworkNum1 = 0;
			int courseNum2 = 0, microNum2 = 0, homeworkNum2 = 0;
			for (Map.Entry<Long, Integer> entry : submitMap.entrySet()) {
				Integer resType = entry.getValue(); //1：课件 2：微课 3：作业
				if (resType == 1) {
					courseNum1++;
				} else if (resType == 2) {
					microNum1++;
				} else {
					homeworkNum1++;
				}
			}
			for (Map.Entry<Long, Integer> entry : noSubmitMap.entrySet()) {
				Integer resType = entry.getValue(); //1：课件 2：微课 3：作业
				if (resType == 1) {
					courseNum2++;
				} else if (resType == 2) {
					microNum2++;
				} else {
					homeworkNum2++;
				}
			}
			StuReadyDetailBean stuReadyDetailBean = new StuReadyDetailBean();
			stuReadyDetailBean.setSingleId(singleId);
			stuReadyDetailBean.setSingleName(lessonRemoteService.getLessonVMByLessonId(singleId).getCourseSingleName());
			stuReadyDetailBean.setTeacherId(readyHomeworkBeanList.get(0).getTeacherId());
			stuReadyDetailBean.setTeacherName(readyHomeworkBeanList.get(0).getTeacherName());
 			Long subjectId = readyHomeworkBeanList.get(0).getSubjectId();
			stuReadyDetailBean.setSubjectId(subjectId);
			stuReadyDetailBean.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());

			if (readyHomeworkNum == submitNum) {
				stuReadyDetailBean.setReadyStatus(1);
				if (courseNum1 == 0) {
					stuReadyDetailBean.setCourse("-");
				} else {
					stuReadyDetailBean.setCourse("√");
				}
				if (microNum1 == 0) {
					stuReadyDetailBean.setMicro("-");
				} else {
					stuReadyDetailBean.setMicro("√");
				}
				if (homeworkNum1 == 0) {
					stuReadyDetailBean.setHomework("-");
				} else {
					stuReadyDetailBean.setHomework("√");
				}

			} else if (readyHomeworkNum == noSubmitNum) {
				stuReadyDetailBean.setReadyStatus(3);
				if (courseNum2 == 0) {
					stuReadyDetailBean.setCourse("-");
				} else {
					stuReadyDetailBean.setCourse("×");
				}
				if (microNum2 == 0) {
					stuReadyDetailBean.setMicro("-");
				} else {
					stuReadyDetailBean.setMicro("×");
				}
				if (homeworkNum2 == 0) {
					stuReadyDetailBean.setHomework("-");
				} else {
					stuReadyDetailBean.setHomework("×");
				}

			} else {
				stuReadyDetailBean.setReadyStatus(2);
				if (courseNum1 == 0 && courseNum2 == 0) {
					stuReadyDetailBean.setCourse("-");
				} else if (courseNum1 != 0 && courseNum2 == 0) {
					stuReadyDetailBean.setCourse("√");
				} else {
					stuReadyDetailBean.setCourse("×");
				}
				if (microNum1 == 0 && microNum2 == 0) {
					stuReadyDetailBean.setMicro("-");
				} else if (microNum1 != 0 && microNum2 == 0) {
					stuReadyDetailBean.setMicro("√");
				} else {
					stuReadyDetailBean.setMicro("×");
				}
				if (homeworkNum1 == 0 && homeworkNum2 == 0) {
					stuReadyDetailBean.setHomework("-");
				} else if (homeworkNum1 != 0 && homeworkNum2 == 0) {
					stuReadyDetailBean.setHomework("√");
				} else {
					stuReadyDetailBean.setHomework("×");
				}

			}
			resultList.add(stuReadyDetailBean);

		});

		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("readyStatus");
			vo.setOrderType("desc");
		}
		page.setTotalSize(resultList.size());
		return commService.handleOrderData(vo, resultList, StuReadyDetailBean.class).stream().skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize())
				.collect(Collectors.toList());

	}
}
