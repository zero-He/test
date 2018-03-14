package cn.strong.leke.homework.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.ApiStudentHomeworkListDTO;
import cn.strong.leke.homework.model.HDStuHomeworkListDTO;
import cn.strong.leke.homework.model.HanWang;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlDTO;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkDtlQuery;
import cn.strong.leke.homework.model.HomeworkPaper;
import cn.strong.leke.homework.model.MHomeworkDtlInfo;
import cn.strong.leke.homework.model.MyHomeworkDTO;
import cn.strong.leke.homework.model.StuHwByDayDTO;
import cn.strong.leke.homework.model.StuSubjRes;
import cn.strong.leke.homework.model.StudentHomeworkQuery;
import cn.strong.leke.homework.model.StudentReviewInfo;
import cn.strong.leke.homework.model.SubjectHwStatistics;
import cn.strong.leke.homework.model.mobile.MPersonWorkCount;
import cn.strong.leke.homework.model.mobile.MPersonWorkDTO;
import cn.strong.leke.homework.model.mobile.MPersonWorkQuery;
import cn.strong.leke.homework.model.query.ApiParamForStuHomework;
import cn.strong.leke.model.common.StuTreeModel;
import cn.strong.leke.model.user.UserBase;

/**
 * 学生作业表服务接口。
 */
public interface HomeworkDtlService {

	/**
	 *	
	 * 描述:
	 *
	 * @author  basil
	 * @created 2014-6-11 上午11:11:25
	 * @since   v1.0.0 
	 * @param homework
	 * @param stuList
	 * @return  void
	 */
	void saveHomeworkDtl(Homework homework, List<StuTreeModel> stuList);

	/**
	 *	
	 * 描述:
	 *
	 * @author  basil
	 * @created 2014-6-11 上午11:12:24
	 * @since   v1.0.0 
	 * @param homework
	 * @param userList
	 * @return  void
	 */
	void saveHomeworkDtlForBind(Homework homework, List<UserBase> userList);

	/**
	 * 描述:多条件查询学生作业列表
	 * @author C.C
	 * @created 2014-6-10 上午10:20:14
	 * @since v1.0.0
	 * @param homeworkId
	 * @return
	 * @return List<HomeworkDtlDTO>
	 */
	public List<HomeworkDtlDTO> findHomeworkDtlList(Long homeworkId,String stuName);

	/**
	 *	
	 * 描述:
	 *
	 * @author  basil
	 * @created 2014-6-13 下午3:36:36
	 * @since   v1.0.0 
	 * @param homeworkId
	 * @return
	 * @return  List<HomeworkDtlDTO>
	 */
	List<HomeworkDtlDTO> getHwDtl(Long homeworkId);

	/**
	 *	
	 * 描述:返回我的作业列表（学生）
	 *
	 * @author  DuanYanming
	 * @created 2014年6月12日 上午10:13:07
	 * @since   v1.0.0 
	 * @param homeworkDtlQuery
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
	
	public HomeworkDtlInfo getHomeworkDtlInfoById(Long homeworkDtlId);

	/**
	 * 更新学生作业信息。
	 * @param homeworkDtl
	 * @return
	 */
	public void updateHomeworkDtl(HomeworkDtl homeworkDtl);

	/**
	 * 根据作业标识查询学生作业列表
	 * @param homeworkId 作业标识
	 * @return
	 */
	public List<HomeworkDtl> findHomeworkDtlListByHomeworkId(Long homeworkId);

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
	 * @param day
	 * @return  List<ApiHwCount>
	 */
	public List<StuHwByDayDTO> queryStuHwByDayList(Long studentId, String day);

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
	 * 根据学生作业ID获取作业信息。
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
	public StuHwByDayDTO getStuHomeworkByHomeworkIdAndStudentId(Long homeworkId, Long studentId);

	/**
	 * 查询上一份作业。
	 * @param homeworkId 老师作业ID
	 * @param homeworkDtlId 学生作业ID
	 * @return
	 */
	public Long getPrevHomeworkId(Long homeworkId, Long homeworkDtlId);

	/**
	 * 查询下一份作业。
	 * @param homeworkId 老师作业ID
	 * @param homeworkDtlId 学生作业ID
	 * @return
	 */
	public Long getNextHomeworkId(Long homeworkId, Long homeworkDtlId);

