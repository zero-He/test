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

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.BeikeRate;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.CorrectHW;
import cn.strong.leke.diag.model.teachingMonitor.LessonAttendInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ResolveDoubt;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.CorrectHWService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;

@Controller
@RequestMapping("/auth/provost/teachingMonitor/homework/")
public class CorrectHWController {
	protected static final Logger logger = LoggerFactory.getLogger(CorrectHWController.class);
	
	@Resource
	private CommService commService;
	
	@Resource
	private CorrectHWService correctHWService;
	
	@RequestMapping("toShowCorrectHWRateStat")
	public String toShowCorrectHWRateStat(){
		return "/auth/teachingMonitor/correctHWStat";
	}
	
	public void handCorrectHW(CorrectHW dest, CorrectHW orig){
		CorrectHW temp = (CorrectHW)BeanUtils.cloneBean(dest);
		BeanUtils.copyProperties(dest, orig);
		dest.setStartDate(temp.getStartDate());
		dest.setEndDate(temp.getEndDate());
		dest.setDateStr(temp.getDateStr());
		formatCorrectHWRate(dest);
	}
	
	/**
	 * 查询作业批改统计信息
	 * @param vo
	 * @return
	 */
	public CorrectHW handleCorrectHWStat(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		CorrectHW lessonTeacherHW = correctHWService.findCorrectHWLessonTeacherNum(vo);
		CorrectHW assignHW = correctHWService.findCorrectHWAssignNum(vo);
		CorrectHW correctHW = correctHWService.findCorrectHWComm(vo);
		
		correctHW.setTeacherNum(assignHW.getTeacherNum());
		correctHW.setLessonNum(lessonTeacherHW.getLessonNum());
		
		if(lessonTeacherHW.getLessonNum() == 0L){
			correctHW.setAvgHWPerLesson("0");
		}else{
			correctHW.setAvgHWPerLesson(NumberUtils.formatScore(Double.valueOf(assignHW.getAssignNum()/lessonTeacherHW.getLessonNum())));
		}
		
		if(assignHW.getTeacherNum() == 0L){
			correctHW.setAvgHWPerTeacher("0");
		}else{
			correctHW.setAvgHWPerTeacher(NumberUtils.formatScore(Double.valueOf(assignHW.getAssignNum()/assignHW.getTeacherNum())));
		}
		
		correctHW.setAssignNum(assignHW.getAssignNum());
		correctHW.setSubjectiveNum(assignHW.getSubjectiveNum());
		correctHW.setSubjectiveNumRate(NumberUtils.formatScore(Double.valueOf(assignHW.getSubjectiveNumRate())));
		correctHW.setObjectiveNum(assignHW.getObjectiveNum());
		correctHW.setObjectiveNumRate(NumberUtils.formatScore(Double.valueOf(assignHW.getObjectiveNumRate())));
		formatCorrectHWRate(correctHW);
		
		return correctHW;
	}
	
	public void formatCorrectHWRate(CorrectHW correctHW){
		correctHW.setCorrectNumRate(NumberUtils.formatScore(Double.valueOf(correctHW.getCorrectNumRate())));
		correctHW.setAutoCorrectNumRate(NumberUtils.formatScore(Double.valueOf(correctHW.getAutoCorrectNumRate())));
		correctHW.setTeacherCorrectNumRate(NumberUtils.formatScore(Double.valueOf(correctHW.getTeacherCorrectNumRate())));
		correctHW.setStudentCorrectNumRate(NumberUtils.formatScore(Double.valueOf(correctHW.getStudentCorrectNumRate())));
		correctHW.setNotCorrectNumRate(NumberUtils.formatScore(Double.valueOf(correctHW.getNotCorrectNumRate())));
	}
	
