package cn.strong.leke.monitor.controller.crm;

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
import cn.strong.leke.monitor.core.model.IndiClassStatisticsDTO;
import cn.strong.leke.monitor.core.model.IndividualsInDto;
import cn.strong.leke.monitor.core.service.IClassLeaderService;
import cn.strong.leke.monitor.core.service.IIndividualsInService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.util.FileUtils;

/**
 * 
 * @Description: 个人入驻统计控制器层接口
 */
@Controller
@RequestMapping("/auth/common/crm/*")
public class IndividualsInController {
	
	@Resource
	private IIndividualsInService individualsInService; 
	@Resource
	private IClassLeaderService classLeaderService;
	
	@RequestMapping("individualsIn")
	public void individualsIn(Model model,IndividualsInDto query){
		User user = UserContext.user.get();

		// 角色ID
		Long roleId = user.getCurrentRole().getId();
		
		List<Area> areas = new ArrayList<Area>();

		// 营销处经理
		if (!roleId.equals(603L)){
			// 营销中心总经理
			if (roleId.equals(604L)){
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
		model.addAttribute("roleId", roleId);
	}
	
	
	@RequestMapping("getindividualsInData")
	@ResponseBody
	public JsonResult getindividualsInData(IndividualsInDto query,Page page){
		User user = UserContext.user.get();
		
		Long roleId = user.getCurrentRole().getId();
		
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), roleId);

		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		query.setRoleId(roleId);
		
		if (listSeller.size() > 0) {
			
			query.setListSeller(listSeller);
			
		}else {
			listSeller.add(Long.valueOf(0));

			query.setListSeller(listSeller);
		}
		
		//只选择了营销部，没有选择营销处，查出营销部下所有营销处！
		if (query.getDepartId() != null && query.getMarketId() == null) {
			
			query.setListMarketId(classLeaderService.getMarketChildren(query.getDepartId()));
		}else {
			query.setListMarketId(marketIds);
		}
		if (roleId.equals(112L)) {
			
			query.setSellerId(user.getId());
			
		}
		IndividualsInDto individualsInDto = individualsInService.getIndividualsInDetail(query);
		
		List<IndividualsInDto> list = individualsInService.getCustomerDetail(query, page);
		
		JsonResult jsonResult = new JsonResult();
		
		jsonResult.addDatas("record", list);
		
		jsonResult.addDatas("detail", individualsInDto);
		
		jsonResult.addDatas("page", page);
		
		return jsonResult;
	}
	
