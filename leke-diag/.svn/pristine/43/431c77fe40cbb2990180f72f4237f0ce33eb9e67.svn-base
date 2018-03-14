package cn.strong.leke.diag.report.unit;

import java.util.HashMap;

public class LogicalContext extends HashMap<String, Object> {

	private static final long serialVersionUID = -4271882118462614663L;

	public LogicalContext append(String key, Object value) {
		this.put(key, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		return (T) this.get(key);
	}

	public static LogicalContext builder() {
		return new LogicalContext();
	}
}