	/**
	 * 计算作业批改率趋势
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> handleCorrectHWByTrendType(RequestVo vo){
		
		commService.setCommPropToRequestVo(vo);
		
		List<CorrectHW> correctHWList = null; 
		if(RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())){
			correctHWList = correctHWService.findCorrectHWByWeek(vo);
		}else if(RequestVo.MONTH.equalsIgnoreCase(vo.getTrendType())){
			correctHWList = correctHWService.findCorrectHWByMonth(vo);
		}else{
			correctHWList = correctHWService.findCorrectHWByDay(vo);
		}
		
		correctHWList.stream().forEach(v->{
			formatCorrectHWRate(v);
		});
		
		return correctHWList;
	}
	
	/**
	 * 查询批改率趋势
	 * @param vo
	 * @return
	 */
	@RequestMapping("findCorrectHWByTrendType")
	@ResponseBody
	public JsonResult findCorrectHWByTrendType(RequestVo vo){
		JsonResult json = new JsonResult();
		List<CorrectHW> correctHWTrendList = handleCorrectHWByTrendType(vo);
		json.addDatas("correctHWTrendList", correctHWTrendList);
		return json;
	}
	
	/**
	 * 处理作业批改率对比数据
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> handleCorrectHWByCompType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		List<CorrectHW> correctHWList = null; 
		if(RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType())){
			correctHWList = correctHWService.findCorrectHWByAllSubject(vo);
		}else if(RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())){
			correctHWList = correctHWService.findCorrectHWByGradeSubject(vo);
		}else if(RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())){
			correctHWList = correctHWService.findCorrectHWByClazz(vo);
		}else{
			correctHWList = correctHWService.findCorrectHWByGrade(vo);
		}
		
		correctHWList.stream().forEach(v->{
			formatCorrectHWRate(v);
		});
		
		return correctHWList;
	}
	
	/**
	 * 根据对比类型查询作业批改率
	 * @param vo
	 * @return
	 */
	@RequestMapping("findCorrectHWByCompType")
	@ResponseBody
	public JsonResult findCorrectHWByCompType(RequestVo vo){
		JsonResult json = new JsonResult();
		List<CorrectHW> correctHWCompList = handleCorrectHWByCompType(vo);
		json.addDatas("correctHWCompList", correctHWCompList);
		return json;
	}
	
	/**
	 * 老师作业批改率排行
	 * @param vo
	 * @return
	 */
	public Map<String, List<CorrectHW>> handleCorrectHWRank(RequestVo vo, String orderAttr){
		Map<String, List<CorrectHW>> correctHWRankMap = new HashMap<>();
		List<CorrectHW> topFiveList = null;
		List<CorrectHW> lastFiveList = null;
		
		commService.setCommPropToRequestVo(vo);
		
		List<CorrectHW> resolveDoubtList = correctHWService.findCorrectHWByRank(vo);
		
		if("correctNumRate".equalsIgnoreCase(orderAttr)){
			resolveDoubtList = resolveDoubtList.stream().sorted((a, b)->{
				BigDecimal x = new BigDecimal(a.getCorrectNumRate());
				BigDecimal y = new BigDecimal(b.getCorrectNumRate());
				return y.compareTo(x);
			}).collect(Collectors.toList());
			
			resolveDoubtList.stream().forEach(v->{
				v.setCorrectNumRate(NumberUtils.formatScore(Double.valueOf(v.getCorrectNumRate())) + "%");
			});
		}else if("teacherCorrectNum".equalsIgnoreCase(orderAttr)){
			resolveDoubtList = resolveDoubtList.stream().sorted((a, b)->{
				BigDecimal x = new BigDecimal(a.getTeacherCorrectNum());
				BigDecimal y = new BigDecimal(b.getTeacherCorrectNum());
				return y.compareTo(x);
			}).collect(Collectors.toList());
		}
		
		int size = resolveDoubtList.size();
		
		CorrectHW correctHW = new CorrectHW();
		correctHW.setTeacherName(CommProp.DEFAULT_RANK_TEACHER_NAME);
		correctHW.setCorrectNumRate(CommProp.DEFAULT_RANK_DATA);
		correctHW.setTeacherCorrectNum(0L);
		
		topFiveList = resolveDoubtList.stream().limit(5).collect(Collectors.toList());
		int topFiveListSize = topFiveList.size();
		for(int i = 0; i < 5 - topFiveListSize; i++){
			topFiveList.add(correctHW);
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
			lastFiveList.add(correctHW);
		}
		
		correctHWRankMap.put("topFive", topFiveList);
		correctHWRankMap.put("lastFive", lastFiveList);
		
		return correctHWRankMap;
	}
	
