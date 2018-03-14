package cn.strong.leke.diag.model.teachingMonitor.attendance;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-18 17:03:36
 */
public class AttendanceCountBean extends AttendanceBean{

	private int teacherTotalNum;
	private int totalClassNum;
	private int realClassNum;
	private double classPro;
	private double allOnPro;
	private double latePro;
	private double earlyPro;
	private double lateAndEarlyPro;
	private double notClassNumPro;

	public int getTeacherTotalNum() {
		return teacherTotalNum;
	}

	public void setTeacherTotalNum(int teacherTotalNum) {
		this.teacherTotalNum = teacherTotalNum;
	}

	public int getTotalClassNum() {
		return totalClassNum;
	}

	public void setTotalClassNum(int totalClassNum) {
		this.totalClassNum = totalClassNum;
	}

	public int getRealClassNum() {
		return realClassNum;
	}

	public void setRealClassNum(int realClassNum) {
		this.realClassNum = realClassNum;
	}

	public double getClassPro() {
		return classPro;
	}

	public void setClassPro(double classPro) {
		this.classPro = classPro;
	}

	public double getAllOnPro() {
		return allOnPro;
	}

	public void setAllOnPro(double allOnPro) {
		this.allOnPro = allOnPro;
	}

	public double getLatePro() {
		return latePro;
	}

	public void setLatePro(double latePro) {
		this.latePro = latePro;
	}

	public double getEarlyPro() {
		return earlyPro;
	}

	public void setEarlyPro(double earlyPro) {
		this.earlyPro = earlyPro;
	}

	public double getLateAndEarlyPro() {
		return lateAndEarlyPro;
	}

	public void setLateAndEarlyPro(double lateAndEarlyPro) {
		this.lateAndEarlyPro = lateAndEarlyPro;
	}

	public double getNotClassNumPro() {
		return notClassNumPro;
	}

	public void setNotClassNumPro(double notClassNumPro) {
		this.notClassNumPro = notClassNumPro;
	}
}
