package cn.strong.leke.diag.dao.homework.model;

public class AnalysePhase {

	private Long homeworkId;
	private Long homeworkDtlId;
	private Integer usePhase;
	private Boolean selected;

	public Long getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Long getHomeworkDtlId() {
		return homeworkDtlId;
	}

	public void setHomeworkDtlId(Long homeworkDtlId) {
		this.homeworkDtlId = homeworkDtlId;
	}

	public Integer getUsePhase() {
		return usePhase;
	}

	public void setUsePhase(Integer usePhase) {
		this.usePhase = usePhase;
	}
	
	public String getUsePhaseName() {
		return HomeworkCst.PHASE_NAMES[this.usePhase] + "报告";
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
