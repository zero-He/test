/**
 * 
 */
package cn.strong.leke.monitor.controller.online;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.core.service.online.IWebOnlineUserService;
import cn.strong.leke.monitor.mongo.online.model.OnlineUser;
import cn.strong.leke.monitor.mongo.online.model.query.OnlineUserQuery;

/**
 * 在线用户管理控制器
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/technicalSupport/online")
public class OnlineUserController {
	@Autowired
	private IWebOnlineUserService onlineService;

	@RequestMapping("total")
	@ResponseBody
	public JsonResult total() {
		long total = onlineService.getCurrentOnlineUserCount();
		JsonResult json = new JsonResult();
		json.addDatas("total", total);
		return json;
	}

	@RequestMapping("userListByOnline")
	public void userListByOnline() {
	}
	@RequestMapping("users")
	@ResponseBody
	public JsonResult users(OnlineUserQuery query, Page page) {
		List<OnlineUser> users = onlineService.queryOnlineUsers(query, page);
		query.setTs(new Date());
		JsonResult json = new JsonResult();
		json.addDatas("total", onlineService.getCurrentOnlineUserCount());
		page.setDataList(users);
		json.addDatas("page", page);
		return json;
	}
}
