package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.ResearcherQuestionQuery;
import cn.strong.leke.question.model.question.QuestionCheckPending;

/**
 *
 * 描述: 习题待审核
 *
 * @author raolei
 * @created 2016年12月4日 上午9:57:46
 * @since v1.0.0
 */
public interface IQuestionCheckPendingDao {

	int add(QuestionCheckPending assoc);

	List<Long> queryResearcherQuestionIdList(ResearcherQuestionQuery query, Page page);

	int del(Long questionId);
}
