/**
 * 
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Outline;
import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.question.model.outline.BaseOutlineQuery;

/**
 * 大纲DAO
 * 
 * @author qw
 * 
 */
public interface OutlineDao {

	/**
	 * 新增提纲
	 * 
	 * @param outline
	 * @return
	 */
	int insertOutline(Outline outline);

	/**
	 * 修改提纲
	 * 
	 * @param outline
	 * @return
	 */
	int modifyOutline(Outline outline);

	/**
	 * 删除提纲
	 * 
	 * @param outline
	 * @return
	 */
	int deleteOutline(Outline outline);
	/**
	 * 查询提纲
	 * 
	 * @param outline
	 * @return
	 */
	List<Outline> queryOutline(Outline outline, Page page);
	
	/**
	 * 查询提纲不需分页给外部
	 * 
	 * @param outline
	 * @return
	 */
	List<Outline> queryOutlines(BaseOutlineQuery outline);
	
	/**
	 *
	 * 描述: 根据学校Id 年级和学科查询大纲
	 *
	 * @author raolei
	 * @created 2016年6月15日 上午10:29:14
	 * @since v1.0.0
	 * @param schoolId
	 * @param gradeId
	 * @param subjectId
	 * @return
	 * @return List<Outline>
	 */
	List<Outline> findOutlinesBySchIdGraIdSubId(@Param("schoolId") Long schoolId,
			@Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId);

	/**
	 *
	 * 描述: 根据学校Id 学段和学科查询大纲
	 *
	 * @author raolei
	 * @created 2016年6月15日 上午10:29:14
	 * @since v1.0.0
	 * @param schoolId
	 * @param schoolStageId
	 * @param subjectId
	 * @return
	 * @return List<Outline>
	 */
	List<Outline> findOutlinesBySchIdStaIdSubId(@Param("schoolId") Long schoolId,
			@Param("schoolStageId") Long schoolStageId, @Param("subjectId") Long subjectId);

	/**
	 * 查询提纲节点
	 * 
	 * @param outline
	 * @return
	 */
	List<OutlineNode> queryOutlineNodesByOutlineId(Long outlineId);

	/**
	 * 获取提纲
	 * 
	 * @param outlineId
	 * @return
	 */
	Outline getOutlineById(Long outlineId);

	/**
	 * 更新提纲
	 * 
	 * @param updates
	 * @return
	 */
	void updateOutlineName(Outline updates);
	
	/**
	 *
	 * 描述:上架
	 *
	 * @author qw
	 * @created 2016年6月24日 
	 * @param outlineId
	 * @param user
	 */
	void outlineUp(Outline ol);

	/**
	 *
	 * 描述:下架
	 *
	 * @author qw
	 * @created 2016年6月24日 
	 * @param outlineId
	 * @param user
	 */
	void outlineDown(Outline ol);
}
