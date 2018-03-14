package cn.strong.leke.diag.model.teachingMonitor;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.strong.leke.common.utils.NumberUtils;

public class ResolveDoubt extends CommProp implements Serializable{

	private static final long serialVersionUID = -291571322153038947L;
	
	//提问总数
	private Long totalDoubt;
	
	//解答数
	private String resolveDoubt;
	
	//未解答数
	private Long notResolveDoubt;
	
	//解答问题的老师数
	private Long teacherNum;
	
	//24小时内解答数
	private Long in24HourResolveDoubt;
	
	//24小时外解答数
	private Long out24HourResolveDoubt;
	
	//解答率
	private String resolveRate = DEFAULT_RATE;
	
	//平均每个老师解答数
	private String teacherAvgResolveRate = DEFAULT_RATE;
	
	//24小时内解答率
	private String in24HourResolveRate = DEFAULT_RATE;
	
	//24小时外解答率
	private String out24HourResolveRate = DEFAULT_RATE;
	
	//未解答率
	private String notResolveRate = DEFAULT_RATE;

	public Long getTotalDoubt() {
		return totalDoubt;
	}

	public void setTotalDoubt(Long totalDoubt) {
		this.totalDoubt = totalDoubt;
	}

	public String getResolveDoubt() {
		return resolveDoubt;
	}

	public void setResolveDoubt(String resolveDoubt) {
		this.resolveDoubt = resolveDoubt;
	}

	public Long getNotResolveDoubt() {
		return notResolveDoubt;
	}

	public void setNotResolveDoubt(Long notResolveDoubt) {
		this.notResolveDoubt = notResolveDoubt;
	}

	public Long getTeacherNum() {
		return teacherNum;
	}

	public void setTeacherNum(Long teacherNum) {
		this.teacherNum = teacherNum;
	}

	public Long getIn24HourResolveDoubt() {
		return in24HourResolveDoubt;
	}

	public void setIn24HourResolveDoubt(Long in24HourResolveDoubt) {
		this.in24HourResolveDoubt = in24HourResolveDoubt;
	}

	public Long getOut24HourResolveDoubt() {
		return out24HourResolveDoubt;
	}

	public void setOut24HourResolveDoubt(Long out24HourResolveDoubt) {
		this.out24HourResolveDoubt = out24HourResolveDoubt;
	}

	public String getResolveRate() {
		return resolveRate;
	}

	public void setResolveRate(String resolveRate) {
		this.resolveRate = NumberUtils.formatScore(new BigDecimal(null == resolveRate? DEFAULT_RATE : resolveRate));
	}

	public String getTeacherAvgResolveRate() {
		return teacherAvgResolveRate;
	}

	public void setTeacherAvgResolveRate(String teacherAvgResolveRate) {
		this.teacherAvgResolveRate = NumberUtils.formatScore(new BigDecimal(null == teacherAvgResolveRate? DEFAULT_RATE : teacherAvgResolveRate));;
	}

	public String getIn24HourResolveRate() {
		return in24HourResolveRate;
	}

	public void setIn24HourResolveRate(String in24HourResolveRate) {
		this.in24HourResolveRate = NumberUtils.formatScore(new BigDecimal(null == in24HourResolveRate? DEFAULT_RATE : in24HourResolveRate));
	}

	public String getOut24HourResolveRate() {
		return out24HourResolveRate;
	}

	public void setOut24HourResolveRate(String out24HourResolveRate) {
		this.out24HourResolveRate = NumberUtils.formatScore(new BigDecimal(null == out24HourResolveRate? DEFAULT_RATE : out24HourResolveRate));
	}

	public String getNotResolveRate() {
		return notResolveRate;
	}

	public void setNotResolveRate(String notResolveRate) {
		this.notResolveRate = NumberUtils.formatScore(new BigDecimal(null == notResolveRate? DEFAULT_RATE : notResolveRate));
	}
	
	
	
}
