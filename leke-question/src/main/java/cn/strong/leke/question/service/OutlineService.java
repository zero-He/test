/**
 * 
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.question.Outline;
import cn.strong.leke.model.question.OutlineNode;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.outline.BaseOutlineQuery;


/**
 * 大纲服务接口
 * 
 * @author qianwei
 * 
 */
public interface OutlineService {

	/**
	 * 
	 * 描述: 根据条件查询大纲
	 * 
	 * @author qw
	 * @created 2016年6月2日 
	 */
	List<Outline> queryOutline(Outline outline, Page page);

	/**
	 * 
	 * 描述: 添加大纲
	 * 
	 * @author qw
	 * @created 2016年6月2日
	 *//*
	void addOutline(Outline outline);*/
	
	/**
	 * 
	 * 描述: 根据条件查询大纲节点
	 * 
	 * @author qw
	 * @created 2016年6月2日 
	 */
	List<OutlineNode> queryOutlineNodesByOutlineId(Long outlineId);


	List<OutlineNode> queryOutlineNodes(OutlineNode query);
	

	/**
	 *
	 * 描述: 根据学校Id，年级和学段查询大纲
	 *
	 * @author raolei
	 * @created 2016年6月15日 上午10:05:26
	 * @since v1.0.0
	 * @param schoolId
	 * @param gradeId
	 * @param subjectId
	 * @return
	 * @return List<Outline>
	 */
	List<Outline> findOutlinesBySchIdGraIdSubId(Long schoolId, Long gradeId, Long subjectId);
	
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
	List<Outline> findOutlinesBySchIdStaIdSubId(Long schoolId, Long schoolStageId, Long subjectId);

	/**
	 * 仅更新提纲名称
	 * 
	 * @author qw
	 * @since v3.2.2
	 * @param updates
	 */
	void updateOutlineName(Outline outline);
	
	/**
	 * 删除教材
	 * 
	 * @param material
	 */
	void deleteOutline(Outline outline);
	
	/**
	 * 描述: 添加大纲
	 * 
	 * @author qw
	 * @param outline
	 * @param copySections
	 * @param user
	 */
	void addOutline(Outline outline, boolean copySections, User user);

	/**
	 * 描述: 添加大纲节点
	 * 
	 * @author qw
	 * @param outlineNode
	 */
	void addOutlineNode(OutlineNode outlineNode);

	/**
	 * 描述: 修改大纲节点
	 * 
	 * @author qw
	 * @param outlineNode
	 */
	void updateOutlineNode(OutlineNode outlineNode);

	/**
	 * 描述: 删除大纲节点
	 * 
	 * @author qw
	 * @param outlineNode
	 */
	void deleteOutlineNode(OutlineNode outlineNode);

	/**
	 * 描述: 上移大纲节点
	 * 
	 * @author qw
	 * @param outlineNode
	 */
	void moveUpNode(OutlineNode outlineNode);

	/**
	 * 描述: 下移大纲节点
	 * 
	 * @author qw
	 * @param outlineNode
	 */
	void moveDownNode(OutlineNode outlineNode);

	/**
	 * 
	 * 描述: 根据条件查询大纲外部接口
	 * 
	 * @author qw
	 * @created 2016年6月2日 
	 */
	List<Outline> queryOutlines(BaseOutlineQuery outline);

	/**
	 * 
	 * 描述: 重建教材目录树左右索引
	 * 
	 * @author qw
	 * @created 2016年6月2日 
	 * @param outlineId
	 */
	void rebuildTreeIndexWithTx(Long outlineId);

	/**
	 *
	 * 描述:上架
	 *
	 * @author qw
	 * @created 2016年6月24日 
	 * @param outlineId
	 * @param user
	 */
	void outlineUp(Long outlineId, User user);

	/**
	 *
	 * 描述:下架
	 *
	 * @author qw
	 * @created 2016年6月24日 
	 * @param outlineId
	 * @param user
	 */
	void outlineDown(Long outlineId, User user);
}
