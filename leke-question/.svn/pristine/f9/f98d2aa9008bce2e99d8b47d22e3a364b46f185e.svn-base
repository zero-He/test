package cn.strong.leke.question.controller.api;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.fs.model.FileView;
import cn.strong.leke.question.core.material.cmd.ITeacherMaterialRecordHandler;
import cn.strong.leke.question.core.material.query.IMaterialFileQueryService;
import cn.strong.leke.question.core.material.query.ITeacherMaterialRecordQueryService;
import cn.strong.leke.question.model.base.MaterialDTO;
import cn.strong.leke.question.model.material.MaterialFile;
import cn.strong.leke.question.model.material.TeacherMaterialRecord;
import cn.strong.leke.question.model.material.TeacherMaterialRecordDTO;
import cn.strong.leke.remote.service.fs.IFileTransRemoteService;

@Controller
@RequestMapping("/api/n/*")
public class APINController {

	private static final Logger logger = LoggerFactory.getLogger(APIWController.class);

	@Resource
	private ITeacherMaterialRecordQueryService teacherMaterialRecordQueryService;
	@Resource
	private IMaterialFileQueryService materialFileQueryService;
	@Resource
	private ITeacherMaterialRecordHandler teacherMaterialRecordHandler;
	@Resource
	private IFileTransRemoteService fileTransRemoteService;

	@ResponseBody
	@RequestMapping("getLastMaterial")
	public JsonResult getLastMaterial(String data) {
		JsonResult jResult = new JsonResult();
		TeacherMaterialRecord teacherMaterialRecord = JsonUtils.fromJSON(data, TeacherMaterialRecord.class);
		teacherMaterialRecord = teacherMaterialRecordQueryService.getNewest(teacherMaterialRecord.getTeacherId());
		MaterialDTO materialDTO = new MaterialDTO();
		if (teacherMaterialRecord != null) {
			Long materialFileId = teacherMaterialRecord.getMaterialFileId();
			MaterialFile materialFile = materialFileQueryService.get(materialFileId);
			if (materialFile != null) {
				BeanUtils.copyProperties(materialDTO, MaterialContext.getMaterial(materialFile.getMaterialId()));
				BeanUtils.copyProperties(materialDTO, materialFile);
				materialDTO.setCurPage(teacherMaterialRecord.getCurPage());
				jResult.addDatas("material", materialDTO);
			}
		}
		return jResult;
	}

	@ResponseBody
	@RequestMapping("saveMaterialHistory")
	public JsonResult saveMaterialHistory(String data) {
		logger.info("老师电子教材浏览记录：", data);
		TeacherMaterialRecordDTO assoc = JsonUtils.fromJSON(data, TeacherMaterialRecordDTO.class);
		Long materialId = assoc.getMaterialId();
		MaterialFile file = materialFileQueryService.getByMaterialId(materialId);
		if (file == null) {
			throw new ValidateException("教材对应的电子教材不存在！");
		}
		assoc.setMaterialFileId(file.getMaterialFileId());
		teacherMaterialRecordHandler.updateCurPage(assoc);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("getMaterial")
	public JsonResult getMaterial(String data) {
		JsonResult result = new JsonResult();
		TeacherMaterialRecordDTO assoc = JsonUtils.fromJSON(data, TeacherMaterialRecordDTO.class);
		if (assoc.getMaterialId() == null) {
			result.setSuccess(false);
			result.setMessage("请求参数materialId错误！");
			return result;
		}
		MaterialFile file = materialFileQueryService.getByMaterialId(assoc.getMaterialId());
		if (file == null) {
			result.setSuccess(false);
			result.setMessage("教材对应的电子教材不存在!");
		} else {
			FileView fileView = fileTransRemoteService.getFileViewByFileId(file.getFileId());
			result.addDatas("fileView", fileView);
		}
		return result;
	}

}
