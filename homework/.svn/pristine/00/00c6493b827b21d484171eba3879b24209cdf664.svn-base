package cn.strong.leke.homework.manage;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.GradeContext;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mongo.HolidayHwDao;
import cn.strong.leke.homework.dao.mongo.HomeworkPaperDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.job.ExerciseWeekRankJob;
import cn.strong.leke.homework.model.*;
import cn.strong.leke.homework.model.mongo.HolidayHwMicro;
import cn.strong.leke.homework.model.mongo.HolidayMicrocourse;
import cn.strong.leke.homework.model.mongo.KnoledgeNode;
import cn.strong.leke.homework.model.mongo.MatNode;
import cn.strong.leke.homework.model.query.HolidayHwQuery;
import cn.strong.leke.homework.util.HomeworkCst;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.SchoolCst;
import cn.strong.leke.model.paper.PaperDetail;
import cn.strong.leke.model.paper.ScoredQuestion;
import cn.strong.leke.notice.model.LetterMessage;
import cn.strong.leke.notice.model.MessageBusinessTypes;
import cn.strong.leke.remote.model.beike.MicrocourseMatNodeKlg;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.microcourse.IMicrocourseRemoteService;
import cn.strong.leke.remote.service.tutor.base.IGradeRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.remote.service.user.IMaterialVersionRemoteService;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;
import cn.strong.leke.user.model.MaterialVersion;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 寒暑假作业服务层
 * @author Zhang Fujun
 * @date 2016年12月15日
 */
@Component
public class HolidayHwMicroService {

	@Resource
	private HolidayHwDao holidayHwDao;
	@Resource
	private ISchoolRemoteService schoolRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private IGradeRemoteService gradeRemoteService;
	@Resource
	private MessageSender holidayHwSender;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private HomeworkPaperDao homeworkPaperDao;
	@Resource
	private IMicrocourseRemoteService iMicrocourseRemoteService;
	@Resource
	private IMaterialVersionRemoteService iMaterialVersionRemoteService;
	@Resource
	private IUserRemoteService iUserRemoteService;

	//寒暑假通知消息接收人
	private List<Long> messageTo = null;

	//执行生成的时间
	private Date executeDate = null;

	private static Logger logger = LoggerFactory.getLogger(ExerciseWeekRankJob.class);

	/**
	 * 发送寒暑假作业生成消息
	 * @param year
	 * @param holiday
	 * @param hwStartTime
	 * @param hwCloseTime
	 * @param start
	 * @param end
	 */
	public void sendHolidayHwMicroMessage(Integer year, Integer holiday, Date hwStartTime, Date hwCloseTime, Date start,
			Date end) {
		HolidayHwMQ mq = new HolidayHwMQ(year, holiday, hwStartTime, hwCloseTime, start, end);
		holidayHwSender.send(mq);
	}

	/**
	 * 监听寒暑假作业消息
	 * @param holidayMQ
	 */
	public void executeHolidayHw(HolidayHwMQ holidayMQ) {
		this.messageTo = new ArrayList<Long>();
		this.executeDate = new Date();
		this.executeHolidayForMic(holidayMQ);
		this.executeHolidayForHw(holidayMQ);

	}

	//作业
	private void executeHolidayForHw(HolidayHwMQ holidayMQ) {
		List<ClassSubjectHw> classSubjectHws = this.homeworkDao.findHolidayClassSubjects(holidayMQ.getStart(),
				holidayMQ.getEnd());
		if (CollectionUtils.isEmpty(classSubjectHws)) {
			return;
		}
		for (ClassSubjectHw item : classSubjectHws) {
			for (Long subjectId : item.getSubjectIds()) {
				List<Long> homeworkIds = this.homeworkDao.findClassHomeworkIds(item.getClassId(), subjectId);
				List<StuHomeworkDtl> subjectHwDtls = this.homeworkDtlDao.findStuHomeworkDtl(homeworkIds);
				this.saveClassHolidayHwData(subjectHwDtls, item.getClassId(), subjectId, holidayMQ);
			}
		}
	}

