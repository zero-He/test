/**
 * 
 */
package cn.strong.leke.monitor.controller.online;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.monitor.core.map.MapCountry;
import cn.strong.leke.monitor.core.model.online.CityOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.CountryOnlineUserStatDTO;
import cn.strong.leke.monitor.core.model.online.ProvinceOnlineUserStatDTO;
import cn.strong.leke.monitor.core.service.online.IPlatformOnlineUserService;
import cn.strong.leke.monitor.core.service.stat.ISchoolUserStatService;

/**
 * 在线用户大屏监控
 * 
 * @author liulongbiao
 *
 */
@Controller
public class OnlineUserMonitorController {
	@Resource
	private IPlatformOnlineUserService platformOnlineUserService;
	@Resource
	private ISchoolUserStatService schoolUserStatService;

	@RequestMapping({ "/auth/platformAdmin/online/monitor", "/auth/devops/online/monitor",
			"/auth/seller/online/monitor", "/auth/technicalSupport/online/monitor", "/auth/operation/online/monitor" })
	public String monitor() {
		return "auth/common/online/monitor";
	}

	@RequestMapping("/auth/common/online/monitor/countryStat")
	@ResponseBody
	public JsonResult countryStat() {
		JsonResult result = new JsonResult();
		CountryOnlineUserStatDTO stat = platformOnlineUserService.getCountryStat(MapCountry.ID_CHINA);
		result.addDatas("stat", stat);
		return result;
	}

	@RequestMapping("/auth/common/online/monitor/provinceStat")
	@ResponseBody
	public JsonResult provinceStat(Long provinceId) {
		JsonResult result = new JsonResult();
		ProvinceOnlineUserStatDTO stat = platformOnlineUserService.getProvinceStat(provinceId);
		result.addDatas("stat", stat);
		return result;
	}

	@RequestMapping("/auth/common/online/monitor/cityStat")
	@ResponseBody
	public JsonResult cityStat(Long cityId) {
		JsonResult result = new JsonResult();
		CityOnlineUserStatDTO stat = platformOnlineUserService.getCityStat(cityId);
		result.addDatas("stat", stat);
		return result;
	}

	@RequestMapping("/auth/devops/initSchoolUserStats")
	@ResponseBody
	public JsonResult initSchoolUserStats() {
		schoolUserStatService.updateAll();
		return new JsonResult();
	}

}
