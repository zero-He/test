package cn.strong.leke.diag.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ScoreUtils {

	/**
	 * 保留小数位
	 * @param score
	 * @param scale
	 * @return
	 */
	public static Double toFixed(Double score, int scale) {
		return new BigDecimal(score).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 计算比例
	 * @param value
	 * @param total
	 * @param scale
	 * @return
	 */
	public static Double toRate(int value, int total, int scale) {
		if (total == 0) {
			return 0D;
		}
		return new BigDecimal(value * 100).divide(new BigDecimal(total), scale, RoundingMode.HALF_UP).doubleValue();
	}
}
