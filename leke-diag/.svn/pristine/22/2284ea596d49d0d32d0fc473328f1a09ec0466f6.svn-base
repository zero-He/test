package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.manage.WorkDetailService;
import cn.strong.leke.diag.model.StuKnoRate;
import cn.strong.leke.diag.model.StuSubjQuery;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.ClsSubjRptView;
import cn.strong.leke.diag.model.report.KnoGraspRate;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.model.report.ScoreRank;
import cn.strong.leke.diag.model.report.ScoreRptQuery;
import cn.strong.leke.diag.model.report.TrendModel;
import cn.strong.leke.diag.model.tchanaly.TchanalyKlassScoreRptView;
import cn.strong.leke.diag.model.tchanaly.TchanalyRptQuery;
import cn.strong.leke.diag.report.unit.ClassKnoRateLogicalUnit;
import cn.strong.leke.diag.report.unit.ClassSubjSummaryLogicalUnit;
import cn.strong.leke.diag.report.unit.LogicalContext;
import cn.strong.leke.diag.report.unit.SubjScoreRankLogicalUnit;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

/**
 * 班级成绩分析。
 * @author  andy
 */
@Component
public class TchanalyKlassScoreReportHandler implements ReportHandler<TchanalyRptQuery, TchanalyKlassScoreRptView> {

	@Resource
	private HomeworkService homeworkService;
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

	private ClassSubjSummaryLogicalUnit classSubjSummaryLogicalUnit = new ClassSubjSummaryLogicalUnit();
	private SubjScoreRankLogicalUnit subjScoreRankLogicalUnit = new SubjScoreRankLogicalUnit();
	private ClassKnoRateLogicalUnit classKnoRateLogicalUnit = new ClassKnoRateLogicalUnit();

	@Override
	public TchanalyKlassScoreRptView execute(TchanalyRptQuery query) {
		List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(query.getClassId());
		if (CollectionUtils.isEmpty(userIds)) {
			return new TchanalyKlassScoreRptView(false, "本班级暂无学生，无数据可供分析！");
		}
		ReportCycle reportCycle = this.reportCycleService.getReportCycleById(query.getCycleId());
		StuSubjQuery stuSubjQuery = new StuSubjQuery();
		stuSubjQuery.setUserIds(userIds);
		stuSubjQuery.setSubjectId(query.getSubjectId());
		stuSubjQuery.setStart(reportCycle.getStart());
		stuSubjQuery.setEnd(reportCycle.getEnd());
		List<StuSubjScore> scores = this.homeworkDtlService.findStuSubjScores(stuSubjQuery);
		if (CollectionUtils.isEmpty(scores)) {
			return new TchanalyKlassScoreRptView(false, "暂无已批改的作业数据供分析，报告无法生成！");
		}
		List<UserBase> userBaseList = UserBaseContext.findUserBaseByUserId(userIds.toArray(new Long[0]));

		List<Long> homeworkDtlIds = scores.stream().map(StuSubjScore::getHomeworkDtlId).collect(toList());
		List<StuKnoRate> rates = this.workDetailService.findStuKnoRates(homeworkDtlIds);

		LogicalContext context = new LogicalContext();
		context.put("query", query);
		context.put("scores", scores);
		context.put("rates", rates);
		context.put("userBaseList", userBaseList);

		// 成绩排行榜
		List<ScoreRank> scoreRanks = this.subjScoreRankLogicalUnit.execute(context);
		// 概要信息
		context.put("scoreRanks", scoreRanks);
		ClsSubjRptView.Summary summary = this.classSubjSummaryLogicalUnit.execute(context);
		// 成绩走势信息
		ScoreRptQuery scoreQuery = new ScoreRptQuery();
		scoreQuery.setCycleId(query.getCycleId());
		scoreQuery.setClassId(query.getClassId());
		scoreQuery.setSubjectId(query.getSubjectId());
		List<TrendModel> trends = scoreTrendReportHandler.execute(scoreQuery);
		// 知识点分析
		List<KnoGraspRate> knoRates = this.classKnoRateLogicalUnit.execute(context);

		TchanalyKlassScoreRptView view = new TchanalyKlassScoreRptView();
		view.setRptType(reportCycle.getType());
		view.setSummary(summary);
		view.setScoreRanks(scoreRanks);
		view.setKnoRates(knoRates);
		view.setTrends(trends);

		return view;
	}
}
