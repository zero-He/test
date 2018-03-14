package cn.strong.leke.diag.model.teachingMonitor.attendance;

import java.io.Serializable;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-08-03 11:06:44
 */
public class AttendanceProBean extends CommProp implements Serializable{

	private static final long serialVersionUID = -6487152608455723257L;
	
	private Integer allOn = 0;
	private Integer late = 0;
	private Integer early = 0;
	private Integer lateAndEarly = 0;
	private Integer notClassNum = 0;
	private double allOnPro;

	public Integer getAllOn() {
		return allOn;
	}

	public void setAllOn(Integer allOn) {
		this.allOn = allOn;
	}

	public Integer getLate() {
		return late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}

	public Integer getEarly() {
		return early;
	}

	public void setEarly(Integer early) {
		this.early = early;
	}

	public Integer getLateAndEarly() {
		return lateAndEarly;
	}

	public void setLateAndEarly(Integer lateAndEarly) {
		this.lateAndEarly = lateAndEarly;
	}

	public Integer getNotClassNum() {
		return notClassNum;
	}

	public void setNotClassNum(Integer notClassNum) {
		this.notClassNum = notClassNum;
	}

	public double getAllOnPro() {
		return allOnPro;
	}

	public void setAllOnPro(double allOnPro) {
		this.allOnPro = allOnPro;
	}
}
