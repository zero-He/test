package cn.strong.leke.diag.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.homework.mongo.UserStatsDao;
import cn.strong.leke.diag.model.ClassStatsDto;
import cn.strong.leke.diag.model.GradeStatsDto;
import cn.strong.leke.diag.model.SubjStatsDto;
import cn.strong.leke.diag.model.UserStats;
import cn.strong.leke.diag.model.UserStatsDto;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Component
public class SchoolStatsService {

	@Resource
	private UserStatsDao userStatsDao;
	@Resource
	private IKlassRemoteService klassRemoteService;

	/**
	 * 获取用户统计数据
	 * @param userId 用户ID
	 * @return
	 */
	public UserStats getUserStatsByUserId(Long userId) {
		return this.userStatsDao.getUserStatsByUserId(userId);
	}

	/**
	 * 查询学校勤奋报告。
	 * @param schoolId 学校ID
	 * @return
	 */
	public List<GradeStatsDto> findSchoolDiligent(Long schoolId) {
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		List<GradeRemote> gradeList = new ArrayList<GradeRemote>();
		for (Long schoolStageId : school.getSchoolStageIds()) {
			gradeList.addAll(SchoolStageContext.getSchoolStage(schoolStageId).getGrades());
		}
		List<GradeStatsDto> gradeStatsDtoList = this.userStatsDao.findSchoolDiligent(schoolId);
		Function<GradeRemote, GradeStatsDto> function = grade -> {
			Optional<GradeStatsDto> optional = gradeStatsDtoList.stream()
					.filter(dto -> dto.getGradeId().equals(grade.getGradeId())).findFirst();
			optional.ifPresent(dto -> dto.setGradeName(grade.getGradeName()));
			return optional.orElseGet(() -> {
				GradeStatsDto dto = new GradeStatsDto();
				dto.setGradeId(grade.getGradeId());
				dto.setGradeName(grade.getGradeName());
				dto.setStatNum(0);
				return dto;
			});
		};
		return gradeList.stream().map(function).collect(Collectors.toList());
	}

	/**
	 * 年级勤奋报告。
	 * @param schoolId 学校ID
	 * @param gradeId 年级ID
	 * @return
	 */
	public List<ClassStatsDto> findGradeDiligent(Long schoolId, Long gradeId) {
		ClazzQuery query = new ClazzQuery();
		query.setSchoolId(schoolId);
		query.setGradeId(gradeId);
		query.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		List<ClassStatsDto> list = this.userStatsDao.findGradeDiligent(schoolId, gradeId);
		Function<Clazz, ClassStatsDto> function = clazz -> {
			Optional<ClassStatsDto> optional = list.stream().filter(dto -> dto.getClassId().equals(clazz.getClassId()))
					.findFirst();
			optional.ifPresent(dto -> dto.setClassName(clazz.getClassName()));
			return optional.orElseGet(() -> {
				ClassStatsDto dto = new ClassStatsDto();
				dto.setClassId(clazz.getClassId());
				dto.setClassName(clazz.getClassName());
				dto.setStatNum(0);
				return dto;
			});
		};
		return clazzList.stream().map(function).collect(Collectors.toList());
	}

	/**
	 * 查询班级勤奋报告。
	 * @param classId 班级ID
	 * @return
	 */
	public List<UserStatsDto> findClassDillgent(Long classId) {
		List<UserStatsDto> list = this.userStatsDao.findClassDillgent(classId);
		List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(classId);
		Function<Long, UserStatsDto> function = userId -> {
			Optional<UserStatsDto> optional = list.stream().filter(dto -> userId.equals(dto.getUserId())).findFirst();
			return optional.orElseGet(() -> {
				UserStatsDto dto = new UserStatsDto();
				dto.setStatNum(0);
				dto.setUserId(userId);
				UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
				dto.setUserName(userBase.getUserName());
				return dto;
			});
		};
		return userIds.stream().map(function).collect(Collectors.toList());
	}

