package cn.strong.leke.monitor.controller.activeuser;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.area.Area;
import cn.strong.leke.monitor.core.service.IActiveUserStaService;
import cn.strong.leke.monitor.core.service.IClassLeaderService;
import cn.strong.leke.monitor.core.service.online.IDeviceOnlineUserService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.mongo.course.model.query.SchoolActiveUserStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;
import cn.strong.leke.monitor.util.FileUtils;

/**
 * 用户统计控制层
 * @author hedan
 *
 */
@Controller
@RequestMapping("/auth/common/activeuser/*")
public class DayActiveUserController {

	@Resource
	private IDeviceOnlineUserService deviceOnlineUserService;
	
	@Resource
	private IActiveUserStaService activeUserStaService;

	@Resource
	private IClassLeaderService classLeaderService;

	@RequestMapping("schoolActiveUserSta")
	public void schoolActiveUserSta(Model model){
		User user = UserContext.user.get();
		// 角色ID
		Long raoleId = user.getCurrentRole().getId();
		
		List<Area> areas = new ArrayList<Area>();

		// 营销处经理
		if (!raoleId.equals(603L)){
			// 营销中心总经理
			if (raoleId.equals(604L)){
				Map<String, List<Area>> MarketMap = classLeaderService.getMarketMap(user, true);

				model.addAttribute("firstDpt", MarketMap.get("firstDpt"));
				model.addAttribute("secondDpt", MarketMap.get("secondDpt"));
				areas = MarketMap.get("secondDpt");
			} else{
				Map<String, List<Area>> MarketMap = classLeaderService.getMarketMap(user, false);

				model.addAttribute("secondDpt", MarketMap.get("secondDpt"));
				areas = MarketMap.get("secondDpt");
			}
		}
		model.addAttribute("areas", JsonUtils.toJSON(areas));
		model.addAttribute("roleId", raoleId);
	}
	
