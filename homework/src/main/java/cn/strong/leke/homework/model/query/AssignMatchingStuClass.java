package cn.strong.leke.homework.model.query;

import java.util.List;

/**
 * 批量布置作业
 * 选择学生班级 以及 对应的索引
 * @author Zhang Fujun
 * @date 2016年7月22日
 */
public class AssignMatchingStuClass {

	private Integer index;
	private List<Long> classIds;

	/**
	 * @return the classIds
	 */
	public List<Long> getClassIds() {
		return classIds;
	}

	/**
	 * @param classIds the classIds to set
	 */
	public void setClassIds(List<Long> classIds) {
		this.classIds = classIds;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

}
