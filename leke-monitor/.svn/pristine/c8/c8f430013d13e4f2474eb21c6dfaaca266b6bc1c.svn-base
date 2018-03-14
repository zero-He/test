package cn.strong.leke.monitor.core.model.lesson;

import java.util.Date;
import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

public class LessonBehavior {
	@_id
	@ObjectId
	private int _id;
	
	private String courseSingleName;
	private TeacherLessonDto teacherLessonDto;
	
	public TeacherLessonDto getTeacherLessonDto() {
		return teacherLessonDto;
	}
	public void setTeacherLessonDto(TeacherLessonDto teacherLessonDto) {
		this.teacherLessonDto = teacherLessonDto;
	}
	private String subjectName;
	
	private Date start;
	
	private Date end;
	
	private Long teacherId;
	
	private String teacherName;
	
	private int examNum;
	
	private int authedNum;
	
	private List<LessonInteracts> interacts;
	
	private List<Student> students;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getCourseSingleName() {
		return courseSingleName;
	}
	public void setCourseSingleName(String courseSingleName) {
		this.courseSingleName = courseSingleName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public int getExamNum() {
		return examNum;
	}
	public void setExamNum(int examNum) {
		this.examNum = examNum;
	}
	public int getAuthedNum() {
		return authedNum;
	}
	public void setAuthedNum(int authedNum) {
		this.authedNum = authedNum;
	}
	public List<LessonInteracts> getInteracts() {
		return interacts;
	}
	public void setInteracts(List<LessonInteracts> interacts) {
		this.interacts = interacts;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}
