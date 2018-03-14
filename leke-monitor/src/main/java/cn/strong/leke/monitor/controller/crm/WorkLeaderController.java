package cn.strong.leke.monitor.controller.crm;

import java.time.LocalDate;
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
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.area.Area;
import cn.strong.leke.monitor.core.model.WorkStatistics;
import cn.strong.leke.monitor.core.service.IClassLeaderService;
import cn.strong.leke.monitor.core.service.IClassStatisticsService;
import cn.strong.leke.monitor.core.service.IWorkStatisticsService;
import cn.strong.leke.monitor.util.FileUtils;

/**
 * @ClassName: WorkLeaderController
 * @Description: CRM1.1新增统计 
 * @author huangkai
 * @date 2017年3月23日 下午4:59:55
 * @version V1.0
 */
@Controller
@RequestMapping("/auth/common/crm/leader/*")
public class WorkLeaderController
{
	@Resource
	private IClassStatisticsService classStatisticsService;

	@Resource
	private IWorkStatisticsService workStatisticsService;

	@Resource
	private IClassLeaderService classLeaderService;

	/**
	 * @Description: 作业系统新增统计列表
	 * @param mode
	 * @throws
	 */
	@RequestMapping("workleaderlist")
	public void workleaderlist(Model model)
	{
		LocalDate localDate = LocalDate.now();

		User user = UserContext.user.get();
		// 角色ID
		Long raoleId = user.getCurrentRole().getId();

		List<Area> areas = new ArrayList<Area>();

		// 营销处经理
		if (!raoleId.equals(603L))
		{
			// 营销中心总经理
			if (raoleId.equals(604L))
			{
				Map<String, List<Area>> MarketMap = classLeaderService.getMarketMap(user, true);

				model.addAttribute("firstDpt", MarketMap.get("firstDpt"));
				model.addAttribute("secondDpt", MarketMap.get("secondDpt"));
				areas = MarketMap.get("secondDpt");
			} else
			{
				Map<String, List<Area>> MarketMap = classLeaderService.getMarketMap(user, false);

				model.addAttribute("secondDpt", MarketMap.get("secondDpt"));
				areas = MarketMap.get("secondDpt");
			}
		}

		model.addAttribute("areas", JsonUtils.toJSON(areas));
		model.addAttribute("roleId", raoleId);
		model.addAttribute("schoolStageStr", JsonUtils.toJSON(classStatisticsService.getSchoolStage()));
		model.addAttribute("dateEnd", localDate);
		model.addAttribute("dateStart", localDate.minusMonths(1));
	}