	/**
	 * 查询下一份待批改作业(无序)。
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	public Long getNextCorrectHomeworkId(Long homeworkId,Long homeworkDtlId);

	/**
	 * 查询下一份待复批作业(无序)。
	 * @param homeworkId 老师作业ID
	 * @return
	 */
	public Long getNextReviewHomeworkId(Long homeworkId);

	/**
	 * 根据老师作业ID和学生ID查询学生作业对象
	 * @param homeworkId 作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlByHomeworkIdAndStudentId(Long homeworkId, Long studentId);

	/**
	 * 根据上级作业ID和学生ID查询学生作业信息(点播作业专用)
	 * @param parentId 作业ID
	 * @param studentId 学生ID
	 * @return
	 */
	public HomeworkDtl getHomeworkDtlByParentIdAndStudentId(Long parentId, Long studentId);

	/**
	 * 根据学生ID查询作业的学科分类数量
	 * @param studentId 学生ID
	 * @return
	 */
	public List<StuSubjRes> findStuSubjResByStudentId(Long studentId,Long subjectId,Date startDate);

	/**
	 * 根据作业ID和得分，查询该分数在作业中的排名情况
	 * @param homeworkId 作业ID
	 * @param score 得分
	 * @return
	 */
	public Integer getRankByHomeworkIdAndScore(Long homeworkId, BigDecimal score);

	/**
	 * 保存汉王脚本。
	 * @param hanWang
	 */
	public void saveHanWang(HanWang hanWang);

	/**
	 * 根据ID查询汉王脚本
	 * @param id
	 * @return
	 */
	public HanWang getHanWangById(Long id);
	
	/**
	 * 查询某个学生的作业信息（按照学科分组的统计的待完成和待订正的）
	 * @param studentId
	 * @return
	 */
	public List<SubjectHwStatistics> getSubjectHomeworkInfo(Long studentId);
	
	
	/**
	 * 获取待批改的学生作业{studentId,homeworkDtlId}
	 * @param homeworkId
	 * @return
	 */
	public List<Map<String, Long>> findSubmitHomeworkDtls(Long homeworkId);

	/**
	 * 根据输入的学生姓名筛选作业列表中的学生
	 * @param homeworkId
	 * @param studentName
	 * @return
	 */
	public List<HomeworkDtl> findHomeworkDtlByHomeworkIdAndStudentName(Long homeworkId, String studentName, Page page);
	
	public List<SubjectHwStatistics> findStudentSubjectHwStatisticsList(Long studentId,Boolean filter);

	/**
	 * 通过试卷ID获取试卷题数
	 * @param paperIds
	 * @return
	 */
	public Map<String, Integer> findPaperQuestionNums(List<String> paperIds);
	
	public HomeworkPaper getHomeworkPaper(String paperId);

	List<MyHomeworkDTO> findHomeworkDtlByIds(List<Long> homeworkDtlIds);
	
	/**
	 * 获取学生的未完成作业数(不含寒暑假作业)
	 * @param studentId
	 * @return
	 */
	Integer getStuToFinishHwTotal(Long studentId);
	
	/**
	 * 查找学生作业Id
	 * @param hwIds
	 * @param studentId
	 * @return
	 */
	List<Long> findStuHwDtlId(List<Long> hwIds, Long studentId);

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
	 * 对课堂预习情况进行激励处理
	 * @param lessonId
	 */
	void doHomeworkCompleteWithTx(Long lessonId);
	
	/**
	 * 根据作业id,查找学生作业信息
	 * @param homeworkIds
	 * @return
	 */
	public List<MHomeworkDtlInfo> findMHwInfo(List<Long> homeworkIds, Long studentId);

	/**
	 * 查询学生作业列表（pad）
	 */
	List<HDStuHomeworkListDTO> queryStuHomeworkListData(StudentHomeworkQuery homeworkQuery, Page page);

	/**
	 * 获取课堂金榜的预习情况（完成、得分）
	 * @param lessonId 课堂ID
	 * @return
	 */
	public List<StudentReviewInfo> findStudentReviewInfosByLessonId(Long lessonId);
}
