package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.wrong.HomeworkForWrongMQ;
import cn.strong.leke.question.model.wrongquestion.WrongQuestion;
import cn.strong.leke.question.model.wrongquestion.WrongQuestionQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubjKnowQuery;
import cn.strong.leke.question.model.wrongquestion.WrongSubject;
import cn.strong.leke.question.model.wrongquestion.WrongSubjectKnowledge;

/**
 * 老师错题本错题题目 服务
 * @author Zhang Fujun
 * @date 2017年1月11日
 */
public interface IWrongQuestionService {

	/**
	 * 获取老师学科设置得分率信息
	 * @param teacherId
	 * @param classId
	 * @param subjectId
	 * @return
	 */
	WrongSubject getWrongSubject(Long teacherId, Long classId,
			Long subjectId,Long schoolId);

	/**
	 * 更新 单题得分率
	 * @param id
	 */
	void saveWrongSubjRate(WrongSubject wrongSubject);
	
	
	/**
	 * 获取一个错题题目信息
	 * @param id
	 * @return
	 */
	WrongQuestion getWrongQuestion(Long id);
	
	/**
	 * 移除错题本的题目
	 * @param id
	 */
	void delWrongQuestion(WrongQuestion wrongQuestion);

	/**
	 * 查询知识点分布统计
	 * @param query
	 * @return
	 */
	List<WrongSubjectKnowledge> findWrongSubjectKnowledges(WrongSubjKnowQuery query);

	/**
	 * 查询老师班级学科的错题本题目
	 * @param query
	 * @param page
	 * @return
	 */
	List<WrongQuestion> findWrongQuestion(WrongQuestionQuery query, Page page);

	/**
	 * 保存错题题目信息
	 * @param wrongQuestion
	 */
	void saveWrongQuestion(HomeworkForWrongMQ wrongMQ);

}
