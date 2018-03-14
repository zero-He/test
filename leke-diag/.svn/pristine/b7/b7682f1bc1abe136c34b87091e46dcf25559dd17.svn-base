package cn.strong.leke.diag.chart.build;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.PieChartRes;
import cn.strong.leke.diag.chart.model.HwScoreStatChartReq;
import cn.strong.leke.diag.model.HomeworkDtl;
import cn.strong.leke.diag.service.HomeworkDtlService;

@Component("chart_hwScoreStat")
public class HwScoreStatChartBuilder implements ChartBuilder<HwScoreStatChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public ChartRes build(HwScoreStatChartReq chartDto) {
		PieChartRes chart = new PieChartRes();
		int O = chartDto.getTotal();
		int A = chartDto.getA();
		int B = chartDto.getB();
		int C = chartDto.getC();
		int D = 0, AN = 0, BN = 0, CN = 0, DN = 0;

		List<HomeworkDtl> homeworkDtlList = this.homeworkDtlService.findHomeworkDtlByHomeworkId(chartDto
				.getHomeworkId());
		homeworkDtlList = homeworkDtlList.stream().filter(v -> v.getCorrectTime() != null).collect(toList());
		chart.addRawData("homeworkDtlList", homeworkDtlList);
		if (homeworkDtlList.isEmpty()) {
			chart.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chart;
		}
			
		for (HomeworkDtl homeworkDtl : homeworkDtlList) {
			double score = homeworkDtl.getScore().doubleValue();
			if (score >= A) {
				AN++;
			} else if (score >= B) {
				BN++;
			} else if (score >= C) {
				CN++;
			} else {
				DN++;
			}
		}

		// 组合生成数据
		Data data = new Data();
		data.addItem(this.structSection(chart, "优秀", O, A, AN));
		data.addItem(this.structSection(chart, "良好", A, B, BN));
		data.addItem(this.structSection(chart, "合格", B, C, CN));
		data.addItem(this.structSection(chart, "不合格", C, D, DN));
		chart.getSeries().add(data);

		BigDecimal totalNum = new BigDecimal(homeworkDtlList.size());
		Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
		rateMap.put("rateA", new BigDecimal(AN * 100).divide(totalNum, 2, RoundingMode.HALF_UP));
		rateMap.put("rateB", new BigDecimal((AN + BN) * 100).divide(totalNum, 2, RoundingMode.HALF_UP));
		rateMap.put("rateC", new BigDecimal((AN + BN + CN) * 100).divide(totalNum, 2, RoundingMode.HALF_UP));
		chart.addRawData("rateMap", rateMap);

		return chart;
	}

	private Map<String, Object> structSection(PieChartRes chart, String name, Integer up, Integer down, Integer totalNum) {
		Map<String, Object> item = new HashMap<String, Object>();
		String label = down + "-" + up + name;
		chart.getLegend().addItem(label);
		item.put("name", label);
		item.put("value", totalNum);
		return item;
	}
}
