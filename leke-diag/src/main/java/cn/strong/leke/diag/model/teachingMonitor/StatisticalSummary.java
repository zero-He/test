package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.strong.leke.diag.model.teachingMonitor.attendance.AttendanceProBean;
import cn.strong.leke.diag.model.teachingMonitor.evaluate.EvaluateDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.interact.InteractDetailBean;
import cn.strong.leke.diag.model.teachingMonitor.resource.ResourceDetailBean;

public class StatisticalSummary extends CommProp implements Serializable{

	private static final long serialVersionUID = -7567285550453605038L;

	//考勤
	private AttendanceProBean attendancePro;
	
	//评价
	private EvaluateDetailBean evaluateDetail;
	
	//互动
	private InteractDetailBean interactDetail;
	
	//资源
	private ResourceDetailBean resourceDetail;
	
	//备课
	private BeikeRate beikeRate;
	
	//上课
	private LessonAttendInfo lessonAttendInfo;
	
	//乐答
	private ResolveDoubt resolveDoubt;
	
	//作业
	private CorrectHW correctHW;
	
	//头像图片src
	private String imgSrc;

	public AttendanceProBean getAttendancePro() {
		return attendancePro;
	}

	public void setAttendancePro(AttendanceProBean attendancePro) {
		this.attendancePro = attendancePro;
	}

	public EvaluateDetailBean getEvaluateDetail() {
		return evaluateDetail;
	}

	public void setEvaluateDetail(EvaluateDetailBean evaluateDetail) {
		this.evaluateDetail = evaluateDetail;
	}

	public InteractDetailBean getInteractDetail() {
		return interactDetail;
	}

	public void setInteractDetail(InteractDetailBean interactDetail) {
		this.interactDetail = interactDetail;
	}

	public ResourceDetailBean getResourceDetail() {
		return resourceDetail;
	}

	public void setResourceDetail(ResourceDetailBean resourceDetail) {
		this.resourceDetail = resourceDetail;
	}

	public BeikeRate getBeikeRate() {
		return beikeRate;
	}

	public void setBeikeRate(BeikeRate beikeRate) {
		this.beikeRate = beikeRate;
	}

	public LessonAttendInfo getLessonAttendInfo() {
		return lessonAttendInfo;
	}

	public void setLessonAttendInfo(LessonAttendInfo lessonAttendInfo) {
		this.lessonAttendInfo = lessonAttendInfo;
	}

	public ResolveDoubt getResolveDoubt() {
		return resolveDoubt;
	}

	public void setResolveDoubt(ResolveDoubt resolveDoubt) {
		this.resolveDoubt = resolveDoubt;
	}

	public CorrectHW getCorrectHW() {
		return correctHW;
	}

	public void setCorrectHW(CorrectHW correctHW) {
		this.correctHW = correctHW;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
}
