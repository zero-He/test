package cn.strong.leke.question.dao.mybatis.question;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.question.QuestionShareLog;
import cn.strong.leke.question.model.question.query.QuestionShareLogQuery;

/**
 * 习题分享日志DAO
 * 
 * @author liulongbiao
 *
 */
public interface IQuestionShareLogDao {

	/**
	 * 添加分享日志
	 * 
	 * @param log
	 * @return
	 */
	int add(QuestionShareLog log);

	/**
	 *
	 * 描述: 批量分享习题记录
	 *
	 * @author raolei
	 */
	int insertBatchQuestionShareLog(@Param("shareLogs") List<QuestionShareLog> shareLogs);

	/**
	 * 移除分享日志
	 * 
	 * @param shareLogId
	 * @param modifiedBy
	 * @return
	 */
	int remove(@Param("shareLogId") Long shareLogId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 更新学校分享的审核日志ID
	 * 
	 * @param questionId
	 * @param schoolId
	 * @param modifiedBy
	 * @return
	 */
	int updateSchoolCheckLogId(@Param("questionId") Long questionId,
			@Param("schoolId") Long schoolId, @Param("checkLogId") Long checkLogId,
			@Param("modifiedBy") Long modifiedBy);

	QuestionShareLog getQuestionShareLog(@Param("shareLogId") Long shareLogId);

	/**
	 *
	 * 描述: 查询分享记录
	 *
	 * @param query
	 * @param page
	 * @return List<QuestionShareLog>
	 */
	List<QuestionShareLog> queryQuestionShareLog(QuestionShareLogQuery query, Page page);
}
