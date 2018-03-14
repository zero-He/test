package cn.strong.leke.monitor.controller.activeuser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.core.service.activeuser.IPlatformActiveUserService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.mongo.course.model.query.PlatformActiveStat;
import cn.strong.leke.monitor.mongo.online.model.query.ActiveUserQuery;
import cn.strong.leke.monitor.util.FileUtils;

/**
 * 平台活跃用户统计控制层
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/auth/common/activeuser/*")
public class PlatformActiveUserController {
	
	@Resource 
	IPlatformActiveUserService iPlatformActiveUserService;
	
	@RequestMapping("platformActiveUserSta")
	public void platformActiveUserSta(Model model,ActiveUserQuery query){
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getPlatformActiveUserData")
	@ResponseBody
	public JsonResult getPlatformActiveUserData(ActiveUserQuery query,Page page){
		JsonResult jsonResult = new JsonResult();
		List<PlatformActiveStat> platformActiveUserInfo = iPlatformActiveUserService.getPlatformActiveUserInfo(query);
		
		List<PlatformActiveStat> list = new ArrayList<>();
		page.setTotalSize(platformActiveUserInfo.size());
		for(int i = 0; i < page.getPageSize() && i < platformActiveUserInfo.size() - page.getOffset(); i++){
			list.add(platformActiveUserInfo.get(page.getOffset() + i));
		}
			
		jsonResult.addDatas("record", list);
		jsonResult.addDatas("page", page);
		return jsonResult;
	}
	
	
	/**
	 * 统计图表
	 * @param query
	 * @return
	 */
	@RequestMapping("loadStatisForChart")
	@ResponseBody
	public JsonResult loadStatisForChart(ActiveUserQuery query){
		List<PlatformActiveStat> list = iPlatformActiveUserService.getPlatformActiveUserInfo(query);
		JsonResult jsonResult = new JsonResult();
		int length = list.size();
		int dateArr[] = new int[length];
		int activeUsersCountArr[] = new int[length];
		String activeProArr[] = new String[length];
		float activeProArrf[] = new float[length];
		
		int i = 0;
		for(PlatformActiveStat platformActiveStat:list){
			dateArr[i] = platformActiveStat.getTs();
			activeUsersCountArr[i] = platformActiveStat.getActiveUsersCount();
			activeProArr[i] = platformActiveStat.getActivePro();
			activeProArrf[i] = Float.parseFloat(activeProArr[i].substring(0,activeProArr[i].indexOf("%")));
			i++;
		}
		
		jsonResult.addDatas("dateArr", dateArr);
		jsonResult.addDatas("activeUsersCountArr", activeUsersCountArr);
		jsonResult.addDatas("activeProArr", activeProArrf);
		return jsonResult;
	}
	
	
	@RequestMapping("exportPlatformActiveUser")
	@ResponseBody
	public void exportPlatformActiveUser(String dataJson,HttpServletRequest request, 
			HttpServletResponse response,Page page) throws Exception{
		
		ActiveUserQuery query = JsonUtils.fromJSON(dataJson, ActiveUserQuery.class);
		
		List<PlatformActiveStat> list = iPlatformActiveUserService.getPlatformActiveUserInfo(query);
		
		String[] headers = { "日期","累计有效用户","活跃用户","活跃率"};
		
		String[] fieldNames = { "ts", "allValidUser", "activeUsersCount", "activePro"};
		
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
			List<PlatformActiveStat> list,String[] headers,String[] fieldNames,ActiveUserQuery query)
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
		new ExportExcelForTable<PlatformActiveStat>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
	
}
