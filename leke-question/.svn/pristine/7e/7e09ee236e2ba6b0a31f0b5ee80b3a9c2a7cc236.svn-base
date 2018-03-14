/**
 * 
 */
package cn.strong.leke.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.question.dao.mybatis.question.ISchoolQuestionDao;
import cn.strong.leke.question.model.question.SchoolQuestion;
import cn.strong.leke.question.service.SchoolQuestionService;

/**
 * @author liulb
 *
 */
@Service
public class SchoolQuestionServiceImpl implements SchoolQuestionService {

	@Autowired
	private ISchoolQuestionDao schoolQuestionDao;

	@Override
	public void addSchoolQuestion(SchoolQuestion sq) {
		if (sq == null || sq.getSchoolId() == null
				|| sq.getQuestionId() == null) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		SchoolQuestion old = schoolQuestionDao.getByQuestionAndSchool(sq.getQuestionId(),
				sq.getSchoolId());
		Validation.isNull(old, "que.questionShare.already.exist");
		schoolQuestionDao.add(sq);
	}

	@Override
	public void addBatchSchoolQuestion(List<SchoolQuestion> sqs) {
		if (CollectionUtils.isEmpty(sqs)) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		sqs.forEach((sq) -> {
			SchoolQuestion old = schoolQuestionDao.getByQuestionAndSchool(sq.getQuestionId(),
					sq.getSchoolId());
			Validation.isNull(old, "que.questionShare.already.exist");
		});
		schoolQuestionDao.insertBatchSchoolQuestion(sqs);
	}

	@Override
	public void deleteSchoolQuestion(SchoolQuestion sq) {
		if (sq == null || sq.getSchoolId() == null
				|| sq.getQuestionId() == null) {
			throw new ValidateException("que.questionShare.info.incomplete");
		}
		schoolQuestionDao.remove(sq);

	}

	@Override
	public List<SchoolQuestion> findByQuestionIds(List<Long> questionIds, Long schoolId) {
		return schoolQuestionDao.findByQuestionIds(questionIds, schoolId);
	}
}
