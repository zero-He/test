package cn.strong.leke.diag.model.studentMonitor;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-28 15:18:33
 */
public class ReadyCountBean extends ReadyBean {

	private int studentTotalNum;
	private int lessonTotalNum;

	public int getStudentTotalNum() {
		return studentTotalNum;
	}

	public void setStudentTotalNum(int studentTotalNum) {
		this.studentTotalNum = studentTotalNum;
	}

	public int getLessonTotalNum() {
		return lessonTotalNum;
	}

	public void setLessonTotalNum(int lessonTotalNum) {
		this.lessonTotalNum = lessonTotalNum;
	}

}
