package cn.strong.leke.monitor.core.model;

import java.util.List;

public class IndividualsInBasic extends SortQuery{
	
	  private float tradeAmount;
	  private float commAmount;
	  private Long sellerId;
	  private String month;
	  private int teacherNum;
	  private int newTeacherNum;
	  private int newInTeacherNum;
	  private List<Long> listSeller;
	  
	  

	public int getTeacherNum() {
		return teacherNum;
	}
	public void setTeacherNum(int teacherNum) {
		this.teacherNum = teacherNum;
	}
	public int getNewTeacherNum() {
		return newTeacherNum;
	}
	public void setNewTeacherNum(int newTeacherNum) {
		this.newTeacherNum = newTeacherNum;
	}
	public int getNewInTeacherNum() {
		return newInTeacherNum;
	}
	public void setNewInTeacherNum(int newInTeacherNum) {
		this.newInTeacherNum = newInTeacherNum;
	}
	public List<Long> getListSeller() {
		return listSeller;
	}
	public void setListSeller(List<Long> listSeller) {
		this.listSeller = listSeller;
	}
	public float getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(float tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public float getCommAmount() {
		return commAmount;
	}
	public void setCommAmount(float commAmount) {
		this.commAmount = commAmount;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	  
	  
}
