package cn.strong.leke.diag.report;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.beike.MicrocourseContext;
import cn.strong.leke.context.paper.PaperContext;
import cn.strong.leke.diag.manage.WorkDetailService;
import cn.strong.leke.diag.model.Homework;
import cn.strong.leke.diag.model.HomeworkDtl;
import cn.strong.leke.diag.model.WorkDetail;
import cn.strong.leke.diag.model.report.HwRptQuery;
import cn.strong.leke.diag.model.report.HwRptView;
import cn.strong.leke.diag.report.unit.HwKnoGroupScoreLogicalUnit;
import cn.strong.leke.diag.report.unit.HwQueGroupScoreLogicalUnit;
import cn.strong.leke.diag.report.unit.HwStuScoreRankLogicalUnit;
import cn.strong.leke.diag.report.unit.HwSummaryLogicalUnit;
import cn.strong.leke.diag.report.unit.LogicalContext;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.diag.util.ScoreLevelUtils;
import cn.strong.leke.diag.util.ScoreLevelUtils.ScoreLevel;
import cn.strong.leke.model.microcourse.MicrocourseSelectQuery;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.remote.service.microcourse.IMicrocourseRemoteService;

/**
 * 单份作业报告分析。
 * @author  andy
 * @since   v1.0.0
 */
@Component
public class HomeworkReportHandler implements ReportHandler<HwRptQuery, HwRptView> {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private WorkDetailService workDetailService;
	@Resource
	private IMicrocourseRemoteService microcourseRemoteService;

	private HwSummaryLogicalUnit hwSummaryLogicalUnit = new HwSummaryLogicalUnit();
	private HwStuScoreRankLogicalUnit hwStuScoreRankLogicalUnit = new HwStuScoreRankLogicalUnit();
	private HwKnoGroupScoreLogicalUnit hwKnoGroupScoreLogicalUnit = new HwKnoGroupScoreLogicalUnit();
	private HwQueGroupScoreLogicalUnit hwQueGroupScoreLogicalUnit = new HwQueGroupScoreLogicalUnit();

	@Override
	public HwRptView execute(HwRptQuery query) {
		Long homeworkId = query.getHomeworkId();
		Long homeworkDtlId = query.getHomeworkDtlId();
		Homework homework = this.homeworkService.getHomeworkByHomeworkId(homeworkId);
		List<HomeworkDtl> homeworkDtls = this.homeworkDtlService.findHomeworkDtlByHomeworkId(homeworkId);
		List<WorkDetail> details = this.workDetailService.findWorkDetailByHomeworkId(homeworkId);
		PaperDTO paperDTO = PaperContext.getPaperDTO(homework.getPaperId());
		BigDecimal totalScore = paperDTO.getDetail().getTotalScore();
		List<ScoreLevel> levels = ScoreLevelUtils.resolveScoreLevels(totalScore);
		Set<Long> homeworkDtlIds = homeworkDtls.stream().filter(v -> v.getCorrectTime() != null)
				.map(HomeworkDtl::getHomeworkDtlId).collect(toSet());
		details = details.stream().filter(v -> homeworkDtlIds.contains(v.getHomeworkDtlId())).collect(toList());

		// 基本数据
		LogicalContext context = new LogicalContext();
		context.put("homework", homework);
		context.put("homeworkDtls", homeworkDtls);
		context.put("details", details);
		context.put("paperDTO", paperDTO);

		// 学生数据提取
		if (homeworkDtlId != null) {
			homeworkDtls.stream().filter(v -> v.getHomeworkDtlId().equals(homeworkDtlId)).findFirst()
					.ifPresent(homeworkDtl -> context.put("homeworkDtl", homeworkDtl));
			details.stream().filter(v -> v.getHomeworkDtlId().equals(homeworkDtlId)).findFirst()
					.ifPresent(detail -> context.put("detail", detail));
		}

		// 概要信息
		HwRptView.Summary summary = this.hwSummaryLogicalUnit.execute(context);
		// 大题得分分析
		List<HwRptView.GroupModel> queGroups = this.hwQueGroupScoreLogicalUnit.execute(context);
		// 知识点得分分析
		List<HwRptView.GroupModel> knoGroups = this.hwKnoGroupScoreLogicalUnit.execute(context);
		// 成绩排行分析
		List<HwRptView.ScoreModel> scoreRanks = this.hwStuScoreRankLogicalUnit.execute(context);
		// 微课推荐
		List<MicrocourseDTO> micros = this.getRecommendMicroCourses(knoGroups);

		HwRptView view = new HwRptView();
		view.setSummary(summary);
		view.setTotalScore(totalScore);
		view.setLevels(levels);
		view.setQueGroups(queGroups);
		view.setKnoGroups(knoGroups);
		view.setScoreRanks(scoreRanks);
		view.setMicros(micros);

		return view;
	}

	private List<MicrocourseDTO> getRecommendMicroCourses(List<HwRptView.GroupModel> knoGroups) {
		if (CollectionUtils.isEmpty(knoGroups)) {
			return Lists.newArrayList();
		}
		List<Long> knoIds = knoGroups.stream().map(HwRptView.GroupModel::getId).collect(toList());
		MicrocourseSelectQuery query = new MicrocourseSelectQuery();
		query.setKnowledgeIds(knoIds);
		query.setMicrocourseNum(8);
		List<Long> microIds = this.microcourseRemoteService.queryRandomMicrocourseIds(query);
		Map<Long, MicrocourseDTO> microcourseMap = MicrocourseContext.findMicrocoursesAsMap(microIds);
		return new ArrayList<>(microcourseMap.values());
	}
}
