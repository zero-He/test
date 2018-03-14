/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.querys.QuestionSelectQuery;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.duplication.model.DupQuestionQuery;
import cn.strong.leke.question.duplication.model.UpdatedQuestionQuery;
import cn.strong.leke.question.model.CheckQuestionQuery;
import cn.strong.leke.question.model.CheckerQuestionQuery;
import cn.strong.leke.question.model.DraftQuestionQuery;
import cn.strong.leke.question.model.ImportedQuestionQuery;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputerQuestionQuery;
import cn.strong.leke.question.model.PublishedQuestionQuery;
import cn.strong.leke.question.model.ResearcherQuestionQuery;
import cn.strong.leke.question.model.ReviewQuestionQuery;
import cn.strong.leke.question.model.StatisQuestionQuery;
import cn.strong.leke.question.model.TeacherShareQuestionQuery;
import cn.strong.leke.question.model.question.QuestionTotalResult;
import cn.strong.leke.question.model.question.query.AmountQuestionQuery;
import cn.strong.leke.question.model.question.query.RandomGetQuestionQuery;
import cn.strong.leke.remote.model.question.SchoolQuestionContribution;
import cn.strong.leke.remote.model.question.SchoolQuestionContributionQuery;

/**
 * 
 * 描述: 题目DAO
 * 
 * @author liulb
 * @created 2014年5月4日 上午10:34:01
 * @since v1.0.0
 */
public interface QuestionDao {

	/**
	 * 
	 * 描述: 新增题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param question
	 * @return int
	 */
	int insertQuestion(Question question);
	
	/**
	 * 
	 * 描述: 修改题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param question
	 * @return int
	 */
	int updateQuestion(Question question);
	
	/**
	 *	
	 * 描述:更新题目分享状态
	 *
	 * @author  lavender
	 * @created 2014年12月16日 下午1:00:18
	 * @since   v1.0.0 
	 * @param question
	 * @return
	 * @return  int
	 */
	int updateQuestionShare(Question question);

	/**
	 *
	 * 描述: 修改习题来源试卷的ID
	 *
	 * @author raolei
	 * @created 2016年10月25日 下午8:50:33
	 * @since v1.0.0
	 * @param questionMessage
	 * @return
	 * @return int
	 */
	int updateQuestionsPaperId(@Param("paperId") Long paperId, @Param("questionIds") List<Long> questionIds);

	/**
	 * 
	 * 描述: 删除题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:36:04
	 * @since v1.0.0
	 * @param question
	 * @return int
	 */
	int deleteQuestion(Question question);

	/**
	 * 描述: 根据ID获取题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:37:23
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return QuestionDTO
	 */
	QuestionDTO getQuestion(Long questionId);

	/**
	 * 
	 * 描述: 根据题目ID获取子题列表
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午4:43:29
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<QuestionDTO>
	 */
	List<QuestionDTO> findSubQuestions(Long questionId);

	/**
	 * 
	 * 描述: 查询录入人员题目
	 * 
	 * @author liulb
	 * @created 2014年5月13日 下午3:24:41
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<QuestionDTO>
	 */
	List<Long> queryInputerQuestionIds(InputerQuestionQuery query,
			Page page);

	/**
	 * 
	 * 描述: 查询审核人员未审核题目
	 * 
	 * @author liulb
	 * @created 2014年5月16日 下午3:26:37
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<QuestionDTO>
	 */
	List<Long> queryCheckerUncheckedQuestionIds(
			CheckerQuestionQuery query, Page page);

	/**
	 * 
	 * 描述: 查询审核人员已审核题目
	 * 
	 * @author liulb
	 * @created 2014年5月16日 下午3:26:37
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<QuestionDTO>
	 */
	List<Long> queryCheckerCheckedQuestionIds(CheckerQuestionQuery query,
			Page page);

	/**
	 * 
	 * 描述: 查询教研员题目
	 * 
	 * @author liulb
	 * @created 2014年5月19日 上午11:15:41
	 * @since v1.0.0
	 * @param query
	 * @return List<QuestionDTO>
	 */
	List<Long> queryResearcherQuestionIds(ResearcherQuestionQuery query,
			Page page);

