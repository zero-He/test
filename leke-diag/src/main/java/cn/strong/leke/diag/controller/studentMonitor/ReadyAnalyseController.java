package cn.strong.leke.diag.controller.studentMonitor;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.studentMonitor.ReadyStatusBean;
import cn.strong.leke.diag.model.studentMonitor.StuReadyDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.service.studentMonitor.ReadyAnalyseService;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学生行为分析-预习
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-21 10:35:49
 */
@Controller
@RequestMapping("/auth/provost/studentMonitor/readyAnalyse")
public class ReadyAnalyseController {

	protected static final Logger logger = LoggerFactory.getLogger(ReadyAnalyseController.class);

	@Resource
	private ReadyAnalyseService readyAnalyseService;
	@Resource
	private CommService commService;

	/**
	 * 跳转到预习分析页
	 * @return
	 */
	@RequestMapping(value = "readyAnalysePage", method = RequestMethod.GET)
	public String readyAnalysePage() {
		return "auth/studentMonitor/readyAnalysePage";
	}

	/**
	 * 查询预习统计、走势、对比、排行等数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "queryReadyAnalysePageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryReadyAnalysePageData(RequestVo vo) {
		ReturnResultBean resultBean = readyAnalyseService.queryReadyAnalysePageData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 预习走势
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "queryReadyTrendData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryReadyTrendData(RequestVo vo) {
		ReturnResultBean resultBean = readyAnalyseService.queryReadyTrendData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "queryReadyDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryReadyDetailDataPage(RequestVo vo, Page page) {
		JsonResult result = new JsonResult();
		List<ReadyStatusBean> detailMaps = readyAnalyseService.queryReadyDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		result.addDatas("page", page);
		return result;
	}

	/**
	 * 预习分析导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "exportReadyDtlData", method = RequestMethod.GET)
	public void exportReadyDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitlePro(vo);
		String title = titleArr[0] + "预习明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"班级", "姓名", "应预习课堂数", "全面预习数", "全面预习率", "部分预习数", "部分预习率", "未预习数", "未预习率"};
		String[] fieldNames = {"className", "studentName", "readyLessonNum", "allReady", "allReadyPro", "partReady", "partReadyPro", "noReady", "noReadyPro"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<ReadyStatusBean> detailMaps = readyAnalyseService.queryReadyDetailData(vo);
		new ExportExcelForTable<ReadyStatusBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);
	}


	/**
	 * 查看预习详情页
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "stuReadyDetailPage", method = RequestMethod.GET)
	public String stuReadyDetailPage(RequestVo vo, Model model) {
		model.addAttribute("startDate", vo.getStartDate());
		model.addAttribute("endDate", vo.getEndDate());
		model.addAttribute("classId", vo.getClassId());
		model.addAttribute("subjectId", vo.getSubjectId());
		model.addAttribute("studentId", vo.getStudentId());
		model.addAttribute("studentName", vo.getStudentName());
		return "/auth/studentMonitor/stuReadyDetailPage";
	}

	/**
	 * 查看预习详情数据
	 * @return
	 */
	@RequestMapping(value = "queryStuReadyDetailData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryStuReadyDetailData(RequestVo vo, Page page) {
		JsonResult result = new JsonResult();
		List<StuReadyDetailBean> detailMaps = readyAnalyseService.queryStuReadyDetailData(vo, page);
		page.setDataList(detailMaps);
		result.addDatas("page", page);
		return result;
	}
}
