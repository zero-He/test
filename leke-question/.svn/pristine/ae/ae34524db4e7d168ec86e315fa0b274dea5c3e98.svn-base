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
import cn.strong.leke.model.incentive.Award;
import cn.strong.leke.model.paper.PaperDTO;
import cn.strong.leke.model.question.Question;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionFeedbackDTO;
import cn.strong.leke.model.question.QuestionKnowledge;
import cn.strong.leke.model.question.QuestionOfficialTag;
import cn.strong.leke.model.question.QuestionSelectRule;
import cn.strong.leke.model.question.StrengThenSelectRule;
import cn.strong.leke.model.question.UserQuestionDTO;
import cn.strong.leke.model.repository.QuestionRepositoryRecord;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.QuestionShareEvent;
import cn.strong.leke.question.word.model.WordQuestionInfo;
import cn.strong.leke.remote.model.question.SchoolQuestionContribution;
import cn.strong.leke.remote.model.question.SchoolQuestionContributionQuery;

/**
 * 
 * 描述: 题目管理服务接口
 * 
 * @author liulb
 * @created 2014年5月4日 上午10:29:55
 * @since v1.0.0
 */
public interface QuestionManager {

	/**
	 * 
	 * 描述: 根据ID获取整个题目信息
	 * 
	 * @author liulb
	 * @created 2014年5月4日 上午10:33:01
	 * @since v1.0.0
	 * @param questionId
	 * @return QuestionDTO
	 */
	QuestionDTO getQuestionDTO(Long questionId);

	/**
	 * 
	 * 描述: 添加题目
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午6:51:55
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	Long addQuestionDTO(QuestionDTO dto);

	/**
	 * 
	 * 描述: 修改题目
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午7:58:38
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void updateQuestionDTO(QuestionDTO dto, User user);
	
	/**
	 *	
	 * 描述:更新题目分享状态
	 *
	 * @author  lavender
	 * @created 2014年12月16日 下午12:51:02
	 * @since   v1.0.0 
	 * @param dto
	 * @return  void
	 */
	void updateQuestionShare(QuestionDTO dto);

	/**
	 * 
	 * 描述: 删除题目
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午8:21:57
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void deleteQuestionDTO(QuestionDTO dto);

	/**
	 * 
	 * 描述: 更新难易度
	 * 
	 * @author liulb
	 * @created 2014年8月25日 上午10:49:46
	 * @since v1.1.0
	 * @param question
	 */
	void updateDifficulty(Question question);

	/**
	 * 
	 * 描述: 添加题目知识点关联
	 * 
	 * @author liulb
	 * @created 2014年5月18日 上午11:12:38
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void addQuestionKnowledges(QuestionDTO dto);

	/**
	 * 
	 * 描述: 删除题目知识点关联
	 * 
	 * @author liulb
	 * @created 2014年8月8日 下午4:12:49
	 * @since v1.0.0
	 * @param assoc
	 * @return void
	 */
	void deleteQuestionKnowledge(QuestionKnowledge assoc);

	/**
	 * 
	 * 描述: 添加题目标签关联
	 * 
	 * @author liulb
	 * @created 2014年5月18日 上午11:13:01
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void addQuestionOfficialTags(QuestionDTO dto);

	/**
	 * 
	 * 描述: 删除题目标签关联
	 * 
	 * @author liulb
	 * @created 2014年8月8日 下午4:15:51
	 * @since v1.0.0
	 * @param assoc
	 * @return void
	 */
	void deleteQuestionOfficialTag(QuestionOfficialTag assoc);

	/**
	 * 
	 * 描述: 审核通过
	 * 
	 * @author liulb
	 * @created 2014年5月18日 下午6:31:49
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void updateCheck(QuestionDTO dto);

	/**
	 * 
	 * 描述: 退回录入
	 * 
	 * @author liulb
	 * @created 2014年5月19日 下午3:33:58
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void updateReject(QuestionDTO dto, String rejectReason);

	/**
	 * 
	 * 描述: 设置纠错
	 * 
	 * @author liulb
	 * @created 2014年5月19日 下午5:09:28
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void updateCorrect(QuestionDTO dto);

	/**
	 * 
	 * 描述: 校对通过
	 * 
	 * @author liulb
	 * @created 2014年5月19日 下午5:30:08
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void updateVerify(QuestionDTO dto);

	/**
	 * 教研员查询纠错反馈列表
	 * @param questionFeedbackDTO 
	 * @param researcher
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-6-17下午2:32:38
	 */
	List<UserQuestionDTO> queryFeedbackQuestions(QuestionFeedbackDTO questionFeedbackDTO, Researcher researcher,
			Page page);
	
