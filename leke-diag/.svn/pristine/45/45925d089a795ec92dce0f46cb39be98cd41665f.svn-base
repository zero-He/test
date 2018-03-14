package cn.strong.leke.diag.service;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.manage.WorkDetailService;
import cn.strong.leke.diag.model.StuKnoRate;
import cn.strong.leke.diag.model.StuSubjQuery;
import cn.strong.leke.diag.model.StuSubjScore;
import cn.strong.leke.diag.model.report.ReportCycle;
import cn.strong.leke.remote.model.diag.KlgGrasp;

/**
 * 知识分析服务。
 * @author  andy
 */
@Component
public class KnoGraspService {

	@Resource
	private ReportCycleService reportCycleService;
	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private WorkDetailService workDetailService;

	/**
	 * 根据学生分析知识点掌握情况
	 * @param userId
	 * @return
	 */
	public List<KlgGrasp> findStudentWeekUnGrasp(Long userId) {
		ReportCycle reportCycle = this.reportCycleService.getWeekReportCycleByDate(new Date());
		StuSubjQuery stuSubjQuery = new StuSubjQuery();
		stuSubjQuery.setStart(reportCycle.getStart());
		stuSubjQuery.setEnd(reportCycle.getEnd());
		stuSubjQuery.setUserIds(Lists.newArrayList(userId));
		List<StuSubjScore> scores = this.homeworkDtlService.findStuSubjScores(stuSubjQuery);

		List<Long> homeworkDtlIds = scores.stream().map(StuSubjScore::getHomeworkDtlId).collect(toList());
		List<StuKnoRate> rates = this.workDetailService.findStuKnoRates(homeworkDtlIds);
		rates.forEach(rate -> rate.setRightRate(rate.getRightNum() * 100 / rate.getTotalNum()));
		return rates.stream().map(rate -> {
			KlgGrasp knoGrasp = new KlgGrasp();
			knoGrasp.setKlgId(rate.getId());
			knoGrasp.setStatus(this.mappingRateToStatus(rate.getRightRate()));
			return knoGrasp;
		}).collect(toList());
	}

	private int mappingRateToStatus(Double rate) {
		if (rate >= 85) {
			return KlgGrasp.GRASP_YES;
		} else if (rate >= 50) {
			return KlgGrasp.GRASP_ALMOST;
		} else {
			return KlgGrasp.GRASP_NO;
		}
	}
}
