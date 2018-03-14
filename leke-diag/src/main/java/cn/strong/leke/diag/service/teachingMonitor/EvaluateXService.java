package cn.strong.leke.diag.service.teachingMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.teachingMonitor.EvaluateXDao;
import cn.strong.leke.diag.model.teachingMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.*;
import cn.strong.leke.diag.mongo.teachingMonitor.InteractMongoDao;
import cn.strong.leke.diag.util.SetRankUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-18 11:36:49
 */
@Service
public class EvaluateXService {

	@Resource
	private EvaluateXDao evaluateXDao;
	@Resource
	private InteractMongoDao interactMongoDao;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private CommService commService;

	/**
	 * 根据筛选条件查询课堂评价的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ReturnResultBean queryEvaluatePageDatas(RequestVo vo) throws Exception {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		ReturnResultBean resultBean = new ReturnResultBean();
		//所有的评价数据
		List<EvaluateBean> evaluateBeanList = evaluateXDao.selectEvaluateData(vo);
		//按课堂分组
		Map<Long, List<EvaluateBean>> collectListBySingleId = evaluateBeanList.stream().collect(Collectors.groupingBy(EvaluateBean::getCourseSingleId));
		//总课堂数
		int totalLessonNum = collectListBySingleId.size();
		List<Long> courseSingleIds = new ArrayList<>();
		//课堂对应的list数据
		List<EvaluateBean> beanList = new ArrayList<>();
		collectListBySingleId.forEach((singleId, evBeanList) -> {
			courseSingleIds.add(singleId);
			double professionalLevel = 0, rhythmLevel = 0, interactionLevel = 0;
			double good = 0, center = 0, poor = 0;
			Long teacherId = null;
			String teacherName = null;
			Long gradeId = null;
			String gradeName = null;
			Long subjectId = null;
			String subjectName = null;
			Long classId = null;
			String className = null;
			for (EvaluateBean bean : evBeanList) {
				int summary = bean.getSummary();
				if (summary == 2) {
					good++;
				} else if (summary == 1) {
					center++;
				} else {
					poor++;
				}
				teacherId = bean.getTeacherId();
				teacherName = bean.getTeacherName();
				gradeId = bean.getGradeId();
				gradeName = bean.getGradeName();
				subjectId = bean.getSubjectId();
				subjectName = bean.getSubjectName();
				classId = bean.getClassId();
				className = bean.getClassName();

				professionalLevel = professionalLevel + bean.getProfessionalLevel();
				rhythmLevel = rhythmLevel + bean.getRhythmLevel();
				interactionLevel = interactionLevel + bean.getInteractionLevel();
			}
			int evaSumToLesson = evBeanList.size();
			double avgProfessional = 0, avgRhythm = 0, avgInteraction = 0, avgTotal = 0, goodPro = 0, centerPro = 0, poorPro = 0;
			if (evaSumToLesson > 0) {
				avgProfessional = professionalLevel / evaSumToLesson;
				avgRhythm = rhythmLevel / evaSumToLesson;
				avgInteraction = interactionLevel / evaSumToLesson;
				avgTotal = (professionalLevel + rhythmLevel + interactionLevel) / (evaSumToLesson * 3);
				goodPro = good / evaSumToLesson;
				centerPro = center / evaSumToLesson;
				poorPro = poor / evaSumToLesson;
			}
			EvaluateBean bean = new EvaluateBean();
			bean.setCourseSingleId(singleId);
			bean.setTeacherId(teacherId);
			bean.setTeacherName(teacherName);
			bean.setGradeId(gradeId);
			bean.setGradeName(gradeName);
			bean.setSubjectId(subjectId);
			bean.setSubjectName(subjectName);
			bean.setClassId(classId);
			bean.setClassName(className);
			bean.setProfessionalLevel(avgProfessional);
			bean.setRhythmLevel(avgRhythm);
			bean.setInteractionLevel(avgInteraction);
			bean.setTotal(avgTotal);
			bean.setGoodPro(goodPro);
			bean.setCenterPro(centerPro);
			bean.setPoorPro(poorPro);
			beanList.add(bean);
		});

		//平均每堂对应的“教学效果”、“教学态度”、“课堂互动”评分等...
		double avgProfessional = 0, avgRhythmLevel = 0, avgInteractionLevel = 0, avgTotal = 0, avgGoodPro = 0, avgCenterPro = 0, avgPoorPro = 0;
		if (beanList.size() > 0) {
			double professionalLevel = 0, rhythmLevel = 0, interactionLevel = 0, total = 0, goodPro = 0, centerPro = 0, poorPro = 0;
			for (EvaluateBean bean : beanList) {
				professionalLevel = professionalLevel + bean.getProfessionalLevel();
				rhythmLevel = rhythmLevel + bean.getRhythmLevel();
				interactionLevel = interactionLevel + bean.getInteractionLevel();
				total = total + bean.getTotal();
				goodPro = goodPro + bean.getGoodPro();
				centerPro = centerPro + bean.getCenterPro();
				poorPro = poorPro + bean.getPoorPro();
			}

			avgProfessional = professionalLevel / totalLessonNum;
			avgRhythmLevel = rhythmLevel / totalLessonNum;
			avgInteractionLevel = interactionLevel / totalLessonNum;
			avgTotal = total / totalLessonNum;
			avgGoodPro = goodPro / totalLessonNum;
			avgCenterPro = centerPro / totalLessonNum;
			avgPoorPro = poorPro / totalLessonNum;

		}

		//根据courseSingleIds查鲜花数
		int flowerTotal = interactMongoDao.queryFlowerNumBySingleIds(vo, courseSingleIds);
		//平均每堂课的鲜花数
		double avgFlowerNum = totalLessonNum > 0 ? (double) flowerTotal / totalLessonNum : 0;

		//统计
		EvaluateCountBean countBean = new EvaluateCountBean();
		countBean.setTotalLessionNum(totalLessonNum);
		countBean.setAvgProfessional(handlerPoint(avgProfessional));
		countBean.setAvgProfessionalPro(avgProfessional * 100 / 5);
		countBean.setAvgRhythmLevel(handlerPoint(avgRhythmLevel));
		countBean.setAvgRhythmLevelPro(avgRhythmLevel * 100 / 5);
		countBean.setAvgInteractionLevel(handlerPoint(avgInteractionLevel));
		countBean.setAvgInteractionLevelPro(avgInteractionLevel * 100 / 5);
		countBean.setAvgTotal(handlerPoint(avgTotal));
		countBean.setAvgTotalPro(avgTotal * 100 / 5);
		countBean.setAvgGoodPro(handlerPoint(avgGoodPro * 100));
		countBean.setAvgCenterPro(avgCenterPro * 100);
		countBean.setAvgPoorPro(avgPoorPro * 100);
		countBean.setAvgFlowerNum(handlerPoint(avgFlowerNum));
		resultBean.setCountBean(countBean);

		//走势 avgTotalCount
		List<EvaluateTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的日
			List<CommProp> dayList = commService.findBeikeRateByDay(vo);
			//根据日查出对应的走势数据
			trendList = getTrendData(evaluateBeanList, dayList);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的周
			List<CommProp> weebList = commService.findBeikeRateByWeek(vo);
			//根据周查出对应的走势数据
			trendList = getTrendData(evaluateBeanList, weebList);
		} else {
			//查出所有的月
			List<CommProp> monthList = commService.findBeikeRateByMonth(vo);
			//根据月查出对应的走势数据
			trendList = getTrendData(evaluateBeanList, monthList);
		}
		resultBean.setTrendList(trendList);

		//对比
		//年级学科班级对比
		List<EvaluateCompareBean> compareBeanList = getCompareData(vo, beanList);
		resultBean.setCompareBeanList(compareBeanList);

		//排行,课堂的list数据根据老师分组
		List<EvaluateRankBean> rankBeanList = new ArrayList<>();
		Map<Long, List<EvaluateBean>> collect = beanList.stream().collect(Collectors.groupingBy(EvaluateBean::getTeacherId));
		collect.forEach((teacherId, evaBeanList) -> {
			EvaluateRankBean rankBean = new EvaluateRankBean();
			rankBean.setTeacherId(teacherId);
			rankBean.setTeacherName(UserBaseContext.getUserBaseByUserId(teacherId).getUserName());
			double total = 0;
			int lessonNum = evaBeanList.size();
			for (EvaluateBean bean : evaBeanList) {
				total = total + bean.getTotal();
			}

			double avgTotal1 = lessonNum == 0 ? 0 : total / lessonNum;
			rankBean.setTotalLevel(BigDecimal.valueOf(handlerPoint(avgTotal1)));
			rankBeanList.add(rankBean);
		});
		List<EvaluateRankBean> collectList = rankBeanList.stream().sorted(Comparator.comparing(EvaluateRankBean::getTotalLevel).reversed()).collect(Collectors.toList());
		RankResultBean rankData = (new SetRankUtils<EvaluateRankBean>()).getRankData(collectList, "BigDecimal");
		resultBean.setRankFrontBeanList(rankData.getRankFrontBeanList());
		resultBean.setRankBackBeanList(rankData.getRankBackBeanList());
		return resultBean;
	}


	/**
	 * 获取走势数据
	 * @param evaluateBeanList
	 * @param commList
	 * @return
	 * @throws ParseException
	 */
	private List<EvaluateTrendBean> getTrendData(List<EvaluateBean> evaluateBeanList, List<CommProp> commList) throws ParseException {
		List<EvaluateTrendBean> trendList = new ArrayList<>();
		for (CommProp comm : commList) {
			Date startDate = DateUtils.parseDate(comm.getStartDate());
			Date endDate = DateUtils.addDays(DateUtils.parseDate(comm.getEndDate()), 1);
			EvaluateTrendBean evaluateTrendBean = new EvaluateTrendBean();
			evaluateTrendBean.setStartDate(comm.getStartDate());
			evaluateTrendBean.setEndDate(DateUtils.formatDate(endDate));
			evaluateTrendBean.setDateStr(comm.getDateStr());

			Map<Long, List<EvaluateBean>> collect = evaluateBeanList.stream().filter(e -> e.getStartTime().getTime() >= startDate.getTime() && e.getEndTime().getTime() < endDate.getTime())
					.collect(Collectors.groupingBy(EvaluateBean::getCourseSingleId));
			int totalLessonNum = collect.size();
			//课堂对应的list数据
			List<EvaluateBean> beanList = new ArrayList<>();
			collect.forEach((singleId, evaluateList) -> {
				double professionalLevel = 0, rhythmLevel = 0, interactionLevel = 0;
				for (EvaluateBean bean : evaluateList) {
					professionalLevel = professionalLevel + bean.getProfessionalLevel();
					rhythmLevel = rhythmLevel + bean.getRhythmLevel();
					interactionLevel = interactionLevel + bean.getInteractionLevel();
				}

				int evaSumToLesson = evaluateList.size();
				double avgTotal = 0;
				if (evaSumToLesson > 0) {
					avgTotal = (professionalLevel + rhythmLevel + interactionLevel) / (evaSumToLesson * 3);
				}
				EvaluateBean bean = new EvaluateBean();
				bean.setTotal(avgTotal);
				beanList.add(bean);

			});

			//平均每堂对应的评分
			double avgTotal = 0;
			if (beanList.size() > 0) {
				double total = 0;
				for (EvaluateBean bean : beanList) {
					total = total + bean.getTotal();
				}
				avgTotal = total / totalLessonNum;
			}
			evaluateTrendBean.setAvgTotal(handlerPoint(avgTotal));
			trendList.add(evaluateTrendBean);
		}

		return trendList;
	}


