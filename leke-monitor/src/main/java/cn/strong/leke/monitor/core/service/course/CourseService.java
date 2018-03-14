/**
 * 
 */
package cn.strong.leke.monitor.core.service.course;

import static cn.strong.leke.common.utils.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.Asserts;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.monitor.msg.CourseAttendMsg;
import cn.strong.leke.model.monitor.msg.CourseOnlineMsg;
import cn.strong.leke.model.monitor.msg.CourseTableChangeMsg;
import cn.strong.leke.model.monitor.msg.CourseTeacherStatusMsg;
import cn.strong.leke.model.monitor.msg.CsetStusChangeMsg;
import cn.strong.leke.monitor.core.model.CurrentPlatformCourseStat;
import cn.strong.leke.monitor.core.model.SchoolSnapshotCollector;
import cn.strong.leke.monitor.mongo.course.model.CoursePlatformDaily;
import cn.strong.leke.monitor.mongo.course.model.CourseSchoolDaily;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.mongo.course.model.CourseSingleOnline;
import cn.strong.leke.monitor.mongo.course.model.CourseSingleSnapshot;
import cn.strong.leke.monitor.mongo.course.model.CourseSingleStudentsAttend;
import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.CourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.DailyCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.OnlineCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.PeriodCourseStat;
import cn.strong.leke.monitor.mongo.course.service.IClazzStusDao;
import cn.strong.leke.monitor.mongo.course.service.ICoursePlatformDailyDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolDailyDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolSnapshotDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleOnlineDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleSnapshotDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleStudentsAttendDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseTableDao;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.model.user.SchoolAreaRemote;

/**
 * @author liulongbiao
 *
 */
@Service
public class CourseService implements ICourseService {
	@Autowired
	private IClazzStusDao clazzStusDao;
	@Autowired
	private ICourseSingleDao courseSingleDao;
	@Autowired
	private ICourseTableDao courseTableDao;
	@Autowired
	private ICourseSingleSnapshotDao courseSingleSnapshotDao;
	@Autowired
	private ICourseSingleOnlineDao courseSingleOnlineDao;
	@Autowired
	private ICourseSchoolSnapshotDao courseSchoolSnapshotDao;
	@Autowired
	private ICourseSingleStudentsAttendDao courseSingleStudentsAttendDao;
	@Autowired
	private ICourseSchoolDailyDao courseSchoolDailyDao;
	@Autowired
	private ICoursePlatformDailyDao coursePlatformDailyDao;

	@Override
	public void handle(CsetStusChangeMsg msg) {
		Integer type = msg.getType();
		List<Long> classIds = msg.getClassIds();
		List<Long> stuIds = msg.getStudentIds();
		Asserts.notNull(type, "CsetStusChangeMsg type is null");
		if (isEmpty(classIds) || isEmpty(stuIds)) {
			return;
		}

		SchoolSnapshotCollector collector = new SchoolSnapshotCollector();

		for (Long clazzId : classIds) {
			// 保存套课学生名单(增、删、改)
			saveClazzStus(type, clazzId, stuIds);

			// 更新受影响单课的应到学生数
			long expectStuCount = clazzStusDao.getExpectStuCount(clazzId);
			courseSingleDao.updateExpectStuCountForUnstarted(clazzId, expectStuCount);

			// 收集受影响学校时间点
			collectUnstarted(collector, clazzId);
		}

		// 更新课堂统计
		refreshCourseStats(collector, false);
	}

	private void collectUnstarted(SchoolSnapshotCollector collector, Long clazzId) {
		List<CourseSingle> unstarted = courseSingleDao.findUnstartedByClazzId(clazzId);
		for (CourseSingle cs : unstarted) {
			collector.add(cs);
		}
	}

	private void saveClazzStus(int type, Long clazzId, List<Long> stuIds) {
		switch (type) {
		case CsetStusChangeMsg.TYPE_ADD:
			clazzStusDao.addClazzStus(clazzId, stuIds);
			break;
		case CsetStusChangeMsg.TYPE_REMOVE:
			// 暂不处理库中没有同步过来的套课学生名单
			clazzStusDao.removeClazzStus(clazzId, stuIds);
			break;
		case CsetStusChangeMsg.TYPE_SET:
			clazzStusDao.setClazzStus(clazzId, stuIds);
			break;
		default:
			throw new IllegalArgumentException("Unknown CsetStusChangeMsg type : " + type);
		}
	}

