package cn.strong.leke.diag.controller.teachingMonitor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import cn.strong.leke.diag.model.teachingMonitor.CorrectHW;
import cn.strong.leke.diag.model.teachingMonitor.LessonAttendInfo;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikeInfo;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ResolveDoubt;
import cn.strong.leke.diag.model.teachingMonitor.StatisticalSummary;
import cn.strong.leke.diag.model.teachingMonitor.attendance.AttendanceProBean;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.EvaluateDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.interact.InteractDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.resource.ResourceDetailBean;
import cn.strong.leke.diag.service.teachingMonitor.AttendanceXService;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.CorrectHWService;
import cn.strong.leke.diag.service.teachingMonitor.EvaluateXService;
import cn.strong.leke.diag.service.teachingMonitor.InteractXService;
import cn.strong.leke.diag.service.teachingMonitor.LessonAttendInfoService;
import cn.strong.leke.diag.service.teachingMonitor.LessonBeikeInfoService;
import cn.strong.leke.diag.service.teachingMonitor.ResolveDoubtService;
import cn.strong.leke.diag.service.teachingMonitor.ResourceXService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.base.SchoolStage;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.tutor.base.IGradeRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISubjectRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

@Controller
@RequestMapping("/auth/provost/teachingMonitor/statsum/")
public class StatisticalSummaryController {
	
	protected static final Logger logger = LoggerFactory.getLogger(StatisticalSummaryController.class);
	
	@Resource
	private CommService commService;
	
	@Resource
	private LessonBeikeInfoService lessonBeikeInfoService;
	
	@Resource
	private LessonAttendInfoService lessonAttendInfoService;
	
	@Resource
	private ResolveDoubtService resolveDoubtService;
	
	@Resource
	private CorrectHWService correctHWService;
	
	@Resource
	private AttendanceXService attendanceXService;
	
	@Resource
	private EvaluateXService evaluateXService;
	
	@Resource
	private InteractXService interactXService;
	
	@Resource
	private ResourceXService resourceXService;
	
	@Resource
	private IGradeRemoteService gradeRemoteService;
	
	@Resource
	private ISubjectRemoteService subjectRemoteService;
	
	@Resource
	private IUserRemoteService userRemoteService;
	
	@RequestMapping("toShowStatSum")
	public String toShowStatSum(Model model){
		List<GradeRemote> gradeList = new ArrayList<>();
		List<SubjectRemote> subjectList = new ArrayList<>();
		
		GradeRemote allGrade = new GradeRemote();
		allGrade.setGradeId(null);
		allGrade.setGradeName("全校");
		allGrade.setIsSelected(true);
		gradeList.add(allGrade);
		
		SubjectRemote allSubject = new SubjectRemote();
		allSubject.setSubjectId(null);
		allSubject.setSubjectName("全部学科");
		allSubject.setIsSelected(true);
		subjectList.add(allSubject);
		
		gradeList.addAll(commService.findGradeOfSchool());
		subjectList.addAll(commService.findSubjectOfSchool());
		
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("subjectList", subjectList);
		return "/auth/teachingMonitor/statSum";
	}
	
	@RequestMapping("toShowStatSumDtl")
	public String toShowStatSumDtl(){
		return "/auth/teachingMonitor/statSumDtl";
	}
	
	/**
	 * 处理统计汇总数据
	 * @param vo
	 * @return
	 */
	public List<StatisticalSummary> handleStatisticalSummary(RequestVo vo, Page page){
		commService.setCommPropToRequestVo(vo);
		
		String orderAttr = null;
		String orderType = null;
		if(StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())){
			orderAttr = "preparedLessonRate";
			orderType = "desc";
		}else{
			orderAttr = vo.getOrderAttr();
			orderType = vo.getOrderType();
		}
		
		vo.setOrderAttr(null);
		vo.setOrderType("desc");
		
