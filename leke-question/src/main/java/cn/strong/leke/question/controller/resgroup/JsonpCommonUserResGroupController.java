package cn.strong.leke.question.controller.resgroup;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.model.question.UserResGroup;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.service.IUserResGroupService;

import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller
@RequestMapping("/auth/common/resgroup/jsonp")
public class JsonpCommonUserResGroupController {
	@Resource
	private IUserResGroupService userResGroupService;

	@ResponseBody
	@RequestMapping("addUserResGroup")
	public JSONPObject addUserResGroup(UserResGroup userResGroup, String callback) {
		return jsonp(callback, json -> {
			// 添加分组的名称
			User user = UserContext.user.get();
			userResGroupService.insertUserResGroup(userResGroup, user);
				json.addDatas("userResGroup", userResGroup);
		});
	}


	@ResponseBody
	@RequestMapping("findUserResGroup")
	public JSONPObject findUserResGroup(Integer resType, String callback) {
		return jsonp(callback, json -> {
			Long userId = UserContext.user.getUserId();
			List<UserResGroup> groups = userResGroupService.findURGroupByUserIdAndResType(
					userId, resType);
			json.addDatas("groups", groups);
		});
	}

	@ResponseBody
	@RequestMapping("updateUserGroup")
	public JSONPObject updateUserGroup(UserResGroup userResGroup, String callback) {
		return jsonp(callback, json -> {
			// 修改分组的名称
			User user = UserContext.user.get();
			userResGroupService.updateURGroupName(userResGroup, user);
		});
	}
}