package cn.strong.leke.diag.controller.teachingMonitor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.BeikeRate;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.LessonAttendInfo;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikeInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.LessonAttendInfoService;
import cn.strong.leke.diag.service.teachingMonitor.LessonBeikeInfoService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

@Controller
@RequestMapping("/auth/provost/teachingMonitor/lessonAttend/")
public class LessonAttendController {
	
	protected static final Logger logger = LoggerFactory.getLogger(LessonAttendController.class);
	
	@Resource
	private LessonAttendInfoService lessonAttendInfoService;
	
	@Resource
	private LessonBeikeInfoService lessonBeikeInfoService;
	
	@Resource
	private CommService commService;
	
	public void handleLessonAttendRate(LessonAttendInfo lessonAttendInfo, List<LessonAttendInfo> lessonAttendList){
		if(!lessonAttendList.isEmpty() && null != lessonAttendList.get(0)){
			LessonAttendInfo attendInfo = lessonAttendList.get(0);
			lessonAttendInfo.setAttendLesson(attendInfo.getAttendLesson());
			lessonAttendInfo.setNotAttendLesson(attendInfo.getNotAttendLesson());
			lessonAttendInfo.setTotalLesson(attendInfo.getTotalLesson());
			lessonAttendInfo.setAttendLessonRate(attendInfo.getAttendLessonRate());
		}else{
			lessonAttendInfo.setAttendLesson(0L);
			lessonAttendInfo.setNotAttendLesson(0L);
			lessonAttendInfo.setTotalLesson(0L);
			lessonAttendInfo.setAttendLessonRate("0");
		}
	}
	
	@RequestMapping("toShowLessonAttendRateStat")
	public String toShowLessonAttendRateStat(){
		return "/auth/teachingMonitor/lessonAttendRateStat";
	}
	
	
	/**
	 * 查询上课率统计
	 * @param vo
	 * @return
	 */
	public LessonAttendInfo handleLessonAttendRateStat(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		LessonAttendInfo attendInfo = lessonAttendInfoService.findAttendLessonStat(vo);
		attendInfo.setAttendLessonRate(NumberUtils.formatScore(Double.valueOf(attendInfo.getAttendLessonRate())));
		return attendInfo;
	}
	
	/**
	 * 处理上课率趋势数据
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> handleLessonAttendRateByTrendType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		List<LessonAttendInfo> lessonAttendList = null; 
		if(RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())){
			lessonAttendList = lessonAttendInfoService.findAttendLessonRateByWeek(vo);
		}else if(RequestVo.MONTH.equalsIgnoreCase(vo.getTrendType())){
			lessonAttendList = lessonAttendInfoService.findAttendLessonRateByMonth(vo);
		}else{
			lessonAttendList = lessonAttendInfoService.findAttendLessonRateByDay(vo);
		}
		lessonAttendList.stream().forEach(v->{
			v.setAttendLessonRate(NumberUtils.formatScore(Double.valueOf(v.getAttendLessonRate())));
		});
		return lessonAttendList;
	}
	
	/**
	 * 查询上课率趋势
	 * @param vo
	 * @return
	 */
	@RequestMapping("findLessonAttendRateByTrendType")
	@ResponseBody
	public JsonResult findLessonAttendRateByTrendType(RequestVo vo){
		JsonResult json = new JsonResult();
		List<LessonAttendInfo> lessonAttendTrendList = handleLessonAttendRateByTrendType(vo);
		json.addDatas("lessonAttendTrendList", lessonAttendTrendList);
		return json;
	}
	
	
	public LessonAttendInfo buildLessonAttendInfo(String startDate, String endDate, Long gradeId, String gradeName, Long classId, String className,
			Long subjectId, String subjectName){
		LessonAttendInfo attendInfo = new LessonAttendInfo();
		attendInfo.setStartDate(startDate);
		attendInfo.setEndDate(endDate);
		attendInfo.setGradeId(gradeId);
		attendInfo.setGradeName(gradeName);
		attendInfo.setClassId(classId);
		attendInfo.setClassName(className);
		attendInfo.setSubjectId(subjectId);
		attendInfo.setSubjectName(subjectName);
		return attendInfo;
	}
	
