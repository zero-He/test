/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IQuestionLogDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-23
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;
import java.util.Map;

import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.model.user.Researcher;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.MaterialAmount;
import cn.strong.leke.question.model.MaterialQuestionAmount;
import cn.strong.leke.question.model.VerifyStatisDTO;

/**
 * 题目日志数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IQuestionLogDao {

	//--------------------------------------admin---------------------------------------------//
	
	/**
	 * 查询录入量统计
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午3:58:44
	 */
	List<InputStatisDTO> queryInputAmountList(InputStatisQuery query);
	/**
	 * 查询录入量统计有效量
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午3:58:44
	 */
	List<InputStatisDTO> queryInputAmountEffectiveList(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询每天录入量，按时间排序
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> queryInputAmountListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询每天录入量有效量，按时间排序
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> queryInputAmountEffectiveListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 *
	 * 描述: 统计审核的记录
	 *
	 * @author raolei
	 * @created 2016年8月9日 下午3:52:22
	 * @since v1.0.0
	 * @param query
	 * @return
	 * @return long
	 */
	long countCheckAmount(InputStatisQuery query);

	/**
	 * 查询审核量统计
	 * 
	 * @param startDate
	 * @param endDate
	 * @return author：lavender 2014-4-23下午3:59:09
	 */
	List<InputStatisDTO> queryCheckAmountList(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询每天审核量，按时间排序
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> queryCheckAmountListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 * 查询导题审核的量
	 * @param query
	 * @return
	 * author：lavender
	 * 2014-5-9下午3:20:40
	 */
	List<InputStatisDTO> queryVerifyAmountList(InputStatisQuery query);

	/**
	 *	
	 * 描述:查询导题未审核的量
	 *
	 * @author  lavender
	 * @created 2015年1月15日 上午10:52:38
	 * @since   v1.0.0 
	 * @param researchers
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<InputStatisDTO> queryUnVerifyAmountList(List<Researcher> researchers);
	
	/**
	 *	
	 * 描述:根据用户id查询导题审核量
	 *
	 * @author  lavender
	 * @created 2015年1月15日 下午4:07:37
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<InputStatisDTO> queryVerifyAmountListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 *	
	 * 描述:根据用户学段学科查询导题未审核（教研员）状态为17
	 *
	 * @author  lavender
	 * @created 2015年1月16日 上午8:59:54
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  InputStatisDTO
	 */
	InputStatisDTO queryUnVerifyAmountBySchoolStageSubjects(InputStatisQuery query);

	/**
	 *	
	 * 描述:根据用户学段学科查询未审核数据（教研员）状态为1
	 *
	 * @author  lavender
	 * @created 2015年1月20日 上午10:25:45
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  InputStatisDTO
	 */
	InputStatisDTO queryUnCheckAmountBySchoolStageSubjects(InputStatisQuery query);

	/**
	 * 查询校对量统计
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午3:59:20
	 */
	List<InputStatisDTO> queryProofreadingAmountList(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询每天校对量，按时间排序
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> queryProofreadingAmountListByUserIdOrderByDate(InputStatisQuery query);
	
	/**
	 * 根据知识点，查询题量（用于正式题量统计下的按知识点统计）
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:44:33
	 */
	List<InputStatisDTO> queryStatisListGroupByKnowledge();
	
	/**
	 * 根据题库标签分组查询题量（用于正式题量统计下的按标签统计）
	 * @return
	 * author：lavender
	 * 2014-4-23下午5:00:26
	 */
	List<InputStatisDTO> queryStatisGroupByOfficialTagId();
	
	/**
	 * 根据题库标签查询各年级科目题量（用于正式题量统计下的按标签统计-年级科目）
	 * @return
	 * author：lavender
	 * 2014-4-23下午5:00:26
	 */
	List<InputStatisDTO> queryStatisByOfficialTagId(Map<String, Object> map);
	
	/**
	 * 根据类型，查询统计信息（用于管理员首页）
	 * @param type，1为正式题， 2为草稿题
	 * @return
	 * author：lavender
	 * 2014-4-24上午9:22:55
	 */
	InputStatisDTO queryStatisByType(Integer type);
	
	/**
	 * 草稿题库查询
	 * @return
	 * author：lavender
	 * 2014-5-5下午2:32:57
	 */
	List<InputStatisDTO> queryDraftAmountList();
	
	/**
	 *  查询草稿题量和正式题量
	 * @return
	 * author：lavender
	 * 2014-5-6上午10:28:02
	 */
	InputStatisDTO queryDraftAndFormalTotalAmount();

	//--------------------------------------checker------------------------------------------//
	/**
	 * 根据用户id，查询审核量统计,及昨日审核量（用于审核员首页）
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	InputStatisDTO queryCheckAmountByUserId(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询未审核量（用于审核员首页）
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	InputStatisDTO queryUnCheckAmountByUserId(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询审核量统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> queryCheckAmountListByUserId(InputStatisQuery query);
	
	
	//--------------------------------------inputer------------------------------------------//
	/**
	 * 根据用户id，查询录入量统计，及昨日录入量（用于录入员首页）
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	InputStatisDTO queryInputAmountByUserId(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询录入量统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	List<InputStatisDTO> queryInputAmountListByUserId(InputStatisQuery query);
	
	//--------------------------------------researcher---------------------------------------//
	
	
	/**
	 * 根据用户id，查询校对量统计（用于教研员首页）
	 * @param userId
	 * @return
	 * author：lavender
	 * 2014-4-24上午9:12:46
	 */
	InputStatisDTO queryProofreadingAmountByUserId(InputStatisQuery query);
	
	/**
	 * 查询需要被校对的量
	 * @param inputStatisDTO
	 * @return
	 * author：lavender
	 * 2014-5-9下午3:20:40
	 */
	List<InputStatisDTO> queryUserNeedToProofreadingAmountList(InputStatisQuery query);
	
	/**
	 * 根据用户id，查询校对量统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * author：lavender
	 * 2014-4-23下午4:01:45
	 */
	InputStatisDTO queryProofreadingAmountListByUserId(InputStatisQuery query);
	
	/**
	 * 
	 * 描述: 添加日志
	 * 
	 * @author liulb
	 * @created 2014年5月4日 下午5:14:56
	 * @since v1.0.0
	 * @param questionLog
	 * @return int
	 */
	int insertQuestionLog(QuestionLog questionLog);

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
	 * @param schoolStageSubjects
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
	List<InputStatisDTO> queryResearcherCheckAmountList(InputStatisQuery query);

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
	List<InputStatisDTO> queryResearcherCheckAmountListByUserId(InputStatisQuery query);

	/**
	 *	
	 * 描述:查询单个审核员有效量列表
	 *
	 * @author  lavender
	 * @created 2014年12月31日 上午10:29:02
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<InputStatisDTO> queryCheckerEffectiveAmountListByUserId(InputStatisQuery query);

	/**
	 *	
	 * 描述:查询所有审核员有效量列表
	 *
	 * @author  lavender
	 * @created 2014年12月31日 下午1:48:30
	 * @since   v1.0.0 
	 * @param inputStatisDTO
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<InputStatisDTO> queryCheckerEffectiveAmountList(InputStatisQuery query);

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
	List<InputStatisDTO> queryResearcherImportedAmountList(InputStatisQuery query);

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
	List<InputStatisDTO> queryResearcherImportedAmountListByUserId(InputStatisQuery query);

	/**
	 *	
	 * 描述:按教材统计
	 *
	 * @author  lavender
	 * @created 2015年1月16日 下午1:14:13
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  List<InputStatisDTO>
	 */
	List<MaterialAmount> queryQuestionAmountByMaterial(InputStatisQuery query);

	/**
	 *	
	 * 描述:根据学段学科查找教材已审核题目
	 *
	 * @author  lavender
	 * @created 2015年1月22日 上午8:47:14
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  List<MaterialQuestion>
	 */
	List<MaterialQuestionAmount> queryQuestionAndMaterialChecked(InputStatisQuery query);

	/**
	 *	
	 * 描述:根据学段学科查找教材已审核题目
	 *
	 * @author  lavender
	 * @created 2015年1月22日 上午8:47:14
	 * @since   v1.0.0 
	 * @param query
	 * @return
	 * @return  List<MaterialQuestion>
	 */
	List<MaterialQuestionAmount> queryQuestionAndMaterialUnChecked(InputStatisQuery query);
}
