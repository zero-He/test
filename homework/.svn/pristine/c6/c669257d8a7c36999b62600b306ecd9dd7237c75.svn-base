package cn.strong.leke.homework.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingLong;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.incentive.DynamicHelper;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mongo.HomeworkPaperDao;
import cn.strong.leke.homework.dao.mybatis.HanWangDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.ApiStudentHomeworkListDTO;
import cn.strong.leke.homework.model.HDStuHomeworkListDTO;
import cn.strong.leke.homework.model.HanWang;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlDTO;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkDtlQuery;
import cn.strong.leke.homework.model.HomeworkPaper;
import cn.strong.leke.homework.model.MHomeworkDtlInfo;
import cn.strong.leke.homework.model.MyHomeworkDTO;
import cn.strong.leke.homework.model.StuHwByDayDTO;
import cn.strong.leke.homework.model.StuSubjRes;
import cn.strong.leke.homework.model.StudentHomeworkQuery;
import cn.strong.leke.homework.model.StudentReviewInfo;
import cn.strong.leke.homework.model.SubjectHwStatistics;
import cn.strong.leke.homework.model.mobile.MPersonWorkCount;
import cn.strong.leke.homework.model.mobile.MPersonWorkDTO;
import cn.strong.leke.homework.model.mobile.MPersonWorkQuery;
import cn.strong.leke.homework.model.query.ApiParamForStuHomework;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.lesson.model.LessonVM;
import cn.strong.leke.model.common.StuTreeModel;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.IncentiveTypes;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.ILessonRemoteService;

@Service("homeworkDtlService")
public class HomeworkDtlServiceImpl implements HomeworkDtlService {

	@Resource
	private HanWangDao hanWangDao;
	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private HomeworkPaperDao homeworkPaperDao;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private ILessonRemoteService lessonRemoteService;

	@Override
	public void saveHomeworkDtl(Homework homework, List<StuTreeModel> stuList) {
		homeworkDtlDao.insertHomeworkDtl(stuList, homework);
	}

	@Override
	public void saveHomeworkDtlForBind(Homework homework, List<UserBase> userList) {
		homeworkDtlDao.insertHomeworkDtlForBind(userList, homework);
	}

	@Override
	public List<HomeworkDtlDTO> findHomeworkDtlList(Long homeworkId, String stuName) {
		return homeworkDtlDao.queryHomeworkDtlList(homeworkId, stuName);
	}

