/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IQuestionLogService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-24
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.SubjectMaterial;
import cn.strong.leke.question.model.VerifyStatisDTO;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionLogService {
	
	// --------------------------------------admin------------------------------------------//
	/**
	 * 查询录入量统计
	 * 
	 * @param inputStatisDTO
	 * @param userName
	 * @return
	 * @author：lavender 2014-4-23下午3:58:44
	 */
	List<InputStatisDTO> findInputAmountList(InputStatisQuery query,
			String userName);
		
		
	/**
	 * 根据用户id，查询每天录入量，按时间排序
	 * 
	 * @param userId
	 * @param inputStatisDTO
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> findInputAmountListByUserIdOrderByDate(Long userId,
 InputStatisQuery query);
		
		
	/**
	 * 查询审核量统计
	 * 
	 * @param inputStatisDTO
	 * @param userName
	 * @return
	 * @author：lavender 2014-4-23下午3:59:09
	 */
	List<InputStatisDTO> findCheckAmountList(InputStatisQuery query,
			String userName);
		

	/**
	 * 查询校对量统计
	 * 
	 * @param inputStatisDTO
	 * @param userName
	 * @return
	 * @author：lavender 2014-4-23下午3:59:20
	 */
	List<InputStatisDTO> findVerifyAmountList(InputStatisQuery query, String userName);

	/**
	 * 根据用户id，查询每天校对量，按时间排序
	 * 
	 * @param userId
	 * @param inputStatisDTO
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> findProofreadingAmountListByUserIdOrderByDate(Long userId, InputStatisQuery query);

	/**
	 *	
	 * 描述:根据用户学段学科查询导题未审核
	 *
	 * @author  lavender
	 * @created 2015年1月16日 上午8:59:54
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  InputStatisDTO
	 */
	InputStatisDTO findUnVerifyAmountBySchoolStageSubjects(Long userId, InputStatisQuery query);

	/**
	 *	
	 * 描述:按教材统计
	 *
	 * @author  lavender
	 * @created 2015年1月16日 下午1:19:49
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<SubjectMaterial> findQuestionAmountByMaterial(InputStatisQuery query);
	/**
	 * 根据知识点，查询题量（用于正式题量统计下的按知识点统计）
	 * 
	 * @return
	 * @author：lavender 2014-4-23下午4:44:33
	 */
	List<InputStatisDTO> findStatisListGroupByKnowledge();
		
	/**
	 * 根据题库标签分组查询题量（用于正式题量统计下的按标签统计）
	 * 
	 * @return
	 * @author：lavender 2014-4-23下午5:00:26
	 */
	List<InputStatisDTO> findStatisGroupByOfficialTagId();

	/**
	 * 根据题库标签查询各年级科目题量（用于正式题量统计下的按标签统计-年级科目）
	 * 
	 * @return
	 * @author：lavender 2014-4-23下午5:00:26
	 */
	List<InputStatisDTO> findStatisByOfficialTagId(Long officialTag);
		
	/**
	 * 根据类型，查询统计信息（用于管理员首页）
	 * 
	 * @param type
	 *            ，1为正式题， 2为草稿题
	 * @return
	 * @author：lavender 2014-4-24上午9:22:55
	 */
	InputStatisDTO findStatisByType(Integer type);
		
	/**
	 * 草稿题库查询
	 * 
	 * @return
	 * @author：lavender 2014-5-5下午2:32:57
	 */
	List<InputStatisDTO> findDraftAmountList();
		
	/**
	 * 查询草稿题量和正式题量
	 * 
	 * @return
	 * @author：lavender 2014-5-6上午10:28:02
	 */
	InputStatisDTO findDraftAndFormalTotalAmount();
		
	// --------------------------------------checker------------------------------------------//
	/**
	 * 根据用户id，查询审核量统计,待审核量及昨日审核量（用于审核员首页）
	 * 
	 * @param userId
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	InputStatisDTO findCheckAmountByUserId(Long userId);
		
	/**
	 * 根据用户id，查询审核量统计
	 * 
	 * @param userId
	 * @param inputStatisDTO
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> findCheckAmountListByUserId(Long userId,
 InputStatisQuery query);
		
		
	// --------------------------------------inputer------------------------------------------//
	/**
	 * 根据用户id，查询录入量统计，及昨日录入量（用于录入员首页）
	 * 
	 * @param userId
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	InputStatisDTO findInputAmountByUserId(Long userId);
		
	/**
	 * 根据用户id，查询录入量统计
	 * 
	 * @param userId
	 * @param inputStatisDTO
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> findInputAmountListByUserId(Long userId,
 InputStatisQuery inputStatisDTO);

	// --------------------------------------researcher---------------------------------------//
		
	/**
	 * 根据用户id，查询校对量统计（用于教研员首页）
	 * 
	 * @param userId
	 * @return
	 * @author：lavender 2014-4-24上午9:12:46
	 */
	InputStatisDTO findProofreadingAmountByUserId(Long userId);
		
	/**
	 * 
	 * 描述: 添加日志
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午5:08:43
	 * @since v1.0.0
	 * @param questionLog
	 * @return void
	 */
	void addQuestionLog(QuestionLog questionLog);

	/**
	 * 
	 * 描述: 根据题目查找日志列表
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午5:10:19
	 * @since v1.0.0
	 * @param questionId
	 * @return
	 * @return List<QuestionLog>
	 */
	List<QuestionLog> findQuestionLogByQuestionId(Long questionId);

	/**
	* 
	* 描述: 查询指定年级学科的待校对量统计
	* 
	* @author liulb
	* @created 2014年5月30日 下午1:39:36
	* @since v1.0.0
	* @param gradeSubjects
	* @return List<VerifyStatisDTO>
	*/
	List<VerifyStatisDTO> findResearcherVerifyStatis(List<SchoolStageSubject> schoolStageSubjects);

	/**
	 *	
	 * 描述:查询教研员审核统计
	 *
	 * @author  lavender
	 * @created 2014年11月3日 下午4:17:53
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	public List<InputStatisDTO> findResearcherCheckAmountList(InputStatisQuery query);

	/**
	 *	
	 * 描述:根据教研员id查询审核统计
	 *
	 * @author  lavender
	 * @created 2014年11月3日 下午4:18:11
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	public List<InputStatisDTO> findResearcherCheckAmountListByUserId(InputStatisQuery query);

	/**
	 *	
	 * 描述:审核员审核量统计
	 *
	 * @author  lavender
	 * @created 2014年12月31日 上午9:37:02
	 * @since   v1.0.0 
	 * @param statisDTO
	 * @param userId
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<InputStatisDTO> findCheckerCheckAmountList(InputStatisQuery query, Long userId);

	/**
	 *	
	 * 描述:查询教研员导题统计
	 *
	 * @author  lavender
	 * @created 2014年11月3日 下午4:17:53
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	public List<InputStatisDTO> findResearcherImportedAmountList(InputStatisQuery query);

	/**
	 *	
	 * 描述:根据教研员id查询导题统计
	 *
	 * @author  lavender
	 * @created 2014年11月3日 下午4:18:11
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	public List<InputStatisDTO> findResearcherImportedAmountListByUserId(InputStatisQuery query);

	/**
	 * 查询录入量统计
	 * 
	 * @param query
	 * @param userName
	 * @return
	 * @author：qw
	 */
	List<InputStatisDTO> findInputAmountExecuteList(InputStatisQuery query, String userName);

	/**
	 * 根据用户id，查询每天录入量，按时间排序
	 * 
	 * @param userId
	 * @param inputStatisDTO
	 * @return
	 * @author：lavender 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> findInputAmountExecuteListByUserIdOrderByDate(Long userId, InputStatisQuery query);

}