	/**
	 * 获取对比数据
	 * @param beanList
	 * @return
	 */
	private List<EvaluateCompareBean> getCompareData(RequestVo vo, List<EvaluateBean> beanList) {
		List<EvaluateCompareBean> compareBeanList = new ArrayList<>();
		if (RequestVo.GRADE.equalsIgnoreCase(vo.getCompType())) {
			List<EvaluateCompareBean> gradeCompareList = new ArrayList<>();
			//根据schoolId（当前登录用户）查所有的gradeIds
			List<GradeRemote> gradeList = commService.findGradeOfSchool();
			//遍历gradeIds,根据gradeId查该年级对应的teacherIds
			gradeList.forEach(g -> {
				//获得年级对应的数据
				Long gradeId = g.getGradeId();
				//根据gradeId查得年级对应的课堂评分
				double total = 0;
				int lessonNum = 0;
				for (EvaluateBean bean : beanList) {
					if (bean.getGradeId().equals(gradeId)) {
						total = total + bean.getTotal();
						lessonNum++;
					}
				}

				double avgTotal = lessonNum == 0 ? 0 : total / lessonNum;
				EvaluateCompareBean compareBean = new EvaluateCompareBean();
				compareBean.setGradeId(gradeId);
				compareBean.setGradeName(g.getGradeName());
				compareBean.setAvgTotal(handlerPoint(avgTotal));
				gradeCompareList.add(compareBean);

			});
			compareBeanList = gradeCompareList;

		} else if (RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType()) || RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())) {
			Long subjectId = vo.getSubjectId();
			if (subjectId != null) {//有学科
				//获得学科对应的数据
				EvaluateCompareBean compareBean = new EvaluateCompareBean();
				compareBean.setSubjectId(subjectId);
				compareBean.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());
				double total = 0;
				int lessonNum = 0;
				for (EvaluateBean bean : beanList) {
					if (bean.getSubjectId().equals(subjectId)) {
						total = total + bean.getTotal();
						lessonNum++;
					}
				}
				double avgTotal = lessonNum == 0 ? 0 : total / lessonNum;
				compareBean.setAvgTotal(handlerPoint(avgTotal));
				compareBeanList.add(compareBean);
			} else {//无学科
				//根据schoolId查该校所有的subjectIds
				List<SubjectRemote> subjectRemoteList = commService.findSubjectOfSchool();
				List<EvaluateCompareBean> subjectCompareList = new ArrayList<>();
				//遍历subjectIds，根据subjectId查对应学科的teacherIds
				subjectRemoteList.forEach(s -> {
					Long subjectId1 = s.getSubjectId();
					EvaluateCompareBean compareBean = new EvaluateCompareBean();
					compareBean.setSubjectId(subjectId1);
					compareBean.setSubjectName(s.getSubjectName());
					double total = 0;
					int lessonNum = 0;
					for (EvaluateBean bean : beanList) {
						if (bean.getSubjectId().equals(subjectId1)) {
							total = total + bean.getTotal();
							lessonNum++;
						}
					}
					double avgTotal = lessonNum == 0 ? 0 : total / lessonNum;
					compareBean.setAvgTotal(handlerPoint(avgTotal));
					subjectCompareList.add(compareBean);

				});

				compareBeanList = subjectCompareList;
			}

		} else if (RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())) {//筛选条件“有年级”,学科不一定
			List<EvaluateCompareBean> classCompareList = new ArrayList<>();
			/*//根据schoolId、gradeId查出所有的classIds
			ClazzQuery clazzQuery = new ClazzQuery();
			clazzQuery.setSchoolId(vo.getSchoolId());
			clazzQuery.setGradeId(vo.getGradeId());
			*//*clazzQuery.setType(1);*//*
			List<Clazz> clazzByQuery = klassRemoteService.findClazzByQuery(clazzQuery);*/

			//查询上过课的classId（根据schoolId、gradeId、subjectId、startDate、endDate）
			List<ClassBean> classList = commService.queryIsLessonClazz(vo);

			//遍历classIds,根据classId查该班级的teacherIds
			classList.forEach(c -> {
				//获得班级对应的数据
				Long classId = c.getClassId();
				EvaluateCompareBean compareBean = new EvaluateCompareBean();
				compareBean.setClassId(classId);
				compareBean.setClassName(c.getClassName());
				double total = 0;
				int lessonNum = 0;
				for (EvaluateBean bean : beanList) {
					if (bean.getClassId().equals(classId)) {
						total = total + bean.getTotal();
						lessonNum++;
					}
				}
				double avgTotal = lessonNum == 0 ? 0 : total / lessonNum;
				compareBean.setAvgTotal(handlerPoint(avgTotal));
				classCompareList.add(compareBean);
			});
			compareBeanList = classCompareList.stream().sorted(new CommService.CnComparator<EvaluateCompareBean>(true, true, "className", EvaluateCompareBean.class)).collect(Collectors.toList());
		}

		return compareBeanList;
	}


	/**
	 * 四舍五入保留小数点后一位
	 * @param param
	 * @return
	 */
	private double handlerPoint(double param) {
		return new BigDecimal(param).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * 根据筛选条件查询课堂评价的明细数据list
	 * @param vo
	 * @return
	 */
	public List<EvaluateDetailBean> queryEvaluateDetailPartData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("lessonNum");
			vo.setOrderType("desc");
		}
		List<EvaluateDetailBean> evaluateDetailBeanList = evaluateXDao.selectEvaluateDetailPartData(vo);
		evaluateDetailBeanList.forEach(d -> {
			Long teacherId = d.getTeacherId();
			Long subjectId = d.getSubjectId();
			//根据teacherId、subjectId、start、end查鲜花数
			int flowerNum = interactMongoDao.queryEvaBeanByTeacherIds(teacherId, subjectId, DateUtils.parseDate(vo.getStartDate()), DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1));
			d.setFlowerNum(flowerNum);
		});
		return commService.handleOrderData(vo, evaluateDetailBeanList, EvaluateDetailBean.class);
	}


	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<EvaluateDetailBean> queryEvaluateDetailDataPage(RequestVo vo, Page page) {
		List<EvaluateDetailBean> evaluateDetailBeanList = queryEvaluateDetailPartData(vo);
		page.setTotalSize(evaluateDetailBeanList.size());
		evaluateDetailBeanList = commService.handleOrderData(vo, evaluateDetailBeanList, EvaluateDetailBean.class).stream()
				.skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());

		return evaluateDetailBeanList;

		/*List<EvaluateDetailBean> evaluateDetailBeanList = handlerDetailData(vo);
		page.setTotalSize(evaluateDetailBeanList.size());
		evaluateDetailBeanList = commService.handleOrderData(vo, evaluateDetailBeanList, EvaluateDetailBean.class).stream()
				.skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());
		return evaluateDetailBeanList;*/
	}


	/**
	 * java处理详情数据
	 * @param vo
	 * @return
	 */
	private List<EvaluateDetailBean> handlerDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		List<EvaluateDetailBean> evaluateDetailBeanList = new ArrayList<>();
		//所有的评价数据
		List<EvaluateBean> evaluateBeanList = evaluateXDao.selectEvaluateData(vo);
		Map<Long, Map<Long, List<EvaluateBean>>> collect = evaluateBeanList.stream().collect(Collectors.groupingBy(EvaluateBean::getTeacherId, Collectors.groupingBy(EvaluateBean::getSubjectId)));
		collect.forEach((teacherId, teaMap) -> {
			teaMap.forEach((subjectId, subList) -> {
				double professionalLevel = 0, rhythmLevel = 0, interactionLevel = 0;
				double good = 0, center = 0, poor = 0;
				for (EvaluateBean bean : subList) {
					int summary = bean.getSummary();
					if (summary == 2) {
						good++;
					} else if (summary == 1) {
						center++;
					} else {
						poor++;
					}
					professionalLevel = professionalLevel + bean.getProfessionalLevel();
					rhythmLevel = rhythmLevel + bean.getRhythmLevel();
					interactionLevel = interactionLevel + bean.getInteractionLevel();

				}
				int evaSumToLesson = subList.size(); //对应的上课堂数
				double avgProfessional = 0, avgRhythm = 0, avgInteraction = 0, avgTotal = 0, goodPro = 0, centerPro = 0, poorPro = 0;
				if (evaSumToLesson > 0) {
					avgProfessional = professionalLevel / evaSumToLesson;
					avgRhythm = rhythmLevel / evaSumToLesson;
					avgInteraction = interactionLevel / evaSumToLesson;
					avgTotal = (professionalLevel + rhythmLevel + interactionLevel) / (evaSumToLesson * 3);
					goodPro = good / evaSumToLesson;
					centerPro = center / evaSumToLesson;
					poorPro = poor / evaSumToLesson;
				}
				int flowerNum = interactMongoDao.queryEvaBeanByTeacherIds(teacherId, subjectId, DateUtils.parseDate(vo.getStartDate()), DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1));
				EvaluateDetailBean detailBean = new EvaluateDetailBean();
				detailBean.setTeacherId(teacherId);
				detailBean.setTeacherName(UserBaseContext.getUserBaseByUserId(teacherId).getUserName());
				detailBean.setSubjectId(subjectId);
				detailBean.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());
				detailBean.setLessonNum(evaSumToLesson);
				detailBean.setTotalLevel(BigDecimal.valueOf(avgTotal));
				detailBean.setGood((int) good);
				detailBean.setGoodPro(goodPro);
				detailBean.setCenter((int) center);
				detailBean.setCenterPro(centerPro);
				detailBean.setPoor((int) poor);
				detailBean.setPoorPro(poorPro);
				detailBean.setFlowerNum(flowerNum);
				evaluateDetailBeanList.add(detailBean);
			});
		});
		return evaluateDetailBeanList;
	}


	/**
	 * 根据筛选条件查询课堂评价的明细数据
	 * @param vo
	 * @return
	 */
	/*public List<EvaluateDetailBean> queryEvaluateDetailData(RequestVo vo) {
		List<EvaluateDetailBean> evaluateDetailBeanList = queryEvaluateDetailPartData(vo);
		List<EvaluateDetailBean> collectList = evaluateDetailBeanList.stream().sorted(Comparator.comparing(EvaluateDetailBean::getLessonNum).reversed()).collect(Collectors.toList());
		final int[] i = {1};
		collectList.forEach(e -> {
			e.setIndex(i[0]++);
		});
		return collectList;
	}*/

	/**
	 * 根据筛选条件查评价率及详细数据
	 * @param vo schoolId:必须
	 *           gradeId:可选
	 *           subjectId:可选
	 *           teacherName:可选（模糊匹配）
	 *           startDate:必须
	 *           endDate:必须
	 * @return
	 */
	public List<EvaluateDetailBean> queryEvaluateAndDetail(RequestVo vo) {
		return queryEvaluateDetailPartData1(vo);
	}

	/**
	 * 只给统计总表调用
	 * @param vo
	 * @return
	 */
	private List<EvaluateDetailBean> queryEvaluateDetailPartData1(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		List<EvaluateDetailBean> evaluateDetailBeanList = evaluateXDao.selectEvaluateDetailPartData(vo);
		evaluateDetailBeanList.forEach(d -> {
			Long teacherId = d.getTeacherId();
			Long subjectId = d.getSubjectId();
			//根据teacherId、subjectId、start、end查鲜花数
			int flowerNum = interactMongoDao.queryEvaBeanByTeacherIds(teacherId, subjectId, DateUtils.parseDate(vo.getStartDate()), DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1));
			d.setFlowerNum(flowerNum);
		});
		return evaluateDetailBeanList;
	}
}
