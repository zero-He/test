/**
 * 
 */
package cn.strong.leke.monitor.controller.course;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.mongo.course.model.CourseTable;
import cn.strong.leke.monitor.mongo.course.model.query.DayRangeCourseQuery;
import cn.strong.leke.monitor.mongo.course.service.ICourseTableDao;
import cn.strong.leke.remote.model.crm.SellerSchoolRemote;
import cn.strong.leke.remote.service.crm.ISellerRemoteService;

/**
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/seller/course")
public class SellerCourseController {

	@Autowired
	private ICourseTableDao courseTableDao;
	@Autowired
	private ISellerRemoteService sellerRemoteService;

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
		Long sellerId = UserContext.user.getUserId();
		SellerSchoolRemote assoc = sellerRemoteService.getAssociatedSchools(sellerId);
		DayRangeCourseQuery query = JsonUtils.fromJSON(dataJson,
				DayRangeCourseQuery.class);
		List<CourseTable> items = findCourseTables(assoc, query);
		JsonResult json = new JsonResult();
		json.addDatas("items", items);
		return json;
	}

	private List<CourseTable> findCourseTables(SellerSchoolRemote assoc, DayRangeCourseQuery query) {

		if (query.getSchoolIds() == null) {
			if (assoc == null || CollectionUtils.isEmpty(assoc.getSchoolIds())) {
				return Collections.emptyList();
			}
			query.setSchoolIds(assoc.getSchoolIds());
		}
		return courseTableDao.findCourseTables(query);
	}
}
