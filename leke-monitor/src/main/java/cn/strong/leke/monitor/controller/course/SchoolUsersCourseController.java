/**
 * 
 */
package cn.strong.leke.monitor.controller.course;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;
import cn.strong.leke.monitor.mongo.course.service.ICourseTableDao;

/**
 * 学校角色课堂控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/schoolUsers/course")
public class SchoolUsersCourseController {
	@Autowired
	private ICourseTableDao courseTableDao;

	@RequestMapping("courseTablePage")
	public void courseTablePage() {

	}

	/**
	 * 学校课表记录
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("courseTables")
	@ResponseBody
	public JsonResult findCourseTables(String dataJson) {
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson, DayRangeCourseQuery.class);
		Long schoolId = UserContext.user.getCurrentSchoolId();
		query.setSchoolIds(Arrays.asList(schoolId));
		List<CourseTable> items = courseTableDao.findCourseTables(query);
		JsonResult json = new JsonResult();
		json.addDatas("items", items);
		return json;
	}
}
