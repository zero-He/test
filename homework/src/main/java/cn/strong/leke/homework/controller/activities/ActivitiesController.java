package cn.strong.leke.homework.controller.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.dao.mongo.activities.AccessRecordDao;
import cn.strong.leke.homework.model.activities.MissionStatus;
import cn.strong.leke.homework.model.activities.UserActivitiesRecord;
import cn.strong.leke.homework.service.ActivityService;
import cn.strong.leke.homework.service.impl.MissionManager.MissionEnum;
import cn.strong.leke.homework.util.ActivityCst;

@Controller
@RequestMapping("/auth/*")
public class ActivitiesController {
	
	@Resource
	private ActivityService activityService;
	@Resource
	private AccessRecordDao accessRecordDao;
	
	private static final Date startDate = ActivityCst.startDate;
	
	private static final Date endDate = ActivityCst.endDate;
	
	@RequestMapping("student/activities/index")
	public void index(Model model){
		Long userId = UserContext.user.getUserId();
		String userName = UserContext.user.getUserName();
		UserActivitiesRecord record = activityService.getUserActivitiesRecord(userId);
		activityService.addAccessRecord(userId, userName);
		List<MissionStatus> list = record.getMissionChain();
		MissionStatus ms_1 = null;
		MissionStatus ms_2 = null;
		List<MissionStatus> ms_3 = null;
		for(MissionStatus ms : list){
			MissionEnum me = MissionEnum.getMissionEnum(ms.getMissionKey());
			switch (me) {
			case Mission_1:
				ms_1 = ms;
				break;
			case Mission_2:
				ms_2 = ms;
				break;
			default:
				if(ms_3==null){
					ms_3 = new ArrayList<>();
				}
				ms_3.add(ms);
				break;
			}
		}
		model.addAttribute("ms_1", ms_1);
		model.addAttribute("ms_2", ms_2);
		model.addAttribute("ms_3", ms_3);
		model.addAttribute("ms_3_size", ms_3!=null?ms_3.size():0);
	}
	
	@RequestMapping("student/activities/acceptPrize")
	@ResponseBody
	public JsonResult acceptPrize(Model model){
		checkout();
		JsonResult result = new JsonResult();
		Long userId = UserContext.user.getUserId();
		String userName = UserContext.user.getUserName();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		Integer lekeVal = activityService.acceptPrize(userId, userName, schoolId);
		result.addDatas("lekeVal", lekeVal);
		return result;
	}
	
	private void checkout(){
		Date now = new Date();
		if(now.before(startDate)){
			throw new ValidateException("活动还未开始");
		}else if(now.after(endDate)){
			throw new ValidateException("活动已经结束");
		}
	}
	
	
	/**
	 * 导出国庆活动日访问量
	 * @throws IOException 
	 */
	@RequestMapping(value = "student/activities/exportDateVist",method = RequestMethod.GET)
	public void exportDateVist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Map<String, Object>> list = accessRecordDao.find();
		String[] headers = { "日期", "访问量"};
		String[] fieldNames = { "date", "countNum"};
		String pattern = "yyyy-MM-dd HH:mm:ss";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		String fileName = FileUtils.getEncodingFileName("国庆活动日访问量.xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<Map<String, Object>>().exportExcel("国庆活动日访问量列表", headers, fieldNames,list,
				response.getOutputStream(), pattern);
	}
	
	/**
	 * 导出国庆活动日访问量
	 * @throws IOException 
	 */
	@RequestMapping(value = "student/activities/exportStatistics",method = RequestMethod.GET)
	public void exportStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Map<String, Object>> list = this.activityService.findAllStatistics();
		String[] headers = {
				"任务一学生参与人数",
				"任务一学生完成人数",
				"任务一学生作业订正总数",
				"任务二学生参与人数",
				"任务二学生完成人数",
				"任务二学生良好人数",
				"任务二学生优秀人数",
				"任务二学生消灭错题份数",
				"任务二学生消灭错题份数良好份数",
				"任务二学生消灭错题份数优秀份数",
				"任务三学生参与人数",
				"任务三学完成人数",
				"任务三学生自主练习完成份数",
				"任务三学生平均正确率"};
		String[] fieldNames = {
				"m_1_n",
				"m_1_n_f",
				"m_1_bt",
				"m_2_n",
				"m_2_n_f",
				"m_2_gc",
				"m_2_ec",
				"m_2_woc",
				"m_2_gn",
				"m_2_en",
				"m_3_n",
				"m_3_n_f",
				"m_3_ec",
				"m_3_a"};
		String pattern = "yyyy-MM-dd HH:mm:ss";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		String fileName = FileUtils.getEncodingFileName("国庆活动统计.xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<Map<String, Object>>().exportExcel("国庆活动日访统计", headers, fieldNames,list,
				response.getOutputStream(), pattern);
	}
	
	
	
}
