/**
 * 
 */
package cn.strong.leke.monitor.core.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.core.office.ExportExcelForTable;
import cn.strong.leke.monitor.core.model.ExportCourseSingleDto;
import cn.strong.leke.monitor.mongo.course.model.CourseSingle;
import cn.strong.leke.monitor.util.FileUtils;

/**
 * 课程帮助类
 * 
 * @author liulongbiao
 *
 */
public class CourseHelper {

	/**
	 * 导出单课统计详情
	 * 
	 * @param items
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportCourseSingleStats(List<CourseSingle> items,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ExportCourseSingleDto> list = CourseHelper.toExportCourseSingleDtos(items);
		String[] headers = { "课程名称", "学科", "课堂名称", "上课开始时间", "上课结束时间", "授课老师", "实到人次", "应到人次",
				"到课率" };
		String[] fieldNames = { "clazzName", "subjectName", "csName", "startTimeStr", "endTimeStr",
				"teacherName", "actualStuCount", "expectStuCount", "ratioCounts" };
		String pattern = "yyyy-MM-dd HH:mm";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String title = "单课详情统计";
		String fileName = FileUtils.getEncodingFileName(title + ".xls",
				request.getHeader("user-agent"));
		response.setHeader("Content-disposition", "attachment;" + fileName);
		new ExportExcelForTable<ExportCourseSingleDto>().exportExcel(title,
				headers, fieldNames, list, response.getOutputStream(), pattern);
	}

	/**
	 * 将单课列表转换为 DTO
	 * 
	 * @param items
	 * @return
	 */
	private static List<ExportCourseSingleDto> toExportCourseSingleDtos(List<CourseSingle> items) {
		Collections.sort(items, Comparator.comparing(CourseSingle::getStartTime));
		return ListUtils.map(items, CourseHelper::toExportCourseSingleDto);
	}

	private static final String FMT_TIME = "HH:mm";

	private static final String formatTime(Date date) {
		if (date == null) {
			return "";
		}
		return DateUtils.format(date, FMT_TIME);
	}

	/**
	 * 将单课转换为DTO
	 * 
	 * @param source
	 * @return
	 */
	private static ExportCourseSingleDto toExportCourseSingleDto(CourseSingle source) {
		ExportCourseSingleDto result = new ExportCourseSingleDto();
		result.setClazzName(source.getClazzName());
		result.setCsName(source.getCsName());
		result.setSubjectName(source.getSubjectName());
		result.setStartTimeStr(formatTime(source.getStartTime()));
		result.setEndTimeStr(formatTime(source.getEndTime()));
		result.setTeacherName(source.getTeacherName());
		result.setActualStuCount(source.getActualStuCount());
		result.setExpectStuCount(source.getExpectStuCount());
		if (source.getExpectStuCount() == 0) {
			result.setRatioCounts("--");
		} else {
			double tatio = ((source.getActualStuCount() * 1.0) / (source.getExpectStuCount() * 1.0) * 100);
			result.setRatioCounts(String.format("%.2f", tatio) + "%");
		}
		return result;
	}
}
