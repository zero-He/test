package cn.strong.leke.dwh.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.datetime.Month;
import cn.strong.leke.dwh.model.diag.AchievementQuery;
import cn.strong.leke.dwh.model.diag.ReportCycle;
import cn.strong.leke.dwh.service.AchievementService;

@Controller
@RequestMapping
public class AchievementController {

	@Resource
	private AchievementService achievementService;

	@ResponseBody
	@RequestMapping("/achievement/teacher")
	public Object getKlassAchievementForTeacher() {
		AchievementQuery query = new AchievementQuery();
		query.setTeacherId(111789L);
		query.setSubjectId(1L);
		query.setClassId(4513L);
		List<ReportCycle> cycles = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			ReportCycle reportCycle = new ReportCycle();
			Date date = DateUtils.addDays(new Date(), -i);
			reportCycle.setId(200033 - i);
			reportCycle.setType(2);
			reportCycle.setLabel(DateUtils.format(date, "yyyy年MM月"));
			Month month = new Month(date);
			reportCycle.setStart(month.getStartDate());
			reportCycle.setEnd(month.getEndDateTime());
			cycles.add(reportCycle);
		}
		query.setCycles(cycles);
		return this.achievementService.getKlassAchievementForTeacher(query);
	}

}
