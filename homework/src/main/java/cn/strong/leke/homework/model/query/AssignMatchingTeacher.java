package cn.strong.leke.homework.model.query;

/**
* 批量布置作业老师
* @author Zhang Fujun
* @date 2016年7月22日
*/
public class AssignMatchingTeacher {

	private Integer index;
	private Long id;
	private String name;

	public AssignMatchingTeacher(Integer index, Long id, String name) {
		super();
		this.index = index;
		this.id = id;
		this.name = name;
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
