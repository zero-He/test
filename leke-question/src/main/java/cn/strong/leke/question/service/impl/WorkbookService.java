package cn.strong.leke.question.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.common.utils.coll.JsArray;
import cn.strong.leke.common.utils.coll.Stack;
import cn.strong.leke.common.utils.function.Predicate;
import cn.strong.leke.context.base.LeagueContext;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.core.business.tree.ReIndexVisitor;
import cn.strong.leke.core.business.tree.TraverseVisitor;
import cn.strong.leke.core.business.tree.TreeBuilder;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.School;
import cn.strong.leke.model.common.TreeCst;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.WorkbookNode;
import cn.strong.leke.model.question.querys.BaseWorkbookQuery;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.dao.mybatis.IWorkbookNodeDao;
import cn.strong.leke.question.dao.mybatis.MaterialNodeDao;
import cn.strong.leke.question.dao.mybatis.workbook.ISchoolWorkbookDao;
import cn.strong.leke.question.service.IWorkbookService;
import cn.strong.leke.question.util.WorkbookHelper;
import cn.strong.leke.remote.service.paper.IPaperRemoteService;

/**
 *
 * 描述:习题册
 *
 * @author raolei
 * @created 2015年11月19日 下午4:32:27
 * @since v1.0.0
 */
@Service
public class WorkbookService implements IWorkbookService {

	@Resource
	private IWorkbookDao workbookDao;
	@Resource
	private IWorkbookNodeDao workbookNodeDao;
	@Resource
	private MaterialNodeDao materialNodeDao;
	@Resource
	private ISchoolWorkbookDao schoolWorkbookDao;
	@Resource
	private IPaperRemoteService paperRemoteService;

