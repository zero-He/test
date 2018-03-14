package cn.strong.leke.diag.controller.teachingMonitor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.BeikeRate;
import cn.strong.leke.diag.model.teachingMonitor.ClazzBean;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikeInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.SubjectBean;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.LessonBeikeInfoService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 教学监控-备课
 * @author xujinsong
 *
 */
@Controller
@RequestMapping("/auth/provost/teachingMonitor/beike/")
public class LessonBeikeController {

	protected static final Logger logger = LoggerFactory.getLogger(LessonBeikeController.class);

	@Resource
	private LessonBeikeInfoService lessonBeikeInfoService;
	
	@Resource
	private CommService commService;
	
	@Resource
	private IUserRemoteService userRemoteService;
	
	
	
	/**
	 * 查询备课统计信息
	 * @param vo 请求参数
	 */
	public BeikeRate handleBeikeStat(RequestVo vo) {
		commService.setCommPropToRequestVo(vo);
		//查询课堂备课明细信息
		List<LessonBeikeInfo> beikeInfoList = lessonBeikeInfoService.findLessonBeikeInfoByGradeSubject(vo);
		//计算相关维度备课统计信息
		BeikeRate beikeRate = new BeikeRate();
		lessonBeikeInfoService.handleBeikeRate(beikeRate, beikeInfoList);
		return beikeRate;
	}
	
	
	/**
	 * 计算备课率趋势
	 * @param vo
	 * @return
	 */
	public List<BeikeRate> handleBeikeRateByTrendType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		List<BeikeRate> beikeRateList = null; 
		List<CommProp> commPropList = null;
		
