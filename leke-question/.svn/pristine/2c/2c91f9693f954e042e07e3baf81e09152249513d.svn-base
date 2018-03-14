package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.question.model.QueSubjectQuestionTypeDTO;

/**
 *
 * 描述:学科题目类型
 *
 * @author raolei
 * @created 2015年8月18日 下午1:51:59
 * @since v1.0.0
 */
public interface IQueSubjectQuestionTypeService {
	/**
	 *
	 * 描述:保存学科和题目类型的关联
	 *
	 * @author raolei
	 * @created 2015年8月18日 上午10:58:21
	 * @since v1.0.0
	 * @param list
	 * @return
	 * @return Integer
	 */
	Boolean saveSubjectQuestion(List<QueSubjectQuestionTypeDTO> list);

	/**
	 *
	 * 描述:删除这个学科下面所有题目类型
	 *
	 * @author raolei
	 * @created 2015年8月18日 上午11:23:36
	 * @since v1.0.0
	 * @param subjectId
	 *            学科ID
	 * @return
	 * @return Integer
	 */
	Boolean deleteSubjectQuestion(Long subjectId);

	/**
	 *
	 * 描述:查询学科下面的所有题目类型
	 *
	 * @author raolei
	 * @created 2015年8月18日 上午11:26:33
	 * @since v1.0.0
	 * @param subjectId
	 * @return
	 * @return List<QueSubjectQuestionTypeDTO>
	 */
	List<QueSubjectQuestionTypeDTO> querySubjectQuestion(Long subjectId);

	/**
	 *
	 * 描述:删除和保存学科题目关联
	 *
	 * @author raolei
	 * @created 2015年8月19日 上午11:17:10
	 * @since v1.0.0
	 * @param subjectId
	 * @param list
	 * @return
	 * @return Boolean
	 */
	void saveSubjectQuestion(Long subjectId,
			List<QueSubjectQuestionTypeDTO> list);
}
