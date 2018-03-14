package cn.strong.leke.diag.controller.teachingMonitor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.LessonAttendInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ResolveDoubt;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.ResolveDoubtService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;

@Controller
@RequestMapping("/auth/provost/teachingMonitor/doubt/")
public class ResolveDoubtController {
	protected static final Logger logger = LoggerFactory.getLogger(ResolveDoubtController.class);
	
	@Resource
	private CommService commService;
	
	@Resource
	private ResolveDoubtService resolveDoubtService;
	
	@RequestMapping("toShowResolveDoubtStat")
	public String toShowResolveDoubtStat(){
		return "/auth/teachingMonitor/resolveDoubtStat";
	}
	
	/**
	 * 处理解答数统计
	 * @param vo
	 * @return
	 */
	public ResolveDoubt handleResolveDoubtStat(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		ResolveDoubt resolveDoubt = resolveDoubtService.findResolveDoubtStat(vo);
		resolveDoubt.setResolveRate(NumberUtils.formatScore(Double.valueOf(resolveDoubt.getResolveRate())));
		resolveDoubt.setTeacherAvgResolveRate(NumberUtils.formatScore(Double.valueOf(resolveDoubt.getTeacherAvgResolveRate())));
		return resolveDoubt;
	}
	
	/**
	 * 处理解答数趋势数据
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> handleResolveDoubtByTrendType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		List<ResolveDoubt> resolveDoubtList = null; 
		if(RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())){
			resolveDoubtList = resolveDoubtService.findResolveDoubtByWeek(vo);
		}else if(RequestVo.MONTH.equalsIgnoreCase(vo.getTrendType())){
			resolveDoubtList = resolveDoubtService.findResolveDoubtByMonth(vo);
		}else{
			resolveDoubtList = resolveDoubtService.findResolveDoubtByDay(vo);
		}
		
		return resolveDoubtList;
	}
	
	/**
	 * 查询解答数趋势
	 * @param vo
	 * @return
	 */
	@RequestMapping("findResolveDoubtByTrendType")
	@ResponseBody
	public JsonResult findResolveDoubtByTrendType(RequestVo vo){
		JsonResult json = new JsonResult();
		List<ResolveDoubt> resolveDoubtTrendList = handleResolveDoubtByTrendType(vo);
		json.addDatas("resolveDoubtTrendList", resolveDoubtTrendList);
		return json;
	}
	
	/**
	 * 处理解答数对比数据
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> handleResolveDoubtByCompType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		List<ResolveDoubt> resolveDoubtList = null; 
		if(RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType())){
			resolveDoubtList = resolveDoubtService.findResolveDoubtByAllSubject(vo);
		}else if(RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())){
			resolveDoubtList = resolveDoubtService.findResolveDoubtByGradeSubject(vo);
		}else if(RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())){
			resolveDoubtList = resolveDoubtService.findResolveDoubtByClazz(vo);
		}else{
			resolveDoubtList = resolveDoubtService.findResolveDoubtByGrade(vo);
		}
		
		return resolveDoubtList;
	}
	
	/**
	 * 根据对比类型查询解答数
	 * @param vo
	 * @return
	 */
	@RequestMapping("findResolveDoubtByCompType")
	@ResponseBody
	public JsonResult findResolveDoubtByCompType(RequestVo vo){
		JsonResult json = new JsonResult();
		List<ResolveDoubt> resolveDoubtCompList = handleResolveDoubtByCompType(vo);
		json.addDatas("resolveDoubtCompList", resolveDoubtCompList);
		return json;
	}
	
