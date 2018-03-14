/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.model.CheckQuestionQuery;
import cn.strong.leke.question.model.CheckerQuestionQuery;
import cn.strong.leke.question.model.DraftQuestionQuery;
import cn.strong.leke.question.model.InputerQuestionQuery;
import cn.strong.leke.question.model.PublishedQuestionQuery;
import cn.strong.leke.question.model.ResearcherQuestionQuery;
import cn.strong.leke.question.model.ReviewQuestionQuery;
import cn.strong.leke.question.model.StatisQuestionQuery;
import cn.strong.leke.question.model.TeacherShareQuestionQuery;
import cn.strong.leke.remote.model.question.SchoolQuestionContribution;
import cn.strong.leke.remote.model.question.SchoolQuestionContributionQuery;

/**
 * 
 * 描述: 题目服务接口
 * 
 * @author liulb
 * @created 2014年5月4日 上午10:29:38
 * @since v1.0.0
 */
public interface QuestionService {

	/**
	 * 
	 * 描述: 添加题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:05:29
	 * @since v1.0.0
	 * @param question
	 * @return void
	 */
	void addQuestion(Question question);

	/**
	 * 
	 * 描述: 更新题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:05:48
	 * @since v1.0.0
	 * @param question
	 * @return void
	 */
	void updateQuestion(Question question);
	
	/**
	 *	
	 * 描述:更新题目分享状态
	 *
	 * @author  lavender
	 * @created 2014年12月16日 下午12:54:12
	 * @since   v1.0.0 
	 * @param question
	 * @return  void
	 */
	void updateQuestionShare(Question question);

	/**
	 * 
	 * 描述: 删除题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:05:57
	 * @since v1.0.0
	 * @param question
	 * @return void
	 */
	void deleteQuestion(Question question);

	/**
	 * 
	 * 描述: 根据ID获取题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午11:06:07
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
	 * @created 2014年5月4日 下午4:42:47
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
	List<QuestionDTO> queryInputerQuestions(InputerQuestionQuery query,
			Page page);

	/**
	 * 
	 * 描述: 查询审核人员题目
	 * 
	 * @author liulb
	 * @created 2014年5月16日 下午4:42:58
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return List<QuestionDTO>
	 */
	List<QuestionDTO> queryCheckerQuestions(CheckerQuestionQuery query,
			Page page);

