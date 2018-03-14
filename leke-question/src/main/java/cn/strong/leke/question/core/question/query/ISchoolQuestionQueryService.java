/**
 * 
 */
package cn.strong.leke.question.core.question.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.model.question.query.AgencySchoolQuestionQuery;
import cn.strong.leke.question.model.question.query.SchoolQuestionCheckQuery;

/**
 * 学校习题查询接口
 * 
 * @author liulongbiao
 *
 */
public interface ISchoolQuestionQueryService {
	/**
	 * 查询学校习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> querySchoolQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查询我的代录学校习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryMyAgencySchoolQuestions(AgencySchoolQuestionQuery query,
			Page page);

	/**
	 * 查找学校记录审核数据列表
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> querySchoolQuestionForCheck(SchoolQuestionCheckQuery query, Page page);
}