		if(RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())){
			commPropList = commService.findBeikeRateByWeek(vo);
		}else if(RequestVo.MONTH.equalsIgnoreCase(vo.getTrendType())){
			commPropList = commService.findBeikeRateByMonth(vo);
		}else{
			commPropList = commService.findBeikeRateByDay(vo);
		}
		
		//将备课率map对象转换为BeikeRate对象，并设置相关属性值
		beikeRateList = commPropList.stream().map(v->{
			BeikeRate beikeRate = new BeikeRate();
			beikeRate.setStartDate(v.getStartDate());
			beikeRate.setEndDate(v.getEndDate());
			beikeRate.setDateStr(v.getDateStr());
			return beikeRate;
		}).collect(Collectors.toList());
		
		//循环计算每个时间段的备课率
		RequestVo requestVo = (RequestVo)BeanUtils.cloneBean(vo); 
		beikeRateList.stream().forEach(v->{
			requestVo.setStartDate(v.getStartDate());
			requestVo.setEndDate(v.getEndDate());
			lessonBeikeInfoService.handleBeikeRate(v, lessonBeikeInfoService.findLessonBeikeInfoByGradeSubject(requestVo));
		});
		
		return beikeRateList;
	}
	
	/**
	 * 查询备课率趋势
	 * @param vo
	 * @return
	 */
	@RequestMapping("findBeikeRateByTrendType")
	@ResponseBody
	public JsonResult findBeikeRateByTrendType(RequestVo vo){
		JsonResult json = new JsonResult();
		json.addDatas("beikeRateTrendList", handleBeikeRateByTrendType(vo));
		return json;
	}
	
	public BeikeRate buildBeikeRate(String startDate, String endDate, Long gradeId, String gradeName, Long classId, String className,
			Long subjectId, String subjectName){
		BeikeRate beikeRate = new BeikeRate();
		beikeRate.setStartDate(startDate);
		beikeRate.setEndDate(endDate);
		beikeRate.setGradeId(gradeId);
		beikeRate.setGradeName(gradeName);
		beikeRate.setClassId(classId);
		beikeRate.setClassName(className);
		beikeRate.setSubjectId(subjectId);
		beikeRate.setSubjectName(subjectName);
		return beikeRate;
	}
	
	/**
	 * 根据对比类型计算备课率
	 * @param vo
	 * @return
	 */
	public List<BeikeRate> handleBeikeRateByCompType(RequestVo vo){
		commService.setCommPropToRequestVo(vo);
		
		List<BeikeRate> beikeRateList = null; 
		List<GradeRemote> gradeList = null;
		List<SubjectRemote> subjectList = null;
		List<ClazzBean> clazzList = null;
		List<LessonBeikeInfo> beikeInfoList = lessonBeikeInfoService.findLessonBeikeInfoByGradeSubject(vo);
		
		if(RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType()) || RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())){//全校按学科
			
			if(RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType())){
				//查询全校的学科
				subjectList = commService.findSubjectOfSchool();
			}else{
				//查询全校指定年级的学科
				subjectList = commService.findSubjectOfGradeBySchool(vo);
			}
			
			//如果请求参数指定某个学科，则返回该学科
			if(null != vo.getSubjectId() && vo.getSubjectId() != -1){
				subjectList = subjectList.stream().filter(v->v.getSubjectId().equals(vo.getSubjectId())).collect(Collectors.toList());
			}
			
			//将学科对象List转换为备课率对象List
			beikeRateList = subjectList.stream().map(v->{
				return buildBeikeRate(vo.getStartDate(), vo.getEndDate(), null, null, null, null, v.getSubjectId(), v.getSubjectName());
			}).collect(Collectors.toList());
			
			//循环学科计算学科在所选时间段内的备课率
			beikeRateList.stream().forEach(v->{
				lessonBeikeInfoService.handleBeikeRate(v, beikeInfoList.stream().filter(b->b.getSubjectId().equals(v.getSubjectId())).collect(Collectors.toList()));
			});
			
		}else if(RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())){//年级按班级
			//查询全校指定年级的班级
			clazzList = beikeInfoList.stream().map(v->{
				ClazzBean c = new ClazzBean();
				c.setClassId(v.getClassId());
				c.setClassName(v.getClassName());
				return c;
			}).distinct().collect(Collectors.toList());
			
			//将班级对象List转换为备课率对象List
			beikeRateList = clazzList.stream().map(v->{
				return buildBeikeRate(vo.getStartDate(), vo.getEndDate(), null, null, v.getClassId(), v.getClassName(), null, null);
			}).collect(Collectors.toList());
			
			//循环班级计算班级在所选时间段内的备课率
			beikeRateList.stream().forEach(v->{
				lessonBeikeInfoService.handleBeikeRate(v, beikeInfoList.stream().filter(b->b.getClassId().equals(v.getClassId())).collect(Collectors.toList()));
			});
			
			beikeRateList = beikeRateList.stream().sorted(new CommService.CnComparator<BeikeRate>(true, true, "className", BeikeRate.class)).collect(Collectors.toList());
			
		}else{//全校按年级，默认
			
			//查询全校的年级
			gradeList = commService.findGradeOfSchool();
			
			//将年级对象List转换为备课率对象List
			beikeRateList = gradeList.stream().map(v->{
				return buildBeikeRate(vo.getStartDate(), vo.getEndDate(), v.getGradeId(), v.getGradeName(), null, null, null, null);
			}).collect(Collectors.toList());
			
			////循环年级计算年级在所选时间段内的备课率
			beikeRateList.stream().forEach(v->{
				lessonBeikeInfoService.handleBeikeRate(v, beikeInfoList.stream().filter(b->b.getGradeId().equals(v.getGradeId())).collect(Collectors.toList()));
			});
		}
		
		return beikeRateList;
	}
	
	/**
	 * 根据对比类型查询备课率
	 * @param vo
	 * @return
	 */
	@RequestMapping("findBeikeRateByCompType")
	@ResponseBody
	public JsonResult findBeikeRateByCompType(RequestVo vo){
		JsonResult json = new JsonResult();
		json.addDatas("beikeRateCompList", handleBeikeRateByCompType(vo));
		return json;
	}
	
	/**
	 * 老师备课率排行
	 * @param vo
	 * @return
	 */
	public Map<String, List<BeikeRate>> handleTeacherBeikeRateRank(RequestVo vo){
		Map<String, List<BeikeRate>> beikeRateRankMap = new HashMap<>();
		List<BeikeRate> topFiveList = null;
		List<BeikeRate> lastFiveList = null;
		
		commService.setCommPropToRequestVo(vo);
		List<BeikeRate> beikeRateInfoList = lessonBeikeInfoService.findTeacherBeikeRateRank(vo).stream().sorted((a, b)->{
			BigDecimal x = new BigDecimal(a.getPreparedLessonRate());
			BigDecimal y = new BigDecimal(b.getPreparedLessonRate());
			return y.compareTo(x);
		}).collect(Collectors.toList());
		
		beikeRateInfoList.stream().forEach(v->{
			v.setPreparedLessonRate(NumberUtils.formatScore(Double.valueOf(v.getPreparedLessonRate())) + "%");
		});
		int size = beikeRateInfoList.size();
		
		BeikeRate beikeRate = new BeikeRate();
		beikeRate.setTeacherName(CommProp.DEFAULT_RANK_TEACHER_NAME);
		beikeRate.setPreparedLessonRate(CommProp.DEFAULT_RANK_DATA);
		
		topFiveList = beikeRateInfoList.stream().limit(5).collect(Collectors.toList());
		int topFiveListSize = topFiveList.size();
		for(int i = 0; i < 5 - topFiveListSize; i++){
			topFiveList.add(beikeRate);
		}
		
		if(size <= 5){
			lastFiveList = new ArrayList<>();
		}else if(size > 5 && size <= 10){
		    lastFiveList = beikeRateInfoList.stream().skip(5).collect(Collectors.toList());	
		}else{
			lastFiveList = beikeRateInfoList.stream().skip(size - 5).collect(Collectors.toList());
		}
		
		int lastFiveListSize = lastFiveList.size();
		for(int i = 0; i < 5 - lastFiveListSize; i++){
			lastFiveList.add(beikeRate);
		}
		
		beikeRateRankMap.put("topFive", topFiveList);
		beikeRateRankMap.put("lastFive", lastFiveList);
		
		return beikeRateRankMap;
	}
	
	@RequestMapping("findAllBeikeRateStatInfo")
	@ResponseBody
	public JsonResult findAllBeikeRateStatInfo(RequestVo vo){
		JsonResult json = new JsonResult();
		json.addDatas("statInfo", handleBeikeStat(vo));
		
		json.addDatas("trendList", handleBeikeRateByTrendType(vo));
		json.addDatas("compList", handleBeikeRateByCompType(vo));
		Map<String, List<BeikeRate>> beikeRateRankMap = handleTeacherBeikeRateRank(vo);
		json.addDatas("topFiveList", beikeRateRankMap.get("topFive"));
		json.addDatas("lastFiveList", beikeRateRankMap.get("lastFive"));
		
		return json;
	}
	
	/**
	 * 处理老师上课详情数据
	 * @param vo
	 * @return
	 */
	public List<LessonBeikeInfo> handleLessonDtlOfTeacher(RequestVo vo, Page page){
		commService.setCommPropToRequestVo(vo);
		if(StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())){
			vo.setOrderAttr("lessonTime");
			vo.setOrderType("desc");
		}
		
		List<LessonBeikeInfo> teacherLessonDtlList = null;
		if(null == page){
			teacherLessonDtlList = lessonBeikeInfoService.findLessonDtlOfTeacher(vo);
		}else{
			teacherLessonDtlList = lessonBeikeInfoService.findLessonDtlOfTeacher(vo, page);
		}
		//判断课堂是否有教案
		teacherLessonDtlList.stream().forEach(v->{
			if(lessonBeikeInfoService.hasLessonPlanForLesson(v.getCourseSingleId())){
				v.setTeachPlan(1);
			}else{
				v.setTeachPlan(0);
			}
			if(StringUtils.hasText(v.getRatio())){
				v.setRatio(NumberUtils.formatScore(Double.valueOf(v.getRatio())));
			}else{
				v.setRatio("0");
			}
		});
		
		return teacherLessonDtlList;
	}
	
	/**
	 * 查询老师上课详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findLessonDtlOfTeacher")
	@ResponseBody
	public JsonResult findLessonDtlOfTeacher(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		List<LessonBeikeInfo> teacherLessonDtlList = handleLessonDtlOfTeacher(vo, page);
		
		int index = 0;
		for(LessonBeikeInfo bk : teacherLessonDtlList){
			++index;
			bk.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(teacherLessonDtlList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 排序老师上课详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findLessonDtlOfTeacherOrder")
	@ResponseBody
	public JsonResult findLessonDtlOfTeacherOrder(RequestVo vo){
		JsonResult json = new JsonResult();
		List<LessonBeikeInfo> teacherLessonDtlList = handleLessonDtlOfTeacher(vo, null);
		json.addDatas("teacherLessonDtlList", commService.handleOrderData(vo, teacherLessonDtlList, LessonBeikeInfo.class));
		return json;
	}
	
	
	/**
	 * 查询老师备课率统计详细信息
	 * @param vo
	 * @return
	 */
	@RequestMapping("findBeikeRateStatOfTeacher")
	@ResponseBody
	public JsonResult findBeikeRateStatOfTeacher(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		List<BeikeRate> beikeRateList = lessonBeikeInfoService.handleBeikeRateStatOfTeacher(vo, page);
		
		int index = 0;
		for(BeikeRate bk : beikeRateList){
			++index;
			bk.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(beikeRateList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 排序老师备课率统计信息
	 * @param vo
	 * @return
	 */
	@RequestMapping("findBeikeRateStatOfTeacherOrder")
	@ResponseBody
	public JsonResult findBeikeRateStatOfTeacherOrder(RequestVo vo){
		JsonResult json = new JsonResult();
		List<BeikeRate> beikeRateList = lessonBeikeInfoService.handleBeikeRateStatOfTeacher(vo, null);
		json.addDatas("beikeRateDtlList", commService.handleOrderData(vo, beikeRateList, BeikeRate.class));
		return json;
	}
	
	@RequestMapping("toShowBeikeRateStatInfo")
	public String toShowBeikeRateStatInfo(){
		return "/auth/teachingMonitor/beikeRateStat";
	}
	
	@RequestMapping("toShowLessonDtlOfTeacher")
	public String toShowLessonDtlOfTeacher(RequestVo vo, Model model){
		model.addAttribute("startDate", vo.getStartDate());
		model.addAttribute("endDate", vo.getEndDate());
		model.addAttribute("teacherId", vo.getTeacherId());
		model.addAttribute("teacherName", vo.getTeacherName());
		return "/auth/teachingMonitor/beikeLessonDtl";
	}
	
	@RequestMapping("exportBeikeDtlData")
	public void exportBeikeDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "备课率明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "备课率", "实备课数", "应备课数", "提前备课率", "提前备课数", "临时备课率", "临时备课数",
				            "备教案率", "备教案数", "备课件率", "备课件数", "备微课率", "备微课数", "备作业率", "备作业数"};
		String[] fieldNames = {"teacherName","subjectName", "preparedLessonRate", "preparedLesson", "actualLesson", "earlyPreparedLessonRate", "earlyPreparedLesson", "tempPreparedLessonRate", "tempPreparedLesson", 
				               "teachPlanLessonRate", "teachPlanLesson", "cwLessonRate", "cwLesson", "mcLessonRate", "mcLesson", "hwLessonRate", "hwLesson"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		List<BeikeRate> excelList = lessonBeikeInfoService.handleBeikeRateStatOfTeacher(vo, null);
		
		new ExportExcelForTable<BeikeRate>().exportExcel(title, headers, fieldNames, excelList,
				response.getOutputStream(),null);
		
	}
	
	@RequestMapping("exportLessonDtlData")
	public void exportLessonDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "上课详情" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"课堂名称", "上课时间", "授课班级", "教案数", "微课数", "课件数", "作业数", "到课率", "实到人数", "应到人数", "未上课人数"};
		String[] fieldNames = {"courseSingleName","lessonTimeStr", "className", "teachPlan", "mcCount", "cwCount", "hwCount", "ratio", "realCount", "totalCount", "absentCount"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		
		List<LessonBeikeInfo> excelList = handleLessonDtlOfTeacher(vo, null);
		
		new ExportExcelForTable<LessonBeikeInfo>().exportExcel(title, headers, fieldNames, excelList,
				response.getOutputStream(),null);
		
	}
}