	/**
	 * 教师查询纠错反馈列表
	 * @param userId
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-7-28下午3:56:50
	 */
	List<UserQuestionDTO> queryFeedbackQuestionsByUser(Long userId,
			Page page);
	
	/**
	 * 查询习题退回列表
	 * @param userId
	 * @param page
	 * @return
	 * author：lavender
	 * 2014-7-29下午4:28:52
	 */
	List<UserQuestionDTO> queryRejectionQuestionsByUser(Long userId,
			Page page);

	/**
	 * 
	 * 描述: 添加教师习题
	 * 
	 * @author liulb
	 * @created 2014年6月19日 下午5:04:07
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void addUserQuestionDTO(UserQuestionDTO dto);

	/**
	 * 
	 * 描述: 编辑教师习题
	 * 
	 * @author liulb
	 * @created 2014年6月19日 下午5:04:27
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void updateUserQuestionDTO(UserQuestionDTO dto);

	/**
	 * 
	 * 描述: 删除教师习题
	 * 
	 * @author liulb
	 * @created 2014年6月19日 下午5:04:42
	 * @since v1.0.0
	 * @param dto
	 * @return void
	 */
	void deleteUserQuestionDTO(UserQuestionDTO dto);

	
	/**
	 * 描述: 根据选题规则生成题目信息列表
	 * @author  andy
	 * @created 2014年6月18日 上午9:02:33
	 * @since   v1.0.0 
	 * @param questionSelectRule
	 * @return
	 * @return  QuestionSelectRule
	 */
	QuestionSelectRule randExerciseByRule(QuestionSelectRule questionSelectRule);
	
	/**
	 * 描述: 根据题目ID，获取强化练习题目列表。
	 * @author  andy
	 * @created 2014年6月19日 下午3:29:13
	 * @since   v1.0.0 
	 * @param strengThenSelectRule 选题规则
	 * @return
	 * @return StrengThenSelectRule
	 */
	StrengThenSelectRule randStrengThenByQuestionId(StrengThenSelectRule strengThenSelectRule);

	/**
	 * 
	 * 描述: 批量将题目放入缓存
	 * 
	 * @author liulb
	 * @created 2014年6月23日 下午5:19:37
	 * @since v1.0.0
	 * @return void
	 */
	void batchCacheQuestions();
	
	/**
	 * 禁用题目
	 * @param dto
	 * author：lavender
	 * 2014-7-28上午10:26:06
	 */
	void disableQuestion(QuestionDTO dto, User user);

	/**
	 * 
	 * 描述: 校对编辑习题；生成一道新题，并禁用原题；返回新题ID
	 * 
	 * @author liulb
	 * @created 2014年7月31日 下午3:41:21
	 * @since v1.1.0
	 * @param dto
	 * @return 新生产的习题ID
	 */
	Long updateQuestionOnVerify(QuestionDTO dto);

	/**
	 * 
	 * 描述: 创建新题，并禁用原题，返回新题ID
	 * 
	 * @author liulb
	 * @created 2014年8月12日 上午8:47:41
	 * @since v1.1.0
	 * @param dto
	 * @return
	 */
	Long updateReplaceQuestion(QuestionDTO dto, User user);
	
	/**
	 * 查询教师学校习题贡献
	 * @param query
	 * @return
	 * author：lavender
	 * 2014-8-11下午3:43:31
	 */
	List<SchoolQuestionContribution> findSchoolQuestionContributions(SchoolQuestionContributionQuery query);