	@RequestMapping("getschoolActiveUserData")
	@ResponseBody
	public JsonResult getschoolActiveUserData(ActiveUserQuery query,Page page){
		User user = UserContext.user.get();
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), user.getCurrentRole().getId());
		
		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		if (listSeller.size() > 0) {
			query.setListSeller(listSeller);
		}
		//只选择了营销部，没有选择营销处，查出营销部下所有营销处！
		if (query.getDepartId() != null && query.getMarketId() == null) {
			
			query.setListMarketId(classLeaderService.getMarketChildren(query.getDepartId()));
		}
		if (user.getCurrentRole().getId().equals(112L)) {
			
			query.setSellerId(user.getId());
			
		}
		List<SchoolActiveUserStat> schoolActiveUserInfo = activeUserStaService.getDistrictSchoolActiveUser(query);
		
		List<SchoolActiveUserStat> list = new ArrayList<>(); 
		
		page.setTotalSize(schoolActiveUserInfo.size());
		
		for (int i = 0; i < page.getPageSize() && i< schoolActiveUserInfo.size() - page.getOffset(); i++) {
			list.add(schoolActiveUserInfo.get(page.getOffset() + i));
		}
		JsonResult jsonResult = new JsonResult();
		
		jsonResult.addDatas("record", list);
		
		jsonResult.addDatas("page", page);
		
		return jsonResult;
	}
	
	@RequestMapping("classActiveUser")
	public void classActiveUser(Model model,ActiveUserQuery query){
		User user = UserContext.user.get();
		// 角色ID
		Long roleId = user.getCurrentRole().getId();
		Long id = query.getRoleId();
		
		List<ActiveUserQuery> classBySchool = activeUserStaService.getClassBySchool(query);
		
		model.addAttribute("query", query);
		
		model.addAttribute("id", id);
		
		model.addAttribute("activeInfo", JsonUtils.toJSON(classBySchool));
		
		model.addAttribute("roleId", roleId);
	}
	
	@RequestMapping("getClassActiveUserData")
	@ResponseBody
	public JsonResult getClassActiveUserData(ActiveUserQuery query,Page page){
		List<SchoolActiveUserStat> classActiveUserInfo = activeUserStaService.getClassActiveUserInfo(query);
		
		List<SchoolActiveUserStat> list = new ArrayList<>();
		
		page.setTotalSize(classActiveUserInfo.size());
		
		for (int i = 0; i < page.getPageSize() && i< classActiveUserInfo.size() - page.getOffset(); i++) {
			list.add(classActiveUserInfo.get(page.getOffset() + i));
		}
		JsonResult jsonResult = new JsonResult();
		
		jsonResult.addDatas("record", list);
		
		jsonResult.addDatas("page", page);
		
		return jsonResult;
	}
	
	
	@RequestMapping("exportSchoolActiveUser")
	@ResponseBody
	public void exportSchoolActiveUser(String dataJson,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		ActiveUserQuery query = JsonUtils.fromJSON(dataJson, ActiveUserQuery.class);
		User user = UserContext.user.get();
		Long roleId = user.getCurrentRole().getId();
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), roleId);
		
		
		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		if (listSeller.size() > 0) {
			query.setListSeller(listSeller);
		}
		//只选择了营销部，没有选择营销处，查出营销部下所有营销处！
		if (query.getDepartId() != null && query.getMarketId() == null) {
			
			query.setListMarketId(classLeaderService.getMarketChildren(query.getDepartId()));
		}
		List<SchoolActiveUserStat> list = activeUserStaService.getDistrictSchoolActiveUser(query);
		
		if (roleId.equals(112L)) {
			String[] headers = { "日期",  "学校","累计注册用户","累计有效用户","活跃用户","活跃率"};
			String[] fieldNames = { "ts",  "schoolName", "registered", "validUsers", "activeUsersCount", "activePro"};
			exportExcelData(request, response, list, headers, fieldNames,query);
		}else if (roleId.equals(603L)) {
			String[] headers = { "日期", "乐号", "客户经理", "学校","累计注册用户",
					"累计有效用户","活跃用户","活跃率"};
			String[] fieldNames = { "ts", "loginName", "sellerName",
					"schoolName", "registered", "validUsers", "activeUsersCount", "activePro"};
			exportExcelData(request, response, list, headers, fieldNames,query);
		}else if (roleId.equals(604L) ) {
			String[] headers = { "日期", "乐号", "客户经理", "营销部", "营销处","学校","累计注册用户",
					"累计有效用户","活跃用户","活跃率"};
			String[] fieldNames = { "ts", "loginName", "sellerName","departName", "marketName",
					"schoolName", "registered", "validUsers", "activeUsersCount", "activePro"};
			exportExcelData(request, response, list, headers, fieldNames,query);
		}else {
			String[] headers = { "日期", "乐号", "客户经理",  "营销处","学校","累计注册用户",
					"累计有效用户","活跃用户","活跃率"};
			String[] fieldNames = { "ts", "loginName", "sellerName", "marketName",
					"schoolName", "registered", "validUsers", "activeUsersCount", "activePro"};
			exportExcelData(request, response, list, headers, fieldNames,query);
		}
	}
	
	
	@RequestMapping("exportClassActiveUser")
	@ResponseBody
	public void exportClassActiveUser(String dataJson,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		ActiveUserQuery query = JsonUtils.fromJSON(dataJson, ActiveUserQuery.class);
		
		List<SchoolActiveUserStat> list = activeUserStaService.getClassActiveUserInfo(query);
		
		String[] headers = { "年级", "班级","累计有效用户","活跃用户","活跃率"};
		
		String[] fieldNames = { "gradeName", "className", "validUsers", "activeUsersCount", "activePro"};
		
		exportExcelData(request, response, list, headers, fieldNames,query);
	}
	
	
	/**
	 * 导出数据到excel中
	 * @param request
	 * @param response
	 * @param list
	 * @param headers
	 * @param fieldNames
	 * @throws Exception
	 */
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response,
			List<SchoolActiveUserStat> list,String[] headers,String[] fieldNames,ActiveUserQuery query)
			throws Exception {
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("用户统计("+query.getCycle()+")");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()
				+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<SchoolActiveUserStat>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
}
