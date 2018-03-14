package cn.strong.leke.diag.model.studentMonitor;

import cn.strong.leke.data.mongo.annotations.BsonDecimal;
import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class LearningBean {

	@_id
	@ObjectId
	private String learnId;
	private Long clazzId;       //行政班级ID
	private Integer elapsed;    //用时(秒)
	private Date start;         //闯关时间
	private Date end;           //通关时间
	private Long learnKlgId;    //专题知识点ID
	private Long learnMcId;     //专题微课ID
	private Integer queCount;   //闯关题量
	private Long schoolId;      //学校ID
	private Integer star;       //闯关星级
	private Long userId;        //用户ID

	public String getLearnId() {
		return learnId;
	}

	public void setLearnId(String learnId) {
		this.learnId = learnId;
	}

	public Long getClazzId() {
		return clazzId;
	}

	public void setClazzId(Long clazzId) {
		this.clazzId = clazzId;
	}

	public Integer getElapsed() {
		return elapsed;
	}

	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
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

	public Long getLearnKlgId() {
		return learnKlgId;
	}

	public void setLearnKlgId(Long learnKlgId) {
		this.learnKlgId = learnKlgId;
	}

	public Long getLearnMcId() {
		return learnMcId;
	}

	public void setLearnMcId(Long learnMcId) {
		this.learnMcId = learnMcId;
	}

	public Integer getQueCount() {
		return queCount;
	}

	public void setQueCount(Integer queCount) {
		this.queCount = queCount;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}