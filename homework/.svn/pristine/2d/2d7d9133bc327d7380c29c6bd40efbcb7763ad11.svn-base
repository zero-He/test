package cn.strong.leke.homework.dao.mybatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.ApiStudentHomeworkListDTO;
import cn.strong.leke.homework.model.CorrectHomeworkDtlInfo;
import cn.strong.leke.homework.model.HDStuHomeworkListDTO;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlDTO;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkDtlQuery;
import cn.strong.leke.homework.model.HomeworkProgressMQ;
import cn.strong.leke.homework.model.MHomeworkDtlInfo;
import cn.strong.leke.homework.model.MyHomeworkDTO;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.StuHomeworkDtl;
import cn.strong.leke.homework.model.StuHwByDayDTO;
import cn.strong.leke.homework.model.StuSubjRes;
import cn.strong.leke.homework.model.StudentHomeworkQuery;
import cn.strong.leke.homework.model.StudentReviewInfo;
import cn.strong.leke.homework.model.SubjectHwStatistics;
import cn.strong.leke.homework.model.WeekMonthHwSubject;
import cn.strong.leke.homework.model.activity.MonthHwInfo;
import cn.strong.leke.homework.model.mobile.MPersonWorkCount;
import cn.strong.leke.homework.model.mobile.MPersonWorkDTO;
import cn.strong.leke.homework.model.mobile.MPersonWorkQuery;
import cn.strong.leke.homework.model.query.ApiParamForStuHomework;
import cn.strong.leke.homework.model.query.HomeworkIncentiveQuery;
import cn.strong.leke.homework.model.query.WeekMonthHwQuery;
import cn.strong.leke.model.common.StuTreeModel;
import cn.strong.leke.model.user.SimpleUser;
import cn.strong.leke.model.user.UserBase;

public interface HomeworkDtlDao {

	/**
	 * 保存作业明细信息
	 * @param homework
	 * @param users
	 */
	void insertHomeworkDtlBySimpleUser(@Param("homework") Homework homework, @Param("users") List<SimpleUser> users);

	/**
	 *	
	 * 描述:
	 *
	 * @author  basil
	 * @created 2014-6-11 下午2:04:11
	 * @since   v1.0.0 
	 * @param stuList
	 * @param homework
	 * @return  void
	 */
	void insertHomeworkDtl(@Param("stuList") List<StuTreeModel> stuList, @Param("homework") Homework homework);

	/**
	 *	
	 * 描述:
	 *
	 * @author  basil
	 * @created 2014-6-11 下午2:04:17
	 * @since   v1.0.0 
	 * @param userList
	 * @param homework
	 * @return  void
	 */
	void insertHomeworkDtlForBind(@Param("stuList") List<UserBase> userList, @Param("homework") Homework homework);

	/**
	 * 描述: 保存学生作业信息
	 * @author  andy
	 * @created 2014年7月10日 上午11:32:25
	 * @since   v1.0.0 
	 * @param homeworkDtl
	 * @return  void
	 */
	public void saveHomeworkDtl(HomeworkDtl homeworkDtl);

	/**
	 * 
	 * 描述:多条件查询学生作业列表
	 *
	 * @author  C.C
	 * @created 2014-6-10 下午3:56:02
	 * @since   v1.0.0 
	 * @return
	 * @return  List<HomeworkDtlDTO>
	 */
	public List<HomeworkDtlDTO> queryHomeworkDtlList(@Param("homeworkId") Long homeworkId,
			@Param("studentName") String stuName);

	/**
	 *	
	 * 描述:
	 *
	 * @author  basil
	 * @created 2014-6-13 下午3:37:11
	 * @since   v1.0.0 
	 * @param homeworkId
	 * @return
	 * @return  List<HomeworkDtlDTO>
	 */
	List<HomeworkDtlDTO> queryHwDtl(Long homeworkId);

	/**
	 *	
	 * 描述:返回我的作业列表（学生）
	 *
	 * @author  DuanYanming
	 * @created 2014年6月12日 上午10:13:07
	 * @since   v1.0.0 
	 * @param myExerciseDTO
	 * @return
	 * @return  List<MyExerciseDTO>
	 */
	public List<MyHomeworkDTO> getMyHomeworkList(HomeworkDtlQuery homeworkDtlQuery, Page page);

	/**
	 * 根据数据ID查询数据对象
	 * @param homeworkDtlId
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlById(Long homeworkDtlId);

	/**
	 * 根据学生学生作业ID，获取作业信息
	 * @param homeworkDtlId
	 * @return
	 */
	public HomeworkDtlInfo getHomeworkDtlInfoById(Long homeworkDtlId);

