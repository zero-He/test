/**
 * 
 */
package cn.strong.leke.monitor.controller.online;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.json.JSON;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.cas.utils.TicketUtils;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.core.model.DeviceHeartbeat;
import cn.strong.leke.monitor.core.model.OnlineHeartbeat;
import cn.strong.leke.monitor.core.service.online.IDeviceOnlineUserService;
import cn.strong.leke.monitor.core.service.online.IWebOnlineUserService;
import cn.strong.leke.monitor.util.HttpRequestUtils;

/**
 * 在线用户心跳包控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
public class OnlineUserHeartbeatController {
	@Resource
	private IWebOnlineUserService webOnlineService;
	@Resource
	private IDeviceOnlineUserService deviceOnlineService;

	@RequestMapping("/auth/common/online/heartbeat")
	@ResponseBody
	public JSONPObject heartbeat(String callback, HttpServletRequest request) {
		OnlineHeartbeat hb = new OnlineHeartbeat();
		hb.setTicket(TicketUtils.getTicket(request));
		hb.setUser(UserContext.user.get());
		hb.setUserAgent(request.getHeader("user-agent"));
		hb.setIp(HttpRequestUtils.getClientIp(request));

		return jsonp(callback, (json) -> {
			webOnlineService.saveHeartbeat(hb);
		});
	}

	@RequestMapping("/api/w/heartbeat")
	@ResponseBody
	public JsonResult deviceHeartbeat(String data, HttpServletRequest request) {
		DeviceHeartbeat hb = JSON.parse(data, DeviceHeartbeat.class);
		if (StringUtils.isEmpty(hb.getTicket())) {
			hb.setTicket(TicketUtils.getTicket(request));
		}
		hb.setIp(HttpRequestUtils.getClientIp(request));
		deviceOnlineService.saveHeartbeat(hb);
		return new JsonResult();
	}
}