	/**
	 * 
	 * 描述: 更新难度值
	 * 
	 * @author liulb
	 * @created 2014年5月18日 上午10:49:45
	 * @since v1.0.0
	 * @param question
	 * @return void
	 */
	void updateDifficulty(Question question);

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
	List<QuestionDTO> queryResearcherQuestions(ResearcherQuestionQuery query,
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
	List<QuestionDTO> queryStatisQuestions(StatisQuestionQuery query, Page page);

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
	List<QuestionDTO> queryDraftQuestions(DraftQuestionQuery query, Page page);

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
	List<QuestionDTO> queryPublishedQuestions(PublishedQuestionQuery query,
			Page page);
	
	
	/**
	 * 教研员查询纠错反馈列表
	 * @param researcher
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:32:38
	 */
	List<UserQuestionDTO> queryFeedbackQuestions(QuestionFeedbackDTO questionFeedbackDto, Researcher researcher,
			Page page);
	
	/**
	 * 教师查询纠错反馈列表
	 * @param userId
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-7-28下午3:54:53
	 */
	List<UserQuestionDTO> queryFeedbackQuestionsByUser(Long userId,
			Page page);
	
	/**
	 * 查询习题退回列表
	 * @param userId
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-7-29下午4:31:11
	 */
	List<UserQuestionDTO> queryRejectionQuestionsByUser(Long userId,
			Page page);

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
	 * 描述: 修改题型
	 * 
	 * @author liulb
	 * @created 2014年7月2日 下午4:59:00
	 * @since v1.0.0
	 * @param question
	 * @return void
	 */
	void updateQuestionType(Question question);

	/**
	 * 
	 * 描述: 查询类型不匹配的录入人员题目
	 * 
	 * @author liulb
	 * @created 2014年7月4日 下午1:28:56
	 * @since v1.0.0
	 * @param query
	 * @param page
	 * @return List<QuestionDTO>
	 */
	List<QuestionDTO> queryTypeMismatchInputerQuestions(
			InputerQuestionQuery query, Page page);

	/**
	 * 
	 * 描述: 获取包含日志的题目信息
	 * 
	 * @author liulb
	 * @created 2014年7月4日 下午2:43:19
	 * @since v1.0.0
	 * @param questionId
	 * @return QuestionDTO
	 */
	QuestionDTO getQuestionWithLog(Long questionId);
	
	/**
	 * 禁用习题
	 * @param question
	 * author：lavender
	 * 2014-7-28上午10:28:19
	 */
	void disableQuestion(Question question);

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
	List<QuestionDTO> queryReviewQuestions(ReviewQuestionQuery query, Page page);

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
	List<UserQuestionDTO> queryTeacherShareQuestions(
			TeacherShareQuestionQuery query, Page page);
	
	/**
	 * 查询教师学校习题贡献
	 * @param query
	 * @return
	 * author：lavender
	 * 2014-8-11下午3:45:05
	 */
	List<SchoolQuestionContribution> findSchoolQuestionContributions(SchoolQuestionContributionQuery query);

	/**
	 *	
	 * 描述:查询习题数量
	 *
	 * @author  lavender
	 * @created 2014年8月28日 上午9:54:39
	 * @since   v1.0.0 
	 * @return
	 * @return  Long
	 */
	Long findQuestionCount();

	/**
	 * 
	 * 描述: 查询审核页面习题
	 * 
	 * @author liulb
	 * @created 2014年9月24日 下午3:00:26
	 * @since v1.1.0
	 * @param query
	 * @param page
	 * @return
	 */
	List<QuestionDTO> queryQuestionsForCheck(CheckQuestionQuery query, Page page);

	/**
	 * 更新习题状态
	 * 
	 * @author liulb
	 * @created 2014年11月18日 上午11:10:57
	 * @since v1.1.0
	 * @param que
	 */
	void updateQuestionStatus(Question que);

	/**
	 * 更新备注
	 * 
	 * @author liulongbiao
	 * @created 2014年12月18日 下午2:19:54
	 * @since v3.2.1
	 * @param questionId
	 * @param note
	 */
	void updateNote(Long questionId, String note);

	/**
	 *	
	 * 描述:教研员查询习题列表
	 *
	 * @author  lavender
	 * @created 2015年1月4日 下午1:43:58
	 * @since   v1.0.0 
	 * @param query
	 * @param page
	 * @return
	 * @return  List<QuestionDTO>
	 */
	List<QuestionDTO> queryResearcherQuestionList(ResearcherQuestionQuery query, Page page);

	/**
	 *
	 * 描述:增加收藏次数
	 *
	 * @author raolei
	 * @created 2016年6月13日 下午5:19:16
	 * @since v1.0.0
	 * @param questionId
	 * @return void
	 */
	void updateIncFavCount(Long questionId);

	/**
	 *
	 * 描述:批量增加收藏次数
	 *
	 * @author raolei
	 * @created 2016年6月13日 下午5:24:28
	 * @since v1.0.0
	 * @param questionIds
	 * @return void
	 */
	void updateBatchIncFavCount(List<Long> questionIds);

	/**
	 * 增加使用次数
	 * 
	 * @author liulongbiao
	 * @param questionId
	 */
	void updateIncUsedCount(Long questionId);

	/**
	 * 增加点赞次数
	 * 
	 * @author liulongbiao
	 * @param questionId
	 */
	void updateIncPraiseCount(Long questionId);

	/**
	 * 增加评论次数
	 * 
	 * @author liulongbiao
	 * @param questionId
	 */
	void updateIncCommentCount(Long questionId);

	/**
	 * 查找个人习题
	 * 
	 * @author liulongbiao
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryPersonalQuestions(
			RepositoryQuestionQuery query, Page page);

	/**
	 * 查找个人收藏习题
	 * 
	 * @author liulongbiao
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryFavoriteQuestions(
			RepositoryQuestionQuery query, Page page);

	/**
	 * 查找学校习题
	 * 
	 * @author liulongbiao
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> querySchoolQuestions(RepositoryQuestionQuery query,
			Page page);

	/**
	 * 查找联盟习题
	 * 
	 * @param query
	 * @param page
	 * @return
	 */
	List<RepositoryRecord> queryLeagueQuestions(RepositoryQuestionQuery query, Page page);

	/**
	 * 
	 *
	 * 描述: 查询所有纠错问题
	 *
	 * @author qw
	 * @created 2017年5月17日
	 * @since v1.0.0
	 * @param questionFeedbackDto
	 * @param researcher
	 * @param page
	 * @return
	 * @return List<UserQuestionDTO>
	 */
	List<UserQuestionDTO> queryAllFeedbackQuestions(QuestionFeedbackDTO questionFeedbackDto, Researcher researcher,
			Page page);
}
