package cn.strong.leke.diag.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.strong.leke.diag.dao.diag.model.ExamReport;
import cn.strong.leke.diag.dao.diag.model.ExamReportDtl;
import cn.strong.leke.diag.dao.diag.mybatis.ExamReportDao;
import cn.strong.leke.framework.page.jdbc.Page;

@Repository
public class ExamReportService {

	@Resource
	private ExamReportDao examReportDao;

	/**
	 * 创建成绩单
	 * @param examReport 成绩单对象
	 * @return 成绩单ID
	 */
	public boolean insertExamReport(ExamReport examReport, List<ExamReportDtl> examReportDtlList) {
		try {
			Long examReportId = examReportDao.insertExamReport(examReport);
			examReportDtlList = examReportDtlList.stream().map(dtl -> {
				dtl.setExamReportId(examReport.getExamReportId());
				return dtl;
			}).distinct().collect(Collectors.toList());
			insertExamReportDtl(examReportDtlList);
			updateExamReportDtlScore(examReport.getExamReportId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 保存成绩单明细信息
	 * @param examReportDtlList  成绩单明细List
	 */
	public void insertExamReportDtl(List<ExamReportDtl> examReportDtlList) {
		examReportDao.insertExamReportDtl(examReportDtlList);
	}

	/**
	 * 根据教务人员ID，学校ID查询成绩单
	 * @param provostId 教务ID
	 * @param schoolId 学校ID
	 * @return
	 */
	public List<ExamReport> queryExamReportByProvost(Long provostId, Long schoolId, Page page) {
		return examReportDao.queryExamReportByProvost(provostId, schoolId, page);
	}

	/**
	 * 根据教师人员ID，学校ID查询成绩单
	 * @param teacherId
	 * @param schoolId
	 * @return
	 */
	public List<ExamReport> queryExamReportByTeacher(Long teacherId, Long schoolId, Page page) {
		return examReportDao.queryExamReportByTeacher(teacherId, schoolId, page);
	}

	/**
	 * 根据成绩单ID查询成绩单明细
	 * @param examReportId 成绩单ID
	 * @return
	 */
	public List<ExamReportDtl> queryExamReportDtlByRptId(Long examReportId, Page page) {
		return examReportDao.queryExamReportDtlByRptId(examReportId, page);
	}

	/**
	 * 根据学生ID查询成绩单
	 * @param studentId
	 * @return
	 */
	public List<ExamReport> queryExamReportByStudentId(Long studentId, Page page) {
		return examReportDao.queryExamReportByStudentId(studentId, page);
	}

	/**
	 * 根据成绩单ID，学生ID查询成绩单明细
	 * @param examReportId
	 * @param studentId
	 * @return
	 */
	public List<ExamReportDtl> queryExamReportDtlByStudentId(Long examReportId, Long studentId) {
		return examReportDao.queryExamReportDtlByStudentId(examReportId, studentId);
	}

	/**
	 * 修改成绩单状态，作废 或者 发布
	 * @param examReportId
	 */
	public boolean updateExamReportStatus(Long examReportId, Long status, Long modifiedBy) {
		try{
			examReportDao.updateExamReportStatus(examReportId, status, modifiedBy);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改成绩单明细最高分，最低分，平均分
	 * @param examReportId
	 */
	public void updateExamReportDtlScore(@Param("examReportId") Long examReportId) {
		examReportDao.updateExamReportDtlScore(examReportId);
	}

	/**
	 * 查询学段学科
	 * @return
	 */
	public List<Map<String, Object>> querySchoolSubjects() {
		return examReportDao.querySchoolSubjects();
	}

	/**
	 * 查询学科
	 * @return
	 */
	public List<Map<String, Object>> querySchoolSubjectsList() {
		return examReportDao.querySchoolSubjectsList();
	}

	/**
	 * 判断给定的乐号和姓名是否存在
	 * @param loginName
	 * @param userName
	 * @return
	 */
	public boolean isStudentExists(Long loginName, String userName) {
		return examReportDao.isStudentExists(loginName, userName) == 0 ? false : true;
	}
}