	/**
	 * 根据作业ID和学生ID查询学生作业信息
	 * @param homeworkId 作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public HomeworkDtlInfo getHomeworkDtlInfoByHomeworkIdAndStudentId(@Param("homeworkId") Long homeworkId,
			@Param("studentId") Long studentId);

	/**
	 * 更新学生作业信息。
	 * @param homeworkDtl
	 * @return
	 */
	public void updateHomeworkDtl(HomeworkDtl homeworkDtl);

	/**
	 * 乐观锁判定
	 * @param homeworkDtlId 学生作业ID
	 * @param version 版本号
	 * @return
	 */
	public int updateHomeworkDtlVersion(@Param("homeworkDtlId") Long homeworkDtlId, @Param("version") Integer version);

	/**
	 * 更新学生作业的开始答题时间
	 * @param homeworkDtlId
	 */
	public void updateHomeworkDtlStartTime(Long homeworkDtlId);

	/**
	 * 更新学生作业的修改时间
	 * @param homeworkDtlId
	 */
	public void updateHomeworkDtlModifiedOn(Long homeworkDtlId);

	/**
	 * 更新作业的排序时间
	 * @param homeworkId 作业ID
	 * @param orderTime 排序时间
	 */
	public void updateHomeworkDtlOrderTime(@Param("homeworkId") Long homeworkId, @Param("orderTime") Date orderTime);

	/**
	 * 更新学生作业信息
	 * @param homeworkDtlInfo
	 */
	public void updateHomeworkDtlInfo(HomeworkDtlInfo homeworkDtlInfo);

	/**
	 * 更新作业批改进度。
	 * @param homeworkProgressMQ
	 */
	public void updateHomeworkDtlProgress(HomeworkProgressMQ homeworkProgressMQ);

	/**
	 * 根据作业ID和学生ID查询学生作业信息
	 * @param homeworkId 作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlByHomeworkIdAndStudentId(@Param("homeworkId") Long homeworkId,
			@Param("studentId") Long studentId);

	/**
	 * 根据上级作业ID和学生ID查询学生作业信息
	 * @param parentId 作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlByParentIdAndStudentId(@Param("parentId") Long parentId,
			@Param("studentId") Long studentId);

	/**
	 * 根据作业标识查询学生作业ID列表
	 * @param homeworkId 作业标识
	 * @return
	 */
	public List<Long> findFinishedStudentIdsByHomeworkId(Long homeworkId);

	/**
	 * 根据作业标识查询学生作业ID列表
	 * @param homeworkId 作业标识
	 * @return
	 */
	public List<Long> findUnfinishStudentIdsByHomeworkId(Long homeworkId);
	
	/**
	 * 根据作业标识查询学生作业列表
	 * @param homeworkId 作业标识
	 * @return
	 */
	public List<HomeworkDtl> findHomeworkDtlListByHomeworkId(@Param("homeworkId") Long homeworkId);

	/**
	 * 描述: 根据老师查询未批改的作业数量和未解答的提问数量
	 * @author  DuanYanming
	 * @created 2014年9月18日 上午10:37:48
	 * @since   v1.0.0 
	 * @return  Map<String,Integer>
	 */
	public Map<String, Integer> queryHwNum(Long teacherId);

	/**
	 * 描述:根据日期和学生标识，查询学生作业列表。（以截止时间为准）
	 * @author  DuanYanming
	 * @created 2014年9月18日 下午6:24:32
	 * @since   v1.0.0 
	 * @param studentId
	 * @param headTime
	 * @param tailTime
	 * @return  List<ApiHwCount>
	 */
	public List<StuHwByDayDTO> queryStuHwByDay(@Param("studentId") Long studentId, @Param("headTime") Date headTime,
			@Param("tailTime") Date tailTime);

	/**
	 * 根据条件查询作业列表。
	 * @param query
	 * @return
	 */
	public List<StuHwByDayDTO> findStuHomeworkList(HomeworkDtlQuery query);

	/**
	 * 根据条件查询作业列表。
	 * @param query
	 * @return
	 */
	public List<ApiStudentHomeworkListDTO> findStuHomeworkList_v1(ApiParamForStuHomework query);

	/**
	 * 根据作业ID获取作业信息。
	 * @param homeworkDtlId
	 * @return
	 */
	public StuHwByDayDTO getStuHomeworkByHomeworkDtlId(Long homeworkDtlId);

