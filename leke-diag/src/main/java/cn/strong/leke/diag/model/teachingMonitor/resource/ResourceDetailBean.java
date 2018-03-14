package cn.strong.leke.diag.model.teachingMonitor.resource;

import java.io.Serializable;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-25 14:14:42
 */
public class ResourceDetailBean extends ResourceRankBean implements Serializable{

	private static final long serialVersionUID = -7813507738892925771L;
	
	private Long subjectId;
	private String subjectName;
	private int createQuestionCount;
	private int createPaperCount;
	private int createCoursewareCount;
	private int createMicrocourseCount;
	private int createBeikePkgCount;
	private int shareQuestionCount;
	private int sharePaperCount;
	private int shareCoursewareCount;
	private int shareMicrocourseCount;
	private int shareBeikePkgCount;
	private Integer index;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getCreateQuestionCount() {
		return createQuestionCount;
	}

	public void setCreateQuestionCount(int createQuestionCount) {
		this.createQuestionCount = createQuestionCount;
	}

	public int getCreatePaperCount() {
		return createPaperCount;
	}

	public void setCreatePaperCount(int createPaperCount) {
		this.createPaperCount = createPaperCount;
	}

	public int getCreateCoursewareCount() {
		return createCoursewareCount;
	}

	public void setCreateCoursewareCount(int createCoursewareCount) {
		this.createCoursewareCount = createCoursewareCount;
	}

	public int getCreateMicrocourseCount() {
		return createMicrocourseCount;
	}

	public void setCreateMicrocourseCount(int createMicrocourseCount) {
		this.createMicrocourseCount = createMicrocourseCount;
	}

	public int getCreateBeikePkgCount() {
		return createBeikePkgCount;
	}

	public void setCreateBeikePkgCount(int createBeikePkgCount) {
		this.createBeikePkgCount = createBeikePkgCount;
	}

	public int getShareQuestionCount() {
		return shareQuestionCount;
	}

	public void setShareQuestionCount(int shareQuestionCount) {
		this.shareQuestionCount = shareQuestionCount;
	}

	public int getSharePaperCount() {
		return sharePaperCount;
	}

	public void setSharePaperCount(int sharePaperCount) {
		this.sharePaperCount = sharePaperCount;
	}

	public int getShareCoursewareCount() {
		return shareCoursewareCount;
	}

	public void setShareCoursewareCount(int shareCoursewareCount) {
		this.shareCoursewareCount = shareCoursewareCount;
	}

	public int getShareMicrocourseCount() {
		return shareMicrocourseCount;
	}

	public void setShareMicrocourseCount(int shareMicrocourseCount) {
		this.shareMicrocourseCount = shareMicrocourseCount;
	}

	public int getShareBeikePkgCount() {
		return shareBeikePkgCount;
	}

	public void setShareBeikePkgCount(int shareBeikePkgCount) {
		this.shareBeikePkgCount = shareBeikePkgCount;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}


