/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionLogController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-24
 */
package cn.strong.leke.question.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.user.ResearcherContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.core.workbook.query.IWorkbookQueryService;
import cn.strong.leke.question.model.DraftQuestionQuery;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.PublishedQuestionQuery;
import cn.strong.leke.question.model.StatisQuestionQuery;
import cn.strong.leke.question.model.SubjectMaterial;
import cn.strong.leke.question.model.VerifyStatisDTO;
import cn.strong.leke.question.model.question.QuestionTotal;
import cn.strong.leke.question.model.question.query.AmountQuestionQuery;
import cn.strong.leke.question.service.IQuestionLogService;
import cn.strong.leke.question.service.QuestionService;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import cn.strong.leke.remote.service.voice.IAlbumCourseRemoteService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    lavender
 * @version   Avatar 
 */
@Controller
@RequestMapping("/auth/*")
public class QuestionStatisController {

	@Resource
	private IQuestionLogService questionLogService;

	@Autowired
	private QuestionService questionService;
	
	@Resource
	private IQuestionQueryService questionQueryService;
	
	@Resource
	private IWorkbookQueryService workbookQueryService;
	
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IAlbumCourseRemoteService albumCourseRemoteService;
	//--------------------------------------admin---------------------------------------------//	
	/**
	 * 查询录入量统计
	 * author：lavender
	 * 2014-4-24下午3:11:54
	 */
	@RequestMapping("admin/questionStatis/input/inputAmountList")
	public void inputAmountList() {
	}
	
	/**
	 * 用户列表 〈功能详细描述〉
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("admin/questionStatis/input/exportUserList")
	@ResponseBody
	public void  userList(Date startDate, Date endDate, String userName, Long roleId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			AmountQuestionQuery query = new AmountQuestionQuery();
			if(null == startDate && null == endDate) {
				Calendar calendar = Calendar.getInstance();
				startDate = calendar.getTime();
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
				endDate = calendar.getTime();
				query.setMinCreatedOn(startDate);
				query.setMaxCreatedOn(endDate);
			} else {
				if (null != startDate) {
					query.setMinCreatedOn(startDate);
				}
				if (null != endDate) {
					query.setMaxCreatedOn(endDate);
				}
			}
			
			if (roleId == null){
				roleId = 0L;
			}
			
			List<QuestionTotal> qtList = questionQueryService.queryTotalQuestionFrom(query, userName, roleId);
			
		String[] headers = { "录入人","单独创建习题量", "	试卷创建习题量","习题册创建习题量","总和"};
		String[] fieldNames = { "userName","total", "totalFromPaper","totalFromWb","totalAll",};
		String pattern = "yyyy-MM-dd HH:mm:ss";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		String fileName = FileUtils.getEncodingFileName("用户列表.xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		qtList = qtList.stream().map(qt -> {
			qt.setTotalAll(qt.getTotal() + qt.getTotalFromPaper() + qt.getTotalFromWb());
			return qt;
		}).collect(Collectors.toList());
		new ExportExcelForTable<QuestionTotal>().exportExcel("用户列表", headers, fieldNames, qtList,
				response.getOutputStream(), pattern);
	}
	
	/**
	 * 查询录入量统计
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:12:20
	 */
	@RequestMapping("admin/questionStatis/ajax/findInputAmountList")
	@ResponseBody
	public JsonResult findInputAmountList(Date startDate, Date endDate, String userName, Long roleId) {
		JsonResult json = new JsonResult();
		AmountQuestionQuery query = new AmountQuestionQuery();
		if(null == startDate && null == endDate) {
			Calendar calendar = Calendar.getInstance();
			startDate = calendar.getTime();
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			endDate = calendar.getTime();
			query.setMinCreatedOn(startDate);
			query.setMaxCreatedOn(endDate);
		} else {
			if (null != startDate) {
				query.setMinCreatedOn(startDate);
			}
			if (null != endDate) {
				query.setMaxCreatedOn(endDate);
			}
		}
		List<QuestionTotal> questionTotals = questionQueryService.queryTotalQuestionFrom(query, userName, roleId);
		json.addDatas("startDate", DateUtils.formatTime(startDate));
		json.addDatas("endDate", DateUtils.formatTime(endDate));
		json.addDatas("questionTotals", questionTotals);
		return json;
	}
	
