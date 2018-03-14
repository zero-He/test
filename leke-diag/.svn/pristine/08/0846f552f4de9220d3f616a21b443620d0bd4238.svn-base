package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.manage.WorkDetailService;
import cn.strong.leke.diag.model.StuSubjQuery;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.HomeOverallRptView;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.model.report.StuScoreRptView;
import cn.strong.leke.diag.model.report.StuScoreRptView.SubjScore;
import cn.strong.leke.diag.report.unit.GlobalScoreRankLogicalUnit;
import cn.strong.leke.diag.report.unit.LogicalContext;
import cn.strong.leke.diag.report.unit.StuScoreCompareLogicalUnit;
import cn.strong.leke.diag.report.unit.StuScoreOverallLogicalUnit;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.util.ScoreLevelUtils;
import cn.strong.leke.diag.util.ScoreUtils;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

/**
 * 学生综合成绩分析。
 * @author  andy
 */
@Component
public class HomeStuComboScoreReportHandler implements ReportHandler<Long, HomeOverallRptView> {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private ReportCycleService reportCycleService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	@Resource
	private ScoreTrendReportHandler scoreTrendReportHandler;

	private StuScoreOverallLogicalUnit stuScoreOverallLogicalUnit = new StuScoreOverallLogicalUnit();
	private StuScoreCompareLogicalUnit stuScoreCompareLogicalUnit = new StuScoreCompareLogicalUnit();
	private GlobalScoreRankLogicalUnit globalScoreRankLogicalUnit = new GlobalScoreRankLogicalUnit();

	@Override
	public HomeOverallRptView execute(Long userId) {
		Clazz clazz = this.getOrganClazzByUserId(userId);
		// 周期及班级人员查询
		ReportCycle reportCycle = this.reportCycleService.getMonthReportCycleByDate(new Date());
		Boolean isUnit;
		List<Long> userIds;
		if (clazz != null) {
			isUnit = false;
			userIds = this.klassRemoteService.findStudentIdsByClassId(clazz.getClassId());
		} else {
			isUnit = true;
			userIds = Lists.newArrayList(userId);
		}

		// 基本条件
		StuSubjQuery stuSubjQuery = new StuSubjQuery();
		stuSubjQuery.setStart(reportCycle.getStart());
		stuSubjQuery.setEnd(reportCycle.getEnd());
		stuSubjQuery.setUserIds(userIds);
		stuSubjQuery.setSubjectId(0L);

		List<StuSubjScore> scores = this.homeworkDtlService.findStuSubjScores(stuSubjQuery);
		List<StuScoreRptView.SubjMin> subjs = this.resolveSubjs(scores);

		// 学生试题正确数：{student: self, subject: self}
		Stream<StuSubjScore> stream = scores.stream().filter(v -> v.getUserId().equals(userId));
		List<Long> homeworkDtlIds = stream.map(StuSubjScore::getHomeworkDtlId).collect(toList());
		if (homeworkDtlIds.isEmpty()) {
			return new HomeOverallRptView(false, "暂无已批改的作业数据供分析，报告无法生成！");
		}

		LogicalContext context = new LogicalContext();
		context.put("subjs", subjs);
		context.put("scores", scores);
		context.put("userIds", userIds);
		context.put("subjectId", 0L);

		// 成绩排行榜
		List<ScoreRank> scoreRanks = this.globalScoreRankLogicalUnit.execute(context);
		ScoreRank scoreRank = scoreRanks.stream().filter(v -> v.getUserId().equals(userId)).findFirst().get();
		context.put("scoreRank", scoreRank);
		context.put("scoreRanks", scoreRanks);
		// 综合成绩信息
		List<SubjScore> subjScores = this.stuScoreCompareLogicalUnit.execute(context);
		context.put("subjScores", subjScores);
		StuScoreRptView.Overall overall = this.stuScoreOverallLogicalUnit.execute(context);

		HomeOverallRptView view = new HomeOverallRptView();
		view.setIsUnit(isUnit);
		view.setNames(subjScores.stream().map(v -> v.getLabel()).collect(toList()));
		view.setKlass(subjScores.stream().map(v -> v.getKlass()).collect(toList()));
		view.setSelfs(subjScores.stream().map(v -> v.getSelf()).collect(toList()));

		view.setScore(overall.getScore());
		view.setRank(overall.getRank());
		view.setRate(ScoreUtils.toRate(overall.getTotal() - overall.getRank(), overall.getTotal(), 1));
		view.setLevel(ScoreLevelUtils.toLevelName(overall.getScore()));

		view.setMaxes1(overall.getMaxes1());
		view.setMaxes2(overall.getMaxes2());
		view.setMines1(overall.getMines1());
		view.setMines2(overall.getMines2());
		return view;
	}

	private List<StuScoreRptView.SubjMin> resolveSubjs(List<StuSubjScore> scores) {
		List<Long> subjectIds = scores.stream().map(StuSubjScore::getSubjectId).distinct().sorted().collect(toList());
		List<SubjectRemote> subjectRemotes = SubjectContext.findSubjects(subjectIds);
		return subjectRemotes.stream().map(v -> new StuScoreRptView.SubjMin(v.getSubjectId(), v.getSubjectName()))
				.collect(toList());
	}

	/**
	 * 获取学生行政班信息
	 * @param userId
	 * @return
	 */
	private Clazz getOrganClazzByUserId(Long userId) {
		ClazzQuery query = new ClazzQuery();
		query.setUserId(userId);
		query.setRoleId(RoleCst.STUDENT);
		query.setType(Clazz.CLAZZ_ORGAN);
		List<Clazz> clazzList = this.klassRemoteService.findClazzByQuery(query);
		return CollectionUtils.getFirst(clazzList);
	}
}
