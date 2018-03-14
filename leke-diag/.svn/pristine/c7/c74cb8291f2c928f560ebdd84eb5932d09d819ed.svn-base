package cn.strong.leke.diag.report;

import java.util.function.Function;

public interface ReportHandler<T, R> {

	public default void init() {
	};

	public R execute(T query);

	public static Function<Double, String> MAPPER_SCORE_TO_LEVEL = score -> {
		if (score == null) {
			return "O";
		} else if (score >= 85) {
			return "A";
		} else if (score >= 70) {
			return "B";
		} else if (score >= 60) {
			return "C";
		} else if (score >= 45) {
			return "D";
		} else {
			return "E";
		}
	};
}
