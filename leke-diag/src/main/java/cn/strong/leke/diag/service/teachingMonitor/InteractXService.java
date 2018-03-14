package cn.strong.leke.diag.service.teachingMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.diag.dao.teachingMonitor.InteractXDao;
import cn.strong.leke.diag.model.teachingMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.QueryEvaluate;
import cn.strong.leke.diag.model.teachingMonitor.interact.*;
import cn.strong.leke.diag.mongo.teachingMonitor.InteractMongoDao;
import cn.strong.leke.diag.util.SetRankUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.lessonlog.model.LessonBehavior;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 16:01:15
 */
@Service
public class InteractXService {

	@Resource
	private InteractXDao interactXDao;
	@Resource
	private InteractMongoDao interactMongoDao;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private CommService commService;


	/**
	 * 根据筛选条件查询课堂互动的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryInteractPageData(RequestVo vo) throws ParseException {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		ReturnResultBean resultBean = new ReturnResultBean();
		List<LessonBehavior> behaviorList = getLessonBehaviorList(vo);

		//-------------------------------------------------------------统计部分(start)-----------------------------------------------------------
		InteractCountBean countBean = getCountBean(behaviorList);
		resultBean.setCountBean(countBean);

		//-------------------------------------------------------------走势部分(start)-----------------------------------------------------------
		ReturnResultBean trendResultBean = queryInteractTrendData(vo, behaviorList);
		resultBean.setTrendList((List<InteractTrendBean>) trendResultBean.getTrendList());

		//-------------------------------------------------------------对比部分(start)-----------------------------------------------------------
		//年级学科班级对比
		List<InteractCompareBean> compareBeanList = getCompareData(vo);
		resultBean.setCompareBeanList(compareBeanList);

		//-------------------------------------------------------------排名部分(start)-----------------------------------------------------------
		ReturnResultBean rankData = getRankData(behaviorList);
		resultBean.setRankFrontBeanList(rankData.getRankFrontBeanList());
		resultBean.setRankBackBeanList(rankData.getRankBackBeanList());

		return resultBean;
	}

	/**
	 * 根据条件查得Behavior列表数据
	 * @param vo
	 * @return
	 */
	private List<LessonBehavior> getLessonBehaviorList(RequestVo vo) {
		//根据schoolId、gradeId查courseSingleIds
		List<Long> courseSingleIds = interactXDao.selectSingleIdsBySchoolIdGradeId(vo);

		//根据courseSingleIds、subjectId(有无不定)、start、end查behaviorList
		return interactMongoDao.queryLessonBehaviorBySingleIds(vo, courseSingleIds);
	}

