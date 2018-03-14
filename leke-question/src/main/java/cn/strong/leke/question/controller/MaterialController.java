/**
 * 
 */
package cn.strong.leke.question.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.model.question.MaterialNodeJoin;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.base.cmd.IMaterialNodeJoinHandler;
import cn.strong.leke.question.core.base.query.IMaterialNodeJoinQueryService;
import cn.strong.leke.question.core.material.cmd.IMaterialFileHandler;
import cn.strong.leke.question.core.material.query.IMaterialFileQueryService;
import cn.strong.leke.question.model.SectionKnowledgeAddEvent;
import cn.strong.leke.question.model.base.MaterialDTO;
import cn.strong.leke.question.model.material.MaterialFile;
import cn.strong.leke.question.service.MaterialService;

/**
 * 教材树维护控制器
 * 
 * @author liulb
 * 
 */
@Controller
@RequestMapping("/auth/admin/material")
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	@Autowired
	private IMaterialNodeJoinHandler materialNodeJoinHandler;
	@Autowired
	private IMaterialNodeJoinQueryService materialNodeJoinQueryService;
	@Autowired
	private IMaterialFileQueryService materialFileQueryService;
	@Autowired
	private IMaterialFileHandler materialFileHandler;


	/**
	 * 教材维护主页
	 * 
	 */
	@RequestMapping("materialList")
	public void materialList() {
		// view
	}

	@RequestMapping("materials")
	@ResponseBody
	public JsonResult materials(Material query, Page page) {
		JsonResult json = new JsonResult();
		List<MaterialDTO> materials = materialService.queryMaterialDTOs(query, page);
		json.addDatas("materials", materials);
		json.addDatas("page", page);
		return json;
	}

	@RequestMapping("addMaterial")
	@ResponseBody
	public JsonResult addMaterial(Material material) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		material.setCreatedBy(userId);
		material.setModifiedBy(userId);
		materialService.addMaterial(material);
		return json;
	}

	@RequestMapping("modifyMaterial")
	@ResponseBody
	public JsonResult modifyMaterial(Material material) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		material.setModifiedBy(userId);
		materialService.updateMaterialName(material);
		return json;
	}

	@RequestMapping("deleteMaterial")
	@ResponseBody
	public JsonResult deleteMaterial(Material material) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		material.setModifiedBy(userId);
		materialService.deleteMaterial(material);
		return json;
	}

	@RequestMapping("materialTree")
	public void materialTree(Material query, Model model) {
		model.addAttribute("query", query);
	}

	@RequestMapping("addSection")
	@ResponseBody
	public JsonResult addSection(MaterialNode materialNode) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		materialNode.setCreatedBy(userId);
		materialNode.setModifiedBy(userId);
		materialNode.setNodeType(MaterialNode.NODE_TYPE_SECTION);
		materialService.addMaterialNode(materialNode);
		return json;
	}

	@RequestMapping("modifySection")
	@ResponseBody
	public JsonResult modifySection(MaterialNode materialNode) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		materialNode.setModifiedBy(userId);
		materialService.updateMaterialNode(materialNode);
		return json;
	}

	@RequestMapping("deleteSection")
	@ResponseBody
	public JsonResult deleteSection(MaterialNode materialNode) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		materialNode.setModifiedBy(userId);
		materialService.deleteMaterialNode(materialNode);
		return json;
	}
	
	@RequestMapping("moveUp")
	@ResponseBody
	public JsonResult moveUp(MaterialNode materialNode) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		materialNode.setModifiedBy(userId);
		materialService.moveUpNode(materialNode);
		return json;
	}
	
	@RequestMapping("moveDown")
	@ResponseBody
	public JsonResult moveDown(MaterialNode materialNode) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		materialNode.setModifiedBy(userId);
		materialService.moveDownNode(materialNode);
		return json;
	}

	@RequestMapping("knowledges")
	@ResponseBody
	public JsonResult knowledges(MaterialNode query) {
		JsonResult json = new JsonResult();
		List<MaterialNode> knowledges = materialService
				.queryKnowledgeNodes(query);
		json.addDatas("knowledges", knowledges);
		return json;
	}

	@RequestMapping("addKnowledges")
	@ResponseBody
	public JsonResult addKnowledges(String dataJson) {
		JsonResult json = new JsonResult();
		SectionKnowledgeAddEvent event = JsonUtils.fromJSON(dataJson,
				SectionKnowledgeAddEvent.class);
		Long userId = UserContext.user.getUserId();
		event.setCreatedBy(userId);
		event.setModifiedBy(userId);
		materialService.addSectionKnowledges(event);
		return json;
	}

	@RequestMapping("deleteKnowledge")
	@ResponseBody
	public JsonResult deleteKnowledge(MaterialNode materialNode) {
		JsonResult json = new JsonResult();
		Long userId = UserContext.user.getUserId();
		materialNode.setModifiedBy(userId);
		materialService.deleteMaterialNode(materialNode);
		return json;
	}

	@RequestMapping("rebuildTreeIndex")
	@ResponseBody
	public JsonResult rebuildTreeIndex(Long materialId) {
		materialService.rebuildTreeIndexWithTx(materialId);
		return new JsonResult();
	}

	/**
	 * 拷贝教材
	 */
	@RequestMapping("bindingMaterial")
	public void bindingMaterial(Long materialId, Model model) {
		Material material = MaterialContext.getMaterial(materialId);
		model.addAttribute("material", JsonUtils.toJSON(material));
	}
	
	@ResponseBody
	@RequestMapping("findBindingMaterials")
	public JsonResult findBindingMaterials(Long origMaterialNodeId) {
		JsonResult jResult = new JsonResult();
		List<MaterialNodeJoin> mnjs = materialNodeJoinQueryService.findMaterialNodeJoins(origMaterialNodeId);
		jResult.addDatas("mnjs", mnjs);
		return jResult;
	}

	@ResponseBody
	@RequestMapping("doBindingMaterial")
	public JsonResult doBindingMaterial(MaterialNodeJoin assoc) {
		User user = UserContext.user.get();
		materialNodeJoinHandler.add(assoc, user);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("doDelBindingMaterial")
	public JsonResult doDelBindingMaterial(Long materialNodeJoinId) {
		User user = UserContext.user.get();
		materialNodeJoinHandler.del(materialNodeJoinId, user);
		return new JsonResult();
	}

	@RequestMapping("materialUpload")
	public void materialUpload(Long materialId, Model model) {
		model.addAttribute("materialId", materialId);
	}



	@ResponseBody
	@RequestMapping("addMaterialFile")
	public JsonResult addMaterialFile(String dataJson) {
		MaterialFile assoc = JsonUtils.fromJSON(dataJson, MaterialFile.class);
		materialFileHandler.add(assoc, UserContext.user.get());
		return new JsonResult();
	}

	@RequestMapping("materialFileView")
	public void materialFileView(Long materialId, Model model) {
		Material material = MaterialContext.getMaterial(materialId);
		MaterialFile assoc = materialFileQueryService.getByMaterialId(materialId);
		model.addAttribute("material", material);
		model.addAttribute("assoc", assoc);
	}

}