	@RequestMapping("loadStatisForChart")
	@ResponseBody
	public JsonResult loadStatisForChart(IndividualsInDto query){
		User user = UserContext.user.get();
		
		query.setOrder("tradeAmount");
		
		query.setSort("desc");

		Long roleId = user.getCurrentRole().getId();
		
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), roleId);

		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		query.setRoleId(roleId);
		
		if (listSeller.size() > 0) {
			
			query.setListSeller(listSeller);
			
		}else {
			listSeller.add(Long.valueOf(0));

			query.setListSeller(listSeller);
		}
		
		//只选择了营销部，没有选择营销处，查出营销部下所有营销处！
		if (query.getDepartId() != null && query.getMarketId() == null) {
			
			query.setListMarketId(classLeaderService.getMarketChildren(query.getDepartId()));
		}else {
			query.setListMarketId(marketIds);
		}
		if (roleId.equals(112L)) {
			
			query.setSellerId(user.getId());
			
		}
		
		
		List<IndividualsInDto> list = individualsInService.getCustomerDetail(query, null);
		
		JsonResult jsonResult = new JsonResult();
		
		int length = list.size();
		
		String nameArr[] = new String[length];
		
		float tradeAmountArr[] = new float[length]; 
		
		float commAmountArr[] = new float[length];
		
		int newTeachNumArr[] = new int[length];
		
		int i = 0;
		for (IndividualsInDto individual : list) {
			tradeAmountArr[i] = individual.getTradeAmount();
			
			commAmountArr[i] = individual.getCommAmount();
			
			if (roleId.equals(112L)) {
				nameArr[i] = individual.getTeacherName();
				
			}else if (roleId.equals(603L)) {
				
				nameArr[i] = individual.getSellerName();
			}else {
				
				nameArr[i] = individual.getMarketName();
			}
			
			if (!roleId.equals(112L)) {
				
				newTeachNumArr[i] = individual.getNewTeacherNum();
				
				jsonResult.addDatas("newTeachNumArr", newTeachNumArr);
			}
			i++;
		}
		
		jsonResult.addDatas("tradeAmountArr", tradeAmountArr);
		
		jsonResult.addDatas("commAmountArr", commAmountArr);
		
		jsonResult.addDatas("nameArr", nameArr);
		
		jsonResult.addDatas("roleId", roleId);
		
		return jsonResult;
	}
	
	@RequestMapping("teacherInDetail")
	public void teacherDetail(Model model,IndividualsInDto query) {
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getTeacherInDetailData")
	@ResponseBody
	public JsonResult getTeacherInDetailData(IndividualsInDto query,Page page) {
		JsonResult jsonResult = new JsonResult();
		
		List<IndividualsInDto> list = individualsInService.getTeacherDetail(query, page);
		
		jsonResult.addDatas("record", list);
		
		jsonResult.addDatas("page", page);
		
		return jsonResult;
	}
	
	@RequestMapping("sellerInDetail")
	public void sellerInDetail(Model model,IndividualsInDto query) {
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getSellerInDetailData")
	@ResponseBody
	public JsonResult getSellerInDetailData(IndividualsInDto query,Page page) {
		JsonResult jsonResult = new JsonResult();
		
		List<IndividualsInDto> list = individualsInService.getSellerInDetail(query, page);
		
		jsonResult.addDatas("record", list);
		
		jsonResult.addDatas("page", page);
		
		return jsonResult;
	}
	
	/**
	 * 课时统计
	 * @param model
	 * @param query
	 */
	@RequestMapping("indiClassStatistics")
	public void indiClassStatistics(Model model,IndividualsInDto query) {
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getClassStatisticsData")
	@ResponseBody
	public JsonResult getClassStatisticsData(IndividualsInDto query) {
		JsonResult jsonResult = new JsonResult();
		IndiClassStatisticsDTO list = individualsInService.getClassStatisticsList(query);
		jsonResult.addDatas("record", list);
		return jsonResult;
	}
	/**
	 * 导出课时统计数据
	 * @param dataJson
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportClassStatisticsData")
	@ResponseBody
	public void exportClassStatisticsData(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndividualsInDto query = JsonUtils.fromJSON(dataJson,IndividualsInDto.class);
		IndiClassStatisticsDTO indiClassStatisticsDTO = individualsInService.getClassStatisticsList(query);
		List<IndiClassStatisticsDTO> list = new ArrayList<>();
		list.add(indiClassStatisticsDTO);
		String[] headers = { "已结束课堂数", "上课课堂数", "上课率", "应上课人次", "实上课人次","到课率","应上课学生数","实上课学生数"};
		String[] fieldNames = { "finishedClassNum", "attendedClassNum","classRate", "mustClassTimes","actualClassTimes", "attendanceRate","mustClassNum","actualClassNum"};
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("课时统计-个人入驻");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<IndiClassStatisticsDTO>().exportExcel(
				titleSingle.toString(), headers, fieldNames,  list,
				response.getOutputStream(), pattern);
	}
	
	
	@RequestMapping("exportExcel")
	@ResponseBody
	public void exportExcel(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		IndividualsInDto query = JsonUtils.fromJSON(dataJson,IndividualsInDto.class);
		
		User user = UserContext.user.get();
		
		Long roleId = user.getCurrentRole().getId();
		
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), user.getCurrentRole().getId());
		
		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		query.setRoleId(roleId);
		
		if (listSeller.size() > 0) {
			query.setListSeller(listSeller);
		}
		
		//只选择了营销部，没有选择营销处，查出营销部下所有营销处！
		if (query.getDepartId() != null && query.getMarketId() == null) {
			
			query.setListMarketId(classLeaderService.getMarketChildren(query.getDepartId()));
		}else {
			query.setListMarketId(marketIds);
		}
		if (user.getId().equals(112L)) {
			
			query.setSellerId(user.getId());
			
		}
		List<IndividualsInDto> list = individualsInService.getCustomerDetail(query, null);
		
		
		if (roleId.equals(112L)) {
			String[] headers = { "乐号", "手机", "绑定时间","老师姓名","充值金额","提成金额"};
			String[] fieldNames = { "loginName", "phone", "tradeTime", "teacherName", "tradeAmount", "commAmount"};
			exportExcelData(request, response, list, headers, fieldNames);
		}else if (roleId.equals(603L)) {
			String[] headers = { "乐号", "客户经理", "新增老师数","充值金额","提成金额"};
			String[] fieldNames = { "loginName", "sellerName", "newTeacherNum", "tradeAmount", "commAmount"};
			exportExcelData(request, response, list, headers, fieldNames);
		}else if (roleId.equals(604L) ) {
			String[] headers = { "营销部","营销处", "新增老师数","充值金额","提成金额"};
			String[] fieldNames = {"departName","marketName","newTeacherNum", "tradeAmount", "commAmount"};
			exportExcelData(request, response, list, headers, fieldNames);
		}else {
			String[] headers = { "营销处", "新增老师数","充值金额","提成金额"};
			String[] fieldNames = {"marketName","newTeacherNum", "tradeAmount", "commAmount"};
			exportExcelData(request, response, list, headers, fieldNames);
		}
	}
	
	@RequestMapping("exportSellerdetail")
	@ResponseBody
	public void exportSellerdetail(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndividualsInDto query = JsonUtils.fromJSON(dataJson,IndividualsInDto.class);
		
		List<IndividualsInDto> list = individualsInService.getSellerInDetail(query, null);
		
		String[] headers = { "乐号", "客户经理", "新增老师数","充值金额","提成金额"};
		
		String[] fieldNames = {  "loginName", "sellerName", "newTeacherNum", "tradeAmount", "commAmount"};
		
		exportExcelData(request, response, list, headers, fieldNames);
	}
	
	@RequestMapping("exportTeacherdetail")
	@ResponseBody
	public void exportTeacherdetail(String dataJson,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		IndividualsInDto query = JsonUtils.fromJSON(dataJson,IndividualsInDto.class);
		
		List<IndividualsInDto> list = individualsInService.getTeacherDetail(query, null);
		
		String[] headers = { "乐号", "手机", "绑定时间","老师姓名","充值金额","提成金额"};
		
		String[] fieldNames = { "loginName", "phone", "tradeTime", "teacherName", "tradeAmount", "commAmount"};
		
		exportExcelData(request, response, list, headers, fieldNames);
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
			List<IndividualsInDto> list,String[] headers,String[] fieldNames)
			throws Exception {
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("个人入驻统计");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()
				+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<IndividualsInDto>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
}
