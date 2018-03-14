package cn.strong.leke.question.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.utils.Booleans;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.context.question.PressContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.querys.BaseWorkbookQuery;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.service.IWorkbookNodeService;
import cn.strong.leke.question.service.IWorkbookService;
import cn.strong.leke.question.service.MaterialService;
import cn.strong.leke.repository.common.cmd.IRepoSaveHandler;
import cn.strong.leke.repository.common.model.RepoSaveContext;
import cn.strong.leke.repository.common.model.RepoScope;

/**
 *
 * 描述:习题册
 *
 * @author raolei
 * @created 2015年11月19日 下午7:37:20
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/common/workbook/*")
public class WorkbookController {

	@Resource
	private IWorkbookService workbookService;
	@Resource
	private IWorkbookNodeService workbookNodeService;
	@Resource
	private MaterialService materialService;
	@Resource(name = "workbookSaveHandler")
	private IRepoSaveHandler<Workbook> workbookSaveHandler;

	@RequestMapping("workbookList")
	public void workbookList() {

	}

	@RequestMapping("findWorkbooks")
	@ResponseBody
	public JsonResult findWorkbooks(BaseWorkbookQuery query, Page page) {
		JsonResult json = new JsonResult();

		User user = UserContext.user.get();
		Long roleId = user.getCurrentRole().getId();
		if (roleId.equals(RoleCst.RESEARCHER) || roleId.equals(RoleCst.QUESTION_ADMIN)) {
			query.setSharePlatform(true);
		} else if (roleId.equals(RoleCst.PROVOST)) {
			query.setShareSchool(true);
			query.setSchoolId(user.getCurrentSchool().getId());
		} else {
			query.setCreatedBy(user.getId());
		}

		List<Workbook> workbooks = workbookService.queryWorkbooks(query, page);
		json.addDatas("workbooks", workbooks);
		json.addDatas("page", page);
		return json;
	}

	@RequestMapping("workbookAdd")
	public void workbookAdd(
			@RequestParam(name = "crossDomain", defaultValue = "false") Boolean crossDomain,
			Workbook workbook, Model model) {
		model.addAttribute("workbook", workbook);
		model.addAttribute("crossDomain", crossDomain);
	}

	@RequestMapping("doWorkbookAdd")
	@ResponseBody
	public JsonResult doWorkbookAdd(Workbook data, RepoScope scope, String action,
			Boolean isCopyMaterial) {
		User user = UserContext.user.get();
		Long roleId = user.getCurrentRole().getId();
		if (roleId.equals(RoleCst.INPUTER) || roleId.equals(RoleCst.RESEARCHER)
				|| roleId.equals(RoleCst.QUESTION_ADMIN)) {
			data.setStatus(Workbook.STATUS_SHELF_OFF);
		} else {
			data.setStatus(Workbook.STATUS_SHELF_ON);
		}

		RepoSaveContext<Workbook> ctx = new RepoSaveContext<>();
		ctx.setData(data);
		ctx.setScope(scope);
		ctx.setUser(user);
		ctx.setOldId(null);
		ctx.setAction(action);
		ctx.attr("copySections", Booleans.isTrue(isCopyMaterial));

		workbookSaveHandler.save(ctx);
		return new JsonResult();
	}

	@RequestMapping("workbookEdit")
	public void workbookEdit(
			@RequestParam(name = "crossDomain", defaultValue = "false") Boolean crossDomain,
			Long workbookId, Model model) {
		Workbook wb = workbookService.getWorkbook(workbookId);
		model.addAttribute("workbook", wb);
		model.addAttribute("crossDomain", crossDomain);
	}

	@RequestMapping("preUpdatePhoto")
	public void preUpdatePhoto(String photoUrl, Long workbookId, Model model) {
		model.addAttribute("photoUrl", photoUrl);
		model.addAttribute("workbookId", workbookId);
	}
	
	@RequestMapping("addPhoto")
	public void addPhoto(String photoUrl, Model model) {
		model.addAttribute("photoUrl", photoUrl);
	}
	
	@RequestMapping("updatePhoto")
	@ResponseBody
	public JsonResult updatePhoto(Long workbookId, String photoUrl) {
		JsonResult json = new JsonResult();
		User user = UserContext.user.get();
		workbookService.workbookPhotoUpload(workbookId, user, photoUrl);
		return json;
	}
	
	@RequestMapping("doWorkbookEdit")
	@ResponseBody
	public JsonResult doWorkbookEdit(Workbook wb) {
		JsonResult jResult = new JsonResult();
		User user = UserContext.user.get();
		wb.setSchoolStageName(SchoolStageContext.getSchoolStage(wb.getSchoolStageId()).getSchoolStageName());
		wb.setSubjectName(SubjectContext.getSubject(wb.getSubjectId()).getSubjectName());
		wb.setPressName(PressContext.getPress(wb.getPressId()).getPressName());
		wb.setMaterialName(MaterialContext.getMaterial(wb.getMaterialId()).getMaterialName());
		workbookService.updateWorkbook(wb, user);
		return jResult;
	}

	@RequestMapping("workbookUp")
	@ResponseBody
	public JsonResult workbookUp(Long workbookId) {
		User user = UserContext.user.get();
		workbookService.saveWorkbookUp(workbookId, user);
		return new JsonResult();
	}

	@RequestMapping("workbookDown")
	@ResponseBody
	public JsonResult workbookDown(Long workbookId) {
		User user = UserContext.user.get();
		workbookService.workbookDown(workbookId, user);
		return new JsonResult();
	}

	@RequestMapping("materialView")
	public void materialView(Material material, Boolean crossDomain, Model model) {
		model.addAttribute("material", material);
		if (crossDomain == null) {
			crossDomain = false;
		}
		model.addAttribute("crossDomain", crossDomain);
	}

	@RequestMapping("findMaterials")
	@ResponseBody
	public JsonResult findMaterials(Material material, Page page) {
		JsonResult jResult = new JsonResult();
		jResult.addDatas("materials",
				materialService.queryMaterials(material, page));
		jResult.addDatas("page", page);
		return jResult;
	}

	@RequestMapping("rebuildIndex")
	@ResponseBody
	public JsonResult rebuildIndex(Long workbookId) {
		JsonResult jResult = new JsonResult();
		workbookNodeService.rebuildTreeIndexWithTx(workbookId);
		return jResult;
	}
}
