package cn.strong.leke.monitor.controller.resource;

import java.text.ParseException;
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
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.User;
import cn.strong.leke.monitor.core.service.resource.IResourceService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopSta;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendSta;
import cn.strong.leke.monitor.util.FileUtils;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

/**
 * 
 *资源库资源使用明细查询控制层接口
 */
@Controller
@RequestMapping("/auth/common/resource/*")
public class ResourceController {
	
	@Resource
	private IResourceService resourceService;
	
	@RequestMapping("resourcedetail")
	public void resourceUsedSta(Model model){
		
		User user = UserContext.user.get();
		
		List<SchoolStageRemote> schoolStages = SchoolStageContext.findSchoolStages();
		
		List<SubjectRemote> subjects = SubjectContext.findSubjects();
		Long roleId = user.getCurrentRole().getId();
		if (roleId.equals(402L)) {		
			SchoolStageRemote stageRemote = resourceService.getSchoolStageByResearch(user.getId());

			SubjectRemote subjectRemote = resourceService.getSubjectByResearch(user.getId());
		
			model.addAttribute("stage", stageRemote);
			model.addAttribute("subject", subjectRemote);
		}
		model.addAttribute("schoolStage", schoolStages);
		
		model.addAttribute("roleId", roleId);
		model.addAttribute("subjects",subjects);
	}
	
	@RequestMapping("schoolResourceUsedDetail")
	@ResponseBody
	public JsonResult schoolResourceUsedDetail(ResourceUsedQuery query,Page page){
		JsonResult jsonResult = new JsonResult();
		
		List<SchoolResourceUsedSta> resourceUsedSta = resourceService.getSchoolResourceUsedSta(query);

		List<SchoolResourceUsedSta> result = new ArrayList<>();
		page.setTotalSize(resourceUsedSta.size());
		
		for (int i = 0; i < page.getPageSize() && i< resourceUsedSta.size()- page.getOffset(); i++) {
			result.add(resourceUsedSta.get(page.getOffset() + i));
		}
		
		jsonResult.addDatas("record", result);
		jsonResult.addDatas("page", page);  
		return jsonResult;
	}
	@RequestMapping("resourceUsedChart")
	@ResponseBody
	public JsonResult resourceUsedChart(ResourceUsedQuery query){
		ResourceUsedSta resourceUsedSta = resourceService.getResourceUsedChart(query);
		
		JsonResult jsonResult = new JsonResult();
		
		List<ResourceUsedSta> resourceUsedStas = resourceUsedSta.getResourceUsedStas();
		
		int size = resourceUsedStas.size();
		
		String[] dateArr = new String[size];
		
		Integer[]  newResourceNumArr = new Integer[size];
		
		Integer[]  usedResourceNumArr = new Integer[size];
		
		Integer[]  usedRescourceCountArr = new Integer[size];
		
		int  i = 0;
		for (ResourceUsedSta resource : resourceUsedStas) {
			dateArr[i] = resource.getTs();
			
			newResourceNumArr[i] = resource.getNewResourceNum();
			
			usedResourceNumArr[i] = resource.getUsedResourceNum();
			
			usedRescourceCountArr[i] = resource.getUsedRescourceCount();
			i++;
		}
		
		jsonResult.addDatas("resourceUsedSta", resourceUsedSta);
		
		jsonResult.addDatas("dateArr", dateArr);
		
		jsonResult.addDatas("newResourceNumArr", newResourceNumArr);
		
		jsonResult.addDatas("usedResourceNumArr", usedResourceNumArr);
		
		jsonResult.addDatas("usedRescourceCountArr", usedRescourceCountArr);
		
		return jsonResult;
	}
	
	
	@RequestMapping("exportExcelData")
	@ResponseBody
	public void exportExcelData(String dataJson,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		ResourceUsedQuery query = JsonUtils.fromJSON(dataJson, ResourceUsedQuery.class);
		
		List<SchoolResourceUsedSta> list = resourceService.getSchoolResourceUsedSta(query);
		
		String[] headers = { "学校", "总使用量","下载量","引用量","编辑量"};
		
		String[] fieldNames = { "schoolName", "usedNum", "downloadNum", "quoteNum", "compileNum"};
		
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("学校使用明细");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()
				+ ".xls", agent);
		
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		new ExportExcelForTable<SchoolResourceUsedSta>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
	
	
	@RequestMapping("resourceTopFifty")
	public void resourceTopFifty(Model model,ResourceTopQuery query){
		
		List<SchoolStageRemote> schoolStages = SchoolStageContext.findSchoolStages();
		
		List<SubjectRemote> subjects = SubjectContext.findSubjects();
		
		model.addAttribute("schoolStage", schoolStages);
		
		model.addAttribute("subjects",subjects);
		
		model.addAttribute("query", query);

	}
	
