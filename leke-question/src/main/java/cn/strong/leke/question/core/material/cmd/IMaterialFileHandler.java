package cn.strong.leke.question.core.material.cmd;

import cn.strong.leke.model.user.User;
import cn.strong.leke.question.model.material.MaterialFile;

public interface IMaterialFileHandler {

	void add(MaterialFile assoc, User user);

}
