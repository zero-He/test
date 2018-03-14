package cn.strong.leke.diag.model.studentMonitor;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-15 05:12:29
 */
public class HomeworkCompareBean extends CommProp {

	private double submitPro;
	private double lateSubmitPro;

	public double getSubmitPro() {
		return submitPro;
	}

	public void setSubmitPro(double submitPro) {
		this.submitPro = submitPro;
	}

	public double getLateSubmitPro() {
		return lateSubmitPro;
	}

	public void setLateSubmitPro(double lateSubmitPro) {
		this.lateSubmitPro = lateSubmitPro;
	}
}
