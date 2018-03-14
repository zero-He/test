package cn.strong.leke.diag.chart.build;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.ContributeStatChartReq;
import cn.strong.leke.framework.spring.MessageSource;
import cn.strong.leke.remote.model.question.SchoolQuestionContribution;
import cn.strong.leke.remote.model.question.SchoolQuestionContributionQuery;
import cn.strong.leke.remote.service.question.IQuestionRemoteService;

/**
 * 教师题库贡献统计。
 * @author  andy
 * @created 2014年8月6日 下午1:58:11
 * @since   v1.0.0
 */
@Component("chart_contribute")
public class ContributeChartBuilder implements ChartBuilder<ContributeStatChartReq> {

	@Resource
	private IQuestionRemoteService questionRemoteService;

	@Override
	public ChartRes build(ContributeStatChartReq chartDto) {
		BarChartRes chartRes = new BarChartRes();

		Long schoolId = UserContext.user.getCurrentSchoolId();
		Date startTime = DateUtils.toDayStartTime(chartDto.getStartTime());
		Date endTime = DateUtils.toDayEndTime(chartDto.getEndTime());
		SchoolQuestionContributionQuery query = new SchoolQuestionContributionQuery();
		query.setSchoolId(schoolId);
		query.setMinCreatedOn(startTime);
		query.setMaxCreatedOn(endTime);
		List<SchoolQuestionContribution> contributeList = this.questionRemoteService
				.findSchoolQuestionContributions(query);
		if (contributeList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		Data xAxis = new Data();
		Data series1 = new Data();
		Data series2 = new Data();
		for (SchoolQuestionContribution contribute : contributeList) {
			if (contribute.getTeacherName() == null) {
				contribute.setTeacherName("");
			}
			xAxis.addItem(contribute.getTeacherName());
			series1.addItem(contribute.getTotalCount());
			series2.addItem(contribute.getTotalPaperCount());
		}

		chartRes.addxAxis(xAxis);
		chartRes.addSeries(series1);
		chartRes.addSeries(series2);

		return chartRes;
	}
}