	@Override
	public void addWorkbook(Workbook wb, boolean copySections, User user) {
		if (wb == null || StringUtils.isEmpty(wb.getWorkbookName()) || wb.getSubjectId() == null
				|| user == null) {
			throw new ValidateException("que.workbook.info.incomplete");
		}
		if (copySections && wb.getMaterialId() == null) {
			throw new ValidateException("que.material.not.exist");
		}
		Validation.notNull(wb.getSchoolStageId(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getSubjectId(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getPressId(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getMaterialId(), "que.workbook.info.incomplete");
		populateWorkbook(wb, user);
		workbookDao.addWorkbook(wb);
		if (copySections) {
			copyMaterailNodesToWorkbookNodes(wb, user);
		} else {
			WorkbookNode root = buildRootWorkbookNode(wb, user);
			workbookNodeDao.addWorkbookNode(root);
		}
	}

	/**
	 * 拷贝
	 * 
	 * @author liulongbiao
	 * @param wb
	 * @param user
	 */
	private void copyMaterailNodesToWorkbookNodes(Workbook wb, User user) {
		List<MaterialNode> matNodes = materialNodeDao.queryMaterialNodesByMaterialId(wb.getMaterialId());
		List<MaterialNode> nodes = ListUtils.filter(matNodes, MatSectionFilter.INSTANCE);
		if (CollectionUtils.isEmpty(nodes)) {
			return;
		}

		MaterialNode tree = TreeBuilder.build(nodes, MaterialNode::getMaterialNodeId);
		tree.accept(new ReIndexVisitor<MaterialNode>());
		tree.setMaterialNodeName(wb.getWorkbookName()); // 设置根节点名称
		tree.accept(new WorkbookNodeCopyVisitor(workbookNodeDao, wb.getWorkbookId(), user.getId()));
	}

	private static class WorkbookNodeCopyVisitor extends TraverseVisitor<MaterialNode> {
		final IWorkbookNodeDao workbookNodeDao;
		final Long workbookId;
		final Long userId;
		final Stack<Long> stack;

		public WorkbookNodeCopyVisitor(IWorkbookNodeDao workbookNodeDao, Long workbookId, Long userId) {
			super();
			this.workbookNodeDao = workbookNodeDao;
			this.workbookId = workbookId;
			this.userId = userId;
			stack = new JsArray<Long>();
			stack.push(TreeCst.TREE_ROOT_ID);
		}

		@Override
		protected void preVisit(MaterialNode node) {
			WorkbookNode current = new WorkbookNode();
			current.setWorkbookNodeName(node.getMaterialNodeName());
			current.setWorkbookId(workbookId);
			current.setMaterialNodeId(node.getMaterialNodeId());
			current.setOrd(node.getOrd());
			current.setLft(node.getLft());
			current.setRgt(node.getRgt());
			current.setParentId(stack.peek());
			current.setCreatedBy(userId);
			current.setModifiedBy(userId);
			workbookNodeDao.addWorkbookNode(current);

			stack.push(current.getWorkbookNodeId());
		}

		@Override
		protected void postVisit(MaterialNode node) {
			stack.pop();
		}

	}

	public static enum MatSectionFilter implements Predicate<MaterialNode> {
		INSTANCE;

		@Override
		public boolean test(MaterialNode node) {
			Integer type = node.getNodeType();
			return type != null && !type.equals(MaterialNode.NODE_TYPE_KNOWLEDGE);
		}

	}

	/**
	 * 构建根节点
	 */
	private WorkbookNode buildRootWorkbookNode(Workbook wb, User user) {
		WorkbookNode node = new WorkbookNode();
		node.setWorkbookNodeName(wb.getWorkbookName());
		node.setWorkbookId(wb.getWorkbookId());
		node.setParentId(TreeCst.TREE_ROOT_ID);
		node.setOrd(0);
		int lft = TreeCst.TREE_FIRST_LFT;
		node.setLft(lft);
		node.setRgt(lft + 1);
		Long userId = user.getId();
		node.setCreatedBy(userId);
		node.setModifiedBy(userId);
		return node;
	}

	/**
	 * 填充习题册
	 */
	private void populateWorkbook(Workbook wb, User user) {
		if (wb.getStatus() == null) {
			wb.setStatus(Workbook.STATUS_SHELF_OFF);
		}
		if (wb.getShareSchool() == null) {
			wb.setShareSchool(false);
		}
		if (wb.getSharePlatform() == null) {
			wb.setSharePlatform(false);
		}

		Long userId = user.getId();
		String userName = user.getUserName();
		wb.setCreatedBy(userId);
		wb.setModifiedBy(userId);
		wb.setCreatorName(userName);
		School school = user.getCurrentSchool();
		if (school != null) {
			wb.setSchoolId(school.getId());
			wb.setSchoolName(school.getName());
		}
	}

	@Override
	public void deleteWorkbook(Long workbookId, User user) {
		checkHasWritePermission(workbookId, user);
		int count = workbookNodeDao.countByWorkbookId(workbookId);
		Validation.isTrue(count <= 1, "que.workbook.has.children");

		Long userId = user.getId();
		workbookDao.deleteWorkbook(workbookId, userId);

		WorkbookNode wn = new WorkbookNode();
		wn.setModifiedBy(userId);
		wn.setWorkbookId(workbookId);
		workbookNodeDao.deleteByWorkbookId(wn);
	}

	@Override
	public void updateDisableWorkbook(Long workbookId, User user) {
		checkHasWritePermission(workbookId, user);

		Long userId = user.getId();
		workbookDao.disableWorkbook(workbookId, userId);
	}

	private Workbook checkHasWritePermission(Long workbookId, User user) {
		Validation.isTrue(workbookId != null && user != null, "que.workbook.info.incomplete");
		Workbook backend = workbookDao.getWorkbook(workbookId);
		Validation.notNull(backend, "que.workbook.not.exist");
		if (!backend.getShareSchool()) {
			Long schoolId = user.getCurrentSchool().getId();
			int count = schoolWorkbookDao.countSchoolWorkbook(workbookId, schoolId);
			if (count > 0) {
				backend.setShareSchool(true);
			}
		}

		Validation.isForbidden(!WorkbookHelper.hasWorkbookWritePermission(backend, user));
		return backend;
	}

	@Override
	public void saveWorkbookUp(Long workbookId, User user) {
		Workbook backend = checkHasWritePermission(workbookId, user);
		if (backend.getPressId() == null) {
			throw new ValidateException("教材版本不能为空！");
		}
		if (backend.getMaterialId() == null) {
			throw new ValidateException("年级或课本不能为空！");
		}
		if (StringUtils.isEmpty(backend.getPhotoUrl())) {
			throw new ValidateException("封面不能为空！");
		}
		Workbook wb = new Workbook();
		wb.setWorkbookId(workbookId);
		wb.setModifiedBy(user.getId());
		workbookDao.workbookUp(wb);
		// paperRemoteService.onLekeBoutiqueByWbId(workbookId, user);
	}

	@Override
	public void workbookDown(Long workbookId, User user) {
		checkHasWritePermission(workbookId, user);
		Workbook wb = new Workbook();
		wb.setWorkbookId(workbookId);
		wb.setModifiedBy(user.getId());
		workbookDao.workbookDown(wb);
	}

	@Override
	public void updateWorkbook(Workbook wb, User user) {
		Validation.notNull(wb, "que.workbook.info.incomplete");
		Validation.hasText(wb.getWorkbookName(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getSchoolStageId(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getSubjectId(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getPressId(), "que.workbook.info.incomplete");
		Validation.notNull(wb.getMaterialId(), "que.workbook.info.incomplete");
		checkHasWritePermission(wb.getWorkbookId(), user);

		Long userId = user.getId();
		wb.setModifiedBy(userId);
		workbookDao.updateWorkbook(wb);

		List<WorkbookNode> roots = workbookNodeDao.findRootNodeByWorkbookId(wb.getWorkbookId());
		for (WorkbookNode root : roots) {
			root.setWorkbookNodeName(wb.getWorkbookName());
			root.setModifiedBy(userId);
			workbookNodeDao.updateWorkbookNodeName(root);
		}
	}
	
	@Override
	public void workbookPhotoUpload(Long workbookId, User user, String photoUrl) {
		checkHasWritePermission(workbookId, user);
		Workbook wb = new Workbook();
		wb.setWorkbookId(workbookId);
		wb.setModifiedBy(user.getId());
		wb.setPhotoUrl(photoUrl);
		workbookDao.workbookPhotoUpload(wb);
	}

	@Override
	public Workbook getWorkbook(Long workbookId) {
		return workbookDao.getWorkbook(workbookId);
	}

	@Override
	public void updateIncFavCount(Long workbookId) {
		workbookDao.incFavCount(workbookId);
	}

	@Override
	public void updateBatchIncFavCount(List<Long> workbookIds) {
		workbookDao.incBatchFavCount(workbookIds);
	}

	@Override
	public List<Workbook> queryWorkbooks(BaseWorkbookQuery query, Page page) {
		return workbookDao.queryWorkbooks(query, page);
	}

	@Override
	public List<Workbook> findWorkbookByIds(Long[] workbookIds) {
		if (workbookIds == null || workbookIds.length == 0) {
			return Collections.emptyList();
		}
		return workbookDao.findWorkbookByIds(workbookIds);
	}

	@Override
	public List<RepositoryRecord> queryPersonalWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryPersonalWorkbooks(query, page);
		return RepositoryContext.ofWorkbooks(workbookIds);
	}

	@Override
	public List<RepositoryRecord> queryFavoriteWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryFavoriteWorkbooks(query, page);
		return RepositoryContext.ofWorkbooks(workbookIds);
	}

	@Override
	public List<RepositoryRecord> querySchoolWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.querySchoolWorkbooks(query, page);
		return RepositoryContext.ofWorkbooks(workbookIds);
	}

	@Override
	public List<RepositoryRecord> queryLeagueWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = queryLeagueWorkbookIds(query, page);
		return RepositoryContext.ofWorkbooks(workbookIds);
	}

	private List<Long> queryLeagueWorkbookIds(RepositoryWorkbookQuery query, Page page) {
		if (query == null || query.getLeagueId() == null) {
			return Collections.emptyList();
		}
		Set<Long> ids = LeagueContext.toLeagueMemberIds(query.getLeagueId(),
				query.getLeagueMemberId(), query.getSchoolId());
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		query.setLeagueMemberIds(ids);
		return workbookDao.queryLeagueWorkbooks(query, page);
	}

	@Override
	public List<RepositoryRecord> queryLekeShareWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryLekeShareWorkbooks(query, page);
		return RepositoryContext.ofWorkbooks(workbookIds);
	}

	@Override
	public List<RepositoryRecord> queryLekeBoutiqueWorkbooks(RepositoryWorkbookQuery query, Page page) {
		List<Long> workbookIds = workbookDao.queryLekeBoutiqueWorkbooks(query, page);
		return RepositoryContext.ofWorkbooks(workbookIds);
	}
}
