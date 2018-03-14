package cn.strong.leke.diag.model.stuanaly;

import java.util.List;
import java.util.Set;

public class UserAchievement {

	private String label;
	private Integer num;
	private Integer score;
	private List<Integer> types;
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

	public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
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