	@Override
	public void handleCourseTableChangeMsgs(List<CourseTableChangeMsg> msgs) {
		if (CollectionUtils.isEmpty(msgs)) {
			return;
		}

		SchoolSnapshotCollector collector = new SchoolSnapshotCollector();
		Map<Long, Long> clazzStuCountCache = new HashMap<>();

		for (CourseTableChangeMsg msg : msgs) {
			handle(msg, collector, clazzStuCountCache);
		}

		refreshCourseStats(collector, false);
	}

	private void refreshCourseStats(SchoolSnapshotCollector collector, boolean actualOnly) {
		collector.getStore().forEach((schoolId, ast) -> {
			SchoolAreaRemote sa = collector.cacheGetSchoolArea(schoolId);

			ast.getTimes().forEach(time -> {
				Date ts = new Date(time);
				if (actualOnly) {
					Long actualStuCount = courseSingleSnapshotDao.getActualStuCount(schoolId, ts);
					courseSchoolSnapshotDao.saveActualStuCount(sa, ts, actualStuCount);
				} else {
					Long expectStuCount = courseSingleDao.getExpectStuCount(schoolId, ts);
					courseSchoolSnapshotDao.saveExpectStuCount(sa, ts, expectStuCount);
				}
			});

			ast.getDays().forEach(day -> {
				List<PeriodCourseStat> stats = courseSingleDao.findPeriodCourseStat(schoolId, day);
				if (!actualOnly) {
					updateCourseTable(sa, day, stats);
				}
				updateCourseSchoolDaily(sa, day, stats);
			});
		});
	}

	// 重新统计该日学校统计相关数据
	private void updateCourseSchoolDaily(SchoolAreaRemote sa, int day, List<PeriodCourseStat> stats) {
		CourseStat stat = aggregate(stats);
		CourseSchoolDaily daily = toCourseSchoolDaily(sa, day, stat);
		courseSchoolDailyDao.save(daily);
	}

	private CourseSchoolDaily toCourseSchoolDaily(SchoolAreaRemote sa, int day, CourseStat stat) {
		School school = SchoolContext.getSchoolBySchoolId(sa.getSchoolId());
		Asserts.notNull(school, "school is not exist");

		CourseSchoolDaily daily = new CourseSchoolDaily();
		daily.setDay(day);
		daily.setSchoolId(sa.getSchoolId());
		daily.setSchoolName(school.getName());
		daily.setSchoolCode(school.getCode());
		daily.setSchoolNature(school.getSchoolNature());
		daily.setRegionIds(toList(sa.getRegionIds()));
		daily.setMarketIds(toList(sa.getMarketIds()));
		daily.setLessonCount(stat.getLessonCount());
		daily.setHours(stat.getHours());
		daily.setExpectStuTimes(stat.getExpectStuTimes());
		daily.setActualStuTimes(stat.getActualStuTimes());

		long expectStuCount = clazzStusDao.getExpectStuCount(stat.getClazzIds());
		daily.setExpectStuCount(expectStuCount);

		long actualStuCount = courseSingleStudentsAttendDao.getActualStuCount(stat.getCsIds());
		daily.setActualStuCount(actualStuCount);

		return daily;
	}

	private CourseStat aggregate(List<? extends CourseStat> stats) {
		long lessonCount = 0;
		long hours = 0;
		long expectStuTimes = 0;
		long actualStuTimes = 0;
		Set<Long> csIds = new HashSet<>();
		Set<Long> clazzIds = new HashSet<>();

		for (CourseStat st : stats) {
			lessonCount += st.getLessonCount();
			hours += st.getHours();
			expectStuTimes += st.getExpectStuTimes();
			actualStuTimes += st.getActualStuTimes();
			csIds.addAll(st.getCsIds());
			clazzIds.addAll(st.getClazzIds());
		}

		CourseStat stat = new CourseStat();
		stat.setLessonCount(lessonCount);
		stat.setHours(hours);
		stat.setExpectStuTimes(expectStuTimes);
		stat.setActualStuTimes(actualStuTimes);
		stat.setCsIds(csIds);
		stat.setClazzIds(clazzIds);
		return stat;
	}

	/*
	 * 更新某校某天的课表
	 */
	private void updateCourseTable(SchoolAreaRemote sa, int day, List<PeriodCourseStat> stats) {
		Long schoolId = sa.getSchoolId();
		courseTableDao.deleteBySchoolAndDay(schoolId, day);

		for (PeriodCourseStat stat : stats) {
			CourseTable ct = toCourseTable(sa, day, stat);
			courseTableDao.save(ct);
		}
	}

