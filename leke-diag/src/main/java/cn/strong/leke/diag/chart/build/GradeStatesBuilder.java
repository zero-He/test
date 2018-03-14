package cn.strong.leke.diag.chart.build;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.GradeStatesChartReq;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.ClassStatsDto;
import cn.strong.leke.diag.service.HomeworkDtlService;

@Component("chart_gradeStates")
public class GradeStatesBuilder implements ChartBuilder<GradeStatesChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private SchoolStatsService SchoolStatsService;
	

	@Override
	public ChartRes build(GradeStatesChartReq chartDto) {
		//从 mongodb 中读取数据
		BarChartRes chartRes = new BarChartRes();
		//params
		Long schoolId = chartDto.getSchoolId();
		Long gradeId = chartDto.getGradeId();
		List<ClassStatsDto> dataList = SchoolStatsService.findGradeDiligent(schoolId, gradeId);
        if(dataList.size() == 0){
        	chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
        	return chartRes;
        }
		//x轴 data 显示班级名称
		Data xData = new Data();
		//seriesData
		//提交率
		Data submitData = new Data();
		//延迟率
		Data delayData = new Data();
		//未交率
		Data unSubmitData = new Data();
		//填充数据
		for (ClassStatsDto item : dataList) {
			xData.addItem(item.getClassName());
			submitData.addItem(item.getSubmitRate() == null ? 0d : new DecimalFormat("#0.##").format(item
					.getSubmitRate()));
			delayData
					.addItem(item.getDelayRate() == null ? 0d : new DecimalFormat("#0.##").format(item.getDelayRate()));
			unSubmitData.addItem(item.getUndoneRate() == null ? 0d : new DecimalFormat("#0.##").format(item
					.getUndoneRate()));
		}
		chartRes.addxAxis(xData);
		chartRes.addSeries(submitData);
		chartRes.addSeries(delayData);
		chartRes.addSeries(unSubmitData);
		return chartRes;
	}
	
}
