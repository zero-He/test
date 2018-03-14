package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.diag.dao.diag.model.ExamReportDtl;
import cn.strong.leke.lesson.model.LessonVM;

public class LessonBeikeInfo extends LessonVM implements Serializable{
	
	private static final long serialVersionUID = -3765878128216066094L;
	
	//备课时间
	private Date beikeTime;
	
	//是否是提前备课:true 提前备课, false 临时备课
	private Boolean isEarlyPrepared;
	
	//是否有教案
	private Integer teachPlan = 0;
	
	//课堂上课时间段
	private String lessonTimeStr;
	
	//对应上课开始时间
	private Long lessonTime;
	
	//到课率
	private String ratio;
	
	//实到人数
	private Integer realCount;
	
	//应到人数
	private Integer totalCount;
	
	//缺勤人数
	private Integer absentCount;
	
	//序号
	private Integer index;
	

	public Date getBeikeTime() {
		return beikeTime;
	}

	public void setBeikeTime(Date beikeTime) {
		this.beikeTime = beikeTime;
	}

	public Boolean getIsEarlyPrepared() {
		return isEarlyPrepared;
	}

	public void setIsEarlyPrepared(Boolean isEarlyPrepared) {
		this.isEarlyPrepared = isEarlyPrepared;
	}

	public Long getLessonTime() {
		return DateUtils.parseDate(lessonTimeStr.substring(0, 16), DateUtils.MINITE_DATE_PATTERN).getTime();
	}

	public void setLessonTime(Long lessonTime) {
		this.lessonTime = lessonTime;
	}
	
	public String getLessonTimeStr() {
		return lessonTimeStr;
	}

	public void setLessonTimeStr(String lessonTimeStr) {
		this.lessonTimeStr = lessonTimeStr;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public Integer getRealCount() {
		return realCount;
	}

	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(Integer absentCount) {
		this.absentCount = absentCount;
	}

	public Integer getTeachPlan() {
		return teachPlan;
	}

	public void setTeachPlan(Integer teachPlan) {
		this.teachPlan = teachPlan;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof LessonBeikeInfo))
			return false;
		if(this.getTeacherId().equals(((LessonBeikeInfo)obj).getTeacherId()) &&
		   this.getSubjectId().equals(((LessonBeikeInfo)obj).getSubjectId())) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(this.getTeacherId(), this.getSubjectId());
	}
	
}