	/*
	 * 构建课表记录
	 */
	private CourseTable toCourseTable(SchoolAreaRemote sa, int day, PeriodCourseStat stat) {
		Long schoolId = sa.getSchoolId();
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		Asserts.notNull(school, "school is not exist");

		CourseTable ct = new CourseTable();
		ct.setDay(day);
		ct.setSchoolId(sa.getSchoolId());
		ct.setPeriod(stat.getPeriod());
		ct.setSchoolName(school.getName());
		ct.setSchoolCode(school.getCode());
		ct.setSchoolNature(school.getSchoolNature());
		ct.setRegionIds(toList(sa.getRegionIds()));
		ct.setMarketIds(toList(sa.getMarketIds()));
		ct.setMinStartTime(stat.getMinStartTime());
		ct.setMaxEndTime(stat.getMaxEndTime());
		ct.setLessonCount(stat.getLessonCount());
		ct.setHours(stat.getHours());
		ct.setExpectStuTimes(stat.getExpectStuTimes());

		long expectStuCount = clazzStusDao.getExpectStuCount(stat.getClazzIds());
		ct.setExpectStuCount(expectStuCount);

		return ct;
	}

	private void handle(CourseTableChangeMsg msg, SchoolSnapshotCollector collector,
			Map<Long, Long> clazzStuCountCache) {
		Long csId = msg.getCsId();
		CourseSingle old = courseSingleDao.getById(csId);
		if (old != null) {
			collector.add(old);
		}

		Integer type = msg.getType();
		Asserts.notNull(type, "CourseTableChangeMsg type is null.");
		if (type.equals(CourseTableChangeMsg.TYPE_REMOVE)) {
			// 原来记录没同步，应该也没有影响
			courseSingleDao.removeById(csId);
			return;
		}

		// 测试学校不保存数据
		if (isInvalidSchool(msg.getSchoolId())) {
			return;
		}
		// 保存记录
		CourseSingle cs = toCourseSingle(msg, collector, clazzStuCountCache);
		courseSingleDao.save(cs);
		collector.add(cs);
	}

	private CourseSingle toCourseSingle(CourseTableChangeMsg msg,
			SchoolSnapshotCollector collector, Map<Long, Long> clazzStuCountCache) {
		Long schoolId = msg.getSchoolId();
		SchoolAreaRemote sa = collector.cacheGetSchoolArea(schoolId);
		Asserts.notNull(sa, "school area info is null");
		Long clazzId = msg.getClassId();
		Long expectStuCount = clazzStuCountCache.computeIfAbsent(clazzId,
				clazzStusDao::getExpectStuCount);
		Long csId = msg.getCsId();
		long actualStuCount = courseSingleStudentsAttendDao.getActualStuCount(csId);

		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		Asserts.notNull(school, "school is not exist");

		CourseSingle cs = new CourseSingle();
		cs.setCsId(csId);
		cs.setCsName(msg.getCsName());
		cs.setClazzId(clazzId);
		cs.setClazzName(msg.getClassName());
		cs.setSubjectName(msg.getSubjectName());

		cs.setSchoolId(schoolId);
		cs.setSchoolName(school.getName());
		cs.setSchoolCode(school.getCode());
		cs.setSchoolNature(school.getSchoolNature());
		cs.setRegionIds(toList(sa.getRegionIds()));
		cs.setMarketIds(toList(sa.getMarketIds()));

		cs.setStartTime(msg.getStartTime());
		cs.setEndTime(msg.getEndTime());
		cs.setPeriod(CourseTable.getPeriod(msg.getStartTime()));
		cs.setHours(msg.getHours());
		cs.setTeacherName(msg.getTeacherName());
		cs.setPhone(msg.getPhone());
		cs.setIsRecord(msg.getIsRecord());
		cs.setCourseType(msg.getCourseType());
		cs.setExpectStuCount(expectStuCount);
		cs.setActualStuCount(actualStuCount);
		cs.setIsOnline(false);
		cs.setIsEnded(false);
		cs.setIsDeleted(false);

		return cs;
	}

	private static <T> List<T> toList(Collection<T> coll) {
		return (coll == null || coll.isEmpty()) ? Collections.emptyList() : new ArrayList<>(coll);
	}

