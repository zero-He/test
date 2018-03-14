package cn.strong.leke.diag.chart.build;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.core.business.chart.PieChartRes;
import cn.strong.leke.diag.chart.model.StuDiligentChartReq;
import cn.strong.leke.remote.model.tukor.AttendanceStatusRemote;
import cn.strong.leke.remote.service.lessonlog.IAttendanceRemoteService;

/**
 * 描述:学生考勤统计。
 * @author  DuanYanming
 * @created 2014年10月30日 下午4:21:49
 * @since   v1.0.0
 */
@Component("chart_attendance")
public class AttendanceChartBuilder implements ChartBuilder<StuDiligentChartReq> {

	@Resource
	private IAttendanceRemoteService attendanceRemoteService;

	@Override
	public ChartRes build(StuDiligentChartReq chartDto) {
		PieChartRes chartRes = new PieChartRes();

		Date startTime = DateUtils.toDayStartTime(chartDto.getStartTime());
		Date endTime = DateUtils.toDayEndTime(chartDto.getEndTime());

		AttendanceStatusRemote attendanceStatusRemote = attendanceRemoteService.findAttendanceStatus(
				chartDto.getStudentId(), chartDto.getClassId(), startTime, endTime);
		
		if (attendanceStatusRemote == null || attendanceStatusRemote.getTotalCount() == 0) {
			chartRes.setErrorMessage(NO_DATA_USED_DISPLAY);
			return chartRes;
		}

		Data data = new Data();
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("name", "全勤");
		item.put("value", attendanceStatusRemote.getPerfectCount());
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", "非全勤");
		item.put("value", attendanceStatusRemote.getImperfectCount());
		data.addItem(item);

		item = new HashMap<String, Object>();
		item.put("name", "未上课");
		item.put("value", attendanceStatusRemote.getNoneCount());
		data.addItem(item);

		Map<String, String> title = new HashMap<String, String>();
		title.put("text", "考勤统计状态");
		title.put("subtext", "总共上课" + attendanceStatusRemote.getTotalCount() + "次");
		title.put("x", "center");

		chartRes.addSeries(data);
		chartRes.addTitle(title);
		return chartRes;
	}


}
