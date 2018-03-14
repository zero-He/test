/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：OfficialTagService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-22
 */
package cn.strong.leke.question.service.impl;

import static cn.strong.leke.model.BaseCache.PREFIX_OFFICIAL_TAG;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.core.nosql.CacheUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.OfficialTag;
import cn.strong.leke.question.dao.mybatis.IOfficialTagDao;
import cn.strong.leke.question.service.IOfficialTagService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class OfficialTagService implements IOfficialTagService {

	@Resource
	private IOfficialTagDao officialTagDao;

	@Override
	public void addOfficialTag(OfficialTag tag) {
		if (tag == null || StringUtils.isEmpty(tag.getOfficialTagName())) {
			throw new ValidateException("que.questionTag.info.incomplete");
		}
		if (officialTagDao.checkOfficialTag(tag) > 0) {
			throw new ValidateException("que.questionTag.exist");
		}
		officialTagDao.saveOfficialTag(tag);
		
		CacheUtils.delete(PREFIX_OFFICIAL_TAG);
	}

	@Override
	public void updateOfficialTag(OfficialTag tag) {
		if (tag == null || tag.getOfficialTagId() == null
				|| StringUtils.isEmpty(tag.getOfficialTagName())) {
			throw new ValidateException("que.questionTag.info.incomplete");
		}
		if (officialTagDao.checkOfficialTag(tag) > 0) {
			throw new ValidateException("que.questionTag.exist");
		}
		officialTagDao.updateOfficialTag(tag);

		CacheUtils.delete(PREFIX_OFFICIAL_TAG);
	}
	
	@Override
	public void deleteOfficialTag(Long officialTagId) {
		officialTagDao.deleteOfficialTag(officialTagId);
		CacheUtils.delete(PREFIX_OFFICIAL_TAG);
	}

	@Override
	public OfficialTag findOfficialTag(Long officialTagId) {
		return officialTagDao.queryOfficialTag(officialTagId);
	}
	
	@Override
	public List<OfficialTag> findOfficialTagList() {
		return officialTagDao.queryOfficialTagList();
	}
	
}
