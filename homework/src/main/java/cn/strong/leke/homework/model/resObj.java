package cn.strong.leke.homework.model;

import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 2.6
 * @Date 2017-04-26 10:00:11
 */
public class resObj {

	private List<resSet> result_set;
	private String swf_name;

	public List<resSet> getResult_set() {
		return result_set;
	}

	public void setResult_set(List<resSet> result_set) {
		this.result_set = result_set;
	}

	public String getSwf_name() {
		return swf_name;
	}

	public void setSwf_name(String swf_name) {
		this.swf_name = swf_name;
	}
}
