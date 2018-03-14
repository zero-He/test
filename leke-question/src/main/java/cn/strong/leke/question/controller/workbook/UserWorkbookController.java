package cn.strong.leke.question.controller.workbook;

import static com.google.common.collect.Sets.newHashSet;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.common.UserResGroupDTO;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.service.IWorkbookService;
import cn.strong.leke.question.service.IWorkbookUserResGroupService;

@Controller
@RequestMapping("/auth/common/workbook")
public class UserWorkbookController {
	@Resource
	private IWorkbookUserResGroupService workbookUserResGroupService;
	@Resource
	private IWorkbookService workbookService;

	@RequestMapping("personal/details")
	@ResponseBody
	public JsonResult personalDetails(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		query.setShareScope(ShareScopes.PERSONAL);
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = workbookService.queryPersonalWorkbooks(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@RequestMapping("favorite/details")
	@ResponseBody
	public JsonResult favoriteDetails(RepositoryWorkbookQuery query, Page page) {
		JsonResult result = new JsonResult();
		query.setShareScope(ShareScopes.FAVORITE);
		query.setUserId(UserContext.user.getUserId());
		List<RepositoryRecord> records = workbookService.queryFavoriteWorkbooks(query, page);
		result.addDatas("records", records);
		result.addDatas("page", page);
		return result;
	}

	@ResponseBody
	@RequestMapping("addBatchWorkbookUserGroup")
	public JsonResult addBatchWorkbookUserGroup(Long userResGroupId,
			@RequestParam("workbookIds[]") Long[] workbookIds) {
		User user = UserContext.user.get();
		workbookUserResGroupService.addBatchWorkbookUserResGroup(userResGroupId,
				newHashSet(workbookIds), user);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("moveBatchWorkbookUserGroup")
	public JsonResult moveBatchWorkbookUserGroup(String dataJson,
			@RequestParam("workbookIds[]") Long[] workbookIds) {
		UserResGroupDTO userResGroup = JsonUtils.fromJSON(dataJson, UserResGroupDTO.class);
		User user = UserContext.user.get();
		workbookUserResGroupService.saveMoveBatchWUserResGroup(userResGroup,
				newHashSet(workbookIds), user);
		return new JsonResult();
	}

	@ResponseBody
	@RequestMapping("delBatchWorkbookUserResGroup")
	public JsonResult delBatchWorkbookUserResGroup(Long userResGroupId,
			@RequestParam("workbookIds[]") Long[] workbookIds) {
		User user = UserContext.user.get();
		workbookUserResGroupService.deleteBatchWorkbookUserResGroup(userResGroupId,
				newHashSet(workbookIds),
				user);
		return new JsonResult();
	}

	/**
	 *
	 * 描述: 习题的分组删除
	 *
	 * @author raolei
	 * @created 2016年4月14日 下午5:45:45
	 */
	@RequestMapping("delUserGroup")
	@ResponseBody
	public JsonResult delUserGroup(Long userResGroupId) {
		// 批量删除
		User user = UserContext.user.get();
		workbookUserResGroupService.deleteWURGroupByUserResGroupId(userResGroupId, user);
		return new JsonResult();
	}
}
