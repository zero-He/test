package cn.strong.leke.diag.controller.teachingMonitor;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.resource.ResourceDetailBean;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.diag.service.teachingMonitor.ResourceXService;
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
 * 资源统计控制器
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-21 15:47:13
 */
@Controller
@RequestMapping("/auth/provost/teachingMonitor/resource")
public class ResourceXController {

	protected static final Logger logger = LoggerFactory.getLogger(ResourceXController.class);

	@Resource
	private ResourceXService resourceXService;
	@Resource
	private CommService commService;

	/**
	 * 跳转到资源概览页
	 * @return
	 */
	@RequestMapping(value = "/resourcePage", method = RequestMethod.GET)
	public String resourceList() {
		return "auth/teachingMonitor/resourcePage";
	}

	/**
	 * 根据筛选条件查询资源的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryResourcePageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryResourcePageData(RequestVo vo) {
		JsonResult result = new JsonResult();
		ReturnResultBean resultBean = null;
		try {
			resultBean = resourceXService.queryResourcePageData(vo);
			result.addDatas("resultBean", resultBean);
		} catch (Exception e) {
			logger.error("resource select data error");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryResourceDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryResourceDetailDataPage(RequestVo vo, Page page) {
		JsonResult json = new JsonResult();
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("createCount");
			vo.setOrderType("desc");
		}
		List<ResourceDetailBean> detailMaps = resourceXService.queryResourceDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 资源导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportResourceDtlData", method = RequestMethod.GET)
	public void exportResourceDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "资源明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "创建总数", "课件（创建）", "微课（创建）", "试卷（创建）", "习题（创建）", "备课包（创建）", "分享总数", "课件（分享）", "微课（分享）", "试卷（分享）", "习题（分享）", "备课包（分享）"};
		String[] fieldNames = {"teacherName", "subjectName", "createCount", "createCoursewareCount", "createMicrocourseCount", "createPaperCount", "createQuestionCount", "createBeikePkgCount",
				"shareCount", "shareCoursewareCount", "shareMicrocourseCount", "sharePaperCount", "shareQuestionCount", "shareBeikePkgCount"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<ResourceDetailBean> detailMaps = resourceXService.queryResourceDetailDataList(vo);
		new ExportExcelForTable<ResourceDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);

	}
}
