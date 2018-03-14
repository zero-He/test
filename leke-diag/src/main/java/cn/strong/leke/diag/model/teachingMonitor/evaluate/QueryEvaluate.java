package cn.strong.leke.diag.model.teachingMonitor.evaluate;

import cn.strong.leke.diag.model.teachingMonitor.RequestVo;

import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-18 16:12:47
 */
public class QueryEvaluate extends RequestVo {

	private List<Long> teacherIds;

	public List<Long> getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(List<Long> teacherIds) {
		this.teacherIds = teacherIds;
	}
}
