package cn.strong.leke.diag.model.teachingMonitor.evaluate;

import cn.strong.leke.diag.model.teachingMonitor.CommProp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 14:56:00
 */
public class EvaluateDetailBean extends CommProp implements Serializable{

	private static final long serialVersionUID = -6219772842082382390L;
	
	private Long owner;
	private String ownerName;
	private Long subjectId;
	private String subjectName;
	private Integer lessonNum;
	private BigDecimal totalLevel;
	private Integer good;
	private double goodPro;
	private Integer center;
	private double centerPro;
	private Integer poor;
	private double poorPro;
	private Integer flowerNum;

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getLessonNum() {
		return lessonNum;
	}

	public void setLessonNum(Integer lessonNum) {
		this.lessonNum = lessonNum;
	}

	public BigDecimal getTotalLevel() {
		return totalLevel;
	}

	public void setTotalLevel(BigDecimal totalLevel) {
		this.totalLevel = totalLevel;
	}

	public Integer getGood() {
		return good;
	}

	public void setGood(Integer good) {
		this.good = good;
	}

	public double getGoodPro() {
		return goodPro;
	}

	public void setGoodPro(double goodPro) {
		this.goodPro = goodPro;
	}

	public Integer getCenter() {
		return center;
	}

	public void setCenter(Integer center) {
		this.center = center;
	}

	public double getCenterPro() {
		return centerPro;
	}

	public void setCenterPro(double centerPro) {
		this.centerPro = centerPro;
	}

	public Integer getPoor() {
		return poor;
	}

	public void setPoor(Integer poor) {
		this.poor = poor;
	}

	public double getPoorPro() {
		return poorPro;
	}

	public void setPoorPro(double poorPro) {
		this.poorPro = poorPro;
	}

	public Integer getFlowerNum() {
		return flowerNum;
	}

	public void setFlowerNum(Integer flowerNum) {
		this.flowerNum = flowerNum;
	}
}
