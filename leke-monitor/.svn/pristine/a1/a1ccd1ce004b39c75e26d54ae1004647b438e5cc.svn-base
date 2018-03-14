/**
 * 
 */
package cn.strong.leke.monitor.controller.online;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.user.RoleSchool;
import cn.strong.leke.model.user.UserBase;

/**
 * 设备登录统计控制器
 * 借助AccessStatHandler进行数据采集
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/api/*")
public class DeviceLoginStatController {

	protected static final Logger logger = LoggerFactory.getLogger(DeviceLoginStatController.class);

	@RequestMapping("w/loginStat")
	@ResponseBody
	public JsonResult loginStat(HttpServletRequest request) {
		JsonResult json = new JsonResult();
		Long userId = null;

		try {
			String identify = request.getAttribute("userId") + "";
			userId = Long.parseLong(identify);
			UserBase user = UserBaseContext.getUserBaseByUserId(userId);
			List<RoleSchool> list = user.getRoleSchoolList();
			if (CollectionUtils.isNotEmpty(list)) {
				RoleSchool rs = list.get(0);
				request.setAttribute("roleId", rs.getRoleId());
				request.setAttribute("schoolId", rs.getSchoolId());
			}
		} catch (Exception e) {
			String message = "Ticket is error for device login stat api. Detail info :" + e.getMessage();
			json.setErr(message);
			logger.error(message, e);
		}
		return json;
	}
}
