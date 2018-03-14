package cn.strong.leke.homework.model;

/**
 * 作业类型枚举类。
 * @author andy
 */
public enum HomeworkType {

    /**
     * 课时作业
     */
    CLASS_HOUR(3, "课时作业"),

    /**
     * 在线考试试卷
     */
    Exam(4, "在线考试试卷"),

    /**
     * 点播作业
     */
    VOD(5, "点播作业"),

    /**
     * 推送作业
     */
    AUTO(6, "推送作业"),

    /**
     * 推送寒暑假作业
     */
    HOLIDAY(7, "寒暑假作业");

    /**
     * 枚举值
     */
    public final int value;
    /**
     * 枚举名
     */
    public final String name;

    private HomeworkType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static HomeworkType valueOf(int value) {
        for (HomeworkType homeworkType : HomeworkType.values()) {
            if (homeworkType.value == value) {
                return homeworkType;
            }
        }
        return null;
    }
    
    public static String getHomeworkTypeName(int value){
    	HomeworkType hwType = valueOf(value);
    	if(hwType != null){
    		return hwType.name;
    	}
    	return null;
    }
}