	/**
	 * 老师解答数排行
	 * @param vo
	 * @return
	 */
	public Map<String, List<ResolveDoubt>> handleResolveDoubtRank(RequestVo vo){
		Map<String, List<ResolveDoubt>> resolveDoubtRankMap = new HashMap<>();
		List<ResolveDoubt> topFiveList = null;
		List<ResolveDoubt> lastFiveList = null;
		
		commService.setCommPropToRequestVo(vo);
		
		List<ResolveDoubt> resolveDoubtList = resolveDoubtService.findResolveDoubtByRank(vo).stream().sorted((a, b)->{
			BigDecimal x = new BigDecimal(a.getResolveDoubt());
			BigDecimal y = new BigDecimal(b.getResolveDoubt());
			return y.compareTo(x);
		}).collect(Collectors.toList());
		
		int size = resolveDoubtList.size();
		
		ResolveDoubt resolveDoubt = new ResolveDoubt();
		resolveDoubt.setTeacherName(CommProp.DEFAULT_RANK_TEACHER_NAME);
		resolveDoubt.setResolveDoubt(CommProp.DEFAULT_RANK_DATA);
		
		topFiveList = resolveDoubtList.stream().limit(5).collect(Collectors.toList());
		int topFiveListSize = topFiveList.size();
		for(int i = 0; i < 5 - topFiveListSize; i++){
			topFiveList.add(resolveDoubt);
		}
		
		if(size <= 5){
			lastFiveList = new ArrayList<>();
		}else if(size > 5 && size <= 10){
		    lastFiveList = resolveDoubtList.stream().skip(5).collect(Collectors.toList());	
		}else{
			lastFiveList = resolveDoubtList.stream().skip(size - 5).collect(Collectors.toList());
		}
		
		int lastFiveListSize = lastFiveList.size();
		for(int i = 0; i < 5 - lastFiveListSize; i++){
			lastFiveList.add(resolveDoubt);
		}
		
		resolveDoubtRankMap.put("topFive", topFiveList);
		resolveDoubtRankMap.put("lastFive", lastFiveList);
		
		return resolveDoubtRankMap;
	}
	
	/**
	 * 加载统计信息页面所有数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findAllResolveDoubtStatInfo")
	@ResponseBody
	public JsonResult findAllResolveDoubtStatInfo(RequestVo vo){
		JsonResult json = new JsonResult();
		json.addDatas("statInfo", handleResolveDoubtStat(vo));
		
		json.addDatas("trendList", handleResolveDoubtByTrendType(vo));
		json.addDatas("compList", handleResolveDoubtByCompType(vo));
		Map<String, List<ResolveDoubt>> lessonAttendRateRankMap = handleResolveDoubtRank(vo);
		json.addDatas("topFiveList", lessonAttendRateRankMap.get("topFive"));
		json.addDatas("lastFiveList", lessonAttendRateRankMap.get("lastFive"));
		
		return json;
	}
	
	/**
	 * 处理解答数明细数据
	 * @param vo
	 * @return
	 */
	public List<ResolveDoubt> handleResolveDoubtDtl(RequestVo vo, Page page){
		commService.setCommPropToRequestVo(vo);
		if(StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())){
			vo.setOrderAttr("resolveRate");
			vo.setOrderType("desc");
		}
		List<ResolveDoubt> resolveDoubtDtlList = null;
		if(null == page){
			resolveDoubtDtlList = resolveDoubtService.findResolveDoubtDtl(vo);
		}else{
			resolveDoubtDtlList = resolveDoubtService.findResolveDoubtDtl(vo, page);
		}
		
		resolveDoubtDtlList.stream().forEach(v->{
			v.setResolveRate(NumberUtils.formatScore(Double.valueOf(v.getResolveRate())));
		});
		
		return resolveDoubtDtlList;
	}
	
	/**
	 * 查询老师解答数详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findResolveDoubtDtl")
	@ResponseBody
	public JsonResult findResolveDoubtDtl(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		List<ResolveDoubt> resolveDoubtDtlList = handleResolveDoubtDtl(vo, page);
		
		int index = 0;
		for(ResolveDoubt rd : resolveDoubtDtlList){
			++index;
			rd.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(resolveDoubtDtlList);
		json.addDatas("page", page);
		json.addDatas("resolveDoubtDtlList", resolveDoubtDtlList);
		return json;
	}
	
	/**
	 * 排序老师解答数详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findResolveDoubtDtlOrder")
	@ResponseBody
	public JsonResult findResolveDoubtDtlOrder(RequestVo vo){
		JsonResult json = new JsonResult();
		List<ResolveDoubt> resolveDoubtDtlList = handleResolveDoubtDtl(vo, null);
		json.addDatas("resolveDoubtDtlList", commService.handleOrderData(vo, resolveDoubtDtlList, ResolveDoubt.class));
		return json;
	}
	
	@RequestMapping("exportResolveDoubtDtlData")
	public void exportResolveDoubtDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "学生提问解答数" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "解答率", "已解答数", "24小时内解答数", "24小时外解答数" ,"待解答数"};
		String[] fieldNames = {"teacherName","subjectName", "resolveRate", "resolveDoubt", "in24HourResolveDoubt",
				               "out24HourResolveDoubt", "notResolveDoubt"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		List<ResolveDoubt> excelList = handleResolveDoubtDtl(vo, null);
		
		new ExportExcelForTable<ResolveDoubt>().exportExcel(title, headers, fieldNames, excelList,
				response.getOutputStream(),null);
		
	}
}