	/**
	 * 加载统计信息页面所有数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findAllCorrectHWStatInfo")
	@ResponseBody
	public JsonResult findAllCorrectHWStatInfo(RequestVo vo){
		JsonResult json = new JsonResult();
		json.addDatas("statInfo", handleCorrectHWStat(vo));
		
		json.addDatas("trendList", handleCorrectHWByTrendType(vo));
		json.addDatas("compList", handleCorrectHWByCompType(vo));
		Map<String, List<CorrectHW>> correctHWRateRankMap = handleCorrectHWRank(vo, "correctNumRate");
		json.addDatas("topFiveList", correctHWRateRankMap.get("topFive"));
		json.addDatas("lastFiveList", correctHWRateRankMap.get("lastFive"));
		Map<String, List<CorrectHW>> correctHWNumRankMap = handleCorrectHWRank(vo, "teacherCorrectNum");
		json.addDatas("topFiveNumList", correctHWNumRankMap.get("topFive"));
		json.addDatas("lastFiveNumList", correctHWNumRankMap.get("lastFive"));
		
		return json;
	}
	
	/**
	 * 处理批改率明细数据
	 * @param vo
	 * @return
	 */
	public List<CorrectHW> handleCorrectHWDtl(RequestVo vo, Page page){
		commService.setCommPropToRequestVo(vo);
		if(StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())){
			vo.setOrderAttr("correctNumRate");
			vo.setOrderType("desc");
		}
		List<CorrectHW> correctHWDtlList = null;
		if(null == page){
			correctHWDtlList = correctHWService.findCorrectHWDtl(vo);
		}else{
			correctHWDtlList = correctHWService.findCorrectHWDtl(vo, page);
		}
		correctHWDtlList.stream().forEach(v->{
			formatCorrectHWRate(v);
		});
		return correctHWDtlList;
	}
	
	/**
	 * 查询老师批改率详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findCorrectHWDtl")
	@ResponseBody
	public JsonResult findCorrectHWDtl(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		
		List<CorrectHW> correctHWDtlList = handleCorrectHWDtl(vo, page);
		
		int index = 0;
		for(CorrectHW hw : correctHWDtlList){
			++index;
			hw.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(correctHWDtlList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 排序老师作业批改率详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findCorrectHWDtlOrder")
	@ResponseBody
	public JsonResult findCorrectHWDtlOrder(RequestVo vo){
		JsonResult json = new JsonResult();
		
		List<CorrectHW> correctHWDtlList = null;
		if(CacheUtils.exists("correctHWDtlList")){
			correctHWDtlList = CacheUtils.get("correctHWDtlList");
		}else{
			correctHWDtlList = handleCorrectHWDtl(vo, null);
		}
		
		json.addDatas("correctHWDtlList", commService.handleOrderData(vo, correctHWDtlList, CorrectHW.class));
		return json;
	}
	
	@RequestMapping("exportCorrectHWDtlData")
	public void exportCorrectHWDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "作业批改率" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "布置作业份数", "纯客观份数", "主观份数", "系统批改人份" ,"系统批改人份占比", "老师批改人份", 
				            "老师批改人份占比", "学生互批人份", "学生互批人份占比", "未批改人份", "未批改人份占比"};
		String[] fieldNames = {"teacherName","subjectName", "assignNum", "objectiveNum", "subjectiveNum", "autoCorrectNum", "autoCorrectNumRate", "teacherCorrectNum",
				               "teacherCorrectNumRate", "studentCorrectNum", "studentCorrectNumRate", "notCorrectNum", "notCorrectNumRate"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		List<CorrectHW> excelList = handleCorrectHWDtl(vo, null);
		
		new ExportExcelForTable<CorrectHW>().exportExcel(title, headers, fieldNames, excelList,
				response.getOutputStream(),null);
		
	}
	
}