	/**
	 * 处理上课率对比数据
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> handleLessonAttendRateByCompType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		List<LessonAttendInfo> lessonAttendAllList = null;
		List<GradeRemote> gradeList = null;
		List<SubjectRemote> subjectList = null;
		List<Clazz> clazzList = null;
		
		if(RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType()) || RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())){//全校按学科
			List<LessonAttendInfo> lessonAttendTemp = null;
			if(RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType())){
				//查询全校的学科
				subjectList = commService.findSubjectOfSchool();
				lessonAttendTemp = lessonAttendInfoService.findAttendLessonRateByAllSubject(vo);
			}else{
				//查询全校指定年级的学科
				subjectList = commService.findSubjectOfGradeBySchool(vo);
				lessonAttendTemp = lessonAttendInfoService.findAttendLessonRateByGradeSubject(vo);
			}
			
			final List<LessonAttendInfo> lessonAttendList = lessonAttendTemp;
			
			//如果请求参数指定某个学科，则返回该学科
			if(null != vo.getSubjectId() && vo.getSubjectId() != -1){
				subjectList = subjectList.stream().filter(v->v.getSubjectId().equals(vo.getSubjectId())).collect(Collectors.toList());
			}
			
			//将学科对象List转换为上课率对象List
			lessonAttendAllList = subjectList.stream().map(v->{
				return buildLessonAttendInfo(vo.getStartDate(), vo.getEndDate(), null, null, null, null, v.getSubjectId(), v.getSubjectName());
			}).collect(Collectors.toList());
			
			
			//循环学科计算学科在所选时间段内的上课率
			lessonAttendAllList.stream().forEach(v->{
				handleLessonAttendRate(v, lessonAttendList.stream().filter(b->b.getSubjectId().equals(v.getSubjectId())).collect(Collectors.toList()));
			});
			
		}else if(RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())){//年级按班级
			lessonAttendAllList = lessonAttendInfoService.findAttendLessonRateByClazz(vo);
		}else{//全校按年级，默认
			
			//查询全校的年级
			gradeList = commService.findGradeOfSchool();
			final List<LessonAttendInfo> lessonAttendList = lessonAttendInfoService.findAttendLessonRateByGrade(vo);
			
			//将年级对象List转换为上课率对象List
			lessonAttendAllList = gradeList.stream().map(v->{
				return buildLessonAttendInfo(vo.getStartDate(), vo.getEndDate(), v.getGradeId(), v.getGradeName(), null, null, null, null);
			}).collect(Collectors.toList());
			
			////循环年级计算年级在所选时间段内的上课率
			lessonAttendAllList.stream().forEach(v->{
				handleLessonAttendRate(v, lessonAttendList.stream().filter(b->b.getGradeId().equals(v.getGradeId())).collect(Collectors.toList()));
			});
		}
		return lessonAttendAllList;
	}
	
	
	/**
	 * 根据对比类型查询上课率
	 * @param vo
	 * @return
	 */
	@RequestMapping("findLessonAttendRateByCompType")
	@ResponseBody
	public JsonResult findLessonAttendRateByCompType(RequestVo vo){
		JsonResult json = new JsonResult();
		List<LessonAttendInfo> lessonAttendRateCompList = handleLessonAttendRateByCompType(vo);
		json.addDatas("lessonAttendRateCompList", lessonAttendRateCompList);
		return json;
	}
	
	/**
	 * 老师上课率排行
	 * @param vo
	 * @return
	 */
	public Map<String, List<LessonAttendInfo>> handleTeacherLessonAttendRateRank(RequestVo vo){
		Map<String, List<LessonAttendInfo>> lessonAttendRateRankMap = new HashMap<>();
		List<LessonAttendInfo> topFiveList = null;
		List<LessonAttendInfo> lastFiveList = null;
		
		commService.setCommPropToRequestVo(vo);
		
		List<LessonAttendInfo> lessonAttendInfoList = lessonAttendInfoService.findAttendLessonRateRank(vo).stream().sorted((a, b)->{
			BigDecimal x = new BigDecimal(a.getAttendLessonRate());
			BigDecimal y = new BigDecimal(b.getAttendLessonRate());
			return y.compareTo(x);
		}).collect(Collectors.toList());
		
		lessonAttendInfoList.stream().forEach(v->{
			v.setAttendLessonRate(NumberUtils.formatScore(Double.valueOf(v.getAttendLessonRate())) + "%");
		});
		
		int size = lessonAttendInfoList.size();
		
		LessonAttendInfo lessonAttendInfo = new LessonAttendInfo();
		lessonAttendInfo.setTeacherName(CommProp.DEFAULT_RANK_TEACHER_NAME);
		lessonAttendInfo.setAttendLessonRate(CommProp.DEFAULT_RANK_DATA);
		
		topFiveList = lessonAttendInfoList.stream().limit(5).collect(Collectors.toList());
		int topFiveListSize = topFiveList.size();
		for(int i = 0; i < 5 - topFiveListSize; i++){
			topFiveList.add(lessonAttendInfo);
		}
		
		if(size <= 5){
			lastFiveList = new ArrayList<>();
		}else if(size > 5 && size <= 10){
		    lastFiveList = lessonAttendInfoList.stream().skip(5).collect(Collectors.toList());	
		}else{
			lastFiveList = lessonAttendInfoList.stream().skip(size - 5).collect(Collectors.toList());
		}
		
		int lastFiveListSize = lastFiveList.size();
		for(int i = 0; i < 5 - lastFiveListSize; i++){
			lastFiveList.add(lessonAttendInfo);
		}
		
		lessonAttendRateRankMap.put("topFive", topFiveList);
		lessonAttendRateRankMap.put("lastFive", lastFiveList);
		
		return lessonAttendRateRankMap;
	}
	
