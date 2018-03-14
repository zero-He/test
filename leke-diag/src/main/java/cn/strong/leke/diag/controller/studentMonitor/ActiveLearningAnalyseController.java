package cn.strong.leke.diag.controller.studentMonitor;

import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.studentMonitor.ActiveLearningDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.service.studentMonitor.ActiveLearningAnalyseService;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学生行为分析-主动学习
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-25 11:35:31
 */
@Controller
@RequestMapping("/auth/provost/studentMonitor/activeLearningAnalyse")
public class ActiveLearningAnalyseController {

	protected static final Logger logger = LoggerFactory.getLogger(ActiveLearningAnalyseController.class);

	@Resource
	private CommService commService;
	@Resource
	private ActiveLearningAnalyseService activeLearningAnalyseService;

	/**
	 * 跳转到主动学习分析页
	 * @return
	 */
	@RequestMapping(value = "activeLearningAnalysePage", method = RequestMethod.GET)
	public String activeLearningAnalysePage() {
		return "auth/studentMonitor/activeLearningAnalysePage";
	}

	/**
	 * 查询自主学习统计、走势、对比、排行等数据
	 * @param vo
	 * @return
	 */
	@RequestMapping("queryActiveLearningAnalysePageData")
	@ResponseBody
	public JsonResult queryActiveLearningAnalysePageData(RequestVo vo) {
		JsonResult result = new JsonResult();
		ReturnResultBean resultBean = activeLearningAnalyseService.queryActiveLearningAnalysePageData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 走势
	 * @param vo
	 * @return
	 */
	@RequestMapping("queryActiveLearningTrendData")
	@ResponseBody
	public JsonResult queryActiveLearningTrendData(RequestVo vo) {
		JsonResult result = new JsonResult();
		ReturnResultBean resultBean = activeLearningAnalyseService.queryActiveLearningTrendData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 对比
	 * @param vo
	 * @return
	 */
	@RequestMapping("queryActiveLearningCompareData")
	@ResponseBody
	public JsonResult queryActiveLearningCompareData(RequestVo vo) {
		JsonResult result = new JsonResult();
		ReturnResultBean resultBean = activeLearningAnalyseService.queryActiveLearningCompareData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "queryActiveLearningDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryActiveLearningDetailDataPage(RequestVo vo, Page page) {
		JsonResult result = new JsonResult();
		List<ActiveLearningDetailBean> detailMaps = activeLearningAnalyseService.queryActiveLearningDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		result.addDatas("page", page);
		return result;
	}


	/**
	 * 自主学习分析导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "exportActiveLearningDtlData", method = RequestMethod.GET)
	public void exportActiveLearningDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitlePro(vo);
		String title = titleArr[0] + "自主学习明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"班级", "姓名", "学习知识点数", "刷题数", "刷题正确率"};
		String[] fieldNames = {"className", "studentName", "learningNum", "questionNum", "rightPro"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<ActiveLearningDetailBean> detailMaps = activeLearningAnalyseService.exportActiveLearningDtlData(vo);
		new ExportExcelForTable<ActiveLearningDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);
	}

}
