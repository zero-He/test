package cn.strong.leke.question.service;

import java.util.List;
import java.util.Set;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.common.UserResGroupDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.workbook.WorkbookUserResGroup;

/**
 *
 * 描述: 习题册和用户资源分组关联
 *
 * @author raolei
 * @created 2016年4月18日 下午4:03:08
 * @since v1.0.0
 */
public interface IWorkbookUserResGroupService {

	void addBatchWorkbookUserResGroup(Long userResGroupId, Set<Long> workbookIds, User user);

	void saveMoveBatchWUserResGroup(UserResGroupDTO userResGroupDTO, Set<Long> workbookIds,
			User user);

	void deleteBatchWorkbookUserResGroup(Long userResGroupId, Set<Long> workbookIds, User user);

	void deleteWURGroupByUserResGroupId(Long userResGroupId, User user);

	List<WorkbookUserResGroup> queryWURGroupByUserResGroupId(Long userResGroupId, Page page);
}
