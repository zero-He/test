package cn.strong.leke.diag.controller.teachingMonitor;

import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.attendance.AttendanceDetailBean;
import cn.strong.leke.diag.service.teachingMonitor.AttendanceXService;
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
 * 考勤统计控制器
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-17 14:06:21
 */
@Controller
@RequestMapping("/auth/provost/teachingMonitor/attendance")
public class AttendanceXController {

	protected static final Logger logger = LoggerFactory.getLogger(AttendanceXController.class);

	@Resource
	private AttendanceXService attendanceXService;
	@Resource
	private CommService commService;

	/**
	 * 跳转到考勤概览页
	 * @return
	 */
	@RequestMapping(value = "/attendancePage", method = RequestMethod.GET)
	public String attendancePage() {
		return "auth/teachingMonitor/attendancePage";
	}

	/**
	 * 根据筛选条件查询考勤的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryAttendancePageData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryAttendancePageData(RequestVo vo) {
		ReturnResultBean resultBean = attendanceXService.queryAttendancePageData(vo);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 根据条件筛选考勤走势数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryAttendanceTrendData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryAttendanceTrendData(RequestVo vo) {
		ReturnResultBean resultBean = attendanceXService.queryAttendanceTrendData(vo, false);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 根据条件筛选考勤对比数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/queryAttendanceCompareData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryAttendanceCompareData(RequestVo vo) {
		ReturnResultBean resultBean = attendanceXService.queryAttendanceCompareData(vo, false);
		return new JsonResult().addDatas("resultBean", resultBean);
	}

	/**
	 * 明细分页请求
	 * @param vo
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryAttendanceDetailDataPage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryAttendanceDetailDataPage(RequestVo vo, Page page) {
		JsonResult json = new JsonResult();
		List<AttendanceDetailBean> detailMaps = attendanceXService.queryAttendanceDetailDataPage(vo, page);
		page.setDataList(detailMaps);
		json.addDatas("page", page);
		return json;
	}

	/**
	 * 考勤导出
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportAttendanceDtlData", method = RequestMethod.GET)
	public void exportAttendanceDtlData(RequestVo vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");

		String agent = request.getHeader("user-agent");
		String[] titleArr = commService.handleExcelTitle(vo);
		String title = titleArr[0] + "考勤明细" + titleArr[1];
		String fileName = FileUtils.getEncodingFileName(title + ".xls", agent);
		String[] headers = {"姓名", "学科", "全勤", "迟到", "早退", "迟到且早退", "缺勤"};
		String[] fieldNames = {"teacherName", "subjectName", "allOn", "late", "early", "lateAndEarly", "notClassNum"};
		StringBuilder sb = new StringBuilder();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());

		List<AttendanceDetailBean> detailMaps = attendanceXService.queryAttendanceDetailData(vo);
		new ExportExcelForTable<AttendanceDetailBean>().exportExcel(title, headers, fieldNames, detailMaps, response.getOutputStream(), null);

	}


}
