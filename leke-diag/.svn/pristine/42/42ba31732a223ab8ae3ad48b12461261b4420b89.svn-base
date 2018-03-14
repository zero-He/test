package cn.strong.leke.diag.chart.build;

import java.util.ArrayList;
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
import cn.strong.leke.diag.chart.model.GradeDiligentChartReq;
import cn.strong.leke.diag.model.ClassDiligentDto;
import cn.strong.leke.diag.service.HomeworkDtlService;
import cn.strong.leke.framework.spring.MessageSource;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.struct.IStructRemoteService;

/**
 * 作业勤奋报告(教务)
 * @author  andy
 * @created 2014年8月6日 下午1:56:44
 * @since   v1.0.0
 */
@Component("chart_allDiligent")
public class AllDiligentChartBuilder implements ChartBuilder<GradeDiligentChartReq> {

	@Resource
	private HomeworkDtlService homeworkDtlService;
	@Resource
	private IStructRemoteService structRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	
	@Override
	public ChartRes build(GradeDiligentChartReq chartDto) {
		BarChartRes chartRes = new BarChartRes();

		List<Long> classIdList = this.getClasses(chartDto.getClassType(), chartDto.getGradeId());
		if (classIdList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		Date startTime = null, endTime = null;
		if (chartDto.getStartTime() != null)
			startTime = DateUtils.toDayStartTime(chartDto.getStartTime());
		if (chartDto.getEndTime() != null)
			endTime = DateUtils.toDayEndTime(chartDto.getEndTime());
		List<ClassDiligentDto> classDiligentDtoList = this.homeworkDtlService.findClassDiligentDtoList(classIdList, 1L,
				chartDto.getSubjectId(), startTime, endTime);
		if (classDiligentDtoList.isEmpty()) {
			chartRes.setErrorMessage(MessageSource.getMessage("diag.java.subAnalysis.nodata"));
			return chartRes;
		}

		Data xdata = new Data();
		Data normalData = new Data();
		Data delayData = new Data();
		Data unsubmitData = new Data();

		for (ClassDiligentDto classDiligentDto : classDiligentDtoList) {
			xdata.addItem(classDiligentDto.getClassName());
			normalData.addItem(classDiligentDto.getNormalRate());
			delayData.addItem(classDiligentDto.getDelayRate());
			unsubmitData.addItem(classDiligentDto.getUnsubmitRate());
		}

		chartRes.addxAxis(xdata);
		chartRes.addSeries(normalData);
		chartRes.addSeries(delayData);
		chartRes.addSeries(unsubmitData);
		chartRes.addRawData("dataList", classDiligentDtoList);

		return chartRes;
	}

	/**
	 * 教务获取选修班级和行政班级
	 * @param gradeId 年级ID
	 * @return
	 */
	public List<Long> getClasses(Integer classType, Long gradeId) {
		User user = UserContext.user.get();
		
		ClazzQuery query = new ClazzQuery();
		query.setType(classType);
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