		//查询当前老师及对应学科信息，并转换为统计汇总对象
		List<StatisticalSummary> statSumList = new ArrayList<>();
		statSumList.addAll(commService.findTeacherSubject(vo).stream().map(v->{
													StatisticalSummary statSum = new StatisticalSummary();
													statSum.setTeacherId(v.getTeacherId());
													statSum.setTeacherName(v.getTeacherName());
													statSum.setSubjectId(v.getSubjectId());
													statSum.setSubjectName(v.getSubjectName());
													statSum.setStartDate(vo.getStartDate());
													statSum.setEndDate(vo.getEndDate());
													statSum.setGradeId(vo.getGradeId());
													String photo = userRemoteService.findUserDetail(vo.getTeacherId()).getPhoto();
													statSum.setImgSrc(null == photo? "https://static.leke.cn/images/home/photo.png" : photo);
													return statSum;
												}).collect(Collectors.toList()));
		
		//查询业务数据
		vo.setOrderAttr(null);
		List<AttendanceProBean> attendanceProList = attendanceXService.queryAttendanceProAndDetail(vo);
		attendanceProList.stream().forEach(v->{
			v.setAllOnPro(Double.valueOf(NumberUtils.formatScore(Double.valueOf(v.getAllOnPro()))));
		});
		
		vo.setOrderAttr(null);
		List<EvaluateDetailBean> evaluateDetailList = evaluateXService.queryEvaluateAndDetail(vo);
		evaluateDetailList.stream().forEach(v->{
			v.setGoodPro(Double.valueOf(NumberUtils.formatScore(Double.valueOf(v.getGoodPro()))));
		});
		
		vo.setOrderAttr(null);
		List<InteractDetailBean> interactDetailList = interactXService.queryInteractAndDetail(vo);
		
		vo.setOrderAttr(null);
		List<ResourceDetailBean> resourceDetailList = resourceXService.queryResourceAndDetail(vo);
		
		vo.setOrderAttr(null);
		List<BeikeRate> beikeRateList = lessonBeikeInfoService.handleBeikeRateStatOfTeacher(vo, null);
		
		vo.setOrderAttr(null);
		List<LessonAttendInfo> lessonAttendList  = lessonAttendInfoService.findAttendLessonRateDtl(vo);
		lessonAttendList.stream().forEach(v->{
			v.setAttendLessonRate(NumberUtils.formatScore(Double.valueOf(v.getAttendLessonRate())));
		});
		
		vo.setOrderAttr(null);
		List<ResolveDoubt> resolveDoubtList = resolveDoubtService.findResolveDoubtDtl(vo);
		
		vo.setOrderAttr(null);
		List<CorrectHW> correctHWList = correctHWService.findCorrectHWDtl(vo);
		