	//保存某个学科的所有学生作业
	private void saveClassHolidayHwData(List<StuHomeworkDtl> subjectHwDtls, Long classId, Long subjectId,
			HolidayHwMQ holidayMQ) {
		int maxLen = subjectHwDtls.stream().max((a, b) -> a.getPaperIds().size() - b.getPaperIds().size()).get()
				.getPaperIds().size();
		Clazz clazz = klassRemoteService.getClazzByClassId(classId);
		GradeRemote grade = GradeContext.getGrade(clazz.getGradeId());
		for (int i = 0; i < maxLen; i++) {
			Homework hw = new Homework();
			hw.setClassId(classId);
			hw.setSubjectId(subjectId);
			hw.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());
			hw.setClassType(1);
			hw.setCloseTime(holidayMQ.getHwCloseTime());
			hw.setStartTime(holidayMQ.getHwStartTime());
			hw.setCreatedBy(888L);
			hw.setCreatedOn(this.executeDate);
			hw.setTeacherId(888L);
			hw.setIsDeleted(false);
			hw.setSubjective(false);
			hw.setSchoolId(clazz.getSchoolId());
			hw.setFinishNum(0);
			hw.setBugFixNum(0);
			hw.setTotalFixNum(0);
			hw.setTotalNum(0);
			hw.setIsVisible(true);
			hw.setHomeworkType(HomeworkType.HOLIDAY.value);
			hw.setResType(HomeworkCst.HOMEWORK_RES_PAPER);
			hw.setHomeworkName((holidayMQ.getHoliday() == 1 ? "快乐寒假" : "快乐暑假") + "（" + (i + 1) + "）");
			//生成homework 和 homeworkDtls
			this.homeworkDao.insertHomework(hw);
			for (StuHomeworkDtl stuHomeworkDtl : subjectHwDtls) {
				if (stuHomeworkDtl.getPaperIds().size() > i) {
					HomeworkDtl dtl = new HomeworkDtl();
					dtl.setHomeworkId(hw.getHomeworkId());
					dtl.setStudentId(stuHomeworkDtl.getUserId());
					dtl.setStudentName(UserBaseContext.getUserBaseByUserId(stuHomeworkDtl.getUserId()).getUserName());
					dtl.setPaperId(buildNewPaperId(stuHomeworkDtl.getPaperIds().get(i)));
					dtl.setCreatedOn(this.executeDate);
					dtl.setIsDeleted(false);
					dtl.setSubmitStatus(0);
					//保存学生作业
					this.homeworkDtlDao.saveHomeworkDtl(dtl);
					stuHomeworkDtl.getHwDtlIds().add(dtl.getHomeworkDtlId());
				}
			}
		}