	/**
	 * 加载统计信息页面所有数据
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("findAllLessonAttendRateStatInfo")
	@ResponseBody
	public JsonResult findAllLessonAttendRateStatInfo(RequestVo vo){
		JsonResult json = new JsonResult();
		json.addDatas("statInfo", handleLessonAttendRateStat(vo));
		
		json.addDatas("trendList", handleLessonAttendRateByTrendType(vo));
		json.addDatas("compList", handleLessonAttendRateByCompType(vo));
		Map<String, List<LessonAttendInfo>> lessonAttendRateRankMap = handleTeacherLessonAttendRateRank(vo);
		json.addDatas("topFiveList", lessonAttendRateRankMap.get("topFive"));
		json.addDatas("lastFiveList", lessonAttendRateRankMap.get("lastFive"));
		
		return json;
	}
	
	/**
	 * 处理上课率明细数据
	 * @param vo
	 * @return
	 */
	public List<LessonAttendInfo> handleLessonAttendRateDtl(RequestVo vo, Page page){
		commService.setCommPropToRequestVo(vo);
		if(StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())){
			vo.setOrderAttr("attendLessonRate");
			vo.setOrderType("desc");
		}
		List<LessonAttendInfo> lessonAttendRateDtlList = null;
		if(null == page){
			lessonAttendRateDtlList = lessonAttendInfoService.findAttendLessonRateDtl(vo);
		}else{
			lessonAttendRateDtlList = lessonAttendInfoService.findAttendLessonRateDtl(vo, page);
		}
		
		lessonAttendRateDtlList.stream().forEach(v->{
			v.setAttendLessonRate(NumberUtils.formatScore(Double.valueOf(v.getAttendLessonRate())));
		});
		
		return lessonAttendRateDtlList;
	}
	
	/**
	 * 查询老师上课率详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findLessonAttendRateDtl")
	@ResponseBody
	public JsonResult findLessonAttendRateDtl(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		List<LessonAttendInfo> lessonAttendRateDtlList = handleLessonAttendRateDtl(vo, page);
		
		int index = 0;
		for(LessonAttendInfo la : lessonAttendRateDtlList){
			++index;
			la.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(lessonAttendRateDtlList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 排序老师上课率详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findLessonAttendRateDtlOrder")
	@ResponseBody
	public JsonResult findLessonAttendRateDtlOrder(RequestVo vo){
		JsonResult json = new JsonResult();
		List<LessonAttendInfo> lessonAttendRateDtlList = handleLessonAttendRateDtl(vo, null);
		json.addDatas("lessonAttendRateDtlList", commService.handleOrderData(vo, lessonAttendRateDtlList, LessonAttendInfo.class));
		return json;
	}
	
	@RequestMapping("exportLessonAttendDtlData")
	public void exportLessonDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "上课率" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "应上课", "实上课", "上课率"};
		String[] fieldNames = {"teacherName","subjectName", "totalLesson", "attendLesson", "attendLessonRate"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		List<LessonAttendInfo> excelList = handleLessonAttendRateDtl(vo, null);
		
		new ExportExcelForTable<LessonAttendInfo>().exportExcel(title, headers, fieldNames, excelList,
				response.getOutputStream(),null);
		
	}
}
