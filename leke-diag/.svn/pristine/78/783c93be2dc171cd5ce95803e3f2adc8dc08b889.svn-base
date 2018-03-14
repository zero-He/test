package cn.strong.leke.diag.chart.build;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.TimePieChartRes;
import cn.strong.leke.core.business.chart.TimePieChartRes.Option;
import cn.strong.leke.core.business.chart.TimePieChartRes.Series;
import cn.strong.leke.core.business.chart.TimePieChartRes.Title;
import cn.strong.leke.diag.chart.model.AnalysisChartReq;
import cn.strong.leke.diag.model.SubjectAnalysisDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

/**
 * 学科优劣分析（班主任）
 * @author  andy
 * @created 2014年8月6日 下午1:57:10
 * @since   v1.0.0
 */
@Component("chart_clsAnalysis")
public class ClsAnalysisChartBuilder implements ChartBuilder<AnalysisChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public ChartRes build(AnalysisChartReq chartDto) {
		TimePieChartRes chartRes = new TimePieChartRes();
		
		List<SubjectRemote> subjectList = UserContext.user.findSubjectsOfCurrentSchool();
		Map<String, String> subjectMap = new HashMap<String, String>();
		for (SubjectRemote subject : subjectList) {
			subjectMap.put(String.valueOf(subject.getSubjectId()), subject.getSubjectName());
		}

		List<SubjectAnalysisDto> subjectAnalysisDtoList = this.homeworkDtlService.findSubjectAnalysisDtoList(
				chartDto.getClassType(), chartDto.getClassId());
		if (subjectAnalysisDtoList.isEmpty()) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}
		
		int index = 1;
		Data timelineData = new Data();
		Map<String, String> labelMap = new HashMap<String, String>();
		for (SubjectAnalysisDto subjectAnalysisDto : subjectAnalysisDtoList) {
			String labelValue = String.valueOf(index++);
			Long subjectId = subjectAnalysisDto.getSubjectId();
			String labelName = subjectMap.get(String.valueOf(subjectId));
			labelMap.put(labelValue, labelName);
			timelineData.addItem(labelValue);

			Data data = new Data();
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("name", "A：85-100分");
			item.put("value", subjectAnalysisDto.getA());
			data.addItem(item);

			item = new HashMap<String, Object>();
			item.put("name", "B：70-85分");
			item.put("value", subjectAnalysisDto.getB());
			data.addItem(item);

			item = new HashMap<String, Object>();
			item.put("name", "C：60-70分");
			item.put("value", subjectAnalysisDto.getC());
			data.addItem(item);

			item = new HashMap<String, Object>();
			item.put("name", "D：0-60分");
			item.put("value", subjectAnalysisDto.getD());
			data.addItem(item);

			Option option = new Option();

			Title title = new Title();
			title.setText(labelName + "学科优劣分析");
			option.setTitle(title);

			Series series = new Series();
			series.setName(labelName + "学科优劣分析");
			series.setData(data.getData());
			option.addSeries(series);

			chartRes.addOption(option);
		}
		chartRes.setTimeline(timelineData);
		chartRes.addRawData("labelMap", labelMap);

		return chartRes;
	}
}
