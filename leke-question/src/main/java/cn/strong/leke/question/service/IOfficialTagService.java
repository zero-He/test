/* 
 * 包名: cn.strong.leke.question.service
 *
 * 文件名：IOfficialTagService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-22
 */
package cn.strong.leke.question.service;

import java.util.List;

import cn.strong.leke.model.question.OfficialTag;

/**
 * 正式题库标签服务层接口
 * @author    lavender
 * @version   Avatar 
 */
public interface IOfficialTagService {
	
	/**
	 * 添加题库标签
	 * @param officialTag
	 * @return
	 * author：lavender
	 * 2014-4-22上午10:28:30
	 */
	void addOfficialTag(OfficialTag officialTag);
	
	/**
	 * 更新题库标签
	 * @param officialTag
	 * @return
	 * author：lavender
	 * 2014-4-22上午10:28:48
	 */
	void updateOfficialTag(OfficialTag officialTag);
	
	/**
	 * 删除题库标签
	 * @param officialTagId
	 * @return
	 * author：lavender
	 * 2014-4-22上午10:28:58
	 */
	void deleteOfficialTag(Long officialTagId);
	
	/**
	 * 根据officialTagId获取题库标签
	 * @param officialTagId
	 * @return
	 * author：lavender
	 * 2014-4-22上午10:54:05
	 */
	OfficialTag findOfficialTag(Long officialTagId);
	
	/**
	 * 查询题库标签列表
	 * @return
	 * author：lavender
	 * 2014-4-22上午10:29:11
	 */
	List<OfficialTag> findOfficialTagList();
}
