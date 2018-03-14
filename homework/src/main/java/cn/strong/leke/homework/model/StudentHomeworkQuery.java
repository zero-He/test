package cn.strong.leke.homework.model;

/**
 * @Author LIU.SHITING
 * @Version 1.0.0
 * @Date 2017-04-11 10:11:46
 */
public class StudentHomeworkQuery {

    private Long studentId;//学生id
    private Long subjectId;//学科id
    private String homeworkFinishFlag;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getHomeworkFinishFlag() {
        return homeworkFinishFlag;
    }

    public void setHomeworkFinishFlag(String homeworkFinishFlag) {
        this.homeworkFinishFlag = homeworkFinishFlag;
    }
}
