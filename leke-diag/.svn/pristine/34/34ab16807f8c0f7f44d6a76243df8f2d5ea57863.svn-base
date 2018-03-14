package cn.strong.leke.diag.controller.studentMonitor;

import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.studentMonitor.OtherDetailBean;
import cn.strong.leke.diag.model.studentMonitor.OtherReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.service.studentMonitor.OtherAnalyseService;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
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
 * 学生行为分析-其它
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-30 16:20:38
 */
@Controller
@RequestMapping("/auth/provost/studentMonitor/otherAnalyse")
public class OtherAnalyseController {

	@Resource
	private CommService commService;
	@Resource
	private OtherAnalyseService otherAnalyseService;

	/**
	 * 跳转到other分析页
	 * @return
	 */
	@RequestMapping(value = "otherAnalysePage", method = RequestMethod.GET)
	public String otherAnalysePage() {
		return "auth/studentMonitor/otherAnalysePage";
	}

	/**
	 * 查询other统计、走势、对比、排行等数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "queryOtherAnalysePageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryOtherAnalysePageData(RequestVo vo) {
		OtherReturnResultBean resultBean = otherAnalyseService.queryOtherAnalysePageData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "queryOtherDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryOtherDetailDataPage(RequestVo vo, Page page) {
		JsonResult result = new JsonResult();
		List<OtherDetailBean> detailMaps = otherAnalyseService.queryOtherDetailDataPage(vo, page);

		page.setDataList(detailMaps);
		result.addDatas("page", page);
		return result;
	}


	/**
	 * 其它分析导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "exportOtherDtlData", method = RequestMethod.GET)
	public void exportOtherDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		vo.setSubjectId(-1L);
		String[] titleArr = commService.handleExcelTitlePro(vo);
		String title = titleArr[0] + "其它明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"班级", "姓名", "消灭/新增错题数", "消灭错题率", "提问数/解答数", "问题解答率", "查看课堂录像数", "每次查看录像时长", "录像查看率", "创建笔记数", "平均每份笔记查看次数"};
		String[] fieldNames = {"className", "studentName", "graspNum", "graspPro", "askSolveSum", "askSolvePro", "lessonNum", "avgTimeLong", "viewSeePro", "createBookNum", "avgReadNum"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<OtherDetailBean> detailMaps = otherAnalyseService.queryOtherDetailData(vo);
		new ExportExcelForTable<OtherDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);
	}

}