	@Override
	public List<HomeworkDtlDTO> getHwDtl(Long homeworkId) {
		return homeworkDtlDao.queryHwDtl(homeworkId);
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<MyHomeworkDTO> getMyHomeworkList(HomeworkDtlQuery homeworkDtlQuery, Page page) {
		if (homeworkDtlQuery.getCloseEndTime() != null) {
			homeworkDtlQuery.setCloseEndTime(DateUtils.toDayEndTime(homeworkDtlQuery.getCloseEndTime()));
		}
		return homeworkDtlDao.getMyHomeworkList(homeworkDtlQuery, page);
	}

	@Override
	public HomeworkDtl getHomeworkDtlById(Long homeworkDtlId) {
		return this.homeworkDtlDao.getHomeworkDtlById(homeworkDtlId);
	}

	@Override
	public void updateHomeworkDtl(HomeworkDtl homeworkDtl) {
		this.homeworkDtlDao.updateHomeworkDtl(homeworkDtl);
	}

	@Override
	public List<HomeworkDtl> findHomeworkDtlListByHomeworkId(Long homeworkId) {
		return homeworkDtlDao.findHomeworkDtlListByHomeworkId(homeworkId);
	}

	@Override
	public Map<String, Integer> queryHwNum(Long teacherId) {
		return homeworkDtlDao.queryHwNum(teacherId);
	}

	@Override
	public List<StuHwByDayDTO> queryStuHwByDayList(Long studentId, String day) {
		Date headTime = DateUtils.parseDate(day, DateUtils.SHORT_DATE_PATTERN);
		Date tailTime = DateUtils.addDays(headTime, 1);
		return homeworkDtlDao.queryStuHwByDay(studentId, headTime, tailTime);
	}

	@Override
	public List<StuHwByDayDTO> findStuHomeworkList(HomeworkDtlQuery query) {
		return this.homeworkDtlDao.findStuHomeworkList(query);
	}

	@Override
	public StuHwByDayDTO getStuHomeworkByHomeworkDtlId(Long homeworkDtlId) {
		return this.homeworkDtlDao.getStuHomeworkByHomeworkDtlId(homeworkDtlId);
	}

	public StuHwByDayDTO getStuHomeworkByHomeworkIdAndStudentId(Long homeworkId, Long studentId) {
		return this.homeworkDtlDao.getStuHomeworkByHomeworkIdAndStudentId(homeworkId, studentId);
	}

	@Override
	public Long getPrevHomeworkId(Long homeworkId, Long homeworkDtlId) {
		return this.homeworkDtlDao.getPrevHomeworkId(homeworkId, homeworkDtlId);
	}

	@Override
	public Long getNextHomeworkId(Long homeworkId, Long homeworkDtlId) {
		return this.homeworkDtlDao.getNextHomeworkId(homeworkId, homeworkDtlId);
	}

	@Override
	public Long getNextCorrectHomeworkId(Long homeworkId, Long correctUserId) {
		Homework hw = homeworkDao.getHomeworkById(homeworkId);
		if (correctUserId == hw.getTeacherId()) {
			return this.homeworkDtlDao.getNextCorrectHomeworkId(homeworkId);
		} else {
			List<Long> hwDtlIds = this.homeworkDtlDao.findHwByCorrectUser(correctUserId);
			for (Long hwDtlId : hwDtlIds) {
				HomeworkDtlInfo hwDtlInfo = homeworkDtlDao.getHomeworkDtlInfoById(hwDtlId);
				HomeworkDtl selfHw = homeworkDtlDao.getHomeworkDtlByHomeworkIdAndStudentId(hwDtlInfo.getHomeworkId(), correctUserId);
				if (selfHw.getSubmitTime() != null) {
					return hwDtlId;
				}
			}
		}
		return null;
	}

	@Override
	public Long getNextReviewHomeworkId(Long homeworkId) {
		return this.homeworkDtlDao.getNextReviewHomeworkId(homeworkId);
	}

	@Override
	public HomeworkDtl getHomeworkDtlByHomeworkIdAndStudentId(Long homeworkId, Long studentId) {
		return this.homeworkDtlDao.getHomeworkDtlByHomeworkIdAndStudentId(homeworkId, studentId);
	}

	@Override
	public HomeworkDtl getHomeworkDtlByParentIdAndStudentId(Long parentId, Long studentId) {
		return this.homeworkDtlDao.getHomeworkDtlByParentIdAndStudentId(parentId, studentId);
	}

	@Override
	public List<StuSubjRes> findStuSubjResByStudentId(Long studentId, Long subjectId, Date startDate) {
		return this.homeworkDtlDao.findStuSubjResByStudentId(studentId, subjectId, startDate);
	}

	@Override
	public Integer getRankByHomeworkIdAndScore(Long homeworkId, BigDecimal score) {
		return this.homeworkDtlDao.getRankByHomeworkIdAndScore(homeworkId, score);
	}

	@Override
	public void saveHanWang(HanWang hanWang) {
		this.hanWangDao.saveHanWang(hanWang);
	}

	@Override
	public HanWang getHanWangById(Long id) {
		return this.hanWangDao.getHanWangById(id);
	}

	@Override
	public List<SubjectHwStatistics> getSubjectHomeworkInfo(Long studentId) {
		return homeworkDtlDao.getSubjectHomeworkInfo(studentId);
	}

	@Override
	public List<Map<String, Long>> findSubmitHomeworkDtls(Long homeworkId) {
		return homeworkDtlDao.findSubmitHomeworkDtls(homeworkId);
	}

	@Override
	public List<ApiStudentHomeworkListDTO> findStuHomeworkList_v1(ApiParamForStuHomework query) {
		List<ApiStudentHomeworkListDTO> list = homeworkDtlDao.findStuHomeworkList_v1(query);
		list.forEach(v->{
			if(v.getOpenAnswerTime() != null) {
				v.setIsOpenAnswer(v.getIsOpenAnswer() ? true : v.getOpenAnswerTime().getTime() <= new Date().getTime());
			}
		});
		return list;
	}

	@Override
	public List<HomeworkDtl> findHomeworkDtlByHomeworkIdAndStudentName(Long homeworkId, String studentName, Page page) {
		return this.homeworkDtlDao.findHomeworkDtlByHomeworkIdAndStudentName(homeworkId, studentName, page);
	}

	@Override
	public List<SubjectHwStatistics> findStudentSubjectHwStatisticsList(Long studentId, Boolean filter) {
		List<SubjectHwStatistics> subjectHomeworks = getSubjectHomeworkInfo(studentId);
		//移除待批改和待订正都是零的
		if (filter) {
			subjectHomeworks = subjectHomeworks.stream().filter(s -> {
				return s.getBugFixNum() > 0 || s.getUnfinishNum() > 0;
			}).collect(toList());
		}
		//分组排序
		Map<Long, List<SubjectHwStatistics>> map = subjectHomeworks.stream()
				.collect(groupingBy(SubjectHwStatistics::getSubjectId));
		List<SubjectHwStatistics> list = new ArrayList<SubjectHwStatistics>();
		map.keySet().forEach(k -> {
			SubjectHwStatistics item = new SubjectHwStatistics();
			item.setSubjectId(k);
			item.setSubjectName(map.get(k).get(0).getSubjectName());
			item.setBugFixNum(map.get(k).stream().collect(summarizingLong(SubjectHwStatistics::getBugFixNum)).getSum());
			item.setUnfinishNum(
					map.get(k).stream().collect(summarizingLong(SubjectHwStatistics::getUnfinishNum)).getSum());
			list.add(item);
		});
		list.sort((a, b) -> a.getSubjectId().compareTo(b.getSubjectId()));
		return list;
	}

	@Override
	public Map<String, Integer> findPaperQuestionNums(List<String> paperIds) {
		return this.homeworkPaperDao.findPaperQuestionNums(paperIds);
	}

	@Override
	public HomeworkPaper getHomeworkPaper(String paperId) {
		return this.homeworkPaperDao.getByPaperId(paperId);
	}

	@Override
	public HomeworkDtlInfo getHomeworkDtlInfoById(Long homeworkDtlId) {
		return this.homeworkDtlDao.getHomeworkDtlInfoById(homeworkDtlId);
	}

	@Override
	public List<MyHomeworkDTO> findHomeworkDtlByIds(List<Long> homeworkDtlIds) {
		return this.homeworkDtlDao.findHomeworkDtlByIds(homeworkDtlIds);
	}

	@Override
	public Integer getStuToFinishHwTotal(Long studentId) {
		return this.homeworkDtlDao.getStuToFinishHwTotal(studentId);
	}

	@Override
	public List<Long> findStuHwDtlId(List<Long> hwIds, Long studentId) {
		return this.homeworkDtlDao.findStuHwDtlId(hwIds, studentId);
	}

	/**
	 * 手机作业数据查询
	 * @param query
	 * @return
	 */
	public List<MPersonWorkDTO> findMobilePersonWorkList(MPersonWorkQuery query) {
		return this.homeworkDtlDao.findMobilePersonWorkList(query);
	}

	/**
	 * 手机作业计数查询
	 * @param query
	 * @return
	 */
	public MPersonWorkCount findMobilePersonWorkCount(MPersonWorkQuery query) {
		return this.homeworkDtlDao.findMobilePersonWorkCount(query);
	}

	@Override
	public void doHomeworkCompleteWithTx(Long lessonId) {
		LessonVM lesson = this.lessonRemoteService.getLessonVMByLessonId(lessonId);
		List<Long> userIds = lessonRemoteService.findStudentIdsByLessonId(lessonId);
		if (userIds.isEmpty()) {
			return;
		}
		List<Long> homeworkIds = homeworkDao.findPreviewHomeworkIdsByLessonId(lessonId);
		int total = homeworkIds.size();
		if (total == 0) {
			return;
		}
		List<StudentReviewInfo> reviewInfos = homeworkDtlDao.findStudentReviewFinishByHomeworkIds(homeworkIds);
		if (!reviewInfos.isEmpty()) {
			Set<Long> finishUserIds = reviewInfos.stream().filter(v -> v.getFinish() == total)
					.map(StudentReviewInfo::getUserId).collect(toSet());
			List<DynamicInfo> dynamicInfos = finishUserIds.stream().map(userId -> {
				return this.buildDynamicInfo(userId, lesson.getCourseSingleName(), lesson.getSchoolId(),
						IncentiveTypes.STUDENT.BK_FINISH_PRE_RE_CW);
			}).collect(toList());
			userIds.stream().filter(v -> !finishUserIds.contains(v)).forEach(userId -> {
				dynamicInfos.add(this.buildDynamicInfo(userId, lesson.getCourseSingleName(), lesson.getSchoolId(),
						IncentiveTypes.STUDENT.BK_UNFINISH_PRE_RE_CW));
			});
			DynamicHelper.publish(dynamicInfos);
		}
	}

	private DynamicInfo buildDynamicInfo(Long userId, String title, Long schoolId, Long typeId) {
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(userId);
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(schoolId);
		dynamicInfo.setTitle(title);
		dynamicInfo.setTypeId(typeId);
		return dynamicInfo;
	}

	@Override
	public List<StudentReviewInfo> findStudentReviewInfosByLessonId(Long lessonId) {
		List<Long> homeworkIds = homeworkDao.findPreviewHomeworkIdsByLessonId(lessonId);
		int total = homeworkIds.size();
		if (total == 0) {
			return Collections.emptyList();
		}
		List<StudentReviewInfo> reviewInfos = homeworkDtlDao.findStudentReviewFinishByHomeworkIds(homeworkIds);
		if (!reviewInfos.isEmpty()) {
			reviewInfos.forEach(reviewInfo -> reviewInfo.setState(reviewInfo.getFinish() == total ? 1 : 2));
			List<StudentReviewInfo> reviewScores = this.homeworkDtlDao.findStudentReviewScoreByCourseSingleId(lessonId);
			Map<Long, Integer> scoreMap = reviewScores.stream()
					.collect(toMap(StudentReviewInfo::getUserId, StudentReviewInfo::getScore));
			reviewInfos.forEach(reviewInfo -> reviewInfo.setScore(scoreMap.get(reviewInfo.getUserId())));
		}
		return reviewInfos;
	}

	/**
	 * 查询学生作业列表（pad）
	 * @param homeworkQuery
	 * @param page
	 * @return
	 */
	@Override
	public List<HDStuHomeworkListDTO> queryStuHomeworkListData(StudentHomeworkQuery homeworkQuery, Page page) {
		return homeworkDtlDao.selectStuHomeworkListData(homeworkQuery, page);
	}
	
	@Override
	public List<MHomeworkDtlInfo> findMHwInfo(List<Long> homeworkIds, Long studentId) {
		return this.homeworkDtlDao.findMHwInfo(homeworkIds, studentId);
	}
}
