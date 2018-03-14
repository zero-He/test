package cn.strong.leke.question.dao.mybatis.wrongquestion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.question.model.wrongquestion.WrongQuestion;
import cn.strong.leke.question.model.wrongquestion.WrongQuestionQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubjKnowQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubject;

/**
 * 老师错题本错题题目 dao
 * @author Zhang Fujun
 * @date 2017年1月11日
 */
public interface IWrongQuestionDao {

	/**
	 * 获取老师设置的 单题得分率
	 * @param teacherId
	 * @param classId
	 * @param subjectId
	 * @return
	 */
	public WrongSubject getWrongSubject(@Param("teacherId") Long teacherId, @Param("classId") Long classId,
			@Param("subjectId") Long subjectId, @Param("schoolId") Long schoolId);

	/**
	 * 更新 单题得分率
	 * @param id
	 */
	public Integer updateWrongSubjRate(WrongSubject wrongSubject);
	
	public void insertWrongSubject(WrongSubject wrongSubject);

	/**
	 * 移除错题本的题目
	 * @param id
	 */
	public void delWrongQuestion(WrongQuestion wrongQuestion);
	
	public WrongQuestion getWrongQuestion(@Param("id") Long id);

	/**
	 * 查询老师班级学科的错题本题目
	 * @param query
	 * @param page
	 * @return
	 */
	public List<WrongQuestion> findWrongQuestion(WrongQuestionQuery query, Page page);
	
	/**
	 * 查询老师班级学科的错题本题目id
	 * @param query
	 * @param page
	 * @return
	 */
	public List<Long> findWrongQuestionId(WrongSubjKnowQuery query);

	public void insertWrongQuestion(WrongQuestion wrongQuestion);

	public WrongQuestion getWrongQueByClassSubjQuestionId(@Param("classId") Long classId, @Param("subjectId") Long subjectId,
			@Param("userId") Long userId, @Param("questionId") Long questionId, @Param("schoolId") Long schoolId);

	/**
	 * 更新 已经存在的错题题目
	 * @param wrongQuestion
	 */
	public Integer updateWrongQuestion(WrongQuestion wrongQuestion);
	
}
