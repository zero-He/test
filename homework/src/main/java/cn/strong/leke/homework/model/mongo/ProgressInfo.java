package cn.strong.leke.homework.model.mongo;

import java.util.List;

import cn.strong.leke.model.user.SimpleUser;

public class ProgressInfo {

	private List<SimpleUser> finished;
	private List<SimpleUser> unfinished;

	public List<SimpleUser> getFinished() {
		return finished;
	}

	public void setFinished(List<SimpleUser> finished) {
		this.finished = finished;
	}

	public List<SimpleUser> getUnfinished() {
		return unfinished;
	}

	public void setUnfinished(List<SimpleUser> unfinished) {
		this.unfinished = unfinished;
	}
}
