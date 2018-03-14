/*
 * Copyright (c) 2015 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.scs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.service.user.ISchoolRemoteService;
import cn.strong.leke.scs.constant.CustomerCst;
import cn.strong.leke.scs.model.CustomerVO;
import cn.strong.leke.scs.model.TutorUser;
import cn.strong.leke.scs.model.query.CustomerQuery;
import cn.strong.leke.scs.model.query.TutorUserQuery;
import cn.strong.leke.scs.service.CustomerService;
import cn.strong.leke.scs.service.SchoolService;

/**
 *
 * 描述:技术支持学校管理
 *
 * @author  yuanyi
 * @created 2015年9月1日 上午11:28:49
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/scs/school/*")
public class SchoolController {
	
	@Resource
	private SchoolService schoolService;

	@Resource
	private CustomerService customerService;
	
	@Autowired
	private ISchoolRemoteService schoolRemoteService;
	
	
	/**
	 * 描述:技术支持学校管理页面
	 *
	 * @author  yuanyi
	 * @created 2015年9月1日 下午4:10:10
	 */
	@RequestMapping("personalSchoolList")
	public void personalSchoolList() {
	}
	
	/**
	 * 描述:学校管理数据列表
	 *
	 * @author  yuanyi
	 * @created 2015年9月1日 下午4:10:43
	 * @since   v1.0.0 
	 */
	@RequestMapping("personalSchoolListData")
	@ResponseBody
	public JsonResult personalSchoolListData(TutorUserQuery query, Page page) {
		JsonResult json = new JsonResult();
		List<TutorUser> list = schoolService.findSchoolUserList(query, page);
		page.setDataList(list);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 描述:导出数据
	 *
	 * @author  yuanyi
	 * @created 2015年9月1日 下午4:09:45
	 * @since   v1.0.0 
	 */
	@RequestMapping("export")
	@ResponseBody
	public JsonResult export(TutorUserQuery query, HttpServletRequest request, HttpServletResponse response) {
		JsonResult json = new JsonResult();
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream;charset=UTF-8");
			String agent = request.getHeader("user-agent");
			String title = "个人学校列表";
			String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
			response.setHeader("Content-disposition", "attachment;" + fileName);
			
			String[] headers = { "个人学校", "个人教师", "乐号", "手机", "客户经理", "创建时间" };
			String[] fieldNames = { "schoolName", "userName", "loginName", "phone", "sellerName", "createdOn" };
			String pattern = "yyyy-MM-dd HH:mm:ss";
			
			List<TutorUser> list = schoolService.findSchoolUserList(query, null);
			
			new ExportExcelForTable<TutorUser>().exportExcel(title, headers, fieldNames, list,
					response.getOutputStream(), pattern);

			json.setMessage("导出成功！");
		} catch (Exception e) {
			json.setMessage("导出失败！");
		}
		
		return json;
	}
	
	
	/**	
	 * 描述:选择学校页面-客户经理/技术支持
	 * @author  zhengyiyin
	 * @created 2015年11月24日 下午7:06:02
	 */
	//课程发布
	@RequestMapping(value={"curriculum/selectSLForSeller","curriculum/selectSLForTechSupport"})
	public String selectSLForCurriculum(ModelMap model) {
		model.addAttribute("type", 1);
		if(UserContext.user.getCurrentRoleId().equals(CustomerCst.ROLE_TECHSUPPORT)){
			return "/auth/scs/school/selectSLForTechSupport";
		}
		return "/auth/scs/school/selectSLForSeller";
	}
	
	//排课管理
	@RequestMapping(value={"arrange/selectSLForSeller","arrange/selectSLForTechSupport"})
	public String selectSLForArrange(ModelMap model) {
		model.addAttribute("type", 2);
		if(UserContext.user.getCurrentRoleId().equals(CustomerCst.ROLE_TECHSUPPORT)){
			return "/auth/scs/school/selectSLForTechSupport";
		}
		return "/auth/scs/school/selectSLForSeller";
	}
	
	//上课名单
	@RequestMapping(value={"roster/selectSLForSeller","roster/selectSLForTechSupport"})
	public String selectSLForRoster(ModelMap model) {
		model.addAttribute("type", 3);
		if(UserContext.user.getCurrentRoleId().equals(CustomerCst.ROLE_TECHSUPPORT)){
			return "/auth/scs/school/selectSLForTechSupport";
		}
		return "/auth/scs/school/selectSLForSeller";
	}
	
	//客户经理学校订单入口
	@RequestMapping(value={"order/selectSLForSeller"})
	public String selectSLForOrder(ModelMap model) {
		model.addAttribute("type", 4);
		return "/auth/scs/school/selectSLForSeller";
	}
	
	@RequestMapping("getSchoolNameFromCookie")
	@ResponseBody
	public String getSchoolNameFromCookie(Long schoolId){
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		return school.getName();
	}
	/**	
	 * 描述:学校切换 下拉框
	 * 技术支持 获取全部 学校
	 * @author  zhengyiyin
	 * @created 2015年11月23日 下午4:12:19
	 */
	@RequestMapping("querySchoolName")
	@ResponseBody
	public JsonResult querySchoolName(String schoolName, Page page) {
		JsonResult result = new JsonResult();
		List<SimpleSchool> simpleSchoolList = new ArrayList<SchoolController.SimpleSchool>();
		PageRemote<School> pageRemote = new PageRemote<School>();
		pageRemote.setCurPage(page.getCurPage());
		pageRemote.setPageCount(page.getPageCount());
		
		boolean isTechSupport = UserContext.user.getCurrentRoleId().equals(CustomerCst.ROLE_TECHSUPPORT);
		PageRemote<School> datas = new PageRemote<School>();
		//技术支持查找包含测试学校
		if(isTechSupport){
			datas = schoolRemoteService.findSchoolListIncludeTestByName(schoolName, pageRemote);
		}else{
			datas = schoolRemoteService.findSchoolListByName(schoolName, pageRemote);
		}
//		List<SimpleSchool> schools = ListUtils.map(datas.getDataList(), s -> {
//			SimpleSchool school = new SimpleSchool();
//			school.setSchoolId(s.getId());
//			school.setSchoolName(s.getName());
//			school.setSchoolNature(s.getSchoolNature());
//			return school;
//		});
		List<School> schools = datas.getDataList();
		boolean isSeller = UserContext.user.getCurrentRoleId().equals(CustomerCst.ROLE_SELLER);
		for(School school : schools){
			if(isSeller){
				//客户经理关联时的下拉框 只能关联机构学校
				if(school.getSchoolNature() != CustomerCst.SCHOOLNATURE_PERSON){
					SimpleSchool simpleSchool = new SimpleSchool();
					simpleSchool.setSchoolId(school.getId());
					simpleSchool.setSchoolName(school.getName());
					simpleSchoolList.add(simpleSchool);
				}
			}else{
				SimpleSchool simpleSchool = new SimpleSchool();
				simpleSchool.setSchoolId(school.getId());
				simpleSchool.setSchoolName(school.getName());
				simpleSchoolList.add(simpleSchool);
			}
			
		}
		result.addDatas("items", simpleSchoolList);
		result.addDatas("page", datas);
		return result;
	}
	
	/**	
	 * 描述:客户经理获取自己已关联的学校 下拉框
	 * 只查学校客户
	 * @author  zhengyiyin
	 * @created 2015年11月30日 上午10:58:54
	 * @since   v1.0.0 
	 */
	@RequestMapping("queryOwnSchoolName")
	@ResponseBody
	public JsonResult queryOwnSchoolName(String schoolName, Page page) {
		JsonResult result = new JsonResult();
		
		CustomerQuery query = new CustomerQuery();
		query.setSellerId(UserContext.user.getUserId());
		query.setCustomerType(CustomerCst.CUSTOMERTYPE_SCHOOL);//学校客户
		query.setCustomerName(schoolName);
		List<CustomerVO> customerList= customerService.findCustomerByQuery(query, page);
		
		List<SimpleSchool> simpleSchoolList = new ArrayList<SchoolController.SimpleSchool>();
		for(CustomerVO vo : customerList){
			SimpleSchool simpleSchool = new SimpleSchool();
			simpleSchool.setSchoolId(vo.getLekeId());
			simpleSchool.setSchoolName(vo.getCustomerName());
			simpleSchoolList.add(simpleSchool);
		}
		result.addDatas("items", simpleSchoolList);
		page.setDataList(simpleSchoolList);
		result.addDatas("page", page);
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
