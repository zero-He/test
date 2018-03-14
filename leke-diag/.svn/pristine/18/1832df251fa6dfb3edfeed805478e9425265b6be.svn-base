package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.manage.WorkDetailService;
import cn.strong.leke.diag.model.StuKnoRate;
import cn.strong.leke.diag.model.StuQueStat;
import cn.strong.leke.diag.model.StuSubjQuery;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.StuSubjWork;
import cn.strong.leke.diag.model.report.KnoGraspRate;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.model.report.ScoreRptQuery;
import cn.strong.leke.diag.model.report.StuScoreRptView;
import cn.strong.leke.diag.model.report.TrendModel;
import cn.strong.leke.diag.report.unit.ClassKnoRateLogicalUnit;
import cn.strong.leke.diag.report.unit.GlobalScoreRankLogicalUnit;
import cn.strong.leke.diag.report.unit.LogicalContext;
import cn.strong.leke.diag.report.unit.StuScoreSummaryLogicalUnit;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.model.microcourse.MicrocourseSelectQuery;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.microcourse.IMicrocourseRemoteService;

/**
 * 学生学科成绩分析。
 * @author  andy
 */
@Component
public class StuSubjScoreReportHandler implements ReportHandler<ScoreRptQuery, StuScoreRptView> {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private ReportCycleService reportCycleService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private IMicrocourseRemoteService microcourseRemoteService;

	@Resource
	private ScoreTrendReportHandler scoreTrendReportHandler;

	private StuScoreSummaryLogicalUnit stuScoreSummaryLogicalUnit = new StuScoreSummaryLogicalUnit();
	private GlobalScoreRankLogicalUnit globalScoreRankLogicalUnit = new GlobalScoreRankLogicalUnit();
	private ClassKnoRateLogicalUnit classKnoRateLogicalUnit = new ClassKnoRateLogicalUnit();

	@Override
	public StuScoreRptView execute(ScoreRptQuery query) {
		if (query.getClassId() == null) {
			return this.executeAsUnit(query);
		}
		// 周期及班级人员查询
		Long studentId = query.getStudentId();
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(query.getCycleId());
		if (reportCycle.getEnd().getTime() > System.currentTimeMillis()) {
			reportCycle.setEnd(new Date());
		}
		List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(query.getClassId());

		// 基本条件
		StuSubjQuery stuSubjQuery = new StuSubjQuery();
		stuSubjQuery.setStart(reportCycle.getStart());
		stuSubjQuery.setEnd(reportCycle.getEnd());

		// 学生提交：{student: self, subject: assign}
		stuSubjQuery.setSubjectId(query.getSubjectId());
		stuSubjQuery.setUserIds(Lists.newArrayList(query.getStudentId()));
		List<StuSubjWork> works = this.homeworkDtlService.findStuSubjWorks(stuSubjQuery);

		// 班级成绩：{student: all, subject: all}
		stuSubjQuery.setUserIds(userIds);
		stuSubjQuery.setSubjectId(0L);
		List<StuSubjScore> scores = this.homeworkDtlService.findStuSubjScores(stuSubjQuery);
		List<StuScoreRptView.SubjMin> subjs = this.resolveSubjs(scores);

		// 学生试题正确数：{student: self, subject: self}
		Stream<StuSubjScore> stream = scores.stream().filter(v -> v.getUserId().equals(studentId));
		if (query.getSubjectId() > 0) {
			stream = stream.filter(v -> v.getSubjectId().equals(query.getSubjectId()));
		}
		List<Long> homeworkDtlIds = stream.map(StuSubjScore::getHomeworkDtlId).collect(toList());
		if (homeworkDtlIds.isEmpty()) {
			return new StuScoreRptView(false, "暂无已批改的作业数据供分析，报告无法生成！");
		}
		StuQueStat stuQueStat = this.workDetailService.getStuQueStatByHomeworkDtlIds(homeworkDtlIds);

		// 查询班级试题正确率
		homeworkDtlIds = scores.stream().filter(v -> v.getSubjectId().equals(query.getSubjectId()))
				.map(v -> v.getHomeworkDtlId()).collect(toList());
		List<StuKnoRate> rates = this.workDetailService.findStuKnoRates(homeworkDtlIds);

		LogicalContext context = new LogicalContext();
		context.put("subjs", subjs);
		context.put("works", works);
		context.put("scores", scores);
		context.put("rates", rates);
		context.put("stuQueStat", stuQueStat);
		context.put("studentId", query.getStudentId());
		context.put("subjectId", query.getSubjectId());
		// 概要信息
		StuScoreRptView.Summary summary = this.stuScoreSummaryLogicalUnit.execute(context);
		// 成绩走势信息
		List<TrendModel> trends = this.scoreTrendReportHandler.execute(query);
		// 成绩排行榜
		List<ScoreRank> scoreRanks = this.globalScoreRankLogicalUnit.execute(context);
		// 知识点分析
		List<KnoGraspRate> knoRates = this.classKnoRateLogicalUnit.execute(context);
		// 微课推荐
		List<MicrocourseDTO> micros = this.getRecommendMicroCourses(knoRates);

		StuScoreRptView view = new StuScoreRptView();
		view.setIsUnit(false);
		view.setRptType(reportCycle.getType());
		view.setSubjs(subjs);
		view.setSummary(summary);
		view.setTrends(trends);
		view.setScoreRanks(scoreRanks);
		view.setKnoRates(knoRates);
		view.setMicros(micros);

		return view;
	}

