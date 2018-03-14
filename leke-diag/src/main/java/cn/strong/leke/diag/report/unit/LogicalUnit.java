package cn.strong.leke.diag.report.unit;

import java.util.function.Function;

/**
 * 逻辑运算单元
 * @author  andy
 * @since   v1.0.0
 */
public interface LogicalUnit<R> {

	R execute(LogicalContext context);

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
