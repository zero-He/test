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
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.model.user.area.Area;
import cn.strong.leke.monitor.core.model.ClassStatistics;
import cn.strong.leke.monitor.core.service.IClassLeaderService;
import cn.strong.leke.monitor.core.service.IClassStatisticsService;
import cn.strong.leke.monitor.util.FileUtils;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;

/**
 * @ClassName: ClassLeaderController
 * @Description: CRM1.1教学监控
 * @author huangkai
 * @date 2017年3月21日 下午1:49:25
 * @version V1.0
 */
@Controller
@RequestMapping("/auth/common/crm/leader/*")
public class ClassLeaderController
{
	@Resource
	private IClassStatisticsService classStatisticsService;

	@Resource
	private IClassLeaderService classLeaderService;

	/**
	 * @Description: 学校统计列表
	 * @param model    参数
	 * @throws
	 */
	@RequestMapping("schoolleaderList")
	public void schoolleaderList(Model model)
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
		model.addAttribute("dateEnd", localDate);
		model.addAttribute("dateStart", localDate.minusMonths(1));
		model.addAttribute("schoolStageStr", JsonUtils.toJSON(classStatisticsService.getSchoolStage()));
		model.addAttribute("classStatistics", JsonUtils.toJSON(new ClassStatistics()));
	}

	/**
	 * @Description: 学校统计列表分页数据源
	 * @param classStatistics
	 * @param page 
	 * @return    参数
	 * @throws
	 */
	@RequestMapping("schoolleaderpage")
	@ResponseBody
	public JsonResult schoolleaderpage(ClassStatistics classStatistics, Page page, String isFind)
	{
		JsonResult jsonResult = new JsonResult();

		User user = UserContext.user.get();

		// 当前登陆人管辖的市场
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), user.getCurrentRole().getId());

		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		if (listSeller.size() > 0)
		{
			classStatistics.setListSeller(listSeller);
		} else
		{
			listSeller.add(Long.valueOf(0));

			classStatistics.setListSeller(listSeller);
		}

		if (classStatistics.getMarket() != null && classStatistics.getMarketId() == null)
		{
			classStatistics.setListMarketId(classLeaderService.getMarketChildren(classStatistics.getMarket()));
		}

		List<ClassStatistics> listClassStatistics = classStatisticsService.querySchoolClassStatistics(classStatistics,
				page);

		page.setDataList(listClassStatistics);

		jsonResult.addDatas("page", page);

		return jsonResult;
	}

	/**
	 * @Description:  学校统计导出
	 * @param dataJson
	 * @param request
	 * @param response
	 * @throws Exception    参数
	 * @throws
	 */
	@RequestMapping("classleaderexport")
	@ResponseBody
	public void classleaderexport(String dataJson, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ClassStatistics classStatistics = JsonUtils.fromJSON(dataJson, ClassStatistics.class);

		User user = UserContext.user.get();

		Long roleId = user.getCurrentRole().getId();

		// 当前登陆人管辖的市场
		List<Long> marketIds = classLeaderService.getMarket(user.getId(), roleId);

		List<Long> listSeller = classLeaderService.getSeller(marketIds);
		if (listSeller.size() > 0)
		{
			classStatistics.setListSeller(listSeller);
		} else
		{
			listSeller.add(Long.valueOf(0));

			classStatistics.setListSeller(listSeller);
		}

		if (classStatistics.getMarket() != null && classStatistics.getMarketId() == null)
		{
			classStatistics.setListMarketId(classLeaderService.getMarketChildren(classStatistics.getMarket()));
		}

		List<ClassStatistics> listClassStatistics = classStatisticsService.getExportData(classStatistics);

		String[] headers =
		{ "乐号", "营销处", "客户经理", "学校名称", "学段", "已结束课堂数", "备课课堂数", "上课课堂数", "备课率", "上课率" };
		String[] fieldNames =
		{ "loginName", "marketName", "sellerName", "schoolName", "schoolStageName", "endClassNum", "beikeClassNum",
				"classNum", "beikeRate", "classRate"};
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");

		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append("学校课堂统计");

		String fileName = FileUtils.getEncodingFileName(titleSingle.toString() + ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		new ExportExcelForTable<ClassStatistics>().exportExcel(titleSingle.toString(), headers, fieldNames,
				listClassStatistics, response.getOutputStream(), pattern);
	}

	/**
	 * @Description: 老师统计列表
	 * @param sellerId（客户经理）
	 * @param schoolId（学校ID）
	 * @param schoolStageId（学段ID）
	 * @param model    参数
	 * @throws
	 */
	@RequestMapping("teacherleaderlist")
	public void teacherleaderlist(ClassStatistics classStatistics, Model model)
	{
		UserBase userBase = UserBaseContext.getUserBaseByUserId(classStatistics.getSellerId());
		School school = SchoolContext.getSchoolBySchoolId(classStatistics.getSchoolId());

		LocalDate localDate = LocalDate.now();

		if (classStatistics.getStatisticsTimeEnd() == null || classStatistics.getStatisticsTimeEnd() == "")
		{
			model.addAttribute("dateEnd", localDate);
		} else
		{
			model.addAttribute("dateEnd", classStatistics.getStatisticsTimeEnd());
		}

		if (classStatistics.getStatisticsTimeStart() == null || classStatistics.getStatisticsTimeStart() == "")
		{
			model.addAttribute("dateStart", localDate.minusMonths(1));
		} else
		{
			model.addAttribute("dateStart", classStatistics.getStatisticsTimeStart());
		}

		User user = UserContext.user.get();

		// 角色ID
		Long raoleId = user.getCurrentRole().getId();

		model.addAttribute("roleId", raoleId);
		if (!classStatistics.getMarketName().equals("null"))
		{
			model.addAttribute("marketName", classStatistics.getMarketName());
		} else
		{
			model.addAttribute("marketName", "");
		}

		SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStage(classStatistics.getSchoolStageId());
		if (schoolStage != null)
		{
			model.addAttribute("schoolStageName",
					SchoolStageContext.getSchoolStage(classStatistics.getSchoolStageId()).getSchoolStageName());
		} else
		{
			model.addAttribute("schoolStageName", "");
		}

		model.addAttribute("loginName", classStatistics.getLoginName());
		model.addAttribute("sellerName", userBase.getUserName());
		model.addAttribute("sellerId", classStatistics.getSellerId());
		model.addAttribute("schoolName", school.getName());
		model.addAttribute("schoolId", classStatistics.getSchoolId());
		model.addAttribute("schoolStageId", classStatistics.getSchoolStageId());
		model.addAttribute("subjectStr", JsonUtils.toJSON(
				classStatisticsService.getSubject(classStatistics.getSchoolId(), classStatistics.getSchoolStageId())));
		model.addAttribute("gradeStr", JsonUtils.toJSON(
				classStatisticsService.getGrade(classStatistics.getSchoolId(), classStatistics.getSchoolStageId())));
		model.addAttribute("classStatistics", JsonUtils.toJSON(new ClassStatistics()));
	}
}