	/**
	 * @Description: 作业系统新增统计分页
	 * @param workStatistics
	 * @param page（分页）
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping("workleaderlistPage")
	@ResponseBody
	public JsonResult workleaderlistPage(WorkStatistics workStatistics, Page page, String isFind)
	{
		JsonResult jsonResult = new JsonResult();

		User user = UserContext.user.get();
		
		workStatistics.setType("1");

		// 当前登陆人管辖的市场
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), user.getCurrentRole().getId());

		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		if (listSeller.size() > 0)
		{
			workStatistics.setListSeller(listSeller);
		} else
		{
			listSeller.add(Long.valueOf(0));

			workStatistics.setListSeller(listSeller);
		}

		if (workStatistics.getMarket() != null && workStatistics.getMarketId() == null)
		{
			workStatistics.setListMarketId(classLeaderService.getMarketChildren(workStatistics.getMarket()));
		}

		List<WorkStatistics> listWorkStatistics = workStatisticsService.querySchoolMarket(workStatistics, page);

		page.setDataList(listWorkStatistics);

		jsonResult.addDatas("page", page);

		return jsonResult;
	}

	/**
	 * @Description: 作业系统新增统计导出
	 * @param dataJson
	 * @param request
	 * @param response
	 * @throws Exception    参数
	 * @throws
	 */
	@RequestMapping("workleaderexport")
	@ResponseBody
	public void workleaderexport(String dataJson, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		WorkStatistics workStatistics = JsonUtils.fromJSON(dataJson, WorkStatistics.class);

		User user = UserContext.user.get();

		workStatistics.setType("1");

		// 当前登陆人管辖的市场
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), user.getCurrentRole().getId());
		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		
		if (listSeller.size() > 0)
		{
			workStatistics.setListSeller(listSeller);
		} else
		{
			listSeller.add(Long.valueOf(0));

			workStatistics.setListSeller(listSeller);
		}

		if (workStatistics.getMarket() != null && workStatistics.getMarketId() == null)
		{
			workStatistics.setListMarketId(classLeaderService.getMarketChildren(workStatistics.getMarket()));
		}

		List<WorkStatistics> listWorkStatistics = workStatisticsService.querySchoolMarket(workStatistics, null);

		String[] headers =
		{ "乐号", "客户经理", "营销处", "学校名称", "学段", "注册用户数","注册学生数","注册老师数","注册家长数", "作业系统新增学生数", "新增购课人数" };
		String[] fieldNames =
		{ "lekeSn", "sellerName", "marketName", "schoolName", "schoolStageName", "allUserNum","studentNum","teacherNum","parentNum", "workStudentNum",
				"buyNum" };

		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");

		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("新增统计");

		String fileName = FileUtils.getEncodingFileName(titleSingle.toString() + ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		new ExportExcelForTable<WorkStatistics>().exportExcel(titleSingle.toString(), headers, fieldNames,
				listWorkStatistics, response.getOutputStream(), pattern);
	}

	/**
	 * @Description: 新增学生明细
	 * @param schoolStageId（学段ID）
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（统计时间开始）
	 * @param statisticsTimeEnd（统计时间结束）
	 * @param model 
	 * @throws
	 */
	@RequestMapping("leaderStudent")
	public void leaderStudent(String sellerName, String marketName, Long schoolStageId, Long schoolId,
			String statisticsTimeStart, String statisticsTimeEnd, Model model)
	{
		School school = SchoolContext.getSchoolBySchoolId(schoolId);

		User user = UserContext.user.get();

		// 角色ID
		Long raoleId = user.getCurrentRole().getId();

		if (marketName == null)
		{
			marketName = "";
		}
		model.addAttribute("sellerName", sellerName);
		model.addAttribute("marketName", marketName);
		model.addAttribute("roleId", raoleId);
		model.addAttribute("gradeStr", JsonUtils.toJSON(classStatisticsService.getGrade(schoolId, schoolStageId)));
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("schoolName", school.getName());
		model.addAttribute("schoolStageId", schoolStageId);
		model.addAttribute("schoolStageName", SchoolStageContext.getSchoolStage(schoolStageId).getSchoolStageName());
		model.addAttribute("statisticsTimeStart", statisticsTimeStart);
		model.addAttribute("statisticsTimeEnd", statisticsTimeEnd);
	}

	/**
	 * @Description: 作业系统新增明细
	 * @param schoolId（学校ID）
	 * @param statisticsTimeStart（统计时间开始）
	 * @param statisticsTimeEnd（统计时间结束）
	 * @param model 
	 * @throws
	 */
	@RequestMapping("workLeaderStudent")
	public void workLeaderStudent(String sellerName, String marketName, Long schoolStageId, Long schoolId,
			String statisticsTimeStart, String statisticsTimeEnd, Model model)
	{
		School school = SchoolContext.getSchoolBySchoolId(schoolId);

		User user = UserContext.user.get();

		// 角色ID
		Long raoleId = user.getCurrentRole().getId();

		if (marketName.equals("null"))
		{
			marketName = "";
		}

		model.addAttribute("sellerName", sellerName);
		model.addAttribute("marketName", marketName);
		model.addAttribute("roleId", raoleId);
		model.addAttribute("gradeStr", JsonUtils.toJSON(classStatisticsService.getGrade(schoolId, null)));
		model.addAttribute("schoolId", schoolId);
		model.addAttribute("schoolName", school.getName());
		model.addAttribute("schoolStageId", schoolStageId);
		model.addAttribute("schoolStageName", SchoolStageContext.getSchoolStage(schoolStageId).getSchoolStageName());
		model.addAttribute("statisticsTimeStart", statisticsTimeStart);
		model.addAttribute("statisticsTimeEnd", statisticsTimeEnd);
	}
}
