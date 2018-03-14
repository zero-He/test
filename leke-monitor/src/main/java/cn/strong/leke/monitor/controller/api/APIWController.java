package cn.strong.leke.monitor.controller.api;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.json.JSON;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.core.model.appcenter.PadSystemReport;
import cn.strong.leke.monitor.core.service.appcenter.IAppCenterService;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseSingleQuery;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleDao;

@Controller
@RequestMapping("/api/w/*")
public class APIWController {

	@Resource
	private ICourseSingleDao courseSingleDao;
	@Resource
	private IAppCenterService appCenterService;

	@ResponseBody
	@RequestMapping("findCourseSingleByDay")
	public JsonResult findCourseSingleByDay(String data) {
		JsonResult json = new JsonResult();
		DayRangeCourseSingleQuery query = JsonUtils.fromJSON(data, DayRangeCourseSingleQuery.class);
		query.setEndDay(query.getStartDay());
		List<CourseSingle> items = courseSingleDao.findCourseSingles(query);
		json.addDatas("items", items);
		return json;
	}

	/**
	 * PAD 系统应用信息数据上报
	 * 
	 * @param data
	 * @param request
	 * @return
	 */
	@RequestMapping("/padSystemReport")
	@ResponseBody
	public JsonResult padSystemReport(String data, HttpServletRequest request) {
		PadSystemReport report = JSON.parse(data, PadSystemReport.class);
		appCenterService.savePadSystemReport(report);
		return new JsonResult();
	}
}
