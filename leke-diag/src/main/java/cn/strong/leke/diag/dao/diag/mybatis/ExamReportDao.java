package cn.strong.leke.diag.dao.diag.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.diag.dao.diag.model.ExamReport;
import cn.strong.leke.diag.dao.diag.model.ExamReportDtl;
import cn.strong.leke.framework.page.jdbc.Page;


public interface ExamReportDao {
	
	/**
	 * 创建成绩单
	 * @param examReport 成绩单对象
	 * @return 成绩单ID
	 */
	public Long insertExamReport(ExamReport examReport);
	
	/**
	 * 保存成绩单明细信息
	 * @param examReportDtlList  成绩单明细List
	 */
	public void insertExamReportDtl(@Param("examReportDtlList") List<ExamReportDtl> examReportDtlList);
	
	/**
	 * 根据教务人员ID，学校ID查询成绩单
	 * @param provostId 教务ID
	 * @param schoolId 学校ID
	 * @return
	 */
	public List<ExamReport> queryExamReportByProvost(@Param("provostId") Long provostId, @Param("schoolId") Long schoolId, Page page);
	
	/**
	 * 根据教师人员ID，学校ID查询成绩单
	 * @param teacherId
	 * @param schoolId
	 * @return
	 */
	public List<ExamReport> queryExamReportByTeacher(@Param("teacherId") Long teacherId, @Param("schoolId") Long schoolId, Page page);
	
	/**
	 * 根据成绩单ID查询成绩单明细
	 * @param examReportId 成绩单ID
	 * @return
	 */
	public List<ExamReportDtl> queryExamReportDtlByRptId(Long examReportId, Page page);
	

	/**
	 * 根据学生ID查询成绩单
	 * @param studentId
	 * @return
	 */
	public List<ExamReport> queryExamReportByStudentId(Long studentId, Page page);
	
	/**
	 * 根据成绩单ID，学生ID查询成绩单明细
	 * @param examReportId
	 * @param studentId
	 * @return
	 */
	public List<ExamReportDtl> queryExamReportDtlByStudentId(@Param("examReportId") Long examReportId, @Param("studentId") Long studentId);
	
	/**
	 * 修改成绩单状态，作废 或者 发布
	 * @param examReportId
	 */
	public void updateExamReportStatus(@Param("examReportId") Long examReportId, @Param("status") Long status, @Param("modifiedBy") Long modifiedBy);
	
	/**
	 * 修改成绩单明细最高分，最低分，平均分
	 * @param examReportId
	 */
	public void updateExamReportDtlScore(@Param("examReportId") Long examReportId);
	
	/**
	 * 查询学段学科
	 * @return
	 */
	public List<Map<String, Object>> querySchoolSubjects();
	
	/**
	 * 查询学科
	 * @return
	 */
	public List<Map<String, Object>> querySchoolSubjectsList();
	
	/**
	 * 判断给定的乐号和姓名是否存在
	 * @param loginName
	 * @param userName
	 * @return
	 */
	public int isStudentExists(@Param("loginName") Long loginName, @Param("userName") String userName);
	
}