	/**
	 * 学生学科勤奋报告。
	 * @param userId 学生ID
	 * @return
	 */
	public List<SubjStatsDto> findStudentSubjDillgent(Long userId) {
		UserStats userStats = this.userStatsDao.getUserStatsByUserId(userId);
		if (userStats == null) {
			return null;
		}
		if (userStats.getGradeId() != null) {
			SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStageByGradeId(userStats.getGradeId());
			Function<SubjectRemote, SubjStatsDto> function = subject -> {
				Optional<UserStats.SubjWork> optional = userStats.getWorks().stream()
						.filter(dto -> dto.getSubjectId().equals(subject.getSubjectId())).findFirst();
				SubjStatsDto dto = new SubjStatsDto();
				dto.setSubjectId(subject.getSubjectId());
				dto.setSubjectName(subject.getSubjectName());
				if (optional.isPresent()) {
					UserStats.SubjWork work = optional.get();
					dto.setStatNum(optional.get().getTotalNum());
					double totalNum = work.getTotalNum();
					dto.setSubmitRate((work.getNormalNum() + work.getDelayNum()) * 100 / totalNum);
					dto.setDelayRate(work.getDelayNum() * 100 / totalNum);
					dto.setUndoneRate(100 - dto.getSubmitRate());
				} else {
					dto.setStatNum(0);
				}
				return dto;
			};
			return schoolStage.getSubjects().stream().map(function).collect(Collectors.toList());
		} else {
			return userStats.getWorks().stream().map(work -> {
				SubjStatsDto dto = new SubjStatsDto();
				dto.setSubjectId(work.getSubjectId());
				dto.setSubjectName(work.getSubjectName());
				dto.setStatNum(work.getTotalNum());
				double totalNum = work.getTotalNum();
				dto.setSubmitRate((work.getNormalNum() + work.getDelayNum()) * 100 / totalNum);
				dto.setDelayRate(work.getDelayNum() * 100 / totalNum);
				dto.setUndoneRate(100 - dto.getSubmitRate());
				return dto;
			}).collect(Collectors.toList());
		}
	}

	private List<SubjStatsDto> mergeSubjStatsDtoList(Long gradeId, List<SubjStatsDto> list) {
		SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStageByGradeId(gradeId);
		Function<SubjectRemote, SubjStatsDto> function = subject -> {
			Optional<SubjStatsDto> optional = list.stream()
					.filter(dto -> dto.getSubjectId().equals(subject.getSubjectId())).findFirst();
			optional.ifPresent(dto -> dto.setSubjectName(subject.getSubjectName()));
			return optional.orElseGet(() -> {
				SubjStatsDto dto = new SubjStatsDto();
				dto.setSubjectId(subject.getSubjectId());
				dto.setSubjectName(subject.getSubjectName());
				dto.setStatNum(0);
				return dto;
			});
		};
		return schoolStage.getSubjects().stream().map(function).collect(Collectors.toList());
	}

	/**
	 * 年级成绩分析
	 * @param schoolId 学校ID
	 * @param gradeId 年级ID
	 * @return
	 */
	public List<SubjStatsDto> findGradeSubjScore(Long schoolId, Long gradeId) {
		List<SubjStatsDto> list = this.userStatsDao.findGradeSubjScore(schoolId, gradeId);
		return this.mergeSubjStatsDtoList(gradeId, list);
	}

	/**
	 * 查询年级下各班级某一学科的得分统计。
	 * @param schoolId 学校ID
	 * @param gradeId 年级ID
	 * @param subjectId 学科ID
	 * @return
	 */
	public List<ClassStatsDto> findGradeClassScore(Long schoolId, Long gradeId, Long subjectId) {
		ClazzQuery query = new ClazzQuery();
		query.setSchoolId(schoolId);
		query.setGradeId(gradeId);
		query.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		List<ClassStatsDto> list = this.userStatsDao.findGradeClassScore(schoolId, gradeId, subjectId);
		Function<Clazz, ClassStatsDto> function = clazz -> {
			Optional<ClassStatsDto> optional = list.stream().filter(dto -> dto.getClassId().equals(clazz.getClassId()))
					.findFirst();
			optional.ifPresent(dto -> dto.setClassName(clazz.getClassName()));
			return optional.orElseGet(() -> {
				ClassStatsDto dto = new ClassStatsDto();
				dto.setClassId(clazz.getClassId());
				dto.setClassName(clazz.getClassName());
				dto.setStatNum(0);
				return dto;
			});
		};
		return clazzList.stream().map(function).collect(Collectors.toList());
	}

	/**
	 * 班级成绩分析(学科视角)
	 * @param classId 班级ID
	 * @return
	 */
	public List<SubjStatsDto> findClassSubjScore(Long gradeId, Long classId) {
		List<SubjStatsDto> list = this.userStatsDao.findClassSubjScore(classId);
		return this.mergeSubjStatsDtoList(gradeId, list);
	}

	/**
	 * 班级成绩分析(学生视角)
	 * @param classId 班级ID
	 * @param subjectId 学科ID
	 * @return
	 */
	public List<UserStatsDto> findClassUserScore(Long classId, Long subjectId) {
		List<UserStatsDto> list = this.userStatsDao.findClassUserScore(classId, subjectId);
		List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(classId);
		Function<Long, UserStatsDto> function = userId -> {
			Optional<UserStatsDto> optional = list.stream().filter(dto -> userId.equals(dto.getUserId())).findFirst();
			return optional.orElseGet(() -> {
				UserBase userBase = UserBaseContext.getUserBaseByUserId(userId);
				UserStatsDto dto = new UserStatsDto();
				dto.setUserId(userId);
				dto.setUserName(userBase.getUserName());
				dto.setStatNum(0);
				return dto;
			});
		};
		return userIds.stream().map(function).collect(Collectors.toList());
	}

