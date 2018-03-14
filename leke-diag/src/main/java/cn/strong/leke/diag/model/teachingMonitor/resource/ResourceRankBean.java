package cn.strong.leke.diag.model.teachingMonitor.resource;

import cn.strong.leke.diag.model.teachingMonitor.RankBean;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-25 11:07:10
 */
public class ResourceRankBean extends RankBean {

	private Integer createCount = 0;
	private Integer shareCount = 0;

	public Integer getCreateCount() {
		return createCount;
	}

	public void setCreateCount(Integer createCount) {
		this.createCount = createCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
}
