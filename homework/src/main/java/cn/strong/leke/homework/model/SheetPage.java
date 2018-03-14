package cn.strong.leke.homework.model;

import java.util.List;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

public class SheetPage implements Comparable<SheetPage> {

	@_id
	@ObjectId
	private String id;
	private String taskId;
	private Boolean success;
	private String lekeNo; // 乐号
	private String stuno; // 学号
	private String idtype; // 身份类型
	private String sheetId;
	private Integer errorNo;
	private Long userId;
	private String userName;
	private Integer index;
	private List<Block> pages;
	private String clterrorNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getLekeNo() {
		return lekeNo;
	}

	public void setLekeNo(String lekeNo) {
		this.lekeNo = lekeNo;
	}

	public String getStuno() {
		return stuno;
	}

	public void setStuno(String stuno) {
		this.stuno = stuno;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public Integer getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(Integer errorNo) {
		this.errorNo = errorNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public List<Block> getPages() {
		return pages;
	}

	public void setPages(List<Block> pages) {
		this.pages = pages;
	}

	public String getClterrorNo() {
		return clterrorNo;
	}

	public void setClterrorNo(String clterrorNo) {
		this.clterrorNo = clterrorNo;
	}

	public static class Block {
		private Boolean success;
		private String pageId;
		private String img;
		private List<Range> ranges;

		public Boolean getSuccess() {
			return success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}

		public String getPageId() {
			return pageId;
		}

		public void setPageId(String pageId) {
			this.pageId = pageId;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public List<Range> getRanges() {
			return ranges;
		}

		public void setRanges(List<Range> ranges) {
			this.ranges = ranges;
		}
	}

	public static class Range {
		private String rangeId;
		private String rangeType;
		private String img;
		private String score;
		private List<String> results;

		public String getRangeId() {
			return rangeId;
		}

		public void setRangeId(String rangeId) {
			this.rangeId = rangeId;
		}

		public String getRangeType() {
			return rangeType;
		}

		public void setRangeType(String rangeType) {
			this.rangeType = rangeType;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public List<String> getResults() {
			return results;
		}

		public void setResults(List<String> results) {
			this.results = results;
		}
	}

	@Override
	public int compareTo(SheetPage o) {
		return Integer.compare(this.index, o.index);
	}
}