	/**
	 * 获取统计数据
	 * @param behaviorList
	 * @return
	 */
	private InteractCountBean getCountBean(List<LessonBehavior> behaviorList) {
		//实上课堂数
		int realLessonNum = behaviorList.size();

		//老师数
		int teacherNum = behaviorList.stream().collect(Collectors.groupingBy(LessonBehavior::getTeacherId)).size();

		int realLessonTeaNum = realLessonNum * teacherNum;

		InteractBean interactBean = getInteractBean(behaviorList);
		int callNum = interactBean.getCallNum();
		int quickNum = interactBean.getQuickNum();
		int examNum = interactBean.getExamNum();
		int discuNum = interactBean.getDiscuNum();
		int authedNum = interactBean.getAuthedNum();

		//共发起课堂互动数
		int lessonInteractNum = callNum + quickNum + examNum + discuNum + authedNum;
		InteractCountBean countBean = new InteractCountBean();
		countBean.setTeacherNum(teacherNum);
		countBean.setRealLessonNum(realLessonNum);
		countBean.setLessonInteractNum(lessonInteractNum);
		//平均每师每堂互动数

		countBean.setAvgTeaLesson(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) lessonInteractNum / realLessonTeaNum)));
		countBean.setCallNum(callNum);
		//每师每堂点名次数
		countBean.setAvgCallNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) callNum / realLessonTeaNum)));
		countBean.setQuickNum(quickNum);
		//每师每堂快速问答次数
		countBean.setAvgQuickNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) quickNum / realLessonTeaNum)));
		countBean.setExamNum(examNum);
		//每师每堂随堂作业次数
		countBean.setAvgExamNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) examNum / realLessonTeaNum)));
		countBean.setDiscuNum(discuNum);
		//每师每堂分组讨论次数
		countBean.setAvgDiscuNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) discuNum / realLessonTeaNum)));
		countBean.setAuthedNum(authedNum);
		//每师每堂授权次数
		countBean.setAvgAuthedNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) authedNum / realLessonTeaNum)));
		return countBean;
	}


	/**
	 * 根据条件筛选互动走势数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryInteractTrendData(RequestVo vo, List<LessonBehavior> behaviorList) throws ParseException {
		if (behaviorList == null) {
			behaviorList = getLessonBehaviorList(vo);
		}
		ReturnResultBean resultBean = new ReturnResultBean();
		List<InteractTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的日
			List<CommProp> dayList = commService.findBeikeRateByDay(vo);
			//根据日查出对应的走势数据
			trendList = getTrendData(behaviorList, dayList);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的周
			List<CommProp> weebList = commService.findBeikeRateByWeek(vo);
			//根据周查出对应的走势数据
			trendList = getTrendData(behaviorList, weebList);
		} else {
			//查出所有的月
			List<CommProp> monthList = commService.findBeikeRateByMonth(vo);
			//根据月查出对应的走势数据
			trendList = getTrendData(behaviorList, monthList);
		}
		resultBean.setTrendList(trendList);
		return resultBean;
	}

	/**
	 * 根据日周月List查出对应的走势数据
	 * @param behaviorList
	 * @param commList
	 * @return
	 */
	private List<InteractTrendBean> getTrendData(List<LessonBehavior> behaviorList, List<CommProp> commList) throws ParseException {
		List<InteractTrendBean> trendList = new ArrayList<>();
		for (CommProp comm : commList) {
			Date startDate =  DateUtils.parseDate(comm.getStartDate());
			Date endDate = DateUtils.addDays( DateUtils.parseDate(comm.getEndDate()), 1);
			int callNum = 0, quickNum = 0, examNum = 0, discuNum = 0, authedNum = 0;
			int lessonCount = 0; //课堂数
			Set<Long> set = new HashSet<>();
			for (LessonBehavior behavior : behaviorList) {
				Date start = behavior.getStart();
				Date end = behavior.getEnd();
				if (start.getTime() >= startDate.getTime() && end.getTime() < endDate.getTime()) {
					callNum = callNum + (behavior.getCallNum() == null ? 0 : behavior.getCallNum());
					quickNum = quickNum + (behavior.getQuickNum() == null ? 0 : behavior.getQuickNum());
					examNum = examNum + (behavior.getExamNum() == null ? 0 : behavior.getExamNum());
					discuNum = discuNum + (behavior.getDiscuNum() == null ? 0 : behavior.getDiscuNum());
					authedNum = authedNum + (behavior.getAuthedNum() == null ? 0 : behavior.getAuthedNum());
					lessonCount++;
					set.add(behavior.getTeacherId());
				}
			}
			int teacherCount = set.size();
			int realLessonTeaNum = lessonCount * teacherCount;
			InteractTrendBean trendBean = new InteractTrendBean();
			trendBean.setStartDate(comm.getStartDate());
			trendBean.setEndDate(DateUtils.formatDate(endDate));
			trendBean.setDateStr(comm.getDateStr());
			trendBean.setTotalNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) (callNum + quickNum + examNum + discuNum + authedNum) / realLessonTeaNum)));
			trendBean.setCallNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) callNum / realLessonTeaNum)));
			trendBean.setQuickNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) quickNum / realLessonTeaNum)));
			trendBean.setExamNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) examNum / realLessonTeaNum)));
			trendBean.setDiscuNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) discuNum / realLessonTeaNum)));
			trendBean.setAuthedNum(BigDecimal.valueOf(realLessonTeaNum == 0 ? 0 : handlerPoint((double) authedNum / realLessonTeaNum)));
			trendList.add(trendBean);
		}
		return trendList;
	}


	/**
	 * 获取对比数据
	 * @param vo
	 * @return
	 */
	private List<InteractCompareBean> getCompareData(RequestVo vo) {
		List<InteractCompareBean> compareBeanList = new ArrayList<>();
		if (RequestVo.GRADE.equalsIgnoreCase(vo.getCompType())) {
			List<InteractCompareBean> gradeCompareList = new ArrayList<>();
			//根据schoolId（当前登录用户）查所有的gradeIds
			List<GradeRemote> gradeList = commService.findGradeOfSchool();
			//遍历gradeIds,根据gradeId查该年级对应的courseSingleIds
			gradeList.forEach(g -> {
				//获得年级、学科、班级对应的数据
				InteractCompareBean compareBean = getCompareDataByGradeOrIsSubjectOrClass(vo, 1, true, true, false, g.getGradeId(), g.getGradeName());
				gradeCompareList.add(compareBean);
			});
			compareBeanList = gradeCompareList;

		} else if (RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType()) || RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())) {
			Long subjectId = vo.getSubjectId();
			if (RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType())) {//筛选条件“无年级”,学科不一定时：年级|学科（点击学科）,遍历该校所有学科
				if (subjectId != null) {//有学科
					//获得年级、学科、班级对应的数据
					InteractCompareBean compareBean = getCompareDataByGradeOrIsSubjectOrClass(vo, 2, false, true, false, subjectId, SubjectContext.getSubject(subjectId).getSubjectName());
					compareBeanList.add(compareBean);
				} else {//无学科
					compareBeanList = getNotSubjectCompareDataBySubjectId(vo, false);
				}

			} else {//筛选条件“有年级”,学科不一定时：班级|学科（点击学科）
				if (subjectId != null) {//有学科
					//获得年级、学科、班级对应的数据
					InteractCompareBean compareBean = getCompareDataByGradeOrIsSubjectOrClass(vo, 2, true, true, false, subjectId, SubjectContext.getSubject(subjectId).getSubjectName());
					compareBeanList.add(compareBean);
				} else {//无学科
					compareBeanList = getNotSubjectCompareDataBySubjectId(vo, true);
				}
			}

		} else if (RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())) {//筛选条件“有年级”,学科不一定
			List<InteractCompareBean> classCompareList = new ArrayList<>();
			/*//根据schoolId、gradeId查出所有的classIds
			ClazzQuery clazzQuery = new ClazzQuery();
			clazzQuery.setSchoolId(vo.getSchoolId());
			clazzQuery.setGradeId(vo.getGradeId());
			*//*clazzQuery.setType(1);*//*
			List<Clazz> clazzByQuery = klassRemoteService.findClazzByQuery(clazzQuery);*/

			//查询上过课的classId（根据schoolId、gradeId、subjectId、startDate、endDate）
			List<ClassBean> classList = commService.queryIsLessonClazz(vo);

			//遍历classIds,根据classId查该班级的courseSingleIds
			classList.forEach(c -> {
				//获得年级、学科、班级对应的数据
				InteractCompareBean compareBean = getCompareDataByGradeOrIsSubjectOrClass(vo, 3, true, false, true, c.getClassId(), c.getClassName());
				classCompareList.add(compareBean);
			});
			compareBeanList = classCompareList.stream().sorted(new CommService.CnComparator<InteractCompareBean>(true, true, "className", InteractCompareBean.class)).collect(Collectors.toList());
		}

		return compareBeanList;
	}

	/**
	 * //获得年级、学科、班级对应的数据
	 * @param vo
	 * @param compareType 对比类型:1.年级,2.学科,3.班级
	 * @param isGrade
	 * @param isSubject
	 * @param isClass
	 * @param Id          gradeId|subjectId|classId
	 * @param name        gradeName|subjectName|className
	 * @return
	 */
	private InteractCompareBean getCompareDataByGradeOrIsSubjectOrClass(RequestVo vo, int compareType, boolean isGrade, boolean isSubject, boolean isClass, Long Id, String name) {

		RequestVo query = new QueryEvaluate();
		query.setSchoolId(vo.getSchoolId());
		if (vo.getGradeId() != null) {
			query.setGradeId(vo.getGradeId());
		} else if (isGrade && compareType == 1) {
			query.setGradeId(Id);
		} else {
			query.setGradeId(null);
		}
		if (vo.getSubjectId() != null) {
			query.setSubjectId(vo.getSubjectId());
		} else if (isSubject && compareType == 2) {
			query.setSubjectId(Id);
		} else {
			query.setSubjectId(null);
		}

		query.setClassId(isClass && compareType == 3 ? Id : null);
		query.setStartDate(vo.getStartDate());
		query.setEndDate(vo.getEndDate());
		query.setTrendType(vo.getTrendType());
		query.setCompType(vo.getCompType());

		//根据schoolId查得该校所有的courseSingleIds
		List<Long> singleIds = interactXDao.selectSingleIdsBySchoolIdGradeId(query);
		//根据courseSingleIds、subjectId(有)、start、end查behaviorList
		List<LessonBehavior> behaviorList = interactMongoDao.queryLessonBehaviorBySingleIds(query, singleIds);
		//根据对应的compareType查对比数据
		InteractCompareBean compareBean = getInteractCompareData(behaviorList, compareType, Id, name);
		return compareBean;
	}

	/**
	 * 无学科
	 * @param vo
	 * @param isGrade
	 * @return
	 */
	private List<InteractCompareBean> getNotSubjectCompareDataBySubjectId(RequestVo vo, boolean isGrade) {
		List<InteractCompareBean> compareBeanList = new ArrayList<>();
		RequestVo query = new QueryEvaluate();
		query.setSchoolId(vo.getSchoolId());
		query.setGradeId(isGrade ? vo.getGradeId() : null);
		query.setStartDate(vo.getStartDate());
		query.setEndDate(vo.getEndDate());
		query.setTrendType(vo.getTrendType());
		query.setCompType(vo.getCompType());
		//根据schoolId和gradeId查得该校该年级所有的courseSingleIds
		List<Long> singleIds = interactXDao.selectSingleIdsBySchoolIdGradeId(query);
		//根据schoolId查该校所有的subjectIds
		List<SubjectRemote> subjectRemoteList = commService.findSubjectOfSchool();
		//遍历subjectIds，根据subjectId查对应学科的数据
		for (SubjectRemote subjectRemote : subjectRemoteList) {
			Long subId = subjectRemote.getSubjectId();
			query.setSubjectId(subId);
			//根据courseSingleIds、subjectId(有)、start、end查behaviorList
			List<LessonBehavior> behaviorList = interactMongoDao.queryLessonBehaviorBySingleIds(query, singleIds);
			//根据学科查对比数据
			InteractCompareBean compareBean = getInteractCompareData(behaviorList, 2, subId, subjectRemote.getSubjectName());
			compareBeanList.add(compareBean);
		}
		return compareBeanList;
	}


	/**
	 * 根据年级、学科、班级类型，查对应的对比数据
	 * @param behaviorList
	 * @param compareType  对比类型:1.年级,2.学科,3.班级
	 * @param Id           gradeId|subjectId|classId
	 * @param name         gradeName|subjectName|className
	 * @return
	 */
	private InteractCompareBean getInteractCompareData(List<LessonBehavior> behaviorList, int compareType, Long Id, String name) {

		InteractBean interactBean = getInteractBean(behaviorList);
		int callNum = interactBean.getCallNum();
		int quickNum = interactBean.getQuickNum();
		int examNum = interactBean.getExamNum();
		int discuNum = interactBean.getDiscuNum();
		int authedNum = interactBean.getAuthedNum();

		InteractCompareBean compareBean = new InteractCompareBean();
		if (compareType == 1) {
			compareBean.setGradeId(Id);
			compareBean.setGradeName(name);
		} else if (compareType == 2) {
			compareBean.setSubjectId(Id);
			compareBean.setSubjectName(name);
		} else {
			compareBean.setClassId(Id);
			compareBean.setClassName(name);
		}
		compareBean.setTotalNum(callNum + quickNum + examNum + discuNum + authedNum);
		compareBean.setCallNum(callNum);
		compareBean.setQuickNum(quickNum);
		compareBean.setExamNum(examNum);
		compareBean.setDiscuNum(discuNum);
		compareBean.setAuthedNum(authedNum);
		return compareBean;
	}

	/**
	 * 获取所有课堂的各个互动行为的总数
	 * @param behaviorList
	 * @return
	 */
	public InteractBean getInteractBean(List<LessonBehavior> behaviorList) {
		int callNum = 0, quickNum = 0, examNum = 0, discuNum = 0, authedNum = 0;
		for (LessonBehavior behavior : behaviorList) {
			callNum = callNum + (behavior.getCallNum() == null ? 0 : behavior.getCallNum());
			quickNum = quickNum + (behavior.getQuickNum() == null ? 0 : behavior.getQuickNum());
			examNum = examNum + (behavior.getExamNum() == null ? 0 : behavior.getExamNum());
			discuNum = discuNum + (behavior.getDiscuNum() == null ? 0 : behavior.getDiscuNum());
			authedNum = authedNum + (behavior.getAuthedNum() == null ? 0 : behavior.getAuthedNum());
		}
		InteractBean bean = new InteractBean();
		bean.setCallNum(callNum);
		bean.setQuickNum(quickNum);
		bean.setExamNum(examNum);
		bean.setDiscuNum(discuNum);
		bean.setAuthedNum(authedNum);
		return bean;
	}


	/**
	 * 获取排行数据
	 * @param behaviorList
	 * @return
	 */
	private ReturnResultBean getRankData(List<LessonBehavior> behaviorList) {
		ReturnResultBean resultBean = new ReturnResultBean();
		List<InteractRankBean> teaCountList = new ArrayList<>();    //单堂课的老师及总互动次数
		for (LessonBehavior behavior : behaviorList) {
			int callNum = behavior.getCallNum() == null ? 0 : behavior.getCallNum() ;
			int quickNum = behavior.getQuickNum() == null ? 0 : behavior.getQuickNum();
			int examNum = behavior.getExamNum() == null ? 0 : behavior.getExamNum();
			int discuNum = behavior.getDiscuNum() == null ? 0 : behavior.getDiscuNum();
			int authedNum = behavior.getAuthedNum() == null ? 0 : behavior.getAuthedNum();
			int totalNum = callNum + quickNum + examNum + discuNum + authedNum;
			InteractRankBean rankBean = new InteractRankBean();
			rankBean.setTeacherId(behavior.getTeacherId());
			rankBean.setTeacherName(behavior.getTeacherName());
			rankBean.setTotalCount(totalNum);//单堂课总互动数
			teaCountList.add(rankBean);
		}

		//根据teacherId分组，得到按老师分组后的List===>>>每个老师的信息和平均每堂课的互动次数
		Map<Long, List<InteractRankBean>> collect = teaCountList.stream().collect(Collectors.groupingBy(InteractRankBean::getTeacherId));
		List<InteractRankBean> rankBeanList = new ArrayList<>();
		collect.forEach((teacherId, lessonTeaCountInfos) -> {
			String teacherName = "";
			int avgLesson = 0;
			for (InteractRankBean info : lessonTeaCountInfos) {
				teacherName = info.getTeacherName();
				avgLesson = avgLesson + info.getTotalCount();
			}
			InteractRankBean countInfo = new InteractRankBean();
			countInfo.setTeacherId(teacherId);
			countInfo.setTeacherName(teacherName);
			countInfo.setTotalCount(avgLesson / lessonTeaCountInfos.size());    //老师平均每堂课的互动次数
			rankBeanList.add(countInfo);
		});
		List<InteractRankBean> rankFrontBeanList = new ArrayList<>();
		List<InteractRankBean> rankBackBeanList = new ArrayList<>();
		rankBeanList.sort((x, y) -> -Integer.compare(x.getTotalCount(), y.getTotalCount()));

		RankResultBean rankData = (new SetRankUtils<InteractRankBean>()).getRankData(rankBeanList, "int");
		resultBean.setRankFrontBeanList(rankData.getRankFrontBeanList());
		resultBean.setRankBackBeanList(rankData.getRankBackBeanList());
		return resultBean;
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<InteractDetailBean> queryInteractDetailDataPage(RequestVo vo, Page page) {
		List<InteractDetailBean> list = queryInteractDetailData(vo);
		page.setTotalSize(list.size());
		list = commService.handleOrderData(vo, list, InteractDetailBean.class).stream()
				.skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());

		return list;
	}


	/**
	 * 根据筛选条件查询课堂互动的明细数据
	 * @param vo
	 * @return
	 */
	public List<InteractDetailBean> queryInteractDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("lessonCount");
			vo.setOrderType("desc");
		}
		//根据schoolId、gradeId(有无不定)查得对应的courseSingleIds
		List<Long> singleIds = interactXDao.selectSingleIdsBySchoolIdGradeId(vo);
		//根据courseSingleIds、subjectId（有无不定）、start、end查behaviorList
		List<LessonBehavior> behaviorList = interactMongoDao.queryLessonBehaviorBySingleIds(vo, singleIds);
		List<InteractDetailBean> list = new ArrayList<>();
		for (LessonBehavior behavior : behaviorList) {
			int callNum = behavior.getCallNum() == null ? 0 : behavior.getCallNum();
			int quickNum = behavior.getQuickNum() == null ? 0 : behavior.getQuickNum();
			int examNum = behavior.getExamNum() == null ? 0 : behavior.getExamNum();
			int discuNum = behavior.getDiscuNum() == null ? 0 : behavior.getDiscuNum();
			int authedNum = behavior.getAuthedNum() == null ? 0 : behavior.getAuthedNum();
			int totalNum = callNum + quickNum + examNum + discuNum + authedNum;
			InteractDetailBean info = new InteractDetailBean();
			info.setTeacherId(behavior.getTeacherId());
			info.setTeacherName(behavior.getTeacherName());
			info.setSubjectId(behavior.getSubjectId());
			info.setSubjectName(behavior.getSubjectName());
			info.setAvgTotalCount(BigDecimal.valueOf(totalNum));    //单堂课总互动数
			info.setCallNum(callNum);
			info.setQuickNum(quickNum);
			info.setExamNum(examNum);
			info.setDiscuNum(discuNum);
			info.setAuthedNum(authedNum);
			list.add(info);
		}

		//根据teacherId,subjectId分组，得到分组后的List
		Map<Long, Map<Long, List<InteractDetailBean>>> collect = list.stream().collect(Collectors.groupingBy(InteractDetailBean::getTeacherId, Collectors.groupingBy(InteractDetailBean::getSubjectId)));
		List<InteractDetailBean> subList = new ArrayList<>();
		collect.forEach((teacherId, map) -> {
			map.forEach((subjectId, detailBeanList) -> {
				String teacherName = "";
				String subjectName = "";
				int totalCount = 0;
				int callNum = 0, quickNum = 0, examNum = 0, discuNum = 0, authedNum = 0;
				for (InteractDetailBean detailBean : detailBeanList) {
					teacherName = detailBean.getTeacherName();
					subjectName = detailBean.getSubjectName();
					totalCount = totalCount + detailBean.getAvgTotalCount().intValue();
					callNum = callNum + detailBean.getCallNum();
					quickNum = quickNum + detailBean.getQuickNum();
					examNum = examNum + detailBean.getExamNum();
					discuNum = discuNum + detailBean.getDiscuNum();
					authedNum = authedNum + detailBean.getAuthedNum();
				}
				int lessonCount = detailBeanList.size();  //课堂数
				BigDecimal avgTotalCount1 = BigDecimal.valueOf(handlerPoint((double)totalCount / lessonCount));//平均每堂课总互动次数
				BigDecimal avgCallNum1 = BigDecimal.valueOf(handlerPoint((double)callNum / lessonCount));//平均每堂课点到数
				BigDecimal avgQuickNum1 = BigDecimal.valueOf(handlerPoint((double)quickNum / lessonCount));//平均每堂课快速问答数
				BigDecimal avgExamNum1 = BigDecimal.valueOf(handlerPoint((double)examNum / lessonCount));//平均每堂课随堂作业数
				BigDecimal avgDiscuNum1 = BigDecimal.valueOf(handlerPoint((double)discuNum / lessonCount));//平均每堂课分组讨论数
				BigDecimal avgAuthedNum1 = BigDecimal.valueOf(handlerPoint((double)authedNum / lessonCount));//平均每堂课授权数

				InteractDetailBean subjectInfo = new InteractDetailBean();
				subjectInfo.setTeacherId(teacherId);
				subjectInfo.setTeacherName(teacherName);
				subjectInfo.setSubjectId(subjectId);
				subjectInfo.setSubjectName(subjectName);
				subjectInfo.setLessonCount(lessonCount);

				subjectInfo.setAvgTotalCount(avgTotalCount1);
				subjectInfo.setAvgCallNum(avgCallNum1);
				subjectInfo.setAvgQuickNum(avgQuickNum1);
				subjectInfo.setAvgExamNum(avgExamNum1);
				subjectInfo.setAvgDiscuNum(avgDiscuNum1);
				subjectInfo.setAvgAuthedNum(avgAuthedNum1);

				subList.add(subjectInfo);
			});

		});
		return commService.handleOrderData(vo, subList, InteractDetailBean.class);
	}

	/**
	 * 根据筛选条件查互动总数及详细数据
	 * @param vo schoolId:必须
	 *           gradeId:可选
	 *           subjectId:可选
	 *           teacherName:可选（模糊匹配）
	 *           startDate:必须
	 *           endDate:必须
	 * @return
	 */
	public List<InteractDetailBean> queryInteractAndDetail(RequestVo vo) {
		return queryInteractDetailData1(vo);
	}

	/**
	 * 只给统计总表调用
	 * @param vo
	 * @return
	 */
	private List<InteractDetailBean> queryInteractDetailData1(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		//根据schoolId、gradeId(有无不定)查得对应的courseSingleIds
		List<Long> singleIds = interactXDao.selectSingleIdsBySchoolIdGradeId(vo);
		//根据courseSingleIds、subjectId（有无不定）、start、end查behaviorList
		List<LessonBehavior> behaviorList = interactMongoDao.queryLessonBehaviorBySingleIds(vo, singleIds);
		List<InteractDetailBean> list = new ArrayList<>();
		for (LessonBehavior behavior : behaviorList) {
			int callNum = behavior.getCallNum() == null ? 0 : behavior.getCallNum();
			int quickNum = behavior.getQuickNum() == null ? 0 : behavior.getQuickNum();
			int examNum = behavior.getExamNum() == null ? 0 : behavior.getExamNum();
			int discuNum = behavior.getDiscuNum() == null ? 0 : behavior.getDiscuNum();
			int authedNum = behavior.getAuthedNum() == null ? 0 : behavior.getAuthedNum();
			int totalNum = callNum + quickNum + examNum + discuNum + authedNum;
			InteractDetailBean info = new InteractDetailBean();
			info.setTeacherId(behavior.getTeacherId());
			info.setTeacherName(behavior.getTeacherName());
			info.setSubjectId(behavior.getSubjectId());
			info.setSubjectName(behavior.getSubjectName());
			info.setAvgTotalCount(BigDecimal.valueOf(totalNum));    //单堂课总互动数
			info.setCallNum(callNum);
			info.setQuickNum(quickNum);
			info.setExamNum(examNum);
			info.setDiscuNum(discuNum);
			info.setAuthedNum(authedNum);
			list.add(info);
		}

		//根据teacherId,subjectId分组，得到分组后的List
		Map<Long, Map<Long, List<InteractDetailBean>>> collect = list.stream().collect(Collectors.groupingBy(InteractDetailBean::getTeacherId, Collectors.groupingBy(InteractDetailBean::getSubjectId)));
		List<InteractDetailBean> subList = new ArrayList<>();
		collect.forEach((teacherId, map) -> {
			map.forEach((subjectId, detailBeanList) -> {
				String teacherName = "";
				String subjectName = "";
				int totalCount = 0;
				int callNum = 0, quickNum = 0, examNum = 0, discuNum = 0, authedNum = 0;
				for (InteractDetailBean detailBean : detailBeanList) {
					teacherName = detailBean.getTeacherName();
					subjectName = detailBean.getSubjectName();
					totalCount = totalCount + detailBean.getAvgTotalCount().intValue();
					callNum = callNum + detailBean.getCallNum();
					quickNum = quickNum + detailBean.getQuickNum();
					examNum = examNum + detailBean.getExamNum();
					discuNum = discuNum + detailBean.getDiscuNum();
					authedNum = authedNum + detailBean.getAuthedNum();
				}
				int lessonCount = detailBeanList.size();  //课堂数
				BigDecimal avgTotalCount1 = BigDecimal.valueOf(handlerPoint((double)totalCount / lessonCount));//平均每堂课总互动次数
				BigDecimal avgCallNum1 = BigDecimal.valueOf(handlerPoint((double)callNum / lessonCount));//平均每堂课点到数
				BigDecimal avgQuickNum1 = BigDecimal.valueOf(handlerPoint((double)quickNum / lessonCount));//平均每堂课快速问答数
				BigDecimal avgExamNum1 = BigDecimal.valueOf(handlerPoint((double)examNum / lessonCount));//平均每堂课随堂作业数
				BigDecimal avgDiscuNum1 = BigDecimal.valueOf(handlerPoint((double)discuNum / lessonCount));//平均每堂课分组讨论数
				BigDecimal avgAuthedNum1 = BigDecimal.valueOf(handlerPoint((double)authedNum / lessonCount));//平均每堂课授权数

				InteractDetailBean subjectInfo = new InteractDetailBean();
				subjectInfo.setTeacherId(teacherId);
				subjectInfo.setTeacherName(teacherName);
				subjectInfo.setSubjectId(subjectId);
				subjectInfo.setSubjectName(subjectName);
				subjectInfo.setLessonCount(lessonCount);

				subjectInfo.setAvgTotalCount(avgTotalCount1);
				subjectInfo.setAvgCallNum(avgCallNum1);
				subjectInfo.setAvgQuickNum(avgQuickNum1);
				subjectInfo.setAvgExamNum(avgExamNum1);
				subjectInfo.setAvgDiscuNum(avgDiscuNum1);
				subjectInfo.setAvgAuthedNum(avgAuthedNum1);

				subList.add(subjectInfo);
			});

		});
		return subList;
	}

	/**
	 * 四舍五入保留小数点后一位
	 * @param param
	 * @return
	 */
	private double handlerPoint(double param) {
		return new BigDecimal(param).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
