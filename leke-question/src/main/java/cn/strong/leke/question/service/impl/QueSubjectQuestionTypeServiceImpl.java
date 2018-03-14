package cn.strong.leke.question.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.BaseCache;
import cn.strong.leke.question.dao.mybatis.IQueSubjectQuestionTypeDao;
import cn.strong.leke.question.model.QueSubjectQuestionTypeDTO;
import cn.strong.leke.question.service.IQueSubjectQuestionTypeService;

/**
 *
 * 描述:学科题目类型
 *
 * @author raolei
 * @created 2015年8月18日 下午1:55:41
 * @since v1.0.0
 */
@Service
public class QueSubjectQuestionTypeServiceImpl implements
		IQueSubjectQuestionTypeService {

	@Resource
	private IQueSubjectQuestionTypeDao iQueSubjectQuestionTypeDao;

	@Override
	public Boolean saveSubjectQuestion(List<QueSubjectQuestionTypeDTO> list) {
		return iQueSubjectQuestionTypeDao.saveSubjectQuestion(list) > 0;
	}

	@Override
	public Boolean deleteSubjectQuestion(Long subjectId) {
		return iQueSubjectQuestionTypeDao.deleteSubjectQuestion(subjectId) > 0;
	}

	@Override
	public List<QueSubjectQuestionTypeDTO> querySubjectQuestion(Long subjectId) {
		return iQueSubjectQuestionTypeDao.querySubjectQuestion(subjectId);
	}

	public void saveSubjectQuestion(Long subjectId,
			List<QueSubjectQuestionTypeDTO> list) {
		if (subjectId == null) {
			throw new ValidateException("que.subquetype.subjectId.isnull");
		}
		if (CollectionUtils.isEmpty(list)) {
			throw new ValidateException("que.subquetype.types.isempty");
		}
		iQueSubjectQuestionTypeDao.deleteSubjectQuestion(subjectId);
		iQueSubjectQuestionTypeDao.saveSubjectQuestion(list);
		CacheUtils.delete(BaseCache.PREFIX_SUBJECT_QUESTION_TYPE);
	}
}