		//保存学生作业
		for (StuHomeworkDtl stuHomeworkDtl : subjectHwDtls) {
			HolidayHwMicro holidayHwMicro = new HolidayHwMicro();
			holidayHwMicro.setSchoolId(clazz.getSchoolId());
			holidayHwMicro.setClassId(classId);
			holidayHwMicro.setCreatedBy(888L);
			holidayHwMicro.setGradeId(grade.getGradeId());
			holidayHwMicro.setGradeName(grade.getGradeName());
			holidayHwMicro.setCreatedOn(this.executeDate);
			holidayHwMicro.setHoliday(holidayMQ.getHoliday());
			holidayHwMicro.setIsDeleted(false);
			holidayHwMicro.setSubjectId(subjectId);
			String subjectName = SubjectContext.getSubject(subjectId).getSubjectName();
			holidayHwMicro.setSubjectName(subjectName);
			holidayHwMicro.setType(1);
			holidayHwMicro.setHomeworkDtlIds(stuHomeworkDtl.getHwDtlIds());
			holidayHwMicro.setTotal((long) stuHomeworkDtl.getHwDtlIds().size());
			holidayHwMicro.setFinish(0L);
			holidayHwMicro.setUserId(stuHomeworkDtl.getUserId());
			holidayHwMicro.setUserName(UserBaseContext.getUserBaseByUserId(stuHomeworkDtl.getUserId()).getUserName());
			holidayHwMicro.setYear(holidayMQ.getYear());
			holidayHwMicro.set_id(new ObjectId().toString());
			this.holidayHwDao.insertOne(holidayHwMicro);
			this.sendLetterMessage(holidayHwMicro, holidayMQ.getHwCloseTime());
		}
	}

	private String buildNewPaperId(String paperId) {
		PaperDetail paperDetail = homeworkPaperDao.getByPaperId(paperId).getPaperDetail();
		paperDetail.getGroups().forEach(v -> {
			Collections.shuffle(v.getQuestions());
			int ord = 1;
			for (ScoredQuestion item : v.getQuestions()) {
				item.setOrd(ord);
				ord++;
			}
		});
		HomeworkPaper hwPaper = new HomeworkPaper();
		hwPaper.setPaperId(new ObjectId().toString());
		hwPaper.setPaperDetail(paperDetail);
		homeworkPaperDao.insertOne(hwPaper);
		return hwPaper.getPaperId();

	}

	//微课
	private void executeHolidayForMic(HolidayHwMQ holidayMQ) {

		/*
		 * 每个学校的年级 发消息，进行处理该年级的微课信息
		 * */
		List<Long> schoolIds = this.schoolRemoteService.findSchoolIdBySchoolNature(SchoolCst.NATURE_BASIC);
		List<Long> graduatedGradeIds = Arrays.asList(6L, 9L, 12L);
		for (Long schoolId : schoolIds) {
			List<Clazz> clazzs = klassRemoteService.findDeptClazzBySchoolId(schoolId);
			List<Long> gradeIds = clazzs.stream().map(v -> v.getGradeId()).distinct().collect(Collectors.toList());
			for (Long gradeId : gradeIds) {
				Long gradeId2 = gradeId; //暑假推送下个年级的上学期，寒假推送本年级的下学期
				List<Clazz> gradeClazzs = clazzs.stream().filter(v -> v.getGradeId().equals(gradeId))
						.collect(Collectors.toList());
				if (holidayMQ.getHoliday() == 2) {
					if (!graduatedGradeIds.contains(gradeId)) {
						gradeId2 = gradeId + 1;
					} else {
						continue; //跨学段升级的年级跳过，不再推送下个年级的微课
					}
				}
				for (Clazz clazz : gradeClazzs) {
					this.saveSchoolGradeHolidayMicro(schoolId, gradeId2, clazz.getClassId(), clazz.getClassName(),
							holidayMQ);
				}
			}
		}
	}

	public void executeHolidayForMic_school(HolidayHwMQ holidayMQ, Long schoolId) {
		this.messageTo = new ArrayList<Long>();
		/*
		 * 每个学校的年级 发消息，进行处理该年级的微课信息
		 * */
		List<Long> graduatedGradeIds = Arrays.asList(6L, 9L, 12L);
		List<Clazz> clazzs = klassRemoteService.findDeptClazzBySchoolId(schoolId);
		List<Long> gradeIds = clazzs.stream().map(v -> v.getGradeId()).distinct().collect(Collectors.toList());
		for (Long gradeId : gradeIds) {
			Long gradeId2 = gradeId; //暑假推送下个年级的上学期，寒假推送本年级的下学期
			List<Clazz> gradeClazzs = clazzs.stream().filter(v -> v.getGradeId().equals(gradeId))
					.collect(Collectors.toList());
			if (holidayMQ.getHoliday() == 2) {
				if (!graduatedGradeIds.contains(gradeId)) {
					gradeId2 = gradeId + 1;
				} else {
					continue; //跨学段升级的年级跳过，不再推送下个年级的微课
				}
			}
			for (Clazz clazz : gradeClazzs) {
				this.saveSchoolGradeHolidayMicro(schoolId, gradeId2, clazz.getClassId(), clazz.getClassName(),
						holidayMQ);
			}
		}
	}

	/**
	 * 生成某个年级的微课信息
	 * @param schoolId
	 * @param gradeId
	 * @param classId
	 * @param className
	 * @param holidayMQ
	 */
	private void saveSchoolGradeHolidayMicro(Long schoolId, Long gradeId, Long classId, String className,
			HolidayHwMQ holidayMQ) {
		/*
		 * 寒假 时，推送下学期,暑假推送下个年纪的上学期
		 * */
		List<MaterialVersion> materisaVersions = iMaterialVersionRemoteService.findMaterialVersion(schoolId, gradeId,
				null);
		if (CollectionUtils.isEmpty(materisaVersions)) {
			return;
		}
		GradeRemote grade = GradeContext.getGrade(gradeId);
		for (MaterialVersion materialVersion : materisaVersions) {
			/*
			 * 寒假 -》 推送下学期
			 * 暑假 -》推送升级后的上学期
			 * */

			if (holidayMQ.getHoliday() == 1 && materialVersion.getSemester() == 1
					|| (holidayMQ.getHoliday() == 2 && materialVersion.getSemester() == 2)) {
				continue;
			}
			materialVersion.getMaterialList().forEach(item -> {
				HolidayHwMicro itemHolidayHwMicro = new HolidayHwMicro();
				itemHolidayHwMicro.setSchoolId(schoolId);
				itemHolidayHwMicro.setGradeId(gradeId);
				itemHolidayHwMicro.setGradeName(grade.getGradeName());
				itemHolidayHwMicro.setMatVersion(item.getMaterialId());
				itemHolidayHwMicro.setMatVersionName(item.getMaterialName());
				itemHolidayHwMicro.setSemester(materialVersion.getSemester());
				itemHolidayHwMicro.setSubjectId(materialVersion.getSubjectId());
				itemHolidayHwMicro.setSubjectName(materialVersion.getSubjectName());
				itemHolidayHwMicro.setBookId(materialVersion.getPressId());
				itemHolidayHwMicro.setBookName(materialVersion.getPressName());
				itemHolidayHwMicro.setHoliday(holidayMQ.getHoliday());
				itemHolidayHwMicro.setIsDeleted(false);
				itemHolidayHwMicro.setType(2);
				itemHolidayHwMicro.setCreatedBy(888L);
				itemHolidayHwMicro.setCreatedOn(this.executeDate);
				itemHolidayHwMicro.setYear(holidayMQ.getYear());
				itemHolidayHwMicro.setFinish(0L);
				itemHolidayHwMicro.setClassId(classId);
				itemHolidayHwMicro.setClassName(className);
				this.fillMicroMatNodeData(itemHolidayHwMicro);
				if (itemHolidayHwMicro.getMicrocourses().size() > 0) {
					//学生
					List<Long> classStudentIds = this.klassRemoteService.findStudentIdsByClassId(classId);
					classStudentIds.forEach(stuId -> {
						itemHolidayHwMicro.getMicrocourses().forEach(v -> v.setExerciseId(new ObjectId().toString()));
						itemHolidayHwMicro.set_id(new ObjectId().toString());
						itemHolidayHwMicro.setUserId(stuId);
						String userName = UserBaseContext.getUserBaseByUserId(stuId).getUserName();
						itemHolidayHwMicro.setUserName(userName == null ? "" : userName);
						this.holidayHwDao.insertOne(itemHolidayHwMicro);
						this.sendLetterMessage(itemHolidayHwMicro, holidayMQ.getHwCloseTime());
					});
				}
			});
		}
	}

	/**
	 * 填充教材对应的微课信息
	 * @param itemHolidayHwMicro
	 */
	private void fillMicroMatNodeData(HolidayHwMicro itemHolidayHwMicro) {
		List<MicrocourseMatNodeKlg> microcourseMatNodeKlgs = iMicrocourseRemoteService
				.queryHomeworkMicrocourse(itemHolidayHwMicro.getMatVersion());
		List<MatNode> matNodes = new ArrayList<MatNode>();
		List<HolidayMicrocourse> microcourses = new ArrayList<HolidayMicrocourse>();
		List<Long> matNodeIds = microcourseMatNodeKlgs.stream().map(v -> v.getMaterialNodeId()).distinct()
				.collect(Collectors.toList());
		for (Long nodeId : matNodeIds) {
			MicrocourseMatNodeKlg item = microcourseMatNodeKlgs.stream()
					.filter(v -> v.getMaterialNodeId().equals(nodeId)).findFirst().get();
			MatNode matNode = new MatNode();
			matNode.setNodeId(item.getMaterialNodeId());
			matNode.setNodeName(item.getMaterialNodeName());
			List<KnoledgeNode> knoledgeNodes = microcourseMatNodeKlgs.stream()
					.filter(v -> v.getMaterialNodeId().equals(nodeId)).map(v -> {
						KnoledgeNode knoledgeNode = new KnoledgeNode();
						knoledgeNode.setkId(v.getKnowledgeId());
						knoledgeNode.setkName(v.getKnowledgeName());
						return knoledgeNode;
					}).collect(Collectors.toList());
			matNode.setKnoledageNodes(knoledgeNodes);
			matNodes.add(matNode);
			//微课信息
			microcourseMatNodeKlgs.stream().filter(v -> v.getMaterialNodeId().equals(nodeId)).forEach(itemMicro -> {
				HolidayMicrocourse microcourse = new HolidayMicrocourse();
				microcourse.setKnowledgId(itemMicro.getKnowledgeId());
				microcourse.setMatNodeId(nodeId);
				microcourse.setMicroState(0);
				microcourse.setMicroId(itemMicro.getMicrocourseId());
				microcourse.setMicroName(itemMicro.getMicrocourseName());
				if (item.getTime() != null) {
					microcourse.setTime(itemMicro.getTime().longValue());
				}
				microcourse.setTimeStr(itemMicro.getTimeStr());
				microcourses.add(microcourse);
			});
		}
		itemHolidayHwMicro.setMatNodes(matNodes);
		itemHolidayHwMicro.setMicrocourses(microcourses);
		itemHolidayHwMicro.setTotal((long) microcourses.size());
	}

	/**
	 * 发送消息提醒
	 * @param itemHolidayHwMicro
	 */
	private void sendLetterMessage(HolidayHwMicro itemHolidayHwMicro, Date closeDate) {
		if (!this.messageTo.contains(itemHolidayHwMicro.getUserId())) {
			List<Long> toList = new ArrayList<Long>();
			toList.add(itemHolidayHwMicro.getUserId());
			NoticeHelper.send(this.createLetterMessage(itemHolidayHwMicro, toList, RoleCst.STUDENT, closeDate));
			this.messageTo.add(itemHolidayHwMicro.getUserId());
		}
		//老师
		List<Long> subjectTeacherIds = klassRemoteService.findTeacherIdsByClassIdAndSubjectId(
				itemHolidayHwMicro.getClassId(), itemHolidayHwMicro.getSubjectId());
		subjectTeacherIds.removeAll(this.messageTo);
		if (CollectionUtils.isNotEmpty(subjectTeacherIds)) {
			NoticeHelper
					.send(this.createLetterMessage(itemHolidayHwMicro, subjectTeacherIds, RoleCst.TEACHER, closeDate));
			this.messageTo.addAll(subjectTeacherIds);
		}
		//班主任
		List<Long> classTeacherHeaderIds = klassRemoteService
				.findTeacherHeaderIdsByClassId(itemHolidayHwMicro.getClassId());
		classTeacherHeaderIds.removeAll(this.messageTo);
		if (CollectionUtils.isNotEmpty(classTeacherHeaderIds)) {
			NoticeHelper.send(this.createLetterMessage(itemHolidayHwMicro, classTeacherHeaderIds,
					RoleCst.TEACHER_HEADER, closeDate));
			this.messageTo.addAll(classTeacherHeaderIds);
		}
		//家长 
		List<Long> parentsIds = iUserRemoteService.findParents(itemHolidayHwMicro.getUserId());
		if (CollectionUtils.isNotEmpty(parentsIds)) {
			NoticeHelper.send(this.createLetterMessage(itemHolidayHwMicro, parentsIds, RoleCst.PARENT, closeDate));
		}

	}

	private LetterMessage createLetterMessage(HolidayHwMicro itemHolidayHwMicro, List<Long> users, Long role,
			Date closeDate) {
		String holidayName = getHolidayName(itemHolidayHwMicro.getHoliday());
		String subject = "寒暑假作业";
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.setSubject(subject);
		message.addVariable("1", holidayName);
		message.addTo(users.stream().map(v -> v.toString()).collect(Collectors.toList()));
		if (RoleCst.STUDENT.equals(role)) {
			message.setContent(ParametersContext.getString(HomeworkCst.HOMEWORK__HOLIDAY_STU_TEMPLETE));
		} else if (RoleCst.TEACHER_HEADER.equals(role)) {
			message.setContent(ParametersContext.getString(HomeworkCst.HOMEWORK__HOLIDAY_TEACHER_HEAD_TEMPLETE));
		} else if (RoleCst.TEACHER.equals(role)) {
			message.setContent(ParametersContext.getString(HomeworkCst.HOMEWORK__HOLIDAY_TEACHER_TEMPLETE));
		} else {
			message.addVariable("2", itemHolidayHwMicro.getUserName() == null ? "" : itemHolidayHwMicro.getUserName());
			message.setContent(ParametersContext.getString(HomeworkCst.HOMEWORK__HOLIDAY_PARENTS_TEMPLETE));
		}
		message.setEndTime(DateUtils.addDays(closeDate, 7).getTime());
		return message;
	}

	private String getHolidayName(Integer holiday) {
		if (holiday == 1) {
			return "寒假";
		}
		return "暑假";
	}

	/**
	 * 查询学生寒暑假作业或微课
	 * @param studentId
	 * @param type
	 * @return
	 */
	public List<HolidayHwMicro> findStuHolidayHomeworks(Long studentId, Integer type, Page page) {
		HolidayHwQuery query = new HolidayHwQuery();
		query.setStudentId(studentId);
		query.setType(type);
		return this.holidayHwDao.findHolidayHomeworks(query, page);
	}

	/**
	 * 获取班级学科下学生的寒暑假作业和微课
	 * @param subjectId
	 * @param classId
	 * @return
	 */
	public List<HolidayHwMicro> findClassHolidayHomeworks(Long subjectId, Long classId, Long year, Integer holiday,
			Page page) {
		HolidayHwQuery query = new HolidayHwQuery();
		query.setSubjectId(subjectId);
		query.setClassId(classId);
		query.setYear(year);
		query.setHoliday(holiday);
		return this.holidayHwDao.findHolidayHomeworks(query, page);
	}

	/**
	 * 查未完成寒暑假作业的学生信息
	 * @return java.util.List<cn.strong.leke.homework.model.mongo.HolidayHwMicro>
	 * @Author LIU.SHITING
	 * @Version 2.6
	 * @Date 2017/5/12 10:37
	 * @Param [subjectId, classId, year, holiday]
	 */
	public List<HolidayHwMicro> queryVacationHomeworks(Long subjectId, Long classId, Long year, Integer holiday) {
		HolidayHwQuery query = new HolidayHwQuery();
		query.setSubjectId(subjectId);
		query.setClassId(classId);
		query.setYear(year);
		query.setHoliday(holiday);
		return this.holidayHwDao.queryVacationHomeworks(query);
	}

	/**
	 * 催交寒暑假作业
	 * @Date 2017/5/12 15:02
	 */
	public void excuteCallVacationHomework() {
		Date date = new Date();
		//1.寒假，2.暑假
		Integer holiday = DateUtils.getMonth(date) > 6 ? HomeworkCst.SUMMER_VACATION_HOMEWORK : HomeworkCst.WINTER_VACATION_HOMEWORK;
		Set<Long> userIdList = this.holidayHwDao.queryVacationHomeworkByAuto(date, holiday);
		if (userIdList.size() > 0) {
			sendCallVacationHomeworkInfo(userIdList, holiday);
		}
	}

	/**
	 * 发送催交寒暑假作业消息
	 */
	public void sendCallVacationHomeworkInfo(Set<Long> list, Integer holiday) {
		logger.info("start sendCallVacationHomeworkInfo task-----" + list + "---" + holiday);
		List<String> to = list.stream().map(String::valueOf).collect(Collectors.toList());
		String subject = "催交寒暑假作业";
		String content = ParametersContext.getString(HomeworkCst.HOMWORK_CALL_VACATION_HOMEWORKS);
		LetterMessage message = new LetterMessage();
		message.setBusinessType(MessageBusinessTypes.HOMEWORK);
		message.addTo(to);
		message.setSubject(subject);
		message.setContent(content);
		message.addVariable("vacation", holiday == HomeworkCst.WINTER_VACATION_HOMEWORK ? "寒假" : "暑假");
		NoticeHelper.send(message);
		logger.info("start sendCallVacationHomeworkInfo task-----" + list + "---" + holiday);
	}

	/**
	 * 提供给测试测的
	 */
	public void excuteCallVacationHomework_2() {
		Date date = new Date();
		//1.寒假，2.暑假
		Integer holiday = DateUtils.getMonth(date) > 6 ? HomeworkCst.SUMMER_VACATION_HOMEWORK : HomeworkCst.WINTER_VACATION_HOMEWORK;
		Set<Long> userIdList = this.holidayHwDao.queryVacationHomeworkByAuto(date, holiday);
		if (userIdList.size() > 0) {
			sendCallVacationHomeworkInfo(userIdList, holiday);
		}
	}

	public HolidayHwMicro getSingleHolidayHomework(String id) {
		return this.holidayHwDao.getById(id);
	}

	/**
	 * 完成一份作业时，更新完成量
	 * @param homeworkDtlId
	 */
	public void updateHwState(Long homeworkDtlId) {
		this.holidayHwDao.updateHwState(homeworkDtlId);
	}

	/**
	 * 更新微课完成状态，以及完成量
	 * @param id
	 * @param microId
	 */
	public void updateMicroState(String id, Long microId, Boolean isFirstFinish, Integer position) {
		this.holidayHwDao.updateMicroState(id, microId, isFirstFinish, position);
	}

}
