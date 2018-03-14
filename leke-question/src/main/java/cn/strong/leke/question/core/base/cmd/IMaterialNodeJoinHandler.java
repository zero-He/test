package cn.strong.leke.question.core.base.cmd;

import cn.strong.leke.model.question.MaterialNodeJoin;
import cn.strong.leke.model.user.User;

public interface IMaterialNodeJoinHandler {

	void add(MaterialNodeJoin assoc, User user);

	void del(Long materialNodeJoinId, User user);
}
