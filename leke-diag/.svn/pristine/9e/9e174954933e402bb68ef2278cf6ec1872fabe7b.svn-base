package cn.strong.leke.diag.controller.teachingMonitor;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.interact.InteractDetailBean;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.InteractXService;
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
import java.text.ParseException;
import java.util.List;

/**
 * 课堂互动统计控制器
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 15:59:53
 */
@Controller
@RequestMapping("/auth/provost/teachingMonitor/interact")
public class InteractXController {

	protected static final Logger logger = LoggerFactory.getLogger(InteractXController.class);

	@Resource
	private InteractXService interactXService;
	@Resource
	private CommService commService;


	/**
	 * 跳转到课堂互动概览页
	 * @return
	 */
	@RequestMapping(value = "/interactPage", method = RequestMethod.GET)
	public String interactPage() {
		return "auth/teachingMonitor/interactPage";
	}

	/**
	 * 根据筛选条件查询课堂互动的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryInteractPageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryInteractPageData(RequestVo vo) {
		JsonResult result = new JsonResult();
		ReturnResultBean resultBean = null;
		try {
			resultBean = interactXService.queryInteractPageData(vo);
			result.addDatas("resultBean", resultBean);
		} catch (ParseException e) {
			logger.error("互动数据获取错误");
		}
		return result;
	}

	/**
	 * 根据条件筛选走势数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryInteractTrendData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryInteractTrendData(RequestVo vo){
		JsonResult result = new JsonResult();
		ReturnResultBean resultBean = null;
		try {
			resultBean = interactXService.queryInteractTrendData(vo, null);
			result.addDatas("resultBean", resultBean);
		} catch (ParseException e) {
			logger.error("走势数据获取错误");
		}
		return result;
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryInteractDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryInteractDetailDataPage(RequestVo vo, Page page) {
		JsonResult json = new JsonResult();
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("lessonCount");
			vo.setOrderType("desc");
		}
		List<InteractDetailBean> detailMaps = interactXService.queryInteractDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 课堂互动导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportInteractDtlData", method = RequestMethod.GET)
	public void exportInteractDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "课堂互动明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "上课堂数", "总次数", "点名", "快速问答", "随堂作业", "分组讨论", "授权"};
		String[] fieldNames = {"teacherName", "subjectName", "lessonCount", "avgTotalCount", "avgCallNum", "avgQuickNum", "avgExamNum", "avgDiscuNum", "avgAuthedNum"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<InteractDetailBean> detailMaps = interactXService.queryInteractDetailData(vo);
		new ExportExcelForTable<InteractDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);

	}
}
