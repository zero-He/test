package cn.strong.leke.diag.service.teachingMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.GradeContext;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.diag.dao.studentMonitor.HomeworkAnalyseDao;
import cn.strong.leke.diag.dao.teachingMonitor.EvaluateXDao;
import cn.strong.leke.diag.dao.teachingMonitor.LessonBeikeInfoDao;
import cn.strong.leke.diag.model.studentMonitor.StudentAttend;
import cn.strong.leke.diag.model.teachingMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.QueryEvaluate;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.base.SchoolStage;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.base.IGradeRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISubjectRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.CollationKey;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommService {
	
	@Resource
	private LessonBeikeInfoDao lessonBeikeInfoDao;
	
	@Resource
	private IGradeRemoteService gradeRemoteService;
	
	@Resource
	private ISubjectRemoteService subjectRemoteService;
	
	@Resource
	private IKlassRemoteService klassRemoteService;

	@Resource
	private EvaluateXDao evaluateXDao;

	@Resource
	private HomeworkAnalyseDao homeworkAnalyseDao;

	protected static final Logger logger = LoggerFactory.getLogger(CommService.class);

	
	/**
	 * 获取当前登录用户的学校
	 * @return
	 */
	public School getSchoolByCurrentUser(){
		User user = UserContext.user.get();
		Long schoolId = user.getCurrentSchool().getId();
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		return school;
	}
	
	/**
	 * 查询当前登录用户所属学校的年级
	 * @return
	 */
	public List<GradeRemote> findGradeOfSchool(){
		School school = getSchoolByCurrentUser();
		List<GradeRemote> gradeList = new ArrayList<>();
		for(SchoolStage stage : school.getSchoolStages()){
			gradeList.addAll(gradeRemoteService.findGradeListBySchoolStageId(stage.getSchoolStageId()));
		}
		return gradeList;
	}
	
	/**
	 * 查询当前登录用户所属学校的班级
	 * @return
	 */
	public List<Clazz> findClazzOfSchool(){
		ClazzQuery query = new ClazzQuery();
		query.setSchoolId(getSchoolByCurrentUser().getId());
		List<Clazz> classList = this.klassRemoteService.findClazzByQuery(query);
		classList = classList.stream().filter(v->v.getClassName() != null).sorted(new CommService.CnComparator<Clazz>(true, true, "className", Clazz.class)).collect(Collectors.toList());
		return classList;
	}
	
	/**
	 * 查询当前登录用户所属学校指定年级的班级
	 * @return
	 */
	public List<Clazz> findClazzOfSchoolByGrade(Long gradeId){
		ClazzQuery query = new ClazzQuery();
		query.setSchoolId(getSchoolByCurrentUser().getId());
		query.setGradeId(gradeId);
		List<Clazz> classList = this.klassRemoteService.findClazzByQuery(query);
		classList = classList.stream().sorted(new CommService.CnComparator<Clazz>(true, true, "className", Clazz.class)).collect(Collectors.toList());
		return classList;
	}

	/**
	 * 根据gradeId查行政班级
	 * @param gradeId
	 * @return
	 */
	public List<Clazz> queryClassByGradeId(Long gradeId) {
		ClazzQuery query = new ClazzQuery();
		query.setSchoolId(getSchoolByCurrentUser().getId());
		query.setGradeId(gradeId);
		query.setType(1);
		long startTime = System.currentTimeMillis();
		List<Clazz> clazzByQuery = this.klassRemoteService.findClazzByQuery(query);
		clazzByQuery = clazzByQuery.stream().sorted(new CommService.CnComparator<Clazz>(true, true, "className", Clazz.class)).collect(Collectors.toList());
		return clazzByQuery;
	}

	/**
	 * 根据schoolId、gradeId、classId查学生Clazz（行政班）
	 * @param vo
	 * @return
	 */
	public List<ClazzBean> queryClazzByParam(RequestVo vo){
		return homeworkAnalyseDao.selectClazzByParam(vo).stream().sorted(new CnComparator<ClazzBean>(true, true, "className", ClazzBean.class)).collect(Collectors.toList());
	}

	/**
	 * 查询当前登录用户所属学校的学科
	 * @return
	 */
	public List<SubjectRemote> findSubjectOfSchool(){
		School school = getSchoolByCurrentUser();
		List<SubjectRemote> subjectResultList = new ArrayList<>();
		List<SubjectRemote> subjectList = new ArrayList<>();
		List<SubjectBean> subjectBeanList = new ArrayList<>();
		for(SchoolStage stage : school.getSchoolStages()){
			subjectList.addAll(subjectRemoteService.findSubjectListBySchoolStageId(stage.getSchoolStageId()));
		}

		subjectBeanList.addAll(subjectList.stream().map(v->{
			SubjectBean sb = new SubjectBean();
			sb.setSubjectId(v.getSubjectId());
			sb.setSubjectName(v.getSubjectName());
			return sb;
		}).distinct().collect(Collectors.toList()));
		
		subjectResultList.addAll(subjectBeanList.stream().map(v->{
			SubjectRemote sr = new SubjectRemote();
			sr.setSubjectId(v.getSubjectId());
			sr.setSubjectName(v.getSubjectName());
			return sr;
		}).collect(Collectors.toList()));
		
		return subjectResultList;
	}
	
	/**
	 * 查询当前登录用户所属学校的指定年级的学科
	 * @return
	 */
	public List<SubjectRemote> findSubjectOfGradeBySchool(RequestVo vo){
		List<SubjectRemote> subjectList = new ArrayList<>();
		Long stageId = lessonBeikeInfoDao.findStageIdByGrade(vo);
		subjectList.addAll(subjectRemoteService.findSubjectListBySchoolStageId(stageId));
		return subjectList;
	}

	/**
	 * 根据gradeId查指定年级的学科
	 * @param gradeId
	 * @return
	 */
	public List<SubjectRemote> findSubjectOfGradeBySchool(Long gradeId) {
		List<SubjectRemote> subjectList = new ArrayList<>();
		RequestVo vo = new RequestVo();
		vo.setSchoolId(getSchoolByCurrentUser().getId());
		vo.setGradeId(gradeId);
		Long stageId = lessonBeikeInfoDao.findStageIdByGrade(vo);
		subjectList.addAll(subjectRemoteService.findSubjectListBySchoolStageId(stageId));
		return subjectList;
	}

	/**
	 * 查询上过课的classId（根据schoolId、gradeId、subjectId、startDate、endDate）
	 * @param vo
	 * @return
	 */
	public List<ClassBean> queryIsLessonClazz(RequestVo vo){
		return evaluateXDao.selectIsLessonClazz(vo);
	}

	/**
	 * 根据schoolId、gradeId查该校该年级的老师ids
	 * @param schoolId
	 * @param gradeId
	 * @return
	 */
	public List<Long> findTeacherIdsBySchoolIdGradeId(Long schoolId, Long gradeId){
		return evaluateXDao.selectTeacherIdsBySchoolIdGradeId(schoolId, gradeId);
	}

	/**
	 * 根据schoolId、gradeId、subjectId查teacherIds
	 * @param schoolId
	 * @param gradeId
	 * @param subjectId
	 * @return
	 */
	public List<Long> findTeacherIdsBySchoolIdGradeIdSubjectId(Long schoolId, Long gradeId, Long subjectId) {
		QueryEvaluate query = new QueryEvaluate();
		query.setSchoolId(schoolId);
		query.setGradeId(gradeId);
		query.setSubjectId(subjectId);
		return evaluateXDao.selectTeacherIdsBySchoolIdGradeIdSubjectId(query);
	}
	
	/**
	 * 根据年级查学段
	 * @param vo
	 * @return
	 */
	public Long findStageIdByGrade(RequestVo vo){
		return lessonBeikeInfoDao.findStageIdByGrade(vo);
	}

	/**
	 * 按日查询时间段
	 * @param vo 请求参数
	 * @return
	 */
	public List<CommProp> findBeikeRateByDay(RequestVo vo){
		return lessonBeikeInfoDao.findTrendDateByDay(vo);
	}
	
	/**
	 * 按周查询时间段
	 * @param vo 请求参数
	 * @return
	 */
	public List<CommProp> findBeikeRateByWeek(RequestVo vo){
		return lessonBeikeInfoDao.findTrendDateByWeek(vo);
	}
	
	/**
	 * 按月查询时间段
	 * @param vo 请求参数
	 * @return
	 */
	public List<CommProp> findBeikeRateByMonth(RequestVo vo){
		return lessonBeikeInfoDao.findTrendDateByMonth(vo);
	}
	
	/**
	 * 查询老师及对应学科信息
	 * @param vo
	 * @return
	 */
	public List<CommProp> findTeacherSubject(RequestVo vo){
		return lessonBeikeInfoDao.findTeacherSubject(vo);
	}
	
	/**
	 * 设置请求参数通用属性
	 * @param vo
	 */
	public void setCommPropToRequestVo(RequestVo vo){
		User user = UserContext.user.get();
		Long schoolId = Long.valueOf(String.valueOf(user.getCurrentSchool().getId()));
		vo.setSchoolId(schoolId);
		vo.setLimit((DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1).getTime() - DateUtils.parseDate(vo.getStartDate()).getTime())/(1000 * 3600 * 24));

		if(null == vo.getTrendType()){
			vo.setTrendType(RequestVo.DAY);
		}
		
		if(null == vo.getCompType()){
			vo.setCompType(RequestVo.GRADE);
		}
	}

	/**
	 * 四舍五入保留小数点后一位
	 * @param param
	 * @return
	 */
	public double handlerPoint(double param) {
		return new BigDecimal(param).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public BigDecimal handlerPoint(BigDecimal param) {
		return param.setScale(1, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 处理excel导出文件名
	 * @param vo
	 * @return
	 */
	public String[] handleExcelTitle(RequestVo vo){
		StringBuilder sb = new StringBuilder("(");
		String[] strArr = new String[2];
		strArr[0] = "【乐课网】";
		
		if(null == vo.getGradeId()){
			sb.append("全校");
		}else{
			sb.append(GradeContext.getGrade(vo.getGradeId()).getGradeName());
		}
		
		if(null == vo.getSubjectId()){
			sb.append("全部学科");
		}else{
			sb.append(SubjectContext.getSubject(vo.getSubjectId()).getSubjectName());
		}
		
		sb.append(vo.getStartDate()).append("--").append(vo.getEndDate()).append(")");
		strArr[1] = sb.toString();
		return strArr;
	}
	
	public String[] handleExcelTitlePro(RequestVo vo){
		StringBuilder sb = new StringBuilder("(");
		String[] strArr = new String[2];
		strArr[0] = "【乐课网】";
		
		if(StringUtils.hasText(vo.getQueryType()) && vo.getQueryType().equalsIgnoreCase(StudentAttend.CLASS_DTL)){
			sb.append(vo.getStartDate() + "_" + vo.getEndDate() + "_" + vo.getClassName()).append(")");
		}else if(StringUtils.hasText(vo.getQueryType()) && vo.getQueryType().equalsIgnoreCase(StudentAttend.LESSON_DTL)){
			sb.append(vo.getTimeStr() + "_" + vo.getClassName() + "_" + vo.getSubjectName() + "_" + vo.getTeacherName()).append(")");
		}else{
			if(vo.getGradeId() != null && vo.getClassId() == null){
				sb.append(GradeContext.getGrade(vo.getGradeId()).getGradeName());
			}
	
			if(null == vo.getClassId()){
				sb.append("全部班级");
			}else{
				sb.append(klassRemoteService.findClazzByClassIds(Arrays.asList(vo.getClassId())).get(0).getClassName());
			}
	
			if(null == vo.getSubjectId()){
				sb.append("全部学科");
			}else if(vo.getSubjectId() > -1L){
				sb.append(SubjectContext.getSubject(vo.getSubjectId()).getSubjectName());
			}
			
			if(StringUtils.hasText(vo.getQueryType()) && vo.getQueryType().equalsIgnoreCase(StudentAttend.DAY)){
				sb.append(vo.getStartDate()).append(")");
			}else{
				sb.append(vo.getStartDate()).append("--").append(vo.getEndDate()).append(")");
			}
		}
		strArr[1] = sb.toString();
		return strArr;
	}
	
	/**
	 * 排序集合数据
	 * @param vo
	 * @param orderData
	 * @param classType
	 * @return
	 */
	public <T> List<T> handleOrderData(RequestVo vo, List<T> orderData, Class<T> classType){
		if(null != orderData && orderData.isEmpty()){
			return orderData;
		}else if(null == orderData){
			return null;
		}
		
		List<T> returnList = null;
		try {
			Class[] classArr = new Class[1];
			try{
				classArr[0] = classType.getDeclaredField(vo.getOrderAttr()).getType();
			}catch(NoSuchFieldException e){
				classArr[0] = classType.getSuperclass().getDeclaredField(vo.getOrderAttr()).getType();
			}
			final Class filedType = classArr[0];

			String methodName = "get" + vo.getOrderAttr().substring(0, 1).toUpperCase() + vo.getOrderAttr().substring(1);
			
			returnList = orderData.stream().sorted((a, b) -> {
				try {
					BigDecimal x = null;
					BigDecimal y = null;
					if (filedType.equals(Long.class)) {
						x = new BigDecimal((Long)a.getClass().getMethod(methodName).invoke(a));
						y = new BigDecimal((Long)b.getClass().getMethod(methodName).invoke(b));
					}else if (filedType.equals(Integer.class)) {
						x = new BigDecimal((Integer) a.getClass().getMethod(methodName).invoke(a));
						y = new BigDecimal((Integer) b.getClass().getMethod(methodName).invoke(b));
					}else if (filedType.equals(BigDecimal.class)) {
						x = (BigDecimal) a.getClass().getMethod(methodName).invoke(a);
						y = (BigDecimal) b.getClass().getMethod(methodName).invoke(b);
					}else if (filedType.equals(String.class)) {
						x = new BigDecimal((String)a.getClass().getMethod(methodName).invoke(a));
						y = new BigDecimal((String)b.getClass().getMethod(methodName).invoke(b));
					}else if (filedType.equals(Double.class)) {
						x = new BigDecimal((Double) a.getClass().getMethod(methodName).invoke(a) == null ? -1 : (Double) a.getClass().getMethod(methodName).invoke(a));
						y = new BigDecimal((Double) b.getClass().getMethod(methodName).invoke(b) == null ? -1 : (Double) b.getClass().getMethod(methodName).invoke(b));
					}else if (filedType.equals(Date.class)) {
						x = new BigDecimal(((Date) a.getClass().getMethod(methodName).invoke(a)) == null ? 1 : ((Date) a.getClass().getMethod(methodName).invoke(a)).getTime());
						y = new BigDecimal(((Date) b.getClass().getMethod(methodName).invoke(b)) == null ? 1 : ((Date) b.getClass().getMethod(methodName).invoke(b)).getTime());
					}
					
					if("asc".equalsIgnoreCase(vo.getOrderType())){
						return x.compareTo(y);
					}else{
						return y.compareTo(x);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}).collect(Collectors.toList());
			
			int index = 0;
			for(T t : returnList){
				t.getClass().getMethod("setIndex", Integer.class).invoke(t, ++index);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return returnList;
	}

	/**
	 * 根据schoolId、gradeId或者classId查所有学生
	 * @param vo
	 * @return
	 */
	public List<Long> queryStudentIdByParam(RequestVo vo){
		return homeworkAnalyseDao.selectStudentIdByParam(vo);
	}

	/**
	 * 根据schoolId、gradeId或者classId查所有学生
	 * @param schoolId
	 * @param gradeId
	 * @param classId
	 * @return
	 */
	public Set<Long> queryStudentIdList(Long schoolId, Long gradeId, Long classId) {
		Set<Long> studentList = new HashSet<>();
		if (schoolId != null && gradeId == null && classId == null) {
			//根据schoolId查所有班级列表
			ClazzQuery query = new ClazzQuery();
			query.setSchoolId(getSchoolByCurrentUser().getId());
			query.setType(1);
			List<Clazz> classList = this.klassRemoteService.findClazzByQuery(query);
			studentList = handlerStudentIds(classList);
		}
		if (schoolId != null && gradeId != null && classId == null) {
			List<Clazz> classList = queryClassByGradeId(gradeId);
			studentList = handlerStudentIds(classList);
		}
		if (schoolId != null && gradeId != null && classId != null) {
			List<Long> studentIds = klassRemoteService.findStudentIdsByClassId(classId);
			studentList.addAll(studentIds);
		}

		return studentList;
	}

	/**
	 * 处理学生Ids
	 * @param classList
	 * @return
	 */
	private Set<Long> handlerStudentIds(List<Clazz> classList) {
		Set<Long> studentList = new HashSet<>();
		classList.forEach(c -> {
			//根据班级ID获取班级学生ID列表
			List<Long> studentIds = klassRemoteService.findStudentIdsByClassId(c.getClassId());
			studentList.addAll(studentIds);
		});
		return studentList;
	}
	
	/**
	 * 中文字符串比较器
	 * @author xujs
	 *
	 * @param <T>
	 */
	public static class CnComparator<T> implements Comparator<T>{
		private Collator collator = Collator.getInstance(Locale.CHINA);
		private boolean ignoreCase;
		private boolean isAsc;
		private String orderAttr;
		private Class<T> classType;
		
		public CnComparator(boolean ignoreCase, boolean isAsc, String orderAttr, Class<T> classType) {
			this.ignoreCase = ignoreCase;
			this.isAsc = isAsc;
			this.orderAttr = orderAttr;
			this.classType = classType;
		}
		
		public int compare(T o1, T o2) {
			CollationKey key1 = null;
			CollationKey key2 = null;
			
			String source1 = null;
			String source2 = null;
			String methodName = "get" + this.orderAttr.substring(0, 1).toUpperCase() + this.orderAttr.substring(1);
			
			if(StringUtils.hasText(this.orderAttr)){
				try {
					source1 = (String)classType.getMethod(methodName).invoke(o1);
					source2 = (String)classType.getMethod(methodName).invoke(o2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				source1 = o1.toString();
				source2 = o2.toString();
			}
			
			if(!this.ignoreCase){
				key1 = collator.getCollationKey(source1);
				key2 = collator.getCollationKey(source2); 
			}else{
				key1 = collator.getCollationKey(source1.toLowerCase());
				key2 = collator.getCollationKey(source2.toLowerCase()); 
			}
			
			if(this.isAsc){
				return key1.compareTo(key2);
			}
			
			return -key1.compareTo(key2);
		}
	}
	
}
