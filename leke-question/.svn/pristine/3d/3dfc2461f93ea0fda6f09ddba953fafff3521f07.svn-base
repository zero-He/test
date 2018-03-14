/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.Press;
import cn.strong.leke.question.core.base.cmd.IStageSubjectPressHandler;
import cn.strong.leke.question.core.base.query.IStageSubjectPressQueryService;
import cn.strong.leke.question.model.base.StageSubjectPress;
import cn.strong.leke.question.service.IPressService;

/**
 *
 * 描述:
 *
 * @author  C.C
 * @created 2014-4-29 下午4:40:26
 * @since   v1.0.0
 */
@Controller
@RequestMapping("/auth/admin/press/*")
public class PressController {
	
	@Resource
	public IPressService iPressService;
	
	@Resource
	public IStageSubjectPressQueryService stageSubjectPressQueryService;
	@Resource
	public IStageSubjectPressHandler stageSubjectPressHandler;

	@RequestMapping("pressList") 
	public void pressList() {
	}
	
	@RequestMapping("loadPressList")
	@ResponseBody
	public JsonResult loadPressList() {
		JsonResult json = new JsonResult();
		List<Press> pressList = iPressService.findPresses();
		json.addDatas("pressList", pressList);
		return json;
	}
	
	@RequestMapping("addPress")
	@ResponseBody
	public JsonResult addPress(String pressName, String nickName) {
		JsonResult json = new JsonResult();
		Press press = new Press();
		press.setPressName(pressName);
		press.setNickName(nickName);
		Long userId = UserContext.user.getUserId();
		press.setCreatedBy(userId);
		press.setModifiedBy(userId);
		Long pressId = iPressService.addPress(press);
		json.addDatas("pressId", pressId);
		json.addDatas("resultMessage", "添加成功");
		return json;
	}
	
	@RequestMapping("updatePress")
	@ResponseBody
	public JsonResult updatePress(Long pressId, String pressName, String nickName) {
		JsonResult json = new JsonResult();
		Press press = new Press();
		press.setPressId(pressId);
		press.setPressName(pressName);
		press.setNickName(nickName);
		press.setModifiedBy(UserContext.user.getUserId());
		iPressService.updatePress(press);
		json.addDatas("resultMessage", "更新成功");
		return json;
	}
	
	@RequestMapping("deletePress")
	@ResponseBody
	public JsonResult deletePress(Long pressId) {
		JsonResult json = new JsonResult();
		iPressService.deletePress(pressId, UserContext.user.get());
		json.addDatas("resultMessage", "删除成功");
		return json;
	}
	
	@RequestMapping("findAllPress")
	@ResponseBody
	public JsonResult findAllPress() {
		JsonResult json = new JsonResult();
		List<Press> pressList = iPressService.findPresses();
		json.addDatas("pressList", pressList);
		return json;
	}

	@RequestMapping("pressSet")
	public void pressSet() {

	}

	@ResponseBody
	@RequestMapping("findStageSubjectPress")
	public JsonResult findStageSubjectPress() {
		JsonResult jResult = new JsonResult();
		List<StageSubjectPress> list = stageSubjectPressQueryService.findAll();
		jResult.addDatas("stageSubjectPress", list);
		return jResult;
	}

	@ResponseBody
	@RequestMapping("doSaveStageSubjectPress")
	public JsonResult doSaveStageSubjectPress(String dataJson) {
		List<StageSubjectPress> assocs = JsonUtils.readList(dataJson, StageSubjectPress.class);
		stageSubjectPressHandler.insertList(assocs);
		return new JsonResult();
	}

}
