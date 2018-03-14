/* 
 * 包名: cn.strong.leke.question.controller
 *
 * 文件名：QuestionLogController.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: QW
 *
 * 日期：2016-6-6
 */
package cn.strong.leke.question.controller;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.question.OutlineContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.Outline;
import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.service.OutlineService;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;

@Controller
@RequestMapping("/auth/*")
public class SyllabusController {

	//--------------------------------------admin---------------------------------------------//	

	@Autowired
	private OutlineService outlineService;

	
	/**
	 *
	 * 描述: 教材大纲设置
	 *
	 * @author  QW
	 * @created 2016-5-28
	 */
	@RequestMapping("schoolResearcher/syllabus/outline/outlineTree")
	public void outlineTree(OutlineNode query, Model model) {
		//model.addAttribute("query", query);
		Outline outline = OutlineContext.getOutline(query.getOutlineId());
		model.addAttribute("outline", JsonUtils.toJSON(outline));
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/querySyllabusNodes")
	@ResponseBody
	public List<OutlineNode> querySyllabusNodes(OutlineNode outlineNode) {
		if (outlineNode == null) {
			return Collections.emptyList();
		}
		OutlineNode query = new OutlineNode();
		Long parentId = outlineNode.getParentId();
		if (parentId == null || parentId.equals(TreeCst.TREE_ROOT_ID)) {
			query.setOutlineId(outlineNode.getOutlineId());
			query.setParentId(TreeCst.TREE_ROOT_ID);
		} else {
			query.setParentId(parentId);
		}
		List<OutlineNode> outlineNodes = outlineService.queryOutlineNodes(query);
		return outlineNodes;
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/queryOutlineNodes")
	@ResponseBody
	public JsonResult queryOutlineNodes(OutlineNode outlineNode) {
		JsonResult json = new JsonResult();
		if (outlineNode == null) {
			return json;
		}
		OutlineNode query = new OutlineNode();
		Long parentId = outlineNode.getParentId();
		if (parentId == null || parentId.equals(TreeCst.TREE_ROOT_ID)) {
			query.setOutlineId(outlineNode.getOutlineId());
			query.setParentId(TreeCst.TREE_ROOT_ID);
		} else {
			query.setParentId(parentId);
		}
		List<OutlineNode> outlineNodes = outlineService.queryOutlineNodes(query);
		json.addDatas("outlineNodes", outlineNodes);
		return json;
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/queryChildren")
	@ResponseBody
	public JSONPObject queryChildren(OutlineNode outlineNode, String callback) {
		return jsonp(callback, json -> {
			if (outlineNode == null) {
				return;
			}
			OutlineNode query = new OutlineNode();
			Long parentId = outlineNode.getParentId();
			if (parentId == null || parentId.equals(TreeCst.TREE_ROOT_ID)) {
				query.setOutlineId(outlineNode.getOutlineId());
				query.setParentId(TreeCst.TREE_ROOT_ID);
			} else {
				query.setParentId(parentId);
			}
			List<OutlineNode> outlineNodes = outlineService.queryOutlineNodes(query);
			json.addDatas("outlineNodes", outlineNodes);
		});
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/querySchoolStageIdByGradeId")
	@ResponseBody
	public JsonResult querySchoolStageIdByGradeId(Outline query) {
		JsonResult json = new JsonResult();
		if (query.getGradeId() == null) {
			json.addDatas("outline", query);
			return json;
		}
		SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStageByGradeId(query.getGradeId());
		query.setSchoolStageId(schoolStage.getSchoolStageId());
		json.addDatas("outline", query);
		return json;
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/querySchoolStages")
	@ResponseBody
	public JsonResult querySchoolStages(Outline query) {
		JsonResult json = new JsonResult();
		List<SchoolStageRemote> schoolStages = new ArrayList<SchoolStageRemote>();
		School school = UserContext.user.getCurrentSchool();
		Long[] stageIds = school.getSchoolStageIds();
		if (stageIds != null && stageIds.length > 0) {
			for (Long stgId : stageIds) {
				SchoolStageRemote stg = SchoolStageContext
						.getSchoolStage(stgId);
				schoolStages.add(stg);
			}
		}
		json.addDatas("schoolStages", schoolStages);
		return json;
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/outlines")
	@ResponseBody
	public JsonResult outlines(Outline query, Page page) {
		JsonResult json = new JsonResult();
		Long schoolId = UserContext.user.getCurrentSchoolId();
		query.setSchoolId(schoolId);
		List<Outline> outlines = outlineService.queryOutline(query, page);
		json.addDatas("outlines", outlines);
		json.addDatas("page", page);
		return json;
	}
	

	@RequestMapping("schoolResearcher/syllabus/outline/addOutline")
	@ResponseBody
	public JsonResult addOutline(Outline outline) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		outline.setCreatedBy(userId);
		outline.setModifiedBy(userId);
//		outlineService.addOutline(outline);
		return json;
	}

	@RequestMapping("schoolResearcher/syllabus/outline/deleteOutline")
	@ResponseBody
	public JsonResult deleteOutline(Outline outline) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		outline.setModifiedBy(userId);
		outlineService.deleteOutline(outline);
		return json;
	}

	/**
	 * 教材维护主页
	 * 
	 */
	@RequestMapping("schoolResearcher/syllabus/outline/outlineList")
	public void outlineList() {
		// view
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/modifyOutline")
	@ResponseBody
	public JsonResult modifyOutline(Outline outline) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		outline.setModifiedBy(userId);
		outlineService.updateOutlineName(outline);
		return json;
	}

	/**
	 * 教材添加页
	 * 
	 */
	@RequestMapping("schoolResearcher/syllabus/outline/outlineAdd")
	public void workbookAdd() {
		// view
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/doOutlineAdd")
	@ResponseBody
	public JsonResult doOutlineAdd(Outline outline, Boolean isCopyMaterial) {
		User user = UserContext.user.get();
		outline.setStatus(Outline.STATUS_OFF);
		boolean copySections = isCopyMaterial != null && isCopyMaterial.equals(true);
		outlineService.addOutline(outline, copySections, user);
		return new JsonResult();
	}

	@RequestMapping("schoolResearcher/syllabus/outline/materialView")
	public void materialView(Material material, Boolean crossDomain, Model model) {
		Long gradeId = material.getGradeId();
		SchoolStageRemote schoolStageRemote = SchoolStageContext.getSchoolStageByGradeId(gradeId);
		Long schoolStageId = schoolStageRemote.getSchoolStageId();
		material.setSchoolStageId(schoolStageId);
		
		model.addAttribute("material", material);
		if (crossDomain == null) {
			crossDomain = false;
		}
		model.addAttribute("crossDomain", crossDomain);
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/addSection")
	@ResponseBody
	public JSONPObject addSection(OutlineNode outlineNode, String callback) {
		return jsonp(callback, (json) -> {
			Validation.notNull(outlineNode, "que.workbookNode.info.incomplete");
			Long userId = UserContext.user.getUserId();
			outlineNode.setCreatedBy(userId);
			outlineNode.setModifiedBy(userId);
			outlineService.addOutlineNode(outlineNode);
		});
	}

	@RequestMapping("schoolResearcher/syllabus/outline/modifySection")
	@ResponseBody
	public JSONPObject modifySection(OutlineNode outlineNode, String callback) {
		return jsonp(callback, (json) -> {
			Validation.notNull(outlineNode, "que.workbookNode.info.incomplete");
			Long userId = UserContext.user.getUserId();
			outlineNode.setModifiedBy(userId);
			outlineService.updateOutlineNode(outlineNode);
		});
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/deleteSection")
	@ResponseBody
	public JSONPObject deleteSection(OutlineNode outlineNode, String callback) {
		return jsonp(callback, (json) -> {
			Validation.notNull(outlineNode, "que.workbookNode.info.incomplete");
			Long userId = UserContext.user.getUserId();
			outlineNode.setModifiedBy(userId);
			outlineService.deleteOutlineNode(outlineNode);
		});
	}
	
	
	@RequestMapping("schoolResearcher/syllabus/outline/moveUp")
	@ResponseBody
	public JSONPObject moveUp(OutlineNode outlineNode, String callback) {
		return jsonp(callback, (json) -> {
			Validation.notNull(outlineNode, "que.workbookNode.info.incomplete");
			Long userId = UserContext.user.getUserId();
			outlineNode.setModifiedBy(userId);
			outlineService.moveUpNode(outlineNode);
		});
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/moveDown")
	@ResponseBody
	public JSONPObject moveDown(OutlineNode outlineNode, String callback) {
		return jsonp(callback, (json) -> {
			Validation.notNull(outlineNode, "que.workbookNode.info.incomplete");
			Long userId = UserContext.user.getUserId();
			outlineNode.setModifiedBy(userId);
			outlineService.moveDownNode(outlineNode);
		});
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/rebuildTreeIndex")
	@ResponseBody
	public JsonResult rebuildTreeIndex(Long outlineId) {
		outlineService.rebuildTreeIndexWithTx(outlineId);
		return new JsonResult();
	}
	
	@RequestMapping("schoolResearcher/syllabus/outline/outlineUp")
	@ResponseBody
	public JsonResult outlineUp(Long outlineId) {
		User user = UserContext.user.get();
		outlineService.outlineUp(outlineId, user);
		return new JsonResult();
	}

	@RequestMapping("schoolResearcher/syllabus/outline/outlineDown")
	@ResponseBody
	public JsonResult outlineDown(Long outlineId) {
		User user = UserContext.user.get();
		outlineService.outlineDown(outlineId, user);
		return new JsonResult();
	}
}
