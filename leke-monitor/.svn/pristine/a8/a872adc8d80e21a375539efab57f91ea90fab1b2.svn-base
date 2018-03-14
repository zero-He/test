/**
 * 
 */
package cn.strong.leke.monitor.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 * 
 * @author liulongbiao
 *
 */
public class HttpRequestUtils {

	private static final String IP_UNKNOWN = "unknown";

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		if (request == null) {
			return IP_UNKNOWN;
		}
		String ip = getFromXForwardedFor(request.getHeader("X-Forwarded-For"));
		if (isUnknown(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		return isUnknown(ip) ? request.getRemoteAddr() : ip;
	}

	private static String getFromXForwardedFor(String forwarded) {
		if (forwarded == null) {
			return null;
		}
		String[] ips = forwarded.split(",");
		for (int index = 0; index < ips.length; index++) {
			String strIp = (String) ips[index];
			if (!("unknown".equalsIgnoreCase(strIp))) {
				return strIp;
			}
		}
		return null;
	}

	private static boolean isUnknown(String ip) {
		return ip == null || ip.isEmpty() || ip.equals(IP_UNKNOWN);
	}

	private HttpRequestUtils() {
		// 工具类
	}

}