	@Override
	public void handleCourseOnlineMsgs(List<CourseOnlineMsg> msgs) {
		if (CollectionUtils.isEmpty(msgs)) {
			return;
		}
		SchoolSnapshotCollector collector = new SchoolSnapshotCollector();

		for (CourseOnlineMsg msg : msgs) {
			// 测试学校不保存数据
			if (isInvalidSchool(msg.getSchoolId())) {
				continue;
			}

			Date ts = StatUtils.getSnapshotTs(msg.getTs());
			Long csId = msg.getCsId();
			Long schoolId = msg.getSchoolId();
			Long stuCount = msg.getStuCount();

			// 添加单课快照
			CourseSingleSnapshot snapshot = new CourseSingleSnapshot();
			snapshot.setTs(ts);
			snapshot.setCsId(csId);
			snapshot.setSchoolId(schoolId);
			snapshot.setActualStuCount(stuCount);
			courseSingleSnapshotDao.save(snapshot);

			// 保存单课最新在线信息
			CourseSingleOnline online = new CourseSingleOnline();
			online.setTs(ts);
			online.setCsId(csId);
			online.setSchoolId(schoolId);
			online.setActualStuCount(stuCount);
			courseSingleOnlineDao.save(online);

			// 收集受影响的学校时间点
			collector.add(schoolId, ts);
		}

		refreshCourseStats(collector, true);
	}

	@Override
	public void handle(CourseAttendMsg msg) {
		// 测试学校不保存数据
		if (isInvalidSchool(msg.getSchoolId())) {
			return;
		}

		Long csId = msg.getCsId();
		CourseSingle cs = courseSingleDao.getById(csId);
		List<Long> stuIds = msg.getAttendStuIds();
		if (stuIds == null) {
			stuIds = Collections.emptyList();
		}

		// 保存考勤信息
		CourseSingleStudentsAttend record = new CourseSingleStudentsAttend();
		record.setCsId(csId);
		record.setStuIds(stuIds);
		Date endTime = cs != null ? cs.getEndTime() : new Date();
		record.setEndTime(endTime);
		courseSingleStudentsAttendDao.save(record);

		if (cs == null) {
			return;
		}

		// 更新单课实到数量
		long actualStuCount = stuIds.size();
		courseSingleDao.updateActualStuCount(csId, actualStuCount);
		courseSingleDao.updateIsEnded(csId, true);

		// 统计并保存 course.school.daily
		Long schoolId = cs.getSchoolId();
		int day = StatUtils.ofDay(cs.getStartTime());
		SchoolAreaRemote sa = SchoolSnapshotCollector.getSchoolArea(schoolId);
		List<PeriodCourseStat> stats = courseSingleDao.findPeriodCourseStat(schoolId, day);
		updateCourseSchoolDaily(sa, day, stats);
	}

	@Override
	public void handle(CourseTeacherStatusMsg msg) {
		if (msg.getCsId() == null || msg.getIsOnline() == null) {
			throw new IllegalArgumentException("CourseTeacherStatusMsg 消息错误");
		}
		courseSingleDao.updateIsOnline(msg.getCsId(), msg.getIsOnline());
	}

	@Override
	public void updateCoursePlatformDaily(int day) {
		CoursePlatformDaily daily = getCoursePlatformDaily(day);
		coursePlatformDailyDao.save(daily);
	}

	private CoursePlatformDaily getCoursePlatformDaily(int day) {
		DailyCourseStat stat = courseSchoolDailyDao.getDailyCourseStat(day);
		long maxActualStuCount = courseSchoolSnapshotDao.getMaxActualStuCount(day);
		CoursePlatformDaily daily = toCoursePlatformDaily(stat, maxActualStuCount);
		return daily;
	}

	private CoursePlatformDaily toCoursePlatformDaily(DailyCourseStat stat, long maxActualStuCount) {
		CoursePlatformDaily daily = new CoursePlatformDaily();
		daily.setDay(stat.getDay());
		daily.setSchoolCount(stat.getSchoolCount());
		daily.setLessonCount(stat.getLessonCount());
		daily.setExpectStuCount(stat.getExpectStuCount());
		daily.setActualStuCount(stat.getActualStuCount());
		daily.setExpectStuTimes(stat.getExpectStuTimes());
		daily.setActualStuTimes(stat.getActualStuTimes());
		daily.setMaxActualStuCount(maxActualStuCount);
		return daily;
	}

	@Override
	public CurrentPlatformCourseStat getCurrentPlatformCourseStat() {
		int today = StatUtils.ofDay(new Date());
		DailyCourseStat daily = courseSchoolDailyDao.getDailyCourseStat(today);
		OnlineCourseStat online = courseSingleOnlineDao.getOnlineCourseStat();
		return new CurrentPlatformCourseStat(daily, online);
	}

	/*
	 * 排除 schoolCode 为 null 的测试学校
	 */
	private static boolean isInvalidSchool(Long schoolId) {
		if (schoolId == null) {
			return true;
		}
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		return school == null || school.getCode() == null;
	}
}