	/**
	 * 查询录入量统计
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:12:20
	 */
	@RequestMapping("admin/questionStatis/ajax/findInputAmountUserList")
	@ResponseBody
	public JsonResult findInputAmountUserList(Date startDate, Date endDate, String userName) {
		JsonResult json = new JsonResult();
		InputStatisQuery statisDTO = new InputStatisQuery();
		/*if(null == startDate && null == endDate) {
			statisDTO = getLastestMonth(statisDTO);
			startDate = statisDTO.getStartDate();
			endDate = statisDTO.getEndDate();
			startDate.setHours(0);
			startDate.setMinutes(0);
			startDate.setSeconds(0);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
		} else {
			if (null != startDate) {
				statisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				//statisDTO.setEndDate(getEndDate(endDate));
				statisDTO.setEndDate(endDate);
			}
		}*/
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		
		try {
			Date dateStart = df.parse("2008-01-01");
			statisDTO.setStartDate(dateStart);
			statisDTO.setEndDate(new Date());
			List<InputStatisDTO> inputStatisList = questionLogService.findInputAmountExecuteList(statisDTO, userName);
			json.addDatas("startDate", DateUtils.formatDate(startDate));
			json.addDatas("endDate", DateUtils.formatDate(endDate));
			json.addDatas("inputStatisList", inputStatisList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 查询整个乐课网录入量
	 * @param 
	 * @return
	 */
	@RequestMapping("admin/questionStatis/ajax/findTotalAmount")
	@ResponseBody
	public JsonResult queryQuestionTotalAmount() {
		JsonResult json = new JsonResult();
		
		String questionAmountTotal = (String) CacheUtils.hget("amount.master.totalquestion",  "total");
		String month = (String) CacheUtils.hget("amount.master.totalquestion", "month");
		Date now = new Date();
		
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND,0);  
		calendar.set(Calendar.MILLISECOND, 0);  
		
		if (month != null && now.getMonth() == Long.parseLong(month) && questionAmountTotal != null ){
			Long workbookAmount = workbookQueryService.queryWorkbookTotalAmount();
			
			InputStatisDTO inputStatisDTO = new InputStatisDTO();
			inputStatisDTO.setStartDate(calendar.getTime());
			Long questionAmount = questionQueryService.queryQuestionTotalAmount(inputStatisDTO);
			
			json.addDatas("questionTotalAmount", questionAmount + Integer.parseInt(questionAmountTotal));
			json.addDatas("workbookTotalAmount", workbookAmount);
		}else{
			Long workbookAmount = workbookQueryService.queryWorkbookTotalAmount();
			CacheUtils.hset("amount.master.totalquestion", "month", ((Integer)now.getMonth()).toString());
			
			InputStatisDTO inputStatisDTOOld = new InputStatisDTO();
			inputStatisDTOOld.setEndDate(calendar.getTime());
			Long questionAmountOld = questionQueryService.queryQuestionTotalAmount(inputStatisDTOOld);
			CacheUtils.hset("amount.master.totalquestion", "total",  questionAmountOld.toString());
			
			InputStatisDTO inputStatisDTO = new InputStatisDTO();
			inputStatisDTO.setStartDate(calendar.getTime());
			Long questionAmount = questionQueryService.queryQuestionTotalAmount(inputStatisDTO);
			json.addDatas("questionTotalAmount", questionAmount + questionAmountOld);
			json.addDatas("workbookTotalAmount", workbookAmount);
		}
		//另外两个要jsonp获取
		
		
		return json;
	}
	
	/**
	 * 查询教研员录入量
	 * @param 
	 * @return
	 */
	@RequestMapping("admin/questionStatis/ajax/clearQuestionAmountCache")
	@ResponseBody
	public JsonResult clearQuestionAmountCache() {
		CacheUtils.hset("amount.master.question", "month", ((Integer)99).toString());
		CacheUtils.hset("amount.master.totalquestion", "month", ((Integer)99).toString());
		CacheUtils.hset("amount.master.totalquestion", "total", ((Integer)0).toString());
		JsonResult json = new JsonResult();
		return json;
	}
	/**
	 * 查询教研员录入量
	 * @param 
	 * @return
	 */
	@RequestMapping("admin/questionStatis/ajax/queryQuestionAmount")
	@ResponseBody
	public JsonResult queryQuestionAmount() {
		JsonResult json = new JsonResult();
		
		String questionAmountPrimary = (String) CacheUtils.hget("amount.master.question",  String.valueOf(1));
		String questionAmountSenior = (String) CacheUtils.hget("amount.master.question",  String.valueOf(2));
		String questionAmountJunior = (String) CacheUtils.hget("amount.master.question",  String.valueOf(3));
		//CacheUtils.hset("amount.master.question", "month", ((Integer)5).toString());
		String month = (String) CacheUtils.hget("amount.master.question", "month");
		//另外两个要jsonp获取
		Date now = new Date();
		
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND,0);  
		calendar.set(Calendar.MILLISECOND, 0);  
		
		if (month != null && now.getMonth() == Long.parseLong(month) && questionAmountPrimary != null && questionAmountSenior != null && questionAmountJunior != null){
			InputStatisDTO inputStatisDTO = new InputStatisDTO();
			List<InputStatisDTO> workbookAmountList = workbookQueryService.queryWorkbookAmount(inputStatisDTO);
			
			inputStatisDTO.setStartDate(calendar.getTime());
			List<InputStatisDTO> questionAmountListOld = new ArrayList<InputStatisDTO>();
			
			InputStatisDTO inputStatisDTOPri = new InputStatisDTO();
			inputStatisDTOPri.setSchoolStageId(1L);
			inputStatisDTOPri.setInputAmount(Integer.parseInt(questionAmountPrimary));
			questionAmountListOld.add(inputStatisDTOPri);
			
			InputStatisDTO inputStatisDTOSnr = new InputStatisDTO();
			inputStatisDTOSnr.setSchoolStageId(2L);
			inputStatisDTOSnr.setInputAmount(Integer.parseInt(questionAmountSenior));
			questionAmountListOld.add(inputStatisDTOSnr);
			
			InputStatisDTO inputStatisDTOJnr = new InputStatisDTO();
			inputStatisDTOJnr.setSchoolStageId(3L);
			inputStatisDTOJnr.setInputAmount(Integer.parseInt(questionAmountJunior));
			questionAmountListOld.add(inputStatisDTOJnr);
			
			
			List<InputStatisDTO> questionAmountList = questionQueryService.queryQuestionAmount(inputStatisDTO);
			for (int i = 0; i < questionAmountListOld.size(); i++){
				for (int j = 0; j < questionAmountList.size(); j++){
					if (questionAmountList.get(j).getSchoolStageId() == questionAmountListOld.get(i).getSchoolStageId()){
						Integer amount = questionAmountListOld.get(i).getInputAmount() + questionAmountList.get(j).getInputAmount();
						questionAmountListOld.get(i).setInputAmount(amount);
						//CacheUtils.hset("amount.master.question", String.valueOf(1), amount.toString());
					}
				}
			}
			
			json.addDatas("questionAmount", questionAmountListOld);
			json.addDatas("workbookAmount", workbookAmountList);	
		}else{
			//把月头的查询一下在加上这个月
			CacheUtils.hset("amount.master.question", "month", ((Integer)now.getMonth()).toString());
			InputStatisDTO inputStatisDTO = new InputStatisDTO();
			List<InputStatisDTO> workbookAmountList = workbookQueryService.queryWorkbookAmount(inputStatisDTO);
			
			InputStatisDTO inputStatisDTOOld = new InputStatisDTO();
			inputStatisDTOOld.setEndDate(calendar.getTime());
			List<InputStatisDTO> questionAmountListOld = questionQueryService.queryQuestionAmount(inputStatisDTOOld);
			for (int i = 0; i < questionAmountListOld.size(); i++){
				if (questionAmountListOld.get(i).getSchoolStageId() == null) {
					continue;
				}
				if (questionAmountListOld.get(i).getSchoolStageId() - 1 == 0){
					CacheUtils.hset("amount.master.question", String.valueOf(1),  questionAmountListOld.get(i).getInputAmount().toString());
				}else if (questionAmountListOld.get(i).getSchoolStageId() - 2 == 0){
					CacheUtils.hset("amount.master.question", String.valueOf(2), questionAmountListOld.get(i).getInputAmount().toString());
				}else if (questionAmountListOld.get(i).getSchoolStageId() - 3 == 0){
					CacheUtils.hset("amount.master.question", String.valueOf(3),  questionAmountListOld.get(i).getInputAmount().toString());
				}
			}
			
			//1判断缓存为空否，2没进去也要返回
			inputStatisDTO.setStartDate(calendar.getTime());
			List<InputStatisDTO> questionAmountList = questionQueryService.queryQuestionAmount(inputStatisDTO);
			for (int i = 0; i < questionAmountListOld.size(); i++){
				for (int j = 0; j < questionAmountList.size(); j++){
					if (questionAmountList.get(j).getSchoolStageId() == questionAmountListOld.get(i).getSchoolStageId()){
						Integer amount = questionAmountListOld.get(i).getInputAmount() + questionAmountList.get(j).getInputAmount();
						questionAmountListOld.get(i).setInputAmount(amount);
					}	
				}
			}
			
			json.addDatas("questionAmount", questionAmountListOld);
			json.addDatas("workbookAmount", workbookAmountList);
		}
		
		
		return json;
	}
	
	/**
	 * 查询教研员学段录入量
	 * @param 
	 * @return
	 */
	@RequestMapping("admin/questionStatis/ajax/queryStageQuestionAmount")
	@ResponseBody
	public JsonResult queryStageQuestionAmount(Long schoolStageId, Date startDate, Date endDate) {
		JsonResult json = new JsonResult();
		
		//另外两个要jsonp获取
		InputStatisDTO inputStatisDTO = new InputStatisDTO();
		if (schoolStageId == null){
			inputStatisDTO.setSubjectId(0L);
		}
		inputStatisDTO.setSchoolStageId(schoolStageId);
		inputStatisDTO.setStartDate(startDate);
		inputStatisDTO.setEndDate(endDate);
		List<InputStatisDTO> questionAmountList = questionQueryService.queryQuestionAmount(inputStatisDTO);
		List<InputStatisDTO> workbookAmountList = workbookQueryService.queryWorkbookAmount(inputStatisDTO);
		json.addDatas("questionStageAmount", questionAmountList);
		json.addDatas("workbookStageAmount", workbookAmountList);
		
		return json;
	}
	
	/**
	 * 查询各教研员学段录入量
	 * @param 
	 * @return
	 */
	@RequestMapping("admin/questionStatis/ajax/queryResearcherQuestionAmount")
	@ResponseBody
	public JsonResult queryResearcherQuestionAmount(Long schoolStageId, Long subjectId, Date startDate, Date endDate) {
		JsonResult json = new JsonResult();
		
		List<UserRemote> userList = userRemoteService.findUserListForQuestion(null, RoleCst.RESEARCHER);
		/*List<UserRemote> researcherList = userRemoteService.findUserListForQuestion(null, RoleCst.RESEARCHER);
		for (UserRemote userRemote : researcherList){
			if (findUserIdList(userList).contains(userRemote.getId()) == false){
				userList.add(userRemote);
			}
		}*/
		//另外两个要jsonp获取
		InputStatisDTO inputStatisDTO = new InputStatisDTO();
		inputStatisDTO.setSchoolStageId(schoolStageId);
		inputStatisDTO.setSubjectId(subjectId);
		inputStatisDTO.setStartDate(startDate);
		inputStatisDTO.setEndDate(endDate);
		inputStatisDTO.setUserIds(findUserIdList(userList));
		List<InputStatisDTO> questionAmountList = questionQueryService.queryQuestionAmount(inputStatisDTO);
		List<InputStatisDTO> workbookAmountList = workbookQueryService.queryWorkbookAmount(inputStatisDTO);
		json.addDatas("questionStageAmount", questionAmountList);
		json.addDatas("workbookStageAmount", workbookAmountList);
		
		return json;
	}

	@RequestMapping("admin/questionStatis/ajax/getAlbumTotal")
	@ResponseBody
	public JsonResult getAlbumTotal() {
		return new JsonResult().addDatas("albumTotal", albumCourseRemoteService.getAlbumTotal());
	}

	/**
	 *	
	 * 描述:根据用户列表获取用户id列表
	 *
	 * @author  lavender
	 * @created 2014年11月3日 下午4:32:18
	 * @since   v1.0.0 
	 * @param userList
	 * @return
	 * @return  List<Long>
	 */
	private List<Long> findUserIdList(List<UserRemote> userList) {
		List<Long> userIdList = new ArrayList<Long>();
		for (UserRemote user : userList) {
			userIdList.add(user.getId());
		}
		return userIdList;
	}
	
	
	/**
	 * 根据用户id，查询每天录入量，按时间排序
	 * author：lavender
	 * 2014-4-24下午3:12:57
	 */
	@RequestMapping(value = { "admin/questionStatis/input/userInputAmount" })
	public void userInputAmount(Date startDate, Date endDate, Long userId,String userName, ModelMap model) {
		try {
			if (null != userName) {
				userName = URLDecoder.decode(userName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}
	
	/**
	 * 根据用户id，查询每天录入量，按时间排序
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:12:42
	 */
	@RequestMapping("common/questionStatis/ajax/findInputAmountListByUserIdOrderByDate")
	@ResponseBody
	public JsonResult findInputAmountListByUserIdOrderByDate(Date startDate, Date endDate, Long userId) {
		JsonResult json = new JsonResult();
		InputStatisQuery statisDTO = new InputStatisQuery();
		if(null == startDate && null == endDate) {
			statisDTO = getLastestMonth(statisDTO);
			startDate = statisDTO.getStartDate();
			endDate = statisDTO.getEndDate();
		} else {
			if (null != startDate) {
				statisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				statisDTO.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findInputAmountListByUserIdOrderByDate(userId, statisDTO);
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		
		json.addDatas("inputStatisList", inputStatisList);
		
		return json;
	}
	
	/**
	 * 查询审核量统计
	 * author：lavender
	 * 2014-4-24下午3:13:13
	 */
	@RequestMapping("admin/questionStatis/check/checkAmountList")
	public void checkAmountList() {
		
	}
	
	/**
	 * 查询审核量统计
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:12:50
	 */
	@RequestMapping("admin/questionStatis/ajax/findCheckAmountList")
	@ResponseBody
	public JsonResult findCheckAmountList(Date startDate, Date endDate, String userName) {
		JsonResult json = new JsonResult();
		InputStatisQuery statisDTO = new InputStatisQuery();
		if(null == startDate && null == endDate) {
			statisDTO = getLastestMonth(statisDTO);
			startDate = statisDTO.getStartDate();
			endDate = statisDTO.getEndDate();
		} else {
			if (null != startDate) {
				statisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				statisDTO.setEndDate(getEndDate(endDate));
			}
		}
		
		List<InputStatisDTO> inputStatisList = questionLogService.findCheckAmountList(statisDTO, userName);
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		json.addDatas("inputStatisList", inputStatisList);
		
		return json;
	}
	
	/**
	 * 根据用户id，查询每天审核量，按时间排序
	 * author：lavender
	 * 2014-4-24下午3:13:17
	 */
	@RequestMapping("admin/questionStatis/check/userCheckAmount")
	public void userCheckAmount(Date startDate, Date endDate, Long userId,String userName, HttpServletRequest request, ModelMap model) {
		try {
			userName = URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}
	
	/**
	 * 根据用户id，查询每天审核量，按时间排序
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:13:20
	 */
	@RequestMapping("common/questionStatis/ajax/findCheckerCheckAmountList")
	@ResponseBody
	public JsonResult findCheckerCheckAmountList(Date startDate, Date endDate, Long userId) {
		JsonResult json = new JsonResult();
		InputStatisQuery statisDTO = new InputStatisQuery();
		if (null == startDate && null == endDate) {
			statisDTO = getLastestMonth(statisDTO);
			startDate = statisDTO.getStartDate();
			endDate = statisDTO.getEndDate();
		} else {
			if (null != startDate) {
				statisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				statisDTO.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findCheckerCheckAmountList(statisDTO, userId);

		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		json.addDatas("inputStatisList", inputStatisList);

		return json;
	}

	
	/**
	 * 查询校对量统计
	 * author：lavender
	 * 2014-4-24下午3:13:23
	 */
	@RequestMapping("admin/questionStatis/researcher/verifyAmountList")
	public void verifyAmountList() {
		
	}
	
	
	/**
	 * 查询校对量统计(导题审核统计)
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:13:26
	 */
	@RequestMapping("admin/questionStatis/ajax/findProofreadingAmountList")
	@ResponseBody
	public JsonResult findVerifyAmountList(Date startDate, Date endDate, String userName) {
		JsonResult json = new JsonResult();
		InputStatisQuery query = new InputStatisQuery();
		if(null == startDate && null == endDate) {
			query = getLastestMonth(query);
			startDate = query.getStartDate();
			endDate = query.getEndDate();
		} else {
			if (null != startDate) {
				query.setStartDate(startDate);
			}
			if (null != endDate) {
				query.setEndDate(getEndDate(endDate));
			}
		}
		
		List<InputStatisDTO> inputStatisList = questionLogService.findVerifyAmountList(query, userName);

		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		json.addDatas("inputStatisList", inputStatisList);
		
		return json;
	}
	
	/**
	 * 根据用户id，查询每天校对量，按时间排序
	 * author：lavender
	 * 2014-4-24下午3:13:29
	 */
	@RequestMapping("admin/questionStatis/researcher/verifyAmount")
	public void userProofreadingAmount(Date startDate, Date endDate, Long userId,String userName, ModelMap model) {
		try {
			userName = URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}
	
	/**
	 * 根据用户id，查询每天校对量，按时间排序
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:13:32
	 */
	@RequestMapping("common/questionStatis/ajax/findProofreadingAmountListByUserIdOrderByDate")
	@ResponseBody
	public JsonResult findProofreadingAmountListByUserIdOrderByDate(Date startDate, Date endDate, Long userId) {
		JsonResult json = new JsonResult();
		InputStatisQuery statisDTO = new InputStatisQuery();
		if(null == startDate && null == endDate) {
			statisDTO = getLastestMonth(statisDTO);
			startDate = statisDTO.getStartDate();
			endDate = statisDTO.getEndDate();
		} else {
			if (null != startDate) {
				statisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				statisDTO.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findProofreadingAmountListByUserIdOrderByDate(userId, statisDTO);
		InputStatisDTO is = questionLogService.findUnVerifyAmountBySchoolStageSubjects(userId, statisDTO);
		Integer unProofreadingAmount = null != is ? is.getUnProofreadingAmount() : 0;
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		json.addDatas("inputStatisList", inputStatisList);
		json.addDatas("unProofreadingAmount", unProofreadingAmount);

		return json;
	}
	
	/**
	 * 正式题量统计页面
	 * @param modle
	 * author：lavender
	 * 2014-5-4下午2:24:36
	 */
	@RequestMapping("admin/questionStatis/total/totalStatis")
	public String totalStatisPage() {
		return "/auth/admin/questionStatis/totalStatis";
		
	}
	
	/**
	 * 
	 * @param modle
	 * author：lavender
	 * 2014-5-4下午2:24:36
	 */
	@RequestMapping("admin/questionStatis/ajax/schoolStageList")
	@ResponseBody
	public JsonResult schoolStageList() {
		JsonResult json = new JsonResult();
		List<SchoolStageRemote> schoolStageList = SchoolStageContext
				.findSchoolStages();
		
		json.addDatas("schoolStageList", schoolStageList);
		return json;
	}

	@RequestMapping("admin/questionStatis/ajax/findQuestionAmountByMaterial")
	@ResponseBody
	public JsonResult findQuestionAmountByMaterial(InputStatisQuery query) {
		JsonResult json = new JsonResult();

		List<SubjectMaterial> inputStatisList = questionLogService.findQuestionAmountByMaterial(query);

		json.addDatas("inputStatisList", inputStatisList);
		return json;
	}

	/**
	 * 根据知识点，查询题量（用于正式题量统计下的按知识点统计）
	 * @param knowledgeId
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:14:48
	 */
	@RequestMapping("admin/questionStatis/ajax/findStatisListByKnowledge")
	@ResponseBody
	public JsonResult findStatisListByKnowledge(){
		JsonResult json = new JsonResult();
		List<InputStatisDTO> inputStatisList = questionLogService.findStatisListGroupByKnowledge();
		List<SchoolStageRemote> schoolStageList = SchoolStageContext
				.findSchoolStages();
		
		json.addDatas("inputStatisList", inputStatisList);
		json.addDatas("schoolStageList", schoolStageList);
		return json;
	}
	
	/**
	 * 根据题库标签分组查询题量（用于正式题量统计下的按标签统计）
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:14:57
	 */
	@RequestMapping("admin/questionStatis/ajax/findStatisGroupByOfficialTagId")
	@ResponseBody
	public JsonResult findStatisGroupByOfficialTagId(){
		JsonResult json = new JsonResult();
		List<InputStatisDTO> inputStatisList = questionLogService.findStatisGroupByOfficialTagId();
		
		json.addDatas("inputStatisList", inputStatisList);
		return json;
	}
	
	/**
	 * 根据题库标签分组查询题量（用于正式题量统计下的按标签统计）
	 * author：lavender
	 * 2014-4-24下午3:14:53
	 */
	@RequestMapping("admin/questionStatis/gradeOfOfficialTagStatis")
	public void gradeOfOfficialTagStatis(Long officialTagId, ModelMap model){
		model.addAttribute("officialTagId", officialTagId);
	}
	
	/**
	 * 根据题库标签分组查询题量（用于正式题量统计下的按标签统计）
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:14:57
	 */
	@RequestMapping("admin/questionStatis/ajax/findStatisByOfficialTagId")
	@ResponseBody
	public JsonResult findStatisByOfficialTagId(Long officialTagId){
		JsonResult json = new JsonResult();
		List<InputStatisDTO> inputStatisList = questionLogService.findStatisByOfficialTagId(officialTagId);
		
		json.addDatas("inputStatisList", inputStatisList);
		return json;
	}
	
	/**
	 * 根据类型，查询统计信息（用于管理员首页）
	 * author：lavender
	 * 2014-4-24下午3:15:00
	 */
	@RequestMapping("admin/questionStatis/statisByType")
	public void statisByType(){
		
	}
	
	/**
	 * 根据类型，查询统计信息（用于管理员首页）
	 * @param type
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:15:04
	 */
	@RequestMapping("admin/questionStatis/ajax/findStatisByType")
	@ResponseBody
	public JsonResult findStatisByType(Integer type) {
		JsonResult json = new JsonResult();
		InputStatisDTO inputStatis = questionLogService.findStatisByType(type);
		
		json.addDatas("inputStatis", inputStatis);
		
		return json;
	}
	
	/**
	 * 草稿题库页面
	 * author：lavender
	 * 2014-5-5下午2:40:56
	 */
	@RequestMapping("admin/questionStatis/draft/draftAmountList")
	public String draftAmountList() {
		return "/auth/admin/questionStatis/draftAmountList";
	}
	
	/**
	 * 查询草稿题库
	 * @return
	 * author：lavender
	 * 2014-5-5下午2:41:07
	 */
	@RequestMapping("admin/questionStatis/ajax/findDraftAmountList")
	@ResponseBody
	public JsonResult findDraftAmountList() {
		JsonResult json = new JsonResult();
		List<InputStatisDTO> inputStatisList = questionLogService.findDraftAmountList();
		
		json.addDatas("inputStatisList", inputStatisList);
		return json;
	}
	

	/**
	 *	
	 * 描述:查询教研员审核统计页面
	 *
	 * @author  lavender
	 * @created 2014年11月4日 上午8:51:14
	 * @since   v1.0.0 
	 * @return  void
	 */
	@RequestMapping("admin/questionStatis/researcher/checkAmountList")
	public void researcherCheckAmountList(Date startDate, Date endDate, Long userId, String userName, ModelMap model) {

	}

	/**
	 *	
	 * 描述:查询教研员审核统计
	 *
	 * @author  lavender
	 * @created 2014年11月4日 上午9:00:10
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping("admin/questionStatis/ajax/findResearcherCheckList")
	@ResponseBody
	public JsonResult findResearcherCheckList(InputStatisQuery inputStatisDTO) {
		JsonResult json = new JsonResult();
		Date startDate = inputStatisDTO.getStartDate();
		Date endDate = inputStatisDTO.getEndDate();
		if (null == startDate && null == endDate) {
			inputStatisDTO = getLastestMonth(inputStatisDTO);
			startDate = inputStatisDTO.getStartDate();
			endDate = inputStatisDTO.getEndDate();
		} else {
			if (null != startDate) {
				inputStatisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				inputStatisDTO.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findResearcherCheckAmountList(inputStatisDTO);
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		json.addDatas("inputStatisList", inputStatisList);
		return json;
	}

	/**
	 *	
	 * 描述:根据教研员id查询该教研员的审核统计页面
	 *
	 * @author  lavender
	 * @created 2014年11月4日 上午9:08:28
	 * @since   v1.0.0 
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @param userName
	 * @param model
	 * @return  void
	 */
	@RequestMapping("admin/questionStatis/researcher/checkAmount")
	public void researcherCheckAmount(Date startDate, Date endDate, Long userId, String userName, ModelMap model) {
		try {
			userName = URLDecoder.decode(userName, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}

	/**
	 *	
	 * 描述:根据教研员id查询该教研员的审核统计
	 *
	 * @author  lavender
	 * @created 2014年11月4日 上午9:09:31
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping("common/questionStatis/ajax/findResearcherCheckAmountListByUserId")
	@ResponseBody
	public JsonResult findResearcherCheckAmountListByUserId(InputStatisQuery inputStatisDTO) {
		JsonResult json = new JsonResult();
		Date startDate = inputStatisDTO.getStartDate();
		Date endDate = inputStatisDTO.getEndDate();
		if (null == startDate && null == endDate) {
			inputStatisDTO = getLastestMonth(inputStatisDTO);
			startDate = inputStatisDTO.getStartDate();
			endDate = inputStatisDTO.getEndDate();
		} else {
			if (null != startDate) {
				inputStatisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				inputStatisDTO.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findResearcherCheckAmountListByUserId(inputStatisDTO);
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));

		json.addDatas("inputStatisList", inputStatisList);

		return json;
	}


		/**
	 *	
	 * 描述:查询教研员导题统计页面
	 *
	 * @author  lavender
	 * @created 2015年1月14日 下午3:16:48
	 * @since   v1.0.0 
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @param userName
	 * @param model
	 * @return  void
	 */
	@RequestMapping("admin/questionStatis/researcher/importedAmountList")
	public void researcherImportedAmountList() {
	}


	/**
	 *	
	 * 描述:查询教研员导题统计
	 *
	 * @author  lavender
	 * @created 2015年1月14日 下午3:17:02
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping("admin/questionStatis/ajax/findResearcherImportedList")
	@ResponseBody
	public JsonResult findResearcherImportedList(InputStatisQuery inputStatisDTO) {
		JsonResult json = new JsonResult();
		Date startDate = inputStatisDTO.getStartDate();
		Date endDate = inputStatisDTO.getEndDate();
		if (null == startDate && null == endDate) {
			inputStatisDTO = getLastestMonth(inputStatisDTO);
			startDate = inputStatisDTO.getStartDate();
			endDate = inputStatisDTO.getEndDate();
		} else {
			if (null != startDate) {
				inputStatisDTO.setStartDate(startDate);
			}
			if (null != endDate) {
				inputStatisDTO.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findResearcherImportedAmountList(inputStatisDTO);
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));
		json.addDatas("inputStatisList", inputStatisList);
		return json;
	}


	/**
	 *	
	 * 描述:根据教研员id查询该教研员的导题统计页面
	 *
	 * @author  lavender
	 * @created 2015年1月14日 下午3:17:20
	 * @since   v1.0.0 
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @param userName
	 * @param model
	 * @return  void
	 */
	@RequestMapping("admin/questionStatis/researcher/importedAmount")
	public void researcherImportedAmount(Date startDate, Date endDate, Long userId, String userName, ModelMap model) {
		try {
			userName = URLDecoder.decode(userName, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}

	/**
	 *	
	 * 描述:根据教研员id查询该教研员的导题统计
	 *
	 * @author  lavender
	 * @created 2015年1月14日 下午3:17:39
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  JsonResult
	 */
	@RequestMapping("common/questionStatis/ajax/findResearcherImportedAmountListByUserId")
	@ResponseBody
	public JsonResult findResearcherImportedAmountListByUserId(InputStatisQuery query) {
		JsonResult json = new JsonResult();
		Date startDate = query.getStartDate();
		Date endDate = query.getEndDate();
		if (null == startDate && null == endDate) {
			query = getLastestMonth(query);
			startDate = query.getStartDate();
			endDate = query.getEndDate();
		} else {
			if (null != startDate) {
				query.setStartDate(startDate);
			}
			if (null != endDate) {
				query.setEndDate(getEndDate(endDate));
			}
		}
		List<InputStatisDTO> inputStatisList = questionLogService.findResearcherImportedAmountListByUserId(query);
		json.addDatas("startDate", DateUtils.formatDate(startDate));
		json.addDatas("endDate", DateUtils.formatDate(endDate));

		json.addDatas("inputStatisList", inputStatisList);

		return json;
	}
	//--------------------------------------checker------------------------------------------//
	/**
	 * 根据用户id，查询审核量统计,及昨日审核量（用于审核员首页）
	 * author：lavender
	 * 2014-4-24下午3:15:10
	 */
	@RequestMapping("checker/questionStatis/checkAmountByUserId")
	public void checkAmountByUserId() {

	}

	/**
	 * 根据用户id，查询审核量统计,及昨日审核量（用于审核员首页）
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:15:13
	 */
	@RequestMapping("checker/questionStatis/ajax/findCheckAmountByUserId")
	@ResponseBody
	public JsonResult findCheckAmountByUserId(Long userId) {
		JsonResult json = new JsonResult();
		InputStatisDTO inputStatis = questionLogService.findCheckAmountByUserId(userId);

		json.addDatas("inputStatis", inputStatis);

		return json;
	}

	/**
	 * 根据用户id，查询审核量统计
	 * author：lavender
	 * 2014-4-24下午3:15:17
	 */
	@RequestMapping("checker/questionStatis/checkerCheckAmount")
	public String checkerCheckAmount(ModelMap model) {

		return "auth/admin/questionStatis/check/userCheckAmount";
	}

	//--------------------------------------inputer------------------------------------------//
	
	/**
	 * 根据用户id，查询录入量统计
	 * author：lavender
	 * 2014-4-24下午3:15:29
	 */
	@RequestMapping("inputer/questionStatis/inputerInputAmount")
	public String inputerInputAmount(ModelMap model) {
		//return "auth/admin/questionStatis/input/input";
		return "auth/common/question/amount/input";
	}
	
	//--------------------------------------researcher---------------------------------------//
	
	/**
	 * 根据用户id，查询校对量统计
	 * author：lavender
	 * 2014-4-24下午3:15:43
	 */
	@RequestMapping("researcher/questionStatis/researcherProofreadingAmount")
	public String researcherProofreadingAmount(ModelMap model) {
		return "auth/admin/questionStatis/researcher/verifyAmount";
	}
	
	/**
	 * 根据用户id，查询校对量统计
	 * author：lavender
	 * 2014-4-24下午3:15:43
	 */
	@RequestMapping("researcher/question/verify/questionProofreading")
	public String questionProofreading() {
		return "/auth/researcher/questionStatis/questionProofreading";
	}
	
	/**
	 * 根据用户id，查询每天校对量，按时间排序
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-24下午3:13:32
	 */
	@RequestMapping("researcher/questionStatis/ajax/findQuestionProofreading")
	@ResponseBody
	public JsonResult findQuestionProofreading() {
		JsonResult json = new JsonResult();
		List<SchoolStageSubject> schoolStageSubjects = ResearcherContext.researcher.findSchoolStageSubjects();
		List<VerifyStatisDTO> verifyStatis = questionLogService.findResearcherVerifyStatis(schoolStageSubjects);
		json.addDatas("verifyStatis", verifyStatis);
		return json;
	}

	@RequestMapping("inputer/questionStatis/statisQueList")
	public String inputerStatisQueList(StatisQuestionQuery query, Model model) {
		query.setOperatorId(UserContext.user.getUserId());
		query.setOperateType(Question.QUE_STATUS_INPUT);
		model.addAttribute("inputStatisDTO", query);
		return "/auth/common/question/statisQueList";
	}

	@RequestMapping("checker/questionStatis/statisQueList")
	public String checkerStatisQueList(StatisQuestionQuery query, Model model) {
		query.setOperatorId(UserContext.user.getUserId());
		query.setOperateType(Question.QUE_STATUS_CHECKED);
		model.addAttribute("inputStatisDTO", query);
		return "/auth/common/question/statisQueList";
	}

	@RequestMapping("researcher/questionStatis/statisQueList")
	public String researcherStatisQueList(StatisQuestionQuery query, Model model) {
		query.setOperatorId(UserContext.user.getUserId());
		query.setOperateType(Question.QUE_STATUS_VERIFIED);
		model.addAttribute("inputStatisDTO", query);
		return "/auth/common/question/statisQueList";
	}

	@RequestMapping("admin/questionStatis/statisQueList")
	public String adminStatisQueList(StatisQuestionQuery query, Model model) {
		model.addAttribute("inputStatisDTO", query);
		return "/auth/common/question/statisQueList";
	}

	/*	@RequestMapping(value = "admin/questionStatis/draft/draftQueList")
		public String draftQueListPage(DraftQuestionQuery query, Model model) {
			model.addAttribute("query", query);
			return "/auth/admin/question/total/draftQueList";
		}*/

	/**
	 * 
	 * 描述: 草稿题目明细
	 * 
	 * @author liulb
	 * @created 2014年5月22日 下午7:41:05
	 * @since v1.0.0
	 * @param query
	 * @param model
	 * @return void
	 */
	@RequestMapping(value = "admin/questionStatis/total/draftQueList")
	public void draftQueListPage(DraftQuestionQuery query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping(value = "admin/questionStatis/total/draftQueListDetails")
	public void draftQueListDetails(DraftQuestionQuery query, Page page, Model model) {
		List<QuestionDTO> questions = questionService.queryDraftQuestions(query, page);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
	}

	@RequestMapping(value = "admin/questionStatis/total/pubQueList")
	public String pubQueListPage(PublishedQuestionQuery query, Model model) {
		model.addAttribute("query", query);
		return "/auth/admin/question/pubQueList";
	}

	@RequestMapping(value = "admin/questionStatis/total/knowledgePubQueList")
	public String knowledgePubQueListPage(PublishedQuestionQuery query,
			Model model) {
		model.addAttribute("query", query);
		return "/auth/admin/question/knowledgePubQueList";
	}
	

	/**
	 * 开始时间结束时间都为空时默认获取最近一个月的数据
	 * @param query
	 * @return
	 * author：lavender
	 * 2014-5-12下午1:58:22
	 */
	private InputStatisQuery getLastestMonth(InputStatisQuery query) {
		//开始时间结束时间都为空时默认获取最近一个月的数据
		Calendar calendar = Calendar.getInstance();
		Date now=calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		Date lastMonthDate = calendar.getTime();
		query.setStartDate(lastMonthDate);
		query.setEndDate(now);

		return query;
	}

	/**
	 * 搜索时时间向后延期一天，以便获取最后一天的数据
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-6-9上午9:12:47
	 */
	private Date getEndDate(Date endDate) {
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(endDate); 
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
	    endDate = calendar.getTime();   //这个时间就是日期往后推一天的结果
	    return endDate;
	}

}
