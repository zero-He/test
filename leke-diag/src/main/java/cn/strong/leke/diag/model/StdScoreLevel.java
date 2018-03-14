package cn.strong.leke.diag.model;

/**
 * 标准优劣等级定义。
 * @author  andy
 * @created 2014年8月1日 下午3:10:36
 * @since   v1.0.0
 */
public enum StdScoreLevel {

	/**
	 * A：85-100分
	 */
	A("A：85-100分"),
	/**
	 * B：70-85分
	 */
	B("B：70-85分"),
	/**
	 * C：60-70分
	 */
	C("C：60-70分"),
	/**
	 * D：0-60分
	 */
	D("D：0-60分");

	public final String desc;

	private StdScoreLevel(String desc) {
		this.desc = desc;
	}

	/**
	 * 将得分换算为优劣等级
	 * @param score 得分
	 * @return
	 */
	public static StdScoreLevel toLevel(Number score) {
		if (score == null) {
			return null;
		} else if (score.intValue() >= 85) {
			return A;
		} else if (score.intValue() >= 70) {
			return B;
		} else if (score.intValue() >= 60) {
			return C;
		} else {
			return D;
		}
	}

	/**
	 * 将得分换算为优劣等级
	 * @param score 得分
	 * @return
	 */
	public static String toLevelName(Number score) {
		StdScoreLevel level = toLevel(score);
		if (level != null) {
			return level.desc;
		}
		return "";
	}
}
