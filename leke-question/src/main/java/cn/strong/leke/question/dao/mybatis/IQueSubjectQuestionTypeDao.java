package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.question.model.QueSubjectQuestionTypeDTO;

/**
 *
 * 描述:学科题目类型Dao
 *
 * @author  raolei
 * @created 2015年8月18日 上午10:58:08
 * @since   v1.0.0
 */
public interface IQueSubjectQuestionTypeDao {
	/**
	 *	
	 * 描述:保存学科和题目类型的关联
	 *
	 * @author  raolei
	 * @created 2015年8月18日 上午10:58:21
	 * @since   v1.0.0 
	 * @param list
	 * @return
	 * @return  Integer
	 */
	public Integer saveSubjectQuestion(@Param("list")List<QueSubjectQuestionTypeDTO> list);
	
	/**
	 *	
	 * 描述:删除这个学科下面所有题目类型
	 *
	 * @author  raolei
	 * @created 2015年8月18日 上午11:23:36
	 * @since   v1.0.0 
	 * @param subjectId 学科ID
	 * @return
	 * @return  Integer
	 */
	public Integer deleteSubjectQuestion(@Param("subjectId")Long subjectId);
	
	/**
	 *	
	 * 描述:查询学科下面的所有题目类型
	 *
	 * @author  raolei
	 * @created 2015年8月18日 上午11:26:33
	 * @since   v1.0.0 
	 * @param subjectId
	 * @return
	 * @return  List<QueSubjectQuestionTypeDTO>
	 */
	public List<QueSubjectQuestionTypeDTO> querySubjectQuestion(@Param("subjectId")Long subjectId);
}
