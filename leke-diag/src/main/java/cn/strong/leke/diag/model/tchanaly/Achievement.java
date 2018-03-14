package cn.strong.leke.diag.model.tchanaly;

import java.util.List;
import java.util.Set;

public class Achievement {

	private String label;
	private Integer num;
	private Integer score;
	private List<AchieveItem> items; // 班级成就列表
	private List<UserItem> userRanks; // 学生排行榜
	private List<TrendItem> trends; // 勤奋走势

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<AchieveItem> getItems() {
		return items;
	}

	public void setItems(List<AchieveItem> items) {
		this.items = items;
	}

	public static class AchieveItem {

		private Integer type;
		private Set<Long> userIds;
		private List<String> userNames;

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Set<Long> getUserIds() {
			return userIds;
		}

		public void setUserIds(Set<Long> userIds) {
			this.userIds = userIds;
		}

		public List<String> getUserNames() {
			return userNames;
		}

		public void setUserNames(List<String> userNames) {
			this.userNames = userNames;
		}
	}

	public List<TrendItem> getTrends() {
		return trends;
	}

	public void setTrends(List<TrendItem> trends) {
		this.trends = trends;
	}

	public List<UserItem> getUserRanks() {
		return userRanks;
	}

	public void setUserRanks(List<UserItem> userRanks) {
		this.userRanks = userRanks;
	}

	public static class AchieveInfo implements java.io.Serializable {

		private static final long serialVersionUID = -4568606374362842231L;

		private Integer score; // 指数
		private Integer achieveNum; // 成就数
		private Integer attendNum; // 全勤
		private Integer previewNum; // 提前预习
		private Integer listenNum; // 专心听讲
		private Integer loveAckNum; // 勤思好问
		private Integer activeNum; // 活跃分子
		private Integer fragrantNum; // 手有余香
		private Integer reformNum; // 及时改错
		private Integer diligentNum; // 不磨蹭
		private Integer practiceNum; // 练习达人
		private Integer refreshNum; // 温故知新

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}

		public Integer getAchieveNum() {
			return achieveNum;
		}

		public void setAchieveNum(Integer achieveNum) {
			this.achieveNum = achieveNum;
		}

		public Integer getAttendNum() {
			return attendNum;
		}

		public void setAttendNum(Integer attendNum) {
			this.attendNum = attendNum;
		}

		public Integer getPreviewNum() {
			return previewNum;
		}

		public void setPreviewNum(Integer previewNum) {
			this.previewNum = previewNum;
		}

		public Integer getListenNum() {
			return listenNum;
		}

		public void setListenNum(Integer listenNum) {
			this.listenNum = listenNum;
		}

		public Integer getLoveAckNum() {
			return loveAckNum;
		}

		public void setLoveAckNum(Integer loveAckNum) {
			this.loveAckNum = loveAckNum;
		}

		public Integer getActiveNum() {
			return activeNum;
		}

		public void setActiveNum(Integer activeNum) {
			this.activeNum = activeNum;
		}

		public Integer getFragrantNum() {
			return fragrantNum;
		}

		public void setFragrantNum(Integer fragrantNum) {
			this.fragrantNum = fragrantNum;
		}

		public Integer getReformNum() {
			return reformNum;
		}

		public void setReformNum(Integer reformNum) {
			this.reformNum = reformNum;
		}

		public Integer getDiligentNum() {
			return diligentNum;
		}

		public void setDiligentNum(Integer diligentNum) {
			this.diligentNum = diligentNum;
		}

		public Integer getPracticeNum() {
			return practiceNum;
		}

		public void setPracticeNum(Integer practiceNum) {
			this.practiceNum = practiceNum;
		}

		public Integer getRefreshNum() {
			return refreshNum;
		}

		public void setRefreshNum(Integer refreshNum) {
			this.refreshNum = refreshNum;
		}
	}

	public static class UserItem implements java.io.Serializable {

		private static final long serialVersionUID = 1068985659877509937L;

		private Long userId;
		private String userName;
		private Integer score; // 勤奋指数
		private Integer achieveNum; // 成就数

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

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}

		public Integer getAchieveNum() {
			return achieveNum;
		}

		public void setAchieveNum(Integer achieveNum) {
			this.achieveNum = achieveNum;
		}
	}

	public static class TrendItem implements java.io.Serializable {

		private static final long serialVersionUID = 1325167604338947503L;

		private String name;
		private Object value;

		public TrendItem() {
		}

		public TrendItem(String name, Object value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
