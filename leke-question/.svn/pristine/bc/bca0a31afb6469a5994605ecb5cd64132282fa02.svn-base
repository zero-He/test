package cn.strong.leke.question.controller.material;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.context.question.MaterialTreeContext;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.question.service.MaterialService;
import cn.strong.leke.remote.model.question.MatPath;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

@Controller
@RequestMapping("/auth/common/material")
public class CommonMaterialController {

	@Resource
	private MaterialService materialService;

	@ResponseBody
	@RequestMapping("getMaterialNode")
	public JsonResult getMaterialNode(Long materialNodeId) {
		JsonResult result = new JsonResult();
		MatPath matPath = MaterialTreeContext.getMatPath(materialNodeId);
		if (matPath != null) {
			Material material = MaterialContext.getMaterial(matPath.getMaterialId());
			result.addDatas("material", material);
		}
		return result.addDatas("matPath", matPath);
	}

	@ResponseBody
	@RequestMapping("getStgSbjName")
	public JsonResult getStgSbjName(Long schoolStageId, Long subjectId) {
		JsonResult result = new JsonResult();
		SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStage(schoolStageId);
		if (schoolStage != null) {
			result.addDatas("schoolStageName", schoolStage.getSchoolStageName());
		}
		SubjectRemote subject = SubjectContext.getSubject(subjectId);
		if (subject != null) {
			result.addDatas("subjectName", subject.getSubjectName());
		}
		return result;

	}

	@ResponseBody
	@RequestMapping("queryTreeNodes")
	public List<MaterialNode> queryTreeNodes(MaterialNode node) {
		if (node == null || node.getMaterialId() == null) {
			return Collections.emptyList();
		}
		return MaterialTreeContext.findMaterialTreeNodes(node.getMaterialId());
	}

}
