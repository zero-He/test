package cn.strong.leke.diag.chart.build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.PieChartRes;
import cn.strong.leke.diag.chart.model.AnalysisChartReq;
import cn.strong.leke.diag.model.StuAvgScoreDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.diag.util.DiagCst;
import cn.strong.leke.framework.spring.MessageSource;

/**
 * 学科优劣分析(教师)
 * @author  andy
 * @created 2014年8月6日 下午2:00:11
 * @since   v1.0.0
 */
@Component("chart_subAnalysis")
public class SubAnalysisChartBuilder implements ChartBuilder<AnalysisChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public ChartRes build(AnalysisChartReq chartDto) {
		PieChartRes chartRes = new PieChartRes();

		Long teacherId = null;
		if (RoleCst.TEACHER.equals(UserContext.user.getCurrentRoleId())) {
			// 如果当前用户为老师，只能查看自已的作业
			teacherId = UserContext.user.getUserId();
		}
		List<StuAvgScoreDto> stuAvgScoreDtoList = homeworkDtlService.findStuAvgScoreDtoList(chartDto.getClassType(),
				chartDto.getClassId(), chartDto.getSubjectId(), teacherId);
		chartRes.addRawData("dataList", stuAvgScoreDtoList);
		if (stuAvgScoreDtoList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		int A = 0, B = 0, C = 0, D = 0;
		for (StuAvgScoreDto stuAvgScoreDto : stuAvgScoreDtoList) {
			Double avgScore = stuAvgScoreDto.getAvgScore();
			if (avgScore == null) {
				continue;
			}
			String level;
			if (avgScore.doubleValue() >= 85) {
				A++;
				level = "A";
			} else if (avgScore.doubleValue() >= 70) {
				B++;
				level = "B";
			} else if (avgScore.doubleValue() >= 60) {
				C++;
				level = "C";
			} else {
				D++;
				level = "D";
			}
			stuAvgScoreDto.setScoreLevel(level);
		}

		Data data = new Data();

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("name", "A：85-100" + MessageSource.getMessage("diag.java.subAnalysis.mark"));
		item.put("value", A);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", "B：70-85" + MessageSource.getMessage("diag.java.subAnalysis.mark"));
		item.put("value", B);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", "C：60-70" + MessageSource.getMessage("diag.java.subAnalysis.mark"));
		item.put("value", C);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", "D：0-60" + MessageSource.getMessage("diag.java.subAnalysis.mark"));
		item.put("value", D);
		data.addItem(item);

		Map<String, String> title = new HashMap<String, String>();
		title.put("text", MessageSource.getMessage("diag.common.subanalysis.name"));
		title.put("subtext", dateCycle());
		title.put("x", "left");

		chartRes.addSeries(data);
		chartRes.addTitle(title);

		return chartRes;
	}

	private static String dateCycle() {
		String startDateStr = "", endDateStr = "";
		Date nowDate = new Date();
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer specDateStr = new StringBuffer(year + "");
		specDateStr.append("-").append(DiagCst.MONTH_NUM).append("-").append(DiagCst.DAY_NUM);
		try {
			Date specDate = formatter.parse(specDateStr.toString());
			if (specDate.before(nowDate)) {
				startDateStr = specDateStr.toString();
			} else {
				c.setTime(formatter.parse(specDateStr.toString()));
				c.add(Calendar.YEAR, -DiagCst.DAY_NUM);
				startDateStr = formatter.format(c.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		endDateStr = formatter.format(nowDate);
		return MessageSource.getMessage("diag.java.subAnalysis.stlcycle", startDateStr, endDateStr);
	}

}
