package cn.strong.leke.question.core.base.cmd.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.question.MaterialNodeJoin;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.base.cmd.IMaterialNodeJoinHandler;
import cn.strong.leke.question.dao.mybatis.base.IMaterialNodeJoinDao;

@Service
public class MaterialNodeJoinHandler implements IMaterialNodeJoinHandler {

	@Resource
	private IMaterialNodeJoinDao materialNodeJoinDao;

	@Override
	public void add(MaterialNodeJoin assoc, User user) {
		if (assoc.getOrigMaterialNodeId() == null || assoc.getDestMaterialNodeId() == null) {
			throw new ValidateException("绑定的教材章节信息错误！");
		}
		Long origMaterialNodeId = assoc.getOrigMaterialNodeId();
		Long destMaterialNodeId = assoc.getDestMaterialNodeId();
		if (materialNodeJoinDao.isExist(origMaterialNodeId, destMaterialNodeId) > 0) {
			throw new ValidateException("绑定的教材章节已存在！");
		}
		Long userId = user.getId();
		assoc.setCreatedBy(userId);
		assoc.setModifiedBy(userId);
		materialNodeJoinDao.add(assoc);
	}

	@Override
	public void del(Long materialNodeJoinId, User user) {
		Long userId = user.getId();
		materialNodeJoinDao.del(materialNodeJoinId, userId);
	}

}
