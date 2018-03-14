package cn.strong.leke.diag.manage;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.diag.dao.homework.mongo.ReportCycleDao;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.diag.util.CycleGenMain;
import cn.strong.leke.diag.util.DiagCst;

@Component
public class ReportCycleService {

	private static final int DEFAULT_CYCLE_LIMIT = 6;

	@Resource
	private ReportCycleDao reportCycleDao;

	/**
	 * 保存报告周期信息
	 * @param reportCycles
	 */
	public void insertReportCycle() {
		List<ReportCycle> reportCycles = CycleGenMain.build();
		this.reportCycleDao.insertReportCycle(reportCycles);
	}

	public List<Map<String, Object>> buildMobileReportTypes() {
		Date date = new Date();
		List<Map<String, Object>> reportTypes = new ArrayList<>();
		reportTypes.add(this.buildMobileReportType(DiagCst.CYCLE_TYPE_WEEK, "周报", date, DEFAULT_CYCLE_LIMIT));
		reportTypes.add(this.buildMobileReportType(DiagCst.CYCLE_TYPE_MONTH, "月报", date, DEFAULT_CYCLE_LIMIT));
	//	reportTypes.add(this.buildMobileReportType(DiagCst.CYCLE_TYPE_TERM, "学期", date, DEFAULT_CYCLE_LIMIT));
	//	reportTypes.add(this.buildMobileReportType(DiagCst.CYCLE_TYPE_YEAR, "学年", date, DEFAULT_CYCLE_LIMIT));
		return reportTypes;
	}

	private Map<String, Object> buildMobileReportType(Integer type, String label, Date date, int limit) {
		List<ReportCycle> list = this.reportCycleDao.findNearestReportCycles(type, date, limit);
		List<Map<String, Object>> subs = list.stream().map(v -> {
			Map<String, Object> item = new HashMap<>();
			item.put("label", v.getLabel());
			item.put("value", v.getId());
			return item;
		}).collect(toList());
		Map<String, Object> item = new HashMap<>();
		item.put("label", label);
		item.put("value", type);
		item.put("subs", subs);
		return item;
	}

	public List<Map<String, Object>> buildReportTypes() {
		Date date = new Date();
		List<Map<String, Object>> reportTypes = new ArrayList<>();
		reportTypes.add(this.buildReportType(DiagCst.CYCLE_TYPE_WEEK, "周报", date, DEFAULT_CYCLE_LIMIT));
		reportTypes.add(this.buildReportType(DiagCst.CYCLE_TYPE_MONTH, "月报", date, DEFAULT_CYCLE_LIMIT));
	//	reportTypes.add(this.buildReportType(DiagCst.CYCLE_TYPE_TERM, "学期", date, DEFAULT_CYCLE_LIMIT));
	//	reportTypes.add(this.buildReportType(DiagCst.CYCLE_TYPE_YEAR, "学年", date, DEFAULT_CYCLE_LIMIT));
		return reportTypes;
	}

	private Map<String, Object> buildReportType(Integer type, String label, Date date, int limit) {
		List<ReportCycle> list = this.reportCycleDao.findNearestReportCycles(type, date, limit);
		Map<String, Object> item = new HashMap<>();
		item.put("type", type);
		item.put("label", label);
		item.put("list", list);
		return item;
	}

	/**
	 * 获取周期信息
	 * @param id 周期ID
	 * @return
	 */
	public ReportCycle getReportCycleById(Integer id) {
		return this.reportCycleDao.getReportCycleById(id);
	}

	/**
	 * 获取指定时间所在的周周期
	 * @param date
	 * @return
	 */
	public ReportCycle getWeekReportCycleByDate(Date date) {
		return this.reportCycleDao.getWeekReportCycleByDate(date);
	}

	/**
	 * 获取指定时间所在的月周期
	 * @param date
	 * @return
	 */
	public ReportCycle getMonthReportCycleByDate(Date date) {
		return this.reportCycleDao.getMonthReportCycleByDate(date);
	}

	/**
	 * 查询一段时间内某个类型的周期列表
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param type 周期类型
	 * @return
	 */
	public List<ReportCycle> findReportCycleByTime(Date start, Date end, Integer type) {
		if (type == null || type == 0) {
			// 如果周期类型为0时，直接构建按日的周期
			return CycleGenMain.buildDate(start, end);
		}
		return this.reportCycleDao.findReportCycleByTime(start, end, type);
	}

	/**
	 * 获取当前周期的前一个周期
	 * @param id 当前周期ID
	 * @param type 周期类型
	 * @return
	 */
	public ReportCycle getPrevReportCycle(Integer id, Integer type) {
		return this.reportCycleDao.getPrevReportCycle(id, type);
	}

	/**
	 * 获取从当前周期向前的6个周期（包含当前周期），不足6个按实际返回，倒序排列
	 * @param reportCycle 当前周期
	 * @return
	 */
	public List<ReportCycle> findNear6ReportCycle(ReportCycle reportCycle) {
		return this.reportCycleDao.findNear6ReportCycle(reportCycle);
	}
}