	/**
	 * 
	 * 描述: 编辑已审核习题，生成一道新题，并禁用原题；返回新题ID
	 * 
	 * @author liulb
	 * @created 2014年9月24日 下午5:14:15
	 * @since v3.1
	 * @param dto
	 * @return
	 */
	Long updateQuestionOnEditChecked(QuestionDTO dto);

	/**
	 * 添加已审核的习题
	 * 
	 * @author liulb
	 * @created 2014年10月13日 下午3:04:52
	 * @since v3.1
	 * @param dto
	 * @return
	 */
	Long addCheckedQuestionDTO(QuestionDTO dto);

	/**
	 * 预处理答题卡习题
	 * 
	 * @author liulb
	 * @created 2014年11月5日 下午2:24:08
	 * @since v3.2
	 * @param paper
	 * @return
	 */
	PaperDTO preprocessAnswerSheetQuestions(PaperDTO paper);

	/**
	 * 添加导入的习题
	 * 
	 * @author liulongbiao
	 * @created 2014年12月13日 上午10:26:05
	 * @since v3.2
	 * @param dto
	 * @param info
	 */
	Long addImportedQuestion(QuestionDTO dto, WordQuestionInfo info);

	/**
	 * 更新导入习题
	 * 
	 * @author liulongbiao
	 * @created 2014年12月27日 下午2:41:03
	 * @since v3.2.2
	 * @param dto
	 * @param user
	 */
	void updateImportedQuestion(QuestionDTO dto, User user);

	void updateStatus(Long questionId, int status);

	void addLog(QuestionDTO dto, int type);

	/**
	 *	
	 * 描述:验证题目信息完整度
	 *
	 * @author  lavender
	 * @created 2015年1月9日 上午10:35:07
	 * @since   v1.0.0 
	 * @param dto
	 * @return  void
	 */
	void validateQueIntegrity(QuestionDTO dto);

	/**
	 * 验证题目信息完整度
	 * 
	 * @author liulongbiao
	 * @created 2015年1月14日 下午2:18:37
	 * @since v3.2.2
	 * @param questionId
	 */
	void validateQueIntegrity(Long questionId);

	void addLog(Long questionId, int type, User user);

	/**
	 * 获取习题共享信息
	 * 
	 * @author liulongbiao
	 * @param questionId
	 * @return
	 */
	QuestionShareEvent getQuestionShareInfo(Long questionId, User user);

	/**
	 * 处理习题共享
	 * 
	 * @author liulongbiao
	 * @param event
	 * @param user
	 *            操作用户
	 */
	Award updateQuestionShare(QuestionShareEvent event, User user);

	/**
	 *
	 * 描述: 教材章节的设置
	 *
	 * @author raolei
	 * @created 2016年8月3日 下午2:59:39
	 * @since v1.0.0
	 * @param dto
	 * @param user
	 * @return void
	 */
	void addQuestionSections(QuestionDTO dto, User user);

	/**
	 *
	 * 描述: 删除习题关联的教材
	 *
	 * @author raolei
	 * @created 2016年8月3日 下午4:10:13
	 * @since v1.0.0
	 * @param assoc
	 * @return void
	 */
	void deleteQuestionSection(Long quesSectionId, User user);

	/**
	 * 
	 *
	 * 描述: 查询feedback
	 *
	 * @author qw
	 * @created 2017年5月17日
	 * @since v1.0.0
	 * @param questionFeedbackDTO
	 * @param researcher
	 * @param page
	 * @return
	 * @return List<QuestionRepositoryRecord>
	 */
	List<QuestionRepositoryRecord> queryFeedbackQuestionRecord(QuestionFeedbackDTO questionFeedbackDTO,
			Researcher researcher, Page page);

	/**
	 * 
	 *
	 * 描述: 查询所有feedback
	 *
	 * @author qw
	 * @created 2017年5月17日
	 * @since v1.0.0
	 * @param questionFeedbackDTO
	 * @param researcher
	 * @param page
	 * @return
	 * @return List<QuestionRepositoryRecord>
	 */
	List<QuestionRepositoryRecord> queryAllFeedbackQuestionRecord(QuestionFeedbackDTO questionFeedbackDTO,
			Researcher researcher, Page page);
}
