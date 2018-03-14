package cn.strong.leke.homework.dao.mongo;

public class MongoDaoConsts {

	private String findBatchProgressByHomeworkId_MAP;

	private String findBatchProgressByHomeworkId_REDUCE;

	private String findBatchProgressByHomeworkId_FINALIZE;

	public String getFindBatchProgressByHomeworkId_MAP() {
		return findBatchProgressByHomeworkId_MAP;
	}

	public void setFindBatchProgressByHomeworkId_MAP(String findBatchProgressByHomeworkId_MAP) {
		this.findBatchProgressByHomeworkId_MAP = findBatchProgressByHomeworkId_MAP;
	}

	public String getFindBatchProgressByHomeworkId_REDUCE() {
		return findBatchProgressByHomeworkId_REDUCE;
	}

	public void setFindBatchProgressByHomeworkId_REDUCE(String findBatchProgressByHomeworkId_REDUCE) {
		this.findBatchProgressByHomeworkId_REDUCE = findBatchProgressByHomeworkId_REDUCE;
	}

	public String getFindBatchProgressByHomeworkId_FINALIZE() {
		return findBatchProgressByHomeworkId_FINALIZE;
	}

	public void setFindBatchProgressByHomeworkId_FINALIZE(String findBatchProgressByHomeworkId_FINALIZE) {
		this.findBatchProgressByHomeworkId_FINALIZE = findBatchProgressByHomeworkId_FINALIZE;
	}
}
