package cn.strong.leke.homework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cn.strong.leke.model.incentive.IncentiveTypes;

/**
 * 得分计算工具类。
 * @author  andy
 * @created 2014年6月11日 下午3:40:30
 * @since   v1.0.0
 */
public class ScoreUtils {

	public static int ceil(BigDecimal val) {
		return val.setScale(0, RoundingMode.CEILING).intValue();
	}

	public static int floor(BigDecimal val) {
		return val.intValue();
	}

	/**
	 * 转换百分比例，
	 * 有小数保留1位，小数部分
	 * @param value
	 * @return
	 */
	public static Long toPercent(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return Math.round(value.doubleValue() * 100);
	}

	/**
	 * 根据得分率计算乐豆值。
	 * @param scoreRate 得分率
	 * @return
	 */
	public static int toState(BigDecimal scoreRate) {
		if (scoreRate != null) {
			if (scoreRate.compareTo(new BigDecimal("0.85")) >= 0) {
				return 0;
			} else if (scoreRate.compareTo(new BigDecimal("0.7")) >= 0) {
				return 1;
			} else if (scoreRate.compareTo(new BigDecimal("0.6")) >= 0) {
				return 2;
			}
		}
		return 3;
	}
	/**
	 * 根据得分率计算乐豆值。
	 * @param scoreRate 得分率
	 * @return
	 */
	public static int toExerciseState(BigDecimal scoreRate) {
		if (scoreRate != null) {
			if (scoreRate.compareTo(new BigDecimal("0.80")) >= 0) {
				return 0;
			}
		}
		return 1;
	}
	/**
	 * 根据批改率 获取激励类型
	 * @param scoreRate 得分率
	 * @return
	 */
	public static Long getTypeIdByRate(BigDecimal correctRate) {
		if (correctRate != null) {
			if (correctRate.compareTo(new BigDecimal("0.8")) >= 0) {
				return IncentiveTypes.TEACHER.HW_CORRECT_WORK;
			} else if (correctRate.compareTo(new BigDecimal("0.6")) < 0) {
				return IncentiveTypes.TEACHER.HW_NO_CORRECT_WORK;
			} 
		}
		return null;
	}

	/**
	 * <b>得分</b>约等于。保留一位小数，暂时按规则二处理。<br>
	 * 
	 * 运算规则：<br>
	 * 1、得分值为整数或者x.5；<br>
	 * 2、四舍五入保留一位小数。
	 * @param score
	 * @return
	 */
	public static BigDecimal roundScore(BigDecimal score) {
		return round2(score);
	}

	/**
	 * <b>得分率</b>约等于。四舍五入保留一位小数。<br>
	 * @param scoreRate
	 * @return
	 */
	public static BigDecimal roundScoreRate(BigDecimal scoreRate) {
		return round3(scoreRate);
	}

	// 运算规则：1、得分值为整数或者x.5
	static BigDecimal round1(BigDecimal score) {
		int intVal = score.intValue();
		double result, tmpScore = score.doubleValue();
		if (tmpScore < intVal + 0.25) {
			result = intVal + 0.0;
		} else if (tmpScore < intVal + 0.75) {
			result = intVal + 0.5;
		} else {
			result = intVal + 1.0;
		}
		// 只要得分有值，最小值为0.5
		if (tmpScore > 0 && result == 0) {
			result = 0.5;
		}
		return new BigDecimal(result);
	}

	// 运算规则：四舍五入保留二位小数
	static BigDecimal round2(BigDecimal score) {
		return score.setScale(2, RoundingMode.HALF_UP);
	}

	// 运算规则：四舍五入保留四位小数
	static BigDecimal round3(BigDecimal scoreRate) {
		return scoreRate.setScale(4, RoundingMode.HALF_UP);
	}
}
