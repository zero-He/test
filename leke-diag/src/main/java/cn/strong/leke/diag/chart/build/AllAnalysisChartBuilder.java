package cn.strong.leke.diag.chart.build;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.business.chart.BarChartRes;
import cn.strong.leke.core.business.chart.ChartBuilder;
import cn.strong.leke.core.business.chart.ChartRes;
import cn.strong.leke.core.business.chart.Data;
import cn.strong.leke.diag.chart.model.AnalysisChartReq;
import cn.strong.leke.diag.model.ClassAnalysisDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.framework.spring.MessageSource;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.struct.IStructRemoteService;

/**
 * 学科优劣分析（教务）
 * @author  andy
 * @created 2014年8月6日 下午1:51:14
 * @since   v1.0.0
 */
@Component("chart_allAnalysis")
public class AllAnalysisChartBuilder implements ChartBuilder<AnalysisChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IStructRemoteService structRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	
	@Override
	public ChartRes build(AnalysisChartReq chartDto) {
		BarChartRes chartRes = new BarChartRes();

		List<Long> classIdList = this.getClasses(chartDto.getClassType(), chartDto.getGradeId());
		if (classIdList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}
		
		List<ClassAnalysisDto> classAnalysisDtoList = this.homeworkDtlService.findClassAnalysisDtoList(1L, classIdList,
				chartDto.getSubjectId());
		if (classAnalysisDtoList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		Data xdata = new Data();
		Data A = new Data();
		Data B = new Data();
		Data C = new Data();
		Data D = new Data();

		for (ClassAnalysisDto classAnalysisDto : classAnalysisDtoList) {
			xdata.addItem(classAnalysisDto.getClassName());
			A.addItem(classAnalysisDto.getRateA());
			B.addItem(classAnalysisDto.getRateB());
			C.addItem(classAnalysisDto.getRateC());
			D.addItem(classAnalysisDto.getRateD());
		}

		chartRes.addxAxis(xdata);
		chartRes.addSeries(A);
		chartRes.addSeries(B);
		chartRes.addSeries(C);
		chartRes.addSeries(D);

		return chartRes;
	}

	
	/**
	 * 教务获取选修班级和行政班级
	 * @param gradeId 年级ID
	 * @return
	 */
	public List<Long> getClasses(Long classType, Long gradeId) {
		User user = UserContext.user.get();
		
		ClazzQuery query = new ClazzQuery();
		query.setType(classType.intValue());
		query.setUserId(user.getId());
		query.setRoleId(user.getCurrentRole().getId());
		query.setSchoolId(user.getCurrentSchool().getId());
		query.setGradeId(gradeId);
		List<Clazz> classList = this.klassRemoteService.findClazzByQuery(query);
		
		List<Long> classIdList = new ArrayList<Long>();
		for (Clazz clazz : classList) {
			classIdList.add(clazz.getClassId());
		}
		return classIdList;
	}
}
