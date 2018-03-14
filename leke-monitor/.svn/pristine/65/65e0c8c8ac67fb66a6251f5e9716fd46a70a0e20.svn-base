/**
 * 
 */
package cn.strong.leke.monitor.controller.course;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.school.SchoolStatistics;
import cn.strong.leke.monitor.core.model.CurrentPlatformCourseStat;
import cn.strong.leke.monitor.core.model.ExportDailyCourseDto;
import cn.strong.leke.monitor.core.service.course.ICourseService;
import cn.strong.leke.monitor.core.utils.CourseHelper;
import cn.strong.leke.monitor.mongo.course.model.CoursePlatformDaily;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotQuery;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotStat;
import cn.strong.leke.monitor.mongo.course.model.query.DailyCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseSingleQuery;
import cn.strong.leke.monitor.mongo.course.service.ICoursePlatformDailyDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolDailyDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolSnapshotDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseTableDao;
import cn.strong.leke.monitor.util.FileUtils;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;

/**
 * 技术支持课堂控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/technicalSupport/course")
public class TechSupportCourseController {

	@Autowired
	private ICourseService courseService;
	@Autowired
	private ICourseSchoolDailyDao courseSchoolDailyDao;
	@Autowired
	private ICourseTableDao courseTableDao;
	@Autowired
	private ICoursePlatformDailyDao coursePlatformDailyDao;
	@Autowired
	private ICourseSingleDao courseSingleDao;
	@Autowired
	private ICourseSchoolSnapshotDao courseSchoolSnapshotDao;
	@Resource
	private ISchoolRemoteService iSchoolRemoteService;
	@Resource
	private IUserRemoteService iUserRemoteService;

	/**
	 * 我的乐课
	 * 
	 * @author raolei
	 * @param callback
	 * @return
	 */
	@RequestMapping("technicalIndex")
	public void technicalIndex(Date date, Model model) {
		model.addAttribute("userName", UserContext.user.getUserName());
		if (date == null) {
			date = new Date();
		}
		model.addAttribute("myDate", DateUtils.format(date, "yyyy-MM-dd"));
	}

	/**
	 * 当前平台课堂统计
	 * 
	 * @param callback
	 * @return
	 */
	@RequestMapping("currentPlatformCourseStat")
	@ResponseBody
	public JSONPObject getCurrentPlatformCourseStat(String callback) {
		return jsonp(callback, json -> {
			CurrentPlatformCourseStat stat = courseService.getCurrentPlatformCourseStat();
			json.addDatas("stat", stat);
		});
	}



	@RequestMapping("courseTablePage")
	public void courseTablePage() {

	}

	/**
	 * 课表记录
	 *
	 * @param query
	 * @return
	 */
	@RequestMapping("courseTables")
	@ResponseBody
	public JsonResult findCourseTables(String dataJson) {
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson, DayRangeCourseQuery.class);
		JsonResult json = new JsonResult();
		List<CourseTable> items = courseTableDao.findCourseTables(query);
		json.addDatas("items", items);
		return json;
	}

	/**
	 * 课堂用户监控
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("clazzroomView")
	public void clazzroomView() {

	}

	/**
	 * 上课人数统计
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("classPeopleTotal")
	public void classPeopleTotal() {

	}

	/**
	 * 日课堂统计
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("dailyCourseStats")
	@ResponseBody
	public JsonResult findDailyCourseStats(String dataJson) {
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseQuery.class);
		JsonResult json = new JsonResult();
		List<DailyCourseStat> stats = courseSchoolDailyDao
				.findDailyCourseStats(query);
		json.addDatas("stats", stats);
		return json;
	}

	/**
	 * 导出上课人数统计
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("exportDailyCourseStats")
	@ResponseBody
	public void exportDailyCourseStats(String dataJson,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseQuery.class);
		List<DailyCourseStat> stats = courseSchoolDailyDao
				.findDailyCourseStats(query);
		List<ExportDailyCourseDto> list = formatDailyCourseStats(stats);
		String[] headers = { "日期", "实到人数", "应到人数", "实到人次", "应到人次", "到课率(人次)" };
		String[] fieldNames = { "day", "actualCount", "expectCount",
				"actualTimes", "expectTimes", "ratioTimes" };
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("上课人数统计");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()
				+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<ExportDailyCourseDto>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
	/**
	 * 格式化上课人数统计成excel格式数据
	 * 
	 * @param query
	 * @return
	 */
	private List<ExportDailyCourseDto> formatDailyCourseStats(
			List<DailyCourseStat> stats) {
		List<ExportDailyCourseDto> dailyCourses = new ArrayList<ExportDailyCourseDto>();
		Long totalActualStuCount = 0L;
		Long totalExpectStuCount = 0L;
		Long totalActualStuTimes = 0L;
		Long totalExpectStuTimes = 0L;
		Collections.sort(stats, new Comparator<DailyCourseStat>() {
			public int compare(DailyCourseStat arg0, DailyCourseStat arg1) {
				return arg0.getDay().compareTo(arg1.getDay());
			}
		});
		for (DailyCourseStat dc : stats) {
			totalActualStuCount += dc.getActualStuCount();
			totalExpectStuCount += dc.getExpectStuCount();
			totalActualStuTimes += dc.getActualStuTimes();
			totalExpectStuTimes += dc.getExpectStuTimes();
			ExportDailyCourseDto dailyCourse = new ExportDailyCourseDto();
			dailyCourse.setDay(dc.getDay().toString());
			dailyCourse.setActualCount(dc.getActualStuCount());
			dailyCourse.setExpectCount(dc.getExpectStuCount());
			dailyCourse.setActualTimes(dc.getActualStuTimes());
			dailyCourse.setExpectTimes(dc.getExpectStuTimes());
			if (dc.getExpectStuTimes() == 0) {
				dailyCourse.setRatioTimes("--");
			} else {
				double tatio = ((dc.getActualStuTimes() * 1.0)
						/ (dc.getExpectStuTimes() * 1.0) * 100);
				dailyCourse.setRatioTimes(String.format("%.2f", tatio) + "%");
			}
			dailyCourses.add(dailyCourse);
		}
		ExportDailyCourseDto total = new ExportDailyCourseDto();
		total.setDay("合计");
		total.setActualCount(totalActualStuCount);
		total.setExpectCount(totalExpectStuCount);
		total.setActualTimes(totalActualStuTimes);
		total.setExpectTimes(totalExpectStuTimes);
		if (totalActualStuTimes == 0) {
			total.setRatioTimes("--");
		} else {
			double tatio = ((totalActualStuTimes*1.0) / (totalExpectStuTimes*1.0) * 100);
			total.setRatioTimes(String.format("%.2f", tatio) + "%");
		}
		dailyCourses.add(0, total);
		return dailyCourses;
	}


	/**
	 * 上课人数统计查看详情页面
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("classPeopleView")
	public void classPeopleView(Integer day, Long schoolId, Model model) {
		model.addAttribute("day", StatUtils.dayToLocalDate(day).toString("yyyy-MM-dd"));
		model.addAttribute("schoolId", schoolId);
		String schoolName = SchoolContext.getSchoolBySchoolId(schoolId).getName();
		schoolName = schoolName.length() > 12 ? schoolName.substring(0, 12)
				+ "..." : schoolName;
		model.addAttribute("schoolName",schoolName);
	}

	/**
	 * 单课详情查询
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("findCourseSingleByQuery")
	@ResponseBody
	public JsonResult findCourseSingleByQuery(String dataJson) {
		JsonResult json = new JsonResult();
		DayRangeCourseSingleQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseSingleQuery.class);
		List<CourseSingle> items = courseSingleDao.findCourseSingles(query);
		json.addDatas("items", items);
		return json;

	}
	/**
	 * 导出上课人数统计详情页面数据
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("exportCourseSingleByQuery")
	@ResponseBody
	public void exportCourseSingleByQuery(String dataJson,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		DayRangeCourseSingleQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseSingleQuery.class);
		List<CourseSingle> items = courseSingleDao.findCourseSingles(query);
		CourseHelper.exportCourseSingleStats(items, request, response);
	}

	/**
	 * 课堂使用统计
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("courseUseTotal")
	public void courseUseTotal() {

	}

	/**
	 * 入驻学校统计
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("schoolStatisForChart")
	public void schoolStatisForChart(Model model) {

		Integer schoolCount = iSchoolRemoteService.findSchoolCount();
		Integer tchCount = iUserRemoteService.findUserCount(RoleCst.TEACHER);
		Integer stuCount = iUserRemoteService.findUserCount(RoleCst.STUDENT);
		SchoolStatistics ss = new SchoolStatistics();
		ss.setSchoolCount(schoolCount);
		ss.setTchCount(tchCount);
		ss.setStuCount(stuCount);
		model.addAttribute("ss", ss);
		Calendar cal = Calendar.getInstance();
		model.addAttribute("endTime", cal.getTime());
		cal.add(Calendar.MONTH, -11);
		model.addAttribute("startTime", cal.getTime());
	}
	
	/**
	 * 历史数据统计页
	 * 
	 * @author raolei
	 * @param query
	 * @return
	 */
	@RequestMapping("historyCourse")
	public void historyCourse() {

	}

	/**
	 * 平台课堂日统计记录
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	@RequestMapping("coursePlatformDailys")
	@ResponseBody
	public JsonResult findCoursePlatformDaily(int startDay, int endDay) {
		JsonResult json = new JsonResult();
		List<CoursePlatformDaily> stats = coursePlatformDailyDao
				.findCoursePlatformDaily(startDay, endDay);
		json.addDatas("stats", stats);
		return json;
	}

	@RequestMapping("findCourseSingleByDay")
	@ResponseBody
	public JsonResult findCourseSingleByDay(String dataJson) {
		JsonResult json = new JsonResult();
		DayRangeCourseSingleQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseSingleQuery.class);
		List<CourseSingle> items = courseSingleDao.findCourseSingles(query);
		json.addDatas("items", items);
		return json;
	}

	@RequestMapping("findCourseSnapshotStats")
	@ResponseBody
	public JsonResult findCourseSnapshotStats(String dataJson) {
		JsonResult json = new JsonResult();
		CourseSnapshotQuery query = JsonUtils.fromJSON(dataJson, CourseSnapshotQuery.class);
		List<CourseSnapshotStat> stats = courseSchoolSnapshotDao.findCourseSnapshotStats(query);
		json.addDatas("stats", stats);
		return json;
	}



	@RequestMapping("updateCoursePlatformDaily")
	@ResponseBody
	public JsonResult updateCoursePlatformDaily(int day) {
		courseService.updateCoursePlatformDaily(day);
		return new JsonResult();
	}
}
