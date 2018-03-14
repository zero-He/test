/**
 * 
 */
package cn.strong.leke.monitor.mongo.stat.model;

import java.util.Date;

import cn.strong.leke.data.mongo.annotations._id;

/**
 * 学校注册用户数统计
 * 
 * @author liulongbiao
 *
 */
public class SchoolUserStat {
	@_id
	private Long schoolId;
	private int registered; // 注册用户数
	private int students; // 学生数
	private int teachers; // 教师数
	private Date modifiedOn; // 更新时间

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public int getStudents() {
		return students;
	}

	public void setStudents(int students) {
		this.students = students;
	}

	public int getTeachers() {
		return teachers;
	}

	public void setTeachers(int teachers) {
		this.teachers = teachers;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