	/**
	 * 
	 * 描述: 查询人员统计题目
	 * 
	 * @author liulb
	 * @created 2014年5月20日 下午4:17:39
	 * @since v1.0.0
	 * @param query
	 * @return List<QuestionDTO>
	 */
	List<Long> queryStatisQuestionIds(StatisQuestionQuery query, Page page);

	/**
	 * 
	 * 描述: 查询草稿题目
	 * 
	 * @author liulb
	 * @created 2014年5月22日 下午2:44:45
	 * @since v1.0.0
	 * @param query
	 * @return List<QuestionDTO>
	 */
	List<Long> queryDraftQuestionIds(DraftQuestionQuery query, Page page);

	/**
	 * 
	 * 描述: 查询正式题目
	 * 
	 * @author liulb
	 * @created 2014年5月23日 上午10:38:35
	 * @since v1.0.0
	 * @param query
	 * @return List<QuestionDTO>
	 */
	List<Long> queryPublishedQuestionIds(PublishedQuestionQuery query,
			Page page);
	
	/**
	 * 教研员查询纠错列表
	 * @param researcher
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:32:38
	 */
	List<Long> queryFeedbackQuestionIds(Researcher researcher,
			Page page);
	
	/**
	 * 教研员查询所有纠错列表
	 * @param researcher
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:32:38
	 */
	List<Long> queryAllFeedbackQuestionIds(Researcher researcher,
			Page page);
	
	/**
	 * 教师查询纠错列表
	 * @param userId
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-7-28下午3:51:46
	 */
	List<Long> queryFeedbackQuestionIdsByUser(Long userId, Page page);
	
	/**
	 * 查询习题退回列表
	 * @param userId
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-7-29下午4:27:45
	 */
	List<Long> queryRejectionQuestionIdsByUser(Long userId, Page page);

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
	 * 描述: 随机根据条件获取一道习题
	 *
	 * @author raolei
	 * @created 2016年10月12日 下午7:03:43
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return Long
	 */
	Long getRandomQuestionId(RandomGetQuestionQuery query);

	/**
	 * 
	 * 描述: 查询独立的题目数量
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午5:21:16
	 * @since v1.0.0
	 * @return int
	 */
	int countStandaloneQuestions();

	/**
	 * 
	 * 描述: 查询独立的题目
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午5:23:04
	 * @since v1.0.0
	 * @param page
	 * @return List<QuestionDTO>
	 */
	List<QuestionDTO> queryStandaloneQuestions(Page page);

	/**
	 * 
	 * 描述: 查询类型不匹配的录入人员题目
	 * 
	 * @author liulb
	 * @created 2014年7月4日 下午1:25:26
	 * @since v1.0.0
	 * @param query
	 * @param page
	 * @return List<QuestionDTO>
	 */
	List<Long> queryTypeMismatchInputerQuestionIds(
			InputerQuestionQuery query, Page page);
	
	/**
	 * 禁用题目
	 * @param question
	 * @return
	 * author：lavender
	 * 2014-7-28上午10:30:05
	 */
	int disableQuestion(Question question);

	/**
	 * 
	 * 描述: 查询初审习题
	 * 
	 * @author liulb
	 * @created 2014年7月29日 下午3:15:29
	 * @since v1.1.0
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryReviewQuestionIds(ReviewQuestionQuery query, Page page);

	/**
	 * 
	 * 描述: 查询教师共享习题审核列表
	 * 
	 * @author liulb
	 * @created 2014年8月1日 下午3:27:27
	 * @since v1.1.0
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryTeacherShareQuestionIds(
			TeacherShareQuestionQuery query, Page page);
	
	/**
	 * 查询教师学校习题贡献
	 * @param query
	 * @return
	 * author：lavender
	 * 2014-8-11下午3:47:17
	 */
	List<SchoolQuestionContribution> findSchoolQuestionContributions(
			SchoolQuestionContributionQuery query);

	/**
	 *	
	 * 描述:查询习题数量
	 *
	 * @author  lavender
	 * @created 2014年8月28日 上午9:53:53
	 * @since   v1.0.0 
	 * @return
	 * @return  Long
	 */
	Long queryQuestionCount();