	@RequestMapping("getResourceTopFiftyData")
	@ResponseBody
	public JsonResult getResourceTopFiftyData(ResourceTopQuery query,Page page){
		JsonResult jsonResult = new JsonResult();
		List<ResourceTopSta> list = resourceService.getResourceTopInfo(query,page);
		
		List<ResourceTopSta> result = new ArrayList<>();
		
		page.setTotalSize(list.size());
		
		for (int i = 0; i < page.getPageSize() && i< list.size()- page.getOffset(); i++) {
			result.add(list.get(page.getOffset() + i));
		}
		
		jsonResult.addDatas("record", result);  
		jsonResult.addDatas("page", page);  
		return jsonResult;
		
	}
	
	/**
	 * 学校使用趋势
	 * @param model
	 * @param query
	 */
	@RequestMapping("schoolUsedTrend")
	public void schoolUsedTrend(Model model,SchoolUsedTrendQuery query){
		model.addAttribute("query", query);
	}
	
	@RequestMapping("getDataForChart")
	@ResponseBody
	public JsonResult getDataForChart(SchoolUsedTrendQuery query) throws ParseException{
		
		List<SchoolUsedTrendSta> schoolUsedTrendStas = resourceService.getSchoolUsedNum(query);
		
		JsonResult jsonResult = new JsonResult();
		
		int size = schoolUsedTrendStas.size();
		
		String[] dateArr = new String[size];
		
		Integer[] usedNumArr = new Integer[size];
		
		int  i = 0;
		for(SchoolUsedTrendSta schoolUsedTrendSta:schoolUsedTrendStas){
			
			dateArr[i] = schoolUsedTrendSta.getTs();
			
			if(schoolUsedTrendSta.getUsedNum()==null){
				usedNumArr[i] = 0;
			}else{
				usedNumArr[i] = schoolUsedTrendSta.getUsedNum();
			}
			i ++;
		}
		
		jsonResult.addDatas("dateArr", dateArr);
		jsonResult.addDatas("usedNumArr", usedNumArr);
		
		return jsonResult;
	}
	
	
	
	/**
	 * 导出数据
	 * @param dataJson
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportResourceTopData")
	@ResponseBody
	public void exportResourceTopData(String dataJson,
			HttpServletRequest request, HttpServletResponse response,Page page) throws Exception{
		ResourceTopQuery query = JsonUtils.fromJSON(dataJson,ResourceTopQuery.class);
		List<ResourceTopSta> list = resourceService.getResourceTopInfo(query, page);
		
		if(query.getResType() == 1){
			String[] headers = {"序号","资源ID","使用量","上传人","学段","学科"};
			String[] fieldNames = {"rank","resId","usedNum","uploadName","stageName","subjectName"};
			exportExcelData(request, response, list, headers, fieldNames);
		}else {
			String[] headers = {"序号","资源ID","资源名称","使用量","上传人","学段","学科"};
			String[] fieldNames = {"rank","resId","resourceName","usedNum","uploadName","stageName","subjectName"};
			exportExcelData(request, response, list, headers, fieldNames);
		}
	}
	
	public void exportExcelData(HttpServletRequest request, HttpServletResponse response,
			List<ResourceTopSta> list,String[] headers,String[] fieldNames)
			throws Exception {
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("资源使用Top50统计");
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString()
				+ ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<ResourceTopSta>().exportExcel(
				titleSingle.toString(), headers, fieldNames, list,
				response.getOutputStream(), pattern);
	}
	
}
