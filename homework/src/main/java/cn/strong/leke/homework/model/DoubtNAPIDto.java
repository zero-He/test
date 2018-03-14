package cn.strong.leke.homework.model;

import java.util.Date;

/**
 * flash接口使用
 * 通用问答对象
 * @author Administrator
 *
 */
public class DoubtNAPIDto {

	private Long createTime;//学生创建提问的时间		必填
	private String doubtContent;//学生的提问		必填
	private Long studentId;//学生userId		必填
	private Long teacherId;//提问老师的userId		必填
	
	private Long answerTime;//老师回答时间
	private String answer;//老师回答内容
	
	public Date getCreateTime() {
		if(this.createTime!=null){
			return new Date(createTime);
		}else{
			return new Date();
		}
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Date getAnswerTime() {
		if(this.answerTime!=null&&this.answerTime!=0){
			return new Date(answerTime);
		}else{
			return null;
		}
	}
	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDoubtContent() {
		return doubtContent;
	}
	public void setDoubtContent(String doubtContent) {
		this.doubtContent = doubtContent;
	}
	
	
}
