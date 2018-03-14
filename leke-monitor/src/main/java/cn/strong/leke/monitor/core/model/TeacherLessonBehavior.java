package cn.strong.leke.monitor.core.model;

import java.io.Serializable;
/**
 * Created by zero on 2017/5/10.
 */
public class TeacherLessonBehavior implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long teacherId;
    private String teacherName;
    private String subjectName;
    private String schoolName;
    private String gradeName;
    private String schoolStageName;
    private int quickNum;
    private int examNum;
    private int manualCallNum;
    private int selfCallNum;
    private int closeNum;
    private int authedNum;
    private int raisedNum;
    private int discussionNum;
    private int isEvalNum;
    private int flowerNum;
    private int analysisNum;
    private int isQuick;
    private int isExam;
    private int isManualCall;
    private int isSelfCall;
    private int isClose;
    private int isAuthed;
    private int isRaised;
    private int isDiscussion;
    private int isEval;
    private int isFlower;
    private int isAnalysis;
    private int lessonNum;
    private String createDate;

    public int getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(int lessonNum) {
        this.lessonNum = lessonNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public TeacherLessonBehavior() {
    }

    public TeacherLessonBehavior(Long teacherId,
                          String teacherName, String subjectName,
                          String schoolName, String gradeName,
                          String schoolStageName, int quickNum,
                          int examNum, int manualCallNum, int selfCallNum,
                          int closeNum, int authedNum, int raisedNum,
                          int discussionNum, int isEvalNum, int flowerNum,
                          int analysisNum, int isQuick, int isExam, int isManualCall,
                          int isSelfCall, int isClose, int isAuthed, int isRaised,
                          int isDiscussion, int isEval, int isFlower, int isAnalysis,
                          int lessonNum,String createDate) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.schoolName = schoolName;
        this.gradeName = gradeName;
        this.schoolStageName = schoolStageName;
        this.quickNum = quickNum;
        this.examNum = examNum;
        this.manualCallNum = manualCallNum;
        this.selfCallNum = selfCallNum;
        this.closeNum = closeNum;
        this.authedNum = authedNum;
        this.raisedNum = raisedNum;
        this.discussionNum = discussionNum;
        this.isEvalNum = isEvalNum;
        this.flowerNum = flowerNum;
        this.analysisNum = analysisNum;
        this.isQuick = isQuick;
        this.isExam = isExam;
        this.isManualCall = isManualCall;
        this.isSelfCall = isSelfCall;
        this.isClose = isClose;
        this.isAuthed = isAuthed;
        this.isRaised = isRaised;
        this.isDiscussion = isDiscussion;
        this.isEval = isEval;
        this.isFlower = isFlower;
        this.isAnalysis = isAnalysis;
        this.lessonNum = lessonNum;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSchoolStageName() {
        return schoolStageName;
    }

    public void setSchoolStageName(String schoolStageName) {
        this.schoolStageName = schoolStageName;
    }

    public int getQuickNum() {
        return quickNum;
    }

    public void setQuickNum(int quickNum) {
        this.quickNum = quickNum;
    }

    public int getExamNum() {
        return examNum;
    }

    public void setExamNum(int examNum) {
        this.examNum = examNum;
    }

    public int getManualCallNum() {
        return manualCallNum;
    }

    public void setManualCallNum(int manualCallNum) {
        this.manualCallNum = manualCallNum;
    }

    public int getSelfCallNum() {
        return selfCallNum;
    }

    public void setSelfCallNum(int selfCallNum) {
        this.selfCallNum = selfCallNum;
    }

    public int getCloseNum() {
        return closeNum;
    }

    public void setCloseNum(int closeNum) {
        this.closeNum = closeNum;
    }

    public int getAuthedNum() {
        return authedNum;
    }

    public void setAuthedNum(int authedNum) {
        this.authedNum = authedNum;
    }

    public int getRaisedNum() {
        return raisedNum;
    }

    public void setRaisedNum(int raisedNum) {
        this.raisedNum = raisedNum;
    }

    public int getDiscussionNum() {
        return discussionNum;
    }

    public void setDiscussionNum(int discussionNum) {
        this.discussionNum = discussionNum;
    }

    public int getIsEvalNum() {
        return isEvalNum;
    }

    public void setIsEvalNum(int isEvalNum) {
        this.isEvalNum = isEvalNum;
    }

    public int getFlowerNum() {
        return flowerNum;
    }

    public void setFlowerNum(int flowerNum) {
        this.flowerNum = flowerNum;
    }

    public int getAnalysisNum() {
        return analysisNum;
    }

    public void setAnalysisNum(int analysisNum) {
        this.analysisNum = analysisNum;
    }

    public int getIsQuick() {
        return isQuick;
    }

    public void setIsQuick(int isQuick) {
        this.isQuick = isQuick;
    }

    public int getIsExam() {
        return isExam;
    }

    public void setIsExam(int isExam) {
        this.isExam = isExam;
    }

    public int getIsManualCall() {
        return isManualCall;
    }

    public void setIsManualCall(int isManualCall) {
        this.isManualCall = isManualCall;
    }

    public int getIsSelfCall() {
        return isSelfCall;
    }

    public void setIsSelfCall(int isSelfCall) {
        this.isSelfCall = isSelfCall;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public int getIsAuthed() {
        return isAuthed;
    }

    public void setIsAuthed(int isAuthed) {
        this.isAuthed = isAuthed;
    }

    public int getIsRaised() {
        return isRaised;
    }

    public void setIsRaised(int isRaised) {
        this.isRaised = isRaised;
    }

    public int getIsDiscussion() {
        return isDiscussion;
    }

    public void setIsDiscussion(int isDiscussion) {
        this.isDiscussion = isDiscussion;
    }

    public int getIsEval() {
        return isEval;
    }

    public void setIsEval(int isEval) {
        this.isEval = isEval;
    }

    public int getIsFlower() {
        return isFlower;
    }

    public void setIsFlower(int isFlower) {
        this.isFlower = isFlower;
    }

    public int getIsAnalysis() {
        return isAnalysis;
    }

    public void setIsAnalysis(int isAnalysis) {
        this.isAnalysis = isAnalysis;
    }
}
