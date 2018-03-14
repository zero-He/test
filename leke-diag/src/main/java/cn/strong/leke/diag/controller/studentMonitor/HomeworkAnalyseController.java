package cn.strong.leke.diag.controller.studentMonitor;

import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.studentMonitor.HomeworkDetailBean;
import cn.strong.leke.diag.model.studentMonitor.StuHomeworkDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.service.studentMonitor.HomeworkAnalyseService;
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
 * 学生行为分析-作业
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-15 23:32:18
 */
@Controller
@RequestMapping("/auth/provost/studentMonitor/homeworkAnalyse")
public class HomeworkAnalyseController {

	protected static final Logger logger = LoggerFactory.getLogger(HomeworkAnalyseController.class);

	@Resource
	private HomeworkAnalyseService homeworkAnalyseService;
	@Resource
	private CommService commService;

	/**
	 * 跳转到作业分析页
	 * @return
	 */
	@RequestMapping(value = "homeworkAnalysePage", method = RequestMethod.GET)
	public String homeworkAnalysePage() {
		return "auth/studentMonitor/homeworkAnalysePage";
	}

	/**
	 * 查询作业提交统计、走势、对比、排行等数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "queryHomeworkAnalysePageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryHomeworkAnalysePageData(RequestVo vo) {
		ReturnResultBean resultBean = homeworkAnalyseService.queryHomeworkAnalysePageData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 作业走势
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "queryHomeworkTrendData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryHomeworkTrendData(RequestVo vo) {
		ReturnResultBean resultBean = homeworkAnalyseService.queryHomeworkTrendData(vo, false);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "queryHomeworkDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryHomeworkDetailDataPage(RequestVo vo, Page page) {
		JsonResult result = new JsonResult();
		List<HomeworkDetailBean> detailMaps = homeworkAnalyseService.queryHomeworkDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		result.addDatas("page", page);
		return result;
	}


	/**
	 * 作业分析导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "exportHomeworkDtlData", method = RequestMethod.GET)
	public void exportHomeworkDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitlePro(vo);
		String title = titleArr[0] + "作业明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"班级", "姓名", "应提交作业份数", "正常提交份数", "正常提交率", "延时提交份数", "延时提交率", "未提交份数", "未提交率"};
		String[] fieldNames = {"className", "studentName", "stuHomeworkNum", "submitNum", "submitPro", "lateSubmitNum", "lateSubmitPro", "noSubmitNum", "noSubmitPro"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<HomeworkDetailBean> detailMaps = homeworkAnalyseService.queryHomeworkDetailData(vo);
		new ExportExcelForTable<HomeworkDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);
	}

	/**
	 * 查看作业详情页
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "stuHomeworkDetailPage", method = RequestMethod.GET)
	public String stuHomeworkDetailPage(RequestVo vo, Model model) {
		model.addAttribute("startDate", vo.getStartDate());
		model.addAttribute("endDate", vo.getEndDate());
		model.addAttribute("classId", vo.getClassId());
		model.addAttribute("subjectId", vo.getSubjectId());
		model.addAttribute("studentId", vo.getStudentId());
		model.addAttribute("studentName", vo.getStudentName());
		return "/auth/studentMonitor/stuHomeworkDetailPage";
	}

	/**
	 * 查看作业详情数据
	 * @return
	 */
	@RequestMapping(value = "queryStuHomeworkDetailData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryStuHomeworkDetailData(RequestVo vo, Page page) {
		JsonResult result = new JsonResult();
		List<StuHomeworkDetailBean> detailMaps = homeworkAnalyseService.queryStuHomeworkDetailData(vo, page);
		page.setDataList(detailMaps);
		result.addDatas("page", page);
		return result;
	}
}