package cn.strong.leke.diag.chart.build;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.GradeScoreChartReq;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Component("chart_gradeScoreStat")
public class GradeScoreChartBuilder implements ChartBuilder<GradeScoreChartReq> {

	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public ChartRes build(GradeScoreChartReq chartDto) {
		User user = UserContext.user.get();
		BarChartRes chartRes = new BarChartRes();

		List<Clazz> clazzList = this.findClazzes(chartDto, user);
		chartRes.addRawData("dataList",clazzList);
		if (CollectionUtils.isEmpty(clazzList)) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}

		Data xData = new Data();
		Data avgData = new Data();

		for (Clazz clazz : clazzList) {
			xData.addItem(clazz.getClassName());
			List<Long> userIds = this.klassRemoteService.findStudentIdsByClassId(clazz.getClassId());
			BigDecimal avgScore = null;
			if (CollectionUtils.isNotEmpty(userIds)) {
				avgScore = this.homeworkDtlService.getUsersAvgScore(userIds, chartDto.getSubjectId(),
						chartDto.getStartTime(), chartDto.getEndTime());
			}
			if (avgScore != null) {
				avgData.addItem(avgScore);
			} else {
				avgData.addItem(NULL_BROKEN_LINE_PLACEHOLDER);
			}
		}

		chartRes.addxAxis(xData);
		chartRes.addSeries(avgData);

		return chartRes;
	}

	private List<Clazz> findClazzes(GradeScoreChartReq chartDto, User user) {
		ClazzQuery query = new ClazzQuery();
		query.setType(Clazz.CLAZZ_ORGAN);
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		query.setGradeId(chartDto.getGradeId());
		return this.klassRemoteService.findClazzByQuery(query);
	}
}