	/**
	 * 
	 * 描述: 查询审核页面习题
	 * 
	 * @author liulb
	 * @created 2014年9月24日 下午3:01:34
	 * @since v3.1
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryQuestionIdsForCheck(CheckQuestionQuery query, Page page);

	/**
	 * 更新难度值
	 * 
	 * @author liulb
	 * @created 2014年11月18日 上午9:02:00
	 * @since v1.1.0
	 * @param question
	 * @return
	 */
	int updateDifficulty(Question question);

	/**
	 * 更新题型ID
	 * 
	 * @author liulb
	 * @created 2014年11月18日 上午9:04:19
	 * @since v1.1.0
	 * @param question
	 */
	int updateQuestionType(Question question);

	/**
	 * 更新习题状态
	 * 
	 * @author liulb
	 * @created 2014年11月18日 上午11:12:01
	 * @since v1.1.0
	 * @param que
	 */
	int updateQuestionStatus(Question que);

	/**
	 * 更新备注
	 * 
	 * @author liulongbiao
	 * @created 2014年12月18日 下午2:17:07
	 * @since v3.2.1
	 * @param que
	 * @return
	 */
	int updateNote(Question que);

	/**
	 * 查询导入的习题列表ID
	 * 
	 * @author liulongbiao
	 * @created 2014年12月27日 下午1:24:49
	 * @since v3.2.2
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryImportedQuestionIds(ImportedQuestionQuery query, Page page);

	/**
	 *	
	 * 描述:教研员查询习题列表
	 *
	 * @author  lavender
	 * @created 2015年1月4日 下午1:42:47
	 * @since   v1.0.0 
	 * @param query
	 * @param page
	 * @return
	 * @return  List<QuestionDTO>
	 */
	List<Long> queryResearcherQuestionIdList(ResearcherQuestionQuery query, Page page);

	/**
	 * 查询更新的平台习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午2:04:02
	 * @since v3.2.2
	 * @param query
	 * @return
	 */
	List<Long> queryUpdatedPlatformQuestionIds(UpdatedQuestionQuery query);

	/**
	 * 查询更新的导入习题
	 * 
	 * @author liulongbiao
	 * @created 2015年1月18日 下午4:00:30
	 * @since v3.2.2
	 * @param query
	 * @return
	 */
	List<Long> queryRemovedPlatformQuestionIds(UpdatedQuestionQuery query);

	/**
	 * 更新疑似重复标识（不触发修改时间的变更）
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 上午11:07:45
	 * @since v3.2.2
	 * @param que
	 * @return
	 */
	int updateMayDup(Question que);

	/**
	 * 查找疑似重复习题ID列表
	 * 
	 * @author liulongbiao
	 * @created 2015年1月19日 下午3:39:16
	 * @since v3.2.2
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryDupQuestionIds(DupQuestionQuery query, Page page);

	/**
	 * 增加使用次数
	 * 
	 * @author liulongbiao
	 * @param questionId
	 * @return
	 */
	int incUsedCount(Long questionId);

	/**
	 *
	 * 描述: 增加收藏次数
	 *
	 * @author raolei
	 * @param questionId
	 */
	int incFavCount(Long questionId);

	/**
	 *
	 * 描述: 批量增加收藏次数
	 *
	 * @author raolei
	 * @param questionIds
	 */
	int incBatchFavCount(@Param("questionIds") List<Long> questionIds);

	/**
	 * 增加点赞次数
	 * 
	 * @author liulongbiao
	 * @param questionId
	 * @return
	 */
	int incPraiseCount(Long questionId);

	/**
	 * 增加评价次数
	 * 
	 * @author liulongbiao
	 * @param questionId
	 * @return
	 */
	int incCommentCount(Long questionId);

	/**
	 * 查找个人习题
	 * 
	 * @author liulongbiao
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryPersonalQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找个人收藏习题
	 * 
	 * @author liulongbiao
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryFavoriteQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找学校习题
	 * 
	 * @author liulongbiao
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> querySchoolQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找联盟习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLeagueQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找乐课分享习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLekeShareQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找乐课精品习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryLekeBoutiqueQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找名师习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryFamousTeacherQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 查找名校习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<Long> queryFamousSchoolQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 *
	 * 描述: 统计习题
	 *
	 * @author raolei
	 * @created 2016年12月7日 下午4:54:56
	 * @return
	 */
	List<QuestionTotalResult> countInputQuestionFrom(AmountQuestionQuery query);

