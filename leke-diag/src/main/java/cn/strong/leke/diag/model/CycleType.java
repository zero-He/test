package cn.strong.leke.diag.model;

import java.util.Arrays;
import java.util.List;

public enum CycleType {

	DATE(0, "日") {
		public List<Integer> childTypes() {
			return Arrays.asList();
		}
	},
	WEEK(1, "周") {
		public List<Integer> childTypes() {
			return Arrays.asList(0);
		}
	},
	MONTH(2, "月") {
		public List<Integer> childTypes() {
			return Arrays.asList(0, 1);
		}
	},
	TERM(3, "学期") {
		public List<Integer> childTypes() {
			return Arrays.asList(1, 2);
		}
	},
	YEAR(4, "学年") {
		public List<Integer> childTypes() {
			return Arrays.asList(2, 3);
		}
	};

	public final int value;
	public final String name;

	private CycleType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public abstract List<Integer> childTypes();

	public static CycleType resolve(int type) {
		for (CycleType cycleType : CycleType.values()) {
			if (cycleType.value == type) {
				return cycleType;
			}
		}
		return CycleType.DATE;
	}
}
