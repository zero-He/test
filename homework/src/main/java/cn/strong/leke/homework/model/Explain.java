/* 
 * 包名: cn.strong.leke.homework.model
 *
 * 文件名：Explain.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-19
 */
package cn.strong.leke.homework.model;

import java.io.Serializable;
import java.util.Date;

import cn.strong.leke.common.utils.DateUtils;

/**
 * 〈一句话功能简述〉
 * 教师对学生提问的解答
 * @author    luf
 * @version   Avatar 
 */
public class Explain implements Serializable {

	
	
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	//教师答疑id
	private  long explainId;
	
	//问题id
	private long doubtId;
	
	//答疑内容
	private String explainContent;
	
	//音频路径
	private String expainAudio;
	
	//音频时长字符串
	private String duration;
	
	//学生姓名
	private String userName;
	
	//学校ID 
	private Long schoolId;
	
	//是否已删除
	private boolean isDeleted;
	
	//创建人id(教师id)
	private long createdBy;
	
	//创建时间
	private Date createdOn;
	
	//仅用户flash端使用
	private Long roleId;
	
	//单课Id 课堂传入时使用
	private Long lessonId;

	public long getExplainId() {
		return explainId;
	}

	public void setExplainId(long explainId) {
		this.explainId = explainId;
	}

	public long getDoubtId() {
		return doubtId;
	}

	public void setDoubtId(long doubtId) {
		this.doubtId = doubtId;
	}

	public String getExplainContent() {
		return explainContent;
	}

	public void setExplainContent(String explainContent) {
		this.explainContent = explainContent;
	}

	public String getExpainAudio() {
		return expainAudio;
	}

	public void setExpainAudio(String expainAudio) {
		this.expainAudio = expainAudio;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getCreateOnString(){
		Date createOn = this.getCreatedOn();
		if(createOn != null){
			return DateUtils.format(createOn, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	
}
