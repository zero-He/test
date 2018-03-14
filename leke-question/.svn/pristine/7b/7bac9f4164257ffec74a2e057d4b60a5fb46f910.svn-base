/**
 * 
 */
package cn.strong.leke.question.core.question.query;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.querys.QuestionSelectQuery;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.RandSelectQuestionQuery;
import cn.strong.leke.question.model.question.QuestionTotal;
import cn.strong.leke.question.model.question.query.AmountQuestionQuery;

/**
 * 习题查询服务接口
 * 
 * @author liulongbiao
 *
 */
public interface IQuestionQueryService {

	/**
	 * 根据ID查询习题
	 * 
	 * @param questionId
	 * @return
	 */
	QuestionDTO getById(Long questionId);

	/**
	 *
	 * 描述: 统计录入量
	 *
	 * @author raolei
	 * @created 2016年8月4日 上午10:50:28
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return long
	 */
	QuestionTotal countInputQuestion(AmountQuestionQuery query);

	/**
	 *
	 * 描述: 统计精品库未审核的习题
	 *
	 * @author raolei
	 * @created 2016年8月9日 下午3:09:43
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return long
	 */
	long countUnCheckedQuestionOfLekeBoutique(AmountQuestionQuery query);

	/**
	 *
	 * 描述: 随机获取一道相似的习题
	 *
	 * @author raolei
	 * @created 2016年10月12日 下午7:19:51
	 * @since v1.0.0
	 * @param questionIds
	 * @return
	 * @return Long
	 */
	Long getLikeQuestionId(List<Long> questionIds);
	
	/**
	 *
	 * 描述: 根据条件查询习题
	 *
	 * @author raolei
	 * @created 2016年11月10日 下午8:09:26
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<Long>
	 */
	List<Long> queryRandSelectQuestions(RandSelectQuestionQuery query);

	/**
	 *
	 * 描述: 作业系统用
	 *
	 * @author raolei
	 * @created 2016年11月14日 下午6:40:02
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<Long>
	 */
	List<Long> queryHomeworkSelectQuestions(QuestionSelectQuery query);


	/**
	 *
	 * 描述: 查询问题数量
	 *
	 */
	List<InputStatisDTO> queryInputStatisDTO(InputStatisDTO amountQuestionQuery);


	List<InputStatisDTO> queryQuestionAmount(InputStatisDTO query);
	
	Long queryQuestionTotalAmount(InputStatisDTO query);
	
	/**
	 *
	 * 描述: 统计习题分来源
	 *
	 * @author raolei
	 * @created 2016年12月7日 下午5:33:46
	 * @return
	 */
	List<QuestionTotal> queryTotalQuestionFrom(AmountQuestionQuery query, String userName, Long roleId);

	/**
	 * 资源库习题查询统一接口
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryRepoQuestions(RepositoryQuestionQuery query, Page page);

	int countLekeQuestion(RepositoryQuestionQuery query);

	List<RepoDayGroup> groupCreatedOnLekeQuestion(RepositoryQuestionQuery query);
}
