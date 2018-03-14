/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：OfficialTagController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-22
 */
package cn.strong.leke.question.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.OfficialTag;
import cn.strong.leke.question.service.IOfficialTagService;

/**
 * 正式题库标签控制器
 * 
 * @author lavender
 * @version Avatar
 */
@Controller
@RequestMapping("/auth/admin/officialTag/*")
public class OfficialTagController {

	@Resource
	private IOfficialTagService officialTagService;

	@RequestMapping("ajax/addOfficialTag")
	@ResponseBody
	public JsonResult addOfficialTag(String officialTagName) {
		JsonResult json = new JsonResult();
		OfficialTag officialTag = new OfficialTag();
		officialTag.setOfficialTagName(officialTagName);
		Long userId = UserContext.user.getUserId();
		officialTag.setCreatedBy(userId);
		officialTag.setModifiedBy(userId);
		officialTagService.addOfficialTag(officialTag);
		return json;
	}

	@RequestMapping("ajax/updateOfficialTag")
	@ResponseBody
	public JsonResult updateOfficialTag(String officialTagName, Long officialTagId) {
		JsonResult json = new JsonResult();
		OfficialTag tag = new OfficialTag();
		tag.setOfficialTagId(officialTagId);
		tag.setOfficialTagName(officialTagName);
		tag.setModifiedBy(UserContext.user.getUserId());
		officialTagService.updateOfficialTag(tag);
		return json;
	}

	@RequestMapping("ajax/deleteOfficialTag")
	@ResponseBody
	public JsonResult deleteOfficialTag(Long officialTagId) {
		officialTagService.deleteOfficialTag(officialTagId);
		return new JsonResult();
	}

	@RequestMapping("officialTagList")
	public void officialTagList() {

	}

	@RequestMapping("ajax/findOfficialTagList")
	@ResponseBody
	public JsonResult findOfficialTagList() {
		JsonResult json = new JsonResult();
		List<OfficialTag> officialTagList = officialTagService.findOfficialTagList();

		json.addDatas("officialTagList", officialTagList);
		return json;
	}
}
