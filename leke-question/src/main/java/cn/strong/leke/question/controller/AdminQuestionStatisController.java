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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.question.model.QueKnowledgeTreeQuery;
import cn.strong.leke.question.model.StatisKnowledge;
import cn.strong.leke.question.model.StatisMaterialNode;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.question.service.MaterialService;

/**
 *
 *
 * @author  liulongbiao
 * @created 2014年12月19日 下午2:03:28
 * @since   v3.2.1
 */
@Controller
@RequestMapping("/auth/admin/question/statis")
public class AdminQuestionStatisController {

	@Autowired
	private MaterialService materialService;
	@Autowired
	private KnowledgeService knowledgeService;

	@RequestMapping("materialNodeStatis")
	@ResponseBody
	public List<StatisMaterialNode> materialNodeStatis(
			QueKnowledgeTreeQuery query) {
		if (query == null || query.getMaterialId() == null) {
			return Collections.emptyList();
		}
		return materialService.queryQueKnowledgeTreeNodes(query);
	}

	@RequestMapping("knowledgeNodeStatis")
	@ResponseBody
	public List<StatisKnowledge> knowledgeNodeStatis(QueKnowledgeTreeQuery query) {
		if (query == null || query.getSchoolStageId() == null
				|| query.getSubjectId() == null) {
			return Collections.emptyList();
		}
		return knowledgeService.queryQueKnowledgeTreeNodes(query);
	}
}
