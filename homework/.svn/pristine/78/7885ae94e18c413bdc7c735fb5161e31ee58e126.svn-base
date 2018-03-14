package cn.strong.leke.homework.model;

/**
 * 资源类型枚举类
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017/4/5 16:01
 */
public enum ResType {

    /**
     * 推送作业
     */
    COURSEWARE(1, "课件"),

    /**
     * 推送寒暑假作业
     */
    MICROCOURSE(2, "微课"),


    /*
     * 课时作业
     * */
    PAPER(3, "试卷");

    /**
     * 枚举值
     */
    public final int value;
    /**
     * 枚举名
     */
    public final String name;

    private ResType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ResType valueOf(int value) {
        for (ResType resType : ResType.values()) {
            if (resType.value == value) {
                return resType;
            }
        }
        return null;
    }
    
    public static String getResTypeName(int value){
    	ResType resType = valueOf(value);
    	if(resType != null){
    		return resType.name;
    	}
    	return null;
    }
}
