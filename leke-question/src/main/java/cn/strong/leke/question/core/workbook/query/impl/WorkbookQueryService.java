/**
 * 
 */
package cn.strong.leke.question.core.workbook.query.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.context.base.LeagueContext;
import cn.strong.leke.context.repository.RepositoryContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.repository.ShareScopes;
import cn.strong.leke.model.user.User;
import cn.strong.leke.model.user.league.LeagueMember;
import cn.strong.leke.question.core.workbook.query.IWorkbookQueryService;
import cn.strong.leke.question.dao.mybatis.IWorkbookDao;
import cn.strong.leke.question.model.InputStatisDTO;

/**
 * @author liulongbiao
 *
 */
@Service
public class WorkbookQueryService implements IWorkbookQueryService {

	@Resource
	private IWorkbookDao workbookDao;

	@Override
	public Workbook getById(Long workbookId) {
		return workbookDao.getWorkbook(workbookId);
	}

	@Override
	public List<InputStatisDTO> queryWorkbookAmount(InputStatisDTO query) {
		return workbookDao.queryWorkbookAmount(query);
	}

	@Override
	public Long queryWorkbookTotalAmount() {
		return workbookDao.queryWorkbookTotalAmount();
	}

	@Override
	public List<RepositoryRecord> queryByScope(RepositoryWorkbookQuery query, Page page, User user) {
		List<Long> workbookIds = ListUtils.newArrayList();
		Long schoolId = user.getCurrentSchool().getId();
		Long userId = user.getId();
		switch (query.getShareScope()) {
		case ShareScopes.FAVORITE:
			query.setUserId(userId);
			workbookIds = workbookDao.queryFavoriteWorkbooks(query, page);
			break;
		case ShareScopes.PLATFORM:
			workbookIds = workbookDao.queryLekeWorkbooks(query, page);
			break;
		case ShareScopes.SCHOOL:
			query.setSchoolId(schoolId);
			workbookIds = workbookDao.querySchoolWorkbooks(query, page);
			break;
		case ShareScopes.LEAGUE:
			List<LeagueMember> leagueMembers = LeagueContext.findAllLeagueMembers(query.getLeagueId());
			List<Long> leagueMemberIds = ListUtils.map(leagueMembers, n -> {
				return n.getSchoolId();
			});
			if (leagueMemberIds.isEmpty()) {
				break;
			}
			query.setLeagueMemberIds(new HashSet<Long>(leagueMemberIds));
			workbookIds = workbookDao.queryLeagueWorkbooks(query, page);
			break;
		case ShareScopes.PERSONAL_ALL:
			query.setUserId(user.getId());
			if (schoolId != null) {
				query.setSchoolId(schoolId);
				query.setLeagueMemberIds(LeagueContext.findVisibleSchoolIds(schoolId));
			}
			workbookIds = workbookDao.queryPersonalAll(query, page);
			break;
		default:
			break;
		}
		if (CollectionUtils.isEmpty(workbookIds)) {
			return Collections.emptyList();
		}
		return RepositoryContext.ofWorkbooks(workbookIds);
	}
	
}
