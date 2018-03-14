package cn.strong.leke.question.core.material.cmd.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.material.cmd.IMaterialFileHandler;
import cn.strong.leke.question.dao.mybatis.material.IMaterialFileDao;
import cn.strong.leke.question.model.material.MaterialFile;

@Service
public class MaterialFileHandler implements IMaterialFileHandler {
	@Resource
	private IMaterialFileDao materialFileDao;

	@Override
	public void add(MaterialFile assoc, User user) {
		if (assoc.getMaterialId() == null || StringUtils.isEmpty(assoc.getFileId())) {
			throw new ValidateException("电子教材附件信息错误！");
		}
		Long materialId = assoc.getMaterialId();
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		materialFileDao.del(materialId, userId);
		materialFileDao.add(assoc);
	}


}
