package cn.strong.leke.question.controller;

import static cn.strong.leke.core.business.web.JSONP.jsonp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.model.question.WorkbookPraise;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.query.IFavoriteWorkbookQueryService;
import cn.strong.leke.question.model.workbook.TeacherWorkbook;
import cn.strong.leke.question.service.ITeacherWorkbookService;
import cn.strong.leke.question.service.IWorkbookNodeService;
import cn.strong.leke.question.service.IWorkbookPraiseService;
import cn.strong.leke.question.service.IWorkbookService;
import cn.strong.leke.repository.common.cmd.store.IPersonalRepoHandler;

import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 *
 * 描述:JSONP 通用用户习题控制器
 *
 * @author raolei
 * @created 2015年12月1日 上午11:06:07
 * @since v1.0.0
 */
@Controller
@RequestMapping("/auth/common/workbook/jsonp")
public class JsonpCommonWorkbookController {

	@Resource
	private ITeacherWorkbookService teacherWorkbookService;
	@Resource
	private IWorkbookPraiseService workbookPraiseService;
	@Resource
	private IWorkbookNodeService workbookNodeService;
	@Resource
	private IWorkbookService workbookService;
	@Autowired
	@Qualifier("personalWorkbookHandler")
	private IPersonalRepoHandler personalRepoHandler;
	@Resource
	private IFavoriteWorkbookQueryService favoriteWorkbookQueryService;

	/**
	 *
	 * 描述:点赞
	 *
	 * @author raolei
	 * @created 2015年12月1日 上午11:21:28
	 * @since v1.0.0
	 * @param workbookId
	 * @param callback
	 * @return
	 * @return JSONPObject
	 */
	@RequestMapping("doPraise")
	@ResponseBody
	public JSONPObject doPraise(final Long workbookId, String callback) {
		return jsonp(callback, (json) -> {
			WorkbookPraise praise = new WorkbookPraise();
			praise.setWorkbookId(workbookId);
			praise.setCreatedBy(UserContext.user.getUserId());
			workbookPraiseService.addWorkbookPraise(praise);
		});
	}

	/**
	 *
	 * 描述:收藏
	 *
	 * @author raolei
	 * @created 2015年12月1日 下午1:48:25
	 * @since v1.0.0
	 * @param workbookId
	 * @param callback
	 * @return
	 * @return JSONPObject
	 */
	@RequestMapping("doFavorite")
	@ResponseBody
	public JSONPObject doFavorite(final Long workbookId, String callback) {
		return jsonp(callback, (json) -> {
			TeacherWorkbook teacher = new TeacherWorkbook();
			Long userId = UserContext.user.getUserId();
			teacher.setCreatedBy(userId);
			teacher.setModifiedBy(userId);
			teacher.setTeacherId(userId);
			teacher.setWorkbookId(workbookId);
			Award award = teacherWorkbookService.addTeacherWorkbook(teacher, UserContext.user.get());
			json.addDatas("award", award);
		});
	}

	@RequestMapping("doUnFavorite")
	@ResponseBody
	public JSONPObject doUnFavorite(TeacherWorkbook teacherWorkbook, String callback) {
		return jsonp(callback, (json) -> {
			Long teacherId = UserContext.user.getUserId();
			teacherWorkbook.setTeacherId(teacherId);
			teacherWorkbookService.deleteTeacherWorkbook(teacherWorkbook);

		});
	}

	@RequestMapping("doBatchUnFavorite")
	@ResponseBody
	public JSONPObject doBatchUnFavorite(@RequestParam("workbookIds[]") Long[] workbookIds,
			String callback) {
		return jsonp(callback, (json) -> {
			Long teacherId = UserContext.user.getUserId();
			teacherWorkbookService.deleteBatchTeacherWorkbook(workbookIds, teacherId);
		});
	}

	@RequestMapping("doDelete")
	@ResponseBody
	public JSONPObject doDelete(final Long workbookId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			workbookService.deleteWorkbook(workbookId, user);
		});
	}

	@RequestMapping("doRemove")
	@ResponseBody
	public JSONPObject doRemove(final Long workbookId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			Long userId = user.getId();
			personalRepoHandler.remove(workbookId, userId, user);
		});
	}

	@RequestMapping("doDisable")
	@ResponseBody
	public JSONPObject doDisable(final Long workbookId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			workbookService.updateDisableWorkbook(workbookId, user);
		});
	}

	@RequestMapping("doSaveWbNode")
	@ResponseBody
	public JSONPObject doSaveWbNode(WorkbookNode node, String callback) {
		return jsonp(callback, (json) -> {
			Validation.notNull(node, "que.workbookNode.info.incomplete");
			User user = UserContext.user.get();
			if (node.getWorkbookNodeId() == null) {
				workbookNodeService.addWorkbookNode(node, user);
			} else {
				workbookNodeService.updateWorkbookNode(node, user);
			}
		});
	}

	@RequestMapping("doDeleteWbNode")
	@ResponseBody
	public JSONPObject doDeleteWbNode(Long workbookNodeId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			workbookNodeService.delWorkbookNode(workbookNodeId, user);
		});
	}
	
	
	@RequestMapping("doMoveUpWbNode")
	@ResponseBody
	public JSONPObject doMoveUpWbNode(Long workbookNodeId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			workbookNodeService.moveUpNode(workbookNodeId, user);
		});
	}
	
	@RequestMapping("doMoveDownWbNode")
	@ResponseBody
	public JSONPObject doMoveDownWbNode(Long workbookNodeId, String callback) {
		return jsonp(callback, (json) -> {
			User user = UserContext.user.get();
			workbookNodeService.moveDownNode(workbookNodeId, user);
		});
	}

	@ResponseBody
	@RequestMapping("filterFavoriteWorkbooks")
	public JSONPObject filterFavoriteWorkbooks(String dataJson, String callback) {
		return jsonp(callback, (json) -> {
			List<Long> workbookIds = JsonUtils.readList(dataJson, Long.class);
			User user = UserContext.user.get();
			List<Long> resIds = favoriteWorkbookQueryService.filterFavoriteWorkbooks(workbookIds, user);
			json.addDatas("resIds", resIds);
		});
	}
}
