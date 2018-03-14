package cn.strong.leke.diag.controller.teachingMonitor;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.EvaluateDetailBean;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.EvaluateXService;
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
 * 课堂评价统计控制器
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-18 11:33:43
 */
@Controller
@RequestMapping("/auth/provost/teachingMonitor/evaluate")
public class EvaluateXController {

	protected static final Logger logger = LoggerFactory.getLogger(EvaluateXController.class);

	@Resource
	private EvaluateXService evaluateXService;
	@Resource
	private CommService commService;

	/**
	 * 跳转到课堂评价概览页
	 * @return
	 */
	@RequestMapping(value = "/evaluatePage", method = RequestMethod.GET)
	public String evaluatePage() {
		return "auth/teachingMonitor/evaluatePage";
	}

	/**
	 * 根据筛选条件查询课堂评价的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryEvaluatePageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryEvaluatePageData(RequestVo vo) {
		ReturnResultBean resultBean = null;
		try {
			resultBean = evaluateXService.queryEvaluatePageDatas(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult().addDatas("resultBean", resultBean);
	}


	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryEvaluateDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryEvaluateDetailDataPage(RequestVo vo, Page page) {
		JsonResult json = new JsonResult();
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("lessonNum");
			vo.setOrderType("desc");
		}
		List<EvaluateDetailBean> detailMaps = evaluateXService.queryEvaluateDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 课堂评价导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportEvaluateDtlData", method = RequestMethod.GET)
	public void exportEvaluateDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "课堂评价明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "上课堂数", "评价得分", "好评数", "好评率", "中评数", "中评率", "差评数", "差评率", "鲜花"};
		String[] fieldNames = {"teacherName", "subjectName", "lessonNum", "totalLevel", "good", "goodPro", "center", "centerPro", "poor", "poorPro", "flowerNum"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<EvaluateDetailBean> detailMaps = evaluateXService.queryEvaluateDetailPartData(vo);
		new ExportExcelForTable<EvaluateDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);

	}


}
