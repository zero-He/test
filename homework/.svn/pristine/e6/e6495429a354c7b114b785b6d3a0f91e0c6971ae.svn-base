package cn.strong.leke.homework.util;

/**
 * Created by zlj on 2017/3/22.
 *  课程枚举目前只包含英语
 */
public enum SubjectEnum {
    ENGLISH(3l,"英语"),TOEFL(12l,"托福"),IELTS(13l,"雅思"),NEW_ENGLISH(14l,"新概念"),ADULT_ENGLISH(15l,"成人英语"),
    HIGH_ENGLISH(16l,"高中英语"),MIDDLE_ENGLISH(17l,"初中英语"),TESOL(18l,"TESOL"),Children_English(32L,"少儿英语"),
    SAT(21L,"SAT"),ACT(33L,"ACT"),GENERAL_ENGLISH(34L,"General English"),IGCSE_LA(35L,"IGCSE L&A"),A_Levels_LA(36L,"A Levels L&A"),
    NCC(37L,"NCC"),IGCSE_MS(38L,"IGCSE M&S"),A_Levels_MS(39L,"A Levels M&S"),AP(40L,"AP"),JAPANESE(47L,"日语"),KOREAN(48L,"韩语"),
    FRENCH(49L,"法语"),GERMAN(50L,"德语"),SPANISH(51L,"西班牙语");
    public Long code;
    public String name;

    SubjectEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }
    //判断 coede是否为英语的学科
    public static boolean isEnglishSubject(Long code){
        for (SubjectEnum subjectEnum : SubjectEnum.values()) {
            if (subjectEnum.code.equals(code)) return  true;
        }
        return false;
    }
}