	/**
	 *
	 * 描述: 统计录入量
	 *
	 * @author raolei
	 * @created 2016年8月4日 上午10:32:20
	 * @since v1.0.0
	 * @param userId
	 * @param minCreatedOn
	 * @param maxCreatedOn
	 * @return
	 * @return long
	 */
	long countInputQuestion(AmountQuestionQuery query);

	/**
	 *
	 * 描述: 统计精品库未审核的习题
	 *
	 * @author raolei
	 * @created 2016年8月9日 下午3:07:59
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return long
	 */
	long countUnCheckedQuestionOfLekeBoutique(AmountQuestionQuery query);

	/**
	 * 添加习题到个人库
	 * 
	 * @param questionId
	 * @param userId
	 * @param modifiedBy
	 * @return
	 */
	int addToPersonal(@Param("questionId") Long questionId, @Param("userId") Long userId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题从个人库中移除
	 * 
	 * @param questionId
	 * @param userId
	 * @param modifiedBy
	 * @return
	 */
	int removeFromPersonal(@Param("questionId") Long questionId, @Param("userId") Long userId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 添加习题到乐课精品库
	 * 
	 * @param questionId
	 * @param modifiedBy
	 */
	int addToLekeBoutique(@Param("questionId") Long questionId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题从乐课精品库中移除
	 * 
	 * @param questionId
	 * @param modifiedBy
	 */
	int removeFromLekeBoutique(@Param("questionId") Long questionId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 添加习题到乐课分享库
	 * 
	 * @param questionId
	 * @param modifiedBy
	 */
	int addToLekeShare(@Param("questionId") Long questionId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题从乐课分享库中移除
	 * 
	 * @param questionId
	 * @param modifiedBy
	 */
	int removeFromLekeShare(@Param("questionId") Long questionId,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 添加习题到学校库
	 * 
	 * @param questionId
	 * @param modifiedBy
	 */
	int addToSchoolShare(@Param("questionId") Long questionId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 把习题从学校库中移除
	 * 
	 * @param questionId
	 * @param modifiedBy
	 */
	int removeFromSchoolShare(@Param("questionId") Long questionId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 更新 subjective
	 * 
	 * @param questionId
	 * @param subjective
	 * @return
	 */
	int updateSubjective(@Param("questionId") Long questionId, @Param("subjective") Boolean subjective);


	/**
	 *
	 * 描述: 查询乐课网最大习题值
	 *
	 * @author raolei
	 * @created 2016年8月30日 下午6:29:04
	 * @since v1.0.0
	 * @return
	 * @return int
	 */
	long maxLekeBoutiqueQuestionId();

	/**
	 *
	 * 描述: 修改满足条件的习题为审核通过
	 *
	 * @author raolei
	 * @created 2016年8月30日 下午5:39:24
	 * @since v1.0.0
	 * @param maxQuestionId
	 * @return
	 * @return int
	 */
	int updateStatusTask(@Param("minQuestionId") Long minQuestionId);

	/**
	 *
	 * 描述: 查询所有人员录入量统计
	 *
	 */
	List<InputStatisDTO> queryInputStatisDTO(InputStatisDTO query);
	
	
	/**
	 *
	 * 描述: 查询乐课教研员录入量统计
	 *
	 */
	List<InputStatisDTO> queryQuestionAmount(InputStatisDTO query);
	
	/**
	 *
	 * 描述: 查询整个乐课录入量统计
	 *
	 */
	Long queryQuestionTotalAmount(InputStatisDTO query);

	/**
	 *
	 * 描述: 设置习题优质等级
	 *
	 * @author raolei
	 * @created 2017年7月17日 下午3:16:01
	 */
	int setQuestionLevel(@Param("questionId") Long questionId, @Param("level") Integer level,
			@Param("userId") Long userId);

	int countLekeQuestion(RepositoryQuestionQuery query);

	List<RepoDayGroup> groupCreatedOnLekeQuestion(RepositoryQuestionQuery query);
}
