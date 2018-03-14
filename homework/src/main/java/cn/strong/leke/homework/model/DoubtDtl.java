/* 
 * 包名: cn.strong.leke.homework.dao.mybatis
 *
 * 文件名：DoubtDtl.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.homework.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.strong.leke.common.utils.DateUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    luf
 * @version   Avatar 
 */
public class DoubtDtl extends Doubt {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	private Date startTime;

	private Date endTime;
	
	private Boolean askAgain;
	
	private String subjectName;
	
	private String teacherName;
	
	private String photo;
	
	private List<Integer> subjects;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreatedOnString() {
		Date date = this.getCreatedOn();
		if (date != null) {
			return DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	
	public String getExplainTimeOnString() {
		Date date = this.getExplainTime()==null?this.getCreatedOn():this.getExplainTime();
		if (date != null) {
			return DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}

	public String getIsSoveString() {
		boolean isSove = this.getResolved();
		if (isSove) {
			return "已解决";
		} else {
			return "未解决";
		}
	}

	public Boolean getAskAgain() {
		return askAgain;
	}

	public void setAskAgain(Boolean askAgain) {
		this.askAgain = askAgain;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Integer> getSubjects() {
		return subjects;
	}

	public void setSubjects(Integer[] subjects) {
		if(subjects!=null&&subjects.length>0){
			this.subjects = Arrays.asList(subjects);
		}
	}

	public String getSourceStr() {
		if(getSource()==null){
			return "课外";
		}else if(getSource()==1){
			return "课外";
		}else if(getSource()==2){
			return "题目";
		}else if(getSource()==3){
			return "课堂";
		}else if(getSource()==4){
			return "点播";
		}else{
			return "课外";
		}
	}

}