	/**
	 * 学生成绩分析。
	 * 包含班级成绩。
	 * @param userId 用户ID
	 * @return
	 */
	public List<SubjStatsDto> findStudentSubjScore(Long userId) {
		UserStats userStats = this.userStatsDao.getUserStatsByUserId(userId);
		if (userStats == null) {
			return null;
		}
		if (userStats.getClassId() != null) {
			List<SubjStatsDto> list = this.findClassSubjScore(userStats.getGradeId(), userStats.getClassId());
			list.forEach(dto -> {
				Optional<UserStats.SubjScore> optional = userStats.getScores().stream()
						.filter(score -> score.getSubjectId().equals(dto.getSubjectId())).findFirst();
				if (optional.isPresent()) {
					UserStats.SubjScore score = optional.get();
					dto.setStuScore(score.getTotalScore() / score.getTotalNum());
				}
			});
			return list;
		} else {
			return userStats.getScores().stream().map(score -> {
				SubjStatsDto dto = new SubjStatsDto();
				dto.setSubjectId(score.getSubjectId());
				dto.setSubjectName(score.getSubjectName());
				dto.setStatNum(score.getTotalNum());
				dto.setStuScore(score.getTotalScore() / score.getTotalNum());
				return dto;
			}).collect(Collectors.toList());
		}
	}

	/**
	 * 学生个人成长曲线列表数据。
	 * 不包含班级成绩。
	 * @param userId 学生ID
	 * @return
	 */
	public List<SubjStatsDto> findStudentSubjScore2(Long userId) {
		UserStats userStats = this.userStatsDao.getUserStatsByUserId(userId);
		if (userStats == null) {
			return null;
		}
		if (userStats.getGradeId() != null) {
			SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStageByGradeId(userStats.getGradeId());
			Function<SubjectRemote, SubjStatsDto> function = subject -> {
				Optional<UserStats.SubjScore> optional = userStats.getScores().stream()
						.filter(dto -> dto.getSubjectId().equals(subject.getSubjectId())).findFirst();
				SubjStatsDto dto = new SubjStatsDto();
				dto.setSubjectId(subject.getSubjectId());
				dto.setSubjectName(subject.getSubjectName());
				if (optional.isPresent()) {
					UserStats.SubjScore score = optional.get();
					dto.setStatNum(score.getTotalNum());
					dto.setMaxScore(score.getMaxScore());
					dto.setAvgScore(score.getTotalScore() / score.getTotalNum());
				} else {
					dto.setStatNum(0);
				}
				return dto;
			};
			return schoolStage.getSubjects().stream().map(function).collect(Collectors.toList());
		} else {
			if (CollectionUtils.isEmpty(userStats.getScores())) {
				return null;
			}
			return userStats.getScores().stream().map(score -> {
				SubjStatsDto dto = new SubjStatsDto();
				dto.setSubjectId(score.getSubjectId());
				dto.setSubjectName(score.getSubjectName());
				dto.setStatNum(score.getTotalNum());
				dto.setMaxScore(score.getMaxScore());
				dto.setAvgScore(score.getTotalScore() / score.getTotalNum());
				return dto;
			}).collect(Collectors.toList());
		}
	}

	/**
	 * 学生成绩分析。
	 * 包含班级平均分，没成功的学科不包含。
	 * @param userId 用户ID
	 * @return
	 */
	public List<SubjStatsDto> findStudentSubjScore3(Long userId) {
		UserStats userStats = this.userStatsDao.getUserStatsByUserId(userId);
		if (userStats == null) {
			return null;
		}
		List<SubjStatsDto> list = userStats.getScores().stream().map(score -> {
			SubjStatsDto dto = new SubjStatsDto();
			dto.setSubjectId(score.getSubjectId());
			dto.setSubjectName(score.getSubjectName());
			dto.setStatNum(score.getTotalNum());
			dto.setStuScore(score.getTotalScore() / score.getTotalNum());
			return dto;
		}).collect(Collectors.toList());
		if (userStats.getClassId() != null) {
			List<SubjStatsDto> clist = this.userStatsDao.findClassSubjScore(userStats.getClassId());
			list.forEach(dto -> {
				Optional<SubjStatsDto> optional = clist.stream()
						.filter(score -> score.getSubjectId().equals(dto.getSubjectId())).findFirst();
				if (optional.isPresent()) {
					dto.setAvgScore(optional.get().getAvgScore());
				}
			});
		}
		return list;
	}

	/**
	 * 查询班级下学生的成绩信息
	 * @param classId 班级ID
	 * @return
	 */
	public List<UserStats> findClassUserStats(Long classId) {
		return this.userStatsDao.findClassUserStats(classId);
	}
}
