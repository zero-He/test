package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.util.List;

public class HomePageLessonSchedule implements Serializable{

	private static final long serialVersionUID = 5168989767128594283L;

	private String startTime;
	
	private String lessonIndex;
	
	private List<HomePageSchedule> lessonList;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLessonIndex() {
		return lessonIndex;
	}

	public void setLessonIndex(String lessonIndex) {
		this.lessonIndex = lessonIndex;
	}

	public List<HomePageSchedule> getLessonList() {
		return lessonList;
	}

	public void setLessonList(List<HomePageSchedule> lessonList) {
		this.lessonList = lessonList;
	}
	
}