	public StuScoreRptView executeAsUnit(ScoreRptQuery query) {
		// 周期及班级人员查询
		Long studentId = query.getStudentId();
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(query.getCycleId());
		if (reportCycle.getEnd().getTime() > System.currentTimeMillis()) {
			reportCycle.setEnd(new Date());
		}
		List<Long> userIds = Lists.newArrayList(query.getStudentId());

		// 基本条件
		StuSubjQuery stuSubjQuery = new StuSubjQuery();
		stuSubjQuery.setStart(reportCycle.getStart());
		stuSubjQuery.setEnd(reportCycle.getEnd());

		// 学生提交：{student: self, subject: assign}
		stuSubjQuery.setSubjectId(query.getSubjectId());
		stuSubjQuery.setUserIds(userIds);
		List<StuSubjWork> works = this.homeworkDtlService.findStuSubjWorks(stuSubjQuery);

		// 班级成绩：{student: all, subject: all}
		stuSubjQuery.setUserIds(userIds);
		stuSubjQuery.setSubjectId(0L);
		List<StuSubjScore> scores = this.homeworkDtlService.findStuSubjScores(stuSubjQuery);
		List<StuScoreRptView.SubjMin> subjs = this.resolveSubjs(scores);

		// 学生试题正确数：{student: self, subject: self}
		Stream<StuSubjScore> stream = scores.stream().filter(v -> v.getUserId().equals(studentId));
		if (query.getSubjectId() > 0) {
			stream = stream.filter(v -> v.getSubjectId().equals(query.getSubjectId()));
		}
		List<Long> homeworkDtlIds = stream.map(StuSubjScore::getHomeworkDtlId).collect(toList());
		if (homeworkDtlIds.isEmpty()) {
			return new StuScoreRptView(false, "暂无已批改的作业数据供分析，报告无法生成！");
		}
		StuQueStat stuQueStat = this.workDetailService.getStuQueStatByHomeworkDtlIds(homeworkDtlIds);

		// 查询班级试题正确率
		homeworkDtlIds = scores.stream().filter(v -> v.getSubjectId().equals(query.getSubjectId()))
				.map(v -> v.getHomeworkDtlId()).collect(toList());
		List<StuKnoRate> rates = this.workDetailService.findStuKnoRates(homeworkDtlIds);

		LogicalContext context = new LogicalContext();
		context.put("subjs", subjs);
		context.put("works", works);
		context.put("scores", scores);
		context.put("rates", rates);
		context.put("stuQueStat", stuQueStat);
		context.put("studentId", query.getStudentId());
		context.put("subjectId", query.getSubjectId());
		// 概要信息
		StuScoreRptView.Summary summary = this.stuScoreSummaryLogicalUnit.execute(context);
		// 成绩走势信息
		List<TrendModel> trends = this.scoreTrendReportHandler.execute(query);
		// 知识点分析
		List<KnoGraspRate> knoRates = this.classKnoRateLogicalUnit.execute(context);
		// this.fillKnoledgeName(knoRates);
		// 微课推荐
		List<MicrocourseDTO> micros = this.getRecommendMicroCourses(knoRates);

		StuScoreRptView view = new StuScoreRptView();
		view.setIsUnit(true);
		view.setRptType(reportCycle.getType());
		view.setSubjs(subjs);
		view.setSummary(summary);
		view.setTrends(trends);
		view.setKnoRates(knoRates);
		view.setMicros(micros);

		return view;
	}

	private List<MicrocourseDTO> getRecommendMicroCourses(List<KnoGraspRate> knoRates) {
		if (CollectionUtils.isEmpty(knoRates)) {
			return Lists.newArrayList();
		}
		List<Long> knoIds = knoRates.stream().map(KnoGraspRate::getId).collect(toList());
		MicrocourseSelectQuery query = new MicrocourseSelectQuery();
		query.setKnowledgeIds(knoIds);
		query.setMicrocourseNum(8);
		List<Long> microIds = this.microcourseRemoteService.queryRandomMicrocourseIds(query);
		Map<Long, MicrocourseDTO> microcourseMap = MicrocourseContext.findMicrocoursesAsMap(microIds);
		return new ArrayList<>(microcourseMap.values());
	}

	private List<StuScoreRptView.SubjMin> resolveSubjs(List<StuSubjScore> scores) {
		List<Long> subjectIds = scores.stream().map(StuSubjScore::getSubjectId).distinct().sorted().collect(toList());
		List<SubjectRemote> subjectRemotes = SubjectContext.findSubjects(subjectIds);
		return subjectRemotes.stream().map(v -> new StuScoreRptView.SubjMin(v.getSubjectId(), v.getSubjectName()))
				.collect(toList());
	}
}
