/**
 * 
 */
package cn.strong.leke.monitor.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.model.crm.SellerSchoolRemote;
import cn.strong.leke.remote.service.crm.ISellerRemoteService;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;

/**
 * 通用数据控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("auth/common/data")
public class CommonDataController {

	@Autowired
	private ISchoolRemoteService schoolRemoteService;

	@Autowired
	private ISellerRemoteService sellerRemoteService;

	@RequestMapping("querySchoolLike")
	@ResponseBody
	public JsonResult querySchoolLike(String schoolName, PageRemote<School> pageRemote) {
		JsonResult result = new JsonResult();
		PageRemote<School> datas = schoolRemoteService.findSchoolListByName(schoolName, pageRemote);
		List<SimpleSchool> schools = ListUtils.map(datas.getDataList(), s -> {
			SimpleSchool school = new SimpleSchool();
			school.setSchoolId(s.getId());
			school.setSchoolName(s.getName());
			school.setSchoolNature(s.getSchoolNature());
			return school;
		});
		datas.setDataList(Collections.emptyList());
		result.addDatas("items", schools);
		result.addDatas("page", datas);
		return result;
	}

	@RequestMapping("querySellerSchools")
	@ResponseBody
	public JsonResult querySellerSchools() {
		JsonResult result = new JsonResult();
		Long sellerId = UserContext.user.getUserId();
		SellerSchoolRemote sellerSchool = sellerRemoteService
				.getAssociatedSchools(sellerId);
		List<Long> schoolIds = sellerSchool.getSchoolIds();
		List<SimpleSchool> schools = ListUtils.map(schoolIds, s -> {
			SimpleSchool simpleSchool = new SimpleSchool();
			School school = SchoolContext.getSchoolBySchoolId(s);
			simpleSchool.setSchoolId(school.getId());
			simpleSchool.setSchoolName(school.getName());
			simpleSchool.setSchoolNature(school.getSchoolNature());
			return simpleSchool;
		});
		result.addDatas("schools", schools);
		return result;
	}

	public static class SimpleSchool {
		private Long schoolId;
		private String schoolName;
		private Integer schoolNature;

		public Long getSchoolId() {
			return schoolId;
		}

		public void setSchoolId(Long schoolId) {
			this.schoolId = schoolId;
		}

		public String getSchoolName() {
			return schoolName;
		}

		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}

		public Integer getSchoolNature() {
			return schoolNature;
		}

		public void setSchoolNature(Integer schoolNature) {
			this.schoolNature = schoolNature;
		}

	}
}
