package cn.strong.leke.diag.chart.build;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.PieChartRes;
import cn.strong.leke.diag.chart.model.AchievementStatChartReq;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.framework.spring.MessageSource;

/**
 *
 * 描述:学生作业提交状态统计
 *
 * @author  DuanYanming
 * @created 2014年7月30日 上午9:49:11
 * @since   v1.0.0
 */
@Component("chart_submitState")
public class SubmitStateBuilder implements ChartBuilder<AchievementStatChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;

	@Override
	public ChartRes build(AchievementStatChartReq chartDto) {
		PieChartRes chartRes = new PieChartRes();
		Date startDate = chartDto.getStartTime();
		Date endTime = new DateTime(DateUtils.formatDate(new Date())).toDate();
		if(UserContext.user.getCurrentRoleId().equals(RoleCst.TEACHER)){
			endTime =new Date();
		}
		List<Map<String, Object>> list = this.homeworkDtlService.findStudentSubmitState(chartDto.getStudentId(),
				chartDto.getSubjectId(), chartDto.getClassType(), chartDto.getClassId(),startDate,endTime);
		chartRes.addRawData("dataList", list);
		if (list.isEmpty()) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}
		int A = 0, B = 0, C = 0;
		for (Map<String, Object> item : list) {
			Integer submitStatus = (Integer) item.get("submitStatus");
			if (submitStatus == 1) {
				A++;
			} else if (submitStatus == 2) {
				B++;
			} else {
				C++;
			}

		}
		Data data = new Data();
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("name", MessageSource.getMessage("diag.java.subAnalysis.normalsub"));
		item.put("value", A);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", MessageSource.getMessage("diag.java.subAnalysis.delaysub"));
		item.put("value", B);
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", MessageSource.getMessage("diag.java.subAnalysis.uncommitted"));
		item.put("value", C);
		data.addItem(item);

		chartRes.addSeries(data);

		return chartRes;
	}
}
