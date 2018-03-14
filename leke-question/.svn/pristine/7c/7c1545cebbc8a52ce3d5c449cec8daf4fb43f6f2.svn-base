/* 
 * 包名: cn.strong.leke.question.dao.mybatis
 *
 * 文件名：IOfficialTagDao.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-22
 */
package cn.strong.leke.question.dao.mybatis;

import java.util.List;

import cn.strong.leke.model.question.OfficialTag;

/**
 * 正式题库标签数据层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IOfficialTagDao {

	/**
	 * 添加题库标签
	 * @param officialTag
	 * @return
	 * author：lavender
	 * 2014-4-22上午9:50:04
	 */
	int saveOfficialTag(OfficialTag officialTag);
	
	/**
	 * 更新题库标签
	 * @param officialTag
	 * @return
	 * author：lavender
	 * 2014-4-22上午9:50:42
	 */
	int updateOfficialTag(OfficialTag officialTag);
	
	/**
	 * 验证题库标签是否重名
	 * @param officialTag
	 * @return
	 * author：lavender
	 * 2014-4-22上午9:50:42
	 */
	int checkOfficialTag(OfficialTag officialTag);
	
	/**
	 * 删除题库标签
	 * @param officialTagId
	 * @return
	 * author：lavender
	 * 2014-4-22上午9:50:55
	 */
	int deleteOfficialTag(long officialTagId);
	
	/**
	 * 根据officialTagId获取题库标签
	 * @param officialTagId
	 * @return
	 * author：lavender
	 * 2014-4-22上午10:52:05
	 */
	OfficialTag queryOfficialTag(long officialTagId);
	
	/**
	 * 查询题库标签列表
	 * @return
	 * author：lavender
	 * 2014-4-22上午9:51:04
	 */
	List<OfficialTag> queryOfficialTagList();
}
