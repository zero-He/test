package cn.strong.leke.monitor.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.strong.leke.common.utils.StringUtils;

/**
 *
 * 描述:文件名称格式化伪实现
 *
 * @author raolei
 * @created 2015年11月4日 上午9:31:49
 * @since v1.0.0
 */
public class FileUtils {
	public static String getEncodingFileName(String fileName, String userAgent) {
		String name = null;
		try {
			name = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			name = fileName;
		}
		if (StringUtils.isNotEmpty(userAgent)) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("opera") != -1) {
				name = "filename*=UTF-8''" + name;
			} else if (userAgent.indexOf("msie") != -1
					|| (userAgent.indexOf("rv:") != -1 && userAgent
							.indexOf("firefox") == -1)) {
				name = "filename=\"" + name + "\"";
			} else if (userAgent.indexOf("mozilla") != -1) {
				try {
					name = "filename=\""
							+ new String(fileName.getBytes("UTF-8"),
									"ISO-8859-1") + "\"";
				} catch (UnsupportedEncodingException e) {
					name = "filename=\"" + name + "\"";
				}
			} else {
				name = "\"filename=" + name + "\"";
			}
		} else {
			name = "\"filename=" + name + "\"";
		}
		return name;
	}
}
