package cn.strong.leke.diag.controller;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Maps;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.diag.manage.ReportCycleService;
import cn.strong.leke.diag.model.report.DataZone;
import cn.strong.leke.lesson.model.TeachSubj;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.User;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

public class ReportBaseController {

	@Resource
	protected IKlassRemoteService klassRemoteService;
	@Resource
	protected ReportCycleService reportCycleService;

	protected void setKlassModel(Map<String, Object> ReportCst) {
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
		List<Map<String, Object>> klasses = zones.stream().map(zone -> {
			Map<String, Object> item = Maps.newHashMap();
			item.put("label", zone.getLabel());
			item.put("value", zone.getClassId() + "-" + zone.getSubjectId());
			return item;
		}).collect(toList());
		ReportCst.put("klasses", klasses);
	}

	protected void setReportCycleModel(Map<String, Object> ReportCst) {
		List<Map<String, Object>> cycles = this.reportCycleService.buildMobileReportTypes();
		ReportCst.put("cycles", cycles);
	}
}
