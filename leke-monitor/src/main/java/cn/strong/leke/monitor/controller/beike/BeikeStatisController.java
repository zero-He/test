package cn.strong.leke.monitor.controller.beike;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.monitor.core.model.BeikeStatisDTO;
import cn.strong.leke.monitor.core.service.IBeikeStatisService;
import cn.strong.leke.monitor.core.utils.ExportExcelForTable;
import cn.strong.leke.monitor.util.FileUtils;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

/**
 * 全网备课统计
 * 
 * @Description
 * @author Deo
 * @createdate 2016年11月16日 上午11:58:32
 */
@Controller
@RequestMapping("/auth/common/beike/")
public class BeikeStatisController
{
	@Resource
	private IBeikeStatisService beikeStatisService;

	/**
	 * 全网备课统计
	 */
	@RequestMapping("allbeikeStatisView")
	public void allbeikeStatisView()
	{

	}

	/**
	 * 加载全网备课数据
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping("getAllbeikeData")
	@ResponseBody
	public JsonResult getAllbeikeData(BeikeStatisDTO query, Page page)
	{
		JsonResult result = new JsonResult();
		BeikeStatisDTO data = beikeStatisService.getAllbeikeData(query);
		result.addDatas("record", data);
		result.addDatas("page", page);
		return result;
	}

	/**
	 * 学校历史备课统计
	 */
	@RequestMapping("schoolbeikeStatisView")
	public void schoolbeikeStatisView(BeikeStatisDTO query, Model model)
	{
		model.addAttribute("query", query);
	}

	/**
	 * 加载学校历史备课数据
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping("getSchoolbeikeData")
	@ResponseBody
	public JsonResult getSchoolbeikeData(BeikeStatisDTO query, Page page)
	{
		JsonResult result = new JsonResult();
		List<BeikeStatisDTO> data = beikeStatisService.getSchoolbeikeList(query, page);
		result.addDatas("record", data);
		result.addDatas("page", page);
		return result;
	}

	/**
	 * 年级学科备课统计
	 */
	@RequestMapping("gradebeikeStatisView")
	public void gradebeikeStatisView(BeikeStatisDTO query, Long schoolId, String schoolName, Model model)
	{
		model.addAttribute("query", query);
	}

	/**
	 * 年级学科备课统计
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping("getGradebeikeData")
	@ResponseBody
	public JsonResult getGradebeikeData(BeikeStatisDTO query, Page page)
	{
		JsonResult result = new JsonResult();
		List<BeikeStatisDTO> data = beikeStatisService.getGradebeikeList(query, page);
		result.addDatas("record", data);
		result.addDatas("page", page);
		return result;
	}

	/**
	 * 根据学校ID获得年级学科
	 */
	@RequestMapping(value = "getGradeSubjectList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getGradeSubjectList(Long schoolId)
	{
		JsonResult json = new JsonResult();
		School school = SchoolContext.getSchoolBySchoolId(schoolId);
		List<GradeRemote> gradeList = new ArrayList<GradeRemote>();
		List<SubjectRemote> subjectList = new ArrayList<SubjectRemote>();
		for (Long schoolStageId : school.getSchoolStageIds())
		{
			SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStage(schoolStageId);
			if (schoolStage != null)
			{
				gradeList.addAll(schoolStage.getGrades());
				List<Long> haslist = subjectList.stream().map(t -> t.getSubjectId()).collect(Collectors.toList());
				for (SubjectRemote item : schoolStage.getSubjects())
				{
					if (!haslist.contains(item.getSubjectId()))
					{
						subjectList.add(item);
					}
				}

			}
		}
		json.addDatas("gradeList", gradeList);
		json.addDatas("subjectList", subjectList);
		return json;
	}

	/**
	 * 导出数据
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("exportExcelData")
	@ResponseBody
	public void exportExcelData(String action, String dataJson, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BeikeStatisDTO query = JsonUtils.fromJSON(dataJson, BeikeStatisDTO.class);
		List<BeikeStatisDTO> list = new ArrayList<BeikeStatisDTO>();

		String title = "";
		int i = 0;
		String[] tmpheaders =
		{ "应上课堂数", "备课课堂数", "备课率", "课件绑定率", "作业绑定率","备课包:上传,引用,编辑引用,占比", 
		  "课件(课前):上传,引用,编辑引用,占比", "作业(课前):上传,引用,编辑引用,占比","微课(课前):上传,引用,占比" , 
		  "课件(课中):上传,引用,编辑引用,占比", "作业(课中):上传,引用,编辑引用,占比",
		  "作业(课后):上传,引用,编辑引用,占比", "微课(课后):上传,引用,占比" };
		String[] tmpfieldNames =
		{ "mustLessons", "beikeLessons", "beikeRate", "coursewareRate", "homeWorkRate",
		   "bkgUpNums","bkgQuoteNums", "bkgEditNums", "bkgRate", 
		   "beforeCwUpNums", "beforeCwQuoteNums", "beforeCwEditNums","beforeCwRate", "beforeHwUpNums", 
		   "beforeHwQuoteNums", "beforeHwEditNums", "beforeHwRate","beforeWkUpNums", "beforeWkQuoteNums", "beforeWkRate", 
		   "inClassCwUpNums", "inClassCwQuoteNums","inClassCwEditNums", "inClassCwRate", 
		   "inClassHwUpNums", "inClassHwQuoteNums", "inClassHwEditNums", "inClassHwRate",  
		   "afterHwUpNums", "afterHwQuoteNums", "afterHwEditNums", "afterHwRate", "afterWkUpNums", "afterWkQuoteNums", "afterWkRate" };
		if (action.equals("grade"))
		{
			i = 2;
		} else
		{
			i = 1;
		}
		String[] headers = new String[tmpheaders.length + i];
		String[] fieldNames = new String[tmpfieldNames.length + i];
		if (action.equals("all"))
		{
			headers[0] = "学校数";
			fieldNames[0] = "schoolNums";
			list.add(beikeStatisService.getAllbeikeData(query));
			title = "全网备课数据统计";
		} else if (action.equals("school"))
		{
			headers[0] = "学校名称";
			fieldNames[0] = "schoolName";
			list = beikeStatisService.getSchoolbeikeList(query, null);
			title = "学校备课数据统计";
		} else if (action.equals("grade"))
		{
			headers[0] = "年级";
			fieldNames[0] = "gradeName";
			headers[1] = "学科";
			fieldNames[1] = "subjectName";
			list = beikeStatisService.getGradebeikeList(query, null);
			title = "年级学科备课数据统计";
		}

		for (int j = i; j < headers.length; j++)
		{
			headers[j] = tmpheaders[j - i];
		}

		for (int j = i; j < fieldNames.length; j++)
		{
			fieldNames[j] = tmpfieldNames[j - i];
		}

		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String agent = request.getHeader("user-agent");
		StringBuffer titleSingle = new StringBuffer("");
		titleSingle.append(title);
		String fileName = FileUtils.getEncodingFileName(titleSingle.toString() + ".xls", agent);
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;").append(fileName);
		response.setHeader("Content-disposition", sb.toString());
		new ExportExcelForTable<BeikeStatisDTO>().exportExcel(titleSingle.toString(), headers, fieldNames, list, response.getOutputStream(), pattern);
	}
}
