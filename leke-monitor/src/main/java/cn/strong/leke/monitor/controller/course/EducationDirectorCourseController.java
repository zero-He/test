/**
 * 
 */
package cn.strong.leke.monitor.controller.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.area.AreaMerge;
import cn.strong.leke.monitor.core.model.ExportDailyCourseDto;
import cn.strong.leke.monitor.core.utils.CourseHelper;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotQuery;
import cn.strong.leke.monitor.mongo.course.model.query.CourseSnapshotStat;
import cn.strong.leke.monitor.mongo.course.model.query.DailyCourseStat;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseSingleQuery;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolDailyDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSchoolSnapshotDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseSingleDao;
import cn.strong.leke.monitor.mongo.course.service.ICourseTableDao;
import cn.strong.leke.monitor.util.FileUtils;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.service.user.IEduBureauRemoteService;

/**
 * 教育局长课堂控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/educationDirector/course")
public class EducationDirectorCourseController {

	@Autowired
	private IEduBureauRemoteService eduBureauRemoteService;
	@Autowired
	private ICourseTableDao courseTableDao;
	@Autowired
	private ICourseSchoolDailyDao courseSchoolDailyDao;
	@Autowired
	private ICourseSingleDao courseSingleDao;
	@Autowired
	private ICourseSchoolSnapshotDao courseSchoolSnapshotDao;

	@RequestMapping("courseTablePage")
	public void courseTablePage() {

	}

	/**
	 * 教育局课表记录
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("courseTables")
	@ResponseBody
	public JsonResult findCourseTables(String dataJson) {
		JsonResult json = new JsonResult();
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson, DayRangeCourseQuery.class);
		AreaMerge area = getCurrentAreaMerge();
		List<CourseTable> items = findCourseTables(area, query);
		json.addDatas("items", items);
		return json;
	}

	private AreaMerge getCurrentAreaMerge() {
		Long userId = UserContext.user.getUserId();
		AreaMerge area = eduBureauRemoteService.findAreaMergeByUserId(userId);
		if (area != null && area.getType() == null) {
			area.setType(AreaMerge.TYPE_REGION);
		}
		return area;
	}

	private List<CourseTable> findCourseTables(AreaMerge area, DayRangeCourseQuery query) {
		if (area == null) {
			return Collections.emptyList();
		}
		query.setArea(area);
		query.setAreaRequired(true);
		return courseTableDao.findCourseTables(query);
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
	 * 上课人数统计
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("findCourseSnapshotStats")
	@ResponseBody
	public JsonResult findCourseSnapshotStats(String dataJson) {
		JsonResult json = new JsonResult();
		CourseSnapshotQuery query = JsonUtils.fromJSON(dataJson,
				CourseSnapshotQuery.class);
		query.setAreaRequired(true);
		query.setArea(getCurrentAreaMerge());
		List<CourseSnapshotStat> stats = courseSchoolSnapshotDao
				.findCourseSnapshotStats(query);
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
		query.setAreaRequired(true);
		query.setArea(getCurrentAreaMerge());
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
			double tatio = ((totalActualStuTimes * 1.0)
					/ (totalExpectStuTimes * 1.0) * 100);
			total.setRatioTimes(String.format("%.2f", tatio) + "%");
		}
		dailyCourses.add(0, total);
		return dailyCourses;
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
		JsonResult json = new JsonResult();
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseQuery.class);
		query.setAreaRequired(true);
		query.setArea(getCurrentAreaMerge());
		List<DailyCourseStat> stats = courseSchoolDailyDao
				.findDailyCourseStats(query);
		json.addDatas("stats", stats);
		return json;
	}

	/**
	 * 上课人数统计查看详情页面
	 * 
	 * @author raolei
	 * @return
	 */
	@RequestMapping("classPeopleView")
	public void classPeopleView(Integer day, Long schoolId ,Model model) {
		model.addAttribute("day", StatUtils.dayToLocalDate(day).toString("yyyy-MM-dd"));
		model.addAttribute("schoolId", schoolId);
		String schoolName = SchoolContext.getSchoolBySchoolId(schoolId)
				.getName();
		schoolName = schoolName.length() > 12 ? schoolName.substring(0, 12)
				+ "..." : schoolName;
		model.addAttribute("schoolName", schoolName);
		
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
			throws Exception {
		DayRangeCourseSingleQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseSingleQuery.class);
		List<CourseSingle> items = courseSingleDao.findCourseSingles(query);
		CourseHelper.exportCourseSingleStats(items, request, response);
	}

}
