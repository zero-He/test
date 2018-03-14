package cn.strong.leke.question.service;

import java.util.List;
import java.util.Set;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.common.UserResGroupDTO;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.question.QuestionUserResGroup;

/**
 *
 * 描述: 习题和个人资源分组关
 *
 * @author raolei
 * @created 2016年4月18日 下午3:10:24
 * @since v1.0.0
 */
public interface IQuestionUserResGroupService {

	void addBatchQuestionUserResGroup(Long userResGroupId, Set<Long> questionIds, User user);

	void saveMoveBatchQUserResGroup(UserResGroupDTO userResGroupDTO, Set<Long> questionIds,
			User user);

	void deleteBatchQuestionUserResGroup(Long userResGroupId, Set<Long> questionIds, User user);

	void deleteQURGroupByUserResGroupId(Long userResGroupId, User user);

	List<QuestionUserResGroup> queryQURGroupByUserResGroupId(Long userResGroupId, Page page);
}
