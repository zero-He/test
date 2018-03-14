package cn.strong.leke.homework.model;

/**
 * 考试状态枚举类
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-06-06 14:22:51
 */
public enum ExamStutus {

	NOTSTART(1, "未开始"),
	ISOVER(2, "已结束"),
	EXAMING(3, "正在考试");

	public final int key;
	public final String value;

	ExamStutus(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public static ExamStutus find(int key) {
		for (ExamStutus exam : ExamStutus.values()) {
			if (key == exam.key) {
				return exam;
			}
		}
		return null;
	}
}
