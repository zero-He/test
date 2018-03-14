package cn.strong.leke.homework.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.homework.manage.ExerciseService;
import cn.strong.leke.homework.model.activity.SimpleExercise;
import cn.strong.leke.homework.util.HomeworkExcel;

@Controller
@RequestMapping("/auth/common/download/*")
public class CommonDownLoadController {
	private static Logger LOG = LoggerFactory.getLogger(CommonDownLoadController.class);

	private static final String DATE_FORMAT = "yyyyMMdd HH:mm:ss";
	private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
		@Override
		public DateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT);
		}
	};

	@Resource
	private ExerciseService exerciseService;

	@RequestMapping("down")
	public void down(Long clazzId, String beg, String end, HttpServletResponse response,
			HttpServletRequest request)
			throws ParseException {
		if (clazzId == null) {
			clazzId = 561L;
		}
		Date minDateTime = DF.get().parse(beg + " 00:00:00");
		Date maxDateTime = DF.get().parse(end + " 23:59:59");
		List<SimpleExercise> records = exerciseService.findStundent(clazzId, minDateTime, maxDateTime);
		try (HSSFWorkbook wb = new HSSFWorkbook(); OutputStream out = response.getOutputStream()) {
			HomeworkExcel.append(wb, records);
			String filename = "练习排行" + ".xls";
			setDownloadHeaders(request, response, filename);
			wb.write(out);
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
			Validation.throwValidException("导出Excel文档失败");
		}

	}

	/**
	 * 设置下载响应头
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 */
	private void setDownloadHeaders(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String ua = request.getHeader("User-Agent");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;" + getAttachmentName(fileName, ua));
	}

	private static String getAttachmentName(String fileName, String ua) {
		String encoded = encodeURLComponent(fileName);
		if (StringUtils.isEmpty(ua)) {
			return "\"filename=" + encoded + "\"";
		}
		ua = ua.toLowerCase();
		if (ua.contains("opera")) {
			return "filename*=UTF-8''" + encoded;
		}
		if (ua.contains("msie")) {
			return "filename=\"" + encoded + "\"";
		}
		if (ua.contains("mozilla")) {
			return "filename=\"" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1) + "\"";
		}
		return "\"filename=" + encoded + "\"";
	}

	private static String encodeURLComponent(String input) {
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return input;
		}
	}

}