		//循环统计汇总集合，根据老师ID、学科ID过滤业务数据
		statSumList.stream().forEach(v->{
			List<AttendanceProBean> attendanceList = attendanceProList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setAttendancePro(attendanceList.isEmpty()? new AttendanceProBean() : attendanceList.get(0));
			
			List<EvaluateDetailBean> evaluateList = evaluateDetailList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setEvaluateDetail(evaluateList.isEmpty()? new EvaluateDetailBean() : evaluateList.get(0));
			
			List<InteractDetailBean> interactList = interactDetailList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setInteractDetail(interactList.isEmpty()? new InteractDetailBean() : interactList.get(0));
			
			List<ResourceDetailBean> resourceList = resourceDetailList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setResourceDetail(resourceList.isEmpty()? new ResourceDetailBean() : resourceList.get(0));
			
			List<BeikeRate> beikeList = beikeRateList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setBeikeRate(beikeList.isEmpty()? new BeikeRate() : beikeList.get(0));
			
			List<LessonAttendInfo> lessonList = lessonAttendList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setLessonAttendInfo(lessonList.isEmpty()? new LessonAttendInfo() : lessonList.get(0));
			
			List<ResolveDoubt> resolveList = resolveDoubtList.stream().filter(a->{
				if(null != a.getSubjectId() && a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setResolveDoubt(resolveList.isEmpty()? new ResolveDoubt() : resolveList.get(0));
			
			List<CorrectHW> correctList = correctHWList.stream().filter(a->{
				if(a.getSubjectId().equals(v.getSubjectId()) && a.getTeacherId().equals(v.getTeacherId())){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			v.setCorrectHW(correctList.isEmpty()? new CorrectHW() : correctList.get(0));
			
		});
		
		vo.setOrderAttr(orderAttr);
		vo.setOrderType(orderType);
		
		if(null == page){
			statSumList = handleStatSumOrderData(statSumList, vo);
		}else{
			page.setTotalSize(statSumList.size());
			statSumList = handleStatSumOrderData(statSumList, vo).stream().skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());
		}
		
		
		return statSumList;
	}
	
	public List<StatisticalSummary> handleStatSumOrderData(List<StatisticalSummary> statSumList, RequestVo vo){
		List<StatisticalSummary> resultList = null;
		resultList = statSumList.stream().sorted((a, b)->{
			BigDecimal x = null;
			BigDecimal y = null;
			if("preparedLessonRate".equalsIgnoreCase(vo.getOrderAttr())){
				String prepared_a = a.getBeikeRate().getPreparedLessonRate();
				String prepared_b = b.getBeikeRate().getPreparedLessonRate();
				x = new BigDecimal(prepared_a == null? "0" : prepared_a);
				y = new BigDecimal(prepared_b == null? "0" : prepared_b);
			}else if("attendLessonRate".equalsIgnoreCase(vo.getOrderAttr())){
				String attend_a = a.getLessonAttendInfo().getAttendLessonRate();
				String attend_b = b.getLessonAttendInfo().getAttendLessonRate();
				x= new BigDecimal(attend_a == null? "0" : attend_a);
				y= new BigDecimal(attend_b == null? "0" : attend_b);
			}else if("assignNum".equalsIgnoreCase(vo.getOrderAttr())){
				Long assign_a = a.getCorrectHW().getAssignNum();
				Long assign_b = b.getCorrectHW().getAssignNum();
				x= new BigDecimal(assign_a == null? 0L : assign_a);
				y= new BigDecimal(assign_b == null? 0L : assign_b);
			}else if("teacherCorrectNum".equalsIgnoreCase(vo.getOrderAttr())){
				Long correct_a = a.getCorrectHW().getTeacherCorrectNum();
				Long correct_b = b.getCorrectHW().getTeacherCorrectNum();
				x= new BigDecimal(correct_a == null? 0L : correct_a);
				y= new BigDecimal(correct_b == null? 0L : correct_b);
			}else if("resolveRate".equalsIgnoreCase(vo.getOrderAttr())){
				String resolve_a = a.getResolveDoubt().getResolveRate();
				String resolve_b = b.getResolveDoubt().getResolveRate();
				x= new BigDecimal(resolve_a == null ? "0" : resolve_a);
				y= new BigDecimal(resolve_b == null ? "0" : resolve_b);
			}else if("createCount".equalsIgnoreCase(vo.getOrderAttr())){
				Integer create_a = a.getResourceDetail().getCreateCount();
				Integer create_b = b.getResourceDetail().getCreateCount();
				x= new BigDecimal(create_a == null? 0 : create_a);
				y= new BigDecimal(create_b == null? 0 : create_b);
			}else if("allOnPro".equalsIgnoreCase(vo.getOrderAttr())){
				x= new BigDecimal(a.getAttendancePro().getAllOnPro());
				y= new BigDecimal(b.getAttendancePro().getAllOnPro());
			}else if("avgTotalCount".equalsIgnoreCase(vo.getOrderAttr())){
				x= a.getInteractDetail().getAvgTotalCount() == null? new BigDecimal(0) : a.getInteractDetail().getAvgTotalCount();
				y= b.getInteractDetail().getAvgTotalCount() == null? new BigDecimal(0) : b.getInteractDetail().getAvgTotalCount();
			}else if("totalLevel".equalsIgnoreCase(vo.getOrderAttr())){
				x= a.getEvaluateDetail().getTotalLevel() == null? new BigDecimal(0) : a.getEvaluateDetail().getTotalLevel();
				y= b.getEvaluateDetail().getTotalLevel() == null? new BigDecimal(0) : b.getEvaluateDetail().getTotalLevel();
			}
			
			if("asc".equalsIgnoreCase(vo.getOrderType())){
				return x.compareTo(y);
			}else{
				return y.compareTo(x);
			}
		}).collect(Collectors.toList());
		return resultList;
	}
	
	/**
	 * 查询汇总统计数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findStatSum")
	@ResponseBody
	public JsonResult findStatSum(RequestVo vo, Page page){
		JsonResult json = new JsonResult();
		List<StatisticalSummary> statSumList = handleStatisticalSummary(vo, page);
		
		int index = 0;
		for(StatisticalSummary statSum : statSumList){
			++index;
			statSum.setIndex((page.getCurPage() - 1) * page.getPageSize() + index);
		}
		
		page.setDataList(statSumList);
		CacheUtils.set("statSumList", statSumList);
		json.addDatas("page", page);
		return json;
	}
	
	/**
	 * 查询汇总统计详情数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("findStatSumDtl")
	public String findStatSumDtl(RequestVo vo, Model model){
		List<StatisticalSummary> statSumList = handleStatisticalSummary(vo, null);
		
		statSumList = statSumList.stream().filter(v->{
			if(v.getTeacherId().equals(vo.getTeacherId()) && v.getSubjectId().equals(vo.getSubjectId())){
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		
		model.addAttribute("statSum", statSumList.isEmpty()? new StatisticalSummary() : statSumList.get(0));
		return "/auth/teachingMonitor/statSumDtl";
	}
	
	@RequestMapping("exportStatSumData")
	public void exportStatSumData(RequestVo vo, String exportType, HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<StatisticalSummary> excelList = handleStatisticalSummary(vo, null);
		String title = null;
		String[] headers = null;
		String[] fieldNames = null;
		String[] titleArr = commService.handleExcelTitle(vo);
		if("base".equalsIgnoreCase(exportType)){
			
			title = titleArr[0] + "老师教学基础数据" + titleArr[1];
			headers = new String[]{"姓名", "学科", "上课率", "实上", "应上", 
					"备课率", "实备", "应备", "布置作业", "纯客观", "含主观",
					"老师批改人份", "老师批改人份占比", "解答率", "已解答", "待解答", 
					"创建资源", "分享资源", "全勤率", "课堂互动次数", "评价得分", "好评率"};
			fieldNames = new String[]{"teacherName","subjectName", "lessonAttendInfo.attendLessonRate", "lessonAttendInfo.attendLesson", "lessonAttendInfo.totalLesson", 
					"beikeRate.preparedLessonRate", "beikeRate.preparedLesson", "beikeRate.actualLesson", "correctHW.assignNum", "correctHW.objectiveNum", "correctHW.subjectiveNum",
					"correctHW.teacherCorrectNum", "correctHW.teacherCorrectNumRate", "resolveDoubt.resolveRate", "resolveDoubt.resolveDoubt", "resolveDoubt.notResolveDoubt",
					"resourceDetail.createCount", "resourceDetail.shareCount", "attendancePro.allOnPro", "interactDetail.avgTotalCount", "evaluateDetail.totalLevel", "evaluateDetail.goodPro"};
		}else{
			title = titleArr[0] + "老师教学全部数据" + titleArr[1];
			headers = new String[]{"姓名", "学科", "上课率", "实上", "应上", 
					"备课率", "实备", "应备", "提前备课率", "提前备课数", "临时备课率", "临时备课数", "备课件率", "备课件数", "备作业率", "备作业数", "备微课率", "备微课数", "备教案率", "备教案数",
					"布置作业", "纯客观", "含主观", "系统批改人份" ,"系统批改人份占比", "老师批改人份", "老师批改人份占比", "学生互批人份", "学生互批人份占比", "未批改人份", "未批改人份占比",
					"解答率", "已解答", "待解答", "全勤", "迟到", "早退", "迟到且早退", "缺勤",
					"创建资源", "分享资源", "创建课件", "分享课件", "创建微课", "分享微课", "创建试卷", "分享试卷", "创建习题", "分享习题", "创建备课包", "分享备课包",
					"课堂互动次数", "点名", "快速问答", "随堂作业", "分组讨论", "授权", 
					"评价得分", "好评数", "好评率", "中评数", "中评率", "差评数", "差评率", "鲜花数"};
			fieldNames = new String[]{"teacherName","subjectName", "lessonAttendInfo.attendLessonRate", "lessonAttendInfo.attendLesson", "lessonAttendInfo.totalLesson", 
					"beikeRate.preparedLessonRate", "beikeRate.preparedLesson", "beikeRate.actualLesson", "beikeRate.earlyPreparedLessonRate", "beikeRate.earlyPreparedLesson", "beikeRate.tempPreparedLessonRate", "beikeRate.tempPreparedLesson", "beikeRate.cwLessonRate", "beikeRate.cwLesson", "beikeRate.hwLessonRate", "beikeRate.hwLesson", "beikeRate.mcLessonRate", "beikeRate.mcLesson", "beikeRate.teachPlanLessonRate", "beikeRate.teachPlanLesson",
					"correctHW.assignNum", "correctHW.objectiveNum", "correctHW.subjectiveNum", "correctHW.autoCorrectNum", "correctHW.autoCorrectNumRate", "correctHW.teacherCorrectNum", "correctHW.teacherCorrectNumRate", "correctHW.studentCorrectNum", "correctHW.studentCorrectNumRate", "correctHW.notCorrectNum", "correctHW.notCorrectNumRate",
					"resolveDoubt.resolveRate", "resolveDoubt.resolveDoubt", "resolveDoubt.notResolveDoubt", "attendancePro.allOn", "attendancePro.late", "attendancePro.early", "attendancePro.lateAndEarly", "attendancePro.notClassNum",
					"resourceDetail.createCount", "resourceDetail.shareCount", "resourceDetail.createCoursewareCount", "resourceDetail.shareCoursewareCount", "resourceDetail.createMicrocourseCount", "resourceDetail.shareMicrocourseCount", "resourceDetail.createPaperCount", "resourceDetail.sharePaperCount", "resourceDetail.createQuestionCount", "resourceDetail.shareQuestionCount", "resourceDetail.createBeikePkgCount", "resourceDetail.shareBeikePkgCount", 
					"interactDetail.avgTotalCount", "interactDetail.callNum", "interactDetail.quickNum", "interactDetail.examNum", "interactDetail.discuNum", "interactDetail.authedNum", 
					"evaluateDetail.totalLevel", "evaluateDetail.good", "evaluateDetail.goodPro", "evaluateDetail.center", "evaluateDetail.centerPro", "evaluateDetail.poor", "evaluateDetail.poorPro", "evaluateDetail.flowerNum"};
		}
		
		exportExcel(request, response, headers, fieldNames, title, excelList);
	}
	
	
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String[] headers, String[] fieldNames, String title, List<StatisticalSummary> dataList){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
		
		//生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		//设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		//生成并设置另一个样式
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//生成另一个字体
		HSSFFont font2 = wb.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		//把字体应用到当前的样式
		style2.setFont(font2);
		
		String[] fieldArr = null;
		String secMethodName = null;
		String fstMethodName = null;
		Class fieldClass = null;
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		StatisticalSummary stat = null;
		for(int i = 0; i < dataList.size(); i++){
			if(null == dataList.get(i)){
				continue;
			}
			stat = dataList.get(i);
			row = sheet.createRow(i+1);
			for (int k = 0; k < fieldNames.length; k++) {
				HSSFCell cell = row.createCell(k);
				cell.setCellStyle(style2);
				fieldArr = fieldNames[k].split("\\.");
				if(fieldArr.length == 2){
					fstMethodName = "get" + fieldArr[0].substring(0, 1).toUpperCase() + fieldArr[0].substring(1);
					secMethodName = "get" + fieldArr[1].substring(0, 1).toUpperCase() + fieldArr[1].substring(1);
					try {
						fieldClass = stat.getClass().getDeclaredField(fieldArr[0]).getType();
						Object obj = stat.getClass().getMethod(fstMethodName).invoke(stat);
						Object secObj = fieldClass.getMethod(secMethodName).invoke(obj);
						if(null == secObj){
							if("interactDetail".equalsIgnoreCase(fieldArr[0]) || "evaluateDetail".equalsIgnoreCase(fieldArr[0])){
								cell.setCellValue("-");
							}else{
								cell.setCellValue("0");
							}
						}else{
							cell.setCellValue(NumberUtils.formatScore(Double.valueOf(fieldClass.getMethod(secMethodName).invoke(obj).toString())));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					if("teacherName".equals(fieldNames[k])){
						cell.setCellValue(stat.getTeacherName());
					}else{
						cell.setCellValue(stat.getSubjectName());
					}
				}
			}
		}
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