	/**
	 * 根据老师作业ID和学生ID获取作业信息。
	 * @param homeworkId 老师作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public StuHwByDayDTO getStuHomeworkByHomeworkIdAndStudentId(@Param("homeworkId") Long homeworkId,
			@Param("studentId") Long studentId);

	/**
	 * 查询上一份作业。
	 * @param homeworkId 老师作业ID
	 * @param homeworkDtlId 学生作业ID
	 * @return
	 */
	public Long getPrevHomeworkId(@Param("homeworkId") Long homeworkId, @Param("homeworkDtlId") Long homeworkDtlId);

	/**
	 * 查询下一份作业。
	 * @param homeworkId 老师作业ID
	 * @param homeworkDtlId 学生作业ID
	 * @return
	 */
	public Long getNextHomeworkId(@Param("homeworkId") Long homeworkId, @Param("homeworkDtlId") Long homeworkDtlId);

	/**
	 * 查询下一份待批改作业(无序)。
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	public Long getNextCorrectHomeworkId(@Param("homeworkId") Long homeworkId);
	/**
	 * 查询下一份待批改作业
	 * @param correctUserId 批改人
	 * @return
	 */
	public List<Long> findHwByCorrectUser(@Param("correctUserId") Long correctUserId);

	/**
	 * 查询下一份待复批作业(无序)。
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	public Long getNextReviewHomeworkId(@Param("homeworkId") Long homeworkId);

	/**
	 * 根据学生ID查询作业的学科分类数量
	 * @param studentId 学生ID
	 * @return
	 */
	public List<StuSubjRes> findStuSubjResByStudentId(@Param("studentId") Long studentId,
			@Param("subjectId") Long subjectId, @Param("startDate") Date startDate);
	
	/**
	 * 根据作业ID和得分，查询该分数在作业中的排名情况
	 * @param homeworkId 作业ID
	 * @param score 得分
	 * @return
	 */
	public Integer getRankByHomeworkIdAndScore(@Param("homeworkId") Long homeworkId, @Param("score") BigDecimal score);

	/**
	 * 复批时更新作业订正状态
	 * @param homeworkDtlId
	 * @param bugFixStage
	 * @param reviewSource
	 * @param modifiedBy
	 * @return
	 */
	public int updateBugFixStageWithReview(@Param("homeworkDtlId") Long homeworkDtlId,
			@Param("bugFixStage") Integer bugFixStage, @Param("reviewSource") Integer reviewSource,
			@Param("modifiedBy") Long modifiedBy);

	/**
	 * 根据作业ID查询需要复批的作业明细ID列表
	 * @param homeworkId
	 * @return
	 */
	public List<Long> getPreReviewIdByHomeworkId(Long homeworkId);

	/**
	 * 查询某个学生的作业信息（按照学科分组的统计的待完成和待订正的）
	 * @param studentId
	 * @return
	 */
	public List<SubjectHwStatistics> getSubjectHomeworkInfo(@Param("studentId") Long studentId);

	/**
	 * 更新学生作业未待订正状态 
	 * @param homeworkId
	 * @param modifiedBy
	 * @return
	 */
	public int updateBugFixStageRevised(@Param("homeworkId") Long homeworkId, @Param("modifiedBy") Long modifiedBy);

	/**
	 * 获取待批改的学生作业{studentId,homeworkDtlId}
	 * @param homeworkId
	 * @return
	 */
	public List<Map<String, Long>> findSubmitHomeworkDtls(@Param("homeworkId") Long homeworkId);

	/**
	 * 查询匹配的作业信息
	 * @param paperId 试卷ID
	 * @param classType 匹配的班级类型
	 * @param studentIds 学生列表
	 * @return
	 */
	public List<SheetBook> findMatchSheetHomeworkDtls(@Param("paperId") Long paperId,
			@Param("classType") Integer classType, @Param("studentIds") List<Long> studentIds);

	/**
	 * 查询匹配的作业信息
	 * @param paperId 试卷ID
	 * @param studentId 学生ID
	 * @return
	 */
	public SheetBook getMatchSheetHomeworkDtls(@Param("paperId") Long paperId, @Param("studentId") Long studentId);

	/**
	 * 根据输入的学生姓名筛选作业列表中的学生
	 * @param homeworkId
	 * @param studentName
	 * @return
	 */
	public List<HomeworkDtl> findHomeworkDtlByHomeworkIdAndStudentName(@Param("homeworkId") Long homeworkId,
			@Param("studentName") String studentName, Page page);

	/**
	 * 查询学生未完成作业
	 * @param query
	 * @return
	 */
	public List<HomeworkDtlInfo> findToFinishHomeworkDtl(HomeworkIncentiveQuery query);

