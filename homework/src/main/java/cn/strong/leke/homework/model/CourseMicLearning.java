package cn.strong.leke.homework.model;

import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 2.6
 * @Date 2017-04-25 17:29:55
 */
public class CourseMicLearning {

	private String type;
	private String cwUrl;
	private String cwSuffix;
	private Long pageCount;
	private List<String> url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCwUrl() {
		return cwUrl;
	}

	public void setCwUrl(String cwUrl) {
		this.cwUrl = cwUrl;
	}

	public String getCwSuffix() {
		return cwSuffix;
	}

	public void setCwSuffix(String cwSuffix) {
		this.cwSuffix = cwSuffix;
	}

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}
}
