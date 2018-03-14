package cn.strong.leke.homework.model.mongo;

import java.io.Serializable;

public class ExerciseRankUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8336950845744250826L;
	/**
	 * 学生排名
	 */
	private Long id;
	private Long total;
	private Integer rank;


	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
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

}