	/**
	 * 查询待订正作业
	 * @param query
	 * @return
	 */
	public List<HomeworkDtlInfo> findBugfixHomeworkDtl(HomeworkIncentiveQuery query);

	/**
	 * 获取单课预习作业平均分
	 * @param courseSingleId
	 * @return
	 */
	public List<StudentReviewInfo> findStudentReviewScoreByCourseSingleId(Long courseSingleId);

	/**
	 * 查询周/月作业的学科老师信息
	 * @param query
	 * @return
	 */
	public List<WeekMonthHwSubject> findNoFullScoreHwSubTeacher(WeekMonthHwQuery query);

	/**
	 * 查找 周/月作业为满分的homeworkDtlId
	 * 月作业 从周作业总做错的
	 * @param query
	 * @return
	 */
	public List<HomeworkDtl> findNoFullScoreHwDtlId(WeekMonthHwQuery query);
	
	public List<MyHomeworkDTO> findHomeworkDtlByIds(@Param("homeworkDtlIds") List<Long> homeworkDtlIds);

	public List<StuHomeworkDtl> findStuHomeworkDtl(@Param("homeworkIds") List<Long> homeworkIds);
	
	/**
	 * 获取已经批改的 homeworkDtlId
	 * @param homeworkId
	 * @return
	 */
	public List<Long> findCorrectedHwDtlId(@Param("homeworkId") Long homeworkId);
	
	/**
	 * 获取老师未完成作业数(不含寒暑假作业)
	 * @param studentId
	 * @return
	 */
	public Integer getStuToFinishHwTotal(Long studentId);
	
	/**
	 * 查找学生作业Id
	 * @param hwIds
	 * @param studentId
	 * @return
	 */
	public List<Long> findStuHwDtlId(@Param("hwIds") List<Long> hwIds, @Param("studentId") Long studentId);

	/**
	 * 手机作业数据查询
	 * @param query
	 * @return
	 */
	public List<MPersonWorkDTO> findMobilePersonWorkList(MPersonWorkQuery query);

	/**
	 * 手机作业计数查询
	 * @param query
	 * @return
	 */
	public MPersonWorkCount findMobilePersonWorkCount(MPersonWorkQuery query);
	
	/**
	 * 查询学生完成作业数统计
	 * @param homeworkIds
	 * @return
	 */
	public List<StudentReviewInfo> findStudentReviewFinishByHomeworkIds(@Param("homeworkIds") List<Long> homeworkIds);

	/**
	 * 查询学生作业列表（pad）
	 * @param homeworkQuery
	 * @param page
	 * @return
	 */
    List<HDStuHomeworkListDTO> selectStuHomeworkListData(StudentHomeworkQuery homeworkQuery, Page page);
    
    /**
     * 修改作业批改人
     * @param homeworkDtlId
     * @param correctUserId
     * @param modifiedBy
     */
    void updateHwDtlCorrectUser(@Param("homeworkDtlId") Long homeworkDtlId,@Param("correctUserId") Long correctUserId,@Param("modifiedBy") Long modifiedBy);
    
	/**
	 * 根据学生学生作业ID，获取作业信息
	 * @param homeworkDtlId
	 * @return
	 */
	List<CorrectHomeworkDtlInfo> findHomeworkDtlInfoByCorrectUser(@Param("correctUserId") Long correctUserId);
	
	List<CorrectHomeworkDtlInfo> findSubmitByHwIds(@Param("studentId") Long studentId,@Param("homeworkIds") List<Long> homeworkIds);

	Long getCorrectTotal(@Param("correctUserId") Long correctUserId);
	
	public List<MHomeworkDtlInfo> findMHwInfo(@Param("homeworkIds") List<Long> homeworkIds, @Param("studentId") Long studentId);
	
	List<MonthHwInfo> findMonthHw(@Param("userId") Long userId,  @Param("startDate") String startDate);

	List<Long> findHwDtlIdByCloseTime(@Param("studentId") Long studentId, @Param("closeTime") Date closeTime);
	
	/**
	 * 获取资源的总数和完成数
	 * map{ total:12,finishTotal:10}
	 * @param homeworkIds
	 * @return
	 */
	Map<String, Long> getHwResFinishInfo(@Param("studentId") Long studentId, @Param("homeworkIds") List<Long> homeworkIds);
	
	/**
	 * 获取课堂的复习资源数
	 * @param lessonId
	 * @return
	 */
	Long getReviewHwResTotal(Long lessonId);
}
