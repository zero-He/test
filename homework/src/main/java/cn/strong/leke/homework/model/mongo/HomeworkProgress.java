/*
 * Copyright (c) 2016 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.homework.model.mongo;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations._id;

/**
 *
 * 描述:
 *
 * @author  raolei
 * @created 2016年3月7日 下午2:08:49
 * @since   v1.0.0
 */
public class HomeworkProgress implements Serializable {

	/**
	 * Description:
	 */
	private static final long serialVersionUID = -189409191039512071L;
	@_id
	private Long homeworkId;
	private Date startTime;
	private Date closeTime;
	private Integer totalNum;
	private List<StudentTs> finished;
	private List<StudentTs> unfinished;

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public List<StudentTs> getFinished() {
		return finished == null ? Collections.emptyList() : finished;
	}

	public void setFinished(List<StudentTs> finished) {
		this.finished = finished;
	}

	public List<StudentTs> getUnfinished() {
		return unfinished == null ? Collections.emptyList() : unfinished;
	}

	public void setUnfinished(List<StudentTs> unfinished) {
		this.unfinished = unfinished;
	}

	public static class StudentTs {
		private Long studentId;
		private Date ts;
		private Integer used;

		public Long getStudentId() {
			return studentId;
		}

		public void setStudentId(Long studentId) {
			this.studentId = studentId;
		}

		public Date getTs() {
			return ts;
		}

		public void setTs(Date ts) {
			this.ts = ts;
		}

		public Integer getUsed() {
			return used;
		}

		public void setUsed(Integer used) {
			this.used = used;
		}
	}
}
