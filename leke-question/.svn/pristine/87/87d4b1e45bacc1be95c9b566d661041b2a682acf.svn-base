/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.question.service.KnowledgeService;

/**
 * 
 * 描述: 知识点维护控制器
 * 
 * @author liulb
 * @created 2014年4月25日 上午11:20:46
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/admin/knowledge")
public class KnowledgeController {

	@Autowired
	private KnowledgeService knowledgeService;

	@RequestMapping("knowledgeList")
	public void knowledgeList() {
		// view
	}

	@RequestMapping("knowledgeRoots")
	@ResponseBody
	public JsonResult knowledgeListData(Knowledge query) {
		JsonResult json = new JsonResult();
		query.setParentId(TreeCst.TREE_ROOT_ID);
		List<Knowledge> knowledges = knowledgeService.queryKnowledges(query);
		json.addDatas("knowledges", knowledges);
		return json;
	}

	@RequestMapping("addRootKnowledge")
	@ResponseBody
	public JsonResult addRootKnowledge(Knowledge knowledge) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		knowledge.setCreatedBy(userId);
		knowledge.setModifiedBy(userId);
		knowledgeService.addKnowledgeRoot(knowledge);
		return json;
	}

	@RequestMapping("knowledgeTree")
	public void knowledgeTree(Knowledge query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("queryTreeNodes")
	@ResponseBody
	public List<Knowledge> queryTreeNodes(Knowledge knowledge) {
		if (knowledge == null) {
			return Collections.emptyList();
		}
		Knowledge query = new Knowledge();
		Long parentId = knowledge.getParentId();
		if (parentId == null || parentId.equals(TreeCst.TREE_ROOT_ID)) {
			query.setSchoolStageId(knowledge.getSchoolStageId());
			query.setSubjectId(knowledge.getSubjectId());
			query.setParentId(TreeCst.TREE_ROOT_ID);
		} else {
			query.setParentId(parentId);
		}
		return knowledgeService.queryKnowledges(query);
	}

	@RequestMapping("addKnowledge")
	@ResponseBody
	public JsonResult addKnowledge(Knowledge knowledge) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		knowledge.setCreatedBy(userId);
		knowledge.setModifiedBy(userId);
		knowledgeService.addKnowledge(knowledge);
		return json;
	}

	@RequestMapping("modifyKnowledge")
	@ResponseBody
	public JsonResult modifyKnowledge(Knowledge knowledge) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		knowledge.setModifiedBy(userId);
		knowledgeService.updateKnowledge(knowledge);
		return json;
	}

	@RequestMapping("deleteKnowledge")
	@ResponseBody
	public JsonResult deleteKnowledge(Knowledge knowledge) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		knowledge.setModifiedBy(userId);
		knowledgeService.deleteKnowledge(knowledge);
		return json;
	}
	
	@RequestMapping("moveUpKnowledge")
	@ResponseBody
	public JsonResult moveUpKnowledge(Knowledge knowledge) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		knowledge.setModifiedBy(userId);
		knowledgeService.moveUpNode(knowledge);
		return json;
	}
	
	@RequestMapping("moveDownKnowledge")
	@ResponseBody
	public JsonResult moveDownKnowledge(Knowledge knowledge) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		knowledge.setModifiedBy(userId);
		knowledgeService.moveDownNode(knowledge);
		return json;
	}
	
	@RequestMapping("rebuildTreeIndex")
	@ResponseBody
	public JsonResult rebuildTreeIndex(Long schoolStageId, Long subjectId) {
		knowledgeService.rebuildTreeIndexWithTx(schoolStageId, subjectId);
		return new JsonResult();
	}

}
