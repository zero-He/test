package cn.strong.leke.diag.controller;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.diag.manage.SchoolStatsService;
import cn.strong.leke.diag.model.report.ClsSubjRptView;
import cn.strong.leke.diag.model.report.DataZone;
import cn.strong.leke.diag.model.report.HomeOverallRptView;
import cn.strong.leke.diag.model.report.ScoreRptQuery;
import cn.strong.leke.diag.report.HomeClassSubjScoreReportHandler;
import cn.strong.leke.diag.report.HomeStuComboScoreReportHandler;
import cn.strong.leke.diag.service.HomeworkService;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.lesson.model.TeachSubj;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Controller
@RequestMapping("/*")
public class JSONPController {

	@Resource
	private HomeworkService homeworkService;
	@Resource
	private SchoolStatsService schoolStatsService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private HomeStuComboScoreReportHandler homeStuComboScoreReportHandler;
	@Resource
	private HomeClassSubjScoreReportHandler homeClassSubjScoreReportHandler;

	private Object buildResult(JsonResult json, String jsonpcallback) {
		if (StringUtils.isNotEmpty(jsonpcallback)) {
			return new JSONPObject(jsonpcallback, json);
		}
		return json;
	}

	/**
	 * 学生首页学科优劣分析数据。
	 */
	@ResponseBody
	@RequestMapping("/auth/student/subjas/data")
	public Object subjas(String jsonpcallback) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		HomeOverallRptView view = this.homeStuComboScoreReportHandler.execute(userId);
		json.addDatas("view", view);
		return this.buildResult(json, jsonpcallback);
	}

	@ResponseBody
	@RequestMapping("/auth/teacher/subjas/klass")
	public Object klass(String jsonpcallback) {
		User user = UserContext.user.get();
		List<TeachSubj> teachSubjs = this.klassRemoteService.findTeachSubjByUserId(user.getId(),
				user.getCurrentSchool().getId());
		List<DataZone> zones = new ArrayList<>();
		for (TeachSubj teachSubj : teachSubjs) {
			for (Clazz clazz : teachSubj.getClazzList()) {
				DataZone zone = new DataZone(clazz.getClassName(), clazz.getClassId(), teachSubj.getSubjectId());
				zone.setSubjectName(teachSubj.getSubjectName());
				zones.add(zone);
			}
		}
		zones.sort(Comparator.comparing(DataZone::getClassId));
		zones.stream().collect(groupingBy(DataZone::getClassId)).values().stream().filter(list -> list.size() > 1)
				.forEach(list -> list.forEach(v -> v.setLabel(v.getLabel() + "--" + v.getSubjectName())));

		JsonResult json = new JsonResult();
		json.addDatas("zones", zones);
		return this.buildResult(json, jsonpcallback);
	}

	/**
	 * 老师首页学科优劣分析数据。
	 */
	@ResponseBody
	@RequestMapping("/auth/teacher/subjas/data")
	public Object subjas(Long classId, Long subjectId, String jsonpcallback) {
		ScoreRptQuery query = new ScoreRptQuery();
		query.setClassId(classId);
		query.setSubjectId(subjectId);
		JsonResult json = new JsonResult();
		ClsSubjRptView view = this.homeClassSubjScoreReportHandler.execute(query);
		json.addDatas("view", view);
		return this.buildResult(json, jsonpcallback);
	}
}
