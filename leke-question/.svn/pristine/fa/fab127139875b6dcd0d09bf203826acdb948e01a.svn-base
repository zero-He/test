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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.question.KnowledgeTreeContext;
import cn.strong.leke.context.question.MaterialTreeContext;
import cn.strong.leke.context.question.PressContext;
import cn.strong.leke.context.question.QuestionTypeContext;
import cn.strong.leke.context.question.WorkbookTreeContext;
import cn.strong.leke.context.user.UserPrefContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.HandwriteType;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.model.question.OfficialTag;
import cn.strong.leke.model.question.QuestionType;
import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.question.model.SimilarQueTypeQuery;
import cn.strong.leke.question.service.HandwriteTypeService;
import cn.strong.leke.question.service.IOfficialTagService;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.question.service.MaterialService;
import cn.strong.leke.question.service.QuestionTypeService;
import cn.strong.leke.remote.model.question.KlgPath;
import cn.strong.leke.remote.model.question.MatPath;
import cn.strong.leke.remote.model.question.PressRemote;
import cn.strong.leke.remote.model.tukor.RegionRemote;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.service.tutor.base.IRegionRemoteService;


/**
 * 
 * 描述: 基础信息查询 API
 * 
 * @author liulb
 * @created 2014年5月7日 下午5:25:44
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/common/base")
public class BaseInfoController {

	public static final int REGION_CHINA = 100000;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private KnowledgeService knowledgeService;
	@Autowired
	private IRegionRemoteService regionRemoteService;
	@Autowired
	private IOfficialTagService officialTagService;
	@Autowired
	private QuestionTypeService questionTypeService;
	@Autowired
	private HandwriteTypeService handwriteTypeService;

	/**
	 * 
	 * 描述: 查询所有学段信息，包含学段关联的年级和学科
	 * 
	 * @author liulb
	 * @created 2014年5月7日 下午7:13:38
	 * @since v1.0.0
	 * @return JsonResult
	 */
	@RequestMapping("schoolStages")
	@ResponseBody
	public JsonResult schoolStages() {
		JsonResult json = new JsonResult();
		List<SchoolStageRemote> schoolStages = SchoolStageContext
				.findSchoolStages();
		json.addDatas("schoolStages", schoolStages);
		return json;
	}

	/**
	 * 
	 * 描述: 查询所有出版社信息
	 * 
	 * @author liulb
	 * @created 2014年5月7日 下午7:14:12
	 * @since v1.0.0
	 * @return JsonResult
	 */
	@RequestMapping("presses")
	@ResponseBody
	public JsonResult presses() {
		JsonResult json = new JsonResult();
		List<PressRemote> presses = PressContext.findPresses();
		json.addDatas("presses", presses);
		return json;
	}

	/**
	 * 
	 * 描述: 查询所有省份
	 * 
	 * @author liulb
	 * @created 2014年5月7日 下午7:15:48
	 * @since v1.0.0
	 * @return JsonResult
	 */
	@RequestMapping("provinces")
	@ResponseBody
	public JsonResult provinces() {
		JsonResult json = new JsonResult();
		List<RegionRemote> provinces = regionRemoteService
				.findRegioList(REGION_CHINA);
		json.addDatas("provinces", provinces);
		return json;
	}

	/**
	 * 
	 * 描述: 查询所有教材
	 * 
	 * @author liulb
	 * @created 2014年5月7日 下午7:15:34
	 * @since v1.0.0
	 * @return
	 * @return JsonResult
	 */
	@RequestMapping("materials")
	@ResponseBody
	public JsonResult materials() {
		JsonResult json = new JsonResult();
		List<Material> materials = materialService.queryMaterials(null, null);
		json.addDatas("materials", materials);
		return json;
	}

	/**
	 * 
	 * 描述: 查询所有题库标签
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午2:53:46
	 * @since v1.0.0
	 * @return JsonResult
	 */
	@RequestMapping("officialTags")
	@ResponseBody
	public JsonResult officialTags() {
		JsonResult json = new JsonResult();
		List<OfficialTag> officialTags = officialTagService
				.findOfficialTagList();
		json.addDatas("officialTags", officialTags);
		return json;
	}

	@RequestMapping("subjectQueTypeMap")
	@ResponseBody
	public JsonResult subjectQueTypeMap() {
		JsonResult json = new JsonResult();
		Map<String, List<QuestionType>> map = QuestionTypeContext
				.findAllSubjectQuestionTypeMap();
		json.addDatas("map", map);
		return json;
	}

	@RequestMapping("similarQueTypes")
	@ResponseBody
	public JsonResult similarQueTypes(SimilarQueTypeQuery query) {
		JsonResult json = new JsonResult();
		List<QuestionType> types = questionTypeService
				.findSimilarQuestionTypes(query);
		json.addDatas("types", types);
		return json;
	}

	@RequestMapping("handwriteTypes")
	@ResponseBody
	public JsonResult handwriteTypes(SimilarQueTypeQuery query) {
		JsonResult json = new JsonResult();
		List<HandwriteType> types = handwriteTypeService
				.findAllHandwriteTypes();
		json.addDatas("types", types);
		return json;
	}

	@RequestMapping("matPath")
	@ResponseBody
	public JsonResult matPath(Long materialNodeId) {
		JsonResult json = new JsonResult();
		MatPath matPath = materialService.getMatPath(materialNodeId);
		json.addDatas("matPath", matPath);
		return json;
	}

	@RequestMapping("klgPath")
	@ResponseBody
	public JsonResult klgPath(Long knowledgeId) {
		JsonResult json = new JsonResult();
		KlgPath klgPath = knowledgeService.getKlgPath(knowledgeId);
		json.addDatas("klgPath", klgPath);
		return json;
	}

	@RequestMapping("matTreeNodes")
	@ResponseBody
	public JsonResult matTreeNodes(Long materialId) {
		JsonResult json = new JsonResult();
		List<MaterialNode> nodes = MaterialTreeContext.findMaterialTreeNodes(materialId);
		nodes = ListUtils.filter(nodes, node -> {
			return node.getNodeType() != MaterialNode.NODE_TYPE_KNOWLEDGE;
		});
		json.addDatas("nodes", nodes);
		return json;
	}

	@RequestMapping("sortMatTreeNodes")
	@ResponseBody
	public JsonResult sortMatTreeNodes(Long materialId) {
		JsonResult json = new JsonResult();
		List<MaterialNode> nodes = MaterialTreeContext.findMaterialTreeNodes(materialId);
		nodes = ListUtils.filter(nodes, node -> {
			return node.getNodeType() != MaterialNode.NODE_TYPE_KNOWLEDGE;
		});
		Collections.sort(nodes, SortMatNode.LFT);
		json.addDatas("nodes", nodes);
		return json;
	}

	@RequestMapping("klgTreeNodes")
	@ResponseBody
	public JsonResult klgTreeNodes(Long schoolStageId, Long subjectId) {
		JsonResult json = new JsonResult();
		List<Knowledge> nodes = KnowledgeTreeContext.findKnowledgeTreeNodes(schoolStageId, subjectId);
		json.addDatas("nodes", nodes);
		return json;
	}

	@RequestMapping("wbTreeNodes")
	@ResponseBody
	public JsonResult wbTreeNodes(Long workbookId) {
		JsonResult json = new JsonResult();
		List<WorkbookNode> nodes = WorkbookTreeContext.findWorkbookTreeNodes(workbookId);
		json.addDatas("nodes", nodes);
		return json;
	}

	@ResponseBody
	@RequestMapping("userAllPref")
	public JsonResult userAllPref() {
		JsonResult json = new JsonResult();
		json.addDatas("userPref", UserPrefContext.getUserPrefForAllDatas());
		return json;
	}

	public enum SortMatNode implements Comparator<MaterialNode> {
		LFT;
		@Override
		public int compare(MaterialNode o1, MaterialNode o2) {
			Integer a = o1.getLft();
			Integer b = o2.getLft();
			if (a == null) {
				return 1;
			} else if (b == null) {
				return -1;
			} else {
				return Integer.compare(a, b);
			}
		}


	}
}
