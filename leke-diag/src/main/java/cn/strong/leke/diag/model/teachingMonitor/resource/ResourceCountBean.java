package cn.strong.leke.diag.model.teachingMonitor.resource;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-24 16:46:05
 */
public class ResourceCountBean {

	private int type;
	private int questionCount;
	private int paperCount;
	private int coursewareCount;
	private int microcourseCount;
	private int beikePkgCount;

	private int resType;
	private String resTypeName;
	private int createCount;
	private int shareCount;

	public int getResType() {
		return resType;
	}

	public void setResType(int resType) {
		this.resType = resType;
	}

	public String getResTypeName() {
		return resTypeName;
	}

	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}

	public int getCreateCount() {
		return createCount;
	}

	public void setCreateCount(int createCount) {
		this.createCount = createCount;
	}

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public int getPaperCount() {
		return paperCount;
	}

	public void setPaperCount(int paperCount) {
		this.paperCount = paperCount;
	}

	public int getCoursewareCount() {
		return coursewareCount;
	}

	public void setCoursewareCount(int coursewareCount) {
		this.coursewareCount = coursewareCount;
	}

	public int getMicrocourseCount() {
		return microcourseCount;
	}

	public void setMicrocourseCount(int microcourseCount) {
		this.microcourseCount = microcourseCount;
	}

	public int getBeikePkgCount() {
		return beikePkgCount;
	}

	public void setBeikePkgCount(int beikePkgCount) {
		this.beikePkgCount = beikePkgCount;
	}
}
